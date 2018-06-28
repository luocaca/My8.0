package com.example.administrator.my80.mvp.m.api.rest.service;

import java.util.Map;

import cn.lemon.resthttp.request.callback.HttpCallback;
import cn.lemon.resthttp.request.callback.HttpsCallback;
import cn.lemon.resthttp.request.http.HttpRequest;


/**
 * Created by Administrator on 2018/6/27 0027.
 */

public class Lately extends HttpRequest {




    public Lately() {
        super();
    }

    @Override
    public void addHeader(Map<String, String> header) {
        super.addHeader(header);
        // 添加头部  多个
    }

    @Override
    public void addHeader(String key, String value) {
        super.addHeader(key, value);
        //添加 单个头部
    }

    @Override
    public void get(String url, HttpCallback callBack) {
        super.get(url, callBack);
    }

    @Override
    public void post(String url, Map<String, String> params, HttpCallback callBack) {
        super.post(url, params, callBack);
    }

    @Override
    public void cancelAllRequest() {
        super.cancelAllRequest();
    }

    @Override
    public void cancelRequest(String httpUrl, Map<String, String> params) {
        super.cancelRequest(httpUrl, params);
    }

    @Override
    public void cancelRequest(String httpUrl) {
        super.cancelRequest(httpUrl);
    }

    @Override
    public void get(String url, HttpsCallback callBack) {
        super.get(url, callBack);
    }

    @Override
    public void post(String url, Map<String, String> params, HttpsCallback callBack) {
        super.post(url, params, callBack);
    }


    //    public void getLately();


}
