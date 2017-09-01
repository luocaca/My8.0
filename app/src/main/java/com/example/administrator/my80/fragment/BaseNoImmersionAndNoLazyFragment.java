package com.example.administrator.my80.fragment;

/**
 * Created by geyifeng on 2017/7/22.
 */

public abstract class BaseNoImmersionAndNoLazyFragment extends BaseNoLazyFragment {

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }
}
