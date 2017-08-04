package com.example.administrator.my80.flyco.animation.ZoomEnter;

import android.view.View;
import android.view.View.MeasureSpec;

import com.example.administrator.my80.flyco.animation.BaseAnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class ZoomInTopEnter extends BaseAnimatorSet {
	public ZoomInTopEnter() {
		duration = 6000;
	}

	@Override
	public void setAnimation(View view) {
		view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		int h = view.getMeasuredHeight();

		animatorSet.playTogether(ObjectAnimator.ofFloat(view, "alpha", 0, 1, 1),//
				ObjectAnimator.ofFloat(view, "scaleX", 0.1f, 0.475f, 1),//
				ObjectAnimator.ofFloat(view, "scaleY", 0.1f, 0.475f, 1),//
				ObjectAnimator.ofFloat(view, "translationY", -h, 60, 0));
	}
}
