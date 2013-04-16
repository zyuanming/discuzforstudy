package net.discuz.init;

import net.discuz.app.DiscuzApp;
import net.discuz.model.LoginInfo;

public class InitSetting
{
	public static class AppParam
	{

		public static final String ADD_ID = "http://api.discuz.qq.com/mobile/site/detail?";
		public static final String ADD_URL = "http://api.discuz.qq.com/mobile/siteInfo?";
		public static final String APK_NAME = "apk_name";
		public static final String CHECK_UPDATE_URL = "http://api.discuz.qq.com/mobile/checkUpdate?";
		public static final String CURRENT_VERSION = "current_version";
		public static final String IS_NEED_UPDATE = "is_need_update";
		public static final String IS_NEED_UPDATE_TOPSite = "is_need_update_topsite";
		public static final String IS_NEED_UPDATE_TOPSite_TIMESTAMP = "is_need_update_topsite_timestamp";
		public static final String LAST_CHECK_UPDATE_TIME = "last_check_update_timestamp";
		public static final String MUST_UPDATE = "must_update";
		public static final String RECOMMAND = "http://api.discuz.qq.com/mobile/recommendList?";
		public static final String SITELIST_BACKSITEID = "sitelist_backsiteid";
		public static final String STAT_URL = "http://api.discuz.qq.com/mobile/stat/report?";
		public static final String Search_URL = "http://api.discuz.qq.com/mobile/site/search?";
		public static final String TopSiteList_CheckURL = "http://api.discuz.qq.com/mobile/blacklist/check?";
		public static final String TopSiteList_URL = "http://api.discuz.qq.com/mobile/site/topList?";
		public static final String UPDATE_INFORMATION = "updata_information";
		public static final String UPDATE_URL = "http://www.discuz.net/mobile.php?platform=android";

		public AppParam()
		{}
	}

	public static enum FavSiteFrom
	{

		Input("Input", 0, 1), QRScan("QRScan", 1, 2), Recommend("Recommend", 2,
				3);

		public int getValue()
		{
			return value;
		}

		private String name;
		private int i;
		private int value;

		private FavSiteFrom(String s, int i, int j)
		{
			this.name = s;
			this.i = i;
			this.value = j;
		}
	}

	public static class SharedPreferencesTab
	{

		public static final String IS_FIRST_OPEN = "is_first_open";
		public static final String IS_SHOW_SLIDINGDRAWER = "is_show_slidingDrawer";
		public static final String SHAREDPREFERENCESS_PUBLIC_NAME = "application_tab";
		public static final String STATISTICS = "statistics";

		public SharedPreferencesTab()
		{}
	}

	public InitSetting()
	{}

	public static String _getAppPath(String s)
	{
		return (new StringBuilder()).append(s).append("/").toString();
	}

	public static String _getUserPath(String s)
	{
		return (new StringBuilder()).append(_getAppPath(s))
				.append(DiscuzApp.getInstance().getLoginUser(s).getUid())
				.append("/").toString();
	}

	public static final int A = 0;
	public static final String APK_FILE_PATH = "/sdcard/discuz/download/DiscuzMobile.apk";
	public static String ATTACHMENT;
	public static final String AUDIO_PATH;
	public static final int B = 8;
	public static final int C = 24;
	public static final String CACHE = "cache/";
	public static final String CACHE_DAILY_TIMESTAMP = "cache_daily_timestamp";
	public static String CACHE_PATH = "/data/data/net.discuz/cache/";
	public static final String CACHE_TODATA_PATH = "_t/";
	public static final String CACHE_WEEKLY_TIMESTAMP = "cache_weekly_timestamp";
	public static final String CACHE_WEEK_PATH = "_w/";
	public static final String CHARSET_UTF_8 = "utf-8";
	public static final byte D = -1;
	public static String DEFAULT_HEADER_BG_COLOR = "#358ce4";
	public static final String E = "0x";
	public static final String FID = "fid";
	public static final String FILE_NAME = "DiscuzMobile.apk";
	public static final String FORUM_NAME = "forumname";
	public static final String HISTORY_PATH = "temp/";
	public static boolean IS_SETUP = false;
	public static boolean IS_UPDATE = false;
	public static final String LD_URL_PARAM = "/misc.php?mod=swfupload&operation=upload&simple=1";
	public static final String PICTURE_PATH = "/sdcard/DCIM/Camera/";
	public static final int POST_ALBUM = 22;
	public static final int POST_CAMERA = 21;
	public static final int POST_EDIT_TEXT = 12;
	public static final int POST_REPLY_FAST = 98;
	public static final int POST_SOUND = 31;
	public static final int POST_SUBMIT = 99;
	public static final int POST_TEXT = 11;
	public static final String RECOMMEND_DATA = "/sdcard/discuz/cache/recommend_data_file";
	public static final String SDCARD_DOWNLOAD_PATH = "download/";
	public static final String SDCARD_DOWNLOAD_TEMP_PATH = "temp/";
	public static final String SDCARD_PATH = "/sdcard/discuz/";
	public static final String SITECHARSET = "GBK";
	public static final String SITE_APP_ID_KEY = "siteappid";
	public static final String SITE_URL_KEY = "siteurl";
	public static final String STYLE = "style/";
	public static final String T_PATH = "/sdcard/DCIM/Camera/MobilePhoto.jpg";
	public static String VERSION_TMP = "1";
	public static boolean isShowingHistory = false;
	public static boolean isUpdateApk = false;
	public static final String variablelist_version = "4";

	static
	{
		isShowingHistory = true;
		ATTACHMENT = "attachment/";
		IS_UPDATE = true;
		isUpdateApk = true;
		IS_SETUP = false;
		AUDIO_PATH = (new StringBuilder()).append("/sdcard/discuz/")
				.append(ATTACHMENT).toString();
	}
}
//2131296256