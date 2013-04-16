package net.discuz.source.prototype.extend;

import net.discuz.R;
import net.discuz.adapter.SMSPublicAdapter;
import net.discuz.app.DiscuzApp;
import net.discuz.json.JsonParser;
import net.discuz.model.PmData;
import net.discuz.model.SiteInfo;
import net.discuz.source.DEBUG;
import net.discuz.source.DJsonReader;
import net.discuz.source.ShowMessage;
import net.discuz.source.InterFace.JsonParseHelperCallBack;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.sub_pulltorefresh_listview_prototype;
import net.discuz.tools.Core;
import net.discuz.tools.HttpConnThread;
import android.os.Handler;

public class SMSPublicManage extends sub_pulltorefresh_listview_prototype
{

	public SMSPublicManage(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		activity = null;
		handler = new Handler();
		smsAdapter = null;
		pmData = null;
		httpConnListener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
		{

			public void onFailed(Exception exception)
			{
				manageException(exception, smsAdapter);
				context.dismissLoading();
			}

			public void onSucceed(String s1, String s2)
			{
				try
				{
					DJsonReader djsonreader = new DJsonReader(s1);
					djsonreader._jsonParse();
					djsonreader._debug();
					(new JsonParser(activity)).publicPm(
							djsonreader._getVariables(),
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
									pmData = pmdata;
									updateUI();
								}

							});
					mpulltorefresh.isLoading = false;
				} catch (Exception exception)
				{
					manageException(exception, smsAdapter);
				}
				context.dismissLoading();
			}
		};
		activity = discuzbaseactivity;
		smsAdapter = new SMSPublicAdapter(activity, true);
		smsAdapter.setSmsPull(this);
		listview.setAdapter(smsAdapter);
		_setUrl();
		mpulltorefresh
				.setUnLoaded(new net.discuz.source.widget.pulltorefresh.UnLoaded()
				{

					public void unload()
					{
						smsAdapter.notifyDataSetChanged();
					}
				});
		httpConnThread = new HttpConnThread(activity.siteAppId, 1);
		httpConnThread.setUrl(url);
		httpConnThread.setCachePath("_t/");
		String s = getClass().getSimpleName();
		httpConnThread.setFilter(s);
		DiscuzApp.getInstance().setHttpConnListener(s, httpConnListener);
	}

	private void _claerAdapter()
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
		String _tmp = activity.siteAppId;
		String as[] = new String[2];
		as[0] = activity.siteAppId;
		as[1] = "module=publicpm";
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
		if (pmData == null || pmData.pmList.size() == 0)
		{
			smsAdapter.setList(null);
			smsAdapter.notifyDataSetChanged();
			errorMessage = context.getResources().getString(
					R.string.error_no_sms);
			setListFooter(-301);
			return;
		}
		mpulltorefresh.isLoading = false;
		smsAdapter.setList(pmData);
		smsAdapter.notifyDataSetChanged();
		String s = "1";
		if (pmData.getValueList() != null
				&& pmData.getValueList().get("page") != null)
		{
			DEBUG.o((new StringBuilder()).append("page!!!>>>")
					.append((String) pmData.getValueList().get("page"))
					.toString());
			s = (String) pmData.getValueList().get("page");
		}
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
			DEBUG.o((new StringBuilder()).append("SMSpublic url = ")
					.append(url).toString());
			SiteInfo siteinfo = DiscuzApp.getInstance().getSiteInfo(
					context.siteAppId);
			if (siteinfo != null)
			{
				i = siteinfo.getLoginUser().getUid();
				if (i >= 1)
					break label0;
				_claerAdapter();
				setListFooter(-501);
			}
			return;
		}
		if (uid != (long) i)
		{
			uid = i;
			pmData = null;
			_claerAdapter();
			setListFooter(-101);
			page = 1;
			activity.showLoading(null);
			httpConnThread.setCacheType(1);
		} else if (isShowingLoding)
		{
			activity.showLoading(null);
			_claerAdapter();
			setListFooter(-101);
			isShowingLoding = false;
			page = 1;
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
		mpulltorefresh.isLoading = true;
		DiscuzApp.getInstance().sendHttpConnThread(httpConnThread);
	}

	public void update()
	{
		isPullDownrefresh = true;
		newList();
	}

	private DiscuzBaseActivity activity;
	private Handler handler;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener httpConnListener;
	private HttpConnThread httpConnThread;
	public PmData pmData;
	private SMSPublicAdapter smsAdapter;

}
// 2131296256