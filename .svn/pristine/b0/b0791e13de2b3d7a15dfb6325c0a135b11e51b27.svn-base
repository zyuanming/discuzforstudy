package net.discuz.source.widget;

import java.util.HashSet;
import java.util.Iterator;

import net.discuz.R;
import net.discuz.source.DEBUG;
import net.discuz.tools.Core;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class DWebView extends WebView
{
	public static interface onLoadUrl
	{

		public abstract void load(String s);
	}

	public static interface onPageLoad
	{

		public abstract void pageError();

		public abstract void pageFinished(WebView webview, String s);

		public abstract void pageStart(WebView webview, String s);
	}

	public static interface onScrollOffset
	{

		public abstract void doScrollOffset(int i, int j, int k, int l);
	}

	public DWebView(Context context)
	{
		super(context);
		activity = null;
		core = null;
		websettings = null;
		webViewClient = null;
		webChromeClient = null;
		onHtmlError = Boolean.valueOf(false);
		onloadurl = null;
		onPageLoadListeners = new HashSet();
		INTERFACE_NAME = "DZ";
		onScrollOffsetCallBack = null;
	}

	public DWebView(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
		activity = null;
		core = null;
		websettings = null;
		webViewClient = null;
		webChromeClient = null;
		onHtmlError = Boolean.valueOf(false);
		onloadurl = null;
		onPageLoadListeners = new HashSet();
		INTERFACE_NAME = "DZ";
		onScrollOffsetCallBack = null;
	}

	public void _addJavascriptInterFace(Object obj)
	{
		if (obj == null)
		{
			addJavascriptInterface(activity, "DZ");
			return;
		} else
		{
			addJavascriptInterface(obj, "DZ");
			return;
		}
	}

	public WebSettings _getSettings()
	{
		return websettings;
	}

	public void _init(Activity activity1, Core core1)
	{
		activity = activity1;
		core = core1;
		websettings = getSettings();
		websettings.setJavaScriptEnabled(true); // 设置支持JavaScript
		websettings.setBuiltInZoomControls(false);
		websettings.setSupportZoom(false);
		setBackgroundColor(0);
		setBackgroundResource(0);
		setInitialScale(core._getWebViewScale(activity));
		setScrollBarStyle(WebView.DRAWING_CACHE_QUALITY_AUTO); // 0
	}

	public void _initWebChromeClient()
	{
		_initWebChromeClient(null);
	}

	public void _initWebChromeClient(WebChromeClient webchromeclient)
	{
		if (webchromeclient != null)
		{
			webChromeClient = webchromeclient;
			return;
		} else
		{
			webChromeClient = new WebChromeClient()
			{

				public void onProgressChanged(WebView webview, int i)
				{
					ProgressBar progressbar;
					progressbar = (ProgressBar) activity
							.findViewById(R.id.progressbar);
					if (progressbar != null)
						progressbar.setMax(100);
					if (i >= 100)
					{
						if (progressbar != null)
						{
							progressbar.setProgress(100);
							progressbar.setVisibility(8);
						}
					} else
					{
						if (progressbar != null)
						{
							progressbar.setProgress(i);
							if (progressbar.getVisibility() == 8)
							{
								progressbar.setVisibility(0);
								progressbar.setProgress(i);
							}
						}
					}
					super.onProgressChanged(webview, i);
					return;
				}
			};
			setWebChromeClient(webChromeClient);
			return;
		}
	}

	public void _initWebViewClient()
	{
		_initWebViewClient(null);
	}

	public void _initWebViewClient(WebViewClient webviewclient)
	{
		if (webviewclient != null)
		{
			webViewClient = webviewclient;
			return;
		} else
		{
			webViewClient = new WebViewClient()
			{

				@Override
				public void onPageFinished(WebView webview, String s)
				{
					super.onPageFinished(webview, s);
					DEBUG.o((new StringBuilder())
							.append("webview pageFinished url:").append(s)
							.toString());
					if (onHtmlError.booleanValue())
					{
						for (Iterator iterator1 = onPageLoadListeners
								.iterator(); iterator1.hasNext(); ((onPageLoad) iterator1
								.next()).pageError())
							;
					} else
					{
						for (Iterator iterator = onPageLoadListeners.iterator(); iterator
								.hasNext(); ((onPageLoad) iterator.next())
								.pageFinished(webview, s))
							;
					}
				}

				@Override
				public void onPageStarted(WebView webview, String s,
						Bitmap bitmap)
				{
					DEBUG.o((new StringBuilder())
							.append("webview pageStarted url:").append(s)
							.toString());
					super.onPageStarted(webview, s, bitmap);
					for (Iterator iterator = onPageLoadListeners.iterator(); iterator
							.hasNext(); ((onPageLoad) iterator.next())
							.pageStart(webview, s))
						;
				}

				@Override
				public void onReceivedError(WebView webview, int i, String s,
						String s1)
				{
					if (!s1.contains("discuz://"))
					{
						onHtmlError = Boolean.valueOf(true);
						loadUrl("file:///android_asset/html_error.html");
					}
				}

				/**
				 * //重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
				 */
				@Override
				public boolean shouldOverrideUrlLoading(WebView webview,
						String s)
				{
					DEBUG.o((new StringBuilder())
							.append("shouldOverrideUrlLoading url:").append(s)
							.toString());
					if (s.lastIndexOf("discuz://") > -1 && onloadurl != null)
					{
						onloadurl.load(s);
						return true;
					} else
					{
						return false;
					}
				}
			};
			setWebViewClient(webViewClient);
			return;
		}
	}

	public CookieManager getCookieManager()
	{
		return CookieManager.getInstance();
	}

	public void loadDataWithBaseURL(String s, String s1, String s2, String s3,
			String s4)
	{
		super.loadDataWithBaseURL(s, s1, s2, s3, s4);
	}

	protected void onScrollChanged(int i, int j, int k, int l)
	{
		super.onScrollChanged(i, j, k, l);
		if (onScrollOffsetCallBack != null)
			onScrollOffsetCallBack.doScrollOffset(i, j, k, l);
	}

	public void setOnLoadUrl(onLoadUrl onloadurl1)
	{
		onloadurl = onloadurl1;
	}

	public void setOnPageLoad(onPageLoad onpageload)
	{
		onPageLoadListeners.add(onpageload);
	}

	public void setOnScrollOffset(onScrollOffset onscrolloffset)
	{
		onScrollOffsetCallBack = onscrolloffset;
	}

	private final String INTERFACE_NAME;
	private Activity activity;
	public Core core;
	private Boolean onHtmlError;
	private HashSet onPageLoadListeners;
	private onScrollOffset onScrollOffsetCallBack;
	private onLoadUrl onloadurl;
	public WebChromeClient webChromeClient;
	public WebViewClient webViewClient;
	private WebSettings websettings;

}
// 2131296256