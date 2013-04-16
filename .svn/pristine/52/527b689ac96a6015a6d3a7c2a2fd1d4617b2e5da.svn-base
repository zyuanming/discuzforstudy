package net.discuz.source;

import net.discuz.R;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DialogPopup
{

	public DialogPopup(Context context)
	{
		activity = context;
	}

	public void _build(int i, int j, int k, int l, int i1)
	{
		if (i == 0)
			popup_style = R.style.popup_style;
		else
			popup_style = i;
		if (j == 0)
			popup_view_id = R.layout.dialog_popup;
		else
			popup_view_id = j;
		dialog = new Dialog(activity, popup_style);
		_setTitle(k);
		popup_view = LayoutInflater.from(activity).inflate(popup_view_id, null);
		btn_yes = (Button) popup_view.findViewById(R.id.popup_btn_yes);
		btn_no = (Button) popup_view.findViewById(R.id.popup_btn_no);
		if (l != 0)
			btn_yes.setText(l);
		if (i1 != 0)
			btn_no.setText(i1);
	}

	public void _dismiss()
	{
		dialog.dismiss();
	}

	public View _getView()
	{
		return popup_view;
	}

	public void _setMessage(int i)
	{
		((TextView) popup_view.findViewById(R.id.popup_message)).setText(i);
	}

	public void _setMessage(String s)
	{
		((TextView) popup_view.findViewById(R.id.popup_message)).setText(s);
	}

	public void _setTitle(int i)
	{
		if (i == 0)
		{
			dialog.setTitle(R.string.dialog_title);
			return;
		} else
		{
			dialog.setTitle(i);
			return;
		}
	}

	public void _setbtnClick(android.view.View.OnClickListener onclicklistener,
			android.view.View.OnClickListener onclicklistener1)
	{
		if (onclicklistener != null)
			btn_yes.setOnClickListener(onclicklistener);
		else
			btn_yes.setOnClickListener(new android.view.View.OnClickListener()
			{

				public void onClick(View view)
				{
					_dismiss();
				}
			});
		if (onclicklistener1 != null)
		{
			btn_no.setVisibility(0);
			btn_no.setOnClickListener(onclicklistener1);
		}
	}

	public void _show()
	{
		dialog.setContentView(popup_view);
		dialog.show();
	}

	public Context activity;
	public Button btn_no;
	public Button btn_yes;
	public Dialog dialog;
	public int popup_style;
	public String popup_title;
	public View popup_view;
	public int popup_view_id;
}
// 2131296256