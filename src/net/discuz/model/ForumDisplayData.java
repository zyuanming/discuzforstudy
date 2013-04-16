package net.discuz.model;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONObject;

public class ForumDisplayData
{

	public ForumDisplayData()
	{
		ThreadList = new ArrayList();
		SubList = new ArrayList();
		ForumData = new HashMap();
		allowperm = null;
	}

	public AllowPerm getAllowPerm()
	{
		return allowperm;
	}

	public HashMap getForumData()
	{
		return ForumData;
	}

	public ArrayList getSubList()
	{
		return SubList;
	}

	public ArrayList getThreadList()
	{
		return ThreadList;
	}

	public void setAllowPerm(JSONObject jsonobject)
	{
		allowperm = new AllowPerm(jsonobject);
	}

	public void setForumData(HashMap hashmap)
	{
		ForumData = hashmap;
	}

	public void setSubList(ArrayList arraylist)
	{
		SubList = arraylist;
	}

	public void setThreadList(ArrayList arraylist)
	{
		ThreadList = arraylist;
	}

	public HashMap ForumData;
	public ArrayList SubList;
	public ArrayList ThreadList;
	public AllowPerm allowperm;
}
//2131296256