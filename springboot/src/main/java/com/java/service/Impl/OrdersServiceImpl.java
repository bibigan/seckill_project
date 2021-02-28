package com.java.service.Impl;

import com.java.controller.UsersController;
import com.java.mapper.OrdersMapper;
import com.java.pojo.Orders;
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
}
