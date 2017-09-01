package com.example.art.widget.imageloader.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.example.art.base.App;
import com.example.art.di.component.AppComponent;
import com.example.art.http.OKHttpUrlLoader;
import com.example.art.utils.DataHelper;

import java.io.File;
import java.io.InputStream;

/**
 * glid 图片加载库  的全局配置信息
 */

public class GlidConfiguration implements GlideModule {

    public static final int IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024; //  图片缓存文件最大值为 100 M


    /**
     * 引用选项
     *
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder
                .setDiskCache(new DiskCache.Factory() {
                    @Override
                    public DiskCache build() {
                        AppComponent appComponent = ((App) context.getApplicationContext()).getAppComponent();

                        return DiskLruCacheWrapper.get(DataHelper.makeDirs(new File(appComponent.cacheFile(), "Glide")), IMAGE_DISK_CACHE_MAX_SIZE);

                    }
                });

        //内存计算器
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();//获取默认 内存缓存大小
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize(); // 获取图片 池的空间大小

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));


    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        //Glide默认使用HttpURLConnection做网络请求,在这切换成okhttp请求
        AppComponent appComponent  =((App) context.getApplicationContext()).getAppComponent();
        glide.register(GlideUrl.class , InputStream.class , new OKHttpUrlLoader.Factory(appComponent.okHttpClient()) );

    }
}
