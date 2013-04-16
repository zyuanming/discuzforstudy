package net.discuz.activity.siteview;

import java.util.HashMap;

import net.discuz.R;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.view.FavForumView;
import net.discuz.view.FavThreadView;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 我收藏的帖子和板块
 * 
 * @author lkh
 * 
 */
public class MyFavActivity extends DiscuzBaseActivity
{

	public MyFavActivity()
	{
		onTitleMenuSelected = new net.discuz.source.popupwindow.TopMenuPopupWindow.OnSelectAction()
		{

			public void itemSelected(int i, String s)
			{
				if (s.equals("fav_forum"))
					showFavForumView();
				else if (s.equals("fav_thread"))
				{
					showFavThreadView();
					return;
				}
			}
		};
	}

	private void showFavForumView()
	{
		Object obj = (View) views.get(1);
		if (obj == null)
		{
			obj = new FavForumView(this);
			views.put(1, obj);
		}
		main_container.removeAllViews();
		main_container.addView(((View) (obj)));
	}

	private void showFavThreadView()
	{
		Object obj = (View) views.get(0);
		if (obj == null)
		{
			obj = new FavThreadView(this);
			views.put(0, obj);
		}
		main_container.removeAllViews();
		main_container.addView(((View) (obj)));
	}

	public void finish()
	{
		dismissLoading();
		super.finish();
	}

	protected void init()
	{
		super.init();
		views = new SparseArray();
		main_container = (RelativeLayout) findViewById(R.id.main_container);
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		titleMenuMaps = new HashMap();
		titleMenuMaps.put("fav_forum", "收藏的版块");
		titleMenuMaps.put("fav_thread", "收藏的帖子");
		site_navbar.setTitleMenuMaps(titleMenuMaps);
		site_navbar.setParentView(findViewById(R.id.DiscuzActivityBox));
		site_navbar.setTitleClickable(true);
		site_navbar.setOnTitleMenuSelectAction(onTitleMenuSelected);
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				finish();
			}
		});
		site_navbar.setTitleMenuCheck("fav_thread");
	}

	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		siteAppId = getIntent().getStringExtra("siteappid");
		setContentView(R.layout.my_center_base_activity);
		init();
	}

	protected void onDestroy()
	{
		main_container = null;
		views.clear();
		views = null;
		titleMenuMaps.clear();
		titleMenuMaps = null;
		site_navbar = null;
		super.onDestroy();
	}

	public boolean onKeyDown(int i, KeyEvent keyevent)
	{
		if (i == 4)
		{
			finish();
			return false;
		} else
		{
			return super.onKeyDown(i, keyevent);
		}
	}

	private RelativeLayout main_container;
	private net.discuz.source.popupwindow.TopMenuPopupWindow.OnSelectAction onTitleMenuSelected;
	private SiteNavbar site_navbar;
	private HashMap titleMenuMaps;
	private SparseArray views;

}
// 2131296256