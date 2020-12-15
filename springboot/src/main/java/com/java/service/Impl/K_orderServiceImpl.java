package com.java.service.Impl;

import com.java.mapper.K_orderMapper;
import com.java.pojo.K_order;
import com.java.service.K_orderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class K_orderServiceImpl implements K_orderService {
    @Autowired
    K_orderMapper k_orderMapper;
    @Override
    public void add(K_order c) {
        k_orderMapper.save(c);
    }

    @Override
    public void delete(int id) {
        k_orderMapper.delete(id);
    }

    @Override
    public void update(K_order c) {
        k_orderMapper.update(c);
    }

    @Override
    public K_order get(int id) {
        return k_orderMapper.get(id);
    }

    @Override
    public List<K_order> list() {
        return k_orderMapper.findAll();
    }
}
