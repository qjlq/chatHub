package com.example.hello_world_with_mvc.entity;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Interval {
    @JsonProperty("label")
    private String label;
    
    @JsonProperty("start")
    private Double startTime; // 使用Double类型接收时间戳
    
    @JsonProperty("end")
    private Double endTime;
    
    @JsonProperty("sum_scores")
    private Double sumScores;
    
    @JsonProperty("count")
    private Integer count;

    // 如果需要时间计算可添加转换方法
    // public Instant getStartAsInstant() {
    //     return Instant.ofEpochSecond(startTime.longValue());
    // }
}
