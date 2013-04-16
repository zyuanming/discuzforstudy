package net.discuz.adapter;

import java.util.HashMap;

import net.discuz.R;
import net.discuz.source.activity.DiscuzBaseActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ForumNavAdapter extends BaseAdapter
{

	public ForumNavAdapter(DiscuzBaseActivity discuzbaseactivity, String as[],
			HashMap hashmap)
	{
		context = discuzbaseactivity;
		items = as;
		values = hashmap;
	}

	public int getCount()
	{
		return items.length;
	}

	public Object getItem(int i)
	{
		return items[i];
	}

	public long getItemId(int i)
	{
		return (long) i;
	}

	public View getView(int i, View view, ViewGroup viewgroup)
	{
		LinearLayout linearlayout;
		TextView textview;
		if (view == null)
			linearlayout = (LinearLayout) LayoutInflater.from(context).inflate(
					R.layout.post_draft_submit_forumnav_listview_item, null);
		else
			linearlayout = (LinearLayout) view;
		textview = (TextView) linearlayout.findViewById(R.id.forumnav_text);
		if (values != null)
		{
			textview.setText(Html.fromHtml((String) values.get(items[i])));
			return linearlayout;
		} else
		{
			textview.setText(Html.fromHtml(items[i].split("\t")[1]));
			return linearlayout;
		}
	}

	public void setList(String as[], HashMap hashmap)
	{
		items = as;
		values = hashmap;
		notifyDataSetChanged();
	}

	public DiscuzBaseActivity context;
	public String items[];
	public HashMap values;
}
// 2131296256