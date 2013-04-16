package net.discuz.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.discuz.R;
import net.discuz.model.SiteInfo;
import net.discuz.tools.Tools;
import android.app.Activity;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchListAdapter extends BaseAdapter
{
	class ViewHolder
	{

		TextView siteItemName;
		TextView siteItemUrl;

		ViewHolder()
		{
			super();
			siteItemName = null;
			siteItemUrl = null;
		}
	}

	public SearchListAdapter(Activity activity1, List list, Boolean boolean1)
	{
		activity = null;
		iconlist = new HashMap();
		activity = activity1;
		if (boolean1.booleanValue())
		{
			siteInfo_ArrayList.addAll(list);
			return;
		} else
		{
			siteInfo_ArrayList = list;
			return;
		}
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
		siteInfo_ArrayList = null;
		iconlist = null;
	}

	public int getCount()
	{
		if (siteInfo_ArrayList == null)
			return 0;
		else
			return siteInfo_ArrayList.size();
	}

	public Object getItem(int i)
	{
		return siteInfo_ArrayList.get(i);
	}

	public long getItemId(int i)
	{
		return (long) i;
	}

	public View getView(int i, View view, ViewGroup viewgroup)
	{
		ViewHolder viewholder = new ViewHolder();
		LinearLayout linearlayout = (LinearLayout) ((LayoutInflater) activity
				.getSystemService("layout_inflater")).inflate(
				R.layout.site_searchlist, null);
		linearlayout.setBackgroundColor(Tools._getHeaderBgColor());
		viewholder.siteItemName = (TextView) linearlayout
				.findViewById(R.id.searchsiteitem_name);
		viewholder.siteItemUrl = (TextView) linearlayout
				.findViewById(R.id.searchsiteitem_url);
		linearlayout.setTag(viewholder);
		SiteInfo siteinfo = (SiteInfo) siteInfo_ArrayList.get(i);
		viewholder.siteItemName.setText(Html.fromHtml(siteinfo.getSiteName()));
		viewholder.siteItemUrl.setText(Html.fromHtml(siteinfo.getSiteUrl()));
		return linearlayout;
	}

	public void remove(int i)
	{
		siteInfo_ArrayList.remove(i);
		notifyDataSetChanged();
	}

	public void setSiteListInfo_ArrayList(List list)
	{
		siteInfo_ArrayList = list;
		notifyDataSetChanged();
	}

	public static List siteInfo_ArrayList = new ArrayList();
	private Activity activity;
	private HashMap iconlist;

}
// 2131296256