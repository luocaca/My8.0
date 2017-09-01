package com.example.art.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.art.base.delegate.ActivityDelegate.LAYOUT_FRAMELAYOUT;
import static com.example.art.base.delegate.ActivityDelegate.LAYOUT_LINEARLAYOUT;
import static com.example.art.base.delegate.ActivityDelegate.LAYOUT_RELATIVELAYOUT;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public class ThirdViewUtil {
    private static final String TAG = "ThirdViewUtil";
    public static int USE_AUTOLAYOUT = -1;//0 说明 android mefest 里面没有使用 AutoLauout 的Meta,即不使用 AutoLayout,1 为有 Meta ,即需要使用


    public ThirdViewUtil() {

    }

    public static Unbinder bindTarget(Object target, Object source) {
        if (source instanceof Activity) {
            return ButterKnife.bind(target, ((Activity) source));
        } else if (source instanceof View) {
            return ButterKnife.bind(target, ((View) source));
        } else if (source instanceof Dialog) {
            return ButterKnife.bind(target, ((Dialog) source));
        } else {
            return Unbinder.EMPTY;
        }
    }


    public static View convertAutoView(String name, Context context, AttributeSet attrs) {
        //本框架并不强制你使用autolayout
        // 如果你不想是用autolayout ， 就不要在 android fest 中声明 ，autolayout 的meta 属性

        if (USE_AUTOLAYOUT == -1) {
            USE_AUTOLAYOUT = 1;
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo;


            try {
                applicationInfo = packageManager.getApplicationInfo(context.getPackageName(),
                        PackageManager.GET_META_DATA);

                if (applicationInfo == null || applicationInfo.metaData == null
                        || !applicationInfo.metaData.containsKey("design_width")
                        || !applicationInfo.metaData.containsKey("design_height")) {
                    USE_AUTOLAYOUT = 0;

                }

            } catch (Exception e) {
                USE_AUTOLAYOUT = 0;
                Log.w(TAG, "convertAutoView: " + e.getMessage());
            }


        }


        if (USE_AUTOLAYOUT == 0) return null;


        View view = null;

        if (name.equals(LAYOUT_FRAMELAYOUT)) {
            view = new AutoFrameLayout(context, attrs);
        } else if (view.equals(LAYOUT_LINEARLAYOUT)) {
            view = new AutoLinearLayout(context, attrs);
        } else if (view.equals(LAYOUT_RELATIVELAYOUT)) {
            view = new AutoRelativeLayout(context, attrs);
        }
        return view;


    }


}
