package net.discuz.source.popupwindow;

import net.discuz.R;
import net.discuz.source.DEBUG;
import net.discuz.source.activity.DiscuzBaseActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class ViewThreadBodyClick
{

	public ViewThreadBodyClick(DiscuzBaseActivity discuzbaseactivity)
	{
		context = discuzbaseactivity;
		view = (LinearLayout) context.getLayoutInflater().inflate(
				R.layout.viewthread_body_click_popup, null);
	}

	public void addButton(String s,
			android.view.View.OnClickListener onclicklistener)
	{
		Button button = new Button(context);
		button.setText(s);
		button.setTextColor(-1);
		button.setTextSize(18F);
		button.setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.alpha_bg));
		if (onclicklistener != null)
			button.setOnClickListener(onclicklistener);
		view.addView(button);
	}

	public void dismiss()
	{
		popupwindow.dismiss();
	}

	public boolean isShown()
	{
		return popupwindow.isShowing();
	}

	public void showPopupWindow(int i, int j)
	{
		DEBUG.o((new StringBuilder()).append("x:").append(i).append("|")
				.append("y:").append(j).toString());
		popupwindow = new PopupWindow(view, -2, -2, false);
		popupwindow.setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.alpha_bg));
		popupwindow.setOutsideTouchable(true);
		popupwindow.setAnimationStyle(R.style.ImagePopupAnimation);
		popupwindow.showAtLocation(
				context.findViewById(R.id.DiscuzActivityBox), 17, i, j);
	}

	private DiscuzBaseActivity context;
	private PopupWindow popupwindow;
	private LinearLayout view;
}
// 2131296256