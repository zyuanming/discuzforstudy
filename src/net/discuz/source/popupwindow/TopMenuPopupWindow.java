package net.discuz.source.popupwindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.tools.DiscuzStats;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class TopMenuPopupWindow
{
	public static interface OnSelectAction
	{

		public abstract void itemSelected(int i, String s);
	}

	public class TopMenuAdapter extends BaseAdapter
	{

		public int getCount()
		{
			if (menuKeys == null)
				return 0;
			else
				return menuKeys.size();
		}

		public String getItem(int i)
		{
			if (menuKeys == null)
				return null;
			else
				return (String) menuTitles.get(i);
		}

		public long getItemId(int i)
		{
			return (long) i;
		}

		public View getView(int i, View view1, ViewGroup viewgroup)
		{
			TextView textview;
			if (view1 == null)
			{
				view1 = LayoutInflater.from(mContext).inflate(
						R.layout.select_fuction, null);
				TextView textview1 = (TextView) view1
						.findViewById(R.id.select_name);
				view1.setTag(textview1);
				textview = textview1;
			} else
			{
				textview = (TextView) view1.getTag();
			}
			if (currentSelected == i)
				textview.setTextColor(0xff1c99cc);
			else
				textview.setTextColor(0xff545454);
			textview.setText((CharSequence) menuTitles.get(i));
			return view1;
		}

		public TopMenuAdapter()
		{
			super();
		}
	}

	public TopMenuPopupWindow(Context context, View view1, View view2,
			TextView textview)
	{
		mContext = null;
		popupWindow = null;
		onSelectAction = null;
		location = new int[2];
		parentView = null;
		view = null;
		listView = null;
		titleView = null;
		currentSelected = 0;
		onItemClickListener = new android.widget.AdapterView.OnItemClickListener()
		{

			public void onItemClick(AdapterView adapterview, View view3, int i,
					long l)
			{
				currentSelected = i;
				if (onSelectAction != null)
					onSelectAction.itemSelected(i, (String) menuKeys.get(i));
				if (titleView != null)
					titleView.setText((CharSequence) menuTitles.get(i));
				dimiss();
			}
		};
		mContext = context;
		parentView = view1;
		titleView = textview;
		view2.getLocationOnScreen(location);
		width = (int) (130F * DiscuzApp.getInstance().density);
		baseHeight = (int) (44F * DiscuzApp.getInstance().density);
		showAtX = (view2.getWidth() - width) / 2;
		showAtY = view2.getBottom()
				+ (int) (26F * DiscuzApp.getInstance().density);
		initWidget();
		initListener();
	}

	private void initListener()
	{
		listView.setOnItemClickListener(onItemClickListener);
	}

	private void initWidget()
	{
		view = LayoutInflater.from(mContext).inflate(R.layout.pull_down_menu,
				null);
		listView = (ListView) view.findViewById(R.id.select_fuction_list);
		listView.setAdapter(new TopMenuAdapter());
	}

	public void dimiss()
	{
		if (popupWindow != null && popupWindow.isShowing())
			popupWindow.dismiss();
	}

	public void setCurrentSelected(int i)
	{
		currentSelected = i;
	}

	public void setItemChecked(String s)
	{
		int i = menuKeys.indexOf(s);
		if (i > -1)
		{
			onSelectAction.itemSelected(i, s);
			currentSelected = i;
		}
	}

	public void setMenus(ArrayList arraylist, ArrayList arraylist1)
	{
		menuKeys = arraylist;
		menuTitles = arraylist1;
		if (listView != null)
			listView.invalidate();
	}

	public void setMenus(HashMap hashmap)
	{
		Set set = hashmap.entrySet();
		menuKeys = new ArrayList();
		menuTitles = new ArrayList();
		java.util.Map.Entry entry;
		for (Iterator iterator = set.iterator(); iterator.hasNext(); menuTitles
				.add(entry.getValue()))
		{
			entry = (java.util.Map.Entry) iterator.next();
			menuKeys.add(entry.getKey());
		}

		if (listView != null)
			listView.invalidate();
	}

	public void setOnDissmissListener(
			android.widget.PopupWindow.OnDismissListener ondismisslistener)
	{
		onDissmissListener = ondismisslistener;
	}

	public void setOnSelectAction(OnSelectAction onselectaction)
	{
		onSelectAction = onselectaction;
	}

	public void showPopupWindow()
	{
		int i = baseHeight * menuKeys.size()
				+ (int) (5F * DiscuzApp.getInstance().density);
		popupWindow = new PopupWindow(view, width, i, true);
		popupWindow.setOnDismissListener(onDissmissListener);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		if (!popupWindow.isShowing())
		{
			MobclickAgent.onEvent(mContext, "c_pulldown");
			DiscuzStats.add(null, "c_pulldown");
			popupWindow.showAtLocation(parentView, 0, showAtX, showAtY);
		}
	}

	private int baseHeight;
	private int currentSelected;
	private ListView listView;
	private int location[];
	private Context mContext;
	private ArrayList menuKeys;
	private ArrayList menuTitles;
	private android.widget.PopupWindow.OnDismissListener onDissmissListener;
	private android.widget.AdapterView.OnItemClickListener onItemClickListener;
	private OnSelectAction onSelectAction;
	private View parentView;
	private PopupWindow popupWindow;
	private int showAtX;
	private int showAtY;
	private TextView titleView;
	private View view;
	private int width;

}
// 2131296256