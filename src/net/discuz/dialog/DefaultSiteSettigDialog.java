package net.discuz.dialog;

import java.util.ArrayList;
import java.util.List;

import net.discuz.R;
import net.discuz.model.SiteInfo;
import net.discuz.source.storage.GlobalDBHelper;
import net.discuz.source.storage.SiteInfoDBHelper;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.Tools;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class DefaultSiteSettigDialog extends Dialog
{
	public class DefaultJumpSiteListAdapter extends BaseAdapter
	{

		public int getCount()
		{
			if (siteInfo_ArrayList == null)
				return 0;
			else
				return siteInfo_ArrayList.size();
		}

		public SiteInfo getItem(int i)
		{
			return (SiteInfo) siteInfo_ArrayList.get(i);
		}

		public long getItemId(int i)
		{
			return (long) i;
		}

		public View getView(int i, View view, ViewGroup viewgroup)
		{
			View view1 = LayoutInflater.from(contenxt).inflate(
					R.layout.default_jumpsitelist_item, null);
			TextView textview = (TextView) view1
					.findViewById(R.id.sitelist_item_info_name);
			ImageView imageview = (ImageView) view1
					.findViewById(R.id.default_jumpsitelist_item_icon);
			SiteInfo siteinfo = (SiteInfo) siteInfo_ArrayList.get(i);
			textview.setText(siteinfo.getSiteName());
			if (Tools.stringIsNullOrEmpty(default_jumpsiteid) && i == 0)
			{
				imageview.setVisibility(0);
				return view1;
			}
			if (siteinfo.getSiteAppid().equals(default_jumpsiteid))
			{
				imageview.setVisibility(0);
				return view1;
			} else
			{
				imageview.setVisibility(8);
				return view1;
			}
		}

		public void setSiteListInfo_ArrayList(List list)
		{
			siteInfo_ArrayList = list;
			notifyDataSetChanged();
		}

		private Context contenxt;
		public List siteInfo_ArrayList;

		public DefaultJumpSiteListAdapter(Context context, List list)
		{
			siteInfo_ArrayList = null;
			contenxt = null;
			contenxt = context;
			siteInfo_ArrayList = list;
		}
	}

	public DefaultSiteSettigDialog(Context context)
	{
		super(context, R.style.ResizableDialogTheme);
		siteInfo_ArrayList = new ArrayList();
	}

	public void loadFavSiteList()
	{
		List list = SiteInfoDBHelper.getInstance().getAllClientView();
		if (list != null && list.size() > 0)
		{
			siteInfo_ArrayList.addAll(0, list);
			SiteInfo siteinfo = new SiteInfo();
			siteinfo.setSiteName("\u6700\u540E\u6253\u5F00\u7684\u7AD9\u70B9");
			siteinfo.setSiteAppid("0");
			siteInfo_ArrayList.add(0, siteinfo);
			SiteInfo siteinfo1 = new SiteInfo();
			siteinfo1.setSiteName("\u9ED8\u8BA4");
			siteinfo1.setSiteAppid("-1");
			siteInfo_ArrayList.add(0, siteinfo1);
			if (sitelistAdapter == null)
				sitelistAdapter = new DefaultJumpSiteListAdapter(getContext(),
						siteInfo_ArrayList);
			sitelistAdapter.notifyDataSetInvalidated();
		}
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.default_jumpsiteset);
		MobclickAgent.onEvent(getContext(), "v_defaultsite");
		DiscuzStats.add(null, "v_defaultsite");
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		site_navbar.setTitle("\u9ED8\u8BA4\u8FDB\u5165");
		site_navbar.setLeftBtnType(0);
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				dismiss();
			}
		});
		default_jumpsiteid = GlobalDBHelper.getInstance().getValue(
				"default_jumpsiteid");
		sitelistAdapter = new DefaultJumpSiteListAdapter(getContext(),
				siteInfo_ArrayList);
		favorite_list = (ListView) findViewById(R.id.default_jumpsite_listView);
		favorite_list.setAdapter(sitelistAdapter);
		favorite_list
				.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
				{

					public void onItemClick(AdapterView adapterview, View view,
							int i, long l)
					{
						String s = sitelistAdapter.getItem(i).getSiteAppid();
						GlobalDBHelper.getInstance().replace(
								"default_jumpsiteid", s);
						onSettingChangeListener.onSettingChanged();
						dismiss();
					}
				});
		loadFavSiteList();
	}

	public void setOnSettingChangeListener(
			net.discuz.activity.siteview.SettingActivity.OnSettingChangeListener onsettingchangelistener)
	{
		onSettingChangeListener = onsettingchangelistener;
	}

	private String default_jumpsiteid;
	private ListView favorite_list;
	private net.discuz.activity.siteview.SettingActivity.OnSettingChangeListener onSettingChangeListener;
	private List siteInfo_ArrayList;
	private SiteNavbar site_navbar;
	private DefaultJumpSiteListAdapter sitelistAdapter;

}
// 2131296256