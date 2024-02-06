package com.youcode.s3cur1ty.app.core.service.impl;

import com.youcode.s3cur1ty.app.core.database.model.entity.User;
import com.youcode.s3cur1ty.app.core.database.repository.UserRepository;
import com.youcode.s3cur1ty.app.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User save(User user) {
        if (findByIdOrNULL(user.getSub()) != null) {
            return null;
        }
        return userRepository.save(user);
    }

    @Override
    public User findByID(String id) {
        return findOrThrow(id);
    }

    @Override
    public User findByIdOrNULL(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    private User findOrThrow(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
    }
}
