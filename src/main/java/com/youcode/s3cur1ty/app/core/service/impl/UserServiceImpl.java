package com.youcode.s3cur1ty.app.core.service.impl;

import com.youcode.s3cur1ty.app.core.database.model.entity.User;
import com.youcode.s3cur1ty.app.core.database.repository.UserRepository;
import com.youcode.s3cur1ty.app.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User save(User user) {
        if (findByID(user.getSub()) != null) {
            return null;
        }
        return userRepository.save(user);
    }

    @Override
    public User findByID(String id) {
        return findOrThrow(id);
    }

    private User findOrThrow(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
}
