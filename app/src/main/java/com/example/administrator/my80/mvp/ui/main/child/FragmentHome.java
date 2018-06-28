package com.example.administrator.my80.mvp.ui.main.child;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.aloglibrary.ALog;
import com.example.administrator.my80.R;
import com.example.administrator.my80.adapter.OneAdapter;
import com.example.administrator.my80.base.fragment.BaseLazyFragment;
import com.example.administrator.my80.http.AjaxCallBack;
import com.example.administrator.my80.http.PostUtil;
import com.example.administrator.my80.mvp.m.entity.UserInfo;
import com.example.administrator.my80.mvp.p.UserInfoPresenter;
import com.example.administrator.my80.util.GlideImageLoader;
import com.example.art.base.App;
import com.example.art.utils.UiUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.luoxx.xxlib.weidet.BaseQuickAdapter;
import com.youth.banner.Banner;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.lemon.resthttp.request.Cache;
import cn.lemon.resthttp.request.Request;
import cn.lemon.resthttp.request.Response;
import cn.lemon.resthttp.request.RestHttp;
import cn.lemon.resthttp.request.ServerCache;
import cn.lemon.resthttp.request.callback.HttpCallback;
import cn.lemon.resthttp.request.http.HttpHeaderParser;
import cn.lemon.resthttp.request.http.HttpRequest;
import cn.lemon.resthttp.util.HttpLog;
import cn.lemon.resthttp.util.RestHttpLog;
import cn.lemon.resthttp.util.Util;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * 特殊对象
 * 为了提供更好的服务，BmobSDK中提供了BmobUser、BmobInstallation、BmobRole三个特殊的BmobObject对象来完成不同的功能，在这里我们统一称为特殊对象。
 * <p>
 * BmobUser对象主要是针对应用中的用户功能而提供的，它对应着web端的User表，使用BmobUser对象可以很方便的在应用中实现用户的注册、
 * 登录、邮箱验证等功能，具体的使用方法可查看文档的用户管理部分。
 * <p>
 * BmobInstallation对象主要用于应用的安装设备管理中，它对应着web端的Installation表，
 * 任何安装了你应用的设备都会在此表中产生一条数据标示该设备。结合Bmob提供的推送功能，
 * 还可以实现将自定义的消息推送给不同的设备终端，具体的使用方法可查看消息推送开发文档。
 * <p>
 * BmobRole对象主要用于角色管理，对应用于Web端的Role表，具体的使用方法可查看文档的ACL和角色部分。
 */

public class FragmentHome extends BaseLazyFragment {

    private static final String TAG = "FragmentHome";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public UserInfoPresenter obtainPresenter() {
        return new UserInfoPresenter(((App) mActivity.getApplication()).getAppComponent());
    }

    @Override
    public void setData(Object data) {

    }


    //    @BindView(R.id.toolbar)
//    Toolbar mToolbar;
    @BindView(R.id.rv)
    RecyclerView mRv;
    @BindView(R.id.refreshLayout)
    TwinklingRefreshLayout refreshLayout;
    private OneAdapter mOneAdapter;
    private List<String> mItemList = new ArrayList<>();
    private List<String> mImages = new ArrayList<>();
    private int bannerHeight;
    private View headView;

    @Override
    public void lazyLoadAftFragmentViewCreated() {
        super.lazyLoadAftFragmentViewCreated();
//        ImmersionBar.setTitleBar(getActivity(), mToolbar);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_one_home;
    }

    @Override
    protected void initData() {
        for (int i = 1; i <= 20; i++) {
            mItemList.add("http://img.qumofang.com/1068_20180514_route_FqQyRKvERafp7OMdchgxu75hWxTg.jpg?imageView2/1/w/640/h/268/q/80");
        }
        mImages.add("http://img.qumofang.com/1068_20180514_route_FqQyRKvERafp7OMdchgxu75hWxTg.jpg?imageView2/1/w/640/h/268/q/80");
        mImages.add("http://img.qumofang.com/1068_20180514_route_llm-MepHD8u9r1MLhSc78PhoCPyn.jpg?imageView2/1/w/640/h/268/q/80");
        mImages.add("http://img.qumofang.com/1068_20180514_route_lqSDWDMeC0F7D95DTCEO3kgAx0kV.jpg?imageView2/1/w/640/h/268/q/80");
        mImages.add("http://img.qumofang.com/1068_20180514_route_FlwPVhbNsuJnb5j2UsveLEpi1bow.jpg?imageView2/1/w/640/h/268/q/80");
    }


    @Override
    public void initData(Bundle saveInstanceState) {

        UiUtils.snackbarText("上传 bmob");
        UserInfo p2 = new UserInfo();
        p2.displayName = "大傻";
        p2.address = "北京海淀";
        p2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    UiUtils.snackbarText("添加数据成功，返回objectId为：" + objectId);

                    Log.i(TAG, "done:添加数据成功 " + objectId);
                } else {
                    UiUtils.snackbarText("创建数据失败：" + e.getMessage());
                    Log.i(TAG, "done: 创建数据失败" + objectId + e.getMessage());
                }
            }
        });


    }

    @Override
    protected void initView() {


        refreshLayout.setEnableLoadmore(false);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        mRv.setLayoutManager(linearLayoutManager);
        mOneAdapter = new OneAdapter(mActivity);
        mOneAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mRv.setAdapter(mOneAdapter);
        addHeaderView();
        addHeaderView1();
        addHeaderView2();
//        mOneAdapter.setPreLoadNumber(1);
        mOneAdapter.setNewData(mItemList);
    }

    private void addHeaderView() {
        if (mImages != null && mImages.size() > 0) {
            headView = LayoutInflater.from(mActivity).inflate(R.layout.item_banner, (ViewGroup) mRv.getParent(), false);
            Banner banner = (Banner) headView.findViewById(R.id.banner);
            banner.setImages(mImages)
                    .setImageLoader(new GlideImageLoader())
                    .setDelayTime(5000)
                    .start();
            mOneAdapter.addHeaderView(headView);
            ViewGroup.LayoutParams bannerParams = banner.getLayoutParams();
//            ViewGroup.LayoutParams titleBarParams = mToolbar.getLayoutParams();
//            bannerHeight = bannerParams.height - titleBarParams.height - ImmersionBar.getStatusBarHeight(getActivity());
        }
    }


    private void addHeaderView1() {

        headView = LayoutInflater.from(mActivity).inflate(R.layout.item_head, (ViewGroup) mRv.getParent(), false);
        mOneAdapter.addHeaderView(headView);

    }

    private void addHeaderView2() {

        headView = LayoutInflater.from(mActivity).inflate(R.layout.item_head_2, (ViewGroup) mRv.getParent(), false);
        mOneAdapter.addHeaderView(headView);

    }

    @Override
    protected void setListener() {

//        mOneAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mOneAdapter.addData(addData());
//                        if (mItemList.size() == 100) {
//                            mOneAdapter.loadMoreEnd();
//                        } else
//                            mOneAdapter.loadMoreComplete();
//                    }
//                }, 2000);
//            }
//        }, mRv);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
/**
 *   RestHttp
 .getInstance(Lately.class)
 */


                String requesUrl = "http://m.zhcw.com/clienth5.do?transactionType=8021&pageNo=1&pageSize=20&busiCode=300205&src=0000100001%7C6000003060";
                String requesUrl1 = "http://www.luocaca.cn/hello-ssm/mountaineering/lately";
                String html = "http://www.luocaca.cn/hello-ssm/mountaineering/lately";
                String html2 = "https://www.jfdaily.com/news/detail";
//                String html = "http://www.luocaca.cn/hello-ssm";

//                Map<String , String> heads = new HashMap<String, String>();
//                heads.put("Content-Type","application/json");


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        doGet(html, new HttpCallback() {
                            @Override
                            public void success(String s) {
                                ALog.e("--------s----" + s);
                            }

                            @Override
                            public void failure(int status, String info) {
                                super.failure(status, info);
                                ALog.e("--------s----" + info);
                            }
                        });

                    }
                }).start();


                RestHttp.setDebug(true, "debug");

                HttpRequest.getInstance()
                        .get(html, new HttpCallback() {
                            //                        .get("http://api.hclyz.cn:81/mf/jsonxiaoshimei.txt", new HttpCallback() {
//                        .get("http://www.luocaca.cn/hello-ssm/mountaineering/lately", new HttpCallback() {
                            @Override
                            public void failure(int status, String info) {
                                UiUtils.snackbarText(info);
                                ALog.w("info is \n" + info);
                            }

                            @Override
                            public void success(String s) {
                                UiUtils.snackbarText(s);
                                ALog.i("info is \n" + s);
                            }
                        });


                ApiInterface apiService = getClient(mActivity, "http://www.luocaca.cn/hello-ssm/").create(ApiInterface.class);

                //"94361"
                apiService.lately().enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                        ALog.e("hello-world");


                        ResponseBody bean = response.body();

                        ALog.i("" + (bean != null ? bean.source() : null));

                        try {
                            if (((ResponseBody) response.body()) != null) {
                                ByteArrayInputStream bais = new ByteArrayInputStream(((ResponseBody) response.body()).bytes());

                                InputStreamReader reader = new InputStreamReader(bais);
                                BufferedReader in = new BufferedReader(reader);

                                String readed;
                                StringBuffer buffer = new StringBuffer();

                                while ((readed = in.readLine()) != null) {
                                    System.out.println(readed); //Log the result

                                    ALog.i("" + readed);
                                    buffer.append(readed);

                                }


                                ALog.i("" + buffer);

//                                Document document = Jsoup.connect("").get();

//                                Element element = document.body();


//                                Document document1 = Jsoup.parse(buffer.toString());
//                                Element element = document1.getElementById("newscontents");

//                                ALog.i("" + element.text());


                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {


                        ALog.e(t.getMessage());
                    }
                });


//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        try {
//                          Document document =  Jsoup.connect("host").get();
//                          Element element = document.getElementById("newscontents");
//
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }).start();

//
//                retrofit.create(ResponseBody.class).enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<Bean> call, Response<Bean> response) {
//                        Bean bean = response.body();
//                        list = bean.getResult().getData();
//                        Toast.makeText(MainActivity.this,"000000"+list.get(0).getTitle(),Toast.LENGTH_SHORT).show();
//                        Adapter adapter = new Adapter();
//                        listView.setAdapter(adapter);
//                    }
//
//                    @Override
//                    public void onFailure(Call<Bean> call, Throwable t) {
//
//                    }
//                });


                PostUtil.asyncGet(html, new AjaxCallBack() {
                    @Override
                    public void onSucceed(String json) {
                        ALog.i(json);
                    }

                    @Override
                    public void onFailed(Throwable throwable, int errorCode, String errorMsg) {
                        ALog.i(errorMsg);

                    }

                    @Override
                    public void onCancle() {
                        ALog.i("onCancle");

                    }
                });


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mItemList.clear();
                        mItemList.addAll(newData());
                        mOneAdapter.setNewData(mItemList);
                        refreshLayout.finishRefreshing();

                        mImmersionBar.statusBarDarkFont(false).init();
                    }
                }, 2000);
            }


            @Override
            public void onPullingDown(TwinklingRefreshLayout refreshLayout, float fraction) {

                mImmersionBar.statusBarDarkFont(true).init();
            }

            @Override
            public void onPullDownReleasing(TwinklingRefreshLayout refreshLayout, float fraction) {

            }
        });
    }

    private List<String> addData() {
        List<String> data = new ArrayList<>();
        for (int i = mItemList.size() + 1; i <= mItemList.size() + 20; i++) {
            data.add("http://www.ecl.com.cn/Upload/201308041400537Qr6Yy.jpg");
        }
        return data;
    }

    private List<String> newData() {
        List<String> data = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            data.add("http://www.ecl.com.cn/Upload/201308041400537Qr6Yy.jpg");
        }
        return data;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarColorTransformEnable(false)
                .navigationBarColor(R.color.white)
                .init();
    }


    public static interface ApiInterface {

        // String html = "https://www.jfdaily.com/news/detail/";
        @GET("book/allbook")
//        @FormUrlEncoded
        Call<ResponseBody> url();

        //http://www.luocaca.cn/hello-ssm/mountaineering/lately
        @GET("mountaineering/lately")
//        @FormUrlEncoded
        Call<ResponseBody> lately();


//        Call<ResponseBody> url(@Query("id") String id);


    }

    public void doGet(String url, HttpCallback httpCallback) {

        URL oracle = null;
        try {
            oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream(), "utf-8"));//防止乱码
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                ALog.e("----inputLine---" + inputLine);
            }




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }







        try {
            URL url1=new URL(url);
            HttpURLConnection urlConnection=(HttpURLConnection) url1.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            int code=urlConnection.getResponseCode();
            Log.i("44444444444444", "code="+code);
            if (code==200) {
                InputStream inputStream=urlConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuffer buffer=new StringBuffer();
                while ((line=bufferedReader.readLine())!=null) {
                    buffer.append(line);

                }
                String str=buffer.toString();


            }

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }








        final byte respondCode = -1;







        try {
            URL url1 = new URL(url);
            HttpURLConnection  urlConnection = (HttpURLConnection) url1.openConnection();


//           urlConnection.setDoOutput(true);

            urlConnection.setDoInput(true);

            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.setRequestMethod("GET");
//            this.configURLConnection(urlConnection, new Request(url, 11, null, httpCallback));
//            urlConnection.setDoOutput(true);
//            urlConnection.setDoInput(true);
//            urlConnection.setConnectTimeout(10000);
//            urlConnection.setReadTimeout(10000);
//            urlConnection.setInstanceFollowRedirects(true);
//            if (request.method == 11) {


            urlConnection.connect();


            final int respondCode1 = urlConnection.getResponseCode();
            InputStream e = urlConnection.getInputStream();
            final String logCode;
            if (respondCode1 == 302) {
                logCode = urlConnection.getHeaderField("Location");

                HttpLog.Log("302重定向Location : " + logCode);

            } else if (respondCode1 == 200) {
                Map logCode2 = urlConnection.getHeaderFields();
                Set logCode1 = logCode2.keySet();
                HashMap headersStr = new HashMap();
                RestHttpLog.i(new String[]{url + "  响应头信息："});
                Iterator result = logCode1.iterator();

                while (result.hasNext()) {
                    String response = (String) result.next();
                    String entry = urlConnection.getHeaderField(response);
                    headersStr.put(response, entry);
                    RestHttpLog.i(new String[]{response + "  " + entry});
                }

                final String result1 = this.readInputStream(e);
                e.close();
                Response response1 = new Response(result1, headersStr);
                Cache.Entry entry1 = HttpHeaderParser.parseCacheHeaders(response1);
                if (entry1 != null) {
                    ServerCache.getInstance().put(Util.getCacheKey(url), entry1);
                    RestHttpLog.i(new String[]{entry1.toString()});
                }


            } else {
                logCode = this.readInputStream(e);
                if (e != null) {
                    e.close();
                } else {
                    RestHttpLog.i(new String[]{"urlConnection.getErrorStream() == null,respondCode : " + respondCode1});
                }


            }
        } catch (final Throwable var16) {
            if (RestHttpLog.isDebug()) {
                RestHttpLog.i(new String[]{"网络异常 : " + url});
                var16.printStackTrace();
            }


        }


    }


    public static Retrofit getClient(Context context, String BASE_URL) {

        Retrofit retrofit = null;

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }


    public HttpURLConnection configURLConnection(HttpURLConnection urlConnection, Request request) {
        try {
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setInstanceFollowRedirects(true);
//            if (request.method == 11) {
            urlConnection.setRequestMethod("GET");
//            } else if (request.method == 12) {
//                urlConnection.setRequestMethod("POST");
//            }


//            urlConnection.connect();
        } catch (Throwable var5) {
            if (RestHttpLog.isDebug()) {
                var5.printStackTrace();
            }
        }

        return urlConnection;
    }


    public String readInputStream(InputStream in) {
        String result = "";
        if (in != null) {
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));

            String line;
            try {
                while ((line = bin.readLine()) != null) {
                    result = result + line;
                }
            } catch (IOException var6) {
                var6.printStackTrace();
            }
        }

        return result;
    }


}
