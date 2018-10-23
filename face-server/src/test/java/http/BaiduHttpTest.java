package http;

import com.mogu.demo.FaceServerApplication;
import com.mogu.demo.api.face.ApiConstants;
import com.mogu.demo.api.face.IFaceHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

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
        String group = faceHttpClient.getGroupId(ApiConstants.ENTITY_ID,"MOGU_FACE_GROUP");
        System.out.println(group);
    }
}
