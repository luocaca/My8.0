package com.example.art.mvp;

/**
 * Created by luocaca on 2017/8/4 0004.
 */

public interface IView {
    /**
     * 显示加载
     */
    void showLoading();


    /**
     * 隐藏加载
     */
    void hideLoading();


    /**
     * 显示主界面
     */

    void showMessage(Message message);



    /**
     * 处理消息,这里面和handler的原理一样,通过swith(what),做不同的操作
     * @param message
     */
    void handleMessage(Message message);



}
