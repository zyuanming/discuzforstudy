package net.discuz.source.ImageFilter;

public class PaintBorderFilter implements IImageFilter
{

	public PaintBorderFilter(int i)
	{
		Size = 1.0F;
		R = (0xff0000 & i) >> 16;
		G = (0xff00 & i) >> 8;
		B = i & 0xff;
	}

	public PaintBorderFilter(int i, float f)
	{
		this(i);
		Size = f;
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
		Image image1 = null;
		int l1;
		if (i > j)
			k = (32768 * j) / i;
		else
			k = (32768 * i) / j;
		l = i >> 1;
		i1 = j >> 1;
		j1 = l * l + i1 * i1;
		k1 = j1 - (int) ((float) j1 * (1.0F - Size));
		try
		{
			image1 = (Image) image.clone();
			l1 = 0;
			do
			{
				if (l1 >= i)
					break;
				int i2 = 0;
				while (i2 < j)
				{
					int j2 = l - l1;
					int k2 = i1 - i2;
					int l2;
					int i3;
					int j3;
					int k3;
					if (i > j)
						j2 = j2 * k >> 15;
					else
						k2 = k2 * k >> 15;
					l2 = j2 * j2 + k2 * k2;
					i3 = (int) (((float) l2 / (float) k1) * (float) R);
					j3 = (int) (((float) l2 / (float) k1) * (float) G);
					k3 = (int) (((float) l2 / (float) k1) * (float) B);
					if (i3 > R)
						i3 = R;
					else if (i3 < 0)
						i3 = 0;
					if (j3 > G)
						j3 = G;
					else if (j3 < 0)
						j3 = 0;
					if (k3 > B)
						k3 = B;
					else if (k3 < 0)
						k3 = 0;
					image.setPixelColor(l1, i2, i3, j3, k3);
					i2++;
				}
				l1++;
			} while (true);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		ImageBlender imageblender = new ImageBlender();
		imageblender.Mode = ImageBlender.BlendMode.Additive;
		return imageblender.Blend(image1, image);
	}

	public int B;
	public int G;
	public int R;
	public float Size;
}
//2131296256