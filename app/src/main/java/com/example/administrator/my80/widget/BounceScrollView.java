package com.example.administrator.my80.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.luoxx.xxlib.weidet.D;


/**
 * Created by Administrator on 2017/5/22.
 */

public class BounceScrollView extends ScrollView {


    private View innerView;


    public BounceScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        D.e("======BounceScrollView=======");
    }

    public BounceScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        D.e("======BounceScrollView=======");
    }


    /***
     * 根据 XML 生成视图工作完成.该函数在生成视图的最后调用，在所有子视图添加完之后. 即使子类覆盖了 onFinishInflate
     * 方法，也应该调用父类的方法，使该方法得以执行.
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            D.i("======onFinishInflate=======");
            innerView = getChildAt(0);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (innerView != null) {
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }


    private float y;// 点击时y坐标

    /**
     * 开启 触摸事件拦截
     *
     * @param ev
     */
    private void commOnTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                D.i("==dowm==");
                break;
            case MotionEvent.ACTION_MOVE:
                final float preY = y;// 按下时的y坐标
                float nowY = ev.getY();// 时时y坐标
                int deltaY = (int) (preY - nowY);// 滑动距离
                D.e("==move==");
                y = nowY;
                if (isNeedMove()) {
                    // 初始化头部矩形
                    if (normal.isEmpty()) {//为空的时候初始化  记录下第一个viewgroup 的矩形位置
                        // 保存正常的布局位置
                        normal.set(innerView.getLeft(), innerView.getTop(),
                                innerView.getRight(), innerView.getBottom());
                    } else {

                        // 移动布局
                        innerView.layout(innerView.getLeft(), innerView.getTop() - deltaY / 2,
                                innerView.getRight(), innerView.getBottom() - deltaY / 2);
                    }
                    isCount = true;

                }


                break;
            case MotionEvent.ACTION_UP:
                // 手指松开.
                if (isNeedAnimation()) {
                    animation();
                    isCount = false;
                }
                D.i("==up==");
                break;

        }
    }

    private void animation() {
        // 开启移动动画
        TranslateAnimation ta = new TranslateAnimation(0, 0, innerView.getTop(),
                normal.top);
        ta.setDuration(200);
        innerView.startAnimation(ta);
        // 设置回到正常的布局位置
        innerView.layout(normal.left, normal.top, normal.right, normal.bottom);
        normal.setEmpty();
    }

    private Rect normal = new Rect();// 矩形(这里只是个形式，只是用于判断是否需要动画.)
    private boolean isCount;

    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    /***
     * 是否需要移动布局 inner.getMeasuredHeight():获取的是控件的总高度
     *
     * getHeight()：获取的是屏幕的高度
     *
     * @return
     */
    public boolean isNeedMove() {
        int offset = innerView.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        // 0是顶部，后面那个是底部
        return scrollY == 0 || scrollY == offset;
    }


}
