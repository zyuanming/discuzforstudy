package net.discuz.source.popupwindow;

import net.discuz.R;
import net.discuz.source.activity.DiscuzBaseActivity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

public class Postpic
{
	public static interface onPostPic
	{

		public abstract void Error();

		public abstract void Suceess(Integer integer);
	}

	public Postpic(DiscuzBaseActivity discuzbaseactivity)
	{
		pic_window = null;
		postPic = null;
		context = discuzbaseactivity;
	}

	public void Error()
	{}

	public void _dismiss()
	{
		if (pic_window != null && pic_window.isShowing())
			pic_window.dismiss();
	}

	public boolean _isShowing()
	{
		if (pic_window != null)
			return pic_window.isShowing();
		else
			return false;
	}

	public void _show()
	{
		View view = context.getLayoutInflater().inflate(R.layout.postpic_popup,
				null);
		pic_window = new PopupWindow(view, -1, -1, false);
		pic_window.setAnimationStyle(R.style.MenuAnimation);
		pic_window.showAtLocation(context.findViewById(R.id.DiscuzActivityBox),
				80, 0, 0);
		((Button) view.findViewById(R.id.postpic_camera))
				.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View view1)
					{
						postPic.Suceess(Integer.valueOf(1));
						if (pic_window.isShowing())
							pic_window.dismiss();
					}
				});
		((Button) view.findViewById(R.id.postpic_album))
				.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View view1)
					{
						postPic.Suceess(Integer.valueOf(2));
						if (pic_window.isShowing())
							pic_window.dismiss();
					}
				});
		((Button) view.findViewById(R.id.postpic_cancel))
				.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View view1)
					{
						if (pic_window.isShowing())
							pic_window.dismiss();
					}
				});
	}

	public void setOnPostPic(onPostPic onpostpic)
	{
		postPic = onpostpic;
	}

	private DiscuzBaseActivity context;
	public PopupWindow pic_window;
	private onPostPic postPic;

}
// 2131296256