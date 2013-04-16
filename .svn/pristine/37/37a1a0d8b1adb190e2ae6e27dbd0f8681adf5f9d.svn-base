package net.discuz.source.QRCodes.encoding;

import java.util.Vector;

import net.discuz.R;
import net.discuz.source.DEBUG;
import net.discuz.source.QRCodes.CaptureActivity;
import net.discuz.source.QRCodes.camera.CameraManager;
import net.discuz.source.QRCodes.view.ViewfinderResultPointCallback;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.google.zxing.Result;

public final class CaptureActivityHandler extends Handler
{
	private enum State
	{

		PREVIEW("PREVIEW", 0), SUCCESS("SUCCESS", 1), DONE("DONE", 2);

		private String name;
		private int i;

		private State(String s, int i)
		{
			this.name = name;
			this.i = i;
		}
	}

	public CaptureActivityHandler(CaptureActivity captureactivity,
			Vector vector, String s)
	{
		activity = captureactivity;
		decodeThread = new DecodeThread(captureactivity, vector, s,
				new ViewfinderResultPointCallback(
						captureactivity.getViewfinderView()));
		decodeThread.start();
		state = State.SUCCESS;
		CameraManager.get().startPreview();
		restartPreviewAndDecode();
	}

	private void restartPreviewAndDecode()
	{
		if (state == State.SUCCESS)
		{
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),
					R.id.decode);
			CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
			activity.drawViewfinder();
		}
	}

	public void handleMessage(Message message)
	{
		switch (message.what)
		{
		case 2131230721:
		case 2131230724:
		case 2131230725:
		case 2131230727:
		default:
			return;
		case 2131230720:
			if (state == State.PREVIEW)
			{
				CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
			}
			break;
		case 2131230728:
			DEBUG.o((new StringBuilder()).append(TAG)
					.append("Got restart preview message").toString());
			restartPreviewAndDecode();
			break;
		case 2131230723:
			DEBUG.o((new StringBuilder()).append(TAG)
					.append("Got decode succeeded message").toString());
			state = State.SUCCESS;
			Bundle bundle = message.getData();
			Bitmap bitmap;
			if (bundle == null)
				bitmap = null;
			else
				bitmap = (Bitmap) bundle.getParcelable("barcode_bitmap");
			activity.handleDecode((Result) message.obj, bitmap);
			break;
		case 2131230722:
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),
					R.id.decode);
			break;
		case 2131230729:
			DEBUG.o((new StringBuilder()).append(TAG)
					.append("Got return scan result message").toString());
			activity.setResult(-1, (Intent) message.obj);
			activity.finish();
			break;
		case 2131230726:
			DEBUG.o((new StringBuilder()).append(TAG)
					.append("Got product query message").toString());
			Intent intent = new Intent("android.intent.action.VIEW",
					Uri.parse((String) message.obj));
			intent.addFlags(0x80000);
			activity.startActivity(intent);
			break;
		}
	}

	public void quitSynchronously()
	{
		state = State.DONE;
		CameraManager.get().stopPreview();
		Message.obtain(decodeThread.getHandler(), R.id.quit).sendToTarget();
		try
		{
			decodeThread.join();
		} catch (InterruptedException interruptedexception)
		{}
		removeMessages(R.id.decode_succeeded);
		removeMessages(R.id.decode_failed);
	}

	private static final String TAG = CaptureActivityHandler.class
			.getSimpleName();
	private final CaptureActivity activity;
	private final DecodeThread decodeThread;
	private State state;

}
// 2131296256