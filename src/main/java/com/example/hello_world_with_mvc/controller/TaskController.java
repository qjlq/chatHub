package com.example.hello_world_with_mvc.controller;

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
import com.example.hello_world_with_mvc.service.FastApiConnect;
import com.example.hello_world_with_mvc.service.TaskQueueService;
import com.example.hello_world_with_mvc.service.VedioWebsocketService;
import com.example.hello_world_with_mvc.service.WebSocketServer;
import com.example.hello_world_with_mvc.utils.TokenUtil;

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


    // @PostMapping("/submit")
    @RequestMapping("/submit")
    public ResponseEntity<String> submitTask(@RequestParam("fileName") String name, @RequestParam("token") String token, @RequestParam("type") String type) {
        log.info("fileName:{}, token:{}, type:{}", name, token, type);
        if (TokenUtil.verify(token) != null){
            String cid = TokenUtil.verify(token).getClaim("cid").asString();
            Task task = taskQueueService.submitTask(name, cid, Task.TaskType.valueOf(type));
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


    @RequestMapping("/complete")
    public ResponseEntity<String> taskComplete(@RequestParam("tid") String tid) {
        fastApiConnect.getResponseFuture().complete(tid);
        return ResponseEntity.ok("success");
    }
    

    // @GetMapping("/{id}")
    // public ResponseEntity<Task> getTaskStatus(@PathVariable Long id) {
    //     return taskRepository.findById(id)
    //             .map(ResponseEntity::ok)
    //             .orElse(ResponseEntity.notFound().build());
    // }

    // public record TaskRequest(String fileName, String creator, Task.TaskType type) {}
}

