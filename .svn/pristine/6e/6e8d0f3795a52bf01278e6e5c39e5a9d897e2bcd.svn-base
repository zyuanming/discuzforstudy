package net.discuz.source.QRCodes.view;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import net.discuz.R;
import net.discuz.source.QRCodes.camera.CameraManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;

public final class ViewfinderView extends View
{

	public ViewfinderView(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
		Resources resources = getResources();
		maskColor = resources.getColor(R.color.viewfinder_mask);
		resultColor = resources.getColor(R.color.result_view);
		frameColor = resources.getColor(R.color.viewfinder_frame);
		laserColor = resources.getColor(R.color.viewfinder_laser);
		resultPointColor = resources.getColor(R.color.possible_result_points);
		scannerAlpha = 0;
		possibleResultPoints = new HashSet(5);
	}

	public void addPossibleResultPoint(ResultPoint resultpoint)
	{
		possibleResultPoints.add(resultpoint);
	}

	public void drawResultBitmap(Bitmap bitmap)
	{
		resultBitmap = bitmap;
		invalidate();
	}

	public void drawViewfinder()
	{
		resultBitmap = null;
		invalidate();
	}

	public void onDraw(Canvas canvas)
	{
		Rect rect = CameraManager.get().getFramingRect();
		if (rect == null)
			return;
		int i = canvas.getWidth();
		int j = canvas.getHeight();
		Paint paint1 = paint;
		int k;
		if (resultBitmap != null)
			k = resultColor;
		else
			k = maskColor;
		paint1.setColor(k);
		canvas.drawRect(0.0F, 0.0F, i, rect.top, paint);
		canvas.drawRect(0.0F, rect.top, rect.left, 1 + rect.bottom, paint);
		canvas.drawRect(1 + rect.right, rect.top, i, 1 + rect.bottom, paint);
		canvas.drawRect(0.0F, 1 + rect.bottom, i, j, paint);
		if (resultBitmap != null)
		{
			paint.setAlpha(255);
			canvas.drawBitmap(resultBitmap, rect.left, rect.top, paint);
			return;
		}
		paint.setColor(frameColor);
		canvas.drawRect(rect.left, rect.top, 1 + rect.right, 2 + rect.top,
				paint);
		canvas.drawRect(rect.left, 2 + rect.top, 2 + rect.left, -1
				+ rect.bottom, paint);
		canvas.drawRect(-1 + rect.right, rect.top, 1 + rect.right, -1
				+ rect.bottom, paint);
		canvas.drawRect(rect.left, -1 + rect.bottom, 1 + rect.right,
				1 + rect.bottom, paint);
		paint.setColor(laserColor);
		paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
		scannerAlpha = (1 + scannerAlpha) % SCANNER_ALPHA.length;
		int l = rect.height() / 2 + rect.top;
		canvas.drawRect(2 + rect.left, l - 1, -1 + rect.right, l + 2, paint);
		Collection collection = possibleResultPoints;
		Collection collection1 = lastPossibleResultPoints;
		if (collection.isEmpty())
		{
			lastPossibleResultPoints = null;
		} else
		{
			possibleResultPoints = new HashSet(5);
			lastPossibleResultPoints = collection;
			paint.setAlpha(255);
			paint.setColor(resultPointColor);
			Iterator iterator = collection.iterator();
			while (iterator.hasNext())
			{
				ResultPoint resultpoint = (ResultPoint) iterator.next();
				canvas.drawCircle((float) rect.left + resultpoint.getX(),
						(float) rect.top + resultpoint.getY(), 6F, paint);
			}
		}
		if (collection1 != null)
		{
			paint.setAlpha(127);
			paint.setColor(resultPointColor);
			ResultPoint resultpoint1;
			for (Iterator iterator1 = collection1.iterator(); iterator1
					.hasNext(); canvas.drawCircle((float) rect.left
					+ resultpoint1.getX(),
					(float) rect.top + resultpoint1.getY(), 3F, paint))
				resultpoint1 = (ResultPoint) iterator1.next();

		}
		postInvalidateDelayed(100L, rect.left, rect.top, rect.right,
				rect.bottom);
	}

	private static final long ANIMATION_DELAY = 100L;
	private static final int OPAQUE = 255;
	private static final int SCANNER_ALPHA[] = { 0, 64, 128, 192, 255, 192,
			128, 64 };
	private final int frameColor;
	private final int laserColor;
	private Collection lastPossibleResultPoints;
	private final int maskColor;
	private final Paint paint = new Paint();
	private Collection possibleResultPoints;
	private Bitmap resultBitmap;
	private final int resultColor;
	private final int resultPointColor;
	private int scannerAlpha;

}
// 2131296256