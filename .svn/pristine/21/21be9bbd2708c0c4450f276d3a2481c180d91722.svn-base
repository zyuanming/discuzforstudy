package net.discuz.source.prototype.extend;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.discuz.R;
import net.discuz.activity.siteview.siteview_forumsmsdisplay;
import net.discuz.adapter.SMSViewAdapter;
import net.discuz.app.DiscuzApp;
import net.discuz.json.JsonParser;
import net.discuz.model.PmData;
import net.discuz.source.DJsonReader;
import net.discuz.source.ShowMessage;
import net.discuz.source.InterFace.InterfaceOnTouch;
import net.discuz.source.InterFace.JsonParseHelperCallBack;
import net.discuz.source.popupwindow.SmsShowClickUrl;
import net.discuz.source.prototype.sub_pulltorefresh_listview_prototype;
import net.discuz.tools.Core;
import net.discuz.tools.HttpConnThread;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

public class siteview_forumsmsdisplayManage extends
		sub_pulltorefresh_listview_prototype
{

	public siteview_forumsmsdisplayManage(
			siteview_forumsmsdisplay siteview_forumsmsdisplay1, String s, int i)
	{
		super(siteview_forumsmsdisplay1);
		context = null;
		touid = null;
		pmData = null;
		isShowLoadMore = false;
		smsDisplayAdapter = null;
		hashMapUrl = null;
		interfaceOnTouch = null;
		httpConnListener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
		{

			public void onFailed(Exception exception)
			{
				manageException(exception, smsDisplayAdapter);
			}

			public void onSucceed(String s1, String s2)
			{
				try
				{
					DJsonReader localDJsonReader = new DJsonReader(s1);
					localDJsonReader._jsonParse();
					localDJsonReader._debug();
					new JsonParser(siteview_forumsmsdisplayManage.this.context)
							.myPmView(localDJsonReader._getVariables(),
									new JsonParseHelperCallBack()
									{
										public void onParseBegin()
										{}

										public void onParseFinish(
												PmData paramAnonymous2PmData)
										{
											if (isLoadMore)
											{
												paramAnonymous2PmData
														.getPmList()
														.addAll(pmData
																.getPmList());
											}
											pmData = paramAnonymous2PmData;
											context.pmid = ((String) pmData
													.getValueList().get("pmid"));
										}

										@Override
										public void onParseFinish(Object obj)
										{
											onParseFinish((PmData) obj);
										}
									});
					updateUI();
					context.dismissLoading();
				} catch (Exception exception)
				{
					exception.printStackTrace();
					manageException(exception, smsDisplayAdapter);
				}

			}
		};
		context = siteview_forumsmsdisplay1;
		touid = s;
		page = i;
		smsDisplayAdapter = new SMSViewAdapter(context, true);
		smsDisplayAdapter.setSmsPull(this);
		listview.setAdapter(smsDisplayAdapter);
		listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
		{

			public void onItemClick(AdapterView adapterview, View view, int j,
					long l)
			{
				String s1 = (String) smsDisplayAdapter.getItem(j).get("pmid");
				DiscuzApp.getInstance().setIsReadPm(s1);
				String s2 = (String) smsDisplayAdapter.getItem(j)
						.get("message");
				Matcher matcher = Pattern.compile(
						"<a href=\"(.+?)\" (.*?)target=(.*?)\">(.+?)</a>", 32)
						.matcher(s2);
				hashMapUrl = new HashMap();
				String s4;
				String s5;
				for (; matcher.find(); hashMapUrl.put(s4, s5))
				{
					String s3 = matcher.group();
					s4 = s3.substring(2 + s3.indexOf("\">"), s3.indexOf("</a>"));
					s5 = s3.substring(s3.indexOf("http"),
							s3.indexOf("\" target="));
				}

				if (hashMapUrl.size() > 0)
					new SmsShowClickUrl(context, hashMapUrl);
			}
		});
		listview.setOnTouchListener(new android.view.View.OnTouchListener()
		{

			public boolean onTouch(View view, MotionEvent motionevent)
			{
				context.menuDismiss();
				if (interfaceOnTouch != null)
					interfaceOnTouch.DOnTouch();
				return false;
			}
		});
	}

	private void updateUI()
	{
		if (pmData.getPmList().size() == 0)
		{
			smsDisplayAdapter.setList(null);
			smsDisplayAdapter.notifyDataSetChanged();
			errorMessage = context.getResources().getString(
					R.string.error_no_sms);
			setListFooter(-301);
		} else
		{
			smsDisplayAdapter.setList(pmData);
			smsDisplayAdapter.notifyDataSetChanged();
			listview.setSelection(smsDisplayAdapter.getCount());
			String s = "1";
			if (pmData.getValueList() != null
					&& pmData.getValueList().get("page") != null)
				s = (String) pmData.getValueList().get("page");
			page = Integer.valueOf(s).intValue();
			if (page > 1)
			{
				context.handlerUI.sendEmptyMessage(1);
				if (isShowLoadMore)
					setListFooter(-102);
			} else
			{
				context.handlerUI.sendEmptyMessage(2);
				setListFooter(-101);
			}
			if (isPullDownrefresh)
			{
				isPullDownrefresh = false;
				mpulltorefresh.loadedReturnOnAsyncTask();
				ShowMessage.getInstance(context)._showToast(
						R.string.message_fresh_succes, 1);
				return;
			}
		}
	}

	public void loadMore()
	{
		super.loadMore();
		isLoadMore = true;
		page = -1 + page;
		newList();
	}

	public void newList()
	{
		mpulltorefresh.isLoading = true;
		HttpConnThread httpconnthread;
		String s;
		if (isLoadMore)
		{
			String as3[] = new String[5];
			as3[0] = context.siteAppId;
			as3[1] = "module=mypm";
			as3[2] = "subop=view";
			as3[3] = (new StringBuilder()).append("touid=").append(touid)
					.toString();
			as3[4] = (new StringBuilder()).append("page=").append(page)
					.toString();
			url = Core.getSiteUrl(as3);
		} else if (isShowingLoding)
		{
			context.showLoading("\u6B63\u5728\u8BFB\u53D6...");
			String as2[] = new String[4];
			as2[0] = context.siteAppId;
			as2[1] = "module=mypm";
			as2[2] = "subop=view";
			as2[3] = (new StringBuilder()).append("touid=").append(touid)
					.toString();
			url = Core.getSiteUrl(as2);
		} else if (isPullDownrefresh)
		{
			String as1[] = new String[4];
			as1[0] = context.siteAppId;
			as1[1] = "module=mypm";
			as1[2] = "subop=view";
			as1[3] = (new StringBuilder()).append("touid=").append(touid)
					.toString();
			url = Core.getSiteUrl(as1);
		} else
		{
			context.showLoading("\u6B63\u5728\u8BFB\u53D6...");
			String as[] = new String[5];
			as[0] = context.siteAppId;
			as[1] = "module=mypm";
			as[2] = "subop=view";
			as[3] = (new StringBuilder()).append("touid=").append(touid)
					.toString();
			as[4] = (new StringBuilder()).append("page=").append(page)
					.toString();
			url = Core.getSiteUrl(as);
		}
		httpconnthread = new HttpConnThread(context.siteAppId, 1);
		httpconnthread.setUrl(url);
		httpconnthread.setCachePath("_t/");
		s = getClass().getSimpleName();
		httpconnthread.setFilter(s);
		DiscuzApp.getInstance().setHttpConnListener(s, httpConnListener);
		DiscuzApp.getInstance().sendHttpConnThread(httpconnthread);
	}

	public void setOnTouch(InterfaceOnTouch interfaceontouch)
	{
		interfaceOnTouch = interfaceontouch;
	}

	public void setPage(int i)
	{
		page = i;
	}

	public void update()
	{
		isPullDownrefresh = true;
		newList();
	}

	private siteview_forumsmsdisplay context;
	private HashMap hashMapUrl;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener httpConnListener;
	private InterfaceOnTouch interfaceOnTouch;
	public boolean isShowLoadMore;
	public PmData pmData;
	private SMSViewAdapter smsDisplayAdapter;
	private String touid;

}
// 2131296256