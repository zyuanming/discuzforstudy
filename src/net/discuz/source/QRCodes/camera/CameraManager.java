package net.discuz.source.QRCodes.camera;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.view.SurfaceHolder;
import java.io.IOException;
import net.discuz.source.DEBUG;

public final class CameraManager
{

	private CameraManager(Context context1)
	{
		context = context1;
		configManager = new CameraConfigurationManager(context1);
		boolean flag;
		if (Integer.parseInt(android.os.Build.VERSION.SDK) > 3)
			flag = true;
		else
			flag = false;
		useOneShotPreviewCallback = flag;
		previewCallback = new PreviewCallback(configManager,
				useOneShotPreviewCallback);
	}

	public static CameraManager get()
	{
		return cameraManager;
	}

	public static void init(Context context1)
	{
		if (cameraManager == null)
			cameraManager = new CameraManager(context1);
	}

	public PlanarYUVLuminanceSource buildLuminanceSource(byte abyte0[], int i,
			int j)
	{
		Rect rect = getFramingRectInPreview();
		int k = configManager.getPreviewFormat();
		String s = configManager.getPreviewFormatString();
		switch (k)
		{
		default:
			if ("yuv420p".equals(s))
				return new PlanarYUVLuminanceSource(abyte0, i, j, rect.left,
						rect.top, rect.width(), rect.height());
			else
				throw new IllegalArgumentException((new StringBuilder())
						.append("Unsupported picture format: ").append(k)
						.append('/').append(s).toString());

		case 16: // '\020'
		case 17: // '\021'
			return new PlanarYUVLuminanceSource(abyte0, i, j, rect.left,
					rect.top, rect.width(), rect.height());
		}
	}

	public void closeDriver()
	{
		if (camera != null)
		{
			FlashlightManager.disableFlashlight();
			camera.release();
			camera = null;
		}
	}

	public Rect getFramingRect()
	{
		int i = 480;
		int j = 240;
		Point point = configManager.getScreenResolution();
		if (framingRect == null)
		{
			if (camera == null)
				return null;
			int k = (3 * point.x) / 4;
			int l;
			int i1;
			int j1;
			if (k < j)
				i = j;
			else if (k <= i)
				i = k;
			l = (3 * point.y) / 4;
			if (l >= j)
				if (l > 360)
					j = 360;
				else
					j = l;
			i1 = (point.x - i) / 2;
			j1 = (point.y - j) / 2;
			framingRect = new Rect(i1, j1, i + i1, j + j1);
			DEBUG.o((new StringBuilder()).append(TAG)
					.append("Calculated framing rect: ").append(framingRect)
					.toString());
		}
		return framingRect;
	}

	public Rect getFramingRectInPreview()
	{
		if (framingRectInPreview == null)
		{
			Rect rect = new Rect(getFramingRect());
			Point point = configManager.getCameraResolution();
			Point point1 = configManager.getScreenResolution();
			rect.left = (rect.left * point.x) / point1.x;
			rect.right = (rect.right * point.x) / point1.x;
			rect.top = (rect.top * point.y) / point1.y;
			rect.bottom = (rect.bottom * point.y) / point1.y;
			framingRectInPreview = rect;
		}
		return framingRectInPreview;
	}

	public void openDriver(SurfaceHolder surfaceholder) throws IOException
	{
		if (camera == null)
		{
			camera = Camera.open();
			if (camera == null)
				throw new IOException();
			camera.setPreviewDisplay(surfaceholder);
			if (!initialized)
			{
				initialized = true;
				configManager.initFromCameraParameters(camera);
			}
			configManager.setDesiredCameraParameters(camera);
		}
	}

	public void requestAutoFocus(Handler handler, int i)
	{
		if (camera != null && previewing)
		{
			autoFocusCallback.setHandler(handler, i);
			camera.autoFocus(autoFocusCallback);
		}
	}

	public void requestPreviewFrame(Handler handler, int i)
	{
		if (camera != null && previewing)
		{
			previewCallback.setHandler(handler, i);
			if (useOneShotPreviewCallback)
				camera.setOneShotPreviewCallback(previewCallback);
		}
		camera.setPreviewCallback(previewCallback);
	}

	public void startPreview()
	{
		if (camera != null && !previewing)
		{
			camera.startPreview();
			previewing = true;
		}
	}

	public void stopPreview()
	{
		if (camera != null && previewing)
		{
			if (!useOneShotPreviewCallback)
				camera.setPreviewCallback(null);
			camera.stopPreview();
			previewCallback.setHandler(null, 0);
			autoFocusCallback.setHandler(null, 0);
			previewing = false;
		}
	}

	private static final int MAX_FRAME_HEIGHT = 360;
	private static final int MAX_FRAME_WIDTH = 480;
	private static final int MIN_FRAME_HEIGHT = 240;
	private static final int MIN_FRAME_WIDTH = 240;
	static int SDK_INT;
	private static final String TAG = CameraManager.class.getSimpleName();
	private static CameraManager cameraManager;
	private final AutoFocusCallback autoFocusCallback = new AutoFocusCallback();
	private Camera camera;
	private final CameraConfigurationManager configManager;
	private final Context context;
	private Rect framingRect;
	private Rect framingRectInPreview;
	private boolean initialized;
	private final PreviewCallback previewCallback;
	private boolean previewing;
	private final boolean useOneShotPreviewCallback;

	static
	{
		int j;
		int i = 10000;
		try
		{
			j = Integer.parseInt(android.os.Build.VERSION.SDK);
			i = j;
			SDK_INT = i;
		} catch (Exception e)
		{
			e.printStackTrace();
			SDK_INT = i;
		}
	}
}
// 2131296256