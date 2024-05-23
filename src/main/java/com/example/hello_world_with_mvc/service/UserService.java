package com.example.hello_world_with_mvc.service;

import java.util.List;

import com.example.hello_world_with_mvc.entity.Group;
import com.example.hello_world_with_mvc.entity.User;

public interface UserService {

    public List<User> list();
    // public void addUser(User user);
    public String addGroup(Group group,List<String> memList);
    public String leaveGroup(String cid,String gid);
}
