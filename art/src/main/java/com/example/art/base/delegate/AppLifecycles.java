package com.example.art.base.delegate;

import android.app.Application;
import android.content.Context;

/**
 * 代理application 的生命周期。用于 运行时代码注入
 */

public interface AppLifecycles {
    void attachBaseContext(Context base);

    void onCreate(Application application);

    void onTerminate(Application application);

}
