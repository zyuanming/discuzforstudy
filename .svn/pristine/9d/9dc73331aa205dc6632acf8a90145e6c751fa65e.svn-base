package net.discuz.source.widget;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.discuz.source.DEBUG;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class HorizontalPager extends ViewGroup
{
	public static interface OnCurrentPageCallback
	{

		public abstract void onCurrentPageFinish(int i);
	}

	public static interface OnScrollListener
	{

		public abstract void onScroll(int i);

		public abstract void onViewScrollFinished(int i);
	}

	public static class SavedState extends android.view.View.BaseSavedState
	{

		public void writeToParcel(Parcel parcel, int i)
		{
			super.writeToParcel(parcel, i);
			parcel.writeInt(currentScreen);
		}

		public static final android.os.Parcelable.Creator CREATOR = new android.os.Parcelable.Creator()
		{

			public Object createFromParcel(Parcel parcel)
			{
				return new SavedState(parcel);
			}

			public Object[] newArray(int i)
			{
				return new SavedState[i];
			}

		};
		int currentScreen;

		private SavedState(Parcel parcel)
		{
			super(parcel);
			currentScreen = -1;
			currentScreen = parcel.readInt();
		}

		SavedState(Parcelable parcelable)
		{
			super(parcelable);
			currentScreen = -1;
		}
	}

	public HorizontalPager(Context context, AttributeSet attributeset)
	{
		this(context, attributeset, 0);
	}

	public HorizontalPager(Context context, AttributeSet attributeset, int i)
	{
		super(context, attributeset, i);
		mFirstLayout = true;
		mNextPage = -1;
		mTouchState = 0;
		mListeners = new HashSet();
		mCallback = new HashSet();
		TypedArray typedarray = context
				.obtainStyledAttributes(
						attributeset,
						net.discuz.R.styleable.net_discuz_source_widget_HorizontalPager);
		pageWidthSpec = typedarray.getDimensionPixelSize(0, -1);
		typedarray.recycle();
		init();
	}

	private void checkStartScroll(float f, float f1)
	{
		int i = (int) Math.abs(f - mLastMotionX);
		int j = (int) Math.abs(f1 - mLastMotionY);
		boolean flag;
		boolean flag1;
		if (i > mTouchSlop)
			flag = true;
		else
			flag = false;
		if (j > mTouchSlop)
			flag1 = true;
		else
			flag1 = false;
		if (flag || flag1)
		{
			if (flag)
			{
				mTouchState = 1;
				enableChildrenCache();
			}
			if (mAllowLongPress)
			{
				mAllowLongPress = false;
				getChildAt(mCurrentPage).cancelLongPress();
			}
		}
	}

	private int getScrollXForPage(int i)
	{
		return i * pageWidth - pageWidthPadding();
	}

	private void init()
	{
		mScroller = new Scroller(getContext());
		mCurrentPage = 1;
		ViewConfiguration viewconfiguration = ViewConfiguration
				.get(getContext());
		mTouchSlop = viewconfiguration.getScaledTouchSlop();
		mMaximumVelocity = viewconfiguration.getScaledMaximumFlingVelocity();
	}

	private void snapToDestination()
	{
		int i;
		int j;
		i = getScrollXForPage(mCurrentPage);
		j = mCurrentPage;
		if (getScrollX() >= i - getWidth() / 8)
		{
			if (getScrollX() > i + getWidth() / 8)
				j = Math.min(-1 + getChildCount(), j + 1);
		} else
		{
			j = Math.max(0, j - 1);
		}
		snapToPage(j);
		return;
	}

	public void addFocusables(ArrayList arraylist, int i)
	{
		getChildAt(mCurrentPage).addFocusables(arraylist, i);
		if (i == 17)
		{
			if (mCurrentPage > 0)
				getChildAt(-1 + mCurrentPage).addFocusables(arraylist, i);
		} else if (i == 66 && mCurrentPage < -1 + getChildCount())
		{
			getChildAt(1 + mCurrentPage).addFocusables(arraylist, i);
			return;
		}
	}

	public void addOnCurrentPageCallback(
			OnCurrentPageCallback oncurrentpagecallback)
	{
		mCallback.add(oncurrentpagecallback);
	}

	public void addOnScrollListener(OnScrollListener onscrolllistener)
	{
		mListeners.add(onscrolllistener);
	}

	public boolean allowLongPress()
	{
		return mAllowLongPress;
	}

	void clearChildrenCache()
	{
		setChildrenDrawnWithCacheEnabled(false);
	}

	public void computeScroll()
	{
		if (mScroller.computeScrollOffset())
		{
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		} else if (mNextPage != -1)
		{
			mCurrentPage = mNextPage;
			mNextPage = -1;
			clearChildrenCache();
			return;
		}
	}

	protected void dispatchDraw(Canvas canvas)
	{
		long l = getDrawingTime();
		int i = getChildCount();
		for (int j = 0; j < i; j++)
			drawChild(canvas, getChildAt(j), l);

		Iterator iterator = mListeners.iterator();
		do
		{
			if (!iterator.hasNext())
				break;
			OnScrollListener onscrolllistener = (OnScrollListener) iterator
					.next();
			int k = getScrollX() + pageWidthPadding();
			onscrolllistener.onScroll(k);
			if (k % pageWidth == 0)
				onscrolllistener.onViewScrollFinished(k / pageWidth);
		} while (true);
	}

	public boolean dispatchUnhandledMove(View view, int i)
	{
		if (i == 17)
		{
			if (getCurrentPage() > 0)
			{
				snapToPage(-1 + getCurrentPage());
				return true;
			}
		} else if (i == 66 && getCurrentPage() < -1 + getChildCount())
		{
			snapToPage(1 + getCurrentPage());
			return true;
		}
		return super.dispatchUnhandledMove(view, i);
	}

	void enableChildrenCache()
	{
		setChildrenDrawingCacheEnabled(true);
		setChildrenDrawnWithCacheEnabled(true);
	}

	int getCurrentPage()
	{
		return mCurrentPage;
	}

	public int getPageWidth()
	{
		return pageWidth;
	}

	public int getScreenForView(View view)
	{
		if (view != null)
		{
			android.view.ViewParent viewparent = view.getParent();
			int i = getChildCount();
			for (int j = 0; j < i; j++)
				if (viewparent == getChildAt(j))
					return j;

		}
		return -1;
	}

	public boolean onInterceptTouchEvent(MotionEvent motionevent)
	{
		int i = motionevent.getAction();
		if (i != 2 || mTouchState == 0)
		{
			float f;
			float f1;
			f = motionevent.getX();
			f1 = motionevent.getY();
			switch (i)
			{
			case 0:
				mLastMotionX = f;
				mLastMotionY = f1;
				mAllowLongPress = true;
				int j;
				if (mScroller.isFinished())
					j = 0;
				else
					j = 1;
				mTouchState = j;
				if (mTouchState == 0)
					return false;
				break;
			case 1:
			case 3:
				clearChildrenCache();
				mTouchState = 0;
				if (mTouchState == 0)
					return false;
				break;
			case 2:
				if (mTouchState == 0)
					checkStartScroll(f, f1);
				if (mTouchState == 0)
					return false;
				break;
			default:
				return false;
			}
		} else
		{
			DEBUG.o("DeezApps.Widget.HorizontalPageronInterceptTouchEvent::shortcut=true");
		}
		return true;
	}

	protected void onLayout(boolean flag, int i, int j, int k, int l)
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

	protected void onMeasure(int i, int j)
	{
		super.onMeasure(i, j);
		int k;
		int l;
		if (pageWidthSpec == -1)
			k = getMeasuredWidth();
		else
			k = pageWidthSpec;
		pageWidth = k;
		pageWidth = Math.min(pageWidth, getMeasuredWidth());
		l = getChildCount();
		for (int i1 = 0; i1 < l; i1++)
			getChildAt(i1).measure(
					android.view.View.MeasureSpec.makeMeasureSpec(pageWidth,
							0x40000000), j);

		if (mFirstLayout)
		{
			scrollTo(getScrollXForPage(mCurrentPage), 0);
			mFirstLayout = false;
		}
	}

	protected boolean onRequestFocusInDescendants(int i, Rect rect)
	{
		int j;
		if (mNextPage != -1)
			j = mNextPage;
		else
			j = mCurrentPage;
		getChildAt(j).requestFocus(i, rect);
		return false;
	}

	protected void onRestoreInstanceState(Parcelable parcelable)
	{
		SavedState savedstate = (SavedState) parcelable;
		super.onRestoreInstanceState(savedstate.getSuperState());
		if (savedstate.currentScreen != -1)
			mCurrentPage = savedstate.currentScreen;
	}

	protected Parcelable onSaveInstanceState()
	{
		SavedState savedstate = new SavedState(super.onSaveInstanceState());
		savedstate.currentScreen = mCurrentPage;
		return savedstate;
	}

	public boolean onTouchEvent(MotionEvent motionevent)
	{
		int i;
		float f;
		float f1;
		if (mVelocityTracker == null)
			mVelocityTracker = VelocityTracker.obtain();
		mVelocityTracker.addMovement(motionevent);
		i = motionevent.getAction();
		f = motionevent.getX();
		f1 = motionevent.getY();
		switch (i)
		{
		case 0:
			if (!mScroller.isFinished())
				mScroller.abortAnimation();
			mLastMotionX = f;
			return true;
		case 1:
			if (mTouchState == 1)
			{
				VelocityTracker velocitytracker = mVelocityTracker;
				velocitytracker.computeCurrentVelocity(1000, mMaximumVelocity);
				int j = (int) velocitytracker.getXVelocity();
				if (j > 1000 && mCurrentPage > 0)
					snapToPage(-1 + mCurrentPage);
				else if (j < -1000 && mCurrentPage < -1 + getChildCount())
					snapToPage(1 + mCurrentPage);
				else
					snapToDestination();
				if (mVelocityTracker != null)
				{
					mVelocityTracker.recycle();
					mVelocityTracker = null;
				}
			}
			mTouchState = 0;
			return true;
		case 2:
			if (mTouchState == 0)
			{
				checkStartScroll(f, f1);
				return true;
			}
			if (mTouchState == 1)
			{
				int k = (int) (mLastMotionX - f);
				mLastMotionX = f;
				if (getScrollX() < 0
						|| getScrollX() > getChildAt(-1 + getChildCount())
								.getLeft())
					k /= 2;
				scrollBy(k, 0);
				return true;
			}
			return true;
		case 3:
			mTouchState = 0;
			return true;
		default:
			return true;
		}
	}

	int pageWidthPadding()
	{
		return (getMeasuredWidth() - pageWidth) / 2;
	}

	public void removeOnScrollListener(OnScrollListener onscrolllistener)
	{
		mListeners.remove(onscrolllistener);
	}

	public boolean requestChildRectangleOnScreen(View view, Rect rect,
			boolean flag)
	{
		return indexOfChild(view) != mCurrentPage || !mScroller.isFinished();
	}

	public void scrollLeft()
	{
		if (mNextPage == -1 && mCurrentPage > 0 && mScroller.isFinished())
			snapToPage(-1 + mCurrentPage);
	}

	public void scrollRight()
	{
		if (mNextPage == -1 && mCurrentPage < -1 + getChildCount()
				&& mScroller.isFinished())
			snapToPage(1 + mCurrentPage);
	}

	public void setCurrentPage(int i)
	{
		mCurrentPage = Math.max(0, Math.min(i, getChildCount()));
		scrollTo(getScrollXForPage(mCurrentPage), 0);
		invalidate();
	}

	public void setPageWidth(int i)
	{
		pageWidthSpec = i;
	}

	void snapToPage(int i)
	{
		enableChildrenCache();
		boolean flag;
		View view;
		int j;
		if (i != mCurrentPage)
			flag = true;
		else
			flag = false;
		mNextPage = i;
		view = getFocusedChild();
		if (view != null && flag && view == getChildAt(mCurrentPage))
			view.clearFocus();
		j = getScrollXForPage(i) - getScrollX();
		mScroller.startScroll(getScrollX(), 0, j, 0, 2 * Math.abs(j));
		invalidate();
		for (Iterator iterator = mCallback.iterator(); iterator.hasNext(); ((OnCurrentPageCallback) iterator
				.next()).onCurrentPageFinish(i))
			;
	}

	private static final int INVALID_SCREEN = -1;
	private static final int SNAP_VELOCITY = 1000;
	public static final int SPEC_UNDEFINED = -1;
	public static final String TAG = "DeezApps.Widget.HorizontalPager";
	private static final int TOUCH_STATE_REST = 0;
	private static final int TOUCH_STATE_SCROLLING = 1;
	private boolean mAllowLongPress;
	private Set mCallback;
	private int mCurrentPage;
	private boolean mFirstLayout;
	private float mLastMotionX;
	private float mLastMotionY;
	private Set mListeners;
	private int mMaximumVelocity;
	private int mNextPage;
	private Scroller mScroller;
	private int mTouchSlop;
	private int mTouchState;
	private VelocityTracker mVelocityTracker;
	private int pageWidth;
	private int pageWidthSpec;
}
// 2131296256