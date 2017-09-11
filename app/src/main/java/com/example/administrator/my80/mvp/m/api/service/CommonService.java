package com.example.administrator.my80.mvp.m.api.service;


import com.example.administrator.my80.mvp.m.entity.base.BaseJson;
import com.example.administrator.my80.mvp.m.entity.common.UserInfo;
import com.example.administrator.my80.mvp.m.entity.index.Index;
import com.example.administrator.my80.mvp.m.entity.index.PageChild;
import com.example.administrator.my80.mvp.m.entity.query.QueryPage;
import com.example.administrator.my80.mvp.m.entity.shop.Data;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public interface CommonService {

    /*获取首页数据*/
    @POST("index")
    Observable<BaseJson<Index>> getIndex();

    /*获取商城界面  分页信息*/
    @POST("seedling/list")
//    Observable<BaseJson<Data<Purchase>>> getPageList(@Query("page") String page, @Query("pageSize") String string);
    Observable<BaseJson<Data<List<PageChild>>>> getPageList(@Body QueryPage page);


    /*获取 收藏夹  分页信息*/
    @POST("admin/collect/listSeedling")
//    Observable<BaseJson<Data<Purchase>>> getPageList(@Query("page") String page, @Query("pageSize") String string);
    Observable<BaseJson<Data<List<PageChild>>>> getFavorList(@Query("pageIndex") int page, @Query("pageSize") int size);


    /*获取 个人信息*/
    @POST("admin/user/getInfo")
    Observable<BaseJson<UserInfo>> getUserInfo();
}
