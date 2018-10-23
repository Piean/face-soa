package com.mogu.demo.controller;

import com.alibaba.fastjson.JSON;
import com.dfire.soa.shop.bo.Shop;
import com.dfire.soa.shop.service.IShopClientService;
import com.google.common.collect.Lists;
import com.mogu.demo.api.face.ApiConstants;
import com.mogu.demo.api.face.IFaceHttpClient;
import com.mogu.demo.baidu.http.FaceDetectResult;
import com.mogu.demo.baidu.http.FaceSearchResult;
import com.mogu.demo.face.bo.Face;
import com.mogu.demo.face.bo.Member;
import com.mogu.demo.face.result.ResultMap;
import com.mogu.demo.face.vo.CountVo;
import com.mogu.demo.face.vo.MemberVo;
import com.mogu.demo.service.IFaceService;
import com.mogu.demo.service.IGroupService;
import com.mogu.demo.service.IMemberService;
import com.mogu.demo.service.IStatisticService;
import com.mogu.demo.utils.Base64Code;
import com.mogu.demo.utils.CollectionUtil;
import com.mogu.demo.utils.StringUtil;
import com.twodfire.share.result.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.websocket.Session;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mogu
 * Date: 2018/10/19
 */

@Controller
public class FaceSearchServer {
    private static final CountVo count = new CountVo();

    @Resource
    private IFaceHttpClient faceHttpClient;
    @Resource
    private SimpMessageSendingOperations messageSending;

    @Resource
    private IStatisticService statisticService;
    @Resource
    private IGroupService groupService;
    @Resource
    private IFaceService faceService;
    @Resource
    private IMemberService memberService;

    @MessageMapping("/face")
    @SendTo("/member/info")
    public ResultMap subMessage(String faceInfo) {
        System.out.println(faceInfo);

//        FaceDetectResult faceObject = JSON.parseObject(faceInfo, FaceDetectResult.class);
//        if (faceObject == null || CollectionUtil.isEmpty(faceObject.getFace_list())) {
//            return new ResultMap();
//        }
//
//        FaceDetectResult.FaceInfo face = faceObject.getFace_list().get(0);
//
//        MemberVo vo = this.searchAndAdd(ApiConstants.ENTITY_ID, face);

        //mok 完成返回vo即可
        MemberVo member = new MemberVo();
        member.setMemberId("123456");
        member.setFaceCode("asdasd");
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

        return new ResultMap(member);
    }

    @SubscribeMapping("/shop/login")
    public String sendMessage(String entityId) {
        return entityId;
    }

    private synchronized MemberVo searchAndAdd(String entityId, FaceDetectResult.FaceInfo faceInfo) {
        final MemberVo vo = new MemberVo();

        FaceSearchResult searchResult = faceHttpClient.search(entityId, faceInfo.getFace_token());

        final String groupId = groupService.getByEntityId(entityId).getId();
        String faceId;
        String faceToken;

        Face face = new Face();
        face.setGroupId(groupId);
        face.setSex(faceInfo.getGender().getType());
        face.setAge((int) Math.round(faceInfo.getAge()));
        face.setArriveTime(System.currentTimeMillis());
        face.setFaceUrl(faceInfo.getBase64Code());

        if (searchResult == null && CollectionUtil.isEmpty(searchResult.getUser_list())) {
            //未入人脸库进行入口操作
            faceId = StringUtil.getUUID();
            faceToken = faceHttpClient.addFace(entityId, faceInfo.getFace_token(),faceId);

            face.setId(faceId);
            face.setFaceToken(faceToken);
            faceService.insertFace(face);
        } else {
            //已入库用当前的faceToken
            faceId = searchResult.getUser_list().get(0).getUser_id();
            faceToken = faceInfo.getFace_token();

            face.setId(faceId);
            face.setFaceToken(faceToken);
            faceService.updateFace(face);
        }

        Member member;
        if (searchResult != null && CollectionUtil.isNotEmpty(searchResult.getUser_list())) {
            FaceSearchResult.User user = searchResult.getUser_list().get(0);
            member = memberService.getByFaceId(user.getUser_id());
            if (StringUtils.isBlank(member.getMemberId())) {
                member.setMemberType(Member.MemberType.AGAIN.getId());
                memberService.updateMember(member);
            }
        } else {
            member = new Member();
            member.setId(StringUtil.getUUID());
            member.setMemberId("");
            member.setName("");
            member.setPhone("");
            member.setMemberType(Member.MemberType.FIRST.getId());
            member.setTag("[]");
            member.setLove("[]");
            member.setHate("[]");
            memberService.insertMember(member);
        }

        vo.setMemberId(member.getMemberId());
        vo.setFaceCode(face.getFaceUrl());
        vo.setSex(face.getSex());
        vo.setAge(face.getAge());
        vo.setName(member.getName());
        vo.setPhone(member.getPhone());
        vo.setMemberType(member.getMemberType());
        vo.setCardType(member.getCardType());
        vo.setCardRemain(member.getCardRemain());
        vo.setArriveTime(face.getArriveTime());
        vo.setCounter(face.getCounter());
        vo.setTag(Lists.newArrayList("逗逼","演员","万万没想到"));
        vo.setLove(Lists.newArrayList("花花"));
        vo.setHate(Lists.newArrayList("大葱","大蒜"));

        this.sendCustomerCount(entityId,face,member);

        return vo;
    }

    private void sendCustomerCount(String entityId, Face face, Member member) {
        if (count.getSum() == 0) {
            count.setCount(statisticService.statistic(entityId));
        } else {
            count.setCount(member,face);
        }

        messageSending.convertAndSend("/customer/count", new ResultMap<>(new CountVo()));
    }
}
