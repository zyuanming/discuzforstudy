package net.discuz.source.storage;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.HashMap;
import net.discuz.source.DB;

public class ForumTrackDBHelper
{

	private ForumTrackDBHelper()
	{}

	public static ForumTrackDBHelper getInstance()
	{
		if (dbObj == null)
			dbObj = new ForumTrackDBHelper();
		return dbObj;
	}

	private boolean insert(String s, String s1, String s2)
	{
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("siteappid", s);
		contentvalues.put("fid", s1);
		contentvalues.put("name", s2);
		return DB._insert("forum_track", contentvalues) > 0L;
	}

	public boolean addForumTrack(String s, String s1, String s2)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("SELECT COUNT(1) AS COUNT FROM ");
		stringbuilder.append("forum_track");
		if (s1 != null)
			stringbuilder.append(" WHERE siteappid='").append(s)
					.append("' AND fid='").append(s1).append("'");
		Cursor cursor = DB._query(stringbuilder.toString());
		int i;
		if (cursor.moveToFirst())
			i = cursor.getInt(0);
		else
			i = 0;
		cursor.close();
		if (i >= 30)
			return false;
		else
			return insert(s, s1, s2);
	}

	public boolean deleteAll()
	{
		return DB._delete("forum_track", null, null) > 0;
	}

	public void dequeueForumTrack()
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("DELETE FROM forum_track ");
		stringbuilder
				.append("WHERE _id < (SELECT MIN(_id) FROM (SELECT _id FROM forum_track ORDER BY _id DESC LIMIT 0,100))");
		DB._query(stringbuilder.toString()).close();
	}

	public ArrayList selectForumTrack(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("SELECT fid,name FROM forum_track ");
		stringbuilder
				.append("WHERE siteappid=")
				.append(s)
				.append(" group by fid,name order by count(fid) desc limit 0,2");
		Cursor cursor = DB._query(stringbuilder.toString());
		boolean flag = cursor.moveToFirst();
		ArrayList arraylist = null;
		if (flag)
		{
			arraylist = new ArrayList();
			do
			{
				HashMap hashmap = new HashMap();
				hashmap.put("fid", cursor.getString(0));
				hashmap.put("name", cursor.getString(1));
				arraylist.add(hashmap);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return arraylist;
	}

	public static final String TABLE_NAME = "forum_track";
	private static ForumTrackDBHelper dbObj = null;
	private final String KEY_FORUM_ID = "fid";
	private final String KEY_FROUM_NAME = "name";
	private final String KEY_SITE_APP_ID = "siteappid";
	private final String columns[] = { "fid", "name" };

}
//2131296256