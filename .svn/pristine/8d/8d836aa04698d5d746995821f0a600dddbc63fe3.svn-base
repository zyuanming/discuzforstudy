package net.discuz.model;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.ContentValues;
import android.util.Log;

public class LoginInfo
{

	public LoginInfo()
	{
		siteappid = "";
		username = "";
		cookiepre = "";
		saltkey = "";
		cookie = "";
		email = "";
		uid = 0;
		groupid = 7;
		cloudmessage = "";
		cloudmessageval = "";
		formhash = "";
		logintoken = "";
		uin = "";
		skey = "";
		loginCookie = null;
	}

	public ContentValues _getSqlVal()
	{
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("uid", Integer.valueOf(uid));
		contentvalues.put("username", username);
		contentvalues.put("groupid", Integer.valueOf(groupid));
		String s;
		if (saltkey == null)
			s = "";
		else
			s = saltkey;
		contentvalues.put("saltkey", s);
		contentvalues.put("cookie", cookie);
		contentvalues.put("formhash", formhash);
		contentvalues.put("logintoken", logintoken);
		return contentvalues;
	}

	public void _initValue(JSONObject jsonobject)
	{
		cookiepre = jsonobject.optString("cookiepre");
		if (jsonobject.optString("saltkey") != null)
			saltkey = (new StringBuilder()).append(cookiepre)
					.append("saltkey=").append(jsonobject.optString("saltkey"))
					.toString();
		uid = jsonobject.optInt("member_uid");
		username = jsonobject.optString("member_username");
		groupid = jsonobject.optInt("groupid");
		if (username != null && !username.equals(""))
			cookie = (new StringBuilder()).append(cookiepre).append("auth=")
					.append(jsonobject.optString("auth")).toString();
		formhash = jsonobject.optString("formhash");
	}

	public void clearLoginCookie()
	{
		if (loginCookie != null)
			loginCookie.clear();
		loginCookie = null;
	}

	public String getCloudmessage()
	{
		return cloudmessage;
	}

	public String getCloudmessageval()
	{
		return cloudmessageval;
	}

	public String getCookie()
	{
		return cookie;
	}

	public String getCookiepre()
	{
		return cookiepre;
	}

	public String getEmail()
	{
		return email;
	}

	public String getFormHash()
	{
		Log.d("LoginInfo", "getFormHash() ----->　Enter");
		Log.d("LoginInfo", "formhash %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% " + formhash);
		Log.d("LoginInfo", "getFormHash() ----->　Exit");
		return formhash;
	}

	public int getGroupid()
	{
		return groupid;
	}

	public String getLoginCookie(String s)
	{
		if (loginCookie == null)
			return null;
		else
			return (String) loginCookie.get(s);
	}

	public HashMap getLoginCookie()
	{
		return loginCookie;
	}

	public String getLoginToken()
	{
		return logintoken;
	}

	public String getSaltkey()
	{
		return saltkey;
	}

	public String getSiteAppid()
	{
		return siteappid;
	}

	public String getSkey()
	{
		return skey;
	}

	public int getUid()
	{
		return uid;
	}

	public String getUin()
	{
		return uin;
	}

	public String getUsername()
	{
		return username;
	}

	public void setCloudmessage(String s)
	{
		cloudmessage = s;
	}

	public void setCloudmessageval(String s)
	{
		cloudmessageval = s;
	}

	public void setCookie(String s)
	{
		cookie = s;
	}

	public void setCookiepre(String s)
	{
		cookiepre = s;
	}

	public void setEmail(String s)
	{
		email = s;
	}

	public void setFormHash(String s)
	{
		formhash = s;
	}

	public void setGroupid(int i)
	{
		groupid = i;
	}

	public void setLoginCookie(String s, String s1)
	{
		if (loginCookie == null)
			loginCookie = new HashMap();
		loginCookie.put(s, s1);
	}

	public void setLoginToken(String s)
	{
		logintoken = s;
	}

	public void setSaltkey(String s)
	{
		saltkey = s;
	}

	public void setSiteAppid(String s)
	{
		siteappid = s;
	}

	public void setSkey(String s)
	{
		skey = s;
	}

	public void setUid(int i)
	{
		uid = i;
	}

	public void setUin(String s)
	{
		uin = s;
	}

	public void setUsername(String s)
	{
		username = s;
	}

	private String cloudmessage;
	private String cloudmessageval;
	private String cookie;
	private String cookiepre;
	private String email;
	private String formhash;
	private int groupid;
	private HashMap loginCookie;
	private String logintoken;
	private String saltkey;
	private String siteappid;
	private String skey;
	private int uid;
	private String uin;
	private String username;
}
// 2131296256