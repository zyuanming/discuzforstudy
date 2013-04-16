package net.discuz.boardcast;

import android.content.*;
import net.discuz.app.DiscuzApp;
import net.discuz.tools.NoticeCenter;

public class BootReceiver extends BroadcastReceiver
{

	public BootReceiver()
	{}

	public void onReceive(Context context, Intent intent)
	{
		if (NoticeCenter.getNoticeSwitch())
		{
			DiscuzApp.getInstance().sendHttpConnThread(null);
			return;
		} else
		{
			DiscuzApp.getInstance().stopHttpConnService();
			DiscuzApp.getInstance().onTerminate();
			return;
		}
	}
}
//2131296256