package net.discuz.dialog;

import java.util.HashMap;
import java.util.Iterator;

import net.discuz.R;
import net.discuz.activity.siteview.SmsNewMessage;
import net.discuz.app.DiscuzApp;
import net.discuz.json.JsonParser;
import net.discuz.model.ProfileData;
import net.discuz.source.AsyncImageLoader;
import net.discuz.source.ClearCache;
import net.discuz.source.DEBUG;
import net.discuz.source.DFile;
import net.discuz.source.DJsonReader;
import net.discuz.source.DialogPopup;
import net.discuz.source.ShowMessage;
import net.discuz.source.InterFace.JsonParseHelperCallBack;
import net.discuz.source.InterFace.OnLogin;
import net.discuz.source.InterFace.OnLogout;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.UserSessionDBHelper;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.HttpConnThread;
import net.discuz.tools.NoticeCenter;
import net.discuz.tools.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

/**
 * 站点用户信息
 * 
 * @author lkh
 * 
 */
public class ProfileDialog extends Dialog
{

	public ProfileDialog(DiscuzBaseActivity discuzbaseactivity, int i, String s)
	{
		super(discuzbaseactivity, R.style.ResizableDialogTheme);
		myprofiledata = null;
		extcreditList = null;
		profile_ui_runnable = new Runnable()
		{
			public void run()
			{
				if (myprofiledata != null)
				{
					TextView textview = (TextView) findViewById(R.id.tab_index_more_profile_username_uid);
					TextView textview1 = (TextView) findViewById(R.id.tab_index_more_profile_grouptitle);
					TextView textview2 = (TextView) findViewById(R.id.tab_index_more_profile_regdate);
					TextView textview3 = (TextView) findViewById(R.id.tab_index_more_profile_oltime);
					TextView textview4 = (TextView) findViewById(R.id.tab_index_more_profile_lastactivity);
					TextView textview5 = (TextView) findViewById(R.id.tab_index_more_profile_lastpost);
					textview.setText((CharSequence) myprofiledata
							.get("username"));
					textview1.setText(Html.fromHtml((String) myprofiledata
							.get("grouptitle")));
					textview2.setText((CharSequence) myprofiledata
							.get("regdate"));
					textview3.setText((new StringBuilder())
							.append((String) myprofiledata.get("oltime"))
							.append("小时").toString());
					textview4.setText((CharSequence) myprofiledata
							.get("lastactivity"));
					textview5.setText((CharSequence) myprofiledata
							.get("lastpost"));
					LinearLayout linearlayout = (LinearLayout) findViewById(R.id.tab_index_more_profile_other);
					linearlayout.removeAllViews();
					android.graphics.drawable.Drawable drawable = mActivity
							.getResources().getDrawable(
									R.drawable.iphone_more_bg_top);
					android.graphics.drawable.Drawable drawable1 = mActivity
							.getResources().getDrawable(
									R.drawable.iphone_more_bg_bottom);
					RelativeLayout relativelayout = (RelativeLayout) mActivity
							.getLayoutInflater().inflate(
									R.layout.profile_dialog_extra_info, null);
					((TextView) relativelayout.findViewById(R.id.profile_name))
							.setText("积分");
					((TextView) relativelayout.findViewById(R.id.profile_value))
							.setText((CharSequence) myprofiledata
									.get("credits"));
					relativelayout.setBackgroundDrawable(drawable);
					linearlayout.addView(relativelayout, -1, -2);
					if (extcreditList != null)
					{
						int j = extcreditList.size();
						Iterator iterator = extcreditList.entrySet().iterator();
						for (int k = 1; iterator.hasNext(); k++)
						{
							java.util.Map.Entry entry = (java.util.Map.Entry) iterator
									.next();
							RelativeLayout relativelayout1 = (RelativeLayout) mActivity
									.getLayoutInflater().inflate(
											R.layout.profile_dialog_extra_info,
											null);
							((TextView) relativelayout1
									.findViewById(R.id.profile_name))
									.setText((CharSequence) ((HashMap) entry
											.getValue()).get("title"));
							((TextView) relativelayout1
									.findViewById(R.id.profile_value))
									.setText((new StringBuilder())
											.append((String) ((HashMap) entry
													.getValue()).get("count"))
											.append((String) ((HashMap) entry
													.getValue()).get("unit"))
											.toString());
							if (j == k)
								relativelayout1
										.setBackgroundDrawable(drawable1);
							linearlayout.addView(relativelayout1, -1, -2);
						}

					}
				}
			}
		};
		mActivity = discuzbaseactivity;
		mUid = i;
		mUsername = s;
	}

	private void buttonStatus()
	{
		final DiscuzApp discuzapp = DiscuzApp.getInstance();
		if (mUid == discuzapp.getLoginUser(mActivity.siteAppId).getUid())
		{
			logout_btn.setText("退出帐号");
			logout_btn
					.setOnClickListener(new android.view.View.OnClickListener()
					{

						public void onClick(View view)
						{
							_logoutConfirm();
							MobclickAgent.onEvent(mActivity, "c_logout");
							DiscuzStats.add(mActivity.siteAppId, "c_logout");
						}
					});
			MobclickAgent.onEvent(mActivity, "v_myinfo");
			DiscuzStats.add(mActivity.siteAppId, "v_myinfo");
			return;
		} else
		{
			logout_btn.setVisibility(8);
			site_navbar
					.setOnSendSMSBtnClicked(new android.view.View.OnClickListener()
					{

						public void onClick(View view)
						{
							if (discuzapp.getLoginUser(mActivity.siteAppId)
									.getUid() < 1)
							{
								ShowMessage.getInstance(mActivity)._showToast(
										"对不起您没有登录", 3);
								Core.showLogin(mActivity, new OnLogin()
								{

									public void loginError()
									{}

									public void loginSuceess(String s,
											JSONObject jsonobject)
									{
										buttonStatus();
										if (mUid == discuzapp.getLoginUser(
												mActivity.siteAppId).getUid())
										{
											ShowMessage.getInstance(mActivity)
													._showToast(
															"抱歉，您不能给自己发短消息", 3);
											return;
										} else
										{
											goToSmsNewMessage();
											return;
										}
									}
								});
								return;
							} else
							{
								MobclickAgent.onEvent(mActivity, "c_newmsg");
								DiscuzStats
										.add(mActivity.siteAppId, "c_newmsg");
								goToSmsNewMessage();
								return;
							}
						}
					});
			MobclickAgent.onEvent(mActivity, "v_otherinfo");
			DiscuzStats.add(mActivity.siteAppId, "v_otherinfo");
			return;
		}
	}

	private void goToSmsNewMessage()
	{
		Intent intent = new Intent();
		intent.setClass(mActivity, SmsNewMessage.class);
		intent.putExtra("username", mUsername);
		intent.putExtra("siteappid", mActivity.siteAppId);
		mActivity.startActivity(intent);
	}

	private void init()
	{
		setContentView(R.layout.profile_dialog);
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		site_navbar.setTitle("个人信息");
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				dismiss();
			}
		});
		logout_btn = (Button) findViewById(R.id.logout);
		buttonStatus();
		HttpConnThread httpconnthread = new HttpConnThread(mActivity.siteAppId,
				1);
		String as[] = new String[3];
		as[0] = mActivity.siteAppId;
		as[1] = "module=profile";
		as[2] = (new StringBuilder()).append("uid=").append(mUid).toString();
		httpconnthread.setUrl(Core.getSiteUrl(as));
		httpconnthread.setCacheType(-1);
		httpconnthread.setFilter(filter);
		DiscuzApp.getInstance().setHttpConnListener(filter,
				new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
				{

					public void onFailed(Exception exception)
					{
						mActivity.ShowMessageByHandler(
								R.string.message_no_internet, 3);
					}

					public void onSucceed(String s, String s1)
					{
						try
						{
							DJsonReader djsonreader;
							DEBUG.o((new StringBuilder())
									.append("myprofile result:").append(s)
									.toString());
							djsonreader = new DJsonReader(s);
							boolean flag;
							djsonreader._jsonParse();
							flag = djsonreader.isjson;
							if (flag)
								(new JsonParser(mActivity)).profile(
										djsonreader._getVariables(),
										new JsonParseHelperCallBack()
										{

											public void onParseBegin()
											{}

											public void onParseFinish(Object obj)
											{
												onParseFinish((ProfileData) obj);
											}

											public void onParseFinish(
													ProfileData profiledata)
											{
												myprofiledata = profiledata
														.getMyProfile();
												extcreditList = profiledata
														.getExtCredits();
											}
										});
						} catch (JSONException je)
						{
							je.printStackTrace();
							DFile._deleteFileOrDir(s1);
							mActivity.ShowMessageByHandler(
									R.string.message_parse_error, 3);
						} catch (NullPointerException ne)
						{
							ne.printStackTrace();
							DFile._deleteFileOrDir(s1);
							mActivity.ShowMessageByHandler(
									R.string.message_no_internet, 3);
						} catch (Exception e)
						{
							e.printStackTrace();
							DFile._deleteFileOrDir(s1);
							mActivity.ShowMessageByHandler(
									R.string.parse_json_error, 3);
						}
						mActivity.runOnUiThread(profile_ui_runnable);
						return;
					}
				});
		DiscuzApp.getInstance().sendHttpConnThread(httpconnthread);
		load_myprofile_avatar();
	}

	private void load_myprofile_avatar()
	{
		DiscuzApp discuzapp = DiscuzApp.getInstance();
		asyncImageLoader = AsyncImageLoader.getAsyncImageLoader();
		asyncImageLoader.loadDrawable(
				mActivity.siteAppId,
				(new StringBuilder())
						.append(discuzapp.getSiteInfo(mActivity.siteAppId)
								.getUCenterUrl()).append("/avatar.php?uid=")
						.append(mUid).append("&size=middle").toString(),
				new net.discuz.source.AsyncImageLoader.ImageCallback()
				{

					public void imageCacheLoaded(Bitmap bitmap, String s)
					{
						ImageView imageview = (ImageView) findViewById(R.id.tab_index_more_profile_avatar);
						Tools.applyRoundCorner(imageview, bitmap);
						imageview.postInvalidate();
					}

					public void imageError(String s)
					{}

					public void imageLoaded(final Bitmap bitmap, String s)
					{
						mActivity.runOnUiThread(new Runnable()
						{

							public void run()
							{
								ImageView imageview = (ImageView) findViewById(R.id.tab_index_more_profile_avatar);
								Tools.applyRoundCorner(imageview, bitmap);
								imageview.postInvalidate();
							}
						});
					}
				});
	}

	public void _logoutConfirm()
	{
		final DialogPopup dialog = new DialogPopup(mActivity);
		dialog._build(0, 0, 0, 0, 0);
		dialog._setMessage(R.string.message_system_logout);
		dialog._setbtnClick(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				dialog._dismiss();
				NoticeCenter.logoutToken(mActivity.siteAppId);
				NoticeCenter.logoutToken(mActivity.siteAppId);
				UserSessionDBHelper.getInstance().deleteByAppId(
						mActivity.siteAppId);
				ClearCache.clearTempData(mActivity.siteAppId);
				ClearCache._clearUserCacheData();
				ClearCache._clearForumNavCacheData();
				ClearCache._clearUserProfileCacheData(mActivity.siteAppId);
				DiscuzApp.getInstance().resetUserToGuest(mActivity.siteAppId);
				ShowMessage.getInstance(mActivity)._showToast("成功退出账号", 1);
				buttonStatus();
				if (onLogout != null)
					onLogout.logout();
				dismiss();
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

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		init();
	}

	public void setOnLogout(OnLogout onlogout)
	{
		onLogout = onlogout;
	}

	private AsyncImageLoader asyncImageLoader;
	private HashMap extcreditList;
	private final String filter = getClass().getSimpleName();
	private Button logout_btn;
	private DiscuzBaseActivity mActivity;
	private int mUid;
	private String mUsername;
	private HashMap myprofiledata;
	private OnLogout onLogout;
	public Runnable profile_ui_runnable;
	private SiteNavbar site_navbar;
}
// 2131296256