package com.example.hello_world_with_mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.hello_world_with_mvc.entity.User;
import com.example.hello_world_with_mvc.service.DatabaseService;

@Service
public class DatabaseServiceImpl implements DatabaseService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    // 总之，在执行效率方面，JdbcTemplate通常会有更好的性能，因为它允许直接编写SQL语句并有更多的控制权。MyBatis在性能和便捷性之间取得了平衡，而JPA则以自动化和简化为主要优势，但可能在某些情况下性能较差。具体选择哪种方法取决于项目需求、开发团队的经验和技能以及应用场景。
    @Override
    public User getVaiUserID(String cid){
        try {
            String sql = "select uid,username,password from chat.user where uid = ?";
            List<User> result = jdbcTemplate.query(sql,(resultset,i)->{
                User user = new User();
                user.setUserId(resultset.getString("uid"));
                user.setUserName(resultset.getString("username"));
                user.setPassWord(resultset.getString("password"));
                return user;
            },cid);
            return result.get(0);
        } catch (Exception e) {
            User user = new User();
            user.setUserId(null);
            return user;
        }

    }
    @Override
    public int addUser(String uid,String password,String username){

        return jdbcTemplate.update("insert into chat.user(uid, username, password) values(?, ?,?)",uid, username, password);
    }
}
