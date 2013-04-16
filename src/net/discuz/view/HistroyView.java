package net.discuz.view;

import java.util.ArrayList;
import java.util.List;

import net.discuz.R;
import net.discuz.activity.siteview.SettingActivity;
import net.discuz.activity.siteview.SiteViewActivity;
import net.discuz.adapter.SiteGridAdapter;
import net.discuz.model.SiteInfo;
import net.discuz.source.ClearCache;
import net.discuz.source.DFile;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.GlobalDBHelper;
import net.discuz.source.storage.NoticeListDBHelper;
import net.discuz.source.storage.SiteInfoDBHelper;
import net.discuz.source.storage.TopSiteDBHelper;
import net.discuz.source.storage.UserSessionDBHelper;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.NoticeCenter;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;

import com.umeng.analytics.MobclickAgent;

/**
 * 访问过的历史站点列表，网格形式显示
 * 
 * @author Ming
 * 
 */
public class HistroyView extends LinearLayout
{

	public HistroyView(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		favGridItemClickListener = new android.widget.AdapterView.OnItemClickListener()
		{

			public void onItemClick(AdapterView adapterview, View view, int i,
					long l)
			{
				Core.visitSite(activity, favAdapter.getItem(i));
			}
		};
		histroyGridItemClickListener = new android.widget.AdapterView.OnItemClickListener()
		{

			public void onItemClick(AdapterView adapterview, View view, int i,
					long l)
			{
				MobclickAgent.onEvent(activity, "c_siteindex_history");
				DiscuzStats.add(null, "c_siteindex_history");
				Core.visitSite(activity, historyAdapter.getItem(i));
			}
		};
		onItemLongClickListener = new android.widget.AdapterView.OnItemLongClickListener()
		{

			public boolean onItemLongClick(AdapterView adapterview, View view,
					final int i, long l)
			{
				final String siteAppId = historyAdapter.getItem(i)
						.getSiteAppid();
				if (siteAppId != null)
				{
					View view1 = activity.getLayoutInflater().inflate(
							R.layout.sitelist_info, null);
					popupwindow = new PopupWindow(view1, -2, -2, true);
					popupwindow.setBackgroundDrawable(getResources()
							.getDrawable(R.drawable.alpha_bg));
					popupwindow.setOutsideTouchable(true);
					popupwindow.setAnimationStyle(R.style.ImagePopupAnimation);
					popupwindow.showAtLocation(
							findViewById(R.id.DiscuzActivityBox), 17, 0, 0);
					final SiteInfo site = SiteInfoDBHelper.getInstance()
							.getByAppId(siteAppId);
					if (site != null)
					{
						View view2 = view1
								.findViewById(R.id.sitelist_info_select_del);
						View view3 = view1
								.findViewById(R.id.sitelist_info_select_home);
						view2.setOnClickListener(new android.view.View.OnClickListener()
						{

							public void onClick(View view)
							{
								MobclickAgent.onEvent(activity, "c_delete_app");
								DiscuzStats.add(null, "c_delete_app");
								ClearCache.clearTempData(siteAppId);
								ClearCache._clearIcon(siteAppId);
								SiteInfoDBHelper.getInstance()
										.delete(siteAppId);
								UserSessionDBHelper.getInstance()
										.deleteByAppId(siteAppId);
								NoticeCenter.clearToken(site.getCloudId());
								NoticeCenter.logoutToken(siteAppId);
								NoticeListDBHelper.getInstance()
										.deleteByCloudId(site.getCloudId());
								(new DFile())
										._deleteSDFile((new StringBuilder())
												.append("style/siteicon_icon_")
												.append(siteAppId)
												.append(".png").toString());
								activity.ShowMessageByHandler(
										R.string.message_sitelist_del_sucessed,
										1);
								popupwindow.dismiss();
								historySiteList.remove(i);
								historyAdapter.notifyDataSetChanged();
							}
						});
						view3.setOnClickListener(new android.view.View.OnClickListener()
						{

							public void onClick(View view)
							{
								Intent intent = new Intent();
								intent.setClass(activity,
										SiteViewActivity.class);
								intent.putExtra("siteappid", siteAppId);
								activity.startActivity(intent);
								if (popupwindow.isShowing())
									popupwindow.dismiss();
								if ("1".equals(site.getClientview()))
									GlobalDBHelper.getInstance().replace(
											"sitelist_backsiteid", siteAppId);
							}
						});
					}
				}
				return true;
			}
		};
		handler = new Handler();
		activity = discuzbaseactivity;
		favSiteList = new ArrayList();
		historySiteList = new ArrayList();
		init();
	}

	private void loadSiteFavList()
	{
		List list = TopSiteDBHelper.getInstance().getByNameUrl("", 16);
		if (list != null && list.size() > 0)
		{
			favSiteList.addAll(list);
			if (favAdapter == null)
				favAdapter = new SiteGridAdapter(activity, favSiteList);
			favAdapter.notifyDataSetInvalidated();
			favorite_grid.invalidate();
		}
	}

	private void setListViewHeightBasedOnChildren(GridView paramGridView)
	{
		ListAdapter localListAdapter = paramGridView.getAdapter();
		if (localListAdapter == null)
			return;
		int i = localListAdapter.getCount() / 4;
		if (localListAdapter.getCount() % 4 > 0)
			i++;
		int j = 0;
		int k = 0;
		while (j < i)
		{
			View localView = localListAdapter.getView(j, null, paramGridView);
			localView.measure(0, 0);
			k += localView.getMeasuredHeight();
			j++;
		}
		ViewGroup.LayoutParams localLayoutParams = paramGridView
				.getLayoutParams();
		localLayoutParams.height = k;
		localLayoutParams.height = (8 + localLayoutParams.height);
		paramGridView.setLayoutParams(localLayoutParams);
	}

	public void init()
	{
		View view = LayoutInflater.from(activity).inflate(
				R.layout.histroy_view, null);
		addView(view);
		android.view.ViewGroup.LayoutParams layoutparams = view
				.getLayoutParams();
		layoutparams.width = -1;
		layoutparams.height = -1;
		view.setLayoutParams(layoutparams);
		favorite_grid = (GridView) view.findViewById(R.id.favorite_grid);
		history_grid = (GridView) view.findViewById(R.id.history_grid);
		setting_btn = view.findViewById(R.id.setting_btn);
		historyAdapter = new SiteGridAdapter(activity, historySiteList);
		history_grid.setAdapter(historyAdapter);
		history_grid.setOnItemClickListener(histroyGridItemClickListener);
		history_grid.setOnItemLongClickListener(onItemLongClickListener);
		favAdapter = new SiteGridAdapter(activity, favSiteList);
		favorite_grid.setAdapter(favAdapter);
		favorite_grid.setOnItemClickListener(favGridItemClickListener);
		setting_btn.setOnClickListener(new android.view.View.OnClickListener()
		{

			public void onClick(View view1)
			{
				Intent intent = new Intent();
				intent.setClass(activity, SettingActivity.class);
				activity.startActivity(intent);
			}
		});
	}

	public void loadSiteHistoryList()
	{
		List list = SiteInfoDBHelper.getInstance().getAll();
		if (list != null && list.size() > 0)
		{
			historySiteList.addAll(list);
			if (historyAdapter == null)
				historyAdapter = new SiteGridAdapter(activity, historySiteList);
			historyAdapter.notifyDataSetInvalidated();
			history_grid.invalidate();
		}
	}

	public void refresh()
	{
		handler.postDelayed(new Runnable()
		{

			public void run()
			{
				favSiteList.clear();
				loadSiteFavList();
				historySiteList.clear();
				loadSiteHistoryList();
			}
		}, 600L);
	}

	private DiscuzBaseActivity activity;
	private SiteGridAdapter favAdapter;
	android.widget.AdapterView.OnItemClickListener favGridItemClickListener;
	private List favSiteList;
	private GridView favorite_grid;
	private Handler handler;
	private SiteGridAdapter historyAdapter;
	private List historySiteList;
	private GridView history_grid;
	android.widget.AdapterView.OnItemClickListener histroyGridItemClickListener;
	android.widget.AdapterView.OnItemLongClickListener onItemLongClickListener;
	private PopupWindow popupwindow;
	private View setting_btn;
}
// 2131296256