package com.example.art.mvp;

import org.simple.eventbus.EventBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class BasePresenter<M extends IModel> implements IPresenter {

    protected final String TAG = this.getClass().getSimpleName();

    protected CompositeDisposable mCompositeDisposable;

    protected M mModel;

    public BasePresenter() {
        onStart();
    }

    public BasePresenter(M model) {
        this.mModel = model;
        onStart();
    }

    @Override
    public void onStart() {

        if (useEventBus())//如果使用 eventbus 请将此方法返回
            EventBus.getDefault().register(this);// 注册 eventbus

    }

    @Override
    public void onDestroy() {
        if (useEventBus())//如果使用 eventbus 请将此方法返回
            EventBus.getDefault().unregister(this);

        unDispose();//接触订阅
        if (mModel != null){
            mModel.onDestroy();
        }

        this.mModel = null ;
        this.mCompositeDisposable = null ;

    }


    /**
     * 是否使用 event Bus ， 默认为（true）  可以重写这个方法。返回 false 可以不使用此框架
     */
    public boolean useEventBus() {
        return true;
    }


    //添加  观察者 到集合中，，到界面关闭的时候，能够统一取消，避免内存泄漏 ， 比如网络请求完。退出界面。还在请求
    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();

        }
        mCompositeDisposable.add(disposable);
    }

    public void unDispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();// 保证 activity 结束的时候，取消所有正在执行的订阅
        }
    }


}
