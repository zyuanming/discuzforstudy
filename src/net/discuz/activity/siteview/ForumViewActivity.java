package net.discuz.activity.siteview;

import java.util.HashMap;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.model.AllowPerm;
import net.discuz.source.ClearCache;
import net.discuz.source.DEBUG;
import net.discuz.source.DJsonReader;
import net.discuz.source.ShowMessage;
import net.discuz.source.InterFace.OnLogin;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.extend.siteview_forumdisplayManage;
import net.discuz.source.prototype.extend.siteview_forumdisplayTopManage;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.HttpConnThread;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;

public class ForumViewActivity extends DiscuzBaseActivity
{

	public ForumViewActivity()
	{
		favorite_image = null;
		favorite_box = null;
		fid = null;
		forumname = null;
		map = null;
		listLayout = null;
		displayManage = null;
		displayTopManage = null;
		isFavorite = false;
		isRefresh = false;
		onBlockViewClick = new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				site_navbar.setMoreBtnsListShow();
			}
		};
		onTitleMenuSelected = new net.discuz.source.popupwindow.TopMenuPopupWindow.OnSelectAction()
		{
			public void itemSelected(int i, String s)
			{
				if (s.equals("threads"))
				{
					showThreads();
				} else
				{
					if (s.equals("sub_forums"))
					{
						showSubForums();
						return;
					}
					if (s.equals("top_threads"))
					{
						showTopThreads();
						return;
					}
				}
			}
		};
		onNewThreadBtnClicked = new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				site_navbar.dissmissButtonsList();
				if (DiscuzApp.getInstance().getSiteInfo(siteAppId)
						.getLoginUser().getUid() < 1)
				{
					Core.showLogin(ForumViewActivity.this, new OnLogin()
					{

						public void loginError()
						{}

						public void loginSuceess(String s, JSONObject jsonobject)
						{
							siteAppId = s;
							Intent intent = new Intent();
							intent.putExtra("fid", fid);
							intent.putExtra("forumname", forumname);
							intent.putExtra("siteappid", siteAppId);
							intent.putExtra("SEND_POST_TYPE", 1);
							intent.putExtra("UPLOAD_HASH",
									jsonobject.optJSONObject("allowperm")
											.optString("uploadhash"));
							intent.setClass(ForumViewActivity.this,
									PostThreadActivity.class);
							startActivity(intent);
						}
					});
					return;
				}
				AllowPerm allowperm;
				Intent intent;
				try
				{
					allowperm = displayManage.getAllowPerm();
					DEBUG.o("=============JUMP INTO siteview_sendpost_submit===============");
					if (!allowperm.getAllowPost())
					{
						ShowMessage.getInstance(ForumViewActivity.this)
								._showToast(
										R.string.message_to_no_send_newthread,
										3);
						return;
					}
				} catch (Exception exception)
				{
					return;
				}
				intent = new Intent();
				intent.putExtra("fid", fid);
				intent.putExtra("forumname", forumname);
				intent.putExtra("siteappid", siteAppId);
				intent.putExtra("SEND_POST_TYPE", 1);
				intent.putExtra("UPLOAD_HASH", allowperm.getUploadHash());
				intent.setClass(ForumViewActivity.this,
						PostThreadActivity.class);
				startActivity(intent);
				return;
			}
		};
		onFavBtnClicked = new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				site_navbar.dissmissButtonsList();
				runOnUiThread(new Runnable()
				{

					public void run()
					{}
				});
				isFavorite = true;
				if (DiscuzApp.getInstance().getSiteInfo(siteAppId)
						.getLoginUser().getUid() > 0)
				{
					MobclickAgent
							.onEvent(ForumViewActivity.this, "c_addfavfrm");
					DiscuzStats.add(siteAppId, "c_addfavfrm");
					HttpConnThread httpconnthread = new HttpConnThread(
							siteAppId, 1);
					String as[] = new String[3];
					as[0] = siteAppId;
					as[1] = "module=favforum";
					as[2] = (new StringBuilder()).append("id=").append(fid)
							.toString();
					httpconnthread.setUrl(Core.getSiteUrl(as));
					String s = getClass().getSimpleName();
					httpconnthread.setFilter(s);
					DiscuzApp.getInstance().setHttpConnListener(s,
							httpConnListener);
					DiscuzApp.getInstance().sendHttpConnThread(httpconnthread);
					runOnUiThread(new Runnable()
					{

						public void run()
						{
							ShowMessage.getInstance(ForumViewActivity.this)
									._showToast("请稍候...", 2);
						}
					});
					return;
				} else
				{
					isFavorite = false;
					Core.showLogin(ForumViewActivity.this, new OnLogin()
					{

						public void loginError()
						{}

						public void loginSuceess(String s, JSONObject jsonobject)
						{
							siteAppId = s;
						}
					});
					return;
				}
			}
		};
		httpConnListener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
		{

			public void onFailed(Exception exception)
			{
				ShowMessageByHandler("版块收藏失败，请重试", 3);
			}

			public void onSucceed(String s, String s1)
			{
				String as[];
				if (s != null)
				{
					try
					{
						DJsonReader djsonreader = new DJsonReader(s);
						djsonreader._jsonParse();
						djsonreader._debug();
						as = djsonreader._getMessage();
						if (as[0].indexOf("_success") > 0)
						{
							ShowMessageByHandler(as, 1);
							return;
						}
					} catch (JSONException jsonexception)
					{
						ShowMessageByHandler("版块收藏失败，请重试", 3);
						return;
					}
					if (as[0].indexOf("repeat") > 0)
					{
						ShowMessageByHandler(as, 2);
						return;
					}
					ShowMessageByHandler(as, 3);
				}

			}
		};
	}

	private void _initListener()
	{
		block_view.setOnClickListener(onBlockViewClick);
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				dismissLoading();
				finish();
			}
		});
		site_navbar.setMoreBtnVisibility(true,
				(RelativeLayout) findViewById(R.id.buttons_list), block_view);
		findViewById(R.id.fav_btn).setOnClickListener(onFavBtnClicked);
		findViewById(R.id.new_thread_btn).setOnClickListener(
				onNewThreadBtnClicked);
	}

	/**
	 * 添加子板块
	 * 
	 * @param flag
	 */
	public void addSubForum(boolean flag)
	{
		if (titleMenuMaps.size() == 2)
			titleMenuMaps.put("sub_forums", "子版");
		if (!flag && titleMenuMaps.size() == 3)
		{
			titleMenuMaps.remove("threads");
			site_navbar.setTitleMenuCheck("sub_forums");
		}
	}

	protected void init()
	{
		super.init();
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		titleMenuMaps = new HashMap();
		titleMenuMaps.put("threads", "主题列表");
		titleMenuMaps.put("top_threads", "置顶帖");
		site_navbar.setTitleMenuMaps(titleMenuMaps);
		site_navbar.setParentView(findViewById(R.id.DiscuzActivityBox));
		site_navbar.setTitleClickable(true);
		site_navbar.setOnTitleMenuSelectAction(onTitleMenuSelected);
		site_navbar.setTitle(forumname);
		listLayout = (RelativeLayout) findViewById(R.id.displayListLayout);
		favorite_image = (ImageView) findViewById(R.id.favorite_childfid);
		block_view = findViewById(R.id.block_view);
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.forum_view_activity);
		Intent intent = getIntent();
		siteAppId = intent.getStringExtra("siteappid");
		fid = intent.getStringExtra("fid");
		forumname = intent.getStringExtra("forumname");
		DiscuzStats.add(siteAppId, "v_frm", "fname", forumname);
		init();
		_initListener();
		map = new HashMap();
		map.put("fid", fid);
		map.put("page", "1");
		site_navbar.setTitleMenuCheck("threads");
	}

	protected void onDestroy()
	{
		ClearCache._clearUserCacheData();
		super.onDestroy();
	}

	public void refresh()
	{
		isRefresh = true;
	}

	/**
	 * 显示子板块
	 */
	public void showSubForums()
	{
		if (displayManage == null)
		{
			displayManage = new siteview_forumdisplayManage(this, map);
			displayManage.setLoadingWhat(2);
			displayManage.newList();
		} else if (isRefresh)
		{
			isRefresh = false;
			displayManage.isShowingLoding = true;
			displayManage.setLoadingWhat(2);
			displayManage.newList();
		} else
		{
			displayManage.setLoadingWhat(2);
			if (displayManage.forumdisplay_threadlist != null)
				displayManage.updateUI();
			else
				displayManage.newList();
		}
		listLayout.removeAllViews();
		listLayout.addView(displayManage.getPullToRefresh(),
				new android.view.ViewGroup.LayoutParams(-1, -1));
	}

	/**
	 * 显示贴子
	 */
	public void showThreads()
	{
		if (displayManage == null)
		{
			displayManage = new siteview_forumdisplayManage(this, map);
			displayManage.setLoadingWhat(1);
			displayManage.newList();
		} else if (isRefresh)
		{
			isRefresh = false;
			displayManage.isShowingLoding = true;
			displayManage.setLoadingWhat(1);
			displayManage.newList();
		} else
		{
			displayManage.setLoadingWhat(1);
			if (displayManage.forumdisplay_threadlist != null)
				displayManage.updateUI();
			else
				displayManage.newList();
		}
		listLayout.removeAllViews();
		listLayout.addView(displayManage.getPullToRefresh(),
				new android.view.ViewGroup.LayoutParams(-1, -1));
	}

	/**
	 * 显示置顶贴
	 */
	public void showTopThreads()
	{
		if (displayTopManage != null)
		{
			if (isRefresh)
			{
				isRefresh = false;
				displayTopManage.isShowingLoding = true;
				displayTopManage.newList();
			}
		} else
		{
			map.put("forumname", forumname);
			displayTopManage = new siteview_forumdisplayTopManage(this, map);
			displayTopManage.newList();
		}
		listLayout.removeAllViews();
		listLayout.addView(displayTopManage.getPullToRefresh(),
				new android.view.ViewGroup.LayoutParams(-1, -1));
		return;
	}

	private View block_view;
	private siteview_forumdisplayManage displayManage;
	private siteview_forumdisplayTopManage displayTopManage;
	private LinearLayout favorite_box;
	private ImageView favorite_image;
	public String fid;
	public String forumname;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener httpConnListener;
	private boolean isFavorite;
	private boolean isRefresh;
	private RelativeLayout listLayout;
	private HashMap map;
	private android.view.View.OnClickListener onBlockViewClick;
	private android.view.View.OnClickListener onFavBtnClicked;
	private android.view.View.OnClickListener onNewThreadBtnClicked;
	private net.discuz.source.popupwindow.TopMenuPopupWindow.OnSelectAction onTitleMenuSelected;
	private SiteNavbar site_navbar;
	private HashMap titleMenuMaps;

}
// 2131296256