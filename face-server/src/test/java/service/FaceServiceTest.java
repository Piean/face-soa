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
        Path path = Paths.get("C:\\Projects\\face-soa\\face-server\\src\\main\\resources\\image\\酥糖-13758270401.jpeg");
        String faceToken = faceHttpClient.addFace(groupId, path, faceId);
        TwodfireMember dfireMember = twodfireMemberService.queryInfoByMobile("13758270401", "+86").getModel();
        Face face = new Face();
        face.setId(faceId);
        face.setGroupId(groupId);
        face.setSex(dfireMember.getSex());
        face.setAge(21);
        face.setArriveTime(System.currentTimeMillis());
        face.setFaceToken(faceToken);
        face.setFaceUrl(Base64Code.encodeImage(path));
        face = faceService.insertFace(face);

        Member member = new Member();
        member.setId(StringUtil.getUUID());
        member.setMemberId(dfireMember.getId());
        member.setFaceId(faceId);
        member.setName(dfireMember.getName());
        member.setPhone(dfireMember.getMobile());
        member.setMemberType(Member.MemberType.MEMBER.getId());
        member.setTag(JSON.toJSONString(Lists.newArrayList("成功人士","懂生活")));
        member.setLove(JSON.toJSONString(Lists.newArrayList("爱吃辣")));
        member.setHate(JSON.toJSONString(Lists.newArrayList("不吃香菜")));
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
    public void getTwoDfireMember() {
        Result<TwodfireMember> memberResult = twodfireMemberService.queryInfoByMobile("18610755717", "+86");
        System.out.println(memberResult);
    }
}
