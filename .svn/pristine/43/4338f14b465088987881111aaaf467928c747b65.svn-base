package net.discuz.view;

import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.extend.siteview_forumindex_myfavthread;
import net.discuz.source.widget.pulltorefresh;
import net.discuz.tools.DiscuzStats;
import android.widget.LinearLayout;

import com.umeng.analytics.MobclickAgent;

public class FavThreadView extends LinearLayout
{

	public FavThreadView(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		activity = discuzbaseactivity;
		MobclickAgent.onEvent(activity, "v_favtrd");
		DiscuzStats.add(activity.siteAppId, "v_favtrd");
		init();
	}

	public void init()
	{
		if (siteview_forumindex_myfavthread != null)
		{
			if (isRefresh)
			{
				isRefresh = false;
				siteview_forumindex_myfavthread.isShowingLoding = true;
			}
		} else
		{
			siteview_forumindex_myfavthread = new siteview_forumindex_myfavthread(
					activity);
		}
		siteview_forumindex_myfavthread.newList();
		removeAllViews();
		pulltorefresh pulltorefresh = siteview_forumindex_myfavthread
				.getPullToRefresh();
		addView(pulltorefresh);
		android.view.ViewGroup.LayoutParams layoutparams = pulltorefresh
				.getLayoutParams();
		layoutparams.width = -1;
		layoutparams.height = -1;
		pulltorefresh.setLayoutParams(layoutparams);
		return;
	}

	private DiscuzBaseActivity activity;
	private boolean isRefresh;
	private siteview_forumindex_myfavthread siteview_forumindex_myfavthread;
}
// 2131296256