package com.blake.instance.common.service;

import com.blake.instance.common.domain.User;
import com.blake.instance.common.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(Long id) {
        return this.userMapper.getById(id);
    }
}
