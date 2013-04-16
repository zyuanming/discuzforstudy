package net.discuz.source.ImageFilter;

public class FocusFilter extends GaussianBlurFilter
{

	public FocusFilter()
	{
		Size = 0.5F;
		Size = 0.5F;
		Sigma = 25F;
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
		float af[];
		int l1;
		if (image.getWidth() > image.getHeight())
			i = (32768 * image.getHeight()) / image.getWidth();
		else
			i = (32768 * image.getWidth()) / image.getHeight();
		j = image.getWidth() >> 1;
		k = image.getHeight() >> 1;
		l = j * j + k * k;
		i1 = (int) ((float) l * (1.0F - Size));
		int _tmp = l - i1;
		j1 = image.getWidth();
		k1 = image.getHeight();
		af = ApplyBlur(ConvertImageWithPadding(image, j1, k1), j1, k1);
		l1 = j1 + 2 * Padding;
		for (int i2 = 0; i2 < k1; i2++)
		{
			int j2 = 3 + l1 * (i2 + 3);
			int k2 = 0;
			while (k2 < j1)
			{
				int l2 = j - k2;
				int i3 = k - i2;
				if (image.getWidth() > image.getHeight())
					i3 = i3 * i >> 14;
				else
					l2 = l2 * i >> 14;
				if (l2 * l2 + i3 * i3 > i1)
				{
					int j3 = 3 * (j2 + k2);
					image.setPixelColor(k2, i2, (byte) (int) (255F * af[j3]),
							(byte) (int) (255F * af[j3 + 1]),
							(byte) (int) (255F * af[j3 + 2]));
				}
				k2++;
			}
		}

		return image;
	}

	public float Size;
}
//2131296256