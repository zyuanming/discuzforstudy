package net.discuz.json.helper.x25;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.discuz.json.helper.ForumIndexParseHelper;
import net.discuz.model.ForumIndexData;
import org.json.JSONArray;
import org.json.JSONObject;

public class ForumIndexParseHelperX25 extends ForumIndexParseHelper
{

	public ForumIndexParseHelperX25(String s)
	{
		super(s);
	}

	protected void parseData()
	{
		int i = 0;
		JSONArray jsonarray = json.optJSONArray("catlist");
		ArrayList arraylist = new ArrayList();
		HashMap hashmap = new HashMap();
		HashMap hashmap1 = new HashMap();
		ArrayList arraylist1 = new ArrayList();
		for (int j = 0; j < jsonarray.length(); j++)
		{
			HashMap hashmap2 = new HashMap();
			arraylist1.add((new StringBuilder()).append("catlist")
					.append(jsonarray.optJSONObject(j).optString("fid"))
					.toString());
			JSONObject jsonobject = jsonarray.optJSONObject(j);
			hashmap2.put("fid", jsonobject.optString("fid"));
			hashmap2.put("name", jsonobject.optString("name"));
			arraylist.add(jsonobject.optString("fid"));
			hashmap.put(jsonobject.optString("fid"),
					jsonobject.optString("name"));
			if (jsonobject.optJSONArray("forums") == null)
				continue;
			ArrayList arraylist2 = new ArrayList();
			for (int k = 0; k < jsonobject.optJSONArray("forums").length(); k++)
				arraylist2.add(jsonobject.optJSONArray("forums").optString(k));

			hashmap1.put(jsonarray.optJSONObject(j).optString("fid"),
					arraylist2);
		}

		forumindexdata.setCatlist(arraylist);
		forumindexdata.setCatValues(hashmap);
		forumindexdata.setForumList(hashmap1);
		JSONArray jsonarray1 = json.optJSONArray("forumlist");
		HashMap hashmap3 = new HashMap();
		while (i < jsonarray1.length())
		{
			HashMap hashmap4 = new HashMap();
			JSONObject jsonobject1 = jsonarray1.optJSONObject(i);
			hashmap4.put("fid", jsonobject1.optString("fid"));
			hashmap4.put(
					"forumname",
					Pattern.compile("<[^<>]*>", 32)
							.matcher(jsonobject1.optString("name"))
							.replaceAll(""));
			hashmap4.put("threads", jsonobject1.optString("threads"));
			hashmap4.put("posts", jsonobject1.optString("posts"));
			hashmap4.put("redirect", jsonobject1.optString("redirect"));
			if ("0".equals(jsonobject1.optString("todayposts"))
					|| "".equals(jsonobject1.optString("todayposts")))
				hashmap4.put("todayposts", null);
			else
				hashmap4.put("todayposts", jsonobject1.optString("todayposts"));
			hashmap4.put("description", jsonobject1.optString("description"));
			hashmap3.put(jsonobject1.optString("fid"), hashmap4);
			i++;
		}
		forumindexdata.setForumValues(hashmap3);
	}
}
// 2131296256