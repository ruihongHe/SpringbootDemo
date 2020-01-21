package com.example.springboot.service;

import com.example.springboot.dto.User;

import java.util.List;

public interface UserService {



    List<User> findAll();

    void addUser(User user);

    void addUserByRedis(User user);

    User getUserByRedis(String account);
}
