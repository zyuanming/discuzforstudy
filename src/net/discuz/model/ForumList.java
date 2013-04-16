package net.discuz.model;

import android.content.ContentValues;
import android.util.Log;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ForumList
{

	public ForumList()
	{
		fid = null;
		name = null;
		fup = null;
		type = null;
		threads = null;
		posts = null;
		description = null;
		sqlValue = null;
	}

	public String _getDescription()
	{
		return description;
	}

	public String _getFid()
	{
		return fid;
	}

	public String _getFup()
	{
		return fup;
	}

	public String _getName()
	{
		return name;
	}

	public String _getPosts()
	{
		return posts;
	}

	public ContentValues _getSqlFieldVal()
	{
		sqlValue = new ContentValues();
		sqlValue.put("fid", _getFid());
		sqlValue.put("description", _getDescription());
		return sqlValue;
	}

	public ContentValues _getSqlVal()
	{
		sqlValue = new ContentValues();
		sqlValue.put("fid", _getFid());
		sqlValue.put("name", _getName());
		sqlValue.put("type", _getType());
		sqlValue.put("fup", _getFup());
		sqlValue.put("threads", _getThreads());
		sqlValue.put("posts", _getPosts());
		return sqlValue;
	}

	public String _getThreads()
	{
		return threads;
	}

	public String _getType()
	{
		return type;
	}

	public void _initValue(XmlPullParser xmlpullparser)
			throws XmlPullParserException, IOException
	{
		if (xmlpullparser.getAttributeValue(0).equals("fid"))
		{
			fid = xmlpullparser.nextText();
		} else
		{
			if (xmlpullparser.getAttributeValue(0).equals("name"))
			{
				String s = xmlpullparser.nextText();
				Log.v("forumname", s);
				name = new String(s.getBytes("ISO-8859-1"), "utf-8");
				return;
			}
			if (xmlpullparser.getAttributeValue(0).equals("fup"))
			{
				fup = xmlpullparser.nextText();
				return;
			}
			if (xmlpullparser.getAttributeValue(0).equals("type"))
			{
				type = xmlpullparser.nextText();
				return;
			}
			if (xmlpullparser.getAttributeValue(0).equals("threads"))
			{
				threads = xmlpullparser.nextText();
				return;
			}
			if (xmlpullparser.getAttributeValue(0).equals("posts"))
			{
				posts = xmlpullparser.nextText();
				return;
			}
			if (xmlpullparser.getAttributeValue(0).equals("description"))
			{
				description = new String(xmlpullparser.nextText().getBytes(
						"ISO-8859-1"), "utf-8");
				return;
			}
		}
	}

	private String description;
	private String fid;
	private String fup;
	private String name;
	private String posts;
	private ContentValues sqlValue;
	private String threads;
	private String type;
}
//2131296256