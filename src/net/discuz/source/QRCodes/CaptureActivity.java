package net.discuz.source.QRCodes;

import java.io.IOException;
import java.util.Vector;

import net.discuz.R;
import net.discuz.source.QRCodes.camera.CameraManager;
import net.discuz.source.QRCodes.encoding.CaptureActivityHandler;
import net.discuz.source.QRCodes.encoding.InactivityTimer;
import net.discuz.source.QRCodes.view.ViewfinderView;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.zxing.Result;

public class CaptureActivity extends Activity implements
		android.view.SurfaceHolder.Callback
{

	public CaptureActivity()
	{}

	private void initBeepSound()
	{
		AssetFileDescriptor assetfiledescriptor;
		if (playBeep && mediaPlayer == null)
		{
			try
			{
				setVolumeControlStream(3);
				mediaPlayer = new MediaPlayer();
				mediaPlayer.setAudioStreamType(3);
				mediaPlayer.setOnCompletionListener(beepListener);
				assetfiledescriptor = getResources().openRawResourceFd(
						R.raw.beep);
				mediaPlayer.setDataSource(
						assetfiledescriptor.getFileDescriptor(),
						assetfiledescriptor.getStartOffset(),
						assetfiledescriptor.getLength());
				assetfiledescriptor.close();
				mediaPlayer.setVolume(0.1F, 0.1F);
				mediaPlayer.prepare();
			} catch (Exception e)
			{
				e.printStackTrace();
				mediaPlayer = null;
			}
		}
	}

	private void initCamera(SurfaceHolder surfaceholder)
	{
		try
		{
			CameraManager.get().openDriver(surfaceholder);
		} catch (IOException ioexception)
		{
			return;
		} catch (RuntimeException runtimeexception)
		{
			return;
		}
		if (handler == null)
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
	}

	private void playBeepSoundAndVibrate()
	{
		if (playBeep && mediaPlayer != null)
			mediaPlayer.start();
		if (vibrate)
			((Vibrator) getSystemService("vibrator")).vibrate(200L);
	}

	public void drawViewfinder()
	{
		viewfinderView.drawViewfinder();
	}

	public Handler getHandler()
	{
		return handler;
	}

	public ViewfinderView getViewfinderView()
	{
		return viewfinderView;
	}

	public void handleDecode(Result result, Bitmap bitmap)
	{
		barcode = bitmap;
		inactivityTimer.onActivity();
		viewfinderView.drawResultBitmap(bitmap);
		playBeepSoundAndVibrate();
		Intent intent = new Intent();
		intent.putExtra("QRCodesResult", result.getText());
		setResult(1, intent);
		finish();
	}

	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.sitelist_add_qrcodes);
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		txtResult = (TextView) findViewById(R.id.txtResult);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
	}

	protected void onDestroy()
	{
		inactivityTimer.shutdown();
		super.onDestroy();
		if (barcode != null)
			barcode.recycle();
	}

	protected void onPause()
	{
		super.onPause();
		if (handler != null)
		{
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	protected void onResume()
	{
		super.onResume();
		SurfaceHolder surfaceholder = ((SurfaceView) findViewById(R.id.preview_view))
				.getHolder();
		if (hasSurface)
		{
			initCamera(surfaceholder);
		} else
		{
			surfaceholder.addCallback(this);
			surfaceholder.setType(3);
		}
		decodeFormats = null;
		characterSet = null;
		playBeep = true;
		if (((AudioManager) getSystemService("audio")).getRingerMode() != 2)
			playBeep = false;
		initBeepSound();
		vibrate = true;
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k)
	{}

	public void surfaceCreated(SurfaceHolder surfaceholder)
	{
		if (!hasSurface)
		{
			hasSurface = true;
			initCamera(surfaceholder);
		}
	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder)
	{
		hasSurface = false;
	}

	private static final float BEEP_VOLUME = 0.1F;
	private static final long VIBRATE_DURATION = 200L;
	private Bitmap barcode;
	private final android.media.MediaPlayer.OnCompletionListener beepListener = new android.media.MediaPlayer.OnCompletionListener()
	{

		public void onCompletion(MediaPlayer mediaplayer)
		{
			mediaplayer.seekTo(0);
		}
	};
	private String characterSet;
	private Vector decodeFormats;
	private CaptureActivityHandler handler;
	private boolean hasSurface;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private TextView txtResult;
	private boolean vibrate;
	private ViewfinderView viewfinderView;
}
// 2131296256