package net.discuz.source.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import net.discuz.boardcast.HttpConnReceiver;
import net.discuz.source.DEBUG;
import net.discuz.tools.HttpConnThread;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 进行网络处理的服务
 * 
 * @author lkh
 * 
 */
public class HttpConnService extends Service
{
	public static interface HttpConnServiceListener
	{

		public abstract void onFinish(HttpConnThread httpconnthread, String s,
				Exception exception);
	}

	public HttpConnService()
	{
		listener = new HttpConnServiceListener()
		{
			public void onFinish(HttpConnThread httpconnthread, String s,
					Exception exception)
			{
				Log.d("HttpConnService",
						"Service Finish callback onFinish() -----> Enter");
				Intent intent = new Intent();
				intent.setAction(HttpConnReceiver.HTTP_CONN_FILTER);
				intent.putExtra("HttpConnListenerFilter",
						httpconnthread.getFilter());
				Log.d("HttpConnService", httpconnthread.getFilter()
						+ " -----> serviceListener.OnFinish()");
				if (s != null)
					intent.putExtra("Result", s);
				if (exception != null)
					intent.putExtra("HttpConnException", exception);
				intent.putExtra("CachePath", httpconnthread.getCachePath());
				sendBroadcast(intent);
				Log.d("HttpConnService",
						"Service Finish callback onFinish() -----> Exit");
			}
		};
	}

	public IBinder onBind(Intent intent)
	{
		return null;
	}

	public void onCreate()
	{
		DEBUG.o("HttpConnService start");
		mHighPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
		mNormalPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
		mLowPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
		super.onCreate();
	}

	public void onDestroy()
	{
		mHighPool.shutdownNow();
		mNormalPool.shutdownNow();
		mLowPool.shutdownNow();
		mNormalPool = null;
		mLowPool = null;
		super.onDestroy();
	}

	public void onStart(Intent paramIntent, int paramInt)
	{
		Log.d("HttpConnSerice", "service onStart() -----> Enter");
		try
		{
			HttpConnThread localHttpConnThread = (HttpConnThread) paramIntent
					.getSerializableExtra("HttpConnThread");
			if (localHttpConnThread != null)
			{
				localHttpConnThread.setHttpConnServiceListener(this.listener);
				switch (localHttpConnThread.getPriority())
				{
				case 0:
					if ((this.mHighPool.getActiveCount() == 1)
							&& (this.mLowPool.getActiveCount() < 2))
					{
						this.mLowPool.execute(localHttpConnThread);
						return;
					}
					if ((this.mHighPool.getActiveCount() == 1)
							&& (this.mNormalPool.getActiveCount() < 3))
					{
						this.mNormalPool.execute(localHttpConnThread);
						return;
					}
					this.mHighPool.execute(localHttpConnThread);
					break;
				case 1:
					if ((this.mNormalPool.getActiveCount() == 3)
							&& (this.mLowPool.getActiveCount() < 2))
					{
						this.mLowPool.execute(localHttpConnThread);
						return;
					}
					this.mNormalPool.execute(localHttpConnThread);
					break;
				case 2:
					this.mLowPool.execute(localHttpConnThread);
					break;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		Log.d("HttpConnSerice", "service onStart() -----> Exit");
	}

	public static final String HTTP_CONN_CACHEPATH = "CachePath";
	public static final String HTTP_CONN_EXCEPTION = "HttpConnException";
	public static final String HTTP_CONN_LISTENER_FILTER = "HttpConnListenerFilter";
	public static final String HTTP_CONN_PARAM_KEYWORD = "HttpConnThread";
	public static final String HTTP_CONN_RESULT = "Result";
	public static final String HTTP_CONN_SERVICE = "Discuz.HttpConnService";
	private final int HIGH_POOL_SIZE = 1;
	private final int LOW_POOL_SIZE = 2;
	private final int NORMAL_POOL_SIZE = 3;
	private HttpConnServiceListener listener;
	private ThreadPoolExecutor mHighPool;
	private ThreadPoolExecutor mLowPool;
	private ThreadPoolExecutor mNormalPool;
}
// 2131296256