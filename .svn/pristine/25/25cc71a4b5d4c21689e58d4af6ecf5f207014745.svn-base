package net.discuz.source.prototype.extend;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.discuz.R;
import net.discuz.activity.siteview.ForumViewActivity;
import net.discuz.activity.siteview.siteview_forumviewthread;
import net.discuz.adapter.ForumIndexRecommendListAdapter;
import net.discuz.app.DiscuzApp;
import net.discuz.boardcast.HttpConnReceiver;
import net.discuz.init.InitSetting;
import net.discuz.json.JsonParser;
import net.discuz.source.DJsonReader;
import net.discuz.source.InterFace.JsonParseHelperCallBack;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.pulltorefresh_expandablelistview_prototype;
import net.discuz.source.prototype.sub_pulltorefresh_expandablelistview_prototype;
import net.discuz.source.storage.ForumTrackDBHelper;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.HttpConnThread;
import android.content.Intent;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;

import com.umeng.analytics.MobclickAgent;

/**
 * 推荐版块，常去板块列表，还有获取了相应的帖子
 * 
 * @author lkh
 * 
 */
public class siteview_forumindex_recommendlist extends
		sub_pulltorefresh_expandablelistview_prototype
{
	public siteview_forumindex_recommendlist(
			DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		listadapter = null;
		recommendForumList = null;
		recommendThreadList = null;
		isrefresh = false;
		// 热门板块
		hotForumListener = new HttpConnReceiver.HttpConnListener()
		{

			public void onFailed(Exception exception)
			{
				pulltorefresh_expandablelistview_prototype.context
						.dismissLoading();
				manageException(exception, listadapter);
			}

			public void onSucceed(String s, String s1)
			{
				DJsonReader djsonreader = new DJsonReader(s);
				if (djsonreader != null)
					try
					{
						djsonreader._jsonParse();
						djsonreader._debug();
						(new JsonParser(
								pulltorefresh_expandablelistview_prototype.context))
								.hotForum(djsonreader._getVariables(),
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
													recommendForumList = new ArrayList();// //////
													Iterator iterator = arraylist
															.iterator();
													do
													{
														if (!iterator.hasNext())
															break;
														HashMap hashmap = (HashMap) iterator
																.next();
														float f = Float
																.parseFloat((String) hashmap
																		.get("threads"));
														String s;
														float f1;
														String s1;
														if (f > 10000F)
															s = (new StringBuilder())
																	.append(df
																			.format(f / 10000F))
																	.append("万")
																	.toString();
														else
															s = (new StringBuilder())
																	.append((int) f)
																	.append("")
																	.toString();
														hashmap.put("threads",
																s);
														f1 = Float
																.parseFloat((String) hashmap
																		.get("posts"));
														if (f1 > 10000F)
															s1 = (new StringBuilder())
																	.append(df
																			.format(f1 / 10000F))
																	.append("万")
																	.toString();
														else
															s1 = (new StringBuilder())
																	.append((int) f1)
																	.append("")
																	.toString();
														hashmap.put("posts", s1);
														if (arraylist
																.indexOf(hashmap) < 2)
														{
															recommendForumList
																	.add(hashmap);
															dbHelper.addForumTrack(
																	siteAppId,
																	(String) hashmap
																			.get("fid"),
																	(String) hashmap
																			.get("name"));
														}
													} while (true);
												}
												updateUI();
											}
										});
					} catch (Exception exception)
					{
						exception.printStackTrace();
					}
				pulltorefresh_expandablelistview_prototype.context
						.dismissLoading();
				return;
			}
		};
		// 热门帖子，就在这里
		hotThreadListener = new HttpConnReceiver.HttpConnListener()
		{

			public void onFailed(Exception exception)
			{
				pulltorefresh_expandablelistview_prototype.context
						.dismissLoading();
				manageException(exception, listadapter);
			}

			// 解析JSON数据
			public void onSucceed(String s, String s1)
			{
				DJsonReader djsonreader = new DJsonReader(s);
				if (djsonreader != null)
					try
					{
						djsonreader._jsonParse();
						djsonreader._debug();
						(new JsonParser(
								pulltorefresh_expandablelistview_prototype.context))
								.hotThread(djsonreader._getVariables(),
										new JsonParseHelperCallBack()
										{
											public void onParseBegin()
											{}

											public void onParseFinish(Object obj)
											{
												onParseFinish((ArrayList) obj);
											}

											public void onParseFinish(
													ArrayList<HashMap<String, String>> arraylist)
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
																	.append("万")
																	.toString();
														else
															s = (new StringBuilder())
																	.append((int) f)
																	.append("")
																	.toString();
														hashmap.put("replies",
																s);
														f1 = Float
																.parseFloat((String) hashmap
																		.get("views"));
														if (f1 > 10000F)
															s1 = (new StringBuilder())
																	.append(df
																			.format(f1 / 10000F))
																	.append("万")
																	.toString();
														else
															s1 = (new StringBuilder())
																	.append((int) f1)
																	.append("")
																	.toString();
														hashmap.put("views", s1);
														j++;
													}
												}
												recommendThreadList = arraylist;
												updateUI();
											}
										});
					} catch (Exception exception)
					{
						exception.printStackTrace();
					}
				pulltorefresh_expandablelistview_prototype.context
						.dismissLoading();
				return;
			}
		};
		listadapter = new ForumIndexRecommendListAdapter(context, null, null);
		listview.setAdapter(listadapter);
		// 点击贴子和板块事件
		listview.setOnChildClickListener(new android.widget.ExpandableListView.OnChildClickListener()
		{

			public boolean onChildClick(ExpandableListView expandablelistview,
					View view, int i, int j, long l)
			{
				int k;
				if (popupWindow_menu != null && popupWindow_menu.isShowing())
				{
					popupWindow_menu.dismiss();
					return true;
				}
				k = listadapter.getGroupIndex(i);
				if (k != 0)
				{
					if (k == 1)
					{
						MobclickAgent
								.onEvent(
										pulltorefresh_expandablelistview_prototype.context,
										"v_hottrd");
						DiscuzStats.add(siteAppId, "v_hottrd");
						String s = (String) ((HashMap) recommendThreadList
								.get(j)).get("tid");
						Intent intent = new Intent();
						intent.putExtra("tid", s);
						intent.putExtra(
								"forumname",
								pulltorefresh_expandablelistview_prototype.context
										.getResources().getString(
												R.string.switcher_hotthread));
						intent.putExtra(
								"siteappid",
								pulltorefresh_expandablelistview_prototype.context.siteAppId);
						intent.setClass(
								pulltorefresh_expandablelistview_prototype.context,
								siteview_forumviewthread.class);
						pulltorefresh_expandablelistview_prototype.context
								.startActivity(intent);
						discuzApp.setIsReadThread(s);
						listadapter.notifyDataSetChanged();
					}
				} else
				{
					MobclickAgent.onEvent(
							pulltorefresh_expandablelistview_prototype.context,
							"c_trdls_often");
					DiscuzStats.add(siteAppId, "c_trdls_often");
					HashMap hashmap = (HashMap) recommendForumList.get(j);
					dbHelper.addForumTrack(siteAppId,
							(String) hashmap.get("fid"),
							(String) hashmap.get("name"));
					dbHelper.dequeueForumTrack();
					Intent intent1 = new Intent();
					intent1.putExtra(
							"siteappid",
							pulltorefresh_expandablelistview_prototype.context.siteAppId);
					intent1.putExtra("fid", (String) hashmap.get("fid"));
					intent1.putExtra("forumname", (String) hashmap.get("name"));
					intent1.setClass(
							pulltorefresh_expandablelistview_prototype.context,
							ForumViewActivity.class);
					pulltorefresh_expandablelistview_prototype.context
							.startActivity(intent1);
				}
				return false;
			}
		});
		listview.setOnGroupClickListener(new android.widget.ExpandableListView.OnGroupClickListener()
		{

			public boolean onGroupClick(ExpandableListView expandablelistview,
					View view, int i, long l)
			{
				return true;
			}
		});
		siteAppId = discuzbaseactivity.siteAppId;
		dbHelper = ForumTrackDBHelper.getInstance();
		hotForumThread = new HttpConnThread(discuzbaseactivity.siteAppId, 1);
		HttpConnThread httpconnthread = hotForumThread;
		String as[] = new String[2];
		as[0] = discuzbaseactivity.siteAppId;
		as[1] = "module=hotforum";
		httpconnthread.setUrl(Core.getSiteUrl(as));
		hotForumThread.setCachePath(InitSetting
				._getUserPath(discuzbaseactivity.siteAppId));
		hotForumFilter = hotForumThread.getClass().getSimpleName();
		hotForumThread.setFilter(hotForumFilter);
		discuzApp = DiscuzApp.getInstance();
		discuzApp.setHttpConnListener(hotForumFilter, hotForumListener);
		hotThreadThread = new HttpConnThread(discuzbaseactivity.siteAppId, 1);
		HttpConnThread httpconnthread1 = hotThreadThread;
		String as1[] = new String[2];
		as1[0] = discuzbaseactivity.siteAppId;
		as1[1] = "module=hotthread";
		httpconnthread1.setUrl(Core.getSiteUrl(as1));
		hotThreadThread.setCachePath("_t/");
		hotThreadFilter = (new StringBuilder())
				.append(hotThreadThread.getClass().getSimpleName())
				.append(context.siteAppId).toString();
		hotThreadThread.setFilter(hotThreadFilter);
		discuzApp.setHttpConnListener(hotThreadFilter, hotThreadListener);
	}

	/**
	 * 加载热门板块
	 */
	private void loadHotForum()
	{
		recommendForumList = dbHelper.selectForumTrack(siteAppId);
		if (recommendForumList == null)
		{
			discuzApp.sendHttpConnThread(hotForumThread);
			return;
		} else
		{
			updateUI();
			return;
		}
	}

	private void updateUI()
	{
		if (isrefresh)
		{
			isrefresh = false;
			mpulltorefresh.loadedReturnOnAsyncTask();
			context.ShowMessageByHandler(R.string.message_fresh_succes, 1); // 刷新成功
		}
		listadapter.setList(recommendForumList, recommendThreadList);
		listview.setGroupIndicator(null);
		for (int i = 0; i < listadapter.getGroupCount(); i++)
			if (listview != null)
				listview.expandGroup(i);

		mpulltorefresh.isLoading = false;
	}

	public void init(PopupWindow popupwindow)
	{
		popupWindow_menu = popupwindow;
	}

	public boolean manageException(Exception exception,
			BaseExpandableListAdapter baseexpandablelistadapter)
	{
		isrefresh = false;
		mpulltorefresh.loadedReturnOnAsyncTask();
		if (exception instanceof UnknownHostException)
		{
			if (baseexpandablelistadapter.getGroupCount() > 0)
			{
				context.ShowMessageByHandler(R.string.message_internet_error, 3);
			} else
			{
				errorMessage = context.getResources().getString(
						R.string.error_check_net);
				_setListFooter(R.drawable.error_wifi);
			}
		} else if (exception instanceof ConnectException)
		{
			if (baseexpandablelistadapter.getGroupCount() > 0)
			{
				context.ShowMessageByHandler(R.string.message_no_internet_1, 3);
			} else
			{
				errorMessage = context.getResources().getString(
						R.string.error_check_net);
				_setListFooter(R.drawable.error_wifi);
			}
		} else if (exception instanceof SocketException)
		{
			if (baseexpandablelistadapter.getGroupCount() > 0)
			{
				context.ShowMessageByHandler(R.string.message_internet_out, 3);
			} else
			{
				errorMessage = context.getResources().getString(
						R.string.message_internet_out);
				_setListFooter(R.drawable.error_wifi);
			}
		} else
		{
			errorMessage = context.getResources()
					.getString(R.string.error_read);
			_setListFooter(0);
		}
		return true;
	}

	/**
	 * 把获取贴子的线程放到了线程池
	 */
	public void newList()
	{
		mpulltorefresh.isLoading = true;
		if (isrefresh)
			hotThreadThread.setCacheType(1);
		else if (isShowingLoding)
		{
			isrefresh = true;
			isShowingLoding = false;
			listadapter.setList(null, null);
			context.showLoading(null);
			hotThreadThread.setCacheType(1);
		} else
		{
			context.showLoading(null);
			hotThreadThread.setCacheType(2);
		}
		loadHotForum();
		discuzApp.sendHttpConnThread(hotThreadThread);
	}

	public void onDestroy()
	{
		discuzApp.removeHttpConnListener(hotForumFilter);
	}

	/**
	 * 加载，更新数据
	 */
	public void update()
	{
		isrefresh = true;
		newList();
	}

	private ForumTrackDBHelper dbHelper;
	private final DecimalFormat df = new DecimalFormat("#####.0");
	private DiscuzApp discuzApp;
	private String hotForumFilter;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener hotForumListener;
	private HttpConnThread hotForumThread;
	private String hotThreadFilter;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener hotThreadListener;
	private HttpConnThread hotThreadThread;
	private boolean isrefresh;
	private ForumIndexRecommendListAdapter listadapter;
	private PopupWindow popupWindow_menu;
	private ArrayList recommendForumList;
	private ArrayList recommendThreadList;
	private String siteAppId;

}
// 2131296256