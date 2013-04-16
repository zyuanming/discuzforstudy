package net.discuz.source.storage;

import android.content.ContentValues;
import android.database.Cursor;
import net.discuz.app.DiscuzApp;
import net.discuz.model.LoginInfo;
import net.discuz.source.DB;
import net.discuz.tools.Tools;

public class LastUserDBHelper
{

	private LastUserDBHelper()
	{}

	public static LastUserDBHelper getInstance()
	{
		return new LastUserDBHelper();
	}

	public boolean deleteAll()
	{
		return DB._delete("common_lastuser", null, null) > 0;
	}

	public boolean deleteByAppId(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("siteappid");
		stringbuilder.append("='");
		stringbuilder.append(s);
		stringbuilder.append("'");
		return DB._delete("common_lastuser", stringbuilder.toString(), null) > 0;
	}

	public int getCountByAppId(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("SELECT COUNT(*) AS COUNT FROM ");
		stringbuilder.append("common_lastuser");
		stringbuilder.append(" WHERE ");
		stringbuilder.append("siteappid");
		stringbuilder.append("='");
		stringbuilder.append(s);
		stringbuilder.append("'");
		Cursor cursor = DB._query(stringbuilder.toString());
		boolean flag = cursor.moveToNext();
		int i = 0;
		if (flag)
			i = cursor.getInt(0);
		cursor.close();
		return i;
	}

	public String getUserNameByAppId(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("siteappid");
		stringbuilder.append("='");
		stringbuilder.append(s);
		stringbuilder.append("'");
		Cursor cursor = DB._select(true, "common_lastuser", columns,
				stringbuilder.toString(), null, null, null, null, "1");
		boolean flag = cursor.moveToFirst();
		String s1 = null;
		if (flag)
			s1 = cursor.getString(0);
		cursor.close();
		return s1;
	}

	public boolean insert(String s, String s1)
	{
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("siteappid", s);
		contentvalues.put("username", DiscuzApp.getInstance().getLoginUser(s)
				.getUsername());
		return DB._insert("common_lastuser", contentvalues) > 0L;
	}

	public boolean update(LoginInfo logininfo, String s)
	{
		ContentValues contentvalues = logininfo._getSqlVal();
		contentvalues.put("dataline", Long.valueOf(Tools._getTimeStamp()));
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("siteappid");
		stringbuilder.append("='");
		stringbuilder.append(s);
		stringbuilder.append("'");
		return DB._update("common_lastuser", contentvalues,
				stringbuilder.toString(), null) > 0;
	}

	public static final String TABLE_NAME = "common_lastuser";
	private final String KEY_DATELINE = "dataline";
	private final String KEY_QQ = "qq";
	private final String KEY_SITE_APP_ID = "siteappid";
	private final String KEY_USERNAME = "username";
	private final String columns[] = { "username" };
}
//2131296256