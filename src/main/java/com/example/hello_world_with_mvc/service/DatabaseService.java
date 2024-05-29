package com.example.hello_world_with_mvc.service;



import java.util.List;

import com.example.hello_world_with_mvc.entity.Group;
import com.example.hello_world_with_mvc.entity.User;

public interface DatabaseService {
    // 总之，在执行效率方面，JdbcTemplate通常会有更好的性能，因为它允许直接编写SQL语句并有更多的控制权。MyBatis在性能和便捷性之间取得了平衡，而JPA则以自动化和简化为主要优势，但可能在某些情况下性能较差。具体选择哪种方法取决于项目需求、开发团队的经验和技能以及应用场景。
    public User getVaiEmail(String email);
    public int addUser(String uid,String email,String password,String username);
    public String getNameByCid(String cid);
    public List<String> getCidByGid(String gid);
    public List<String> getGidByCid(String gid);
    public String getGnamebyGid(String gid);
    public Group getGroupGid(String gid);
    public int AddtoGroupInfo(Group group);
    public int AddtoGroupMember(String gid,String cid);
    public int DeleteCidViaGroupMember(String gid, String cid);
    public int UpdateGroupNumber(String gid, String number);
}