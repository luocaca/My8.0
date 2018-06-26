package com.example.administrator.my80.base.fragment;

/**
 * Created by geyifeng on 2017/7/22.
 */

public abstract class BaseNoImmersionFragment extends BaseLazyFragment {

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }
}