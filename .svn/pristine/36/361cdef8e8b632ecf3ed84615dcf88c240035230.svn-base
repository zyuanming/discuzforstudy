package net.discuz.source.QRCodes.encoding;

import java.util.Hashtable;

import net.discuz.R;
import net.discuz.source.DEBUG;
import net.discuz.source.QRCodes.CaptureActivity;
import net.discuz.source.QRCodes.camera.CameraManager;
import net.discuz.source.QRCodes.camera.PlanarYUVLuminanceSource;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

final class DecodeHandler extends Handler
{

	DecodeHandler(CaptureActivity captureactivity, Hashtable hashtable)
	{
		multiFormatReader.setHints(hashtable);
		activity = captureactivity;
	}

	private void decode(byte abyte0[], int i, int j)
	{
		long l;
		PlanarYUVLuminanceSource planaryuvluminancesource;
		BinaryBitmap binarybitmap;
		l = System.currentTimeMillis();
		try
		{
			planaryuvluminancesource = CameraManager.get()
					.buildLuminanceSource(abyte0, i, j);
			binarybitmap = new BinaryBitmap(new HybridBinarizer(
					planaryuvluminancesource));
			Result result1 = multiFormatReader.decodeWithState(binarybitmap);
			Result result;
			result = result1;
			multiFormatReader.reset();
			if (result != null)
			{
				long l1 = System.currentTimeMillis();
				DEBUG.o((new StringBuilder()).append(TAG)
						.append("Found barcode (").append(l1 - l)
						.append(" ms):\n").append(result.toString()).toString());
				Message message = Message.obtain(activity.getHandler(),
						R.id.decode_succeeded, result);
				Bundle bundle = new Bundle();
				bundle.putParcelable("barcode_bitmap",
						planaryuvluminancesource.renderCroppedGreyscaleBitmap());
				message.setData(bundle);
				message.sendToTarget();
			} else
			{
				Message.obtain(activity.getHandler(), R.id.decode_failed)
						.sendToTarget();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			multiFormatReader.reset();
		}

	}

	public void handleMessage(Message message)
	{
		switch (message.what)
		{
		default:
			return;

		case 2131230721:
			decode((byte[]) (byte[]) message.obj, message.arg1, message.arg2);
			return;

		case 2131230727:
			Looper.myLooper().quit();
			break;
		}
	}

	private static final String TAG = DecodeHandler.class.getSimpleName();
	private final CaptureActivity activity;
	private final MultiFormatReader multiFormatReader = new MultiFormatReader();

}
// 2131296256