package net.discuz.activity.siteview;

import net.discuz.R;
import net.discuz.dialog.AboutDialog;
import net.discuz.dialog.DefaultSiteSettigDialog;
import net.discuz.dialog.NotifySettingDialog;
import net.discuz.dialog.ThreadSortSettingDialog;
import net.discuz.dialog.ThreadViewModeSettingDialog;
import net.discuz.model.SiteInfo;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.GlobalDBHelper;
import net.discuz.source.storage.SiteInfoDBHelper;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.Tools;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class SettingActivity extends DiscuzBaseActivity
{
	public static interface OnSettingChangeListener
	{

		public abstract void onSettingChanged();
	}

	public SettingActivity()
	{
		onSettingChangeListener = new OnSettingChangeListener()
		{

			public void onSettingChanged()
			{
				refreshView();
			}
		};
	}

	private void refreshView()
	{
		String s = GlobalDBHelper.getInstance().getValue("default_jumpsiteid");
		String s1;
		if (Tools.stringIsNullOrEmpty(s) || s.equals("-1"))
			default_jumpsite_show.setText("\u9ED8\u8BA4");
		else if (s.equals("0"))
		{
			default_jumpsite_show
					.setText("\u6700\u540E\u6253\u5F00\u7684\u7AD9\u70B9");
		} else
		{
			SiteInfo siteinfo = SiteInfoDBHelper.getInstance().getByAppId(s);
			if (siteinfo != null)
				default_jumpsite_show.setText(siteinfo.getSiteName());
		}
		s1 = GlobalDBHelper.getInstance().getValue("thread_sort");
		if (Tools.stringIsNullOrEmpty(s1) || s1.equals("dateline_sort"))
			thread_sort_show
					.setText("\u6309\u53D1\u5E03\u65F6\u95F4\u6392\u5E8F");
		else
			thread_sort_show
					.setText("\u6309\u56DE\u590D\u65F6\u95F4\u6392\u5E8F");
		if ("noimage".equals(GlobalDBHelper.getInstance().getValue(
				"threadview_image")))
		{
			viewthread_image_mode_show.setText("\u5F00(ON)");
			return;
		} else
		{
			viewthread_image_mode_show.setText("\u5173(OFF)");
			return;
		}
	}

	protected void init()
	{
		super.init();
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		site_navbar.setTitle("\u8BBE\u7F6E\u4E2D\u5FC3");
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				finish();
			}
		});
		default_jumpsite_show = (TextView) findViewById(R.id.default_jumpsite_show);
		thread_sort_show = (TextView) findViewById(R.id.thread_sort_show);
		viewthread_image_mode_show = (TextView) findViewById(R.id.viewthread_image_mode_show);
		refreshView();
		findViewById(R.id.about_group).setOnClickListener(
				new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						(new AboutDialog(SettingActivity.this)).show();
					}
				});
		findViewById(R.id.umeng_feedback_group).setOnClickListener(
				new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						// UMFeedbackService.setGoBackButtonVisible();
						// UMFeedbackService
						// .openUmengFeedbackSDK(SettingActivity.this);
					}
				});
		findViewById(R.id.thread_sort_group).setOnClickListener(
				new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						ThreadSortSettingDialog threadsortsettingdialog = new ThreadSortSettingDialog(
								SettingActivity.this);
						threadsortsettingdialog
								.setOnSettingChangeListener(onSettingChangeListener);
						threadsortsettingdialog.show();
					}
				});
		findViewById(R.id.viewthread_image_mode_group).setOnClickListener(
				new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						ThreadViewModeSettingDialog threadviewmodesettingdialog = new ThreadViewModeSettingDialog(
								SettingActivity.this);
						threadviewmodesettingdialog
								.setOnSettingChangeListener(onSettingChangeListener);
						threadviewmodesettingdialog.show();
					}
				});
		findViewById(R.id.default_jumpsite_group).setOnClickListener(
				new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						DefaultSiteSettigDialog defaultsitesettigdialog = new DefaultSiteSettigDialog(
								SettingActivity.this);
						defaultsitesettigdialog
								.setOnSettingChangeListener(onSettingChangeListener);
						defaultsitesettigdialog.show();
					}
				});
		findViewById(R.id.notify_setting_group).setOnClickListener(
				new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						(new NotifySettingDialog(SettingActivity.this)).show();
					}
				});
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		MobclickAgent.onEvent(this, "v_setting");
		DiscuzStats.add(null, "v_setting");
		setContentView(R.layout.setting_activity);
		init();
	}

	private TextView default_jumpsite_show;
	OnSettingChangeListener onSettingChangeListener;
	private SiteNavbar site_navbar;
	private TextView thread_sort_show;
	private TextView viewthread_image_mode_show;

}
// 2131296256