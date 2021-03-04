package com.java.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.pojo.*;
import com.java.service.ItemService;
import com.java.service.Item_killService;
import com.java.service.OrdersService;
import com.java.service.UsersService;
import com.java.util.ComparatorItems;
import com.java.util.Result;
import com.java.util.TokenUtil;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class UsersController {
    @Autowired
    UsersService usersService;
    @Autowired
    ItemService itemService;
    @Autowired
    Item_killService item_killService;
    @Autowired
    OrdersService ordersService;
    @Autowired
    private AmqpTemplate rabbitTemplate;

    //每秒放行10个请求 令牌
    RateLimiter rateLimiter = RateLimiter.create(10);

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

//    // 延时时间：预估读数据库数据业务逻辑的耗时，用来做缓存再删除
//    private static final int DELAY_MILLSECONDS = 1000;
//    // 延时双删线程池
//    private static ExecutorService cachedThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());

    @GetMapping("/users")
    public String listUsers() throws Exception {
        LOGGER.info("访问users");
        String jsonString=JSON.toJSONString(usersService.list());
        return jsonString;
    }
    /*
    1. 通过参数Users获取浏览器提交的账号密码
    2. 通过HtmlUtils.htmlEscape(name);把账号里的特殊符号进行转义
    3. 判断用户名是否存在
    3.1 如果已经存在，就返回Result.fail,并带上错误信息
    3.2 如果不存在，则加入到数据库中，并返回 Result.success()*/
    @PostMapping("/register")
    public String add(@RequestBody Users user) throws Exception {
        LOGGER.info("访问/register");
//        System.out.println("参数："+user.getUser_name()+","+user.getUser_password()+","+user.getUser_email());
        String name =  user.getUser_name();
//        String password = user.getUser_password();
        name = HtmlUtils.htmlEscape(name);
//        user.setUser_name(name);

        boolean exist = usersService.isExist(name);

        if(exist){
//            console("存在用户名"+name);
            String message ="用户名已经被使用,不能使用";
            return Result.fail(message);

        }
        usersService.add(user);

        return Result.success();
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestHeader Map<String,Object> he,@RequestBody Map<String,Object> para) throws JsonProcessingException {
        LOGGER.info("访问login");
        String username=(String)para.get("user_name");
        username = HtmlUtils.htmlEscape(username);
        String password=(String)para.get("user_password");

        Users res=usersService.getByNameAndPassword(username,password);

        ObjectMapper objectMapper=new ObjectMapper();
        if(null!=res){
            LOGGER.info("登录成功！");
            String token= TokenUtil.sign(res);
            HashMap<String,Object> hs=new HashMap<>();
            hs.put("token","token"+token);
            return objectMapper.writeValueAsString(hs);
        }
        return "error";
    }
    @GetMapping("/items")
    public Object listItems() throws Exception {
        /*先得到ik的list，遍历list，为每个ik找到对应的i，加入到新list*/
        LOGGER.info("访问/items");
        List<Item_kill> item_kills=item_killService.list();
        List<ItemInVuex> items=new ArrayList<>();
        for (Item_kill item_kill:item_kills){
            int item_id=item_kill.getItem_id();
            Item item=itemService.get(item_id);
            ItemInVuex itemInVuex=new ItemInVuex(item_kill.getId(),item.getItem_title(),item.getItem_img(),item.getItem_price(),item.getItem_stock(),item_kill.getItem_kill_seckillStartTime(),item_kill.getItem_kill_seckillEndTime());
            items.add(itemInVuex);
        }
        Collections.sort(items, new ComparatorItems());

        return items;
    }
    @GetMapping("/order")
    public Object listOrders() throws Exception {
        //先根据uid找到其所有的orders,遍历os,依次设置ocode，number，create_time
        //期间通过item_id找到对应的item，设置title,img,price
        LOGGER.info("访问/order");
        int user_id=getUserId();
        List<OrdersInVuex> orders=new ArrayList<>();
        List<Orders> ordersList=ordersService.listByUid(user_id);
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss" );
        for(Orders o:ordersList){
            Item item=itemService.get(o.getItem_id());
            OrdersInVuex ordersInVuex=new OrdersInVuex(o.getItem_id(),item.getItem_title(),item.getItem_img(),item.getItem_price(),sdf.format(o.getOrders_create_time()),o.getOrders_ocode(),o.getOrders_number());
            orders.add(ordersInVuex);
        }
        return orders;
    }
    /*缓存一致性+乐观锁防止超卖+接口限流+单一用户访问控制*/
    @PostMapping("buy")
    public String createOrder(@RequestBody Map<String,Object> para) throws JsonProcessingException {
        LOGGER.info("访问/buy");
        // 阻塞式获取令牌
        //LOGGER.info("等待时间" + rateLimiter.acquire());
        // 非阻塞式获取令牌——初始化了令牌桶类，每秒放行10个请求
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
            LOGGER.warn("你被限流了，真不幸，直接返回失败");
            return "接口被限流";
        }
        LOGGER.info("未被限流");

        int user_id=getUserId();
        Integer orders_number=(Integer)para.get("orders_number");
        Integer item_kill_id=(Integer)para.get("item_kill_id");
        String mess="";

        int count = usersService.addUserCount(user_id);
        LOGGER.info("用户截至该次的访问次数为: [{}]", count);
        boolean isBanned = usersService.getUserIsBanned(user_id);
        if (isBanned) {
            return "购买失败，超过频率限制";
        }

        try{
            //乐观锁更新数据库
            mess= buy(orders_number,item_kill_id,user_id);
        }catch (Exception e) {
            LOGGER.error("购买失败：[{}]", e.getMessage());
        }

        return mess;
//        return "订单生成";
    }
    /*缓存一致性+乐观锁防止超卖+接口限流*/
    @PostMapping("buy3")
    public String createOrder3(@RequestBody Map<String,Object> para) throws JsonProcessingException {
        LOGGER.info("访问/buy");
        // 阻塞式获取令牌
        //LOGGER.info("等待时间" + rateLimiter.acquire());
        // 非阻塞式获取令牌——初始化了令牌桶类，每秒放行10个请求
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
            LOGGER.warn("你被限流了，真不幸，直接返回失败");
            return "超卖";
        }

        int user_id=getUserId();
        Integer orders_number=(Integer)para.get("orders_number");
        Integer item_kill_id=(Integer)para.get("item_kill_id");
        String mess="";
        try{
            //更新数据库
            mess= buy(orders_number,item_kill_id,user_id);
        }catch (Exception e) {
            LOGGER.error("购买失败：[{}]", e.getMessage());
        }

        return mess;
//        return "订单生成";
    }
    /*缓存一致性+乐观锁防止超卖*/
    @PostMapping("buy2")
    public String createOrder2(@RequestBody Map<String,Object> para) throws JsonProcessingException {
        LOGGER.info("访问/buy");
        int user_id=getUserId();
        Integer orders_number=(Integer)para.get("orders_number");
        Integer item_kill_id=(Integer)para.get("item_kill_id");
        String mess="";
        try{
            //乐观锁更新数据库
            mess= buy(orders_number,item_kill_id,user_id);
        }catch (Exception e) {
            LOGGER.error("购买失败：[{}]", e.getMessage());
        }
        LOGGER.info("购买成功");

        return mess;
//        return "订单生成";
    }
    /*缓存一致性*/
    @PostMapping("buy1")
    public String createOrder1(@RequestBody Map<String,Object> para) throws JsonProcessingException {
        LOGGER.info("访问/buy");
        int user_id=getUserId();
        Integer orders_number=(Integer)para.get("orders_number");
        Integer item_kill_id=(Integer)para.get("item_kill_id");
        String mess="";
        try{
            //悲观更新数据库
            mess= buy1(orders_number,item_kill_id,user_id);
//            //删除缓存
//            ordersService.delOrderCache(user_id);
//            try{
//                // 延时指定时间后再次删除缓存
//                cachedThreadPool.execute(new delCacheByThread(user_id));
//            }catch (Exception e){
//                // 上述再次删除缓存没成功，通知消息队列进行删除缓存
//                sendToDelCache(String.valueOf(user_id));
//            }
        }catch (Exception e) {
            LOGGER.error("购买失败：[{}]", e.getMessage());
        }
        LOGGER.info("购买成功");

        return mess;
//        return "订单生成";
    }
//    /**
//     * 缓存再删除线程
//     */
//    private class delCacheByThread implements Runnable {
//        private int user_id;
//        public delCacheByThread(int user_id) {
//            this.user_id = user_id;
//        }
//        public void run() {
//            try {
//                LOGGER.info("异步执行缓存再删除，商品id：[{}]， 首先休眠：[{}] 毫秒", user_id, DELAY_MILLSECONDS);
//                Thread.sleep(DELAY_MILLSECONDS);
//                ordersService.delOrderCache(user_id);
//                LOGGER.info("再次删除商品id：[{}] 缓存", user_id);
//            } catch (Exception e) {
//                LOGGER.error("delCacheByThread执行出错", e);
//            }
//        }
//    }
    private void sendToDelCache(String message) {
        LOGGER.info("这就去通知消息队列开始重试删除缓存：[{}]", message);
        this.rabbitTemplate.convertAndSend("delCache", message);
    }
    /*未考虑到缓存一致性*/
    @PostMapping("buy0")
    public String createOrder0(@RequestBody Map<String,Object> para) throws JsonProcessingException {
        LOGGER.info("访问/buy");
        int user_id=getUserId();
        Integer orders_number=(Integer)para.get("orders_number");
        Integer item_kill_id=(Integer)para.get("item_kill_id");

        //更新数据库
        String mess= buy(orders_number,item_kill_id,user_id);
        //删除缓存
        ordersService.delOrderCache(user_id);
        return mess;
//        return "订单生成";
    }

    @GetMapping("del")
    public String del(){
        ordersService.delOrderCache(4);
        return "OK";
    }
    /**
     * 乐观锁更新库存+异步生成订单
     */
    public String buy(Integer orders_number,Integer item_kill_id,int user_id) throws JsonProcessingException {
       String message ="";
       try {
            int item_id = item_killService.get(item_kill_id).getItem_id();
            String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);

            Byte orders_status = 0;
            Item item = itemService.get(item_id);
            int num=item.getItem_stock()-orders_number;
            LOGGER.info("检查缓存中商品是否还有库存");
            if(num<0){//超卖
                orders_status =-1;
                LOGGER.info("秒杀请求失败，库存不足");
                message="超卖";
            }else {
                // 有库存，则将用户id和商品id封装为消息体传给消息队列处理
                // 注意这里的有库存和已经下单都是缓存中的结论，存在不可靠性，在消息队列中会查表再次验证
                LOGGER.info("有库存：[{}]", item.getItem_stock());

                item.setItem_stock(num);
                //乐观锁更新库存
//                saleStockOptimistic(item);
                //异步生成订单
                Orders orders = new Orders(orderCode, orders_number, item_id, item_kill_id, user_id, orders_status, new Date());
//                ordersService.add(orders);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("orders", orders);
                jsonObject.put("item", item);
                sendToOrderQueue(jsonObject.toJSONString());

                message = "订单生成";
            }
        }catch (Exception e) {
            LOGGER.error("下单接口：异步处理订单异常：", e);
            return "秒杀请求失败，服务器正忙.....";
        }
        return message;
    }
    /**
     * 乐观锁更新库存
     */
    public String buy2(Integer orders_number,Integer item_kill_id,int user_id) throws JsonProcessingException {
        String message ="";

        int item_id = item_killService.get(item_kill_id).getItem_id();
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);

        Byte orders_status = 0;
        Item item = itemService.get(item_id);
        int num=item.getItem_stock()-orders_number;
        LOGGER.info("检查缓存中商品是否还有库存");
        if(num<0){//超卖
            orders_status =-1;
            LOGGER.info("秒杀请求失败，库存不足");
            message="超卖";
        }else{
            // 有库存，则将用户id和商品id封装为消息体传给消息队列处理
            // 注意这里的有库存和已经下单都是缓存中的结论，存在不可靠性，在消息队列中会查表再次验证
            LOGGER.info("有库存：[{}]", item.getItem_stock());
            item.setItem_stock(num);
            //乐观锁更新库存
            saleStockOptimistic(item);

            message="订单生成";
            Orders orders=new Orders(orderCode,orders_number,item_id,item_kill_id,user_id,orders_status,new Date());
            ordersService.add(orders);
        }
        return message;
    }
    /*
    * 无乐观锁解决超卖
    * */
    @Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = "Exception")//通过注解进行事务管理
    public String buy1(Integer orders_number,Integer item_kill_id,int user_id) throws JsonProcessingException {
        String message ="";

        int item_id = item_killService.get(item_kill_id).getItem_id();
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);

        Byte orders_status = 0;
        Item item = itemService.get(item_id);
        int num=item.getItem_stock()-orders_number;
        if(num<0){//超卖
            orders_status =-1;
            message="超卖";
        }else{
            item.setItem_stock(num);
            itemService.update(item);

            message="订单生成";
            Orders orders=new Orders(orderCode,orders_number,item_id,item_kill_id,user_id,orders_status,new Date());
            ordersService.add(orders);
        }
        return message;
    }
    /*
    * 乐观锁更新库存
    * */
    private void saleStockOptimistic(Item item) {
        LOGGER.info("查询数据库，尝试更新库存");
        int count = itemService.updateStockByOptimistic(item);
        if (count == 0){
            throw new RuntimeException("并发更新库存失败，version不匹配") ;
        }
    }

//    @PostMapping("buy")
//    @Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = "Exception")//通过注解进行事务管理
//    public String createOrder(@RequestBody Map<String,Object> para) throws JsonProcessingException {
//        //获取user_id，orders_number,item_kill_id
//        //通过item_kill_id，找到item_kill，获取item_id
//        //生成orders_ocode,orders_create_time
//        //根据item_id获取item，判断是否超卖，是orders_status=-1购买失败，否则=0
//        //成功时，item的库存-orders_number，更新数据库
//        //设置orders，加到数据库
//        System.out.println("访问/buy1");
//        String message ="";
//
//        int user_id=getUserId();
//        Integer orders_number=(Integer)para.get("orders_number");
//        Integer item_kill_id=(Integer)para.get("item_kill_id");
//
////        System.out.println("orders_number:"+orders_number+",item_kill_id: "+item_kill_id);
//        int item_id = item_killService.get(item_kill_id).getItem_id();
//        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
//
//        Byte orders_status = 0;
//        Item item = itemService.get(item_id);
//        int num=item.getItem_stock()-orders_number;
//        if(num<0){//超卖
//            orders_status =-1;
//            message="超卖";
//        }else{
//            item.setItem_stock(num);
//            itemService.update(item);
//            message="订单生成";
//        }
//
//        Orders orders=new Orders(orderCode,orders_number,item_id,item_kill_id,user_id,orders_status,new Date());
//        ordersService.add(orders);
//        System.out.println(orders);
//        return message;
//    }
    public int getUserId(){
        String userName=TokenUtil.getUserName();
        return usersService.getByName(userName).getId();
    }
    /**
     * 向消息队列orderQueue发送消息
     * @param message
     */
    private void sendToOrderQueue(String message) {
        LOGGER.info("这就去通知消息队列开始下单：[{}]", message);
        this.rabbitTemplate.convertAndSend("orderQueue", message);
    }
}
