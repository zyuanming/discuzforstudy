package net.discuz.model;

import org.json.JSONObject;

public class MsgInfo
{

	public MsgInfo()
	{}

	public MsgInfo(JSONObject jsonobject)
	{
		convertJSON(jsonobject);
	}

	private void convertJSON(JSONObject jsonobject)
	{
		msgId = jsonobject.optInt("msgId");
		type = jsonobject.optInt("type");
		sId = jsonobject.optInt("sId");
		subject = jsonobject.optString("subject");
		author = jsonobject.optString("author");
	}

	public String getAuthor()
	{
		return author;
	}

	public int getMsgId()
	{
		return msgId;
	}

	public String getSubject()
	{
		return subject;
	}

	public int getType()
	{
		return type;
	}

	public int getsId()
	{
		return sId;
	}

	public void setAuthor(String s)
	{
		author = s;
	}

	public void setMsgId(int i)
	{
		msgId = i;
	}

	public void setSubject(String s)
	{
		subject = s;
	}

	public void setType(int i)
	{
		type = i;
	}

	public void setsId(int i)
	{
		sId = i;
	}

	public static final int TYPE_ADMIN_ANNOUNCEMENT = 4;
	public static final int TYPE_DISCUZ_ANNOUNCEMENT = 5;
	public static final int TYPE_NOTICE = 3;
	public static final int TYPE_PRIVATE = 1;
	public static final int TYPE_PUBLIC = 2;
	private String author;
	private int msgId;
	private int sId;
	private String subject;
	private int type;
}
//2131296256