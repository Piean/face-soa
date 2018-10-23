package com.mogu.demo.controller;

import com.alibaba.fastjson.JSON;
import com.dfire.soa.shop.bo.Shop;
import com.dfire.soa.shop.service.IShopClientService;
import com.google.common.collect.Lists;
import com.mogu.demo.api.face.ApiConstants;
import com.mogu.demo.face.result.ResultMap;
import com.mogu.demo.face.vo.CountVo;
import com.mogu.demo.face.vo.MemberVo;
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
    public ResultMap subMessage(String faceToken) {
        System.out.println(faceToken);
        MemberVo member = new MemberVo();
        member.setMemberId("123456");
        member.setSex(1);
        member.setAge(30);
        member.setName("王大锤");
        member.setPhone("15334860985");
        member.setMemberType(3);
        member.setCardType(2);
        member.setCardRemain(50000);
        member.setArriveTime(System.currentTimeMillis());
        member.setCounter(994);
        member.setTag(Lists.newArrayList("逗逼","演员","万万没想到"));
        member.setLove(Lists.newArrayList("花花"));
        member.setHate(Lists.newArrayList("大葱","大蒜"));

        this.sendCustomerCount(ApiConstants.ENTITY_ID);

        return new ResultMap(member);
    }

    @SubscribeMapping("/shop/login")
    public String sendMessage(String entityId) {
        Result<Shop> shop = shopClientService.getShopByEntityId(entityId);
        return JSON.toJSONString(shop);
    }

    private void sendCustomerCount(String entityId) {
        messageSending.convertAndSend("/customer/count", new ResultMap<>(new CountVo()));
    }
}
