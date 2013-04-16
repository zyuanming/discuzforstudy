package net.discuz.source.prototype.extend;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.activity.siteview.SmsNewMessage;
import net.discuz.activity.siteview.siteview_forumsmsdisplay;
import net.discuz.adapter.SMSPersonAdapter;
import net.discuz.app.DiscuzApp;
import net.discuz.json.JsonParser;
import net.discuz.model.DownFile;
import net.discuz.model.PmData;
import net.discuz.model.SiteInfo;
import net.discuz.source.DJsonReader;
import net.discuz.source.ShowMessage;
import net.discuz.source.ThreadUtils;
import net.discuz.source.InterFace.JsonParseHelperCallBack;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.sub_pulltorefresh_listview_prototype;
import net.discuz.source.service.DownLoadService;
import net.discuz.tools.Core;
import net.discuz.tools.HttpConnThread;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;

public class SMSPersonalManage extends sub_pulltorefresh_listview_prototype
{
	public class AsyncTaskBase extends AsyncTask
	{

		protected Object doInBackground(Object aobj[])
		{
			return doInBackground((String[]) aobj);
		}

		protected String doInBackground(String as[])
		{
			ArrayList arraylist = myPmData.getPmList();
			for (int i = 0; i < arraylist.size(); i++)
			{
				if (arraylist.get(i) == null)
					continue;
				File file = new File((new StringBuilder())
						.append("/sdcard/discuz/style/")
						.append((String) ((HashMap) arraylist.get(i))
								.get("touid")).append(".png").toString());
				if (!file.exists() || !file.isFile())
				{
					Intent intent = new Intent(activity, DownLoadService.class);
					DownLoadService
							.setDownLoadParams(
									activity,
									new DownFile(
											(new StringBuilder())
													.append(DiscuzApp
															.getInstance()
															.getSiteInfo(
																	context.siteAppId)
															.getUCenterUrl())
													.append("/avatar.php?uid=")
													.append((String) ((HashMap) arraylist
															.get(i))
															.get("touid"))
													.append("&size=middle")
													.toString(),
											(new StringBuilder())
													.append((String) ((HashMap) arraylist
															.get(i))
															.get("touid"))
													.append(".png").toString(),
											"/sdcard/discuz/style/"));
					activity.startService(intent);
					ThreadUtils.sleep(500L);
				}
			}

			return null;
		}

		public AsyncTaskBase()
		{
			super();
		}
	}

	class clickItemListener implements
			android.widget.AdapterView.OnItemClickListener
	{

		public void onItemClick(AdapterView adapterview, View view, int i,
				long l)
		{
			if (l != -1L)
			{
				Intent intent = new Intent();
				intent.putExtra("siteappid", activity.siteAppId);
				intent.putExtra("touid",
						(String) smsAdapter.getItem(i).get("touid"));
				intent.putExtra("friendname", (String) smsAdapter.getItem(i)
						.get("tousername"));
				String s;
				if ("".equals(smsAdapter.getItem(i).get("pmnum")))
					s = "0";
				else
					s = (String) smsAdapter.getItem(i).get("pmnum");
				intent.putExtra("page",
						(int) Math.ceil(Float.valueOf(s).floatValue() / 10F));
				intent.putExtra("pmid",
						(String) smsAdapter.getItem(i).get("pmid"));
				intent.setClass(activity, siteview_forumsmsdisplay.class);
				DiscuzApp.getInstance().setIsReadPm(
						(String) smsAdapter.getItem(i).get("pmid"));
				activity.startActivity(intent);
				smsAdapter.getItem(i).put("isnew", "0");
				smsAdapter.notifyDataSetChanged();
			}
		}

		private SMSPersonAdapter smsAdapter;

		public clickItemListener(SMSPersonAdapter smspersonadapter)
		{
			super();
			smsAdapter = null;
			smsAdapter = smspersonadapter;
		}
	}

	public SMSPersonalManage(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		activity = null;
		handler = new Handler();
		smsAdapter = null;
		myPmData = null;
		friendData = null;
		httpConnListener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
		{

			public void onFailed(Exception exception)
			{
				manageException(exception, smsAdapter);
			}

			public void onSucceed(String s, String s1)
			{
				try
				{
					DJsonReader djsonreader = new DJsonReader(s);
					djsonreader._debug();
					JsonParser jsonparser = new JsonParser(activity);
					djsonreader._jsonParse();
					jsonparser.myPm(djsonreader._getVariables(),
							new JsonParseHelperCallBack()
							{

								public void onParseBegin()
								{}

								public void onParseFinish(Object obj)
								{
									onParseFinish((PmData) obj);
								}

								public void onParseFinish(PmData pmdata)
								{
									myPmData = pmdata;
									updateUI();
								}
							});
				} catch (Exception exception)
				{
					manageException(exception, smsAdapter);
				}
				context.dismissLoading();
			}
		};
		httpConnMessageListener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
		{

			public void onFailed(Exception exception)
			{
				context.dismissLoading();
			}

			public void onSucceed(String s, String s1)
			{
				DJsonReader djsonreader = new DJsonReader(s);
				try
				{
					djsonreader._jsonParse();
					djsonreader._debug();
					(new JsonParser(activity)).friend(
							djsonreader._getVariables(),
							new JsonParseHelperCallBack()
							{

								public void onParseBegin()
								{}

								public void onParseFinish(Object obj)
								{
									onParseFinish((net.discuz.json.helper.FriendParseHelper.FriendData) obj);
								}

								public void onParseFinish(
										net.discuz.json.helper.FriendParseHelper.FriendData frienddata)
								{
									friendData = frienddata;
									Intent intent = new Intent();
									if (friendData.getUsernames() != null)
									{
										intent.putExtra("friendsListName",
												friendData.getUsernames());
										intent.putExtra("siteappid",
												context.siteAppId);
										intent.putExtra("friendsListID",
												friendData.getUids());
										intent.setFlags(0x40000000);
										intent.setClass(activity,
												SmsNewMessage.class);
										activity.startActivityForResult(intent,
												200);
										return;
									} else
									{
										ShowMessage
												.getInstance(activity)
												._showToast(
														R.string.message_sms_load_null_friends,
														2);
										return;
									}
								}
							});
				} catch (Exception exception)
				{
					exception.printStackTrace();
				}
				context.dismissLoading();
			}
		};
		activity = discuzbaseactivity;
		smsAdapter = new SMSPersonAdapter(activity, false);
		smsAdapter.setSmsPull(this);
		listview.setAdapter(smsAdapter);
		mpulltorefresh
				.setUnLoaded(new net.discuz.source.widget.pulltorefresh.UnLoaded()
				{

					public void unload()
					{
						handler.post(new Runnable()
						{

							public void run()
							{
								smsAdapter.notifyDataSetChanged();
							}
						});
					}
				});
		_setUrl();
		listview.setOnItemClickListener(new clickItemListener(smsAdapter));
	}

	private void _clearAdapter()
	{
		handler.post(new Runnable()
		{

			public void run()
			{
				smsAdapter.setList(null);
				smsAdapter.notifyDataSetChanged();
			}
		});
	}

	private void _setUrl()
	{
		String as[] = new String[3];
		as[0] = activity.siteAppId;
		as[1] = "module=mypm";
		as[2] = (new StringBuilder()).append("page=").append(page).toString();
		url = Core.getSiteUrl(as);
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
		smsAdapter._setInformationMaxLines(2);
		if (myPmData == null || myPmData.pmList.size() == 0)
		{
			smsAdapter.setList(null);
			smsAdapter.notifyDataSetChanged();
			errorMessage = context.getResources().getString(
					R.string.error_no_sms);
			setListFooter(-301);
			return;
		}
		mpulltorefresh.isLoading = false;
		smsAdapter.setList(myPmData);
		smsAdapter.notifyDataSetChanged();
		String s = "1";
		if (myPmData.getValueList() != null
				&& myPmData.getValueList().get("page") != null)
			s = (String) myPmData.getValueList().get("page");
		page = Integer.valueOf(s).intValue();
		if (page > 1)
		{
			setListFooter(-102);
			return;
		} else
		{
			setListFooter(-101);
			return;
		}
	}

	public void _openNewMessage()
	{
		context.showLoading("\u8BFB\u53D6\u597D\u53CB...");
		httpConnThreadMessage = new HttpConnThread(activity.siteAppId, 1);
		HttpConnThread httpconnthread = httpConnThreadMessage;
		String as[] = new String[2];
		as[0] = activity.siteAppId;
		as[1] = "module=friend";
		httpconnthread.setUrl(Core.getSiteUrl(as));
		httpConnThreadMessage.setCachePath("_t/");
		String s = getClass().getSimpleName();
		httpConnThreadMessage.setFilter(s);
		DiscuzApp.getInstance().setHttpConnListener(s, httpConnMessageListener);
		DiscuzApp.getInstance().sendHttpConnThread(httpConnThreadMessage);
	}

	public void loadMore()
	{
		super.loadMore();
		isLoadMore = true;
		if (page > 1)
			page = -1 + page;
		newList();
	}

	public void newList()
	{
		int i;
		label0:
		{
			SiteInfo siteinfo = DiscuzApp.getInstance().getSiteInfo(
					context.siteAppId);
			if (siteinfo != null)
			{
				i = siteinfo.getLoginUser().getUid();
				httpConnThread = new HttpConnThread(activity.siteAppId, 1);
				httpConnThread.setUrl(url);
				httpConnThread.setCachePath("_t/");
				String s = getClass().getSimpleName();
				httpConnThread.setFilter(s);
				DiscuzApp.getInstance()
						.setHttpConnListener(s, httpConnListener);
				if (i >= 1)
					break label0;
				_clearAdapter();
				setListFooter(-501);
			}
			return;
		}
		if (uid != (long) i)
		{
			uid = i;
			myPmData = null;
			_clearAdapter();
			page = 1;
			_setUrl();
			setListFooter(-101);
			activity.showLoading(null);
			httpConnThread.setCacheType(1);
		} else if (isShowingLoding)
		{
			isPullDownrefresh = true;
			isShowingLoding = false;
			_clearAdapter();
			page = 1;
			_setUrl();
			setListFooter(-101);
			activity.showLoading(null);
			httpConnThread.setCacheType(1);
		} else if (isPullDownrefresh || isLoadMore)
		{
			_setUrl();
			httpConnThread.setCacheType(1);
		} else
		{
			mpulltorefresh.isLoading = false;
			return;
		}
		DiscuzApp.getInstance().sendHttpConnThread(httpConnThread);
	}

	public void update()
	{
		isPullDownrefresh = true;
		myPmData = null;
		newList();
	}

	public final int SEND_MESSAGE_SUCCESS_REQUEST_CODE = 200;
	private DiscuzBaseActivity activity;
	public net.discuz.json.helper.FriendParseHelper.FriendData friendData;
	private Handler handler;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener httpConnListener;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener httpConnMessageListener;
	private HttpConnThread httpConnThread;
	private HttpConnThread httpConnThreadMessage;
	public PmData myPmData;
	private SMSPersonAdapter smsAdapter;

}
// 2131296256