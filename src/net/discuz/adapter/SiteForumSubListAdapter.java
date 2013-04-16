package net.discuz.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.tools.Core;
import net.discuz.tools.Tools;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SiteForumSubListAdapter extends BaseAdapter
{
	class ViewHolder
	{

		TextView forumname;
		ImageView isreadimage;
		TextView posts;
		TextView threads;
		TextView todayposts;

		ViewHolder()
		{
			super();
		}
	}

	public SiteForumSubListAdapter(DiscuzBaseActivity discuzbaseactivity)
	{
		subList = new ArrayList();
		activity = discuzbaseactivity;
	}

	public int getCount()
	{
		if (subList == null)
			return 0;
		else
			return subList.size();
	}

	public Object getItem(int i)
	{
		return subList.get(i);
	}

	public long getItemId(int i)
	{
		return (long) i;
	}

	public ArrayList getSubList()
	{
		return subList;
	}

	public View getView(int i, View view, ViewGroup viewgroup)
	{
		ViewHolder viewholder1;
		LinearLayout linearlayout1;
		HashMap hashmap;
		if (view == null)
		{
			ViewHolder viewholder = new ViewHolder();
			LinearLayout linearlayout = (LinearLayout) LayoutInflater.from(
					activity).inflate(R.layout.listview_item, null);
			linearlayout.setBackgroundColor(Tools._getHeaderBgColor());
			((LinearLayout) linearlayout.findViewById(R.id.listview_item_box))
					.addView((LinearLayout) LayoutInflater.from(activity)
							.inflate(R.layout.siteview_forumindex_forum, null),
							-1, -2);
			viewholder.forumname = (TextView) linearlayout
					.findViewById(R.id.siteview_forumindex_forumname);
			viewholder.todayposts = (TextView) linearlayout
					.findViewById(R.id.siteview_forumindex_todayposts);
			viewholder.threads = (TextView) linearlayout
					.findViewById(R.id.siteview_forumindex_threads);
			viewholder.posts = (TextView) linearlayout
					.findViewById(R.id.siteview_forumindex_posts);
			viewholder.isreadimage = (ImageView) linearlayout
					.findViewById(R.id.siteview_forumindex_isread);
			linearlayout.setTag(viewholder);
			viewholder1 = viewholder;
			linearlayout1 = linearlayout;
		} else
		{
			linearlayout1 = (LinearLayout) view;
			viewholder1 = (ViewHolder) linearlayout1.getTag();
		}
		hashmap = (HashMap) subList.get(i);
		viewholder1.forumname.setText((CharSequence) hashmap.get("name"));
		viewholder1.posts.setText((new StringBuilder()).append("\u8D34\u5B50:")
				.append((String) hashmap.get("posts")).toString());
		viewholder1.threads.setText((new StringBuilder())
				.append("\u4E3B\u9898:")
				.append((String) hashmap.get("threads")).toString());
		if (!"0".equals(hashmap.get("todayposts")))
			viewholder1.todayposts.setText((new StringBuilder()).append("(")
					.append((String) hashmap.get("todayposts")).append(")")
					.toString());
		else
			viewholder1.todayposts.setText("");
		if (DiscuzApp.getInstance().isReadForum((String) hashmap.get("fid")))
		{
			viewholder1.isreadimage.setBackgroundDrawable(activity
					.getResources().getDrawable(
							R.drawable.listview_icon_isread_selector));
			viewholder1.forumname.setTextColor(Core.getInstance()
					.createReadSelector());
			viewholder1.posts.setTextColor(Core.getInstance()
					.createReadSelector());
			viewholder1.threads.setTextColor(Core.getInstance()
					.createReadSelector());
			return linearlayout1;
		} else
		{
			viewholder1.isreadimage.setBackgroundDrawable(activity
					.getResources().getDrawable(
							R.drawable.listview_icon_default_selector));
			viewholder1.forumname.setTextColor(Core.getInstance()
					.createSelector());
			return linearlayout1;
		}
	}

	public void setSubList(ArrayList arraylist)
	{
		subList = arraylist;
		if (arraylist != null)
			notifyDataSetChanged();
	}

	private DiscuzBaseActivity activity;
	private ArrayList subList;
}
// 2131296256