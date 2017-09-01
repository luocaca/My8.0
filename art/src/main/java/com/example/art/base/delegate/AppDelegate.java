package com.example.art.base.delegate;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;

import com.example.art.base.App;
import com.example.art.di.Module.AppModule;
import com.example.art.di.Module.ClientModule;
import com.example.art.di.Module.GlobalConfigModule;
import com.example.art.di.component.AppComponent;
import com.example.art.di.component.DaggerAppComponent;
import com.example.art.integration.ActivityLifecycle;
import com.example.art.integration.ConfigModule;
import com.example.art.integration.ManifestParser;
import com.example.art.widget.imageloader.glide.GlideImageConfig;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 用于代理 app 生命周期。。用于运行时动态注入代码。
 */

public class AppDelegate implements App, AppLifecycles {

    private Application mApplication;
    private AppComponent mAppComponent;

    @Inject
    protected ActivityLifecycle mActivityLifecycle;

    private final List<ConfigModule> mModules;
    private List<AppLifecycles> mAppLifecycles = new ArrayList<>();
    private List<Application.ActivityLifecycleCallbacks> mActivityLifecycles = new ArrayList<>();

    /**
     * 内存检测接口
     */
    private ComponentCallbacks2 mComponentCallback;

    public AppDelegate(Context context) {
        this.mModules = new ManifestParser(context).parse();//包含glide  配置    与 全局 globe 配置-自定义
        for (ConfigModule mModule : mModules) {
            mModule.injectAppLifecycle(context, mAppLifecycles);
            mModule.injectActivityLifecycle(context, mActivityLifecycles);
        }

    }

    @Override
    public void attachBaseContext(Context base) {
        for (AppLifecycles mAppLifecycle : mAppLifecycles) {
            mAppLifecycle.attachBaseContext(base);
        }
    }

    @Override
    public void onCreate(Application application) {
        this.mApplication = application;
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(mApplication))//提供application
                .clientModule(new ClientModule())//用于提供 okhttp 和retrofit 的单利
                .globalConfigModule(getGlobalConfigModul(mApplication, mModules))//全局配置
                .build();
        mAppComponent.inject(this);

        mAppComponent.extras().put(ConfigModule.class.getName(), mModules);

        mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle);

        for (Application.ActivityLifecycleCallbacks activityLifecycle : mActivityLifecycles) {
            mApplication.registerActivityLifecycleCallbacks(activityLifecycle);
        }

        for (AppLifecycles mAppLifecycle : mAppLifecycles) {
            mAppLifecycle.onCreate(mApplication);
        }

        //app 注入  内存检测接口，，低内存时调用
        mComponentCallback = new AppComponentCallbacks(mApplication, mAppComponent);
        mApplication.registerComponentCallbacks(mComponentCallback);

    }

    @Override
    public void onTerminate(Application application) {
        if (mActivityLifecycle != null) {
            mApplication.unregisterActivityLifecycleCallbacks(mActivityLifecycle);
        }

        if (mComponentCallback != null) {
            //反注册 监听没有被注销。会造成内存泄漏
            mApplication.unregisterComponentCallbacks(mComponentCallback);
        }

        if (mActivityLifecycles != null && mActivityLifecycles.size() > 0) {
            for (Application.ActivityLifecycleCallbacks lifecycle : mActivityLifecycles) {
                mApplication.unregisterActivityLifecycleCallbacks(lifecycle);
            }
        }

        if (mAppLifecycles != null && mAppLifecycles.size() > 0) {
            for (AppLifecycles lifecycle : mAppLifecycles) {
                lifecycle.onTerminate(mApplication);
            }
        }

        this.mAppComponent = null;
        this.mActivityLifecycle = null;
        this.mActivityLifecycles = null;
        this.mComponentCallback = null;
        this.mAppLifecycles = null;
        this.mApplication = null;

    }


    private GlobalConfigModule getGlobalConfigModul(Context context, List<ConfigModule> modules) {
        GlobalConfigModule.Builder builder = GlobalConfigModule
                .builder();
        for (ConfigModule module : modules) {
            module.applyOptions(context, builder);
        }
        return builder.build();
    }


    /**
     * 将AppComponent返回出去,供其它地方使用, AppComponent接口中声明的方法返回的实例,在getAppComponent()拿到对象后都可以直接使用
     *
     * @return
     */
    @Override
    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    private static class AppComponentCallbacks implements ComponentCallbacks2 {

        private Application mApplication;
        private AppComponent mAppComponent;

        public AppComponentCallbacks(Application application, AppComponent appComponent) {
            this.mApplication = application;
            this.mAppComponent = appComponent;
        }

        @Override
        public void onTrimMemory(int level) {

        }

        @Override
        public void onConfigurationChanged(Configuration configuration) {

        }

        @Override
        public void onLowMemory() {
            //内存不足时清理图片请求框架的内存缓存
            mAppComponent.imageLoader().clear(mApplication, GlideImageConfig
                    .builder()
                    .isClearDiskCache(true)
                    .build()
            );
        }
    }


}
