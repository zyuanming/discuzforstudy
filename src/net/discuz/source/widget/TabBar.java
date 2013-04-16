package net.discuz.source.widget;

import java.util.ArrayList;

import net.discuz.R;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class TabBar extends RadioGroup
{
	public static interface OnTabClickListener
	{

		public abstract void onclick();
	}

	public TabBar(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
		addNum = 0;
		clickListeners = new ArrayList();
		arrowsView = null;
		activity = (Activity) context;
		setOrientation(7);
	}

	private Button createButton()
	{
		Button button = (Button) activity.getLayoutInflater().inflate(
				R.layout.tabbar_button, null);
		button.setLayoutParams(new android.widget.RadioGroup.LayoutParams(-2,
				-2, 1.0F));
		button.setId(addNum);
		addNum = 1 + addNum;
		return button;
	}

	private RadioButton createRadioButton()
	{
		RadioButton radiobutton = (RadioButton) activity.getLayoutInflater()
				.inflate(R.layout.tabbar_radiobutton, null);
		radiobutton.setLayoutParams(new android.widget.RadioGroup.LayoutParams(
				-2, -2, 1.0F));
		radiobutton.setId(addNum);
		if (addNum == 0)
			radiobutton.setChecked(true);
		addNum = 1 + addNum;
		return radiobutton;
	}

	public static int dip2px(Context context, float f)
	{
		return (int) (0.5F + f
				* context.getResources().getDisplayMetrics().density);
	}

	public static int px2dip(Context context, float f)
	{
		return (int) (0.5F + f
				/ context.getResources().getDisplayMetrics().density);
	}

	public void addArrows(View view)
	{
		arrowsView = view;
	}

	public void addButton(int i, int j, int k,
			android.view.View.OnClickListener onclicklistener)
	{
		addButton(activity.getString(i), j, k, onclicklistener);
	}

	public void addButton(String s, int i, int j,
			android.view.View.OnClickListener onclicklistener)
	{
		setOnTabClickListener(null);
		Button button = createButton();
		button.setText(s);
		button.setCompoundDrawablesWithIntrinsicBounds(0, i, 0, 0);
		button.setBackgroundResource(j);
		button.setOnClickListener(onclicklistener);
		addView(button);
		setWeightSum(addNum);
	}

	public void addTab(int i, int j, OnTabClickListener ontabclicklistener)
	{
		addTab(activity.getString(i), j, ontabclicklistener);
	}

	public void addTab(String s, int i, int j,
			OnTabClickListener ontabclicklistener)
	{
		setOnTabClickListener(ontabclicklistener);
		RadioButton radiobutton = createRadioButton();
		radiobutton.setText(s);
		radiobutton.setCompoundDrawablesWithIntrinsicBounds(0, i, 0, 0);
		if (j != 0)
			radiobutton.setBackgroundResource(j);
		else
			radiobutton.setBackgroundResource(R.drawable.alpha_bg);
		addView(radiobutton);
		setWeightSum(addNum);
	}

	public void addTab(String s, int i, OnTabClickListener ontabclicklistener)
	{
		addTab(s, i, 0, ontabclicklistener);
	}

	protected void onLayout(boolean flag, int i, int j, int k, int l)
	{
		super.onLayout(flag, i, j, k, l);
		if (arrowsView != null)
		{
			int i1 = getChildAt(getCheckedRadioButtonId()).getWidth() / 2
					- arrowsView.getWidth() / 2;
			arrowsView.layout(i1, arrowsView.getTop(),
					i1 + arrowsView.getWidth(), arrowsView.getBottom());
		}
	}

	public void refreshArrowsView(int i)
	{
		RadioButton radiobutton;
		label0:
		{
			if (arrowsView != null)
			{
				radiobutton = (RadioButton) getChildAt(i);
				if (radiobutton != null)
					break label0;
			}
			return;
		}
		final int halfWidth = radiobutton.getWidth() / 2
				- arrowsView.getWidth() / 2;
		final int fromLeft = radiobutton.getLeft();
		int j = arrowsView.getLeft();
		TranslateAnimation translateanimation = new TranslateAnimation(0.0F,
				(fromLeft + halfWidth) - j, arrowsView.getTop(),
				arrowsView.getTop());
		translateanimation.setInterpolator(new DecelerateInterpolator());
		translateanimation.setFillEnabled(true);
		translateanimation.setDuration(400L);
		translateanimation
				.setAnimationListener(new android.view.animation.Animation.AnimationListener()
				{

					public void onAnimationEnd(Animation animation)
					{
						arrowsView.layout(fromLeft + halfWidth,
								arrowsView.getTop(), fromLeft + halfWidth
										+ arrowsView.getWidth(),
								arrowsView.getBottom());
					}

					public void onAnimationRepeat(Animation animation)
					{}

					public void onAnimationStart(Animation animation)
					{}
				});
		arrowsView.startAnimation(translateanimation);
	}

	public void setFirstCheckedItem(int i)
	{
		check(i);
		if (clickListeners.get(i) != null)
			((OnTabClickListener) clickListeners.get(i)).onclick();
	}

	public void setOnTabClickListener(OnTabClickListener ontabclicklistener)
	{
		clickListeners.add(ontabclicklistener);
	}

	public void startListener()
	{
		setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener()
		{

			public void onCheckedChanged(RadioGroup radiogroup, int i)
			{
				refreshArrowsView(i);
				if (clickListeners.get(i) != null)
					((OnTabClickListener) clickListeners.get(i)).onclick();
			}
		});
	}

	private final int ArrowAnimationTime = 400;
	private Activity activity;
	private int addNum;
	private View arrowsView;
	private ArrayList clickListeners;

}
// 2131296256