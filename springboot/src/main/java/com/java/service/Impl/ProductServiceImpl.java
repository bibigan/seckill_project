package com.java.service.Impl;

import com.java.mapper.ProductMapper;
import com.java.pojo.Product;
import com.java.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;;
    @Override
    public void add(Product c) {
        productMapper.save(c);
    }

    @Override
    public void delete(int id) {
        productMapper.delete(id);
    }

    @Override
    public void update(Product c) {
        productMapper.update(c);
    }

    @Override
    public Product get(int id) {
        return productMapper.get(id);
    }

    @Override
    public List<Product> list() {
        return productMapper.findAll();
    }
}
