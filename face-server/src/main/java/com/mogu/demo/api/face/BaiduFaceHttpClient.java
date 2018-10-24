package com.mogu.demo.api.face;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.mogu.demo.baidu.http.*;
import com.mogu.demo.face.bo.Group;
import com.mogu.demo.service.IGroupService;
import com.mogu.demo.utils.Base64Code;
import com.mogu.demo.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Created by mogu
 * Date: 2018/10/22
 */
@Service
public class BaiduFaceHttpClient implements IFaceHttpClient {
    @Resource
    private IGroupService groupService;

    private static String token = null;

    @Override
    public HttpResult<FaceDetectResult> detect(Path path) {
        HttpPost httpPost = new HttpPost("https://aip.baidubce.com/rest/2.0/face/v3/detect");
        try {
            httpPost.addHeader("Content-Type","application/json");
            httpPost.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(
                    new BasicNameValuePair("access_token", this.getAccessToken()),
                    new BasicNameValuePair("image", Base64Code.encodeFile(path)),
                    new BasicNameValuePair("image_type", ApiConstants.IMAGE_TYPE_BASE64),
                    new BasicNameValuePair("face_field", ApiConstants.BAIDU_DETECT_FILED)
            ), ApiConstants.API_ENCODE));

            try (CloseableHttpResponse response = ApiConstants.HTTPCLIENT.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String result = EntityUtils.toString(response.getEntity(), ApiConstants.API_ENCODE);
                    return JSON.parseObject(result, new TypeReference<HttpResult<FaceDetectResult>>(){});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HttpResult<FaceDetectResult> detect(String faceToken) {
        HttpPost httpPost = new HttpPost("https://aip.baidubce.com/rest/2.0/face/v3/detect");
        try {
            httpPost.addHeader("Content-Type","application/json");
            httpPost.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(
                    new BasicNameValuePair("access_token", this.getAccessToken()),
                    new BasicNameValuePair("image", faceToken),
                    new BasicNameValuePair("image_type", ApiConstants.IMAGE_TYPE_FACETOKEN),
                    new BasicNameValuePair("face_field", StringUtil.getUUID())
            ), ApiConstants.API_ENCODE));

            try (CloseableHttpResponse response = ApiConstants.HTTPCLIENT.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String result = EntityUtils.toString(response.getEntity(), ApiConstants.API_ENCODE);
                    return JSON.parseObject(result, new TypeReference<HttpResult<FaceDetectResult>>(){});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String addFace(String groupId, String faceToken, String bizId) {
        HttpPost httpPost = new HttpPost("https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add");
//        HttpPost httpPost = new HttpPost("https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/update");
        try {
            httpPost.addHeader("Content-Type","application/json");
            httpPost.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(
                    new BasicNameValuePair("access_token", this.getAccessToken()),
                    new BasicNameValuePair("group_id", groupId),
                    new BasicNameValuePair("image", faceToken),
                    new BasicNameValuePair("image_type", ApiConstants.IMAGE_TYPE_FACETOKEN),
                    new BasicNameValuePair("user_id", bizId)
            ), ApiConstants.API_ENCODE));

            try (CloseableHttpResponse response = ApiConstants.HTTPCLIENT.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String result = EntityUtils.toString(response.getEntity(), ApiConstants.API_ENCODE);
                    return (String) JSONPath.read(result, "$.result.face_token");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String addFace(String groupId, Path path, String bizId) {
        HttpPost httpPost = new HttpPost("https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add");
        try {
            httpPost.addHeader("Content-Type","application/json");
            httpPost.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(
                    new BasicNameValuePair("access_token", this.getAccessToken()),
                    new BasicNameValuePair("group_id", groupId),
                    new BasicNameValuePair("image", Base64Code.encodeFile(path)),
                    new BasicNameValuePair("image_type", ApiConstants.IMAGE_TYPE_BASE64),
                    new BasicNameValuePair("user_id", bizId)
            ), ApiConstants.API_ENCODE));

            try (CloseableHttpResponse response = ApiConstants.HTTPCLIENT.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String result = EntityUtils.toString(response.getEntity(), ApiConstants.API_ENCODE);
                    return (String) JSONPath.read(result, "$.result.face_token");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getGroupId(String entityId, String groupName) {
        Group group = groupService.getByEntityId(entityId);
        if (group != null) {
            return group.getId();
        }

        if (StringUtils.isBlank(groupName)) {
            return null;
        }

        final String groupId = StringUtil.getUUID();
        Group create = new Group();
        create.setId(groupId);
        create.setEntityId(entityId);
        create.setGroupName(groupName);

        HttpPost httpPost = new HttpPost("https://aip.baidubce.com/rest/2.0/face/v3/faceset/group/add");
        try {
            httpPost.addHeader("Content-Type","application/json");
            httpPost.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(
                    new BasicNameValuePair("access_token", this.getAccessToken()),
                    new BasicNameValuePair("group_id", groupId)
            ), ApiConstants.API_ENCODE));

            System.out.println("Create face group, url:" + httpPost.getURI());

            try (CloseableHttpResponse response = ApiConstants.HTTPCLIENT.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String result = EntityUtils.toString(response.getEntity(), ApiConstants.API_ENCODE);
                    if (Objects.equals(HttpResult.SUCCESS,JSONPath.read(result,"$.error_code"))) {
                        groupService.createGroup(create);
                        return groupId;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean delete(String groupId, String userId, String faceToken) {
        HttpPost httpPost = new HttpPost("https://aip.baidubce.com/rest/2.0/face/v3/faceset/face/delete");
        try {
            httpPost.addHeader("Content-Type","application/json");
            httpPost.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(
                    new BasicNameValuePair("access_token", this.getAccessToken()),
                    new BasicNameValuePair("group_id", groupId),
                    new BasicNameValuePair("user_id", userId),
                    new BasicNameValuePair("face_token", faceToken)
            ), ApiConstants.API_ENCODE));

            try (CloseableHttpResponse response = ApiConstants.HTTPCLIENT.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String result = EntityUtils.toString(response.getEntity(), ApiConstants.API_ENCODE);
                    if (Objects.equals(HttpResult.SUCCESS,JSONPath.read(result,"$.error_code"))) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public HttpResult<FaceSearchResult> search(String groupId, String faceToken) {
        HttpPost httpPost = new HttpPost("https://aip.baidubce.com/rest/2.0/face/v3/search");
        try {
            httpPost.addHeader("Content-Type","application/json");
            httpPost.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(
                    new BasicNameValuePair("access_token", this.getAccessToken()),
                    new BasicNameValuePair("image", faceToken),
                    new BasicNameValuePair("image_type", ApiConstants.IMAGE_TYPE_FACETOKEN),
                    new BasicNameValuePair("group_id_list", groupId),
                    new BasicNameValuePair("quality_control", ApiConstants.BAIDU_HIGH)
            ), ApiConstants.API_ENCODE));

            System.out.println("Search face " + faceToken + " in group " + groupId);

            try (CloseableHttpResponse response = ApiConstants.HTTPCLIENT.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String result = EntityUtils.toString(response.getEntity(), ApiConstants.API_ENCODE);
                    return JSON.parseObject(result, new TypeReference<HttpResult<FaceSearchResult>>(){});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private String getAccessToken() {
        if (StringUtils.isNotBlank(token)) {
            return token;
        }
        HttpPost httpPost = new HttpPost("https://aip.baidubce.com/oauth/2.0/token");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(
                    new BasicNameValuePair("grant_type",ApiConstants.BAIDU_GRANT_TYPE),
                    new BasicNameValuePair("client_id",ApiConstants.BAIDU_API_KEY),
                    new BasicNameValuePair("client_secret",ApiConstants.BAIDU_SECRET_KEY)
            ), ApiConstants.API_ENCODE));

            System.out.println("Request get access token, url:" + httpPost.getURI());

            try (CloseableHttpResponse response = ApiConstants.HTTPCLIENT.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String result = EntityUtils.toString(response.getEntity(), ApiConstants.API_ENCODE);
                    TokenResult tokenResult = JSON.parseObject(result, TokenResult.class);
                    token = tokenResult.getAccess_token();
                    return token;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
