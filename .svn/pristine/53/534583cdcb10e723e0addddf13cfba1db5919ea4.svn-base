package net.discuz.source.ImageFilter;

public class ZoomBlurFilter implements IImageFilter
{

	public ZoomBlurFilter(int i)
	{
		this(i, 0.0D, 0.0D);
	}

	public ZoomBlurFilter(int i, double d, double d1)
	{
		super();
		double d2 = 2D;
		RADIUS_LENGTH = 64;
		if (i < 1)
			i = 1;
		m_length = i;
		if (d > d2)
			d = d2;
		else if (d < -2D)
			d = 0.0D;
		m_offset_x = d;
		if (d1 <= d2)
			if (d1 < -2D)
				d2 = 0.0D;
			else
				d2 = d1;
		m_offset_y = d2;
	}

	public Image process(Image image)
	{
		int i = image.getWidth();
		int j = image.getHeight();
		m_fcx = (int) (32768D * ((double) i * m_offset_x)) + 32768 * i;
		m_fcy = (int) (32768D * ((double) j * m_offset_y)) + 32768 * j;
		try
		{
			Image image1 = (Image) image.clone();
			int k = 0;
			while (k < i)
			{
				int l = 0;
				while (l < j)
				{
					int i1 = 255 * image1.getRComponent(k, l);
					int j1 = 255 * image1.getGComponent(k, l);
					int k1 = 255 * image1.getBComponent(k, l);
					int l1 = 0x10000 * k - m_fcx;
					int i2 = 0x10000 * l - m_fcy;
					int j2 = 255;
					int k2 = k1;
					int l2 = i2;
					int i3 = i1;
					int j3 = l1;
					int k3 = j1;
					int l3 = 0;
					while (l3 < 64)
					{
						int i4 = j3 - ((j3 / 16) * m_length) / 1024;
						int j4 = l2 - ((l2 / 16) * m_length) / 1024;
						int k4 = (32768 + (i4 + m_fcx)) / 0x10000;
						int l4 = (32768 + (j4 + m_fcy)) / 0x10000;
						int j5;
						int k5;
						int l5;
						if (k4 >= 0 && k4 < i && l4 >= 0 && l4 < j)
						{
							int i6 = i3 + 255 * image1.getRComponent(k4, l4);
							k3 += 255 * image1.getGComponent(k4, l4);
							k5 = k2 + 255 * image1.getBComponent(k4, l4);
							j2 += 255;
							j5 = i6;
						} else
						{
							int i5 = k2;
							j5 = i3;
							k5 = i5;
						}
						l3++;
						l2 = j4;
						j3 = i4;
						l5 = k5;
						i3 = j5;
						k2 = l5;
					}
					image.setPixelColor(k, l, Image.SAFECOLOR(i3 / j2),
							Image.SAFECOLOR(k3 / j2), Image.SAFECOLOR(k2 / j2));
					l++;
				}
				k++;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return image;
	}

	final int RADIUS_LENGTH;
	int m_fcx;
	int m_fcy;
	int m_length;
	double m_offset_x;
	double m_offset_y;
}
//2131296256