package net.discuz.source.storage;

import android.content.ContentValues;
import android.database.Cursor;
import net.discuz.source.DB;

public class GlobalDBHelper
{

	private GlobalDBHelper()
	{}

	public static GlobalDBHelper getInstance()
	{
		if (dbObj == null)
			dbObj = new GlobalDBHelper();
		return dbObj;
	}

	public String getValue(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("key");
		stringbuilder.append("='");
		stringbuilder.append(s);
		stringbuilder.append("'");
		Cursor cursor = DB._select(true, "globalkv", columns,
				stringbuilder.toString(), null, null, null, null, "1");
		boolean flag = cursor.moveToFirst();
		String s1 = null;
		if (flag)
			s1 = cursor.getString(0);
		cursor.close();
		return s1;
	}

	public boolean replace(String s, String s1)
	{
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("key", s);
		contentvalues.put("value", s1);
		return DB._replace("globalkv", contentvalues) > 0L;
	}

	public static final String TABLE_NAME = "globalkv";
	private static GlobalDBHelper dbObj;
	private final String KEY_KEY = "key";
	private final String KEY_VALUE = "value";
	private final String columns[] = { "value" };
}
//2131296256