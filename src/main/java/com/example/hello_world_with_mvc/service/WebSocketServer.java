package com.example.hello_world_with_mvc.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/*
 *   WebSocket的操作类
 * html页面与之关联的接口
 * var reqUrl = "http://localhost:8080/websocket/" + cid;
 * socket = new WebSocket(reqUrl.replace("http", "ws"));
 */

@Component
@Slf4j
@ServerEndpoint("/websocket/{cid}")
public class WebSocketServer {
    /**
     * 静态变量，用来记录当前在线连接数，线程安全的类。
     */
    private static AtomicInteger onlineSessionClientCount = new AtomicInteger(0);

    /**
     * 存放所有在线的客户端
     */
    private static Map<String, Session> onlineSessionClientMap = new ConcurrentHashMap<>();

    /**
     * 连接cid和连接会话
     */
    private String cid;
    private Session session;

    /**
     * 连接建立成功调用的方法。由前端<code>new WebSocket</code>触发
     *
     * @param cid     每次页面建立连接时传入到服务端的id，比如用户id等。可以自定义。
     * @param session 与某个客户端的连接会话，需要通过它来给客户端发送消息
     */
    @OnOpen
    public void onOpen(@PathParam("cid") String cid, Session session) {
        /**
         * session.getId()：当前session会话会自动生成一个id，从0开始累加的。
         */
        log.info("连接建立中 ==> session_id = {}， cid = {}", session.getId(), cid);
        //加入 Map中。将页面的cid和session绑定或者session.getId()与session
        //onlineSessionIdClientMap.put(session.getId(), session);
        onlineSessionClientMap.put(cid, session);

        //在线数加1
        onlineSessionClientCount.incrementAndGet();
        this.cid = cid;
        this.session = session;
        sendToOne(cid, "你上线了");
        String message = cid.toString()+ " 上线了";
        sendToAll(message);
        log.info("连接建立成功，当前在线数为：{} ==> 开始监听新连接：session_id = {}， cid = {},。", onlineSessionClientCount, session.getId(), cid);
    }

    /**
     * 连接关闭调用的方法。由前端<code>socket.close()</code>触发
     *
     * @param cid
     * @param session
     */
    @OnClose
    public void onClose(@PathParam("cid") String cid, Session session) {
        //onlineSessionIdClientMap.remove(session.getId());
        // 从 Map中移除
        String message = cid.toString()+ " 下线了";

        onlineSessionClientMap.remove(cid);

        //在线数减1
        onlineSessionClientCount.decrementAndGet();
        log.info("连接关闭成功，当前在线数为：{} ==> 关闭该连接信息：session_id = {}， cid = {},。", onlineSessionClientCount, session.getId(), cid);
        sendToAll(message);
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
         * html界面传递来得数据格式，可以自定义.
         * {"cid":"user","message":"hello websocket"}
         */
        //JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
        JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
        String tocid = jsonObject.get("cid").getAsString();
        String msg = jsonObject.get("message").getAsString();
        log.info("服务端收到客户端消息 ==> fromcid = {}, tocid = {}, message = {}", cid, tocid, message);

        /**
         * 模拟约定：如果未指定cid信息，则群发，否则就单独发送
         */
        if (tocid == null || tocid == "" || "".equalsIgnoreCase(tocid)) {
            sendToAll(msg);
        } else {
            sendToOne(tocid, msg);
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
        log.error("WebSocket发生错误，错误信息为：" + error.getMessage());
        System.out.println(error.getMessage());
        error.printStackTrace();
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
                log.info("服务端给客户端群发消息 ==> cid = {}, tocid = {}, message = {}", cid, onlinecid, message);
                // toSession.getAsyncRemote().sendText(message);
                sendWithform(toSession,cid,message);
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
            log.error("服务端给客户端发送消息 ==> tocid = {} 不存在, message = {}", tocid, message);
            return;
        }
        // 异步发送
        log.info("服务端给客户端发送消息 ==> tocid = {}, message = {}", tocid, message);
        // toSession.getAsyncRemote().sendText(message);
        sendWithform(toSession,tocid,message);
        /*
        // 同步发送
        try {
            toSession.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("发送消息失败，WebSocket IO异常");
            e.printStackTrace();
        }*/
    }

    private void sendWithform(Session session,String cid, String msg) {
        JsonObject message = new JsonObject();
        message.addProperty("cid",cid);
        message.addProperty("msg",msg);
        session.getAsyncRemote().sendText(message.toString());
    }
    
}
