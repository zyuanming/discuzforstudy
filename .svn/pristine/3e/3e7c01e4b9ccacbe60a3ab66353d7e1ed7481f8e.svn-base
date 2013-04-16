package net.discuz.view;

import android.view.View;
import android.widget.LinearLayout;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.extend.siteview_forumindex_recommendlist;

/**
 * 常去板块，推荐版块视图
 * 
 * @author lkh
 * 
 */
public class ForumRecommendView extends LinearLayout
{

	public ForumRecommendView(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		activity = discuzbaseactivity;
		init();
	}

	public void init()
	{
		if (siteview_forumindex_recommend != null)
		{
			if (isRefresh)
			{
				isRefresh = false;
				siteview_forumindex_recommend.isShowingLoding = true;
				siteview_forumindex_recommend.newList();
			}
		} else
		{
			siteview_forumindex_recommend = new siteview_forumindex_recommendlist(
					activity);
			siteview_forumindex_recommend.newList();
		}
		view = siteview_forumindex_recommend.getPullToRefresh();
		addView(view);
		android.view.ViewGroup.LayoutParams layoutparams = view
				.getLayoutParams();
		layoutparams.width = -1;
		layoutparams.height = -1;
		view.setLayoutParams(layoutparams);
		return;
	}

	private DiscuzBaseActivity activity;
	private boolean isRefresh;
	private siteview_forumindex_recommendlist siteview_forumindex_recommend;
	private View view;
}
// 2131296256