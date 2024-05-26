package com.example.hello_world_with_mvc.config;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.hello_world_with_mvc.service.UserService;
import com.example.hello_world_with_mvc.utils.TokenUtil;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author chen bo
 * @Date 2023/10
 * @Des
 */
@Slf4j
//@WebListener
@Configuration
// @EnableWebSocket
//public class WebSocketConfig extends ServerEndpointConfig.Configurator implements WebSocketConfigurer{
public class WebSocketConfig extends ServerEndpointConfig.Configurator{

    /**
     * bean注册：会自动扫描带有@ServerEndpoint注解声明的Websocket Endpoint(端点)，注册成为Websocket bean。
     * 要注意，如果项目使用外置的servlet容器，而不是直接使用springboot内置容器的话，就不要注入ServerEndpointExporter，因为它将由容器自己提供和管理。
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        
        // String cid = request.getParameterMap().get("cid").get(0).toString();
        // log.info("c"+cid);
        // log.info("c:"+request.toString());
        // log.info("c:"+request.getHeaders().toString());
        // log.info("c:"+request.getRequestURI().toString());


        // String token = request.getHeaders().get("Protocol").toString();
        // log.info("t"+token);

        // if(null != user && user.getPassword().equals(password)){
        // config.getUserProperties().put("cid", cid);
        // }else{
        //     return;
        // }
    }

//     @Override
//     public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//         registry.addHandler(new MyWebSocketHandler(), "/websocket/{cid}")//设置连接路径和处理
//                 .setAllowedOrigins("*")
//                 .addInterceptors(new MyWebSocketInterceptor());//设置拦截器
  
//     }
//     /**
//      * 自定义拦截器拦截WebSocket请求
//      */  
//     class MyWebSocketInterceptor implements HandshakeInterceptor {
//         //前置拦截一般用来注册用户信息，绑定 WebSocketSession
//         @Override
//         public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                                        WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//             //System.out.println("前置拦截~~");
//             log.info("AAAAAAAAAAAAAAAAA");
//             //if (!(request instanceof ServletServerHttpRequest)) return true;
//             HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
// //            String userName = (String) servletRequest.getSession().getAttribute("userName");

//             return true;
//         }
//         @Override
//         public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                                    WebSocketHandler wsHandler, Exception exception) {
//             System.out.println("后置拦截~~");
//         }
//     }


    
}

