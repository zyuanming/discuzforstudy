package net.discuz.source.widget;

import net.discuz.source.DEBUG;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

class CustomWebView extends WebView
{

	public CustomWebView(Context context)
	{
		super(context);
		webViewClient = null;
		webChromeClient = null;
		mCustomWebView = null;
		progress = null;
		mCustomWebView = this;
	}

	public CustomWebView(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
		webViewClient = null;
		webChromeClient = null;
		mCustomWebView = null;
		progress = null;
		mCustomWebView = this;
	}

	public void _init()
	{
		getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		setInitialScale(80);
		webViewClient = new WebViewClient()
		{

			public void onPageFinished(WebView webview, String s)
			{
				if (progress != null)
					progress.setVisibility(4);
				super.onPageFinished(webview, s);
			}

			public void onPageStarted(WebView webview, String s, Bitmap bitmap)
			{
				if (progress != null)
					progress.setVisibility(0);
				super.onPageStarted(webview, s, bitmap);
			}

			public void onReceivedError(WebView webview, int i, String s,
					String s1)
			{
				if (progress != null)
					progress.setVisibility(4);
				super.onReceivedError(webview, i, s, s1);
			}

			public boolean shouldOverrideUrlLoading(WebView webview, String s)
			{
				mCustomWebView.loadUrl(s);
				DEBUG.o(s);
				return true;
			}
		};
		setWebViewClient(webViewClient);
		webChromeClient = new WebChromeClient()
		{

			public void onProgressChanged(WebView webview, int i)
			{
				super.onProgressChanged(webview, i);
			}
		};
		setWebChromeClient(webChromeClient);
	}

	public void _setProgress(ProgressBar progressbar)
	{
		progress = progressbar;
	}

	private CustomWebView mCustomWebView;
	private ProgressBar progress;
	public WebChromeClient webChromeClient;
	public WebViewClient webViewClient;

}
//2131296256