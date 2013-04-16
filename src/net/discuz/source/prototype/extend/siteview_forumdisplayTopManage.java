package net.discuz.source.prototype.extend;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.activity.siteview.ForumViewActivity;
import net.discuz.activity.siteview.siteview_forumviewthread;
import net.discuz.adapter.ThreadListAdapter;
import net.discuz.app.DiscuzApp;
import net.discuz.json.JsonParser;
import net.discuz.source.DJsonReader;
import net.discuz.source.ShowMessage;
import net.discuz.source.InterFace.JsonParseHelperCallBack;
import net.discuz.source.prototype.sub_pulltorefresh_listview_prototype;
import net.discuz.tools.Core;
import net.discuz.tools.HttpConnThread;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

public class siteview_forumdisplayTopManage extends
		sub_pulltorefresh_listview_prototype
{

	class ClickItemListener implements AdapterView.OnItemClickListener
	{
		ClickItemListener()
		{}

		public void onItemClick(AdapterView adapterview, View view, int i,
				long l)
		{
			if (adapter.getCount() > 0)
			{
				String s = (String) adapter.getItem(i).get("tid");
				Intent intent = new Intent();
				intent.putExtra("tid", s);
				intent.putExtra("siteappid", context.siteAppId);
				intent.putExtra("forumname",
						(String) adapter.getItem(i).get("subject"));
				intent.setClass(activity, siteview_forumviewthread.class);
				activity.startActivity(intent);
				DiscuzApp.getInstance().setIsReadThread(s);
				adapter.notifyDataSetChanged();
			}
		}
	}

	public siteview_forumdisplayTopManage(ForumViewActivity forumviewactivity,
			HashMap hashmap)
	{
		super(forumviewactivity);
		adapter = null;
		activity = null;
		paramsMap = null;
		forumTopList = null;
		httpConnListener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
		{

			public void onFailed(Exception exception)
			{
				manageException(exception, adapter);
				activity.dismissLoading();
			}

			public void onSucceed(String s, String s1)
			{
				try
				{
					DJsonReader djsonreader = new DJsonReader(s);
					djsonreader._jsonParse();
					djsonreader._debug();
					(new JsonParser(activity)).topList(
							djsonreader._getVariables(),
							new JsonParseHelperCallBack()
							{

								public void onParseBegin()
								{}

								public void onParseFinish(Object obj)
								{
									onParseFinish((ArrayList) obj);
								}

								public void onParseFinish(ArrayList arraylist)
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
														.append("万").toString();
											else
												s = (new StringBuilder())
														.append((int) f)
														.append("").toString();
											hashmap.put("replies", s);
											f1 = Float
													.parseFloat((String) hashmap
															.get("views"));
											if (f1 > 10000F)
												s1 = (new StringBuilder())
														.append(df
																.format(f1 / 10000F))
														.append("万").toString();
											else
												s1 = (new StringBuilder())
														.append((int) f1)
														.append("").toString();
											hashmap.put("views", s1);
											j++;
										}
									}
									forumTopList = arraylist;
									if (isPullDownrefresh)
									{
										mpulltorefresh
												.loadedReturnOnAsyncTask();
										mpulltorefresh.isLoading = false;
									}
									updateUI();
								}
							});
				} catch (Exception exception)
				{
					exception.printStackTrace();
					manageException(exception, adapter);
				}
				activity.dismissLoading();
			}
		};
		activity = forumviewactivity;
		paramsMap = hashmap;
		adapter = new ThreadListAdapter(activity, null);
		listview.setAdapter(adapter);
	}

	private void updateUI()
	{
		if (forumTopList != null && forumTopList.size() == 0)
		{
			adapter._setList(null);
			adapter.notifyDataSetChanged();
			listview.setAdapter(adapter);
			errorMessage = context.getResources().getString(
					R.string.error_no_topthread);
			setListFooter(-301);
		} else
		{
			adapter._setList(forumTopList);
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new ClickItemListener());
			if (isPullDownrefresh)
			{
				isPullDownrefresh = false;
				ShowMessage.getInstance(context)._showToast(
						R.string.message_fresh_succes, 1);
				return;
			}
		}
	}

	public void loadMore()
	{
		super.loadMore();
		isPullDownrefresh = true;
		newList();
	}

	public void newList()
	{
		String as[] = new String[3];
		as[0] = activity.siteAppId;
		as[1] = "module=toplist";
		as[2] = (new StringBuilder()).append("fid=")
				.append((String) paramsMap.get("fid")).toString();
		url = Core.getSiteUrl(as);
		httpConnThread = new HttpConnThread(activity.siteAppId, 1);
		String as1[] = new String[3];
		as1[0] = activity.siteAppId;
		as1[1] = "module=toplist";
		as1[2] = (new StringBuilder()).append("fid=")
				.append((String) paramsMap.get("fid")).toString();
		url = Core.getSiteUrl(as1);
		httpConnThread.setUrl(url);
		httpConnThread.setCachePath("_t/");
		String s = getClass().getSimpleName();
		httpConnThread.setFilter(s);
		DiscuzApp.getInstance().setHttpConnListener(s, httpConnListener);
		if (!isPullDownrefresh)
			if (isShowingLoding)
			{
				isShowingLoding = false;
				isPullDownrefresh = true;
				activity.showLoading(null);
				adapter._setList(null);
				activity.runOnUiThread(new Runnable()
				{

					public void run()
					{
						adapter.notifyDataSetChanged();
					}
				});
				setListFooter(-101);
			} else
			{
				activity.showLoading(null);
			}
		httpConnThread.setCacheType(1);
		DiscuzApp.getInstance().sendHttpConnThread(httpConnThread);
		mpulltorefresh.isLoading = true;
	}

	public void update()
	{
		isPullDownrefresh = true;
		newList();
	}

	private ForumViewActivity activity;
	private ThreadListAdapter adapter;
	private final DecimalFormat df = new DecimalFormat("#####.0");
	public ArrayList forumTopList;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener httpConnListener;
	private HttpConnThread httpConnThread;
	private HashMap paramsMap;

}
// 2131296256