package net.discuz.adapter;

import java.util.List;

import net.discuz.R;
import net.discuz.model.SiteInfo;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SiteGridAdapter extends SitelistAdapter
{
	private class ViewHolder
	{

		ImageView site_icon;
		TextView site_name;

		private ViewHolder()
		{
			super();
		}

	}

	public SiteGridAdapter(Activity activity, List list)
	{
		super(activity, list);
	}

	public View getView(int i, View view, ViewGroup viewgroup)
	{
		SiteInfo siteinfo = (SiteInfo) siteInfoList.get(i);
		ViewHolder viewholder;
		android.graphics.Bitmap bitmap;
		if (view == null)
		{
			view = LayoutInflater.from(activity).inflate(
					R.layout.sitelist_grid_item, null);
			ViewHolder viewholder1 = new ViewHolder();
			viewholder1.site_name = (TextView) view
					.findViewById(R.id.site_name);
			viewholder1.site_icon = (ImageView) view
					.findViewById(R.id.site_icon);
			view.setTag(viewholder1);
			viewholder = viewholder1;
		} else
		{
			viewholder = (ViewHolder) view.getTag();
		}
		viewholder.site_name.setText(siteinfo.getSiteName());
		bitmap = BitmapFactory.decodeFile(siteinfo.getIconFromSD());
		if (bitmap != null)
		{
			viewholder.site_icon.setImageBitmap(bitmap);
			return view;
		} else
		{
			viewholder.site_icon.setImageResource(R.drawable.icon_nosite);
			return view;
		}
	}
}
// 2131296256