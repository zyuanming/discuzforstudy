package net.discuz.dialog;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.source.InterFace.OnLogin;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.widget.DWebView;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.Core;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;

public class QQConnectDialog extends Dialog
{

	public QQConnectDialog(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity, R.style.ResizableDialogTheme);
		refererUrl = "Mobile_Android";
		onlogin = null;
		isNewVersion = true;
		context = discuzbaseactivity;
	}

	public void loadUrl(String s)
	{
		webview.loadUrl(s);
	}

	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.webview_dialog);
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		site_navbar.setTitle("QQ\u767B\u5F55");
		loading = findViewById(R.id.loading);
		webview = (DWebView) findViewById(R.id.custom_webview);
		webview._init(context, Core.getInstance());
		webview._initWebChromeClient();
		webview._initWebViewClient();
		cm = webview.getCookieManager();
		webview.setOnPageLoad(new net.discuz.source.widget.DWebView.onPageLoad()
		{

			public void pageError()
			{
				loading.setVisibility(8);
			}

			public void pageFinished(WebView webview1, String s)
			{
				loading.setVisibility(8);
			}

			public void pageStart(WebView webview1, String s)
			{
				loading.setVisibility(0);
			}
		});
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				dismiss();
			}
		});
		webview.clearCache(true);
		webview._addJavascriptInterFace(this);
		startUrl = (new StringBuilder())
				.append(DiscuzApp.getInstance().getSiteInfo(context.siteAppId)
						.getSiteUrl())
				.append("/connect.php?mod=login&op=init&mobile=no&oauth_style=mobile&referer=")
				.append(refererUrl).toString();
		cm.removeAllCookie();
		webview.setOnPageLoad(new net.discuz.source.widget.DWebView.onPageLoad()
		{

			public void pageError()
			{
			}

			public void pageFinished(WebView webview1, String s)
			{

			}

			public void pageStart(WebView webview1, String s)
			{

			}
		});
	}

	public void setOnLogin(OnLogin onlogin1)
	{
		onlogin = onlogin1;
	}

	public void show()
	{
		super.show();
		loading.setVisibility(8);
		loadUrl(startUrl);
	}

	public void showSource(String s)
	{
		if (s.contains("module_not_exists"))
		{
			isNewVersion = false;
			loadUrl(tmpUrl);
		}
	}

	private CookieManager cm;
	private DiscuzBaseActivity context;
	private boolean isNewVersion;
	private View loading;
	private OnLogin onlogin;
	private String refererUrl;
	private SiteNavbar site_navbar;
	private String startUrl;
	private String tmpUrl;
	private DWebView webview;

}
// 2131296256