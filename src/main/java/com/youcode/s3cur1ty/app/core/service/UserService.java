package com.youcode.s3cur1ty.app.core.service;

import com.youcode.s3cur1ty.app.core.database.model.entity.User;


public interface UserService {
    public User save(User user);
    public User findByID(String id);

}
