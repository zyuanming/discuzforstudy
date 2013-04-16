package net.discuz.model;

import android.content.ContentValues;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 站点信息bean
 * 
 * @author lkh
 * 
 */
public class SiteInfo
{
	private String clientview;
	private String cloudid;
	private String cookiepre;
	private String description;
	private int flag;
	private String icon;
	private String isfav;
	private String isnotify;
	private String isnotifyupgrade;
	private LoginInfo loginUser; // 用户登录信息
	private String pluginVer;
	public String productid;
	private String qqConnect;
	private String regName;
	private String siteAppid;
	private String siteCharset;
	private String siteKey;
	private String siteName;
	private String siteNameUrl;
	private String siteUpdated;
	private String siteUrl;
	private String theme;
	private String uCenterUrl;
	private String updated;
	private String ver;
	private String yesterdayPost;

	public SiteInfo()
	{
		siteName = "";// 站点名字
		siteUrl = ""; // 站点URL地址
		siteNameUrl = "";// 站点名称的URL地址
		siteAppid = "";// 站点的Appid
		siteCharset = "";// 站点的字符编码
		description = "";// 站点的描述
		siteKey = "";// 站点的KEY
		ver = "";// 站点版本
		pluginVer = "0";// 站点插件的版本
		icon = "";// 站点的ICON图标
		theme = "";// 站点的主题

		updated = "";// 更新
		siteUpdated = "";// 站点更新

		isfav = "";// 是否是用户喜欢的
		yesterdayPost = "";// 昨天的请求
		clientview = "";// 客户端视图
		uCenterUrl = "";// 用户中心URL
		qqConnect = "";// QQ连接
		regName = "";// 注册的名字
		productid = "";// 产品ID
		cloudid = "";// 云端ID
		isnotify = "";// 是否通知，推送
		cookiepre = "";// 用户Cookie

		flag = 0;
		isnotifyupgrade = "";// 是否通知更新
		resetUserToGuest();// 重置用户为游客
	}

	public void _initCheckValue(JSONObject jsonobject) throws JSONException
	{
		cloudid = jsonobject.optString("mysiteid");
		siteCharset = jsonobject.optString("charset");
		regName = jsonobject.optString("regname");
		qqConnect = jsonobject.optString("qqconnect");
		siteName = jsonobject.optString("sitename");
		uCenterUrl = jsonobject.optString("ucenterurl");
		if (jsonobject.optString("discuzversion") != null)
		{
			if (!jsonobject.optString("discuzversion").contains("X2.5"))
			{
				if (jsonobject.optString("discuzversion").contains("X2"))
					ver = "X2";
			} else
			{
				ver = "X25";
			}

		}
		pluginVer = jsonobject.optString("pluginversion", "0");
		return;
	}

	public void _initSearchValue(JSONObject jsonobject) throws JSONException
	{
		siteName = jsonobject.optString("sName");
		siteUrl = jsonobject.optString("sUrl");
		ver = jsonobject.optString("sVersion");
		siteCharset = jsonobject.optString("sCharset");
		yesterdayPost = jsonobject.getString("sYesterdayPost");
		icon = jsonobject.optString("icon");
		productid = jsonobject.optString("productid");
		cloudid = jsonobject.optString("cloudid");
	}

	public void _initValue(JSONObject jsonobject) throws JSONException
	{
		siteName = jsonobject.getString("name");
		siteUrl = jsonobject.getString("url");
		icon = jsonobject.getString("icon");
		siteCharset = jsonobject.getString("charset");
		description = jsonobject.getString("desc");
		siteKey = jsonobject.getString("sKey");
		updated = jsonobject.getString("updated");
		ver = jsonobject.getString("ver");
		theme = jsonobject.optString("theme");
		productid = jsonobject.optString("productid");
		cloudid = jsonobject.optString("cloudid");
	}

	public boolean equals(Object obj)
	{
		return siteUrl.equals(((SiteInfo) obj).siteUrl);
	}

	public String getClientview()
	{
		return clientview;
	}

	public String getCloudId()
	{
		if (cloudid == null)
			cloudid = "";
		return cloudid;
	}

	public String getCookiepre()
	{
		return cookiepre;
	}

	public String getDescription()
	{
		return description;
	}

	public int getFlag()
	{
		return flag;
	}

	public String getIcon()
	{
		return icon;
	}

	/**
	 * 从SD卡读取站点图标
	 * 
	 * @return
	 */
	public String getIconFromSD()
	{
		if (siteUrl.contains("."))
			return (new StringBuilder())
					.append("/sdcard/discuz/style/site_icon_")
					.append(siteUrl.split("\\.")[1]).append(".png").toString();
		else
			return null;
	}

	public String getIsNotify()
	{
		if (isnotify == null)
			isnotify = "";
		return isnotify;
	}

	public String getIsNotifyUpgrade()
	{
		if (isnotifyupgrade == null)
			isnotifyupgrade = "";
		return isnotifyupgrade;
	}

	public String getIsfav()
	{
		return isfav;
	}

	public LoginInfo getLoginUser()
	{
		return loginUser;
	}

	public String getPluginVersion()
	{
		if (pluginVer == null)
			pluginVer = "0";
		return pluginVer;
	}

	public String getProductid()
	{
		return productid;
	}

	public String getQQConnect()
	{
		return qqConnect;
	}

	public String getRegName()
	{
		return regName;
	}

	public String getSiteAppid()
	{
		return siteAppid;
	}

	public String getSiteCharset()
	{
		return siteCharset;
	}

	public String getSiteKey()
	{
		return siteKey;
	}

	public String getSiteName()
	{
		return siteName;
	}

	public String getSiteNameUrl()
	{
		return siteNameUrl;
	}

	public String getSiteUpdated()
	{
		return siteUpdated;
	}

	public String getSiteUrl()
	{
		return siteUrl;
	}

	public ContentValues getSqlValue()
	{
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("sitecharset", siteCharset);
		contentvalues.put("name", siteName);
		contentvalues.put("ucenterurl", uCenterUrl);
		contentvalues.put("qqconnect", qqConnect);
		contentvalues.put("regname", regName);
		contentvalues.put("url", siteUrl);
		contentvalues.put("description", description);
		contentvalues.put("ver", ver);
		contentvalues.put("pluginver", pluginVer);
		contentvalues.put("cloudid", cloudid);
		contentvalues.put("clientview", clientview);
		contentvalues.put("isfav", isfav);
		contentvalues.put("isnotify", isnotify);
		return contentvalues;
	}

	public String getTheme()
	{
		return theme;
	}

	public String getUCenterUrl()
	{
		return uCenterUrl;
	}

	public String getUpdated()
	{
		return updated;
	}

	public String getVersion()
	{
		return ver;
	}

	public String getYesterdayPost()
	{
		return yesterdayPost;
	}

	public void resetUserToGuest()
	{
		if (loginUser == null)
			loginUser = new LoginInfo();
		loginUser.setUid(0);
		loginUser.setUsername("");
		loginUser.setGroupid(7);
		loginUser.clearLoginCookie();
	}

	public void setClientview(String s)
	{
		clientview = s;
	}

	public void setCloudid(String s)
	{
		cloudid = s;
	}

	public void setCookiepre(String s)
	{
		cookiepre = s;
	}

	public void setDescription(String s)
	{
		description = s;
	}

	public void setFlag(int i)
	{
		flag = i;
	}

	public void setIcon(String s)
	{
		icon = s;
	}

	public void setIsNotify(String s)
	{
		isnotify = s;
	}

	public void setIsNotifyUpgrade(String s)
	{
		isnotifyupgrade = s;
	}

	public void setLoginUser(LoginInfo logininfo)
	{
		loginUser = logininfo;
	}

	public void setPluginVersion(String s)
	{
		pluginVer = s;
	}

	public void setProductid(String s)
	{
		productid = s;
	}

	public void setQQConnect(String s)
	{
		qqConnect = s;
	}

	public void setRegName(String s)
	{
		regName = s;
	}

	public void setSiteAppid(String s)
	{
		siteAppid = s;
	}

	public void setSiteCharset(String s)
	{
		siteCharset = s;
	}

	public void setSiteKey(String s)
	{
		siteKey = s;
	}

	public void setSiteName(String s)
	{
		siteName = s;
	}

	public void setSiteNameUrl(String s)
	{
		siteNameUrl = s;
	}

	public void setSiteUpdated(String s)
	{
		siteUpdated = s;
	}

	public void setSiteUrl(String s)
	{
		siteUrl = s;
	}

	public void setTheme(String s)
	{
		theme = s;
	}

	public void setUCenterUrl(String s)
	{
		uCenterUrl = s;
	}

	public void setUpdated(String s)
	{
		updated = s;
	}

	public void setVersion(String s)
	{
		ver = s;
	}

	public void setYesterdayPost(String s)
	{
		yesterdayPost = s;
	}

	public void setfav(String s)
	{
		isfav = s;
	}

}
// 2131296256