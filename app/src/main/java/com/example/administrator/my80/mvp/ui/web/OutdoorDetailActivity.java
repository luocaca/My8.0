package com.example.administrator.my80.mvp.ui.web;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.administrator.my80.base.activity.BaseWebActivity;


/**
 * 户外知识  web 展示界面
 */

public class OutdoorDetailActivity extends BaseWebActivity {


    public static String url = "";

    @Override
    public String BaseUrl() {
        return url;
    }

    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, OutdoorDetailActivity.class));
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mToolbarRight.setVisibility(View.GONE);
        mToolbarTitle.setText("户外知识");
    }
}
