package net.discuz.source;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.model.DownFile;
import net.discuz.model.SiteInfo;
import net.discuz.source.InterFace.SucceCallBack;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.service.DownLoadService;
import net.discuz.source.storage.SiteInfoDBHelper;
import net.discuz.tools.Tools;
import android.content.Intent;

public class SiteDetail
{

	public SiteDetail(DiscuzBaseActivity discuzbaseactivity, String s,
			String s1, DownLoadService.DownLoadInterface downloadinterface)
	{
		activity = null;
		success = null;
		dbHelper = null;
		activity = discuzbaseactivity;
		success = downloadinterface;
		addSiteUrl = s;
		addSiteName = s1;
		callBack = new SucceCallBack()
		{

			public void onFaild(Exception exception)
			{}

			public void onsucced(Object obj)
			{
				onsucced((String) obj);
			}

			public void onsucced(String s2)
			{
				activity.dismissLoading();
				activity.showLoading(activity.getResources().getString(
						R.string.loading_site_icon)); // 加载图标...
				SiteInfo siteinfo = dbHelper.getByUrl(addSiteUrl.replace("'",
						"’"));
				if (siteinfo != null)
				{
					DiscuzApp.getInstance().setSiteInfo(siteinfo);
					String s3 = (new StringBuilder()).append("site_icon_")
							.append(siteinfo.getSiteUrl().split("\\.")[1])
							.append(".png").toString();
					(new DFile())._createDir("/sdcard/discuz/style/");
					DownLoadService.setDownLoadParams(
							activity,
							new DownFile((new StringBuilder())
									.append(addSiteUrl).append("/mobile.png")
									.toString(), s3, "/sdcard/discuz/style/",
									false, false, false, success));
					activity.startService(new Intent(activity,
							DownLoadService.class));
				}
			}
		};
		init();
	}

	private void init()
	{
		if (addSiteName == null)
			if (addSiteUrl.indexOf("www") != -1)
				addSiteName = addSiteUrl.substring(11, addSiteUrl.length());
			else
				addSiteName = addSiteUrl.substring(7, addSiteUrl.length());
		addSiteUrl = Tools.trim(addSiteUrl, "/");
		dbHelper = SiteInfoDBHelper.getInstance();
		dbHelper.replaceSite(addSiteUrl.replace("'", "’"), addSiteName);
		Tools.CheckSiteClientMode(getClass().getSimpleName(), addSiteUrl, "",
				callBack);
	}

	private DiscuzBaseActivity activity;
	private String addSiteName;
	private String addSiteUrl;
	private SucceCallBack callBack;
	private SiteInfoDBHelper dbHelper;
	private DownLoadService.DownLoadInterface success;

}
// 2131296256