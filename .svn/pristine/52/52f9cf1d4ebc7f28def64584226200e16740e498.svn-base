package net.discuz.model;

import java.io.IOException;
import net.discuz.init.InitSetting;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class MemberUpdate
{

	public MemberUpdate()
	{}

	public void _initValue(JSONObject jsonobject)
	{
		description = jsonobject.optString("desc").replace(";", "\n");
		version = jsonobject.optString("ver");
		update_status = jsonobject.optString("recUpdated");
		InitSetting.VERSION_TMP = jsonobject.optString("ver");
	}

	public void _initValue(XmlPullParser xmlpullparser)
			throws XmlPullParserException, IOException
	{
		if (xmlpullparser.getAttributeValue(0).equals("release_description"))
			description = new String(xmlpullparser.nextText().getBytes(
					"ISO-8859-1"), "utf-8");
		if (xmlpullparser.getAttributeValue(0).equals("app_version"))
			version = xmlpullparser.nextText();
		if (xmlpullparser.getAttributeValue(0).equals("update_status"))
			update_status = xmlpullparser.nextText();
		if (xmlpullparser.getAttributeValue(0).equals("release_version"))
			InitSetting.VERSION_TMP = xmlpullparser.nextText();
	}

	public String getDescription()
	{
		return description;
	}

	public String getUpdatestatus()
	{
		return update_status;
	}

	public String getVersion()
	{
		return version;
	}

	public void setDescription(String s)
	{
		description = s;
	}

	public void setUpdatestatus(String s)
	{
		update_status = s;
	}

	public void setVersion(String s)
	{
		version = s;
	}

	private String description;
	private String update_status;
	private String version;
}
//2131296256