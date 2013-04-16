package net.discuz.source;

import java.io.File;
import net.discuz.init.InitSetting;
import net.discuz.source.storage.GlobalDBHelper;
import net.discuz.tools.Core;
import net.discuz.tools.Tools;

public class ClearCache
{

	public ClearCache()
	{}

	public static void _clearAppCacheData(String s)
	{
		DFile._deleteFileOrDir(new File((new StringBuilder())
				.append(InitSetting.CACHE_PATH).append(s).toString()));
	}

	public static void _clearEverydayData()
	{
		String s = GlobalDBHelper.getInstance().getValue(
				"cache_daily_timestamp");
		if (s == null)
		{
			GlobalDBHelper.getInstance().replace(
					"cache_daily_timestamp",
					(new StringBuilder()).append(Tools._getTimeStamp())
							.append("").toString());
		} else
		{
			long l = Long.parseLong(s);
			long l1 = Tools._getTimeStamp();
			if (l1 - l >= 0x15180L)
			{
				DFile._deleteFileOrDir(new File((new StringBuilder())
						.append(InitSetting.CACHE_PATH).append("_t/")
						.toString()));
				GlobalDBHelper.getInstance().replace("cache_daily_timestamp",
						(new StringBuilder()).append(l1).append("").toString());
				return;
			}
		}
	}

	public static void _clearForumNavCacheData()
	{
		DFile._deleteFileOrDir(new File((new StringBuilder())
				.append(InitSetting.CACHE_PATH).append("forumnav/").toString()));
	}

	public static void _clearIcon(String s)
	{
		DFile._deleteFileOrDir(new File((new StringBuilder())
				.append("/sdcard/discuz/style/siteicon_icon_").append(s)
				.append(".png").toString()));
	}

	public static void _clearUserCacheData()
	{
		DFile._deleteFileOrDir((new StringBuilder())
				.append(InitSetting.CACHE_PATH).append("viewthread/")
				.toString());
		DFile._deleteFileOrDir(new File((new StringBuilder())
				.append(InitSetting.CACHE_PATH).append("vperm/").toString()));
	}

	public static void _clearUserProfileCacheData(String s)
	{
		DFile._deleterFileOrDir((new StringBuilder())
				.append(InitSetting.CACHE_PATH)
				.append("profile/")
				.append(Tools._md5(Core.getSiteUrl(new String[] { s,
						"module=profile" }))).append("_json").toString());
		DFile._deleteFileOrDir(new File((new StringBuilder())
				.append(InitSetting.CACHE_PATH).append("vperm/").toString()));
	}

	public static void _clearViewThreadTemplateCacheData()
	{
		DFile._deleteFileOrDir(new File((new StringBuilder())
				.append(InitSetting.CACHE_PATH).append("viewthread_cache/")
				.toString()));
	}

	public static void _clearWeekCacheData()
	{
		String s = GlobalDBHelper.getInstance().getValue(
				"cache_weekly_timestamp");
		if (s == null)
		{
			GlobalDBHelper.getInstance().replace(
					"cache_weekly_timestamp",
					(new StringBuilder()).append(Tools._getTimeStamp())
							.append("").toString());
		} else
		{
			long l = Long.parseLong(s);
			long l1 = Tools._getTimeStamp();
			if (l1 - l >= 0x93a80L)
			{
				DFile._deleteFileOrDir(new File((new StringBuilder())
						.append(InitSetting.CACHE_PATH).append("_w/")
						.toString()));
				GlobalDBHelper.getInstance().replace("cache_weekly_timestamp",
						(new StringBuilder()).append(l1).append("").toString());
				return;
			}
		}
	}

	public static void clearTempData(String s)
	{
		DFile._deleteFileOrDir(new File((new StringBuilder())
				.append(InitSetting.CACHE_PATH)
				.append(InitSetting._getAppPath(s)).toString()));
	}
}
//2131296256