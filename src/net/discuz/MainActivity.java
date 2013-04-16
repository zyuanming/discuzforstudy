package net.discuz;

import java.util.List;

import net.discuz.activity.sitelist.SiteSearchActivity;
import net.discuz.activity.siteview.noticecenterlist_new;
import net.discuz.source.UpdateApp;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.GlobalDBHelper;
import net.discuz.source.storage.NoticeListDBHelper;
import net.discuz.source.storage.SiteInfoDBHelper;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.NoticeCenter;
import net.discuz.tools.Tools;
import net.discuz.view.HistroyView;
import net.discuz.view.RecommendView;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.umeng.analytics.MobclickAgent;

/**
 * 主界面
 * 
 * @author Ming
 * 
 */
public class MainActivity extends DiscuzBaseActivity implements
		android.widget.RadioGroup.OnCheckedChangeListener
{

	public MainActivity()
	{}

	private void setHistroyView()
	{
		View view = (View) views.get(R.id.radio_button_right);
		Object obj;
		if (view == null)
		{
			obj = new HistroyView(this);
			views.put(R.id.radio_button_right, obj);
		} else
		{
			obj = view;
		}
		((HistroyView) obj).refresh();
		MobclickAgent.onEvent(this, "v_history");
		DiscuzStats.add(null, "v_history");
		main_container.removeAllViews();
		main_container.addView(((View) (obj)));
	}

	/**
	 * 设置推荐列表视图
	 */
	private void setRecommendView()
	{
		Object obj = (View) views.get(R.id.radio_button_left);
		if (obj == null)
		{
			obj = new RecommendView(this);
			views.put(R.id.radio_button_left, obj);
		}
		MobclickAgent.onEvent(this, "v_reco");
		DiscuzStats.add(null, "v_reco");
		main_container.removeAllViews();
		main_container.addView(((View) (obj)));
	}

	protected void init()
	{
		super.init();
		List list = SiteInfoDBHelper.getInstance().getAll();
		views = new SparseArray();

		setContentView(R.layout.main_activity);
		main_container = (LinearLayout) findViewById(R.id.main_container);
		radio_btn_holder = findViewById(R.id.radio_button_holder);
		tab_btns_group = (RadioGroup) findViewById(R.id.tab_btns);
		tab_btns_group.setOnCheckedChangeListener(this);
		if (list != null && list.size() > 0)
		{
			tab_btn_line = findViewById(R.id.tab_btn_line_right);
			tab_btn_line.setVisibility(0);
			tab_btns_group.check(R.id.radio_button_right);
		} else
		{
			tab_btn_line = findViewById(R.id.tab_btn_line_left);
			tab_btn_line.setVisibility(0);
			tab_btns_group.check(R.id.radio_button_left);
		}
		findViewById(R.id.search_btn).setOnClickListener(
				new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						Intent intent = new Intent();
						intent.setClass(MainActivity.this,
								SiteSearchActivity.class);
						startActivity(intent);
					}
				});
		notice_btn = (ImageButton) findViewById(R.id.notice_btn);
		notice_btn.setOnClickListener(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, noticecenterlist_new.class);
				startActivity(intent);
				notice_btn.setImageResource(R.drawable.notice_null);
			}
		});
	}

	protected void onBackKeyEvent()
	{
		isExitApp = true;
		super.onBackKeyEvent();
	}

	public void onCheckedChanged(RadioGroup radiogroup, int i)
	{
		if (i == R.id.radio_button_left)
		{
			tabBtnRightToLeftAnimation();// 下方蓝色横线的动画
			setRecommendView();
		} else if (i == R.id.radio_button_right)
		{
			tabBtnLeftToRightAnimation();// 下方蓝色横线的动画
			setHistroyView();
			return;
		}
	}

	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);

		// 如果是通过点击通知栏进入当前的Activity
		if (getIntent().getBooleanExtra("COME_FROM_NOTICE", false))
		{
			Intent intent = new Intent();
			intent.setClass(this, noticecenterlist_new.class);
			startActivity(intent);
			NoticeCenter.isMainActivityRunning = true;
		}
		if (!Core.getInstance()._hasInternet())
			ShowMessageByHandler(R.string.message_no_internet, 3); // 您处于离线状态
		if (!Tools._isSdcardMounted().booleanValue())
			ShowMessageByHandler(R.string.message_sdcard_remove, 3);// 您的SD卡已被卸载，无法进行正常的读取工作
		init();
		new UpdateApp(this,
				new net.discuz.source.UpdateApp.UpdateAppInterface()
				{

					public void finishSucess()
					{}

					public void firstOpen()
					{}

					public void neendCheckUpdate()
					{
						runOnUiThread(new Runnable()
						{

							public void run()
							{
								Core._isUpdate(MainActivity.this);
							}
						});
					}
				});
	}

	protected void onResume()
	{
		notice_btn.setImageResource(R.drawable.notice_null);
		String s = GlobalDBHelper.getInstance().getValue("noticenumber");
		if (s != null && !"0".equals(s)
				&& NoticeListDBHelper.getInstance().selectUnClick() != null)
			notice_btn.setImageResource(R.drawable.notice_new); // 提示有信息
		super.onResume();
	}

	public void tabBtnLeftToRightAnimation()
	{
		TranslateAnimation translateanimation = new TranslateAnimation(0.0F,
				radio_btn_holder.getRight(), 0.0F, 0.0F);
		translateanimation
				.setAnimationListener(new android.view.animation.Animation.AnimationListener()
				{

					public void onAnimationEnd(Animation animation)
					{
						tab_btn_line.setVisibility(8);
						tab_btn_line = findViewById(R.id.tab_btn_line_right);
						tab_btn_line.setVisibility(0);
					}

					public void onAnimationRepeat(Animation animation)
					{}

					public void onAnimationStart(Animation animation)
					{}
				});
		translateanimation.setInterpolator(new DecelerateInterpolator());
		translateanimation.setDuration(300L);
		tab_btn_line.startAnimation(translateanimation);
	}

	public void tabBtnRightToLeftAnimation()
	{
		TranslateAnimation translateanimation = new TranslateAnimation(0.0F,
				-radio_btn_holder.getWidth() - tab_btn_line.getWidth(), 0.0F,
				0.0F);
		translateanimation
				.setAnimationListener(new android.view.animation.Animation.AnimationListener()
				{

					public void onAnimationEnd(Animation animation)
					{
						tab_btn_line.setVisibility(8);
						tab_btn_line = findViewById(R.id.tab_btn_line_left);
						tab_btn_line.setVisibility(0);
					}

					public void onAnimationRepeat(Animation animation)
					{}

					public void onAnimationStart(Animation animation)
					{}
				});
		translateanimation.setInterpolator(new DecelerateInterpolator());
		translateanimation.setDuration(300L);
		tab_btn_line.startAnimation(translateanimation);
	}

	private LinearLayout main_container;
	private ImageButton notice_btn;
	private View radio_btn_holder;
	private View tab_btn_line;
	private RadioGroup tab_btns_group;
	private SparseArray views;
}
// 2131296256