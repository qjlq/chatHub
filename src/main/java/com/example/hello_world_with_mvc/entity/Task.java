package com.example.hello_world_with_mvc.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String creator;
    private TaskType type;
    private TaskStatus status;
    private LocalDateTime createTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }



    public enum TaskType {
        PROCESS_DOCUMENT, GENERATE_REPORT, CONVERT_FORMAT
    }
    
    public enum TaskStatus {
        QUEUED, PROCESSING, COMPLETED, FAILED
    }
    
}

