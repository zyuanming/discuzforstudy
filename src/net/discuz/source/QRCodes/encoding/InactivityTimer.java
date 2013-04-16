package net.discuz.source.QRCodes.encoding;

import android.app.Activity;
import java.util.concurrent.*;

public final class InactivityTimer
{
	private static final class DaemonThreadFactory implements ThreadFactory
	{

		public Thread newThread(Runnable runnable)
		{
			Thread thread = new Thread(runnable);
			thread.setDaemon(true);
			return thread;
		}

		private DaemonThreadFactory()
		{}

	}

	public InactivityTimer(Activity activity1)
	{
		inactivityFuture = null;
		activity = activity1;
		onActivity();
	}

	private void cancel()
	{
		if (inactivityFuture != null)
		{
			inactivityFuture.cancel(true);
			inactivityFuture = null;
		}
	}

	public void onActivity()
	{
		cancel();
		inactivityFuture = inactivityTimer.schedule(
				new FinishListener(activity), 300L, TimeUnit.SECONDS);
	}

	public void shutdown()
	{
		cancel();
		inactivityTimer.shutdown();
	}

	private static final int INACTIVITY_DELAY_SECONDS = 300;
	private final Activity activity;
	private ScheduledFuture inactivityFuture;
	private final ScheduledExecutorService inactivityTimer = Executors
			.newSingleThreadScheduledExecutor(new DaemonThreadFactory());
}
// 2131296256