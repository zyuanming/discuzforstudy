package net.discuz.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.discuz.activity.siteview.noticedetaillist;
import net.discuz.app.DiscuzApp;
import net.discuz.model.LoginInfo;
import net.discuz.model.NoticeDetail;
import net.discuz.model.SiteInfo;
import net.discuz.source.DEBUG;
import net.discuz.source.storage.GlobalDBHelper;
import net.discuz.source.storage.NoticeDetailDBHelper;
import net.discuz.source.storage.SiteInfoDBHelper;
import net.discuz.source.storage.UserSessionDBHelper;
import net.discuz.source.timelooper.TimeLooper;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class NoticeCenter
{
	public static class NoticeHttpConnListener implements
			net.discuz.boardcast.HttpConnReceiver.HttpConnListener
	{

		public void onFailed(Exception exception)
		{
			removeHttpConnListener();
		}

		public void onSucceed(String s, String s1)
		{}

		public void removeHttpConnListener()
		{
			NoticeCenter.app.removeHttpConnListener(filter);
		}

		private String filter;

		public NoticeHttpConnListener(String s)
		{
			filter = null;
			filter = (new StringBuilder()).append(getClass().getSimpleName())
					.append(s).toString();
		}
	}

	public NoticeCenter()
	{}

	public static void addToken()
	{
		addToken(null);
	}

	public static void addToken(final String s)
	{
		Log.d("NoticeCenter", "addToken() -----> Enter");
		String s1 = GlobalDBHelper.getInstance().getValue("addToken");
		if (Tools.stringIsNullOrEmpty(s) && !Tools.stringIsNullOrEmpty(s1)
				&& Long.valueOf(s1).longValue() == 0L)
			return;
		NoticeHttpConnListener noticehttpconnlistener = new NoticeHttpConnListener(
				"addtoken")
		{

			public void onSucceed(String s3, String s4)
			{
				if (!Tools.stringIsNullOrEmpty(s3) && s3.contains("code"))
				{
					try
					{
						String s5 = (new JSONObject(s3)).optString("code");
						DEBUG.o((new StringBuilder())
								.append("=======add Token=====").append(s5)
								.append(" ")
								.append((new JSONObject(s3)).optString("msg"))
								.toString());
						if ("0".equals(s5))
							if (Tools.stringIsNullOrEmpty(s))
							{
								GlobalDBHelper.getInstance().replace(
										"addToken", s5);
							} else
							{
								SiteInfo siteinfo = SiteInfoDBHelper
										.getInstance().getByCloudId(
												Integer.valueOf(s).intValue());
								if (siteinfo != null)

									siteinfo.setIsNotify("1");
								SiteInfoDBHelper.getInstance()
										.updateSiteByAppId(
												siteinfo.getSiteAppid(),
												siteinfo);

							}
					} catch (Exception exception)
					{
						exception.printStackTrace();
					}
				}
				super.removeHttpConnListener();
				return;
			}
		};
		StringBuilder stringbuilder = (new StringBuilder())
				.append("user/addToken?");
		Core core1 = core;
		String as[] = new String[2];
		as[0] = "osType=and";
		String s2;
		if (Tools.stringIsNullOrEmpty(s))
			s2 = "";
		else
			s2 = (new StringBuilder()).append("sId=").append(s).toString();
		as[1] = s2;
		createHttpThread("addtoken", noticehttpconnlistener, stringbuilder
				.append(core1._getParamsSig(as)).toString());
		Log.d("NoticeCenter", "addToken() -----> Exit");
	}

	public static void check()
	{
		Log.d("NoticeCenter", "check() ----> Enter");
		if (getNoticeSwitch())
			createHttpThread(
					"check",
					new NoticeHttpConnListener("check")
					{

						public void onSucceed(String s, String s1)
						{
							try
							{
								if (Tools.stringIsNullOrEmpty(s)
										|| !s.contains("code")
										|| !s.contains("res"))
								{
									TimeLooper.startLoop(Integer.valueOf(300)
											.intValue());
								} else
								{
									JSONObject jsonobject;
									jsonobject = new JSONObject(s);
									DEBUG.o((new StringBuilder())
											.append("=====checknoticenumber=====")
											.append(jsonobject).toString());
									if ("0".equals(jsonobject.optString("code")))
									{
										JSONObject jsonobject1 = jsonobject
												.getJSONObject("res");
										if (jsonobject1 != null)
										{
											String s2;
											s2 = jsonobject1.optString("ttl");
											String s3 = jsonobject1
													.optString("totalNum");
											if (!Tools.stringIsNullOrEmpty(s3)
													&& Integer.valueOf(s3)
															.intValue() > 0)
											{
												GlobalDBHelper.getInstance()
														.replace(
																"noticenumber",
																s3);
												NoticeCenter.list();
											}
											DEBUG.o((new StringBuilder())
													.append("=====checknoticenumber==result===")
													.append(s3).append("  ")
													.append(s2).toString());
											if (Tools.stringIsNullOrEmpty(s2)
													|| Integer.valueOf(s2)
															.intValue() <= 0)
											{

												TimeLooper.startLoop(Integer
														.valueOf(300)
														.intValue());

											} else
											{
												TimeLooper
														.startLoop(Integer
																.valueOf(s2)
																.intValue());
												GlobalDBHelper.getInstance()
														.replace("noticettl",
																s2);
											}
										}
									}
								}
							} catch (Exception e)
							{
								e.printStackTrace();
							}
							super.removeHttpConnListener();
							return;
						}
					},
					(new StringBuilder())
							.append("notification/check?")
							.append(core
									._getParamsSig(new String[] { "osType=and" }))
							.toString());
		Log.d("NoticeCenter", "check() ----> Exit");
	}

	public static void clearToken(final String s)
	{
		NoticeHttpConnListener noticehttpconnlistener = new NoticeHttpConnListener(
				"clearToken")
		{

			public void onSucceed(String s2, String s3)
			{
				if (!Tools.stringIsNullOrEmpty(s2) && s2.contains("code"))
				{
					try
					{
						SiteInfo siteinfo;
						String s4 = (new JSONObject(s2)).optString("code");
						DEBUG.o((new StringBuilder())
								.append("=====cleartoken=====").append(s4)
								.toString());
						if ("0".equals(s4))
						{
							siteinfo = SiteInfoDBHelper
									.getInstance()
									.getByCloudId(Integer.valueOf(s).intValue());
							if (siteinfo != null)

								siteinfo.setIsNotify("0");
							SiteInfoDBHelper.getInstance().updateSiteByAppId(
									siteinfo.getSiteAppid(), siteinfo);

						}
					} catch (Exception exception)
					{}
				}
				super.removeHttpConnListener();
				return;
			}
		};
		StringBuilder stringbuilder = (new StringBuilder())
				.append("user/clearToken?");
		Core core1 = core;
		String as[] = new String[2];
		as[0] = "osType=and";
		String s1;
		if (Tools.stringIsNullOrEmpty(s))
			s1 = "";
		else
			s1 = (new StringBuilder()).append("sId=").append(s).toString();
		as[1] = s1;
		createHttpThread("clearToken", noticehttpconnlistener, stringbuilder
				.append(core1._getParamsSig(as)).toString());
	}

	public static void createHttpThread(
			String s,
			net.discuz.boardcast.HttpConnReceiver.HttpConnListener httpconnlistener,
			String s1)
	{
		Log.d("NoticeCenter", "createHttpThread() -----> Enter");
		HttpConnThread httpconnthread = new HttpConnThread(null, 0);
		if (core != null)
		{
			DEBUG.o((new StringBuilder()).append("=====").append(s)
					.append("== url===")
					.append("http://m.discuz.qq.com/mobile/").append(s1)
					.toString());
			httpconnthread.setUrl((new StringBuilder())
					.append("http://m.discuz.qq.com/mobile/").append(s1)
					.toString());
			httpconnthread.setCacheType(-1);
			httpconnthread.setFilter(s);
			app.setHttpConnListener(s, httpconnlistener);
			app.sendHttpConnThread(httpconnthread);
			Log.d("NoticeCenter", "createHttpThread() -----> Exit");
		}
	}

	public static void detail(final String s,
			final noticedetaillist noticedetaillist1)
	{
		DEBUG.o((new StringBuilder())
				.append("=====notifiycenter=detail===cloudid====").append(s)
				.toString());
		if (getNoticeSwitch())
		{
			if (noticedetaillist1 != null)
				noticedetaillist1.showLoading("正在加载数据....");
			String s1 = GlobalDBHelper.getInstance().getValue(
					"noticedetail_nextstartid");
			if (Tools.stringIsNullOrEmpty(s1))
				s1 = "0";
			String s2 = (new StringBuilder()).append("startId=").append(s1)
					.toString();
			final NoticeDetailDBHelper noticedetaildbhelper = NoticeDetailDBHelper
					.getInstance();
			noticedetaildbhelper.update(s);
			NoticeHttpConnListener noticehttpconnlistener = new NoticeHttpConnListener(
					"detail")
			{

				public void onSucceed(String s3, String s4)
				{
					if (!Tools.stringIsNullOrEmpty(s3) && s3.contains("code")
							&& s3.contains("res") && s3.contains("list"))
					{
						try
						{
							JSONObject jsonobject1 = null;
							JSONArray jsonarray;
							JSONObject jsonobject = new JSONObject(s3);
							DEBUG.o((new StringBuilder())
									.append("=====notifiycenter=detail====")
									.append(jsonobject).toString());
							ArrayList arraylist = null;
							if ("0".equals(jsonobject.optString("code")))
							{
								jsonobject1 = jsonobject.getJSONObject("res");
								GlobalDBHelper.getInstance().replace(
										"noticedetail_nextstartid",
										jsonobject1.optString("nextStartId"));
								DEBUG.o((new StringBuilder())
										.append("======noticecenter=detail=nextstartid=result===")
										.append(jsonobject1
												.optString("nextStartId"))
										.toString());
								jsonarray = jsonobject1.getJSONArray("list");
								int j = 1;
								SiteInfo siteinfo;
								List list1;

								siteinfo = SiteInfoDBHelper.getInstance()
										.getByCloudId(
												Integer.valueOf(s).intValue());
								list1 = (List) SiteInfoDBHelper.getInstance()
										.getByCloudId(
												Integer.valueOf(s).intValue());
								arraylist = new ArrayList();
								if (siteinfo != null)
								{
									if (!Tools.stringIsNullOrEmpty(siteinfo
											.getSiteAppid()))
									{
										NoticeDetail noticedetail1 = null;
										NoticeDetail noticedetail = null;
										for (int i = 0; i < jsonarray.length(); i++)
										{

											noticedetail = NoticeDetail
													._initValue(jsonarray
															.getJSONObject(i),
															siteinfo);
											if (list1 != null)
											{
												Iterator iterator = list1
														.iterator();

												do
												{
													if (iterator.hasNext())
														noticedetail1 = (NoticeDetail) iterator
																.next();
												} while (!noticedetail.dateline
														.equals(noticedetail1.dateline)
														|| !noticedetail.subject
																.equals(noticedetail1.subject));
												noticedetail.readed = noticedetail1.readed;
											}
										}
										noticedetaildbhelper
												.insert(noticedetail);
										arraylist.add(noticedetail);
									}
								}
							}

							noticedetaillist1.setDetailList(arraylist,
									jsonobject1.optString("nextStartId"));
							super.removeHttpConnListener();
						} catch (Exception exception)
						{
							DEBUG.o((new StringBuilder())
									.append("========noticedetailwrong=====")
									.append(exception.getMessage()).toString());
						}
					} else
					{
						noticedetaillist1.setDetailList(null, null);
					}
					super.removeHttpConnListener();
					return;
				}
			};
			StringBuilder stringbuilder = (new StringBuilder())
					.append("notification/detail?");
			Core core1 = core;
			String as[] = new String[4];
			as[0] = "osType=and";
			as[1] = s2;
			as[2] = (new StringBuilder()).append("sId=").append(s).toString();
			as[3] = "num=5";
			createHttpThread("detail", noticehttpconnlistener, stringbuilder
					.append(core1._getParamsSig(as)).toString());
		}
	}

	public static boolean getNoticeSwitch()
	{
		String s = GlobalDBHelper.getInstance().getValue("noticetoggle");
		return Tools.stringIsNullOrEmpty(s) || s.equals("1");
	}

	public static void list()
	{

	}

	public static void loginToken(final String s)
	{
		LoginInfo logininfo = UserSessionDBHelper.getInstance().getByAppId(s);
		SiteInfo siteinfo;
		if (logininfo != null)
			if ((siteinfo = SiteInfoDBHelper.getInstance().getByAppId(s)) != null
					&& !Tools.stringIsNullOrEmpty(siteinfo.getCloudId())
					&& siteinfo.getIsNotify().equals("1"))
			{
				String s1 = siteinfo.getCloudId();
				NoticeHttpConnListener noticehttpconnlistener = new NoticeHttpConnListener(
						"logintoken")
				{
					public void onSucceed(String s2, String s3)
					{
						if (!Tools.stringIsNullOrEmpty(s2)
								&& s2.contains("code"))
						{
							try
							{
								UserSessionDBHelper usersessiondbhelper;
								LoginInfo logininfo1;
								String s4 = (new JSONObject(s2))
										.optString("code");
								DEBUG.o((new StringBuilder())
										.append("======loginToken===")
										.append(s4)
										.append(" ")
										.append((new JSONObject(s2))
												.optString("msg")).toString());
								if ("0".equals(s4))
								{
									usersessiondbhelper = UserSessionDBHelper
											.getInstance();
									logininfo1 = usersessiondbhelper
											.getByAppId(s);
									if (logininfo1 != null)
										try
										{
											logininfo1.setLoginToken("1");
											usersessiondbhelper.update(
													logininfo1, s);
										} catch (Exception exception)
										{}
								}
							} catch (Exception e)
							{
								e.printStackTrace();
							}
						}
						super.removeHttpConnListener();
						return;
					}
				};
				StringBuilder stringbuilder = (new StringBuilder())
						.append("user/loginToken?");
				Core core1 = core;
				String as[] = new String[3];
				as[0] = "osType=and";
				as[1] = (new StringBuilder()).append("sId=").append(s1)
						.toString();
				as[2] = (new StringBuilder()).append("sSiteUid=")
						.append(logininfo.getUid()).toString();
				createHttpThread("logintoken", noticehttpconnlistener,
						stringbuilder.append(core1._getParamsSig(as))
								.toString());
				return;
			}
	}

	public static void logoutToken(final String s)
	{
		LoginInfo logininfo = UserSessionDBHelper.getInstance().getByAppId(s);
		SiteInfo siteinfo;
		if (logininfo != null)
			if ((siteinfo = SiteInfoDBHelper.getInstance().getByAppId(s)) != null
					&& !Tools.stringIsNullOrEmpty(siteinfo.getCloudId())
					&& siteinfo.getIsNotify().equals("1"))
			{
				String s1 = siteinfo.getCloudId();
				NoticeHttpConnListener noticehttpconnlistener = new NoticeHttpConnListener(
						"logoutToken")
				{

					public void onSucceed(String s2, String s3)
					{
						try
						{
							if (!Tools.stringIsNullOrEmpty(s2)
									&& s2.contains("code"))
							{
								UserSessionDBHelper usersessiondbhelper;
								LoginInfo logininfo1;
								String s4 = (new JSONObject(s2))
										.optString("code");
								DEBUG.o((new StringBuilder())
										.append("=====logoutToken==")
										.append(s4)
										.append(" ")
										.append((new JSONObject(s2))
												.optString("msg")).toString());
								if ("0".equals(s4))
								{
									usersessiondbhelper = UserSessionDBHelper
											.getInstance();
									logininfo1 = usersessiondbhelper
											.getByAppId(s);
									if (logininfo1 != null)
										try
										{
											logininfo1.setLoginToken("0");
											usersessiondbhelper.update(
													logininfo1, s);
										} catch (Exception exception)
										{}
								}
							}
						} catch (Exception e)
						{
							e.printStackTrace();
						}
						super.removeHttpConnListener();
						return;
					}
				};
				StringBuilder stringbuilder = (new StringBuilder())
						.append("user/logoutToken?");
				Core core1 = core;
				String as[] = new String[3];
				as[0] = "osType=and";
				as[1] = (new StringBuilder()).append("sId=").append(s1)
						.toString();
				as[2] = (new StringBuilder()).append("sSiteUid=")
						.append(logininfo.getUid()).toString();
				createHttpThread("logoutToken", noticehttpconnlistener,
						stringbuilder.append(core1._getParamsSig(as))
								.toString());
				return;
			}
	}

	public static void setNoticeSwitch(boolean flag)
	{
		GlobalDBHelper globaldbhelper = GlobalDBHelper.getInstance();
		String s;
		if (flag)
			s = "1";
		else
			s = "0";
		globaldbhelper.replace("noticetoggle", s);
		if (flag)
		{
			DiscuzApp.getInstance().startTimeLooper(10);
			return;
		} else
		{
			TimeLooper.endLoop();
			return;
		}
	}

	private static DiscuzApp app = DiscuzApp.getInstance();
	private static Core core = Core.getInstance();
	public static final boolean falsedata = false;
	public static boolean isMainActivityRunning = false;
	private static final String noticeurl = "http://m.discuz.qq.com/mobile/";
	private static final String osType = "osType=and";

	static
	{
		isMainActivityRunning = false;
	}

}
// 2131296256