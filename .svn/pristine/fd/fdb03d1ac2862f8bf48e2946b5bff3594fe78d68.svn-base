package net.discuz.source.popupwindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.discuz.R;
import net.discuz.activity.siteview.siteview_forumsmsdisplay;
import net.discuz.adapter.SmsShowUrlListAdapter;
import net.discuz.source.InterFace.InterfaceOnTouch;
import net.discuz.source.widget.WebViewCustom;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

public class SmsShowClickUrl
{

	public SmsShowClickUrl(siteview_forumsmsdisplay siteview_forumsmsdisplay1,
			HashMap hashmap)
	{
		activity = null;
		parentView = null;
		popupWindow = null;
		list_tv = null;
		hashMap = null;
		arrayList = null;
		webViewCustom = null;
		interfaceOnTouch = null;
		adapter = null;
		activity = siteview_forumsmsdisplay1;
		parentView = activity.getLayoutInflater().inflate(
				R.layout.sms_show_url, null);
		hashMap = hashmap;
		arrayList = hashMapToArrayList(hashMap);
		_setListView();
		_setPopupWindow();
		_showPopupWindow();
	}

	private void _setClickItemListener()
	{
		list_tv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
		{

			public void onItemClick(AdapterView adapterview, View view, int i,
					long l)
			{
				webViewCustom = new WebViewCustom(activity);
				webViewCustom._init();
				webViewCustom._loadUrl((String) hashMap.get(arrayList.get(i)));
				popupWindow.dismiss();
			}
		});
		list_tv.setOnKeyListener(new android.view.View.OnKeyListener()
		{

			public boolean onKey(View view, int i, KeyEvent keyevent)
			{
				popupWindow.dismiss();
				return false;
			}
		});
	}

	private void _setListView()
	{
		list_tv = (ListView) parentView.findViewById(R.id.mListView);
		adapter = new SmsShowUrlListAdapter(activity, arrayList);
		list_tv.setAdapter(adapter);
		_setClickItemListener();
	}

	private void _setPopupWindow()
	{
		popupWindow = new PopupWindow(parentView, -2, -2, true);
		popupWindow.setAnimationStyle(R.style.LoadingPopupAnimation);
	}

	private ArrayList hashMapToArrayList(HashMap hashmap)
	{
		arrayList = new ArrayList();
		java.util.Map.Entry entry;
		for (Iterator iterator = hashmap.entrySet().iterator(); iterator
				.hasNext(); arrayList.add(entry.getKey()))
			entry = (java.util.Map.Entry) iterator.next();

		return arrayList;
	}

	public void _dismissPopupWindow()
	{
		if (popupWindow.isShowing())
			popupWindow.dismiss();
	}

	public boolean _isPopupWindowShowing()
	{
		return popupWindow.isShowing();
	}

	public void _showPopupWindow()
	{
		if (!popupWindow.isShowing())
		{
			popupWindow.showAtLocation(
					activity.findViewById(R.id.DiscuzActivityBox), 17, 50, 100);
			interfaceOnTouch = new InterfaceOnTouch()
			{

				public void DOnTouch()
				{
					popupWindow.dismiss();
				}
			};
			activity.setOnTouch(interfaceOnTouch);
		}
	}

	private siteview_forumsmsdisplay activity;
	private SmsShowUrlListAdapter adapter;
	private ArrayList arrayList;
	private HashMap hashMap;
	private InterfaceOnTouch interfaceOnTouch;
	private ListView list_tv;
	private View parentView;
	private PopupWindow popupWindow;
	private WebViewCustom webViewCustom;

}
// 2131296256