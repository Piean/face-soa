package com.mogu.demo.baidu.http;

public class HttpResult<T> {
    public static final int SUCCESS = 0;

    private String error_code;
    private String error_msg;
    private T result;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
