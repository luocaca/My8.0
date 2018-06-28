package com.example.administrator.my80.mvp.p;

import com.blankj.aloglibrary.ALog;
import com.example.administrator.my80.mvp.m.entity.base.BaseJson;
import com.example.administrator.my80.mvp.m.entity.index.Index;
import com.example.administrator.my80.mvp.m.entity.index.PageChild;
import com.example.administrator.my80.mvp.m.entity.query.QueryPage;
import com.example.administrator.my80.mvp.m.entity.repository.IndexRepository;
import com.example.administrator.my80.mvp.m.entity.shop.Data;
import com.example.art.di.component.AppComponent;
import com.example.art.mvp.BasePresenter;
import com.example.art.mvp.Message;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Created by Administrator on 2017/9/6 0006.
 */

public class IndexPresenter extends BasePresenter<IndexRepository> {
    private static final boolean CACHE_SWITCH = true;//缓存开关
    private RxErrorHandler mErrorHandler;//dagger2 注入

    public IndexPresenter(AppComponent appComponent) {
        super(appComponent.repositoryManager().createRepository(IndexRepository.class));
        this.mErrorHandler = appComponent.rxErrorHandler();
    }


    /*Rxjava 配合retrofit 请求 网络数据 进行线程变换*/
    public void requestIndex(final Message msg) {
        msg.what = 0;

        mModel.getIndex(CACHE_SWITCH)//retrofit  异步请求网络
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {//在 onSubscribe 的时候 放回此方法  订阅
                    addDispose(disposable);
                    msg.getTarget().showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> msg.getTarget().hideLoading())
                .subscribe(new ErrorHandleSubscriber<BaseJson<Index>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<Index> indexBaseJson) {
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


    /*商城界面   数据请求*/
    public void requestPageList(final Message msg) {
        msg.what = 1;

        mModel.getPageList(CACHE_SWITCH, (QueryPage) msg.objs[1])
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    addDispose(disposable);
                    msg.getTarget().showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> msg.getTarget().hideLoading())
                .subscribe(new ErrorHandleSubscriber<BaseJson<Data<List<PageChild>>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<Data<List<PageChild>>> dataBaseJson) {
                        ALog.e(dataBaseJson);
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
                });


    }


    /*收藏夹 界面   数据请求*/
    public void requestFavorList(final Message msg) {
        msg.what = 1;

        mModel.getFavorList(CACHE_SWITCH, (Integer) msg.objs[1])
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    addDispose(disposable);
                    msg.getTarget().showLoading();
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> msg.getTarget().hideLoading())
                .subscribe(new ErrorHandleSubscriber<BaseJson<Data<List<PageChild>>>>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson<Data<List<PageChild>>> dataBaseJson) {
                        ALog.e(dataBaseJson);
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
                });


    }


}
