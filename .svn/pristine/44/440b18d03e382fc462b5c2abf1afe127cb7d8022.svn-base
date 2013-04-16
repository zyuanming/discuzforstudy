package net.discuz.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.model.SiteInfo;
import net.discuz.source.activity.DiscuzBaseActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecommendedAdapter extends BaseExpandableListAdapter
{

	public RecommendedAdapter(DiscuzBaseActivity discuzbaseactivity)
	{
		list = null;
		activity = null;
		groupBgMap = new HashMap();
		subList = null;
		activity = discuzbaseactivity;
	}

	public Object getChild(int i, int j)
	{
		return ((ArrayList) subList.get(i)).get(j);
	}

	public long getChildId(int i, int j)
	{
		return (long) j;
	}

	public View getChildView(int i, int j, boolean flag, View view,
			ViewGroup viewgroup)
	{
		LinearLayout linearlayout;
		TextView textview;
		TextView textview1;
		SiteInfo siteinfo;
		if (view == null)
			linearlayout = (LinearLayout) LayoutInflater.from(activity)
					.inflate(R.layout.recommended_child, null);
		else
			linearlayout = (LinearLayout) view;
		textview = (TextView) linearlayout.findViewById(R.id.child_item_name);
		textview1 = (TextView) linearlayout
				.findViewById(R.id.child_item_information);
		linearlayout.findViewById(R.id.line);
		siteinfo = (SiteInfo) ((ArrayList) subList.get(i)).get(j);
		textview.setText(siteinfo.getSiteName());
		textview1.setText(siteinfo.getDescription());
		return linearlayout;
	}

	public int getChildrenCount(int i)
	{
		return ((ArrayList) subList.get(i)).size();
	}

	public Object getGroup(int i)
	{
		return subList.get(i);
	}

	public int getGroupCount()
	{
		return subList.size();
	}

	public long getGroupId(int i)
	{
		return (long) i;
	}

	public View getGroupView(int i, boolean flag, View view, ViewGroup viewgroup)
	{
		RelativeLayout relativelayout;
		ImageView imageview;
		if (view == null)
			relativelayout = (RelativeLayout) LayoutInflater.from(activity)
					.inflate(R.layout.recommended_group, null);
		else
			relativelayout = (RelativeLayout) view;
		imageview = (ImageView) relativelayout
				.findViewById(R.id.group_item_imageView);
		((TextView) relativelayout.findViewById(R.id.group_item_name))
				.setText((CharSequence) list.get(i));
		if (i == 0)
			if (groupBgMap.get(String.valueOf(i)) != null)
			{
				imageview.setImageResource(R.drawable.recommend_up);
				relativelayout
						.setBackgroundResource(R.drawable.recommend_item_open_1);
				return relativelayout;
			} else
			{
				imageview.setImageResource(R.drawable.recommend_down);
				relativelayout
						.setBackgroundResource(R.drawable.recommend_item_open_1);
				return relativelayout;
			}
		if (i == -1 + getGroupCount())
			if (groupBgMap.get(String.valueOf(i)) != null)
			{
				imageview.setImageResource(R.drawable.recommend_up);
				relativelayout
						.setBackgroundResource(R.drawable.recommend_item_open);
				return relativelayout;
			} else
			{
				imageview.setImageResource(R.drawable.recommend_down);
				relativelayout
						.setBackgroundResource(R.drawable.recommend_group_bottom_closed_bg);
				return relativelayout;
			}
		if (groupBgMap.get(String.valueOf(i)) != null)
		{
			imageview.setImageResource(R.drawable.recommend_up);
			relativelayout
					.setBackgroundResource(R.drawable.recommend_item_mode_close);
			return relativelayout;
		} else
		{
			imageview.setImageResource(R.drawable.recommend_down);
			relativelayout
					.setBackgroundResource(R.drawable.recommend_item_mode_close);
			return relativelayout;
		}
	}

	public boolean hasStableIds()
	{
		return false;
	}

	public boolean isChildSelectable(int i, int j)
	{
		return true;
	}

	public void setList(ArrayList arraylist)
	{
		list = arraylist;
	}

	public void setSubList(ArrayList arraylist)
	{
		subList = arraylist;
	}

	private DiscuzBaseActivity activity;
	public HashMap groupBgMap;
	private ArrayList list;
	private ArrayList subList;
}
// 2131296256