package com.example.art.base.delegate;

import android.os.Bundle;

import com.example.art.mvp.IPresenter;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public interface IActivity<P extends IPresenter> {
    boolean useEventBus();


    /**
     * 如果 initView 返回 0 , 框架则不会调用{@link android.app.Activity#setContentView(int)}
     *
     * @param savedInstanceState
     * @return
     */

    int initView(Bundle savedInstanceState);


    void initData(Bundle savedInstanceState);


    //obtion 获得
    P obtainPresenter();

    void setPresenter(P presenter);


    boolean useFragment();



}
