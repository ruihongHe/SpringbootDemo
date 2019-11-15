package com.example.demo.service.impl;

import com.example.demo.dto.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public List<User> findAll() {

        return repository.findAll();
    }

    @Override
    public void addUser(User user) {
        user.setCreateDate(new Date());
        repository.save(user);
    }

    @Override
    public void addUserByRedis(User user) {
        Gson gson= new Gson();
        redisTemplate.opsForValue().set(user.getAccount(),gson.toJson(user),30, TimeUnit.SECONDS);
    }

    @Override
    public User getUserByRedis(String account) {
        Gson gson= new Gson();
        User user=null;
        String   result =redisTemplate.opsForValue().get(account);
        if(!StringUtils.isEmpty(result))
           {
            user=gson.fromJson(result,User.class);
           }
        return user;
    }
}
