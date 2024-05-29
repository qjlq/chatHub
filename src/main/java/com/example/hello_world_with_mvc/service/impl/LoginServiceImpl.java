package com.example.hello_world_with_mvc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.hello_world_with_mvc.dao.UserRepository;
import com.example.hello_world_with_mvc.entity.User;
import com.example.hello_world_with_mvc.service.DatabaseService;
import com.example.hello_world_with_mvc.service.LoginService;
import com.example.hello_world_with_mvc.service.UserService;
import com.example.hello_world_with_mvc.utils.JWTUtil;
import com.example.hello_world_with_mvc.utils.TokenUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class LoginServiceImpl implements LoginService {


    // private User user;

    
    @Autowired
    private DatabaseService database;
    @Override
    public String Login(String email,String password){
        // System.err.println(uid + " " + password);
        String token = new String();
        User user = database.getVaiEmail(email);
        // System.err.println(user.getUserId() + " "+user.getPassword());
        try {
            if (password.equals(user.getPassword()) || user.getUserId() != null){
                // token = JWTUtil.createJwtToken(uid);
                // token = TokenUtil.signToken(email);
                token = TokenUtil.signToken(user.getUserId());
                log.info("email:{},token:{}",email,token);
    
            }else{
                token = "400";
                log.info("error password: email:{}",email);
            }
        } catch (Exception e) {
            token = "500";
        }

        return token;
    }
    @Override
    public String SignUp(String uid,String email,String password,String username){
        String token = new String();
        if (database.addUser(uid,email,password, username) == 1){
            token = "y";
        }else{
            token = "n";
        }
        return token;
    }

    @Override
    public String getCid(String email){
        User user = database.getVaiEmail(email);
        return user.getUserId();
    }

}
