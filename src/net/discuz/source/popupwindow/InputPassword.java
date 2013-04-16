package net.discuz.source.popupwindow;

import net.discuz.R;
import net.discuz.source.InterFace.InterfaceDisplayInputSub;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class InputPassword
{

	public InputPassword(Activity activity1)
	{
		activity = null;
		parentView = null;
		inputEditText = null;
		subButton = null;
		cancelButton = null;
		exitImageView = null;
		popupWindow = null;
		intrefaceInputSub = null;
		isInputMoth = false;
		activity = activity1;
		_init();
	}

	public void _dismiss()
	{
		if (popupWindow.isShowing())
			popupWindow.dismiss();
	}

	public void _init()
	{
		parentView = (LinearLayout) activity.getLayoutInflater().inflate(
				R.layout.forum_forumdisplay_input_password, null);
		inputEditText = (EditText) parentView.findViewById(R.id.input_et);
		subButton = (Button) parentView.findViewById(R.id.sub_btn);
		cancelButton = (Button) parentView.findViewById(R.id.cancel_btn);
		exitImageView = (ImageView) parentView.findViewById(R.id.exit);
		popupWindow = new PopupWindow(parentView, -1, -1, true);
		popupWindow.setAnimationStyle(R.style.LoadingPopupAnimation);
		popupWindow.showAtLocation(
				activity.findViewById(R.id.DiscuzActivityBox), 17, 0, 0);
		_listener();
	}

	public boolean _isShowing()
	{
		return popupWindow.isShowing();
	}

	public void _listener()
	{
		InputMethodManager _tmp = (InputMethodManager) activity
				.getSystemService("input_method");
		subButton.setOnClickListener(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				String s = inputEditText.getText().toString().trim();
				if (intrefaceInputSub != null)
					intrefaceInputSub.interfaceinputSub(s);
				if (popupWindow.isShowing())
					popupWindow.dismiss();
			}
		});
		inputEditText.setOnKeyListener(new android.view.View.OnKeyListener()
		{

			public boolean onKey(View view, int i, KeyEvent keyevent)
			{
				label0:
				{
					if (i == 4)
					{
						if (!isInputMoth)
							break label0;
						if (popupWindow.isShowing())
						{
							popupWindow.dismiss();
							activity.finish();
						}
						isInputMoth = false;
					}
					return false;
				}
				isInputMoth = true;
				return false;
			}
		});
		cancelButton.setOnClickListener(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				if (popupWindow.isShowing())
				{
					popupWindow.dismiss();
					activity.finish();
				}
			}
		});
		exitImageView
				.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						if (popupWindow.isShowing())
						{
							popupWindow.dismiss();
							activity.finish();
						}
					}
				});
	}

	public void setInterface(InterfaceDisplayInputSub interfacedisplayinputsub)
	{
		intrefaceInputSub = interfacedisplayinputsub;
	}

	private Activity activity;
	private Button cancelButton;
	private ImageView exitImageView;
	private EditText inputEditText;
	private InterfaceDisplayInputSub intrefaceInputSub;
	private boolean isInputMoth;
	private LinearLayout parentView;
	private PopupWindow popupWindow;
	private Button subButton;

}
// 2131296256