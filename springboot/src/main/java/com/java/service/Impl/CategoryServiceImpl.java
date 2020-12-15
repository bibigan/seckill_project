package com.java.service.Impl;

import com.java.mapper.CategoryMapper;
import com.java.pojo.Category;
import com.java.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public int add(Category Category) {
        return categoryMapper.save(Category);
    }

    @Override
    public void delete(int id) {
        categoryMapper.delete(id);
    }

    @Override
    public Category get(int id) {
        return categoryMapper.get(id);
    }

    @Override
    public int update(Category Category) {
        return categoryMapper.update(Category);
    }

    @Override
    public List<Category> list() {
        return categoryMapper.findAll();
    }
}