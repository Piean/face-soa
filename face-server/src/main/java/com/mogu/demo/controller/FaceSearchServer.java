package com.mogu.demo.controller;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mogu
 * Date: 2018/10/19
 */

@Component
@ServerEndpoint("/face/search/{face_id}")
public class FaceSearchServer {
    final private static Map<String, FaceSearchServer> client = new ConcurrentHashMap<>();
    private Session session;


    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        client.put(session.getId(), this);
        System.out.println("One clint connected, id is" + session.getId());
    }

    @OnClose
    public void onClose() {
        client.remove(this.session.getId());
    }
}
