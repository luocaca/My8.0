package com.example.administrator.my80.mvp.ui.main.child;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.aloglibrary.ALog;
import com.example.administrator.my80.R;
import com.example.administrator.my80.adapter.OneAdapter;
import com.example.administrator.my80.base.fragment.BaseLazyFragment;
import com.example.administrator.my80.mvp.m.api.Api;
import com.example.administrator.my80.mvp.m.entity.UserInfo;
import com.example.administrator.my80.mvp.m.entity.base.BaseJson;
import com.example.administrator.my80.mvp.m.entity.mountaineering.ImagesBean;
import com.example.administrator.my80.mvp.m.entity.mountaineering.Mountaineering;
import com.example.administrator.my80.mvp.p.UserInfoPresenter;
import com.example.administrator.my80.util.GlideImageLoader;
import com.example.administrator.my80.util.SpanUtils;
import com.example.art.base.App;
import com.hedgehog.ratingbar.RatingBar;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.luoxx.xxlib.weidet.BaseQuickAdapter;
import com.youth.banner.Banner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
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
    private Banner banner;
    private TextView content;
    private TextView price;

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

//        UiUtils.snackbarText("上传 bmob");
        UserInfo p2 = new UserInfo();
        p2.displayName = "大傻";
        p2.address = "北京海淀";
        p2.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
//                    UiUtils.snackbarText("添加数据成功，返回objectId为：" + objectId);

                    Log.i(TAG, "done:添加数据成功 " + objectId);
                } else {
//                    UiUtils.snackbarText("创建数据失败：" + e.getMessage());
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


        pullOnlineData();


    }

    private void addHeaderView(List<ImagesBean> imagesBeanList) {
        if (headView == null) {
            headView = LayoutInflater.from(mActivity).inflate(R.layout.item_banner, (ViewGroup) mRv.getParent(), false);
            banner = (Banner) headView.findViewById(R.id.banner);

            if (imagesBeanList != null)
                banner.setImages(imagesBeanList)
                        .setImageLoader(new GlideImageLoader())
                        .setDelayTime(5000)
                        .start();
            mOneAdapter.addHeaderView(headView);
//            ViewGroup.LayoutParams titleBarParams = mToolbar.getLayoutParams();
//            bannerHeight = bannerParams.height - titleBarParams.height - ImmersionBar.getStatusBarHeight(getActivity());
        } else {
            if (imagesBeanList != null && banner != null) {
//                Banner banner = (Banner) headView.findViewById(R.id.banner);
                banner.setImages(imagesBeanList)
                        .setImageLoader(new GlideImageLoader())
                        .setDelayTime(5000)
                        .start();
            }


        }


    }


    private void addHeaderView1(Mountaineering mountaineering) {


        if (content == null) {
            headView = LayoutInflater.from(mActivity).inflate(R.layout.item_head, (ViewGroup) mRv.getParent(), false);
            mOneAdapter.addHeaderView(headView);
            content = (TextView) headView.findViewById(R.id.content);
            price = (TextView) headView.findViewById(R.id.price);
            content.setText(mountaineering.data.title);
            price.setText("￥" + mountaineering.data.price + "");

        } else {
            content.setText(mountaineering.data.title);
            price.setText("￥" + mountaineering.data.price + "");

        }


    }


    /* 上车线路 */
//    private SuperTextView fjzs;
    private RatingBar ratingbar;
    private TextView scdd;
    private TextView xlts;
    private  TextView leader_name;
    private TextView userJoin;
    private TextView zeng_title;
    private TextView zeng_content;

    private void addHeaderView2(Mountaineering mountaineering) {

        if (scdd == null) {
            headView = LayoutInflater.from(mActivity).inflate(R.layout.item_head_2, (ViewGroup) mRv.getParent(), false);
            mOneAdapter.addHeaderView(headView);
//            fjzs = (SuperTextView) headView.findViewById(R.id.fjzs);
            ratingbar = (RatingBar) headView.findViewById(R.id.ratingbar);
            scdd = (TextView) headView.findViewById(R.id.scdd);
            xlts = (TextView) headView.findViewById(R.id.xlts);
            leader_name = (TextView) headView.findViewById(R.id.leader_name);
            userJoin = (TextView) headView.findViewById(R.id.userJoin);
            zeng_title = (TextView) headView.findViewById(R.id.zeng_title);
            zeng_content = (TextView) headView.findViewById(R.id.zeng_content);
//            scxl.setText(mountaineering.data.lineFeature);
//            xlts.setText("线路特色：" + mountaineering.data.lineFeature);
            scdd.setText("上车地点: " + mountaineering.data.loaction);
//            fjzs.setLeftString("风景指数:" + mountaineering.data.star + "颗❤");
            ratingbar.setStar(mountaineering.data.star);
            leader_name.setText("报名方式: " + mountaineering.data.userJoin);
            userJoin.setText("活动领队: " + mountaineering.data.leaderName);
            zeng_title.setText(mountaineering.data.specialOffers);
            zeng_content.setText(mountaineering.data.desc);

            setWithSpan(xlts, mountaineering.data.lineFeature);


        } else {
//            xlts.setText("线路特色：" + mountaineering.data.lineFeature);
            setWithSpan(xlts, mountaineering.data.lineFeature);
            scdd.setText("上车地点: " + mountaineering.data.loaction);
//            fjzs.setLeftString("风景指数:" + mountaineering.data.star + "颗❤");
            ratingbar.setStar(mountaineering.data.star);

            leader_name.setText("报名方式: " + mountaineering.data.userJoin);
            userJoin.setText("活动领队: " + mountaineering.data.leaderName);
            zeng_title.setText(mountaineering.data.specialOffers);
            zeng_content.setText(mountaineering.data.desc);
        }


    }

    // 线路特色
    private void setWithSpan(TextView xts, String lineFeature) {

        SpanUtils spanUtils = new SpanUtils();

        if (!TextUtils.isEmpty(lineFeature) && lineFeature.contains(" ")) {

            String[] strings = lineFeature.split(" ");

            spanUtils.append("线路特色：");
            for (String string : strings) {
                spanUtils.append(" ")
                        .append(" " + string + " ").setBackgroundColor(Color.rgb(27, 80, 119)).setForegroundColor(Color.WHITE)
                        .append(" ");

            }
            xts.setText(spanUtils.create());


        } else {
            xlts.setText("线路特色：" + lineFeature);
        }


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

                pullOnlineData();
//                String html = "http://www.luocaca.cn/hello-ssm";

//                Map<String , String> heads = new HashMap<String, String>();
//                heads.put("Content-Type","application/json");


//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        doGet(html, new HttpCallback() {
//                            @Override
//                            public void success(String s) {
//                                ALog.e("--------s----" + s);
//                            }
//
//                            @Override
//                            public void failure(int status, String info) {
//                                super.failure(status, info);
//                                ALog.e("--------s----" + info);
//                            }
//                        });
//
//                    }
//                }).start();


//                RestHttp.setDebug(true, "debug");

//                HttpRequest.getInstance()
//                        .get(html, new HttpCallback() {
//                            //                        .get("http://api.hclyz.cn:81/mf/jsonxiaoshimei.txt", new HttpCallback() {
////                        .get("http://www.luocaca.cn/hello-ssm/mountaineering/lately", new HttpCallback() {
//                            @Override
//                            public void failure(int status, String info) {
//                                UiUtils.snackbarText(info);
//                                ALog.w("info is \n" + info);
//                            }
//
//                            @Override
//                            public void success(String s) {
//                                UiUtils.snackbarText(s);
//                                ALog.i("info is \n" + s);
//                            }
//                        });


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


//                PostUtil.asyncGet(html, new AjaxCallBack() {
//                    @Override
//                    public void onSucceed(String json) {
//                        ALog.i(json);
//                    }
//
//                    @Override
//                    public void onFailed(Throwable throwable, int errorCode, String errorMsg) {
//                        ALog.i(errorMsg);
//
//                    }
//
//                    @Override
//                    public void onCancle() {
//                        ALog.i("onCancle");
//
//                    }
//                });


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        mItemList.clear();
//                        mItemList.addAll(newData());
//                        mOneAdapter.setNewData(mItemList);
                        refreshLayout.finishRefreshing();

//                        mImmersionBar.statusBarDarkFont(false).init();
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


    public void pullOnlineData() {
//        ApiInterface apiService = getClient(mActivity, "http://192.168.1.142/hello-ssm/").create(ApiInterface.class);
        ApiInterface apiService = getClient(mActivity, Api.host ).create(ApiInterface.class);

        //"94361"
        apiService.lately().enqueue(new Callback<Mountaineering>() {
            @Override
            public void onResponse(Call<Mountaineering> call, retrofit2.Response<Mountaineering> response) {

                ALog.e("hello-world");
                Mountaineering mountaineering = response.body();

                if ((mountaineering != null ? mountaineering.data : null) != null) {
                    addHeaderView(mountaineering.data.listImagesBanner);


//                    Toast.makeText(mActivity, mountaineering.data.leaderName + "", Toast.LENGTH_SHORT).show();


                    addHeaderView1(mountaineering);
                    addHeaderView2(mountaineering);
//        mOneAdapter.setPreLoadNumber(1);
                    mOneAdapter.setNewData(mountaineering.data.listImagesMore);

                }


            }

            @Override
            public void onFailure(Call<Mountaineering> call, Throwable t) {

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
        Call<Mountaineering> lately();

        @GET("mountaineering/list")
//        @FormUrlEncoded
        Call<BaseJson<List<Mountaineering.DataBean>>> list();


//        Call<ResponseBody> url(@Query("id") String id);


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
