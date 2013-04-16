package net.discuz.view;

import java.util.HashMap;

import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.tools.DiscuzStats;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;

import com.umeng.analytics.MobclickAgent;

/**
 * 热门板块，全部板块
 * 
 * @author lkh
 * 
 */
public class ForumIndexView extends LinearLayout
{

	public ForumIndexView(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		onTitleMenuSelected = new net.discuz.source.popupwindow.TopMenuPopupWindow.OnSelectAction()
		{

			public void itemSelected(int i, String s)
			{
				if (s.equals("hot_forums"))
					setHotForumView();
				else if (s.equals("all_forums"))
				{
					setAllForumView();
					return;
				}
			}
		};
		activity = discuzbaseactivity;
		views = new SparseArray();
		titleMenuMaps = new HashMap();
		init();
	}

	private void setAllForumView()
	{
		Object obj = (View) views.get(1);
		if (obj == null)
		{
			obj = new AllForumView(activity);
			views.put(1, obj);
		}
		MobclickAgent.onEvent(activity, "v_allfrm");
		DiscuzStats.add(activity.siteAppId, "v_allfrm");
		removeAllViews();
		addView(((View) (obj)));
	}

	private void setHotForumView()
	{
		Object obj = (View) views.get(0);
		if (obj == null)
		{
			obj = new HotForumView(activity);
			views.put(0, obj);
		}
		MobclickAgent.onEvent(activity, "v_hotfrm");
		DiscuzStats.add(activity.siteAppId, "v_hotfrm");
		removeAllViews();
		addView(((View) (obj)));
	}

	public net.discuz.source.popupwindow.TopMenuPopupWindow.OnSelectAction getOnTitleMenuSelected()
	{
		return onTitleMenuSelected;
	}

	public HashMap getTitleMenuMaps()
	{
		return titleMenuMaps;
	}

	public void init()
	{
		titleMenuMaps.put("hot_forums", "热门版块");
		titleMenuMaps.put("all_forums", "全部版块");
	}

	private DiscuzBaseActivity activity;
	private net.discuz.source.popupwindow.TopMenuPopupWindow.OnSelectAction onTitleMenuSelected;
	private HashMap titleMenuMaps;
	private SparseArray views;

}
// 2131296256