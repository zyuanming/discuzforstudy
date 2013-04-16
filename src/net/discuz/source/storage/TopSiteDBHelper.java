package net.discuz.source.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;
import net.discuz.model.SiteInfo;
import net.discuz.source.DB;

public class TopSiteDBHelper
{

	private TopSiteDBHelper()
	{}

	public static TopSiteDBHelper getInstance()
	{
		if (dbObj == null)
			dbObj = new TopSiteDBHelper();
		return dbObj;
	}

	public boolean deleteAll()
	{
		return DB._delete("top1000_site", null, null) > 0;
	}

	public List getAll()
	{
		Cursor cursor = DB._select(true, "top1000_site", columns, "name<>''",
				null, null, null, "[_id] asc", null);
		boolean flag = cursor.moveToFirst();
		ArrayList arraylist = null;
		if (flag)
		{
			arraylist = new ArrayList();
			do
			{
				SiteInfo siteinfo = new SiteInfo();
				siteinfo.setSiteName(cursor.getString(1).toLowerCase());
				siteinfo.setSiteUrl(cursor.getString(2).toLowerCase());
				siteinfo.setSiteNameUrl(cursor.getString(3).toLowerCase());
				siteinfo.setIcon(cursor.getString(5));
				siteinfo.setSiteCharset(cursor.getString(0));
				siteinfo.setVersion(cursor.getString(4));
				siteinfo.setProductid(cursor.getString(7));
				siteinfo.setCloudid(cursor.getString(8));
				arraylist.add(siteinfo);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return arraylist;
	}

	public List getByNameUrl(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("name");
		stringbuilder.append("<>''");
		String s1;
		Cursor cursor;
		boolean flag;
		ArrayList arraylist;
		if (s.length() != 0)
		{
			stringbuilder.append(" AND ");
			stringbuilder.append("name_url");
			stringbuilder.append(" like '%");
			stringbuilder.append(s);
			stringbuilder.append("%'");
			s1 = "100";
		} else
		{
			s1 = "50";
		}
		cursor = DB._select(true, "top1000_site", columns,
				stringbuilder.toString(), null, null, null, "[_id] asc", s1);
		flag = cursor.moveToFirst();
		arraylist = null;
		if (flag)
		{
			arraylist = new ArrayList();
			do
			{
				SiteInfo siteinfo = new SiteInfo();
				String s2 = cursor.getString(1).toLowerCase();
				String s3 = cursor.getString(2).toLowerCase();
				if (s.length() != 0)
				{
					StringBuilder stringbuilder1 = new StringBuilder();
					stringbuilder1.append("<font color=red>");
					stringbuilder1.append(s);
					stringbuilder1.append("</font>");
					String s4 = stringbuilder1.toString();
					siteinfo.setSiteName(s2.replace(s, s4));
					siteinfo.setSiteUrl(s3.replace(s, s4));
				} else
				{
					siteinfo.setSiteName(s2);
					siteinfo.setSiteUrl(s3);
				}
				siteinfo.setSiteNameUrl(cursor.getString(3));
				siteinfo.setIcon(cursor.getString(5));
				siteinfo.setSiteCharset(cursor.getString(0));
				siteinfo.setVersion(cursor.getString(4));
				siteinfo.setProductid(cursor.getString(7));
				siteinfo.setCloudid(cursor.getString(8));
				siteinfo.setFlag(1);
				arraylist.add(siteinfo);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return arraylist;
	}

	public List getByNameUrl(String s, int i)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("name");
		stringbuilder.append("<>''");
		if (s.length() != 0)
		{
			stringbuilder.append(" AND ");
			stringbuilder.append("name_url");
			stringbuilder.append(" like '%");
			stringbuilder.append(s);
			stringbuilder.append("%'");
		}
		Cursor cursor = DB._select(true, "top1000_site", columns,
				stringbuilder.toString(), null, null, null, "[_id] asc",
				(new StringBuilder()).append(i).append("").toString());
		boolean flag = cursor.moveToFirst();
		ArrayList arraylist = null;
		if (flag)
		{
			arraylist = new ArrayList();
			do
			{
				SiteInfo siteinfo = new SiteInfo();
				String s1 = cursor.getString(1).toLowerCase();
				String s2 = cursor.getString(2).toLowerCase();
				if (s.length() != 0)
				{
					StringBuilder stringbuilder1 = new StringBuilder();
					stringbuilder1.append("<font color=red>");
					stringbuilder1.append(s);
					stringbuilder1.append("</font>");
					String s3 = stringbuilder1.toString();
					siteinfo.setSiteName(s1.replace(s, s3));
					siteinfo.setSiteUrl(s2.replace(s, s3));
				} else
				{
					siteinfo.setSiteName(s1);
					siteinfo.setSiteUrl(s2);
				}
				siteinfo.setSiteNameUrl(cursor.getString(3));
				siteinfo.setIcon(cursor.getString(5));
				siteinfo.setSiteCharset(cursor.getString(0));
				siteinfo.setVersion(cursor.getString(4));
				siteinfo.setProductid(cursor.getString(7));
				siteinfo.setCloudid(cursor.getString(8));
				siteinfo.setFlag(1);
				arraylist.add(siteinfo);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return arraylist;
	}

	public int getCount()
	{
		int i = 0;
		Cursor cursor;
		boolean flag;
		try
		{
			cursor = DB._query("SELECT COUNT(*) AS COUNT FROM top1000_site");
			flag = cursor.moveToNext();
		} catch (Exception exception)
		{
			return i;
		}
		if (flag)
			i = cursor.getInt(0);
		cursor.close();
		return i;
	}

	public boolean insert(SiteInfo siteinfo)
	{
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("sitecharset", siteinfo.getSiteCharset());
		contentvalues.put("name",
				siteinfo.getSiteName().replaceAll("'", "\u2019"));
		contentvalues.put("url", siteinfo.getSiteUrl()
				.replaceAll("'", "\u2019"));
		contentvalues.put("name_url", siteinfo.getSiteUrl().toLowerCase()
				.replaceAll("'", "\u2019").replaceAll("http://www.", "")
				.replace("http://", ""));
		contentvalues.put("ver", siteinfo.getVersion());
		contentvalues.put("icon", siteinfo.getIcon());
		contentvalues.put("yesterdayPost", siteinfo.getYesterdayPost());
		contentvalues.put("productid", siteinfo.getProductid());
		contentvalues.put("cloudid", siteinfo.getCloudId());
		return DB._insert("top1000_site", contentvalues) > 0L;
	}

	public boolean insertBatch(List list)
	{
		if (list != null)
		{
			SQLiteDatabase sqlitedatabase = DB.getDatabase();
			sqlitedatabase.beginTransaction();
			int i = 0;
			boolean flag = false;
			while (i < list.size())
			{
				SiteInfo siteinfo = (SiteInfo) list.get(i);
				ContentValues contentvalues = new ContentValues();
				contentvalues.put("sitecharset", siteinfo.getSiteCharset());
				contentvalues.put("name",
						siteinfo.getSiteName().replaceAll("'", "\u2019"));
				contentvalues.put("url",
						siteinfo.getSiteUrl().replaceAll("'", "\u2019"));
				String s = (new StringBuilder()).append("/")
						.append(siteinfo.getSiteUrl()).append("/").toString()
						.toLowerCase().replaceAll("'", "\u2019")
						.replaceAll("http://www.", "").replaceAll("http:", "")
						.replaceAll("/www.", "").replaceAll(".com.cn/", "./")
						.replaceAll(".com/", "./").replaceAll(".net/", "./")
						.replaceAll(".gov.cn/", "./").replaceAll(".gov/", "./")
						.replaceAll(".org/", "./").replaceAll(".cn/", "./");
				if (s.indexOf("./") > 0)
					s = s.substring(0, s.indexOf("./"));
				contentvalues.put("name_url", s.replaceAll("/", ""));
				contentvalues.put("ver", siteinfo.getVersion());
				contentvalues.put("icon", siteinfo.getIcon());
				contentvalues.put("yesterdayPost", siteinfo.getYesterdayPost());
				contentvalues.put("productid", siteinfo.getProductid());
				contentvalues.put("cloudid", siteinfo.getCloudId());
				boolean flag1;
				if (sqlitedatabase.replace("top1000_site", null, contentvalues) > 0L)
					flag1 = true;
				else
					flag1 = false;
				i++;
				flag = flag1;
			}
			sqlitedatabase.setTransactionSuccessful();
			sqlitedatabase.endTransaction();
			return flag;
		} else
		{
			return false;
		}
	}

	public static final String TABLE_NAME = "top1000_site";
	private static TopSiteDBHelper dbObj = null;
	private final String KEY_CLOUD_ID = "cloudid";
	private final String KEY_ICON = "icon";
	private final String KEY_NAME_URL = "name_url";
	private final String KEY_PRODUCT_ID = "productid";
	private final String KEY_SITE_CHARSET = "sitecharset";
	private final String KEY_SITE_NAME = "name";
	private final String KEY_URL = "url";
	private final String KEY_VER = "ver";
	private final String KEY_YESTERDAY_POST = "yesterdayPost";
	private final String columns[] = { "sitecharset", "name", "url",
			"name_url", "ver", "icon", "yesterdayPost", "productid", "cloudid",
			"_id" };

}
//2131296256