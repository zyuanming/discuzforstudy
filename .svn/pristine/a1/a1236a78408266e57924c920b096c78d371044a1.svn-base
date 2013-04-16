package net.discuz.source.ImageFilter;

public class ReliefFilter implements IImageFilter
{

	public ReliefFilter()
	{}

	public Image process(Image image)
	{
		for (int i = 0; i < -1 + image.getWidth(); i++)
		{
			for (int j = 0; j < image.getHeight(); j++)
			{
				int k = 128 + (image.getRComponent(i, j) - image.getRComponent(
						i + 1, j));
				int l = 128 + (image.getGComponent(i, j) - image.getGComponent(
						i + 1, j));
				int i1 = 128 + (image.getBComponent(i, j) - image
						.getBComponent(i + 1, j));
				if (k > 255)
					k = 255;
				if (k < 0)
					k = 0;
				if (l > 255)
					l = 255;
				if (l < 0)
					l = 0;
				if (i1 > 255)
					i1 = 255;
				if (i1 < 0)
					i1 = 0;
				image.setPixelColor(i, j, k, l, i1);
			}

		}

		return image;
	}
}
//2131296256