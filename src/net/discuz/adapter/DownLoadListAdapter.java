package net.discuz.adapter;

import java.util.List;

import net.discuz.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DownLoadListAdapter extends BaseAdapter
{
	static class viewHolder
	{

		TextView fileNmae;
		ImageView typeImage;

		viewHolder()
		{}
	}

	public DownLoadListAdapter(Context context1, List list1)
	{
		list = null;
		holder = null;
		context = context1;
		list = list1;
		drawableAPK = context.getResources().getDrawable(
				R.drawable.icon_attachment);
		drawableImage = context.getResources().getDrawable(
				R.drawable.icon_photo);
		drawableText = context.getResources().getDrawable(R.drawable.icon_txt);
		drawableCommont = context.getResources().getDrawable(
				R.drawable.icon_attachment);
	}

	public void deleteItem(int i)
	{
		list.remove(i);
		notifyDataSetChanged();
	}

	public int getCount()
	{
		if (list == null)
			return 0;
		else
			return list.size();
	}

	public String getFileName(int i)
	{
		return ((String) list.get(i)).toString();
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
		int j;
		String s;
		if (view == null)
		{
			view = LayoutInflater.from(context).inflate(
					R.layout.down_load_manager_item, null);
			holder = new viewHolder();
			holder.typeImage = (ImageView) view
					.findViewById(R.id.downLoadItemImage);
			holder.fileNmae = (TextView) view
					.findViewById(R.id.downLoadItemNmae);
			view.setTag(holder);
		} else
		{
			holder = (viewHolder) view.getTag();
		}
		j = ((String) list.get(i)).length();
		s = ((String) list.get(i)).substring(j - 3, j);
		if (s.equals("apk"))
			holder.typeImage.setImageDrawable(drawableAPK);
		else if (s.equals("jpg") || s == "png")
			holder.typeImage.setImageDrawable(drawableImage);
		else if (s.equals("txt"))
			holder.typeImage.setImageDrawable(drawableText);
		else
			holder.typeImage.setImageDrawable(drawableCommont);
		holder.fileNmae.setText(((String) list.get(i)).toString());
		return view;
	}

	private Context context;
	private Drawable drawableAPK;
	private Drawable drawableCommont;
	private Drawable drawableImage;
	private Drawable drawableText;
	private viewHolder holder;
	private List list;
}
// 2131296256