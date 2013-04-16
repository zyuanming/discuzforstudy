package net.discuz.dialog;

import java.util.ArrayList;
import java.util.List;

import net.discuz.R;
import net.discuz.model.SiteInfo;
import net.discuz.source.ShowMessage;
import net.discuz.source.InterFace.SucceCallBack;
import net.discuz.source.storage.SiteInfoDBHelper;
import net.discuz.source.widget.Loading;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.NoticeCenter;
import net.discuz.tools.Tools;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.umeng.analytics.MobclickAgent;

public class NotifySettingDialog extends Dialog
{
	public class SiteListAdapter extends BaseAdapter
	{

		public int getCount()
		{
			if (siteInfoList == null)
				return 0;
			else
				return siteInfoList.size();
		}

		public SiteInfo getItem(int i)
		{
			return (SiteInfo) siteInfoList.get(i);
		}

		public long getItemId(int i)
		{
			return (long) i;
		}

		public View getView(final int i, View view, ViewGroup viewgroup)
		{
			View view1 = LayoutInflater.from(context).inflate(
					R.layout.sitenotify_viewitem, null);
			TextView textview = (TextView) view1
					.findViewById(R.id.sitelist_item_info_name);
			CheckBox checkbox = (CheckBox) view1
					.findViewById(R.id.notifytoggle_item);
			SiteInfo siteinfo = (SiteInfo) siteInfoList.get(i);
			textview.setText(siteinfo.getSiteName());
			if (siteinfo.getIsNotify().equals("1"))
				checkbox.setChecked(true);
			else
				checkbox.setChecked(false);
			checkbox.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener()
			{

				public void onCheckedChanged(CompoundButton compoundbutton,
						boolean flag)
				{
					SiteInfo siteinfo = sitelistAdapter.getItem(i);
					if (flag)
					{
						showLoading("\u6B63\u5728\u53D1\u9001\u8BF7\u6C42...");
						Tools.CheckSiteClientMode("notice_check",
								siteinfo.getSiteUrl(), siteinfo.getSiteAppid(),
								callBack);
						return;
					} else
					{
						NoticeCenter.clearToken(siteinfo.getCloudId());
						return;
					}
				}
			});
			return view1;
		}

		public SiteListAdapter()
		{
			super();
		}
	}

	public NotifySettingDialog(Context context1)
	{
		super(context1, R.style.ResizableDialogTheme);
		notifytoggle = null;
		siteInfoList = new ArrayList();
		callBack = new SucceCallBack()
		{

			public void onFaild(Exception exception)
			{}

			public void onsucced(String s)
			{
				dismissLoading();
				SiteInfo siteinfo = SiteInfoDBHelper.getInstance()
						.getByAppId(s);
				if (siteinfo != null
						&& !Tools.stringIsNullOrEmpty(siteinfo.getCloudId())
						&& Tools.strToInt(siteinfo.getCloudId()) > 0)
				{
					NoticeCenter.addToken(siteinfo.getCloudId());
					NoticeCenter.loginToken(siteinfo.getSiteAppid());
					return;
				} else
				{
					ShowMessage
							.getInstance(context)
							._showToast(
									"\u7AD9\u70B9\u672A\u5F00\u901A\u4E91\u5E73\u53F0!",
									2);
					loadFavSiteList();
					sitelistAdapter.notifyDataSetChanged();
					return;
				}
			}

			@Override
			public void onsucced(Object obj)
			{
				onsucced(obj.toString());
			}
		};
		context = context1;
	}

	public void dismissLoading()
	{
		if (loading != null)
			loading.dismissLoading();
	}

	public void loadFavSiteList()
	{
		List list = SiteInfoDBHelper.getInstance().getAllClientView();
		if (list != null && list.size() > 0)
		{
			siteInfoList.clear();
			siteInfoList.addAll(0, list);
			if (sitelistAdapter == null)
				sitelistAdapter = new SiteListAdapter();
			sitelistAdapter.notifyDataSetInvalidated();
		}
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.sitenotify_view);
		MobclickAgent.onEvent(getContext(), "v_pushnotice");
		DiscuzStats.add(null, "v_pushnotice");
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		site_navbar.setTitle("\u7AD9\u70B9\u901A\u77E5");
		site_navbar.setLeftBtnType(0);
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			public void onClick(View view1)
			{
				dismiss();
			}
		});
		favorite_list = (ListView) findViewById(R.id.sitenotify_listView);
		notifytoggle = (ToggleButton) findViewById(R.id.notifytoggle);
		View view;
		if (NoticeCenter.getNoticeSwitch())
		{
			notifytoggle.setChecked(true);
			favorite_list.setVisibility(0);
		} else
		{
			notifytoggle.setChecked(false);
			favorite_list.setVisibility(8);
		}
		notifytoggle
				.setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener()
				{

					public void onCheckedChanged(CompoundButton compoundbutton,
							boolean flag)
					{
						NoticeCenter.setNoticeSwitch(flag);
						sitelistAdapter.notifyDataSetChanged();
						if (flag)
						{
							favorite_list.setVisibility(0);
							return;
						} else
						{
							favorite_list.setVisibility(8);
							return;
						}
					}
				});
		loadFavSiteList();
		sitelistAdapter = new SiteListAdapter();
		view = LayoutInflater.from(context).inflate(
				R.layout.sitenofity_view_footer, null);
		if (favorite_list != null && favorite_list.getFooterViewsCount() == 0)
			favorite_list.addFooterView(view);
		favorite_list.setAdapter(sitelistAdapter);
	}

	public boolean onKeyDown(int i, KeyEvent keyevent)
	{
		if (i == 4)
		{
			if (loading != null && loading.isShown())
				dismissLoading();
			else
				dismiss();
			return true;
		} else
		{
			return super.onKeyDown(i, keyevent);
		}
	}

	public void showLoading(String s)
	{
		if (loading == null)
			loading = new Loading(context,
					(RelativeLayout) findViewById(R.id.DiscuzActivityBox));
		loading.showLoading(s);
	}

	public SucceCallBack callBack;
	private Context context;
	private ListView favorite_list;
	private Loading loading;
	private ToggleButton notifytoggle;
	private List siteInfoList;
	private SiteNavbar site_navbar;
	private SiteListAdapter sitelistAdapter;

}
// 2131296256