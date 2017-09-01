package com.example.administrator.my80.mvp.m.api.service;

import com.example.administrator.my80.mvp.m.entity.BaseJson;
import com.example.administrator.my80.mvp.m.entity.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public interface UserInfoService {

    /**
     * if (TextUtils.isEmpty(userId)) {
     Log.e("userId", "getUserInfo: 为空 无法获取数据");
     return;
     }
     FinalHttp finalHttp = new FinalHttp();
     GetServerUrl.addHeaders(finalHttp, false);
     finalHttp.addHeader("authc", userId);
     * @return
     */
    /**
     * chain.request().newBuilder()
     * .addHeader("token", HeadManager.getKeyStr(System.currentTimeMillis()))
     * .addHeader("authc", "3378ca2d-b09b-411b-a3d0-28766e314685")
     * .addHeader("version", "3.0.4")
     * .addHeader("deviceId", "deviceId")
     * .addHeader("type", "type")
     * .addHeader("sdkVersion", "6.0")
     * .addHeader("deviceType", "android")
     * .addHeader("manufacturer", "honor")
     * .build();
     *
     * @param authc
     * @return
     */


//    @Headers({
//            "X-Foo: Bar",
//            "X-Ping: Pong"
//    })

//    @Headers({
//            "token:d061f22d979dfb73c355cd7439e48f2ad455f58b95f9de0d",
//            "authc:3378ca2d-b09b-411b-a3d0-28766e314685",
//            "version:3.0.4"
//    })

    @POST("admin/user/getInfo")
    Observable<BaseJson<UserInfo>> getUserInfo();

//        .addQueryParameter("token",HeadManager.getKeyStr(System.currentTimeMillis()))
////                                .addQueryParameter("authc", "3378ca2d-b09b-411b-a3d0-28766e314685")


}
