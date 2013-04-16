package net.discuz.source.popupwindow;

import net.discuz.R;
import net.discuz.activity.siteview.siteview_forumviewthread;
import net.discuz.adapter.SelectFuctionAdapter;
import net.discuz.app.DiscuzApp;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

public class ViewThreadPopupWindow
{
	public static interface onSelectAction
	{

		public abstract void itemSelected(int i);
	}

	public ViewThreadPopupWindow(
			siteview_forumviewthread siteview_forumviewthread1, View view1,
			View view2)
	{
		activity = null;
		popupWindow = null;
		actionInterface = null;
		location = new int[2];
		parentView = null;
		view = null;
		listView = null;
		adapter = null;
		activity = siteview_forumviewthread1;
		parentView = view1;
		view2.getLocationOnScreen(location);
		width = (int) (100F * DiscuzApp.getInstance().density);
		height = (int) (117.2D * (double) DiscuzApp.getInstance().density);
		showAtX = view2.getWidth() - width
				- (int) (4F * DiscuzApp.getInstance().density);
		showAtY = view2.getBottom()
				+ (int) (26F * DiscuzApp.getInstance().density);
		initWidget();
		initListener();
	}

	private void initListener()
	{
		listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
		{

			public void onItemClick(AdapterView adapterview, View view1, int i,
					long l)
			{
				if (actionInterface != null)
					actionInterface.itemSelected(i);
				dimiss();
			}
		});
	}

	private void initWidget()
	{
		view = activity.getLayoutInflater().inflate(
				R.layout.popup_view_select_fuction, null);
		listView = (ListView) view.findViewById(R.id.select_fuction_list);
		adapter = new SelectFuctionAdapter(activity, array);
		listView.setAdapter(adapter);
		popupWindow = new PopupWindow(view, width, height, true);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	public void dimiss()
	{
		if (popupWindow != null && popupWindow.isShowing())
			popupWindow.dismiss();
	}

	public void setOnSelectAction(onSelectAction onselectaction)
	{
		actionInterface = onselectaction;
	}

	public void showPopupWindow()
	{
		if (popupWindow != null && !popupWindow.isShowing())
			popupWindow.showAtLocation(parentView, 0, showAtX, showAtY);
	}

	private onSelectAction actionInterface;
	private siteview_forumviewthread activity;
	private SelectFuctionAdapter adapter;
	private String array[] = { "\u8DF3\u9875", "\u6536\u85CF\u5E16\u5B50",
			"\u53EA\u770B\u697C\u4E3B" };
	private int height;
	private ListView listView;
	private int location[];
	private View parentView;
	private PopupWindow popupWindow;
	private int showAtX;
	private int showAtY;
	private View view;
	private int width;

}
// 2131296256