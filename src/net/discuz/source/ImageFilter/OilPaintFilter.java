package net.discuz.source.ImageFilter;

public class OilPaintFilter implements IImageFilter
{

	public OilPaintFilter()
	{
		Model = 3;
	}

	public Image process(Image image)
	{
		int i = image.getWidth();
		int j = image.getHeight();
		try
		{
			Image image1 = (Image) image.clone();
			for (int k = 0; k < i; k++)
			{
				int l = 0;
				while (l < j)
				{
					int i1 = NoiseFilter.getRandomInt(1, 10000) % Model;
					int j1;
					int k1;
					if (k + i1 < i)
						j1 = k + i1;
					else if (k - i1 >= 0)
						j1 = k - i1;
					else
						j1 = k;
					if (l + i1 < j)
						k1 = l + i1;
					else if (l - i1 >= 0)
						k1 = l - i1;
					else
						k1 = l;
					image.setPixelColor(k, l, image1.getRComponent(j1, k1),
							image1.getGComponent(j1, k1),
							image1.getBComponent(j1, k1));
					l++;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return image;
	}

	public int Model;
}
//2131296256