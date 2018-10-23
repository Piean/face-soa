package com.mogu.demo.service.impl;

import com.mogu.demo.face.bo.Face;
import com.mogu.demo.mapper.FaceMapper;
import com.mogu.demo.service.IFaceService;
import com.mogu.demo.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FaceService implements IFaceService {
    @Resource
    private FaceMapper faceMapper;

    @Override
    public Face insertFace(Face face) {
        if (StringUtils.isAnyBlank(face.getGroupId(),face.getFaceToken(),face.getFaceUrl())) {
            return null;
        }
        if (StringUtils.isBlank(face.getId())) {
            face.setId(StringUtil.getUUID());
        }
        faceMapper.insertFace(face);
        return face;
    }

    @Override
    public boolean updateFace(Face face) {
        if (StringUtils.isBlank(face.getId())) {
            return false;
        }
        return faceMapper.updateFace(face) > 0;
    }

    @Override
    public Face getById(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        return faceMapper.getById(id);
    }
}
