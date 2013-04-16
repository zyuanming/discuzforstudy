package net.discuz.tools;

import java.net.*;
import java.util.*;
import net.discuz.app.DiscuzApp;
import net.discuz.model.LoginInfo;
import net.discuz.source.HttpRequest;

public class ImageThread extends HttpConnThread
{

	public ImageThread(String s, int i)
	{
		super(s, i);
	}

	private String getImage(String s)
	{
		HttpRequest httprequest;
		String s1;
		String s2;
		try
		{
			httprequest = new HttpRequest(mSiteappid);
			s1 = httprequest._getFile(s, null, "utf-8");
			HttpURLConnection httpurlconnection = httprequest.conn;
			if (httpurlconnection.getHeaderFields().get("set-cookie") != null)
			{
				Iterator iterator = ((List) httpurlconnection.getHeaderFields()
						.get("set-cookie")).iterator();
				do
				{
					if (!iterator.hasNext())
						break;
					String s3 = (String) iterator.next();
					if (s3.contains("seccode"))
					{
						String s4 = URLDecoder
								.decode(s3.split(";")[0], "utf-8");
						DiscuzApp.getInstance().getLoginUser(mSiteappid)
								.setLoginCookie("seccode", s4);
					}
				} while (true);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			s1 = null;
		}
		return s1;
	}

	public void run()
	{
		String s1 = getImage(url);
		Object obj = null;
		String s;
		s = s1;
		if (s == null)
		{
			obj = new UnknownHostException();
		}
		mListener.onFinish(this, s, ((Exception) (obj)));
	}

	private static final long serialVersionUID = 0x51b030e5b906ad80L;
}
// 2131296256