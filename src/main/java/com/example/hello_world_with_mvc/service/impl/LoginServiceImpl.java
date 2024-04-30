package com.example.hello_world_with_mvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.hello_world_with_mvc.dao.UserRepository;
import com.example.hello_world_with_mvc.entity.User;
import com.example.hello_world_with_mvc.service.DatabaseService;
import com.example.hello_world_with_mvc.service.LoginService;
import com.example.hello_world_with_mvc.service.UserService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class LoginServiceImpl implements LoginService {


    private User user;
    @Autowired
    private DatabaseService database;
    @Override
    public String Login(String uid,String password){
        // System.err.println(uid + " " + password);
        String token = new String();
        user = database.getVaiUserID(uid);
        // System.err.println(user.getUserId() + " "+user.getPassword());
        if (password.equals(user.getPassword()) || user.getUserId() != null){
            token = "y";
        }else{
            token = "n";
            log.info("error password: cid:{}",uid);
        }
        return token;
    }
    @Override
    public String SignUp(String uid,String password,String username){
        String token = new String();
        if (database.addUser(uid, password, username) == 1){
            token = "y";
        }else{
            token = "n";
        }
        return token = "y";
    }
}
