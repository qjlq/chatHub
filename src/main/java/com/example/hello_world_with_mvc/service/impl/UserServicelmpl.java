package com.example.hello_world_with_mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hello_world_with_mvc.dao.UserRepository;
import com.example.hello_world_with_mvc.entity.User;
import com.example.hello_world_with_mvc.service.UserService;

@Service //@Service注解用于类上，标记当前类是一个service类，加上该注解会将当前类自动注入到spring容中。
public class UserServicelmpl implements UserService {
    
    @Autowired
    private UserRepository userDao;

    // @Override
    // public void addUser(User user){
    //     userDao.save(user);
    // }

    @Override
    public List<User> list(){
        return userDao.findAll();
    }
}
