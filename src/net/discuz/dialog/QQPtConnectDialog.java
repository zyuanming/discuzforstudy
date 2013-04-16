package net.discuz.dialog;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.model.LoginInfo;
import net.discuz.source.InterFace.OnLogin;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.widget.DWebView;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.NoticeCenter;

import org.json.JSONObject;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;

import com.umeng.analytics.MobclickAgent;

public class QQPtConnectDialog extends Dialog
{

	public QQPtConnectDialog(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity, R.style.ResizableDialogTheme);
		refererUrl = "Mobile_Android";
		onlogin = null;
		isNewVersion = true;
		submodule_checkpost = false;
		mActivity = discuzbaseactivity;
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
		webview._init(mActivity, Core.getInstance());
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
				.append(DiscuzApp.getInstance()
						.getSiteInfo(mActivity.siteAppId).getSiteUrl())
				.append("/api/mobile/ptlogin.php?action=login&referer")
				.toString();
		cm.removeAllCookie();
	}

	public void setCheckPost(boolean flag)
	{
		submodule_checkpost = flag;
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
		JSONObject jsonobject;
		LoginInfo logininfo;
		try
		{
			jsonobject = (new JSONObject(s)).optJSONObject("Variables");
		} catch (Exception exception)
		{
			exception.printStackTrace();
			return;
		}
		if (jsonobject != null)
		{
			logininfo = DiscuzApp.getInstance().getLoginUser(
					mActivity.siteAppId);
			logininfo.setFormHash(jsonobject.optString("formhash").trim());
			logininfo.setCookiepre(jsonobject.optString("cookiepre").trim());
			logininfo.setUin(uin);
			logininfo.setSkey(skey);
			saltkey = jsonobject.optString("saltkey").trim();
			logininfo.setLoginCookie("saltkey",
					(new StringBuilder()).append(logininfo.getCookiepre())
							.append("saltkey=").append(saltkey).toString());
			logininfo.setLoginCookie("uin", uin);
			logininfo.setLoginCookie("skey", skey);
			logininfo.setUid(jsonobject.optInt("member_uid"));
			logininfo.setUsername(jsonobject.optString("member_username"));
			logininfo.setGroupid(jsonobject.optInt("groupid"));
			logininfo.setLoginCookie(
					"saltkey",
					(new StringBuilder()).append(logininfo.getCookiepre())
							.append("auth=")
							.append(jsonobject.optString("auth")).toString());
			jsonobject.optString("sechash").trim();
			mActivity.runOnUiThread(new Runnable()
			{

				public void run()
				{
					MobclickAgent.onEvent(mActivity, "suc_ptlogin");
					DiscuzStats.add(mActivity.siteAppId, "suc_ptlogin");
					onlogin.loginSuceess(mActivity.siteAppId, null);
				}
			});
			NoticeCenter.loginToken(mActivity.siteAppId);
			dismiss();
		}
	}

	private CookieManager cm;
	private boolean isNewVersion;
	private View loading;
	private DiscuzBaseActivity mActivity;
	private OnLogin onlogin;
	private String refererUrl;
	private String saltkey;
	private SiteNavbar site_navbar;
	private String skey;
	private String startUrl;
	private boolean submodule_checkpost;
	private String tmpUrl;
	private String uin;
	private DWebView webview;
}