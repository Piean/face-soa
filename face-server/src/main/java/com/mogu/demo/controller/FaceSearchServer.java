package com.mogu.demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mogu
 * Date: 2018/10/19
 */

@Controller
public class FaceSearchServer {
    final private static Map<String, FaceSearchServer> client = new ConcurrentHashMap<>();
    private Session session;

    @MessageMapping("/face")
    @SendTo("/face/search")
    public String subMessage(String entityId, String token) {
        System.out.println(entityId + token);
        return entityId + token;
    }

    @SubscribeMapping("/member/info")
    public String sendMessage() {
        return "hhhh";
    }
}
