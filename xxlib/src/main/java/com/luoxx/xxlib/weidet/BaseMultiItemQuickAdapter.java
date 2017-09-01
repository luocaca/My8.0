package com.luoxx.xxlib.weidet;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.luoxx.xxlib.weidet.entity.MultiItemEntity;


/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public abstract class BaseMultiItemQuickAdapter<T extends MultiItemEntity, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {

    /**
     * layouts 的  资源布局的集合
     */
    private SparseArray<Integer> layouts;


    private static final int DEFAULT_VIEW_TYPE = -0xff;

    public static final int TYPE_NOT_FOUND = -404;





    public BaseMultiItemQuickAdapter (int res )
    {
        super(res);

    }




    @Override
    protected int getDefItemViewType(int position) {

        //list<T> 的每一个 数据  = object  对象类型
        Object item = mData.get(position);
        if (item instanceof MultiItemEntity) {//秒极了
            return ((MultiItemEntity) item).getItemType();
        }
        return DEFAULT_VIEW_TYPE;
//        if (defaultType == GRID_VIEW) {
//            return GRID_VIEW;
//        } else {
//            return 0;
//        }
    }

    public void setDefaultViewTypeLayout(@LayoutRes int layoutResId) {
        addItemType(DEFAULT_VIEW_TYPE, layoutResId);
    }


    /**
     * 代码很简单，从我们存储布局缓存的字段中根据viewType返回对象的布局资源的ids。
     * 所以BaseMultiItemQuickAdapter 还给我们包装了一个addItemType方法：
     *
     * @param type
     * @param layoutResId
     */
    public void addItemType(int type, @LayoutRes int layoutResId) {

        if (layouts == null) {
            layouts = new SparseArray<>();
        }

        layouts.put(type, layoutResId);

    }


    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    protected K onCreateDefViewHolder(ViewGroup parent, int viewType) {
        //改进
        return createBaseViewHolder(parent, getLayoutId(viewType));//fuck

        //原创
//        if (defaultType == GRID_VIEW) {//grid 类型
//            return createBaseViewHolder(parent, layoutIds[1]);
//        } else {
//            return super.createBaseViewHolder(parent, layoutIds[0]); // 默认的
//        }

    }

    private int getLayoutId(int viewType) {

        int layid = layouts.get(viewType) ;
        return layouts.get(viewType);

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
                    return getItemViewType(position) == LOADING_VIEW ? gridManager.getSpanCount() : 1;
                }
            });
        }

    }

    @Override
    public void onBindViewHolder(K holder, int positions) {
        super.onBindViewHolder(holder, positions);
    }


}
