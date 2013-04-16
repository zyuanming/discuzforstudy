package net.discuz.adapter;

import java.util.List;
import java.util.Map;

import net.discuz.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectPopupWindowAdapter extends BaseAdapter
{
	static class viewHolder
	{

		TextView fileNmae;
		ImageView typeImage;

		viewHolder()
		{}
	}

	public SelectPopupWindowAdapter(Activity activity1, List list1)
	{
		activity = activity1;
		list = list1;
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
		viewHolder viewholder;
		if (view == null)
		{
			view = LayoutInflater.from(activity).inflate(
					R.layout.select_item_popupwindow, null);
			viewholder = new viewHolder();
			viewholder.typeImage = (ImageView) view
					.findViewById(R.id.itemImage);
			viewholder.fileNmae = (TextView) view
					.findViewById(R.id.itemTextView);
			view.setTag(viewholder);
		} else
		{
			viewholder = (viewHolder) view.getTag();
		}
		viewholder.fileNmae.setText(((Map) list.get(i)).get("text").toString());
		if (((Map) list.get(i)).get("image").toString().equals("camera"))
		{
			viewholder.typeImage.setBackgroundResource(R.drawable.icon_camera);
			return view;
		} else
		{
			viewholder.typeImage.setBackgroundResource(R.drawable.icon_album);
			return view;
		}
	}

	private Activity activity;
	private List list;
}
// 2131296256