package net.discuz.source.prototype.extend;

import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.activity.siteview.ForumViewActivity;
import net.discuz.adapter.MyFavForumListAdapter;
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

public class siteview_forumindex_myfavforum extends
		sub_pulltorefresh_listview_prototype
{

	public siteview_forumindex_myfavforum(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		myFavForum = null;
		pageSize = 20;
		filter = getClass().getName();
		myFavForumApter = new MyFavForumListAdapter(context, myFavForum);
		listview.setAdapter(myFavForumApter);
	}

	private void _clearAdapter()
	{
		handler.post(new Runnable()
		{

			public void run()
			{
				myFavForumApter._setList(null);
				myFavForumApter.notifyDataSetChanged();
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
		if (myFavForum == null || myFavForum.size() == 0)
		{
			myFavForumApter._setList(null);
			myFavForumApter.notifyDataSetChanged();
			errorMessage = context.getResources().getString(
					R.string.error_no_favordisplay);
			setListFooter(-301);
			return;
		}
		myFavForumApter._setList(myFavForum);
		if (isNextPage)
			setListFooter(-102);
		else
			setListFooter(-101);
		listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
		{

			public void onItemClick(AdapterView adapterview, View view, int i,
					long l)
			{
				MobclickAgent.onEvent(context, "c_trdls_fav");
				DiscuzStats.add(context.siteAppId, "c_trdls_fav");
				DiscuzApp.getInstance().setIsReadForum(
						(String) ((HashMap) myFavForum.get(i)).get("fid"));
				myFavForumApter.notifyDataSetChanged();
				HashMap hashmap = (HashMap) myFavForum.get(i);
				Intent intent = new Intent();
				intent.putExtra("siteappid", context.siteAppId);
				intent.putExtra("fid", (String) hashmap.get("id"));
				intent.putExtra("forumname", (String) hashmap.get("title"));
				intent.setClass(context, ForumViewActivity.class);
				context.startActivity(intent);
			}
		});
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
			_clearAdapter();
			setListFooter(-501);
			return;
		}
		httpConnThread = new HttpConnThread(context.siteAppId, 1);
		httpConnThread
				.setCachePath(InitSetting._getUserPath(context.siteAppId));
		httpConnThread.setFilter(filter);
		mpulltorefresh.isLoading = true;
		String as[];
		if (uid != (long) i)
		{
			httpConnThread.setCacheType(1);
			uid = i;
			_clearAdapter();
			setListFooter(-101);
			page = 1;
			context.showLoading(null);
		} else if (isShowingLoding)
		{
			isShowingLoding = false;
			isPullDownrefresh = true;
			_clearAdapter();
			setListFooter(-101);
			context.showLoading(null);
			httpConnThread.setCacheType(1);
		} else if (isPullDownrefresh || isLoadMore)
			httpConnThread.setCacheType(1);
		else if (myFavForum != null)
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
		as[1] = "module=myfavforum";
		as[2] = (new StringBuilder()).append("page=").append(page).toString();
		url = Core.getSiteUrl(as);
		httpConnThread.setUrl(url);
		DiscuzApp.getInstance().setHttpConnListener(filter,
				new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
				{

					public void onFailed(Exception exception)
					{
						context.dismissLoading();
						manageException(exception, myFavForumApter);
					}

					public void onSucceed(String s, String s1)
					{
						try
						{
							DJsonReader djsonreader = new DJsonReader(s);
							djsonreader._jsonParse();
							djsonreader._debug();
							(new JsonParser(context)).myFavForum(
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
											if (siteview_forumindex_myfavforum.this.isLoadMore)
											{
												if (arraylist.size() == 0)
													siteview_forumindex_myfavforum.this.context
															.ShowMessageByHandler(
																	2131165292,
																	2);
											} else if (arraylist.size() >= siteview_forumindex_myfavforum.this.pageSize)
											{
												siteview_forumindex_myfavforum.this.myFavForum
														.addAll(arraylist);
											}
										}

									});
						} catch (Exception exception)
						{
							exception.printStackTrace();
							manageException(exception, myFavForumApter);
							return;
						}
						updateUI();
					}
				});
		DiscuzApp.getInstance().sendHttpConnThread(httpConnThread);
	}

	public void onDestroy()
	{
		DiscuzApp.getInstance().removeHttpConnListener(filter);
		super.onDestroy();
	}

	public void update()
	{
		isPullDownrefresh = true;
		newList();
	}

	private String filter;
	private HttpConnThread httpConnThread;
	private ArrayList myFavForum;
	private MyFavForumListAdapter myFavForumApter;
	private int pageSize;

}
// 2131296256