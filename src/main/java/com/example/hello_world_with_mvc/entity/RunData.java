package com.example.hello_world_with_mvc.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component  //设置全局变量
public class RunData {
    public static List<String> onlinecid = new ArrayList<>();
}
