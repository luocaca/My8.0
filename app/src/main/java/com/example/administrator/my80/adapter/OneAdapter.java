package com.example.administrator.my80.adapter;


import android.app.Activity;

import com.example.administrator.my80.R;
import com.example.administrator.my80.util.GlideImageLoader;
import com.luoxx.xxlib.weidet.BaseQuickAdapter;
import com.luoxx.xxlib.weidet.BaseViewHolder;

/**
 * Created by geyifeng on 2017/6/3.
 */

public class OneAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Activity mActivity;

    public OneAdapter(Activity context) {
        super(R.layout.item_one);
        this.mActivity = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
//        helper.setText(R.id.image, item);

        new GlideImageLoader().displayImage(mContext, item, helper.getView(R.id.image));


    }
}
