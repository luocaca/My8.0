package com.example.art.integration;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.example.art.base.delegate.AppLifecycles;
import com.example.art.di.Module.GlobalConfigModule;

import java.util.List;

/**
 * 此接口 可以给框架配置一些参数，需要实现类实现后，并在AndroidManifest 中声明该实现类
 */

public interface ConfigModule {

    /**
     * 使用{@link GlobalConfigModule.Builder}给框架配置一些配置参数
     * 应用配置
     *
     * @param context
     * @param builder
     */
    void applyOptions(Context context, GlobalConfigModule.Builder builder);


    /**
     * 使用{@link AppLifecycles}在Application的生命周期中注入一些操作
     *
     * @return
     */
    void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles);


    /**
     * 使用{@link Application.ActivityLifecycleCallbacks}在Activity的生命周期中注入一些操作
     *
     * @param context
     * @param lifecycles
     */
    void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycleCallbackses);


    /**
     * 使用{@link FragmentManager.FragmentLifecycleCallbacks}在Fragment的生命周期中注入一些操作
     *
     * @param context
     * @param lifecycles
     */
    void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycleCallbacksList);


}
