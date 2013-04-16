package net.discuz.source.widget;

import net.discuz.R;
import net.discuz.tools.Core;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 帖子内容视图的触摸事件处理
 * 
 * @author lkh
 * 
 */
public class DWebView_TouchEvent extends DWebView implements
		android.view.GestureDetector.OnGestureListener
{

	public DWebView_TouchEvent(Context context)
	{
		super(context);
		onHtmlError = Boolean.valueOf(false);
		upX = 0;
		upY = 0;
		mVelocityTracker = null;
		webViewCustom = null;
		mGestureDetector = new GestureDetector(this);
	}

	public DWebView_TouchEvent(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
		onHtmlError = Boolean.valueOf(false);
		upX = 0;
		upY = 0;
		mVelocityTracker = null;
		webViewCustom = null;
		mGestureDetector = new GestureDetector(this);
	}

	public void _init(Activity activity1, Core core)
	{
		activity = activity1;
		super._init(activity1, core);
	}

	public void _initWebChromeClient()
	{
		webChromeClient = new WebChromeClient()
		{

			public void onProgressChanged(WebView webview, int i)
			{
				super.onProgressChanged(webview, i);
			}
		};
		setWebChromeClient(webChromeClient);
	}

	public void _initWebViewClient()
	{
		webViewClient = new WebViewClient()
		{

			public void onPageFinished(WebView webview, String s)
			{
				super.onPageFinished(webview, s);
				if (onHtmlError.booleanValue())
					activity.getBaseContext().getString(R.string.Html_error_2);
			}

			public void onPageStarted(WebView webview, String s, Bitmap bitmap)
			{
				super.onPageStarted(webview, s, bitmap);
			}

			public void onReceivedError(WebView webview, int i, String s,
					String s1)
			{
				onHtmlError = Boolean.valueOf(true);
			}

			public boolean shouldOverrideUrlLoading(WebView webview, String s)
			{
				if (s.subSequence(0, 4).equals("file"))
					return super.shouldOverrideUrlLoading(webview, s);
				if (s.lastIndexOf("mailto:") > -1)
				{
					Intent intent = new Intent("android.intent.action.SENDTO",
							Uri.parse(s));
					activity.startActivity(intent);
				} else
				{
					webViewCustom = new WebViewCustom(activity);
					webViewCustom._init();
					webViewCustom._loadUrl(s);
				}
				return true;
			}
		};
		setWebViewClient(webViewClient);
	}

	public boolean dispatchTouchEvent(MotionEvent motionevent)
	{
		mGestureDetector.onTouchEvent(motionevent);
		return super.dispatchTouchEvent(motionevent);
	}

	public int[] getDownPos()
	{
		int ai[] = new int[2];
		ai[0] = (int) downX;
		ai[1] = (int) downY;
		return ai;
	}

	public int[] getUpPos()
	{
		int ai[] = new int[2];
		ai[0] = upX;
		ai[1] = upY;
		return ai;
	}

	public boolean onDown(MotionEvent motionevent)
	{
		downX = motionevent.getX() - (float) (getWidth() / 2);
		downY = motionevent.getY() - (float) (getHeight() / 2);
		return false;
	}

	public boolean onFling(MotionEvent motionevent, MotionEvent motionevent1,
			float f, float f1)
	{
		return false;
	}

	public void onLongPress(MotionEvent motionevent)
	{}

	public boolean onScroll(MotionEvent motionevent, MotionEvent motionevent1,
			float f, float f1)
	{
		return false;
	}

	protected void onScrollChanged(int i, int j, int k, int l)
	{
		super.onScrollChanged(i, j, k, l);
	}

	public void onShowPress(MotionEvent motionevent)
	{}

	public boolean onSingleTapUp(MotionEvent motionevent)
	{
		return false;
	}

	public boolean overlayVerticalScrollbar()
	{
		return super.overlayVerticalScrollbar();
	}

	private Activity activity;
	private float downX;
	private float downY;
	private final GestureDetector mGestureDetector;
	private VelocityTracker mVelocityTracker;
	private Boolean onHtmlError;
	private int sumX;
	private int sumY;
	private int upX;
	private int upY;
	private DViewGroup viewGroup;
	private WebViewCustom webViewCustom;
}
// 2131296256