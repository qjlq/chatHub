package com.example.hello_world_with_mvc.controller;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.hello_world_with_mvc.entity.Task;
import com.example.hello_world_with_mvc.utils.TokenUtil;
import com.google.gson.JsonObject;

@RestController
@RequestMapping("/SSEs")
public class SseController {
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    // 客户端连接此端点
    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter stream() {
        SseEmitter emitter = new SseEmitter(60_000L); // 超时时间60秒
        emitters.add(emitter);

        // 移除已断开连接的Emitter
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    // 模拟发送事件（实际由业务逻辑触发）
    public void sendEvent(String data) {
        emitters.forEach(emitter -> {
            try {
                JsonObject message = new JsonObject();
                message.addProperty("msg",data);
                emitter.send(SseEmitter.event()
                        .data(message.toString()) // 事件数据
                        // .id("123")  // 可选ID
                        // .name("message") // 事件名称
                );
            } catch (Exception e) {
                emitter.completeWithError(e);
                emitters.remove(emitter);
            }
        });

        // try {
        //     // 使用Fastjson序列化
        //     jsonData = JSON.toJSONString(data);
        // } catch (Exception e) {
        //     jsonData = JSON.toJSONString(Collections.singletonMap("error", "数据序列化失败"));
        // }

        // final String finalData = jsonData;
        // emitters.forEach(emitter -> {
        //     try {
        //         emitter.send(SseEmitter.event()
        //             .data(finalData, MediaType.APPLICATION_JSON)
        //             .name("message"));
        //     } catch (IOException ex) {
        //         emitter.completeWithError(ex);
        //     }
        // });
    }

    @PostMapping(value = "/SSEsend")
    public void sseSend() {
        this.sendEvent("Hello:SSE!");
    }
}
