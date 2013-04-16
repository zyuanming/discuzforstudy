package net.discuz.asynctask;

import java.util.ArrayList;
import net.discuz.activity.sitelist.SiteSearchActivity;
import net.discuz.app.DiscuzApp;
import net.discuz.model.SiteInfo;
import net.discuz.source.DEBUG;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.tools.Core;
import net.discuz.tools.HttpConnThread;
import org.json.JSONArray;
import org.json.JSONObject;

public class StrongRecommend
{

	public StrongRecommend(DiscuzBaseActivity discuzbaseactivity)
	{
		httpConnListener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
		{
			public void onFailed(Exception exception)
			{
				if (activity != null)
					activity.dismissLoading();
			}

			public void onSucceed(String paramAnonymousString1,
					String paramAnonymousString2)
			{
				if (paramAnonymousString1 != null)
					;
				try
				{
					JSONObject localJSONObject1 = new JSONObject(
							paramAnonymousString1);
					if (localJSONObject1.optString("res") != null)
					{
						DEBUG.o(localJSONObject1.optString("res"));
						JSONArray localJSONArray1 = new JSONArray(
								localJSONObject1.optString("res"));
						if (localJSONArray1 != null)
						{
							ArrayList localArrayList = new ArrayList();
							JSONArray localJSONArray2 = new JSONArray(
									localJSONArray1.optJSONObject(0).optString(
											"sites"));
							int i = 0;
							if (localJSONArray2 != null)
								while (i < localJSONArray2.length())
								{
									SiteInfo localSiteInfo = new SiteInfo();
									JSONObject localJSONObject2 = localJSONArray2
											.optJSONObject(i);
									localSiteInfo.setSiteAppid(localJSONObject2
											.getString("siteid"));
									localSiteInfo.setSiteName(localJSONObject2
											.optString("sitename"));
									localSiteInfo.setSiteUrl(localJSONObject2
											.optString("siteurl"));
									localArrayList.add(localSiteInfo);
									i++;
								}
							if (StrongRecommend.this.activity != null)
								((SiteSearchActivity) StrongRecommend.this.activity)
										.updateTags(localArrayList);
						}
					}
				} catch (Exception localException)
				{
					localException.printStackTrace();
				} finally
				{
					if (StrongRecommend.this.activity != null)
						StrongRecommend.this.activity.dismissLoading();
				}
			}
		};
		activity = discuzbaseactivity;
		initData();
	}

	private void initData()
	{
		if (activity != null)
			activity.showLoading("\u6B63\u5728\u52A0\u8F7D...");
		httpConnThread = new HttpConnThread(null, 1);
		String s = (new StringBuilder())
				.append("http://api.discuz.qq.com/mobile/recommendList?")
				.append(Core.getInstance()._getParamsSig(
						new String[] { "type=strong" })).toString();
		httpConnThread.setUrl(s);
		httpConnThread.setCachePath("_t/");
		String s1 = getClass().getSimpleName();
		httpConnThread.setFilter(s1);
		DiscuzApp.getInstance().setHttpConnListener(s1, httpConnListener);
		httpConnThread.setCacheType(2);
		DiscuzApp.getInstance().sendHttpConnThread(httpConnThread);
	}

	private DiscuzBaseActivity activity;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener httpConnListener;
	private HttpConnThread httpConnThread;

}
//2131296256