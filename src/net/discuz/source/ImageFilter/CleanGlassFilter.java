package net.discuz.source.ImageFilter;

public class CleanGlassFilter implements IImageFilter
{

	public CleanGlassFilter()
	{
		Size = 0.5F;
		Size = 0.5F;
	}

	public Image process(Image image)
	{
		int i = image.getWidth();
		int j = image.getHeight();
		try
		{
			Image image1 = (Image) image.clone();
			int k;
			int l;
			int i1;
			int j1;
			int k1;
			int l1;
			if (image.getWidth() > image.getHeight())
				k = (32768 * image.getHeight()) / image.getWidth();
			else
				k = (32768 * image.getWidth()) / image.getHeight();
			l = image.getWidth() >> 1;
			i1 = image.getHeight() >> 1;
			j1 = l * l + i1 * i1;
			k1 = (int) ((float) j1 * (1.0F - Size));
			int _tmp = j1 - k1;
			l1 = 0;
			while (l1 < i)
			{
				int i2 = 0;
				while (i2 < j)
				{
					int j2 = l - l1;
					int k2 = i1 - i2;
					int l2;
					int i3;
					int j3;
					int k3;
					if (image.getWidth() > image.getHeight())
						k2 = k2 * k >> 14;
					else
						j2 = j2 * k >> 14;
					if (j2 * j2 + k2 * k2 <= k1)
						continue;
					l2 = NoiseFilter.getRandomInt(1, 0x1e240);
					i3 = l1 + l2 % 19;
					j3 = i2 + l2 % 19;
					if (i3 >= i)
						k3 = i - 1;
					else
						k3 = i3;
					if (j3 >= j)
						j3 = j - 1;
					image.setPixelColor(l1, i2, image1.getRComponent(k3, j3),
							image1.getGComponent(k3, j3),
							image1.getBComponent(k3, j3));
					i2++;
				}
				l1++;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return image;
	}

	public float Size;
}
//2131296256