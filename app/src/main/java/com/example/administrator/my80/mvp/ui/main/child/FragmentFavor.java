package com.example.administrator.my80.mvp.ui.main.child;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.my80.R;
import com.example.administrator.my80.mvp.m.entity.UserInfo;
import com.example.art.base.BaseFragment;
import com.example.art.mvp.IPresenter;
import com.luoxx.xxlib.weidet.BaseQuickAdapter;
import com.luoxx.xxlib.weidet.BaseViewHolder;
import com.luoxx.xxlib.weidet.CoreRecyclerView;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class FragmentFavor extends BaseFragment {


    @BindView(R.id.rv_favor)
    CoreRecyclerView rvFavor;


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.favor, null);
    }


    @Override
    public void initData(Bundle saveInstanceState) {
        rvFavor.init(new BaseQuickAdapter<UserInfo,BaseViewHolder>(R.layout.item_res) {
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
    public IPresenter obtainPresenter() {
        return null;
    }

    @Override
    public void setData(Object data) {

    }




}
