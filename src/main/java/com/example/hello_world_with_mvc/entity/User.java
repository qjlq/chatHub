package com.example.hello_world_with_mvc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity //为加 h2新增的
@Table(name = "tb_user") //为加 h2新增的
public class User {
    private int userId;
    @Id  //为加 h2新增的
    public int getUserId(){
        return userId;
    }
    private String userName;
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setUserId(String userName){
        this.userName = userName;
    }

}
