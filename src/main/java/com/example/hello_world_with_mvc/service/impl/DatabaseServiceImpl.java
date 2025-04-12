package com.example.hello_world_with_mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.hello_world_with_mvc.entity.Group;
import com.example.hello_world_with_mvc.entity.Task;
import com.example.hello_world_with_mvc.entity.User;
import com.example.hello_world_with_mvc.entity.VideoState;
import com.example.hello_world_with_mvc.service.DatabaseService;
import com.example.hello_world_with_mvc.service.WebSocketServer;

import lombok.extern.slf4j.Slf4j; //log
@Slf4j
@Service
public class DatabaseServiceImpl implements DatabaseService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
// 总之，在执行效率方面，JdbcTemplate通常会有更好的性能，因为它允许直接编写SQL语句并有更多的控制权。MyBatis在性能和便捷性之间取得了平衡，而JPA则以自动化和简化为主要优势，但可能在某些情况下性能较差。具体选择哪种方法取决于项目需求、开发团队的经验和技能以及应用场景。


    @Override
    public User getVaiEmail(String email){
        try {
            String sql = "select uid,username,password from chat.user where email = ?";
            List<User> result = jdbcTemplate.query(sql,(resultset,i)->{
                User user = new User();
                user.setUserId(resultset.getString("uid"));
                user.setUserName(resultset.getString("username"));
                user.setPassWord(resultset.getString("password"));
                log.info("DBid: " + user.getUserId());
                return user;
            },email);
            return result.get(0);
        } catch (Exception e) {
            User user = new User();
            user.setUserId(null);
            log.info("{} user does ont exit error:{}",email,e.toString());
            return user = null;
        }

    }
    @Override
    public int addUser(String uid,String email,String password,String username){

        return jdbcTemplate.update("insert into chat.user(uid,email,username, password) values(?, ?,?,?)",uid, email,username, password);
    }

    @Override
    public String getNameByCid(String cid){
        try {
            String sql = "select username from chat.user where uid = ?";
            List<String> result = jdbcTemplate.query(sql,(resultset,i)->{
                return resultset.getString("username");
            },cid);
            return result.get(0);
        } catch (Exception e) {
            log.info("{} cid does not exit error:{}",cid,e.toString());
            return null;
        }
    }

    @Override
    public List<String> getCidByGid(String gid){
        try {
            String sql = "select cid from chat.groupMember where gid = ?";
            List<String> result = jdbcTemplate.query(sql,(resultset,i)->{
                return resultset.getString("cid");
            },gid);
            return result;
        } catch (Exception e) {
            log.info("{} gid does not exit error:{}",gid,e.toString());
            return null;
        }
    }
    @Override
    public List<String> getGidByCid(String cid){
        try {
            String sql = "select gid from chat.groupMember where cid = ?";
            List<String> result = jdbcTemplate.query(sql,(resultset,i)->{
                return resultset.getString("gid");
            },cid);
            return result;
        } catch (Exception e) {
            log.info("{} cid does not exit error:{}",cid,e.toString());
            return null;
        }
    }

    @Override
    public List<String> getVideoByCid(String cid){
        try {
            String sql = "select video from chat.VideoList where cid = ?";
            List<String> result = jdbcTemplate.query(sql,(resultset,i)->{
                return resultset.getString("video");
            },cid);
            return result;
        } catch (Exception e) {
            log.info("{} cid does not exit error:{}",cid,e.toString());
            return null;
        }
    }

    @Override
    public String getGnamebyGid(String gid){
        try {
            String sql = "select groupname from chat.groupinfo where gid = ?";
            List<String> result = jdbcTemplate.query(sql,(resultset,i)->{
                return resultset.getString("username");
            },gid);
            return result.get(0);
        } catch (Exception e) {
            log.info("{} gid does not exit error:{}",gid,e.toString());
            return null;
        }
    }

    @Override
    public Group getGroupGid(String gid){
        try {
            String sql = "select groupname,ccid,number from chat.groupinfo where gid = ?";
            List<Group> result = jdbcTemplate.query(sql,(resultset,i)->{
                Group group = new Group();
                //user.setUserId(resultset.getString("gid"));
                group.setgroupname(resultset.getString("groupname"));
                group.setcid(resultset.getString("ccid"));
                group.setnumber(resultset.getString("number"));

                return group;
            },gid);
            return result.get(0);
        } catch (Exception e) {
            Group group = new Group();
            group.setgid(null);
            log.info("{} user does ont exit error:{}",gid,e.toString());
            return group;
        }
    }

    @Override
    public int AddtoGroupInfo(Group group){
        return jdbcTemplate.update("insert into chat.groupinfo(gid, ccid, groupname, number) values(?,?,?,?)",group.getgid(), group.getcid(), group.getgroupname(),group.getnumber());
    }

    @Override
    public int AddtoGroupMember(String gid,String cid){
        return jdbcTemplate.update("insert into chat.groupMember(gid, cid ) values(?, ?)",gid,cid);
    }

    @Override
    public int AddVideo(String video,String cid){
        return jdbcTemplate.update("insert into chat.VideoList(video, cid ) values(?, ?)",video,cid);
    }

    @Override
    public int UpdateGroupNumber(String gid, String number){
        return jdbcTemplate.update("update chat.groupinfo set number = ? where gid = ?",number,gid);
    }

    @Override
    public int DeleteCidViaGroupMember(String gid, String cid){
        jdbcTemplate.update("delete from chat.groupMember where gid = ? and cid = ?",gid,cid);
        String sql = "select number from chat.groupinfo where gid = ?";
        //修改群人数
        List<Group> result = jdbcTemplate.query(sql,(resultset,i)->{
            Group group = new Group();
            group.setnumber(resultset.getString("number"));
            return group;
        },gid);
        int number = Integer.parseInt(result.get(0).getnumber());
        number --;
        UpdateGroupNumber(gid,String.valueOf(number));
        if (number <= 1){ 
            jdbcTemplate.update("delete from chat.groupMember where gid = ?",gid); //删除剩下的一人
            jdbcTemplate.update("delete from chat.groupinfo where gid = ? ",gid); //删除群信息
            log.info("delete group: " + gid + " name: " + result.get(0).getgroupname());
            return 201; //群人数为1 时 删除群
        }
        return 200;
    }

    @SuppressWarnings("deprecation")
    @Override
    public List<VideoState> getVideoStateListByCid(String cid){
        try {
            /*方法1 */
            // String sql = "SELECT vl.video,vs.filename FROM VideoList vl INNER JOIN VideoState vs ON vl.video = vs.filename WHERE vl.cid = ?";
            // List<VideoState> result = jdbcTemplate.query(sql,(resultset,i)->{
            //     VideoState videoState = new VideoState();
            //     videoState.setFileName(resultset.getString("fileName"));
            //     videoState.setKeypoint_task(resultset.getBoolean("keypoint_task"));
            //     videoState.setRecognize_task(resultset.getBoolean("recognize_task"));
            //     videoState.setSeparate_task(resultset.getBoolean("separate_task"));
            //     return videoState;
            // },cid);
            // return result;

            /*方法2*/
            String sql = "SELECT vs.filename , vs.keypoint_task ,vs.recognize_task, vs.separate_task " +
                "FROM VideoList vl " +
                "INNER JOIN VideoState vs ON vl.video = vs.filename " +
                "WHERE vl.cid = ?";
        
            return jdbcTemplate.query(
                sql,
                new Object[]{cid}, // 参数绑定
                new BeanPropertyRowMapper<>(VideoState.class) // 自动映射结果集到对象
            );
        }
        catch (Exception e) {
            log.info("{} cid get video state error:{}",cid,e.toString());
            // VideoState videoState = new VideoState();
            // return videoState;
            return null;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public VideoState getVideoStateByFileName(String fileName){
        try {
            // String sql = "SELECT id, task_identifier,fileName, creator,task_type, task_status, create_time, start_time, end_time FROM TaskList";
            String sql = "SELECT * FROM VideoState WHERE filename = ?";
            return jdbcTemplate.query(
                sql,
                new Object[]{fileName},
                new BeanPropertyRowMapper<>(VideoState.class) // 自动映射结果集到对象
            ).get(0);
        }
        catch (Exception e) {
            log.info("get videoState error:{}",e.toString());
            return null;
        }
    }

    @Override
    public int updateVideoState(VideoState videoState){
        return jdbcTemplate.update("update chat.VideoState set keypoint_task = ?, recognize_task = ?, separate_task = ? where filename = ?",videoState.getKeypointTask().toString(), videoState.getRecognizeTask().toString(), videoState.getSeparateTask().toString(), videoState.getFileName());
    }



    @Override
    public List<Task> getTaskList(){
        try {
            // String sql = "SELECT id, task_identifier,fileName, creator,task_type, task_status, create_time, start_time, end_time FROM TaskList";
            String sql = "SELECT * FROM TaskList";

            return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Task.class) // 自动映射结果集到对象
            );
        }
        catch (Exception e) {
            log.info("get task list error:{}",e.toString());
            return null;
        }
    }

    @Override
    public int addTask(Task task){
        return jdbcTemplate.update("insert into chat.TaskList(filename, creator,task_type, task_status) values(?,?,?,?)", task.getFileName(), task.getCreator(),task.getTaskType().toString() , task.getTaskStatus().toString());
    }

    @SuppressWarnings("deprecation")
    @Override
    public Task getTaskByIdentifier(Task task){
        try {
            String sql = "SELECT * FROM TaskList WHERE task_identifier = ?";
            return jdbcTemplate.query(
                sql,
                new Object[]{task.getTaskIdentifier()},
                new BeanPropertyRowMapper<>(Task.class) // 自动映射结果集到对象
            ).get(0);
        }
        catch (Exception e) {
            log.info("get task by identifier error:{}",e.toString());
            return null;
        }
    }

    @Override
    public int updateTaskStateByIdentifier(Task task){
        return jdbcTemplate.update("update chat.TaskList set task_status = ? where task_identifier = ?",task.getTaskStatus().toString(), task.getTaskIdentifier());
    }


    

}
