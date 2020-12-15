package com.java.service;

import com.java.pojo.K_order;

import java.util.List;

public interface K_orderService {
    void add(K_order c);
    void delete(int id);
    void update(K_order c);
    K_order get(int id);
    List<K_order> list();
}
