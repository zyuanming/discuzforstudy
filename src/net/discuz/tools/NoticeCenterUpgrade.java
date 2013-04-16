package net.discuz.tools;

import java.util.List;
import net.discuz.model.LoginInfo;
import net.discuz.model.SiteInfo;
import net.discuz.source.DEBUG;
import net.discuz.source.storage.*;
import org.json.JSONObject;

public class NoticeCenterUpgrade
{

	public NoticeCenterUpgrade()
	{}

	private static void upgradeLoginToken(final LoginInfo logininfo, String s,
			final int i)
	{
		NoticeCenter.NoticeHttpConnListener noticehttpconnlistener = new NoticeCenter.NoticeHttpConnListener(
				(new StringBuilder()).append("UpgradeLoginToken").append(i)
						.toString())
		{
			public void onSucceed(String s2, String s3)
			{
				if (!Tools.stringIsNullOrEmpty(s2) && s2.contains("code"))
					try
					{
						String s4 = (new JSONObject(s2)).optString("code");
						DEBUG.o((new StringBuilder())
								.append("====upgradeLoginToken===").append(s4)
								.toString());
						if ("0".equals(s4))
						{
							logininfo.setLoginToken("1");
							UserSessionDBHelper.getInstance().update(logininfo,
									logininfo.getSiteAppid());
							NoticeCenterUpgrade.loginList.remove(i);
							if (NoticeCenterUpgrade.siteList.size() == 0
									&& NoticeCenterUpgrade.loginList.size() == 0)
							{
								NoticeCenterUpgrade.siteList = null;
								NoticeCenterUpgrade.loginList = null;
								GlobalDBHelper.getInstance().replace(
										"noticeupgrade", "1");
							}
						}
					} catch (Exception exception)
					{}
				super.removeHttpConnListener();
			}
		};
		String s1 = (new StringBuilder()).append("upgradeLoginToken").append(i)
				.toString();
		StringBuilder stringbuilder = (new StringBuilder())
				.append("user/loginToken?");
		Core core = Core.getInstance();
		String as[] = new String[2];
		as[0] = osType;
		as[1] = (new StringBuilder()).append("sid=").append(s).toString();
		NoticeCenter.createHttpThread(s1, noticehttpconnlistener, stringbuilder
				.append(core._getParamsSig(as)).toString());
	}

	public static void upgradeNoticeApp()
	{
		String s = GlobalDBHelper.getInstance().getValue("noticeupgrade");
		if (Tools.isEmptyString(s) || !"1".equals(s))
		{
			siteList = SiteInfoDBHelper.getInstance().getAllSiteNotice();
			loginList = UserSessionDBHelper.getInstance().getAllLoginToken();
			int i = 0;
			int k;
			do
			{
				int j = siteList.size();
				k = 0;
				if (i >= j)
					break;
				if (!Tools.stringIsNullOrEmpty(((SiteInfo) siteList.get(i))
						.getCloudId()))
					upgradeSiteToken((SiteInfo) siteList.get(i), i);
				i++;
			} while (true);
			for (; k < loginList.size(); k++)
			{
				SiteInfo siteinfo = SiteInfoDBHelper.getInstance().getByAppId(
						((LoginInfo) loginList.get(k)).getSiteAppid());
				if (siteinfo != null
						&& !Tools.stringIsNullOrEmpty(siteinfo.getCloudId()))
					upgradeLoginToken((LoginInfo) loginList.get(k),
							siteinfo.getCloudId(), k);
			}

		}
	}

	private static void upgradeSiteToken(final SiteInfo siteinfo, final int i)
	{
		NoticeCenter.NoticeHttpConnListener noticehttpconnlistener = new NoticeCenter.NoticeHttpConnListener(
				(new StringBuilder()).append("UpgradeSiteToken").append(i)
						.toString())
		{

			public void onSucceed(String s1, String s2)
			{
				if (!Tools.stringIsNullOrEmpty(s1) && s1.contains("code"))
					try
					{
						String s3 = (new JSONObject(s1)).optString("code");
						DEBUG.o((new StringBuilder())
								.append("====UpgradeSiteToken===").append(s3)
								.toString());
						if ("0".equals(s3))
						{
							siteinfo.setIsNotifyUpgrade("1");
							SiteInfoDBHelper.getInstance().updateSiteByAppId(
									siteinfo.getSiteAppid(), siteinfo);
							NoticeCenterUpgrade.siteList.remove(i);
							if (NoticeCenterUpgrade.siteList.size() == 0
									&& NoticeCenterUpgrade.loginList.size() == 0)
							{
								NoticeCenterUpgrade.siteList = null;
								NoticeCenterUpgrade.loginList = null;
								GlobalDBHelper.getInstance().replace(
										"noticeupgrade", "1");
							}
						}
					} catch (Exception exception)
					{}
				super.removeHttpConnListener();
			}
		};
		String s = (new StringBuilder()).append("UpgradeSiteToken").append(i)
				.toString();
		StringBuilder stringbuilder = (new StringBuilder())
				.append("user/addToken?");
		Core core = Core.getInstance();
		String as[] = new String[2];
		as[0] = osType;
		as[1] = (new StringBuilder()).append("sId=")
				.append(siteinfo.getCloudId()).toString();
		NoticeCenter.createHttpThread(s, noticehttpconnlistener, stringbuilder
				.append(core._getParamsSig(as)).toString());
	}

	private static List loginList;
	private static String osType = "osType=and";
	private static List siteList;

}
// 2131296256