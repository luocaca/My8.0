package com.example.administrator.my80.flyco.animation.SlideExit;

import android.util.DisplayMetrics;
import android.view.View;

import com.example.administrator.my80.flyco.animation.BaseAnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class SlideRightExit extends BaseAnimatorSet {
	@Override
	public void setAnimation(View view) {
		DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
		animatorSet.playTogether(//
				ObjectAnimator.ofFloat(view, "translationX", 0, 250 * dm.density), //
				ObjectAnimator.ofFloat(view, "alpha", 1, 0));
	}
}