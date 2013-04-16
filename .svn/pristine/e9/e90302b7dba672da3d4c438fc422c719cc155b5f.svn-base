package net.discuz.source.ImageFilter;

public class ThresholdFilter implements IImageFilter
{

	public ThresholdFilter()
	{
		Threshold = 0.5F;
	}

	public Image process(Image image)
	{
		int i = (int) (255F * Threshold);
		for (int j = 0; j < image.getWidth(); j++)
		{
			int k = 0;
			while (k < image.getHeight())
			{
				int l = image.getRComponent(j, k);
				int i1 = image.getGComponent(j, k);
				int j1 = image.getBComponent(j, k);
				char c;
				if (l * 6966 + i1 * 23436 + j1 * 2366 >> 15 > i)
					c = '\377';
				else
					c = '\0';
				image.setPixelColor(j, k, c, c, c);
				k++;
			}
		}

		return image;
	}

	private float Threshold;
}
//2131296256