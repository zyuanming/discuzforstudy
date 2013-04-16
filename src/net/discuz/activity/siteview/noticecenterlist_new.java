package net.discuz.activity.siteview;

import java.util.ArrayList;
import java.util.List;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.model.NoticeListItem;
import net.discuz.model.SiteInfo;
import net.discuz.source.DialogPopup;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.GlobalDBHelper;
import net.discuz.source.storage.NoticeListDBHelper;
import net.discuz.source.storage.SiteInfoDBHelper;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.Tools;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class noticecenterlist_new extends DiscuzBaseActivity
{
	public class NoticeInfo
	{

		public String _id;
		public String cloudid;
		public Integer isclick;
		public String msg;
		public String siteappid;
		public Integer type;

		public NoticeInfo(String s, String s1, Integer integer, String s2,
				Integer integer1)
		{
			super();
			msg = "";
			isclick = Integer.valueOf(0);
			siteappid = s;
			type = integer;
			msg = s2;
			cloudid = s1;
			isclick = integer1;
		}
	}

	public class NoticeListAdapter extends BaseAdapter
	{

		public int getCount()
		{
			if (notice_list == null)
				return 0;
			else
				return notice_list.size();
		}

		public NoticeListItem getItem(int i)
		{
			return (NoticeListItem) notice_list.get(i);
		}

		public long getItemId(int i)
		{
			return (long) i;
		}

		public View getView(int i, View view, ViewGroup viewgroup)
		{
			View view1 = LayoutInflater.from(activity).inflate(
					R.layout.noticelist_grid_item, null);
			final NoticeListItem notice = (NoticeListItem) notice_list.get(i);
			final Intent intent = new Intent();
			view1.findViewById(R.id.noticelist_grid_item_pm)
					.setOnClickListener(new android.view.View.OnClickListener()
					{

						public void onClick(View view)
						{
							MobclickAgent.onEvent(noticecenterlist_new.this,
									"c_noti_mymsg");
							DiscuzStats.add(siteAppId, "c_noti_mymsg");
							notice.pmclick = Integer.valueOf(1);
							SiteInfo siteinfo = SiteInfoDBHelper.getInstance()
									.getByCloudId(
											Integer.valueOf(notice.cloudid)
													.intValue());
							if (siteinfo != null)
							{
								if (DiscuzApp.getInstance().getSiteInfo(
										siteinfo.getSiteAppid()) == null)
								{
									DiscuzApp.getInstance().setSiteInfo(
											siteinfo);
									DiscuzApp.getInstance().loadDBUser(
											siteinfo.getSiteAppid());
								}
								intent.setClass(noticecenterlist_new.this,
										MyMessageActivity.class);
								intent.putExtra("siteappid",
										siteinfo.getSiteAppid());
								NoticeListDBHelper.getInstance().update(notice);
								startActivity(intent);
								return;
							} else
							{
								ShowMessageByHandler(
										"\u7AD9\u70B9\u4FE1\u606F\u65E0\u6548!",
										3);
								return;
							}
						}
					});
			view1.findViewById(R.id.noticelist_grid_item_am)
					.setOnClickListener(new android.view.View.OnClickListener()
					{

						public void onClick(View view)
						{
							notice.stmclick = Integer.valueOf(1);
							intent.setClass(noticecenterlist_new.this,
									noticecenterlist_new.class);
							intent.putExtra("cloudid", notice.cloudid);
							NoticeListDBHelper.getInstance().update(notice);
							startActivity(intent);
						}
					});
			((TextView) view1.findViewById(R.id.noticelist_grid_item_site_name))
					.setText(notice.sitename);
			TextView textview = (TextView) view1
					.findViewById(R.id.noticelist_grid_item_pm_text);
			textview.setText(notice.pm.toString());
			if (notice.pmclick.intValue() == 1)
				textview.setTextColor(0xcccccccc);
			TextView textview1 = (TextView) view1
					.findViewById(R.id.noticelist_grid_item_am_text);
			textview1.setText(notice.stm.toString());
			if (notice.stmclick.intValue() == 1)
				textview1.setTextColor(0xcccccccc);
			View view2 = view1.findViewById(R.id.noticelist_delete_site);
			if (show_delete_site)
			{
				view2.setVisibility(0);
				view2.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						final DialogPopup dialogpopup = new DialogPopup(
								noticecenterlist_new.this);
						dialogpopup._build(0, 0, 0, 0, 0);
						dialogpopup._setMessage((new StringBuilder())
								.append("\u5220\u9664 '")
								.append(notice.sitename)
								.append("' \u7684\u901A\u77E5").toString());
						dialogpopup._setbtnClick(
								new android.view.View.OnClickListener()
								{

									public void onClick(View view)
									{
										NoticeListDBHelper
												.getInstance()
												.deleteByCloudId(notice.cloudid);
										SiteInfo siteinfo = SiteInfoDBHelper
												.getInstance().getByAppId(
														notice.siteappid);
										if (siteinfo != null)
										{
											siteinfo.setIsNotify("0");
											SiteInfoDBHelper
													.getInstance()
													.updateSiteByAppId(
															siteinfo.getSiteAppid(),
															siteinfo);
										}
										noticecenterlist_new.loadNoticeList();
										noticecenterlist_new.noticeListAdapter
												.setNoticeList(noticecenterlist_new.noticeList);
										dialogpopup._dismiss();
									}
								}, new android.view.View.OnClickListener()
								{

									public void onClick(View view)
									{
										dialogpopup._dismiss();
									}
								});
						dialogpopup._show();
					}
				});
				return view1;
			} else
			{
				view2.setVisibility(8);
				return view1;
			}
		}

		public void setNoticeList(List list)
		{
			notice_list = list;
			notifyDataSetInvalidated();
		}

		private Activity activity;
		public List notice_list;

		public NoticeListAdapter(Activity activity1, List list)
		{
			super();
			notice_list = null;
			activity = null;
			activity = activity1;
			notice_list = list;
		}
	}

	public noticecenterlist_new()
	{
		show_delete_site = false;
	}

	public static void loadNoticeList()
	{
		if (noticeList == null)
			noticeList = new ArrayList();
		if (noticeList.size() > 0)
			noticeList.clear();
		noticeList = NoticeListDBHelper.getInstance().getAll();
	}

	public static void setNewNotice()
	{
		String s = GlobalDBHelper.getInstance().getValue("noticenumber");
		if (!Tools.stringIsNullOrEmpty(s) && Integer.valueOf(s).intValue() > 0)
		{
			GlobalDBHelper.getInstance().replace("noticenumber", "0");
			newnotice.setText((new StringBuilder()).append("\u60A8\u6709")
					.append(s).append("\u6761\u65B0\u6D88\u606F").toString());
			noticeLinear.setVisibility(0);
			noticeLinear
					.setOnClickListener(new android.view.View.OnClickListener()
					{

						public void onClick(View view)
						{
							noticecenterlist_new.noticeLinear.setVisibility(8);
							noticecenterlist_new.loadNoticeList();
							noticecenterlist_new.noticeListAdapter
									.setNoticeList(noticecenterlist_new.noticeList);
							noticecenterlist_new.notice_gridView
									.setAdapter(noticecenterlist_new.noticeListAdapter);
						}

					});
		}
	}

	protected void onBackKeyEvent()
	{
		super.onBackKeyEvent();
	}

	protected void onCreate(Bundle bundle)
	{
		if (getIntent().getBooleanExtra("COME_FROM_NOTICE", false))
		{
			MobclickAgent.onEvent(this, "c_sys_notice");
			DiscuzStats.add(null, "c_sys_notice");
		}
		MobclickAgent.onEvent(this, "v_noti_main");
		DiscuzStats.add(null, "v_noti_main");
		((NotificationManager) DiscuzApp.getInstance().getSystemService(
				"notification")).cancel(100);
		GlobalDBHelper.getInstance().replace("noticenumber", "0");
		super.onCreate(bundle);
		setContentView(R.layout.noticelist_new);
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		site_navbar.setTitleClickable(false);
		site_navbar.setTitle("\u901A\u77E5\u4E2D\u5FC3");
		site_navbar.setCustomBtnText("\u7F16\u8F91");
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				finish();
			}
		});
		site_navbar
				.setOnCustomBtnClicked(new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						MobclickAgent.onEvent(noticecenterlist_new.this,
								"c_delnoti");
						DiscuzStats.add(null, "c_delnoti");
						noticecenterlist_new noticecenterlist_new1 = noticecenterlist_new.this;
						boolean flag;
						if (!show_delete_site)
							flag = true;
						else
							flag = false;
						noticecenterlist_new1.show_delete_site = flag;
						noticecenterlist_new.loadNoticeList();
						noticecenterlist_new.noticeListAdapter
								.setNoticeList(noticecenterlist_new.noticeList);
					}
				});
		noticeLinear = (LinearLayout) findViewById(R.id.noticlist_count_layout);
		newnotice = (TextView) findViewById(R.id.newnotice);
		notice_gridView = (GridView) findViewById(R.id.notice_listView);
		loadNoticeList();
		noticeListAdapter = new NoticeListAdapter(this, noticeList);
		notice_gridView.setAdapter(noticeListAdapter);
		if (noticeList == null || noticeList.size() == 0)
			ShowMessageByHandler("\u6682\u65E0\u6D88\u606F!", 2);
	}

	protected void onDestroy()
	{
		noticeList = null;
		noticeListAdapter = null;
		noticeLinear = null;
		newnotice = null;
		notice_gridView = null;
		site_navbar = null;
		super.onDestroy();
	}

	public void onResume()
	{
		super.onResume();
		runOnUiThread(new Runnable()
		{

			public void run()
			{
				noticecenterlist_new.loadNoticeList();
				noticecenterlist_new.noticeListAdapter
						.setNoticeList(noticecenterlist_new.noticeList);
			}
		});
	}

	public static TextView newnotice = null;
	public static LinearLayout noticeLinear = null;
	private static List noticeList = new ArrayList();
	private static NoticeListAdapter noticeListAdapter;
	public static GridView notice_gridView = null;
	private boolean show_delete_site;
	private SiteNavbar site_navbar;
}
// 2131296256