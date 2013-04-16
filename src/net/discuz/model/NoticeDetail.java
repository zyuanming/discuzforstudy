package net.discuz.model;

import android.content.ContentValues;
import net.discuz.source.DEBUG;
import net.discuz.tools.Tools;
import org.json.JSONObject;

public class NoticeDetail
{

	public NoticeDetail()
	{
		subject = "";
		message = "";
		author = "";
		dateline = Integer.valueOf(0);
		readed = Integer.valueOf(0);
		cloudid = "";
	}

	public static NoticeDetail _initValue(JSONObject jsonobject,
			SiteInfo siteinfo)
	{
		NoticeDetail noticedetail = new NoticeDetail();
		noticedetail.subject = jsonobject.optString("subject");
		noticedetail.message = jsonobject.optString("message");
		noticedetail.author = jsonobject.optString("author");
		noticedetail.dateline = Integer.valueOf((int) Tools
				._getTimeStamp(jsonobject.optString("dateline")));
		DEBUG.o((new StringBuilder()).append("====NoticeDetail dateline =====")
				.append(noticedetail.dateline).toString());
		noticedetail.readed = Integer.valueOf(jsonobject.optInt("readed"));
		noticedetail.siteappid = siteinfo.getSiteAppid();
		noticedetail.siteicon = siteinfo.getIconFromSD();
		noticedetail.cloudid = siteinfo.getCloudId();
		return noticedetail;
	}

	public ContentValues _getSqlVal()
	{
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("_id", _id);
		contentvalues.put("siteappid", siteappid);
		contentvalues.put("subject", subject);
		contentvalues.put("message", message);
		contentvalues.put("author", author);
		contentvalues.put("dateline", dateline);
		contentvalues.put("readed", readed);
		contentvalues.put("cloudid", cloudid);
		return contentvalues;
	}

	public String _id;
	public String author;
	public String cloudid;
	public Integer dateline;
	public String message;
	public Integer readed;
	public String siteappid;
	public String siteicon;
	public String subject;
}
//2131296256