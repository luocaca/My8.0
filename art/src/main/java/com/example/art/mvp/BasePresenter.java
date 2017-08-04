package com.example.art.mvp;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class BasePresenter<M extends IModel> implements IPresenter {

    protected final String TAG = this.getClass().getSimpleName();

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


    }

    @Override
    public void onDestroy() {

    }


    /**
     * 是否使用 event Bus ， 默认为（true）  可以重写这个方法。返回 false 可以不使用此框架
     */
    public boolean useEventBus() {
        return true ;
    }







}
