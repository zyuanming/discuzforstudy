package net.discuz.source.storage;

import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
import net.discuz.model.NoticeDetail;
import net.discuz.source.DB;

public class NoticeDetailDBHelper
{

	private NoticeDetailDBHelper()
	{
		KEY_ID = "_id";
		KEY_SITEAPPID = "siteappid";
		KEY_SUBJECT = "subject";
		KEY_MESSAGE = "message";
		KEY_AUTHOR = "author";
		KEY_DATELINE = "dateline";
		KEY_READED = "readed";
		KEY_CLOUDID = "cloudid";
		String as[] = new String[8];
		as[0] = KEY_ID;
		as[1] = KEY_SITEAPPID;
		as[2] = KEY_SUBJECT;
		as[3] = KEY_MESSAGE;
		as[4] = KEY_AUTHOR;
		as[5] = KEY_DATELINE;
		as[6] = KEY_READED;
		as[7] = KEY_CLOUDID;
		columns = as;
	}

	private NoticeDetail constructMember(Cursor cursor)
	{
		NoticeDetail noticedetail = new NoticeDetail();
		noticedetail._id = cursor.getString(0);
		noticedetail.siteappid = cursor.getString(1);
		noticedetail.subject = cursor.getString(2);
		noticedetail.message = cursor.getString(3);
		noticedetail.author = cursor.getString(4);
		noticedetail.dateline = Integer.valueOf(cursor.getInt(5));
		noticedetail.readed = Integer.valueOf(cursor.getString(6));
		noticedetail.cloudid = cursor.getString(7);
		return noticedetail;
	}

	public static NoticeDetailDBHelper getInstance()
	{
		if (dbObj == null)
			dbObj = new NoticeDetailDBHelper();
		return dbObj;
	}

	public boolean deleteAll()
	{
		return DB._delete("noticedetails", null, null) > 0;
	}

	public boolean deleteByCloudId(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append(KEY_CLOUDID);
		stringbuilder.append("='");
		stringbuilder.append(s);
		stringbuilder.append("'");
		return DB._delete("noticedetails", stringbuilder.toString(), null) > 0;
	}

	public List getByCloudId(String s)
	{
		String s1 = (new StringBuilder()).append(KEY_CLOUDID).append("='")
				.append(s).append("'").toString();
		Cursor cursor = DB._select(true, "noticedetails", columns, s1, null,
				null, null, "_id desc", null);
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

	public boolean insert(NoticeDetail noticedetail)
	{
		return DB._insert("noticedetails", noticedetail._getSqlVal()) > 0L;
	}

	public void update(String s)
	{
		DB._execsql((new StringBuilder()).append("update noticedetails set ")
				.append(KEY_READED).append("=1 where ").append(KEY_CLOUDID)
				.append("='").append(s).append("'").toString());
	}

	public static final String TABLE_NAME = "noticedetails";
	private static NoticeDetailDBHelper dbObj;
	public String KEY_AUTHOR;
	public String KEY_CLOUDID;
	public String KEY_DATELINE;
	public String KEY_ID;
	public String KEY_MESSAGE;
	public String KEY_READED;
	public String KEY_SITEAPPID;
	public String KEY_SUBJECT;
	private final String columns[];
}
//2131296256