package com.example.hello_world_with_mvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hello_world_with_mvc.dao.UserRepository;
import com.example.hello_world_with_mvc.entity.User;
import com.example.hello_world_with_mvc.service.DatabaseService;
import com.example.hello_world_with_mvc.service.LoginService;
import com.example.hello_world_with_mvc.service.UserService;

@Service
public class LoginServiceImpl implements LoginService {
    private User user;
    @Autowired
    private DatabaseService database;
    @Override
    public String Login(String uid,String password){
        System.err.println(uid + " " + password);
        String token = new String();
        user = database.getVaiUserID(uid);
        System.err.println(user.getUserId() + " "+user.getPassword());
        if (password.equals(user.getPassword())){
            token = "y";
        }else{
            token = "n";
        }
        return token;
    }
}
