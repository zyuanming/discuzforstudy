package net.discuz.source.ImageFilter;

public class HistogramEqualFilter implements IImageFilter
{

	public HistogramEqualFilter()
	{
		ContrastIntensity = 1.0F;
	}

	public Image process(Image image)
	{
		int ai[] = new int[256];
		int ai1[] = new int[image.getHeight() * image.getWidth()];
		int i = (int) (255F * ContrastIntensity);
		int j = 0;
		int k = 0;
		for (; j < image.getWidth(); j++)
		{
			for (int k3 = 0; k3 < image.getHeight();)
			{
				int l3 = image.getRComponent(j, k3);
				int i4 = image.getGComponent(j, k3);
				int j4 = image.getBComponent(j, k3);
				int k4 = l3 * 6966 + i4 * 23436 + j4 * 2366 >> 15;
				ai[k4] = 1 + ai[k4];
				ai1[k] = k4;
				int l4 = k + 1;
				k3++;
				k = l4;
			}

		}

		for (int l = 1; l < 256; l++)
			ai[l] = ai[l] + ai[l - 1];

		for (int i1 = 0; i1 < 256; i1++)
		{
			ai[i1] = ((ai[i1] << 8) / image.getHeight()) * image.getWidth();
			ai[i1] = (i * ai[i1] >> 8) + (i1 * (255 - i) >> 8);
		}

		int j1 = 0;
		int i2;
		for (int k1 = 0; j1 < image.getWidth(); k1 = i2)
		{
			int l1 = 0;
			i2 = k1;
			while (l1 < image.getHeight())
			{
				byte byte0 = (byte) image.getRComponent(j1, l1);
				byte byte1 = (byte) image.getGComponent(j1, l1);
				int j2 = image.getBComponent(j1, l1);
				if (ai1[i2] != 0)
				{
					int k2 = ai[ai1[i2]];
					int l2 = (byte0 * k2) / ai1[i2];
					int i3 = (byte1 * k2) / ai1[i2];
					int j3 = (k2 * j2) / ai1[i2];
					byte byte2;
					if (l2 > 255)
						byte0 = -1;
					else if (l2 < 0)
						byte0 = 0;
					else
						byte0 = (byte) l2;
					if (i3 > 255)
						byte1 = -1;
					else if (i3 < 0)
						byte1 = 0;
					else
						byte1 = (byte) i3;
					if (j3 > 255)
						byte2 = -1;
					else if (j3 < 0)
						byte2 = 0;
					else
						byte2 = (byte) j3;
					j2 = byte2;
				}
				image.setPixelColor(j1, l1, byte0, byte1, j2);
				i2++;
				l1++;
			}
			j1++;
		}

		return image;
	}

	public float ContrastIntensity;
}
//2131296256