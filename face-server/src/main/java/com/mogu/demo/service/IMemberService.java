package com.mogu.demo.service;

import com.mogu.demo.face.bo.Member;

public interface IMemberService {
    Member insertMember(Member member);
    Member getByMemberId(String memberId);
}
