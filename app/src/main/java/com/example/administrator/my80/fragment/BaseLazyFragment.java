package com.example.administrator.my80.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luoxx.xxlib.weidet.D;

import butterknife.ButterKnife;


/**
 * 懒加载  fragment
 */

public abstract class BaseLazyFragment extends Fragment {
    public Activity mActivity;


    // fragment是否显示了
    public boolean mIsVisible = false;


    public View mRootView;


    public boolean isFirstVisible;

//    public boolean ismIsVisible() {
//        return mIsVisible;
//    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getContentView() != null) {
            return getContentView();
        } else {
            return inflater.inflate(bindLayoutID(), null);
        }

    }

    public View getContentView() {
        return null;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = view;
            ButterKnife.bind(this, mRootView);
            if (getUserVisibleHint()) {
                if (isFirstVisible) {
                    onFragmentFirstVisible();
                    isFirstVisible = false;
                }
                onFragmentVisibleChange(true);
                mIsVisible = true;
            }
            D.e("======当前Fragment===位置=====" + this.getClass().getName());
        }
        super.onViewCreated(mRootView, savedInstanceState);
    }

    protected abstract void onFragmentVisibleChange(boolean b);

    protected abstract void onFragmentFirstVisible();


    protected void initListener() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        initVariable();
    }

    private void initVariable() {
        isFirstVisible = true;
        mIsVisible = false;
        mRootView = null;


    }

    /**
     * 在这里实现Fragment数据的缓加载. 方法是比onCreate更早调用的
     * setUserVisibleHint(boolean isVisibleToUser)
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        /* setUserVisibleHint（） 有可能在fragment 的生命周期之前被调用 */
        if (mRootView == null) {
            return;
        }

        if (isFirstVisible && isVisibleToUser) {
            onFragmentFirstVisible();
            isFirstVisible = false;

        }

        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            mIsVisible = true;
            return;
        }

        if (mIsVisible) {
            mIsVisible = false;
            onFragmentVisibleChange(false);

        }


    }



    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        super.onAttach(context);
    }

    protected abstract void initView(View rootView);

    protected abstract int bindLayoutID();


    protected boolean isFragmentVisible() {
        return mIsVisible;
    }

    /**
     * Views indexed with their IDs
     */
    private SparseArray<View> views = new SparseArray<>();

    protected <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = mRootView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }



}
