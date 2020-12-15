package com.java.service;

import com.java.pojo.Product;

import java.util.List;

public interface ProductService {
    void add(Product c);
    void delete(int id);
    void update(Product c);
    Product get(int id);
    List<Product> list();
}
