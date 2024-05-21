package com.example.hello_world_with_mvc.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.hello_world_with_mvc.entity.Group;
import com.example.hello_world_with_mvc.service.UserService;
import com.example.hello_world_with_mvc.service.WebSocketServer;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


//@Controller
@Slf4j
@RestController
@RequestMapping("/chatcontroller")
public class ChatController {
        /**
     * 跳转到websocketDemo.html页面，携带自定义的cid信息。
     * http://localhost:8300/demo/toWebSocketDemo/user
     *
     * @param cid
     * @param model
     * @return
     */
    // @GetMapping("/demo/toWebSocketDemo/{cid}")
    // //@GetMapping("/demo/{cid}")
    // public String toWebSocketDemo(@PathVariable String cid, Model model) {
    //     model.addAttribute("cid", cid);
    //     // 这里return就是去index.html了
    //     return "index";
    // }

    // @GetMapping("hello")
    // @ResponseBody
    // public String hi(HttpServletResponse response) {
    //     return "Hi";
    // }

    @Autowired
    private UserService Uservice;

    @RequestMapping("/getgid")
    public String requestMethodName(String cid) {
        return new String();
    }

    @RequestMapping("/addGroup")
    public String addGroup(@RequestParam String json) {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        String cid = jsonObject.get("ccid").getAsString();
        String groupname = jsonObject.get("groupname").getAsString();
        String number = jsonObject.get("number").getAsString();
        String member = jsonObject.get("member").getAsString();
        //String gid = UUID.randomUUID().toString(); //带特殊字符 “-” 使winform的数据库报错
        String gid = UUID.randomUUID().toString().replaceAll("-", "");
        List<String> memberList = new ArrayList<String>(Arrays.asList(member.split(",")));
        log.info("newGroupAdd: ccid: " + cid + " groupname: " + groupname + " number: " + number + " member: " + member + " gid: " + gid);

        

        // try {
        //     lService.SignUp(uid, psw, username);
        // } catch(DuplicateKeyException e){
        //     log.info("Id重复: " + gid + " 已存在");
        //     return "501";
        // }
        // catch (Exception e) {
            
        //    log.info("Error: " + e.toString() );
        //    return "500";
        // }

        return Uservice.addGroup(new Group(cid,groupname,gid,number), memberList);
    }
    
    
}
