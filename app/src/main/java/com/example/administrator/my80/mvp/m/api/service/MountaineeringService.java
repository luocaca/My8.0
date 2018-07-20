package com.example.administrator.my80.mvp.m.api.service;

import com.example.administrator.my80.mvp.m.entity.base.BaseJson;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * 提交 发布内容能够接口
 */

public interface MountaineeringService {

    /**
     * String[] strings = new String[]{
     * "http://www.luocaca.cn/hello-ssm/mountaineering/add",
     * getInputText(title),
     * getInputText(top_banner),
     * getInputText(loaction),
     * getInputText(lineFeature),
     * getInputText(star),
     * getInputText(leaderName),
     * getInputText(userJoin),
     * getInputText(specialOffers),
     * getInputText(desc),
     * getInputText(close_date),
     * getInputText(bottom_banner),
     *
     * @param url
     * @param title
     * @param imagesBanner
     * @param loaction
     * @param lineFeature
     * @param star
     * @param leaderName
     * @param userJoin
     * @param specialOffers
     * @param desc
     * @param imagesMore
     * @return
     */
    @GET
    Observable<BaseJson<String>> commitMsg(@Url String url,
                                           @Query("id") String changeId,
                                           @Query("title") String title,
                                           @Query("price") String price,
                                           @Query("imagesBanner") String imagesBanner,
                                           @Query("loaction") String loaction,
                                           @Query("lineFeature") String lineFeature,
                                           @Query("star") String star,
                                           @Query("leaderName") String leaderName,
                                           @Query("userJoin") String userJoin,
                                           @Query("specialOffers") String specialOffers,
                                           @Query("desc") String desc,
                                           @Query("closeDate") String closeDate,
                                           @Query("imagesMore") String imagesMore

    );


}
