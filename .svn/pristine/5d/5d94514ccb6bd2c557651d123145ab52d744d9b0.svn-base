package net.discuz.source.ImageFilter;

import android.util.FloatMath;

public class RadialDistortionFilter implements IImageFilter
{
	public class Point
	{

		public float X;
		public float Y;

		public Point(float f, float f1)
		{
			super();
			X = f;
			Y = f1;
		}
	}

	public RadialDistortionFilter()
	{
		Radius = 0.5F;
		Distortion = 1.5F;
		Center = new Point(0.5F, 0.5F);
	}

	public Image process(Image image)
	{
		int i = image.getWidth();
		int j = image.getHeight();
		int k = (int) ((float) i * Center.X);
		int l = (int) ((float) j * Center.Y);
		float f = (float) Math.min(i, j) * Radius;
		try
		{
			Image image1 = (Image) image.clone();

			int i1 = 0;
			do
			{
				if (i1 >= i)
					break;
				int j1 = 0;
				while (j1 < j)
				{
					float f1 = 1.0F
							- FloatMath.sqrt((i1 - k) * (i1 - k) + (j1 - l)
									* (j1 - l)) / f;
					int k1;
					int l1;
					int i2;
					if (f1 > 0.0F)
					{
						float f2 = 1.0F - f1 * (f1 * Distortion);
						float f3 = f2 * (float) (i1 - k) + (float) k;
						float f4 = f2 * (float) (j1 - l) + (float) l;
						int j2 = (int) f3;
						float f5 = f3 - (float) j2;
						int k2;
						int l2;
						float f6;
						int i3;
						int j3;
						int k3;
						int l3;
						int i4;
						int j4;
						int k4;
						int l4;
						int i5;
						int j5;
						int k5;
						int l5;
						int i6;
						if (f5 > 0.0F)
							k2 = j2 + 1;
						else
							k2 = j2;
						l2 = (int) f4;
						f6 = f4 - (float) l2;
						if (f6 > 0.0F)
							i3 = l2 + 1;
						else
							i3 = l2;
						if (j2 < 0)
							j2 = 0;
						else if (j2 >= i)
							j2 = i - 1;
						if (k2 < 0)
							k2 = 0;
						else if (k2 >= i)
							k2 = i - 1;
						if (l2 < 0)
							l2 = 0;
						else if (l2 >= j)
							l2 = j - 1;
						if (i3 < 0)
							i3 = 0;
						else if (i3 >= j)
							i3 = j - 1;
						j3 = image1.getRComponent(j2, l2);
						k3 = image1.getGComponent(j2, l2);
						l3 = image1.getBComponent(j2, l2);
						i4 = image1.getRComponent(k2, l2);
						j4 = image1.getGComponent(k2, l2);
						k4 = image1.getBComponent(k2, l2);
						l4 = image1.getRComponent(k2, i3);
						i5 = image1.getGComponent(k2, i3);
						j5 = image1.getBComponent(k2, i3);
						k5 = image1.getRComponent(j2, i3);
						l5 = image1.getGComponent(j2, i3);
						i6 = image1.getBComponent(j2, i3);
						k1 = (int) ((float) j3 * (1.0F - f6) * (1.0F - f5) + f5
								* ((float) i4 * (1.0F - f6)) + f5
								* (f6 * (float) l4) + f6 * (float) k5
								* (1.0F - f5));
						l1 = (int) ((float) k3 * (1.0F - f6) * (1.0F - f5) + f5
								* ((float) j4 * (1.0F - f6)) + f5
								* (f6 * (float) i5) + f6 * (float) l5
								* (1.0F - f5));
						i2 = (int) ((float) l3 * (1.0F - f6) * (1.0F - f5) + f5
								* ((float) k4 * (1.0F - f6)) + f5
								* (f6 * (float) j5) + f6 * (float) i6
								* (1.0F - f5));
					} else
					{
						k1 = image1.getRComponent(i1, j1);
						l1 = image1.getGComponent(i1, j1);
						i2 = image1.getBComponent(i1, j1);
					}
					image.setPixelColor(i1, j1, k1, l1, i2);
					j1++;
				}
				i1++;
			} while (true);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return image;
	}

	public Point Center;
	public float Distortion;
	public float Radius;
}
//2131296256