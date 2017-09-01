package com.example.art.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.art.base.delegate.IFragment;
import com.example.art.mvp.IPresenter;

/**
 * Created by luocaca on 2017/8/7 0007.
 */

public abstract class BaseFragment<P extends IPresenter> extends Fragment implements IFragment<P> {

    protected final String TAG = this.getClass().getSimpleName();
    protected P mPresenter;

    public BaseFragment() {
        //必须确保在Fragment实例化时setArguments()
        setArguments(new Bundle());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater, container, savedInstanceState);
    }

    @Override
    public void setPresenter(P presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = obtainPresenter();//获取presenter
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        mPresenter = null;
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    @Override
    public boolean useEventBus() {
        return true;
    }


}
