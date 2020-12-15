package com.java.service.Impl;

import com.java.mapper.Product_killMapper;
import com.java.pojo.Product_kill;
import com.java.service.Product_killService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Product_killServiceImpl implements Product_killService {
    @Autowired
    Product_killMapper product_killMapper;
    @Override
    public void add(Product_kill c) {
        product_killMapper.save(c);
    }

    @Override
    public void delete(int id) {
        product_killMapper.delete(id);
    }

    @Override
    public void update(Product_kill c) {
        product_killMapper.update(c);
    }

    @Override
    public Product_kill get(int id) {
        return product_killMapper.get(id);
    }

    @Override
    public List<Product_kill> list() {
        return product_killMapper.findAll();
    }
}
