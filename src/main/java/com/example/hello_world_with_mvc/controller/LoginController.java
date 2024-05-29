package com.example.hello_world_with_mvc.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hello_world_with_mvc.service.LoginService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j; // log的引用

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
@Slf4j
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
        String email = jsonObject.get("email").getAsString();
        String psw = jsonObject.get("password").getAsString();
        return lService.Login(email, psw);
    }

    @RequestMapping("/signup")
    public String requestMethodName(String json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        String email = jsonObject.get("email").getAsString();
        String uid = "c" + UUID.randomUUID().toString().replaceAll("-", "").replaceAll("e", "a");
        String psw = jsonObject.get("password").getAsString();
        String username = jsonObject.get("username").getAsString();
        try {
            lService.SignUp(uid,email, psw, username);
            log.info("新用户： " + email);
        } catch(DuplicateKeyException e){
            log.info("注册的邮箱: " + email + "已存在");
            return "501";
        }
        catch (Exception e) {
            
           log.info("Error: " + e.toString() );
           return "500";
        }

        return "200";
    }

    @RequestMapping("/getCid")
    // @JsonInclude(Include.NON_NULL)
    public String GetCid(String json2){
        System.out.println(json2);
        JsonObject jsonObject = new JsonParser().parse(json2).getAsJsonObject();
        String email = jsonObject.get("email").getAsString();
        return lService.getCid(email);
    }
    
}
