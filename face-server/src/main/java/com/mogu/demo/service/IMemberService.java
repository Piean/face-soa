package com.mogu.demo.service;

import com.mogu.demo.face.bo.Member;

public interface IMemberService {
    Member insertMember(Member member);
    boolean updateMember(Member member);
    Member getById(String id);
    Member getByFaceId(String faceId);
}
