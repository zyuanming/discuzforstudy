package net.discuz.adapter;

import net.discuz.R;
import net.discuz.activity.siteview.siteview_forumviewthread;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectFuctionAdapter extends BaseAdapter
{

	public SelectFuctionAdapter(
			siteview_forumviewthread siteview_forumviewthread1, String as[])
	{
		array = null;
		activity = null;
		activity = siteview_forumviewthread1;
		array = as;
	}

	public int getCount()
	{
		return array.length;
	}

	public Object getItem(int i)
	{
		return null;
	}

	public long getItemId(int i)
	{
		return 0L;
	}

	public View getView(int i, View view, ViewGroup viewgroup)
	{
		LinearLayout linearlayout = (LinearLayout) activity.getLayoutInflater()
				.inflate(R.layout.select_fuction, null);
		((TextView) linearlayout.findViewById(R.id.select_name))
				.setText(array[i]);
		if (i == 0)
		{
			linearlayout.setBackgroundResource(R.drawable.fuction_selector_top);
			return linearlayout;
		}
		if (i == 1)
		{
			linearlayout
					.setBackgroundResource(R.drawable.fuction_selector_modle);
			return linearlayout;
		} else
		{
			linearlayout
					.setBackgroundResource(R.drawable.fuction_selector_bottom);
			return linearlayout;
		}
	}

	private siteview_forumviewthread activity;
	private String array[];
}
// 2131296256