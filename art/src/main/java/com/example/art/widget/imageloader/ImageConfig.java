package com.example.art.widget.imageloader;

import android.widget.ImageView;

/**
 * 这里是图片 加载配置信息的基类，，，，可以定义一些所有图片加载框架 都可以使用的参数
 */

public class ImageConfig {

    protected String url;//图片资源 的加载地址
    protected ImageView imageView;//可能是 imageview
    protected int placeholder;//占位符
    protected int errorPic;//错误时显示的图片资源

    public String getUrl() {
        return url;
    }


    public ImageView getImageView() {
        return imageView;
    }


    public int getPlaceholder() {
        return placeholder;
    }

    public int getErrorPic() {
        return errorPic;
    }

}
