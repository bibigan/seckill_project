package com.java.controller;
import java.text.SimpleDateFormat;
import java.util.*;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import org.springframework.web.util.HtmlUtils;
import org.apache.commons.lang.math.RandomUtils;
import javax.servlet.http.HttpSession;

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
    @GetMapping("/users")
    public String listUsers() throws Exception {
        System.out.println("访问users");
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
        System.out.println("访问/register");
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
//        System.out.println("不存在用户名"+name);
//        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
//        int times = 2;
//        String algorithmName = "md5";
//
//        String encodedPassword = new SimpleHash(algorithmName, password, salt, times).toString();
//
//        user.setSalt(salt);
//        Users user=new Users(name,password,email);
//        user.setPassword(encodedPassword);
//        user.setUser_password(password);
        usersService.add(user);

        return Result.success();
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestHeader Map<String,Object> he,@RequestBody Map<String,Object> para) throws JsonProcessingException {
        System.out.println("访问login");
        String username=(String)para.get("user_name");
        username = HtmlUtils.htmlEscape(username);
        String password=(String)para.get("user_password");

        Users res=usersService.getByNameAndPassword(username,password);

        ObjectMapper objectMapper=new ObjectMapper();
        if(null!=res){
            System.out.println("登录成功！");
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
        System.out.println("访问/items");
        List<Item_kill> item_kills=item_killService.list();
        List<ItemInVuex> items=new ArrayList<>();
        for (Item_kill item_kill:item_kills){
            int item_id=item_kill.getItem_id();
            Item item=itemService.get(item_id);
            ItemInVuex itemInVuex=new ItemInVuex(item_kill.getId(),item.getItem_title(),item.getItem_img(),item.getItem_price(),item.getItem_stock(),item_kill.getItem_kill_seckillStartTime(),item_kill.getItem_kill_seckillEndTime());
            items.add(itemInVuex);
        }
//        String jsonString=JSON.toJSONString(items);
//        return jsonString;
        Collections.sort(items, new ComparatorItems());

        System.out.println(JSON.toJSONString(items));
        return items;
    }
    @GetMapping("/order")
    public Object listOrders() throws Exception {
//        Users users=(Users) session.getAttribute("user");
        //先根据uid找到其所有的orders,遍历os,依次设置ocode，number，create_time
        //期间通过item_id找到对应的item，设置title,img,price
        System.out.println("访问/order");
        int user_id=getUserId();
        List<OrdersInVuex> orders=new ArrayList<>();
        List<Orders> ordersList=ordersService.listByUid(user_id);
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss" );
        for(Orders o:ordersList){
            Item item=itemService.get(o.getItem_id());
            OrdersInVuex ordersInVuex=new OrdersInVuex(o.getItem_id(),item.getItem_title(),item.getItem_img(),item.getItem_price(),sdf.format(o.getOrders_create_time()),o.getOrders_ocode(),o.getOrders_number());
            orders.add(ordersInVuex);
        }
//        System.out.println("orders:"+orders);
        System.out.println(JSON.toJSONString(orders));
        return orders;
    }

    @PostMapping("buy")
    @Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = "Exception")//通过注解进行事务管理
    public String createOrder(@RequestBody Map<String,Object> para) throws JsonProcessingException {
        //获取user_id，orders_number,item_kill_id
        //通过item_kill_id，找到item_kill，获取item_id
        //生成orders_ocode,orders_create_time
        //根据item_id获取item，判断是否超卖，是orders_status=-1购买失败，否则=0
        //成功时，item的库存-orders_number，更新数据库
        //设置orders，加到数据库
        System.out.println("访问/buy");
        System.out.println("para:"+para);
        String message ="";

        int user_id=getUserId();
        Integer orders_number=(Integer)para.get("orders_number");
        Integer item_kill_id=(Integer)para.get("item_kill_id");

//        System.out.println("orders_number:"+orders_number+",item_kill_id: "+item_kill_id);
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
        }

        Orders orders=new Orders(orderCode,orders_number,item_id,item_kill_id,user_id,orders_status,new Date());
        ordersService.add(orders);
        System.out.println(orders);
        return message;
    }
    public int getUserId(){
        String userName=TokenUtil.getUserName();
        return usersService.getByName(userName).getId();
    }
}
