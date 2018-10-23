package com.mogu.demo.api.face;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Created by mogu
 * Date: 2018/10/22
 */
public class ApiConstants {
    public static final CloseableHttpClient HTTPCLIENT = HttpClients.createDefault();

    public static final String ENTITY_ID = "99928635";

    public static final String API_ENCODE = "UTF-8";

    public static final String IMAGE_TYPE_BASE64 = "BASE64";
    public static final String IMAGE_TYPE_FACETOKEN = "FACE_TOKEN";

    public static final String BAIDU_GRANT_TYPE = "client_credentials";
    public static final String BAIDU_APP_ID = "14475594";
    public static final String BAIDU_API_KEY = "kSsCbW1z6OSGpSF4rN0ElbmQ";
    public static final String BAIDU_SECRET_KEY = "2zsDaDsHkenFsFFbBDwS7MqG2jwVeqpC";
}
