package net.discuz.source.storage;

import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
import net.discuz.model.NoticeListItem;
import net.discuz.source.DB;

public class NoticeListDBHelper
{

	private NoticeListDBHelper()
	{}

	private NoticeListItem constructMember(Cursor cursor)
	{
		NoticeListItem noticelistitem = new NoticeListItem();
		noticelistitem._id = cursor.getString(0);
		noticelistitem.siteappid = cursor.getString(1);
		noticelistitem.sitename = cursor.getString(2);
		noticelistitem.siteicon = cursor.getString(3);
		noticelistitem.pm = Integer.valueOf(cursor.getInt(4));
		noticelistitem.pmclick = Integer.valueOf(cursor.getInt(5));
		noticelistitem.cm = Integer.valueOf(cursor.getInt(6));
		noticelistitem.cmclick = Integer.valueOf(cursor.getInt(7));
		noticelistitem.tm = Integer.valueOf(cursor.getInt(8));
		noticelistitem.tmclick = Integer.valueOf(cursor.getInt(9));
		noticelistitem.stm = Integer.valueOf(cursor.getInt(10));
		noticelistitem.stmclick = Integer.valueOf(cursor.getInt(11));
		noticelistitem.am = Integer.valueOf(cursor.getInt(12));
		noticelistitem.amclick = Integer.valueOf(cursor.getInt(13));
		noticelistitem.amcontent = cursor.getString(14);
		noticelistitem.cloudid = cursor.getString(15);
		noticelistitem.timestamp = cursor.getString(16);
		return noticelistitem;
	}

	public static NoticeListDBHelper getInstance()
	{
		if (dbObj == null)
			dbObj = new NoticeListDBHelper();
		return dbObj;
	}

	public boolean deleteAll()
	{
		return DB._delete("notices", null, null) > 0;
	}

	public boolean deleteByCloudId(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("cloudid");
		stringbuilder.append("='");
		stringbuilder.append(s);
		stringbuilder.append("'");
		return DB._delete("notices", stringbuilder.toString(), null) > 0;
	}

	public List getAll()
	{
		Cursor cursor = DB._select(true, "notices", columns, "", null, null,
				null, "_id desc", null);
		boolean flag = cursor.moveToFirst();
		ArrayList arraylist = null;
		if (flag)
		{
			arraylist = new ArrayList();
			do
				arraylist.add(constructMember(cursor));
			while (cursor.moveToNext());
		}
		cursor.close();
		return arraylist;
	}

	public boolean insert(NoticeListItem noticelistitem)
	{
		return DB._insert("notices", noticelistitem._getSqlVal()) > 0L;
	}

	public NoticeListItem select(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("cloudid");
		stringbuilder.append("='");
		stringbuilder.append(s);
		stringbuilder.append("'");
		Cursor cursor = DB._select(true, "notices", columns,
				stringbuilder.toString(), null, null, null, null, "1");
		boolean flag = cursor.moveToFirst();
		NoticeListItem noticelistitem = null;
		if (flag)
			noticelistitem = constructMember(cursor);
		cursor.close();
		return noticelistitem;
	}

	public NoticeListItem selectUnClick()
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("pmclick");
		stringbuilder.append("='0' OR ");
		stringbuilder.append("cmclick");
		stringbuilder.append("='0'");
		Cursor cursor = DB._select(true, "notices", columns,
				stringbuilder.toString(), null, null, null, null, "1");
		boolean flag = cursor.moveToFirst();
		NoticeListItem noticelistitem = null;
		if (flag)
			noticelistitem = constructMember(cursor);
		cursor.close();
		return noticelistitem;
	}

	public boolean update(NoticeListItem noticelistitem)
	{
		return DB._update("notices", noticelistitem._getSqlVal(),
				(new StringBuilder()).append("_id='")
						.append(noticelistitem._id).append("'").toString(),
				null) > 0;
	}

	public static final String TABLE_NAME = "notices";
	private static NoticeListDBHelper dbObj;
	private final String KEY_AM = "am";
	private final String KEY_AMCLICK = "amclick";
	private final String KEY_AMCONTENT = "amcontent";
	private final String KEY_CLOUDID = "cloudid";
	private final String KEY_CM = "cm";
	private final String KEY_CMCLICK = "cmclick";
	private final String KEY_ID = "_id";
	private final String KEY_PM = "pm";
	private final String KEY_PMCLICK = "pmclick";
	private final String KEY_SITEAPPID = "siteappid";
	private final String KEY_SITEICON = "siteicon";
	private final String KEY_SITENAME = "sitename";
	private final String KEY_STM = "stm";
	private final String KEY_STMCLICK = "stmclick";
	private final String KEY_TIMESTAMP = "timestamp";
	private final String KEY_TM = "tm";
	private final String KEY_TMCLICK = "tmclick";
	private final String columns[] = { "_id", "siteappid", "sitename",
			"siteicon", "pm", "pmclick", "cm", "cmclick", "tm", "tmclick",
			"stm", "stmclick", "am", "amclick", "amcontent", "cloudid",
			"timestamp" };
}
//2131296256