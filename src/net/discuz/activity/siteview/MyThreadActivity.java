package net.discuz.activity.siteview;

import net.discuz.R;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.extend.siteview_forumindex_mythread;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.DiscuzStats;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;

public class MyThreadActivity extends DiscuzBaseActivity
{

	public MyThreadActivity()
	{
		siteview_forumindex_mythread = null;
		isRefresh = false;
	}

	public void finish()
	{
		dismissLoading();
		super.finish();
	}

	protected void init()
	{
		super.init();
		main_container = (RelativeLayout) findViewById(R.id.main_container);
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		site_navbar.setTitle("\u6211\u7684\u4E3B\u9898");
		if (siteview_forumindex_mythread != null)
		{
			if (isRefresh)
			{
				isRefresh = false;
				siteview_forumindex_mythread.isShowingLoding = true;
			}
		} else
		{
			siteview_forumindex_mythread = new siteview_forumindex_mythread(
					this);
		}
		siteview_forumindex_mythread.newList();
		main_container.addView(siteview_forumindex_mythread.getPullToRefresh());
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				finish();
			}
		});
	}

	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		siteAppId = getIntent().getStringExtra("siteappid");
		setContentView(R.layout.my_center_base_activity);
		MobclickAgent.onEvent(this, "v_mytrd");
		DiscuzStats.add(siteAppId, "v_mytrd");
		init();
	}

	protected void onDestroy()
	{
		site_navbar = null;
		main_container = null;
		siteview_forumindex_mythread = null;
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

	private boolean isRefresh;
	private RelativeLayout main_container;
	private SiteNavbar site_navbar;
	private siteview_forumindex_mythread siteview_forumindex_mythread;
}
// 2131296256