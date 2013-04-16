package net.discuz.model;

import java.io.Serializable;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;

public class AllowPerm implements Serializable
{

	public AllowPerm(JSONObject jsonobject)
	{
		allowpost = false;
		allowreply = false;
		allowupload = new HashMap();
		attachremain = new HashMap();
		uploadHash = "";
		setAllowPost(jsonobject.optString("allowpost"));
		setAllowReply(jsonobject.optString("allowreply"));
		setAllowUpload(jsonobject);
		setAttachRemain(jsonobject);
		setUploadHash(jsonobject.optString("uploadhash"));
	}

	private void setAllowPost(String s)
	{
		if ("1".equals(s))
			allowpost = true;
	}

	private void setAllowReply(String s)
	{
		if ("1".equals(s))
			allowreply = true;
	}

	private void setAllowUpload(JSONObject jsonobject)
	{
		JSONArray jsonarray = jsonobject.names();
		for (int i = 0; i < jsonarray.length(); i++)
			allowupload.put(jsonarray.optString(i),
					jsonobject.optString(jsonarray.optString(i)));

	}

	private void setAttachRemain(JSONObject jsonobject)
	{
		JSONArray jsonarray = jsonobject.names();
		for (int i = 0; i < jsonarray.length(); i++)
			attachremain.put(jsonarray.optString(i),
					jsonobject.optString(jsonarray.optString(i)));

	}

	private void setUploadHash(String s)
	{
		if (s != null)
		{
			uploadHash = s;
		} else
		{
			uploadHash = "";
		}
	}

	public boolean getAllowPost()
	{
		return allowpost;
	}

	public boolean getAllowReply()
	{
		return allowreply;
	}

	public HashMap getAllowUpload()
	{
		return allowupload;
	}

	public HashMap getAttachRemain()
	{
		return attachremain;
	}

	public String getUploadHash()
	{
		return uploadHash;
	}

	public boolean allowpost;
	public boolean allowreply;
	public HashMap allowupload;
	public HashMap attachremain;
	public String uploadHash;
}
//2131296256