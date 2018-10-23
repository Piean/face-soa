package com.mogu.demo.controller;

import com.alibaba.fastjson.JSON;
import com.dfire.soa.shop.bo.Shop;
import com.dfire.soa.shop.service.IShopClientService;
import com.twodfire.share.result.Result;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mogu
 * Date: 2018/10/19
 */

@Controller
public class FaceSearchServer {
    @Resource
    private IShopClientService shopClientService;

    @Resource
    private SimpMessageSendingOperations messageSending;

    final private static Map<String, FaceSearchServer> client = new ConcurrentHashMap<>();
    private Session session;

    @MessageMapping("/face")
    @SendTo("/member/info")
    public String subMessage(String faceToken) {
        System.out.println(faceToken);
//        Result<Shop> shop = shopClientService.getShopByEntityId(faceToken);
//        messageSending.convertAndSend("/shop/info", shop);
        return faceToken;
    }

    @SubscribeMapping("/shop/login")
    public String sendMessage(String entityId) {
        Result<Shop> shop = shopClientService.getShopByEntityId(entityId);
        return JSON.toJSONString(shop);
    }
}
