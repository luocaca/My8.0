package com.example.administrator.my80.mvp.ui.setting;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.administrator.my80.R;
import com.example.art.base.BaseActivity;
import com.example.art.mvp.IPresenter;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

/**
 * 设置  界面
 */
public class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ImmersionBar mImmersionBar;

    @Override
    public int initView(Bundle savedInstanceState) {

        return R.layout.setting;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        initImmersionBar();
        mImmersionBar.addViewSupportTransformColor(toolbar).statusBarDarkFont(true).init();
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.navigationBarWithKitkatEnable(false).init();
    }

    @Override
    public IPresenter obtainPresenter() {
        return null;
    }

    @Override
    public boolean useEventBus() {
        return super.useEventBus();
    }
}
