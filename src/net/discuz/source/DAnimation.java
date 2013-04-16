package net.discuz.source;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

public class DAnimation
{

	public DAnimation()
	{}

	public static Animation getRotateAnimation(float f, float f1, int i)
	{
		RotateAnimation rotateanimation = new RotateAnimation(f, f1, 1, 0.5F,
				1, 0.5F);
		rotateanimation.setDuration(i);
		rotateanimation.setFillAfter(true);
		return rotateanimation;
	}

	public static Animation getSectorRotateAnimation(float f, float f1, int i)
	{
		RotateAnimation rotateanimation = new RotateAnimation(f, f1, 2, 0.5F,
				2, 0.5F);
		rotateanimation.setDuration(i);
		rotateanimation.setFillAfter(true);
		rotateanimation.setInterpolator(new OvershootInterpolator(1.0F));
		return rotateanimation;
	}

	public static void startAnimationsIn(ViewGroup viewgroup)
	{
		viewgroup.setVisibility(0);
		for (int i = 0; i < viewgroup.getChildCount(); i++)
		{
			View view = viewgroup.getChildAt(i);
			view.setVisibility(0);
			view.setClickable(true);
			view.setFocusable(true);
			TranslateAnimation translateanimation = new TranslateAnimation(
					0.0F, 0.0F,
					-((android.view.ViewGroup.MarginLayoutParams) view
							.getLayoutParams()).topMargin - view.getHeight(),
					0.0F);
			translateanimation.setFillAfter(true);
			translateanimation.setDuration(200L);
			translateanimation.setStartOffset((i * 100)
					/ (-1 + viewgroup.getChildCount()));
			translateanimation.setInterpolator(new OvershootInterpolator(2.0F));
			view.startAnimation(translateanimation);
		}

	}

	public static void startAnimationsOut(final ViewGroup viewgroup)
	{
		for (int i = 0; i < viewgroup.getChildCount(); i++)
		{
			final View view = viewgroup.getChildAt(i);
			TranslateAnimation translateanimation = new TranslateAnimation(
					0.0F, 0.0F, 0.0F,
					-((android.view.ViewGroup.MarginLayoutParams) view
							.getLayoutParams()).topMargin - view.getHeight());
			translateanimation.setFillAfter(true);
			translateanimation.setDuration(200L);
			translateanimation
					.setStartOffset((100 * (viewgroup.getChildCount() - i))
							/ (-1 + viewgroup.getChildCount()));
			translateanimation
					.setAnimationListener(new android.view.animation.Animation.AnimationListener()
					{

						public void onAnimationEnd(Animation animation)
						{
							viewgroup.setVisibility(8);
							view.setClickable(false);
							view.setFocusable(false);
						}

						public void onAnimationRepeat(Animation animation)
						{}

						public void onAnimationStart(Animation animation)
						{}
					});
			view.startAnimation(translateanimation);
		}

	}
}
//2131296256