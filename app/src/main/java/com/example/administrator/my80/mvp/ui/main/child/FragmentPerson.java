package com.example.administrator.my80.mvp.ui.main.child;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blankj.ALog;
import com.example.administrator.my80.R;
import com.example.administrator.my80.fragment.BaseLazyFragment;
import com.example.administrator.my80.mvp.ui.setting.SettingsActivity;
import com.example.administrator.my80.widget.BounceScrollView;
import com.example.administrator.my80.widget.GlideCircleTransform;
import com.example.art.base.App;
import com.example.art.di.component.AppComponent;
import com.example.art.mvp.IPresenter;
import com.example.art.utils.UiUtils;
import com.example.art.widget.imageloader.ImageLoader;
import com.example.art.widget.imageloader.glide.GlideImageConfig;
import com.lqr.optionitemview.OptionItemView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的
 */

public class FragmentPerson extends BaseLazyFragment implements View.OnClickListener {


    @BindView(R.id.iv_circle_head)
    ImageView iv_circle_head;

    @BindView(R.id.bounce_view)
    BounceScrollView scrollView;


    @BindView(R.id.top_bar_option)
    OptionItemView optionItemView;


    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;


    @Override
    protected int setLayoutId() {
        return R.layout.person;
    }

    @Override
    public void initData() {
        ALog.e("initData");
        mAppComponent = ((App) mActivity.getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
        mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                        ? mAppComponent.application() : mAppComponent.appManager().getCurrentActivity(),
                GlideImageConfig
                        .builder()
                        .url("http://img.taopic.com/uploads/allimg/111202/58010-1112020ZJ645.jpg")
//                        .url("http://c.csdnimg.cn/jifen/images/xunzhang/xunzhang/chizhiyiheng.png")
                        .imageView(iv_circle_head)
                        .placeholder(R.mipmap.ic_notifications_white)
                        .errorPic(R.mipmap.test)
                        .transformation(new GlideCircleTransform(mActivity))
                        .build());
    }

    @Override
    protected void initView() {
        super.initView();
        scrollView.setOnStateChangeListener(new BounceScrollView.OnStateChangeListener() {
            @Override
            public void onMoveEnd() {
                mImmersionBar.statusBarDarkFont(false).init();
            }

            @Override
            public void OnMove(int delta) {

                if (delta > 10) {
                    mImmersionBar.statusBarDarkFont(false).init();
                } else {
                    mImmersionBar.statusBarDarkFont(true).init();
                }
            }
        });
        optionItemView.setOnOptionItemClickListener(new OptionItemView.OnOptionItemClickListener() {
            @Override
            public void leftOnClick() {
                ALog.e("leftOnClick");
                UiUtils.startActivity(SettingsActivity.class);
            }

            @Override
            public void centerOnClick() {

            }

            @Override
            public void rightOnClick() {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void initData(Bundle saveInstanceState) {
        ALog.e("initData(Bundle saveInstanceState) ");
    }

    @Override
    public IPresenter obtainPresenter() {
        return null;
    }

    @Override
    public void setData(Object data) {

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarColorTransformEnable(false)
                .navigationBarColor(R.color.white)
                .statusBarDarkFont(false)
                .init();
    }

    @OnClick(R.id.iv_circle_head)
    @Override
    public void onClick(View v) {
        ALog.e("==点击显示头像==");
//        mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
//                        ? mAppComponent.application() : mAppComponent.appManager().getCurrentActivity(),
//                GlideImageConfig
//                        .builder()
//                        .placeholder(R.mipmap.test)
//                        .url("http://img.taopic.com/uploads/allimg/111202/58010-1112020ZJ645.jpg")
////                        .url("http://c.csdnimg.cn/jifen/images/xunzhang/xunzhang/chizhiyiheng.png")
//                        .imageView(iv_circle_head)
//                        .errorPic(R.mipmap.ic_launcher)
//                        .transformation(new GlideCircleTransform(mActivity))
//                        .build());
        String url1 = "http://img.taopic.com/uploads/allimg/111202/58010-1112020ZJ645.jpg";
        String url2 = "http://c.csdnimg.cn/jifen/images/xunzhang/xunzhang/chizhiyiheng.png";

        String url = count++ % 2 == 0 ? url1 : url2;
        mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                        ? mAppComponent.application() : mAppComponent.appManager().getCurrentActivity(),
                GlideImageConfig
                        .builder()
                        .placeholder(R.mipmap.test)
//                      .url("http://img.taopic.com/uploads/allimg/111202/58010-1112020ZJ645.jpg")
                        .url(url)
                        .imageView(iv_circle_head)
                        .errorPic(R.mipmap.ic_launcher)
                        .transformation(new GlideCircleTransform(mActivity))
                        .build());
    }


    int count = 0;
}
