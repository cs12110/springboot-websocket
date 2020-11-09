package com.pkgs.bullshit.socket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 怎么给指定的用户发送指定的消息?
 * <p>
 * 发送给其他用户不在线时,怎么处理?
 *
 * @author huapeng.huang
 * @version V1.0
 * @since 2020-11-09 16:32
 */
@Slf4j
@Component
@ServerEndpoint("/chat/{token}")
public class WebsocketEndpointService {

    private static final Map<String, Session> SOCKET_SESSION_STORAGE = new ConcurrentHashMap<>();

    @OnOpen
    public void handleOpenEvent(Session session, @PathParam("token") String token) {
        log.info("Function[handleOpenEvent] open by:{},token:{}", session.getId(), token);
        SOCKET_SESSION_STORAGE.put(token, session);

        // 返回确认连接信息
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sendMessage(session, sdf.format(new Date()) + " pong");
    }

    @OnClose
    public void handleCloseEvent(Session session, @PathParam("token") String token) {
        SOCKET_SESSION_STORAGE.remove(token);
        log.info("Function[handleCloseEvent] close by:{}", session.getId());
    }

    @OnMessage
    public void handleWithMessage(String message, Session session) {
        try {
            log.info("Function[handleWithMessage] sessionId:{}, message:{}", session.getId(), message);
            sendMessage(session, message);
        } catch (Exception e) {
            log.error("Function[handleWithMessage]", e);
        }
    }

    public void point(String token, String message) {
        Session session = SOCKET_SESSION_STORAGE.get(token);
        sendMessage(session, message);
    }

    public void broadcast(String message) {
        for (Map.Entry<String, Session> entry : SOCKET_SESSION_STORAGE.entrySet()) {
            sendMessage(entry.getValue(), message);
        }
    }

    private void sendMessage(Session session, String message) {
        if (Objects.isNull(session) || !session.isOpen()) {
            log.error("Function[sendMessage]send failure,session is null or session is close");
            return;
        }
        try {
            session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.info("Function[sendMessage]", e);
        }
    }

}
