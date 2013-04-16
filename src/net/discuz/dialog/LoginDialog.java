package net.discuz.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.discuz.R;
import net.discuz.activity.siteview.Vscode;
import net.discuz.app.DiscuzApp;
import net.discuz.boardcast.HttpConnReceiver;
import net.discuz.model.LoginInfo;
import net.discuz.source.ClearCache;
import net.discuz.source.DEBUG;
import net.discuz.source.DJsonReader;
import net.discuz.source.ShowMessage;
import net.discuz.source.InterFace.OnLogin;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.popupwindow.SelectList;
import net.discuz.source.storage.LastUserDBHelper;
import net.discuz.source.storage.UserSessionDBHelper;
import net.discuz.source.widget.DWebView;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.HttpConnThread;
import net.discuz.tools.NoticeCenter;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class LoginDialog extends Dialog
{
	private DiscuzApp app;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener checkVodeTaskListener;
	private String cookie;
	private String filter;
	private View login;
	private int login_question_selected;
	private android.view.View.OnClickListener loginclick;
	private String loginpassword;
	private String loginusername;
	public DiscuzBaseActivity mActivity;
	private OnLogin onlogin;
	private View register;
	private String saltkey;
	private SiteNavbar site_navbar;
	private EditText siteview_more_login_password;
	private EditText siteview_more_login_username;
	private boolean submodule_checkpost;

	public LoginDialog(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity, R.style.ResizableDialogTheme);
		mActivity = null;
		login_question_selected = 0;
		cookie = "";
		onlogin = null;
		login = null;
		register = null;
		submodule_checkpost = false;

		// 检查站点登录是否需要验证码的回调函数
		checkVodeTaskListener = new HttpConnReceiver.HttpConnListener()
		{
			public void onFailed(Exception exception)
			{
				mActivity.dismissLoading();
				exception.printStackTrace();
				mActivity.ShowMessageByHandler(R.string.message_internet_error,
						3); // 网络连接失败
			}

			public void onSucceed(String s, String s1)
			{
				if (!"error".equals(s))
				{
					if (s != null)
					{
						JSONObject jsonobject = null;
						final String seccodeurl;
						final String secqaa;
						LoginInfo logininfo;
						String s2;
						try
						{
							jsonobject = (new JSONObject(s))
									.optJSONObject("Variables");
						} catch (Exception exception)
						{
							exception.printStackTrace();
						}
						if (jsonobject != null)
						{
							seccodeurl = jsonobject.optString("seccode").trim();
							secqaa = jsonobject.optString("secqaa").trim();
							logininfo = DiscuzApp.getInstance().getLoginUser(
									mActivity.siteAppId);
							logininfo.setFormHash(jsonobject.optString(
									"formhash").trim());
							logininfo.setCookiepre(jsonobject.optString(
									"cookiepre").trim());
							saltkey = jsonobject.optString("saltkey").trim();
							logininfo.setLoginCookie(
									"saltkey",
									(new StringBuilder())
											.append(logininfo.getCookiepre())
											.append("saltkey=").append(saltkey)
											.toString());
							s2 = jsonobject.optString("sechash").trim();
							if (!seccodeurl.equals("") || !secqaa.equals(""))
							{
								mActivity.runOnUiThread(new Runnable()
								{
									public void run()
									{
										mActivity.dismissLoading();
										Intent intent = new Intent();
										intent.setClass(mActivity, Vscode.class);
										intent.putExtra("seccodeurl",
												seccodeurl);
										intent.putExtra("secqaa", secqaa);
										intent.putExtra("siteappid",
												mActivity.siteAppId);
										Log.d("LoginDialog",
												"******************************************"
														+ "siteAppId:"
														+ mActivity.siteAppId);
										mActivity.startActivity(intent);
										// 获得用户名，密码，验证码的回调方法
										Vscode.onvcode = new Vscode.onVcode()
										{

											public void VcodeError()
											{}

											public void VcodeSuceess(String s,
													String s1, String s2)
											{
												ArrayList arraylist = new ArrayList();
												if (!s2.equals(""))
													arraylist
															.add((new StringBuilder())
																	.append("sechash=")
																	.append(s2)
																	.toString());
												cookie = s2;
												if (!s.equals(""))
													arraylist
															.add((new StringBuilder())
																	.append("seccodeverify=")
																	.append(s)
																	.toString());
												if (!s1.equals(""))
													arraylist
															.add((new StringBuilder())
																	.append("secanswer=")
																	.append(s1)
																	.toString());
												DEBUG.o((new StringBuilder())
														.append("===========seccodeverify=========")
														.append(s)
														.append("======secanswer=====")
														.append(s1).toString());
												(new LoginTask(arraylist,
														submodule_checkpost))
														.execute();
											}
										};
									}
								});
								return;
							}
							(new LoginTask(null, submodule_checkpost))
									.execute();
							return;
						}
					}
					(new LoginTask(null, submodule_checkpost)).execute();
					return;
				}
			}
		};

		// 点击普通登录按钮事件
		loginclick = new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				mActivity.showLoading(mActivity
						.getString(R.string.message_logging)); // 正在登录...
				loginusername = siteview_more_login_username.getText()
						.toString();
				loginpassword = siteview_more_login_password.getText()
						.toString();
				if (loginusername.equals("") || loginpassword.equals(""))
				{
					if (!loginusername.equals(""))
					{

						ShowMessage.getInstance(LoginDialog.this.mActivity)
								._showToast("用户名不能为空", 3);

					}
					if (loginpassword.equals(""))
					{
						ShowMessage.getInstance(LoginDialog.this.mActivity)
								._showToast("密码不能为空", 3);
					}
				} else
				{
					mActivity.dismissLoading();
					if (!Core.getInstance()._hasInternet())
					{
						ShowMessage.getInstance(LoginDialog.this.mActivity)
								._showToast("请您连接网络后重试", 3);
						return;
					} else
					{
						checkVcode();
						return;
					}
				}
			}
		};

		MobclickAgent.onEvent(discuzbaseactivity, "v_login");
		DiscuzStats.add(discuzbaseactivity.siteAppId, "v_login");
		app = DiscuzApp.getInstance();
		filter = getClass().getSimpleName();
		mActivity = discuzbaseactivity;
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.login_dialog);
		init();
	}

	private class LoginTask
	{
		public void execute()
		{
			net.discuz.boardcast.HttpConnReceiver.HttpConnListener httpconnlistener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
			{

				public void onFailed(Exception exception)
				{
					mActivity.dismissLoading();
					exception.printStackTrace();
					mActivity.ShowMessageByHandler(
							R.string.message_internet_error, 3);
				}

				public void onSucceed(String s, String s1)
				{
					mActivity.dismissLoading();
					DEBUG.o((new StringBuilder()).append("login result:")
							.append(s).toString());
					if ("error".equals(s) || s == null)
					{
						loginusername = null;
						loginpassword = null;
						login_question_selected = 0;
						return;
					}
					if (s.lastIndexOf("param_error") >= 0 && mActivity != null)
					{
						mActivity.ShowMessageByHandler("参数错误!", 2);
						return;
					}
					jsonReader_Login_forumindex jsonreader_login_forumindex = new jsonReader_Login_forumindex(
							s);
					try
					{
						jsonreader_login_forumindex._jsonParse();
					} catch (JSONException jsonexception)
					{
						jsonexception.printStackTrace();
						if (mActivity != null)
							mActivity.ShowMessageByHandler(R.string.error_read,
									3);
					}
					jsonreader_login_forumindex._debug();
					if (jsonreader_login_forumindex.isjson)
					{
						String as[] = jsonreader_login_forumindex._getMessage();
						DEBUG.o((new StringBuilder())
								.append("***login.message = ").append(as[1])
								.toString());
						if (as[0].lastIndexOf("_succeed") > -1)
						{
							LoginDialog.setUser(mActivity, mActivity.siteAppId,
									jsonreader_login_forumindex);
							NoticeCenter.loginToken(mActivity.siteAppId);
							if (mActivity != null)
								mActivity.runOnUiThread(new Runnable()
								{
									public void run()
									{
										dismiss();
									}
								});
							ClearCache._clearAppCacheData(mActivity.siteAppId);
							if (onlogin != null)
							{
								DEBUG.o((new StringBuilder())
										.append(">>>login>>>")
										.append(jsonreader_login_forumindex
												._getVariables()).toString());
								MobclickAgent.onEvent(mActivity,
										"login_succeed");
								DiscuzStats.add(mActivity.siteAppId,
										"login_succeed");
								onlogin.loginSuceess(mActivity.siteAppId,
										jsonreader_login_forumindex
												._getVariables());
							}
							ClearCache._clearForumNavCacheData();
							ClearCache._clearUserCacheData();
							mActivity.runOnUiThread(new Runnable()
							{
								public void run()
								{
									(new DWebView(mActivity)).clearCache(true);
								}
							});
							mActivity
									.ShowMessageByHandler(
											(new StringBuilder())
													.append("欢迎回来! ")
													.append(DiscuzApp
															.getInstance()
															.getLoginUser(
																	mActivity.siteAppId)
															.getUsername())
													.toString(), 1);
						} else
						{
							if (onlogin != null)
								onlogin.loginError();
							mActivity.ShowMessageByHandler(as, 2);
							if (as[0].equals("submit_secqaa_invalid")
									|| as[0].equals("submit_seccode_invalid"))
								checkVcode();
						}
					} else
					{
						mActivity.ShowMessageByHandler("无法访问论坛", 3);
					}
				}
			};
			HttpConnThread httpconnthread = new HttpConnThread(
					mActivity.siteAppId, 0);
			httpconnthread.setHttpMethod(1);
			httpconnthread.setPostparams(postparams);
			httpconnthread.setUrl(url);
			httpconnthread.setCacheType(-1);
			if (!cookie.equals(""))
				httpconnthread.setCookie(cookie);
			httpconnthread.setFilter(filter);
			app.setHttpConnListener(filter, httpconnlistener);
			app.sendHttpConnThread(httpconnthread);
		}

		private HashMap<String, String> postparams;
		private String url;

		public LoginTask(List<String> list, boolean flag)
		{
			postparams = null;
			if (list == null)
				list = new ArrayList<String>();
			list.add("module=login");
			list.add("loginsubmit=yes");
			list.add("loginfield=auto");
			if (flag)
				list.add("submodule=checkpost");
			list.add(0, mActivity.siteAppId);
			url = Core.getSiteUrl((String[]) list.toArray(new String[list
					.size()]));
			postparams = new HashMap<String, String>();
			postparams.put("username", loginusername);
			postparams.put("password", loginpassword);
			postparams.put("formhash",
					DiscuzApp.getInstance().getLoginUser(mActivity.siteAppId)
							.getFormHash());
			if (login_question_selected > 0)
			{
				postparams.put("questionid",
						String.valueOf(login_question_selected));
				EditText edittext = (EditText) findViewById(R.id.siteview_more_login_answer);
				postparams.put("answer", edittext.getText().toString());
			}
		}
	}

	public static interface QQLoginListener
	{

		public abstract void onFailed();

		public abstract void onSusscced();
	}

	public static class jsonReader_Login_forumindex extends DJsonReader
	{

		public void _variableParse(JSONObject jsonobject) throws JSONException
		{
			super._variableParse(jsonobject);
			memberlogin = new LoginInfo();
			memberlogin._initValue(jsonobject);
		}

		public LoginInfo memberlogin;

		public jsonReader_Login_forumindex(String s)
		{
			super(s);
			memberlogin = null;
		}
	}

	/**
	 * 登录成功后，保存用户信息
	 * 
	 * @param context
	 * @param s
	 * @param jsonreader_login_forumindex
	 */
	public static void setUser(Context context, String s,
			jsonReader_Login_forumindex jsonreader_login_forumindex)
	{
		UserSessionDBHelper usersessiondbhelper = UserSessionDBHelper
				.getInstance();
		LastUserDBHelper lastuserdbhelper;
		if (usersessiondbhelper.getCountByAppId(s) > 0)
			usersessiondbhelper.update(jsonreader_login_forumindex.memberlogin,
					s);
		else
			usersessiondbhelper.insert(jsonreader_login_forumindex.memberlogin,
					s);
		jsonreader_login_forumindex.memberlogin.setLoginCookie("auth",
				jsonreader_login_forumindex.memberlogin.getCookie());
		jsonreader_login_forumindex.memberlogin.setLoginCookie("saltkey",
				jsonreader_login_forumindex.memberlogin.getSaltkey());
		lastuserdbhelper = LastUserDBHelper.getInstance();
		lastuserdbhelper.deleteByAppId(s);
		lastuserdbhelper.insert(s,
				jsonreader_login_forumindex.memberlogin.getUsername());
		DiscuzApp.getInstance().setLoginUser(s,
				jsonreader_login_forumindex.memberlogin);
	}

	public void _loginAuto(String s, String s1)
	{
		(new LoginTask(null, false)).execute();
	}

	/**
	 * 检查站点登录是否需要验证码，如果需要，就弹出验证码对话框
	 */
	public void checkVcode()
	{
		Log.d("LoginDialog", "checkVcode() ----> Enter");
		String as[] = new String[3];
		as[0] = mActivity.siteAppId;
		as[1] = "module=secure";
		as[2] = "type=login";
		String s = Core.getSiteUrl(as);
		DEBUG.o((new StringBuilder()).append("请求是否存在验证码url>>>").append(s)
				.toString());
		HttpConnThread httpconnthread = new HttpConnThread(mActivity.siteAppId,
				0);
		httpconnthread.setUrl(s);
		httpconnthread.setCacheType(-1);
		httpconnthread.setFilter(filter);
		app.setHttpConnListener(filter, checkVodeTaskListener);
		app.sendHttpConnThread(httpconnthread);
		Log.d("LoginDialog", "checkVcode() ----> Exit");
	}

	public void dismiss()
	{
		app.removeHttpConnListener(filter);
		super.dismiss();
	}

	public void init()
	{
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		site_navbar.setTitle("登录");
		login = findViewById(R.id.login);
		final EditText siteview_more_login_answer = (EditText) findViewById(R.id.siteview_more_login_answer);
		register = findViewById(R.id.register);
		final TextView siteview_more_login_question = (TextView) findViewById(R.id.siteview_more_login_question);
		siteview_more_login_question
				.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						ArrayList arraylist = new ArrayList();
						ArrayList arraylist1 = new ArrayList();
						for (int i = 0; i < 8; i++)
						{
							arraylist.add(Core.getInstance()._getStringByName(
									(new StringBuilder())
											.append("security_question_")
											.append(i).toString()));
							arraylist1.add((new StringBuilder()).append(i)
									.append("").toString());
						}

						final SelectList selectlist = new SelectList(mActivity,
								findViewById(R.id.siteview_more_login_box),
								siteview_more_login_question, arraylist,
								arraylist1);
						selectlist.popupWindowShowing();
						selectlist
								.setOnSelected(new net.discuz.source.popupwindow.SelectList.DeleteData()
								{

									public void deletePosition(int i)
									{}

									public void selected(int i)
									{
										siteview_more_login_question
												.setText((CharSequence) selectlist.data
														.get(i));
										login_question_selected = Integer
												.parseInt(selectlist
														.getSelectedValue());
										int j;
										if (login_question_selected > 0)
										{
											siteview_more_login_answer
													.setVisibility(0);
											siteview_more_login_question
													.setBackgroundDrawable(mActivity
															.getResources()
															.getDrawable(
																	R.drawable.iphone_more_bg_medium));
										} else
										{
											siteview_more_login_answer
													.setVisibility(8);
											siteview_more_login_question
													.setBackgroundDrawable(mActivity
															.getResources()
															.getDrawable(
																	R.drawable.iphone_more_bg_bottom));
										}
										j = (int) (16F * DiscuzApp
												.getInstance().density);
										siteview_more_login_question
												.setPadding(j, 0, j, 0);
									}
								});
					}
				});
		siteview_more_login_username = (EditText) findViewById(R.id.siteview_more_login_username);
		siteview_more_login_password = (EditText) findViewById(R.id.siteview_more_login_password);
		String s = LastUserDBHelper.getInstance().getUserNameByAppId(
				mActivity.siteAppId);
		if (s != null)
		{
			siteview_more_login_username.setText(s);
			siteview_more_login_username.selectAll();
		}
		login.setOnClickListener(loginclick);

		// 注册按钮事件
		register.setOnClickListener(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				RegisterDialog registerdialog = new RegisterDialog(mActivity);
				registerdialog.setOnLogin(new OnLogin()
				{

					public void loginError()
					{
						onlogin.loginError();
					}

					public void loginSuceess(String s, JSONObject jsonobject)
					{
						if ("dismiss".equals(jsonobject.optString("action")))
							dismiss();
						MobclickAgent.onEvent(mActivity, "login_succeed");
						DiscuzStats.add(s, "login_succeed");
						onlogin.loginSuceess(s, jsonobject);
					}
				});
				registerdialog.show();
			}
		});
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				dismiss();
			}
		});

		// 使用QQ帐号按钮登录事件
		findViewById(R.id.qq_login_box).setOnClickListener(
				new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						QQConnectDialog qqconnectdialog = new QQConnectDialog(
								mActivity);
						qqconnectdialog.setOnLogin(new OnLogin()
						{

							public void loginError()
							{
								onlogin.loginError();
							}

							public void loginSuceess(String s,
									JSONObject jsonobject)
							{
								if ("dismiss".equals(jsonobject
										.optString("action")))
									dismiss();
								MobclickAgent.onEvent(mActivity, "suc_qqlogin");
								DiscuzStats.add(s, "suc_qqlogin");
								onlogin.loginSuceess(s, jsonobject);
								NoticeCenter.loginToken(s);
							}
						});
						qqconnectdialog.show();
					}
				});
	}

	public void setCheckPost(boolean flag)
	{
		submodule_checkpost = flag;
	}

	public void setOnLogin(OnLogin onlogin1)
	{
		onlogin = onlogin1;
	}

}
// 2131296256