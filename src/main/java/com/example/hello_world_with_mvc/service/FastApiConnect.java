package com.example.hello_world_with_mvc.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.hello_world_with_mvc.entity.Task;
import com.example.hello_world_with_mvc.entity.Task.TaskType;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FastApiConnect {
    private final String FAST_API_URL = "http://localhost:8000/start-task"; // 替换为实际的FastAPI服务地址
    private CompletableFuture<String> responseFuture;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ConcurrentHashMap<String, CompletableFuture<String>> futureMap = new ConcurrentHashMap<>();
    // 定义请求体结构
    private static class TaskRequest {
        public int k;
        public String gid;
        public TaskType typed;
        public String tid;

        public TaskRequest(int k, String gid, TaskType typed, String tid) {
            this.k = k;
            this.gid = gid;
            this.typed = typed;
            this.tid = tid;
        }
    }

    public CompletableFuture<String> startTask(Task task) {
        CompletableFuture<String> future = new CompletableFuture<>();
        String taskId = UUID.randomUUID().toString();
        // String taskId = task.getId().toString();
        futureMap.put(taskId, future);
        try {
            // 1. 构建规范的 JSON 请求体
            TaskRequest requestBody = new TaskRequest(
                100,  // 根据需求调整 k 的值
                task.getFileName(),
                task.getTaskType(),
                taskId
            );

            String jsonBody = objectMapper.writeValueAsString(requestBody);

            // 2. 设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

            // 3. 发送异步请求
            ResponseEntity<String> response = restTemplate.exchange(
                FAST_API_URL,
                HttpMethod.POST,
                requestEntity,
                String.class
            );

            // 4. 处理响应
            if (response.getStatusCode() == HttpStatus.OK) {
                future.complete(response.getBody());
            } else {
                future.completeExceptionally(new RuntimeException("API Error: " + response.getStatusCode()));
            }

        } catch (Exception e) {
            // 5. 异常处理
            future.completeExceptionally(e);
        }

        return future;
    }

    public void completeTask(String taskId) {
        CompletableFuture<String> future = futureMap.remove(taskId);
        // log.info(taskId+ " completed1");
        if (future != null) {
            // log.info(taskId+ " completed2");
            future.complete("success");
        }
    }
    
    private ApiResponse parseResponse(ResponseEntity<String> response) {
        if (response.getStatusCode() == HttpStatus.OK) {
            // 任务成功启动
            return new ApiResponse(true, "Task started");
        } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            // 任务已经在进行中
            return new ApiResponse(false, "Task already in progress");
        } else {
            // 其他错误
            return new ApiResponse(false, "Unexpected error: " + response.getStatusCode());
        }
    }

    public class ApiResponse {
        private boolean success;
        private String message;
    
        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    
        // Getters and Setters
        public boolean isSuccess() {
            return success;
        }
    
        public void setSuccess(boolean success) {
            this.success = success;
        }
    
        public String getMessage() {
            return message;
        }
    
        public void setMessage(String message) {
            this.message = message;
        }
    }

    public CompletableFuture<String> getResponseFuture() {
        return responseFuture;
    }
}
