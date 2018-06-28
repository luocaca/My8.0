package com.example.administrator.my80.mvp.ui.main.child;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.my80.R;
import com.example.administrator.my80.fragment.BaseLazyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 报名须知
 */

public class FragmentTips extends BaseLazyFragment {

    @BindView(R.id.web)
    WebView web;

    @Override
    protected void onFragmentVisibleChange(boolean b) {

    }

    @Override
    protected void onFragmentFirstVisible() {


        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);

//        web.loadUrl("http://www.baidu.com");


        //WebView加载web资源
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });


        web.loadUrl("https://mp.weixin.qq.com/s/dAWmyaBEzym8sy3X7AJL9w");


    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected int bindLayoutID() {
        return R.layout.tips;
    }
}
