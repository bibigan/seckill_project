package com.java.service;

import com.java.pojo.Item;
import com.java.pojo.Orders;

import java.util.List;

public interface OrdersService {
    void add(Orders c);
    void delete(int id);
    void update(Orders c);
    Orders get(int id);
    List<Orders> list();
    List<Orders> listByUid(int uid);
    void delOrderCache(int uid);
    void createOrderByMq(Orders c, Item item);
}
