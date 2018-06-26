package com.example.administrator.my80.mvp.m.entity.repository;

import com.example.administrator.my80.mvp.m.api.cache.CommonCache;
import com.example.administrator.my80.mvp.m.api.service.CommonService;
import com.example.administrator.my80.mvp.m.entity.base.BaseJson;
import com.example.administrator.my80.mvp.m.entity.common.UserInfo;
import com.example.art.mvp.IModel;
import com.example.art.mvp.IRepositoryManager;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class UserInfoRepository implements IModel {


    private IRepositoryManager mManager;


    /**
     * 必须含有一个接受 IRepositoryManager 接口的构造函数，否则会报错
     */
    public UserInfoRepository(IRepositoryManager manager) {
        this.mManager = manager;
    }


    public Observable<BaseJson<UserInfo>> getUserInfo(String authId, boolean update) {
        Observable<BaseJson<UserInfo>> userInfo = mManager.createRetrofitService(CommonService.class).getUserInfo();

        return mManager.createCacheService(CommonCache.class)
                .getUserInfo(userInfo,
                        new DynamicKey(authId),
                        new EvictDynamicKey(update))
                .flatMap(new Function<Reply<BaseJson<UserInfo>>, ObservableSource<BaseJson<UserInfo>>>() {
                    @Override
                    public ObservableSource<BaseJson<UserInfo>> apply(@NonNull Reply<BaseJson<UserInfo>> baseJsonReply) throws Exception {
                        return Observable.just(baseJsonReply.getData());
                    }
                });
//        return  userInfo;
        // 使用rxcache 缓存，上啦刷新则不读取缓存，加载更多，读取缓存
//        return mManager.createCacheService(CommonCache.class)
//                .getUserInfo(userInfo
//                        , new DynamicKey(authId)
//                        , new EvictDynamicKey(update))
//                .flatMap(new Function<Reply<BaseJson<UserInfo>>, ObservableSource<BaseJson<UserInfo>>>() {
//
//                    @Override
//                    public ObservableSource<BaseJson<UserInfo>> apply(@NonNull Reply<BaseJson<UserInfo>> userInfoReply) throws Exception {
//                        return Observable.just(userInfoReply.getData());
//                    }
//                });
    }


    @Override
    public void onDestroy() {

    }
}
