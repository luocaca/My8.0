package com.luoxx.xxlib.weidet.swipeview;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public abstract class DipUtils {
    public DipUtils() {
    }

    public static float dipToPx(Context context, float value) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(1, value, metrics);
    }
}
