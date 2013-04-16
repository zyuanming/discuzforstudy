package net.discuz.source.prototype.extend;

import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.activity.siteview.siteview_forumviewthread;
import net.discuz.adapter.MyFavThreadListAdapter;
import net.discuz.app.DiscuzApp;
import net.discuz.init.InitSetting;
import net.discuz.json.JsonParser;
import net.discuz.source.DJsonReader;
import net.discuz.source.ShowMessage;
import net.discuz.source.InterFace.JsonParseHelperCallBack;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.sub_pulltorefresh_listview_prototype;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.HttpConnThread;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.umeng.analytics.MobclickAgent;

public class siteview_forumindex_myfavthread extends
		sub_pulltorefresh_listview_prototype
{

	public siteview_forumindex_myfavthread(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		listadapter = null;
		myFavThreadList = null;
		pageSize = 20;
		filter = getClass().getName();
		listadapter = new MyFavThreadListAdapter(context, null);
		listview.setAdapter(listadapter);
		httpConnThread = new HttpConnThread(context.siteAppId, 1);
	}

	private void _clearAdapter()
	{
		handler.post(new Runnable()
		{

			public void run()
			{
				listadapter._setList(null);
				listadapter.notifyDataSetChanged();
			}
		});
	}

	private void updataUI()
	{
		context.dismissLoading();
		if (isPullDownrefresh)
		{
			isPullDownrefresh = false;
			mpulltorefresh.loadedReturnOnAsyncTask();
			ShowMessage.getInstance(context)._showToast(
					R.string.message_fresh_succes, 1);
		}
		if (myFavThreadList == null || myFavThreadList != null
				&& myFavThreadList.size() == 0)
		{
			myFavThreadList = new ArrayList();
			_clearAdapter();
			errorMessage = context.getResources().getString(
					R.string.error_no_favorthread);
			setListFooter(-301);
			return;
		}
		listadapter._setList(myFavThreadList);
		listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
		{

			public void onItemClick(AdapterView adapterview, View view, int i,
					long l)
			{
				MobclickAgent.onEvent(context, "c_trd_fav");
				DiscuzStats.add(context.siteAppId, "c_trd_fav");
				HashMap hashmap = listadapter.getItem(i);
				DiscuzApp.getInstance().setIsReadThread(
						(String) hashmap.get("id"));
				listadapter.notifyDataSetChanged();
				Intent intent = new Intent();
				intent.putExtra("siteappid", context.siteAppId);
				intent.putExtra("tid", (String) hashmap.get("id"));
				intent.putExtra(
						"forumname",
						context.getResources().getString(
								R.string.switcher_myfavthread));
				intent.setClass(context, siteview_forumviewthread.class);
				context.startActivity(intent);
			}
		});
		if (isNextPage)
		{
			isNextPage = false;
			setListFooter(-102);
			return;
		} else
		{
			setListFooter(-101);
			return;
		}
	}

	public void loadMore()
	{
		super.loadMore();
		isLoadMore = true;
		page = 1 + page;
		newList();
	}

	public void newList()
	{
		int i = DiscuzApp.getInstance().getLoginUser(context.siteAppId)
				.getUid();
		if (i < 1)
		{
			uid = i;
			_clearAdapter();
			setListFooter(-501);
			return;
		}
		httpConnThread
				.setCachePath((new StringBuilder()).append("cache/")
						.append(InitSetting._getUserPath(context.siteAppId))
						.toString());
		httpConnThread.setFilter(filter);
		String as[];
		if (uid != (long) i)
		{
			httpConnThread.setCacheType(1);
			uid = i;
			myFavThreadList = null;
			_clearAdapter();
			setListFooter(-101);
			page = 1;
			context.showLoading(null);
		} else if (isShowingLoding)
		{
			httpConnThread.setCacheType(1);
			myFavThreadList = null;
			isShowingLoding = false;
			isPullDownrefresh = true;
			_clearAdapter();
			setListFooter(-101);
			context.showLoading(null);
		} else if (isLoadMore || isPullDownrefresh)
			httpConnThread.setCacheType(1);
		else if (myFavThreadList != null)
		{
			mpulltorefresh.isLoading = false;
			httpConnThread.setCacheType(0);
		} else
		{
			httpConnThread.setCacheType(1);
			_clearAdapter();
			setListFooter(-101);
			context.showLoading(null);
		}
		as = new String[3];
		as[0] = context.siteAppId;
		as[1] = "module=myfavthread";
		as[2] = (new StringBuilder()).append("page=").append(page).toString();
		url = Core.getSiteUrl(as);
		httpConnThread.setUrl(url);
		DiscuzApp.getInstance().setHttpConnListener(filter,
				new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
				{

					public void onFailed(Exception exception)
					{
						manageException(exception, listadapter);
					}

					public void onSucceed(String s, String s1)
					{
						try
						{
							DJsonReader localDJsonReader = new DJsonReader(s1);
							localDJsonReader._jsonParse();
							localDJsonReader._debug();
							new JsonParser(
									siteview_forumindex_myfavthread.this.context)
									.myFavThread(
											localDJsonReader._getVariables(),
											new JsonParseHelperCallBack()
											{
												public void onParseBegin()
												{}

												public void onParseFinish(
														ArrayList<HashMap<String, String>> paramAnonymous2ArrayList)
												{
													if (siteview_forumindex_myfavthread.this.isLoadMore)
													{
														if (paramAnonymous2ArrayList
																.size() == 0)
															siteview_forumindex_myfavthread.this.context
																	.ShowMessageByHandler(
																			2131165292,
																			2);
													} else if (paramAnonymous2ArrayList
															.size() >= siteview_forumindex_myfavthread.this.pageSize)
													{
														siteview_forumindex_myfavthread.this.myFavThreadList
																.addAll(paramAnonymous2ArrayList);
													}
												}

												@Override
												public void onParseFinish(
														Object obj)
												{
													onParseFinish((ArrayList) obj);
												}
											});
							updataUI();
						} catch (Exception exception)
						{
							exception.printStackTrace();
							manageException(exception, listadapter);
						}
					}
				});
		DiscuzApp.getInstance().sendHttpConnThread(httpConnThread);
	}

	public void onDestroy()
	{
		super.onDestroy();
		DiscuzApp.getInstance().removeHttpConnListener(filter);
	}

	public void update()
	{
		isPullDownrefresh = true;
		myFavThreadList = null;
		page = 1;
		newList();
	}

	private String filter;
	private HttpConnThread httpConnThread;
	private MyFavThreadListAdapter listadapter;
	private ArrayList myFavThreadList;
	private int pageSize;

}
// 2131296256