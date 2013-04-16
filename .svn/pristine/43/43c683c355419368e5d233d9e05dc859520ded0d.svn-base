package net.discuz.source.ImageFilter;

import android.graphics.Color;
import android.graphics.Paint;
import java.lang.reflect.Array;

public class EdgeFilter implements IImageFilter
{

	public EdgeFilter()
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
		for (int k = 0; k <= 255; k++)
		{
			Paint paint = new Paint();
			paint.setColor(Color.rgb(k, k, k));
			apaint[k] = paint;
		}

		int ai[] = { i, j };
		int ai1[][] = (int[][]) Array.newInstance(Integer.TYPE, ai);
		for (int l = 0; l < j; l++)
		{
			int i2 = 0;
			while (i2 < i)
			{
				if (aflag == null || aflag[i2][l])
					ai1[i2][l] = luminance(image.getRComponent(i2, l),
							image.getGComponent(i2, l),
							image.getBComponent(i2, l));
				i2++;
			}
		}

		for (int i1 = 1; i1 < j - 1; i1++)
		{
			int j1 = 1;
			while (j1 < i - 1)
			{
				if (aflag == null || aflag[j1][i1])
				{
					int k1 = ((((-ai1[j1 - 1][i1 - 1] + ai1[j1 - 1][2 + (i1 - 1)]) - 2 * ai1[1 + (j1 - 1)][i1 - 1]) + 2 * ai1[1 + (j1 - 1)][2 + (i1 - 1)]) - ai1[2 + (j1 - 1)][i1 - 1])
							+ ai1[2 + (j1 - 1)][2 + (i1 - 1)];
					int l1 = (ai1[j1 - 1][i1 - 1] + 2
							* ai1[j1 - 1][1 + (i1 - 1)] + ai1[j1 - 1][2 + (i1 - 1)])
							- ai1[2 + (j1 - 1)][i1 - 1]
							- 2
							* ai1[2 + (j1 - 1)][1 + (i1 - 1)]
							- ai1[2 + (j1 - 1)][2 + (i1 - 1)];
					image.setPixelColor(j1, i1,
							apaint[255 - truncate(Math.abs(k1) + Math.abs(l1))]
									.getColor());
				}
				j1++;
			}
		}

		return image;
	}
}
//2131296256