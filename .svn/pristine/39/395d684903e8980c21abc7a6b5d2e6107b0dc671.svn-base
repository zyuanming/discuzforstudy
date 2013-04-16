package net.discuz.source.ImageFilter;

public class ParamEdgeDetectFilter extends ConvolutionFilter
{

	public ParamEdgeDetectFilter()
	{
		DoGrayConversion = true;
		DoInversion = true;
		Threshold = 0.25F;
		K00 = 1.0F;
		K01 = 2.0F;
		K02 = 1.0F;
	}

	private Image ProcessColor(int i, int j, int k, int l, int i1, int j1,
			Image image, int k1)
	{
		int l1 = image.getWidth();
		int i2 = image.getHeight();
		Image image1;
		try
		{
			image1 = (Image) image.clone();
			int j2 = 0;
			while (j2 < l1)
			{
				int k2 = 0;
				while (k2 < i2)
				{
					int l2 = GetPixelColor(image1, j2 - 1, k2 - 1, l1, i2);
					int i3 = k2 - 1;
					int j3 = GetPixelColor(image1, j2, i3, l1, i2);
					int k3 = GetPixelColor(image1, j2 + 1, k2 - 1, l1, i2);
					int l3 = GetPixelColor(image1, j2 - 1, k2, l1, i2);
					int i4 = GetPixelColor(image1, j2 + 1, k2, l1, i2);
					int j4 = GetPixelColor(image1, j2 - 1, k2 + 1, l1, i2);
					int k4 = k2 + 1;
					int l4 = GetPixelColor(image1, j2, k4, l1, i2);
					int i5 = GetPixelColor(image1, j2 + 1, k2 + 1, l1, i2);
					int j5 = (0xff0000 & l2) >> 16;
					int k5 = (0xff0000 & k3) >> 16;
					int l5 = (0xff0000 & j4) >> 16;
					int i6 = (0xff0000 & i5) >> 16;
					int j6 = j5 * i + k5 * k + j * ((0xff0000 & j3) >> 16) + l5
							* l + i1 * ((0xff0000 & l4) >> 16) + i6 * j1 >> 8;
					int k6 = j5 * i + k5 * l + j * ((0xff0000 & l3) >> 16) + l5
							* k + i1 * ((0xff0000 & i4) >> 16) + i6 * j1 >> 8;
					int l6;
					int i7;
					int j7;
					int k7;
					int l7;
					int i8;
					int j8;
					int k8;
					int l8;
					int i9;
					int j9;
					int k9;
					int l9;
					int i10;
					int j10;
					int k10;
					int l10;
					int i11;
					if (j6 * j6 + k6 * k6 > k1)
						l6 = 0;
					else
						l6 = 255;
					if (DoInversion)
						i7 = 255 - l6;
					else
						i7 = l6;
					j7 = (0xff00 & l2) >> 8;
					k7 = (0xff00 & k3) >> 8;
					l7 = (0xff00 & j4) >> 8;
					i8 = (0xff00 & i5) >> 8;
					j8 = j7 * i + k7 * k + j * ((0xff00 & j3) >> 8) + l7 * l
							+ i1 * ((0xff00 & l4) >> 8) + i8 * j1 >> 8;
					k8 = j7 * i + k7 * l + j * ((0xff00 & l3) >> 8) + l7 * k
							+ i1 * ((0xff00 & i4) >> 8) + i8 * j1 >> 8;
					if (j8 * j8 + k8 * k8 > k1)
						l8 = 0;
					else
						l8 = 255;
					if (DoInversion)
						i9 = 255 - l8;
					else
						i9 = l8;
					j9 = l2 & 0xff;
					k9 = k3 & 0xff;
					l9 = j4 & 0xff;
					i10 = i5 & 0xff;
					j10 = j9 * i + k9 * k + j * (j3 & 0xff) + l9 * l + i1
							* (l4 & 0xff) + i10 * j1 >> 8;
					k10 = j9 * i + k9 * l + j * (l3 & 0xff) + l9 * k + i1
							* (i4 & 0xff) + i10 * j1 >> 8;
					if (j10 * j10 + k10 * k10 > k1)
						l10 = 0;
					else
						l10 = 255;
					if (DoInversion)
						i11 = 255 - l10;
					else
						i11 = l10;
					image.setPixelColor(j2, k2, i7, i9, i11);
					k2++;
				}
				j2++;
			}
		} catch (CloneNotSupportedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}

	private Image ProcessGray(int i, int j, int k, int l, int i1, int j1,
			Image image, int k1)
	{
		int l1 = image.getWidth();
		int i2 = image.getHeight();
		try
		{
			Image image1 = (Image) image.clone();
			int j2 = 0;
			while (j2 < l1)
			{
				int k2 = 0;
				while (k2 < i2)
				{
					int l2 = GetPixelBrightness(image1, j2 - 1, k2 - 1, l1, i2);
					int i3 = k2 - 1;
					int j3 = GetPixelBrightness(image1, j2, i3, l1, i2);
					int k3 = GetPixelBrightness(image1, j2 + 1, k2 - 1, l1, i2);
					int l3 = GetPixelBrightness(image1, j2 - 1, k2, l1, i2);
					int i4 = GetPixelBrightness(image1, j2 + 1, k2, l1, i2);
					int j4 = GetPixelBrightness(image1, j2 - 1, k2 + 1, l1, i2);
					int k4 = k2 + 1;
					int l4 = GetPixelBrightness(image1, j2, k4, l1, i2);
					int i5 = GetPixelBrightness(image1, j2 + 1, k2 + 1, l1, i2);
					int j5 = l2 * i + j3 * j + k3 * k + j4 * l + l4 * i1 + i5
							* j1 >> 8;
					int k5 = l2 * i + k3 * l + l3 * j + i4 * i1 + j4 * k + i5
							* j1 >> 8;
					int l5;
					int i6;
					if (j5 * j5 + k5 * k5 > k1)
						l5 = 0;
					else
						l5 = 255;
					if (DoInversion)
						i6 = 255 - l5;
					else
						i6 = l5;
					image.setPixelColor(j2, k2, i6, i6, i6);
					k2++;
				}
				j2++;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return image;
	}

	public Image process(Image image)
	{
		int i = (int) (255F * K00);
		int j = (int) (255F * K01);
		int k = (int) (255F * K02);
		int l = (int) (2.0F * (255F * Threshold));
		int i1 = l * l;
		if (!DoGrayConversion)
			try
			{
				return ProcessColor(i, j, k, -i, -j, -k, (Image) image.clone(),
						i1);
			} catch (CloneNotSupportedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		else
			return ProcessGray(i, j, k, -i, -j, -k, image, i1);
	}

	public boolean DoGrayConversion;
	public boolean DoInversion;
	public float K00;
	public float K01;
	public float K02;
	public float Threshold;
}
//2131296256