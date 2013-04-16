package net.discuz.model;

import java.util.HashMap;
import org.json.JSONObject;

public class ViewThreadData
{

	public ViewThreadData()
	{
		threadValueList = null;
		attachmentList = null;
		allowPerm = null;
		viewThreadPostListHtml = null;
		postListValues = null;
	}

	public AllowPerm getAllowPerm()
	{
		return allowPerm;
	}

	public HashMap getAttachmentList()
	{
		return attachmentList;
	}

	public HashMap getPostListValues()
	{
		return postListValues;
	}

	public HashMap getThreadValueList()
	{
		return threadValueList;
	}

	public StringBuilder getViewThreadPostListHtml()
	{
		return viewThreadPostListHtml;
	}

	public void setAllowPerm(JSONObject jsonobject)
	{
		allowPerm = new AllowPerm(jsonobject);
	}

	public void setAttachmentList(HashMap hashmap)
	{
		attachmentList = hashmap;
	}

	public void setPostListValues(HashMap hashmap)
	{
		if (postListValues == null)
			postListValues = new HashMap();
		HashMap hashmap1 = new HashMap();
		hashmap1.put("author", hashmap.get("author"));
		hashmap1.put("message", hashmap.get("message"));
		hashmap1.put("dateline", hashmap.get("dateline"));
		hashmap1.put("realnumber", hashmap.get("realnumber"));
		postListValues.put(hashmap.get("pid"), hashmap1);
	}

	public void setThreadValueList(HashMap hashmap)
	{
		threadValueList = hashmap;
	}

	public void setViewThreadPostListHtml(StringBuilder stringbuilder)
	{
		viewThreadPostListHtml = stringbuilder;
		viewThreadPostListHtml.insert(0, "<div class='postlist'><ul>").append(
				"</ul></div>");
	}

	private AllowPerm allowPerm;
	private HashMap attachmentList;
	private HashMap postListValues;
	private HashMap threadValueList;
	private StringBuilder viewThreadPostListHtml;
}
//2131296256