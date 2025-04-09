package com.example.hello_world_with_mvc.entity;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class VideoState {
    /*Jackson 依赖 Getter 方法序列化字段。例如：
    字段 fileName 需要对应的 getFileName() 方法。
    字段 keypoint_task 需要 getKeypoint_task() 方法（不符合 Java 命名规范，需特殊处理）。 */
    
    @Id //设fileName为主键
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String fileName;

    /*如果后端字段名是驼峰形式（如 keypointTask），但前端期望下划线形式（如 keypoint_task），可以用@JsonProperty("keypoint_task")注解来统一命名风格： */
    @Column(name = "keypoint_task")
    @JsonProperty("keypoint_task")
    private Boolean keypointTask;
    @Column(name = "recognize_task")
    @JsonProperty("recognize_task")
    private Boolean recognizeTask;
    @Column(name = "separate_task")
    @JsonProperty("separate_task")
    private Boolean separateTask;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getKeypointTask() {
        return keypointTask;
    }
    public void setKeypointTask(Boolean keypointTask) {
        this.keypointTask = keypointTask;
    }

    public Boolean getRecognizeTask() {
        return recognizeTask;
    }

    public void setRecognizeTask(Boolean recognizeTask) {
        this.recognizeTask = recognizeTask;
    }

    public Boolean getSeparateTask() {  
        return separateTask;    
    }    

    public void setSeparateTask(Boolean separateTask) {         
        this.separateTask = separateTask;       
    }    





    
    
}

