package net.discuz.json.helper;

import java.util.HashMap;
import net.discuz.app.DiscuzApp;
import net.discuz.model.LoginInfo;
import net.discuz.model.SiteInfo;
import net.discuz.source.InterFace.JsonParseHelperCallBack;
import org.json.JSONObject;

public abstract class JsonParseHelper
{

	public JsonParseHelper(String s)
	{
		VariableList = null;
		json = null;
		siteAppId = null;
		siteAppId = s;
		VariableList = new HashMap();
	}

	public void destroy()
	{
		json = null;
	}

	public void onParseBegin()
	{
		if (jsonParseHelperCallBack != null)
			jsonParseHelperCallBack.onParseBegin();
	}

	public void onParseFinish(Object obj)
	{
		if (jsonParseHelperCallBack != null)
			jsonParseHelperCallBack.onParseFinish(obj);
	}

	public void parse(JSONObject jsonobject) throws Exception
	{
		json = jsonobject;
		parsePublicVariable();
		parseData();
		destroy();
	}

	protected abstract void parseData();

	protected void parsePublicVariable() throws Exception
	{
		DiscuzApp.getInstance().getSiteInfo(siteAppId).getLoginUser()
				.setFormHash(json.optString("formhash"));
		DiscuzApp.getInstance().getSiteInfo(siteAppId).getLoginUser()
				.setCookiepre(json.optString("cookiepre"));
	}

	public void setParseHelperCallBack(
			JsonParseHelperCallBack jsonparsehelpercallback)
	{
		if (jsonparsehelpercallback != null)
			jsonParseHelperCallBack = jsonparsehelpercallback;
	}

	protected HashMap VariableList;
	protected JSONObject json;
	private JsonParseHelperCallBack jsonParseHelperCallBack;
	public String siteAppId;
}
// 2131296256