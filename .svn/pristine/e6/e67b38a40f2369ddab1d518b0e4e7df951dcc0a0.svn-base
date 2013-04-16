package net.discuz.source.QRCodes.camera;

import net.discuz.source.DEBUG;
import android.hardware.Camera;
import android.os.Handler;

final class AutoFocusCallback implements
		android.hardware.Camera.AutoFocusCallback
{

	AutoFocusCallback()
	{}

	public void onAutoFocus(boolean flag, Camera camera)
	{
		if (autoFocusHandler != null)
		{
			android.os.Message message = autoFocusHandler.obtainMessage(
					autoFocusMessage, Boolean.valueOf(flag));
			autoFocusHandler.sendMessageDelayed(message, 1500L);
			autoFocusHandler = null;
			return;
		} else
		{
			DEBUG.o((new StringBuilder()).append(TAG)
					.append("Got auto-focus callback, but no handler for it")
					.toString());
			return;
		}
	}

	void setHandler(Handler handler, int i)
	{
		autoFocusHandler = handler;
		autoFocusMessage = i;
	}

	private static final long AUTOFOCUS_INTERVAL_MS = 1500L;
	private static final String TAG = AutoFocusCallback.class.getSimpleName();
	private Handler autoFocusHandler;
	private int autoFocusMessage;

}
// 2131296256