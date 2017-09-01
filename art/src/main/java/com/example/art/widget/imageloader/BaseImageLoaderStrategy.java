package com.example.art.widget.imageloader;

import android.content.Context;

/**
 * 罗擦擦
 * Strategy  ---- english  策略
 * <p>
 * 规范 下来接口  ，，方便 开发者 更换加载图片框架
 */

public interface BaseImageLoaderStrategy<T extends ImageConfig> {
    void loadImage(Context ctx, T config);

    void clear(Context ctx, T config);
}
