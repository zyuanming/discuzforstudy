package net.discuz.source;

import android.graphics.*;
import java.io.File;
import java.io.FileOutputStream;

public class DImage
{

	public DImage()
	{
		quality = 90;
	}

	public DImage(int i)
	{
		quality = i;
	}

	public static void _saveFile(Bitmap bitmap, String s,
			android.graphics.Bitmap.CompressFormat compressformat)
			throws Exception
	{
		File file = new File(s);
		if (!file.exists() && !file.isFile())
			file.createNewFile();
		FileOutputStream fileoutputstream = new FileOutputStream(file);
		if (bitmap.compress(compressformat, quality, fileoutputstream))
		{
			fileoutputstream.flush();
			fileoutputstream.close();
		}
	}

	public static Bitmap toRoundCorner(Bitmap bitmap, int i)
	{
		Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), android.graphics.Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap1);
		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		RectF rectf = new RectF(rect);
		float f = i;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(0xff424242);
		canvas.drawRoundRect(rectf, f, f, paint);
		paint.setXfermode(new PorterDuffXfermode(
				android.graphics.PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		if (!bitmap.isRecycled())
		{
			bitmap.recycle();
			System.gc();
		}
		return bitmap1;
	}

	public Bitmap _rotate(Bitmap bitmap, int i, String s)
	{
		if (bitmap == null)
		{
			bitmap = null;
		} else
		{
			double d = Math.rint(i / 90);
			if (d != 0.0D)
			{
				int j = (int) (d * 90D);
				Matrix matrix = new Matrix();
				DEBUG.o((new StringBuilder()).append("Image Rotate:").append(j)
						.toString());
				matrix.postRotate(j);
				int k = bitmap.getWidth();
				int l = bitmap.getHeight();
				Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, k, l,
						matrix, true);
				if (_saveFile(bitmap1, s))
				{
					if (!bitmap.isRecycled())
						bitmap.recycle();
					return bitmap1;
				}
			}
		}
		return bitmap;
	}

	public boolean _saveFile(Bitmap bitmap, String s)
	{
		boolean flag = false;
		if (s != null)
		{
			flag = false;
			if (s != "")
			{
				try
				{
					_saveFile(bitmap, s,
							android.graphics.Bitmap.CompressFormat.JPEG);
				} catch (Exception exception)
				{
					exception.printStackTrace();
					return false;
				}
				flag = true;
			}
		}
		return flag;
	}

	public Bitmap _thumb(Bitmap bitmap, double d, String s)
	{
		if (bitmap == null)
		{
			bitmap = null;
		} else
		{
			int i = bitmap.getWidth();
			int j = bitmap.getHeight();
			double d1 = Math.max(i, j);
			double d2 = Math.min(i, j);
			double d3;
			Matrix matrix;
			float f;
			float f1;
			if (d1 >= d && d > 0.0D && d1 > 0.0D)
			{
				if (i > j)
				{
					double d4 = d / (d1 / d2);
					d3 = d;
					d = d4;
				} else
				{
					d3 = d / (d1 / d2);
				}
			} else
			{
				d3 = i;
				d = j;
			}
			matrix = new Matrix();
			f = (float) d3 / (float) i;
			f1 = (float) d / (float) j;
			matrix.postScale(f, f1);
			if (f != 1.0F && f1 != 1.0F)
			{
				Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, i, j,
						matrix, true);
				if (!bitmap.isRecycled())
				{
					bitmap.recycle();
					bitmap = bitmap1;
					System.gc();
				} else
				{
					bitmap = bitmap1;
				}
			}
			DEBUG.o((new StringBuilder())
					.append("image params >>> \u539F\u59CB\u56FE\u7247\u5927\u5C0F\uFF1A")
					.append(i).append("*").append(j).toString());
			DEBUG.o((new StringBuilder())
					.append("image params >>> \u7EFD\u653E\u540E\u56FE\u7247\u5927\u5C0F\uFF1A")
					.append(bitmap.getWidth()).append("*")
					.append(bitmap.getHeight()).toString());
			if (!_saveFile(bitmap, s))
				return null;
		}
		return bitmap;
	}

	private static int quality = 100;

}
//2131296256