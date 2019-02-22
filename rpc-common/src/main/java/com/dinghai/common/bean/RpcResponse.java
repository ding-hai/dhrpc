package com.dinghai.common.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("error")
public class RpcResponse {
    private String responseId;
    private Object result;
    private Throwable cause;

    public boolean isError() {
        return cause != null;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return "RpcResponse{" +
                "responseId='" + responseId + '\'' +
                ", result=" + result +
                ", cause=" + cause +
                '}';
    }
}
