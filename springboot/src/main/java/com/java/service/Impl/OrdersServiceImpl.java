package com.java.service.Impl;

import com.java.mapper.OrdersMapper;
import com.java.pojo.Orders;
import com.java.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig(cacheNames="orders")
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersMapper ordersMapper;

    @Override
    @CacheEvict(allEntries=true)
    public void add(Orders c) {
        ordersMapper.save(c);
    }

    @Override
    @CacheEvict(key="'orders-one-'+ #p0")
    public void delete(int id) {
        ordersMapper.delete(id);
    }

    @Override
    @CacheEvict(allEntries=true)
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
}
