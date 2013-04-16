package net.discuz.json.helper;

import net.discuz.model.ForumIndexData;
import org.json.JSONObject;

public abstract class ForumIndexParseHelper extends JsonParseHelper
{

	public ForumIndexParseHelper(String s)
	{
		super(s);
		forumindexdata = new ForumIndexData();
	}

	public void parse(JSONObject jsonobject) throws Exception
	{
		onParseBegin();
		super.parse(jsonobject);
		onParseFinish(forumindexdata);
	}

	public ForumIndexData forumindexdata;
}
