package com.example.art.http;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

public interface GlobalHttpHandler {

    Response onHttpResultResponse(String httpResult , Interceptor.Chain chain , Response response);


    Request onHttpRequestBefore(Interceptor.Chain chain , Request request);


    GlobalHttpHandler  EMPTY =  new GlobalHttpHandler(){

        @Override
        public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain , Response response) {
            return response;
        }

        @Override
        public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
            return request;
        }
    };






}
