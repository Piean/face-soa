package com.mogu.demo.face.vo;

import com.mogu.demo.face.bo.Face;
import com.mogu.demo.face.bo.Member;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.List;

public class MemberVo implements Serializable {
    private static final long serialVersionUID = 110L;

    private String id;
    private String memberId;
    private String faceCode;
    private int sex;
    private int age;
    private String name;
    private String phone;
    private int memberType;
    private int cardType;
    private long cardRemain;
    private long arriveTime;
    private int counter;
    private List<String> tag;
    private List<String> love;
    private List<String> hate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getFaceCode() {
        return faceCode;
    }

    public void setFaceCode(String faceCode) {
        this.faceCode = faceCode;
    }

    public String getSex() {
        return Face.Sex.getName(this.sex);
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age + "岁";
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMemberType() {
        return Member.MemberType.getName(this.memberType);
    }

    public void setMemberType(int memberType) {
        this.memberType = memberType;
    }

    public String getCardType() {
        return Member.CardType.getName(this.cardType);
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    public String getCardRemain() {
        return (cardRemain / 100) + "元";
    }

    public void setCardRemain(long cardRemain) {
        this.cardRemain = cardRemain;
    }

    public String getArriveTime() {
        return DateFormatUtils.format(this.arriveTime,"yyyy-MM-dd HH:mm:ss");
    }

    public void setArriveTime(long arriveTime) {
        this.arriveTime = arriveTime;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public List<String> getLove() {
        return love;
    }

    public void setLove(List<String> love) {
        this.love = love;
    }

    public List<String> getHate() {
        return hate;
    }

    public void setHate(List<String> hate) {
        this.hate = hate;
    }
}
