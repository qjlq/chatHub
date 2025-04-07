package com.example.hello_world_with_mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hello_world_with_mvc.entity.Task;
import com.example.hello_world_with_mvc.service.TaskQueueService;
import com.example.hello_world_with_mvc.utils.TokenUtil;

@RequestMapping("/tasks")
public class TaskController {
        @Autowired
    private TaskQueueService taskQueueService;

    @PostMapping("/submit")
    public ResponseEntity<String> submitTask(@RequestParam("fileName") String name, @RequestParam("token") String token, @RequestParam("type") String type) {
        if (TokenUtil.verify(token) != null){
            String cid = TokenUtil.verify(token).getClaim("cid").asString();
            Task task = taskQueueService.submitTask(name, cid, Task.TaskType.valueOf(type));
            return ResponseEntity.ok("success");
        }
        else{{
            return ResponseEntity.status(401).body("无效token");
        }}

    }

    // @GetMapping("/{id}")
    // public ResponseEntity<Task> getTaskStatus(@PathVariable Long id) {
    //     return taskRepository.findById(id)
    //             .map(ResponseEntity::ok)
    //             .orElse(ResponseEntity.notFound().build());
    // }

    // public record TaskRequest(String fileName, String creator, Task.TaskType type) {}
}

