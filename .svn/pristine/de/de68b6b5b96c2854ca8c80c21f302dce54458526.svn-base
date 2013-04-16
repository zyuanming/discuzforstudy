package net.discuz.activity.siteview;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.model.SiteInfo;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.extend.SMSPersonalManage;
import net.discuz.source.prototype.extend.SMSPublicManage;
import net.discuz.source.storage.SiteInfoDBHelper;
import net.discuz.tools.Tools;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class smsshow extends DiscuzBaseActivity
{

	public smsshow()
	{}

	private void showPrivateMsg()
	{
		SMSPersonalManage smspersonalmanage = new SMSPersonalManage(this);
		smspersonalmanage.isShowingLoding = true;
		smspersonalmanage.newList();
		smsshow_view.addView(smspersonalmanage.getPullToRefresh());
	}

	private void showPublicMsg()
	{
		SMSPublicManage smspublicmanage = new SMSPublicManage(this);
		smspublicmanage.isShowingLoding = true;
		smspublicmanage.newList();
		smsshow_view.addView(smspublicmanage.getPullToRefresh());
	}

	protected void onBackKeyEvent()
	{
		super.onBackKeyEvent();
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.smsshow);
		smsshow_view = (LinearLayout) findViewById(R.id.smsshow_view);
		Button button = (Button) findViewById(R.id.back);
		button.setVisibility(0);
		((TextView) findViewById(R.id.header_title))
				.setText("\u901A\u77E5\u4E2D\u5FC3");
		button.setOnClickListener(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				finish();
			}
		});
		String s = getIntent().getStringExtra("cloudid");
		if (Tools.stringIsNullOrEmpty(s))
		{
			ShowMessageByHandler("\u7AD9\u70B9\u4FE1\u606F\u65E0\u6548", 2);
			return;
		}
		SiteInfo siteinfo = SiteInfoDBHelper.getInstance().getByCloudId(
				Integer.valueOf(s).intValue());
		if (siteinfo == null)
		{
			ShowMessageByHandler("\u7AD9\u70B9\u4FE1\u606F\u65E0\u6548", 2);
			return;
		}
		siteAppId = siteinfo.getSiteAppid();
		if (DiscuzApp.getInstance().getSiteInfo(siteAppId) == null)
			DiscuzApp.getInstance().setSiteInfo(siteinfo);
		if ("pm".equals(getIntent().getStringExtra("smstype")))
		{
			showPrivateMsg();
			return;
		} else
		{
			showPublicMsg();
			return;
		}
	}

	protected void onDestroy()
	{
		smsshow_view = null;
		super.onDestroy();
	}

	private LinearLayout smsshow_view;
}
// 2131296256