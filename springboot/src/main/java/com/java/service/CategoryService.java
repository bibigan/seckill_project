package com.java.service;

import java.util.List;

import com.java.pojo.Category;

public interface CategoryService {
    public int add(Category Category);

    public void delete(int id);

    public Category get(int id);

    public int update(Category Category);

    public List<Category> list();
}
