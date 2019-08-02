package com.example.demo.service;

import com.example.demo.dto.User;

import java.util.List;

public interface UserService {



    List<User> findAll();

    void addUser(User user);
}
