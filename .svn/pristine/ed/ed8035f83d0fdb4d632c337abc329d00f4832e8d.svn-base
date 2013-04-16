package net.discuz.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SystemSetListAdapter extends BaseAdapter
{
	class ViewHolder
	{

		ImageView imageView;
		TextView information;
		TextView title;

		ViewHolder()
		{
			super();
		}
	}

	public SystemSetListAdapter(Activity activity1, ArrayList arraylist,
			SharedPreferences sharedpreferences)
	{
		list = new ArrayList();
		activity = activity1;
		list = arraylist;
		preferences = sharedpreferences;
	}

	public int getCount()
	{
		if (list == null)
			return 0;
		else
			return list.size();
	}

	public Object getItem(int i)
	{
		return list.get(i);
	}

	public long getItemId(int i)
	{
		return (long) i;
	}

	public View getView(int i, View view, ViewGroup viewgroup)
	{
		if (view == null)
		{
			view = LayoutInflater.from(activity).inflate(
					R.layout.set_list_item, null);
			holder = new ViewHolder();
			holder.title = (TextView) view
					.findViewById(R.id.set_list_item_tile);
			holder.information = (TextView) view
					.findViewById(R.id.set_list_item_information);
			holder.imageView = (ImageView) view
					.findViewById(R.id.set_list_item_Image);
			view.setTag(holder);
		} else
		{
			holder = (ViewHolder) view.getTag();
		}
		holder.information.setText((String) ((HashMap) list.get(i))
				.get("description"));
		holder.imageView.setImageResource(((Integer) ((HashMap) list.get(i))
				.get("image")).intValue());
		return view;
	}

	public void setHistoryList(int i, String s, Object obj)
	{
		((HashMap) list.get(i)).put(s, obj);
		notifyDataSetChanged();
	}

	private Activity activity;
	private ViewHolder holder;
	private ArrayList list;
	private SharedPreferences preferences;
}
// 2131296256