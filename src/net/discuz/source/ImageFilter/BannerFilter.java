package net.discuz.source.ImageFilter;

public class BannerFilter extends RadialDistortionFilter
{

	public BannerFilter(int i, boolean flag)
	{
		IsHorizontal = true;
		BannerNum = 10;
		BannerNum = i;
		IsHorizontal = flag;
	}

	public Image process(Image image)
	{
		int i = image.getWidth();
		int j = image.getHeight();
		Image image1 = null;
		try
		{
			image1 = (Image) image.clone();
			image1.clearImage(0xffcccccc);
			RadialDistortionFilter.Point apoint[] = new RadialDistortionFilter.Point[BannerNum];
			if (IsHorizontal)
			{
				int l2 = j / BannerNum;
				for (int i3 = 0; i3 < BannerNum; i3++)
					apoint[i3] = new RadialDistortionFilter.Point(0.0F, i3 * l2);

				for (int j3 = 0; j3 < l2; j3++)
				{
					for (int i4 = 0; i4 < BannerNum; i4++)
					{
						for (int j4 = 0; j4 < i; j4++)
						{
							int k4 = j4 + (int) apoint[i4].X;
							int l4 = (int) apoint[i4].Y
									+ (int) ((double) j3 / 1.1000000000000001D);
							image1.setPixelColor(k4, l4,
									image.getRComponent(k4, l4),
									image.getGComponent(k4, l4),
									image.getBComponent(k4, l4));
						}

					}

				}

				for (int k3 = 0; k3 < i; k3++)
				{
					for (int l3 = l2 + (int) apoint[-1 + BannerNum].Y; l3 < j; l3++)
						image1.setPixelColor(k3, l3,
								image.getRComponent(k3, l3),
								image.getGComponent(k3, l3),
								image.getBComponent(k3, l3));

				}

			} else
			{
				int k = i / BannerNum;
				for (int l = 0; l < BannerNum; l++)
					apoint[l] = new RadialDistortionFilter.Point(l * k, 0.0F);

				for (int i1 = 0; i1 < k; i1++)
				{
					for (int l1 = 0; l1 < BannerNum; l1++)
					{
						for (int i2 = 0; i2 < j; i2++)
						{
							int j2 = (int) apoint[l1].X
									+ (int) ((double) i1 / 1.1000000000000001D);
							int k2 = i2 + (int) apoint[l1].Y;
							image1.setPixelColor(j2, k2,
									image.getRComponent(j2, k2),
									image.getGComponent(j2, k2),
									image.getBComponent(j2, k2));
						}

					}

				}

				for (int j1 = 0; j1 < j; j1++)
				{
					for (int k1 = k + (int) apoint[-1 + BannerNum].X; k1 < i; k1++)
						image1.setPixelColor(k1, j1,
								image.getRComponent(k1, j1),
								image.getGComponent(k1, j1),
								image.getBComponent(k1, j1));

				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return image1;
	}

	public int BannerNum;
	public boolean IsHorizontal;
}
//2131296256