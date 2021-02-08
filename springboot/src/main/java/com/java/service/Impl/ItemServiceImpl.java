package com.java.service.Impl;

import com.java.mapper.ItemMapper;
import com.java.pojo.Item;
import com.java.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemMapper itemMapper;;
    @Override
    public void add(Item c) {
        itemMapper.save(c);
    }

    @Override
    public void delete(int id) {
        itemMapper.delete(id);
    }

    @Override
    public void update(Item c) {
        itemMapper.update(c);
    }

    @Override
    public Item get(int id) {
        return itemMapper.get(id);
    }

    @Override
    public List<Item> list() {
        return itemMapper.findAll();
    }
}
