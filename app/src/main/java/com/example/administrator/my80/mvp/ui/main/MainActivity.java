package com.example.administrator.my80.mvp.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.example.administrator.my80.R;
import com.example.administrator.my80.mvp.ui.main.child.FragmentFavor;
import com.example.administrator.my80.mvp.ui.main.child.FragmentHome;
import com.example.administrator.my80.mvp.ui.main.child.FragmentPerson;
import com.example.administrator.my80.mvp.ui.main.child.FragmentShop;
import com.example.administrator.my80.mvp.p.UserInfoPresenter;
import com.example.administrator.my80.widget.BottomNavigationViewEx;
import com.example.art.base.AdapterViewPager;
import com.example.art.base.App;
import com.example.art.base.BaseActivity;
import com.example.art.mvp.IView;
import com.example.art.mvp.Message;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<UserInfoPresenter> implements IView {

    private RxPermissions mRxPermissions;


    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main2;
    }

    @BindView(R.id.navigation)
    BottomNavigationViewEx navigation;

    @BindView(R.id.view_page_content)
    ViewPager viewPageContent;

    List<Fragment> fragments = new ArrayList<Fragment>() {
        {
            add(new FragmentHome());
            add(new FragmentShop());
            add(new FragmentFavor());
            add(new FragmentPerson());
        }
    };




    // 为ViewPager添加页面改变事件
      ViewPager.OnPageChangeListener vl = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            // 将当前的页面对应的底部标签设为选中状态
            navigation.getMenu().getItem(position).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };



    // 为bnv设置选择监听事件
    BottomNavigationView.OnNavigationItemSelectedListener b = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // 跳转到对应的页面
                    viewPageContent.setCurrentItem(0);
                    break;
                case R.id.navigation_shop:
                    viewPageContent.setCurrentItem(1);
                    break;
                case R.id.navigation_favorite:
                    viewPageContent.setCurrentItem(2);
                    break;
                case R.id.navigation_person:
                    viewPageContent.setCurrentItem(3);
                    break;
            }
            // 这里必须返回true才能响应点击事件
            return true;
        }
    };


    @Override
    public void initData(Bundle savedInstanceState) {
        navigation.setOnNavigationItemSelectedListener(b);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);


        //初始化viewPagers
        AdapterViewPager viewPagerAdapter = new AdapterViewPager(getSupportFragmentManager(), fragments);
        viewPageContent.setAdapter(viewPagerAdapter);
        viewPageContent.addOnPageChangeListener(vl);


        this.mRxPermissions = new RxPermissions(this);
        mPresenter.requestUserInfo(Message.obtain(this,new Object[]{true,mRxPermissions}));



    }

    @Override
    public UserInfoPresenter obtainPresenter() {
        return new UserInfoPresenter(((App) getApplication()).getAppComponent());
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void handleMessage(Message message) {

    }
}
