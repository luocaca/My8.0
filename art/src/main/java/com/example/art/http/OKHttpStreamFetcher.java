package com.example.art.http;

import android.util.Log;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.util.ContentLengthInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class OKHttpStreamFetcher implements DataFetcher<InputStream> {

    private static final String TAG = "OKHttpStreamFetcher";
    private final Call.Factory mClient;//如果在构造函数中进行初始化，并且全部不想改变 值  可以用final  提高效率
    private final GlideUrl mUrl;
    private InputStream stream;
    private volatile Call call;
    private ResponseBody responseBody;


    public OKHttpStreamFetcher(Call.Factory client, GlideUrl url) {
        this.mClient = client;
        this.mUrl = url;
    }

    @Override
    public InputStream loadData(Priority priority) throws Exception {
        Request.Builder requestBuilder = new Request.Builder().url(mUrl.toStringUrl());

//        mUrl.getHeaders().entrySet().forEach((name) -> Request.Builder.name.);
        for (Map.Entry<String, String> headerEntry : mUrl.getHeaders().entrySet()) {
            String key = headerEntry.getKey();
            requestBuilder.addHeader(key, headerEntry.getValue());
        }
        Request request = requestBuilder.build();

        Response response;
        call = mClient.newCall(request);
        response = call.execute();
        responseBody = response.body();
        if (!response.isSuccessful()) {
            throw new IOException("Request failed with code :  " + response.code());
        }

        long contentLength = responseBody.contentLength();
        stream = ContentLengthInputStream.obtain(responseBody.byteStream(), contentLength);
        return stream;
    }

    @Override
    public void cleanup() {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException e) {
            Log.w(TAG, "cleanup: " + e.getMessage());
            e.printStackTrace();
        }


        if (responseBody != null) {
            responseBody.close();
        }
    }

    @Override
    public String getId() {
        return mUrl.getCacheKey();
    }

    @Override
    public void cancel() {

        Call local = call;
        if (local != null) {
            local.cancel();
        }
    }
}
