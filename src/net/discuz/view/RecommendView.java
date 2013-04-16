package net.discuz.view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.boardcast.HttpConnReceiver;
import net.discuz.model.SiteInfo;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.tools.Core;
import net.discuz.tools.HttpConnThread;

import org.json.JSONArray;
import org.json.JSONObject;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 推荐列表视图，列出了不同类型的站点
 * 
 * @author lkh
 * 
 */
public class RecommendView extends LinearLayout
{

	public RecommendView(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		// 应用程序开机自启动
		// 获取推荐站点信息后的回调函数
		httpConnListener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
		{

			public void onFailed(Exception exception)
			{}

			public void onSucceed(String paramAnonymousString1,
					String paramAnonymousString2)
			{
				if (paramAnonymousString1 != null)
				{
					int i;
					try
					{
						JSONObject localJSONObject1 = new JSONObject(
								paramAnonymousString1);
						if (localJSONObject1.optString("res") != null)
						{
							JSONArray localJSONArray1 = new JSONArray(
									localJSONObject1.optString("res"));

							if (localJSONArray1 != null)
							{
								recommendSites = new LinkedHashMap();
								i = 0;
								for (; i < localJSONArray1.length(); i++)
								{
									JSONArray localJSONArray2 = new JSONArray(
											localJSONArray1.optJSONObject(i)
													.optString("sites"));
									if (localJSONArray2 != null)
									{
										ArrayList localArrayList = new ArrayList();
										for (int j = 0; j < localJSONArray2
												.length(); j++)
										{
											SiteInfo localSiteInfo = new SiteInfo();
											JSONObject localJSONObject2 = localJSONArray2
													.optJSONObject(j);
											localSiteInfo
													.setSiteAppid(localJSONObject2
															.getString("siteid"));
											localSiteInfo
													.setSiteName(localJSONObject2
															.optString("sitename"));
											localSiteInfo
													.setSiteUrl(localJSONObject2
															.optString("siteurl"));
											localSiteInfo
													.setIcon(localJSONObject2
															.optString("siteicon"));
											localSiteInfo
													.setDescription(localJSONObject2
															.optString("sitedescription"));
											localArrayList.add(localSiteInfo);
										}
										recommendSites
												.put(localJSONArray1
														.optJSONObject(i)
														.optString(
																"sitetypename"),
														localArrayList);
									}
								}
								recView.removeAllViews();
								Iterator localIterator = recommendSites
										.entrySet().iterator();
								while (localIterator.hasNext())
								{
									Map.Entry localEntry = (Map.Entry) localIterator
											.next();
									addRcommendGroup(
											(String) localEntry.getKey(),
											(ArrayList) localEntry.getValue());
								}
							}
						}
					} catch (Exception localException)
					{
						localException.printStackTrace();
					}
				}
			}
		};
		mActivity = discuzbaseactivity;
		init();
	}

	private void addRcommendGroup(String s, ArrayList arraylist)
	{
		RecommendList recommendlist = new RecommendList(mActivity);
		recView.addView(recommendlist);
		recommendlist.setSiteInfoList(s, arraylist);
	}

	private void init()
	{
		View view = LayoutInflater.from(mActivity).inflate(
				R.layout.recommend_view, null);
		addView(view);
		android.view.ViewGroup.LayoutParams layoutparams = view
				.getLayoutParams();
		layoutparams.width = -1;// match_parent
		layoutparams.height = -1;// match_parent
		view.setLayoutParams(layoutparams);
		recView = (LinearLayout) view.findViewById(R.id.recommend_view);
		initData();
	}

	/**
	 * 获得推荐的站点信息
	 */
	private void initData()
	{
		httpConnThread = new HttpConnThread(null, 1);
		String s = (new StringBuilder())
				.append("http://api.discuz.qq.com/mobile/recommendList?")
				.append(Core.getInstance()._getParamsSig(new String[0]))
				.toString();
		httpConnThread.setUrl(s);
		String s1 = getClass().getSimpleName();
		httpConnThread.setFilter(s1);
		DiscuzApp.getInstance().setHttpConnListener(s1, httpConnListener);
		httpConnThread.setCacheType(2);
		DiscuzApp.getInstance().sendHttpConnThread(httpConnThread);
	}

	private HttpConnReceiver.HttpConnListener httpConnListener;
	private HttpConnThread httpConnThread;
	private DiscuzBaseActivity mActivity;
	private LinearLayout recView;
	private LinkedHashMap recommendSites;
}
// 2131296256