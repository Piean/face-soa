package com.mogu.demo.mapper;

import com.mogu.demo.face.bo.Member;
import org.apache.ibatis.annotations.Param;

public interface MemberMapper {
    int insertMember(Member member);
    int updateMember(Member member);
    Member getById(String id);
    Member getByFaceId(String faceId);
    int countByMemberType(@Param("eid") String entityId, @Param("type") int memberType);
}
