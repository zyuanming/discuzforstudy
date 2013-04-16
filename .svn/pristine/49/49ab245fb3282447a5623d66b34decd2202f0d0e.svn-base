package net.discuz.tools;

import java.net.URLEncoder;
import java.util.HashMap;

import net.discuz.app.DiscuzApp;
import net.discuz.model.SiteInfo;
import net.discuz.source.DEBUG;
import net.discuz.source.storage.DiscuzStatsDBHelper;
import android.util.Log;

public class DiscuzStats
{

	public DiscuzStats()
	{}

	public static boolean add(String s, String s1)
	{
		String s2 = "";
		String s3 = "";
		if (s != null && !s.equals(""))
		{
			SiteInfo siteinfo = DiscuzApp.getInstance().getSiteInfo(s);
			if (siteinfo != null)
			{
				s2 = siteinfo.getCloudId();
				s3 = siteinfo.getSiteUrl();
			}
		}
		long l = System.currentTimeMillis() / 1000L;
		return DiscuzStatsDBHelper.getInstance().insert(s2, l, s1, s3, "", "");
	}

	public static boolean add(String s, String s1, String s2)
	{
		long l = System.currentTimeMillis() / 1000L;
		return DiscuzStatsDBHelper.getInstance().insert(s, l, s2, s1, "", "");
	}

	public static boolean add(String s, String s1, String s2, String s3)
	{
		String s4 = "";
		String s5 = "";
		if (s != null && !s.equals(""))
		{
			SiteInfo siteinfo = DiscuzApp.getInstance().getSiteInfo(s);
			if (siteinfo != null)
			{
				s4 = siteinfo.getCloudId();
				s5 = siteinfo.getSiteUrl();
			}
		}
		long l = System.currentTimeMillis() / 1000L;
		return DiscuzStatsDBHelper.getInstance().insert(s4, l, s1, s5, s2, s3);
	}

	public static void clear()
	{
		DiscuzStatsDBHelper.getInstance().deleteAll();
	}

	/**
	 * 这个也太明显了，发送手机信息给服务器端
	 */
	public static void send()
	{
		Log.d("DiscuzStats", "send() ----> Enter");
		String s = DiscuzStatsDBHelper.getInstance().selectAll();
		if (s != null && !s.equals(""))
		{
			HttpConnThread httpconnthread = new HttpConnThread();
			long l = System.currentTimeMillis() / 1000L;
			String s1 = Core.getInstance()._getIMEI();
			String s2 = Core.getInstance()._getVersionName();
			if (Tools.stringIsNullOrEmpty(s2))
				s2 = "DEV";
			StringBuilder stringbuilder = new StringBuilder();
			stringbuilder.append("imei=").append(s1).append("&ver=").append(s2)
					.append("|bf70c8c3af777524d9a0e48793237364|").append(l);
			String s3 = Tools._md5(stringbuilder.toString());
			StringBuilder stringbuilder1 = new StringBuilder();
			stringbuilder1
					.append("http://service.nt.discuz.net/stats.aspx?imei=")
					.append(s1).append("&ver=").append(URLEncoder.encode(s2))
					.append("&ts=").append(l).append("&sig=").append(s3);
			httpconnthread.setUrl(stringbuilder1.toString());
			httpconnthread.setHttpMethod(1);
			HashMap hashmap = new HashMap();
			hashmap.put("info", s);
			httpconnthread.setPostparams(hashmap);
			String s4 = DiscuzStats.class.getSimpleName();
			httpconnthread.setFilter(s4);
			DiscuzApp
					.getInstance()
					.setHttpConnListener(
							s4,
							new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
							{
								public void onFailed(Exception exception)
								{}

								public void onSucceed(String s5, String s6)
								{
									if (s5.contains("\"code\":\"0\""))
									{
										DEBUG.o("****统计数据提交成功****");
										DiscuzStatsDBHelper.getInstance()
												.deleteAll();
									}
								}
							});
			DiscuzApp.getInstance().sendHttpConnThread(httpconnthread);
			Log.d("DiscuzStats", "send() ----> Exit");
		}
	}
}
// 2131296256