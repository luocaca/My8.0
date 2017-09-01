package com.example.art.http;

import android.support.annotation.Nullable;

import com.example.art.utils.CharacterHandler;
import com.example.art.utils.ZipHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import timber.log.Timber;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

@Singleton
public class RequestInterceptor implements Interceptor {

    private GlobalHttpHandler mHandler;
    private final Level printLevel;


    public enum Level {
        NONE,       //不打印log
        REQUEST,    //只打印请求信息
        RESPONSE,   //只打印响应信息
        ALL         //所有数据全部打印
    }


    @Inject
    public RequestInterceptor(@Nullable GlobalHttpHandler handler, @Nullable Level level) {
        this.mHandler = handler;
        if (level == null)
            printLevel = Level.ALL;
        else
            printLevel = level;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        boolean logRequest = printLevel == Level.ALL || (printLevel != Level.NONE && printLevel == Level.RESPONSE);

        if (logRequest) {
            boolean hasRequestBody = request.body() != null;
            //打印 请求信息
                Timber.tag(getTag(request, "Request_Info")).w("Params : 「 %s 」 %nConnection :  「 %s 」%nHeaders ： %n 「 %s 」 "
                        , hasRequestBody ? parseParams(request.newBuilder().build().body()) : "Null"
                        , chain.connection()
                        , request.headers());

//            Timber.tag(getTag(request, "Request_Info")).w("Params : 「 %s 」 % %nConnection :  「 %s 」%nHeaders ： %n 「 %s 」 "
//                    , hasRequestBody ? parseParams(request.newBuilder().build().body()) : "Null"
//                    , chain.connection()
//                    , request.headers());
//            Timber.tag(getTag(request, "Request_Info")).w("Params : 「 %s 」%nConnection : 「 %s 」%nHeaders : %n「 %s 」"
//                    , hasRequestBody ? parseParams(request.newBuilder().build().body()) : "Null"
//                    , chain.connection()
//                    , request.headers());
        }


        boolean logResponse = printLevel == Level.ALL || (printLevel != Level.NONE && printLevel == Level.RESPONSE);

        long t1 = logRequest ? System.nanoTime() : 0;

        Response originalResponse;

        try {
            originalResponse = chain.proceed(request);
        } catch (IOException e) {
            Timber.w("Http Error : " + e);
            throw e;
        }

        long t2 = logResponse ? System.nanoTime() : 0;

        if (logResponse) {
            String bodySize = originalResponse.body().contentLength() != -1 ? originalResponse.body().contentLength() + "-bype" : "unknown-length";
            //打印响应时间以及响应头
            Timber.tag(getTag(request, "Response_Info")).w("Received response in [ %d-ms ] , [ %s ]%n%s "
                    , TimeUnit.NANOSECONDS.toMillis(t2 - t1)
                    , bodySize, originalResponse.headers());
        }


        //打印响应结果
        String bodyString = printResult(request, originalResponse.newBuilder().build(), logResponse);

        if (mHandler != null) {
            return mHandler.onHttpResultResponse(bodyString, chain, originalResponse);

        }

        return originalResponse;
    }


    private String printResult(Request request, Response response, boolean logResponse) {
        //读取服务器返回的结果
        ResponseBody responseBody = response.body();
        String bodyString = null;
        if (isParseable(responseBody.contentType())) {
            try {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE);// Buffer the entire body
                Buffer buffer = source.buffer();


                //获取content 的压缩类型
                String encoding = response
                        .headers()
                        .get("Content-Encoding");

                Buffer clone = buffer.clone();

                ///解析 response  content
                bodyString = parseContent(responseBody, encoding, clone);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (logResponse) {
                Timber.tag(getTag(request, "Response_Result")).w(isJson(responseBody.contentType()) ?
                        CharacterHandler.jsonFormat(bodyString) : isXml(responseBody.contentType()) ?
                        CharacterHandler.xmlFormat(bodyString) : bodyString);
            }

        } else {
            if (logResponse) {
                Timber.tag(getTag(request, "Response_Resykt")).w("This result isn't parsed");
            }
        }
        return bodyString;
    }


    private String getTag(Request request, String tag) {
        return String.format("[%s] 「 %s 」>>> %s", request.method(), request.url().topPrivateDomain(), tag);
    }


    private String parseContent(ResponseBody responseBody, String encoding, Buffer clone) {
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {//content 使用gzip 压缩
            return ZipHelper.decompressForGzip(clone.readByteArray(), convertCharset(charset));
        } else if (encoding != null && encoding.equalsIgnoreCase("zlib")) {
            return ZipHelper.decompressToStringForZlib(clone.readByteArray(), convertCharset(charset));
        } else {//content 没有压缩
            return clone.readString(charset);
        }
    }

    public static String parseParams(RequestBody body) throws UnsupportedEncodingException {
        if (isParseable(body.contentType())) {
            try {
                Buffer requestBurrer = new Buffer();

                body.writeTo(requestBurrer);

                Charset charset = Charset.forName("UTF-8");
                MediaType contentType = body.contentType();
                if (contentType != null) {
                    charset = contentType.charset(charset);
                }
                return URLDecoder.decode(requestBurrer.readString(charset), convertCharset(charset));


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "this params isn't parsed";

    }


    public static boolean isParseable(MediaType mediaType) {
        if (mediaType == null) return false;

        return mediaType.toString().toLowerCase().contains("text")
                || isJson(mediaType) || isForm(mediaType)
                || isHtml(mediaType) || isXml(mediaType);
    }

    public static boolean isJson(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("json");
    }

    public static boolean isForm(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("x-www-form-urlencoded");
    }

    public static boolean isHtml(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("html");
    }

    public static boolean isXml(MediaType mediaType) {
        return mediaType.toString().toLowerCase().contains("xml");
    }

    public static String convertCharset(Charset charset) {
        String s = charset.toString();
        int i = s.indexOf("[");
        if (i == -1) {
            return s;
        }

        return s.substring(i + 1, s.length() - 1);
    }


}
