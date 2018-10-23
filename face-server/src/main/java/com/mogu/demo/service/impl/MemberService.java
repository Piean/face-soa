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
        if (StringUtils.isAnyBlank(member.getMemberId())) {
            return null;
        }

        member.setId(StringUtil.getUUID());
        memberMapper.insertMember(member);
        return member;
    }

    @Override
    public Member getByMemberId(String memberId) {
        if (StringUtils.isBlank(memberId)) {
            return null;
        }
        return memberMapper.getByMemberId(memberId);
    }
}
