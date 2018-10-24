package http;

import com.mogu.demo.FaceServerApplication;
import com.mogu.demo.api.face.ApiConstants;
import com.mogu.demo.api.face.IFaceHttpClient;
import com.mogu.demo.baidu.http.FaceDetectResult;
import com.mogu.demo.baidu.http.FaceSearchResult;
import com.mogu.demo.baidu.http.HttpResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.nio.file.Paths;

/**
 * Created by mogu
 * Date: 2018/10/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FaceServerApplication.class)
public class BaiduHttpTest {
    @Resource
    private IFaceHttpClient faceHttpClient;

    @Test
    public void createGroupTest() {
        String group = faceHttpClient.getGroupId(ApiConstants.ENTITY_ID,"HACK_DEMO_FACE_GROUP");
        System.out.println(group);
    }

    @Test
    public void detectTest() {
        HttpResult<FaceDetectResult> detect = faceHttpClient.detect(Paths.get("C:\\Projects\\face-soa\\face-server\\src\\main\\resources\\image\\枳橙-18557543822.jpeg"));
        System.out.println(detect);
    }

    @Test
    public void searchTest() {
//        HttpResult<FaceSearchResult> s1 = faceHttpClient.search("ac1fd1c4ea714fd8901905e19f8a7806","be8ee58b347d1a0066c4828067adcafe");
//        HttpResult<FaceSearchResult> s2 = faceHttpClient.search("ac1fd1c4ea714fd8901905e19f8a7806","379d80d6b750a284797f7e9d661ecc6c");
//        HttpResult<FaceSearchResult> s3 = faceHttpClient.search("ac1fd1c4ea714fd8901905e19f8a7806","02b531c5d942fbe07457e9b5718540b5");
        HttpResult<FaceSearchResult> s4 = faceHttpClient.search("ac1fd1c4ea714fd8901905e19f8a7806","552134174780edbf1b20a1ccf975073d");
        System.out.println("");
    }

    @Test
    public void update() {
        faceHttpClient.addFace("ac1fd1c4ea714fd8901905e19f8a7806","552134174780edbf1b20a1ccf975073d","f1dd0de356534908955ee043c54522f9");
        System.out.println(true);
    }
}
