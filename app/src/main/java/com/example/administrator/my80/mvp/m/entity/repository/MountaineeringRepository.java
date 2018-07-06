package com.example.administrator.my80.mvp.m.entity.repository;

import com.example.administrator.my80.mvp.m.api.service.MountaineeringService;
import com.example.administrator.my80.mvp.m.entity.base.BaseJson;
import com.example.art.mvp.IModel;
import com.example.art.mvp.IRepositoryManager;

import io.reactivex.Observable;

/**
 *
 */

public class MountaineeringRepository implements IModel {


    private IRepositoryManager mManager;

    /**
     * 必须含有一个接受 IRepositoryManager 接口的构造函数，否则会报错
     */
    public MountaineeringRepository(IRepositoryManager manager) {
        this.mManager = manager;
    }


    public Observable<BaseJson<String>> commit(String... strings) {
        Observable<BaseJson<String>> commitMsg = mManager.createRetrofitService(MountaineeringService.class).commitMsg(
                strings[0], strings[1], strings[2], strings[3],
                strings[4], Integer.valueOf(strings[5]), strings[6], strings[7],
                strings[8], strings[9], strings[10],strings[11]
        );

        return commitMsg;


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
