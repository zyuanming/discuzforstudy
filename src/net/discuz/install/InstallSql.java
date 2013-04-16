package net.discuz.install;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import net.discuz.tools.Tools;

public class InstallSql
{

	public InstallSql()
	{}

	private static String _createAttachments()
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("Create table if not exists ")
				.append("forum_attachments").append(" (aid text primary key, ")
				.append("tid text,").append("fileName text,")
				.append("ext text,").append("price text,")
				.append("payed text,").append("url text,")
				.append("attachment text").append(");");
		return stringbuilder.toString();
	}

	private static String _createCache()
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("Create table if not exists ")
				.append("common_cache").append(" (url text primary key,")
				.append("siteappid text,").append("time integer,")
				.append("data text").append(");");
		stringbuilder.append("DROP INDEX IF EXISTS siteappid;");
		stringbuilder.append("DROP INDEX IF EXISTS url;");
		stringbuilder
				.append("CREATE INDEX siteappid on common_cache (siteappid);");
		stringbuilder.append("CREATE INDEX url on common_cache (url);");
		return stringbuilder.toString();
	}

	private static String _createForumTrack()
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("Create table if not exists ")
				.append("forum_track")
				.append(" (_id integer primary key autoincrement ,")
				.append("siteappid text,").append("name text,")
				.append("fid text").append(");");
		stringbuilder.append("DROP INDEX IF EXISTS siteappid;");
		stringbuilder.append("CREATE INDEX siteappid on ")
				.append("forum_track").append(" (siteappid);");
		stringbuilder.append("DROP INDEX IF EXISTS fid;");
		stringbuilder.append("CREATE INDEX fid on ").append("forum_track")
				.append(" (fid);");
		return stringbuilder.toString();
	}

	private static String _createGlobalkv()
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("Create table if not exists ").append("globalkv")
				.append(" (key text primary key, ").append("value text")
				.append(");");
		return stringbuilder.toString();
	}

	private static String _createLastUser()
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("Create table if not exists ")
				.append("common_lastuser")
				.append(" (siteappid text primary key, ")
				.append("username text, ").append("qq text").append(");");
		return stringbuilder.toString();
	}

	private static String _createNotifyDetails()
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("Create table if not exists ")
				.append("noticedetails")
				.append(" (_id integer primary key autoincrement ,")
				.append("siteappid text,").append("subject text,")
				.append("message text,").append("author text,")
				.append("dateline integer default 0,")
				.append("readed integer default 0,").append("cloudid text")
				.append(");");
		return stringbuilder.toString();
	}

	private static String _createNotifyList()
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("Create table if not exists ").append("notices")
				.append(" (_id integer primary key autoincrement ,")
				.append("siteappid text,").append("sitename text,")
				.append("siteicon text,").append("pm integer default 0,")
				.append("pmclick integer default 0,")
				.append("cm integer default 0,")
				.append("cmclick integer default 0,")
				.append("tm integer default 0,")
				.append("tmclick integer default 0,")
				.append("stm integer default 0,")
				.append("stmclick integer default 0,")
				.append("am integer default 0,")
				.append("amclick integer default 0,").append("amcontent text,")
				.append("cloudid text,").append("timestamp integer default 0")
				.append(");");
		return stringbuilder.toString();
	}

	private static String _createPostDraft()
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("Create table if not exists ")
				.append("forum_postdraft")
				.append(" (_id integer primary key autoincrement,")
				.append("type integer default 0,").append("value text,")
				.append("displayorder integer default 0,")
				.append("dateline integer default 0").append(");");
		return stringbuilder.toString();
	}

	private static String _createSite()
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("Create table if not exists ")
				.append("common_site")
				.append(" (siteappid integer PRIMARY KEY autoincrement,")
				.append("cookiepre text ,").append("sitekey text ,")
				.append("sitecharset text ,").append("name text ,")
				.append("url text ,").append("description text ,")
				.append("isfav integer ,").append("updated integer,")
				.append("islogo integer,").append("ver text,")
				.append("pluginver text,").append("displayorder integer,")
				.append("icon text,").append("theme text,")
				.append("clientview integer default 0,")
				.append("ucenterurl text,")
				.append("qqconnect integer default 0,").append("regname text,")
				.append("productid text,").append("cloudid text,")
				.append("isnotify integer default 1,")
				.append("isnotifyupgrade integer default 0").append(");");
		stringbuilder.append("DROP INDEX IF EXISTS siteappid;");
		stringbuilder.append("DROP INDEX IF EXISTS siteappid_isfav;");
		stringbuilder.append("DROP INDEX IF EXISTS isfav;");
		stringbuilder
				.append("CREATE INDEX siteappid_isfav on common_site(siteappid, isfav);");
		stringbuilder.append("CREATE INDEX isfav on common_site(isfav);");
		return stringbuilder.toString();
	}

	private static String _createStatistics()
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("Create table if not exists ")
				.append("discuz_stats")
				.append(" (_id integer primary key autoincrement ,")
				.append("sid text,").append("updated text,").append("op text,")
				.append("op_key text,").append("op_value text,")
				.append("url text").append(");");
		return stringbuilder.toString();
	}

	private static String _createTop1000Site()
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("Create table if not exists ")
				.append("top1000_site")
				.append(" (_id integer primary key autoincrement,")
				.append("sitecharset text ,").append("name text ,")
				.append("url text ,").append("name_url text ,")
				.append("ver text,").append("icon text,")
				.append("yesterdayPost integer,").append("productid text,")
				.append("cloudid text").append(");");
		stringbuilder.append("DROP INDEX IF EXISTS name_url;");
		stringbuilder
				.append("CREATE INDEX name_url on top1000_site(name_url);");
		return stringbuilder.toString();
	}

	private static String _createUserSession()
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("Create table if not exists ")
				.append("common_usersession")
				.append(" (_id integer primary key autoincrement ,")
				.append("siteappid text,").append("uid integer,")
				.append("username text,").append("groupid integer, ")
				.append("saltkey text,").append("cookie text,")
				.append("dateline integer,").append("formhash text,")
				.append("logintoken integer default 0").append(");");
		stringbuilder.append("DROP INDEX IF EXISTS uid;");
		stringbuilder.append("CREATE INDEX uid on common_usersession (uid);");
		return stringbuilder.toString();
	}

	public static ArrayList<String> _initCreate()
	{
		ArrayList<String> arraylist = new ArrayList<String>();
		arraylist.add(_createSite());
		arraylist.add(_createTop1000Site());
		arraylist.add(_createUserSession());
		arraylist.add(_createCache());
		arraylist.add(_createAttachments());
		arraylist.add(_createLastUser());
		arraylist.add(_createPostDraft());
		arraylist.add(_createGlobalkv());
		arraylist.add(_createForumTrack());
		arraylist.add(_createStatistics());
		arraylist.add(_createNotifyList());
		arraylist.add(_createNotifyDetails());
		return arraylist;
	}

	public static ArrayList<String> _initDrop()
	{
		ArrayList<String> arraylist = new ArrayList<String>();
		arraylist.add("DROP table if exists common_site;");
		arraylist.add("DROP table if exists common_sitefield;");
		arraylist.add("DROP table if exists common_usersession;");
		arraylist.add("DROP table if exists common_lastuser;");
		arraylist.add("DROP table if exists common_cache;");
		arraylist.add("DROP table if exists common_statsite;");
		arraylist.add("DROP table if exists forum_attachments;");
		arraylist.add("DROP table if exists forum_postdraft;");
		arraylist.add("DROP table if exists top1000_site;");
		arraylist.add("DROP table if exists forum_track;");
		arraylist.add("DROP table if exists discuz_stats;");
		arraylist.add("DROP table if exists noticedetails;");
		arraylist.add("DROP table if exists notices;");
		return arraylist;
	}

	public static ArrayList<String> _initMoveTable()
	{
		ArrayList<String> arraylist = new ArrayList<String>();
		arraylist.add("ALTER TABLE common_site RENAME TO common_site_tmp; ");
		arraylist
				.add("ALTER TABLE common_usersession RENAME TO common_usersession_tmp; ");
		return arraylist;
	}

	public static void _recover_site_table(SQLiteDatabase sqlitedatabase)
	{
		Cursor cursor = sqlitedatabase.rawQuery(
				"SELECT * FROM common_site_tmp;", null);
		if (cursor.getCount() > 0)
		{
			ContentValues contentvalues1;
			for (; cursor.moveToNext(); sqlitedatabase.insert("common_site",
					null, contentvalues1))
			{
				contentvalues1 = new ContentValues();
				contentvalues1.put("sitekey",
						cursor.getString(cursor.getColumnIndex("sitekey")));
				contentvalues1.put("sitecharset",
						cursor.getString(cursor.getColumnIndex("sitecharset")));
				contentvalues1.put("name",
						cursor.getString(cursor.getColumnIndex("name")));
				contentvalues1.put("url", Tools.trim(
						cursor.getString(cursor.getColumnIndex("url")), "/"));
				contentvalues1.put("description",
						cursor.getString(cursor.getColumnIndex("description")));
				contentvalues1.put("isfav",
						cursor.getString(cursor.getColumnIndex("isfav")));
				contentvalues1.put("islogo",
						cursor.getString(cursor.getColumnIndex("islogo")));
				if (cursor.getColumnIndex("updated") != -1)
					contentvalues1.put("updated",
							cursor.getString(cursor.getColumnIndex("updated")));
				if (cursor.getColumnIndex("icon") != -1)
					contentvalues1.put("icon",
							cursor.getString(cursor.getColumnIndex("icon")));
				if (cursor.getColumnIndex("theme") != -1)
					contentvalues1.put("theme",
							cursor.getString(cursor.getColumnIndex("theme")));
				if (cursor.getColumnIndex("clientview") != -1)
					contentvalues1.put("clientview", cursor.getString(cursor
							.getColumnIndex("clientview")));
				if (cursor.getColumnIndex("ucenterurl") != -1)
					contentvalues1.put("ucenterurl", cursor.getString(cursor
							.getColumnIndex("ucenterurl")));
				if (cursor.getColumnIndex("qqconnect") != -1)
					contentvalues1.put("qqconnect", cursor.getString(cursor
							.getColumnIndex("qqconnect")));
				if (cursor.getColumnIndex("regname") != -1)
					contentvalues1.put("regname",
							cursor.getString(cursor.getColumnIndex("regname")));
				if (cursor.getColumnIndex("productid") != -1)
					contentvalues1.put("productid", cursor.getString(cursor
							.getColumnIndex("productid")));
				if (cursor.getColumnIndex("cloudid") != -1)
					contentvalues1.put("cloudid",
							cursor.getString(cursor.getColumnIndex("cloudid")));
			}

		}
		cursor.close();
		sqlitedatabase.execSQL("DROP table if exists common_site_tmp;");
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("key", "is_first_open");
		contentvalues.put("value", "false");
		sqlitedatabase.replace("globalkv", null, contentvalues);
	}

	public static void _recover_usersession_table(SQLiteDatabase sqlitedatabase)
	{
		Cursor cursor = sqlitedatabase.rawQuery(
				"SELECT * FROM common_usersession_tmp;", null);
		if (cursor.getCount() > 0)
		{
			ContentValues contentvalues;
			for (; cursor.moveToNext(); sqlitedatabase.insert(
					"common_usersession", null, contentvalues))
			{
				contentvalues = new ContentValues();
				contentvalues.put("_id",
						cursor.getString(cursor.getColumnIndex("_id")));
				contentvalues.put("siteappid",
						cursor.getString(cursor.getColumnIndex("siteappid")));
				contentvalues.put("uid",
						cursor.getString(cursor.getColumnIndex("uid")));
				contentvalues.put("username",
						cursor.getString(cursor.getColumnIndex("username")));
				contentvalues.put("groupid",
						cursor.getString(cursor.getColumnIndex("groupid")));
				contentvalues.put("saltkey",
						cursor.getString(cursor.getColumnIndex("saltkey")));
				contentvalues.put("cookie",
						cursor.getString(cursor.getColumnIndex("cookie")));
				contentvalues.put("dateline",
						cursor.getString(cursor.getColumnIndex("dateline")));
				contentvalues.put("formhash",
						cursor.getString(cursor.getColumnIndex("formhash")));
				int i = cursor.getColumnIndex("logintoken");
				if (i <= -1)
					continue;
				String s = cursor.getString(i);
				if (s != null && !"".equals(s))
					contentvalues.put("logintoken",
							Integer.valueOf(Integer.parseInt(s)));
			}

		}
		cursor.close();
		sqlitedatabase.execSQL("DROP table if exists common_usersession_tmp;");
	}
}
//2131296256