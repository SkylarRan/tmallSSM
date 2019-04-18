package com.tmall.service;

import com.tmall.pojo.Property;

import java.util.List;

public interface PropertyService {
    void add(Property property);
    void delete(int id);
    void update(Property property);
    Property get(int id);
    List<Property> list(int cid); //根据分类id查出每种分类的所有属性
}