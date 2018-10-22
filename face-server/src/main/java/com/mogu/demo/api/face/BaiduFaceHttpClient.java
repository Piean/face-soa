package com.mogu.demo.api.face;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.mogu.demo.baidu.http.GroupAddResult;
import com.mogu.demo.baidu.http.TokenResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by mogu
 * Date: 2018/10/22
 */
@Service
public class BaiduFaceHttpClient implements IFaceHttpClient {
    private String token = null;
    private static final String GROUP_ID = "mogu_face_demo_group";

    @Override
    public <T> T detect() {
        return null;
    }

    @Override
    public String createGroup(String groupName) {
        if (StringUtils.isBlank(token)) {
            this.getAccessToken();
        }

        HttpPost httpPost = new HttpPost("https://aip.baidubce.com/rest/2.0/face/v3/faceset/group/add");
        try {
            httpPost.addHeader("Content-Type","application/json");
            httpPost.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(
                    new BasicNameValuePair("access_token", token),
                    new BasicNameValuePair("group_id", GROUP_ID)
            ), ApiConstants.API_ENCODE));

            System.out.println("Create face group, url:" + httpPost.getURI());

            try (CloseableHttpResponse response = ApiConstants.HTTPCLIENT.execute(httpPost)) {
                String result = EntityUtils.toString(response.getEntity(), ApiConstants.API_ENCODE);
                GroupAddResult groupResult = JSON.parseObject(result, GroupAddResult.class);
                if (GroupAddResult.SUCCESS == groupResult.getError_code())
                return GROUP_ID;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public <T> T search(String faceToken, String groupId) {
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
                String result = EntityUtils.toString(response.getEntity(), ApiConstants.API_ENCODE);
                TokenResult tokenResult = JSON.parseObject(result, TokenResult.class);
                token = tokenResult.getAccess_token();
                return token;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
