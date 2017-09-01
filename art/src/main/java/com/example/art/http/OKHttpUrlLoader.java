package com.example.art.http;

import android.content.Context;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;

import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * xx
 * <p>
 * a simple model loaer for fetching media over http/https using OKHtttp ,
 */

public class OKHttpUrlLoader implements ModelLoader<GlideUrl, InputStream> {

    private final Call.Factory mClient;

    public OKHttpUrlLoader(Call.Factory client) {
        this.mClient = client;
    }


    @Override
    public DataFetcher<InputStream> getResourceFetcher(GlideUrl model, int width, int height) {

        return new OKHttpStreamFetcher(mClient, model);
    }


    /**
     * the default factory for {@link OKHttpUrlLoader}
     */

    public static class Factory implements ModelLoaderFactory<GlideUrl, InputStream> {

        private static volatile Call.Factory internalClient;
        private Call.Factory client;

        public Factory(Call.Factory client) {
            this.client = client;
        }

        private static Call.Factory getInternalClient() {
            if (internalClient == null) {//为null 的时候  线程锁住，执行
                synchronized (Factory.class) {
                    if (internalClient == null) {
                        internalClient = new OkHttpClient();
                    }
                }
            }
            return internalClient;
        }

        @Override
        public ModelLoader<GlideUrl, InputStream> build(Context context, GenericLoaderFactory factories) {
            return new OKHttpUrlLoader(client);
        }

        @Override
        public void teardown() {
            // do nothing , this instance doesn't own the client
        }
    }


}
