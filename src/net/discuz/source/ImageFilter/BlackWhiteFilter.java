package net.discuz.source.ImageFilter;

public class BlackWhiteFilter implements IImageFilter
{

	public BlackWhiteFilter()
	{}

	public Image process(Image image)
	{
		for (int i = 0; i < image.getWidth(); i++)
		{
			for (int j = 0; j < image.getHeight(); j++)
			{
				int k = image.getRComponent(i, j);
				int l = image.getGComponent(i, j);
				int i1 = image.getBComponent(i, j);
				int j1 = (int) (0.29999999999999999D * (double) k
						+ 0.58999999999999997D * (double) i1 + 0.11D * (double) l);
				image.setPixelColor(i, j, j1, j1, j1);
			}

		}

		return image;
	}
}
//2131296256