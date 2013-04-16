package net.discuz.source.ImageFilter;

public class GradientMapFilter implements IImageFilter
{

	public GradientMapFilter()
	{
		Map = new Gradient();
	}

	public GradientMapFilter(Gradient gradient)
	{
		Map = gradient;
		ContrastFactor = 0.0F;
		BrightnessFactor = 0.0F;
	}

	public Image process(Image image)
	{
		Palette palette = Map.CreatePalette(256);
		int ai[] = palette.Red;
		int ai1[] = palette.Green;
		int ai2[] = palette.Blue;
		try
		{
			Image image1 = (Image) image.clone();
			image1.clearImage(-1);
			int i = (int) (255F * BrightnessFactor);
			float f = 1.0F + ContrastFactor;
			int j = 1 + (int) (32768F * (f * f));
			int k = 0;
			do
			{
				if (k < image.colorArray.length)
				{
					int l = (0xff0000 & image.colorArray[k]) >>> 16;
					int i1 = (0xff00 & image.colorArray[k]) >>> 8;
					int j1 = 0xff & image.colorArray[k];
					int k1 = l * 6966 + i1 * 23436 + j1 * 2366 >> 15;
					if (i != 0)
					{
						k1 += i;
						if (k1 > 255)
							k1 = 255;
						else if (k1 < 0)
							k1 = 0;
					}
					if (j != 32769)
					{
						k1 = 128 + (j * (k1 - 128) >> 15);
						if (k1 > 255)
							k1 = 255;
						else if (k1 < 0)
							k1 = 0;
					}
					image1.colorArray[k] = 0xff000000 + (ai[k1] << 16)
							+ (ai1[k1] << 8) + ai2[k1];
					k++;
					continue;
				}
				return image1;
			} while (true);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public float BrightnessFactor;
	public float ContrastFactor;
	public Gradient Map;
}
//2131296256