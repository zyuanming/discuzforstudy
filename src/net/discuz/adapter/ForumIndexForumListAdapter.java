package net.discuz.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.source.activity.DiscuzBaseActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ForumIndexForumListAdapter extends BaseExpandableListAdapter
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

	public ForumIndexForumListAdapter(DiscuzBaseActivity discuzbaseactivity,
			ArrayList arraylist, HashMap hashmap, HashMap hashmap1,
			HashMap hashmap2)
	{
		activity = discuzbaseactivity;
		catlist = arraylist;
		catvalues = hashmap;
		catforumlist = hashmap1;
		forumvalues = hashmap2;
	}

	public String _getfid(int i, int j)
	{
		return (String) ((ArrayList) catforumlist.get(catlist.get(i))).get(j);
	}

	public void _setList(ArrayList arraylist, HashMap hashmap,
			HashMap hashmap1, HashMap hashmap2)
	{
		catlist = arraylist;
		catvalues = hashmap;
		catforumlist = hashmap1;
		forumvalues = hashmap2;
		notifyDataSetChanged();
	}

	public Object getChild(int i, int j)
	{
		return null;
	}

	public long getChildId(int i, int j)
	{
		return (long) j;
	}

	public View getChildView(int i, int j, boolean flag, View view,
			ViewGroup viewgroup)
	{
		ViewHolder viewholder1;
		String s;
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
		if (catlist.size() > 0 && catforumlist.size() > 0
				&& catlist.get(i) != null)
			s = (String) ((ArrayList) catforumlist.get(catlist.get(i))).get(j);
		else
			s = null;
		if (s != null && forumvalues.size() > 0)
		{
			HashMap hashmap = (HashMap) forumvalues.get(s);
			viewholder1.forumname.setText((CharSequence) hashmap
					.get("forumname"));
			viewholder1.todayposts.setText((CharSequence) hashmap
					.get("todayposts"));
			if (DiscuzApp.getInstance()
					.isReadForum((String) hashmap.get("fid")))
				viewholder1.forumname.setTextColor(0xff8e8e8e);
		}
		viewholder1.forumname.setTextColor(0xff545454);
		return view;
	}

	public int getChildrenCount(int i)
	{
		return ((ArrayList) catforumlist.get(catlist.get(i))).size();
	}

	public Object getGroup(int i)
	{
		return null;
	}

	public int getGroupCount()
	{
		if (catlist == null)
			return 0;
		else
			return catlist.size();
	}

	public long getGroupId(int i)
	{
		return (long) i;
	}

	public View getGroupView(int i, boolean flag, View view, ViewGroup viewgroup)
	{
		if (catlist.size() > 0)
		{
			String s = (String) catlist.get(i);
			TextView textview;
			if (view == null)
			{
				view = LayoutInflater.from(activity).inflate(
						R.layout.siteview_forumindex_category, null); // fenglei
				textview = (TextView) view
						.findViewById(R.id.siteview_forumindex_categoryname);
				view.setTag(textview);
			} else
			{
				textview = (TextView) view.getTag();
			}
			textview.setText((CharSequence) catvalues.get(s));
		}
		return view;
	}

	public boolean hasStableIds()
	{
		return false;
	}

	public boolean isChildSelectable(int i, int j)
	{
		return true;
	}

	private DiscuzBaseActivity activity;
	public HashMap catforumlist;
	public ArrayList catlist; //
	public HashMap catvalues; 
	public HashMap forumvalues;
}
// 2131296256