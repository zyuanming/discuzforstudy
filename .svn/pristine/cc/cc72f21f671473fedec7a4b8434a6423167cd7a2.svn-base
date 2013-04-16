package net.discuz.source;

import android.os.AsyncTask;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.discuz.model.MemberUpdate;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.GlobalDBHelper;
import net.discuz.tools.Core;
import org.json.JSONObject;

public class UpdateApp
{
	private class JsonReader_UpDate extends DJsonReader
	{

		public void _variableParse(JSONObject jsonobject)
		{
			initMember._initValue(jsonobject);
		}

		public void isUpdata()
		{
			GlobalDBHelper globaldbhelper = GlobalDBHelper.getInstance();
			if ("0".equals(code))
			{
				if (-5 + Integer.valueOf(initMember.getVersion()).intValue() > Core
						.getInstance()._getVersionCode())
				{
					globaldbhelper.replace("must_update", "true");
					DEBUG.o("******\u5F3A\u5236\u5347\u7EA7*********");
					return;
				} else
				{
					globaldbhelper.replace("is_need_update", "true");
					globaldbhelper.replace("updata_information",
							initMember.getDescription());
					DEBUG.o("*******\u666E\u901A\u5347\u7EA7********");
					return;
				}
			} else
			{
				DEBUG.o("******\u672A\u53D1\u73B0\u65B0\u7248\u672C******");
				globaldbhelper.replace("is_need_update", "false");
				return;
			}
		}

		private MemberUpdate initMember;

		public JsonReader_UpDate(String s)
		{
			super(s);
			initMember = new MemberUpdate();
		}
	}

	private class UpdataeAsyncTask extends AsyncTask
	{

		protected Object doInBackground(Object aobj[])
		{
			return doInBackground((Void[]) aobj);
		}

		protected Void doInBackground(Void avoid[])
		{
			if (update == null)
				return null;
			if (isFirstOpen() == null || "".equals(isFirstOpen()))
			{
				update.firstOpen();
				DFile._deleteFileOrDir("/sdcard/discuz/cache/");
				DFile._deleteFileOrDir("/sdcard/discuz/style/");
				db.replace("is_first_open", "false");
			}
			if (isPastTime())
			{
				internetCheckUpdate();
				update.neendCheckUpdate();
			}
			if (System.currentTimeMillis() - enterAppTime < 1000L)
				try
				{
					Thread.sleep(1000L - (System.currentTimeMillis() - enterAppTime));
				} catch (InterruptedException interruptedexception)
				{
					interruptedexception.printStackTrace();
				}
			update.finishSucess();
			return null;
		}
	}

	public static interface UpdateAppInterface
	{

		public abstract void finishSucess();

		public abstract void firstOpen();

		public abstract void neendCheckUpdate();
	}

	public UpdateApp(DiscuzBaseActivity discuzbaseactivity,
			UpdateAppInterface updateappinterface)
	{
		df = new SimpleDateFormat("yyyy-MM-dd");
		db = null;
		enterAppTime = System.currentTimeMillis();
		update = null;
		context = discuzbaseactivity;
		update = updateappinterface;
		db = GlobalDBHelper.getInstance();
		(new UpdataeAsyncTask()).execute(new Void[0]);
	}

	private String getNumToDateTime(long l)
	{
		return df.format(new Date(l));
	}

	private void internetCheckUpdate()
	{
		HttpRequest httprequest = new HttpRequest();
		try
		{
			String s = httprequest
					._get((new StringBuilder())
							.append("http://api.discuz.qq.com/mobile/checkUpdate?")
							.append(Core.getInstance()._getParamsSig(
									new String[0])).toString(), null, "utf-8");
			if (s != null)
			{
				JsonReader_UpDate jsonreader_update = new JsonReader_UpDate(s);
				jsonreader_update._jsonParse();
				jsonreader_update._debug();
				jsonreader_update.isUpdata();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private String isFirstOpen()
	{
		return db.getValue("is_first_open");
	}

	private boolean isPastTime()
	{
		String s = getNumToDateTime((new Date()).getTime());
		if (!s.equals(db.getValue("last_check_update_timestamp")))
		{
			db.replace("last_check_update_timestamp", s);
			return true;
		} else
		{
			return false;
		}
	}

	private final String LAST_CHECK_UPDATE_TIME = "last_check_update_timestamp";
	private DiscuzBaseActivity context;
	private GlobalDBHelper db;
	private SimpleDateFormat df;
	private long enterAppTime;
	private UpdateAppInterface update;

}
//2131296256