package net.discuz.source.timelooper;

import android.content.*;

public class TimeLoopReceiver extends BroadcastReceiver
{

	public TimeLoopReceiver(TimeLoopListener timelooplistener)
	{
		mListener = timelooplistener;
	}

	public void onReceive(Context context, Intent intent)
	{
		if (intent.getAction().equals(RECEIVER_KEY))
			mListener.onAlarm();
	}

	public static final String RECEIVER_KEY = TimeLoopReceiver.class.getName();
	private TimeLoopListener mListener;

}
//2131296256