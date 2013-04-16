package net.discuz.source.WebInterFace;

import java.util.HashMap;

import net.discuz.R;
import net.discuz.activity.siteview.ForumViewActivity;
import net.discuz.activity.siteview.siteview_forumviewthread;
import net.discuz.app.DiscuzApp;
import net.discuz.dialog.ProfileDialog;
import net.discuz.model.DownFile;
import net.discuz.source.DEBUG;
import net.discuz.source.ShowMessage;
import net.discuz.source.InterFace.OnLogin;
import net.discuz.source.InterFace.ThreadPay;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.popupwindow.PayMain;
import net.discuz.source.service.DownLoadService;
import net.discuz.source.storage.AttachmentDBHelper;
import net.discuz.source.widget.DWebView;
import net.discuz.source.widget.DWebView_TouchEvent;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.Tools;

import org.json.JSONObject;

import android.content.Intent;

import com.umeng.analytics.MobclickAgent;

/**
 * 这个类会映射到JavaScript中，最终是通过JavaScript调用的
 * 
 * @author lkh
 * 
 */
public class ViewThreadJavaScriptInterFace extends BaseJavascriptInterFace
{

	public ViewThreadJavaScriptInterFace(DiscuzBaseActivity discuzbaseactivity,
			DWebView dwebview)
	{
		super(discuzbaseactivity, dwebview);
		activity = null;
		if (context instanceof siteview_forumviewthread)
			activity = (siteview_forumviewthread) context;
	}

	private void _downLoadAttackment(String s, String s1)
	{
		Intent intent = new Intent(activity, DownLoadService.class);
		DownLoadService.setDownLoadParams(activity, new DownFile(s, s1, null,
				true, false, false, null));
		activity.startService(intent);
		ShowMessage.getInstance(activity)._showToast(
				R.string.message_attachment_downloading, 1);
	}

	private void _downLoadBuy(final String url_final, String s, String s1)
	{
		PayMain paymain = new PayMain(activity);
		paymain.setOnPay(new ThreadPay()
		{

			public void onThreadPayFail()
			{}

			public void onThreadPaySucceed(String s2)
			{
				_downLoadAttackment(url_final, s2);
			}
		});
		paymain._init(2, new String[] { s1, s });
	}

	/**
	 * 下载附件，供JS调用
	 * 
	 * @param s
	 */
	public void DownLoadAttach(String s)
	{
		String s2;
		String s3;
		if (Tools._isSdcardMounted().booleanValue())
		{
			HashMap hashmap = AttachmentDBHelper.getInstance().getByAid(s);
			DEBUG.o((new StringBuilder()).append("downloadAttach By:")
					.append(s).toString());
			if (hashmap != null && hashmap.size() > 0)
			{
				String s1 = (String) hashmap.get("tid");
				int i = Integer.parseInt((String) hashmap.get("price"));
				int j = Integer.parseInt((String) hashmap.get("payed"));
				s2 = (String) hashmap.get("filename");
				s3 = (String) hashmap.get("attachment");
				String s4 = DiscuzApp.getInstance()
						.getSiteInfo(activity.siteAppId).getSiteUrl();
				if (s4.lastIndexOf("http://") <= -1)
					s4 = (new StringBuilder()).append("http://").append(s4)
							.toString();
				if (s3 != null && s3.indexOf("http://") == -1)
					s3 = (new StringBuilder()).append(s4).append("/")
							.append(s3).toString();
				DEBUG.o((new StringBuilder()).append("price:").append(i)
						.append("| payed:").append(j).toString());
				if (i != 0)
					_downLoadAttackment(s3, s2);
				if (j == 0)
					_downLoadBuy(s3, s, s1);
			}
		}
		activity.ShowMessageByHandler(R.string.message_attachment_sdcardmiss, 3);
	}

	public void ImageWindow(String s, String s1)
	{
		MobclickAgent.onEvent(activity, "c_imgattach");
		DiscuzStats.add(activity.siteAppId, "c_imgattach");
		String s2 = DiscuzApp.getInstance().getSiteInfo(activity.siteAppId)
				.getSiteUrl();
		String s3;
		Intent intent;
		if (s2.lastIndexOf("http://") <= -1)
			s2 = (new StringBuilder()).append("http://").append(s2).toString();
		if (s != null && s.indexOf("http://") == -1)
			s3 = (new StringBuilder()).append(s2).append("/").append(s)
					.toString();
		else
			s3 = s;
		DownLoadService.setDownLoadParams(activity, new DownFile(s3, s1, null,
				false, true, false, null));
		intent = new Intent(activity, DownLoadService.class);
		intent.setFlags(0x40000000);
		activity.showLoading(activity.getResources().getString(
				R.string.message_loading_data));
		activity.startService(intent);
	}

	public void NextPage()
	{
		activity.gotoNextPage();
	}

	public void ReloadNextPage()
	{
		activity.reloadNextPage();
	}

	public void Reply()
	{
		activity.gotoReply();
	}

	public void ShowReplyByPid(final String pid)
	{
		final int pos[] = ((DWebView_TouchEvent) webview).getDownPos();
		DEBUG.o((new StringBuilder()).append("引用楼层：").append(pid).toString());
		activity.runOnUiThread(new Runnable()
		{

			public void run()
			{
				activity.showInputReplyByPid(pid, pos[0], pos[1]);
			}
		});
	}

	public void ThreadPay(String s)
	{
		if (DiscuzApp.getInstance().getLoginUser(activity.siteAppId).getUid() > 0)
		{
			PayMain paymain = new PayMain(activity);
			paymain.setOnPay(new ThreadPay()
			{

				public void onThreadPayFail()
				{}

				public void onThreadPaySucceed(String s1)
				{
					activity.refresh();
				}
			});
			paymain._init(1, new String[] { s });
			return;
		} else
		{
			activity.ShowMessageByHandler("您未登录无法发起主题支付", 2);
			toLogin();
			return;
		}
	}

	public void addSubject(String s)
	{
		if (s == null)
			s = "";
		String s1 = (new StringBuilder())
				.append("javascript:document.getElementById('subjectbox').innerHTML='")
				.append(s).append("';").toString();
		webview.loadUrl(s1);
	}

	public void gotoUrlForumDisplay(String as[])
	{
		int i = as.length;
		for (int j = 0;; j++)
		{
			String s = null;
			if (j < i)
			{
				String s1 = as[j];
				if (s1.contains("fid="))
					s = s1.split("=")[1];
			}
			if (s != null)
			{
				Intent intent = new Intent();
				intent.putExtra("fid", s);
				intent.putExtra("forumname", "浏览版块");
				intent.setClass(context, ForumViewActivity.class);
				activity.startActivity(intent);
				return;
			} else
			{
				activity.ShowMessageByHandler("本连接有误无法打开", 3);
				return;
			}
		}
	}

	public void gotoUrlViewThread(String as[])
	{
		int i = as.length;
		for (int j = 0;; j++)
		{
			String s = null;
			if (j < i)
			{
				String s1 = as[j];
				if (s1.contains("tid="))
					s = s1.split("=")[1];
			}
			if (s != null)
			{
				Intent intent = new Intent();
				intent.putExtra("tid", s);
				intent.putExtra("forumname", "查看主题");
				intent.putExtra("siteappid", activity.siteAppId);
				intent.setClass(activity, siteview_forumviewthread.class);
				activity.startActivity(intent);
				return;
			} else
			{
				activity.ShowMessageByHandler("本连接有误无法打开", 3);
				return;
			}
		}
	}

	public void gotoUrlWebView(String s)
	{
		if (activity.isPopupShown())
			activity.closePopup();
		activity.ShowMessageByHandler("正在为您打开此链接地址", 1);
		Core.getInstance().gotoUrlByWebView(s);
	}

	/**
	 * 显示用户的信息
	 * 
	 * @param uid
	 * @param username
	 */
	public void showUserInfoPage(final String uid, final String username)
	{
		activity.runOnUiThread(new Runnable()
		{
			public void run()
			{
				(new ProfileDialog(activity, Integer.parseInt(uid), username))
						.show();
			}
		});
	}

	public void toLogin()
	{
		if (activity != null)
			Core.showLogin(activity, new OnLogin()
			{

				public void loginError()
				{}

				public void loginSuceess(String s, JSONObject jsonobject)
				{
					activity.siteAppId = s;
					activity.refresh();
				}
			});
	}

	private siteview_forumviewthread activity;

}
// 2131296256