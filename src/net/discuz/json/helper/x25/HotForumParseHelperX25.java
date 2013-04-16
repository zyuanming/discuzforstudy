package net.discuz.json.helper.x25;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.discuz.json.helper.HotForumParseHelper;
import org.json.JSONArray;
import org.json.JSONObject;

public class HotForumParseHelperX25 extends HotForumParseHelper
{

	public HotForumParseHelperX25(String s)
	{
		super(s);
	}

	protected void parseData()
	{
		JSONArray jsonarray = json.optJSONArray(DataName);
		int i = jsonarray.length();
		String as[] = DataKeys.split("\\|");
		for (int j = 0; j < i; j++)
		{
			HashMap hashmap = new HashMap();
			int k = 0;
			while (k < as.length)
			{
				if ("name".equals(as[k]))
				{
					String s1 = Pattern
							.compile("<[^<>]*>", 32)
							.matcher(
									jsonarray.optJSONObject(j).optString(as[k]))
							.replaceAll("");
					hashmap.put(as[k], s1);
				} else if ("todayposts".equals(as[k]))
				{
					String s = jsonarray.optJSONObject(j).optString(as[k]);
					hashmap.put(as[k], s);
				} else
				{
					hashmap.put(as[k],
							jsonarray.optJSONObject(j).optString(as[k]));
				}
				k++;
			}
			hotForumList.add(hashmap);
		}

	}
}
// 2131296256