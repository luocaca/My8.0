package com.example.administrator.my80.mvp.m.entity;

import com.example.administrator.my80.mvp.m.api.Api;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class BaseJson<T> implements Serializable {


//    {"code":"403","msg":"非法请求","version":"v3.0.4"}

    private T data;
    private String code;
    private String msg;
    private String version;


    public T getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getVersion() {
        return version;
    }

    /**
     * 请求是否成功
     */
    public boolean isSucceed() {
        return code.equals(Api.RequestSuccess);
    }


}
