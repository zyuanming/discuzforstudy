package net.discuz.source.storage;

import android.content.ContentValues;
import android.database.Cursor;
import net.discuz.source.DB;

public class DiscuzStatsDBHelper
{

	private DiscuzStatsDBHelper()
	{}

	public static DiscuzStatsDBHelper getInstance()
	{
		if (dbObj == null)
			dbObj = new DiscuzStatsDBHelper();
		return dbObj;
	}

	public boolean deleteAll()
	{
		return DB._delete("discuz_stats", null, null) > 0;
	}

	public boolean insert(String s, long l, String s1, String s2, String s3,
			String s4)
	{
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("sid", s);
		contentvalues.put("updated", Long.valueOf(l));
		contentvalues.put("op", s1);
		contentvalues.put("url", s2);
		contentvalues.put("op_key", s3);
		contentvalues.put("op_value", s4);
		return DB._insert("discuz_stats", contentvalues) > 0L;
	}

	public String selectAll()
	{
		StringBuilder stringbuilder = new StringBuilder();
		Cursor cursor = DB._select(true, "discuz_stats", columns, null, null,
				null, null, null, null);
		if (cursor.moveToFirst())
			do
				stringbuilder.append(cursor.getString(0)).append(",")
						.append(cursor.getString(1)).append(",")
						.append(cursor.getString(2)).append(",")
						.append(cursor.getString(3)).append(",")
						.append(cursor.getString(4)).append(",")
						.append(cursor.getString(5)).append(";");
			while (cursor.moveToNext());
		cursor.close();
		return stringbuilder.toString();
	}

	public static final String TABLE_NAME = "discuz_stats";
	private static DiscuzStatsDBHelper dbObj = null;
	private final String KEY_OP = "op";
	private final String KEY_OP_KEY = "op_key";
	private final String KEY_OP_VALUE = "op_value";
	private final String KEY_SID = "sid";
	private final String KEY_UPDATED = "updated";
	private final String KEY_URL = "url";
	private final String columns[] = { "sid", "updated", "op", "url", "op_key",
			"op_value" };

}
//2131296256