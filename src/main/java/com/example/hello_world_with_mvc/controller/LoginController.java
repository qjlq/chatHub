package com.example.hello_world_with_mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hello_world_with_mvc.service.LoginService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService lService;

    @RequestMapping("/logins")
    public String loginDepend(String json){
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        String uid = jsonObject.get("cid").getAsString();
        String psw = jsonObject.get("password").getAsString();
        return lService.Login(uid, psw);
    }
}
