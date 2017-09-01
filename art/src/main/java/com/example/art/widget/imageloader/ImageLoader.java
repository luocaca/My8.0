package com.example.art.widget.imageloader;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 图片加载的注解类   程序中单例存在  使用时通过dagger2
 * 进行注解  使用
 */
@Singleton
public class ImageLoader {
    //策略接口
    private BaseImageLoaderStrategy mStrategy;


    @Inject
    public ImageLoader(BaseImageLoaderStrategy strategy) {
        setLoadImgStrategy(strategy);
    }



    public <T extends ImageConfig> void loadImage(Context context, T config) {
        this.mStrategy.loadImage(context, config);
    }


    public <T extends ImageConfig> void clear(Context context, T config) {
        this.mStrategy.clear(context, config);
    }


    public void setLoadImgStrategy(BaseImageLoaderStrategy strategy) {
        this.mStrategy = strategy;
    }

}
