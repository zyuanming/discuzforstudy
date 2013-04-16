package net.discuz.source.ImageFilter;

public class WaterWaveFilter extends RadialDistortionFilter
{

	public WaterWaveFilter()
	{}

	void DropStone(int i, int j, int k, int l)
	{
		if (i + k <= width && j + k <= height && i - k >= 0 && j - k >= 0)
		{
			int i1 = i - k;
			while (i1 < i + k)
			{
				for (int j1 = j - k; j1 < j + k; j1++)
					if ((i1 - i) * (i1 - i) + (j1 - j) * (j1 - j) <= k * k)
						buf1[i1 + j1 * width] = (short) (-l);

				i1++;
			}
		}
	}

	void RippleSpread()
	{
		for (int i = width; i < width * height - width; i++)
		{
			buf2[i] = (short) ((buf1[i - 1] + buf1[i + 1] + buf1[i - width]
					+ buf1[i + width] >> 1) - buf2[i]);
			short aword1[] = buf2;
			aword1[i] = (short) (aword1[i] - (buf2[i] >> 5));
		}

		short aword0[] = buf1;
		buf1 = buf2;
		buf2 = aword0;
	}

	public Image process(Image image)
	{
		width = image.getWidth();
		height = image.getHeight();
		buf2 = new short[width * height];
		buf1 = new short[width * height];
		source = image.colorArray;
		temp = new int[source.length];
		DropStone(width / 2, height / 2, Math.max(width, height) / 4,
				Math.max(width, height));
		for (int i = 0; i < 170; i++)
		{
			RippleSpread();
			render();
		}

		image.colorArray = temp;
		return image;
	}

	void render()
	{
		int i = width;
		for (int j = 1; j < -1 + height; j++)
		{
			int k = 0;
			while (k < width)
			{
				int l = buf1[i - 1] - buf1[i + 1];
				int i1 = buf1[i - width] - buf1[i + width];
				int j1;
				if (j + i1 < 0 || j + i1 >= height || k + l < 0
						|| k + l >= width)
				{
					j1 = i + 1;
				} else
				{
					int k1 = width * (i1 + j) + (l + k);
					int l1 = k + j * width;
					int ai[] = temp;
					int _tmp = l1 + 1;
					int ai1[] = source;
					int _tmp1 = k1 + 1;
					ai[l1] = ai1[k1];
					j1 = i + 1;
				}
				k++;
				i = j1;
			}
		}

	}

	short buf1[];
	short buf2[];
	int height;
	int source[];
	int temp[];
	int width;
}
//2131296256