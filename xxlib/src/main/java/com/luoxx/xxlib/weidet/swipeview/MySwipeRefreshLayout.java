package com.luoxx.xxlib.weidet.swipeview;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.annotation.LayoutRes;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import com.example.xxlib.R;


public class MySwipeRefreshLayout extends FrameLayout implements NestedScrollingParent, NestedScrollingChild {

    private static final String TAG = "MySwipeRefreshLayout";

    private NestedScrollingChildHelper mScrollingChildHelper;
    private NestedScrollingParentHelper mNestedScrollingParentHelper;

    private MySwipeRefreshLayout.SHSOnRefreshListener onRefreshListener;
    public static final int NOT_OVER_TRIGGER_POINT = 1;
    public static final int OVER_TRIGGER_POINT = 2;
    public static final int START = 3;
    private SHGuidanceView headerView = new SHGuidanceView(this.getContext());
    private SHGuidanceView footerView = new SHGuidanceView(this.getContext());
    private View mTargetView;
    private static final int GUIDANCE_VIEW_HEIGHT = 80;
    private static final int ACTION_PULL_REFRESH = 0;
    private static final int ACTION_LOADMORE = 1;
    private boolean mPullRefreshEnable = true;
    private boolean mPullLoadEnable = true;
    private volatile boolean mRefreshing = false;
    private float guidanceViewHeight = 0.0F;
    private float guidanceViewFlowHeight = 0.0F;
    private int mCurrentAction = -1;
    private boolean isConfirm = false;
    private int mGuidanceViewBgColor;
    private int mGuidanceViewTextColor;
    private int mProgressBgColor;
    private int mProgressColor;
    private String mRefreshDefaulText = "";
    private String mLoadDefaulText = "";

    boolean nestedScrollingEnabled = true;
    private boolean mNestedScrollInProgress;


    /**
     * luocaca  add  params
     */
    // If nested scrolling is enabled, the total amount that needed to be
    // consumed by this as the nested scrolling parent is used in place of the
    // overscroll determined by MOVE events in the onTouch handler
    private float mTotalUnconsumed;

    // Target is returning to its start offset because it was cancelled or a
    // refresh was triggered.
    private boolean mReturningToStart;

    private final int[] mParentOffsetInWindow = new int[2];

    public MySwipeRefreshLayout(Context context) {
        super(context);

        this.initAttrs(context, null);

    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initAttrs(context, attrs);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initAttrs(context, attrs);
    }


    private void initAttrs(Context context, AttributeSet attrs) {
        if (this.getChildCount() > 1) {
            throw new RuntimeException("WXSwipeLayout should not have more than one child");
        } else {
            this.guidanceViewHeight = this.dipToPx(context, 80.0F);
            this.guidanceViewFlowHeight = this.guidanceViewHeight * 1.5F;
            if (!this.isInEditMode() || attrs != null) {
//                TypedArray ta = context.obtainStyledAttributes(attrs, styleable.MySwipeRefreshLayout);
                TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MySwipeRefreshLayout);
                Resources resources = context.getResources();
                int resId = ta.getResourceId(R.styleable.MySwipeRefreshLayout_load_text, -1);
                if (resId == -1) {
                    this.mGuidanceViewBgColor = ta.getColor(R.styleable.MySwipeRefreshLayout_load_text, -1);
                } else {
                    this.mGuidanceViewBgColor = resources.getColor(resId);
                }

                resId = ta.getResourceId(R.styleable.MySwipeRefreshLayout_guidance_text_color, -1);
                if (resId == -1) {
                    this.mGuidanceViewTextColor = ta.getColor(R.styleable.MySwipeRefreshLayout_load_text, -16777216);
                } else {
                    this.mGuidanceViewTextColor = resources.getColor(resId);
                }

                resId = ta.getResourceId(R.styleable.MySwipeRefreshLayout_load_text, -1);
                if (resId == -1) {
                    this.mProgressBgColor = ta.getColor(R.styleable.MySwipeRefreshLayout_load_text, -1);
                } else {
                    this.mProgressBgColor = resources.getColor(resId);
                }

                resId = ta.getResourceId(R.styleable.MySwipeRefreshLayout_load_text, -1);
                if (resId == -1) {
                    this.mProgressColor = ta.getColor(R.styleable.MySwipeRefreshLayout_load_text, -65536);
                } else {
                    this.mProgressColor = resources.getColor(resId);
                }

                resId = ta.getResourceId(R.styleable.MySwipeRefreshLayout_load_text, -1);
                if (resId == -1) {
                    this.mRefreshDefaulText = ta.getString(R.styleable.MySwipeRefreshLayout_load_text);
                } else {
                    this.mRefreshDefaulText = resources.getString(resId);
                }

                resId = ta.getResourceId(R.styleable.MySwipeRefreshLayout_load_text, -1);
                if (resId == -1) {
                    this.mLoadDefaulText = ta.getString(R.styleable.MySwipeRefreshLayout_load_text);
                } else {
                    this.mLoadDefaulText = resources.getString(resId);
                }

                ta.recycle();
                mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
                setNestedScrollingEnabled(true);
            }
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mTargetView = this.getChildAt(0);
        this.setGuidanceView();
    }

    private void setGuidanceView() {
        LayoutParams lp = new LayoutParams(-1, 0);
        this.headerView.setStartEndTrim(0.0F, 0.75F);
        this.headerView.setText(this.mRefreshDefaulText);
        this.headerView.setTextColor(this.mGuidanceViewTextColor);
        this.headerView.setBackgroundColor(this.mGuidanceViewBgColor);
        this.headerView.setProgressBgColor(this.mProgressBgColor);
        this.headerView.setProgressColor(this.mProgressColor);
        this.addView(this.headerView, lp);
        lp = new LayoutParams(-1, 0);
        lp.gravity = 80;
        this.footerView.setStartEndTrim(0.5F, 1.25F);
        this.footerView.setText(this.mLoadDefaulText);
        this.footerView.setTextColor(this.mGuidanceViewTextColor);
        this.footerView.setBackgroundColor(this.mGuidanceViewBgColor);
        this.footerView.setProgressBgColor(this.mProgressBgColor);
        this.footerView.setProgressColor(this.mProgressColor);
        this.addView(this.footerView, lp);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.i(TAG, "onInterceptTouchEvent: ");

        final int action = MotionEventCompat.getActionMasked(ev);
        int pointerIndex;

        if (mReturningToStart && action == MotionEvent.ACTION_DOWN) {
            mReturningToStart = false;
        }

        if (!isEnabled() || mReturningToStart || canChildScrollUp()
                || mRefreshing || mNestedScrollInProgress) {
            // Fail fast if we're not in a state where a swipe is possible
            return false;
        }

        return !(!this.mPullRefreshEnable && !this.mPullLoadEnable) && super.onInterceptTouchEvent(ev);
//        return true;
    }


    //开始滑动
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
//        Log.i(TAG, "onStartNestedScroll:开始滑动 " + nestedScrollAxes);
        //target 发起滑动的 view，可以不是当前view的直接子view
        //child 包含target的直接子view
        //返回true表示要与target配套滑动，为true则下面的accepted也会被调用
        //mReturningToStart是为了配合onTouchEvent的，这里我们不扩展
        boolean b = !mRefreshing && (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
//        Log.i(TAG, "onStartNestedScroll: 是否拦截" + b);
//        Log.i(TAG, "onStartNestedScroll: 强制拦截" + b);
//        return  !mRefreshing  && (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        return true;

    }

    // 分发   如果  不调用  startNestedScroll  无法通知 parent  联合滑动
    public void onNestedScrollAccepted(View child, View target, int axes) {
        this.mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
        Log.i(TAG, "onNestedScrollAccepted: axes=" + axes);//2 表示垂直
        // 开始分发  嵌套滑动
        startNestedScroll(axes & ViewCompat.SCROLL_AXIS_VERTICAL);
        mTotalUnconsumed = 0;
        mNestedScrollInProgress = true;
    }


    //停止滑动
    @Override
    public void onStopNestedScroll(View child) {
        Log.i(TAG, "onStopNestedScroll: 停止滑动");
        mNestedScrollInProgress = false;
//        this.mNestedScrollingParentHelper.onStopNestedScroll(child);
        if (mTotalUnconsumed > 0) {
            mTotalUnconsumed = 0;
        }
        stopNestedScroll();

        this.handlerAction();
    }


    //在滑动前，进行滑动事件分配（询问），consumed是父亲消耗的滑动距离，
    //offsetInWindow 是父亲在窗口中进行的相应的移动，子view需要根据这个进行自身调整（需要的话）
    //区别于下面的，在这里可以进行父亲预备处理
    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    //滑动后滑动事件的分配，子view询问父亲是否需要在滑动后消耗事件
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
                mParentOffsetInWindow);

        final int dy = dyUnconsumed + mParentOffsetInWindow[1];
        Log.i(TAG, "onNestedScroll: dy=" + dy);

        if (dy < 0 && !canChildScrollUp()) {
            mTotalUnconsumed += Math.abs(dy);
            // TODO
            //滑动头部
            this.moveGuidanceView(mTotalUnconsumed);
        } else {
//            mTotalUnconsumed -= Math.abs(dy);
//            if (mTotalUnconsumed <= 0) {
//                mTotalUnconsumed = 0;
//                this.moveGuidanceView(mTotalUnconsumed);
//            }
        }


    }


    /**
     * @return Whether it is possible for the child view of this layout to
     * scroll up. Override this if the child view is a custom view.
     */


    //当惯性嵌套滚动时被调用
    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return  dispatchNestedFling(velocityX,velocityY,consumed);
    }

    @Override
    public void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll();
        this.handlerAction();
    }


    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

//        Log.i(TAG, "onNestedPreScroll: dx=" + dx + "   dy=" + dy + "  mTotalUnconsumed=" + mTotalUnconsumed + "  isConfirm =" + isConfirm);
        if (this.mPullRefreshEnable || this.mPullLoadEnable) {
            if (Math.abs(dy) <= 200 && mTotalUnconsumed > 0) {//&& Math.abs(dy) >= 50
//                if (dy > 0) {
//                    mTotalUnconsumed = 0;
//                }

                //   isConfirm =true   头部滑动，，，不协调滑动
                if (!this.isConfirm) {
                    //联动中
                    if (dy < 0 && !this.canChildScrollUp()) {
                        this.mCurrentAction = 0;
                        this.isConfirm = true;
                        mTotalUnconsumed -= dy;
                        consumed[1] = dy;
                    } else if (dy > 0 && !this.canChildScrollDown()) {
                        consumed[1] = dy - (int) mTotalUnconsumed;
                        mTotalUnconsumed = 0;
                        this.mCurrentAction = 1;
                        this.isConfirm = true;
                    }
                }

                //滑动头部
                if (this.moveGuidanceView((float) (-dy))) {
//                if (this.moveGuidanceView((float) (-dy))) {
                    consumed[1] += dy;
//                    Log.i(TAG, "consumed: " + consumed);
                }

            }
        }
        // Now let our nested parent consume the leftovers
        final int[] parentConsumed = mParentScrollConsumed;

        if (dispatchNestedPreScroll(dx - consumed[0], dy - consumed[1], parentConsumed, null)) {
            consumed[0] += parentConsumed[0];
            consumed[1] += parentConsumed[1];
        }

    }

    private final int[] mParentScrollConsumed = new int[2];

    private boolean moveGuidanceView(float distanceY) {
        if (this.mRefreshing) {
            return false;
        } else {
            LayoutParams lp;
            if (!this.canChildScrollUp() && this.mPullRefreshEnable && this.mCurrentAction == 0) {
                lp = (LayoutParams) this.headerView.getLayoutParams();
                lp.height = (int) ((float) lp.height + distanceY);
                if (lp.height < 0) {
                    lp.height = 0;
                }

                if ((float) lp.height > this.guidanceViewFlowHeight) {
                    lp.height = (int) this.guidanceViewFlowHeight;
                }

                if (this.onRefreshListener != null) {
                    if ((float) lp.height >= this.guidanceViewHeight) {//高度大于 头部view 的高度   ----  释放刷新
                        this.onRefreshListener.onRefreshPulStateChange((float) lp.height / this.guidanceViewHeight, 2);
                    } else {//高度小于  头部view 的高度    下拉刷新
                        this.onRefreshListener.onRefreshPulStateChange((float) lp.height / this.guidanceViewHeight, 1);
                    }
                }

                if (lp.height == 0) {
                    this.isConfirm = false;
                    this.mCurrentAction = -1;
                }

                this.headerView.setLayoutParams(lp);
                this.headerView.setProgressRotation((float) lp.height / this.guidanceViewFlowHeight);
                this.moveTargetView((float) lp.height);
                return true;
            } else if (!this.canChildScrollDown() && this.mPullLoadEnable && this.mCurrentAction == 1) {
                lp = (LayoutParams) this.footerView.getLayoutParams();
                lp.height = (int) ((float) lp.height - distanceY);
                if (lp.height < 0) {
                    lp.height = 0;
                }

                if ((float) lp.height > this.guidanceViewFlowHeight) {
                    lp.height = (int) this.guidanceViewFlowHeight;
                }

                if (this.onRefreshListener != null) {
                    if ((float) lp.height >= this.guidanceViewHeight) {
                        this.onRefreshListener.onLoadmorePullStateChange((float) lp.height / this.guidanceViewHeight, 2);
                    } else {
                        this.onRefreshListener.onLoadmorePullStateChange((float) lp.height / this.guidanceViewHeight, 1);
                    }
                }

                if (lp.height == 0) {
                    this.isConfirm = false;
                    this.mCurrentAction = -1;
                }

                this.footerView.setLayoutParams(lp);
                this.footerView.setProgressRotation((float) lp.height / this.guidanceViewFlowHeight);
                this.moveTargetView((float) (-lp.height));
                return true;
            } else {
                return false;
            }
        }
    }

    private void moveTargetView(float h) {
        this.mTargetView.setTranslationY(h);
    }

    private void handlerAction() {
        if (!this.isRefreshing()) {
            this.isConfirm = false;
            LayoutParams lp;
            if (this.mPullRefreshEnable && this.mCurrentAction == 0) {
                lp = (LayoutParams) this.headerView.getLayoutParams();
                if ((float) lp.height >= this.guidanceViewHeight) {
                    this.startRefresh(lp.height);
                    if (this.onRefreshListener != null) {
                        this.onRefreshListener.onRefreshPulStateChange(1.0F, 3);
                    }
                } else if (lp.height > 0) {
                    this.resetHeaderView(lp.height);
                } else {
                    this.resetRefreshState();
                }
            }

            if (this.mPullLoadEnable && this.mCurrentAction == 1) {
                lp = (LayoutParams) this.footerView.getLayoutParams();
                if ((float) lp.height >= this.guidanceViewHeight) {
                    this.startLoadmore(lp.height);
                    if (this.onRefreshListener != null) {
                        this.onRefreshListener.onLoadmorePullStateChange(1.0F, 3);
                    }
                } else if (lp.height > 0) {
                    this.resetFootView(lp.height);
                } else {
                    this.resetLoadmoreState();
                }
            }

        }
    }

    private void startRefresh(int headerViewHeight) {
        this.mRefreshing = true;
        ValueAnimator animator = ValueAnimator.ofFloat((float) headerViewHeight, this.guidanceViewHeight);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                LayoutParams lp = (LayoutParams) MySwipeRefreshLayout.this.headerView.getLayoutParams();
                lp.height = (int) ((Float) animation.getAnimatedValue()).floatValue();
                MySwipeRefreshLayout.this.headerView.setLayoutParams(lp);
                MySwipeRefreshLayout.this.moveTargetView((float) lp.height);
            }
        });
        animator.addListener(new MySwipeRefreshLayout.WXRefreshAnimatorListener() {
            public void onAnimationEnd(Animator animation) {
                MySwipeRefreshLayout.this.headerView.startAnimation();
                if (MySwipeRefreshLayout.this.onRefreshListener != null) {
                    MySwipeRefreshLayout.this.onRefreshListener.onRefresh();
                }

            }
        });
        animator.setDuration(300L);
        animator.start();
    }

    private void resetHeaderView(int headerViewHeight) {
        this.headerView.stopAnimation();
        this.headerView.setStartEndTrim(0.0F, 0.75F);
        ValueAnimator animator = ValueAnimator.ofFloat((float) headerViewHeight, 0.0F);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                LayoutParams lp = (LayoutParams) MySwipeRefreshLayout.this.headerView.getLayoutParams();
                lp.height = (int) ((Float) animation.getAnimatedValue()).floatValue();
                MySwipeRefreshLayout.this.headerView.setLayoutParams(lp);
                MySwipeRefreshLayout.this.moveTargetView((float) lp.height);
            }
        });
        animator.addListener(new MySwipeRefreshLayout.WXRefreshAnimatorListener() {
            public void onAnimationEnd(Animator animation) {
                MySwipeRefreshLayout.this.resetRefreshState();
            }
        });
        animator.setDuration(300L);
        animator.start();
    }

    private void resetRefreshState() {
        this.mRefreshing = false;
        this.isConfirm = false;
        this.mCurrentAction = -1;
    }

    private void startLoadmore(int headerViewHeight) {
        this.mRefreshing = true;
        ValueAnimator animator = ValueAnimator.ofFloat((float) headerViewHeight, this.guidanceViewHeight);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                LayoutParams lp = (LayoutParams) MySwipeRefreshLayout.this.footerView.getLayoutParams();
                lp.height = (int) ((Float) animation.getAnimatedValue()).floatValue();
                MySwipeRefreshLayout.this.footerView.setLayoutParams(lp);
                MySwipeRefreshLayout.this.moveTargetView((float) (-lp.height));
            }
        });
        animator.addListener(new MySwipeRefreshLayout.WXRefreshAnimatorListener() {
            public void onAnimationEnd(Animator animation) {
                MySwipeRefreshLayout.this.footerView.startAnimation();
                if (MySwipeRefreshLayout.this.onRefreshListener != null) {
                    MySwipeRefreshLayout.this.onRefreshListener.onLoading();
                }

            }
        });
        animator.setDuration(300L);
        animator.start();
    }

    private void resetFootView(int headerViewHeight) {
        this.footerView.stopAnimation();
        this.footerView.setStartEndTrim(0.5F, 1.25F);
        ValueAnimator animator = ValueAnimator.ofFloat((float) headerViewHeight, 0.0F);
        animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                LayoutParams lp = (LayoutParams) MySwipeRefreshLayout.this.footerView.getLayoutParams();
                lp.height = (int) ((Float) animation.getAnimatedValue()).floatValue();
                MySwipeRefreshLayout.this.footerView.setLayoutParams(lp);
                MySwipeRefreshLayout.this.moveTargetView((float) (-lp.height));
            }
        });
        animator.addListener(new MySwipeRefreshLayout.WXRefreshAnimatorListener() {
            public void onAnimationEnd(Animator animation) {
                MySwipeRefreshLayout.this.resetLoadmoreState();
            }
        });
        animator.setDuration(300L);
        animator.start();
    }

    private void resetLoadmoreState() {
        this.mRefreshing = false;
        this.isConfirm = false;
        this.mCurrentAction = -1;
    }

    public boolean canChildScrollUp() {
        if (this.mTargetView == null) {
            return false;
        } else if (VERSION.SDK_INT >= 14) {
            return ViewCompat.canScrollVertically(this.mTargetView, -1);
        } else if (this.mTargetView instanceof AbsListView) {
            AbsListView absListView = (AbsListView) this.mTargetView;
            return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
        } else {
            return ViewCompat.canScrollVertically(this.mTargetView, -1) || this.mTargetView.getScrollY() > 0;
        }
    }

    public boolean canChildScrollDown() {
        if (this.mTargetView == null) {
            return false;
        } else if (VERSION.SDK_INT >= 14) {
            return ViewCompat.canScrollVertically(this.mTargetView, 1);
        } else if (this.mTargetView instanceof AbsListView) {
            AbsListView absListView = (AbsListView) this.mTargetView;
            if (absListView.getChildCount() <= 0) {
                return false;
            } else {
                int lastChildBottom = absListView.getChildAt(absListView.getChildCount() - 1).getBottom();
                return absListView.getLastVisiblePosition() == absListView.getAdapter().getCount() - 1 && lastChildBottom <= absListView.getMeasuredHeight();
            }
        } else {
            return ViewCompat.canScrollVertically(this.mTargetView, 1) || this.mTargetView.getScrollY() > 0;
        }
    }

    public float dipToPx(Context context, float value) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(1, value, metrics);
    }

    public void setOnRefreshListener(MySwipeRefreshLayout.SHSOnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public void finishRefresh() {
        if (this.mCurrentAction == 0) {
            this.resetHeaderView(this.headerView == null ? 0 : this.headerView.getMeasuredHeight());
        }

    }

    public void finishLoadmore() {
        if (this.mCurrentAction == 1) {
            this.resetFootView(this.footerView == null ? 0 : this.footerView.getMeasuredHeight());
        }

    }

    public boolean isLoadmoreEnable() {
        return this.mPullLoadEnable;
    }

    public void setLoadmoreEnable(boolean mPullLoadEnable) {
        this.mPullLoadEnable = mPullLoadEnable;
    }

    public boolean isRefreshEnable() {
        return this.mPullRefreshEnable;
    }

    public void setRefreshEnable(boolean mPullRefreshEnable) {
        this.mPullRefreshEnable = mPullRefreshEnable;
    }

    public boolean isRefreshing() {
        return this.mRefreshing;
    }

    public void setHeaderView(@LayoutRes int layoutResID) {
        this.headerView.setGuidanceView(layoutResID);
    }

    public void setHeaderView(View view) {
        this.headerView.setGuidanceView(view);
    }

    public void setFooterView(@LayoutRes int layoutResID) {
        this.footerView.setGuidanceView(layoutResID);
    }

    public void setFooterView(View view) {
        this.footerView.setGuidanceView(view);
    }

    public void setRefreshViewText(String text) {
        this.headerView.setText(text);
    }

    public void setLoaderViewText(String text) {
        this.footerView.setText(text);
    }


    static class WXRefreshAnimatorListener implements AnimatorListener {
        WXRefreshAnimatorListener() {
        }

        public void onAnimationStart(Animator animation) {
        }

        public void onAnimationEnd(Animator animation) {
        }

        public void onAnimationCancel(Animator animation) {
        }

        public void onAnimationRepeat(Animator animation) {
        }
    }

    public interface SHSOnRefreshListener {
        void onRefresh();

        void onLoading();

        void onRefreshPulStateChange(float var1, int var2);

        void onLoadmorePullStateChange(float var1, int var2);
    }


    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
//        Log.i(TAG, "setNestedScrollingEnabled: " + enabled);
//        Log.i(TAG, " getScrollingChildHelper() " + getScrollingChildHelper());
        getScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }


    @Override
    public boolean startNestedScroll(int axes) {
        return getScrollingChildHelper().startNestedScroll(axes);
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, int[] offsetInWindow) {
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow);
    }


    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return getScrollingChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getScrollingChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }


    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (mScrollingChildHelper == null) {
            mScrollingChildHelper = new NestedScrollingChildHelper(this);
        }
        return mScrollingChildHelper;
    }


    public int getNestedScrollAxes() {
        return this.mNestedScrollingParentHelper.getNestedScrollAxes();
    }


}
