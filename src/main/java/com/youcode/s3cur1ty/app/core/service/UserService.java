package com.youcode.s3cur1ty.app.core.service;

import com.youcode.s3cur1ty.app.core.database.model.entity.User;

import java.util.List;


public interface UserService {
    public User save(User user);
    public User findByID(String id);
    public User findByIdOrNULL(String id);

    List<User> findAll();
}
