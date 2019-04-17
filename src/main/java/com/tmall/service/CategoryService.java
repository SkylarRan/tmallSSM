package com.tmall.service;

import com.tmall.pojo.Category;
import com.tmall.util.Page;

import java.util.List;

public interface CategoryService {
    int total();
    List<Category> list(Page page);
    void add(Category category);
}
