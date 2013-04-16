package net.discuz.tools;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.HashMap;

import net.discuz.app.DiscuzApp;
import net.discuz.asynctask.WriteFileTask;
import net.discuz.init.InitSetting;
import net.discuz.source.DEBUG;
import net.discuz.source.DFile;
import net.discuz.source.DJsonReader;
import net.discuz.source.HttpRequest;
import net.discuz.source.service.HttpConnService;
import android.util.Log;

/**
 * 网络连接线程
 * 
 * @author Ming
 * 
 */
public class HttpConnThread implements Runnable, Serializable
{

	public HttpConnThread()
	{
		mPriority = 1;
		method = 0;
		cacheType = -1;
	}

	public HttpConnThread(String s, int i)
	{
		mPriority = 1;
		method = 0;
		cacheType = -1;
		mSiteappid = s;
		mPriority = i;
	}

	public String getCachePath()
	{
		return cachePath;
	}

	public int getCacheType()
	{
		return cacheType;
	}

	public String getCookie()
	{
		return cookie;
	}

	public String getFilter()
	{
		return filter;
	}

	public int getHttpMethod()
	{
		return method;
	}

	public String getId()
	{
		return id;
	}

	public String getInputStream(String s, String s1, int i) throws Exception
	{
		Log.d("HttpConnThread", "getInputStream() ----> Enter");
		DFile dfile = new DFile();
		String s2;
		if ((i == 0 || i == 2) && Tools._isSdcardMounted().booleanValue()
				&& dfile._isFileExist(s1))
		{
			DEBUG.o((new StringBuilder()).append("**调取缓存***").append(s1)
					.toString());
			s2 = dfile._readFile(s1);
		} else
		{
			s2 = (new HttpRequest(mSiteappid))._get(s, null, "utf-8");
			if (s2 != null && (i == 1 || i == 2))
			{
				DJsonReader djsonreader = new DJsonReader(s2);
				djsonreader._jsonParseSimple();
				if (djsonreader.isjson
						&& Tools._isSdcardMounted().booleanValue())
				{
					(new WriteFileTask()).execute(new String[] { s2, s1 });
					return s2;
				}
			}
		}
		Log.d("HttpConnThread", "getInputStream() ----> Exit");
		return s2;
	}

	public HashMap getPostparams()
	{
		return postparams;
	}

	public int getPriority()
	{
		return mPriority;
	}

	public String getUrl()
	{
		return url;
	}

	public String post() throws Exception
	{
		HttpRequest httprequest = new HttpRequest(mSiteappid);
		if (cookie != null && !"".equals(cookie))
			httprequest._setCookie(cookie);
		String s;
		if (mSiteappid == null)
			s = "utf-8";
		else
			s = DiscuzApp.getInstance().getSiteInfo(mSiteappid)
					.getSiteCharset();
		return httprequest._post(url, null, postparams, s);
	}

	public void run()
	{
		String s = null;
		Log.d(Thread.currentThread().getName(),
				(new StringBuilder()).append("----Start to connect:")
						.append(url).append(", priority:").append(mPriority)
						.append("-----").toString());
		StringBuilder stringbuilder = new StringBuilder();
		String s3;
		if (cacheType != -1)
			if (cachePath == null)
				stringbuilder.append(InitSetting.CACHE_PATH).append("_w/")
						.append(Tools._md5(url.split("\\?")[0]))
						.append("_json");
			else
				stringbuilder.append(InitSetting.CACHE_PATH).append(cachePath)
						.append(Tools._md5(url)).append("_json");
		cachePath = stringbuilder.toString();
		Object obj = null;
		String s1;
		String s2;
		try
		{
			if (method != 0)
			{
				if (method == 1)
				{
					s1 = post();
					s = s1;
					if (s == null)
					{
						s2 = post();
						s = s2;
					}
				}
			} else
			{
				s3 = getInputStream(url, cachePath, cacheType);
				s = s3;
				if (s == null)
					s = getInputStream(url, cachePath, cacheType);
			}
		} catch (Exception e)
		{
			if (s == null)
				obj = new UnknownHostException();
			e.printStackTrace();
		}
		mListener.onFinish(this, s, ((Exception) (obj)));
		Log.d(Thread.currentThread().getName(),
				(new StringBuilder()).append("----Finish to connect:")
						.append(url).append(", priority:").append(mPriority)
						.append("-----").toString());
	}

	public void setCachePath(String s)
	{
		cachePath = s;
	}

	public void setCacheType(int i)
	{
		cacheType = i;
	}

	public void setCookie(String s)
	{
		cookie = s;
	}

	public void setFilter(String s)
	{
		filter = s;
	}

	public void setHttpConnServiceListener(
			HttpConnService.HttpConnServiceListener httpconnservicelistener)
	{
		mListener = httpconnservicelistener;
	}

	public void setHttpMethod(int i)
	{
		method = i;
	}

	public void setId(String s)
	{
		id = s;
	}

	public void setPostparams(HashMap hashmap)
	{
		postparams = hashmap;
	}

	public void setPriority(int i)
	{
		mPriority = i;
	}

	public void setUrl(String s)
	{
		url = s;
	}

	public static final int CACHE_NONE = -1;
	public static final int CACHE_R = 0;
	public static final int CACHE_RW = 2;
	public static final int CACHE_W = 1;
	public static final int HIGH_LEVEL = 0;
	public static final int HTTP_GET = 0;
	public static final int HTTP_POST = 1;
	public static final int LOW_LEVEL = 2;
	public static final int NORMAL_LEVEL = 1;
	private static final long serialVersionUID = 0x6bc9422c7b114088L;
	protected String cachePath;
	protected int cacheType;
	protected String cookie;
	protected String filter;
	protected String id;
	protected HttpConnService.HttpConnServiceListener mListener;
	protected int mPriority; // 线程优先级
	protected String mSiteappid;
	protected int method;
	protected HashMap postparams;
	protected String url;
}
// 2131296256