package net.discuz.source.popupwindow;

import java.util.ArrayList;

import net.discuz.R;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class SelectList
{
	public static interface DeleteData
	{

		public abstract void deletePosition(int i);

		public abstract void selected(int i);
	}

	public static interface GetData
	{

		public abstract void getData(ArrayList arraylist);
	}

	class SelectListAdapter extends BaseAdapter
	{

		private void removeItem(int i)
		{
			list.remove(i);
			notifyDataSetChanged();
		}

		public int getCount()
		{
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

		public View getView(final int i, View view, ViewGroup viewgroup)
		{
			ViewHolder viewholder;
			String s;
			if (view == null)
			{
				viewholder = new ViewHolder();
				view = LayoutInflater.from(activity).inflate(
						R.layout.selectlist_item, null);
				viewholder.textView = (TextView) view
						.findViewById(R.id.selectlist_item_text);
				viewholder.imageView = (ImageView) view
						.findViewById(R.id.selectlist_item_image);
				view.setTag(viewholder);
			} else
			{
				viewholder = (ViewHolder) view.getTag();
			}
			s = (String) list.get(i);
			viewholder.textView.setText(Html.fromHtml(s));
			if (!isCanShowDelete)
				viewholder.imageView.setBackgroundDrawable(null);
			viewholder.imageView
					.setOnClickListener(new android.view.View.OnClickListener()
					{

						public void onClick(View view)
						{
							setDelete(i);
							removeItem(i);
							setGetData(list);
						}
					});
			return view;
		}

		private Activity activity;
		public boolean isCanShowDelete;
		private ArrayList list;

		public SelectListAdapter(Activity activity1, ArrayList arraylist)
		{
			super();
			list = new ArrayList();
			activity = null;
			isCanShowDelete = false;
			activity = activity1;
			list = arraylist;
		}

		class ViewHolder
		{

			ImageView imageView;
			TextView textView;

			ViewHolder()
			{
				super();
			}
		}
	}

	public SelectList(Activity activity1, View view, View view1,
			ArrayList arraylist, ArrayList arraylist1)
	{
		selectPopupWindow = null;
		parentView = null;
		activity = null;
		listView = null;
		selectListAdapter = null;
		deleteData = null;
		getData = null;
		data = null;
		data_value = null;
		selected_item = -1;
		location = new int[2];
		activity = activity1;
		parentView = view;
		view1.getLocationOnScreen(location);
		width = view1.getWidth();
		height = view1.getHeight();
		data = arraylist;
		data_value = arraylist1;
		initPopuWindow();
	}

	private void initPopuWindow()
	{
		View view = activity.getLayoutInflater().inflate(R.layout.selectlist,
				null);
		listView = (ListView) view.findViewById(R.id.selectListView);
		selectListAdapter = new SelectListAdapter(activity, data);
		listView.setAdapter(selectListAdapter);
		selectPopupWindow = new PopupWindow(view, width, -2, true);
		selectPopupWindow.setOutsideTouchable(true);
		selectPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
		{

			public void onItemClick(AdapterView adapterview, View view1, int i,
					long l)
			{
				setSelected(i);
				selectPopupWindow.dismiss();
			}
		});
	}

	private void setDelete(int i)
	{
		if (deleteData != null)
			deleteData.deletePosition(i);
	}

	private void setGetData(ArrayList arraylist)
	{
		if (getData != null)
			getData.getData(arraylist);
	}

	private void setSelected(int i)
	{
		selected_item = i;
		if (deleteData != null)
			deleteData.selected(i);
	}

	public void dismiss()
	{
		if (selectPopupWindow.isShowing())
			selectPopupWindow.dismiss();
	}

	public String getSelectedValue()
	{
		int i = selected_item;
		String s = null;
		if (i != -1)
		{
			if (data_value != null)
				s = (String) data_value.get(selected_item);
		}
		return s;
	}

	public void popupWindowShowing()
	{
		if (!selectPopupWindow.isShowing())
			selectPopupWindow.showAtLocation(parentView, 0, location[0],
					location[1] + height);
	}

	public void setCanDelete(boolean flag)
	{
		selectListAdapter.isCanShowDelete = flag;
	}

	public void setGetData(GetData getdata)
	{
		getData = getdata;
	}

	public void setOnDeleteData(DeleteData deletedata)
	{
		deleteData = deletedata;
	}

	public void setOnSelected(DeleteData deletedata)
	{
		deleteData = deletedata;
	}

	private Activity activity;
	public ArrayList data;
	public ArrayList data_value;
	private DeleteData deleteData;
	private GetData getData;
	private int height;
	private ListView listView;
	private int location[];
	private View parentView;
	private SelectListAdapter selectListAdapter;
	private PopupWindow selectPopupWindow;
	private int selected_item;
	private int width;

}
// 2131296256