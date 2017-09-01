package com.example.art.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;

import com.example.art.base.delegate.IActivity;
import com.example.art.mvp.IPresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IActivity<P> {


    protected final String TAG = this.getClass().getSimpleName();
    private Unbinder mUnbinder;
    protected P mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Toast.makeText(this, getClass().getSimpleName() + " is debug ?", Toast.LENGTH_LONG).show();

        int layoutResID = initView(savedInstanceState);
        try {
            if (layoutResID != 0) {
                setContentView(layoutResID);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        initData(savedInstanceState);
    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        //每次 ui 进行绘制的时候。都会回调这个方法。。。。。。对

        return super.onCreateView(name, context, attrs);
    }



    @Override
    public void initData(Bundle savedInstanceState) {

    }



    @Override
    public void setPresenter(P presenter) {
        this.mPresenter = presenter;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mPresenter == null) mPresenter = obtainPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        this.mPresenter = null;
        this.mUnbinder = null;
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

    @Override
    public boolean useFragment() {
        return true;
    }
}
