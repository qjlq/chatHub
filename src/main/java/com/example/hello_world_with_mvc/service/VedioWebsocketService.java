package com.example.hello_world_with_mvc.service;

import java.io.EOFException;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.CloseReason;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import com.example.hello_world_with_mvc.config.WebSocketConfig;
import com.example.hello_world_with_mvc.entity.Group;
import com.example.hello_world_with_mvc.entity.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@Slf4j
@Service
@ServerEndpoint(value = "/VedioWebsocket/",configurator = WebSocketConfig.class)
public class VedioWebsocketService {
     // 静态变量，记录当前在线人数。
    private static AtomicInteger onlineSessionClientCount = new AtomicInteger(0);
    // 存放所有在线的客户端
    private static Map<String, Session> onlineSessionClientMap = new ConcurrentHashMap<>();
    //连接cid和连接会话
    public String cid;
    //存储用户名
    public String UserName = null;
    //json 格式容器
    private Gson gson = new Gson();
    private Session session;

    @Autowired //连接databaseservice
    private DatabaseService database;
    private static VedioWebsocketService serverHandler ; //service to service 要加

    private ScheduledExecutorService pingScheduler;
    private volatile long lastPongTime = System.currentTimeMillis();

    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {   //普通的autowired 引用database 会报错 null
        serverHandler = this;  
        serverHandler.database = this.database;  
        // 初使化时将已静态化的testService实例化
    } 

    
    
    @OnOpen
    public void onOpen(Session session,EndpointConfig config) {
        try {
            this.cid = config.getUserProperties().get("cid").toString();
        } catch (Exception e) {
            log.info("{} token过期({}) / 非法访问",getTimeString(),UserName);
        }
        log.info("cid f: " + cid);
        log.info("{} connecting: ==> session_id = {}， cid = {}",getTimeString(), session.getId(), cid);
        if (!onlineSessionClientMap.containsKey(cid)){
            onlineSessionClientMap.put(cid, session);
            onlineSessionClientCount.incrementAndGet();
        }
        // this.cid = cid;
        this.session = session;
        this.UserName = serverHandler.database.getNameByCid(cid);
        log.info("{} video在线数：{} ==> username = {}",getTimeString(), onlineSessionClientCount, UserName);
        // startPingScheduler(); //心跳检测
    }

    @OnClose
    public void onClose(Session session) {
        // String message = UserName+ " 下线了";
        onlineSessionClientMap.remove(this.cid);
        //在线数减1
        onlineSessionClientCount.decrementAndGet();
        log.info("{} video close: 在线数：{} ==> {} 关闭video WS连接",getTimeString(), onlineSessionClientCount, UserName);
        // sendToAll(message,2);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("VideoSocke get" + message);
    }


    @OnError
    public void onError(Session session, Throwable error) {
        log.error("{}:WebSocket error：{}" ,getTimeString(),error);
        // error.printStackTrace();
    }

    public void sendProgress(String fileName, String taskName,String progress) {
        onlineSessionClientMap.forEach((onlinecid, toSession) -> {
            sendWithform(toSession,fileName,taskName,progress,201);
            // sendWithform(toSession,progress,201);
        });
    }

    public void sendTask(String fileName, String taskName,int code) {
        // 遍历在线map集合
        onlineSessionClientMap.forEach((onlinecid, toSession) -> {
                sendWithform(toSession,fileName,taskName,code);
        });
    }

    //发送的数据格式
    //{"cid":"user","gid":"gid","name":"name","message":"hello websocket","code":"code"}
    private void sendWithform(Session session,String fileName, String taskName,int code) {
        JsonObject message = new JsonObject();
        message.addProperty("fileName",fileName);
        message.addProperty("taskType",taskName);
        message.addProperty("code",code);
        synchronized(session){ //防止冲突
            try {
                session.getBasicRemote().sendText(message.toString());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.info(e.toString());
                e.printStackTrace();
            }
        }
    }

    // private void sendProgressForm(Session session,String taskId, String progress,int code) {
    //     JsonObject message = new JsonObject();
    //     message.addProperty("taskId",taskId);
    //     message.addProperty("progress",progress);
    //     message.addProperty("code",code);
    //     synchronized(session){ //防止冲突
    //         try {
    //             session.getBasicRemote().sendText(message.toString());
    //         } catch (IOException e) {
    //             // TODO Auto-generated catch block
    //             log.info(e.toString());
    //             e.printStackTrace();
    //         }
    //     }
    // }

    private void sendWithform(Session session,String fileName, String taskName,String progress,int code) {
        JsonObject message = new JsonObject();
        // message.addProperty("fileName",fileName);
        // message.addProperty("taskType",taskName);
        message.addProperty("taskIdentifier",fileName+"_"+taskName);
        message.addProperty("progress",progress);
        message.addProperty("code",code);
        synchronized(session){ //防止冲突
            try {
                session.getBasicRemote().sendText(message.toString());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.info(e.toString());
                e.printStackTrace();
            }
        }
    }

    
    // private void sendWithform(Session session,String progress,int code) {
    //     synchronized(session){ //防止冲突
    //         try {
    //             session.getBasicRemote().sendText(progress);
    //         } catch (IOException e) {
    //             // TODO Auto-generated catch block
    //             log.info(e.toString());
    //             e.printStackTrace();
    //         }
    //     }
    // }

    private String getTimeString(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    //以下为连接fastapi websocket客户端代码
    private WebSocketClient client = new WebSocketClient();
    private Session clientSession = null;

    // public CompletableFuture<String> getProgress(){
    public void getProgress(){
    

        if (clientSession==null || !clientSession.isOpen()){
            client.connect("ws://localhost:8000/ws");
            // client.sendMessage(test);
        }
    }

    // public void downProgress() {
    //     if (clientSession!=null && clientSession.isOpen()){
    //         client.sendMessage("down");
    //     }
    // }
    

    @ClientEndpoint
    public class WebSocketClient {
        private Session session;
        // private TaskStatus taskStatus;
        @OnOpen
        public void onOpen(Session session) {
            clientSession = session;
            System.out.println("Connected to FastAPI WebSocket");
            // sendMessage("来自Spring Boot的初始消息");
        }

        @OnMessage
        public void onMessage(String message) throws IllegalArgumentException, IOException {
            // log.info("收到消息: " + message);
            try {
                synchronized (this) { // 同步块保证线程安全
                    if (this.session != null && this.session.isOpen()) {
                        this.session.getBasicRemote().sendPing(ByteBuffer.allocate(0)); // 发送ping消息
                    }else{

                    }
                }
                String fileName = TaskQueueService.currentTask.getFileName();
                // fileName = fileName.substring(0, fileName.lastIndexOf('.'));
                sendProgress(fileName,TaskQueueService.currentTask.getTaskType().toString(),message); //向前端转发进度消息
            } catch (IOException e) {
                // 连接已关闭，停止心跳
                log.info("connection close by error");
                // e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // if ("success".equals(message)) {
            //     // responseFuture.complete(message); // 当收到success时完成Future
            //     this.close();
            // }
        }

        @OnClose
        public void onClose(CloseReason reason) {
            System.out.println("连接关闭: " + reason.getReasonPhrase());
        }

        @OnError
        public void onError(Throwable throwable) {
            // System.err.println("WebSocket错误: ");
            // throwable.printStackTrace();
            if (throwable instanceof EOFException) {
                // 客户端非正常断开，无需记录错误日志
                try {
                    session.close();
                } catch (IOException e) {
                    // 可选：记录关闭时的异常
                }
            } else {
                // 其他异常需记录并处理
                log.error("WebSocket error: {}", throwable.getMessage());
            }
        }

        public void sendMessage(String message) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                throw new RuntimeException("消息发送失败", e);
            }
        }

        public void connect(String endpoint) {
            try {
                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                container.connectToServer(this, URI.create(endpoint));
            } catch (Exception e) {
                throw new RuntimeException("连接失败", e);
            }
        }

        public void close() {
            try {
                if (session != null && session.isOpen()) {
                    session.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
