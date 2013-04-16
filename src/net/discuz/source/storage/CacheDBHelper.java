package net.discuz.source.storage;

import android.content.ContentValues;
import android.database.Cursor;
import net.discuz.source.DB;

public class CacheDBHelper
{

	private CacheDBHelper()
	{}

	public static CacheDBHelper getInstance()
	{
		if (dbObj == null)
			dbObj = new CacheDBHelper();
		return dbObj;
	}

	public boolean deleteAll()
	{
		return DB._delete("common_cache", null, null) > 0;
	}

	public String getByUrl(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("url");
		stringbuilder.append("='");
		stringbuilder.append(s);
		stringbuilder.append("'");
		Cursor cursor = DB._select(true, "common_cache", columns,
				stringbuilder.toString(), null, null, null, null, "1");
		boolean flag = cursor.moveToFirst();
		String s1 = null;
		if (flag)
			s1 = cursor.getString(0);
		cursor.close();
		return s1;
	}

	public boolean replaceCache(String s, String s1, long l, String s2)
	{
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("siteappid", s);
		contentvalues.put("url", s1);
		contentvalues.put("time", Long.valueOf(l));
		contentvalues.put("data", s2);
		return DB._replace("common_cache", contentvalues) > 0L;
	}

	public static final String TABLE_NAME = "common_cache";
	private static CacheDBHelper dbObj = null;
	private final String KEY_DATA = "data";
	private final String KEY_SITE_APP_ID = "siteappid";
	private final String KEY_TIME = "time";
	private final String KEY_URL = "url";
	private final String columns[] = { "data" };

}
//2131296256