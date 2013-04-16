package net.discuz.source.QRCodes.camera;

import android.graphics.Point;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import net.discuz.source.DEBUG;

final class PreviewCallback implements android.hardware.Camera.PreviewCallback
{

	PreviewCallback(CameraConfigurationManager cameraconfigurationmanager,
			boolean flag)
	{
		configManager = cameraconfigurationmanager;
		useOneShotPreviewCallback = flag;
	}

	public void onPreviewFrame(byte abyte0[], Camera camera)
	{
		Point point = configManager.getCameraResolution();
		if (!useOneShotPreviewCallback)
			camera.setPreviewCallback(null);
		if (previewHandler != null)
		{
			previewHandler.obtainMessage(previewMessage, point.x, point.y,
					abyte0).sendToTarget();
			previewHandler = null;
			return;
		} else
		{
			DEBUG.o((new StringBuilder()).append(TAG)
					.append("Got preview callback, but no handler for it")
					.toString());
			return;
		}
	}

	void setHandler(Handler handler, int i)
	{
		previewHandler = handler;
		previewMessage = i;
	}

	private static final String TAG = PreviewCallback.class.getSimpleName();
	private final CameraConfigurationManager configManager;
	private Handler previewHandler;
	private int previewMessage;
	private final boolean useOneShotPreviewCallback;

}
// 2131296256