package com.java.service;



import com.java.pojo.Product_kill;

import java.util.List;

public interface Product_killService {
    void add(Product_kill c);
    void delete(int id);
    void update(Product_kill c);
    Product_kill get(int id);
    List<Product_kill> list();
}
