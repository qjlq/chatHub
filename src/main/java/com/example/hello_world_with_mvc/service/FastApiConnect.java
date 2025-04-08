package com.example.hello_world_with_mvc.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FastApiConnect {
    private final String FAST_API_URL = "http://localhost:8000/start-task"; // 替换为实际的FastAPI服务地址
    private CompletableFuture<String> responseFuture;

    public CompletableFuture<String> startTask(int taskId) {
        RestTemplate restTemplate = new RestTemplate();
        responseFuture = new CompletableFuture<>(); // 异步响应结果,用來阻塞
        // 创建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"k\": " + taskId + "}";

        // 创建请求体
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody,headers);

        try {
            // 发送POST请求
            ResponseEntity<String> response = restTemplate.exchange(
                    FAST_API_URL,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            // 解析响应
            // return parseResponse(response);
            if (parseResponse(response).isSuccess()) {
                return responseFuture;
            }
            else {
                responseFuture.complete(parseResponse(response).getMessage());
                return responseFuture;
            }
        } catch (Exception e) {
            // 处理异常
            log.info(e.getMessage());
            responseFuture.complete("Error: " + e.getMessage());
            return responseFuture;
            // return new ApiResponse(false, "Error: " + e.getMessage());
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
