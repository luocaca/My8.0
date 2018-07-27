package com.example.administrator.my80;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.my80.app.AttachApp;
import com.example.administrator.my80.i.OnLazyLoadListener;
import com.example.administrator.my80.mvp.m.api.Api;
import com.example.administrator.my80.util.StatetBarUtil;
import com.example.art.base.App;
import com.example.art.base.delegate.AppLifecycles;
import com.example.art.di.Module.AppModule;
import com.example.art.di.Module.ClientModule;
import com.example.art.di.Module.GlobalConfigModule;
import com.example.art.http.GlobalHttpHandler;
import com.example.art.http.RequestInterceptor;
import com.example.art.integration.ConfigModule;
import com.example.art.utils.UiUtils;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.rx_cache2.internal.RxCache;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.rxerrorhandler.handler.listener.ResponseErrorListener;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * app 的全局配置信息再次配置，需要将此 实现类名声明到  AndroidManifest 中
 */

public final class GlobalConfiguration implements ConfigModule {

    /**
     * {@link com.example.art.base.delegate.AppDelegate#onCreate(Application)}
     * -- getGlobalConfigModul();
     * -- application oncreate 时，将 build 和 context 返回出来。。用于配置信息的初始化
     *
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        if (!BuildConfig.LOG_DEBUG) {// Release 时 ，让框架不再打印 Http 请求和响应信息---
            //本字段 通过gradle debug 和 release 字段来控制
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);
        }

        builder.printHttpLogLevel(RequestInterceptor.Level.ALL);

        builder.baseUrl(Api.APP_DOMAIN)
                .globalHttpHandler(new GlobalHttpHandler() {
                    // 这里可以提供一个全局处理Http请求和响应结果的处理类,
                    // 这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
                    @Override
                    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
                        /* 这里可以先客户端一步拿到每一次http请求的结果,可以解析成json,做一些操作,如检测到token过期后
                         重新请求token,并重新执行请求 */
                        return response;
                         /* 这里如果发现token过期,可以先请求最新的token,然后在拿新的token放入request里去重新请求
                        注意在这个回调之前已经调用过proceed,所以这里必须自己去建立网络请求,如使用okhttp使用新的request去请求
                        create a new request and modify it accordingly using the new token
                        Request newRequest = chain.request().newBuilder().header("token", newToken)
                                             .build();

                        retry the request

                        response.body().close();
                        如果使用okhttp将新的请求,请求成功后,将返回的response  return出去即可
                        如果不需要返回新的结果,则直接把response参数返回出去 */

                    }


                    @Override
                    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                         /* 如果需要再请求服务器之前做一些操作,则重新返回一个做过操作的的requeat如增加header,不做操作则直接返回request参数
                           return chain.request().newBuilder().header("token", tokenId)
                                  .build(); */

                        return chain.request().newBuilder()
                                .addHeader("token", "d061f22d979dfb73c355cd7439e48f2ad455f58b95f9de0d")
//                                .addHeader("token", HeadManager.getKeyStr(System.currentTimeMillis()))
                                .addHeader("authc", "3378ca2d-b09b-411b-a3d0-28766e314685")
                                .addHeader("version", "3.0.4")
//                                .addHeader("deviceId", "deviceId")
//                                .addHeader("type", "type")
//                                .addHeader("sdkVersion", "6.0")
//                                .addHeader("deviceType", "android")
//                                .addHeader("manufacturer", "honor")
                                .build();
                    }
                })
                .responseErrorListener(new ResponseErrorListener() {
                    @Override
                    public void handleResponseError(Context context, Throwable t) {
                          /* 用来提供处理所有错误的监听
                       rxjava必要要使用ErrorHandleSubscriber(默认实现Subscriber的onError方法),
                       此监听才生效 */

                        Timber.tag("Catch-Error").w(t.getMessage());

                        //这里不光是只打印错误 ，还可以根据不同的错误作出不同的逻辑处理
                        String msg = "未知错误";

                        if (t instanceof UnknownHostException) {
                            msg = "网络不可用";
                        } else if (t instanceof SocketTimeoutException) {
                            msg = "请求网络超时";
                        } else if (t instanceof HttpException) {
                            HttpException httpException = ((HttpException) t);
                            msg = convertStatusCode(httpException);
                        } else if
                                (
                                t instanceof JsonParseException ||
                                        t instanceof ParseException ||
                                        t instanceof JSONException ||
                                        t instanceof JsonIOException
                                ) {
                            msg = "数据解析错误";
                        }
                        UiUtils.snackbarText(msg);
                    }
                })
                .gsonConfiguration(new AppModule.GsonConfiguration() {
                    @Override
                    public void configGson(Context context, GsonBuilder builder) {
                        //这里可以自己自定义配置Gson的参数
                        builder
                                .serializeNulls()//支持序列化null的参数
                                .enableComplexMapKeySerialization();//支持将序列化key为object的map,默认只能序列化key为string的map

                    }
                })
                .retrofitConfiguration(new ClientModule.RetrofitConfiguration() {
                    @Override
                    public void configRetrofit(Context context, Retrofit.Builder retrofitBuilder) {
                        //这里可以自己自定义配置Retrofit的参数,甚至你可以替换系统配置好的okhttp对象
                        // retrofitBuilder.addConverterFactory(FastJsonConverterFactory.create());
//                        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
                        // 比如使用fastjson替代gson
                    }
                })
                .okhttpConfiguration(new ClientModule.OkhttpConfiguration() {
                    @Override
                    public void configOkhttp(Context context, OkHttpClient.Builder builder) {
                        builder.writeTimeout(10, TimeUnit.SECONDS);
                        // 开启使用一行代码监听  Retrofit / Okhttp 上传下载进度监听 ，以及 glide  加载进度监听
                        // 详细使用方法查看 https://github.com/JessYanCoding/ProgressManager
                        ProgressManager.getInstance().with(builder);
                    }
                })
                .rxCacheConfiguration(new ClientModule.RxCacheConfiguration() {//这里可以自己自定义配置RxCache的参数
                    @Override
                    public void configRxCache(Context context, RxCache.Builder rxCacheBuilder) {
                        rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
                    }
                })
        ;
    }


    /**
     * {@link com.example.art.base.delegate.AppDelegate 构造方法中调用 }
     *
     * @param context    app
     * @param lifecycles 生命周期管理列表
     */
    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        // AppLifecycles 的所有方法都会在基类Application对应的生命周期中被调用
        // ,所以在对应的方法中可以扩展一些自己需要的逻辑
        lifecycles.add(new AppLifecycles() {
            @Override
            public void attachBaseContext(Context base) {
                AttachApp.attachApp(base);
            }

            @Override
            public void onCreate(Application application) {
                if (BuildConfig.LOG_DEBUG) {//Timber 初始化
                    //Timber 是一个日志框架容器,外部使用统一的Api,内部可以动态的切换成任何日志框架(打印策略)进行日志打印
                    //并且支持添加多个日志框架(打印策略),做到外部调用一次 Api,内部却可以做到同时使用多个策略
                    //比如添加三个策略,一个打印日志,一个将日志保存本地,一个将日志上传服务器
                    Timber.plant(new Timber.DebugTree());
                }

                //leakCanary 内存泄漏检查
                App app = (App) application;


                new AttachApp(application);
//                app.getAppComponent().extras()
//                        .put(
//                                RefWatcher.class.getName(),
//                                BuildConfig.USE_CANARY ?
//                                        LeakCanary.install(application)
//                                        : RefWatcher.DISABLED
//                        );
            }

            @Override
            public void onTerminate(Application application) {

            }
        });

    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        lifecycles.add(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Timber.w(activity + " - onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Timber.w(activity + " - onActivityStarted");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//增加黑色字体顶部状态栏
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }

                StatetBarUtil.setTransStateAftK(activity);


                if (!activity.getIntent().getBooleanExtra("isInitToolbar", false)) {
                    //由于加强框架的兼容性,故将 setContentView 放到 onActivityCreated 之后,onActivityStarted 之前执行
                    //而 findViewById 必须在 Activity setContentView() 后才有效,
                    // 所以将以下代码从之前的 onActivityCreated 中移动到 onActivityStarted 中执行
                    activity.getIntent().putExtra("isInitToolbar", true);
                    //这里全局给activity 设置 toolbar 和title ， 你想象力有多丰富，这里就有多强大，
                    //以前放到baseActivity 的操作 都可以放到这里
                    if (activity.findViewById(R.id.toolbar) != null) {
                        if (activity instanceof AppCompatActivity) {
                            ((AppCompatActivity) activity).setSupportActionBar((Toolbar) activity.findViewById(R.id.toolbar));
                            ((AppCompatActivity) activity).getSupportActionBar().setDisplayShowTitleEnabled(false);
                        } else {
                            //  >  6.0
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                activity.setActionBar((android.widget.Toolbar) activity.findViewById(R.id.toolbar));
                                activity.getActionBar().setDisplayShowCustomEnabled(false);//不显示 title
                            }
                        }
                    }

                    if (activity.findViewById(R.id.toolbar_title) != null) {
                        ((TextView) activity.findViewById(R.id.toolbar_title)).setText(activity.getTitle());
                    }

                    if (activity.findViewById(R.id.toolbar_back) != null) {
                        activity.findViewById(R.id.toolbar_back).setOnClickListener(v -> {
                            activity.onBackPressed();
                        });
                    }


                }

            }

            @Override
            public void onActivityResumed(Activity activity) {
                Timber.w(activity + " - onActivityResumed");
            }

            @Override
            public void onActivityPaused(Activity activity) {
//                Timber.w(activity + " - onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Timber.w(activity + " - onActivityStopped");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Timber.w(activity + " - onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Timber.w(activity + " - onActivityDestroyed");
            }
        });


    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycleCallbacksList) {
        lifecycleCallbacksList.add(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                // 在配置变化的时候将这个 Fragment 保存下来,在 Activity 由于配置变化重建是重复利用已经创建的Fragment。
                // https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
                // 如果在 XML 中使用 <Fragment/> 标签,的方式创建 Fragment 请务必在标签中加上 android:id 或者 android:tag 属性,否则 setRetainInstance(true) 无效
                // 在 Activity 中绑定少量的 Fragment 建议这样做,如果需要绑定较多的 Fragment 不建议设置此参数,如 ViewPager 需要展示较多 Fragment
                f.setRetainInstance(true);
            }

            @Override
            public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
                super.onFragmentViewCreated(fm, f, v, savedInstanceState);
                if (f instanceof OnLazyLoadListener) {
                    ((OnLazyLoadListener) f).lazyLoadAftFragmentViewCreated();
                }
            }

            @Override
            public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
//                ((RefWatcher) ((App) f.getActivity()
//                        .getApplication())
//                        .getAppComponent()
//                        .extras()
//                        .get(RefWatcher.class.getName()))
//                        .watch(f);
            }
        });
    }


    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }


}
