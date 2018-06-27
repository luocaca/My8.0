package com.example.administrator.my80.mvp.ui.main.child;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.my80.R;
import com.example.administrator.my80.adapter.OneAdapter;
import com.example.administrator.my80.fragment.BaseLazyFragment;
import com.example.administrator.my80.mvp.m.entity.UserInfo;
import com.example.administrator.my80.mvp.p.UserInfoPresenter;
import com.example.administrator.my80.util.GlideImageLoader;
import com.example.art.base.App;
import com.example.art.mvp.IView;
import com.example.art.mvp.Message;
import com.example.art.utils.UiUtils;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.luoxx.xxlib.weidet.BaseQuickAdapter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 特殊对象
 为了提供更好的服务，BmobSDK中提供了BmobUser、BmobInstallation、BmobRole三个特殊的BmobObject对象来完成不同的功能，在这里我们统一称为特殊对象。

 BmobUser对象主要是针对应用中的用户功能而提供的，它对应着web端的User表，使用BmobUser对象可以很方便的在应用中实现用户的注册、
 登录、邮箱验证等功能，具体的使用方法可查看文档的用户管理部分。

 BmobInstallation对象主要用于应用的安装设备管理中，它对应着web端的Installation表，
 任何安装了你应用的设备都会在此表中产生一条数据标示该设备。结合Bmob提供的推送功能，
 还可以实现将自定义的消息推送给不同的设备终端，具体的使用方法可查看消息推送开发文档。

 BmobRole对象主要用于角色管理，对应用于Web端的Role表，具体的使用方法可查看文档的ACL和角色部分。
 */

public class FragmentHome extends BaseLazyFragment implements IView {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

                    Log.i(TAG, "done:添加数据成功 "+objectId);
                } else {
                    UiUtils.snackbarText("创建数据失败：" + e.getMessage());
                    Log.i(TAG, "done: 创建数据失败"+objectId+ e.getMessage());
                }
            }
        });




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


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void handleMessage(Message message) {

    }
}
