package com.example.administrator.my80.http;

/**
 * 结果回调接口
 */

public interface AjaxCallBack {

    void onSucceed(String json);


    void onFailed(Throwable throwable, int errorCode, String errorMsg);


    void onCancle();



}