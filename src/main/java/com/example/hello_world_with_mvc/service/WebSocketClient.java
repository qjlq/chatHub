// package com.example.hello_world_with_mvc.service;

// import java.io.IOException;
// import java.net.URI;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// import jakarta.annotation.PostConstruct;
// import jakarta.websocket.ClientEndpoint;
// import jakarta.websocket.CloseReason;
// import jakarta.websocket.ContainerProvider;
// import jakarta.websocket.OnClose;
// import jakarta.websocket.OnError;
// import jakarta.websocket.OnMessage;
// import jakarta.websocket.OnOpen;
// import jakarta.websocket.WebSocketContainer;
// import jakarta.websocket.Session;

// @Component
// @ClientEndpoint
// public class WebSocketClient {
//     private Session session;

//     @Autowired
//     private WebSocketServer server;
//     private static WebSocketClient serverHandler;
    
//     @PostConstruct
//     public void init() { 
//         serverHandler.server = this.server;
//     }


//     @OnOpen
//     public void onOpen(Session session) {
//         this.session = session;
//         System.out.println("Connected to FastAPI WebSocket");
//         sendMessage("来自Spring Boot的初始消息");
//     }

//     @OnMessage
//     public void onMessage(String message) {
//         System.out.println("收到FastAPI响应: " + message);
//         serverHandler.server.sendMissonData(message);
//     }

//     @OnClose
//     public void onClose(CloseReason reason) {
//         System.out.println("连接关闭: " + reason.getReasonPhrase());
//     }

//     @OnError
//     public void onError(Throwable throwable) {
//         System.err.println("WebSocket错误: ");
//         throwable.printStackTrace();
//     }

//     public void sendMessage(String message) {
//         try {
//             session.getBasicRemote().sendText(message);
//         } catch (IOException e) {
//             throw new RuntimeException("消息发送失败", e);
//         }
//     }

//     public void connect(String endpoint) {
//         try {
//             WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//             container.connectToServer(this, URI.create(endpoint));
//         } catch (Exception e) {
//             throw new RuntimeException("连接失败", e);
//         }
//     }

//     public void close() {
//         try {
//             if (session != null && session.isOpen()) {
//                 session.close();
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }
