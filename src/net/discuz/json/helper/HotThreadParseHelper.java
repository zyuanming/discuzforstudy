package net.discuz.json.helper;

import java.util.ArrayList;
import org.json.JSONObject;

public abstract class HotThreadParseHelper extends JsonParseHelper
{

	public HotThreadParseHelper(String s)
	{
		super(s);
		HotThreadsList = null;
		DataKeys = "tid|fid|author|authorid|subject|dateline|lastpost|lastposter|views|replies|attachment|dbdateline|dblastpost";
		DataName = "data";
		HotThreadsList = new ArrayList();
	}

	public void parse(JSONObject jsonobject) throws Exception
	{
		onParseBegin();
		super.parse(jsonobject);
		onParseFinish(HotThreadsList);
		HotThreadsList = null;
	}

	public String DataKeys;
	public String DataName;
	public ArrayList HotThreadsList;
}
// 2131296256