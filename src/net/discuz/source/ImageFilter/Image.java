package net.discuz.source.ImageFilter;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import java.nio.IntBuffer;

public class Image
{

	public Image(Bitmap bitmap)
	{
		image = bitmap;
		formatName = "jpg";
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		destImage = Bitmap.createBitmap(width, height,
				android.graphics.Bitmap.Config.ARGB_8888);
		updateColorArray();
	}

	public static int SAFECOLOR(int i)
	{
		if (i < 0)
			i = 0;
		else if (i > 255)
			return 255;
		return i;
	}

	private void updateColorArray()
	{
		colorArray = new int[width * height];
		image.getPixels(colorArray, 0, width, 0, 0, width, height);
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				int k = j + i * width;
				int l = 0xff & colorArray[k] >> 16;
				int i1 = 0xff & colorArray[k] >> 8;
				int j1 = 0xff & colorArray[k];
				colorArray[k] = l | (0xff000000 | j1 << 16 | i1 << 8);
			}

		}

	}

	public void clearImage(int i)
	{
		for (int j = 0; j < height; j++)
		{
			for (int k = 0; k < width; k++)
				setPixelColor(k, j, i);

		}

	}

	public Object clone() throws CloneNotSupportedException
	{
		return new Image(image);
	}

	public void copyPixelsFromBuffer()
	{
		IntBuffer intbuffer = IntBuffer.wrap(colorArray);
		destImage.copyPixelsFromBuffer(intbuffer);
		intbuffer.clear();
	}

	public int getBComponent(int i, int j)
	{
		return 0xff & getColorArray()[i + j * width];
	}

	public int[] getColorArray()
	{
		return colorArray;
	}

	public String getFormatName()
	{
		return formatName;
	}

	public int getGComponent(int i, int j)
	{
		return (0xff00 & getColorArray()[i + j * width]) >>> 8;
	}

	public int getHeight()
	{
		return height;
	}

	public Bitmap getImage()
	{
		return destImage;
	}

	public int getPixelColor(int i, int j)
	{
		return colorArray[i + j * width];
	}

	public int getRComponent(int i, int j)
	{
		return (0xff0000 & getColorArray()[i + j * width]) >>> 16;
	}

	public int getWidth()
	{
		return width;
	}

	public void rotate(int i)
	{
		Matrix matrix = new Matrix();
		matrix.postRotate(i);
		image = Bitmap.createBitmap(image, 0, 0, width, height, matrix, true);
		width = image.getWidth();
		height = image.getHeight();
		updateColorArray();
	}

	public void setColorArray(int ai[])
	{
		colorArray = ai;
	}

	public void setFormatName(String s)
	{
		formatName = s;
	}

	public void setHeight(int i)
	{
		height = i;
	}

	public void setImage(Bitmap bitmap)
	{
		image = bitmap;
	}

	public void setPixelColor(int i, int j, int k)
	{
		colorArray[i + j * image.getWidth()] = k;
	}

	public void setPixelColor(int i, int j, int k, int l, int i1)
	{
		int j1 = i1 + (0xff000000 + (k << 16) + (l << 8));
		colorArray[i + j * image.getWidth()] = j1;
	}

	public void setWidth(int i)
	{
		width = i;
	}

	protected int colorArray[];
	public Bitmap destImage;
	private String formatName;
	private int height;
	public Bitmap image;
	private int width;
}
//2131296256