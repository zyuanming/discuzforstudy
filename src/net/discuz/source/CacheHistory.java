package net.discuz.source;

import java.util.*;
import net.discuz.activity.siteview.siteview_forumviewthread;
import net.discuz.source.storage.CacheDBHelper;
import net.discuz.tools.Tools;

public class CacheHistory
{

	public CacheHistory()
	{
		activity = null;
	}

	public CacheHistory(siteview_forumviewthread siteview_forumviewthread1)
	{
		activity = null;
		activity = siteview_forumviewthread1;
	}

	public HashMap _getCache(String s)
	{
		HashMap hashmap = new HashMap();
		String s1 = CacheDBHelper.getInstance().getByUrl(s);
		if (s1 != null)
		{
			String as[] = s1.split("\\|");
			for (int i = 0; i < as.length; i++)
			{
				String as1[] = as[i].split("=");
				if (as1.length > 1)
					hashmap.put(as1[0], as1[1]);
			}
		}
		return hashmap;
	}

	public void _setCache(String s, HashMap hashmap)
	{
		Iterator iterator = hashmap.entrySet().iterator();
		String s1 = "";
		while (iterator.hasNext())
		{
			java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
			s1 = (new StringBuilder()).append(s1)
					.append((String) entry.getKey()).append("=")
					.append((String) entry.getValue()).append("|").toString();
		}
		try
		{
			String s2 = Tools.trim(s1, "|");
			CacheDBHelper.getInstance().replaceCache(activity.siteAppId, s,
					System.currentTimeMillis() / 1000L, s2);
		} catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}

	private siteview_forumviewthread activity;
}
//2131296256