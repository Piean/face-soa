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
        final String id = StringUtil.getUUID();
        face.setId(id);
        faceMapper.insertFace(face);
        return face;
    }

    @Override
    public Face getById(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        return faceMapper.getById(id);
    }
}
