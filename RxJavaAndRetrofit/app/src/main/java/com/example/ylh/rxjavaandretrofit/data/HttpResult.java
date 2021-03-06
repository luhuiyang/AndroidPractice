package com.example.ylh.rxjavaandretrofit.data;

/**
 * Created by scorpio on 15/12/25.
 */
public class HttpResult <T> {

    /**
     * resultCode : 0
     * resultMessage : 成功
     * data : {}
     */

    private int resultCode;
    private String resultMessage;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }
}
