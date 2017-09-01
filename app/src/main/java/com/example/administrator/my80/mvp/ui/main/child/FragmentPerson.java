package com.example.administrator.my80.mvp.ui.main.child;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.my80.R;
import com.example.art.base.BaseFragment;
import com.example.art.mvp.IPresenter;

/**
 *  我的
 */

public class FragmentPerson extends BaseFragment {


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return  inflater.inflate(R.layout.person,null);
    }

    @Override
    public void initData(Bundle saveInstanceState) {

    }

    @Override
    public IPresenter obtainPresenter() {
        return null;
    }

    @Override
    public void setData(Object data) {

    }
}
