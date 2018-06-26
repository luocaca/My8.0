package com.example.administrator.my80.mvp.ui.main.child;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.my80.BuildConfig;
import com.example.administrator.my80.R;
import com.example.administrator.my80.fragment.BaseLazyFragment;
import com.example.administrator.my80.mvp.m.entity.UserInfo;
import com.example.art.mvp.IPresenter;
import com.gyf.barlibrary.ImmersionBar;
import com.luoxx.xxlib.weidet.BaseQuickAdapter;
import com.luoxx.xxlib.weidet.BaseViewHolder;
import com.luoxx.xxlib.weidet.CoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.alien95.resthttp.request.RestHttp;
import cn.lemon.multi.MultiView;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class FragmentShop extends BaseLazyFragment {

    private List<String> mData = new ArrayList<>();

    @BindView(R.id.recyclerView)
    CoreRecyclerView coreRecyclerView;



    @Override
    public void lazyLoadAftFragmentViewCreated() {
        super.lazyLoadAftFragmentViewCreated();
        ImmersionBar.with(this)
                .init();
    }



    protected void initView() {

        RestHttp.initialize(mActivity);
        if(BuildConfig.DEBUG){
            RestHttp.setDebug(true,"network");
        }

    }

    @Override
    protected int setLayoutId() {
        return R.layout.shop;
    }


    @Override
    public void initData(Bundle saveInstanceState) {


        coreRecyclerView.init(new BaseQuickAdapter<UserInfo, BaseViewHolder>(R.layout.item_mult_view) {
            @Override
            protected void convert(BaseViewHolder helper, UserInfo item) {
                MultiView  multiView = (MultiView) helper.getView(R.id.multi_view);
                multiView.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                List<String> data = new ArrayList<String>();
                data.add( "http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add( "http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add( "http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add( "http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add( "http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add( "http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add( "http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add( "http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add( "http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add( "http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add( "http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add( "http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add( "http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add( "http://i02.pictn.sogoucdn.com/73a90748d5e19769");

                multiView.setImages(data);




            }
        });

        coreRecyclerView.getAdapter().addData(new UserInfo());
        coreRecyclerView.getAdapter().addData(new UserInfo());
        coreRecyclerView.getAdapter().addData(new UserInfo());
        coreRecyclerView.getAdapter().addData(new UserInfo());
        coreRecyclerView.getAdapter().addData(new UserInfo());
        coreRecyclerView.getAdapter().addData(new UserInfo());
        coreRecyclerView.getAdapter().addData(new UserInfo());
        coreRecyclerView.getAdapter().addData(new UserInfo());
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
                .init();
    }
}
