package net.discuz.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

import net.discuz.R;
import net.discuz.activity.siteview.SiteViewActivity;
import net.discuz.app.DiscuzApp;
import net.discuz.dialog.LoginDialog;
import net.discuz.dialog.QQPtConnectDialog;
import net.discuz.init.InitSetting;
import net.discuz.model.DownFile;
import net.discuz.model.SiteInfo;
import net.discuz.source.DEBUG;
import net.discuz.source.DFile;
import net.discuz.source.DialogPopup;
import net.discuz.source.SiteDetail;
import net.discuz.source.InterFace.OnLogin;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.service.DownLoadService;
import net.discuz.source.storage.GlobalDBHelper;
import a.a.a.s;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;

public class Core
{

	private Core(Context context1)
	{
		context = context1;
	}

	public static void _DownLoad(final DiscuzBaseActivity discuzbaseactivity)
	{
		Intent intent = new Intent(discuzbaseactivity, DownLoadService.class);
		DownLoadService.setDownLoadParams(discuzbaseactivity, new DownFile(
				"http://www.discuz.net/mobile.php?platform=android",
				"DiscuzMobile.apk", null, true, false, false,
				new DownLoadService.DownLoadInterface()
				{

					public void downLoadError(Exception exception)
					{}

					public void downLoadSuccess(String s1)
					{
						GlobalDBHelper.getInstance().replace("must_update",
								"false");
						GlobalDBHelper.getInstance().replace("is_need_update",
								"false");
						(new DFile())._openFile(discuzbaseactivity, s1);
						discuzbaseactivity.dismissLoading();
						discuzbaseactivity.finish();
					}
				}));
		discuzbaseactivity.startService(intent);
	}

	// 对字符串进行UTF-8编码
	private String _getEnCode(String s1) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(s1, "utf-8");
	}

	/**
	 * 获得当前应用的包信息
	 * 
	 * @return
	 */
	private PackageInfo _getPackAgeInfo()
	{
		PackageManager packagemanager = context.getPackageManager();
		PackageInfo packageinfo;
		try
		{
			packageinfo = packagemanager.getPackageInfo(
					context.getPackageName(), 0);
		} catch (android.content.pm.PackageManager.NameNotFoundException namenotfoundexception)
		{
			return null;
		}
		return packageinfo;
	}

	/**
	 * 弹出对话框，选择是否下载新版本
	 * 
	 * @param discuzbaseactivity
	 */
	public static void _isUpdate(final DiscuzBaseActivity discuzbaseactivity)
	{
		final DialogPopup dialogpopup = new DialogPopup(discuzbaseactivity);
		final GlobalDBHelper globaldbhelper = GlobalDBHelper.getInstance();
		if ("true".equals(globaldbhelper.getValue("is_need_update")))
		{
			dialogpopup._build(0, 0, R.string.message_update_app_title,
					R.string.message_update_app_ok,
					R.string.message_update_app_no);
			if (globaldbhelper.getValue("updata_information") != null)
				dialogpopup._setMessage(globaldbhelper
						.getValue("updata_information"));
			else
				dialogpopup._setMessage(discuzbaseactivity.getResources()
						.getString(R.string.message_system_update));
			dialogpopup._setbtnClick(new android.view.View.OnClickListener()
			{

				public void onClick(View view)
				{
					discuzbaseactivity.ShowMessageByHandler("正在下载最新版本", 1);
					Core._DownLoad(discuzbaseactivity);
					dialogpopup._dismiss();
				}
			}, new android.view.View.OnClickListener()
			{

				public void onClick(View view)
				{
					globaldbhelper.replace("is_need_update", "false");
					InitSetting.IS_UPDATE = false;
					dialogpopup._dismiss();
				}
			});
			dialogpopup._show();
		}
	}

	/**
	 * 设置Activity无标题样式
	 * 
	 * @param activity
	 */
	private void _windowNoTitle(Activity activity)
	{
		activity.requestWindowFeature(1);
	}

	public static Core getInstance()
	{
		if (instance == null)
			instance = new Core(DiscuzApp.getInstance());
		return instance;
	}

	/**
	 * 获得站点的请求URL地址
	 * 
	 * @param as
	 *            URL后面跟的参数数组
	 * @return
	 */
	public static String getSiteUrl(String as[])
	{
		SiteInfo siteinfo = DiscuzApp.getInstance().getSiteInfo(as[0]);
		String s1 = "";
		if (siteinfo != null)
		{
			StringBuilder stringbuilder = (new StringBuilder()).append(siteinfo
					.getSiteUrl());
			String s2;
			String s3;
			String s4;
			String s5;
			if (siteinfo.getSiteUrl().endsWith("/"))
				s2 = "";
			else
				s2 = "/";
			s3 = stringbuilder.append(s2).toString();
			s4 = (new StringBuilder()).append(s3)
					.append("api/mobile/index.php?mobile=no&version=1")
					.toString();
			s5 = "";
			for (int i = 1; i < as.length; i++)
				if (as[i] != null)
					s5 = (new StringBuilder()).append(s5).append("&")
							.append(as[i]).toString();

			s1 = (new StringBuilder()).append(s4).append(s5).toString();
		}
		return s1;
	}

	/**
	 * 显示登录或注册对话框
	 * 
	 * @param discuzbaseactivity
	 * @param onlogin
	 */
	public static void showLogin(DiscuzBaseActivity discuzbaseactivity,
			OnLogin onlogin)
	{
		Log.d("Core", "showLogin() ----> Enter");
		String s1 = DiscuzApp.getInstance()
				.getSiteInfo(discuzbaseactivity.siteAppId).getSiteUrl();
		String as[] = { ".qq.com", ".tenpay.com", ".paipai.com",
				".tencent.com", ".3366.com", ".imqq.com", ".pengyou.com",
				".soso.com", ".qzone.com", ".qplus.com" };
		int i = 0;
		boolean flag = false;
		while (i < as.length)
		{
			if (flag || s1.contains(as[i]))
				flag = true;
			else
				flag = false;
			i++;
		}
		if (flag)
		{
			Log.d("Core", "flag = true" + " qq");
			QQPtConnectDialog qqptconnectdialog = new QQPtConnectDialog(
					discuzbaseactivity);
			qqptconnectdialog.setCheckPost(true);
			qqptconnectdialog.setOnLogin(onlogin);
			qqptconnectdialog.show();
			return;
		} else
		{
			Log.d("Core", "flag = false" + " qq");
			LoginDialog logindialog = new LoginDialog(discuzbaseactivity);
			logindialog.setCheckPost(true);
			logindialog.setOnLogin(onlogin);
			logindialog.show();
			return;
		}
	}

	/**
	 * 访问站点
	 * 
	 * @param discuzbaseactivity
	 * @param siteinfo
	 *            站点信息
	 */
	public static void visitSite(final DiscuzBaseActivity discuzbaseactivity,
			final SiteInfo siteinfo)
	{
		String s1 = siteinfo.getSiteAppid();
		int i = siteinfo.getFlag();
		if (i == 0)
		{
			if (!Tools._isSdcardMounted().booleanValue())
			{
				discuzbaseactivity.ShowMessageByHandler(
						R.string.message_sdcard_remove, 3); // 您的SD卡已被卸载，无法进行正常的读取工作
			} else
			{
				Intent intent = new Intent();
				intent.setClass(discuzbaseactivity, SiteViewActivity.class);
				intent.putExtra("siteappid", s1);
				discuzbaseactivity.startActivity(intent);
			}
		} else if (i == 1)
		{
			if (!Tools._isSdcardMounted().booleanValue())
			{
				discuzbaseactivity.ShowMessageByHandler(
						R.string.message_sdcard_remove, 3);
			} else
			{
				discuzbaseactivity.showLoading(discuzbaseactivity
						.getResources().getString(R.string.loading_site));
				new SiteDetail(discuzbaseactivity, siteinfo.getSiteUrl(),
						siteinfo.getSiteName(),
						new DownLoadService.DownLoadInterface()
						{

							public void downLoadError(Exception exception)
							{
								discuzbaseactivity.dismissLoading();
							}

							public void downLoadSuccess(String s2)
							{
								Intent intent1 = new Intent();
								intent1.setClass(discuzbaseactivity,
										SiteViewActivity.class);
								intent1.putExtra("siteurl",
										siteinfo.getSiteUrl());
								discuzbaseactivity.startActivity(intent1);
								discuzbaseactivity.dismissLoading();
							}
						});
			}
		}
	}

	public int _getActiveNetWorkType()
	{
		return ((ConnectivityManager) context.getSystemService("connectivity"))
				.getActiveNetworkInfo().getType();
	}

	public String _getBuild()
	{
		return (new StringBuilder()).append("and_all-")
				.append(_getVersionCode()).toString();
	}

	public String _getDefaultUserAgent()
	{
		return (new WebView(context)).getSettings().getUserAgentString();
	}

	public Display _getDisplay(Activity activity)
	{
		display = activity.getWindowManager().getDefaultDisplay();
		return display;
	}

	/**
	 * 获取手机IMEI号
	 * 
	 * @return
	 */
	public String _getIMEI()
	{
		Log.d("Core", "_getIMEI() -----> Enter");
		String s1 = ((TelephonyManager) context.getSystemService("phone"))
				.getDeviceId();
		Log.i("Core", s1);
		Log.d("Core", "_getIMEI() -----> Exit");
		return s1;
	}

	public void _getMemoryInfo()
	{
		ActivityManager activitymanager = (ActivityManager) context
				.getSystemService("activity");
		android.app.ActivityManager.MemoryInfo memoryinfo = new android.app.ActivityManager.MemoryInfo();
		activitymanager.getMemoryInfo(memoryinfo);
		DEBUG.o((new StringBuilder()).append(" minfo.availMem ")
				.append(memoryinfo.availMem).toString());
		DEBUG.o((new StringBuilder()).append(" minfo.lowMemory ")
				.append(memoryinfo.lowMemory).toString());
		DEBUG.o((new StringBuilder()).append(" minfo.threshold ")
				.append(memoryinfo.threshold).toString());
	}

	public String _getMessageByName(String as[])
	{
		String s1 = _getStringByName((new StringBuilder()).append("message_")
				.append(as[0]).toString());
		if ("".equals(s1))
			s1 = as[1];
		return s1;
	}

	public String _getMobileDataParams(String as[], String s1)
	{
		String s2 = "";
		String s3 = "";
		int i = 0;
		while (i < as.length)
		{
			StringBuilder stringbuilder = (new StringBuilder()).append(s2);
			String s4;
			StringBuilder stringbuilder1;
			String s5;
			if (s2 == "")
				s4 = as[i];
			else
				s4 = (new StringBuilder()).append("|").append(as[i]).toString();
			s2 = stringbuilder.append(s4).toString();
			stringbuilder1 = (new StringBuilder()).append(s3);
			if (s3 == "")
				s5 = as[i];
			else
				s5 = (new StringBuilder()).append("|").append(as[i]).toString();
			s3 = stringbuilder1.append(s5).toString();
			i++;
		}
		return (new StringBuilder())
				.append(Tools._md5((new StringBuilder()).append(Tools._md5(s2))
						.append(s1).toString())).append("|").append(s2)
				.toString();
	}

	public String _getPackAgeName()
	{
		return _getPackAgeInfo().packageName;
	}

	public String _getParamsSig(String as[])
	{
		Log.d("Core", "_getParamsSig() -----> Enter");
		String s1 = "";
		StringBuffer stringbuffer = new StringBuffer();
		// 带参数
		if (as.length > 0)
		{
			int k = as.length;
			int l = 0;
			while (l < k)
			{
				String s7 = as[l];
				if (!Tools.stringIsNullOrEmpty(s7))
				{
					String as2[] = s7.split("=");
					if (as2.length > 1)
						try
						{
							stringbuffer.append((new StringBuilder())
									.append(as2[0]).append("=")
									.append(_getEnCode(as2[1])).toString());
							stringbuffer.append("&");
						} catch (UnsupportedEncodingException unsupportedencodingexception1)
						{
							unsupportedencodingexception1.printStackTrace();
						}
				}
				l++;
			}
		}
		DEBUG.o((new StringBuilder()).append(">>>>").append(stringbuffer)
				.toString());
		String as1[];
		StringBuffer stringbuffer1;
		int i;
		try
		{
			stringbuffer.append((new StringBuilder()).append("imei=")
					.append(Tools._md5(_getIMEI())).append("&")
					.append("build=").append(_getEnCode(_getBuild()))
					.toString());
		} catch (UnsupportedEncodingException unsupportedencodingexception)
		{
			unsupportedencodingexception.printStackTrace();
		}
		as1 = stringbuffer.toString().split("&");
		Arrays.sort(as1);
		stringbuffer1 = new StringBuffer();
		i = as1.length;
		for (int j = 0; j < i; j++)
		{
			stringbuffer1.append(as1[j]);
			stringbuffer1.append("&");
		}

		String s2 = Tools.trim(stringbuffer1.toString(), "&");
		Log.i("Core", s2);
		if ("".equals(s1))
		{
			s1 = (new s()).as();
			Log.i("Core", "(new s()).as() = " + s1);
		}
		String s3 = String.valueOf(System.currentTimeMillis());
		DEBUG.o((new StringBuilder()).append("sigkey:").append(s1).toString());
		String s4 = (new StringBuilder()).append(s2).append("|").append(s1)
				.append("|").append(s3).toString();
		Log.i("Core", s4);
		String s5 = Tools._md5(s4);
		String s6 = (new StringBuilder()).append(s2).append("&ts=").append(s3)
				.append("&sig=").append(s5).toString();
		Log.i("Core", s6);

		Log.d("Core", "_getParamsSig() -----> Enter");
		return s6;
	}

	public String _getStringByName(String s1)
	{
		int i = context.getResources().getIdentifier(s1, "string",
				_getPackAgeName());
		if (i == 0)
			return "";
		else
			return context.getString(i);
	}

	public int _getTitleHeight(View view)
	{
		Rect rect = new Rect();
		view.getWindowVisibleDisplayFrame(rect);
		return rect.top;
	}

	public int _getVersionCode()
	{
		return _getPackAgeInfo().versionCode;
	}

	/**
	 * 获得当前应用程序的版本信息，方便检查程序更新
	 * 
	 * @return
	 */
	public String _getVersionName()
	{
		return _getPackAgeInfo().versionName;
	}

	public int _getWebViewScale(Activity activity)
	{
		int i = _getDisplay(activity).getWidth();
		if (i < 480)
			return 68;
		return i < 720 ? 100 : 200;
	}

	public boolean _hasInternet()
	{
		NetworkInfo networkinfo = ((ConnectivityManager) context
				.getSystemService("connectivity")).getActiveNetworkInfo();
		return networkinfo != null && networkinfo.isConnected();
	}

	public void _hideSoftInput(Activity activity)
	{
		((InputMethodManager) activity.getSystemService("input_method"))
				.hideSoftInputFromWindow(activity.getCurrentFocus()
						.getWindowToken(), 2);
	}

	public void _hideSoftInput(EditText edittext)
	{
		((InputMethodManager) context.getSystemService("input_method"))
				.hideSoftInputFromWindow(edittext.getWindowToken(), 0);
	}

	public void _initWindow(Activity activity)
	{
		_windowNoTitle(activity);
	}

	public void _isUpdateApk(final DiscuzBaseActivity _activity)
	{
		if (_activity != null && InitSetting.IS_SETUP)
		{
			final DialogPopup dialog = new DialogPopup(_activity);
			dialog._build(0, 0, 0, 0, 0);
			dialog._setMessage(R.string.message_system_setup);
			dialog._setbtnClick(new android.view.View.OnClickListener()
			{

				public void onClick(View view)
				{
					dialog._dismiss();
					(new DFile())._setUp(_activity);
					InitSetting.IS_SETUP = false;
				}
			}, new android.view.View.OnClickListener()
			{

				public void onClick(View view)
				{
					dialog._dismiss();
				}
			});
			dialog._show();
		}
	}

	public void _showSoftInput(EditText edittext)
	{
		((InputMethodManager) context.getSystemService("input_method"))
				.showSoftInput(edittext, 2);
	}

	public ColorStateList createReadSelector()
	{
		int ai[][] = new int[4][];
		ai[0] = (new int[] { 0x10100a7 });
		int ai1[] = new int[1];
		ai1[0] = -0x10100a7;
		ai[1] = ai1;
		ai[2] = (new int[] { 0x101009c });
		int ai2[] = new int[1];
		ai2[0] = -0x101009c;
		ai[3] = ai2;
		return new ColorStateList(ai, new int[] {
				context.getResources().getColor(
						R.color.listview_item_subject_select),
				context.getResources().getColor(R.color.list_item_selected),
				context.getResources().getColor(
						R.color.listview_item_subject_select),
				context.getResources().getColor(R.color.list_item_selected) });
	}

	public ColorStateList createSelector()
	{
		int ai[][] = new int[4][];
		ai[0] = (new int[] { 0x10100a7 });
		int ai1[] = new int[1];
		ai1[0] = -0x10100a7;
		ai[1] = ai1;
		ai[2] = (new int[] { 0x101009c });
		int ai2[] = new int[1];
		ai2[0] = -0x101009c;
		ai[3] = ai2;
		return new ColorStateList(ai, new int[] {
				context.getResources().getColor(
						R.color.listview_item_subject_select),
				context.getResources().getColor(
						R.color.listview_item_subject_unselect),
				context.getResources().getColor(
						R.color.listview_item_subject_select),
				context.getResources().getColor(
						R.color.listview_item_subject_unselect) });
	}

	/**
	 * 通过WebView显示网页
	 * 
	 * @param s1
	 *            网页URL
	 */
	public void gotoUrlByWebView(String s1)
	{
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.setData(Uri.parse(s1));
		intent.addFlags(0x10000000);
		intent.addFlags(0x10000000);
		context.startActivity(intent);
	}

	private static Core instance;
	private Context context;
	private Display display;
}
// 2131296256