package com.example.administrator.my80.app;

import com.example.art.base.BaseApplication;

/**
 * Created by Administrator on 2017/9/6 0006.
 */

public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

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


//        Bmob.initialize(baseApplication,appaction_id);
//        ALog.Builder builder = new ALog.Builder(baseApplication);
//
//
//        new ALog.Builder(baseApplication)
//                .setLogSwitch(BuildConfig.DEBUG)// 设置log总开关，默认开
//                .setGlobalTag("")// 设置log全局标签，默认为空
//                // 当全局标签不为空时，我们输出的log全部为该tag，
//                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
//                .setLogHeadSwitch(true)// 设置log头部是否显示，默认显示
//                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
//                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
//                .setLogFilter(ALog.V);// log过滤器，和logcat过滤器同理，默认Verbose
    }
}
