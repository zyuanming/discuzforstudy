package net.discuz.activity.siteview;

import net.discuz.MainActivity;
import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.model.NoticeListItem;
import net.discuz.model.SiteInfo;
import net.discuz.source.DAnimation;
import net.discuz.source.ShowMessage;
import net.discuz.source.InterFace.OnLogin;
import net.discuz.source.InterFace.OnLogout;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.GlobalDBHelper;
import net.discuz.source.storage.NoticeListDBHelper;
import net.discuz.source.storage.SiteInfoDBHelper;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.NoticeCenter;
import net.discuz.tools.Tools;
import net.discuz.view.ForumIndexView;
import net.discuz.view.ForumRecommendView;
import net.discuz.view.MyCenterView;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;

/**
 * 站点视图,点击站点来到的页面
 * 
 * @author Ming
 * 
 */
public class SiteViewActivity extends DiscuzBaseActivity
{

	public SiteViewActivity()
	{
		onBlockViewClick = new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				block_view.setVisibility(8);
				sectorMenu.setVisibility(8);
				sectorMenu.startAnimation(DAnimation.getSectorRotateAnimation(
						0.0F, 90F, 300));
				sectorMenuButtonIcon.startAnimation(DAnimation
						.getRotateAnimation(-225F, 0.0F, 300));
				isSectorMenuShowing = false;
			}
		};

		// 右下角加号menu的点击事件，就是弹开和不弹开Menu
		onSectorMenuMainButtonClick = new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				SiteViewActivity siteviewactivity = SiteViewActivity.this;
				boolean flag;
				if (!isSectorMenuShowing)
					flag = true;
				else
					flag = false;
				siteviewactivity.showSectorMenu(flag);
			}
		};
		// 加号menu里面的三个菜单的点击事件
		onSectorMenuBtnClick = new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				switch (view.getId())
				{
				case R.id.forum_btn:
					setForumIndexView();
					break;
				case R.id.recommend_btn:
					setForumRecommendView();
					break;
				case R.id.my_btn:
					if (DiscuzApp.getInstance().getSiteInfo(siteAppId)
							.getLoginUser().getUid() < 1)
						Core.showLogin(SiteViewActivity.this, new OnLogin()
						{
							public void loginError()
							{}

							public void loginSuceess(String s,
									JSONObject jsonobject)
							{
								siteAppId = s;
								setMyCenterView();
							}
						});
					else
						setMyCenterView();
					break;
				default:
				}
				showSectorMenu(false);
				return;
			}
		};
	}

	private void refreshSectorMenuBtnState()
	{
		switch (currentViewId)
		{
		default:
			return;

		case R.id.forum_btn: // 板块
			forumBtn.setImageResource(R.drawable.all_forum_btn_selected);
			recommendBtn.setImageResource(R.drawable.recommend_btn_selector);
			myBtn.setImageResource(R.drawable.my_btn_selector);
			return;

		case R.id.my_btn: // 我的
			forumBtn.setImageResource(R.drawable.all_forum_btn_selector);
			recommendBtn.setImageResource(R.drawable.recommend_btn_selector);
			myBtn.setImageResource(R.drawable.my_btn_selected);
			return;

		case R.id.recommend_btn: // 首页
			forumBtn.setImageResource(R.drawable.all_forum_btn_selector);
			recommendBtn.setImageResource(R.drawable.recommend_btn_selected);
			myBtn.setImageResource(R.drawable.my_btn_selector);
			return;
		}
	}

	/**
	 * 热门板块，全部板块
	 */
	private void setForumIndexView()
	{
		ForumIndexView forumindexview = (ForumIndexView) views
				.get(R.id.forum_btn);
		if (forumindexview == null)
		{
			forumindexview = new ForumIndexView(this);
			views.put(R.id.forum_btn, forumindexview);
		}
		main_container.removeAllViews();
		main_container.addView(forumindexview);
		site_navbar.setTitleMenuMaps(forumindexview.getTitleMenuMaps());
		site_navbar.setParentView(findViewById(R.id.DiscuzActivityBox));
		site_navbar.setTitleClickable(true);
		site_navbar.setOnTitleMenuSelectAction(forumindexview
				.getOnTitleMenuSelected());
		site_navbar.setTitleMenuCheck("hot_forums");
		site_navbar.setLeftBtnType(0);
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				setForumRecommendView();
			}
		});
		currentViewId = R.id.forum_btn;
		refreshSectorMenuBtnState();
	}

	/**
	 * 热门板块
	 */
	private void setForumRecommendView()
	{
		site_navbar.setTitle(sitename);
		Object obj = (View) views.get(R.id.recommend_btn);
		if (obj == null)
		{
			obj = new ForumRecommendView(this);
			views.put(R.id.recommend_btn, obj);
		}
		MobclickAgent.onEvent(this, "v_recommend");
		DiscuzStats.add(siteAppId, "v_recommend");
		main_container.removeAllViews();
		main_container.addView(((View) (obj)));
		site_navbar.setTitleClickable(false);
		site_navbar.setLeftBtnType(1);
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			/**
			 * 上方导航栏的返回
			 */
			public void onClick(View view)
			{
				MobclickAgent.onEvent(SiteViewActivity.this, "c_house");
				DiscuzStats.add(siteAppId, "c_house");
				Intent intent = new Intent();
				intent.setClass(SiteViewActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		currentViewId = R.id.recommend_btn;
		refreshSectorMenuBtnState();
	}

	private void setMyCenterView()
	{
		red_point.setVisibility(8);
		site_navbar.setTitle("我的");
		MyCenterView mycenterview = (MyCenterView) views.get(R.id.my_btn);
		if (mycenterview == null)
		{
			mycenterview = new MyCenterView(this);
			views.put(R.id.my_btn, mycenterview);
			mycenterview.setOnLogout(new OnLogout()
			{

				public void logout()
				{
					views.remove(R.id.my_btn);
					setForumRecommendView();
				}
			});
		}
		MobclickAgent.onEvent(this, "v_my");
		DiscuzStats.add(siteAppId, "v_my");
		main_container.removeAllViews();
		main_container.addView(mycenterview);
		mycenterview.refresh();
		site_navbar.setTitleClickable(false);
		site_navbar.setLeftBtnType(0);
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				setForumRecommendView();
			}
		});
		currentViewId = R.id.my_btn;
		refreshSectorMenuBtnState();
	}

	/**
	 * 显示右下角菜单的动画，4分一圆
	 * 
	 * @param flag
	 *            标志菜单是关闭还是打开
	 */
	private void showSectorMenu(boolean flag)
	{
		if (flag)
		{
			NoticeListItem noticelistitem = NoticeListDBHelper.getInstance()
					.select(cloudid);
			if (noticelistitem != null
					&& noticelistitem.pmclick.intValue() == 0)
				red_point.setVisibility(0);
			else
				red_point.setVisibility(8);
			block_view.setVisibility(0);
			sectorMenu.setVisibility(0);
			forumBtn.setOnClickListener(onSectorMenuBtnClick);
			recommendBtn.setOnClickListener(onSectorMenuBtnClick);
			myBtn.setOnClickListener(onSectorMenuBtnClick);
			sectorMenu.startAnimation(DAnimation.getSectorRotateAnimation(90F,
					0.0F, 300));
			sectorMenuButtonIcon.startAnimation(DAnimation.getRotateAnimation(
					0.0F, -225F, 300));
		} else
		{
			block_view.setVisibility(8);
			sectorMenu.setVisibility(8);
			forumBtn.setOnClickListener(null);
			recommendBtn.setOnClickListener(null);
			myBtn.setOnClickListener(null);
			sectorMenu.startAnimation(DAnimation.getSectorRotateAnimation(0.0F,
					90F, 300));
			sectorMenuButtonIcon.startAnimation(DAnimation.getRotateAnimation(
					-225F, 0.0F, 300));
		}
		isSectorMenuShowing = flag;
	}

	protected void init()
	{
		super.init();
		views = new SparseArray();
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar); // 上方的返回导航栏
		site_navbar.setTitle(sitename);
		main_container = (RelativeLayout) findViewById(R.id.main_container);
		block_view = findViewById(R.id.block_view);
		block_view.setOnClickListener(onBlockViewClick);
		red_point = (ImageView) findViewById(R.id.red_point);
		sectorMenu = findViewById(R.id.sector_menu);
		sectorMenuButtonIcon = findViewById(R.id.sector_menu_main_btn);// 右下角的动画菜单
		forumBtn = (ImageButton) findViewById(R.id.forum_btn);
		recommendBtn = (ImageButton) findViewById(R.id.recommend_btn);
		myBtn = (ImageButton) findViewById(R.id.my_btn);
		sectorMenuButtonIcon.setOnClickListener(onSectorMenuMainButtonClick);
		setForumRecommendView();
	}

	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		siteAppId = getIntent().getStringExtra("siteappid");
		siteUrl = getIntent().getStringExtra("siteurl");
		SiteInfoDBHelper siteinfodbhelper = SiteInfoDBHelper.getInstance();
		SiteInfo siteinfo;
		if (Tools.stringIsNullOrEmpty(siteAppId))
			siteinfo = siteinfodbhelper.getByUrl(siteUrl);
		else
			siteinfo = siteinfodbhelper.getByAppId(siteAppId);
		if (siteinfo != null)
		{
			siteAppId = siteinfo.getSiteAppid();
			siteUrl = siteinfo.getSiteUrl();
			cloudid = siteinfo.getCloudId();
		}
		if (siteinfo != null && "0".equals(siteinfo.getClientview()))
		{
			NoticeCenter.addToken(siteinfo.getCloudId());
			GlobalDBHelper.getInstance().replace("sitelist_backsiteid",
					siteAppId);
			DiscuzApp.getInstance().setSiteInfo(siteinfo);
			DiscuzApp.getInstance().resetUserToGuest(siteAppId);
			DiscuzApp.getInstance().loadDBUser(siteAppId);
			MobclickAgent.onEvent(this, "c_onService");
			DiscuzStats.add(siteAppId, "c_onService");

			// layout
			setContentView(R.layout.site_view_activity);
			sitename = siteinfo.getSiteName();
			init();
		} else
		{
			if (!Tools.stringIsNullOrEmpty(siteUrl))
			{
				MobclickAgent.onEvent(this, "c_noService");
				DiscuzStats.add("", siteUrl, "c_noService");
				String s;
				String s1;
				String s2;
				if (siteinfo != null)
					s = siteinfo.getSiteUrl();
				else
					s = siteUrl;
				s1 = Tools.getHttpUrl(s);

				// 设置站点客户端模式，表明访问的是手机端的站点
				Tools.CheckSiteClientMode(getClass().getSimpleName(), s1, "",
						null);
				if (!s1.endsWith("/"))
					s1 = (new StringBuilder()).append(s1).append("/")
							.toString();
				if (s1.contains("?"))
					s2 = (new StringBuilder()).append(s1).append("&mobile=yes")
							.toString();
				else
					s2 = (new StringBuilder()).append(s1).append("?mobile=yes")
							.toString();

				// 网站客户端服务未开通，系统将启用手机浏览器浏览
				ShowMessage.getInstance(this)._showToast(
						R.string.message_response_clienterror, 2, 1);
				Core.getInstance().gotoUrlByWebView(s2);
				finish();
				return;
			}
		}
	}

	public boolean onKeyDown(int i, KeyEvent keyevent)
	{
		if (i == 4)
		{
			if (isSectorMenuShowing)
			{
				showSectorMenu(false);
				return false;
			}
			if (currentViewId == R.id.recommend_btn)
			{
				Intent intent = new Intent();
				intent.setClass(this, MainActivity.class);
				startActivity(intent);
				finish();
				return false;
			} else
			{
				setForumRecommendView();
				return false;
			}
		} else
		{
			return super.onKeyDown(i, keyevent);
		}
	}

	private View block_view;
	private String cloudid;
	private int currentViewId;
	private ImageButton forumBtn;
	private boolean isSectorMenuShowing;
	private RelativeLayout main_container;
	private ImageButton myBtn;
	private android.view.View.OnClickListener onBlockViewClick;
	private android.view.View.OnClickListener onSectorMenuBtnClick;
	private android.view.View.OnClickListener onSectorMenuMainButtonClick;
	private ImageButton recommendBtn;
	private ImageView red_point;
	private View sectorMenu;
	private View sectorMenuButtonIcon;
	private SiteNavbar site_navbar;
	private String sitename;
	private SparseArray views;

}
// 2131296256