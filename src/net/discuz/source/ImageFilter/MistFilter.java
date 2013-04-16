package net.discuz.source.ImageFilter;

public class MistFilter implements IImageFilter
{

	public MistFilter()
	{}

	public Image process(Image image)
	{
		int i = image.getWidth();
		int j = image.getHeight();
		try
		{
			Image image1 = (Image) image.clone();
			int k = 0;
			while (k < i)
			{
				int l = 0;
				while (l < j)
				{
					int i1 = NoiseFilter.getRandomInt(1, 0x1e240);
					int j1 = k + i1 % 19;
					int k1 = l + i1 % 19;
					int l1;
					if (j1 >= i)
						l1 = i - 1;
					else
						l1 = j1;
					if (k1 >= j)
						k1 = j - 1;
					image.setPixelColor(k, l, image1.getRComponent(l1, k1),
							image1.getGComponent(l1, k1),
							image1.getBComponent(l1, k1));
					l++;
				}
				k++;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return image;
	}
}
//2131296256