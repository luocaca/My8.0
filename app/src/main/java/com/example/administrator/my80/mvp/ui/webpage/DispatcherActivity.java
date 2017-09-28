package com.example.administrator.my80.mvp.ui.webpage;

import android.webkit.WebView;

import com.example.administrator.my80.base.activity.BaseWebActivity;
import com.example.art.utils.UiUtils;

public class DispatcherActivity extends BaseWebActivity {


    @Override
    public String BaseUrl() {
        String head = "http://m.hmeg.cn/";
        return head + "page/dispatch.html?isApp=true";
    }

    @Override
    public void onLoadUrl(WebView webView, String url) {
        if (url.contains("tel:")) {
//            ToastUtil.showShortToast(url);
            String phone = url.split(":")[1];
            UiUtils.snackbarText("phone = " + phone);
        } else {
            super.onLoadUrl(webView, url);
        }
    }


}
