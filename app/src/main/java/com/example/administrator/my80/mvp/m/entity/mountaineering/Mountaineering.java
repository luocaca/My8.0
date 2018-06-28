package com.example.administrator.my80.mvp.m.entity.mountaineering;

import java.io.Serializable;
import java.util.List;

/**
 * 发布活动 与 获取  首页活动的 对象
 */

public class Mountaineering implements Serializable {


    /**
     * code : 1
     * msg : 查询成功
     * data : {"id":7,"listImagesBanner":[{"url":"http://image.hmeg.cn/upload/image/201805/252aefefe9d2409d8b1c99e4a0b983d3.jpeg"},{"url":"http://image.hmeg.cn/upload/image/201805/7d2a44cda36c441fab629aa086284b72.jpeg"},{"url":"http://image.hmeg.cn/upload/image/201805/47a33c4413794f2d99e9914c5f81755c.jpeg"}],"listImagesMore":[{"url":"http://image.hmeg.cn/upload/image/201805/252aefefe9d2409d8b1c99e4a0b983d3.jpeg"},{"url":"http://image.hmeg.cn/upload/image/201805/7d2a44cda36c441fab629aa086284b72.jpeg"},{"url":"http://image.hmeg.cn/upload/image/201805/47a33c4413794f2d99e9914c5f81755c.jpeg"}],"createDate":"Jun 26, 2018 12:00:00 AM","title":"55555555","desc":"本次参赛者各得奖杯一个","userJoin":"10/30","leaderName":"二傻","loaction":"请选择","lineFeature":"路由 中专 嫖娼","star":2,"specialOffers":"优惠100块"}
     */

    public String code;
    public String msg;
    public DataBean data;

    public static class DataBean implements Serializable {
        /**
         * id : 7
         * listImagesBanner : [{"url":"http://image.hmeg.cn/upload/image/201805/252aefefe9d2409d8b1c99e4a0b983d3.jpeg"},{"url":"http://image.hmeg.cn/upload/image/201805/7d2a44cda36c441fab629aa086284b72.jpeg"},{"url":"http://image.hmeg.cn/upload/image/201805/47a33c4413794f2d99e9914c5f81755c.jpeg"}]
         * listImagesMore : [{"url":"http://image.hmeg.cn/upload/image/201805/252aefefe9d2409d8b1c99e4a0b983d3.jpeg"},{"url":"http://image.hmeg.cn/upload/image/201805/7d2a44cda36c441fab629aa086284b72.jpeg"},{"url":"http://image.hmeg.cn/upload/image/201805/47a33c4413794f2d99e9914c5f81755c.jpeg"}]
         * createDate : Jun 26, 2018 12:00:00 AM
         * title : 55555555
         * desc : 本次参赛者各得奖杯一个
         * userJoin : 10/30
         * leaderName : 二傻
         * loaction : 请选择
         * lineFeature : 路由 中专 嫖娼
         * star : 2
         * specialOffers : 优惠100块
         */

        public int id;
        public String createDate;
        public String title;
        public String desc;
        public String userJoin;
        public String leaderName;
        public String loaction;
        public String lineFeature;
        public int star;
        public int price = 97;
        public String specialOffers;
        public List<ImagesBean> listImagesBanner;
        public List<ImagesBean> listImagesMore;


    }
}
