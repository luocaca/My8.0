package com.example.art.http;

import okhttp3.HttpUrl;

/**
 *  lox
 */

public interface BaseUrl {

    /**
     * 针对于 baseUrl 在 App 启动时不能确定，需要请求服务器接口动态获取的应用场景
     * 在调用Retrofit 接口之前，使用Okhttp 或其他方式，请求到正确的 BaseUrl 并通过此方法返回
     *
     * @return   针对于 baseUrl
     *
     * */

    HttpUrl url();

}
