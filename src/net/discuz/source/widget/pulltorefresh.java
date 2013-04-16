package net.discuz.source.widget;

import java.io.IOException;

import net.discuz.R;
import net.discuz.tools.Core;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 下拉更新
 * 
 * @author Ming
 * 
 */
public class pulltorefresh extends FrameLayout implements
		android.view.GestureDetector.OnGestureListener
{
	public static interface OnLoading
	{

		public abstract void Loading();
	}

	public static interface UnLoaded
	{

		public abstract void unload();
	}

	public pulltorefresh(Context context1)
	{
		super(context1);
		firstitem = true;
		sumY = 0;
		Allow_Min_Touch_Height = 100;
		PULL_RATIO = 3.7F;
		PULL_DOWN = true;
		IS_PULL_STATE = false;
		refresh_header_position = 180;
		refresh_header = null;
		progressbar_loading = null;
		pull_txt = null;
		refresh_txt_default = "\u4E0B\u62C9\u5373\u53EF\u5237\u65B0...";
		refresh_txt_justdo = "\u677E\u5F00\u5237\u65B0...";
		refresh_txt_doing = "\u5237\u65B0\u4E2D...";
		listview = null;
		listview_childid = 1;
		arrowAnimationState = 0;
		arrowAnimationTime = 150;
		isRefresh = false;
		isLoading = false;
		loadedReturnAnimationTime = 160;
		onLoading = null;
		unLoaded = null;
		mediaPlayer = null;
		pullingMediaPlayer = null;
		beepListener = new android.media.MediaPlayer.OnCompletionListener()
		{

			public void onCompletion(MediaPlayer mediaplayer)
			{
				mediaplayer.seekTo(0);
			}
		};
		context = context1;
		mGestureDetector = new GestureDetector(this);
		initChild();
		initAnimation();
	}

	public pulltorefresh(Context context1, AttributeSet attributeset)
	{
		super(context1, attributeset);
		firstitem = true;
		sumY = 0;
		Allow_Min_Touch_Height = 100;
		PULL_RATIO = 3.7F;
		PULL_DOWN = true;
		IS_PULL_STATE = false;
		refresh_header_position = 180;
		refresh_header = null;
		progressbar_loading = null;
		pull_txt = null;
		refresh_txt_default = "\u4E0B\u62C9\u5373\u53EF\u5237\u65B0...";
		refresh_txt_justdo = "\u677E\u5F00\u5237\u65B0...";
		refresh_txt_doing = "\u5237\u65B0\u4E2D...";
		listview = null;
		listview_childid = 1;
		arrowAnimationState = 0;
		arrowAnimationTime = 150;
		isRefresh = false;
		isLoading = false;
		loadedReturnAnimationTime = 160;
		onLoading = null;
		unLoaded = null;
		mediaPlayer = null;
		pullingMediaPlayer = null;
		beepListener = new android.media.MediaPlayer.OnCompletionListener()
		{

			public void onCompletion(MediaPlayer mediaplayer)
			{
				mediaplayer.seekTo(0);
			}
		};
		context = context1;
		mGestureDetector = new GestureDetector(this);
		initChild();
		initAnimation();
		initBeepSound();
	}

	private MediaPlayer createMediaPlayer(int i)
	{
		MediaPlayer mediaplayer = new MediaPlayer();
		mediaplayer.setAudioStreamType(2);
		mediaplayer.setOnCompletionListener(beepListener);
		AssetFileDescriptor assetfiledescriptor = getResources()
				.openRawResourceFd(i);
		try
		{
			mediaplayer.setDataSource(assetfiledescriptor.getFileDescriptor(),
					assetfiledescriptor.getStartOffset(),
					assetfiledescriptor.getLength());
			assetfiledescriptor.close();
			mediaplayer.setVolume(1.1F, 1.1F);
			mediaplayer.prepare();
		} catch (IOException ioexception)
		{
			ioexception.printStackTrace();
			return null;
		}
		return mediaplayer;
	}

	private void initBeepSound()
	{
		mediaPlayer = createMediaPlayer(R.raw.shake);
		pullingMediaPlayer = createMediaPlayer(R.raw.pulling);
	}

	public void Loading()
	{
		if (onLoading != null)
			onLoading.Loading();
	}

	public Boolean _getIsPullState()
	{
		return Boolean.valueOf(IS_PULL_STATE);
	}

	public boolean dispatchTouchEvent(MotionEvent motionevent)
	{
		mGestureDetector.onTouchEvent(motionevent);
		return super.dispatchTouchEvent(motionevent);
	}

	public void initAnimation()
	{
		mFlipAnimation = new RotateAnimation(0.0F, -180F, 1, 0.5F, 1, 0.5F);
		mFlipAnimation.setInterpolator(new LinearInterpolator());
		mFlipAnimation.setDuration(arrowAnimationTime);
		mFlipAnimation.setFillAfter(true);
		mReverseFlipAnimation = new RotateAnimation(-180F, 0.0F, 1, 0.5F, 1,
				0.5F);
		mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
		mReverseFlipAnimation.setDuration(arrowAnimationTime);
		mReverseFlipAnimation.setFillAfter(true);
		mClearFlipAnimation = new RotateAnimation(-180F, 0.0F, 1, 0.5F, 1, 0.5F);
		mClearFlipAnimation.setInterpolator(new LinearInterpolator());
		mClearFlipAnimation.setDuration(arrowAnimationTime);
		mClearFlipAnimation.setFillAfter(true);
	}

	public void initChild()
	{
		if (Core.getInstance()._getDisplay((Activity) context).getHeight() <= 480)
			refresh_header_position = refresh_header_position / 2;
		refresh_header = LayoutInflater.from(context).inflate(
				R.layout.pulltorefresh_header, null);
		progressbar_loading = (ProgressBar) refresh_header
				.findViewById(R.id.pull_loading);
		pull_txt = (TextView) refresh_header.findViewById(R.id.pull_txt);
		pull_txt.setText("\u4E0B\u62C9\u5373\u53EF\u5237\u65B0...");
		addView(refresh_header);
		pull_arrow = (ImageView) refresh_header.findViewById(R.id.pull_arrow);
		pull_arrow.setVisibility(0);
	}

	public boolean isLoading()
	{
		return isLoading;
	}

	public void loadedReturnOnAsyncTask()
	{
		if (listview != null && listview.getWindowVisibility() == 0)
		{
			TranslateAnimation translateanimation = new TranslateAnimation(
					0.0F, 0.0F, listview.getTop(), 0.0F);
			translateanimation.setDuration(loadedReturnAnimationTime);
			translateanimation.setFillAfter(true);
			translateanimation
					.setAnimationListener(new android.view.animation.Animation.AnimationListener()
					{

						public void onAnimationEnd(Animation animation)
						{
							try
							{
								listview.layout(0, 0, getMeasuredWidth(),
										getMeasuredHeight());
							} catch (Exception exception)
							{
								exception.printStackTrace();
							}
							refresh_header.setVisibility(View.INVISIBLE);
							listview.clearAnimation();
						}

						public void onAnimationRepeat(Animation animation)
						{
						}

						public void onAnimationStart(Animation animation)
						{
						}
					});
			translateanimation.setInterpolator(AnimationUtils.loadInterpolator(
					context, 0x10a0006));
			listview.startAnimation(translateanimation);
		}
		if (mediaPlayer != null)
			mediaPlayer.start();
		isRefresh = false;
		PULL_DOWN = false;
		isLoading = false;
		pull_arrow.startAnimation(mClearFlipAnimation);
		progressbar_loading.setVisibility(4);
		refresh_header.setVisibility(4);
		pull_arrow.setVisibility(0);
		pull_txt.setText("\u4E0B\u62C9\u5373\u53EF\u5237\u65B0...");
	}

	public void move(MotionEvent motionevent)
	{
		if (PULL_DOWN)
		{
			if (!isRefresh)
			{
				TranslateAnimation translateanimation = new TranslateAnimation(
						0.0F, 0.0F, listview.getTop(), 0.0F);
				translateanimation.setDuration(300L);
				listview.startAnimation(translateanimation);
				listview.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
				TranslateAnimation translateanimation1 = new TranslateAnimation(
						0.0F, 0.0F, refresh_header.getTop()
								+ refresh_header_position, 0.0F);
				translateanimation1.setDuration(300L);
				refresh_header.startAnimation(translateanimation1);
				progressbar_loading.setVisibility(4);
				refresh_header.layout(0, -refresh_header_position,
						getMeasuredWidth(), 0);
				PULL_DOWN = false;
				IS_PULL_STATE = false;
				unLoaded();
			} else
			{
				progressbar_loading.setVisibility(0);
				pull_arrow.setAnimation(null);
				pull_arrow.setVisibility(4);
				pull_txt.setText("\u5237\u65B0\u4E2D...");
				TranslateAnimation translateanimation2 = new TranslateAnimation(
						0.0F, 0.0F, listview.getTop() - refresh_header_position
								/ 2, 0.0F);
				translateanimation2.setDuration(150L);
				translateanimation2.setInterpolator(new LinearInterpolator());
				translateanimation2.setFillAfter(true);
				listview.startAnimation(translateanimation2);
				TranslateAnimation translateanimation3;
				try
				{
					listview.layout(0, refresh_header_position / 2,
							getMeasuredWidth(), getMeasuredHeight());
				} catch (Exception exception)
				{
					exception.printStackTrace();
				}
				translateanimation3 = new TranslateAnimation(0.0F, 0.0F,
						refresh_header.getTop() + refresh_header_position / 2,
						0.0F);
				translateanimation3.setDuration(150L);
				refresh_header.startAnimation(translateanimation3);
				refresh_header.layout(0, -refresh_header_position / 2,
						getMeasuredWidth(), refresh_header_position / 2);
				toLoading(motionevent);
			}
			arrowAnimationState = 0;
		}
	}

	public boolean onDown(MotionEvent motionevent)
	{
		sumY = 0;
		return true;
	}

	public void onFirstScrollItem(int i)
	{
		if (i == 0)
		{
			firstitem = true;
			return;
		} else
		{
			firstitem = false;
			return;
		}
	}

	public boolean onFling(MotionEvent motionevent, MotionEvent motionevent1,
			float f, float f1)
	{
		return false;
	}

	protected void onLayout(boolean flag, int i, int j, int k, int l)
	{
		super.onLayout(flag, i, j, k, l);
		if (listview == null)
			listview = (AdapterView) getChildAt(listview_childid);
		int i1 = getMeasuredWidth();
		int j1 = getMeasuredHeight();
		listview.layout(0, 0, i1, j1);
		refresh_header.layout(0, -refresh_header_position, i1, 0);
		invalidate();
	}

	public void onLongPress(MotionEvent motionevent)
	{
	}

	public boolean onScroll(MotionEvent motionevent, MotionEvent motionevent1,
			float f, float f1)
	{
		if (!isLoading)
			if (f1 < 0.0F && firstitem)
			{
				PULL_DOWN = true;
				if (!IS_PULL_STATE)
					IS_PULL_STATE = true;
				if (!refresh_header.isShown())
					refresh_header.setVisibility(0);
				if (listview.getChildCount() > 0)
				{
					sumY = (int) (motionevent1.getY() - motionevent.getY());
					if (sumY > 100 && listview.getChildAt(0).getTop() == 0)
					{
						refresh_header.setVisibility(0);
						int i1 = getMeasuredWidth();
						int j1 = getMeasuredHeight();
						int k1 = (int) ((float) (listview.getTop() + sumY) / 3.7F);
						listview.layout(0, k1, i1, j1);
						motionevent1.setAction(3);
						listview.dispatchTouchEvent(motionevent1);
						int l1 = k1 + -refresh_header_position;
						refresh_header.layout(0, l1, i1,
								l1 + refresh_header.getHeight());
						if (refresh_header.getTop() > -(refresh_header_position / 2)
								&& arrowAnimationState != 1)
						{
							pull_arrow.startAnimation(mFlipAnimation);
							arrowAnimationState = 1;
							isRefresh = true;
							pull_txt.setText("\u677E\u5F00\u5237\u65B0...");
							if (pullingMediaPlayer != null)
								pullingMediaPlayer.start();
						}
					}
				}
			}
		if (f1 > 0.0F && PULL_DOWN)
		{
			if (listview.getTop() > 0)
			{
				if (!IS_PULL_STATE)
					IS_PULL_STATE = true;
				sumY = (int) (motionevent1.getY() - motionevent.getY());
				int i = getMeasuredWidth();
				int j = getMeasuredHeight();
				int k = (int) ((float) (listview.getTop() + sumY) / 3.7F);
				listview.layout(0, k, i, j);
				int l = k + -refresh_header_position;
				refresh_header.layout(0, l, i, l + refresh_header.getHeight());
				if (refresh_header.getTop() < -(refresh_header_position / 2)
						&& arrowAnimationState != 2)
				{
					pull_arrow.startAnimation(mReverseFlipAnimation);
					arrowAnimationState = 2;
					isRefresh = false;
					pull_txt.setText("\u4E0B\u62C9\u5373\u53EF\u5237\u65B0...");
					return true;
				}
			}
		} else
		{
			IS_PULL_STATE = false;
			return true;
		}
		return true;
	}

	public void onShowPress(MotionEvent motionevent)
	{
	}

	public boolean onSingleTapUp(MotionEvent motionevent)
	{
		return false;
	}

	public boolean onTouchEvent(MotionEvent motionevent)
	{
		switch (motionevent.getAction())
		{
		case 1:
			move(motionevent);
			break;
		default:
			break;
		}
		return false;
	}

	public void setOnLoading(OnLoading onloading)
	{
		if (onloading != null)
			onLoading = onloading;
	}

	public void setUnLoaded(UnLoaded unloaded)
	{
		if (unloaded != null)
			unLoaded = unloaded;
	}

	public void toLoading(MotionEvent motionevent)
	{
		isLoading = true;
		Loading();
	}

	public void unLoaded()
	{
		if (unLoaded != null)
			unLoaded.unload();
	}

	private static final float BEEP_VOLUME = 1.1F;
	private final int Allow_Min_Touch_Height;
	public boolean IS_PULL_STATE;
	public boolean PULL_DOWN;
	private final float PULL_RATIO;
	public int arrowAnimationState;
	public int arrowAnimationTime;
	private final android.media.MediaPlayer.OnCompletionListener beepListener;
	private final Context context;
	private boolean firstitem;
	public boolean isLoading;
	public boolean isRefresh;
	public AdapterView listview;
	public int listview_childid;
	public int loadedReturnAnimationTime;
	public Runnable loading_runnable = new Runnable()
	{

		public void run()
		{
			loadedReturnOnAsyncTask();
		}
	};
	public RotateAnimation mClearFlipAnimation;
	public RotateAnimation mFlipAnimation;
	private final GestureDetector mGestureDetector;
	public RotateAnimation mReverseFlipAnimation;
	private MediaPlayer mediaPlayer;
	public OnLoading onLoading;
	public ProgressBar progressbar_loading;
	public ImageView pull_arrow;
	public TextView pull_txt;
	private MediaPlayer pullingMediaPlayer;
	public View refresh_header;
	public int refresh_header_position;
	public final String refresh_txt_default;
	public final String refresh_txt_doing;
	public final String refresh_txt_justdo;
	private int sumY;
	public UnLoaded unLoaded;
}
// 2131296256