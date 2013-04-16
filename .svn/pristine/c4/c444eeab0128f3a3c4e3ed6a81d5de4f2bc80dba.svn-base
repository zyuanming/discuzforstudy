package net.discuz.json.helper;

import java.util.HashMap;
import org.json.JSONObject;

public abstract class ForumNavParseHelper extends JsonParseHelper
{
	public class ForumNavData
	{

		public HashMap forumnames;
		public String fupfids[];
		public HashMap requiredThreadTypes;
		public HashMap subfids;
		public HashMap threadTypes;

		public ForumNavData()
		{
			super();
			requiredThreadTypes = new HashMap();
			threadTypes = new HashMap();
		}
	}

	public ForumNavParseHelper(String s)
	{
		super(s);
		forumNavData = new ForumNavData();
	}

	public void parse(JSONObject jsonobject) throws Exception
	{
		onParseBegin();
		super.parse(jsonobject);
		onParseFinish(forumNavData);
	}

	public ForumNavData forumNavData;
}
// 2131296256