package net.discuz.source.ImageFilter;

public class RectMatrixFilter extends RadialDistortionFilter
{

	public RectMatrixFilter()
	{
		Oriention = 0;
		BannerNum = 15;
	}

	public Image process(Image image)
	{
		int i = image.getWidth();
		int j = image.getHeight();
		Image image1 = null;
		try
		{
			image1 = (Image) image.clone();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		image1.clearImage(0xffcccccc);
		RadialDistortionFilter.Point apoint[] = new RadialDistortionFilter.Point[BannerNum];
		int k = j / BannerNum;
		for (int l = 0; l < BannerNum; l++)
			apoint[l] = new RadialDistortionFilter.Point(0.0F, l * k);

		for (int i1 = 0; i1 < k; i1++)
		{
			for (int i4 = 0; i4 < BannerNum; i4++)
			{
				for (int j4 = 0; j4 < i; j4++)
				{
					int k4 = j4 + (int) apoint[i4].X;
					int l4 = (int) apoint[i4].Y + (int) ((double) i1 / 1.8D);
					image1.setPixelColor(k4, l4, image.getRComponent(k4, l4),
							image.getGComponent(k4, l4),
							image.getBComponent(k4, l4));
				}

			}

		}

		for (int j1 = 0; j1 < i; j1++)
		{
			for (int l3 = k + (int) apoint[-1 + BannerNum].Y; l3 < j; l3++)
				image1.setPixelColor(j1, l3, image.getRComponent(j1, l3),
						image.getGComponent(j1, l3),
						image.getBComponent(j1, l3));

		}

		RadialDistortionFilter.Point apoint1[] = new RadialDistortionFilter.Point[BannerNum];
		int k1 = i / BannerNum;
		for (int l1 = 0; l1 < BannerNum; l1++)
			apoint1[l1] = new RadialDistortionFilter.Point(l1 * k1, 0.0F);

		for (int i2 = 0; i2 < k1; i2++)
		{
			for (int l2 = 0; l2 < BannerNum; l2++)
			{
				for (int i3 = 0; i3 < j; i3++)
				{
					int j3 = (int) apoint1[l2].X + (int) ((double) i2 / 1.8D);
					int k3 = i3 + (int) apoint1[l2].Y;
					image1.setPixelColor(j3, k3, image.getRComponent(j3, k3),
							image.getGComponent(j3, k3),
							image.getBComponent(j3, k3));
				}

			}

		}

		for (int j2 = 0; j2 < j; j2++)
		{
			for (int k2 = k1 + (int) apoint1[-1 + BannerNum].X; k2 < i; k2++)
				image1.setPixelColor(k2, j2, image.getRComponent(k2, j2),
						image.getGComponent(k2, j2),
						image.getBComponent(k2, j2));

		}

		return image1;
	}

	public int BannerNum;
	public int Oriention;
}
//2131296256