package net.discuz.source.storage;

import android.content.ContentValues;
import android.database.Cursor;
import java.text.SimpleDateFormat;
import java.util.*;
import net.discuz.model.SiteInfo;
import net.discuz.source.DB;
import net.discuz.tools.Tools;

public class SiteInfoDBHelper
{

	private SiteInfoDBHelper()
	{
		sdf = new SimpleDateFormat("yyyyMMdd");
	}

	private SiteInfo constructSiteInfo(Cursor cursor)
	{
		SiteInfo siteinfo = new SiteInfo();
		siteinfo.setSiteAppid(cursor.getString(0));
		siteinfo.setSiteName(cursor.getString(1));
		siteinfo.setSiteUrl(cursor.getString(2));
		siteinfo.setSiteCharset(cursor.getString(3));
		siteinfo.setRegName(cursor.getString(4));
		siteinfo.setUpdated(cursor.getString(5));
		siteinfo.setClientview(cursor.getString(6));
		siteinfo.setUCenterUrl(cursor.getString(7));
		siteinfo.setQQConnect(cursor.getString(8));
		siteinfo.setVersion(cursor.getString(9));
		siteinfo.setDescription(cursor.getString(10));
		siteinfo.setIsNotify(cursor.getString(11));
		siteinfo.setCloudid(cursor.getString(12));
		siteinfo.setIsNotifyUpgrade(cursor.getString(13));
		siteinfo.setPluginVersion(cursor.getString(14));
		return siteinfo;
	}

	public static SiteInfoDBHelper getInstance()
	{
		if (dbObj == null)
			dbObj = new SiteInfoDBHelper();
		return dbObj;
	}

	public boolean delete(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("siteappid");
		stringbuilder.append("='");
		stringbuilder.append(s);
		stringbuilder.append("'");
		return DB._delete("common_site", stringbuilder.toString(), null) > 0;
	}

	public List getAll()
	{
		ArrayList arraylist = new ArrayList();
		Cursor cursor = DB._select(true, "common_site", columns, null, null,
				null, null, null, null);
		if (cursor.moveToFirst())
			do
				arraylist.add(constructSiteInfo(cursor));
			while (cursor.moveToNext());
		cursor.close();
		return arraylist;
	}

	public List getAllClientView()
	{
		ArrayList arraylist = new ArrayList();
		Cursor cursor = DB._select(true, "common_site", columns,
				"clientview=='1'", null, null, null, null, null);
		if (cursor.moveToFirst())
			do
				arraylist.add(constructSiteInfo(cursor));
			while (cursor.moveToNext());
		cursor.close();
		return arraylist;
	}

	/**
	 * 获得所有站点的通知信息
	 * 
	 * @return
	 */
	public List getAllSiteNotice()
	{
		ArrayList arraylist = new ArrayList();
		Cursor cursor = DB._select(true, "common_site", columns,
				"isnotify==1 AND isnotifyupgrade==0", null, null, null, null,
				null);
		if (cursor.moveToFirst())
			do
				arraylist.add(constructSiteInfo(cursor));
			while (cursor.moveToNext());
		cursor.close();
		return arraylist;
	}

	public SiteInfo getByAppId(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("siteappid");
		stringbuilder.append("='");
		stringbuilder.append(s);
		stringbuilder.append("'");
		Cursor cursor = DB._select(true, "common_site", columns,
				stringbuilder.toString(), null, null, null, null, "1");
		boolean flag = cursor.moveToFirst();
		SiteInfo siteinfo = null;
		if (flag)
			siteinfo = constructSiteInfo(cursor);
		cursor.close();
		return siteinfo;
	}

	public SiteInfo getByCloudId(int i)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("cloudid");
		stringbuilder.append("='");
		stringbuilder.append(i);
		stringbuilder.append("'");
		Cursor cursor = DB._select(true, "common_site", columns,
				stringbuilder.toString(), null, null, null, null, "1");
		boolean flag = cursor.moveToFirst();
		SiteInfo siteinfo = null;
		if (flag)
			siteinfo = constructSiteInfo(cursor);
		cursor.close();
		return siteinfo;
	}

	public SiteInfo getByUrl(String s)
	{
		String s1 = Tools.trim(s, "/");
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("url");
		stringbuilder.append("='");
		stringbuilder.append(s1);
		stringbuilder.append("'");
		Cursor cursor = DB._select(true, "common_site", columns,
				stringbuilder.toString(), null, null, null, null, "1");
		boolean flag = cursor.moveToFirst();
		SiteInfo siteinfo = null;
		if (flag)
			siteinfo = constructSiteInfo(cursor);
		cursor.close();
		return siteinfo;
	}

	public int getCount(String s, String s1)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("SELECT COUNT(*) AS COUNT FROM ");
		stringbuilder.append("common_site");
		if (s != null && s1 != null)
		{
			stringbuilder.append(" WHERE ");
			stringbuilder.append(s);
			stringbuilder.append("='");
			stringbuilder.append(s1);
			stringbuilder.append("'");
		}
		Cursor cursor = DB._query(stringbuilder.toString());
		boolean flag = cursor.moveToFirst();
		int i = 0;
		if (flag)
			i = cursor.getInt(0);
		cursor.close();
		return i;
	}

	public List getFav()
	{
		Cursor cursor = DB._select(true, "common_site", columns, "isfav='1'",
				null, null, null, null, null);
		boolean flag = cursor.moveToFirst();
		ArrayList arraylist = null;
		if (flag)
		{
			arraylist = new ArrayList();
			do
				arraylist.add(constructSiteInfo(cursor));
			while (cursor.moveToNext());
		}
		cursor.close();
		return arraylist;
	}

	public int getFavCount(String s, String s1)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("SELECT COUNT(*) AS COUNT FROM ");
		stringbuilder.append("common_site");
		Cursor cursor;
		boolean flag;
		int i;
		if (s != null && s1 != null)
		{
			stringbuilder.append(" WHERE ");
			stringbuilder.append(s);
			stringbuilder.append("='");
			stringbuilder.append(s1);
			stringbuilder.append("'");
			stringbuilder.append("' AND isfav = '1' LIMIT 1");
		} else
		{
			stringbuilder.append("' WHERE isfav = '1' LIMIT 1");
		}
		cursor = DB._query(stringbuilder.toString());
		flag = cursor.moveToFirst();
		i = 0;
		if (flag)
			i = cursor.getInt(0);
		cursor.close();
		return i;
	}

	public List getNeedUpdate()
	{
		ArrayList arraylist = new ArrayList();
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("updated");
		stringbuilder.append("<>'");
		stringbuilder.append(sdf.format(Long.valueOf((new Date()).getTime())));
		stringbuilder.append("'");
		Cursor cursor = DB._select(true, "common_site", columns,
				stringbuilder.toString(), null, null, null, null, null);
		if (cursor.moveToFirst())
			do
				arraylist.add(constructSiteInfo(cursor));
			while (cursor.moveToNext());
		cursor.close();
		return arraylist;
	}

	public long insert(SiteInfo siteinfo)
	{
		return DB._insert("common_site", siteinfo.getSqlValue());
	}

	public long replaceSite(String s, String s1)
	{
		SiteInfo siteinfo = getByUrl(s);
		if (siteinfo != null && siteinfo.getSiteUrl().equals(s))
		{
			siteinfo.setSiteName(s1);
			updateSiteByUrl(s, siteinfo);
			return (long) Integer.parseInt(siteinfo.getSiteAppid());
		} else
		{
			ContentValues contentvalues = new ContentValues();
			contentvalues.put("url", s);
			contentvalues.put("name", s1);
			return DB._replace("common_site", contentvalues);
		}
	}

	public boolean updateClientViewByAppId(String s, String s1)
	{
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("updated",
				sdf.format(Long.valueOf((new Date()).getTime())));
		contentvalues.put("clientview", s);
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("siteappid");
		stringbuilder.append("='");
		stringbuilder.append(s1);
		stringbuilder.append("'");
		return DB._update("common_site", contentvalues,
				stringbuilder.toString(), null) > 0;
	}

	public boolean updateClientViewByUrl(String s, String s1)
	{
		String s2 = Tools.trim(s1, "/");
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("updated",
				sdf.format(Long.valueOf((new Date()).getTime())));
		contentvalues.put("clientview", s);
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("url");
		stringbuilder.append("='");
		stringbuilder.append(s2);
		stringbuilder.append("'");
		return DB._update("common_site", contentvalues,
				stringbuilder.toString(), null) > 0;
	}

	public boolean updateSiteByAppId(String s, SiteInfo siteinfo)
	{
		boolean flag = false;
		if (siteinfo != null)
		{
			ContentValues contentvalues = siteinfo.getSqlValue();
			contentvalues.put("updated",
					sdf.format(Long.valueOf((new Date()).getTime())));
			StringBuilder stringbuilder = new StringBuilder();
			stringbuilder.append("siteappid");
			stringbuilder.append("='");
			stringbuilder.append(s);
			stringbuilder.append("'");
			int i = DB._update("common_site", contentvalues,
					stringbuilder.toString(), null);
			flag = false;
			if (i > 0)
				flag = true;
		}
		return flag;
	}

	public boolean updateSiteByUrl(String s, SiteInfo siteinfo)
	{
		String s1 = Tools.trim(s, "/");
		boolean flag = false;
		if (siteinfo != null)
		{
			ContentValues contentvalues = siteinfo.getSqlValue();
			contentvalues.put("updated",
					sdf.format(Long.valueOf((new Date()).getTime())));
			StringBuilder stringbuilder = new StringBuilder();
			stringbuilder.append("url");
			stringbuilder.append("='");
			stringbuilder.append(s1);
			stringbuilder.append("'");
			int i = DB._update("common_site", contentvalues,
					stringbuilder.toString(), null);
			flag = false;
			if (i > 0)
				flag = true;
		}
		return flag;
	}

	public static final String TABLE_NAME = "common_site";
	private static SiteInfoDBHelper dbObj = null;
	private final String KEY_CLIENT_VIEW = "clientview";
	private final String KEY_CLOUD_ID = "cloudid";
	private final String KEY_COOKIE_PRE = "cookiepre";
	private final String KEY_DESCRIPTION = "description";
	private final String KEY_DISPLAY_ORDER = "displayorder";
	private final String KEY_ICON = "icon";
	private final String KEY_IS_FAV = "isfav";
	private final String KEY_IS_LOGO = "islogo";
	private final String KEY_IS_NOTIFY = "isnotify";
	private final String KEY_IS_NOTIFY_UPGRADE = "isnotifyupgrade";
	private final String KEY_PLUGIN_VER = "pluginver";
	private final String KEY_PRODUCT_ID = "productid";
	private final String KEY_QQ_CONNECT = "qqconnect";
	private final String KEY_REG_NAME = "regname";
	private final String KEY_SITE_APP_ID = "siteappid";
	private final String KEY_SITE_CHARSET = "sitecharset";
	private final String KEY_SITE_KEY = "sitekey";
	private final String KEY_SITE_NAME = "name";
	private final String KEY_THEME = "theme";
	private final String KEY_UCENTER_URL = "ucenterurl";
	private final String KEY_UPDATED = "updated";
	private final String KEY_URL = "url";
	private final String KEY_VER = "ver";
	private final String columns[] = { "siteappid", "name", "url",
			"sitecharset", "regname", "updated", "clientview", "ucenterurl",
			"qqconnect", "ver", "description", "isnotify", "cloudid",
			"isnotifyupgrade", "pluginver" };
	private SimpleDateFormat sdf;

}
// 2131296256