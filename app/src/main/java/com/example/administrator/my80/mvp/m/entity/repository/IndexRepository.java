package com.example.administrator.my80.mvp.m.entity.repository;

import com.blankj.ALog;
import com.example.administrator.my80.mvp.m.api.service.CommonService;
import com.example.administrator.my80.mvp.m.entity.base.BaseJson;
import com.example.administrator.my80.mvp.m.entity.index.Index;
import com.example.administrator.my80.mvp.m.entity.index.PageChild;
import com.example.administrator.my80.mvp.m.entity.query.QueryPage;
import com.example.administrator.my80.mvp.m.entity.shop.Data;
import com.example.art.mvp.IModel;
import com.example.art.mvp.IRepositoryManager;

import java.util.List;

import io.reactivex.Observable;

/**
 *
 */

public class IndexRepository implements IModel {


    private IRepositoryManager mManager;

    public IndexRepository(IRepositoryManager manager) {
        this.mManager = manager;
    }


    public Observable<BaseJson<Index>> getIndex(boolean update) {

        Observable<BaseJson<Index>> index = mManager.createRetrofitService(CommonService.class).getIndex();
        return index;
//        return mManager.createCacheService(CommonCache.class)
//                .getIndex(
//                        index,
//                        new DynamicKey("index"),
//                        new EvictDynamicKey(update))
//                .flatMap(indexBaseJson -> observer -> indexBaseJson.getData());
    }

    public Observable<BaseJson<Data<List<PageChild>>>> getPageList(boolean update, QueryPage query) {
        ALog.e("getPageList" + query.toString());
        Observable<BaseJson<Data<List<PageChild>>>> index = mManager.createRetrofitService(CommonService.class).getPageList(query);
        return index;
    }


    public Observable<BaseJson<Data<List<PageChild>>>> getFavorList(boolean update, int pageIndex) {
        ALog.e("pageIndex= " + pageIndex);
        Observable<BaseJson<Data<List<PageChild>>>> index = mManager.createRetrofitService(CommonService.class).getFavorList(pageIndex, PAGE_SIZE);
        return index;
    }


    @Override
    public void onDestroy() {

    }
}
