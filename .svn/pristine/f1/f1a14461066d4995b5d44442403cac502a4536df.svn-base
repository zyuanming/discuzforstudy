package net.discuz.view;

import android.view.View;
import android.widget.LinearLayout;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.extend.SMSPersonalManage;

public class MyPrivateMsgView extends LinearLayout
{

	public MyPrivateMsgView(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		smsPersonalMenage = null;
		activity = discuzbaseactivity;
		init();
	}

	public void init()
	{
		if (smsPersonalMenage != null)
		{
			if (isRefresh)
			{
				isRefresh = false;
				smsPersonalMenage.isShowingLoding = true;
			}
		} else
		{
			smsPersonalMenage = new SMSPersonalManage(activity);
		}
		smsPersonalMenage.newList();
		removeAllViews();
		net.discuz.source.widget.pulltorefresh pulltorefresh = smsPersonalMenage
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
	private SMSPersonalManage smsPersonalMenage;
}
//2131296256