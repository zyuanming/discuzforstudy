package net.discuz.asynctask;

import android.os.AsyncTask;
import java.io.*;
import net.discuz.source.DEBUG;
import net.discuz.source.DFile;

public class WriteFileTask extends AsyncTask
{

	public WriteFileTask()
	{}

	protected Object doInBackground(Object aobj[])
	{
		return doInBackground((String[]) aobj);
	}

	protected Void doInBackground(String as[])
	{
		String s = as[0];
		String s1 = as[1];
		try
		{
			String s2 = s1.substring(0, s1.lastIndexOf("/"));
			File file = new File(s2);
			if (!file.exists() || !file.isDirectory())
				(new DFile())._createDir(s2);
			File file1 = new File(s1);
			DEBUG.o((new StringBuilder()).append("****写入文件地址*****").append(s1)
					.toString());
			if (!file1.exists())
				file1.createNewFile();
			FileWriter filewriter = new FileWriter(file1);
			filewriter.append(s);
			filewriter.close();
		} catch (IOException ioexception)
		{
			DEBUG.o("文件写入异常");
			ioexception.printStackTrace();
		}
		return null;
	}

	protected void onPreExecute()
	{
		super.onPreExecute();
	}
}