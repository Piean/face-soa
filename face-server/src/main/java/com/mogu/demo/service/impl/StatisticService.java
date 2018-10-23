package com.mogu.demo.service.impl;

import com.mogu.demo.face.bo.Face;
import com.mogu.demo.face.bo.Member;
import com.mogu.demo.face.vo.CountVo;
import com.mogu.demo.mapper.FaceMapper;
import com.mogu.demo.mapper.MemberMapper;
import com.mogu.demo.service.IStatisticService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StatisticService implements IStatisticService {
    @Resource
    private FaceMapper faceMapper;
    @Resource
    private MemberMapper memberMapper;

    @Override
    public CountVo statistic(String entityId) {
        CountVo count = new CountVo();
        int man = faceMapper.countBySex(entityId, Face.Sex.MAN.getId());
        int woman = faceMapper.countBySex(entityId, Face.Sex.WOMAN.getId());

        int first = memberMapper.countByMemberType(entityId, Member.MemberType.FIRST.getId());
        int again = memberMapper.countByMemberType(entityId, Member.MemberType.AGAIN.getId());
        int member = memberMapper.countByMemberType(entityId, Member.MemberType.MEMBER.getId());

        count.setSum(first + again + member);
        count.setManNum(man);
        count.setWomanNum(woman);
        count.setCustomerNum(first + again);
        count.setMemberNum(member);
        return count;
    }
}
