package net.discuz.source.ImageFilter;

public class MonitorFilter implements IImageFilter
{

	public MonitorFilter()
	{}

	public int getValidInterval(int i)
	{
		if (i < 0)
			i = 0;
		else if (i > 255)
			return 255;
		return i;
	}

	public Image process(Image image)
	{
		label0: for (int i = 0; i < image.getWidth(); i++)
		{
			int j = 0;
			do
			{
				int k = image.getHeight();
				if (j >= k)
					continue label0;
				int l = 0;
				int i1 = 0;
				int j1 = 0;
				for (int k1 = 0; k1 < 3; k1++)
					if (j + k1 < image.getHeight())
					{
						j1 += image.getRComponent(i, j + k1) / 2;
						i1 += image.getGComponent(i, j + k1) / 2;
						l += image.getBComponent(i, j + k1) / 2;
					}

				int l1 = getValidInterval(j1);
				int i2 = getValidInterval(i1);
				int j2 = getValidInterval(l);
				int k2 = 0;
				while (k2 < 3)
				{
					if (j + k2 < image.getHeight())
						if (k2 == 0)
							image.setPixelColor(i, j + k2, l1, 0, 0);
						else if (k2 == 1)
						{
							int i3 = j + k2;
							image.setPixelColor(i, i3, 0, i2, 0);
						} else if (k2 == 2)
						{
							int l2 = j + k2;
							image.setPixelColor(i, l2, 0, 0, j2);
						}
					k2++;
				}
				j += 3;
			} while (true);
		}

		return image;
	}
}
//2131296256