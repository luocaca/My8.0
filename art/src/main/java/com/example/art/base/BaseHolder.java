package com.example.art.base;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.art.utils.ThirdViewUtil;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by Administrator on 2017/8/10 0010.
 */

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected OnViewClickListener mOnViewClickListener = null;
    private final String TAG = this.getClass().getSimpleName();

    public BaseHolder(View itemView) {
        super(itemView);
        Log.i(TAG, "BaseHolder: " + TAG);
        itemView.setOnClickListener(this);
        AutoUtils.autoSize(itemView);//适配
        ThirdViewUtil.bindTarget(this, itemView);//绑定

    }

    @Override
    public void onClick(View v) {
        if (mOnViewClickListener != null) {
            mOnViewClickListener.onViewClick(v, this.getPosition());
        }
    }

    /**
     * 设置数据
     * 刷新界面
     *
     * @param
     * @param position
     */
    public abstract void setData(T data, int position);


    /**
     * 释放资源
     */
    protected void onRelease() {

    }




    public interface OnViewClickListener {
        void onViewClick(View view, int position);
    }

    public void setOnItemClickListener(OnViewClickListener listener) {
        this.mOnViewClickListener = listener;
    }


}
