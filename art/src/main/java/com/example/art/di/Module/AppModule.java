package com.example.art.di.Module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import com.example.art.mvp.IRepositoryManager;
import com.example.art.mvp.RepositoryManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by luocaca on 2017/8/7 0007.
 */

@Module
public class AppModule {
    private Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }


    @Singleton
    @Provides
    public Application provideApplication() {
        return mApplication;
    }





//    OkHttpClient provideClient(Application application , @Nullable ClientModule.OkhttpConfiguration configuration
//    , OkHttpClient.Builder builder , Interceptor interceptor , @Nullable List<Interceptor> interceptors , @Nullable GlobalHttpHandler handler){
//
//        builder
//                .connectTimeout(timeou)
//
//
//
//    }



    @Singleton
    @Provides
    public Gson provideGson(Application application, @Nullable GsonConfiguration configuration) {
        GsonBuilder builder = new GsonBuilder();
        if (configuration != null) {
            configuration.configGson(application, builder);
        }
        return builder.create();
    }


    @Singleton
    @Provides
    public IRepositoryManager provideRepositoryManager(RepositoryManager repositoryManager) {
        return repositoryManager;
    }


    @Singleton
    @Provides
    public Map<String, Object> provideExtras() {
        return new ArrayMap<>();
    }


    public interface GsonConfiguration {
        void configGson(Context context, GsonBuilder builder);
    }

}
