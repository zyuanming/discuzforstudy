package net.discuz.source.ImageFilter;

public class SaturationModifyFilter implements IImageFilter
{

	public SaturationModifyFilter()
	{
		SaturationFactor = 0.5F;
	}

	public Image process(Image image)
	{
		float f = 1.0F + SaturationFactor;
		float f1 = 1.0F - f;
		for (int i = 0; i < image.getWidth(); i++)
		{
			int j = 0;
			while (j < image.getHeight())
			{
				int k = image.getRComponent(i, j);
				int l = image.getGComponent(i, j);
				int i1 = image.getBComponent(i, j);
				float f2 = 0.2126F * f1;
				float f3 = f2 + f;
				float f4 = 0.7152F * f1;
				float f5 = f4 + f;
				float f6 = 0.0722F * f1;
				float f7 = f6 + f;
				float f8 = f3 * (float) k + f4 * (float) l + f6 * (float) i1;
				float f9 = f2 * (float) k + f5 * (float) l + f6 * (float) i1;
				float f10 = f2 * (float) k + f4 * (float) l + f7 * (float) i1;
				int j1;
				int k1;
				int l1;
				if (f8 > 255F)
					j1 = 255;
				else if (f8 < 0.0F)
					j1 = 0;
				else
					j1 = (int) f8;
				if (f9 > 255F)
					k1 = 255;
				else if (f9 < 0.0F)
					k1 = 0;
				else
					k1 = (int) f9;
				if (f10 > 255F)
					l1 = 255;
				else if (f10 < 0.0F)
					l1 = 0;
				else
					l1 = (int) f10;
				image.setPixelColor(i, j, j1, k1, l1);
				j++;
			}
		}

		return image;
	}

	public float SaturationFactor;
}
//2131296256