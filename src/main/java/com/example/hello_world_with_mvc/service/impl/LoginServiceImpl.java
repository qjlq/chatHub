package com.example.hello_world_with_mvc.service.impl;

import com.example.hello_world_with_mvc.dao.UserRepository;
import com.example.hello_world_with_mvc.entity.User;
import com.example.hello_world_with_mvc.service.DatabaseService;
import com.example.hello_world_with_mvc.service.LoginService;
import com.example.hello_world_with_mvc.service.UserService;

public class LoginServiceImpl implements LoginService {
    private User user;
    private DatabaseService database;
    @Override
    public String Login(String uid,String password){
        String token = new String();
        user = database.getVaiUserID(uid);
        if (password.equals(user.getPassword())){
            token = "y";
        }else{
            token = "n";
        }
        return token;
    }
}
