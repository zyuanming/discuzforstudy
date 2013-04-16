package net.discuz.source.activity;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.model.SiteInfo;
import net.discuz.source.ClearCache;
import net.discuz.source.DialogPopup;
import net.discuz.source.ExceptionReporter;
import net.discuz.source.InitSiteData;
import net.discuz.source.ShowMessage;
import net.discuz.source.InterFace.InterfaceErrorException;
import net.discuz.source.popupwindow.MenuPopupWindow;
import net.discuz.source.service.DownLoadService;
import net.discuz.source.storage.SiteInfoDBHelper;
import net.discuz.source.widget.Loading;
import net.discuz.tools.Core;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;

public class DiscuzBaseActivity extends Activity
{
	public static interface CallBackKeyEvent
	{

		public abstract void callBack();
	}

	public DiscuzBaseActivity()
	{
		isExitApp = false;
		menuPopupWindow = null;
		siteAppId = null;
		siteUrl = null;
	}

	public void ReceiveBoardCast(String s, BroadcastReceiver broadcastreceiver)
	{
		IntentFilter intentfilter = new IntentFilter();
		intentfilter.addAction(s);
		registerReceiver(broadcastreceiver, intentfilter);
	}

	/**
	 * 通过handler弹出提示信息
	 * 
	 * @param i
	 *            message 显示的文档
	 * @param j
	 *            status，图标资源
	 */
	public void ShowMessageByHandler(int i, int j)
	{
		ShowMessageByHandler(getString(i), j);
	}

	public void ShowMessageByHandler(final String message, final int status)
	{
		runOnUiThread(new Runnable()
		{
			public void run()
			{
				ShowMessage.getInstance(DiscuzBaseActivity.this)._showToast(
						message, status);
			}
		});
	}

	public void ShowMessageByHandler(String as[], int i)
	{
		ShowMessageByHandler(Core.getInstance()._getMessageByName(as), i);
	}

	public void dismissLoading()
	{
		if (loading != null)
			loading.dismissLoading();
	}

	/**
	 * 退出确认对话框
	 */
	public void exitConfirmDialog()
	{
		dialog = new DialogPopup(this);
		dialog._build(0, 0, 0, 0, 0);
		dialog._setMessage(R.string.message_system_exit);
		dialog._setbtnClick(new android.view.View.OnClickListener()
		{
			public void onClick(View view)
			{
				dialog._dismiss();
				try
				{
					new ClearCache();
					ClearCache._clearUserCacheData();
					Intent intent = new Intent(DiscuzBaseActivity.this,
							DownLoadService.class);
					stopService(intent);
				} catch (Exception exception)
				{
					exception.printStackTrace();
				}
				isExitApp = true;
				finish();
			}
		}, new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				isExitApp = false;
				dialog._dismiss();
			}
		});
		dialog._show();
	}

	public void finish()
	{
		dismissLoading();
		super.finish();
		stopService(new Intent(this, DownLoadService.class));
	}

	public InterfaceErrorException getErrorException()
	{
		return errorException;
	}

	protected void init()
	{}

	public void menuDismiss()
	{
		if (menuPopupWindow != null)
			menuPopupWindow._dismiss();
	}

	protected void onBackKeyEvent()
	{
		if (menuPopupWindow != null && menuPopupWindow._isShowing())
		{
			menuPopupWindow._dismiss();
			return;
		}
		if (loading != null && loading.isShown())
		{
			dismissLoading();
			return;
		}
		if (callBackKeyEvent != null)
			callBackKeyEvent.callBack();
		if (isExitApp)
		{
			exitConfirmDialog();
			return;
		} else
		{
			finish();
			return;
		}
	}

	protected void onCreate(Bundle bundle)
	{
		Log.d("DiscuzBaseActivity", "onCreate() ----> Enter");
		ExceptionReporter.register(this);
		requestWindowFeature(1);
		super.onCreate(bundle);
		if (bundle != null)
		{
			siteAppId = bundle.getString("siteappid");
			Log.d("DiscuzBaseActivity", "siteAppId --------> " + siteAppId);
			SiteInfo siteinfo = SiteInfoDBHelper.getInstance().getByAppId(
					siteAppId);
			if (siteinfo != null)
				DiscuzApp.getInstance().setSiteInfo(siteinfo);
			if (bundle.getInt("uid") > 0)
				DiscuzApp.getInstance().loadDBUser(siteAppId);
			new InitSiteData(siteAppId);
		}
		menuPopupWindow = new MenuPopupWindow(this);
		showMessage = ShowMessage.getInstance(this);
		Log.d("DiscuzBaseActivity", "onCreate() ----> Exit");
	}

	protected void onDestroy()
	{
		super.onDestroy();
		dialog = null;
		errorException = null;
		showMessage = null;
		menuPopupWindow = null;
		if (isExitApp)
		{
			Intent intent = new Intent();
			intent.setAction("ExitApp");
			sendBroadcast(intent);
			DiscuzApp.getInstance().onTerminate();
		}
	}

	public boolean onKeyDown(int i, KeyEvent keyevent)
	{
		if (i == 4)
		{
			onBackKeyEvent();
			return true;
		} else
		{
			return super.onKeyDown(i, keyevent);
		}
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

	/**
	 * 中断时保存实例状态
	 */
	protected void onSaveInstanceState(Bundle bundle)
	{
		if (siteAppId == null)
		{
			return;
		} else
		{
			bundle.putString("siteappid", siteAppId);
			bundle.putInt("uid", DiscuzApp.getInstance()
					.getLoginUser(siteAppId).getUid());
			super.onSaveInstanceState(bundle);
			return;
		}
	}

	protected void onStart()
	{
		super.onStart();
	}

	public boolean onTouchEvent(MotionEvent motionevent)
	{
		menuDismiss();
		return super.onTouchEvent(motionevent);
	}

	public void onTouchListener(View aview[])
	{
		int i = aview.length;
		for (int j = 0; j < i; j++)
			aview[j].setOnTouchListener(new android.view.View.OnTouchListener()
			{
				public boolean onTouch(View view, MotionEvent motionevent)
				{
					menuDismiss();
					return false;
				}
			});

	}

	public void refresh()
	{}

	public void setCallBackKeyEvent(CallBackKeyEvent callbackkeyevent)
	{
		callBackKeyEvent = callbackkeyevent;
	}

	public void setErrorException(
			InterfaceErrorException interfaceerrorexception)
	{
		errorException = interfaceerrorexception;
	}

	/**
	 *  显示加载图片
	 * 
	 * @param s
	 */
	public void showLoading(String s)
	{
		if (loading == null)
			loading = new Loading(this,
					(RelativeLayout) findViewById(R.id.DiscuzActivityBox));
		if (s == null)
			s = getString(R.string.loading);
		loading.showLoading(s);
	}

	private CallBackKeyEvent callBackKeyEvent;
	private DialogPopup dialog;
	private InterfaceErrorException errorException;
	protected boolean isExitApp;
	private Loading loading;
	private MenuPopupWindow menuPopupWindow;
	public ShowMessage showMessage;
	public String siteAppId;
	public String siteUrl;

}
// 2131296256