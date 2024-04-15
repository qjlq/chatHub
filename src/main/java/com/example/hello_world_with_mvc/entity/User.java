package com.example.hello_world_with_mvc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity //为加 h2新增的
@Table(name = "tb_user") //为加 h2新增的
public class User {
    private String userId;
    @Id  //为加 h2新增的
    private String userName;
    private String passWord;

    public User(String userId,String userName,String passwWord){
        this.userId = userId;
        this.userName = userName;
        this.passWord = passwWord;
    }

    public User(){
        this.userId = null;
        this.userName = null;
        this.passWord = null;
    }

    public String getUserId(){
        return this.userId;
    }
    public String getUserName(){
        return this.userName;
    }
    public String getPassword(){
        return this.passWord;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setUserId(String userName){
        this.userName = userName;
    }
    public void setPassWord(String passWord){
        this.passWord = passWord;
    }

}
