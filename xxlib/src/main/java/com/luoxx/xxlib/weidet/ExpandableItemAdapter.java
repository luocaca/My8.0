package com.luoxx.xxlib.weidet;

import android.view.ViewGroup;

import com.luoxx.xxlib.weidet.entity.MultiItemEntity;


public abstract class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {


    private static final String TAG = ExpandableItemAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_0 = 0;

    public static final int TYPE_LEVEL_1 = 1;

    public static final int TYPE_LEVEL_2 = 2;

    public static final int TYPE_LEVEL_3 = 3;


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data. 带初始化值
     *
     * @param
     */
    public ExpandableItemAdapter(Integer... layoutResIds){
        super(layoutResIds[0]);

        int length = layoutResIds.length;
        if (length > 3) {
            new SoMuchLayoutsException("so much layouts  ，very 多 layouts  不行啊，要3个以内 ");
        }
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                addItemType(i, layoutResIds[0]);
            } else if (i > 0) {
                addItemType(i, layoutResIds[i]);
            }
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int positions) {
        super.onBindViewHolder(holder, positions);
    }


    public class SoMuchLayoutsException extends Exception {
         SoMuchLayoutsException(String errMsg) {
            super(errMsg);
        }
    }

}
