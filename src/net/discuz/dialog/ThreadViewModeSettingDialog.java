package net.discuz.dialog;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.source.storage.GlobalDBHelper;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.DiscuzStats;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;

public class ThreadViewModeSettingDialog extends Dialog
{

	public ThreadViewModeSettingDialog(Context context)
	{
		super(context, R.style.ResizableDialogTheme);
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.threadview_noimage_set);
		MobclickAgent.onEvent(getContext(), "v_noimage");
		DiscuzStats.add(null, "v_noimage");
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		site_navbar.setTitle("\u65E0\u56FE\u770B\u5E16\u6A21\u5F0F");
		site_navbar.setLeftBtnType(0);
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				dismiss();
			}
		});
		RelativeLayout relativelayout = (RelativeLayout) findViewById(R.id.threadview_noimage);
		RelativeLayout relativelayout1 = (RelativeLayout) findViewById(R.id.threadview_image);
		if ("noimage".equals(GlobalDBHelper.getInstance().getValue(
				"threadview_image")))
		{
			findViewById(R.id.threadview_noimage_icon).setVisibility(0);
			DiscuzApp.isShowPicture = false;
		} else
		{
			findViewById(R.id.threadview_image_icon).setVisibility(0);
			DiscuzApp.isShowPicture = true;
		}
		relativelayout
				.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						GlobalDBHelper.getInstance().replace(
								"threadview_image", "noimage");
						DiscuzApp.isShowPicture = false;
						onSettingChangeListener.onSettingChanged();
						dismiss();
					}
				});
		relativelayout1
				.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						GlobalDBHelper.getInstance().replace(
								"threadview_image", "image");
						DiscuzApp.isShowPicture = true;
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