package com.example.hello_world_with_mvc.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.hello_world_with_mvc.entity.User;


@Repository //@Reponsitory的作用与@Controller，@Service的作用都是把对象交给Spring管理。@Reponsitory是标注在Dao层接口上，作用是将接口的一个实现类交给Spring管理。
//使用这个注解的前提是必须在启动类上添加 @MapperScan("Mapper接口层路径") 注解。
//@Mapper : 这个注解一般使用在Dao层接口上，相当于一个mapper.xml文件，它的作用就是将接口生成一个动态代理类。加入了@Mapper注解，目的就是为了不再写mapper映射文件。这个注解就是用来映射mapper.xml文件的。
public class UserRepository {
    
    private List<User> userDemolist = new ArrayList<>();
    public void save(User user){
        userDemolist.add(user);
    }
    public List<User> findAll(){
        return userDemolist;
    }
}
