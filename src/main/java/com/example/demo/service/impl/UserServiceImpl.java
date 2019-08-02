package com.example.demo.service.impl;

import com.example.demo.dto.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public List<User> findAll() {

        return repository.findAll();
    }

    @Override
    public void addUser(User user) {
        user.setCreateDate(new Date());
        repository.save(user);
    }
}
