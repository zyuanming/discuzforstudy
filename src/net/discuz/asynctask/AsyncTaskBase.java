package net.discuz.asynctask;

import android.os.AsyncTask;
import net.discuz.source.activity.DiscuzBaseActivity;

public abstract class AsyncTaskBase<Params, Progress, Result> extends
		AsyncTask<Params, Progress, Result>
{

	public AsyncTaskBase(DiscuzBaseActivity discuzbaseactivity)
	{
		activity = null;
		error = null;
		activity = discuzbaseactivity;
	}

	public abstract boolean onException();

	protected DiscuzBaseActivity activity;
	protected Exception error;
}
// 2131296256