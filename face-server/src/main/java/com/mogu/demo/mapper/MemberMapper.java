package com.mogu.demo.mapper;

import com.mogu.demo.face.bo.Member;

public interface MemberMapper {
    int insertMember(Member member);
    int updateMember(Member member);
    Member getByMemberId(String memberId);
}
