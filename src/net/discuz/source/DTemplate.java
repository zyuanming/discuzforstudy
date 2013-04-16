package net.discuz.source;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import java.io.*;

public class DTemplate
{

	public DTemplate(Activity activity1)
	{
		activity = null;
		tpl_suffix = ".html";
		activity = activity1;
	}

	public String _loadTplFile(String s)
	{
		StringBuffer stringbuffer = new StringBuffer();
		try
		{
			InputStreamReader inputstreamreader = new InputStreamReader(
					activity.getResources()
							.getAssets()
							.open((new StringBuilder()).append("template/")
									.append(s).append(tpl_suffix).toString()),
					"utf-8");
			int i;
			try
			{
				while ((i = inputstreamreader.read()) > 0)
					stringbuffer.append((char) i);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		} catch (Exception e)
		{
			Log.v("FileNotFoundException", "Template File Not Found");
		}

		return stringbuffer.toString();
	}

	private Activity activity;
	private String tpl_suffix;
}
//2131296256