package net.discuz.adapter;

import java.util.ArrayList;

import net.discuz.R;
import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SmsShowUrlListAdapter extends BaseAdapter
{

	public SmsShowUrlListAdapter(Activity activity1, ArrayList arraylist)
	{
		activity = null;
		arrayList = null;
		tv = null;
		activity = activity1;
		arrayList = arraylist;
	}

	public int getCount()
	{
		return arrayList.size();
	}

	public Object getItem(int i)
	{
		return arrayList.get(i);
	}

	public long getItemId(int i)
	{
		return (long) i;
	}

	public View getView(int i, View view, ViewGroup viewgroup)
	{
		View view1 = LayoutInflater.from(activity).inflate(R.layout.list_item,
				null);
		tv = (TextView) view1.findViewById(R.id.item);
		tv.setText(Html.fromHtml((String) arrayList.get(i)));
		return view1;
	}

	private Activity activity;
	private ArrayList arrayList;
	private TextView tv;
}
// 2131296256