package net.discuz.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.source.activity.DiscuzBaseActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ForumIndexFidAdapter extends BaseAdapter
{
	class ViewHolder
	{

		TextView forumname;
		TextView todayposts;

		ViewHolder()
		{
			super();
		}
	}

	public ForumIndexFidAdapter(DiscuzBaseActivity discuzbaseactivity)
	{
		activity = null;
		fidList = new ArrayList();
		activity = discuzbaseactivity;
	}

	public int getCount()
	{
		if (fidList == null)
			return 0;
		else
			return fidList.size();
	}

	public ArrayList getFidList()
	{
		return fidList;
	}

	public Object getItem(int i)
	{
		return fidList.get(i);
	}

	public long getItemId(int i)
	{
		return (long) i;
	}

	public View getView(int i, View view, ViewGroup viewgroup)
	{
		ViewHolder viewholder1;
		HashMap hashmap;
		if (view == null)
		{
			ViewHolder viewholder = new ViewHolder();
			view = LayoutInflater.from(activity).inflate(R.layout.forum_item,
					null);
			viewholder.forumname = (TextView) view
					.findViewById(R.id.forum_name);
			viewholder.todayposts = (TextView) view
					.findViewById(R.id.today_posts_count);
			view.setTag(viewholder);
			viewholder1 = viewholder;
		} else
		{
			viewholder1 = (ViewHolder) view.getTag();
		}
		hashmap = (HashMap) fidList.get(i);
		viewholder1.forumname.setText((CharSequence) hashmap.get("name"));
		viewholder1.todayposts
				.setText((CharSequence) hashmap.get("todayposts"));
		if (DiscuzApp.getInstance().isReadForum((String) hashmap.get("fid")))
		{
			viewholder1.forumname.setTextColor(0xff8e8e8e);
			return view;
		} else
		{
			viewholder1.forumname.setTextColor(0xff545454);
			return view;
		}
	}

	public void setFidList(ArrayList arraylist)
	{
		fidList = arraylist;
		if (arraylist != null)
			notifyDataSetChanged();
	}

	private DiscuzBaseActivity activity;
	private ArrayList fidList;
}
// 2131296256