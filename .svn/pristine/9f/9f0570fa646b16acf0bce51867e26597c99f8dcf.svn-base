package net.discuz.source.popupwindow;

import net.discuz.R;
import net.discuz.source.ShowMessage;
import net.discuz.source.activity.DiscuzBaseActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class MenuPopupWindow
{
	private class MenuClickListener implements
			android.view.View.OnClickListener
	{

		public void onClick(View view)
		{
			switch (view.getId())
			{
			case 2131230834:
			case 2131230835:
			case 2131230836:
			case 2131230837:
			case 2131230838:
			case 2131230839:
			case 2131230840:
			case 2131230841:
			case 2131230842:
			case 2131230843:
			case 2131230844:
			case 2131230846:
			case 2131230848:
			default:

			case 2131230845:
				if (Environment.getExternalStorageState().equals("mounted"))
				{
					downloadPopupWindow = new DownLoadPopupWindow(activity);
					downloadPopupWindow._showPopupWindow();
				} else
				{
					ShowMessage.getInstance(activity)._showToast(
							R.string.message_sdcard_remove, 3);
				}
				break;
			case 2131230847:
				intent.setFlags(0x40000000);
				activity.startActivity(intent);
				break;
			case 2131230849:
				activity.refresh();
				break;
			}
			popupWindow_menu.dismiss();
			return;
		}

		private Intent intent;

		private MenuClickListener()
		{
			super();
			intent = new Intent();
		}

	}

	public MenuPopupWindow(Activity activity1)
	{
		activity = null;
		linear1_1 = null;
		linear1_2 = null;
		linear1_3 = null;
		linear1_4 = null;
		linear2_1 = null;
		linear2_2 = null;
		linear2_3 = null;
		linear2_4 = null;
		downloadPopupWindow = null;
		if (activity1 instanceof DiscuzBaseActivity)
		{
			activity = (DiscuzBaseActivity) activity1;
			_initMenuWeight();
			_initMenuListener();
		}
	}

	private void _initMenuListener()
	{
		linear2_1.setOnClickListener(new MenuClickListener());
		linear2_2.setOnClickListener(new MenuClickListener());
		linear2_3.setOnClickListener(new MenuClickListener());
		linear2_4.setOnClickListener(new MenuClickListener());
	}

	private void _initMenuWeight()
	{
		View view = activity.getLayoutInflater().inflate(R.layout.menu_list,
				null);
		popupWindow_menu = new PopupWindow(view, -1, -2, false);
		popupWindow_menu.setAnimationStyle(R.style.MenuAnimation);
		linear1_1 = (LinearLayout) view.findViewById(R.id.item1_1);
		linear1_2 = (LinearLayout) view.findViewById(R.id.item1_2);
		linear1_3 = (LinearLayout) view.findViewById(R.id.item1_3);
		linear1_4 = (LinearLayout) view.findViewById(R.id.item1_4);
		linear2_1 = (LinearLayout) view.findViewById(R.id.item2_1);
		linear2_2 = (LinearLayout) view.findViewById(R.id.item2_2);
		linear2_3 = (LinearLayout) view.findViewById(R.id.item2_3);
		linear2_4 = (LinearLayout) view.findViewById(R.id.item2_4);
	}

	public void _dismiss()
	{
		if (popupWindow_menu != null && popupWindow_menu.isShowing())
			popupWindow_menu.dismiss();
	}

	public boolean _isShowing()
	{
		return popupWindow_menu != null && popupWindow_menu.isShowing();
	}

	public void _showMenu()
	{
		if (popupWindow_menu != null && !popupWindow_menu.isShowing())
			popupWindow_menu.showAtLocation(
					activity.findViewById(R.id.DiscuzActivityBox), 80, 0, 0);
	}

	private DiscuzBaseActivity activity;
	private DownLoadPopupWindow downloadPopupWindow;
	private LinearLayout linear1_1;
	private LinearLayout linear1_2;
	private LinearLayout linear1_3;
	private LinearLayout linear1_4;
	private LinearLayout linear2_1;
	private LinearLayout linear2_2;
	private LinearLayout linear2_3;
	private LinearLayout linear2_4;
	private PopupWindow popupWindow_menu;

}
// 2131296256