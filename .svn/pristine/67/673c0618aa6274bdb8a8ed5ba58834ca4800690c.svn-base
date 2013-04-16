package net.discuz.asynctask;

import android.os.AsyncTask;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.discuz.model.SiteInfo;
import net.discuz.source.DEBUG;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.GlobalDBHelper;
import net.discuz.source.storage.SiteInfoDBHelper;
import net.discuz.tools.Tools;

/**
 * 检查喜欢的站点
 * 
 * @author lkh
 * 
 */
public class CheckFavSite extends AsyncTask
{

	public CheckFavSite(DiscuzBaseActivity discuzbaseactivity)
	{
		sdf = new SimpleDateFormat("yyyyMMdd");
		activity = discuzbaseactivity;
	}

	private boolean CheckFavSiteTimestamp()
	{
		String s;
		boolean flag;
		boolean flag1;
		try
		{
			s = GlobalDBHelper.getInstance().getValue(
					"daily_check_favsite_timestamp");
		} catch (Exception exception)
		{
			exception.printStackTrace();
			return false;
		}
		flag = false;
		if (s != null)
		{
			DEBUG.o((new StringBuilder())
					.append("=================LAST CHECK FAVSITE TIMESTAMP==============")
					.append(s).toString());
			flag1 = sdf.format(Long.valueOf((new Date()).getTime())).equals(s);
			flag = false;
			if (!flag1)
				flag = true;
		}
		return flag;
	}

	public Object doInBackground(Object aobj[])
	{
		return doInBackground((Void[]) aobj);
	}

	public Void doInBackground(Void avoid[])
	{
		if (CheckFavSiteTimestamp())
		{
			List list = SiteInfoDBHelper.getInstance().getNeedUpdate();
			if (list != null)
			{
				int i = list.size();
				for (int j = 0; j < i; j++)
				{
					SiteInfo siteinfo = (SiteInfo) list.get(j);
					String s = siteinfo.getSiteAppid().toLowerCase();
					String s1 = siteinfo.getSiteUrl().toLowerCase();
					Tools.CheckSiteClientMode(getClass().getSimpleName(), s1,
							s, null);
				}

			}
			GlobalDBHelper.getInstance().replace(
					"daily_check_favsite_timestamp",
					sdf.format(Long.valueOf((new Date()).getTime())));
		}
		return null;
	}

	private DiscuzBaseActivity activity;
	private SimpleDateFormat sdf;
}
// 2131296256