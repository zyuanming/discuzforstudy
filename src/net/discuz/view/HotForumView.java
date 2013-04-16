package net.discuz.view;

import android.view.View;
import android.widget.LinearLayout;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.extend.siteview_forumindex_hotforum;

public class HotForumView extends LinearLayout
{

	public HotForumView(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		activity = discuzbaseactivity;
		init();
	}

	public void init()
	{
		if (siteview_forumindex_hotforum != null)
		{
			if (isRefresh)
			{
				isRefresh = false;
				siteview_forumindex_hotforum.isShowingLoding = true;
				siteview_forumindex_hotforum.newList();
			}
		} else
		{
			siteview_forumindex_hotforum = new siteview_forumindex_hotforum(
					activity);
			siteview_forumindex_hotforum.newList();
		}
		removeAllViews();
		net.discuz.source.widget.pulltorefresh pulltorefresh = siteview_forumindex_hotforum
				.getPullToRefresh();
		addView(pulltorefresh);
		android.view.ViewGroup.LayoutParams layoutparams = pulltorefresh
				.getLayoutParams();
		layoutparams.width = -1;
		layoutparams.height = -1;
		pulltorefresh.setLayoutParams(layoutparams);
		return;
	}

	protected static DiscuzBaseActivity activity;
	private boolean isRefresh;
	private siteview_forumindex_hotforum siteview_forumindex_hotforum;
}
//2131296256