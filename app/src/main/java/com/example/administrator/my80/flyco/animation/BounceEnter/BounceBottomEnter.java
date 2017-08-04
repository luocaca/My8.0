package com.example.administrator.my80.flyco.animation.BounceEnter;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import com.example.administrator.my80.flyco.animation.BaseAnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class BounceBottomEnter extends BaseAnimatorSet {
	public BounceBottomEnter() {
		duration = 1000;
	}

	@Override
	public void setAnimation(View view) {
		DisplayMetrics dm = view.getContext().getResources().getDisplayMetrics();
		animatorSet.playTogether(ObjectAnimator.ofFloat(view, "alpha", 0, 1, 1, 1),//
				ObjectAnimator.ofFloat(view, "translationY", 250 * dm.density, -30, 10, 0));
	}

	@Override
	public BaseAnimatorSet interpolator(Interpolator interpolator) {
		return super.interpolator(new OvershootInterpolator());
	}
}
