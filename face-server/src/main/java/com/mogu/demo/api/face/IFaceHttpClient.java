package com.mogu.demo.api.face;

/**
 * Created by mogu
 * Date: 2018/10/22
 */
public interface IFaceHttpClient {
    <T> T detect();

    String createGroup(String groupName);

    <T> T search(String faceToken, String groupId);
}
