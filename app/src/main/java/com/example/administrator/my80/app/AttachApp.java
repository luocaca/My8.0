package com.example.administrator.my80.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.blankj.aloglibrary.ALog;
import com.example.administrator.my80.BuildConfig;

import cn.alien95.resthttp.request.RestHttp;
import cn.bmob.v3.Bmob;

/**
 * 自定义 app
 */

public class AttachApp {

    public static Application application;

    public String appaction_id = "657314b4cd99fefb25596349e9bb06dd";

    public static void attachApp(Context application) {
        MultiDex.install(application);  //这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化
    }


    public AttachApp(Application baseApplication) {
        application = baseApplication ;

        Bmob.initialize(baseApplication, appaction_id);
        ALog.Builder builder = new ALog.Builder(baseApplication);


        new ALog.Builder(baseApplication)
                .setLogSwitch(BuildConfig.DEBUG)// 设置log总开关，默认开
                .setGlobalTag("")// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLogHeadSwitch(true)// 设置log头部是否显示，默认显示
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setLogFilter(ALog.V);// log过滤器，和logcat过滤器同理，默认Verbose


        RestHttp.initialize(baseApplication);
        if (BuildConfig.DEBUG) {
            RestHttp.setDebug(true, "network");
        }
//        ALog.Config config = ALog.init(this)
//                .setLogSwitch(BuildConfig.DEBUG)// 设置log总开关，包括输出到控制台和文件，默认开
//                .setConsoleSwitch(BuildConfig.DEBUG)// 设置是否输出到控制台开关，默认开
//                .setGlobalTag(null)// 设置log全局标签，默认为空
//                // 当全局标签不为空时，我们输出的log全部为该tag，
//                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
//                .setLogHeadSwitch(true)// 设置log头信息开关，默认为开
//                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
//                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
//                .setFilePrefix("")// 当文件前缀为空时，默认为"alog"，即写入文件为"alog-MM-dd.txt"
//                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
//                .setConsoleFilter(ALog.V)// log的控制台过滤器，和logcat过滤器同理，默认Verbose
//                .setFileFilter(ALog.V)// log文件过滤器，和logcat过滤器同理，默认Verbose
//                .setStackDeep(1);// log栈深度，默认为1
//        ALog.d(config.toString());


    }

}
