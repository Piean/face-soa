package com.mogu.demo.api.face;

import java.nio.file.Path;

/**
 * Created by mogu
 * Date: 2018/10/22
 */
public interface IFaceHttpClient {
    <T> T detect(Path path);

    <T> T detect(String faceToken);

    <T> T addFace(String groupId, Path path, String bizId);

    <T> T addFace(String groupId, String faceToken, String bizId);

    String getGroupId(String entityId, String groupName);

    <T> T search(String entityId, String faceToken);
}
