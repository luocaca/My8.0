package com.example.art.di.component;

import android.app.Application;

import com.example.art.base.delegate.AppDelegate;
import com.example.art.di.Module.AppModule;
import com.example.art.di.Module.ClientModule;
import com.example.art.di.Module.GlobalConfigModule;
import com.example.art.integration.AppManager;
import com.example.art.mvp.IRepositoryManager;
import com.example.art.widget.imageloader.ImageLoader;
import com.google.gson.Gson;

import java.io.File;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Component;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import okhttp3.OkHttpClient;


/**
 * Component 会通过注解  代码生成 DaggerAppComponent.java
 * 桥梁 用于  动态生成代码 并且注入对象
 */


/**
 * 版权：公司 版权所有
 * <p>
 * 作者：大傻
 * <p>
 * 版本：
 * <p>
 * 创建日期：2017/8/9 0009
 * <p>
 * 描述：大傻出品避暑精品
 */


@Singleton
@Component(modules = {AppModule.class, GlobalConfigModule.class, ClientModule.class})
public interface AppComponent {
//    Error:Execution failed for task ':art:compileDebugJavaWithJavac'.
//            > java.lang.NoClassDefFoundError: dagger/Provides

    /**
     * {@code}
     * {@link AppModule#provideApplication()}
     */
    Application application();


    //Rxjava 错误管理类
    RxErrorHandler rxErrorHandler();


    /**
     * {@link ClientModule#provideClient(Application, ClientModule.OkhttpConfiguration, OkHttpClient.Builder, Interceptor, List, GlobalHttpHandler)}
     *
     * @return
     */
    //dagger 自动注入
    OkHttpClient okHttpClient();

    // 图片选择管理， 用于加载图片的管理类 ，默认使用glide ，使用策略模式， 可替换框架
    ImageLoader imageLoader();

    /**
     * Gson {@link AppModule#provideGson}
     */
    Gson gson();

    /**
     * 由类的构造方法 进行注入
     * {@link AppManager#AppManager(Application)}  }
     */
    AppManager appManager();





    //用于管理所有仓库， 网络请求层，以及数据缓存层
    IRepositoryManager repositoryManager();





    /**
     * {@link GlobalConfigModule#provideFile(Application)}
     * 缓存文件根目录 (RxCache 和 Glide 的缓存都已经作为子文件夹在这个目录里)， 应该讲所有缓存都放在这个根目录里，便于管理和清理，可在GlobeConfigModule 里配置
     *
     * @return
     */
    File cacheFile();


    /**
     * {@link AppModule#provideExtras()}
     *
     * @return
     */
    Map<String, Object> extras();


    /**
     * 为  AppDelegate 进行file 注入 通过dagger2 自动生成的实现类  来注入字段
     *
     * @param appDelegate
     */
    void inject(AppDelegate appDelegate);






}
