package net.discuz.source.prototype.extend;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.activity.siteview.siteview_forumviewthread;
import net.discuz.adapter.ThreadListAdapter;
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

public class siteview_forumindex_mythread extends
		sub_pulltorefresh_listview_prototype
{

	public siteview_forumindex_mythread(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		myThreadAdapter = null;
		myThreadList = null;
		pageSize = 20;
		filter = getClass().getName();
		context = discuzbaseactivity;
		myThreadAdapter = new ThreadListAdapter(context, null);
		listview.setAdapter(myThreadAdapter);
	}

	private void _clearAdapter()
	{
		handler.post(new Runnable()
		{

			public void run()
			{
				if (myThreadAdapter != null)
				{
					myThreadAdapter._setList(null);
					myThreadAdapter.notifyDataSetChanged();
				}
			}
		});
	}

	private void updateUI()
	{
		context.dismissLoading();
		if (isPullDownrefresh)
		{
			isPullDownrefresh = false;
			mpulltorefresh.loadedReturnOnAsyncTask();
			ShowMessage.getInstance(context)._showToast(
					R.string.message_fresh_succes, 1);
		}
		if (myThreadList == null || myThreadList != null
				&& myThreadList.size() == 0)
		{
			myThreadList = null;
			_clearAdapter();
			errorMessage = context.getResources().getString(
					R.string.error_no_mythread);
			setListFooter(-301);
			return;
		}
		mpulltorefresh.isLoading = false;
		myThreadAdapter._setList(myThreadList);
		listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
		{

			public void onItemClick(AdapterView adapterview, View view, int i,
					long l)
			{
				HashMap hashmap = myThreadAdapter.getItem(i);
				DiscuzApp.getInstance().setIsReadThread(
						(String) hashmap.get("tid"));
				myThreadAdapter.notifyDataSetChanged();
				MobclickAgent.onEvent(context, "c_trd_my");
				DiscuzStats.add(context.siteAppId, "c_trd_my");
				Intent intent = new Intent();
				intent.putExtra("siteappid", context.siteAppId);
				intent.putExtra("tid", (String) hashmap.get("tid"));
				intent.putExtra(
						"forumname",
						context.getResources().getString(
								R.string.switcher_mythread));
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
		httpConnThread = new HttpConnThread(context.siteAppId, 1);
		httpConnThread
				.setCachePath((new StringBuilder()).append("cache/")
						.append(InitSetting._getUserPath(context.siteAppId))
						.toString());
		mpulltorefresh.isLoading = true;
		String as[];
		if (uid != (long) i)
		{
			uid = i;
			httpConnThread.setCacheType(1);
			myThreadList = null;
			_clearAdapter();
			setListFooter(-101);
			page = 1;
			context.showLoading(null);
		} else if (isShowingLoding)
		{
			httpConnThread.setCacheType(1);
			myThreadList = null;
			isShowingLoding = false;
			isPullDownrefresh = true;
			_clearAdapter();
			page = 1;
			setListFooter(-101);
			context.showLoading(null);
		} else if (isLoadMore || isPullDownrefresh)
			httpConnThread.setCacheType(1);
		else if (myThreadList != null)
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
		as[1] = "module=mythread";
		as[2] = (new StringBuilder()).append("page=").append(page).toString();
		url = Core.getSiteUrl(as);
		httpConnThread.setFilter(filter);
		httpConnThread.setUrl(url);
		DiscuzApp.getInstance().setHttpConnListener(filter,
				new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
				{

					public void onFailed(Exception exception)
					{
						manageException(exception, myThreadAdapter);
					}

					public void onSucceed(String s, String s1)
					{
						DJsonReader djsonreader = new DJsonReader(s);
						try
						{
							djsonreader._jsonParse();
							djsonreader._debug();
							(new JsonParser(context)).myThread(
									djsonreader._getVariables(),
									new JsonParseHelperCallBack()
									{

										public void onParseBegin()
										{}

										public void onParseFinish(Object obj)
										{
											onParseFinish((ArrayList) obj);
										}

										public void onParseFinish(
												ArrayList arraylist)
										{
											if (arraylist != null)
											{
												int i = arraylist.size();
												int j = 0;
												while (j < i)
												{
													HashMap hashmap = (HashMap) arraylist
															.get(j);
													float f = Float
															.parseFloat((String) hashmap
																	.get("replies"));
													String s;
													float f1;
													String s1;
													if (f > 10000F)
														s = (new StringBuilder())
																.append(df
																		.format(f / 10000F))
																.append("\u4E07")
																.toString();
													else
														s = (new StringBuilder())
																.append((int) f)
																.append("")
																.toString();
													hashmap.put("replies", s);
													f1 = Float
															.parseFloat((String) hashmap
																	.get("views"));
													if (f1 > 10000F)
														s1 = (new StringBuilder())
																.append(df
																		.format(f1 / 10000F))
																.append("\u4E07")
																.toString();
													else
														s1 = (new StringBuilder())
																.append((int) f1)
																.append("")
																.toString();
													hashmap.put("views", s1);
													j++;
												}
												if (isLoadMore)
												{
													if (arraylist.size() == 0)
														context.ShowMessageByHandler(
																R.string.message_no_more_data,
																2);
													else
														myThreadList
																.addAll(arraylist);
												} else
												{
													myThreadList = arraylist;
												}
												if (arraylist.size() < pageSize)
													myThreadList
															.addAll(arraylist);
											}
											return;
										}
									});
						} catch (Exception exception)
						{
							exception.printStackTrace();
							manageException(exception, myThreadAdapter);
							return;
						}
						updateUI();
					}
				});
		DiscuzApp.getInstance().sendHttpConnThread(httpConnThread);
	}

	public void onDestroy()
	{
		myThreadAdapter = null;
		myThreadList = null;
		DiscuzApp.getInstance().removeHttpConnListener(filter);
		super.onDestroy();
	}

	public void update()
	{
		page = 1;
		isPullDownrefresh = true;
		myThreadList = null;
		newList();
	}

	private DiscuzBaseActivity context;
	private final DecimalFormat df = new DecimalFormat("#####.0");
	private String filter;
	private HttpConnThread httpConnThread;
	private ThreadListAdapter myThreadAdapter;
	private ArrayList myThreadList;
	private int pageSize;

}
// 2131296256