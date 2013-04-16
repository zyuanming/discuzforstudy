package net.discuz.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.tools.Core;
import net.discuz.tools.Tools;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 热门板块，帖子适配器
 */
public class ForumIndexRecommendListAdapter extends BaseExpandableListAdapter
{
	public class ThreadViewHolder
	{

		TextView forumname;
		ImageView isread_image;
		View recommend_forum;
		View recommend_thread;
		TextView threadlastpost;
		TextView threadlastposter;
		ImageView threadlist_have_image;
		TextView threadreplies;
		TextView threadsubject;
		TextView threadviews;

		public ThreadViewHolder()
		{
			super();
			recommend_thread = null;
			isread_image = null;
			threadsubject = null;
			threadreplies = null;
			threadviews = null;
			threadlastposter = null;
			threadlastpost = null;
			threadlist_have_image = null;
			recommend_forum = null;
			forumname = null;
		}
	}

	public ForumIndexRecommendListAdapter(Context context1,
			ArrayList arraylist, ArrayList arraylist1)
	{
		catlist = new ArrayList();
		context = context1;
		catlist.add("常去的版块");
		catlist.add("热帖");
		recommendForumList = arraylist;
		recommendThreadList = arraylist1;
		defaultSelector = Core.getInstance().createSelector();
		readSelector = Core.getInstance().createReadSelector();
		isReadDrawable = context.getResources().getDrawable(
				R.drawable.listview_icon_isread_selector);
	}

	public String _getfid(int i)
	{
		return (String) ((HashMap) recommendForumList.get(i)).get("fid");
	}

	public HashMap getChild(int i, int j)
	{
		int k = getGroupIndex(i);
		HashMap hashmap;
		if (k == 0)
		{
			hashmap = (HashMap) recommendForumList.get(j);
		} else
		{
			hashmap = null;
			if (k == 1)
				return (HashMap) recommendThreadList.get(j);
		}
		return hashmap;
	}

	public long getChildId(int i, int j)
	{
		return (long) j;
	}

	public View getChildView(int i, int j, boolean flag, View view,
			ViewGroup viewgroup)
	{
		ThreadViewHolder threadviewholder;
		int k;
		if (view == null)
		{
			view = LayoutInflater.from(context).inflate(
					R.layout.recommend_forum_item, null);
			ThreadViewHolder threadviewholder1 = new ThreadViewHolder();
			threadviewholder1.recommend_forum = view
					.findViewById(R.id.recommend_forum);
			threadviewholder1.forumname = (TextView) view
					.findViewById(R.id.siteview_forumindex_forumname);
			threadviewholder1.recommend_thread = view
					.findViewById(R.id.recommend_thread);
			threadviewholder1.isread_image = (ImageView) view
					.findViewById(R.id.tab_index_mythread_list_isread);
			threadviewholder1.threadsubject = (TextView) view
					.findViewById(R.id.tab_index_mythread_list_subject);
			threadviewholder1.threadreplies = (TextView) view
					.findViewById(R.id.tab_index_mythread_list_replies);
			threadviewholder1.threadviews = (TextView) view
					.findViewById(R.id.tab_index_mythread_list_views);
			threadviewholder1.threadlastposter = (TextView) view
					.findViewById(R.id.tab_index_mythread_list_lastposter);
			threadviewholder1.threadlastpost = (TextView) view
					.findViewById(R.id.tab_index_mythread_list_lastpost);
			threadviewholder1.threadlist_have_image = (ImageView) view
					.findViewById(R.id.threadlist_have_image);
			view.setTag(threadviewholder1);
			threadviewholder = threadviewholder1;
		} else
		{
			threadviewholder = (ThreadViewHolder) view.getTag();
		}
		k = getGroupIndex(i);
		if (k == 0)
		{
			HashMap hashmap1 = (HashMap) recommendForumList.get(j);
			threadviewholder.forumname.setText((CharSequence) hashmap1
					.get("name"));
			threadviewholder.recommend_forum.setVisibility(0);
			threadviewholder.recommend_thread.setVisibility(8);
			threadviewholder.threadlist_have_image.setVisibility(8);
		} else if (k == 1)
		{
			HashMap hashmap = (HashMap) recommendThreadList.get(j);
			if (hashmap != null)
			{
				if ("2".equals(hashmap.get("attachment")))
					threadviewholder.threadlist_have_image.setVisibility(0);
				else
					threadviewholder.threadlist_have_image.setVisibility(8);
				threadviewholder.threadsubject
						.setText(Tools.htmlSpecialCharDecode((String) hashmap
								.get("subject")));
				threadviewholder.threadlastposter
						.setText((CharSequence) hashmap.get("lastposter"));
				threadviewholder.threadlastpost.setText((CharSequence) hashmap
						.get("lastpost"));
				if (DiscuzApp.getInstance().isReadThread(
						(String) hashmap.get("tid")))
					threadviewholder.threadsubject.setTextColor(0xff8e8e8e);
				else
					threadviewholder.threadsubject.setTextColor(0xff545454);
				threadviewholder.threadreplies.setText((CharSequence) hashmap
						.get("replies"));
				threadviewholder.recommend_forum.setVisibility(8);
				threadviewholder.recommend_thread.setVisibility(0);
				return view;
			}
		}
		return view;
	}

	public int getChildrenCount(int i)
	{
		int j = getGroupIndex(i);
		int k;
		if (j == 0)
		{
			k = recommendForumList.size();
		} else
		{
			k = 0;
			if (j == 1)
				return recommendThreadList.size();
		}
		return k;
	}

	public String getGroup(int i)
	{
		String s;
		if (i == 0 && recommendForumList != null)
		{
			s = (String) catlist.get(0);
		} else
		{
			if (i == 0 && recommendForumList == null
					&& recommendThreadList != null
					&& recommendThreadList.size() > 0)
				return (String) catlist.get(1);
			s = null;
			if (i == 1)
			{
				ArrayList arraylist = recommendThreadList;
				s = null;
				if (arraylist != null)
				{
					int j = recommendThreadList.size();
					s = null;
					if (j > 0)
						return (String) catlist.get(1);
				}
			}
		}
		return s;
	}

	public int getGroupCount()
	{
		if (recommendForumList == null && recommendThreadList == null)
			return 0;
		return recommendForumList != null && recommendThreadList != null
				&& recommendThreadList.size() != 0 ? 2 : 1;
	}

	public long getGroupId(int i)
	{
		return (long) i;
	}

	public int getGroupIndex(int i)
	{
		return catlist.indexOf(getGroup(i));
	}

	public View getGroupView(int i, boolean flag, View view, ViewGroup viewgroup)
	{
		TextView textview;
		if (view == null)
		{
			view = LayoutInflater.from(context).inflate(
					R.layout.siteview_forumindex_category, null);
			textview = (TextView) view
					.findViewById(R.id.siteview_forumindex_categoryname);
			view.setTag(textview);
		} else
		{
			textview = (TextView) view.getTag();
		}
		textview.setText(getGroup(i));
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

	public void setList(ArrayList arraylist, ArrayList arraylist1)
	{
		recommendForumList = arraylist;
		recommendThreadList = arraylist1;
		notifyDataSetChanged();
	}

	private ArrayList catlist;
	private Context context;
	private ColorStateList defaultSelector;
	private Drawable isReadDrawable;
	private ColorStateList readSelector;
	private ArrayList recommendForumList;
	private ArrayList recommendThreadList;
}
// 2131296256