package com.example.administrator.my80.mvp.m.api;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public interface Api {

     /*发布时主要版本号   后面需要加 / */
     String apiVersion = "3.0.4/";//发布的时候修改 api 版本号

     String APP_DOMAIN = "http://test.api.hmeg.cn/" + apiVersion;
     String RequestSuccess = "0";
}
