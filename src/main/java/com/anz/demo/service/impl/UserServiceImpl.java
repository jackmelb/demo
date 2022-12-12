package com.anz.demo.service.impl;

import com.anz.demo.model.User;
import com.anz.demo.repository.UserRepository;
import com.anz.demo.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUser(String userId) {
        return userRepository.findById(userId);
    }
}
