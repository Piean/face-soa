package com.mogu.demo.mapper;

import com.mogu.demo.face.bo.Face;
import org.apache.ibatis.annotations.Param;

public interface FaceMapper {
    int insertFace(Face face);
    int updateFace(Face face);
    Face getById(String id);
    int countBySex(@Param("eid") String entityId, @Param("sex") int sex);
}
