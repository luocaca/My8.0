package com.example.art.di.Module;

import android.app.Application;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.example.art.http.BaseUrl;
import com.example.art.http.GlobalHttpHandler;
import com.example.art.http.RequestInterceptor;
import com.example.art.utils.DataHelper;
import com.example.art.widget.imageloader.BaseImageLoaderStrategy;
import com.example.art.widget.imageloader.glide.GlideImageLoaderStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * Created by luocaca on 2017/8/7 0007.
 */

@Module
public class GlobalConfigModule {


    private HttpUrl mApiUrl;//--
    private BaseUrl mBaseUrl;//--
    private BaseImageLoaderStrategy mLoaderStrategy;
    private GlobalHttpHandler mHandler;
    private List<Interceptor> mInterceptors;
    private ResponseErrorListener mErrorListener;
    private File mCacheFile;
    private ClientModule.RetrofitConfiguration mRetrofitConfiguration;
    private ClientModule.OkhttpConfiguration mOkhttpConfiguration;//--
    private ClientModule.RxCacheConfiguration mRxCacheConfiguration;
    private AppModule.GsonConfiguration mGsonConfiguration;//--
    private RequestInterceptor.Level mPrintHttpLogLevel;

    public GlobalConfigModule(Builder builder) {
        this.mApiUrl = builder.apiUrl;
        this.mBaseUrl = builder.baseUrl;
        this.mLoaderStrategy = builder.loaderStrategy;
        this.mHandler = builder.handler;
        this.mInterceptors = builder.interceptors;
        this.mErrorListener = builder.responseErrorListener;
        this.mCacheFile = builder.cacheFile;
        this.mRetrofitConfiguration = builder.retrofitConfiguration;
        this.mOkhttpConfiguration = builder.okhttpConfiguration;
        this.mRxCacheConfiguration = builder.rxCacheConfiguration;
        this.mGsonConfiguration = builder.gsonConfiguration;
        this.mPrintHttpLogLevel = builder.printHttpLogLevel;

    }

    public static Builder builder() {
        return new Builder();
    }

    @Singleton
    @Provides
    @Nullable
    List<Interceptor> provideInterceptors() {
        return mInterceptors;
    }


    @Singleton
    @Provides
    HttpUrl provideBaseUrl() {
        if (mBaseUrl != null) {
            HttpUrl httpUrl = mBaseUrl.url();
            if (httpUrl != null) {
                return httpUrl;
            }
        }
        return mApiUrl == null ? HttpUrl.parse("https://api.github.com/") : mApiUrl;
    }


    @Singleton
    @Provides
    BaseImageLoaderStrategy provideBaseImageLoaderStrategy() {
        return mLoaderStrategy == null ? new GlideImageLoaderStrategy() : mLoaderStrategy ;
    }


    @Singleton
    @Provides
    GlobalHttpHandler provideGlobalHttpHandler() {
        return mHandler;
    }


    /**
     * 提供缓存文件
     */
    @Singleton
    @Provides
    File provideFile(Application application) {
        return mCacheFile == null ? DataHelper.getCacheFile(application) : mCacheFile;
    }


    /**
     * 提供处理Rxjava错误的管理器的回调
     */
    @Singleton
    @Provides
    ResponseErrorListener provideResponseErrorListener() {
        return mErrorListener == null ? ResponseErrorListener.EMPTY : mErrorListener;
    }


    @Singleton
    @Provides
    @Nullable
    ClientModule.RetrofitConfiguration provideRetrofitConfiguration() {
        return mRetrofitConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    ClientModule.OkhttpConfiguration provideOkhttpConfiguration() {
        return mOkhttpConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    ClientModule.RxCacheConfiguration provideRxCacheConfiguration() {
        return mRxCacheConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    AppModule.GsonConfiguration provideGsonConfiguration() {
        return mGsonConfiguration;
    }


    @Singleton
    @Provides
    @Nullable
    RequestInterceptor.Level providePrintHttpLogLevel() {
        return mPrintHttpLogLevel;
    }


    public static final class Builder {
        private HttpUrl apiUrl;
        private BaseUrl baseUrl;
        private GlobalHttpHandler handler;
        private List<Interceptor> interceptors;
        private ResponseErrorListener responseErrorListener;
        private File cacheFile;
        private BaseImageLoaderStrategy loaderStrategy;
        private AppModule.GsonConfiguration gsonConfiguration;
        private ClientModule.RetrofitConfiguration retrofitConfiguration;
        private ClientModule.OkhttpConfiguration okhttpConfiguration;
        private ClientModule.RxCacheConfiguration rxCacheConfiguration;
        private RequestInterceptor.Level printHttpLogLevel;


        private Builder() {
        }


        public Builder baseUrl(String baseUrl)//基础 url
        {
            if (TextUtils.isEmpty(baseUrl)) {
                throw new IllegalArgumentException("BaseUrl can not be empty");
            }
            this.apiUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public Builder baseUrl(BaseUrl baseUrl) {
            if (baseUrl == null) {
                throw new IllegalArgumentException("baseUrl can not be null");
            }
            this.baseUrl = baseUrl;
            return this;
        }


        public Builder globalHttpHandler(GlobalHttpHandler handler) {//用来处理 http 响应结果
            this.handler = handler;
            return this;
        }


        public Builder addInterceptor(Interceptor interceptor) {
            if (interceptors == null)
                interceptors = new ArrayList<>();

            this.interceptors.add(interceptor);
            return this;
        }


        public Builder responseErrorListener(ResponseErrorListener listener) {
            this.responseErrorListener = listener;
            return this;
        }

        public Builder cacheFile(File cacheFile) {
            this.cacheFile = cacheFile;
            return this;
        }


        public Builder retrofitConfiguration(ClientModule.RetrofitConfiguration retrofitConfiguration) {
            this.retrofitConfiguration = retrofitConfiguration;
            return this;
        }


        public Builder okhttpConfiguration(ClientModule.OkhttpConfiguration okhttpConfiguration) {
            this.okhttpConfiguration = okhttpConfiguration;
            return this;
        }

        public Builder rxCacheConfiguration(ClientModule.RxCacheConfiguration rxCacheConfiguration) {
            this.rxCacheConfiguration = rxCacheConfiguration;
            return this;
        }


        public Builder gsonConfiguration(AppModule.GsonConfiguration gsonConfiguration) {
            this.gsonConfiguration = gsonConfiguration;
            return this;
        }


        public Builder printHttpLogLevel(RequestInterceptor.Level printHttpLogLevel) {

            if (printHttpLogLevel == null)
                throw new IllegalArgumentException("printHttpLogLevel == null . Use ReqyestUbterceotir,Kevek,NONE instead.");
            this.printHttpLogLevel = printHttpLogLevel;
            return this;
        }


        public GlobalConfigModule build() {
            return new GlobalConfigModule(this);
        }


    }


}
