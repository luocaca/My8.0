package com.example.administrator.my80.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class StatetBarUtil {


    public static void setTransStateAftK(Activity activity) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        //  大于21  5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        //  大于19   4.4
       else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

            //设置透明状态栏
            if ((params.flags & bits) == 0) {
                params.flags |= bits;
                window.setAttributes(params);
            }

        }



    }


}
