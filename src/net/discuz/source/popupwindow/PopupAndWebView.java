package net.discuz.source.popupwindow;

import net.discuz.R;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.widget.DWebView;
import net.discuz.tools.Core;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PopupAndWebView
{

	protected PopupAndWebView(DiscuzBaseActivity discuzbaseactivity)
	{
		context = discuzbaseactivity;
	}

	public void dismiss()
	{
		if (popupwindow.isShowing())
			popupwindow.dismiss();
	}

	public void dismissLoading()
	{
		loading.setVisibility(8);
	}

	public CookieManager getCookieManager()
	{
		return webview.getCookieManager();
	}

	protected void init()
	{
		view = context.getLayoutInflater().inflate(R.layout.popup_and_webview,
				null);
		header_title = (TextView) view.findViewById(R.id.header_title);
		header_back = (Button) view.findViewById(R.id.back);
		header_back.setVisibility(0);
		loading = view.findViewById(R.id.loading);
		webview = (DWebView) view.findViewById(R.id.custom_webview);
		webview._init(context, Core.getInstance());
		webview._initWebChromeClient();
		webview._initWebViewClient();
		cm = webview.getCookieManager();
		popupwindow = new PopupWindow(view, -1, -1, true);
		popupwindow.setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.alpha_bg));
		popupwindow.setAnimationStyle(R.style.DefaultActivityAnimation);
		popupwindow.setOutsideTouchable(true);
		setWebViewPageLoad(new net.discuz.source.widget.DWebView.onPageLoad()
		{

			public void pageError()
			{
				dismissLoading();
			}

			public void pageFinished(WebView webview1, String s)
			{
				dismissLoading();
			}

			public void pageStart(WebView webview1, String s)
			{
				showLoading();
			}
		});
	}

	public void loadUrl(String s)
	{
		webview.loadUrl(s);
	}

	public void setCallBack(
			net.discuz.source.widget.DWebView.onLoadUrl onloadurl)
	{
		webview.setOnLoadUrl(onloadurl);
	}

	protected void setOnClickListener()
	{
		header_back.setOnClickListener(new android.view.View.OnClickListener()
		{

			public void onClick(View view1)
			{
				dismiss();
			}
		});
	}

	public void setTitle(String s)
	{
		header_title.setText(s);
	}

	public void setWebViewPageLoad(
			net.discuz.source.widget.DWebView.onPageLoad onpageload)
	{
		webview.setOnPageLoad(onpageload);
	}

	public void showLoading()
	{
		loading.setVisibility(0);
	}

	public void showPopupWindow()
	{
		if (!popupwindow.isShowing())
			popupwindow.showAtLocation(
					context.findViewById(R.id.DiscuzActivityBox), 5, 0, 0);
	}

	public CookieManager cm;
	protected DiscuzBaseActivity context;
	private Button header_back;
	private TextView header_title;
	private View loading;
	private PopupWindow popupwindow;
	private View view;
	protected DWebView webview;
}
// 2131296256