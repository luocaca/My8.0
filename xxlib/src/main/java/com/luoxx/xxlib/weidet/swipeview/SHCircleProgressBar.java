package com.luoxx.xxlib.weidet.swipeview;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

import com.example.xxlib.R;


public class SHCircleProgressBar extends ImageView {
    private static final int KEY_SHADOW_COLOR = 503316480;
    private static final int FILL_SHADOW_COLOR = 1023410176;
    private static final float X_OFFSET = 0.0F;
    private static final float Y_OFFSET = 1.75F;
    private static final float SHADOW_RADIUS = 3.5F;
    private static final int SHADOW_ELEVATION = 4;
    public static final int DEFAULT_CIRCLE_BG_LIGHT = -328966;
    public static final int DEFAULT_CIRCLE_COLOR = -1048576;
    private static final int DEFAULT_CIRCLE_DIAMETER = 40;
    private static final int STROKE_WIDTH_LARGE = 3;
    public static final int DEFAULT_TEXT_SIZE = 9;
    private AnimationListener mListener;
    private int mShadowRadius;
    private int mBackGroundColor;
    private int mProgressColor;
    private int mProgressStokeWidth;
    private int mArrowWidth;
    private int mArrowHeight;
    private int mProgress;
    private int mMax;
    private int mDiameter;
    private int mInnerRadius;
    private boolean mShowArrow;
    public MaterialProgressDrawable mProgressDrawable;
    private ShapeDrawable mBgCircle;
    private boolean mCircleBackgroundEnabled;
    private int[] mColors = new int[]{-16777216};

    public SHCircleProgressBar(Context context) {
        super(context);
        this.init(context, (AttributeSet)null, 0);
    }

    public SHCircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs, 0);
    }

    public SHCircleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SHCircleProgressBar, defStyleAttr, 0);
        float density = this.getContext().getResources().getDisplayMetrics().density;
        this.mBackGroundColor = a.getColor(R.styleable.SHCircleProgressBar_background_color, -328966);
        this.mProgressColor = a.getColor(R.styleable.SHCircleProgressBar_progress_color, -1048576);
        this.mColors = new int[]{this.mProgressColor};
        this.mInnerRadius = a.getDimensionPixelOffset(R.styleable.SHCircleProgressBar_inner_radius, -1);
        this.mProgressStokeWidth = a.getDimensionPixelOffset(R.styleable.SHCircleProgressBar_progress_stoke_width, (int)(3.0F * density));
        this.mArrowWidth = a.getDimensionPixelOffset(R.styleable.SHCircleProgressBar_arrow_width, -1);
        this.mArrowHeight = a.getDimensionPixelOffset(R.styleable.SHCircleProgressBar_arrow_height, -1);
        this.mShowArrow = a.getBoolean(R.styleable.SHCircleProgressBar_show_arrow, true);
        this.mCircleBackgroundEnabled = a.getBoolean(R.styleable.SHCircleProgressBar_enable_circle_background, true);
        this.mProgress = a.getInt(R.styleable.SHCircleProgressBar_progress, 0);
        this.mMax = a.getInt(R.styleable.SHCircleProgressBar_max, 100);
        a.recycle();
        this.mProgressDrawable = new MaterialProgressDrawable(this.getContext(), this);
        super.setImageDrawable(this.mProgressDrawable);
    }

    public void setProgressBackGroundColor(int color) {
        this.mBackGroundColor = color;
    }

    private boolean elevationSupported() {
        return VERSION.SDK_INT >= 21;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(!this.elevationSupported()) {
            this.setMeasuredDimension(this.getMeasuredWidth() + this.mShadowRadius * 2, this.getMeasuredHeight() + this.mShadowRadius * 2);
        }

    }

    public int getProgressStokeWidth() {
        return this.mProgressStokeWidth;
    }

    public void setProgressStokeWidth(int mProgressStokeWidth) {
        float density = this.getContext().getResources().getDisplayMetrics().density;
        this.mProgressStokeWidth = (int)((float)mProgressStokeWidth * density);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        float density = this.getContext().getResources().getDisplayMetrics().density;
        this.mDiameter = Math.min(this.getMeasuredWidth(), this.getMeasuredHeight());
        if(this.mDiameter <= 0) {
            this.mDiameter = (int)density * 40;
        }

        if(this.getBackground() == null && this.mCircleBackgroundEnabled) {
            int shadowYOffset = (int)(density * 1.75F);
            int shadowXOffset = (int)(density * 0.0F);
            this.mShadowRadius = (int)(density * 3.5F);
            if(this.elevationSupported()) {
                this.mBgCircle = new ShapeDrawable(new OvalShape());
                ViewCompat.setElevation(this, 4.0F * density);
            } else {
                SHCircleProgressBar.OvalShadow oval = new SHCircleProgressBar.OvalShadow(this.mShadowRadius, this.mDiameter - this.mShadowRadius * 2);
                this.mBgCircle = new ShapeDrawable(oval);
                ViewCompat.setLayerType(this, 1, this.mBgCircle.getPaint());
                this.mBgCircle.getPaint().setShadowLayer((float)this.mShadowRadius, (float)shadowXOffset, (float)shadowYOffset, 503316480);
                int padding = this.mShadowRadius;
                this.setPadding(padding, padding, padding, padding);
            }

            this.mBgCircle.getPaint().setColor(this.mBackGroundColor);
            this.setBackgroundDrawable(this.mBgCircle);
        }

        this.mProgressDrawable.setBackgroundColor(this.mBackGroundColor);
        this.mProgressDrawable.setColorSchemeColors(this.mColors);
        if(this.isShowArrow()) {
            this.mProgressDrawable.setArrowScale(1.0F);
            this.mProgressDrawable.showArrow(true);
        }

        super.setImageDrawable((Drawable)null);
        super.setImageDrawable(this.mProgressDrawable);
        this.mProgressDrawable.setAlpha(255);
        if(this.getVisibility() == 0) {
            this.mProgressDrawable.setStartEndTrim(0.0F, 0.8F);
        }

    }

    public boolean isShowArrow() {
        return this.mShowArrow;
    }

    public void setShowArrow(boolean showArrow) {
        this.mShowArrow = showArrow;
    }

    public void setAnimationListener(AnimationListener listener) {
        this.mListener = listener;
    }

    public void onAnimationStart() {
        super.onAnimationStart();
        if(this.mListener != null) {
            this.mListener.onAnimationStart(this.getAnimation());
        }

    }

    public void onAnimationEnd() {
        super.onAnimationEnd();
        if(this.mListener != null) {
            this.mListener.onAnimationEnd(this.getAnimation());
        }

    }

    public void setColorSchemeColors(int... colors) {
        this.mColors = colors;
        if(this.mProgressDrawable != null) {
            this.mProgressDrawable.setColorSchemeColors(colors);
        }

    }

    public void setBackgroundColorResource(int colorRes) {
        if(this.getBackground() instanceof ShapeDrawable) {
            Resources res = this.getResources();
            ((ShapeDrawable)this.getBackground()).getPaint().setColor(res.getColor(colorRes));
        }

    }

    public void setBackgroundColor(int color) {
        if(this.getBackground() instanceof ShapeDrawable) {
            Resources res = this.getResources();
            ((ShapeDrawable)this.getBackground()).getPaint().setColor(color);
        }

    }

    public int getMax() {
        return this.mMax;
    }

    public void setMax(int max) {
        this.mMax = max;
    }

    public int getProgress() {
        return this.mProgress;
    }

    public void setProgress(int progress) {
        if(this.getMax() > 0) {
            this.mProgress = progress;
        }

        this.invalidate();
    }

    public boolean circleBackgroundEnabled() {
        return this.mCircleBackgroundEnabled;
    }

    public void setCircleBackgroundEnabled(boolean enableCircleBackground) {
        this.mCircleBackgroundEnabled = enableCircleBackground;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(this.mProgressDrawable != null) {
            this.mProgressDrawable.stop();
            this.mProgressDrawable.setVisible(this.getVisibility() == 0, false);
        }

    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(this.mProgressDrawable != null) {
            this.mProgressDrawable.stop();
            this.mProgressDrawable.setVisible(false, false);
        }

    }

    public void start() {
        this.mProgressDrawable.start();
    }

    public void setStartEndTrim(float startAngle, float endAngle) {
        this.mProgressDrawable.setStartEndTrim(startAngle, endAngle);
    }

    public void stop() {
        this.mProgressDrawable.stop();
    }

    public void setProgressRotation(float rotation) {
        this.mProgressDrawable.setProgressRotation(rotation);
    }

    private class OvalShadow extends OvalShape {
        private RadialGradient mRadialGradient;
        private int mShadowRadius;
        private Paint mShadowPaint = new Paint();
        private int mCircleDiameter;

        public OvalShadow(int shadowRadius, int circleDiameter) {
            this.mShadowRadius = shadowRadius;
            this.mCircleDiameter = circleDiameter;
            this.mRadialGradient = new RadialGradient((float)(this.mCircleDiameter / 2), (float)(this.mCircleDiameter / 2), (float)this.mShadowRadius, new int[]{1023410176, 0}, (float[])null, TileMode.CLAMP);
            this.mShadowPaint.setShader(this.mRadialGradient);
        }

        public void draw(Canvas canvas, Paint paint) {
            int viewWidth = SHCircleProgressBar.this.getWidth();
            int viewHeight = SHCircleProgressBar.this.getHeight();
            canvas.drawCircle((float)(viewWidth / 2), (float)(viewHeight / 2), (float)(this.mCircleDiameter / 2 + this.mShadowRadius), this.mShadowPaint);
            canvas.drawCircle((float)(viewWidth / 2), (float)(viewHeight / 2), (float)(this.mCircleDiameter / 2), paint);
        }
    }
}
