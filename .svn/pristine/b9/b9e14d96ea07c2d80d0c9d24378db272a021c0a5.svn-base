package net.discuz.source;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import net.discuz.tools.Tools;

public class DFile
{

	public DFile()
	{
		file = null;
		sdDir = null;
	}

	public static void _deleteFileOrDir(File file1)
	{
		if (file1.exists())
		{
			if (file1.isDirectory())
			{
				File afile[] = file1.listFiles();
				if (afile != null)
				{
					int i = afile.length;
					int j = 0;
					while (j < i)
					{
						File file2 = afile[j];
						if (file2.isDirectory())
							_deleteFileOrDir(file2);
						else
							file2.delete();
						j++;
					}
				}
			}
			file1.delete();
		}
	}

	public static void _deleteFileOrDir(String s)
	{
		_deleteFileOrDir(new File(s));
	}

	public static void _deleterFileOrDir(String s)
	{
		Runtime runtime = Runtime.getRuntime();
		String as[] = new String[3];
		as[0] = "sh";
		as[1] = "-c";
		as[2] = (new StringBuilder()).append("rm -r ").append(s).toString();
		try
		{
			runtime.exec(as);
			return;
		} catch (IOException ioexception)
		{
			ioexception.printStackTrace();
		}
	}

	private String _getMIMEType(File file1)
	{
		String s = file1.getName();
		String s1 = s.substring(1 + s.lastIndexOf("."), s.length())
				.toLowerCase();
		return MimeTypeMap.getSingleton().getMimeTypeFromExtension(s1);
	}

	public void _createDir(String s)
	{
		File file1 = new File(s);
		if (!file1.exists() && !file1.isFile())
			(new File(s)).mkdirs();
	}

	public void _createSDCustomDir(String s)
	{
		sdDir = (new StringBuilder()).append("/sdcard/discuz/").append(s)
				.toString();
		if (!_isFileExist(sdDir))
			_createDir(sdDir);
	}

	public boolean _deleteSDFile(String s)
	{
		String s1 = (new StringBuilder()).append("/sdcard/discuz/").append(s)
				.toString();
		if (_isFileExist(s1))
			(new File(s1)).delete();
		return true;
	}

	public List _getSDDownLoadFiles(String s)
	{
		(new DFile())._createSDCustomDir(s);
		File afile[] = (new File((new StringBuilder())
				.append("/sdcard/discuz/").append(s).toString())).listFiles();
		ArrayList arraylist = new ArrayList();
		int i = afile.length;
		for (int j = 0; j < i; j++)
		{
			File file1 = afile[j];
			if (!file1.isDirectory())
				arraylist.add(file1.getName().toString());
		}

		return arraylist;
	}

	public File _getSDFile(String s)
	{
		file = new File((new StringBuilder()).append("/sdcard/discuz/")
				.append(s).toString());
		return file;
	}

	public String _getSdcardPath()
	{
		if (!_isFileExist("/sdcard/discuz/"))
			_createDir("/sdcard/discuz/");
		return "/sdcard/discuz/";
	}

	public boolean _isDirExist(String s)
	{
		File file1 = new File(s);
		return file1.exists() && file1.isDirectory();
	}

	public boolean _isFileExist(String s)
	{
		File file1 = new File(s);
		return file1.exists() && file1.isFile();
	}

	public void _listFile(File file1)
	{
		File afile[] = file1.listFiles();
		if (afile != null)
		{
			int i = afile.length;
			int j = 0;
			while (j < i)
			{
				File file2 = afile[j];
				if (file2.isDirectory())
					_listFile(file2);
				else
					DEBUG.o((new StringBuilder()).append("**")
							.append(file2.getPath()).toString());
				j++;
			}
		}
	}

	public File _newFile(String s)
	{
		if (s.contains("/"))
		{
			String s1 = s.substring(0, s.lastIndexOf("/"));
			if (!_isDirExist(s1))
				_createDir(s1);
		}
		File file1 = new File(s);
		if (file1.exists())
			file1.delete();
		try
		{
			file1.createNewFile();
		} catch (IOException ioexception)
		{
			ioexception.printStackTrace();
			return file1;
		}
		return file1;
	}

	public void _openFile(Activity activity, String s)
	{
		File file1;
		Intent intent;
		String s1;
		file1 = new File(s);
		intent = new Intent();
		intent.addFlags(0x10000000);
		intent.setAction("android.intent.action.VIEW");
		s1 = _getMIMEType(file1);
		if (!"application/rar".equals(s1))
		{
			if (s1 == null || "".equals(s1))
				s1 = "*/*";
		} else
		{
			s1 = "application/*";
		}
		intent.setDataAndType(Uri.fromFile(file1), s1);
		activity.startActivity(intent);
		return;
	}

	public String _readFile(String s)
	{
		StringBuffer stringbuffer;
		stringbuffer = new StringBuffer();
		if (_isFileExist(s))
			try
			{
				FileReader filereader = new FileReader(new File(s));
				DEBUG.o("***开始读取文件***");
				if (filereader.ready())
				{
					int i;
					while ((i = filereader.read()) != -1)
					{
						stringbuffer.append((char) i);
					}
				}
			} catch (IOException ioexception)
			{
				ioexception.printStackTrace();
				DEBUG.o("####读取文件时出错###");
				file = null;
				return null;
			}
		String s1 = stringbuffer.toString();
		return s1;
	}

	public String _readSDFile(String s)
	{
		return _readFile((new StringBuilder()).append("/sdcard/discuz/")
				.append(s).toString());
	}

	public void _setUp(Activity activity)
	{
		File file1 = new File("/sdcard/discuz/download/DiscuzMobile.apk");
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setDataAndType(Uri.fromFile(file1),
				"application/vnd.android.package-archive");
		activity.startActivity(intent);
	}

	public void _showFileList(String s)
	{
		DEBUG.o((new StringBuilder()).append("*********调用者**").append(s)
				.append("******************").toString());
		_listFile(new File("/data/data/net.discuz/_a/"));
		DEBUG.o("***********打印程序在手机中的文件*******************");
	}

	public boolean _writeFile(String s, InputStream inputstream)
	{
		File file1;
		FileOutputStream fileoutputstream = null;
		if (Tools._isSdcardMounted().booleanValue() && inputstream != null)
		{
			file1 = _newFile(s);
			if (file1 != null)
			{
				try
				{
					byte abyte0[];
					fileoutputstream = new FileOutputStream(file1);
					abyte0 = new byte[1024];

					int i = -1;
					while ((i = inputstream.read(abyte0)) != -1)
					{
						fileoutputstream.write(abyte0, 0, i);
					}
				} catch (Exception e)
				{
					e.printStackTrace();

				} finally
				{
					try
					{
						inputstream.close();
						if (fileoutputstream != null)
						{
							fileoutputstream.close();
						}
					} catch (IOException ioexception2)
					{
						ioexception2.printStackTrace();
					}
				}
				return true;
			}
		}
		return false;
	}

	private File file;
	private String sdDir;
}
// 2131296256