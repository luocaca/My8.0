package com.example.administrator.my80.mvp.m.api.cache;

import com.example.administrator.my80.mvp.m.entity.base.BaseJson;
import com.example.administrator.my80.mvp.m.entity.common.UserInfo;
import com.example.administrator.my80.mvp.m.entity.index.Index;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * 缓存类
 */

public interface CommonCache {


    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<BaseJson<UserInfo>>> getUserInfo(Observable<BaseJson<UserInfo>> userInfo, DynamicKey idLastUserQueried, EvictProvider evictProvider);


    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<BaseJson<Index>> getIndex(Observable<BaseJson<Index>> Index, DynamicKey idLastUserQueried, EvictProvider evictProvider);

}
