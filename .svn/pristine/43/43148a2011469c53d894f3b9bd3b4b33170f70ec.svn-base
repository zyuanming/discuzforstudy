package net.discuz.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.tools.Core;
import net.discuz.tools.Tools;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ThreadListAdapter extends BaseAdapter
{
	class ViewHolder
	{

		ImageView isread_image;
		TextView threadlastpost;
		TextView threadlastposter;
		ImageView threadlist_have_image;
		TextView threadreplies;
		TextView threadsubject;
		TextView threadviews;

		ViewHolder()
		{
			super();
			isread_image = null;
			threadsubject = null;
			threadreplies = null;
			threadviews = null;
			threadlastposter = null;
			threadlastpost = null;
			threadlist_have_image = null;
		}
	}

	public ThreadListAdapter(DiscuzBaseActivity discuzbaseactivity,
			ArrayList arraylist)
	{
		context = discuzbaseactivity;
		threadlist = arraylist;
		defaultSelector = Core.getInstance().createSelector();
		readSelector = Core.getInstance().createReadSelector();
		isReadDrawable = context.getResources().getDrawable(
				R.drawable.listview_icon_isread_selector);
	}

	private void setViewColorStateList(TextView textview, int i, int j)
	{
		if (textview.isEnabled() || textview.isFocused()
				|| textview.isPressed() || textview.isSelected())
		{
			textview.setTextColor(i);
			return;
		} else
		{
			textview.setTextColor(j);
			return;
		}
	}

	public void _setList(ArrayList arraylist)
	{
		threadlist = arraylist;
		if (arraylist != null)
			notifyDataSetChanged();
	}

	public int getCount()
	{
		if (threadlist == null)
			return 0;
		else
			return threadlist.size();
	}

	public HashMap getItem(int i)
	{
		return (HashMap) threadlist.get(i);
	}

	public long getItemId(int i)
	{
		return (long) i;
	}

	public ArrayList getThreadlist()
	{
		return threadlist;
	}

	public View getView(int i, View view, ViewGroup viewgroup)
	{
		ViewHolder viewholder1;
		HashMap hashmap;
		if (view == null)
		{
			ViewHolder viewholder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(
					R.layout.thread_list_item, null);
			viewholder.isread_image = (ImageView) view
					.findViewById(R.id.tab_index_mythread_list_isread);
			viewholder.threadsubject = (TextView) view
					.findViewById(R.id.tab_index_mythread_list_subject);
			viewholder.threadreplies = (TextView) view
					.findViewById(R.id.tab_index_mythread_list_replies);
			viewholder.threadviews = (TextView) view
					.findViewById(R.id.tab_index_mythread_list_views);
			viewholder.threadlastposter = (TextView) view
					.findViewById(R.id.tab_index_mythread_list_lastposter);
			viewholder.threadlastpost = (TextView) view
					.findViewById(R.id.tab_index_mythread_list_lastpost);
			viewholder.threadlist_have_image = (ImageView) view
					.findViewById(R.id.threadlist_have_image);
			view.setTag(viewholder);
			viewholder1 = viewholder;
		} else
		{
			viewholder1 = (ViewHolder) view.getTag();
		}
		try
		{
			if (this.threadlist.get(i) != null)
			{
				HashMap localHashMap = (HashMap) this.threadlist.get(i);
				if ("2".equals(localHashMap.get("attachment")))
				{
					viewholder1.threadlist_have_image.setVisibility(0);
				} else
				{
					viewholder1.threadlist_have_image.setVisibility(8);
				}
				viewholder1.threadsubject.setText(Tools
						.htmlSpecialCharDecode((String) localHashMap
								.get("subject")));
				viewholder1.threadlastposter
						.setText((CharSequence) localHashMap.get("lastposter"));
				viewholder1.threadlastpost.setText((CharSequence) localHashMap
						.get("lastpost"));
				if (DiscuzApp.getInstance().isReadThread(
						(String) localHashMap.get("tid")))
				{
					viewholder1.threadsubject.setTextColor(-7434610);
					viewholder1.threadreplies
							.setText((CharSequence) localHashMap.get("replies"));
				} else
				{
					viewholder1.threadsubject.setTextColor(-11250604);
				}
			}
			return view;

		} catch (Exception localException)
		{
			localException.printStackTrace();
			return view;
		}
	}

	private DiscuzBaseActivity context;
	private ColorStateList defaultSelector;
	private Drawable isReadDrawable;
	private ColorStateList readSelector;
	private ArrayList threadlist;
}
// 2131296256