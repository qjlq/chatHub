package com.example.hello_world_with_mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.example.hello_world_with_mvc.service.LoginService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class LoginController {
    private LoginService lService;

    @RequestMapping("/login")
    public String loginDepend(String json){
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        String uid = jsonObject.get("cid").getAsString();
        String psw = jsonObject.get("password").getAsString();
        return lService.Login(uid, psw);
    }
}
