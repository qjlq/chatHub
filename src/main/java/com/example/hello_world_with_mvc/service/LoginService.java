package com.example.hello_world_with_mvc.service;

public interface LoginService {
    public String Login(String uid,String password);
    public String SignUp(String uid,String password,String username);
}
