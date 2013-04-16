package net.discuz.activity.siteview;

import java.util.ArrayList;
import java.util.List;

import net.discuz.R;
import net.discuz.model.NoticeDetail;
import net.discuz.source.DEBUG;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.GlobalDBHelper;
import net.discuz.source.storage.SiteInfoDBHelper;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.NoticeCenter;
import net.discuz.tools.Tools;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class noticedetaillist extends DiscuzBaseActivity
{
	public static class DetailListAdapter extends BaseAdapter
	{

		public int getCount()
		{
			if (detailList == null)
				return 0;
			else
				return detailList.size();
		}

		public NoticeDetail getItem(int i)
		{
			return (NoticeDetail) detailList.get(i);
		}

		public long getItemId(int i)
		{
			return (long) i;
		}

		public View getView(int i, View view, ViewGroup viewgroup)
		{
			View view1 = LayoutInflater.from(activity).inflate(
					R.layout.noticedetaillist_item, null);
			ImageView imageview = (ImageView) view1
					.findViewById(R.id.sitedetail_siteicon);
			TextView textview = (TextView) view1
					.findViewById(R.id.detail_item_subject);
			TextView textview1 = (TextView) view1
					.findViewById(R.id.detail_item_author);
			TextView textview2 = (TextView) view1
					.findViewById(R.id.detail_item_dateline);
			TextView textview3 = (TextView) view1
					.findViewById(R.id.detail_item_message);
			NoticeDetail noticedetail = (NoticeDetail) detailList.get(i);
			if (noticedetail.readed.intValue() == 0)
			{
				textview.setTextColor(0xff000000);
				textview3.setTextColor(0xff000000);
			}
			DEBUG.o((new StringBuilder()).append("=======DETAIL==SITEICON====")
					.append(noticedetail.siteicon).toString());
			android.graphics.Bitmap bitmap = BitmapFactory
					.decodeFile(noticedetail.siteicon);
			if (bitmap != null)
				imageview.setImageBitmap(bitmap);
			textview.setText(noticedetail.subject);
			textview3.setText(Html.fromHtml((new StringBuilder())
					.append("&nbsp;&nbsp;").append(noticedetail.message.trim())
					.toString()));
			textview1.setText(noticedetail.author);
			textview2.setText(Tools._getNumToDateTime(
					String.valueOf(noticedetail.dateline), "yyyy-MM-dd"));
			return view1;
		}

		public void setDetailList(List list)
		{
			detailList.addAll(list);
			notifyDataSetInvalidated();
		}

		public static List detailList = null;
		private Activity activity;

		public DetailListAdapter(Activity activity1)
		{
			activity = null;
			activity = activity1;
			if (detailList == null)
				detailList = new ArrayList();
		}
	}

	public noticedetaillist()
	{
		footerView = null;
	}

	private void addFooterView(String s)
	{
		if (!Tools.stringIsNullOrEmpty(s))
		{
			if (footerView == null)
			{
				footerView = LayoutInflater.from(this).inflate(
						R.layout.noticedetaillist_more, null);
				if (detail_listView != null
						&& detail_listView.getFooterViewsCount() == 0)
					detail_listView.addFooterView(footerView);
			}
			((TextView) footerView.findViewById(R.id.morenoticedetail_btn))
					.setOnClickListener(new android.view.View.OnClickListener()
					{

						public void onClick(View view)
						{
							NoticeCenter.detail(cloudid, noticedetaillist.this);
						}
					});
		} else if (footerView != null)
		{
			if (detail_listView != null
					&& detail_listView.getFooterViewsCount() > 0)
				detail_listView.removeFooterView(footerView);
			footerView = null;
			return;
		}
	}

	protected void onBackKeyEvent()
	{
		super.onBackKeyEvent();
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		MobclickAgent.onEvent(this, "v_noti_detail");
		DiscuzStats.add(siteAppId, "v_noti_detail");
		setContentView(R.layout.noticedetaillist);
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		site_navbar.setTitleClickable(false);
		site_navbar.setTitle("\u6D88\u606F\u4E2D\u5FC3");
		site_navbar.setCustomBtnText("\u5237\u65B0");
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
						if (DetailListAdapter.detailList != null)
							DetailListAdapter.detailList.clear();
						GlobalDBHelper.getInstance().replace(
								"noticedetail_nextstartid", "");
						NoticeCenter.detail(cloudid, noticedetaillist.this);
					}
				});
		cloudid = getIntent().getStringExtra("cloudid");
		if (Tools.stringIsNullOrEmpty(cloudid))
		{
			ShowMessageByHandler("\u7AD9\u70B9\u4FE1\u606F\u65E0\u6548", 2);
			return;
		}
		if (SiteInfoDBHelper.getInstance().getByCloudId(
				Integer.valueOf(cloudid).intValue()) == null)
		{
			ShowMessageByHandler("\u7AD9\u70B9\u4FE1\u606F\u65E0\u6548", 2);
			return;
		} else
		{
			GlobalDBHelper.getInstance()
					.replace("noticedetail_nextstartid", "");
			detail_listView = (ListView) findViewById(R.id.detail_listView);
			NoticeCenter.detail(cloudid, this);
			return;
		}
	}

	protected void onDestroy()
	{
		detailListAdapter = null;
		site_navbar = null;
		detail_listView = null;
		DetailListAdapter.detailList = null;
		super.onDestroy();
	}

	public void setDetailList(List list, String s)
	{
		dismissLoading();
		if (list != null && list.size() > 0)
		{
			addFooterView(s);
			detailListAdapter = new DetailListAdapter(this);
			detail_listView = (ListView) findViewById(R.id.detail_listView);
			detail_listView.setAdapter(detailListAdapter);
			detailListAdapter.setDetailList(list);
			return;
		} else
		{
			ShowMessageByHandler("\u6682\u65E0\u6570\u636E!", 2);
			return;
		}
	}

	private String cloudid;
	private DetailListAdapter detailListAdapter;
	public ListView detail_listView;
	private View footerView;
	private SiteNavbar site_navbar;

}
// 2131296256