package net.discuz.source.ImageFilter;

public class ImageBlender
{
	public static class BlendMode
	{

		public static int Additive = 1;
		public static int ColorBurn = 6;
		public static int ColorDodge = 5;
		public static int Darken = 8;
		public static int Frame = 12;
		public static int Glow = 10;
		public static int Lighten = 7;
		public static int LinearLight = 11;
		public static int Multiply = 3;
		public static int Normal = 0;
		public static int Overlay = 4;
		public static int Reflect = 9;
		public static int Subractive = 2;

		public BlendMode()
		{}
	}

	public ImageBlender()
	{
		Mixture = 0.9F;
		Mode = BlendMode.Multiply;
	}

	public Image Blend(Image image, Image image1)
	{
		int i;
		int j;
		int k;
		i = (int) (255F * Mixture);
		j = 255 - i;
		k = 0;
		int i1;
		int j1;
		int k1;
		int l1;
		int i2;
		int j2;
		for (int l = 0; l < image.getHeight(); l++)
		{

			i1 = image.getRComponent(k, l);
			j1 = image.getGComponent(k, l);
			k1 = image.getBComponent(k, l);
			l1 = image1.getRComponent(k, l);
			i2 = image1.getGComponent(k, l);
			j2 = image1.getBComponent(k, l);
			switch (Mode)
			{
			case 1:
				l1 += i1;
				i2 += j1;
				int j15 = k1 + j2;
				if (l1 > 255)
					l1 = 255;
				if (i2 > 255)
					i2 = 255;
				if (j15 > 255)
					j15 = 255;
				j2 = j15;
				break;
			case 2:
				int j14 = i1 + l1;
				int k14 = i2 + j1;
				int l14 = j2 + k1;
				int i15;
				if (j14 < 255)
					l1 = 0;
				else
					l1 = j14 - 255;
				if (k14 < 255)
					i2 = 0;
				else
					i2 = k14 - 255;
				if (l14 < 255)
					i15 = 0;
				else
					i15 = l14 - 255;
				j2 = i15;
				break;
			case 3:
				l1 = (i1 * l1) / 255;
				i2 = (j1 * i2) / 255;
				j2 = (k1 * j2) / 255;
				break;
			case 4:
				int i14;
				if (l1 < 128)
					l1 = (l1 * (i1 * 2)) / 255;
				else
					l1 = 255 - (2 * (255 - i1) * (255 - l1)) / 255;
				if (i2 < 128)
					i2 = (i2 * (j1 * 2)) / 255;
				else
					i2 = 255 - (2 * (255 - j1) * (255 - i2)) / 255;
				if (j2 < 128)
					i14 = (j2 * (k1 * 2)) / 255;
				else
					i14 = 255 - (2 * (255 - k1) * (255 - j2)) / 255;
				j2 = i14;
				break;
			case 5:
				int l11 = i1 << 8;
				int i12;
				int j12;
				int k12;
				int l12;
				int i13;
				int j13;
				int k13;
				int l13;
				if (l1 != 255)
					i12 = l1;
				else
					i12 = 254;
				j12 = l11 / (255 - i12);
				k12 = j1 << 8;
				if (i2 != 255)
					l12 = i2;
				else
					l12 = 254;
				i13 = k12 / (255 - l12);
				j13 = k1 << 8;
				if (j2 != 255)
					k13 = j2;
				else
					k13 = 254;
				l13 = j13 / (255 - k13);
				if (l1 != 255)
					if (j12 < 255)
						l1 = j12;
					else
						l1 = 255;
				if (i2 != 255)
					if (i13 < 255)
						i2 = i13;
					else
						i2 = 255;
				if (j2 != 255)
					if (l13 < 255)
						j2 = l13;
					else
						j2 = 255;
				break;
			case 6:
				int k9 = 255 - i1 << 8;
				int l9;
				int i10;
				int j10;
				int k10;
				int l10;
				int i11;
				int j11;
				int k11;
				if (l1 != 0)
					l9 = l1;
				else
					l9 = 1;
				i10 = 255 - k9 / l9;
				j10 = 255 - j1 << 8;
				if (i2 != 0)
					k10 = i2;
				else
					k10 = 1;
				l10 = 255 - j10 / k10;
				i11 = 255 - k1 << 8;
				if (j2 != 0)
					j11 = j2;
				else
					j11 = 1;
				k11 = 255 - i11 / j11;
				if (l1 != 0)
					if (i10 > 0)
						l1 = i10;
					else
						l1 = 0;
				if (i2 != 0)
					if (l10 > 0)
						i2 = l10;
					else
						i2 = 0;
				if (j2 != 0)
					if (k11 > 0)
						j2 = k11;
					else
						j2 = 0;
				break;
			case 7:
				if (l1 <= i1)
					l1 = i1;
				if (i2 <= j1)
					i2 = j1;
				if (j2 <= k1)
					j2 = k1;
				break;
			case 8:
				if (l1 > i1)
					l1 = i1;
				if (i2 > j1)
					i2 = j1;
				if (j2 > k1)
					j2 = k1;
				break;
			case 9:
				int j7 = i1 * i1;
				int k7;
				int l7;
				int i8;
				int j8;
				int k8;
				int l8;
				int i9;
				int j9;
				if (l1 != 255)
					k7 = l1;
				else
					k7 = 254;
				l7 = j7 / (255 - k7);
				i8 = j1 * j1;
				if (i2 != 255)
					j8 = i2;
				else
					j8 = 254;
				k8 = i8 / (255 - j8);
				l8 = k1 * k1;
				if (j2 != 255)
					i9 = j2;
				else
					i9 = 254;
				j9 = l8 / (255 - i9);
				if (l1 != 255)
					if (l7 < 255)
						l1 = l7;
					else
						l1 = 255;
				if (i2 != 255)
					if (k8 < 255)
						i2 = k8;
					else
						i2 = 255;
				if (j2 != 255)
					if (j9 < 255)
						j2 = j9;
					else
						j2 = 255;
				break;
			case 10:
				int k5 = l1 * l1;
				int l5;
				int i6;
				int j6;
				int k6;
				int l6;
				int i7;
				if (i1 != 255)
					l5 = i1;
				else
					l5 = 254;
				l1 = k5 / (255 - l5);
				i6 = i2 * i2;
				if (j1 != 255)
					j6 = j1;
				else
					j6 = 254;
				i2 = i6 / (255 - j6);
				k6 = j2 * j2;
				if (k1 != 255)
					l6 = k1;
				else
					l6 = 254;
				i7 = k6 / (255 - l6);
				if (i1 == 255)
					l1 = i1;
				else if (l1 >= 255)
					l1 = 255;
				if (j1 == 255)
					i2 = j1;
				else if (i2 >= 255)
					i2 = 255;
				if (k1 == 255)
					i7 = k1;
				else if (i7 >= 255)
					i7 = 255;
				j2 = i7;
				break;
			case 11:
				if (l1 < 128)
				{
					int i5 = i1 + l1 * 2;
					int j5;
					if (i5 < 255)
						j5 = 0;
					else
						j5 = i5 - 255;
					l1 = j5;
				} else
				{
					int j3 = i1 + 2 * (l1 - 128);
					if (j3 > 255)
						j3 = 255;
					l1 = j3;
				}
				if (i2 < 128)
				{
					int k4 = j1 + i2 * 2;
					int l4;
					if (k4 < 255)
						l4 = 0;
					else
						l4 = k4 - 255;
					i2 = l4;
				} else
				{
					int k3 = j1 + 2 * (i2 - 128);
					if (k3 > 255)
						k3 = 255;
					i2 = k3;
				}
				if (j2 < 128)
				{
					int i4 = k1 + j2 * 2;
					int j4;
					if (i4 < 255)
						j4 = 0;
					else
						j4 = i4 - 255;
					j2 = j4;
				} else
				{
					int l3 = k1 + 2 * (j2 - 128);
					if (l3 > 255)
						l3 = 255;
					j2 = l3;
				}
				break;
			case 12:
				if (l1 == 0 && i2 == 0 && l1 == 0)
				{
					j2 = k1;
					i2 = j1;
					l1 = i1;
				}
			default:
			}

			int k2 = i1 * j + l1 * i;
			int l2 = j1 * j + i2 * i;
			int i3 = k1 * j + j2 * i;
			image.setPixelColor(k, l, k2 >> 8, l2 >> 8, i3 >> 8);
		}
		return image;
	}

	public float Mixture;
	public int Mode;
}
// 2131296256