package net.discuz.source.storage;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
import net.discuz.model.LoginInfo;
import net.discuz.source.DB;
import net.discuz.tools.Tools;

public class UserSessionDBHelper
{

	private UserSessionDBHelper()
	{}

	private LoginInfo constructMember(Cursor cursor)
	{
		LoginInfo logininfo = new LoginInfo();
		logininfo.setSiteAppid(cursor.getString(1));
		logininfo.setUid(cursor.getInt(2));
		logininfo.setUsername(cursor.getString(3));
		logininfo.setGroupid(cursor.getInt(4));
		logininfo.setSaltkey(cursor.getString(5));
		logininfo.setCookie(cursor.getString(6));
		logininfo.setFormHash(cursor.getString(8));
		logininfo.setLoginToken(cursor.getString(9));
		return logininfo;
	}

	public static UserSessionDBHelper getInstance()
	{
		return new UserSessionDBHelper();
	}

	public boolean deleteAll()
	{
		return DB._delete("common_usersession", null, null) > 0;
	}

	public boolean deleteByAppId(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("siteappid");
		stringbuilder.append("='");
		stringbuilder.append(s);
		stringbuilder.append("'");
		return DB._delete("common_usersession", stringbuilder.toString(), null) > 0;
	}

	public List getAllLoginToken()
	{
		ArrayList arraylist = new ArrayList();
		Cursor cursor = DB._select(true, "common_usersession", columns,
				"logintoken=='0'", null, null, null, null, null);
		if (cursor.moveToFirst())
			do
				arraylist.add(constructMember(cursor));
			while (cursor.moveToNext());
		cursor.close();
		return arraylist;
	}

	public LoginInfo getByAppId(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("siteappid");
		stringbuilder.append("='");
		stringbuilder.append(s);
		stringbuilder.append("'");
		Cursor cursor = DB._select(true, "common_usersession", columns,
				stringbuilder.toString(), null, null, null, null, "1");
		boolean flag = cursor.moveToFirst();
		LoginInfo logininfo = null;
		if (flag)
			logininfo = constructMember(cursor);
		cursor.close();
		return logininfo;
	}

	public int getCountByAppId(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("SELECT COUNT(*) AS COUNT FROM ");
		stringbuilder.append("common_usersession");
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

	public boolean insert(LoginInfo logininfo, String s)
	{
		ContentValues contentvalues = logininfo._getSqlVal();
		contentvalues.put("dateline", Long.valueOf(Tools._getTimeStamp()));
		contentvalues.put("siteappid", s);
		return DB._insert("common_usersession", contentvalues) > 0L;
	}

	public boolean update(LoginInfo logininfo, String s)
	{
		ContentValues contentvalues = logininfo._getSqlVal();
		contentvalues.put("dateline", Long.valueOf(Tools._getTimeStamp()));
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("siteappid");
		stringbuilder.append("='");
		stringbuilder.append(s);
		stringbuilder.append("'");
		return DB._update("common_usersession", contentvalues,
				stringbuilder.toString(), null) > 0;
	}

	public static final String TABLE_NAME = "common_usersession";
	private final String KEY_COOKIE = "cookie";
	private final String KEY_DATELINE = "dateline";
	private final String KEY_FORMHASH = "formhash";
	private final String KEY_GROUPID = "groupid";
	private final String KEY_ID = "_id";
	private final String KEY_LOGINTOKEN = "logintoken";
	private final String KEY_SALTKEY = "saltkey";
	private final String KEY_SITE_APP_ID = "siteappid";
	private final String KEY_UID = "uid";
	private final String KEY_USERNAME = "username";
	private final String columns[] = { "_id", "siteappid", "uid", "username",
			"groupid", "saltkey", "cookie", "dateline", "formhash",
			"logintoken" };
}
//2131296256