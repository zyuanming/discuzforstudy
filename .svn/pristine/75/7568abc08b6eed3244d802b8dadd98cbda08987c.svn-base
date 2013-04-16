package net.discuz.source.ImageFilter;

public class VignetteFilter implements IImageFilter
{

	public VignetteFilter()
	{
		Size = 0.5F;
		Size = 0.5F;
	}

	public Image process(Image image)
	{
		int i;
		int j;
		int k;
		int l;
		int i1;
		int j1;
		int k1;
		if (image.getWidth() > image.getHeight())
			i = (32768 * image.getHeight()) / image.getWidth();
		else
			i = (32768 * image.getWidth()) / image.getHeight();
		j = image.getWidth() >> 1;
		k = image.getHeight() >> 1;
		l = j * j + k * k;
		i1 = (int) ((float) l * (1.0F - Size));
		j1 = l - i1;
		k1 = 0;
		do
		{
			if (k1 >= image.getWidth())
				break;
			int l1 = 0;
			while (l1 < image.getHeight())
			{
				byte byte0 = (byte) image.getRComponent(k1, l1);
				byte byte1 = (byte) image.getGComponent(k1, l1);
				byte byte2 = (byte) image.getBComponent(k1, l1);
				int i2 = j - k1;
				int j2 = k - l1;
				int k2;
				if (image.getWidth() > image.getHeight())
					i2 = i2 * i >> 15;
				else
					j2 = j2 * i >> 15;
				k2 = i2 * i2 + j2 * j2;
				if (k2 > i1)
				{
					int l2 = (l - k2 << 8) / j1;
					int i3 = l2 * l2;
					int j3 = byte0 * i3 >> 16;
					int k3 = byte1 * i3 >> 16;
					int l3 = i3 * byte2 >> 16;
					if (j3 > 255)
						j3 = 255;
					else if (j3 < 0)
						j3 = 0;
					byte0 = (byte) j3;
					if (k3 > 255)
						k3 = 255;
					else if (k3 < 0)
						k3 = 0;
					byte1 = (byte) k3;
					if (l3 > 255)
						l3 = 255;
					else if (l3 < 0)
						l3 = 0;
					byte2 = (byte) l3;
				}
				image.setPixelColor(k1, l1, byte0, byte1, byte2);
				l1++;
			}
			k1++;
		} while (true);
		return image;
	}

	public float Size;
}
//2131296256