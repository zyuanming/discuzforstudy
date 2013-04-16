package net.discuz.view;

import java.io.File;
import java.util.ArrayList;

import net.discuz.R;
import net.discuz.activity.siteview.SiteViewActivity;
import net.discuz.model.DownFile;
import net.discuz.model.SiteInfo;
import net.discuz.source.DFile;
import net.discuz.source.SiteDetail;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.service.DownLoadService;
import net.discuz.source.storage.SiteInfoDBHelper;
import net.discuz.tools.DiscuzStats;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

/**
 * 推荐站点视图，网格式的
 * 
 * @author lkh
 * 
 */
public class RecommendList extends LinearLayout
{

	public RecommendList(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		mActivity = discuzbaseactivity;
		mHandler = new Handler();
		init();
	}

	private void init()
	{
		View view = LayoutInflater.from(mActivity).inflate(
				R.layout.recommend_list, null);
		addView(view);
		android.view.ViewGroup.LayoutParams layoutparams = view
				.getLayoutParams();
		layoutparams.width = -1;
		layoutparams.height = -1;
		view.setLayoutParams(layoutparams);
		mItemContent = (LinearLayout) findViewById(R.id.recommend_items);
		mGourpName = (TextView) findViewById(R.id.recommend_groupname);
	}

	private void loadData(ArrayList arraylist)
	{
		int i = arraylist.size();
		for (int j = 0; j < i; j++)
		{
			final SiteItemView item = new SiteItemView(mActivity);
			mItemContent.addView(item);
			SiteInfo siteinfo = (SiteInfo) arraylist.get(j);
			final String siteUrl = siteinfo.getSiteUrl();
			final String siteName = siteinfo.getSiteName();
			item.setSiteInfo(siteinfo);
			item.setOnClickListener(new android.view.View.OnClickListener()
			{

				// 点击站点图标事件，进入站点
				public void onClick(View view)
				{
					SiteInfo siteinfo1 = SiteInfoDBHelper.getInstance()
							.getByUrl(siteUrl);
					if (siteinfo1 == null)
					{
						mActivity.showLoading(mActivity.getResources()
								.getString(R.string.loading_site)); // 读取站点信息...
						new SiteDetail(mActivity, siteUrl, siteName,
								new DownLoadService.DownLoadInterface()
								{

									public void downLoadError(
											Exception exception)
									{
										mActivity.dismissLoading();
									}

									public void downLoadSuccess(String s)
									{
										MobclickAgent.onEvent(mActivity,
												"c_siteindex_reco");
										DiscuzStats.add(null,
												"c_siteindex_reco");
										invalidate();
										Intent intent = new Intent();
										intent.putExtra("siteurl", siteUrl);
										intent.setClass(mActivity,
												SiteViewActivity.class);
										mActivity.startActivity(intent);
										mActivity.dismissLoading();
									}
								});
						return;
					} else
					{
						MobclickAgent.onEvent(mActivity, "c_siteindex_reco");
						DiscuzStats.add(null, "c_siteindex_reco");
						Intent intent = new Intent();
						intent.putExtra("siteappid", siteinfo1.getSiteAppid());
						intent.setClass(mActivity, SiteViewActivity.class);
						mActivity.startActivity(intent);
						return;
					}
				}
			});
			mHandler.postDelayed(new Runnable()
			{
				public void run()
				{
					String s = (new StringBuilder()).append("site_icon_")
							.append(siteUrl.split("\\.")[1]).append(".png")
							.toString();
					if (!(new File((new StringBuilder())
							.append("/sdcard/discuz/style/").append(s)
							.toString())).exists())
					{
						(new DFile())._createDir("/sdcard/discuz/style/");
						DownLoadService
								.setDownLoadParams(
										mActivity,
										new DownFile(
												(new StringBuilder())
														.append(siteUrl)
														.append("/mobile.png")
														.toString(),
												s,
												"/sdcard/discuz/style/",
												false,
												false,
												false,
												new net.discuz.source.service.DownLoadService.DownLoadInterface()
												{

													public void downLoadError(
															Exception exception)
													{}

													public void downLoadSuccess(
															String s)
													{
														item.refresh();
													}
												}));
						mActivity.startService(new Intent(mActivity,
								DownLoadService.class));
					}
				}
			}, 200L);
		}

	}

	public void setSiteInfoList(String s, ArrayList arraylist)
	{
		if (s != null)
			mGourpName.setText(s);
		if (arraylist != null)
			loadData(arraylist);
	}

	private DiscuzBaseActivity mActivity;
	private TextView mGourpName;
	private Handler mHandler;
	private LinearLayout mItemContent;

}
// 2131296256