package com.example.administrator.my80.mvp.ui.other;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.administrator.my80.R;
import com.example.art.base.BaseActivity;
import com.example.art.mvp.IPresenter;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

public class AccountActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.account;
    }


    @Override
    public void initData(Bundle savedInstanceState) {
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
}
