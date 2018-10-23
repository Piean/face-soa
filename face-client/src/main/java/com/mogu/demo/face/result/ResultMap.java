package com.mogu.demo.face.result;

import java.io.Serializable;

public class ResultMap<T> implements Serializable {
    private static final long serialVersionUID = 5211314L;

    private int code;

    private String errorCode;

    private String errorMsg;

    private T data;

    public ResultMap() {
        this.code = 1;
    }

    public ResultMap(T data) {
        this.code = 1;
        this.data = data;
    }

    public ResultMap(String errorCode, String errorMsg) {
        this.code = 0;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
