package net.discuz.view;

import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.extend.siteview_forumindex_myfavforum;
import net.discuz.tools.DiscuzStats;
import android.widget.LinearLayout;

import com.umeng.analytics.MobclickAgent;

public class FavForumView extends LinearLayout
{

	public FavForumView(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		activity = discuzbaseactivity;
		MobclickAgent.onEvent(activity, "v_myfavfrm");
		DiscuzStats.add(activity.siteAppId, "v_myfavfrm");
		init();
	}

	public void init()
	{
		if (siteview_forumindex_myfavforum != null)
		{
			if (isRefresh)
			{
				isRefresh = false;
				siteview_forumindex_myfavforum.isShowingLoding = true;
			}
		} else
		{
			siteview_forumindex_myfavforum = new siteview_forumindex_myfavforum(
					activity);
		}
		siteview_forumindex_myfavforum.newList();
		removeAllViews();
		net.discuz.source.widget.pulltorefresh pulltorefresh = siteview_forumindex_myfavforum
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
	private siteview_forumindex_myfavforum siteview_forumindex_myfavforum;
}
//2131296256