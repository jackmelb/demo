package com.anz.demo.service;

import com.anz.demo.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUser(String userId);
}
