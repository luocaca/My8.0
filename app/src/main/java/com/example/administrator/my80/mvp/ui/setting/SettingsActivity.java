package com.example.administrator.my80.mvp.ui.setting;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.ALog;
import com.example.administrator.my80.R;
import com.example.administrator.my80.mvp.ui.webpage.AboutActivity;
import com.example.art.base.BaseActivity;
import com.example.art.mvp.IPresenter;
import com.example.art.utils.UiUtils;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置  界面
 */
public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ImmersionBar immersionBar;




    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.setting;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        immersionBar = ImmersionBar.with(this);
        initImmersionBar();
    }

    /**
     * 初始化沉浸式
     */
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(false)
                .titleBar(toolbar)
                .navigationBarColor(R.color.white)
                .init();
    }

    @Override
    public IPresenter obtainPresenter() {
        return null;
    }

    @Override
    public boolean useEventBus() {
        return super.useEventBus();
    }


    @OnClick(R.id.about)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about:
                ALog.e("关于");
                UiUtils.startActivity(AboutActivity.class);
                break;
        }
    }
}
