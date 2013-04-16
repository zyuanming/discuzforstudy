package net.discuz.asynctask;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;

import net.discuz.R;
import net.discuz.source.LimitsException;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.sub_pulltorefresh_listview_prototype;
import android.widget.BaseAdapter;

public abstract class PullAsyncTask extends AsyncTaskBase
{

	public PullAsyncTask(
			DiscuzBaseActivity discuzbaseactivity,
			sub_pulltorefresh_listview_prototype sub_pulltorefresh_listview_prototype1)
	{
		super(discuzbaseactivity);
		sub = null;
		sub = sub_pulltorefresh_listview_prototype1;
	}

	public boolean manageException(Exception exception, BaseAdapter baseadapter)
	{
		if (baseadapter == null || activity == null || sub == null)
			return false;
		sub.isPullDownrefresh = false;
		sub.mpulltorefresh.loadedReturnOnAsyncTask();
		if (exception instanceof UnknownHostException)
		{
			if (baseadapter.getCount() > 0)
			{
				activity.ShowMessageByHandler(R.string.message_internet_error,
						3);
			} else
			{
				sub.errorMessage = activity.getResources().getString(
						R.string.error_read);
				sub_pulltorefresh_listview_prototype sub_pulltorefresh_listview_prototype5 = sub;
				sub.getClass();
				sub_pulltorefresh_listview_prototype5.setListFooter(-601);
			}
		} else if (exception instanceof ConnectException)
		{
			if (baseadapter.getCount() > 0)
			{
				activity.ShowMessageByHandler(R.string.message_no_internet_1, 3);
			} else
			{
				sub.errorMessage = activity.getResources().getString(
						R.string.error_read);
				sub_pulltorefresh_listview_prototype sub_pulltorefresh_listview_prototype4 = sub;
				sub.getClass();
				sub_pulltorefresh_listview_prototype4.setListFooter(-601);
			}
		} else if (exception instanceof SocketException)
		{
			if (baseadapter.getCount() > 0)
			{
				activity.ShowMessageByHandler(R.string.message_internet_out, 3);
			} else
			{
				sub.errorMessage = activity.getResources().getString(
						R.string.message_internet_out);
				sub_pulltorefresh_listview_prototype sub_pulltorefresh_listview_prototype3 = sub;
				sub.getClass();
				sub_pulltorefresh_listview_prototype3.setListFooter(-601);
			}
		} else if (exception instanceof LimitsException)
		{
			sub.errorMessage = ((LimitsException) exception).getErrorMessage();
			sub_pulltorefresh_listview_prototype sub_pulltorefresh_listview_prototype2 = sub;
			sub.getClass();
			sub_pulltorefresh_listview_prototype2.setListFooter(-502);
		} else
		{
			sub.errorMessage = activity.getResources().getString(
					R.string.error_read);
			sub_pulltorefresh_listview_prototype sub_pulltorefresh_listview_prototype1 = sub;
			sub.getClass();
			sub_pulltorefresh_listview_prototype1.setListFooter(-301);
		}
		return true;
	}

	private sub_pulltorefresh_listview_prototype sub;
}
// 2131296256