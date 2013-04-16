package net.discuz.activity.siteview;

import java.util.HashMap;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.source.InterFace.OnLogin;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.extend.SMSPersonalManage;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;
import net.discuz.view.MyPrivateMsgView;
import net.discuz.view.MyPublicMsgView;

import org.json.JSONObject;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class MyMessageActivity extends DiscuzBaseActivity
{

	public MyMessageActivity()
	{
		smsPersonalMenage = null;
		pmClickListener = new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				if (DiscuzApp.getInstance().getLoginUser(siteAppId).getUid() > 0)
				{
					try
					{
						if (smsPersonalMenage != null)
							smsPersonalMenage._openNewMessage();
						return;
					} catch (Exception exception)
					{
						exception.printStackTrace();
					}
					return;
				} else
				{
					Core.showLogin(MyMessageActivity.this, new OnLogin()
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
		onTitleMenuSelected = new net.discuz.source.popupwindow.TopMenuPopupWindow.OnSelectAction()
		{

			public void itemSelected(int i, String s)
			{
				if (s.equals("private_msg"))
					showPrivateMsgView();
				else if (s.equals("public_msg"))
				{
					showPublicMsgView();
					return;
				}
			}
		};
	}

	private void showPrivateMsgView()
	{
		Object obj = (View) views.get(0);
		if (obj == null)
		{
			obj = new MyPrivateMsgView(this);
			views.put(0, obj);
		}
		MobclickAgent.onEvent(this, "v_mymsg");
		DiscuzStats.add(siteAppId, "v_mymsg");
		main_container.removeAllViews();
		main_container.addView(((View) (obj)));
		header_title.setText((CharSequence) titleMenuMaps.get("private_msg"));
	}

	private void showPublicMsgView()
	{
		Object obj = (View) views.get(1);
		if (obj == null)
		{
			obj = new MyPublicMsgView(this);
			views.put(1, obj);
		}
		main_container.removeAllViews();
		main_container.addView(((View) (obj)));
		header_title.setText((CharSequence) titleMenuMaps.get("public_msg"));
	}

	public void finish()
	{
		dismissLoading();
		super.finish();
	}

	protected void init()
	{
		super.init();
		views = new SparseArray();
		header_title = (TextView) findViewById(R.id.header_title);
		main_container = (RelativeLayout) findViewById(R.id.main_container);
		if (smsPersonalMenage == null)
			smsPersonalMenage = new SMSPersonalManage(this);
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		titleMenuMaps = new HashMap();
		titleMenuMaps.put("public_msg", "\u516C\u4FE1\u6D88\u606F");
		titleMenuMaps.put("private_msg", "\u79C1\u4FE1\u6D88\u606F");
		site_navbar.setTitleMenuMaps(titleMenuMaps);
		site_navbar.setParentView(findViewById(R.id.DiscuzActivityBox));
		site_navbar.setTitleClickable(true);
		site_navbar.setOnTitleMenuSelectAction(onTitleMenuSelected);
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				finish();
			}
		});
		site_navbar.setOnSendSMSBtnClicked(pmClickListener);
		site_navbar.setTitleMenuCheck("private_msg");
	}

	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		siteAppId = getIntent().getStringExtra("siteappid");
		setContentView(R.layout.my_center_base_activity);
		init();
	}

	protected void onDestroy()
	{
		main_container = null;
		views.clear();
		views = null;
		titleMenuMaps.clear();
		titleMenuMaps = null;
		site_navbar = null;
		smsPersonalMenage = null;
		super.onDestroy();
	}

	public boolean onKeyDown(int i, KeyEvent keyevent)
	{
		if (i == 4)
		{
			finish();
			return false;
		} else
		{
			return super.onKeyDown(i, keyevent);
		}
	}

	private Core core;
	private TextView header_title;
	private RelativeLayout main_container;
	private net.discuz.source.popupwindow.TopMenuPopupWindow.OnSelectAction onTitleMenuSelected;
	private android.view.View.OnClickListener pmClickListener;
	private SiteNavbar site_navbar;
	private SMSPersonalManage smsPersonalMenage;
	private HashMap titleMenuMaps;
	private SparseArray views;

}
// 2131296256