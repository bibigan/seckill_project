package com.java.service.Impl;

import com.java.controller.UsersController;
import com.java.mapper.ItemMapper;
import com.java.pojo.Item;
import com.java.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    ItemMapper itemMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
    @Override
//    @CacheEvict(allEntries=true)
    public void add(Item c) {
        itemMapper.save(c);
    }

    @Override
    @CacheEvict(key="'items-one-'+ #p0")
    public void delete(int id) {
        itemMapper.delete(id);
    }

    @Override
//    @CacheEvict(allEntries=true)
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

    @Override
    @CacheEvict(key="'items-one-'+ #p0")
    public void delItemCache(int id)   {
        String hashKey= "items-one-"+id;
//        stringRedisTemplate.delete(hashKey);
        LOGGER.info("删除商品缓存:：[{}]",hashKey);
    }

    @Override
    public int updateStockByOptimistic(Item c) {
        return itemMapper.updateStockByOptimistic(c);
    }
}
