package com.java.service.Impl;

import com.java.mapper.UsersMapper;
import com.java.pojo.Users;
import com.java.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersMapper usersMapper;

    @Override
    public void add(Users c) {
        usersMapper.save(c);
    }

    @Override
    public void delete(int id) {
        usersMapper.delete(id);
    }

    @Override
    public void update(Users c) {
        usersMapper.update(c);
    }

    @Override
    public Users get(int id) {
        return usersMapper.get(id);
    }

    @Override
    public List<Users> list() {
        return usersMapper.findAll();
    }
}
