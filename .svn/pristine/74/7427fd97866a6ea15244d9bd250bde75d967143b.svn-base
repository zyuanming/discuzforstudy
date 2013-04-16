package net.discuz.source.ImageFilter;

public class PixelateFilter implements IImageFilter
{

	public PixelateFilter()
	{
		pixelSize = 4;
	}

	private void fillRect(Image image, int i, int j, int k, int l)
	{
		for (int i1 = i; i1 < i + k; i1++)
		{
			for (int j1 = j; j1 < j + k; j1++)
				if (i1 < image.getWidth() && j1 < image.getHeight())
					image.setPixelColor(i1, j1, l);

		}

	}

	private int getPredominantRGB(Image image, int i, int j, int k)
	{
		int l = i;
		int i1 = -1;
		int j1 = -1;
		int k1 = -1;
		for (; l < i + k; l++)
		{
			int l1 = j;
			while (l1 < j + k)
			{
				if (l < image.getWidth() && l1 < image.getHeight())
				{
					if (k1 == -1)
						k1 = image.getRComponent(l, l1);
					else
						k1 = (k1 + image.getRComponent(l, l1)) / 2;
					if (j1 == -1)
						j1 = image.getGComponent(l, l1);
					else
						j1 = (j1 + image.getGComponent(l, l1)) / 2;
					if (i1 == -1)
						i1 = image.getBComponent(l, l1);
					else
						i1 = (i1 + image.getBComponent(l, l1)) / 2;
				}
				l1++;
			}
		}

		return i1 + (0xff000000 + (k1 << 16) + (j1 << 8));
	}

	public int getPixelSize()
	{
		return pixelSize;
	}

	public Image process(Image image)
	{
		for (int i = 0; i < image.getWidth(); i += pixelSize)
		{
			for (int j = 0; j < image.getHeight(); j += pixelSize)
			{
				int k = getPredominantRGB(image, i, j, pixelSize);
				fillRect(image, i, j, pixelSize, k);
			}

		}

		return image;
	}

	public void setPixelSize(int i)
	{
		pixelSize = i;
	}

	private int pixelSize;
}
//2131296256