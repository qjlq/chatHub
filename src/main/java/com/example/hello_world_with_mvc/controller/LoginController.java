package com.example.hello_world_with_mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hello_world_with_mvc.service.LoginService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService lService;

    @RequestMapping("/logins")
    // @JsonInclude(Include.NON_NULL)
    public String loginDepend(String json){
        System.out.println(json);
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        String uid = jsonObject.get("cid").getAsString();
        String psw = jsonObject.get("password").getAsString();
        return lService.Login(uid, psw);
    }

    @RequestMapping("/signup")
    public String requestMethodName(String json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        String uid = jsonObject.get("cid").getAsString();
        String psw = jsonObject.get("password").getAsString();
        String username = jsonObject.get("username").getAsString();
        lService.SignUp(uid, psw, username);
        return new String();
    }
    
}
