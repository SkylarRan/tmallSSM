package com.tmall.service;

import com.tmall.pojo.User;

import java.util.List;

public interface UserService {
    void add(User u);
    void delete(int id);
    void update(User u);
    User get(int id);
    List list();
}
