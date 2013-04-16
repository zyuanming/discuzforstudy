package net.discuz.json;

import net.discuz.model.SiteInfo;
import net.discuz.source.DEBUG;
import net.discuz.source.DJsonReader;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonReaderSiteAdd extends DJsonReader
{

	public JsonReaderSiteAdd(String s)
	{
		super(s);
		sitename = null;
		siteinfo = null;
	}

	public void _variableParse(JSONObject jsonobject) throws JSONException
	{
		DEBUG.o((new StringBuilder()).append("addSite:").append(jsonobject)
				.toString());
		siteinfo = new SiteInfo();
		siteinfo._initSearchValue(jsonobject);
	}

	public SiteInfo siteinfo;
	public String sitename;
}
//2131296256