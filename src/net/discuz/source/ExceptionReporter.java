package net.discuz.source;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

import net.discuz.tools.Core;
import net.discuz.tools.Tools;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.util.Log;

public final class ExceptionReporter
{
	/**
	 * 
	 * UncaughtExceptionHandler
	 * 线程未捕获异常控制器是用来处理未捕获异常的。如果程序出现了未捕获异常默认情况下则会出现强行关闭对话框。
	 * 通过实现该接口并注册为程序中的默认未捕获异常处理。 这样当未捕获异常发生时，就可以做些异常处理操作，例如：收集异常信息，发送错误报告 等。
	 * 
	 * @author lkh
	 * 
	 */
	private class Handler implements Thread.UncaughtExceptionHandler
	{

		public void uncaughtException(Thread thread, Throwable throwable)
		{
			try
			{
				reportException(thread, throwable, null, false);
			} catch (Exception exception)
			{
				Log.e(ExceptionReporter.TAG, "Error while reporting exception",
						exception);
			}
			subject.uncaughtException(thread, throwable);
		}

		private ExceptionReporter errorHandler;
		private Thread.UncaughtExceptionHandler subject;

		private Handler(Thread.UncaughtExceptionHandler uncaughtexceptionhandler)
		{
			super();
			subject = uncaughtexceptionhandler;
			errorHandler = ExceptionReporter.this;
		}

	}

	/**
	 * 向服务器报告异常的异步任务类
	 * 
	 * @author lkh
	 * 
	 */
	private class reportExceptionTask extends AsyncTask
	{

		protected Object doInBackground(Object aobj[])
		{
			return doInBackground((String[]) aobj);
		}

		protected Void doInBackground(String as[])
		{
			String s = as[0];
			String s1 = Tools._md5(s);
			Core core = Core.getInstance();
			String as1[] = new String[2];
			as1[0] = (new StringBuilder()).append("content=").append(s)
					.toString();
			as1[1] = (new StringBuilder()).append("md5=").append(s1).toString();
			String s2 = core._getParamsSig(as1);
			HttpRequest httprequest = new HttpRequest();
			try
			{
				httprequest
						._post((new StringBuilder())
								.append("http://api.discuz.qq.com/mobile/client/crash?")
								.append(s2).toString(), null, null, null);
			} catch (UnknownHostException unknownhostexception)
			{
				return null;
			} catch (Exception exception)
			{
				return null;
			}
			return null;
		}
	}

	private ExceptionReporter(
			Thread.UncaughtExceptionHandler uncaughtexceptionhandler,
			Context context1)
	{
		handler = new Handler(uncaughtexceptionhandler);
		setContext(context1);
	}

	/**
	 * UncaughtExceptionHandler
	 * 线程未捕获异常控制器是用来处理未捕获异常的。如果程序出现了未捕获异常默认情况下则会出现强行关闭对话框。
	 * 通过实现该接口并注册为程序中的默认未捕获异常处理。 这样当未捕获异常发生时，就可以做些异常处理操作，例如：收集异常信息，发送错误报告 等。
	 * 
	 * @param context1
	 * @return
	 */
	public static ExceptionReporter register(Context context1)
	{
		Thread.UncaughtExceptionHandler uncaughtexceptionhandler = Thread
				.getDefaultUncaughtExceptionHandler();
		if (uncaughtexceptionhandler instanceof Handler)
		{
			Handler handler1 = (Handler) uncaughtexceptionhandler;
			handler1.errorHandler.setContext(context1);
			return handler1.errorHandler;
		} else
		{
			ExceptionReporter exceptionreporter = new ExceptionReporter(
					uncaughtexceptionhandler, context1);
			Thread.setDefaultUncaughtExceptionHandler(exceptionreporter.handler);
			return exceptionreporter;
		}
	}

	private void reportException(Thread thread, Throwable throwable, String s,
			boolean flag)
	{
		StringWriter stringwriter = new StringWriter();
		throwable.printStackTrace(new PrintWriter(stringwriter));
		String s1 = stringwriter.toString();
		(new reportExceptionTask()).execute(new String[] { s1 });
	}

	private void setContext(Context context1)
	{
		if (context1.getApplicationContext() != null)
		{
			context = context1.getApplicationContext();
			return;
		} else
		{
			context = context1;
			return;
		}
	}

	private static final String TAG = ExceptionReporter.class.getSimpleName();
	private static final String reportUrl = "http://api.discuz.qq.com/mobile/client/crash?";
	private ApplicationInfo applicationInfo;
	private Context context;
	private Handler handler;

}
// 2131296256