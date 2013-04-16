package net.discuz.source.ImageFilter;

public class ColorQuantizeFilter implements IImageFilter
{

	public ColorQuantizeFilter()
	{
		levels = 5F;
	}

	public Image process(Image image)
	{
		for (int i = 0; i < image.getWidth(); i++)
		{
			int j = 0;
			while (j < image.getHeight())
			{
				int k = image.getRComponent(i, j);
				int l = image.getGComponent(i, j);
				int i1 = image.getBComponent(i, j);
				float f = 255F * ((float) (int) (0.003921569F * (float) k * levels) / levels);
				float f1 = 255F * ((float) (int) (0.003921569F * (float) l * levels) / levels);
				float f2 = 255F * ((float) (int) (0.003921569F * (float) i1 * levels) / levels);
				int j1;
				int k1;
				int l1;
				if (f > 255F)
					j1 = 255;
				else if (f < 0.0F)
					j1 = 0;
				else
					j1 = (int) f;
				if (f1 > 255F)
					k1 = 255;
				else if (f1 < 0.0F)
					k1 = 0;
				else
					k1 = (int) f1;
				if (f2 > 255F)
					l1 = 255;
				else if (f2 < 0.0F)
					l1 = 0;
				else
					l1 = (int) f2;
				image.setPixelColor(i, j, j1, k1, l1);
				j++;
			}
		}

		return image;
	}

	private float levels;
}
//2131296256