package net.discuz.source.prototype.extend;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.activity.siteview.siteview_forumviewthread;
import net.discuz.adapter.ThreadListAdapter;
import net.discuz.app.DiscuzApp;
import net.discuz.json.JsonParser;
import net.discuz.source.DEBUG;
import net.discuz.source.DJsonReader;
import net.discuz.source.InterFace.JsonParseHelperCallBack;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.sub_pulltorefresh_listview_prototype;
import net.discuz.tools.Core;
import net.discuz.tools.HttpConnThread;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

public class siteview_forumindex_hotthread extends
		sub_pulltorefresh_listview_prototype
{
	class ClickItemListener implements
			android.widget.AdapterView.OnItemClickListener
	{

		ClickItemListener()
		{
			super();
		}

		public void onItemClick(AdapterView adapterview, View view, int i,
				long l)
		{
			if (adapter.getCount() > 0)
			{
				String s = (String) adapter.getItem(i).get("tid");
				Intent intent = new Intent();
				intent.putExtra("tid", s);
				intent.putExtra(
						"forumname",
						context.getResources().getString(
								R.string.switcher_hotthread));
				intent.putExtra("siteappid", context.siteAppId);
				intent.setClass(context, siteview_forumviewthread.class);
				context.startActivity(intent);
				DiscuzApp.getInstance().setIsReadThread(s);
				adapter.notifyDataSetChanged();
			}
		}

	}

	public siteview_forumindex_hotthread(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		hotThreadsList = null;
		adapter = null;
		httpConnListener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
		{

			public void onFailed(Exception exception)
			{
				context.dismissLoading();
				manageException(exception, adapter);
			}

			public void onSucceed(String s, String s1)
			{
				DJsonReader djsonreader = new DJsonReader(s);
				if (djsonreader != null)
					try
					{
						djsonreader._jsonParse();
						djsonreader._debug();
						(new JsonParser(context)).hotThread(
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
										}
										hotThreadsList = arraylist;
										updateUI();
									}
								});
					} catch (Exception exception)
					{
						exception.printStackTrace();
					}
				context.dismissLoading();
				return;
			}
		};
		adapter = new ThreadListAdapter(context, null);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new ClickItemListener());
		httpConnThread = new HttpConnThread(discuzbaseactivity.siteAppId, 1);
		String as[] = new String[2];
		as[0] = discuzbaseactivity.siteAppId;
		as[1] = "module=hotthread";
		url = Core.getSiteUrl(as);
		httpConnThread.setUrl(url);
		httpConnThread.setCachePath("_t/");
		filter = (new StringBuilder()).append(getClass().getSimpleName())
				.append(context.siteAppId).toString();
		httpConnThread.setFilter(filter);
		DiscuzApp.getInstance().setHttpConnListener(filter, httpConnListener);
	}

	private void updateUI()
	{
		if (adapter == null || hotThreadsList == null)
			return;
		if (isPullDownrefresh)
		{
			mpulltorefresh.loadedReturnOnAsyncTask();
			isPullDownrefresh = false;
			context.ShowMessageByHandler(R.string.message_fresh_succes, 1);
		}
		if (hotThreadsList != null && hotThreadsList.size() > 0)
		{
			setListFooter(-101);
			adapter._setList(hotThreadsList);
			return;
		} else
		{
			errorMessage = context.getResources().getString(
					R.string.error_no_hotthread);
			adapter._setList(hotThreadsList);
			adapter.notifyDataSetChanged();
			setListFooter(-301);
			return;
		}
	}

	public void newList()
	{
		mpulltorefresh.isLoading = true;
		if (isPullDownrefresh)
			httpConnThread.setCacheType(1);
		else if (isShowingLoding)
		{
			isPullDownrefresh = true;
			isShowingLoding = false;
			context.showLoading(null);
			setListFooter(-101);
			httpConnThread.setCacheType(1);
		} else
		{
			context.showLoading(null);
			httpConnThread.setCacheType(2);
		}
		DiscuzApp.getInstance().sendHttpConnThread(httpConnThread);
	}

	public void onDestroy()
	{
		DiscuzApp.getInstance().removeHttpConnListener(filter);
		adapter = null;
		hotThreadsList = null;
	}

	public void update()
	{
		DEBUG.o("******\u4E0B\u62C9\u5237\u65B0*******");
		isPullDownrefresh = true;
		newList();
	}

	private ThreadListAdapter adapter;
	private final DecimalFormat df = new DecimalFormat("#####.0");
	private String filter;
	private ArrayList hotThreadsList;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener httpConnListener;
	private HttpConnThread httpConnThread;

}
// 2131296256