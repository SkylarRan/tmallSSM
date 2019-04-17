package com.tmall.mapper;

import com.tmall.pojo.Category;
import com.tmall.util.Page;

import java.util.List;

public interface CategoryMapper {
    List<Category> list(Page page);

    int total();

    void add(Category category);
}
