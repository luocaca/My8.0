package com.example.administrator.my80.mvp.p;

import com.blankj.aloglibrary.ALog;
import com.example.administrator.my80.mvp.m.entity.base.BaseJson;
import com.example.administrator.my80.mvp.m.entity.repository.MountaineeringRepository;
import com.example.art.di.component.AppComponent;
import com.example.art.mvp.BasePresenter;
import com.example.art.mvp.Message;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Created by Administrator on 2017/9/6 0006.
 */

public class MountaineeringPresenter extends BasePresenter<MountaineeringRepository> {
    private static final boolean CACHE_SWITCH = true;//缓存开关
    private RxErrorHandler mErrorHandler;//dagger2 注入

    public MountaineeringPresenter(AppComponent appComponent) {
        super(appComponent.repositoryManager().createRepository(MountaineeringRepository.class));
        this.mErrorHandler = appComponent.rxErrorHandler();
    }


    /*Rxjava 配合retrofit 请求 网络数据 进行线程变换*/
    public void commitMsgs(final Message msg) {
        msg.what = 0;
        String[] strs = (String[]) msg.objs[0];

        mModel.commit(strs)//retrofit  异步请求网络
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {//在 onSubscribe 的时候 放回此方法  订阅
                    addDispose(disposable);
                    msg.getTarget().showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> msg.getTarget().hideLoading())
                .subscribe(new ErrorHandleSubscriber<BaseJson<String>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<String> indexBaseJson) {
                        if (indexBaseJson.isSucceed()) {
                            ALog.e(indexBaseJson);
                            msg.obj = indexBaseJson.getData();
                            ALog.e(indexBaseJson.toString());
                            ALog.e("oNext");
                        } else {
                            msg.getTarget().showMessage(indexBaseJson.getMsg());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ALog.e("onError");
                        super.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        ALog.e("onComplete");
                        super.onComplete();
                    }
                });// 注册  开启订阅


    }


}
