package net.discuz.dialog;

import java.net.URLDecoder;

import net.discuz.R;
import net.discuz.source.DEBUG;
import net.discuz.source.InterFace.OnLogin;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.SiteInfoDBHelper;
import net.discuz.source.widget.DWebView;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;

import com.umeng.analytics.MobclickAgent;

public class RegisterDialog extends Dialog
{

	public RegisterDialog(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity, R.style.ResizableDialogTheme);
		onlogin = null;
		context = discuzbaseactivity;
		MobclickAgent.onEvent(discuzbaseactivity, "v_reg");
		DiscuzStats.add(discuzbaseactivity.siteAppId, "v_reg");
	}

	public void loadUrl(String s)
	{
		webview.loadUrl(s);
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.webview_dialog);
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		site_navbar.setTitle("\u6CE8\u518C\u4F1A\u5458");
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

			public void pageFinished(WebView webview1, String s1)
			{
				loading.setVisibility(8);
			}

			public void pageStart(WebView webview1, String s1)
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
		String s = SiteInfoDBHelper.getInstance().getByAppId(context.siteAppId)
				.getRegName();
		String as[] = new String[4];
		as[0] = context.siteAppId;
		as[1] = "module=register";
		as[2] = (new StringBuilder()).append("mod=").append(s).toString();
		as[3] = "mobilemessage=1";
		regurl = Core.getSiteUrl(as);
		DEBUG.o((new StringBuilder()).append("regurl:").append(regurl)
				.toString());
		cm.removeAllCookie();
		setCallBack(new net.discuz.source.widget.DWebView.onLoadUrl()
		{

			public void load(String s1)
			{
				DEBUG.o((new StringBuilder()).append("reg url:").append(s1)
						.toString());
				if (s1.lastIndexOf("discuz://") > -1)
				{
					String as1[] = s1.split("\\/\\/");
					as1[2] = URLDecoder.decode(as1[2]);
					if (as1[3] != null)
						as1[3] = URLDecoder.decode(as1[3]);
					if (as1[1].contains("register_email_send_succeed"))
						context.ShowMessageByHandler(as1[2], 1);
					else if (as1[1].lastIndexOf("_succeed") > -1)
					{
						if (as1[3] != null && !"".equals(as1[3]))
						{
							LoginDialog.jsonReader_Login_forumindex jsonreader_login_forumindex = new LoginDialog.jsonReader_Login_forumindex(
									as1[3]);
							try
							{
								jsonreader_login_forumindex._jsonParse();
							} catch (JSONException jsonexception)
							{
								jsonexception.printStackTrace();
								if (onlogin != null)
									onlogin.loginError();
							}
							jsonreader_login_forumindex._debug();
							LoginDialog.setUser(context, context.siteAppId,
									jsonreader_login_forumindex);
							if (onlogin != null)
								try
								{
									JSONObject jsonobject = new JSONObject(
											"{\"action\":\"dismiss\"}");
									onlogin.loginSuceess(context.siteAppId,
											jsonobject);
								} catch (JSONException jsonexception1)
								{
									jsonexception1.printStackTrace();
								}
							loading.setVisibility(8);
							dismiss();
							return;
						}
					} else
					{
						context.ShowMessageByHandler(as1[2], 3);
						return;
					}
				}
			}
		});
	}

	public void setCallBack(
			net.discuz.source.widget.DWebView.onLoadUrl onloadurl)
	{
		webview.setOnLoadUrl(onloadurl);
	}

	public void setOnLogin(OnLogin onlogin1)
	{
		onlogin = onlogin1;
	}

	public void show()
	{
		super.show();
		loading.setVisibility(0);
		loadUrl(regurl);
	}

	private CookieManager cm;
	private DiscuzBaseActivity context;
	private View loading;
	private OnLogin onlogin;
	private String regurl;
	private SiteNavbar site_navbar;
	private DWebView webview;

}
// 2131296256