package com.example.art.widget.imageloader.glide;

import android.os.Build;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.Target;
import com.example.art.widget.imageloader.ImageConfig;

/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class GlideImageConfig extends ImageConfig {
    private int cacheStrategy;//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
    private BitmapTransformation transformation;//glide用它来改变图形的形状
    private Target[] targets;
    private ImageView[] imageViews;
    private boolean isClearMemory;//清理内存缓存
    private boolean isClearDiskCache;//清理本地缓存

    private Build mBuild ;
    private GlideImageConfig(Builder builder){

        this.url = builder.url;
        this.imageView = builder.imageView;
        this.placeholder = builder.placeholder;
        this.errorPic = builder.errorPic;
        this.cacheStrategy = builder.cacheStrategy;
        this.transformation = builder.transformation;
        this.targets = builder.targets;
        this.imageViews = builder.imageViews;
        this.isClearMemory = builder.isClearMemory;
        this.isClearDiskCache = builder.isClearDiskCache;
    }



    public int getCacheStrategy() {
        return cacheStrategy;
    }


    public BitmapTransformation getTransformation() {
        return transformation;
    }

    public Target[] getTargets() {
        return targets;
    }

    public ImageView[] getImageViews() {
        return imageViews;
    }

    public boolean isClearMemory() {
        return isClearMemory;
    }

    public boolean isClearDiskCache() {
        return isClearDiskCache;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String url;

        private ImageView imageView;
        private int placeholder;
        private int errorPic;
        /**
         * 0 cacheStrategy  {@link com.bumptech.glide.load.engine.DiskCacheStrategy.ALL}
         * 1  cacheStrategy  {@link  com.bumptech.glide.load.engine.DiskCacheStrategy.NONE}
         * 2 cacheStrategy  {@link com.bumptech.glide.load.engine.DiskCacheStrategy.SOURCE}
         * 3  cacheStrategy  {@link com.bumptech.glide.load.engine.DiskCacheStrategy.RESULT}
         *
         * ↑↑↑↑↑↑↑↑
         */
        private int cacheStrategy;
        private BitmapTransformation transformation;//glide 用它来改变图形的形状，
        private Target[] targets;
        private ImageView[] imageViews;
        private boolean isClearMemory; //清理内存缓存
        private boolean isClearDiskCache;//清理本地缓存


        public Builder() {

        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder placeholder(int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Builder errorPic(int errorPic) {
            this.errorPic = errorPic;
            return this;
        }

        public Builder imageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder cacheStrategy(int cacheStrategy) {
            this.cacheStrategy = cacheStrategy;
            return this;
        }

        public Builder transformation(BitmapTransformation transformation) {
            this.transformation = transformation;
            return this;
        }

        public Builder targets(Target... targets) {
            this.targets = targets;
            return this;
        }

        public Builder imageViews(ImageView... imageViews) {
            this.imageViews = imageViews;
            return this;
        }

        public Builder isClearMemory(boolean isClearMemory) {
            this.isClearMemory = isClearMemory;
            return this;
        }

        public Builder isClearDiskCache(boolean isClearDiskCache) {
            this.isClearDiskCache = isClearDiskCache;
            return this;
        }


        public GlideImageConfig build() {
            return new GlideImageConfig(this);
        }

    }


}
