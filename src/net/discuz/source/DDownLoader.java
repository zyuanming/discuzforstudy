package net.discuz.source;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.InputStream;
import java.util.HashMap;
import net.discuz.tools.Core;

public class DDownLoader
{

	public DDownLoader(Activity activity1, Core core1)
	{
		core = null;
		httprequest = new HttpRequest();
		inputStream = null;
		activity = activity1;
		core = core1;
	}

	public Boolean _downloadFile(String s, HashMap hashmap, String s1)
	{
		Boolean boolean1;
		try
		{
			httprequest._getFile(s, hashmap, "ISO-8859-1",
					new HttpRequest.requestCallBack()
					{

						public void download(Object obj)
						{
							inputStream = (InputStream) obj;
						}
					});
			if (inputStream != null)
				(new DFile())._writeFile(s1, inputStream);
			boolean1 = Boolean.valueOf(true);
		} catch (Exception exception)
		{
			Log.v("Exception", exception.getMessage());
			return Boolean.valueOf(false);
		}
		return boolean1;
	}

	public boolean _downloadFile(String s, final String fileName)
	{
		try
		{
			DEBUG.o((new StringBuilder()).append("***Url = ").append(s)
					.toString());
			httprequest._getFile(s, null, "ISO-8859-1",
					new HttpRequest.requestCallBack()
					{

						public void download(Object obj)
						{
							(new DFile())._writeFile(fileName,
									(InputStream) obj);
						}
					});
		} catch (Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
		return true;
	}

	public Bitmap _downloadImage(String s, HashMap hashmap, String s1)
	{
		boolean flag = _downloadFile(s, hashmap, s1).booleanValue();
		Bitmap bitmap = null;
		if (flag)
			bitmap = BitmapFactory.decodeFile((new StringBuilder())
					.append("/sdcard/discuz/").append(s1).toString());
		return bitmap;
	}

	private Activity activity;
	private Core core;
	public HttpRequest httprequest;
	public InputStream inputStream;
}
//2131296256