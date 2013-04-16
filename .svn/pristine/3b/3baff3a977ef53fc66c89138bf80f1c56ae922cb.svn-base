package net.discuz.json.helper;

import net.discuz.model.PmData;
import org.json.JSONObject;

public abstract class MyPmViewParseHelper extends JsonParseHelper
{

	public MyPmViewParseHelper(String s)
	{
		super(s);
		pmData = null;
		DataName = "list";
		DataKeys = "authorid|author|message|dateline";
		pmData = new PmData();
	}

	public void parse(JSONObject jsonobject) throws Exception
	{
		onParseBegin();
		super.parse(jsonobject);
		onParseFinish(pmData);
	}

	public String DataKeys;
	public String DataName;
	public PmData pmData;
}
// 2131296256