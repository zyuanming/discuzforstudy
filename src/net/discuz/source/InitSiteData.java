package net.discuz.source;

import java.io.PrintStream;
import net.discuz.app.DiscuzApp;
import net.discuz.model.SiteInfo;
import net.discuz.source.storage.SiteInfoDBHelper;

public class InitSiteData
{

	public InitSiteData(String s)
	{
		siteAppId = s;
		setData();
	}

	private void setData()
	{
		SiteInfo siteinfo = SiteInfoDBHelper.getInstance()
				.getByAppId(siteAppId);
		if (siteinfo != null)
		{
			System.out.println((new StringBuilder()).append("****siteName===")
					.append(DiscuzApp.getInstance().getSiteInfo(siteAppId))
					.toString());
			DiscuzApp.getInstance().getSiteInfo(siteAppId)
					.setSiteName(siteinfo.getSiteName());
			DiscuzApp.getInstance().getSiteInfo(siteAppId)
					.setSiteNameUrl(siteinfo.getSiteUrl());
			DiscuzApp.getInstance().getSiteInfo(siteAppId)
					.setSiteCharset(siteinfo.getSiteCharset());
			DiscuzApp.getInstance().getSiteInfo(siteAppId)
					.setSiteUpdated(siteinfo.getSiteUpdated());
		}
	}

	private String siteAppId;
}
//2131296256