package net.discuz.source.widget;

import net.discuz.R;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SlidingDrawer;

public class WrapSlidingDrawer extends SlidingDrawer
{

	public WrapSlidingDrawer(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
		boolean flag = true;
		int i = attributeset.getAttributeIntValue("android", "orientation", 1);
		mTopOffset = attributeset.getAttributeIntValue("android", "topOffset",
				0);
		if (i != 1)
			flag = false;
		mVertical = flag;
	}

	public WrapSlidingDrawer(Context context, AttributeSet attributeset, int i)
	{
		super(context, attributeset, i);
		boolean flag = true;
		int j = attributeset.getAttributeIntValue("android", "orientation", 1);
		mTopOffset = attributeset.getAttributeIntValue("android", "topOffset",
				0);
		if (j != 1)
			flag = false;
		mVertical = flag;
	}

	protected void onFinishInflate()
	{
		super.onFinishInflate();
	}

	public boolean onInterceptTouchEvent(MotionEvent motionevent)
	{
		int[] _tmp = new int[2];
		int i = (int) motionevent.getX();
		int j = (int) motionevent.getRawY();
		View view = getHandle();
		int ai[] = new int[2];
		view.getLocationOnScreen(ai);
		if ((new Rect(ai[0], ai[1], ai[0] + view.getWidth(), ai[1]
				+ view.getHeight())).contains(i, j))
			view.setBackgroundResource(R.drawable.slidingdrawer_top_open_bg_replay);
		return super.onInterceptTouchEvent(motionevent);
	}

	protected void onMeasure(int i, int j)
	{
		int k;
		int l;
		int i1;
		int j1;
		int l1;
		int i2;
		View view;
		View view1;
		k = android.view.View.MeasureSpec.getMode(i);
		l = android.view.View.MeasureSpec.getSize(i);
		i1 = android.view.View.MeasureSpec.getMode(j);
		j1 = android.view.View.MeasureSpec.getSize(j);
		view = getHandle();
		view1 = getContent();
		measureChild(view, i, j);
		if (!mVertical)
		{
			int k1 = l - view.getMeasuredWidth() - mTopOffset;
			getContent().measure(
					android.view.View.MeasureSpec.makeMeasureSpec(k1, k), j);
			l1 = view.getMeasuredWidth() + mTopOffset
					+ view1.getMeasuredWidth();
			i2 = view1.getMeasuredHeight();
			if (view.getMeasuredHeight() > i2)
				i2 = view.getMeasuredHeight();
		} else
		{

			view1.measure(
					i,
					android.view.View.MeasureSpec.makeMeasureSpec(
							j1 - view.getMeasuredHeight() - mTopOffset, i1));
			int j2 = view.getMeasuredHeight() + mTopOffset
					+ view1.getMeasuredHeight();
			int k2 = view1.getMeasuredWidth();
			if (view.getMeasuredWidth() > k2)
				k2 = view.getMeasuredWidth();
			l1 = k2;
			i2 = j2;
		}
		setMeasuredDimension(l1, i2);
		return;
	}

	private int mTopOffset;
	private boolean mVertical;
}
// 2131296256