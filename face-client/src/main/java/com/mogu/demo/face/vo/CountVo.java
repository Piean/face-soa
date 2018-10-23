package com.mogu.demo.face.vo;

import com.mogu.demo.face.bo.Face;
import com.mogu.demo.face.bo.Member;

import java.io.Serializable;

public class CountVo implements Serializable {
    private static final long serialVersionUID = 110L;

    private int sum;
    private int customerNum;
    private int memberNum;
    private int manNum;
    private int womanNum;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getCustomerNum() {
        return customerNum;
    }

    public void setCustomerNum(int customerNum) {
        this.customerNum = customerNum;
    }

    public int getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(int memberNum) {
        this.memberNum = memberNum;
    }

    public int getManNum() {
        return manNum;
    }

    public void setManNum(int manNum) {
        this.manNum = manNum;
    }

    public int getWomanNum() {
        return womanNum;
    }

    public void setWomanNum(int womanNum) {
        this.womanNum = womanNum;
    }

    public synchronized void setCount(final Member member, final Face face) {
        sum++;

        if (Member.MemberType.FIRST.getId() == member.getMemberType() || Member.MemberType.AGAIN.getId() == member.getMemberType()) {
            customerNum++;
        } else if (Member.MemberType.MEMBER.getId() == member.getMemberType()) {
            memberNum++;
        }

        if (Face.Sex.MAN.getId() == face.getSex()) {
            manNum++;
        } else if (Face.Sex.WOMAN.getId() == face.getSex()) {
            womanNum++;
        }
    }
}
