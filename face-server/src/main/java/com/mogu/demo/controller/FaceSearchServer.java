package com.mogu.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dfire.soa.shop.bo.Shop;
import com.dfire.soa.shop.service.IShopClientService;
import com.google.common.collect.Lists;
import com.mogu.demo.api.face.ApiConstants;
import com.mogu.demo.api.face.IFaceHttpClient;
import com.mogu.demo.baidu.http.FaceDetectResult;
import com.mogu.demo.baidu.http.FaceSearchResult;
import com.mogu.demo.baidu.http.HttpResult;
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
    private static volatile int number = 0;

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
        System.out.println("info:"+ faceInfo);

        HttpResult<FaceDetectResult> faceObject = JSON.parseObject(faceInfo, new TypeReference<HttpResult<FaceDetectResult>>(){});
        if (faceObject == null || faceObject.getResult() == null || CollectionUtil.isEmpty(faceObject.getResult().getFace_list())) {
            return new ResultMap();
        } else {
            FaceDetectResult.FaceInfo face = faceObject.getResult().getFace_list().get(0);

            MemberVo vo = this.searchAndAdd(ApiConstants.ENTITY_ID, face);

            return new ResultMap(vo);
        }
    }

    @SubscribeMapping("/init")
    public ResultMap sendMessage() {
        count.setCount(statisticService.statistic(ApiConstants.ENTITY_ID));
        return new ResultMap<>(count);
    }

    private synchronized MemberVo searchAndAdd(String entityId, FaceDetectResult.FaceInfo faceInfo) {
        final MemberVo vo = new MemberVo();

        final String groupId = groupService.getByEntityId(entityId).getId();

        HttpResult<FaceSearchResult> searchResult = faceHttpClient.search(groupId, faceInfo.getFace_token());

        String faceId;
        String faceToken;

        Face face = new Face();
        face.setGroupId(groupId);
        face.setSex(faceInfo.getGender() != null ? faceInfo.getGender().getType() : 0);
        face.setAge((int) Math.round(faceInfo.getAge()));
        face.setArriveTime(System.currentTimeMillis());
        face.setFaceUrl(faceInfo.getBase64Code());

        Member member;

        boolean flag = false;

        if (searchResult == null || searchResult.getResult() == null || CollectionUtil.isEmpty(searchResult.getResult().getUser_list())) {
            //未入人脸库进行入库操作
            faceId = StringUtil.getUUID();
            faceToken = faceHttpClient.addFace(entityId, faceInfo.getFace_token(),faceId);

            face.setId(faceId);
            face.setFaceToken(faceToken);
            faceService.insertFace(face);

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
            flag = true;
        } else {
            //已入库用当前的faceToken
            faceId = searchResult.getResult().getUser_list().get(0).getUser_id();
            faceToken = faceInfo.getFace_token();
            face.setId(faceId);
            face.setFaceToken(faceToken);
            flag = faceService.updateFace(face);

            member = memberService.getByFaceId(faceId);
            if (StringUtils.isBlank(member.getMemberId())) {
                member.setMemberType(Member.MemberType.AGAIN.getId());
                memberService.updateMember(member);
            }
        }

        vo.setId(member.getId());
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
        vo.setTag(JSON.parseArray(member.getTag(),String.class));
        vo.setLove(JSON.parseArray(member.getLove(),String.class));
        vo.setHate(JSON.parseArray(member.getHate(),String.class));

        if (flag) {
            this.sendCustomerCount(entityId,face,member);
            number++;
        }
        vo.setCounter(number);

        return vo;
    }

    private void sendCustomerCount(String entityId, Face face, Member member) {
        if (count.getSum() == 0) {
            count.setCount(statisticService.statistic(entityId));
        } else {
            count.setCount(member,face);
        }

        messageSending.convertAndSend("/customer/count", new ResultMap<>(count));
    }
}
