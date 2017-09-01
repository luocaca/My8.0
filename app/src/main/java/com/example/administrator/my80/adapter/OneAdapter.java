package com.example.administrator.my80.adapter;


import com.example.administrator.my80.R;
import com.luoxx.xxlib.weidet.BaseQuickAdapter;
import com.luoxx.xxlib.weidet.BaseViewHolder;

/**
 * Created by geyifeng on 2017/6/3.
 */

public class OneAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public OneAdapter() {
        super(R.layout.item_one);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.text, item);
    }
}
