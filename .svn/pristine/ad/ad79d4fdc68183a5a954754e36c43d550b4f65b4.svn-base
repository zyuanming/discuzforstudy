package net.discuz.source;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import net.discuz.app.DiscuzApp;
import net.discuz.install.InstallSql;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper
{

	private DB(Context context)
	{
		super(context, "discuz", null, 60);
		mDB = super.getWritableDatabase();
	}

	public static int _delete(String s, String s1, String as[])
	{
		int i;
		try
		{
			writer.acquire();
			if (mDB == null || !mDB.isOpen())
				mDB = obj.getWritableDatabase();
			i = mDB.delete(s, s1, as);
		} catch (InterruptedException ie)
		{
			ie.printStackTrace();
			return 0;
		} finally
		{
			writer.release();
		}
		return i;
	}

	public static void _execsql(String s)
	{
		if (s.toLowerCase().indexOf("select ") < 0)
		{
			try
			{
				writer.tryAcquire();
				if (mDB == null || !mDB.isOpen())
					mDB = obj.getWritableDatabase();
				DEBUG.o((new StringBuilder())
						.append("=====\u5F53\u524D[insert,delete,create table,drop]\u66F4\u65B0\u6570\u636E\u64CD\u4F5C=======")
						.append(s)
						.append(" \u6B64\u65F6\u67E5\u8BE2\u64CD\u4F5C\u6570:")
						.append(readercount).toString());
				mDB.execSQL(s);
			} catch (Exception exception1)
			{
				writer.release();
			}
		}
	}

	public static int _getVersion()
	{
		int i = 0;
		try
		{
			if (mDB == null || !mDB.isOpen())
				mDB = obj.getWritableDatabase();
			i = mDB.getVersion();
		} catch (Exception e)
		{
			e.printStackTrace();
			return 0;
		}
		return i;
	}

	public static long _insert(String s, ContentValues contentvalues)
	{
		long l;
		try
		{
			writer.acquire();
			if (mDB == null || !mDB.isOpen())
				mDB = obj.getWritableDatabase();
			l = mDB.insert(s, null, contentvalues);
		} catch (InterruptedException ie)
		{
			ie.printStackTrace();
			return 0L;
		} finally
		{
			writer.release();
		}
		return l;
	}

	public static Cursor _query(String s)
	{
		Cursor cursor = null;
		int i;
		SQLiteDatabase sqlitedatabase;
		try
		{
			reader.acquire();
			readercount = 1 + readercount;
			i = readercount;

			cursor = null;
			if (i == 1)
				writer.acquire();
			reader.release();
			sqlitedatabase = mDB;
			cursor = null;
			if (sqlitedatabase != null)
				if (!mDB.isOpen())
					mDB = obj.getReadableDatabase();
			cursor = mDB.rawQuery(s, null);
			reader.acquire();
			readercount = -1 + readercount;
			if (readercount == 0)
				writer.release();
			reader.release();
		} catch (InterruptedException interruptedexception)
		{
			return cursor;
		}
		return cursor;
	}

	public static long _replace(String s, ContentValues contentvalues)
	{
		long l;
		try
		{
			writer.acquire();
			if (mDB == null || !mDB.isOpen())
				mDB = obj.getWritableDatabase();
			l = mDB.replace(s, null, contentvalues);
		} catch (InterruptedException ie)
		{
			ie.printStackTrace();
			return 0L;
		} finally
		{
			writer.release();
		}
		return l;
	}

	public static Cursor _select(boolean flag, String s, String as[],
			String s1, String as1[], String s2, String s3, String s4, String s5)
	{
		Cursor cursor1;
		try
		{
			reader.acquire();
			readercount = 1 + readercount;
			if (readercount == 1)
				writer.acquire();
			reader.release();
			if (mDB == null || !mDB.isOpen())
				mDB = obj.getReadableDatabase();
			cursor1 = mDB.query(flag, s, as, s1, as1, s2, s3, s4, s5);
			reader.acquire();
			readercount = -1 + readercount;
		} catch (InterruptedException ie)
		{
			ie.printStackTrace();
			return null;
		} finally
		{
			if (readercount == 0)
				writer.release();
			reader.release();
		}
		return cursor1;
	}

	public static int _update(String s, ContentValues contentvalues, String s1,
			String as[])
	{
		int i;
		try
		{
			writer.tryAcquire();
			if (mDB == null || !mDB.isOpen())
				mDB = obj.getWritableDatabase();
			i = mDB.update(s, contentvalues, s1, as);
		} catch (Exception e)
		{
			e.printStackTrace();
			return 0;
		} finally
		{
			writer.release();
		}
		return i;
	}

	public static SQLiteDatabase getDatabase()
	{
		return mDB;
	}

	public static DB getInstance(DiscuzApp discuzapp)
	{
		if (obj == null)
			obj = new DB(discuzapp);
		return obj;
	}

	private void onUpgrade_save_cache(SQLiteDatabase sqlitedatabase)
	{
		ArrayList arraylist = InstallSql._initMoveTable();
		for (int i = 0; i < arraylist.size(); i++)
			sqlitedatabase.execSQL((String) arraylist.get(i));

	}

	public void onCreate(SQLiteDatabase sqlitedatabase)
	{
		ArrayList arraylist = InstallSql._initCreate();
		for (int i = 0; i < arraylist.size(); i++)
			sqlitedatabase.execSQL((String) arraylist.get(i));

	}

	public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j)
	{
		onUpgrade_save_cache(sqlitedatabase);
		ArrayList arraylist = InstallSql._initDrop();
		for (int k = 0; k < arraylist.size(); k++)
			sqlitedatabase.execSQL((String) arraylist.get(k));

		onCreate(sqlitedatabase);
		InstallSql._recover_site_table(sqlitedatabase);
		InstallSql._recover_usersession_table(sqlitedatabase);
	}

	public static final String DB_NAME = "discuz";
	public static final int VERSION = 60;
	private static SQLiteDatabase mDB = null;
	private static DB obj = null;
	private static Semaphore reader = new Semaphore(3);
	private static int readercount = 0;
	public static Semaphore writer = new Semaphore(1);

}
// 2131296256