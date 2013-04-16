package net.discuz.model;

import android.content.ContentValues;
import net.discuz.source.storage.SiteInfoDBHelper;
import net.discuz.tools.Tools;
import org.json.JSONObject;

public class NoticeListItem
{

	public NoticeListItem()
	{
		pm = Integer.valueOf(0);
		pmclick = Integer.valueOf(0);
		cm = Integer.valueOf(0);
		cmclick = Integer.valueOf(0);
		tm = Integer.valueOf(0);
		tmclick = Integer.valueOf(0);
		stm = Integer.valueOf(0);
		stmclick = Integer.valueOf(0);
		am = Integer.valueOf(0);
		amclick = Integer.valueOf(0);
		amcontent = "";
		timestamp = "0";
	}

	public static NoticeListItem _initValue(JSONObject jsonobject)
	{
		String s = jsonobject.optString("sId");
		SiteInfo siteinfo = SiteInfoDBHelper.getInstance().getByCloudId(
				Integer.valueOf(s).intValue());
		NoticeListItem noticelistitem = null;
		if (siteinfo != null)
		{
			boolean flag = Tools.stringIsNullOrEmpty(siteinfo.getSiteAppid());
			noticelistitem = null;
			if (!flag)
			{
				noticelistitem = new NoticeListItem();
				noticelistitem.siteappid = siteinfo.getSiteAppid();
				noticelistitem.sitename = siteinfo.getSiteName();
				noticelistitem.siteicon = siteinfo.getIconFromSD();
				noticelistitem.pm = Integer.valueOf(jsonobject.optInt("pm"));
				if (noticelistitem.pm.intValue() == 0)
					noticelistitem.pmclick = Integer.valueOf(1);
				noticelistitem.cm = Integer.valueOf(jsonobject.optInt("cm"));
				if (noticelistitem.cm.intValue() == 0)
					noticelistitem.cmclick = Integer.valueOf(1);
				noticelistitem.tm = Integer.valueOf(jsonobject.optInt("tm"));
				if (noticelistitem.tm.intValue() == 0)
					noticelistitem.tmclick = Integer.valueOf(1);
				noticelistitem.stm = Integer.valueOf(jsonobject.optInt("stm"));
				if (noticelistitem.stm.intValue() == 0)
					noticelistitem.stmclick = Integer.valueOf(1);
				noticelistitem.cloudid = s;
				noticelistitem.timestamp = String.valueOf(Tools
						._getTimeStampUnix());
			}
		}
		return noticelistitem;
	}

	public ContentValues _getSqlVal()
	{
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("_id", _id);
		contentvalues.put("siteappid", siteappid);
		contentvalues.put("sitename", sitename);
		contentvalues.put("siteicon", siteicon);
		contentvalues.put("pm", pm);
		contentvalues.put("pmclick", pmclick);
		contentvalues.put("cm", cm);
		contentvalues.put("cmclick", cmclick);
		contentvalues.put("tm", tm);
		contentvalues.put("tmclick", tmclick);
		contentvalues.put("stm", stm);
		contentvalues.put("stmclick", stmclick);
		contentvalues.put("am", am);
		contentvalues.put("amclick", amclick);
		contentvalues.put("amcontent", amcontent);
		contentvalues.put("cloudid", cloudid);
		contentvalues.put("timestamp", timestamp);
		return contentvalues;
	}

	public String _id;
	public Integer am;
	public Integer amclick;
	public String amcontent;
	public String cloudid;
	public Integer cm;
	public Integer cmclick;
	public Integer pm;
	public Integer pmclick;
	public String siteappid;
	public String siteicon;
	public String sitename;
	public Integer stm;
	public Integer stmclick;
	public String timestamp;
	public Integer tm;
	public Integer tmclick;
}
//2131296256