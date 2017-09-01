/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package com.luoxx.xxlib.weidet;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xxlib.R;

import java.util.Date;

public class SwipeViewHeader extends LinearLayout implements BaseRefreshHeader {
    private RelativeLayout mContainer;
    private ImageView mArrowImageView;
    private ProgressBar mProgressBar;
    private TextView mHintTextView;
    private int mState = STATE_NORMAL;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;

    private final int ROTATE_ANIM_DURATION = 180;


    private int mMeasuredHeight;
    private ImageView mIvState;
    private TextView xlistview_header_time;//头部显示的时间

    public SwipeViewHeader(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public SwipeViewHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        // 初始情况，设置下拉刷新view高度为0

        LayoutInflater.from(context).inflate(R.layout.swipe_rc_view_header, this);
        mContainer = (RelativeLayout) findViewById(R.id.xlistview_header_content);
        mMeasuredHeight = getMeasuredHeight();

        mArrowImageView = (ImageView) findViewById(R.id.xlistview_header_arrow);
        mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);
        xlistview_header_time = (TextView) findViewById(R.id.xlistview_header_time);
        mProgressBar = (ProgressBar) findViewById(R.id.xlistview_header_progressbar);
        mIvState = (ImageView) findViewById(R.id.xlistview_header_state);

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);

        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
        setGravity(Gravity.CENTER);
        mContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

    public void setState(int state) {
        if (state == mState) return;
        Log.e("=====", state + "");

        if (state == STATE_REFRESHING) {    // 显示进度
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
            mIvState.setVisibility(GONE);
        } else if (state == STATE_DONE) {
            mArrowImageView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
            mIvState.setVisibility(VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mArrowImageView.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            }, 1000);
        } else {    // 显示箭头图片
            mArrowImageView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
            mIvState.setVisibility(GONE);
        }

        switch (state) {
            case STATE_NORMAL:
                if (mState == STATE_RELEASE_TO_REFRESH) {
                    mArrowImageView.startAnimation(mRotateDownAnim);
                }
                if (mState == STATE_REFRESHING) {
                    mArrowImageView.clearAnimation();
                }
                mHintTextView.setText(R.string.listview_header_hint_normal);
                break;
            case STATE_RELEASE_TO_REFRESH:
                if (mState != STATE_RELEASE_TO_REFRESH) {
                    mArrowImageView.clearAnimation();
                    mArrowImageView.startAnimation(mRotateUpAnim);
                    mHintTextView.setText(R.string.listview_header_hint_release);
                }
                break;
            case STATE_REFRESHING:
                mHintTextView.setText(R.string.refreshing);
                break;
            case STATE_DONE:
                mHintTextView.setText(R.string.refresh_done);
                xlistview_header_time.setText(new Date().toLocaleString());
                break;
            default:
        }

        mState = state;
    }

    public void setVisiableHeight(int height) {
        if (height < 0)
            height = 0;
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    @Override
    public void onMove(float delta) {
        if (getVisiableHeight() > 0 || delta > 0) {
            setVisiableHeight((int) delta + getVisiableHeight());
            if (mState <= STATE_RELEASE_TO_REFRESH) { // 未处于刷新状态，更新箭头
                if (getVisiableHeight() > mMeasuredHeight) {
                    setState(STATE_RELEASE_TO_REFRESH);
                } else {
                    setState(STATE_NORMAL);
                }
            }


        }
    }

    @Override
    public boolean releaseAction() {
        boolean isOnRefresh = false;
        int height = getVisiableHeight();
        if (height == 0) // not visible.
            isOnRefresh = false;

        if (getVisiableHeight() > mMeasuredHeight && mState < STATE_REFRESHING) {
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        // refreshing and header isn't shown fully. do nothing.
        if (mState == STATE_REFRESHING && height <= mMeasuredHeight) {
            //return;
        }
        int destHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mState == STATE_REFRESHING) {
            destHeight = mMeasuredHeight;
        }
        smoothScrollTo(destHeight);

        return isOnRefresh;

    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisiableHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisiableHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    @Override
    public void refreshComplate() {
        setState(STATE_DONE);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                reset();
            }
        }, 500);
    }

    public void reset() {
        smoothScrollTo(0);
        setState(STATE_NORMAL);
    }

    public int getVisiableHeight() {
        return mContainer.getHeight();
    }


    public int getState() {
        return mState;
    }

}
