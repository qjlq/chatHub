package com.example.hello_world_with_mvc.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //表示主键的值由数据库自动生成（例如自增字段），而非由应用程序手动设置。
    private Long id;
    @Column(name = "filename")
    @JsonProperty("fileName")
    private String fileName;
    @Column(name = "task_identifier")
    @JsonProperty("taskIdentifier")
    private String taskIdentifier; // task_identifier = fileName + task_type
    @Column(name = "creator")
    @JsonProperty("creator")
    private String creator;
    @Column(name = "task_type")
    @JsonProperty("taskType")
    private TaskType taskType;
    @Column(name = "task_status")
    @JsonProperty("taskStatus")
    private TaskStatus taskStatus;
    @Column(name = "create_time")
    @JsonProperty("createTime")
    private LocalDateTime createTime;
    @Column(name = "start_time")
    @JsonProperty("startTime")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    @JsonProperty("endTime")
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

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }


    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
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
    
    public String getTaskIdentifier() {
        return taskIdentifier;
    }

    public void setTaskIdentifier(String taskIdentifier) {
        this.taskIdentifier = taskIdentifier;
    }



    public enum TaskType {
        recognize_task, keypoint_task, separate_task
    }
    
    public enum TaskStatus {
        QUEUED, PROCESSING, COMPLETED, FAILED
    }
    
}

