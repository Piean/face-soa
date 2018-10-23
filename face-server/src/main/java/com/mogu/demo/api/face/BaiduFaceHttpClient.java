package com.mogu.demo.api.face;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.google.common.collect.Lists;
import com.mogu.demo.baidu.http.FaceSearchResult;
import com.mogu.demo.baidu.http.GroupAddResult;
import com.mogu.demo.baidu.http.TokenResult;
import com.mogu.demo.face.bo.Group;
import com.mogu.demo.service.IGroupService;
import com.mogu.demo.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import scala.util.parsing.combinator.testing.Str;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by mogu
 * Date: 2018/10/22
 */
@Service
public class BaiduFaceHttpClient implements IFaceHttpClient {
    @Resource
    private IGroupService groupService;

    private String token = null;

    @Override
    public <T> T detect(String base64Code) {
        return null;
    }

    @Override
    public String addFace(String entityId, String memberId, String base64Code) {
        HttpPost httpPost = new HttpPost("https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add");
        try {
            httpPost.addHeader("Content-Type","application/json");
            httpPost.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(
                    new BasicNameValuePair("access_token", this.getAccessToken()),
                    new BasicNameValuePair("group_id", this.getGroupId(entityId)),
                    new BasicNameValuePair("image", base64Code),
                    new BasicNameValuePair("image_type", ApiConstants.IMAGE_TYPE_BASE64),
                    new BasicNameValuePair("user_id", memberId)
            ), ApiConstants.API_ENCODE));

            try (CloseableHttpResponse response = ApiConstants.HTTPCLIENT.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String result = EntityUtils.toString(response.getEntity(), ApiConstants.API_ENCODE);
                    return (String) JSONPath.read(result, "$.face_token");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getGroupId(String entityId) {
        Group group = groupService.getByEntityId(entityId);
        if (group != null) {
            return group.getGroupId();
        }

        final String groupId = StringUtil.getUUID();
        Group create = new Group();
        create.setEntityId(entityId);
        create.setGroupId(groupId);
        groupService.createGroup(create);

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
                    GroupAddResult groupResult = JSON.parseObject(result, GroupAddResult.class);
                    if (GroupAddResult.SUCCESS == groupResult.getError_code())
                        return groupId;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public FaceSearchResult search(String faceToken, String groupId) {
        HttpPost httpPost = new HttpPost("https://aip.baidubce.com/rest/2.0/face/v3/search");
        try {
            httpPost.addHeader("Content-Type","application/json");
            httpPost.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(
                    new BasicNameValuePair("access_token", this.getAccessToken()),
                    new BasicNameValuePair("image", faceToken),
                    new BasicNameValuePair("image_type", ApiConstants.IMAGE_TYPE_FACETOKEN),
                    new BasicNameValuePair("group_id_list", groupId)
            ), ApiConstants.API_ENCODE));

            System.out.println("Search face " + faceToken + " in " + groupId);

            try (CloseableHttpResponse response = ApiConstants.HTTPCLIENT.execute(httpPost)) {
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String result = EntityUtils.toString(response.getEntity(), ApiConstants.API_ENCODE);
                    return JSON.parseObject(result, FaceSearchResult.class);
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
