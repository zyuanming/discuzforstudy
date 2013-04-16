package net.discuz.activity.siteview;

import java.util.HashMap;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.source.DJsonReader;
import net.discuz.source.HttpRequest;
import net.discuz.source.ShowMessage;
import net.discuz.source.InterFace.InterfaceOnTouch;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.extend.siteview_forumsmsdisplayManage;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.umeng.analytics.MobclickAgent;

public class siteview_forumsmsdisplay extends DiscuzBaseActivity
{
	class ClickListener implements android.view.View.OnClickListener
	{

		public void onClick(View view)
		{
			MobclickAgent.onEvent(siteview_forumsmsdisplay.this, "c_replymsg");
			DiscuzStats.add(siteAppId, "c_replymsg");
			switch (view.getId())
			{
			default:
				return;

			case 2131231082:
				break;
			}
			if (pmid == null)
			{
				ShowMessage
						.getInstance(siteview_forumsmsdisplay.this)
						._showToast(
								"\u77ED\u6D88\u606F\u6B63\u5728\u52A0\u8F7D\u8BF7\u7A0D\u5019",
								2);
				return;
			} else
			{
				String s = editMessage.getText().toString();
				_sendMessage(s);
				return;
			}
		}

		ClickListener()
		{
			super();
		}
	}

	public siteview_forumsmsdisplay()
	{
		smsDisplayManage = null;
		listLinearLayout = null;
		editMessage = null;
		returnMessage = null;
		touid = null;
		pmid = null;
		friendName = null;
		interfaceOnTouch = null;
		page = 1;
		handlerUI = new Handler()
		{

			public void handleMessage(Message message)
			{
				switch (message.what)
				{
				case 1:
					smsDisplayManage.loadMoreMessage(true);
					break;
				case 2:
					smsDisplayManage.loadMoreMessage(false);
					break;
				case 3:
					editMessage.setText("");
					smsDisplayManage.setPage(0);
				default:
				}
				super.handleMessage(message);
				return;
			}
		};
	}

	public void _sendMessage(final String _message)
	{
		(new Thread()
		{

			public void run()
			{
				Looper.prepare();
				String s1;
				boolean flag;
				showLoading("\u56DE\u590D\u4E2D...");
				String as[] = new String[4];
				as[0] = siteAppId;
				as[1] = "module=sendpm";
				as[2] = (new StringBuilder()).append("pmid=").append(pmid)
						.toString();
				as[3] = "pmsubmit=yes";
				String s = Core.getSiteUrl(as);
				HashMap hashmap = new HashMap();
				hashmap.put("message", _message);
				hashmap.put("formhash",
						DiscuzApp.getInstance().getSiteInfo(siteAppId)
								.getLoginUser().getFormHash());
				try
				{
					s1 = (new HttpRequest(siteAppId))._post(s, null, hashmap,
							"GBK");
					flag = "error".equals(s1);
					String s2 = null;
					Exception exception;
					DJsonReader djsonreader;
					if (!flag)
						s2 = s1;
					if (s2 != null)
					{
						djsonreader = new DJsonReader(s2);
						djsonreader._jsonParse();
						djsonreader._debug();
						if (djsonreader._getMessage()[0].equals("do_success"))
						{
							smsDisplayManage.isShowLoadMore = false;
							handlerUI.sendEmptyMessage(3);
							smsDisplayManage.setPage(0);
							smsDisplayManage.newList();
						}
						ShowMessage.getInstance(siteview_forumsmsdisplay.this)
								._showToast(djsonreader._getMessage()[1], 1);
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				dismissLoading();
				Looper.loop();
				super.run();
			}
		}).start();
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.sms_main);
		touid = getIntent().getStringExtra("touid");
		friendName = getIntent().getStringExtra("friendname");
		page = getIntent().getIntExtra("page", 1);
		siteAppId = getIntent().getStringExtra("siteappid");
		MobclickAgent.onEvent(this, "v_msg");
		DiscuzStats.add(siteAppId, "v_msg");
		listLinearLayout = (LinearLayout) findViewById(R.id.sms_main_center);
		returnMessage = (Button) findViewById(R.id.sub_return_message);
		editMessage = (EditText) findViewById(R.id.edit_message);
		editMessage
				.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener()
				{

					public void onFocusChange(View view, boolean flag)
					{}
				});
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		site_navbar
				.setOnRefreshBtnClicked(new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						smsDisplayManage.update();
						MobclickAgent.onEvent(siteview_forumsmsdisplay.this,
								"c_refresh");
						DiscuzStats.add(siteAppId, "c_refresh");
					}
				});
		site_navbar.setTitleClickable(false);
		returnMessage.setOnClickListener(new ClickListener());
		smsDisplayManage = new siteview_forumsmsdisplayManage(this, touid, page);
		site_navbar.setTitle(friendName);
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				dismissLoading();
				finish();
			}
		});
		smsDisplayManage.newList();
		listLinearLayout.removeAllViews();
		listLinearLayout.addView(smsDisplayManage.getPullToRefresh(),
				new android.view.ViewGroup.LayoutParams(-1, -1));
		View aview[] = new View[1];
		aview[0] = listLinearLayout;
		onTouchListener(aview);
	}

	public boolean onKeyDown(int i, KeyEvent keyevent)
	{
		dismissLoading();
		return super.onKeyDown(i, keyevent);
	}

	protected void onPause()
	{
		super.onPause();
		MobclickAgent.onPause(this);
	}

	protected void onResume()
	{
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public boolean onTouchEvent(MotionEvent motionevent)
	{
		if (interfaceOnTouch != null)
			interfaceOnTouch.DOnTouch();
		return super.onTouchEvent(motionevent);
	}

	public void refresh()
	{
		smsDisplayManage.isShowingLoding = true;
		smsDisplayManage.update();
	}

	public void setOnTouch(InterfaceOnTouch interfaceontouch)
	{
		interfaceOnTouch = interfaceontouch;
		smsDisplayManage.setOnTouch(interfaceontouch);
	}

	private EditText editMessage;
	private String friendName;
	public Handler handlerUI;
	private InterfaceOnTouch interfaceOnTouch;
	private LinearLayout listLinearLayout;
	private int page;
	public String pmid;
	private Button returnMessage;
	private SiteNavbar site_navbar;
	private siteview_forumsmsdisplayManage smsDisplayManage;
	private String touid;

}
// 2131296256