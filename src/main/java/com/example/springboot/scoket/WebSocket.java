package com.example.springboot.scoket;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Log4j2
@ServerEndpoint("/websocket")
@Component
public class WebSocket {

    //每个客户端都会有相应的session,服务端可以发送相关消息
    private Session session;
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount=0;


    //J.U.C包下线程安全的类，主要用来存放每个客户端对应的webSocket连接，为什么说他线程安全。在文末做简单介绍
    private static CopyOnWriteArraySet<WebSocket> copyOnWriteArraySet = new CopyOnWriteArraySet<WebSocket>();


    /**
     * 打开连接。进入页面后会自动发请求到此进行连接
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        copyOnWriteArraySet.add(this);
        log.info("websocket有新的连接, 总数:"+ getOnlineCount());

    }

    /**
     * 用户关闭页面，即关闭连接
     */
    @OnClose
    public void onClose() {
        copyOnWriteArraySet.remove(this);
        log.info("websocket连接断开, 总数:"+getOnlineCount());
    }

    /**
     * 测试客户端发送消息，测试是否联通
     * @param message
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("websocket收到客户端发来的消息:"+message);
    }


    /**
     * 出现错误
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误：" + error.getMessage(), session.getId());
        error.printStackTrace();
    }

    /**
     * 用于发送给客户端消息（群发）
     * @param message
     */

    public void sendMessage(String message) {


        //遍历客户端
        for (WebSocket webSocket : copyOnWriteArraySet) {
            log.info("websocket广播消息：" + message);
            try {
                //服务器主动推送
               webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 用于发送给指定客户端消息,这里写的不好。。不管了
     *
     * @param message
     */
    public void sendMessage(String sessionId, String message) throws IOException {
        Session session = null;
        WebSocket tempWebSocket = null;
        for (WebSocket webSocket : copyOnWriteArraySet) {
            if (webSocket.session.getId().equals(sessionId)) {
                tempWebSocket = webSocket;
                session = webSocket.session;
                break;
            }
        }
        if (session != null) {
            tempWebSocket.session.getBasicRemote().sendText(message);
        } else {
            log.warn("没有找到你指定ID的会话：{}", sessionId);
        }
    }
    /**
     * 统计用户
     */
    public static synchronized int getOnlineCount() {
        onlineCount=copyOnWriteArraySet.size();
        return onlineCount;
    }


}
