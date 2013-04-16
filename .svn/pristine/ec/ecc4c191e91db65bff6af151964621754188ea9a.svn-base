package net.discuz.source.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.*;
import net.discuz.source.DB;

public class AttachmentDBHelper
{

	private AttachmentDBHelper()
	{}

	public static AttachmentDBHelper getInstance()
	{
		return new AttachmentDBHelper();
	}

	public boolean deleteAll()
	{
		return DB._delete("forum_attachments", null, null) > 0;
	}

	public HashMap getByAid(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("aid");
		stringbuilder.append("='");
		stringbuilder.append(s);
		stringbuilder.append("'");
		Cursor cursor = DB._select(true, "forum_attachments", columns,
				stringbuilder.toString(), null, null, null, null, "1");
		boolean flag = cursor.moveToFirst();
		HashMap hashmap = null;
		if (flag)
		{
			hashmap = new HashMap();
			hashmap.put("aid", cursor.getString(0));
			hashmap.put("tid", cursor.getString(1));
			hashmap.put("filename", cursor.getString(2));
			hashmap.put("ext", cursor.getString(3));
			hashmap.put("price", cursor.getString(4));
			hashmap.put("payed", cursor.getString(5));
			hashmap.put("url", cursor.getString(6));
			hashmap.put("attachment", cursor.getString(7));
		}
		cursor.close();
		return hashmap;
	}

	public void replaceAttachment(HashMap hashmap)
	{
		while (hashmap == null || hashmap.size() <= 0)
			return;
		SQLiteDatabase sqlitedatabase = DB.getDatabase();
		sqlitedatabase.beginTransaction();
		ContentValues contentvalues;
		for (Iterator iterator = hashmap.entrySet().iterator(); iterator
				.hasNext(); sqlitedatabase.replace("forum_attachments", null,
				contentvalues))
		{
			HashMap hashmap1 = (HashMap) ((java.util.Map.Entry) iterator.next())
					.getValue();
			contentvalues = new ContentValues();
			contentvalues.put("aid", (String) hashmap1.get("aid"));
			contentvalues.put("tid", (String) hashmap1.get("tid"));
			contentvalues.put("url", (String) hashmap1.get("url"));
			contentvalues.put("filename", (String) hashmap1.get("filename"));
			contentvalues.put("ext", (String) hashmap1.get("ext"));
			contentvalues.put("price", (String) hashmap1.get("price"));
			contentvalues.put("payed", (String) hashmap1.get("payed"));
			contentvalues
					.put("attachment", (String) hashmap1.get("attachment"));
		}

		sqlitedatabase.setTransactionSuccessful();
		sqlitedatabase.endTransaction();
	}

	public boolean updatePayedByAid(int i, String s)
	{
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("payed", Integer.valueOf(i));
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("aid");
		stringbuilder.append("='");
		stringbuilder.append(s);
		stringbuilder.append("'");
		return DB._update("forum_attachments", contentvalues,
				stringbuilder.toString(), null) > 0;
	}

	public static final String TABLE_NAME = "forum_attachments";
	private final String KEY_AID = "aid";
	private final String KEY_ATTACHMENT = "attachment";
	private final String KEY_EXT = "ext";
	private final String KEY_FILENAME = "filename";
	private final String KEY_PAYED = "payed";
	private final String KEY_PRICE = "price";
	private final String KEY_TID = "tid";
	private final String KEY_URL = "url";
	private final String columns[] = { "aid", "tid", "filename", "ext",
			"price", "payed", "url", "attachment" };
}
//2131296256