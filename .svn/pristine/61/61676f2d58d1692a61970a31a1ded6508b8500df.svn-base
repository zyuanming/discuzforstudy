package net.discuz.boardcast;

import java.util.HashMap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class HttpConnReceiver extends BroadcastReceiver
{
	public static interface HttpConnListener
	{

		public abstract void onFailed(Exception exception);

		public abstract void onSucceed(String s, String s1);
	}

	public HttpConnReceiver()
	{
		mListeners = new HashMap();
	}

	public void onReceive(Context context, Intent intent)
	{
		String s1;
		String s2;
		HttpConnListener httpconnlistener;
		if (intent.getAction().equals(HTTP_CONN_FILTER))
		{
			Exception exception = (Exception) intent
					.getSerializableExtra("HttpConnException");
			String s = intent.getStringExtra("HttpConnListenerFilter");
			s1 = intent.getStringExtra("Result");
			s2 = intent.getStringExtra("CachePath");
			httpconnlistener = (HttpConnListener) mListeners.get(s);
			Log.d("HttpConnReceiver", "Receiver.onReceive() ****** " + s);
			if (httpconnlistener != null)
			{
				if (s1 == null)
				{
					Log.d("HttpConnReceiver", "failed");
					httpconnlistener.onFailed(exception);
				} else
				{
					Log.d("HttpConnReceiver", "succeed");
					httpconnlistener.onSucceed(s1, s2);
				}
			}
		}
	}

	public void removeHttpConnListener(String s)
	{
		mListeners.remove(s);
	}

	public void setHttpConnListener(String s, HttpConnListener httpconnlistener)
	{
		Log.d("mingming", "Receiver.setHttpConnListener() >>>>> " + s);
		if (httpconnlistener != null)
			mListeners.put(s, httpconnlistener);
	}

	public static final String HTTP_CONN_FILTER = HttpConnReceiver.class
			.getName();
	private HashMap mListeners;

}
// 2131296256