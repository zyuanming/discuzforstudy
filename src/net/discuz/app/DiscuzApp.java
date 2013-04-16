package net.discuz.app;

import java.util.HashMap;

import net.discuz.boardcast.HttpConnReceiver;
import net.discuz.model.LoginInfo;
import net.discuz.model.SiteInfo;
import net.discuz.source.DB;
import net.discuz.source.DEBUG;
import net.discuz.source.service.HttpConnService;
import net.discuz.source.storage.GlobalDBHelper;
import net.discuz.source.storage.UserSessionDBHelper;
import net.discuz.source.timelooper.TimeLoopListener;
import net.discuz.source.timelooper.TimeLooper;
import net.discuz.tools.HttpConnThread;
import net.discuz.tools.NoticeCenter;
import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Process;
import android.util.Log;

/**
 * 全局类
 * 
 * @author Ming
 * 
 */
public class DiscuzApp extends Application
{
	public static boolean isShowPicture = true; // 是否显示图片
	private static DiscuzApp mInstance;
	public float density; // 密度
	private HashMap isReadForum;
	public HashMap isReadPm;
	public HashMap isReadThread;
	private boolean isUpdateApk; // 是否更新软件
	private HttpConnReceiver mHttpConnReceiver;
	private TimeLoopListener mListener;
	private HashMap siteInfoMap; // 站点信息
	private String userAgent; // 客户端代理

	public DiscuzApp()
	{
		isUpdateApk = false;
		density = 0.0F;
		userAgent = "";
		siteInfoMap = new HashMap();
		isReadForum = new HashMap();
		isReadThread = new HashMap();
		isReadPm = new HashMap();
		mListener = new TimeLoopListener()
		{

			public void onAlarm()
			{
				NoticeCenter.check();
				TimeLooper.startLoop(5);
			}
		};
	}

	public static DiscuzApp getInstance()
	{
		return mInstance;
	}

	private void registerHttpConnReceiver()
	{
		mHttpConnReceiver = new HttpConnReceiver();
		IntentFilter intentfilter = new IntentFilter();
		intentfilter.addAction(HttpConnReceiver.HTTP_CONN_FILTER);
		registerReceiver(mHttpConnReceiver, intentfilter);
	}

	public static void setInstance(DiscuzApp discuzapp)
	{
		mInstance = discuzapp;
	}

	private void unRegisterHttpConnReceiver()
	{
		if (mHttpConnReceiver != null)
			unregisterReceiver(mHttpConnReceiver);
	}

	public LoginInfo getLoginUser(String s)
	{
		return ((SiteInfo) siteInfoMap.get(s)).getLoginUser();
	}

	public SiteInfo getSiteInfo(String s)
	{
		return (SiteInfo) siteInfoMap.get(s);
	}

	public HashMap getSiteInfoMap()
	{
		return siteInfoMap;
	}

	public String getUserAgent()
	{
		return userAgent;
	}

	public boolean isReadForum(String s)
	{
		return isReadForum.get(s) != null;
	}

	public boolean isReadPm(String s)
	{
		return isReadPm.get(s) != null;
	}

	public boolean isReadThread(String s)
	{
		return isReadThread.get(s) != null;
	}

	public boolean isUpdateApk()
	{
		return isUpdateApk;
	}

	public void loadDBUser(String s)
	{
		LoginInfo logininfo = UserSessionDBHelper.getInstance().getByAppId(s);
		if (logininfo != null)
		{
			logininfo.setLoginCookie("auth", logininfo.getCookie());
			logininfo.setLoginCookie("saltkey", logininfo.getSaltkey());
			setLoginUser(s, logininfo);
		}
	}

	public void onCreate()
	{
		super.onCreate();
		Log.d("DiscuzApp", "onCreate() -----> Enter");
		mInstance = this;
		DEBUG.o("****\u8FD0\u884C  onCreate***DiscuzAppplication***");
		DB.getInstance(this);
		registerHttpConnReceiver();
		if ("noimage".equals(GlobalDBHelper.getInstance().getValue(
				"threadview_image")))
			isShowPicture = false;
		else
			isShowPicture = true;
		if (NoticeCenter.getNoticeSwitch())
			startTimeLooper(10);
		Log.d("DiscuzApp", "onCreate() -----> Exit");
	}

	public void onTerminate()
	{
		if (!NoticeCenter.getNoticeSwitch())
		{
			unRegisterHttpConnReceiver();
			stopHttpConnService();
		}
		Process.killProcess(Process.myPid());
		super.onTerminate();
	}

	public void removeHttpConnListener(String s)
	{
		mHttpConnReceiver.removeHttpConnListener(s);
	}

	public void removeSiteInfo(String s)
	{
		siteInfoMap.remove(s);
	}

	public void resetUserToGuest(String s)
	{
		((SiteInfo) siteInfoMap.get(s)).resetUserToGuest();
	}

	public void sendHttpConnThread(HttpConnThread httpconnthread)
	{
		Log.d("DiscuzApp", "sendHttpConnThread ----> Enter");
		Intent intent = new Intent("Discuz.HttpConnService");
		intent.setClass(this, HttpConnService.class);
		if (httpconnthread != null)
			intent.putExtra("HttpConnThread", httpconnthread);
		startService(intent);
		Log.d("DiscuzApp", "sendHttpConnThread ----> Exit");
	}

	public void setHttpConnListener(
			String s,
			net.discuz.boardcast.HttpConnReceiver.HttpConnListener httpconnlistener)
	{
		mHttpConnReceiver.setHttpConnListener(s, httpconnlistener);
	}

	public void setIsReadForum(String s)
	{
		isReadForum.put(s, Boolean.valueOf(true));
	}

	public void setIsReadPm(String s)
	{
		isReadPm.put(s, Boolean.valueOf(true));
	}

	public void setIsReadThread(String s)
	{
		isReadThread.put(s, Boolean.valueOf(true));
	}

	public void setLoginUser(String s, LoginInfo logininfo)
	{
		((SiteInfo) siteInfoMap.get(s)).setLoginUser(logininfo);
	}

	public void setSiteInfo(SiteInfo siteinfo)
	{
		siteInfoMap.put(siteinfo.getSiteAppid(), siteinfo);
	}

	public void setUpdateApk(boolean flag)
	{
		isUpdateApk = flag;
	}

	public void setUserAgent(String s)
	{
		userAgent = s;
	}

	public void startTimeLooper(int i)
	{
		TimeLooper.initLooper(mListener);
		TimeLooper.startLoop(i);
	}

	public void stopHttpConnService()
	{
		stopService(new Intent("Discuz.HttpConnService"));
	}

}
// 2131296256