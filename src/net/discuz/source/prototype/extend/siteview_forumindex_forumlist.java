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
import net.discuz.adapter.ForumIndexForumListAdapter;
import net.discuz.app.DiscuzApp;
import net.discuz.init.InitSetting;
import net.discuz.json.JsonParser;
import net.discuz.model.ForumIndexData;
import net.discuz.source.DJsonReader;
import net.discuz.source.InterFace.JsonParseHelperCallBack;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.pulltorefresh_expandablelistview_prototype;
import net.discuz.source.prototype.sub_pulltorefresh_expandablelistview_prototype;
import net.discuz.tools.Core;
import net.discuz.tools.HttpConnThread;
import android.content.Intent;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;

public class siteview_forumindex_forumlist extends
		sub_pulltorefresh_expandablelistview_prototype
{

	public siteview_forumindex_forumlist(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		listadapter = null;
		catlist = null;
		catvalues = null;
		catforumlist = null;
		forumvalues = null;
		isrefresh = false;
		httpConnListener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
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
								.forumindex(djsonreader._getVariables(),
										new JsonParseHelperCallBack()
										{

											public void onParseBegin()
											{}

											public void onParseFinish(Object obj)
											{
												onParseFinish((ForumIndexData) obj);
											}

											public void onParseFinish(
													ForumIndexData forumindexdata)
											{
												catlist = forumindexdata
														.getCatlist();
												catvalues = forumindexdata
														.getCatValues();
												catforumlist = forumindexdata
														.getForumList();
												forumvalues = forumindexdata
														.getForumValues();
												if (forumvalues != null)
												{
													Iterator iterator = forumvalues
															.values()
															.iterator();
													while (iterator.hasNext())
													{
														HashMap hashmap = (HashMap) iterator
																.next();
														float f = Float
																.parseFloat((String) hashmap
																		.get("threads"));
														String s;
														float f1;
														String s1;
														String s2;
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
														hashmap.put("threads",
																s);
														f1 = Float
																.parseFloat((String) hashmap
																		.get("posts"));
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
														hashmap.put("posts", s1);
														if (hashmap
																.get("todayposts") == null)
														{
															s2 = "0";
														} else
														{
															int i = Integer
																	.parseInt((String) hashmap
																			.get("todayposts"));
															if (i > 999)
																s2 = "999+";
															else
																s2 = (new StringBuilder())
																		.append(i)
																		.append("")
																		.toString();
														}
														hashmap.put(
																"todayposts",
																s2);
													}
												}
												if (listadapter != null)
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
		listadapter = new ForumIndexForumListAdapter(context, null, null, null,
				null);
		listview.setAdapter(listadapter);
		String as[] = new String[2];
		as[0] = discuzbaseactivity.siteAppId;
		as[1] = "module=forumindex";
		url = Core.getSiteUrl(as);
		httpConnThread = new HttpConnThread(discuzbaseactivity.siteAppId, 1);
		httpConnThread.setUrl(url);
		httpConnThread.setCachePath(InitSetting
				._getUserPath(discuzbaseactivity.siteAppId));
		filter = getClass().getSimpleName();
		httpConnThread.setFilter(filter);
		discuzApp = DiscuzApp.getInstance();
		discuzApp.setHttpConnListener(filter, httpConnListener);
	}

	private void updateUI()
	{
		if (isrefresh)
		{
			isrefresh = false;
			mpulltorefresh.loadedReturnOnAsyncTask();
			context.ShowMessageByHandler(R.string.message_fresh_succes, 1);
		}
		listadapter._setList(catlist, catvalues, catforumlist, forumvalues);
		listview.setAdapter(listadapter);
		listview.setGroupIndicator(null);
		if (listadapter.catlist != null)
		{
			for (int i = 0; i < listadapter.catlist.size(); i++)
				if (listview != null)
					listview.expandGroup(i);

		}
		listview.setOnChildClickListener(new android.widget.ExpandableListView.OnChildClickListener()
		{

			public boolean onChildClick(ExpandableListView expandablelistview,
					View view, int j, int k, long l)
			{
				String s;
				HashMap hashmap;
				if (popupWindow_menu != null && popupWindow_menu.isShowing())
				{
					popupWindow_menu.dismiss();
					return true;
				}
				s = listadapter._getfid(j, k);
				hashmap = (HashMap) listadapter.forumvalues.get(s);
				if (!((String) ((HashMap) listadapter.forumvalues.get(s))
						.get("redirect")).equals("")
						&& !"0".contains((CharSequence) ((HashMap) listadapter.forumvalues
								.get(s)).get("redirect")))
				{
					if (URLUtil
							.isHttpUrl((String) ((HashMap) listadapter.forumvalues
									.get(s)).get("redirect")))
						Core.getInstance().gotoUrlByWebView(
								(String) ((HashMap) listadapter.forumvalues
										.get(s)).get("redirect"));
				} else
				{
					Intent intent = new Intent();
					intent.putExtra("fid", (String) hashmap.get("fid"));
					intent.putExtra(
							"siteappid",
							pulltorefresh_expandablelistview_prototype.context.siteAppId);
					intent.putExtra("forumname",
							(String) hashmap.get("forumname"));
					intent.setClass(
							pulltorefresh_expandablelistview_prototype.context,
							ForumViewActivity.class);
					DiscuzApp.getInstance().setIsReadForum(
							(String) hashmap.get("fid"));
					listadapter.notifyDataSetChanged();
					pulltorefresh_expandablelistview_prototype.context
							.startActivity(intent);
				}
				return false;
			}
		});
		listview.setOnGroupClickListener(new android.widget.ExpandableListView.OnGroupClickListener()
		{

			public boolean onGroupClick(ExpandableListView expandablelistview,
					View view, int j, long l)
			{
				return true;
			}
		});
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

	public void newList()
	{
		mpulltorefresh.isLoading = true;
		if (isrefresh)
			httpConnThread.setCacheType(-1);
		else if (isShowingLoding)
		{
			isrefresh = true;
			isShowingLoding = false;
			listadapter._setList(null, null, null, null);
			context.showLoading(null);
			httpConnThread.setCacheType(-1);
		} else
		{
			context.showLoading(null);
			httpConnThread.setCacheType(-1);
		}
		discuzApp.sendHttpConnThread(httpConnThread);
	}

	public void onDestroy()
	{
		discuzApp.removeHttpConnListener(filter);
	}

	public void update()
	{
		isrefresh = true;
		newList();
	}

	public HashMap catforumlist;
	public ArrayList catlist;
	public HashMap catvalues;
	private final DecimalFormat df = new DecimalFormat("#####.0");
	private DiscuzApp discuzApp;
	private String filter;
	public HashMap forumvalues;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener httpConnListener;
	private HttpConnThread httpConnThread;
	private boolean isrefresh;
	private ForumIndexForumListAdapter listadapter;
	private PopupWindow popupWindow_menu;

}
// 2131296256