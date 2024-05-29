package com.example.hello_world_with_mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hello_world_with_mvc.dao.UserRepository;
import com.example.hello_world_with_mvc.entity.Group;
import com.example.hello_world_with_mvc.entity.User;
import com.example.hello_world_with_mvc.service.DatabaseService;
import com.example.hello_world_with_mvc.service.UserService;
import com.example.hello_world_with_mvc.service.WebSocketServer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service //@Service注解用于类上，标记当前类是一个service类，加上该注解会将当前类自动注入到spring容中。
public class UserServicelmpl implements UserService {
    
    @Autowired
    private DatabaseService database;

    @Autowired
    private WebSocketServer socketServer;

    @Autowired
    private UserRepository userDao;

    // @Override
    // public void addUser(User user){
    //     userDao.save(user);
    // }

    @Override
    public List<User> list(){
        return userDao.findAll();
    }

    @Override
    public String addGroup(Group group,List<String> memList){
        try {
            database.AddtoGroupInfo(group);
            database.AddtoGroupMember(group.getgid(),group.getcid());
            for (String string : memList) {
                database.AddtoGroupMember(group.getgid(), string);
            }
            socketServer.addGroup(group.getgid());
            return "200";
        } catch (Exception e) {
            // TODO: handle exception
            log.info(e.toString());
            return "500";
        }
 //tmp
    }

    @Override
    public String leaveGroup(String cid,String gid){
        try {
            int state = database.DeleteCidViaGroupMember(gid, cid);
            if (state == 201){
                socketServer.deleteGroupMsg(gid, cid);
                return "201";
            }else if (state == 200) {
                return "200";
            }
            return "200";
        } catch (Exception e) {
            // TODO: handle exception
            log.info(e.toString());
            return "500";
        }
    }
}
