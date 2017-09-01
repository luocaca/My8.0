package com.example.art.base.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcel;

import com.example.art.mvp.IPresenter;

import org.simple.eventbus.EventBus;

/**
 * Created by 罗擦擦 on 2017/8/7 0007.
 */

public class ActivityDelegateImpl implements ActivityDelegate {

    private Activity mActivity;
    private IActivity iActivity;
    private IPresenter iPresenter;

    public ActivityDelegateImpl(Activity activity) {
        this.mActivity = activity;
        this.iActivity = (IActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        if (iActivity.useEventBus())//如果要使用eventbus 请将此方法返回true
        {
            //------------------// TODO: 2017/8/7 0007
            EventBus.getDefault().register(mActivity);//注册到事件总线
        }

        this.iPresenter = iActivity.obtainPresenter();
        iActivity.setPresenter(iPresenter);

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
    public void onDestroy() {

        if (iActivity != null && iActivity.useEventBus()) {
            EventBus.getDefault().unregister(mActivity);
        }

        if (iPresenter != null) iPresenter.onDestroy();
        this.iActivity = null;
        this.mActivity = null;
        this.iPresenter = null;


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    protected ActivityDelegateImpl(Parcel in) {
        this.mActivity = in.readParcelable(Activity.class.getClassLoader());
        this.iActivity = in.readParcelable(IActivity.class.getClassLoader());
        this.iPresenter = in.readParcelable(IPresenter.class.getClassLoader());
    }

    public static final Creator<ActivityDelegateImpl> CREATOR = new Creator<ActivityDelegateImpl>() {
        @Override
        public ActivityDelegateImpl createFromParcel(Parcel source) {
            return new ActivityDelegateImpl(source);
        }

        @Override
        public ActivityDelegateImpl[] newArray(int size) {
            return new ActivityDelegateImpl[size];
        }
    };
}
