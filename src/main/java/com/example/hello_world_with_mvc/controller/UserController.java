package com.example.hello_world_with_mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hello_world_with_mvc.entity.User;
import com.example.hello_world_with_mvc.service.UserService;

import lombok.extern.slf4j.Slf4j;

//@Controller ：表示是控制器对象

@RestController  //是@Controller和@ResponseBody的组合注解
@RequestMapping("/user")  //@RequestMapping：标记该类/方法对应接口的配置信息
public class UserController {
    @Autowired //动装配（自动注入）Spring Bean到其他Bean中。它的主要作用是帮助我们消除手动配置Bean依赖关系的繁琐工作
    //在服务层（Service）中，通常需要依赖于数据访问层（Repository）的Bean，可以使用 @Autowired 注解来注入数据访问层的实例。
    private UserService userService;

    // @RequestMapping("add")
    // public User add(User user) {
    //     userService.addUser(user);
    //     return user;
    // }

    @GetMapping("list") //@GetMapping 注解：对应 @GET 请求方法的 @RequestMapping 注解。
    public List<User> list() {
        return userService.list();
    }
    // 运行在浏览器栏输入 /user/add?userId=2&userName=test2
    //或 /user/list
    //运行时要先去http://localhost:8080/h2

}
