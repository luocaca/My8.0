package com.example.art.mvp;

/**
 * Created by luoxx on 2017/8/8 0008.
 */

public interface IRepositoryManager {


    /**
     * 根据 传入的 class  创建对应的   请求仓库
     *
     * @param repository
     * @param <T>
     * @return
     */
    //    <> 用于声明泛型方法的类型
    <T extends IModel> T createRepository(Class<T> repository);


    /**
     * 根据传入的Class创建对应的Retrift service
     *
     * @param service
     * @param <T>
     * @return
     */
    <T> T createRetrofitService(Class<T> service);


    /**
     * 根据传入的Class 创建对应的 RxCache service
     *
     * @param cache
     * @param <T>
     * @return
     */
    <T> T createCacheService(Class<T> cache);


    /**
     * 清除所有缓存
     */
    void clearAllCache();

}
