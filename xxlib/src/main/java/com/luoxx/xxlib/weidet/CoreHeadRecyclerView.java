package com.luoxx.xxlib.weidet;

import android.animation.Animator;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.xxlib.R;
import com.luoxx.xxlib.weidet.animation.BaseAnimation;
import com.luoxx.xxlib.weidet.listener.OnItemClickListener;
import com.luoxx.xxlib.weidet.swipeview.MySwipeRefreshLayout;

import static com.luoxx.xxlib.weidet.CoreRecyclerView.REFRESH;


/**
 * Created by hpw on 16/11/1.
 */

public class CoreHeadRecyclerView extends LinearLayout implements BaseQuickAdapter.RequestLoadMoreListener, MySwipeRefreshLayout.SHSOnRefreshListener{
    private RecyclerView mRecyclerView;
    private MySwipeRefreshLayout mSwipeRefreshLayout;
    BaseQuickAdapter mQuickAdapter;
    addDataListener addDataListener;
    RefreshListener refreshListener;
    private SwipeViewHeader mViewHeader;


    public CoreHeadRecyclerView addRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
        return this;
    }

    public interface RefreshListener {
        void refresh();
    }

    public interface addDataListener {
        void addData(int page);
    }

    private View notLoadingView;
    private int page;

    public CoreHeadRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public CoreHeadRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CoreHeadRecyclerView initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_head_recyclerview, null);

        view.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(view);
        mSwipeRefreshLayout = (MySwipeRefreshLayout) findViewById(R.id.swipeLayout_head);
        mSwipeRefreshLayout.setLoadmoreEnable(false);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mSwipeRefreshLayout.setNestedScrollingEnabled(true);
//        }
        mSwipeRefreshLayout.setEnabled(false);
//        mSwipeRefreshLayout.openNesScroll(true);
        mViewHeader = new SwipeViewHeader(context);
        mSwipeRefreshLayout.setHeaderView(mViewHeader);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        return this;
    }

    public CoreHeadRecyclerView init(BaseQuickAdapter mQuickAdapter) {
        init(null, mQuickAdapter);
        return this;
    }

    public CoreHeadRecyclerView init(BaseQuickAdapter mQuickAdapter, Boolean isRefresh) {
        init(null, mQuickAdapter, true);
        return this;
    }

    public CoreHeadRecyclerView init(RecyclerView.LayoutManager layoutManager, BaseQuickAdapter mQuickAdapter) {
        init(layoutManager, mQuickAdapter, true);
        return this;
    }

    public CoreHeadRecyclerView init(RecyclerView.LayoutManager layoutManager, BaseQuickAdapter mQuickAdapter, Boolean isRefresh) {
        if (isRefresh != true) {
            mSwipeRefreshLayout.setVisibility(GONE);
            mRecyclerView = (RecyclerView) findViewById(R.id.rv_list1);
            mRecyclerView.setVisibility(VISIBLE);
        }
        mRecyclerView.setLayoutManager(layoutManager != null ? layoutManager : new LinearLayoutManager(getContext()));
        this.mQuickAdapter = mQuickAdapter;
        mRecyclerView.setAdapter(mQuickAdapter);
        mQuickAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mQuickAdapter);
        return this;
    }

    public CoreHeadRecyclerView addOnItemClickListener(OnItemClickListener onItemClickListener) {
        mRecyclerView.addOnItemTouchListener(onItemClickListener);
        return this;
    }




    @Override
    public void onRefresh() {
        page = 0;
//      mQuickAdapter.getData().clear();
//      mQuickAdapter.notifyDataSetChanged();//如果直接刷新会闪一下
//        datasState = REFRESH;//刷新时
        mQuickAdapter.setDatasState(REFRESH);
        addDataListener.addData(0);
        if (refreshListener != null) {
            refreshListener.refresh();
        }
        mQuickAdapter.openLoadMore(mQuickAdapter.getPageSize());
        mQuickAdapter.removeAllFooterView();
//        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onRefreshPulStateChange(float parent, int state) {
        switch (state) {
            case MySwipeRefreshLayout.NOT_OVER_TRIGGER_POINT:
//                mViewHeader.setLoaderViewText("下拉刷新");
                mViewHeader.setState(0);
                break;
            case MySwipeRefreshLayout.OVER_TRIGGER_POINT:
//                swipeRefreshLayout.setLoaderViewText("松开刷新");
                mViewHeader.setState(1);
                break;
            case MySwipeRefreshLayout.START:
//                swipeRefreshLayout.setLoaderViewText("正在刷新");
                mViewHeader.setState(2);
                break;
        }
    }

    @Override
    public void onLoadmorePullStateChange(float parent, int state) {

    }

    @Override
    //添加refresh 接口回调

    public void onLoadMoreRequested() {
        mRecyclerView.post(() -> {
            if (mQuickAdapter.getData().size() < page * mQuickAdapter.getPageSize()) {
                mQuickAdapter.loadComplete();
                if (notLoadingView == null) {
                    notLoadingView = LayoutInflater.from(getContext()).inflate(R.layout.not_loading, (ViewGroup) mRecyclerView.getParent(), false);
                }
                mQuickAdapter.addFooterView(notLoadingView);
            } else {
                addDataListener.addData(page);
            }
        });
        page += 1;
    }

    public BaseQuickAdapter getAdapter() {
        return mQuickAdapter;
    }

    public CoreHeadRecyclerView addFooterView(View footer) {
        mQuickAdapter.addFooterView(footer);
        return this;
    }

    public CoreHeadRecyclerView addFooterView(View footer, int index) {
        mQuickAdapter.addFooterView(footer, index);
        return this;
    }

    public CoreHeadRecyclerView addHeaderView(View header) {
        mQuickAdapter.addHeaderView(header);
        return this;
    }

    public CoreHeadRecyclerView addHeaderView(View header, int index) {
        mQuickAdapter.addHeaderView(header, index);
        return this;
    }

    public CoreHeadRecyclerView addHeaderView(View header, int index, int orientation) {
        mQuickAdapter.addHeaderView(header, index, orientation);
        return this;
    }

    public CoreHeadRecyclerView hideLoadingMore() {
        mQuickAdapter.hideLoadingMore();
        return this;
    }

    public CoreHeadRecyclerView loadComplete() {
        mQuickAdapter.loadComplete();
        return this;
    }

    public CoreHeadRecyclerView openLoadAnimation() {
        mQuickAdapter.openLoadAnimation();
        return this;
    }

    public CoreHeadRecyclerView openLoadAnimation(BaseAnimation animation) {
        mQuickAdapter.openLoadAnimation(animation);
        return this;
    }

    public CoreHeadRecyclerView openLoadAnimation(@BaseQuickAdapter.AnimationType int animationType) {
        mQuickAdapter.openLoadAnimation(animationType);
        return this;
    }

    public CoreHeadRecyclerView openLoadMore(int pageSize, addDataListener addDataListener) {
//        this.data = data == null ? new ArrayList<T>() : data;
        this.addDataListener = addDataListener;
        mQuickAdapter.openLoadMore(pageSize);
        mQuickAdapter.setOnLoadMoreListener(this);
        return this;
    }

    public CoreHeadRecyclerView remove(int position) {
        mQuickAdapter.remove(position);
        return this;
    }

    public CoreHeadRecyclerView removeAllFooterView() {
        mQuickAdapter.removeAllFooterView();
        return this;
    }

    public CoreHeadRecyclerView removeAllHeaderView() {
        mQuickAdapter.removeAllHeaderView();
        return this;
    }

    public CoreHeadRecyclerView removeFooterView(View footer) {
        mQuickAdapter.removeFooterView(footer);
        return this;
    }

    public CoreHeadRecyclerView removeHeaderView(View header) {
        mQuickAdapter.removeHeaderView(header);
        return this;
    }

    public CoreHeadRecyclerView setDuration(int duration) {
        mQuickAdapter.setDuration(duration);
        return this;
    }

    public CoreHeadRecyclerView setEmptyView(boolean isHeadAndEmpty, boolean isFootAndEmpty, View emptyView) {
        mQuickAdapter.setEmptyView(isHeadAndEmpty, isFootAndEmpty, emptyView);
        return this;
    }

    public CoreHeadRecyclerView setEmptyView(boolean isHeadAndEmpty, View emptyView) {
        mQuickAdapter.setEmptyView(isHeadAndEmpty, emptyView);
        return this;
    }

    public CoreHeadRecyclerView setEmptyView(View emptyView) {
        mQuickAdapter.setEmptyView(emptyView);
        return this;
    }

    public CoreHeadRecyclerView setLoadingView(View loadingView) {
        mQuickAdapter.setLoadingView(loadingView);
        return this;
    }

    public CoreHeadRecyclerView setLoadMoreFailedView(View view) {
        mQuickAdapter.setLoadMoreFailedView(view);
        return this;
    }

    public CoreHeadRecyclerView setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener requestLoadMoreListener) {
        mQuickAdapter.setOnLoadMoreListener(requestLoadMoreListener);
        return this;
    }

    public CoreHeadRecyclerView showLoadMoreFailedView() {
        mQuickAdapter.showLoadMoreFailedView();
        return this;
    }

    public CoreHeadRecyclerView startAnim(Animator anim, int index) {
        mQuickAdapter.startAnim(anim, index);
        return this;
    }

    public CoreHeadRecyclerView openRefresh() {
        mSwipeRefreshLayout.setEnabled(true);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        return this;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    public CoreHeadRecyclerView selfRefresh(boolean b) {
//        mSwipeRefreshLayout.setRefreshing(b);

        if (mSwipeRefreshLayout.isRefreshing()) {
            new Handler().postDelayed(() -> mSwipeRefreshLayout.finishRefresh(), 500);
            mViewHeader.setState(3);
        }
        return this;
    }



    public CoreHeadRecyclerView setDefaultEmptyView() {
        View empty = LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null);
//        View empty  = getContext(). inflate(R.layout.empty_view, null);
        empty.setOnClickListener(view1 -> onRefresh());
        final RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
        final ViewGroup.LayoutParams lp = empty.getLayoutParams();
        if (lp != null) {
            layoutParams.width = lp.width ;
            layoutParams.height = lp.height;
//            layoutParams.width = lp.width;
//            layoutParams.height = lp.height;
        }
        empty.setLayoutParams(layoutParams);
        mQuickAdapter.setEmptyView(empty);
        return this;
    }

}

