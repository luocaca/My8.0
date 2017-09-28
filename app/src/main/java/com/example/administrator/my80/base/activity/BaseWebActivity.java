package com.example.administrator.my80.base.activity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.ALog;
import com.example.administrator.my80.R;
import com.example.administrator.my80.i.OnProgressChangeListener;
import com.example.art.base.BaseActivity;
import com.example.art.mvp.IPresenter;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

import static com.example.administrator.my80.R.id.toolbar;

/**
 * 用于显示 基础 的web 页面
 */

public abstract class BaseWebActivity extends BaseActivity {
    private static final String TAG = "BaseWebViewActivity";

    @BindView(R.id.web)
    WebView web;

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.base_web;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ImmersionBar.with(this)
                .statusBarDarkFont(false)
                .titleBar(toolbar)
                .navigationBarColor(R.color.white)
                .init();
        //启用支持javascript
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        web.loadUrl(BaseUrl());
//        web.loadUrl("http://www.jianshu.com/p/a506ee4afecb");
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (setProgressChangeListener() != null) {
                    setProgressChangeListener().onProgressChange(newProgress);
                }
            }
        });

        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
                onLoadUrl(view, url);
                ALog.e(url);
                return true;
            }
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                // TODO Auto-generated method stub
//                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(request.getUrl().toString());
//                ALog.e(BaseUrl());
//                return true;
//            }
        });

    }

    @Override
    public IPresenter obtainPresenter() {
        return null;
    }


    @Override
    public void onBackPressed() {
        if (web.canGoBack()) {
            web.goBack();//返回上一页面
        } else {
            finish();
//            System.exit(0);//退出程序
        }
    }


    public void onLoadUrl(WebView webView, String url) {
        webView.loadUrl(url);
    }


    public abstract String BaseUrl();


//    private OnProgressChangeListener changeListener;

    public OnProgressChangeListener setProgressChangeListener() {
        //默认为空实现，需要的话 可以重写返回 一个接口，用于接收进度信息
        return null;
    }

//    public OnProgressChangeListener getChangeListener() {
//        return changeListener;
//    }


}
