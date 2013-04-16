package net.discuz.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.discuz.app.DiscuzApp;
import net.discuz.boardcast.HttpConnReceiver;
import net.discuz.init.InitSetting;
import net.discuz.model.SiteInfo;
import net.discuz.source.DEBUG;
import net.discuz.source.DFile;
import net.discuz.source.InterFace.SucceCallBack;
import net.discuz.source.storage.SiteInfoDBHelper;

import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

public class Tools
{

	public Tools()
	{}

	public static void CheckSiteClientMode(final String s, String s1,
			final String s2, final SucceCallBack succecallback)
	{
		Log.d("Tools", "CheckSiteClientMode() -----> Enter");
		HttpConnReceiver.HttpConnListener httpconnlistener = new HttpConnReceiver.HttpConnListener()
		{

			public void onFailed(Exception exception)
			{
				if (succecallback != null)
					succecallback.onFaild(exception);
			}

			public void onSucceed(String s4, String s5)
			{
				String s6 = Tools.trim(s, "/");
				SiteInfoDBHelper siteinfodbhelper;
				siteinfodbhelper = SiteInfoDBHelper.getInstance();
				DEBUG.o((new StringBuilder())
						.append("===CheckSiteClientMode===clientid====resultString==")
						.append(s).append(" DATA:").append(s4).toString());
				SiteInfo siteinfo = null;
				if (s4 != null)
				{
					if (s4.contains("discuzversion") && s4.contains("charset"))
					{
						try
						{
							JSONObject jsonobject = new JSONObject(s4);
							siteinfo = new SiteInfo();
							siteinfo._initCheckValue(jsonobject);
							siteinfo.setSiteUrl(s6);
							siteinfo.setClientview("1");
							DEBUG.o((new StringBuilder())
									.append("====CheckSiteClientMode===clientid==")
									.append(siteinfo.getCloudId()).toString());
							siteinfo.setCloudid(jsonobject
									.optString("mysiteid"));
							if (!Tools.stringIsNullOrEmpty(siteinfo
									.getCloudId())
									&& Tools.strToInt(siteinfo.getCloudId()) > 0)
							{
								try
								{
									DEBUG.o((new StringBuilder())
											.append("update_check_discuzversion_right:")
											.append(siteinfo.getVersion())
											.toString());
									if (siteinfo.getVersion() != null)
									{

										if (s2 == null || s2.equals(""))
										{
											siteinfodbhelper.updateSiteByUrl(
													s6, siteinfo);
										} else
										{
											siteinfodbhelper.updateSiteByAppId(
													s2, siteinfo);
										}
									}
									siteinfo.setIsNotify("1");
								} catch (Exception exception)
								{
									exception.printStackTrace();
									if (succecallback != null)
									{
										succecallback.onFaild(exception);
									}
								}
							} else
							{
								siteinfo.setIsNotify("0");
							}
							if (succecallback != null)
							{
								succecallback.onsucced(s2);
								return;
							}
						} catch (Exception e)
						{
							DEBUG.o("update_check_discuzversion_wrong");
							if (s2 != null && !s2.equals(""))
							{
								siteinfodbhelper.updateClientViewByAppId("0",
										s2);
							} else
							{
								siteinfodbhelper.updateClientViewByUrl("0", s6);
							}
							if (succecallback != null)
							{
								succecallback.onFaild(e);
							}
							e.printStackTrace();
						}
					}
				}
			}
		};
		String s3;
		HttpConnThread httpconnthread;
		if (!s1.toLowerCase().startsWith("http://"))
			s1 = (new StringBuilder()).append("http://").append(s1).toString();
		if (!s1.endsWith("/"))
			s1 = (new StringBuilder()).append(s1).append("/").toString();
		s3 = (new StringBuilder()).append(s1)
				.append("api/mobile/index.php?check").toString();
		DEBUG.o((new StringBuilder())
				.append("=================CheckSiteUrl============").append(s3)
				.toString());
		httpconnthread = new HttpConnThread(s2, 1);
		httpconnthread.setUrl(s3);
		httpconnthread.setCacheType(-1);
		httpconnthread.setFilter(s);
		DiscuzApp.getInstance().setHttpConnListener(s, httpconnlistener);
		DiscuzApp.getInstance().sendHttpConnThread(httpconnthread);
		Log.d("Tools", "CheckSiteClientMode() -----> Exit");
	}

	public static Object DeSerialize(String s)
	{
		File file;
		Object obj = null;
		file = new File(s);
		boolean flag = _isSdcardMounted().booleanValue();
		if (flag)
		{
			boolean flag1 = file.exists();
			if (flag1)
			{
				Object obj1;
				try
				{
					ObjectInputStream objectinputstream = new ObjectInputStream(
							new FileInputStream(file.getPath()));
					obj1 = objectinputstream.readObject();
				} catch (Exception e)
				{
					e.printStackTrace();
					return null;
				}
				obj = obj1;
			}
		}

		return obj;
	}

	public static Object Serialize(Object obj, String s)
	{
		File file;
		if (_isSdcardMounted().booleanValue() && obj instanceof Serializable)
		{
			file = (new DFile())._newFile(s);
			try
			{
				(new ObjectOutputStream(new FileOutputStream(file)))
						.writeObject(obj);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return obj;
	}

	public static int _getHeaderBgColor()
	{
		return Color.parseColor(InitSetting.DEFAULT_HEADER_BG_COLOR);
	}

	public static String _getNumToDateTime(long l, String s)
	{
		Date date;
		SimpleDateFormat simpledateformat;
		try
		{
			date = new Date(l);
		} catch (Exception exception)
		{
			return null;
		}
		if (s != null)
		{
			simpledateformat = new SimpleDateFormat(s);

		} else
		{
			simpledateformat = new SimpleDateFormat("yyyy-MM-dd");
		}
		return simpledateformat.format(date);
	}

	public static String _getNumToDateTime(String s, String s1)
	{
		if (s == null)
			s = String.valueOf(System.currentTimeMillis());
		if (s.length() == 10)
			s = (new StringBuilder()).append(s).append("000").toString();
		return _getNumToDateTime(Long.valueOf(s).longValue(), s1);
	}

	public static long _getTimeStamp()
	{
		return System.currentTimeMillis();
	}

	public static long _getTimeStamp(Boolean boolean1)
	{
		long l = System.currentTimeMillis();
		if (boolean1.booleanValue())
		{
			DEBUG.o("Start The Time Point;");
			timestamp = l;
		} else if (!boolean1.booleanValue())
		{
			DEBUG.o((new StringBuilder()).append("The Time:")
					.append(l - timestamp).toString());
			return l;
		}
		return l;
	}

	public static long _getTimeStamp(String s)
	{
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try
		{
			Long long2 = Long
					.valueOf(simpledateformat.parse(s).getTime() / 1000L);
			Long long1 = long2;
			return long1.longValue();
		} catch (Exception e)
		{
			return 0;
		}

	}

	public static long _getTimeStampUnix()
	{
		return System.currentTimeMillis() / 1000L;
	}

	public static Boolean _isSdcardMounted()
	{
		if (Environment.getExternalStorageState().equals("mounted"))
			return Boolean.valueOf(true);
		else
			return Boolean.valueOf(false);
	}

	/**
	 * MD5加密
	 * 
	 * @param s
	 * @return
	 */
	public static String _md5(String s)
	{
		String s1;
		Log.i("mrghappy", s);
		try
		{
			MessageDigest messagedigest = MessageDigest.getInstance("MD5");
			messagedigest.update(s.getBytes());
			s1 = _toHexString(messagedigest.digest());
			Log.i("mrghappy", s1);
			return s1;
		} catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}

	public static String _time(int i)
	{
		DEBUG.o(Integer.valueOf(i));
		int j;
		int k;
		int l;
		if (i >= 1000)
		{
			int i1 = i % 1000;
			int j1 = i / 1000;
			if (j1 >= 60)
			{
				int k1 = j1 % 60;
				int l1 = j1 / 60;
				if (l1 >= 60)
				{
					l = l1 % 60;
					int i2 = l1 / 60;
					if (i2 >= 60)
					{
						int j2 = i2 % 60;
						int _tmp = i2 / 60;
						i = i1;
						j = k1;
						k = j2;
					} else
					{
						i = i1;
						j = k1;
						k = i2;
					}
				} else
				{
					i = i1;
					j = k1;
					l = l1;
					k = 0;
				}
			} else
			{
				i = i1;
				j = j1;
				k = 0;
				l = 0;
			}
		} else
		{
			j = 0;
			k = 0;
			l = 0;
		}
		return (new StringBuilder()).append(k).append("'").append(l)
				.append("'").append(j).append("'").append(i).toString();
	}

	private static String _toHexString(byte abyte0[])
	{
		StringBuilder stringbuilder = new StringBuilder(2 * abyte0.length);
		for (int i = 0; i < abyte0.length; i++)
		{
			stringbuilder.append(HEX_DIGITS[(0xf0 & abyte0[i]) >>> 4]);
			stringbuilder.append(HEX_DIGITS[0xf & abyte0[i]]);
		}

		return stringbuilder.toString();
	}

	public static void applyRoundCorner(ImageView imageview, Bitmap bitmap)
	{
		int i = (int) (3.5F * DiscuzApp.getInstance().density);
		imageview.setImageBitmap(getRoundCornerBmp(bitmap));
		imageview.setPadding(i, i, i, i);
		imageview.setBackgroundDrawable(new BitmapDrawable(getRoundCornerBG(i,
				bitmap)));
	}

	public static android.graphics.BitmapFactory.Options getBitmapOptions(
			String s, int i)
	{
		android.graphics.BitmapFactory.Options options;
		int j;
		int k;
		options = new android.graphics.BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(s, options);
		j = options.outWidth;
		k = options.outHeight;
		options.inSampleSize = 1;
		if (j <= k)
		{
			if (k > i)
				options.inSampleSize = 1 + k / i;
		} else
		{
			if (j > i)
				options.inSampleSize = 1 + j / i;
		}
		DEBUG.o((new StringBuilder()).append("path_temp = ").append(s)
				.toString());
		DEBUG.o((new StringBuilder()).append("inSampleSize = ")
				.append(options.inSampleSize).toString());
		DEBUG.o((new StringBuilder()).append("IMAGEWidth = ").append(j)
				.append(" IMAGEHeight = ").append(k).toString());
		options.inJustDecodeBounds = false;
		return options;
	}

	public static String getHttpUrl(String s)
	{
		if (s.startsWith("http://"))
			return s;
		else
			return (new StringBuilder()).append("http://").append(s).toString();
	}

	public static Bitmap getRoundCornerBG(int i, Bitmap bitmap)
	{
		int j = bitmap.getWidth() + i * 2;
		Bitmap bitmap1 = Bitmap.createBitmap(j, j,
				android.graphics.Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap1);
		canvas.drawARGB(0, 0, 0, 0);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(-1);
		paint.setMaskFilter(new EmbossMaskFilter(
				new float[] { 1.0F, 1.0F, 1.0F }, 0.8F, 4F, 0.5F));
		canvas.drawRoundRect(new RectF(new Rect(0, 0, j, j)), j, j, paint);
		return bitmap1;
	}

	public static Bitmap getRoundCornerBmp(Bitmap bitmap)
	{
		int i = bitmap.getWidth();
		Bitmap bitmap1 = Bitmap.createBitmap(i, i,
				android.graphics.Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap1);
		canvas.drawARGB(0, 0, 0, 0);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		RectF rectf = new RectF(new Rect(0, 0, i, i));
		canvas.drawRoundRect(rectf, i, i, paint);
		paint.setXfermode(new PorterDuffXfermode(
				android.graphics.PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, null, rectf, paint);
		return bitmap1;
	}

	public static String htmlSpecialCharDecode(String s)
	{
		return s.replaceAll("&amp;", "&").replaceAll("&nbsp;", " ")
				.replaceAll("&quot;", "\"").replaceAll("&#039;", "'")
				.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
	}

	public static boolean isEmptyString(String s)
	{
		if (s == null || "".equals(s.trim()))
			return true;
		return false;
	}

	public static byte[] readStream(InputStream inputstream) throws Exception
	{
		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
		byte abyte0[] = new byte[1024];
		do
		{
			int i = inputstream.read(abyte0);
			if (i != -1)
			{
				bytearrayoutputstream.write(abyte0, 0, i);
			} else
			{
				bytearrayoutputstream.close();
				inputstream.close();
				return bytearrayoutputstream.toByteArray();
			}
		} while (true);
	}

	public static String readableFileSize(long l)
	{
		if (l <= 0L)
		{
			return "0";
		} else
		{
			String as[] = { "B", "KB", "MB", "GB", "TB" };
			int i = (int) (Math.log10(l) / Math.log10(1024D));
			return (new StringBuilder())
					.append((new DecimalFormat("#,##0.#")).format((double) l
							/ Math.pow(1024D, i))).append(" ").append(as[i])
					.toString();
		}
	}

	public static int strToInt(String s)
	{
		return strToInt(s, 0);
	}

	public static int strToInt(String s, int i)
	{
		int j;
		try
		{
			j = Integer.valueOf(s).intValue();
		} catch (Exception exception)
		{
			exception.printStackTrace();
			return i;
		}
		return j;
	}

	public static boolean stringIsNullOrEmpty(String s)
	{
		return s == null || s.equals("null") || s.equals("");
	}

	public static String trim(String s, String s1)
	{
		if (!"".equals(s) && !"".equals(s1) && s.endsWith(s1))
			s = s.substring(0, s.length() - s1.length());
		return s;
	}

	public String _getMobileDataParams(String as[], String s)
	{
		String s1 = "";
		String s2 = "";
		int i = 0;
		while (i < as.length)
		{
			StringBuilder stringbuilder = (new StringBuilder()).append(s1);
			String s3;
			StringBuilder stringbuilder1;
			String s4;
			if (s1 == "")
				s3 = as[i];
			else
				s3 = (new StringBuilder()).append("|").append(as[i]).toString();
			s1 = stringbuilder.append(s3).toString();
			stringbuilder1 = (new StringBuilder()).append(s2);
			if (s2 == "")
				s4 = as[i];
			else
				s4 = (new StringBuilder()).append("|").append(as[i]).toString();
			s2 = stringbuilder1.append(s4).toString();
			i++;
		}
		return (new StringBuilder())
				.append(_md5((new StringBuilder()).append(_md5(s1)).append(s)
						.toString())).append("|").append(s1).toString();
	}

	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static long timestamp = 0L;

}
// 2131296256