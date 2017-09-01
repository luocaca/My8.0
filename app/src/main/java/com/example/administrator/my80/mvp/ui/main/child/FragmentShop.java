package com.example.administrator.my80.mvp.ui.main.child;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import com.example.administrator.my80.R;
import com.example.administrator.my80.fragment.BaseLazyFragment;
import com.example.administrator.my80.mvp.m.entity.UserInfo;
import com.example.art.mvp.IPresenter;
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

public class FragmentShop extends BaseLazyFragment {

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
    public IPresenter obtainPresenter() {
        return null;
    }

    @Override
    public void setData(Object data) {

    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarColorTransformEnable(false)
                .navigationBarColor(R.color.colorPrimary)
                .init();
    }
}
