package net.discuz.source;

import net.discuz.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowMessage
{

	public ShowMessage(Context context1)
	{
		TOAST_ICON_SUCCESS = 1;
		TOAST_ICON_INFO = 2;
		TOAST_ICON_ERROR = 3;
		TOAST_TIMER = 2;
		context = context1;
	}

	public static ShowMessage getInstance(Context context1)
	{
		if (instance == null)
			instance = new ShowMessage(context1);
		return instance;
	}

	private void show()
	{
		Toast toast = new Toast(context);
		View view = LayoutInflater.from(context).inflate(R.layout.toast, null);
		TextView textview = (TextView) view.findViewById(R.id.toast_text);
		ImageView imageview = (ImageView) view.findViewById(R.id.toast_icon);
		if (icon == TOAST_ICON_ERROR)
			imageview.setImageResource(R.drawable.icon_toast_error);
		else if (icon == TOAST_ICON_INFO)
			imageview.setImageResource(R.drawable.icon_toast_info);
		else
			imageview.setImageResource(R.drawable.icon_toast_success);
		textview.setText(messageStr);
		toast.setView(view);
		if (TOAST_TIMER == 1)
			toast.setDuration(1);
		else
			toast.setDuration(0);
		toast.show();
	}

	public void _showToast(int i, int j)
	{
		messageStr = context.getResources().getString(i);
		icon = j;
		show();
	}

	public void _showToast(int i, int j, int k)
	{
		messageStr = context.getResources().getString(i);
		icon = j;
		int l;
		if (k == 0)
			l = TOAST_TIMER;
		else
			l = 2;
		TOAST_TIMER = l;
		show();
	}

	public void _showToast(String s, int i)
	{
		messageStr = s;
		icon = i;
		show();
	}

	public void _showToast(String s, int i, int j)
	{
		messageStr = s;
		icon = i;
		int k;
		if (j == 0)
			k = TOAST_TIMER;
		else
			k = 2;
		TOAST_TIMER = k;
		show();
	}

	public void jsToast(String s, int i)
	{
		messageStr = s;
		icon = i;
		show();
	}

	public void jsToast(String s, int i, int j)
	{
		messageStr = s;
		icon = i;
		int k;
		if (j == 0)
			k = TOAST_TIMER;
		else
			k = 2;
		TOAST_TIMER = k;
		show();
	}

	private static ShowMessage instance = null;
	public int TOAST_ICON_ERROR;
	public int TOAST_ICON_INFO;
	public int TOAST_ICON_SUCCESS;
	public int TOAST_TIMER;
	private Context context;
	private int icon;
	private String messageStr;

}
// 2131296256