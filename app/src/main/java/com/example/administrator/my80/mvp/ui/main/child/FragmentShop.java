package com.example.administrator.my80.mvp.ui.main.child;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import com.blankj.ALog;
import com.example.administrator.my80.R;
import com.example.administrator.my80.fragment.BaseLazyFragment;
import com.example.administrator.my80.mvp.m.entity.common.UserInfo;
import com.example.administrator.my80.mvp.m.entity.query.QueryPage;
import com.example.administrator.my80.mvp.p.IndexPresenter;
import com.example.art.base.App;
import com.example.art.mvp.IView;
import com.example.art.mvp.Message;
import com.gyf.barlibrary.ImmersionBar;
import com.luoxx.xxlib.weidet.BaseQuickAdapter;
import com.luoxx.xxlib.weidet.BaseViewHolder;
import com.luoxx.xxlib.weidet.CoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class FragmentShop extends BaseLazyFragment<IndexPresenter> implements IView {

    private List<String> mData = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    CoreRecyclerView coreRecyclerView;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;


    @Override
    public void lazyLoadAftFragmentViewCreated() {
        super.lazyLoadAftFragmentViewCreated();
        ImmersionBar.with(this)
                .statusBarView(R.id.view)
                .init();
    }


    protected void initView() {


    }

    @Override
    protected int setLayoutId() {
        return R.layout.shop;
    }


    @Override
    public void initData(Bundle saveInstanceState) {
        super.initData(saveInstanceState);
        ALog.e("FragmentShop 商店界面请求网络+ initData   initData(Bundle saveInstanceState)   ");
    }

    @Override
    protected void initData() {

        ALog.e("FragmentShop 商店界面请求网络+ initData()   看看是不是懒加载");

        mPresenter.requestPageList(Message.obtain(this, new Object[]{true, new QueryPage()}));

        TabLayout.Tab tab1 = tabLayout.newTab().setText("筛选");
        tabLayout.addTab(tab1);
        TabLayout.Tab tab2 = tabLayout.newTab().setText("排序");
        tabLayout.addTab(tab2);

        coreRecyclerView.init(new BaseQuickAdapter<UserInfo, BaseViewHolder>(R.layout.item_res) {
            @Override
            protected void convert(BaseViewHolder helper, UserInfo item) {

            }
        });

        coreRecyclerView.getAdapter().addData(new UserInfo());
        coreRecyclerView.getAdapter().addData(new UserInfo());
        coreRecyclerView.getAdapter().addData(new UserInfo());
        coreRecyclerView.getAdapter().addData(new UserInfo());
        coreRecyclerView.getAdapter().addData(new UserInfo());
        coreRecyclerView.getAdapter().addData(new UserInfo());
        coreRecyclerView.getAdapter().addData(new UserInfo());
        coreRecyclerView.getAdapter().addData(new UserInfo());
    }

    @Override
    public IndexPresenter obtainPresenter() {
        return new IndexPresenter(((App) mActivity.getApplication()).getAppComponent());
    }

    @Override
    public void setData(Object data) {

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
        ALog.e("showLoading");
    }

    @Override
    public void hideLoading() {
        ALog.e("hideLoading");
    }

    @Override
    public void showMessage(String message) {
        ALog.e("showMessage");
    }

    @Override
    public void handleMessage(Message message) {
        ALog.e("handleMessage");
    }
}
