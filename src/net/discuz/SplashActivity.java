package net.discuz;

import net.discuz.app.DiscuzApp;
import net.discuz.asynctask.CheckFavSite;
import net.discuz.asynctask.UpdateTop1000Site;
import net.discuz.init.InitSetting;
import net.discuz.model.SiteInfo;
import net.discuz.source.ClearCache;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.GlobalDBHelper;
import net.discuz.source.storage.SiteInfoDBHelper;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.HttpConnThread;
import net.discuz.tools.NoticeCenter;
import net.discuz.tools.NoticeCenterUpgrade;
import net.discuz.tools.Tools;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;

import com.umeng.analytics.MobclickAgent;

public class SplashActivity extends DiscuzBaseActivity
{

	public SplashActivity()
	{}

	/**
	 * 进入主界面
	 */
	private void enter()
	{
		String s = GlobalDBHelper.getInstance().getValue("default_jumpsiteid");
		if (s == null || "-1".equals(s))
		{
			Intent intent = new Intent();
			intent.setClass(this, MainActivity.class);
			startActivity(intent);
		} else if ("0".equals(s))
		{
			String s1 = GlobalDBHelper.getInstance().getValue(
					"sitelist_backsiteid");
			Core.visitSite(this, SiteInfoDBHelper.getInstance().getByAppId(s1));
		} else
		{
			SiteInfo siteinfo = SiteInfoDBHelper.getInstance().getByAppId(s);
			if (siteinfo == null)
			{
				Intent intent1 = new Intent();
				intent1.setClass(this, MainActivity.class);
				startActivity(intent1);
			} else
			{
				Core.visitSite(this, siteinfo);
			}
		}
		finish();
	}

	public void onCreate(Bundle bundle)
	{
		Log.d("SplashActivity", "onCreate() -----> Enter");
		super.onCreate(bundle);
		setContentView(R.layout.splashscreen);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		DiscuzApp.getInstance().density = displaymetrics.density;
		DiscuzApp.getInstance().setUserAgent(
				Core.getInstance()._getDefaultUserAgent());

		// 判断是否有SD卡
		if (Tools._isSdcardMounted().booleanValue())
			InitSetting.CACHE_PATH = "/sdcard/discuz/cache/";

		// 清除缓存
		ClearCache._clearEverydayData();
		ClearCache._clearWeekCacheData();
		MobclickAgent.setDebugMode(true);
		MobclickAgent.setSessionContinueMillis(3000L);
		MobclickAgent.onError(this);
		// 发送手机信息
		DiscuzStats.send();
		UpdateTop1000Site updatetop1000site = new UpdateTop1000Site(this);

		// 保存，加载默认的站点信息，直接写到程序中
		updatetop1000site.loadDefaultSite();
		if (updatetop1000site._needToUpdate()
				&& Core.getInstance()._hasInternet())
			updatetop1000site.start();
		(new CheckFavSite(this)).execute(new Void[0]);
		NoticeCenter.isMainActivityRunning = true;
		NoticeCenterUpgrade.upgradeNoticeApp();
		new NoticeCenter();
		NoticeCenter.addToken();
		HttpConnThread httpconnthread = new HttpConnThread(null, 1);
		httpconnthread.setUrl((new StringBuilder())
				.append("http://api.discuz.qq.com/mobile/recommendList?")
				.append(Core.getInstance()._getParamsSig(new String[0]))
				.toString());
		httpconnthread.setCacheType(2);
		String s = getClass().getSimpleName();
		httpconnthread.setFilter(s);
		DiscuzApp.getInstance().setHttpConnListener(s,
				new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
				{
					public void onFailed(Exception exception)
					{
						exception.printStackTrace();
						enter();
					}

					public void onSucceed(String s1, String s2)
					{
						enter();
					}
				});
		DiscuzApp.getInstance().sendHttpConnThread(httpconnthread);
		Log.d("SplashActivity", "onCreate() -----> Exit");
	}

	public boolean onKeyDown(int i, KeyEvent keyevent)
	{
		if (i == 4)
			return true;
		else
			return super.onKeyDown(i, keyevent);
	}

	public boolean onKeyUp(int i, KeyEvent keyevent)
	{
		if (i == 4)
			return true;
		else
			return super.onKeyUp(i, keyevent);
	}

	protected void onPause()
	{
		super.onPause();
		MobclickAgent.onPause(this);
	}

	protected void onResume()
	{
		super.onResume();
		MobclickAgent.onResume(this);
	}

}
// 2131296256