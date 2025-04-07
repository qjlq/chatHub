package com.example.hello_world_with_mvc.service;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import jakarta.annotation.PostConstruct;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.CloseReason;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.Endpoint;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.SendHandler;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import com.example.hello_world_with_mvc.config.WebSocketConfig;
import com.example.hello_world_with_mvc.entity.Group;
import com.example.hello_world_with_mvc.entity.Task.TaskStatus;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/*
 *   WebSocket的操作类
 * html页面与之关联的接口
 * var reqUrl = "http://localhost:8080/websocket/" + cid;
 * socket = new WebSocket(reqUrl.replace("http", "ws"));
 * 
 */

/*
 * code 码
 * 0：用户上线
 * 1:普通信息
 * 2：用户下线
 * 3：群组列表
 * 4：个人消息
 * 5：群组被删除
 */

@Component
// @EnableWebSocket
@Slf4j
@ServerEndpoint(value = "/websocket/",configurator = WebSocketConfig.class)

//@ServerEndpoint("/websocket/{cid}")

public class WebSocketServer {

    @Autowired //连接databaseservice
    private DatabaseService database;
    private static WebSocketServer serverHandler ; //service to service 要加

    // @Autowired
    // private WebSocketClient client;

    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {   //普通的autowired 引用database 会报错 null
        serverHandler = this;  
        serverHandler.database = this.database;  
        // 初使化时将已静态化的testService实例化
    }  
    /**
     * 静态变量，用来记录当前在线连接数，线程安全的类。
     */
    private static AtomicInteger onlineSessionClientCount = new AtomicInteger(0);

    /**
     * 存放所有在线的客户端
     */
    private static Map<String, Session> onlineSessionClientMap = new ConcurrentHashMap<>();
    //存放已登录用户所在的群组及其拥有的成员
    private Map<String, List<String>> gidMapCid = new ConcurrentHashMap<>();
    //存放有该用户在的群主列表
    private Set<String> gidList;

    private Map<String, Group> gidName = new ConcurrentHashMap<>();
    /**
     * 连接cid和连接会话
     */
    public String cid;
    public String fromname;
    private Session session;
    private Gson gson = new Gson();
    WebSocketClient client;

    /**
     * 连接建立成功调用的方法。由前端<code>new WebSocket</code>触发
     *
     * @param cid     每次页面建立连接时传入到服务端的id，比如用户id等。可以自定义。 
     * @param session 与某个客户端的连接会话，需要通过它来给客户端发送消息
     */
    @OnOpen
    //public void onOpen(@PathParam("cid") String cid, Session session) {

    public void onOpen(Session session,EndpointConfig config) {
        /**
         * session.getId()：当前session会话会自动生成一个id，从0开始累加的。
         */
        try {
            this.cid = config.getUserProperties().get("cid").toString();
        } catch (Exception e) {
            log.info("{} token过期 / 非法访问",getTimeString());
        }
        log.info("cid f: " + cid);
        log.info("{} connecting: ==> session_id = {}， cid = {}",getTimeString(), session.getId(), cid);
        //加入 Map中。将页面的cid和session绑定或者session.getId()与session
        //onlineSessionIdClientMap.put(session.getId(), session);

        if (!onlineSessionClientMap.containsKey(cid)){
            onlineSessionClientMap.put(cid, session);
            onlineSessionClientCount.incrementAndGet();

            if (onlineSessionClientMap.size() == 1){
                //test python client API
                client = new WebSocketClient();
                client.connect("ws://localhost:8000/ws");
                client.close();      
            }
        
        }


        //在线数加1

        //初始化群列表
        initGroup(cid);
        // gidMapCid.forEach((gid,cidlist)->{
        //     System.err.println("gid:"+gid);
        //     cidlist.forEach(gcid->{
        //         System.err.println("cid:"+gcid);
        //     });
        // });

        // JsonObject gidlist = new JsonObject();
        // gidMapCid.forEach((gid,cidlist)->{
        //     System.err.println(cidlist.toString());
        //     gidlist.addProperty(gid, cidlist.toString());
        // });
        // System.err.println(gidlist.toString());

        this.cid = cid;
        this.session = session;
        this.fromname = serverHandler.database.getNameByCid(cid);
        sendWithform(session, cid, "",fromname, "你上线了", 0); //发用户名字
        sendWithform(session, 3);                                      //发totallist 和 gidinfo
        String message = fromname+ " 上线了";
        sendToAll(message,0);                                          //告诉其他用户你上线了
        sendOnlineList(session);                                            //告诉用户已上线的用户
        log.info("{} 在线数：{} ==> listening：session_id = {}， cid = {}",getTimeString(), onlineSessionClientCount, session.getId(), cid);
    }

    /**
     * 连接关闭调用的方法。由前端<code>socket.close()</code>触发
     *
     * @param cid
     * @param session
     */
    @OnClose
    //public void onClose(@PathParam("cid") String cid, Session session) {
    public void onClose(Session session) {

        //onlineSessionIdClientMap.remove(session.getId());
        // 从 Map中移除
        String message = fromname+ " 下线了";

        onlineSessionClientMap.remove(this.cid);

        //在线数减1
        onlineSessionClientCount.decrementAndGet();
        log.info("{} close: 在线数：{} ==> 关闭该连接信息：session_id = {}， cid = {}",getTimeString(), onlineSessionClientCount, session.getId(), cid);
        sendToAll(message,2);
    }

    /**
     * 收到客户端消息后调用的方法。由前端<code>socket.send</code>触发
     * * 当服务端执行toSession.getAsyncRemote().sendText(xxx)后，前端的socket.onmessage得到监听。
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        /**
         * 接收的数据格式.
         * {"cid":"user","gid":"gid","message":"hello websocket"}
         */
        //JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
        JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
        String tocid = jsonObject.get("cid").getAsString();
        String msg = jsonObject.get("message").getAsString();
        String gid = jsonObject.get("gid").getAsString();
        log.info("{} recieve:fromcid = {} ==>tocid = {} and togid{}, message = {}",getTimeString(), cid, tocid,gid, msg);

        /**
         * 约定：如果未指定cid和gid，则群发
         * cid != null and gid == null 发给指定的人
         * gid != null 发到群内
         */
        if (tocid == null || tocid == "" || "".equalsIgnoreCase(tocid)) {
            if(gid == null || gid == "" || "".equalsIgnoreCase(gid)){
                sendToAll(msg);
            }
            else{
                sendToGroup(msg, gid);
            }
        } else {
            sendToOne(tocid, msg);
            //log.info("toOne"+ tocid +  msg);
        }
    }

    /**
     * 发生错误调用的方法
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("{}:WebSocket error：{}" ,getTimeString(),error.getMessage());
        System.out.println(error.getMessage());
        error.printStackTrace();
    }

    public void sendMissonData(String message) {
        // 遍历在线map集合
        onlineSessionClientMap.forEach((onlinecid, toSession) -> {
            // 排除掉自己
            log.info("{} send: cid = {} ==> tocid = {}, message = {}",getTimeString(), cid, onlinecid, message);
            // toSession.getAsyncRemote().sendText(message);
            sendWithform(toSession,onlinecid,"",fromname,message,1); //defualt code 1
            
        });
    }

    /**
     * 群发消息
     *
     * @param message 消息
     */
    private void sendToAll(String message) {
        // 遍历在线map集合
        onlineSessionClientMap.forEach((onlinecid, toSession) -> {
            // 排除掉自己
            if (!cid.equalsIgnoreCase(onlinecid)) {
                log.info("{} send: cid = {} ==> tocid = {}, message = {}",getTimeString(), cid, onlinecid, message);
                // toSession.getAsyncRemote().sendText(message);
                sendWithform(toSession,cid,"",fromname,message,1); //defualt code 1
            }
        });
    }

    private void sendToAll(String message,int code) {
        // 遍历在线map集合
        onlineSessionClientMap.forEach((onlinecid, toSession) -> {
            // 排除掉自己
            if (!cid.equalsIgnoreCase(onlinecid)) {
                log.info("{} send: cid = {} ==> tocid = {}, message = {}",getTimeString(), cid, onlinecid, message);
                // toSession.getAsyncRemote().sendText(message);
                sendWithform(toSession,cid,"",fromname,message,code);
            }
        });
    }
//发送群消息
    private void sendToGroup(String message,String gid) {
        
        onlineSessionClientMap.forEach((onlinecid, toSession) -> { 
            try {
                gidMapCid.get(gid).forEach(lcid -> {
                    // 遍历在线map集合
                    //log.info("acc"+this.cid);
                        if (lcid.equalsIgnoreCase(onlinecid) && onlinecid != this.cid) {
                            log.info("{} gsend: cid = {} ==> tocid = {}, message = {}",getTimeString(), cid, onlinecid, message);
                            // toSession.getAsyncRemote().sendText(message);
                            sendWithform(toSession,cid,gid,fromname,message,1);
                        }
                    });
            } catch (Exception e) {
                initGroup(this.cid);
                gidMapCid.get(gid).forEach(lcid -> {
                    // 遍历在线map集合
                    //log.info("acc"+this.cid);
                        if (lcid.equalsIgnoreCase(onlinecid) && onlinecid != this.cid) {
                            log.info("{} gsend: cid = {} ==> tocid = {}, message = {}",getTimeString(), cid, onlinecid, message);
                            // toSession.getAsyncRemote().sendText(message);
                            sendWithform(toSession,cid,gid,fromname,message,1);
                        }
                    });
                // TODO: handle exception
            }

        });

    }

    /**
     * 指定发送消息
     *
     * @param tocid
     * @param message
     */
    private void sendToOne(String tocid, String message) {
        // 通过cid查询map中是否存在
        Session toSession = onlineSessionClientMap.get(tocid);
        if (toSession == null) {
            log.error("{} send error: tocid = {} 不存在, message = {}",getTimeString(), tocid, message);
            return;
        }
        // 异步发送
        log.info("{} personal send: ==> tocid = {}, message = {}",getTimeString(), tocid, message);
        // toSession.getAsyncRemote().sendText(message);
        sendWithform(toSession,this.cid,"",fromname,message,4);
        /*
        // 同步发送
        try {
            toSession.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("发送消息失败，WebSocket IO异常");
            e.printStackTrace();
        }*/
    }
    //发送的数据格式
    //{"cid":"user","gid":"gid","name":"name","message":"hello websocket","code":"code"}
    private void sendWithform(Session session,String cid,String gid,String name, String msg,int code) {
        JsonObject message = new JsonObject();
        message.addProperty("cid",cid);
        message.addProperty("gid",gid);
        message.addProperty("name",name);
        message.addProperty("msg",msg);
        //message.addProperty("totallist","");
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

    //发送的数据格式
    //{"totallist":"totallist","gidinfo":"info","code":"code"}
    private void sendWithform(Session session,int code) { //发送用户的群组信息
        JsonObject message = new JsonObject();
        JsonObject totallist = new JsonObject();
        // message.addProperty("cid","");
        // message.addProperty("gid","");
        // message.addProperty("name","");
        // message.addProperty("msg","");
        
        gidMapCid.forEach((gid,cidlist)->{
            //System.err.println(cidlist.toString());
            totallist.addProperty(gid, cidlist.toString());
        });
        message.add("totallist",totallist); //有list 所以不直接用tojson
        //message.addProperty("totallist",gson.toJson(gidMapCid));
        //message.addProperty("gidlist", this.gidList.toString());
        message.addProperty("gidinfo",gson.toJson(gidName));
        message.addProperty("code",code);
        synchronized(session){ //防止冲突
            try {
                session.getBasicRemote().sendText(message.toString());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.info(e.toString());
                e.printStackTrace();
            } //getAsyncRemote是异步发送是错误的使用，该用getBasicRemote()
        }
        
    }
    
    public void addGroup(String gid){  //把新创建的群组信息发给群组成员
        JsonObject message = new JsonObject();
        JsonObject totallist = new JsonObject();
        Map<String, Group> TMPgidName = new ConcurrentHashMap<>();;
        List<String> cidList = serverHandler.database.getCidByGid(gid);
        Group gidinfo = serverHandler.database.getGroupGid(gid);
        TMPgidName.put(gid, gidinfo);
        totallist.addProperty(gid, cidList.toString());
        message.add("totallist",totallist);
        message.addProperty("gidinfo",gson.toJson(TMPgidName));
        message.addProperty("code","3");
        onlineSessionClientMap.forEach((onlinecid, toSession) -> {  //只发给在线的群成员
            for (String string : cidList) {
                if (string.equals(onlinecid)) {
                    synchronized(toSession){ //防止冲突
                        try {
                            toSession.getBasicRemote().sendText(message.toString());
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            log.info(e.toString());
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

    }

    public void deleteGroupMsg(String gid,String ccid){
        JsonObject message = new JsonObject();
        message.addProperty("gid",gid);
        message.addProperty("code","5");
        onlineSessionClientMap.forEach((onlinecid, toSession) -> {
            // 排除掉删除群的cid
            if (!ccid.equalsIgnoreCase(onlinecid)) {
                log.info("delete group send: gid = {} ==> tocid = {}", gid, onlinecid);
                synchronized(toSession){ //防止冲突
                    try {
                        toSession.getBasicRemote().sendText(message.toString());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        log.info(e.toString());
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private String getTimeString(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    
    private void initGroup(String cid){//获取用户所在群组的成员
        this.gidList = new HashSet<String>(serverHandler.database.getGidByCid(cid)); //error:com.example.hello_world_with_mvc.service.WebSocketServer.database" is null 参考:https://www.cnblogs.com/shizhe99/p/15579881.html
        gidList.forEach(gid -> {
            // if (!gidMapCid.containsKey(gid)){
            //     gidMapCid.put(gid, serverHandler.database.getCidByGid(gid));
            // }
            gidMapCid.put(gid, serverHandler.database.getCidByGid(gid));
            gidName.put(gid, serverHandler.database.getGroupGid(gid));
            //System.err.println(serverHandler.database.getGroupGid(gid).getgroupname());
        });
    }

    private void sendOnlineList(Session session){  //发送在线用户列表
        onlineSessionClientMap.forEach((onlinecid, toSession) -> {
            //log.info(onlinecid);
            if (!onlinecid.equals(this.cid)){
                //log.info(onlinecid);
                sendWithform(session, onlinecid, "", serverHandler.database.getNameByCid(onlinecid), "", 0);
            }
        });
    }

    public CompletableFuture<String> startTask(String test){
        WebSocketClient client = new WebSocketClient();
        client.connect("ws://qjlkalok:8000/ws");
        client.sendMessage(test);
        return client.getResponseFuture();
    }

    @ClientEndpoint
    public class WebSocketClient {
        private Session session;
        private CompletableFuture<String> responseFuture = new CompletableFuture<>();
        private TaskStatus taskStatus;
        @OnOpen
        public void onOpen(Session session) {
            this.session = session;
            System.out.println("Connected to FastAPI WebSocket");
            sendMessage("来自Spring Boot的初始消息");
        }

        @OnMessage
        public void onMessage(String message) {
            System.out.println("收到FastAPI响应: " + message);
            if ("success".equals(message)) {
                responseFuture.complete(message); // 当收到success时完成Future
            }
        }

        public CompletableFuture<String> getResponseFuture() {
            return responseFuture;
        }

        @OnClose
        public void onClose(CloseReason reason) {
            System.out.println("连接关闭: " + reason.getReasonPhrase());
        }

        @OnError
        public void onError(Throwable throwable) {
            System.err.println("WebSocket错误: ");
            throwable.printStackTrace();
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
