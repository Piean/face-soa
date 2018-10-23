package com.mogu.demo.face.bo.base;

import java.io.Serializable;

public class Base implements Serializable {
    private static final long serialVersionUID = 5201314L;

    private String id;
    private long opTime;
    private long createTime;
    private int lastVer;
    private int isValid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getOpTime() {
        return opTime;
    }

    public void setOpTime(long opTime) {
        this.opTime = opTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getLastVer() {
        return lastVer;
    }

    public void setLastVer(int lastVer) {
        this.lastVer = lastVer;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }
}
