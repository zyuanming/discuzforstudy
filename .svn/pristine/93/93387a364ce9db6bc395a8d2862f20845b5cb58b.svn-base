package net.discuz.adapter;

import java.util.ArrayList;
import java.util.List;

import net.discuz.R;
import net.discuz.source.activity.DiscuzBaseActivity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DownLoadEListAdapter extends BaseExpandableListAdapter
{
	static class viewHolder
	{

		TextView fileNmae;
		ImageView typeImage;

		viewHolder()
		{}
	}

	public DownLoadEListAdapter(DiscuzBaseActivity discuzbaseactivity)
	{
		activity = null;
		list = new ArrayList();
		holder = null;
		activity = discuzbaseactivity;
	}

	public DownLoadEListAdapter(DiscuzBaseActivity discuzbaseactivity,
			List list1)
	{
		activity = null;
		list = new ArrayList();
		holder = null;
		activity = discuzbaseactivity;
		list = list1;
	}

	public Object getChild(int i, int j)
	{
		return list.get(j);
	}

	public long getChildId(int i, int j)
	{
		return (long) j;
	}

	public View getChildView(int i, int j, boolean flag, View view,
			ViewGroup viewgroup)
	{
		int k;
		String s;
		if (view == null)
		{
			view = LayoutInflater.from(activity).inflate(
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
		k = ((String) list.get(j)).length();
		s = ((String) list.get(j)).substring(k - 3, k);
		if (s.equals("apk"))
			holder.typeImage.setImageDrawable(drawableAPK);
		else if (s.equals("jpg") || s == "png")
			holder.typeImage.setImageDrawable(drawableImage);
		else if (s.equals("txt"))
			holder.typeImage.setImageDrawable(drawableText);
		else
			holder.typeImage.setImageDrawable(drawableCommont);
		holder.fileNmae.setText(((String) list.get(j)).toString());
		return view;
	}

	public int getChildrenCount(int i)
	{
		return list.size();
	}

	public Object getGroup(int i)
	{
		return listArray[i];
	}

	public int getGroupCount()
	{
		return listArray.length;
	}

	public long getGroupId(int i)
	{
		return (long) i;
	}

	public View getGroupView(int i, boolean flag, View view, ViewGroup viewgroup)
	{
		RelativeLayout relativelayout = (RelativeLayout) activity
				.getLayoutInflater().inflate(
						R.layout.download_elist_group_item, null);
		ImageView _tmp = (ImageView) relativelayout
				.findViewById(R.id.down_group_style_image);
		TextView textview = (TextView) relativelayout
				.findViewById(R.id.down_group_style_name);
		ImageView _tmp1 = (ImageView) relativelayout
				.findViewById(R.id.down_group_select_image);
		textview.setText(listArray[i]);
		return relativelayout;
	}

	public boolean hasStableIds()
	{
		return false;
	}

	public boolean isChildSelectable(int i, int j)
	{
		return true;
	}

	public void setList(List list1)
	{
		list = list1;
	}

	private DiscuzBaseActivity activity;
	private Drawable drawableAPK;
	private Drawable drawableCommont;
	private Drawable drawableImage;
	private Drawable drawableText;
	private viewHolder holder;
	private List list;
	private String listArray[] = { "\u6B63\u5728\u4E0B\u8F7D",
			"\u5DF2\u4E0B\u8F7D" };
}
// 2131296256