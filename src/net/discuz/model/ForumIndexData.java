package net.discuz.model;

import java.util.ArrayList;
import java.util.HashMap;

public class ForumIndexData
{

	public ForumIndexData()
	{
		catlist = null;
		catvalues = null;
		forumlist = null;
		forumvalues = null;
	}

	public HashMap getCatValues()
	{
		return catvalues;
	}

	public ArrayList getCatlist()
	{
		return catlist;
	}

	public HashMap getForumList()
	{
		return forumlist;
	}

	public HashMap getForumValues()
	{
		return forumvalues;
	}

	public void setCatValues(HashMap hashmap)
	{
		catvalues = hashmap;
	}

	public void setCatlist(ArrayList arraylist)
	{
		catlist = arraylist;
	}

	public void setForumList(HashMap hashmap)
	{
		forumlist = hashmap;
	}

	public void setForumValues(HashMap hashmap)
	{
		forumvalues = hashmap;
	}

	private ArrayList catlist;
	private HashMap catvalues;
	private HashMap forumlist;
	private HashMap forumvalues;
}
//2131296256