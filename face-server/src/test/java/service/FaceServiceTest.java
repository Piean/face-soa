package service;

import com.alibaba.fastjson.JSON;
import com.dfire.soa.member.bean.TwodfireMember;
import com.dfire.soa.member.service.ITwodfireMemberService;
import com.google.common.collect.Lists;
import com.mogu.demo.FaceServerApplication;
import com.mogu.demo.api.face.ApiConstants;
import com.mogu.demo.api.face.IFaceHttpClient;
import com.mogu.demo.face.bo.Face;
import com.mogu.demo.face.bo.Member;
import com.mogu.demo.service.IFaceService;
import com.mogu.demo.service.IMemberService;
import com.mogu.demo.utils.Base64Code;
import com.mogu.demo.utils.StringUtil;
import com.twodfire.share.result.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by mogu
 * Date: 2018/10/22
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FaceServerApplication.class)
public class FaceServiceTest {
    @Resource
    private ITwodfireMemberService twodfireMemberService;

    @Resource
    private IFaceHttpClient faceHttpClient;
    @Resource
    private IFaceService faceService;
    @Resource
    private IMemberService memberService;

    private final String groupId = "ac1fd1c4ea714fd8901905e19f8a7806";

    @Test
    public void createFaceTest() {
        String faceId = StringUtil.getUUID();
        Path path = Paths.get("C:\\Projects\\face-soa\\face-server\\src\\main\\resources\\image\\桑果-18768167804.jpeg");
        String faceToken = faceHttpClient.addFace(groupId, path, faceId);
//        TwodfireMember dfireMember = twodfireMemberService.queryInfoByMobile("13758270401", "+86").getModel();
        Face face = new Face();
        face.setId(faceId);
        face.setGroupId(groupId);
        face.setSex(Face.Sex.MAN.getId());
        face.setAge(24);
        face.setArriveTime(System.currentTimeMillis());
        face.setFaceToken(faceToken);
        face.setFaceUrl(Base64Code.encodeImage(path));
        face = faceService.insertFace(face);

        Member member = new Member();
        member.setId(StringUtil.getUUID());
        member.setMemberId("");
        member.setFaceId(faceId);
        member.setName("");
        member.setPhone("");
        member.setMemberType(Member.MemberType.FIRST.getId());
        member.setTag(JSON.toJSONString(Lists.newArrayList()));
        member.setLove(JSON.toJSONString(Lists.newArrayList()));
        member.setHate(JSON.toJSONString(Lists.newArrayList()));
        memberService.insertMember(member);

        System.out.println(false);
    }

    @Test
    public void createMemberTest() {
        Member member = new Member();
        member = memberService.insertMember(member);
        System.out.println(member);
    }

    @Test
    public void updateFaceTest() {
        Face face = new Face();
        face.setId("f1dd0de356534908955ee043c54522f9");
        face.setArriveTime(System.currentTimeMillis());
        face.setFaceUrl(Base64Code.encodeImage(Paths.get("C:\\Projects\\face-soa\\face-server\\src\\main\\resources\\image\\枳橙-18557543822.jpeg")));
        System.out.println(faceService.updateFace(face));;
    }

    @Test
    public void updateMemberTest() {
        Member member = new Member();
        member.setId("d0c80206e0b54a6ab96bb8c4b9536827");
        member.setTag(JSON.toJSONString(Lists.newArrayList("金领人群","帅哥","高价值顾客","复购率较高","老余杭商圈")));
        member.setLove(JSON.toJSONString(Lists.newArrayList("鲈鱼","牛肉","甜食")));
        member.setHate(JSON.toJSONString(Lists.newArrayList("不吃蒜")));
        memberService.updateMember(member);

        member.setId("dd1191d2243143a198f3d4b7c8e07baa");
        member.setTag(JSON.toJSONString(Lists.newArrayList("二维火商圈","美少女","喜欢甜品","白领")));
        member.setLove(JSON.toJSONString(Lists.newArrayList("冰淇淋","无糖","酸辣")));
        member.setHate(JSON.toJSONString(Lists.newArrayList("不要香菜")));
        memberService.updateMember(member);

        member.setId("1b72efe0c090480089cc67606f75c9e7");
        member.setTag(JSON.toJSONString(Lists.newArrayList()));
        member.setLove(JSON.toJSONString(Lists.newArrayList()));
        member.setHate(JSON.toJSONString(Lists.newArrayList()));
        memberService.updateMember(member);

        member.setId("4a9c6c9e2f114ae1ba57cebac9624657");
        member.setTag(JSON.toJSONString(Lists.newArrayList()));
        member.setLove(JSON.toJSONString(Lists.newArrayList()));
        member.setHate(JSON.toJSONString(Lists.newArrayList()));
        memberService.updateMember(member);
    }

    @Test
    public void getTwoDfireMember() {
        Result<TwodfireMember> memberResult = twodfireMemberService.queryInfoByMobile("18610755717", "+86");
        System.out.println(memberResult);
    }
}
