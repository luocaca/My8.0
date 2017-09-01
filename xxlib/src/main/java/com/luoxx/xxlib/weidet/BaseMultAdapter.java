package com.luoxx.xxlib.weidet;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public abstract class BaseMultAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {


    private int defaultType = 0;
    public final static int GRID_VIEW = 0x00000666;

    public int[] layoutIds;

    public void setDefaultType(int defaultType) {
        this.defaultType = defaultType;
    }

    public int getDefaultType() {
        return defaultType;
    }

    public BaseMultAdapter(int... layoutResId) {
        super(layoutResId[0]);
        this.layoutIds = layoutResId;
    }


    @Override
    protected int getDefItemViewType(int position) {
        if (defaultType == GRID_VIEW) {
            return GRID_VIEW;
        } else {
            return 0;
        }
    }

    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        if (defaultType == GRID_VIEW) {
            return createBaseViewHolder(parent, layoutIds[1]);
        } else {
            return super.createBaseViewHolder(parent, layoutIds[0]);
        }

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    D.e("======LOADING_VIEW========="+ (getItemViewType(position) == LOADING_VIEW));
                    return getItemViewType(position) == LOADING_VIEW  | getItemViewType(position) == EMPTY_VIEW ? gridManager.getSpanCount() : 1;
                }
            });
        }

    }

    @Override
    public void onBindViewHolder(K holder, int positions) {
        super.onBindViewHolder(holder, positions);
    }


}
