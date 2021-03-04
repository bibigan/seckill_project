package com.java.service.Impl;

import com.java.controller.UsersController;
import com.java.mapper.OrdersMapper;
import com.java.pojo.Item;
import com.java.pojo.Orders;
import com.java.service.ItemService;
import com.java.service.OrdersService;
import org.apache.ibatis.cache.CacheKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@CacheConfig(cacheNames="orders")
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    ItemService itemService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
    @Override
//    @CacheEvict(allEntries=true)
    public void add(Orders c) {
        ordersMapper.save(c);
    }

    @Override
    @CacheEvict(key="'orders-one-'+ #p0")
    public void delete(int id) {
        ordersMapper.delete(id);
    }

    @Override
//    @CacheEvict(allEntries=true)
    public void update(Orders c) {
        ordersMapper.update(c);
    }

    @Override
    @Cacheable(key="'orders-one-'+ #p0")
    public Orders get(int id) {
        return ordersMapper.get(id);
    }

    @Override
    @Cacheable(key="'orders-all'")
    public List<Orders> list() {
        return ordersMapper.findAll();
    }

    @Override
    @Cacheable(key="'orders-uid-'+ #p0")
    public List<Orders> listByUid(int uid) {
        List<Orders> ordersList= ordersMapper.getByUid(uid);
        return ordersList;
    }
    @Override
    @CacheEvict(key="'orders-uid-'+ #p0")
    public void delOrderCache(int uid)   {
        String hashKey= "orders-uid-"+uid;
//        stringRedisTemplate.delete(hashKey);
        LOGGER.info("删除订单缓存:：[{}]",hashKey);
    }
    /**
     * 检查库存
     */
//    private Stock checkStock(int sid) {
//        Stock stock = stockService.getStockById(sid);
//        if (stock.getSale().equals(stock.getCount())) {
//            throw new RuntimeException("库存不足");
//        }
//        return stock;
//    }

    @Override
    public void createOrderByMq(Orders c, Item item) {
        //乐观锁更新库存
        boolean updateStock = saleStockOptimistic(item);
        if (!updateStock) {
            LOGGER.warn("扣减库存失败，库存已经为0");
            return;
        }

        LOGGER.info("扣减库存成功，剩余库存：[{}]", item.getItem_stock());
//        itemService.delItemCache(item.getId());
//        LOGGER.info("删除库存缓存");

        //创建订单
        LOGGER.info("写入订单至数据库");
        add(c);
//        LOGGER.info("写入订单至缓存供查询");
//        createOrderWithUserInfoInCache(item, c.getUser_id());
        LOGGER.info("下单完成");
    }
    /**
     * 创建订单：保存用户订单信息到缓存
     * @return 返回添加的个数
     */
//    private Long createOrderWithUserInfoInCache(Item item, Integer userId) {
//        String key = CacheKey.USER_HAS_ORDER.getKey() + "_" + stock.getId().toString();
//        LOGGER.info("写入用户订单数据Set：[{}] [{}]", key, userId.toString());
//        return stringRedisTemplate.opsForSet().add(key, userId.toString());
//    }
    /**
     * 更新库存 乐观锁
     */
    private boolean saleStockOptimistic(Item item) {
        LOGGER.info("查询数据库，尝试更新库存");
        int count = itemService.updateStockByOptimistic(item);
        return count != 0;
    }
}
