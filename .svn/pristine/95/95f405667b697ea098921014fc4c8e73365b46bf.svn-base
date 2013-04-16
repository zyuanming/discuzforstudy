package net.discuz.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.source.activity.DiscuzBaseActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyFavForumListAdapter extends BaseAdapter
{

	public MyFavForumListAdapter(DiscuzBaseActivity discuzbaseactivity,
			ArrayList arraylist)
	{
		context = discuzbaseactivity;
		favforumlist = arraylist;
	}

	public void _setList(ArrayList arraylist)
	{
		favforumlist = arraylist;
		if (favforumlist != null)
			notifyDataSetChanged();
	}

	public int getCount()
	{
		if (favforumlist == null)
			return 0;
		else
			return favforumlist.size();
	}

	public Object getItem(int i)
	{
		return favforumlist.get(i);
	}

	public long getItemId(int i)
	{
		return (long) i;
	}

	public View getView(int i, View view, ViewGroup viewgroup)
	{
		RelativeLayout relativelayout;
		if (view == null)
			relativelayout = (RelativeLayout) ((LayoutInflater) context
					.getSystemService("layout_inflater")).inflate(
					R.layout.tab_index_myfavforum, null);
		else
			relativelayout = (RelativeLayout) view;
		((TextView) relativelayout.findViewById(R.id.tab_index_myfavforum_name))
				.setText((CharSequence) ((HashMap) favforumlist.get(i))
						.get("title"));
		return relativelayout;
	}

	private DiscuzBaseActivity context;
	private ArrayList favforumlist;
}
// 2131296256