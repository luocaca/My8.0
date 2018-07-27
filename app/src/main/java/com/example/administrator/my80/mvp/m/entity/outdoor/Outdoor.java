package com.example.administrator.my80.mvp.m.entity.outdoor;

/**
 * 户外知识 文章对象  用于解析 搜狗微信的 文章 title imageUrl 时间
 */

public class Outdoor {

    public String title;
    public String banner;
    public String from;
    public String time;
    public String content;
    public String href ;


    @Override
    public String toString() {
        return "Outdoor{" +
                "title='" + title + '\'' +
                ", banner='" + banner + '\'' +
                ", from='" + from + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
