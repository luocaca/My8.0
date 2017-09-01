package com.example.administrator.my80.mvp.m;

import com.example.administrator.my80.mvp.m.api.service.UserInfoService;
import com.example.administrator.my80.mvp.m.entity.BaseJson;
import com.example.administrator.my80.mvp.m.entity.UserInfo;
import com.example.art.mvp.IModel;
import com.example.art.mvp.IRepositoryManager;

import io.reactivex.Observable;

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
        Observable<BaseJson<UserInfo>> userInfo = mManager.createRetrofitService(UserInfoService.class).getUserInfo();

        return userInfo ;
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
