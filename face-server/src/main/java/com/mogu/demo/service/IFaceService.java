package com.mogu.demo.service;

import com.mogu.demo.face.bo.Face;

public interface IFaceService {
    Face insertFace(Face face);
    boolean updateFace(Face face);
    Face getById(String id);
}
