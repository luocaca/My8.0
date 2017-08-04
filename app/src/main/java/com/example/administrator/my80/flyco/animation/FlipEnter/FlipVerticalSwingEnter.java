package com.example.administrator.my80.flyco.animation.FlipEnter;

import android.view.View;
import com.example.administrator.my80.flyco.animation.BaseAnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class FlipVerticalSwingEnter extends BaseAnimatorSet {
	public FlipVerticalSwingEnter() {
		duration = 2;
	}

	@Override
	public void setAnimation(View view) {
		animatorSet.playTogether(//
				ObjectAnimator.ofFloat(view, "rotationY", -90, -10, 10, 90,0,20,60,90,-10,-20,-50,0),//
				ObjectAnimator.ofFloat(view, "rotationX", -90, -10, 10, 90,0,20,60,90,-10,-20,-50,0),//
//				ObjectAnimator.ofFloat(view, "rotationY", 90, -10, 10, 0),
				ObjectAnimator.ofFloat(view, "alpha", 0.25f, 0.5f, 0.75f, 1));
	}
}
