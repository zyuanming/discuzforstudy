package net.discuz.view;

import net.discuz.R;
import net.discuz.activity.siteview.MyFavActivity;
import net.discuz.activity.siteview.MyMessageActivity;
import net.discuz.activity.siteview.MyThreadActivity;
import net.discuz.app.DiscuzApp;
import net.discuz.dialog.ProfileDialog;
import net.discuz.model.LoginInfo;
import net.discuz.model.NoticeListItem;
import net.discuz.source.AsyncImageLoader;
import net.discuz.source.InterFace.OnLogout;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.NoticeListDBHelper;
import net.discuz.source.storage.SiteInfoDBHelper;
import net.discuz.tools.Tools;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 我的个人中心
 * 
 * @author lkh
 * 
 */
public class MyCenterView extends LinearLayout
{

	public MyCenterView(DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		onClick = new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				int i = view.getId();
				if (i == R.id.my_fav_btn)
				{
					Intent intent = new Intent();
					intent.setClass(mActivity, MyFavActivity.class);
					intent.putExtra("siteappid", mActivity.siteAppId);
					mActivity.startActivity(intent);
				} else
				{
					if (i == R.id.my_thread_btn)
					{
						Intent intent1 = new Intent();
						intent1.setClass(mActivity, MyThreadActivity.class);
						intent1.putExtra("siteappid", mActivity.siteAppId);
						mActivity.startActivity(intent1);
						return;
					}
					if (i == R.id.my_msg_btn)
					{
						cloudid = SiteInfoDBHelper.getInstance()
								.getByAppId(mActivity.siteAppId).getCloudId();
						NoticeListItem noticelistitem = NoticeListDBHelper
								.getInstance().select(cloudid);
						if (noticelistitem != null)
						{
							noticelistitem.pmclick = Integer.valueOf(1);
							NoticeListDBHelper.getInstance().update(
									noticelistitem);
						}
						red_point.setVisibility(8);
						Intent intent2 = new Intent();
						intent2.setClass(mActivity, MyMessageActivity.class);
						intent2.putExtra("siteappid", mActivity.siteAppId);
						mActivity.startActivity(intent2);
						return;
					}
					if (i == R.id.feedback_btn)
					{
						// UMFeedbackService.setGoBackButtonVisible();
						// UMFeedbackService.openUmengFeedbackSDK(mActivity);
						return;
					}
					if (i == R.id.user_base_profile_layout)
					{
						ProfileDialog profiledialog = new ProfileDialog(
								mActivity, loginUser.getUid(),
								loginUser.getUsername());
						profiledialog.setOnLogout(onLogout);
						profiledialog.show();
						return;
					}
				}
			}
		};
		mActivity = discuzbaseactivity;
		init();
	}

	private void load_myprofile_avatar()
	{
		DiscuzApp discuzapp = DiscuzApp.getInstance();
		AsyncImageLoader.getAsyncImageLoader().loadDrawable(
				mActivity.siteAppId,
				(new StringBuilder())
						.append(discuzapp.getSiteInfo(mActivity.siteAppId)
								.getUCenterUrl())
						.append("/avatar.php?uid=")
						.append(discuzapp.getLoginUser(mActivity.siteAppId)
								.getUid()).append("&size=middle").toString(),
				new net.discuz.source.AsyncImageLoader.ImageCallback()
				{

					public void imageCacheLoaded(Bitmap bitmap, String s)
					{
						Tools.applyRoundCorner(user_avatar, bitmap);
						user_avatar.postInvalidate();
					}

					public void imageError(String s)
					{}

					public void imageLoaded(final Bitmap bitmap, String s)
					{
						if (mActivity != null)
							mActivity.runOnUiThread(new Runnable()
							{

								public void run()
								{
									Tools.applyRoundCorner(user_avatar, bitmap);
									user_avatar.postInvalidate();
								}
							});
					}
				});
	}

	public void init()
	{
		View view = LayoutInflater.from(mActivity).inflate(
				R.layout.my_center_view, null);
		addView(view);
		android.view.ViewGroup.LayoutParams layoutparams = view
				.getLayoutParams();
		layoutparams.width = -1;
		layoutparams.height = -1;
		view.setLayoutParams(layoutparams);
		View view1 = findViewById(R.id.user_base_profile_layout);
		View view2 = findViewById(R.id.my_fav_btn);
		View view3 = findViewById(R.id.my_thread_btn);
		View view4 = findViewById(R.id.my_msg_btn);
		View view5 = findViewById(R.id.feedback_btn);
		view1.setOnClickListener(onClick);
		view2.setOnClickListener(onClick);
		view3.setOnClickListener(onClick);
		view4.setOnClickListener(onClick);
		view5.setOnClickListener(onClick);
		user_avatar = (ImageView) findViewById(R.id.user_avatar);
		username = (TextView) findViewById(R.id.username);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.user_icon);
		Tools.applyRoundCorner(user_avatar, bitmap);
		loginUser = DiscuzApp.getInstance().getLoginUser(mActivity.siteAppId);
		username.setText(loginUser.getUsername());
		load_myprofile_avatar();
		red_point = (ImageView) findViewById(R.id.red_point);
		refresh();
	}

	public void refresh()
	{
		cloudid = SiteInfoDBHelper.getInstance()
				.getByAppId(mActivity.siteAppId).getCloudId();
		NoticeListItem noticelistitem = NoticeListDBHelper.getInstance()
				.select(cloudid);
		if (noticelistitem != null && noticelistitem.pmclick.intValue() == 0)
		{
			red_point.setVisibility(0);
		} else
		{
			red_point.setVisibility(8);
		}
	}

	public void setOnLogout(OnLogout onlogout)
	{
		onLogout = onlogout;
	}

	private String cloudid;
	private LoginInfo loginUser;
	private DiscuzBaseActivity mActivity;
	private android.view.View.OnClickListener onClick;
	private OnLogout onLogout;
	private ImageView red_point;
	private ImageView user_avatar;
	private TextView username;

}
// 2131296256