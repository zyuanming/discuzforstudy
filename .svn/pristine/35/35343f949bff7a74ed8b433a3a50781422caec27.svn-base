package net.discuz.view;

import net.discuz.R;
import net.discuz.model.SiteInfo;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SiteItemView extends LinearLayout
{

	public SiteItemView(Context context)
	{
		super(context);
		mContext = context;
		init();
	}

	public void init()
	{
		View view = LayoutInflater.from(mContext).inflate(R.layout.site_item,
				null);
		addView(view);
		android.view.ViewGroup.LayoutParams layoutparams = view
				.getLayoutParams();
		layoutparams.width = -1;
		layoutparams.height = -1;
		view.setLayoutParams(layoutparams);
		mSiteIcon = (ImageView) view.findViewById(R.id.site_icon);
		mSiteName = (TextView) view.findViewById(R.id.site_name);
	}

	public void refresh()
	{
		android.graphics.Bitmap bitmap = BitmapFactory.decodeFile(mSiteInfo
				.getIconFromSD());
		if (bitmap != null)
			mSiteIcon.setImageBitmap(bitmap);
		mSiteName.setText(mSiteInfo.getSiteName());
	}

	public void setOnClickListener(
			android.view.View.OnClickListener onclicklistener)
	{
		findViewById(R.id.click_area).setOnClickListener(onclicklistener);
	}

	public void setSiteInfo(SiteInfo siteinfo)
	{
		mSiteInfo = siteinfo;
		refresh();
	}

	private Context mContext;
	private ImageView mSiteIcon;
	private SiteInfo mSiteInfo;
	private TextView mSiteName;
}
// 2131296256