package net.discuz.source.ImageFilter;

public class NoiseFilter implements IImageFilter
{

	public NoiseFilter()
	{
		Intensity = 0.2F;
	}

	public static int getRandomInt(int i, int j)
	{
		int k = Math.min(i, j);
		int l = Math.max(i, j);
		return k + (int) (Math.random() * (double) (1 + (l - k)));
	}

	public Image process(Image image)
	{
		int i = (int) (32768F * Intensity);
		for (int j = 0; j < image.getWidth(); j++)
		{
			int k = 0;
			while (k < image.getHeight())
			{
				byte byte0 = (byte) image.getRComponent(j, k);
				byte byte1 = (byte) image.getGComponent(j, k);
				int l = image.getBComponent(j, k);
				if (i != 0)
				{
					int i1 = i * getRandomInt(-255, 255);
					int j1 = i * getRandomInt(-255, 255);
					int k1 = i * getRandomInt(-255, 255);
					int l1 = byte0 + (i1 >> 15);
					int i2 = byte1 + (j1 >> 15);
					int j2 = l + (k1 >> 15);
					byte byte2;
					if (l1 > 255)
						byte0 = -1;
					else if (l1 < 0)
						byte0 = 0;
					else
						byte0 = (byte) l1;
					if (i2 > 255)
						byte1 = -1;
					else if (i2 < 0)
						byte1 = 0;
					else
						byte1 = (byte) i2;
					if (j2 > 255)
						byte2 = -1;
					else if (j2 < 0)
						byte2 = 0;
					else
						byte2 = (byte) j2;
					l = byte2;
				}
				image.setPixelColor(j, k, byte0, byte1, l);
				k++;
			}
		}

		return image;
	}

	public float Intensity;
}
//2131296256