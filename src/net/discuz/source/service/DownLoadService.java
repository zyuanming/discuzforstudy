package net.discuz.source.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import net.discuz.R;
import net.discuz.activity.siteview.siteview_downloadmanager;
import net.discuz.asynctask.AsyncTaskBase;
import net.discuz.model.DownFile;
import net.discuz.source.DEBUG;
import net.discuz.source.DFile;
import net.discuz.source.DialogPopup;
import net.discuz.source.ExceptionReporter;
import net.discuz.source.HttpRequest;
import net.discuz.source.ThreadUtils;
import net.discuz.source.activity.DiscuzBaseActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

public class DownLoadService extends Service
{
	public static interface DownLoadInterface
	{
		public abstract void downLoadError(Exception exception);

		public abstract void downLoadSuccess(String s);
	}

	public class DownLoadTask extends AsyncTaskBase<String, Integer, Void>
	{

		private void openFile()
		{
			if (!isCancelled())
			{
				if (downFile.isOpen())
				{
					if (downFile.isNotify())
						activity.ShowMessageByHandler((new StringBuilder())
								.append(downFile.getName()).append("下载完成")
								.toString(), 1);
					if (downFile.isSetUp())
					{
						ThreadUtils.sleep(1000L);
						_showIsUpdatAPk((new StringBuilder())
								.append(downFile.getPath())
								.append(downFile.getName()).toString());
						return;
					}
				}
			}
			(new DFile())._openFile(
					activity,
					(new StringBuilder()).append(downFile.getPath())
							.append(downFile.getName()).toString());
			activity.dismissLoading();
		}

		@Override
		protected Void doInBackground(String as[])
		{
			Log.d("DownLoadService", "doInBackground() -----> Enter");
			try
			{
				this.id = DownLoadService.downLoadId;
				this.downFile.setId(this.id);
				if ((notification != null) && (this.downFile.isNotify()))
				{
					contentView.setTextViewText(R.id.ServiceFileName,
							this.fileName);
					contentView.setTextViewText(R.id.progress_text,
							this.downFile.getPercent() + "%");
					notification.contentView = contentView;
					notification.contentIntent = contentIntent;
					mNM.notify(this.id, notification);
				}
				final HttpRequest localHttpRequest = new HttpRequest();
				DEBUG.o("downLoadUrl:" + this.url);
				localHttpRequest._getFile(this.url, null, "utf-8",
						new HttpRequest.requestCallBack()
						{
							public void download(Object obj)
							{
								int i;
								File file;
								FileOutputStream fileoutputstream = null;
								byte abyte0[];
								int j;
								try
								{
									if (isCancelled())
										return;

									i = localHttpRequest.conn
											.getContentLength();
									downFile.setSize(i);
									file = new File(downFile.getPath());
									if (!file.exists())
										file.mkdirs();
									fileoutputstream = new FileOutputStream(
											new File((new StringBuilder())
													.append(downFile.getPath())
													.append(downFile.getName())
													.toString()));
									abyte0 = new byte[1024];
									if (downFile.isNotify())
										time.schedule(new TimerTask()
										{

											public void run()
											{
												if (!isCancelled())
												{
													while (downFile
															.getPercent() < 100)
													{
														if (downLoadPecent != downFile
																.getPercent())
														{
															downLoadPecent = downFile
																	.getPercent();
															contentView
																	.setTextViewText(
																			R.id.ServiceFileName,
																			fileName);
															contentView
																	.setTextViewText(
																			R.id.progress_text,
																			(new StringBuilder())
																					.append(downFile
																							.getPercent())
																					.append("%")
																					.toString());
															notification.contentView = contentView;
															notification.contentIntent = contentIntent;
															mNM.notify(id,
																	notification);
														}
													}
												} else
												{
													downFile = null;
													mNM.cancel(id);
													cancel();
												}
											}

										}, 1000L, 1000L);
									while (true)
									{
										j = ((InputStream) obj).read(abyte0, 0,
												1024);
										if (j == -1)
											break;
										if (!isCancelled())
										{
											downFile.addProgress(j);
											downFile.setPercent((int) (100F * ((float) downFile
													.getProgress() / Float
													.valueOf(i).floatValue())));
											fileoutputstream
													.write(abyte0, 0, j);
										}
									}
								} catch (Exception ie)
								{
									ie.printStackTrace();
								} finally
								{
									if (fileoutputstream != null)
									{
										try
										{
											fileoutputstream.close();
										} catch (Exception e)
										{
											e.printStackTrace();
										}
									}
								}
							}
						});
			} catch (Exception localException)
			{
				this.error = localException;
			}
			Log.d("DownLoadService", "doInBackground() -----> Exit");
			return null;
		}

		protected void onCancelled()
		{
			super.onCancelled();
			if (AsyncTaskList != null)
				AsyncTaskList.remove(this);
			if (DownLoadService.downMap != null)
				DownLoadService.downMap.remove(url);
		}

		public boolean onException()
		{
			if (!(error instanceof FileNotFoundException))
			{
				if (error instanceof IOException)
					activity.ShowMessageByHandler("下载失败", 3);
				else if ((error instanceof UnknownHostException)
						|| (error instanceof ConnectException)
						|| (error instanceof SocketException))
					activity.ShowMessageByHandler("下载失败，请检查网络", 3);
			} else
			{
				activity.ShowMessageByHandler("下载失败", 3);
			}
			if (mNM != null)
				mNM.cancel(id);
			DownLoadService.downMap.remove(url);
			activity.dismissLoading();
			error.printStackTrace();
			return false;
		}

		/**
		 * 后台线程操作完后，有UI线程调用
		 * 
		 * @param void1
		 */
		@Override
		protected void onPostExecute(Void paramVoid)
		{
			Log.d("DownLoadService", "onPostExecute() ------> Enter");
			if (error != null)
			{
				if (downFile.isNotify())
					onException();
				if (downFile.getDownLoadInterface() != null)
					downFile.getDownLoadInterface().downLoadError(error);
			} else if (downFile != null)
			{
				openFile();
				if (downFile.getDownLoadInterface() != null)
					downFile.getDownLoadInterface().downLoadSuccess(
							(new StringBuilder()).append(filePath)
									.append(fileName).toString());
				onCancelled();
				Log.d("DownLoadService", "onPostExecute() ------> Exit");
				return;
			}
		}

		private DownFile downFile;
		private int downLoadPecent;
		private String fileName;
		private String filePath;
		private int id;
		private String url;

		public DownLoadTask(DiscuzBaseActivity discuzbaseactivity, String s)
		{
			super(discuzbaseactivity);
			id = 0;
			url = null;
			fileName = null;
			filePath = null;
			downFile = null;
			downLoadPecent = 0;
			url = s;
			downFile = (DownFile) DownLoadService.downMap.get(s);
			fileName = downFile.getName();
			filePath = downFile.getPath();
		}
	}

	public DownLoadService()
	{
		downLoadId = 0;
		AsyncTaskList = new LinkedList();
		time = new Timer();
	}

	private void _showIsUpdatAPk(final String fileName)
	{
		final DialogPopup dialog = new DialogPopup(activity);
		dialog._build(0, 0, 0, 0, 0);
		dialog._setMessage(R.string.message_system_setup);
		dialog._setbtnClick(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				dialog._dismiss();
				(new DFile())._openFile(DownLoadService.activity, fileName);
				DownLoadService.activity.dismissLoading();
				DownLoadService.activity
						.getSharedPreferences("application_tab", 0).edit()
						.putBoolean("is_need_update", false).commit();
			}
		}, new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				dialog._dismiss();
			}
		});
		dialog._show();
	}

	private void clearAsyncTaskList()
	{
		if (AsyncTaskList != null)
		{
			if (AsyncTaskList.size() > 0)
			{
				for (int i = 0; i < AsyncTaskList.size(); i++)
					((AsyncTask) AsyncTaskList.get(i)).cancel(true);

				AsyncTaskList.clear();
			}
			AsyncTaskList = null;
		}
		downMap.clear();
	}

	public static void setDownLoadParams(DiscuzBaseActivity discuzbaseactivity,
			DownFile downfile)
	{
		Log.d("DownLoadService",
				"setDownLoadParams() ----> " + downfile.getUrl());
		synchronized (lockhelper)
		{
			if (downMap.get(downfile.getUrl()) == null)
			{
				downMap.put(downfile.getUrl(), downfile);
				list.add(downfile.getUrl());
			}
			activity = discuzbaseactivity;
		}
	}

	public IBinder onBind(Intent intent)
	{
		return null;
	}

	public void onCreate()
	{
		Log.d("DownLoadService", "onCreate() -------> Enter");
		ExceptionReporter.register(this);
		mNM = (NotificationManager) getSystemService("notification");
		notification = new Notification(0x1080081, null,
				System.currentTimeMillis());
		contentIntent = PendingIntent.getActivity(this, 1, new Intent(this,
				siteview_downloadmanager.class), 1);
		contentView = new RemoteViews(getPackageName(),
				R.layout.service_message);
		Log.d("DownLoadService", "onCreate() -------> Exit");
	}

	public void onDestroy()
	{
		super.onDestroy();
		if (mNM != null)
			mNM.cancelAll();
		clearAsyncTaskList();
	}

	public int onStartCommand(Intent intent, int i, int j)
	{
		Log.d("DownLoadService", "onStartCommand() -----> Enter");
		if (list.size() > 0)
		{
			Log.d("DownLoadService", "list.size() > 0" + " : " + list.size());
			AsyncTaskList
					.add((new DownLoadTask(activity, (String) list.get(0)))
							.execute(new String[0]));
			list.remove(0);
			ThreadUtils.sleep(500L);
		}
		Log.d("DownLoadService", "onStartCommand() -----> Exit");
		return 0;
	}

	private static DiscuzBaseActivity activity;
	private static HashMap downMap = new HashMap();
	private static ArrayList list = new ArrayList();
	public static Object lockhelper = new Object();
	private LinkedList AsyncTaskList;
	private PendingIntent contentIntent;
	private RemoteViews contentView;
	private static int downLoadId;
	private NotificationManager mNM;
	private Notification notification;
	private Timer time;

}