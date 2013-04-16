package net.discuz.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.source.activity.DiscuzBaseActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyFavThreadListAdapter extends BaseAdapter
{

	public MyFavThreadListAdapter(DiscuzBaseActivity discuzbaseactivity,
			ArrayList arraylist)
	{
		myFavThreadlist = new ArrayList();
		context = discuzbaseactivity;
		myFavThreadlist = arraylist;
	}

	public void _setList(ArrayList arraylist)
	{
		myFavThreadlist = arraylist;
		notifyDataSetChanged();
	}

	public int getCount()
	{
		if (myFavThreadlist == null)
			return 0;
		else
			return myFavThreadlist.size();
	}

	public HashMap getItem(int i)
	{
		return (HashMap) myFavThreadlist.get(i);
	}

	public long getItemId(int i)
	{
		return (long) i;
	}

	public ArrayList getMyFavThreadlist()
	{
		return myFavThreadlist;
	}

	public View getView(int i, View view, ViewGroup viewgroup)
	{
		LinearLayout linearlayout;
		HashMap hashmap;
		TextView textview;
		TextView textview1;
		if (view == null)
		{
			LayoutInflater layoutinflater = (LayoutInflater) context
					.getSystemService("layout_inflater");
			LinearLayout linearlayout1 = (LinearLayout) layoutinflater.inflate(
					R.layout.listview_favthread_item, null);
			linearlayout1.setBackgroundDrawable(null);
			((LinearLayout) linearlayout1.findViewById(R.id.listview_item_box))
					.addView((LinearLayout) layoutinflater.inflate(
							R.layout.tab_index_myfavthread_list, null), -1, -2);
			linearlayout = linearlayout1;
		} else
		{
			linearlayout = (LinearLayout) view;
		}
		hashmap = (HashMap) myFavThreadlist.get(i);
		textview = (TextView) linearlayout
				.findViewById(R.id.tab_index_myfavthread_list_subject);
		textview1 = (TextView) linearlayout
				.findViewById(R.id.tab_index_myfavthread_list_desciption);
		textview.setText((CharSequence) hashmap.get("title"));
		try
		{
			textview1.setText((CharSequence) hashmap.get("dateline"));
		} catch (Exception exception)
		{
			return linearlayout;
		}
		return linearlayout;
	}

	private DiscuzBaseActivity context;
	private ArrayList myFavThreadlist;
}
// 2131296256