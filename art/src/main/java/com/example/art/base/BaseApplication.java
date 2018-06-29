package com.example.art.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.art.base.delegate.AppDelegate;
import com.example.art.base.delegate.AppLifecycles;
import com.example.art.di.component.AppComponent;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class BaseApplication extends Application implements App {


    private AppLifecycles mAppDelegate;


    /**
     * 这里在{@link BaseApplication#onCreate()}  之前被调用，可以做一些较早的初始化
     * 常用于 MultiDex  以及插件化框架的初始化
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);  //这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化
        this.mAppDelegate = new AppDelegate(base);
        this.mAppDelegate.attachBaseContext(base);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        this.mAppDelegate.onCreate(this);
    }


    /**
     * 在模拟环境中程序终止时被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null)
            this.mAppDelegate.onTerminate(this);
    }


    /**
     * 将AppComponent 返回出去，供其他地方使用，AppComponent 接口中声明的方法返回实例
     *
     * @return
     */
    @Override
    public AppComponent getAppComponent() {
        return ((App) mAppDelegate).getAppComponent();
    }
}
