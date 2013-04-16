package net.discuz.dialog;

import net.discuz.R;
import net.discuz.source.storage.GlobalDBHelper;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.Tools;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;

public class ThreadSortSettingDialog extends Dialog
{

	public ThreadSortSettingDialog(Context context)
	{
		super(context, R.style.ResizableDialogTheme);
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.threadsort_set);
		MobclickAgent.onEvent(getContext(), "v_listorder");
		DiscuzStats.add(null, "v_listorder");
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		site_navbar.setTitle("\u4E3B\u9898\u5217\u8868\u6392\u5E8F");
		site_navbar.setLeftBtnType(0);
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				dismiss();
			}
		});
		RelativeLayout relativelayout = (RelativeLayout) findViewById(R.id.dateline_sort);
		RelativeLayout relativelayout1 = (RelativeLayout) findViewById(R.id.lastpost_sort);
		String s = GlobalDBHelper.getInstance().getValue("thread_sort");
		if (Tools.stringIsNullOrEmpty(s) || s.equals("dateline_sort"))
			findViewById(R.id.dateline_sort_icon).setVisibility(0);
		else
			findViewById(R.id.lastpost_sort_icon).setVisibility(0);
		relativelayout
				.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						GlobalDBHelper.getInstance().replace("thread_sort",
								"dateline_sort");
						onSettingChangeListener.onSettingChanged();
						dismiss();
					}
				});
		relativelayout1
				.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						GlobalDBHelper.getInstance().replace("thread_sort",
								"lastpost_sort");
						onSettingChangeListener.onSettingChanged();
						dismiss();
					}
				});
	}

	public void setOnSettingChangeListener(
			net.discuz.activity.siteview.SettingActivity.OnSettingChangeListener onsettingchangelistener)
	{
		onSettingChangeListener = onsettingchangelistener;
	}

	private net.discuz.activity.siteview.SettingActivity.OnSettingChangeListener onSettingChangeListener;
	private SiteNavbar site_navbar;

}
// 2131296256