package com.example.art.base.delegate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.art.mvp.IPresenter;

/**
 * Created by luocaca on 2017/8/7 0007.
 */

public interface IFragment<P extends IPresenter> {

    boolean useEventBus();

    View initView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState);

    void initData(Bundle saveInstanceState);

    //obtain   获得
    P obtainPresenter();

    void setPresenter(P presenter);


    /**
     * 此方法是让外部调用使 fragment、 做一些操作的。比如说外部的activity 想让fragment、 对象执行一些方法
     * 建议在多个需要让外界调用的方法时，统一穿Message ，通过what字段，来区分不同的方法，在setData、 方法中
     * 就可以switch 做不同的操作，这样就可以用统一的入口方法做不同的事了
     *
     * 使用此方法时注意调用fragment、 的生命周期，如果调用此setData 方法时，oncreate、 还没有执行，
     * setData里却调用了presenter的方法时 ,是会报空的,因为presenter是在onCreate方法中创建的
     * .如果要做一些初始化操作,可以不必让外部调setData,在initData中初始化就可以了
     *
     *  @param data
     */

    void setData(Object data);




}
