package com.java.service.Impl;

import com.java.mapper.OrdersMapper;
import com.java.pojo.Orders;
import com.java.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    OrdersMapper ordersMapper;
    @Override
    public void add(Orders c) {
        ordersMapper.save(c);
    }

    @Override
    public void delete(int id) {
        ordersMapper.delete(id);
    }

    @Override
    public void update(Orders c) {
        ordersMapper.update(c);
    }

    @Override
    public Orders get(int id) {
        return ordersMapper.get(id);
    }

    @Override
    public List<Orders> list() {
        return ordersMapper.findAll();
    }

    @Override
    public List<Orders> listByUid(int uid) {
        return ordersMapper.getByUid(uid);
    }
}
