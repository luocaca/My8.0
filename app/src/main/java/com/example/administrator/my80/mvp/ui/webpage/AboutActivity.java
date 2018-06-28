package com.example.administrator.my80.mvp.ui.webpage;

import com.blankj.aloglibrary.ALog;
import com.example.administrator.my80.base.activity.BaseWebActivity;
import com.example.administrator.my80.i.OnProgressChangeListener;

public class AboutActivity extends BaseWebActivity {


    @Override
    public String BaseUrl() {
        return "http://www.jianshu.com/p/a506ee4afecb";
    }


    @Override
    public OnProgressChangeListener setProgressChangeListener() {
        return progress -> ALog.e("========progress========" + progress);//重写 此方法。用于监听 加载进度信息
    }

}
