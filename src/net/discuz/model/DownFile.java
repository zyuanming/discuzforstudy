package net.discuz.model;

import net.discuz.source.service.DownLoadService;

public class DownFile
{

	public DownFile(String s, String s1, String s2)
	{
		id = 0;
		name = null;
		url = null;
		path = "/sdcard/discuz/download/";
		size = 0;
		percent = 0;
		progress = 0;
		isOpen = false;
		isSetUp = false;
		isNotify = false;
		downLoadInterface = null;
		url = s;
		name = s1;
		if (s2 != null)
			path = s2;
	}

	public DownFile(String s, String s1, String s2, boolean flag,
			boolean flag1, boolean flag2,
			DownLoadService.DownLoadInterface downloadinterface)
	{
		id = 0;
		name = null;
		url = null;
		path = "/sdcard/discuz/download/";
		size = 0;
		percent = 0;
		progress = 0;
		isOpen = false;
		isSetUp = false;
		isNotify = false;
		url = s;
		name = s1;
		isNotify = flag;
		isOpen = flag1;
		isSetUp = flag2;
		if (s2 != null)
			path = s2;
		downLoadInterface = downloadinterface;
	}

	public void addProgress(int i)
	{
		progress = i + progress;
	}

	public DownLoadService.DownLoadInterface getDownLoadInterface()
	{
		return downLoadInterface;
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		if (name == null || "".equals(name))
			name = (new StringBuilder())
					.append(String.valueOf(System.currentTimeMillis())
							.substring(2, 10)).append(".\u91CD\u547D\u540D")
					.toString();
		return name;
	}

	public String getPath()
	{
		return path;
	}

	public int getPercent()
	{
		return percent;
	}

	public int getProgress()
	{
		return progress;
	}

	public int getSize()
	{
		return size;
	}

	public String getUrl()
	{
		return url;
	}

	public boolean isNotify()
	{
		return isNotify;
	}

	public boolean isOpen()
	{
		return isOpen;
	}

	public boolean isSetUp()
	{
		return isSetUp;
	}

	public void setDownLOadInterface(
			net.discuz.source.service.DownLoadService.DownLoadInterface downloadinterface)
	{
		downLoadInterface = downloadinterface;
	}

	public void setId(int i)
	{
		id = i;
	}

	public void setName(String s)
	{
		name = s;
	}

	public void setNotify(boolean flag)
	{
		isNotify = flag;
	}

	public void setOpen(boolean flag)
	{
		isOpen = flag;
	}

	public void setPath(String s)
	{
		path = s;
	}

	public void setPercent(int i)
	{
		percent = i;
	}

	public void setProgress(int i)
	{
		progress = i;
	}

	public void setSetUp(boolean flag)
	{
		isSetUp = flag;
	}

	public void setSize(int i)
	{
		size = i;
	}

	public void setUrl(String s)
	{
		url = s;
	}

	private net.discuz.source.service.DownLoadService.DownLoadInterface downLoadInterface;
	private int id;
	private boolean isNotify;
	private boolean isOpen;
	private boolean isSetUp;
	private String name;
	private String path;
	private int percent;
	private int progress;
	private int size;
	private String url;
}
// 2131296256