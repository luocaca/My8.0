package com.example.art.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.example.art.mvp.IPresenter;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/8/7 0007.
 */

public class FragmentDelegateImpl implements FragmentDelegate {

    private static final String TAG = "FragmentDelegateImpl";

    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    private IFragment iFragment;
    private Unbinder mUnbinder;
    private IPresenter iPresenter;


    public FragmentDelegateImpl(FragmentManager fragmentManager, Fragment fragment) {
        this.mFragmentManager = fragmentManager;
        this.mFragment = fragment;
        this.iFragment = ((IFragment) fragment);
    }

    //step 1
    @Override
    public void onAttach(Context context) {

        if (iFragment.useEventBus())
            EventBus.getDefault().register(mFragment);

        this.iPresenter = iFragment.obtainPresenter();
        iFragment.setPresenter(iPresenter);


    }
    //step 2
    @Override
    public void onCreate(Bundle saveInstanceState) {

        Log.i(TAG, "onCreate: ");
    }

    //step 3
    @Override
    public void onCreateView(View view, Bundle saveInstanceState) {

        Log.i(TAG, "onCreateView: ");

        if (view != null) {
            mUnbinder = ButterKnife.bind(mFragment, view);
        }
    }

    //step 4
    @Override
    public void onActivityCreate(Bundle saveInstanceState) {
        iFragment.initData(saveInstanceState);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onDestroyView() {
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            try {
                mUnbinder.unbind();
            } catch (Exception e) {
                e.printStackTrace();
                Log.w(TAG, "onDestoryView: " + e.getMessage());
            }
        }

    }

    @Override
    public void onDestroy() {
        if (iFragment != null && iFragment.useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(mFragment);//注册到事件主线
        if (iPresenter != null) iPresenter.onDestroy(); //释放资源
        this.mUnbinder = null;
        this.mFragmentManager = null;
        this.mFragment = null;
        this.iFragment = null;
        this.iPresenter = null;
    }

    @Override
    public void onDetach() {

    }

    /**
     * return true if the fragment is currently added to its activity
     *
     * @return
     */
    @Override
    public boolean isAdded() {
        return mFragment != null && mFragment.isAdded();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    protected FragmentDelegateImpl(Parcel in) {
        this.mFragmentManager = in.readParcelable(FragmentManager.class.getClassLoader());
        this.mFragment = in.readParcelable(Fragment.class.getClassLoader());
        this.iFragment = in.readParcelable(IFragment.class.getClassLoader());
        this.mUnbinder = in.readParcelable(Unbinder.class.getClassLoader());
        this.iPresenter = in.readParcelable(IPresenter.class.getClassLoader());
    }

    public static final Creator<FragmentDelegateImpl> CREATOR = new Creator<FragmentDelegateImpl>() {
        @Override
        public FragmentDelegateImpl createFromParcel(Parcel source) {
            return new FragmentDelegateImpl(source);
        }

        @Override
        public FragmentDelegateImpl[] newArray(int size) {
            return new FragmentDelegateImpl[size];
        }
    };
}
