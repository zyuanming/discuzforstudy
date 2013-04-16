package net.discuz.source.ImageFilter;

import java.lang.reflect.Array;

public class ConvolutionFilter implements IImageFilter
{

	public ConvolutionFilter()
	{
		int ai[] = { 3, 3 };
		float af[][] = (float[][]) Array.newInstance(Float.TYPE, ai);
		af[0][0] = 0.0F;
		af[0][1] = 0.0F;
		af[0][2] = 0.0F;
		af[1][0] = 0.0F;
		af[1][1] = 1.0F;
		af[1][2] = 0.0F;
		af[2][0] = 0.0F;
		af[2][1] = 0.0F;
		af[2][2] = 0.4F;
		kernel = af;
		factor = 1;
		offset = 1;
	}

	protected int GetPixelBrightness(Image image, int i, int j, int k, int l)
	{
		if (i < 0)
			i = 0;
		else if (i >= k)
			i = k - 1;
		if (j < 0)
			j = 0;
		else if (j >= l)
			j = l - 1;
		return 6966 * image.getRComponent(i, j) + 23436
				* image.getGComponent(i, j) + 2366 * image.getBComponent(i, j) >> 15;
	}

	protected int GetPixelColor(Image image, int i, int j, int k, int l)
	{
		if (i < 0)
			i = 0;
		else if (i >= k)
			i = k - 1;
		if (j < 0)
			j = 0;
		else if (j >= l)
			j = l - 1;
		return 0xff000000 | image.getRComponent(i, j) << 16
				| image.getGComponent(i, j) << 8 | image.getBComponent(i, j);
	}

	public Image process(Image image)
	{
		int i = image.getWidth();
		int j = image.getHeight();
		int k = 0;
		while (k < i)
		{
			int l = 0;
			while (l < j)
			{
				float f = kernel[0][0];
				boolean i1 = f != 0.0F;
				int j1 = 0;
				int k1 = 0;
				int l1 = 0;
				if (i1 != false)
				{
					int k5 = GetPixelColor(image, k - 1, l - 1, i, j);
					int l5 = (int) ((float) 0 + f
							* (float) ((0xff0000 & k5) >> 16));
					int i6 = (int) ((float) 0 + f
							* (float) ((0xff00 & k5) >> 8));
					j1 = (int) ((float) 0 + f * (float) (k5 & 0xff));
					k1 = i6;
					l1 = l5;
				}
				float f1 = kernel[0][1];
				if (f1 != 0.0F)
				{
					int i5 = l - 1;
					int j5 = GetPixelColor(image, k, i5, i, j);
					l1 = (int) ((float) l1 + f1
							* (float) ((0xff0000 & j5) >> 16));
					k1 = (int) ((float) k1 + f1 * (float) ((0xff00 & j5) >> 8));
					j1 = (int) ((float) j1 + f1 * (float) (j5 & 0xff));
				}
				float f2 = kernel[0][2];
				if (f2 != 0.0F)
				{
					int l4 = GetPixelColor(image, k + 1, l - 1, i, j);
					l1 = (int) ((float) l1 + f2
							* (float) ((0xff0000 & l4) >> 16));
					k1 = (int) ((float) k1 + f2 * (float) ((0xff00 & l4) >> 8));
					j1 = (int) ((float) j1 + f2 * (float) (l4 & 0xff));
				}
				float f3 = kernel[1][0];
				if (f3 != 0.0F)
				{
					int k4 = GetPixelColor(image, k - 1, l, i, j);
					l1 = (int) ((float) l1 + f3
							* (float) ((0xff0000 & k4) >> 16));
					k1 = (int) ((float) k1 + f3 * (float) ((0xff00 & k4) >> 8));
					j1 = (int) ((float) j1 + f3 * (float) (k4 & 0xff));
				}
				float f4 = kernel[1][1];
				if (f4 != 0.0F)
				{
					int j4 = GetPixelColor(image, k, l, i, j);
					l1 = (int) ((float) l1 + f4
							* (float) ((0xff0000 & j4) >> 16));
					k1 = (int) ((float) k1 + f4 * (float) ((0xff00 & j4) >> 8));
					j1 = (int) ((float) j1 + f4 * (float) (j4 & 0xff));
				}
				float f5 = kernel[1][2];
				if (f5 != 0.0F)
				{
					int i4 = GetPixelColor(image, k + 1, l, i, j);
					l1 = (int) ((float) l1 + f5
							* (float) ((0xff0000 & i4) >> 16));
					k1 = (int) ((float) k1 + f5 * (float) ((0xff00 & i4) >> 8));
					j1 = (int) ((float) j1 + f5 * (float) (i4 & 0xff));
				}
				float f6 = kernel[2][0];
				if (f6 != 0.0F)
				{
					int l3 = GetPixelColor(image, k - 1, l + 1, i, j);
					l1 = (int) ((float) l1 + f6
							* (float) ((0xff0000 & l3) >> 16));
					k1 = (int) ((float) k1 + f6 * (float) ((0xff00 & l3) >> 8));
					j1 = (int) ((float) j1 + f6 * (float) (l3 & 0xff));
				}
				float f7 = kernel[2][1];
				if (f7 != 0.0F)
				{
					int j3 = l + 1;
					int k3 = GetPixelColor(image, k, j3, i, j);
					l1 = (int) ((float) l1 + f7
							* (float) ((0xff0000 & k3) >> 16));
					k1 = (int) ((float) k1 + f7 * (float) ((0xff00 & k3) >> 8));
					j1 = (int) ((float) j1 + f7 * (float) (k3 & 0xff));
				}
				float f8 = kernel[2][2];
				if (f8 != 0.0F)
				{
					int i3 = GetPixelColor(image, k + 1, l + 1, i, j);
					l1 = (int) ((float) l1 + f8
							* (float) ((0xff0000 & i3) >> 16));
					k1 = (int) ((float) k1 + f8 * (float) ((0xff00 & i3) >> 8));
					j1 = (int) ((float) j1 + f8 * (float) (i3 & 0xff));
				}
				int i2 = l1 / factor + offset;
				int j2 = k1 / factor + offset;
				int k2 = j1 / factor + offset;
				int l2;
				if (i2 < 0)
					l2 = 0;
				else
					l2 = i2;
				if (l2 > 255)
					l2 = 255;
				if (k2 < 0)
					k2 = 0;
				if (k2 > 255)
					k2 = 255;
				if (j2 < 0)
					j2 = 0;
				if (j2 > 255)
					j2 = 255;
				image.setPixelColor(k, l, l2, j2, k2);
				l++;
			}
			k++;
		}
		return image;
	}

	private int factor;
	private float kernel[][];
	private int offset;
}
//2131296256