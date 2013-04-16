package net.discuz.adapter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.discuz.R;
import net.discuz.model.SiteInfo;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SitelistAdapter extends BaseAdapter
{

	public SitelistAdapter(Activity activity1, List list)
	{
		siteInfoList = null;
		activity = null;
		iconlist = new HashMap();
		activity = activity1;
		siteInfoList = list;
	}

	public void destroy()
	{
		if (!iconlist.isEmpty())
		{
			Iterator iterator = iconlist.entrySet().iterator();
			do
			{
				if (!iterator.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator
						.next();
				if (entry.getValue() != null)
					((Bitmap) entry.getValue()).recycle();
			} while (true);
		}
	}

	public int getCount()
	{
		if (siteInfoList == null)
			return 0;
		else
			return siteInfoList.size();
	}

	public SiteInfo getItem(int i)
	{
		if (i < siteInfoList.size())
			return (SiteInfo) siteInfoList.get(i);
		else
			return null;
	}

	public long getItemId(int i)
	{
		return (long) i;
	}

	public View getView(int i, View view, ViewGroup viewgroup)
	{
		SiteInfo siteinfo = (SiteInfo) siteInfoList.get(i);
		int j = siteinfo.getFlag();
		if (j == 2)
		{
			View view3 = LayoutInflater.from(activity).inflate(
					R.layout.sitelist_category, null);
			((TextView) view3.findViewById(R.id.sitelist_categoryname))
					.setText(siteinfo.getSiteName());
			return view3;
		}
		if (j == 1)
		{
			View view2 = LayoutInflater.from(activity).inflate(
					R.layout.site_searchlist, null);
			TextView textview2 = (TextView) view2
					.findViewById(R.id.searchsiteitem_name);
			TextView textview3 = (TextView) view2
					.findViewById(R.id.searchsiteitem_url);
			textview2.setText(siteinfo.getSiteName());
			textview3.setText(siteinfo.getSiteUrl());
			return view2;
		}
		View view1 = LayoutInflater.from(activity).inflate(
				R.layout.sitelist_item, null);
		TextView textview = (TextView) view1
				.findViewById(R.id.sitelist_item_info_name);
		TextView textview1 = (TextView) view1
				.findViewById(R.id.sitelist_item_info_url);
		ImageView imageview = (ImageView) view1
				.findViewById(R.id.sitelist_item_info_siteicon);
		textview.setText(siteinfo.getSiteName());
		textview1.setText(siteinfo.getSiteUrl());
		Bitmap bitmap = (Bitmap) iconlist.get(siteinfo.getIconFromSD());
		if (bitmap == null)
		{
			bitmap = BitmapFactory.decodeFile(siteinfo.getIconFromSD());
			iconlist.put(siteinfo.getIconFromSD(), bitmap);
		}
		imageview.setImageBitmap(bitmap);
		return view1;
	}

	public void remove(int i)
	{
		if (i < siteInfoList.size())
			siteInfoList.remove(i);
		notifyDataSetChanged();
	}

	public void setSiteListInfo_ArrayList(List list)
	{
		siteInfoList = list;
		notifyDataSetChanged();
	}

	public Activity activity;
	public HashMap iconlist;
	public List siteInfoList;
}
// 2131296256