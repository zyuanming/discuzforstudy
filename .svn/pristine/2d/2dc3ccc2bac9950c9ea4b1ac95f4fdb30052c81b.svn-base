package net.discuz.json.helper.x25;

import java.util.*;
import net.discuz.app.DiscuzApp;
import net.discuz.json.helper.ForumNavParseHelper;
import net.discuz.model.LoginInfo;
import net.discuz.model.SiteInfo;
import net.discuz.source.DEBUG;
import org.json.JSONArray;
import org.json.JSONObject;

public class ForumNavParseHelperX25 extends ForumNavParseHelper
{
	static class forumValue
	{

		public String _fid;
		public String _fup;
		public String _name;
		public String _type;

		public forumValue(String s, String s1, String s2, String s3)
		{
			_fid = s;
			_type = s1;
			_name = s2;
			_fup = s3;
		}
	}

	public ForumNavParseHelperX25(String s)
	{
		super(s);
		forumname = new HashMap();
		fupforumList_tmp = new ArrayList();
		subforumValue_tmp = new HashMap();
		forumList_tmp = new ArrayList();
	}

	private boolean getPostPerm(JSONObject jsonobject)
	{
		String as[] = jsonobject.optString("postperm").split("\t");
		boolean flag = true;

		if (as.length <= 1)
		{
			return true;
		} else
		{
			for (int i = 0; i < as.length; i++)
			{
				if (!groupid.equals(as[i]))
				{
					flag = false;
				}
			}
		}
		return flag;
	}

	private boolean getViewPerm(JSONObject jsonobject)
	{
		String as[] = jsonobject.optString("viewperm").split("\t");
		boolean flag = true;
		if (as.length <= 1)
		{
			return true;
		} else
		{
			for (int i = 0; i < as.length; i++)
			{
				if (!groupid.equals(as[i]))
				{
					flag = false;
				}
			}
		}
		return flag;
	}

	protected void parseData()
	{
		int i = DiscuzApp.getInstance().getSiteInfo(siteAppId).getLoginUser()
				.getGroupid();
		groupid = String.valueOf(i);
		JSONArray jsonarray = json.optJSONArray("forums");
		int j = 0;
		do
		{
			JSONObject jsonobject;
			if (j >= jsonarray.length())
				break;
			jsonobject = jsonarray.optJSONObject(j);
			boolean flag;
			if (i == 7)
				flag = false;
			else
				flag = true;
			forumList_tmp.add(new forumValue(
					jsonobject.optString("fid").trim(), jsonobject.optString(
							"type").trim(),
					jsonobject.optString("name").trim(), jsonobject.optString(
							"fup").trim()));
			if (!"group".equals(jsonobject.optString("type")))
			{
				if (flag)
					flag = getViewPerm(jsonobject);
				if (flag
						&& (jsonobject.optJSONObject("threadsorts") == null || jsonobject
								.optJSONObject("threadsorts").optString(
										"required") == null)
						&& jsonobject.optJSONObject("threadtypes") != null)
				{
					JSONObject jsonobject1 = jsonobject
							.optJSONObject("threadtypes");
					if (jsonobject1.optString("required") != null)
						forumNavData.requiredThreadTypes.put(
								jsonobject.optString("fid"),
								Boolean.valueOf(true));
					if (jsonobject1.optJSONObject("types") != null)
					{
						ArrayList arraylist = new ArrayList();
						JSONObject jsonobject2 = jsonobject1
								.optJSONObject("types");
						for (int j1 = 0; j1 < jsonobject2.names().length(); j1++)
						{
							String s = jsonobject2.names().optString(j1);
							String s1 = jsonobject2.optString(s);
							arraylist.add((new StringBuilder()).append(s)
									.append("\t").append(s1).toString());
						}

						forumNavData.threadTypes.put(jsonobject
								.optString("fid"),
								(String[]) (String[]) arraylist
										.toArray(new String[arraylist.size()]));
					}
				}
			}
			j++;
		} while (true);
		for (int k = 0; k < forumList_tmp.size(); k++)
		{
			if (!"group".equals(((forumValue) forumList_tmp.get(k))._type))
				continue;
			for (int l = 0; l < forumList_tmp.size(); l++)
			{
				if (!((forumValue) forumList_tmp.get(k))._fid
						.equals(((forumValue) forumList_tmp.get(l))._fup)
						|| !"forum"
								.equals(((forumValue) forumList_tmp.get(l))._type))
					continue;
				forumname.put(((forumValue) forumList_tmp.get(l))._fid,
						((forumValue) forumList_tmp.get(l))._name);
				fupforumList_tmp.add(((forumValue) forumList_tmp.get(l))._fid);
				for (int i1 = 0; i1 < forumList_tmp.size(); i1++)
					if (((forumValue) forumList_tmp.get(l))._fid
							.equals(((forumValue) forumList_tmp.get(i1))._fup)
							&& "sub".equals(((forumValue) forumList_tmp.get(i1))._type))
					{
						forumname.put(
								((forumValue) forumList_tmp.get(i1))._fid,
								(new StringBuilder())
										.append("\t- ")
										.append(((forumValue) forumList_tmp
												.get(i1))._name).toString());
						fupforumList_tmp.add(((forumValue) forumList_tmp
								.get(i1))._fid);
					}

			}

		}

		DEBUG.o((new StringBuilder()).append("fupforumList_tmp:")
				.append(fupforumList_tmp).toString());
		forumNavData.forumnames = forumname;
		forumNavData.fupfids = (String[]) (String[]) fupforumList_tmp
				.toArray(new String[fupforumList_tmp.size()]);
		forumNavData.subfids = new HashMap();
		java.util.Map.Entry entry;
		for (Iterator iterator = subforumValue_tmp.entrySet().iterator(); iterator
				.hasNext(); forumNavData.subfids.put(entry.getKey(),
				(String[]) (String[]) ((ArrayList) entry.getValue())
						.toArray(new String[((ArrayList) entry.getValue())
								.size()])))
			entry = (java.util.Map.Entry) iterator.next();

	}

	public ArrayList forumList_tmp;
	public HashMap forumname;
	public ArrayList fupforumList_tmp;
	public String groupid;
	public HashMap subforumValue_tmp;
}
// 2131296256