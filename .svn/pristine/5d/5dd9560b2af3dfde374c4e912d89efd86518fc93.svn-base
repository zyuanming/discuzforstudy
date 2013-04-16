package net.discuz.source.storage;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import net.discuz.model.PostDraft;
import net.discuz.source.DB;
import net.discuz.tools.Tools;

public class PostDraftDBHelper
{

	private PostDraftDBHelper()
	{}

	private PostDraft constructPostDraft(Cursor cursor)
	{
		PostDraft postdraft = new PostDraft();
		postdraft.setId(Long.valueOf(cursor.getLong(0)));
		postdraft.setType(cursor.getInt(1));
		postdraft.setValue(cursor.getString(2));
		postdraft.setDisplayorder(cursor.getInt(3));
		postdraft.setDateline(cursor.getLong(4));
		return postdraft;
	}

	public static PostDraftDBHelper getInstance()
	{
		return new PostDraftDBHelper();
	}

	public long addItem(String s, int i)
	{
		long l = Tools._getTimeStamp();
		ContentValues contentvalues = new ContentValues();
		switch (i)
		{
		default:
			i = 0;
			// fall through

		case 0: // '\0'
		case 1: // '\001'
		case 2: // '\002'
		case 3: // '\003'
			contentvalues.put("type", Integer.valueOf(i));
			break;
		}
		contentvalues.put("value", s);
		contentvalues.put("displayorder", Integer.valueOf(0));
		contentvalues.put("dateline", Long.valueOf(l));
		return DB._insert("forum_postdraft", contentvalues);
	}

	public long addItemImageForNewThread(String s)
	{
		return addItem(s, 1);
	}

	public long addItemImageForReply(String s)
	{
		return addItem(s, 3);
	}

	public long addItemText(String s)
	{
		return addItem(s, 0);
	}

	public boolean deleteAll()
	{
		return DB._delete("forum_postdraft", null, null) > 0;
	}

	public boolean deleteAllForReply()
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("type");
		stringbuilder.append("='3'");
		return DB._delete("forum_postdraft", stringbuilder.toString(), null) > 0;
	}

	public boolean deleteItem(Long long1)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("_id");
		stringbuilder.append("='");
		stringbuilder.append(long1);
		stringbuilder.append("'");
		return DB._delete("forum_postdraft", stringbuilder.toString(), null) > 0;
	}

	public ArrayList getAllItem()
	{
		return getAllItem(null);
	}

	public ArrayList getAllItem(String s)
	{
		Cursor cursor = DB._select(true, "forum_postdraft", columns, s, null,
				null, null, "displayorder desc, dateline asc", null);
		boolean flag = cursor.moveToFirst();
		ArrayList arraylist = null;
		if (flag)
		{
			arraylist = new ArrayList();
			do
				arraylist.add(constructPostDraft(cursor));
			while (cursor.moveToNext());
		}
		cursor.close();
		return arraylist;
	}

	public ArrayList getAllItemForNewThread()
	{
		return getAllItem("type<='2'");
	}

	public ArrayList getAllItemForReply()
	{
		return getAllItem("type='3'");
	}

	public PostDraft getItemById(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("_id");
		stringbuilder.append("='");
		stringbuilder.append(s);
		stringbuilder.append("'");
		Cursor cursor = DB._select(true, "forum_postdraft", columns,
				stringbuilder.toString(), null, null, null, null, "1");
		boolean flag = cursor.moveToFirst();
		PostDraft postdraft = null;
		if (flag)
			postdraft = constructPostDraft(cursor);
		cursor.close();
		return postdraft;
	}

	public PostDraft getItemForText()
	{
		Cursor cursor = DB._select(true, "forum_postdraft", columns,
				"type='0'", null, null, null, "dateline asc", "1");
		boolean flag = cursor.moveToFirst();
		PostDraft postdraft = null;
		if (flag)
			postdraft = constructPostDraft(cursor);
		cursor.close();
		return postdraft;
	}

	public int getItemNum(String s)
	{
		String s1;
		Cursor cursor;
		int i;
		if (s != null)
		{
			if ("".equals(s))
				s1 = "";
			else
				s1 = (new StringBuilder()).append(" WHERE ").append(s)
						.toString();
		} else
		{
			s1 = "";
		}
		cursor = DB._query((new StringBuilder())
				.append("SELECT COUNT(*) AS COUNT FROM forum_postdraft")
				.append(s1).toString());
		if (cursor.moveToFirst())
			i = cursor.getInt(0);
		else
			i = 0;
		cursor.close();
		return i;
	}

	public int getItemNumForNewThread()
	{
		return getItemNum("type<=2");
	}

	public int getItemNumForReply()
	{
		return getItemNum("type=3");
	}

	public PostDraft getLastItemForReply()
	{
		Cursor cursor = DB._select(true, "forum_postdraft", columns,
				"type= '3'", null, null, null, "_id DESC", "1");
		cursor.moveToFirst();
		PostDraft postdraft = constructPostDraft(cursor);
		cursor.close();
		return postdraft;
	}

	public boolean hasPostDraft()
	{
		return getItemNum(null) > 0;
	}

	public boolean hasPostDraftForNewThread()
	{
		return getItemNum("type<=2") > 0;
	}

	public boolean hasPostDraftForReply()
	{
		return getItemNum("type=3") > 0;
	}

	public void updateItem(String s, String s1)
	{
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("value", s1);
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("_id");
		stringbuilder.append("='");
		stringbuilder.append(s);
		stringbuilder.append("'");
		DB._update("forum_postdraft", contentvalues, stringbuilder.toString(),
				null);
	}

	public static final String TABLE_NAME = "forum_postdraft";
	public static final int TYPE_IMAGE = 1;
	public static final int TYPE_REPLY_IMAGE = 3;
	public static final int TYPE_SOUND = 2;
	public static final int TYPE_TEXT = 4;
	public final int DEFAULT_DISPLAYORDER = 0;
	private final String KEY_DATELINE = "dateline";
	private final String KEY_DISPLAYORDER = "displayorder";
	private final String KEY_ID = "_id";
	private final String KEY_TYPE = "type";
	private final String KEY_VALUE = "value";
	private final String columns[] = { "_id", "type", "value", "displayorder",
			"dateline" };
}
//2131296256