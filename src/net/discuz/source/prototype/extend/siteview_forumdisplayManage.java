package net.discuz.source.prototype.extend;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.activity.siteview.ForumViewActivity;
import net.discuz.activity.siteview.siteview_forumviewthread;
import net.discuz.adapter.ForumIndexFidAdapter;
import net.discuz.adapter.ThreadListAdapter;
import net.discuz.app.DiscuzApp;
import net.discuz.boardcast.HttpConnReceiver;
import net.discuz.boardcast.HttpConnReceiver.HttpConnListener;
import net.discuz.json.JsonParser;
import net.discuz.model.AllowPerm;
import net.discuz.model.ForumDisplayData;
import net.discuz.source.DJsonReader;
import net.discuz.source.LimitsException;
import net.discuz.source.ShowMessage;
import net.discuz.source.InterFace.InterfaceDisplayInputSub;
import net.discuz.source.InterFace.JsonParseHelperCallBack;
import net.discuz.source.popupwindow.InputPassword;
import net.discuz.source.prototype.sub_pulltorefresh_listview_prototype;
import net.discuz.source.storage.GlobalDBHelper;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.HttpConnThread;
import net.discuz.tools.Tools;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.umeng.analytics.MobclickAgent;

public class siteview_forumdisplayManage extends
		sub_pulltorefresh_listview_prototype
{

	private ThreadListAdapter adapter;
	private AllowPerm allowPerm;
	private int default_tpp_params;
	private final DecimalFormat df = new DecimalFormat("#####.0");
	private DJsonReader djson;
	public ArrayList forumdisplay_threadlist;
	private String forumname;
	private HttpConnListener httpConnListener;
	private HttpConnThread httpConnThread;
	private InterfaceDisplayInputSub intrefaceInputSub;
	private boolean isInputPassword;
	private boolean isPassword;
	private boolean isShowIpnutPassword;
	private int loadingWhat;
	private int new_data_size;
	private HashMap paramsMap;
	private String password;
	private ForumIndexFidAdapter subAdapter;
	public ArrayList subForumList;

	public siteview_forumdisplayManage(ForumViewActivity forumviewactivity,
			HashMap hashmap)
	{
		super(forumviewactivity);
		paramsMap = hashmap;
		djson = null;
		loadingWhat = 1;
		default_tpp_params = 20;
		intrefaceInputSub = null;
		password = null;
		allowPerm = null;
		isPassword = false;
		isInputPassword = false;
		isShowIpnutPassword = true;
		forumname = null;
		forumdisplay_threadlist = null;
		subForumList = null;
		adapter = new ThreadListAdapter(context, null);
		subAdapter = new ForumIndexFidAdapter(context);
		httpConnListener = new HttpConnReceiver.HttpConnListener()
		{
			public void onFailed(Exception exception)
			{
				manageException(exception, adapter);
			}

			public void onSucceed(String s, String s1)
			{
				djson = new DJsonReader(s);
				try
				{
					djson._jsonParse();
					djson._debug();
					if (djson._getMessage() != null
							&& djson._getMessage()[1] != null
							&& !"".equals(djson._getMessage()[1]))
						throw new LimitsException(djson._getMessage()[1]);

					(new JsonParser(context)).forumDisplay(
							djson._getVariables(),
							new JsonParseHelperCallBack()
							{
								public void onParseBegin()
								{}

								public void onParseFinish(Object obj)
								{
									onParseFinish((ForumDisplayData) obj);
								}

								public void onParseFinish(
										ForumDisplayData paramAnonymous2ForumDisplayData)
								{
									if (paramAnonymous2ForumDisplayData != null)
									{
										if (("1".equals(paramAnonymous2ForumDisplayData
												.getForumData().get("password"))))
											isPassword = true;
										else
											isPassword = false;
										ArrayList localArrayList;
										boolean bool;
										forumname = (String) paramAnonymous2ForumDisplayData
												.getForumData().get("name");
										localArrayList = paramAnonymous2ForumDisplayData
												.getThreadList();
										int i = localArrayList.size();
										for (int j = 0; j < i; j++)
										{
											HashMap localHashMap2 = (HashMap) localArrayList
													.get(j);
											float f1 = Float
													.parseFloat((String) localHashMap2
															.get("replies"));
											String str3;
											String str4;
											float f2;
											if (f1 > 10000.0F)
											{
												str3 = df.format(f1 / 10000.0F)
														+ "万";

											} else
											{
												str3 = (int) f1 + "";
											}
											localHashMap2.put("replies", str3);
											f2 = Float
													.parseFloat((String) localHashMap2
															.get("views"));
											if (f2 > 10000.0F)
											{
												str4 = df.format(f2 / 10000.0F)
														+ "万";
											} else
											{
												str4 = (int) f2 + "";
											}
											localHashMap2.put("views", str4);

										}
										forumdisplay_threadlist = localArrayList;
										if (isLoadMore)
										{
											new_data_size = localArrayList
													.size();
											forumdisplay_threadlist
													.addAll(localArrayList);
										}
										allowPerm = paramAnonymous2ForumDisplayData
												.getAllowPerm();
										siteview_forumdisplayManage localsiteview_forumdisplayManage = siteview_forumdisplayManage.this;
										if (forumdisplay_threadlist.size() >= Integer
												.valueOf(
														(String) paramAnonymous2ForumDisplayData
																.getForumData()
																.get("threads"))
												.intValue())
											isPullDownrefresh = false;
										bool = true;
										isNextPage = bool;
										subForumList = paramAnonymous2ForumDisplayData
												.getSubList();
										if (subForumList.size() > 0)
										{
											int k = subForumList.size();
											for (int m = 0; m < k; m++)
											{
												HashMap localHashMap1 = (HashMap) subForumList
														.get(m);
												double d1 = Double
														.parseDouble((String) localHashMap1
																.get("threads"));
												String str1;
												String str2;
												double d2;
												if (d1 > 10000.0D)
												{
													str1 = df
															.format(d1 / 10000.0D)
															+ "万";

												} else
												{
													str1 = (int) d1 + "";
												}
												localHashMap1.put("threads",
														str1);
												d2 = Double
														.parseDouble((String) localHashMap1
																.get("posts"));
												if (d1 > 10000.0D)
												{
													str2 = df
															.format(d2 / 10000.0D)
															+ "万";
												} else
												{
													str2 = (int) d2 + "";
												}
												localHashMap1
														.put("posts", str2);
											}
										}
										ForumViewActivity localForumViewActivity = (ForumViewActivity) context;
										localForumViewActivity
												.addSubForum(true);
										if (forumdisplay_threadlist.size() == 0)
										{
											loadingWhat = 2;
											localForumViewActivity
													.addSubForum(false);
										}

										bool = false;
										if (isPullDownrefresh)
										{
											mpulltorefresh
													.loadedReturnOnAsyncTask();
											isPullDownrefresh = false;
											ShowMessage
													.getInstance(context)
													._showToast(
															R.string.message_fresh_succes,
															1);
										}
									}
									updateUI();
								}
							});
				} catch (Exception exception)
				{
					manageException(exception, adapter);
					exception.printStackTrace();
				}
				context.dismissLoading();
				return;
			}
		};
	}

	class ClickItemListener implements
			android.widget.AdapterView.OnItemClickListener
	{

		public void onItemClick(AdapterView adapterview, View view, int i,
				long l)
		{
			switch (siteview_forumdisplayManage.this.loadingWhat)
			{
			case 1:
				MobclickAgent.onEvent(context, "v_toptrd");
				DiscuzStats.add(context.siteAppId, "v_toptrd");
				if (adapter.getCount() > 0)
				{
					String s = (String) adapter.getItem(i).get("tid");
					Intent intent = new Intent();
					intent.putExtra("tid", s);
					intent.putExtra("siteappid", context.siteAppId);
					intent.putExtra("forumname", forumname);
					intent.setClass(context, siteview_forumviewthread.class);
					context.startActivity(intent);
					DiscuzApp.getInstance().setIsReadThread(s);
					adapter.notifyDataSetChanged();
					return;
				}
				break;
			case 2:
				MobclickAgent.onEvent(context, "v_subfrm");
				DiscuzStats.add(context.siteAppId, "v_subfrm");
				if (subAdapter.getFidList().size() > 0)
				{
					String s1 = (String) ((HashMap) subAdapter.getFidList()
							.get(i)).get("fid");
					Intent intent1 = new Intent();
					intent1.putExtra("fid", s1);
					intent1.putExtra("siteappid", context.siteAppId);
					intent1.putExtra("forumname",
							(String) ((HashMap) subAdapter.getFidList().get(i))
									.get("name"));
					intent1.setClass(context, context.getClass());
					context.startActivity(intent1);
					DiscuzApp.getInstance().setIsReadForum(s1);
					subAdapter.notifyDataSetChanged();
					return;
				}
				break;
			case 3:
				MobclickAgent.onEvent(context, "v_toptrd");
				DiscuzStats.add(context.siteAppId, "v_toptrd");
				break;
			default:
			}
		}

		ClickItemListener()
		{
			super();
		}
	}

	private void _clearAdapter()
	{
		handler.post(new Runnable()
		{

			public void run()
			{
				adapter._setList(null);
				adapter.notifyDataSetChanged();
				subAdapter.setFidList(null);
				subAdapter.notifyDataSetChanged();
			}
		});
	}

	private void _setUrl(String s)
	{
		String as[] = new String[7];
		as[0] = context.siteAppId;
		as[1] = "module=forumdisplay";
		as[2] = "submodule=checkpost";
		as[3] = (new StringBuilder()).append("fid=")
				.append((String) paramsMap.get("fid")).toString();
		StringBuilder stringbuilder = (new StringBuilder()).append("page=");
		Object obj;
		String s1;
		if (paramsMap.get("page") == null)
			obj = Integer.valueOf(1);
		else
			obj = (Serializable) paramsMap.get("page");
		as[4] = stringbuilder.append(obj).toString();
		as[5] = (new StringBuilder()).append("tpp=").append(default_tpp_params)
				.toString();
		as[6] = s;
		url = Core.getSiteUrl(as);
		s1 = GlobalDBHelper.getInstance().getValue("thread_sort");
		if (Tools.stringIsNullOrEmpty(s1) || s1.equals("dateline_sort"))
		{
			StringBuilder stringbuilder1 = (new StringBuilder()).append(url);
			String s2;
			if (url.endsWith("&"))
				s2 = "orderby=dateline";
			else
				s2 = "&orderby=dateline";
			url = stringbuilder1.append(s2).toString();
		}
	}

	public AllowPerm getAllowPerm()
	{
		return allowPerm;
	}

	public HashMap getParamsMap()
	{
		return paramsMap;
	}

	public void loadMore()
	{
		loadMore();
		MobclickAgent.onEvent(context, "c_moretrd");
		DiscuzStats.add(context.siteAppId, "c_moretrd");
		isLoadMore = true;
		paramsMap.put("page", String.valueOf(1L + Long.valueOf(
				(String) paramsMap.get("page")).longValue()));
		newList();
	}

	public void newList()
	{
		_setUrl("");
		httpConnThread = new HttpConnThread(context.siteAppId, 1);
		httpConnThread.setUrl(url);
		httpConnThread.setCachePath("_t/");
		String s = getClass().getSimpleName();
		httpConnThread.setFilter(s);
		DiscuzApp.getInstance().setHttpConnListener(s, httpConnListener);
		if (!isLoadMore && !isPullDownrefresh)
			if (isShowingLoding)
			{
				isPullDownrefresh = true;
				isShowingLoding = false;
				context.showLoading(null);
				_clearAdapter();
				setListFooter(-101);
			} else
			{
				context.showLoading(null);
				_clearAdapter();
			}
		mpulltorefresh.isLoading = true;
		httpConnThread.setCacheType(1);
		DiscuzApp.getInstance().sendHttpConnThread(httpConnThread);
	}

	public void setLoadingWhat(int i)
	{
		loadingWhat = i;
	}

	public void setParamsMap(HashMap hashmap)
	{
		paramsMap = hashmap;
	}

	public void update()
	{
		isPullDownrefresh = true;
		paramsMap.put("page", "1");
		newList();
	}

	public void updateUI()
	{
		if (forumname == null)
			;
		if (isInputPassword)
		{
			isInputPassword = false;
			if (djson._getMessage() != null && djson._getMessage().length == 2)
			{
				if ("forum_passwd_correct".equals(djson._getMessage()[0]))
				{
					loadingWhat = 1;
					DiscuzApp
							.getInstance()
							.getLoginUser(context.siteAppId)
							.setLoginCookie(
									"fidpw",
									(new StringBuilder())
											.append(DiscuzApp
													.getInstance()
													.getLoginUser(
															context.siteAppId)
													.getCookiepre())
											.append("fidpw")
											.append((String) paramsMap
													.get("fid")).append("=")
											.append(password).toString());
					newList();
					isShowIpnutPassword = false;
					return;
				} else
				{
					loadingWhat = 1;
					ShowMessage.getInstance(context)._showToast(
							R.string.message_display_input_password_error, 3);
					(new InputPassword(context))
							.setInterface(intrefaceInputSub);
					return;
				}
			} else
			{
				(new InputPassword(context)).setInterface(intrefaceInputSub);
				return;
			}
		}
		if (isPassword && isShowIpnutPassword)
		{
			intrefaceInputSub = new InterfaceDisplayInputSub()
			{

				public void interfaceinputSub(String s)
				{
					password = s;
					loadingWhat = 3;
					_setUrl((new StringBuilder()).append("pw=").append(s)
							.toString());
					httpConnThread.setUrl(url);
					DiscuzApp.getInstance().sendHttpConnThread(httpConnThread);
					isInputPassword = true;
				}
			};
			(new InputPassword(context)).setInterface(intrefaceInputSub);
			adapter._setList(null);
			listview.setAdapter(adapter);
			errorMessage = context.getResources().getString(
					R.string.error_no_null_data);
			setListFooter(-502);
			return;
		}
		if (djson != null
				&& djson._getMessage()[0] != null
				&& ("custom//1".equals(djson._getMessage()[0]) || "viewperm_login_nopermission//1"
						.equals(djson._getMessage()[0])))
		{
			adapter._setList(null);
			listview.setAdapter(adapter);
			errorMessage = djson._getMessage()[1];
			setListFooter(-501);
			return;
		}
		if (djson != null
				&& djson._getMessage()[0] != null
				&& "viewperm_upgrade_nopermission"
						.equals(djson._getMessage()[0]))
		{
			adapter._setList(null);
			listview.setAdapter(adapter);
			errorMessage = context.getResources().getString(
					R.string.message_display_limits);
			setListFooter(-502);
			return;
		}
		switch (loadingWhat)
		{
		default:
			return;

		case 1: // '\001'
			if (forumdisplay_threadlist == null
					|| forumdisplay_threadlist.size() == 0)
			{
				adapter._setList(null);
				listview.setAdapter(adapter);
				errorMessage = context.getResources().getString(
						R.string.error_no_themethread);
				setListFooter(-301);
				return;
			}
			if (isNextPage)
				setListFooter(-102, 0, adapter);
			else
				setListFooter(-101);
			adapter._setList(forumdisplay_threadlist);
			if (!isLoadMore)
				listview.setAdapter(adapter);
			isLoadMore = false;
			listview.setOnItemClickListener(new ClickItemListener());
			if (new_data_size == 0)
			{
				listview.setSelection(0);
				return;
			} else
			{
				listview.setSelection(forumdisplay_threadlist.size()
						- new_data_size);
				return;
			}

		case 2: // '\002'
			break;
		}
		if (subForumList.size() == 0)
		{
			subAdapter.setFidList(null);
			subAdapter.notifyDataSetChanged();
			listview.setAdapter(subAdapter);
			errorMessage = context.getResources().getString(
					R.string.error_no_subthread);
			setListFooter(-301);
			return;
		} else
		{
			setListFooter(-101, 0, subAdapter);
			subAdapter.setFidList(subForumList);
			listview.setAdapter(subAdapter);
			listview.setOnItemClickListener(new ClickItemListener());
			listview.setSelection(subForumList.size() - default_tpp_params);
			return;
		}
	}

	public void update_by_login()
	{
		isPullDownrefresh = true;
		paramsMap.put("page", "1");
		newList();
	}
}
// 2131296256