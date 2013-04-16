package net.discuz.json.helper;

import java.util.ArrayList;
import org.json.JSONObject;

public abstract class HotForumParseHelper extends JsonParseHelper
{

	public HotForumParseHelper(String s)
	{
		super(s);
		hotForumList = null;
		DataName = "data";
		DataKeys = "fid|name|threads|posts|todayposts";
		hotForumList = new ArrayList();
	}

	public void parse(JSONObject jsonobject) throws Exception
	{
		onParseBegin();
		super.parse(jsonobject);
		onParseFinish(hotForumList);
		hotForumList = null;
	}

	public String DataKeys;
	public String DataName;
	public ArrayList hotForumList;
}
// 2131296256