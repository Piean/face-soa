package com.mogu.demo.service.impl;

import com.mogu.demo.face.bo.Member;
import com.mogu.demo.mapper.MemberMapper;
import com.mogu.demo.service.IMemberService;
import com.mogu.demo.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MemberService implements IMemberService {
    @Resource
    private MemberMapper memberMapper;

    @Override
    public Member insertMember(Member member) {
        if (StringUtils.isAnyBlank(member.getFaceId())) {
            return null;
        }

        if (StringUtils.isBlank(member.getId())) {
            member.setId(StringUtil.getUUID());
        }
        memberMapper.insertMember(member);
        return member;
    }

    @Override
    public boolean updateMember(Member member) {
        if (StringUtils.isBlank(member.getId())) {
            return false;
        }
        return memberMapper.updateMember(member) > 0;
    }

    @Override
    public Member getById(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        return memberMapper.getById(id);
    }

    @Override
    public Member getByFaceId(String faceId) {
        if (StringUtils.isBlank(faceId)) {
            return null;
        }
        return memberMapper.getByFaceId(faceId);
    }
}
