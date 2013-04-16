package net.discuz.source.widget;

import net.discuz.R;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WebViewCustom
{

	public WebViewCustom(Activity activity1)
	{
		activity = null;
		webViewCustomPopupWindow = null;
		webViewCustomView = null;
		webView = null;
		webView_custon_url = null;
		webView_custom_loading = null;
		activity = activity1;
	}

	public void _init()
	{
		webViewCustomView = activity.getLayoutInflater().inflate(
				R.layout.webview_custom, null);
		webView_custon_url = (TextView) webViewCustomView
				.findViewById(R.id.webView_custon_url);
		webView_custom_loading = (ProgressBar) webViewCustomView
				.findViewById(R.id.webView_custom_loading);
		webViewCustomPopupWindow = new PopupWindow(webViewCustomView, -1, -1,
				true);
		webViewCustomPopupWindow.setOutsideTouchable(true);
		webViewCustomPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		webViewCustomPopupWindow
				.setAnimationStyle(R.style.LoadingPopupAnimation);
		webView = (CustomWebView) webViewCustomView
				.findViewById(R.id.webView_custom);
		webView._setProgress(webView_custom_loading);
		webView._init();
	}

	public void _loadUrl(String s)
	{
		if (!webViewCustomPopupWindow.isShowing())
			webViewCustomPopupWindow.showAtLocation(
					activity.findViewById(R.id.DiscuzActivityBox), 80, 0, 0);
		webView_custon_url.setText(s);
		webView.loadUrl(s);
	}

	private Activity activity;
	private CustomWebView webView;
	private PopupWindow webViewCustomPopupWindow;
	private View webViewCustomView;
	private ProgressBar webView_custom_loading;
	private TextView webView_custon_url;
}
// 2131296256