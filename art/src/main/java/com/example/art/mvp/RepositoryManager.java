package com.example.art.mvp;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

/**
 * 用来管理所有业务逻辑的仓库
 */

@Singleton
public class RepositoryManager implements IRepositoryManager {


    private Lazy<Retrofit> mRetrofit;
    private Lazy<RxCache> mRxCache;
    private final Map<String, IModel> mRepositoryCache = new HashMap<>();
    private final Map<String, Object> mRetrofitServiceCache = new HashMap<>();
    private final Map<String, Object> mCacheServiceCache = new HashMap<>();


    @Inject
    public RepositoryManager(Lazy<Retrofit> retrofit, Lazy<RxCache> rxCache) {
        this.mRetrofit = retrofit;
        this.mRxCache = rxCache;
    }

    /**
     * 根据 传入的 class  创建对应的   请求仓库
     *
     * @param repository
     * @param <T>
     * @return
     */
    //    <> 用于声明泛型方法的类型
    @Override
    public <T extends IModel> T createRepository(Class<T> repository) {
        T repositoryInstance;
        synchronized (mRepositoryCache) {
            repositoryInstance = (T) mRepositoryCache.get(repository.getName());
            if (repositoryInstance == null) {
                Constructor<? extends IModel> constructor = findConstructorForClass(repository);

                try {
                    repositoryInstance = (T) constructor.newInstance(this);
                } catch (InstantiationException e) {
                    throw new RuntimeException("Unable to invoke " + constructor, e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Unable to invoke " + constructor, e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException("create repositor error ", e);
                }
                //缓存仓库 。反射获取影响性能
                mRepositoryCache
                        .put(repository.getName(), repositoryInstance);

            }

        }

        return repositoryInstance;
    }


    /**
     * 根据传入的Class创建对应的Retrift service
     *
     * @param service
     * @param <T>
     * @return
     */
    @Override
    public <T> T createRetrofitService(Class<T> service) {
        T retrofitService;

        synchronized (mRepositoryCache) {
            retrofitService = (T) mRepositoryCache.get(service.getName());
            if (retrofitService == null) {
                //get instance by reflect
                retrofitService = mRetrofit.get().create(service);
                mRetrofitServiceCache.
                        put(service.getName(), retrofitService);
            }
        }

        return retrofitService;
    }


    /**
     * 根据传入的Class创建对应的RxCache service
     *
     * @param cache
     * @param <T>
     * @return
     */
    @Override
    public <T> T createCacheService(Class<T> cache) {
        T cacheService;

        synchronized (mCacheServiceCache) {
            cacheService = (T) mCacheServiceCache.get(cache.getName());
            if (cacheService == null) {
                //create cache
                cacheService = mRxCache.get().using(cache);
                //put to map
                mCacheServiceCache.put(cache.getName(), cacheService);
            }
        }
        return cacheService;
    }

    @Override
    public void clearAllCache() {
        mRxCache.get().evictAll();
    }


    private static Constructor<? extends IModel> findConstructorForClass(Class<?> cls) {
        Constructor<? extends IModel> bindingCtor;

        String clsName = cls.getName();
        try {
            bindingCtor = (Constructor<? extends IModel>) cls.getConstructor(IRepositoryManager.class);
        } catch (NoSuchMethodException e) {

            throw new RuntimeException("Unable to find constructor for " + clsName, e);
        }
        return bindingCtor;


    }


}
