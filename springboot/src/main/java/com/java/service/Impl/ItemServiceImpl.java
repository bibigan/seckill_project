package com.java.service.Impl;

import com.java.mapper.ItemMapper;
import com.java.pojo.Item;
import com.java.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames="items")
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemMapper itemMapper;;
    @Override
    @CacheEvict(allEntries=true)
    public void add(Item c) {
        itemMapper.save(c);
    }

    @Override
    @CacheEvict(key="'items-one-'+ #p0")
    public void delete(int id) {
        itemMapper.delete(id);
    }

    @Override
    @CacheEvict(allEntries=true)
    public void update(Item c) {
        itemMapper.update(c);
    }

    @Override
    @Cacheable(key="'items-one-'+ #p0")
    public Item get(int id) {
        return itemMapper.get(id);
    }

    @Override
    @Cacheable(key="'items-all'")
    public List<Item> list() {
        return itemMapper.findAll();
    }
}
