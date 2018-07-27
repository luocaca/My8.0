package com.example.administrator.my80.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.my80.R;
import com.example.administrator.my80.mvp.m.entity.mountaineering.ImagesBean;
import com.lzy.ninegrid.NineGridView;
import com.youth.banner.loader.ImageLoader;

/**
 * 九宫格   图片加载接口  实现
 *
 * https://github.com/jeasonlzy/NineGridView
 */


// implements NineGridView.ImageLoader
public class GlideImageLoader extends ImageLoader implements NineGridView.ImageLoader{
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {


        String realUrl = "";
        if (path instanceof String) {
            realUrl = path.toString();
        }

        if (path instanceof ImagesBean) {
            realUrl = ((ImagesBean) path).url;
        }


//        RequestBuilder requestBuilder  = new RequestBuilder();

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.test)
                .error(R.mipmap.test)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context.getApplicationContext())
                .load(realUrl)
                .apply(options)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .centerCrop()
//                .placeholder(R.mipmap.test)
                .into(imageView);
    }

    @Override
    public void onDisplayImage(Context context, ImageView imageView, String realUrl) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.test)
                .error(R.mipmap.test)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context.getApplicationContext())
                .load(realUrl)
                .apply(options)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .centerCrop()
//                .placeholder(R.mipmap.test)
                .into(imageView);
    }

    @Override
    public Bitmap getCacheImage(String s) {
        return null;
    }
}
