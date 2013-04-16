package net.discuz.source.prototype.extend;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.activity.siteview.ForumViewActivity;
import net.discuz.adapter.ForumIndexFidAdapter;
import net.discuz.app.DiscuzApp;
import net.discuz.json.JsonParser;
import net.discuz.source.DEBUG;
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

public class siteview_forumindex_hotforum extends
		sub_pulltorefresh_listview_prototype
{
	class ClickItemListener implements
			android.widget.AdapterView.OnItemClickListener
	{

		public void onItemClick(AdapterView adapterview, View view, int i,
				long l)
		{
			HashMap hashmap = (HashMap) adapter.getFidList().get(i);
			MobclickAgent.onEvent(context, "c_trdls_hot");
			DiscuzStats.add(context.siteAppId, "c_trdls_hot");
			Intent intent = new Intent();
			intent.putExtra("siteappid", context.siteAppId);
			intent.putExtra("fid", (String) hashmap.get("fid"));
			intent.putExtra("forumname", (String) hashmap.get("name"));
			intent.setClass(context, ForumViewActivity.class);
			context.startActivity(intent);
			DiscuzApp.getInstance().setIsReadForum((String) hashmap.get("fid"));
			adapter.notifyDataSetChanged();
		}

		ClickItemListener()
		{
			super();
		}
	}

	public siteview_forumindex_hotforum(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		hotFidList = null;
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
						(new JsonParser(context)).hotForum(
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
															.append(f)
															.append("")
															.toString();
												hashmap.put("threads", s);
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
															.append(f1)
															.append("")
															.toString();
												hashmap.put("posts", s1);
												if (hashmap.get("todayposts") == null)
												{
													s2 = "0";
												} else
												{
													int k = Integer
															.parseInt((String) hashmap
																	.get("todayposts"));
													if (k > 999)
														s2 = "999+";
													else
														s2 = (new StringBuilder())
																.append(k)
																.append("")
																.toString();
												}
												hashmap.put("todayposts", s2);
												j++;
											}
										}
										hotFidList = arraylist;
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
		adapter = new ForumIndexFidAdapter(discuzbaseactivity);
		listview.setAdapter(adapter);
		String as[] = new String[2];
		as[0] = discuzbaseactivity.siteAppId;
		as[1] = "module=hotforum";
		url = Core.getSiteUrl(as);
		DEBUG.o((new StringBuilder()).append("hotforum >>>").append(url)
				.toString());
		httpConnThread = new HttpConnThread(discuzbaseactivity.siteAppId, 1);
		httpConnThread.setUrl(url);
		httpConnThread.setCachePath("_t/");
		filter = getClass().getSimpleName();
		httpConnThread.setFilter(filter);
		discuzApp = DiscuzApp.getInstance();
		discuzApp.setHttpConnListener(filter, httpConnListener);
	}

	private void updateUI()
	{
		if (isPullDownrefresh)
		{
			isPullDownrefresh = false;
			mpulltorefresh.loadedReturnOnAsyncTask();
			ShowMessage.getInstance(context)._showToast(
					R.string.message_fresh_succes, 1);
		}
		if (hotFidList == null || hotFidList.size() == 0)
		{
			adapter.setFidList(null);
			adapter.notifyDataSetChanged();
			errorMessage = context.getResources().getString(
					R.string.error_no_hotdisplay);
			setListFooter(-301);
			return;
		} else
		{
			setListFooter(-101);
			adapter.setFidList(hotFidList);
			listview.setOnItemClickListener(new ClickItemListener());
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
			adapter.setFidList(null);
			adapter.notifyDataSetChanged();
			setListFooter(-101);
			context.showLoading(null);
			httpConnThread.setCacheType(1);
		} else
		{
			context.showLoading(null);
			httpConnThread.setCacheType(2);
		}
		discuzApp.sendHttpConnThread(httpConnThread);
	}

	public void onDestroy()
	{
		discuzApp.removeHttpConnListener(filter);
		hotFidList = null;
		adapter = null;
		super.onDestroy();
	}

	public void update()
	{
		isPullDownrefresh = true;
		newList();
	}

	private ForumIndexFidAdapter adapter;
	private final DecimalFormat df = new DecimalFormat("#####.0");
	private DiscuzApp discuzApp;
	private String filter;
	public ArrayList hotFidList;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener httpConnListener;
	private HttpConnThread httpConnThread;

}
// 2131296256