package com.example.art.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import org.simple.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * 版权：公司 版权所有
 * 
 * 作者：大傻
 * 
 * 版本： 1.0.0
 * 
 * 创建日期：2017/8/10 0010
 * 
 * 描述：大傻出品避暑精品
 * 
 * 基础service 通过eventbus 来 进行组件 之间的 交互
 */

public abstract class BaseService extends Service {

    private static final String TAG = "BaseService";
    protected CompositeDisposable mCompositeDisposable;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    protected void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();//将所有subscription 放入，集中处理
        }
        mCompositeDisposable.add(disposable);
    }


    protected void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();//保证activity 结束的时候，取消正在执行的订阅
        }
    }


    /**
     * 初始化
     */
    abstract public void init();


}
