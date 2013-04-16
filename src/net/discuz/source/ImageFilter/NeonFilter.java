package net.discuz.source.ImageFilter;

import android.graphics.Color;
import android.graphics.Paint;
import java.lang.reflect.Array;

public class NeonFilter implements IImageFilter
{

	public NeonFilter()
	{}

	private int luminance(int i, int j, int k)
	{
		return (int) (0.29899999999999999D * (double) i + 0.57999999999999996D
				* (double) j + 0.11D * (double) k);
	}

	private int truncate(int i)
	{
		if (i < 0)
			i = 0;
		else if (i > 255)
			return 255;
		return i;
	}

	public Image process(Image image)
	{
		int i = image.getWidth();
		int j = image.getHeight();
		boolean aflag[][] = (boolean[][]) null;
		Paint apaint[] = new Paint[256];
		double d = Math.random();
		byte byte0;
		int k;
		Paint paint;
		int l;
		int i1;
		int j1;
		int k1;
		int ai[];
		int ai1[][];
		int l1;
		int i2;
		int j2;
		int k2;
		int l2;
		int i3;
		if (d > 0.33000000000000002D && d < 0.66000000000000003D)
			byte0 = 2;
		else if (d > 0.66000000000000003D)
			byte0 = 3;
		else
			byte0 = 1;
		k = 255;
		while (k > 0)
		{
			paint = new Paint();
			if (k <= 127)
			{
				l = k;
				i1 = k;
				j1 = k;
			} else
			{
				switch (byte0)
				{
				case 1:
					k1 = 255 - k;
					i1 = k;
					j1 = k1;
					l = k;
					break;
				case 2:
					i1 = 255 - k;
					j1 = k;
					l = k;
					break;
				case 3:
					l = 255 - k;
					i1 = k;
					j1 = k;
					break;
				default:
					l = k;
					i1 = k;
					j1 = k;
				}
			}
			paint.setColor(Color.rgb(j1, i1, l));
			apaint[255 - k] = paint;
			k--;
		}

		ai = (new int[] { i, j });
		ai1 = (int[][]) Array.newInstance(Integer.TYPE, ai);
		for (l1 = 0; l1 < j; l1++)
		{
			i3 = 0;
			while (i3 < i)
			{
				if (aflag == null || aflag[i3][l1])
					ai1[i3][l1] = luminance(image.getRComponent(i3, l1),
							image.getGComponent(i3, l1),
							image.getBComponent(i3, l1));
				i3++;
			}
		}

		for (i2 = 1; i2 < j - 1; i2++)
		{
			j2 = 1;
			while (j2 < i - 1)
			{
				if (aflag == null || aflag[j2][i2])
				{
					k2 = ((((-ai1[j2 - 1][i2 - 1] + ai1[j2 - 1][2 + (i2 - 1)]) - 2 * ai1[1 + (j2 - 1)][i2 - 1]) + 2 * ai1[1 + (j2 - 1)][2 + (i2 - 1)]) - ai1[2 + (j2 - 1)][i2 - 1])
							+ ai1[2 + (j2 - 1)][2 + (i2 - 1)];
					l2 = (ai1[j2 - 1][i2 - 1] + 2 * ai1[j2 - 1][1 + (i2 - 1)] + ai1[j2 - 1][2 + (i2 - 1)])
							- ai1[2 + (j2 - 1)][i2 - 1]
							- 2
							* ai1[2 + (j2 - 1)][1 + (i2 - 1)]
							- ai1[2 + (j2 - 1)][2 + (i2 - 1)];
					image.setPixelColor(j2, i2,
							apaint[255 - truncate(Math.abs(k2) + Math.abs(l2))]
									.getColor());
				}
				j2++;
			}
		}

		return image;
	}
}
// 2131296256