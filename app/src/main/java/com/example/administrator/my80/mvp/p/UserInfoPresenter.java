package com.example.administrator.my80.mvp.p;

import android.util.Log;

import com.example.administrator.my80.mvp.m.UserInfoRepository;
import com.example.administrator.my80.mvp.m.entity.BaseJson;
import com.example.administrator.my80.mvp.m.entity.UserInfo;
import com.example.art.di.component.AppComponent;
import com.example.art.mvp.BasePresenter;
import com.example.art.mvp.Message;
import com.example.art.utils.PermissionUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class UserInfoPresenter extends BasePresenter<UserInfoRepository> {

    private RxErrorHandler mErrorHandler;//dagger2 注入


    public UserInfoPresenter(AppComponent appComponent) {
        super(appComponent.repositoryManager().createRepository(UserInfoRepository.class));
        this.mErrorHandler = appComponent.rxErrorHandler();

    }


    public void requestUserInfo(final Message msg) {
        final boolean pullToRefresh = (boolean) msg.objs[0];


        //请求外部存储权限用于适配 android6.0 的权限管理机制
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {

            @Override
            public void onRequestPermissionSuccess() {
                // request permission succeed . do something
            }

            @Override
            public void onRequestPermissionFailure() {
                msg.getTarget().showMessage("Request permissons failure");

            }
        }, (RxPermissions) msg.objs[1], mErrorHandler);


        mModel.getUserInfo("3378ca2d-b09b-411b-a3d0-28766e314685", false)//true 去除缓存，
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试， 第一个为重试几次，第二个 间隔
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        addDispose(disposable);
                        msg.getTarget().showLoading();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        msg.getTarget().hideLoading();
                    }
                })
        .subscribe(new Consumer<BaseJson<UserInfo>>() {
            @Override
            public void accept(@NonNull BaseJson<UserInfo> userInfoBaseJson) throws Exception {
                Log.i(TAG, "accept: " + userInfoBaseJson.toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " +throwable.getMessage());
            }
        } )
//                .subscribe(new ErrorHandleSubscriber<BaseJson<UserInfo>>(mErrorHandler) {
//                    @Override
//                    public void onNext(@NonNull BaseJson<UserInfo> userInfo) {
//                        UiUtils.snackbarText(userInfo.getMsg());
//                    }
//                })
        ;


    }


}
