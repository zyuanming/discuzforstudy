package net.discuz.source.timelooper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import net.discuz.app.DiscuzApp;

public class TimeLooper
{

	public TimeLooper()
	{}

	public static void endLoop()
	{
		if (timer != null)
		{
			timer.cancel(pendingIntent);
			if (mTimeLoopReceiver != null)
				DiscuzApp.getInstance().unregisterReceiver(mTimeLoopReceiver);
			pendingIntent = null;
			timer = null;
		}
	}

	public static void initLooper(TimeLoopListener timelooplistener)
	{
		mTimeLoopReceiver = new TimeLoopReceiver(timelooplistener);
		IntentFilter intentfilter = new IntentFilter();
		intentfilter.addAction(TimeLoopReceiver.RECEIVER_KEY);
		DiscuzApp.getInstance().registerReceiver(mTimeLoopReceiver,
				intentfilter);
	}

	public static void startLoop(int i)
	{
		int j;
		if (i < 5)
			j = 5000;
		else
			j = i * 1000;
		if (timer == null)
		{
			timer = (AlarmManager) DiscuzApp.getInstance().getSystemService(
					"alarm");
			Intent intent = new Intent();
			intent.setAction(TimeLoopReceiver.RECEIVER_KEY);
			pendingIntent = PendingIntent.getBroadcast(DiscuzApp.getInstance(),
					0, intent, 0);
		}
		timer.setRepeating(1, System.currentTimeMillis() + (long) j, j,
				pendingIntent);
	}

	private static TimeLoopReceiver mTimeLoopReceiver = null;
	private static PendingIntent pendingIntent = null;
	private static AlarmManager timer = null;

}
//2131296256