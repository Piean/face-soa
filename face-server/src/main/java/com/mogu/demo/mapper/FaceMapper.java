package com.mogu.demo.mapper;

import com.mogu.demo.face.bo.Face;

public interface FaceMapper {
    int insertFace(Face face);
    int updateFace(Face face);
    Face getByMemberId(String memberId);
}
