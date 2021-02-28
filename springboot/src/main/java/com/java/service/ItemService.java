package com.java.service;

import com.java.pojo.Item;

import java.util.List;

public interface ItemService {
    void add(Item c);
    void delete(int id);
    void update(Item c);
    Item get(int id);
    List<Item> list();
    void delItemCache(int uid);
}
