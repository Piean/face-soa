package com.mogu.demo.api.face;

/**
 * Created by mogu
 * Date: 2018/10/22
 */
public interface IFaceHttpClient {
    <T> T detect(String base64Code);

    <T> T addFace(String entityId, String memberId, String base64Code);

    String getGroupId(String entityId);

    <T> T search(String faceToken, String groupId);
}
