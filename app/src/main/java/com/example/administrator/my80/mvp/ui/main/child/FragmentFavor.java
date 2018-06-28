package com.example.administrator.my80.mvp.ui.main.child;

import android.os.Bundle;

import com.example.administrator.my80.R;
import com.example.administrator.my80.base.fragment.BaseLazyFragment;
import com.example.administrator.my80.mvp.m.entity.common.UserInfo;
import com.example.administrator.my80.mvp.p.IndexPresenter;
import com.example.art.base.App;
import com.example.art.mvp.IView;
import com.example.art.mvp.Message;
import com.luoxx.xxlib.weidet.BaseQuickAdapter;
import com.luoxx.xxlib.weidet.BaseViewHolder;
import com.luoxx.xxlib.weidet.CoreRecyclerView;

import butterknife.BindView;

/**
 * 收藏夹
 */

public class FragmentFavor extends BaseLazyFragment<IndexPresenter> implements IView {


    @BindView(R.id.rv_favor)
    CoreRecyclerView rvFavor;


    @Override
    protected int setLayoutId() {
        return R.layout.favor;
    }

    @Override
    protected void initData() {
        mPresenter.requestFavorList(Message.obtain(this, new Object[]{true, 1}));
//      mPresenter.requestPageList(Message.obtain(this, new Object[]{true, new QueryPage()}));
        rvFavor.init(new BaseQuickAdapter<UserInfo, BaseViewHolder>(R.layout.item_res) {
            @Override
            protected void convert(BaseViewHolder helper, UserInfo item) {

            }
        });

        rvFavor.getAdapter().addData(new UserInfo());
        rvFavor.getAdapter().addData(new UserInfo());
        rvFavor.getAdapter().addData(new UserInfo());
        rvFavor.getAdapter().addData(new UserInfo());
        rvFavor.getAdapter().addData(new UserInfo());
        rvFavor.getAdapter().addData(new UserInfo());
        rvFavor.getAdapter().addData(new UserInfo());
    }


    @Override
    public void initData(Bundle saveInstanceState) {

    }

    @Override
    public IndexPresenter obtainPresenter() {
        return new IndexPresenter(((App) mActivity.getApplication()).getAppComponent());
//        return new IndexPresenter(((App) mActivity.getApplication()).getAppComponent());


    }

    @Override
    public void setData(Object data) {

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

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarColorTransformEnable(false)
                .navigationBarColor(R.color.white)
                .init();
    }
}
