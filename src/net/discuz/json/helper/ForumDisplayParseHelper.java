package net.discuz.json.helper;

import net.discuz.model.ForumDisplayData;
import org.json.JSONObject;

public abstract class ForumDisplayParseHelper extends JsonParseHelper
{

	public ForumDisplayParseHelper(String s)
	{
		super(s);
		ForumDisplayData = null;
		ForumDisplayData = null;
	}

	public void parse(JSONObject jsonobject) throws Exception
	{
		onParseBegin();
		super.parse(jsonobject);
		onParseFinish(ForumDisplayData);
	}

	public ForumDisplayData ForumDisplayData;
}
// 2131296256