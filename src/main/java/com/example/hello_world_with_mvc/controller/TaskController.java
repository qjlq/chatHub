package com.example.hello_world_with_mvc.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hello_world_with_mvc.entity.Task;
import com.example.hello_world_with_mvc.entity.VideoState;
import com.example.hello_world_with_mvc.service.DatabaseService;
import com.example.hello_world_with_mvc.service.FastApiConnect;
import com.example.hello_world_with_mvc.service.TaskQueueService;
import com.example.hello_world_with_mvc.service.VedioWebsocketService;
import com.example.hello_world_with_mvc.service.WebSocketServer;
import com.example.hello_world_with_mvc.utils.TokenUtil;
import com.example.hello_world_with_mvc.utils.VideoHandler;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMethod;


@Slf4j
@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskQueueService taskQueueService;

    @Autowired
    private VedioWebsocketService client;

    @Autowired
    private FastApiConnect fastApiConnect;

    @Autowired
    private DatabaseService database;
    private static TaskController serverHandler;

    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {   //普通的autowired 引用database 会报错 null
        serverHandler = this;  
        serverHandler.database = this.database;        
        // 初使化时将已静态化的testService实例化
    }  


    // @PostMapping("/submit")
    @RequestMapping("/submit")
    public ResponseEntity<String> submitTask(@RequestParam("fileName") String name, @RequestParam("token") String token, @RequestParam("type") String type) {
        log.info("fileName:{}, token:{}, type:{}", name, token, type);
        if (TokenUtil.verify(token) != null){
            String cid = TokenUtil.verify(token).getClaim("cid").asString();
            Task task = taskQueueService.submitTask(name, cid, Task.TaskType.valueOf(type));
            serverHandler.database.addTask(task); 
            VideoState videoState = serverHandler.database.getVideoStateByFileName(name);
            videoState.setState(type, "QUEUED");
            serverHandler.database.updateVideoState(videoState); // 更新状态

            log.info("submit task success, task id:{}, fileName:{}, creator:{}, type:{}", task.getId(), task.getFileName(), task.getCreator(), task.getTaskType());

            return ResponseEntity.ok("success");
        }
        else{{
            return ResponseEntity.status(401).body("无效token");
        }}

    }

    @RequestMapping("/getProgress")
    public ResponseEntity<String> getProgress(@RequestParam("token") String token) {
        client.getProgress();
        return ResponseEntity.ok("success");
    }


    @PostMapping("/complete")
    public ResponseEntity<String> taskComplete(@RequestBody String tid, String fileName, String taskState) {
        // fastApiConnect.getResponseFuture().complete(tid);
        Task task = new Task();
        task.setFileName(fileName);
        task.setTaskType(Task.TaskType.valueOf(taskState));
        log.info(tid + " complete task");
        fastApiConnect.completeTask(tid);
        taskQueueService.completeProcessing(tid,task);
        return ResponseEntity.ok("success");
    }
    
    @PostMapping(value = "/taskList")
    public Set<Task> taskList(String token) {
        if (TokenUtil.verify(token) != null){

            String cid = TokenUtil.verify(token).getClaim("cid").asString();
            Set<Task> TaskList = new HashSet<Task>(serverHandler.database.getTaskList());
            log.info("TaskList request frome [cid]: " + cid );

            /*  Controller 返回 Set<TaskState> 时，Spring Boot 会自动调用 Jackson 库将对象序列化为 JSON*/
            return TaskList;
        }
        else{
            log.info("TaskList request frome with fail token ");
            // return false;
            return null;
        }
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<Task> getTaskStatus(@PathVariable Long id) {
    //     return taskRepository.findById(id)
    //             .map(ResponseEntity::ok)
    //             .orElse(ResponseEntity.notFound().build());
    // }

    // public record TaskRequest(String fileName, String creator, Task.TaskType type) {}
}

