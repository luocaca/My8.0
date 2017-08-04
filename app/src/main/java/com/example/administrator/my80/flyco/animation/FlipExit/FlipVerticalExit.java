package com.example.administrator.my80.flyco.animation.FlipExit;

import android.view.View;
import com.example.administrator.my80.flyco.animation.BaseAnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class FlipVerticalExit extends BaseAnimatorSet {
	@Override
	public void setAnimation(View view) {
		animatorSet.playTogether(ObjectAnimator.ofFloat(view, "rotationX", 0, 90),//
				ObjectAnimator.ofFloat(view, "alpha", 1, 0));
	}
}
