package net.discuz.source.QRCodes.camera;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.util.regex.Pattern;
import net.discuz.source.DEBUG;

final class CameraConfigurationManager
{

	CameraConfigurationManager(Context context1)
	{
		context = context1;
	}

	private static int findBestMotZoomValue(CharSequence charsequence, int i)
	{
		String as[] = COMMA_PATTERN.split(charsequence);
		int j = as.length;
		int k = 0;
		int l = 0;
		while (k < j)
		{
			String s = as[k].trim();
			double d;
			int i1;
			try
			{
				d = Double.parseDouble(s);
			} catch (NumberFormatException numberformatexception)
			{
				return i;
			}
			i1 = (int) (10D * d);
			if (Math.abs((double) i - d) >= (double) Math.abs(i - l))
				i1 = l;
			k++;
			l = i1;
		}
		return l;
	}

	private static Point findBestPreviewSizeValue(CharSequence charsequence,
			Point point)
	{
		return null;
	}

	private static Point getCameraResolution(
			android.hardware.Camera.Parameters parameters, Point point)
	{
		String s = parameters.get("preview-size-values");
		String s1;
		Point point1;
		if (s == null)
			s1 = parameters.get("preview-size-value");
		else
			s1 = s;
		point1 = null;
		if (s1 != null)
		{
			Log.d(TAG,
					(new StringBuilder())
							.append("preview-size-values parameter: ")
							.append(s1).toString());
			point1 = findBestPreviewSizeValue(s1, point);
		}
		if (point1 == null)
			point1 = new Point((point.x >> 3) << 3, (point.y >> 3) << 3);
		return point1;
	}

	private void setFlash(android.hardware.Camera.Parameters parameters)
	{
		if (Build.MODEL.contains("Behold II") && CameraManager.SDK_INT == 3)
			parameters.set("flash-value", 1);
		else
			parameters.set("flash-value", 2);
		parameters.set("flash-mode", "off");
	}

	private void setZoom(android.hardware.Camera.Parameters parameters)
	{

	}

	Point getCameraResolution()
	{
		return cameraResolution;
	}

	int getPreviewFormat()
	{
		return previewFormat;
	}

	String getPreviewFormatString()
	{
		return previewFormatString;
	}

	Point getScreenResolution()
	{
		return screenResolution;
	}

	void initFromCameraParameters(Camera camera)
	{
		android.hardware.Camera.Parameters parameters = camera.getParameters();
		previewFormat = parameters.getPreviewFormat();
		previewFormatString = parameters.get("preview-format");
		DEBUG.o((new StringBuilder()).append(TAG)
				.append("Default preview format: ").append(previewFormat)
				.append('/').append(previewFormatString).toString());
		Display display = ((WindowManager) context.getSystemService("window"))
				.getDefaultDisplay();
		screenResolution = new Point(display.getWidth(), display.getHeight());
		DEBUG.o((new StringBuilder()).append(TAG).append("Screen resolution: ")
				.append(screenResolution).toString());
		cameraResolution = getCameraResolution(parameters, screenResolution);
		DEBUG.o((new StringBuilder()).append(TAG).append("Camera resolution: ")
				.append(screenResolution).toString());
	}

	void setDesiredCameraParameters(Camera camera)
	{
		android.hardware.Camera.Parameters parameters = camera.getParameters();
		Log.d(TAG, (new StringBuilder()).append("Setting preview size: ")
				.append(cameraResolution).toString());
		parameters.setPreviewSize(cameraResolution.x, cameraResolution.y);
		setFlash(parameters);
		setZoom(parameters);
		camera.setParameters(parameters);
	}

	private static final Pattern COMMA_PATTERN = Pattern.compile(",");
	private static final int DESIRED_SHARPNESS = 30;
	private static final String TAG = CameraConfigurationManager.class
			.getSimpleName();
	private static final int TEN_DESIRED_ZOOM = 27;
	private Point cameraResolution;
	private final Context context;
	private int previewFormat;
	private String previewFormatString;
	private Point screenResolution;

}
// 2131296256