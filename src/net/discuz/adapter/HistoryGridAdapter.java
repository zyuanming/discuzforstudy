package net.discuz.adapter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.discuz.model.SiteInfo;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.view.SiteItemView;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoryGridAdapter extends BaseAdapter
{

	public HistoryGridAdapter(DiscuzBaseActivity discuzbaseactivity, List list1)
	{
		name_tv = null;
		icon_img = null;
		activity = null;
		list = null;
		iconlist = new HashMap();
		activity = discuzbaseactivity;
		list = list1;
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
		if (list.size() >= 8)
			return 8;
		else
			return list.size();
	}

	public SiteInfo getItem(int i)
	{
		return (SiteInfo) list.get(i);
	}

	public long getItemId(int i)
	{
		return (long) i;
	}

	public View getView(int i, View view, ViewGroup viewgroup)
	{
		SiteItemView siteitemview;
		if (view == null)
			siteitemview = new SiteItemView(activity);
		else
			siteitemview = (SiteItemView) view;
		siteitemview.setSiteInfo((SiteInfo) list.get(i));
		return siteitemview;
	}

	public void remove(int i)
	{
		if (i < list.size())
			list.remove(i);
		notifyDataSetChanged();
	}

	private DiscuzBaseActivity activity;
	private ImageView icon_img;
	private HashMap iconlist;
	private List list;
	private TextView name_tv;
}
// 2131296256