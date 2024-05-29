package com.example.hello_world_with_mvc.service;

public interface LoginService {
    public String Login(String email,String password);
    public String SignUp(String uid,String email,String password,String username);
    public String getCid(String email);
}
