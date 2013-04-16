package net.discuz.source.WebInterFace;

import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.widget.DWebView;
import net.discuz.tools.Core;

public class BaseJavascriptInterFace
{

	public BaseJavascriptInterFace(DiscuzBaseActivity discuzbaseactivity,
			DWebView dwebview)
	{
		needLogin = "0";
		context = discuzbaseactivity;
		webview = dwebview;
	}

	public String getHtmlResult()
	{
		return result;
	}

	public String geterrormessage()
	{
		return message;
	}

	public String getneedlogin()
	{
		return needLogin;
	}

	public void loadViewBox(String s)
	{
		webview.clearCache(true);
		webview.loadUrl((new StringBuilder())
				.append("file:///android_asset/template/").append(s)
				.append(".html").toString());
	}

	public void setHtmlResult(String s)
	{
		result = s;
	}

	public void showMessage(String s, String s1)
	{
		context.ShowMessageByHandler(s, Integer.valueOf(s1).intValue());
	}

	public void showWebViewMessage(String as[])
	{
		String as1[] = as[0].split("//");
		if (as1.length > 1)
		{
			if ("1".equals(as1[1]))
				needLogin = "1";
			else
				needLogin = "0";
		} else
		{
			needLogin = "0";
		}
		message = Core.getInstance()._getMessageByName(as);
		webview.loadUrl("file:///android_asset/html_error.html");
	}

	protected DiscuzBaseActivity context;
	private String message;
	private String needLogin;
	protected String result;
	protected DWebView webview;
}
// 2131296256