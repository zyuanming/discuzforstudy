package net.discuz.source.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.*;
import android.widget.*;
import net.discuz.tools.Core;

public class DViewGroup extends ViewGroup
{

	public DViewGroup(Context context, AttributeSet attributeset)
	{
		this(context, attributeset, 0);
	}

	public DViewGroup(Context context, AttributeSet attributeset, int i)
	{
		super(context, attributeset, i);
		screenWidth = 0;
		displayAllowX = 75;
		statusX = 0;
		mScroller = new Scroller(context);
	}

	public void _disappear()
	{}

	public void _displaybtn()
	{}

	public void _isDisplay()
	{
		(new Thread()
		{

			public void run()
			{
				Message message = new Message();
				message.what = 2;
				handle.sendMessage(message);
				super.run();
			}
		}).start();
	}

	public void _isLoginButton(LinearLayout linearlayout)
	{
		(new Thread()
		{

			public void run()
			{
				Message message = new Message();
				message.what = 1;
				handle.sendMessage(message);
				super.run();
			}
		}).start();
	}

	public void _setImageBtn(ImageView imageview)
	{
		btn = imageview;
	}

	public void _setProfile(LinearLayout linearlayout)
	{
		profile = linearlayout;
	}

	public void computeScroll()
	{
		if (mScroller.computeScrollOffset())
		{
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}

	public Scroller getScroller()
	{
		return mScroller;
	}

	public void goLeft()
	{
		if (getScrollX() != displayAllowX)
		{
			statusX = 1;
			mScroller.startScroll(0, 0, displayAllowX, 0, 400);
			invalidate();
		}
	}

	public void goRight()
	{
		if (getScrollX() == displayAllowX)
		{
			statusX = 0;
			mScroller.startScroll(getScrollX(), 0, -displayAllowX, 0, 500);
			invalidate();
		}
	}

	public void init(Core core)
	{
		core._getDisplay((Activity) getContext()).getWidth();
		displayAllowX = 120;
	}

	protected void onLayout(boolean flag, int i, int j, int k, int l)
	{
		if (flag)
		{
			int i1 = getChildCount();
			int j1 = 0;
			int k1 = 0;
			for (; j1 < i1; j1++)
			{
				View view = getChildAt(j1);
				if (view.getVisibility() != 8)
				{
					int l1 = view.getMeasuredWidth();
					view.layout(k1, 0, k1 + l1, view.getMeasuredHeight());
					k1 += l1;
				}
			}

		}
	}

	protected void onMeasure(int i, int j)
	{
		super.onMeasure(i, j);
		int k = getChildCount();
		for (int l = 0; l < k; l++)
		{
			getChildAt(l).measure(i, j);
			Log.v("child",
					(new StringBuilder())
							.append(android.view.View.MeasureSpec.getSize(i))
							.append("").toString());
			screenWidth = screenWidth
					+ android.view.View.MeasureSpec.getSize(i);
		}

		Log.v("width", (new StringBuilder()).append(screenWidth).append("")
				.toString());
		scrollTo(0, 0);
	}

	private ImageView btn;
	public int displayAllowX;
	private Handler handle = new Handler()
	{

		public void handleMessage(Message message)
		{
			switch (message.what)
			{
			case 1:
				profile.setVisibility(0);
				break;
			case 2:
				profile.setVisibility(8);
				break;
			default:
				super.handleMessage(message);
				return;
			}
		}
	};
	private Scroller mScroller;
	private LinearLayout profile;
	private int screenWidth;
	public int statusX;
}
//2131296256