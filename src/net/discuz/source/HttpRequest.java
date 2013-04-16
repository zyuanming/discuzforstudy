package net.discuz.source;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import net.discuz.app.DiscuzApp;
import net.discuz.model.LoginInfo;

import org.apache.http.HttpResponse;
import org.apache.http.params.BasicHttpParams;

import android.util.Log;

public class HttpRequest
{
	public static class LoginCallBack implements requestCallBack
	{

		public void download(Object obj)
		{
			if (obj != null && (obj instanceof HttpURLConnection))
			{
				Map map = ((HttpURLConnection) obj).getHeaderFields();
				if (map.get("set-cookie") != null)
				{
					Iterator iterator = ((List) map.get("set-cookie"))
							.iterator();

					try
					{
						while (iterator.hasNext())
						{
							String s;
							s = (String) iterator.next();
							if (s.contains("secqaa"))
							{
								String s1 = URLDecoder.decode(s.split(";")[0],
										"utf-8");
								DEBUG.o((new StringBuilder())
										.append("=============GET SECQAA HEARDER Cookie DECODE============")
										.append(s1).toString());
								DiscuzApp.getInstance().getLoginUser(siteAppId)
										.setLoginCookie("secqaa", s1);
							}
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}

		private String siteAppId;

		public LoginCallBack(String s)
		{
			siteAppId = s;
		}
	}

	public static interface requestCallBack
	{

		public abstract void download(Object obj);
	}

	public HttpRequest()
	{
		response = null;
		connCookies = null;
		requestProperty = null;
		postParams = null;
		redirect = 0;
		cookiePre = null;
		isGZIP = true;
	}

	public HttpRequest(String s)
	{
		response = null;
		connCookies = null;
		requestProperty = null;
		postParams = null;
		redirect = 0;
		cookiePre = null;
		isGZIP = true;
		siteAppId = s;
		DiscuzApp discuzapp = DiscuzApp.getInstance();
		if (discuzapp != null && siteAppId != null && !siteAppId.equals(""))
		{
			LoginInfo logininfo = discuzapp.getLoginUser(siteAppId);
			if (logininfo != null)
			{
				HashMap hashmap = logininfo.getLoginCookie();
				if (hashmap != null && !hashmap.isEmpty())
				{
					java.util.Map.Entry entry;
					for (Iterator iterator = hashmap.entrySet().iterator(); iterator
							.hasNext(); DEBUG.o((new StringBuilder())
							.append("==============HTTPREQUEST============")
							.append((String) entry.getValue()).toString()))
					{
						entry = (java.util.Map.Entry) iterator.next();
						_setCookie((String) entry.getValue());
					}

				}
			}
		}
	}

	public HttpRequest(ArrayList arraylist)
	{
		response = null;
		connCookies = null;
		requestProperty = null;
		postParams = null;
		redirect = 0;
		cookiePre = null;
		isGZIP = true;
		if (arraylist != null)
		{
			for (int i = 0; i < arraylist.size(); i++)
				_setCookie((String) arraylist.get(i));

		}
	}

	private HttpURLConnection _createConn(String s, String s1)
			throws IOException
	{
		Log.d("HttpRequest", "_createConn(string, string) ----> Enter" + s
				+ "***" + s1);
		URL url = new URL(s);
		if (conn != null)
			conn.disconnect();
		conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(60000);
		conn.setRequestMethod(s1);
		conn.setUseCaches(false);
		HttpURLConnection.setFollowRedirects(true);
		conn.setInstanceFollowRedirects(true);
		if (requestProperty != null)
		{
			java.util.Map.Entry entry1;
			for (Iterator iterator1 = requestProperty.entrySet().iterator(); iterator1
					.hasNext(); conn.addRequestProperty(
					(String) entry1.getKey(), (String) entry1.getValue()))
				entry1 = (java.util.Map.Entry) iterator1.next();

		}
		conn.addRequestProperty("User-Agent", DiscuzApp.getInstance()
				.getUserAgent());
		if (isGZIP && !conn.getURL().toString().contains("module=seccode"))
			conn.addRequestProperty("Accept-Encoding", "gzip, deflate");
		if (s1.equals("POST"))
		{
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
		}
		HashMap hashmap = connCookies;
		String s2 = null;
		if (hashmap != null)
			if (!connCookies.isEmpty())
			{
				Iterator iterator = connCookies.entrySet().iterator();
				java.util.Map.Entry entry;
				for (s2 = ""; iterator.hasNext(); s2 = (new StringBuilder())
						.append(s2).append((String) entry.getKey()).append("=")
						.append((String) entry.getValue()).append(";")
						.toString())
					entry = (java.util.Map.Entry) iterator.next();

			} else
			{
				s2 = "";
			}
		if (s2 != null)
			conn.addRequestProperty("Cookie", s2);
		Log.d("HttpRequest", "_createConn(string, string) ----> Exit");
		return conn;
	}

	private String _initUrlParams(String s, HashMap hashmap, String s1)
	{
		Log.d("HttpRequest", "_initUrlParams ----> Enter");
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append(s);
		if (hashmap != null && !hashmap.isEmpty())
		{
			Iterator iterator;
			if (stringbuilder.indexOf("?") == -1)
				stringbuilder.append('?');
			else
				stringbuilder.append("&");
			for (iterator = hashmap.entrySet().iterator(); iterator.hasNext();)
			{
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator
						.next();
				try
				{
					stringbuilder
							.append((String) entry.getKey())
							.append('=')
							.append(URLEncoder.encode(
									(String) entry.getValue(), "utf-8"))
							.append('&');
				} catch (UnsupportedEncodingException unsupportedencodingexception)
				{
					unsupportedencodingexception.printStackTrace();
				}
			}

			stringbuilder.deleteCharAt(-1 + stringbuilder.length());
		}
		Log.d("HttpRequest", stringbuilder.toString());
		Log.d("HttpRequest", "_initUrlParams ----> Exit");
		return stringbuilder.toString();
	}

	/**
	 * POST方式的响应
	 * 
	 * @param s
	 * @return
	 * @throws IOException
	 */
	private String postResponse(String s) throws IOException
	{
		Log.d("HttpRequest", "postResponse() ----> Enter");
		Integer integer = Integer.valueOf(-1);
		Integer integer1 = Integer.valueOf(conn.getResponseCode());
		integer = integer1;
		if (integer.intValue() == 200)
		{
			InputStream inputstream = getGZIP(conn);
			InputStreamReader inputstreamreader = new InputStreamReader(
					inputstream, s);
			String s1 = "";
			BufferedReader bufferedreader = new BufferedReader(
					inputstreamreader);
			do
			{
				String s2 = bufferedreader.readLine();
				if (s2 != null)
				{
					s1 = (new StringBuilder()).append(s1).append(s2)
							.append("\n").toString();
				} else
				{
					inputstreamreader.close();
					inputstream.close();
					conn.disconnect();
					Log.d("HttpRequest", "postResponse() ----> Exit");
					return s1;
				}
			} while (true);
		}
		if (integer.intValue() >= 400)
		{
			if (conn != null)
				conn.disconnect();
			return "error";
		}
		if (integer.intValue() == 301 && redirect < 2)
		{
			redirect = 1 + redirect;
			_createConn(
					(String) ((List) conn.getHeaderFields().get("Location"))
							.get(0),
					"POST");
			postString(s);
			Log.d("HttpRequest", "postResponse() ----> Exit");
			return postResponse(s);
		}
		if (conn != null)
			conn.disconnect();
		DEBUG.e((new StringBuilder()).append("HttpUrlConnection Error:")
				.append(integer).toString());
		Log.d("HttpRequest", "postResponse() ----> Exit");
		return null;
	}

	private void postString(String s) throws IOException
	{
		Log.d("HttpRequest", "postString(s) ----> Enter" + "s = " + s);
		StringBuilder stringbuilder = new StringBuilder();
		if (postParams != null && !postParams.isEmpty())
		{
			java.util.Map.Entry entry;
			Iterator iterator = postParams.entrySet().iterator();
			while (iterator.hasNext())
			{
				entry = (java.util.Map.Entry) iterator.next();
				stringbuilder
						.append((String) entry.getKey())
						.append('=')
						.append(URLEncoder.encode(
								((String) entry.getValue()).trim(), s))
						.append('&');
			}
			stringbuilder.deleteCharAt(-1 + stringbuilder.length());
		}
		byte abyte0[] = stringbuilder.toString().getBytes();
		conn.setRequestProperty("Content-Length", String.valueOf(abyte0.length));
		OutputStream outputstream = null;
		try
		{
			conn.connect();
			outputstream = conn.getOutputStream();
			outputstream.write(abyte0);
			outputstream.flush();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (outputstream != null)
				outputstream.close();
		}
		Log.d("HttpRequest", "postString() ----> Exit");
	}

	public void _addRequestProperty(String s, String s1)
	{
		if (requestProperty == null)
			requestProperty = new HashMap();
		if (requestProperty.get(s) != null)
			requestProperty.remove(s);
		requestProperty.put(s, s1);
	}

	public void _clearCookie()
	{
		connCookies = null;
	}

	public String _get(String s) throws Exception
	{
		return _get(s, null, null, null);
	}

	public String _get(String s, HashMap hashmap, String s1) throws Exception
	{
		String result = _get(s, hashmap, s1, null);
		return result;
	}

	public String _get(String s, HashMap hashmap, String s1,
			requestCallBack requestcallback) throws Exception
	{
		String s2;
		Integer integer;
		if (s1 == null)
			s1 = "utf-8";
		s2 = _initUrlParams(s, hashmap, s1);
		_createConn(s2, "GET");
		integer = Integer.valueOf(-1);
		Integer integer2;
		conn.connect();
		integer2 = Integer.valueOf(conn.getResponseCode());
		Integer integer1 = integer2;
		Integer integer3;
		if (integer1.intValue() == -1)
		{
			_createConn(s2, "GET");
			conn.connect();
			integer3 = Integer.valueOf(conn.getResponseCode());
			integer1 = integer3;
		}

		DEBUG.o((new StringBuilder()).append("GET URL:").append(s2).toString());
		DEBUG.o((new StringBuilder()).append("GET responseCode:")
				.append(integer1).toString());
		if (integer1.intValue() == 200)
		{
			InputStream inputstream = getGZIP(conn);
			InputStreamReader inputstreamreader = new InputStreamReader(
					inputstream, s1);
			StringBuilder stringbuilder = new StringBuilder();
			if (requestcallback != null)
				requestcallback.download(conn);
			BufferedReader bufferedreader = new BufferedReader(
					inputstreamreader);
			do
			{
				String s3 = bufferedreader.readLine();
				if (s3 != null)
				{
					stringbuilder.append(s3).append("\n");
				} else
				{
					inputstreamreader.close();
					inputstream.close();
					DEBUG.o((new StringBuilder())
							.append("GET URL Input Stream close :").append(s)
							.toString());
					DEBUG.o((new StringBuilder())
							.append("GET URL Input Stream result :")
							.append(stringbuilder.toString()).toString());
					conn.disconnect();
					return stringbuilder.toString();
				}
			} while (true);
		}
		if (integer1.intValue() >= 400)
		{
			if (conn != null)
				conn.disconnect();
			return "error";
		}
		if (integer1.intValue() == -1)
		{
			DEBUG.e((new StringBuilder())
					.append("#######HttpUrlConnection Error:").append(integer1)
					.toString());
			if (conn != null)
				conn.disconnect();
			return null;
		}
		if (integer1.intValue() >= 300 && redirect < 2)
		{
			_getFile(
					(String) ((List) conn.getHeaderFields().get("Location"))
							.get(0),
					hashmap, s1, requestcallback);
			return null;
		}
		DEBUG.o((new StringBuilder()).append("URLConnection header Fielder:")
				.append(conn.getHeaderFields()).toString());
		if (conn != null)
			conn.disconnect();
		DEBUG.e((new StringBuilder()).append("HttpUrlConnection Error:")
				.append(integer1).toString());
		return null;
	}

	/**
	 * 
	 * @param s
	 *            资源的URL地址
	 * @param hashmap
	 * @param s1
	 *            编码格式
	 * @return
	 * @throws Exception
	 */
	public String _getFile(String s, HashMap hashmap, String s1)
			throws Exception
	{
		String s2;
		String s3 = null;
		Integer integer = null;
		InputStream inputstream = null;
		try
		{
			s2 = _initUrlParams(s, hashmap, s1);
			_createConn(s2, "GET");
			DEBUG.o((new StringBuilder())
					.append("=============vocode url===============").append(s)
					.toString());
			integer = Integer.valueOf(-1);
			Integer integer1;
			conn.connect();
			integer = Integer.valueOf(conn.getResponseCode());
			if (integer.intValue() == -1)
			{
				_createConn(s2, "GET");
				conn.connect();
				integer1 = Integer.valueOf(conn.getResponseCode());
				integer = integer1;
			}
			DEBUG.o((new StringBuilder())
					.append("=============responseCode===============")
					.append(integer).toString());
			if (integer.intValue() == 200)
			{
				inputstream = getGZIP(conn);
				DFile dfile = new DFile();
				String s4 = (new StringBuilder())
						.append("/sdcard/discuz/temp/")
						.append(s.split("\\?")[1]).toString();

				if (!dfile._writeFile(s4, inputstream))
					s4 = null;
				s3 = s4;
			}

			if (integer.intValue() > 400)
			{
				s3 = null;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			DEBUG.e((new StringBuilder()).append("HttpUrlConnection Error:")
					.append(integer).toString());
			s3 = null;
		} finally
		{
			if (conn != null)
			{
				conn.disconnect();
			}
			if (inputstream != null)
			{
				inputstream.close();
			}
		}
		return s3;
	}

	public void _getFile(String s, HashMap hashmap, String s1,
			requestCallBack requestcallback) throws Exception
	{
		String s2 = _initUrlParams(s, hashmap, s1);
		Integer integer = Integer.valueOf(-1);

		DEBUG.o((new StringBuilder())
				.append("=============vocode url===============").append(s)
				.toString());

		_createConn(s2, "GET");
		conn.connect();

		integer = Integer.valueOf(conn.getResponseCode());
		if (integer.intValue() == -1)
		{
			_createConn(s2, "GET");
			conn.connect();
			integer = Integer.valueOf(conn.getResponseCode());
		}

		DEBUG.o((new StringBuilder())
				.append("=============responseCode===============")
				.append(integer).toString());
		if (integer.intValue() != 200)
		{
			if (integer.intValue() < 400)
			{
				if (conn != null)
				{
					conn.disconnect();
				} else
				{
					if (integer.intValue() < 300)
					{
						return;
					} else
					{
						DEBUG.e((new StringBuilder())
								.append("HttpUrlConnection Error:")
								.append(integer).toString());
						if (conn != null)
						{
							conn.disconnect();
						}
					}
				}
			}
		} else
		{
			InputStream inputstream = getGZIP(conn);
			if (requestcallback != null)
				if (s.contains("module=seccode"))
					requestcallback.download(this);
				else
					requestcallback.download(inputstream);
			inputstream.close();
			if (conn != null)
				conn.disconnect();
		}
	}

	public String _post(String s, HashMap hashmap, HashMap hashmap1, String s1)
			throws Exception
	{
		if (s1 == null)
			s1 = "utf-8";
		String s2 = _initUrlParams(s, hashmap, s1);
		DEBUG.o((new StringBuilder())
				.append("=============LOGIN POST URL===========").append(s2)
				.toString());
		_createConn(s2, "POST");
		postParams = hashmap1;
		postString(s1);
		return postResponse(s1);
	}

	public void _setCookie(String s)
	{
		if (s != null)
		{
			if (connCookies == null)
				connCookies = new HashMap();
			String as[] = s.split("=");
			if (as.length > 1)
				connCookies.put(as[0], URLEncoder.encode(as[1]));
		}
	}

	public void _setCookiePre(String s)
	{
		cookiePre = s;
	}

	public InputStream getGZIP(HttpURLConnection httpurlconnection)
			throws IOException
	{
		InputStream inputstream = httpurlconnection.getInputStream();
		if ("gzip".equals(httpurlconnection.getContentEncoding()))
			return new GZIPInputStream(new BufferedInputStream(inputstream));
		else
			return inputstream;
	}

	public void setIsGzip(boolean flag)
	{
		isGZIP = flag;
	}

	public HttpURLConnection conn;
	public HashMap connCookies;
	public String cookiePre;
	public BasicHttpParams httpParameters;
	private boolean isGZIP;
	public HashMap postParams;
	public int redirect;
	public HashMap requestProperty;
	public HttpResponse response;
	private String siteAppId;
}
// 2131296256