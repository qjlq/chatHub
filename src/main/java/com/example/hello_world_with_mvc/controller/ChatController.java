package com.example.hello_world_with_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@Controller
//@RestController
public class ChatController {
        /**
     * 跳转到websocketDemo.html页面，携带自定义的cid信息。
     * http://localhost:8300/demo/toWebSocketDemo/user
     *
     * @param cid
     * @param model
     * @return
     */
    @GetMapping("/demo/toWebSocketDemo/{cid}")
    //@GetMapping("/demo/{cid}")
    public String toWebSocketDemo(@PathVariable String cid, Model model) {
        model.addAttribute("cid", cid);
        // 这里return就是去index.html了
        return "index";
    }

    @GetMapping("hello")
    @ResponseBody
    public String hi(HttpServletResponse response) {
        return "Hi";
    }
}
