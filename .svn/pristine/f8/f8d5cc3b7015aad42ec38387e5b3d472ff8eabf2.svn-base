package net.discuz.source.ImageFilter;

public class FeatherFilter implements IImageFilter
{

	public FeatherFilter()
	{
		Size = 0.5F;
	}

	public Image process(Image image)
	{
		int i = image.getWidth();
		int j = image.getHeight();
		int k;
		int l;
		int i1;
		int j1;
		int k1;
		int l1;
		if (i > j)
			k = (32768 * j) / i;
		else
			k = (32768 * i) / j;
		l = i >> 1;
		i1 = j >> 1;
		j1 = l * l + i1 * i1;
		k1 = j1 - (int) ((float) j1 * (1.0F - Size));
		l1 = 0;
		do
		{
			if (l1 >= i)
				break;
			int i2 = 0;
			while (i2 < j)
			{
				int j2 = image.getRComponent(l1, i2);
				int k2 = image.getGComponent(l1, i2);
				int l2 = image.getBComponent(l1, i2);
				int i3 = l - l1;
				int j3 = i1 - i2;
				float f;
				int k3;
				int l3;
				int i4;
				if (i > j)
					i3 = i3 * k >> 15;
				else
					j3 = j3 * k >> 15;
				f = 255F * ((float) (i3 * i3 + j3 * j3) / (float) k1);
				k3 = (int) (f + (float) j2);
				l3 = (int) (f + (float) k2);
				i4 = (int) (f + (float) l2);
				if (k3 > 255)
					k3 = 255;
				else if (k3 < 0)
					k3 = 0;
				if (l3 > 255)
					l3 = 255;
				else if (l3 < 0)
					l3 = 0;
				if (i4 > 255)
					i4 = 255;
				else if (i4 < 0)
					i4 = 0;
				image.setPixelColor(l1, i2, k3, l3, i4);
				i2++;
			}
			l1++;
		} while (true);
		return image;
	}

	public float Size;
}
//2131296256