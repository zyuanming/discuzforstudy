package net.discuz.view;

import android.view.View;
import android.widget.LinearLayout;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.extend.siteview_forumindex_forumlist;

public class AllForumView extends LinearLayout
{

	public AllForumView(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		activity = discuzbaseactivity;
		init();
	}

	public void init()
	{
		if (siteview_forumindex_forumlist != null)
		{
			if (isRefresh)
			{
				isRefresh = false;
				siteview_forumindex_forumlist.isShowingLoding = true;
				siteview_forumindex_forumlist.newList();
			}
		} else
		{
			siteview_forumindex_forumlist = new siteview_forumindex_forumlist(
					activity);
			siteview_forumindex_forumlist.newList();
		}
		removeAllViews();
		net.discuz.source.widget.pulltorefresh pulltorefresh = siteview_forumindex_forumlist
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
	private siteview_forumindex_forumlist siteview_forumindex_forumlist;
}