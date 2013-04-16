package net.discuz.view;

import android.view.View;
import android.widget.LinearLayout;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.extend.SMSPublicManage;

public class MyPublicMsgView extends LinearLayout
{

	public MyPublicMsgView(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		smsPublicMenage = null;
		activity = discuzbaseactivity;
		init();
	}

	public void init()
	{
		if (smsPublicMenage != null)
		{
			if (isRefresh)
			{
				isRefresh = false;
				smsPublicMenage.isShowingLoding = true;
			}
		} else
		{
			smsPublicMenage = new SMSPublicManage(activity);
		}
		smsPublicMenage.newList();
		removeAllViews();
		net.discuz.source.widget.pulltorefresh pulltorefresh = smsPublicMenage
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
	private SMSPublicManage smsPublicMenage;
}
//2131296256