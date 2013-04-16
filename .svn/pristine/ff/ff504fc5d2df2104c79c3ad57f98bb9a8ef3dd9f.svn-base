package net.discuz.source.ImageFilter;

public class BrightContrastFilter implements IImageFilter
{

	public BrightContrastFilter()
	{
		BrightnessFactor = 0.01F;
		ContrastFactor = 0.15F;
	}

	public Image process(Image image)
	{
		int i = (int) (255F * BrightnessFactor);
		float f = 1.0F + ContrastFactor;
		int j = 1 + (int) (32768F * (f * f));
		int k = 0;
		int l1;
		int i2;
		int j2;
		int k2;
		int l2;
		int i3;
		do
		{
			if (k >= image.getWidth())
				break;
			int l = 0;
			while (l < image.getHeight())
			{
				int i1 = image.getRComponent(k, l);
				int j1 = image.getGComponent(k, l);
				int k1 = image.getBComponent(k, l);
				if (i != 0)
				{
					i1 += i;
					j1 += i;
					int k3 = k1 + i;

					if (i1 > 255)
						i1 = 255;
					else if (i1 < 0)
						i1 = 0;
					if (j1 > 255)
						j1 = 255;
					else if (j1 < 0)
						j1 = 0;
					if (k3 > 255)
						k3 = 255;
					else if (k3 < 0)
						k3 = 0;
					k1 = k3;
				}
				if (j != 32769)
				{
					l1 = i1 - 128;
					i2 = j1 - 128;
					j2 = k1 - 128;
					k2 = l1 * j >> 15;
					l2 = i2 * j >> 15;
					i3 = j2 * j >> 15;
					i1 = k2 + 128;
					j1 = l2 + 128;
					int j3 = i3 + 128;
					if (i1 > 255)
						i1 = 255;
					else if (i1 < 0)
						i1 = 0;
					if (j1 > 255)
						j1 = 255;
					else if (j1 < 0)
						j1 = 0;
					if (j3 > 255)
						j3 = 255;
					else if (j3 < 0)
						j3 = 0;
					k1 = j3;
				}
				image.setPixelColor(k, l, i1, j1, k1);
				l++;
			}
			k++;
		} while (true);
		return image;
	}

	public float BrightnessFactor;
	public float ContrastFactor;
}
//2131296256