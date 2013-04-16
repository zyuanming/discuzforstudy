package net.discuz.source.ImageFilter;

public class GaussianBlurFilter implements IImageFilter
{

	public GaussianBlurFilter()
	{
		Sigma = 0.75F;
	}

	float[] ApplyBlur(float af[], int i, int j)
	{
		float af1[] = new float[af.length];
		System.arraycopy(af, 0, af1, 0, af.length);
		int k = i + 2 * Padding;
		int l = j + 2 * Padding;
		float f = Sigma;
		float f1 = f * f;
		float f2 = f1 * f;
		float f3 = 1.57825F + 2.44413F * f + 1.4281F * f1 + 0.422205F * f2;
		float f4 = f * 2.44413F + 2.85619F * f1 + 1.26661F * f2;
		float f5 = -(1.4281F * f1 + 1.26661F * f2);
		float f6 = f2 * 0.422205F;
		float f7 = 1.0F - (f6 + (f4 + f5)) / f3;
		ApplyPass(af1, k, l, f3, f4, f5, f6, f7);
		float af2[] = new float[af1.length];
		Transpose(af1, af2, k, l);
		ApplyPass(af2, l, k, f3, f4, f5, f6, f7);
		Transpose(af2, af1, l, k);
		return af1;
	}

	void ApplyPass(float af[], int i, int j, float f, float f1, float f2,
			float f3, float f4)
	{
		float f5 = 1.0F / f;
		int k = i * 3;
		for (int l = 0; l < j; l++)
		{
			int i1 = l * k;
			for (int j1 = i1 + 9; j1 < i1 + k; j1 += 3)
			{
				af[j1] = f4 * af[j1] + f5
						* (f1 * af[j1 - 3] + f2 * af[j1 - 6] + f3 * af[j1 - 9]);
				af[j1 + 1] = f4
						* af[j1 + 1]
						+ f5
						* (f1 * af[-3 + (j1 + 1)] + f2 * af[-6 + (j1 + 1)] + f3
								* af[-9 + (j1 + 1)]);
				af[j1 + 2] = f4
						* af[j1 + 2]
						+ f5
						* (f1 * af[-3 + (j1 + 2)] + f2 * af[-6 + (j1 + 2)] + f3
								* af[-9 + (j1 + 2)]);
			}

			for (int k1 = -3 + (-9 + (i1 + k)); k1 >= i1; k1 -= 3)
			{
				af[k1] = f4 * af[k1] + f5
						* (f1 * af[k1 + 3] + f2 * af[k1 + 6] + f3 * af[k1 + 9]);
				af[k1 + 1] = f4
						* af[k1 + 1]
						+ f5
						* (f1 * af[3 + (k1 + 1)] + f2 * af[6 + (k1 + 1)] + f3
								* af[9 + (k1 + 1)]);
				af[k1 + 2] = f4
						* af[k1 + 2]
						+ f5
						* (f1 * af[3 + (k1 + 2)] + f2 * af[6 + (k1 + 2)] + f3
								* af[9 + (k1 + 2)]);
			}

		}

	}

	float[] ConvertImageWithPadding(Image image, int i, int j)
	{
		int k = j + 2 * Padding;
		int l = i + 2 * Padding;
		float af[] = new float[3 * (k * l)];
		int i1 = 0;
		int j1 = -3;
		int k1 = 0;
		int l2;
		while (k1 < k)
		{
			int l1;
			int i2;
			int j2;
			int k2;
			if (j1 < 0)
				l1 = 0;
			else if (j1 >= j)
				l1 = j - 1;
			else
				l1 = j1;
			i2 = -1 * Padding;
			j2 = i1;
			k2 = 0;
			while (k2 < l)
			{
				int i3;
				int j3;
				if (i2 < 0)
					i3 = 0;
				else if (i2 >= i)
					i3 = i - 1;
				else
					i3 = i2;
				af[j2] = 0.003921569F * (float) image.getRComponent(i3, l1);
				af[j2 + 1] = 0.003921569F * (float) image.getGComponent(i3, l1);
				af[j2 + 2] = 0.003921569F * (float) image.getBComponent(i3, l1);
				j3 = k2 + 1;
				i2++;
				j2 += 3;
				k2 = j3;
			}
			l2 = k1 + 1;
			j1++;
			k1 = l2;
			i1 = j2;
		}
		return af;
	}

	void Transpose(float af[], float af1[], int i, int j)
	{
		for (int k = 0; k < j; k++)
		{
			for (int l = 0; l < i; l++)
			{
				int i1 = 3 * (l * j) + k * 3;
				int j1 = 3 * (k * i) + l * 3;
				af1[i1] = af[j1];
				af1[i1 + 1] = af[j1 + 1];
				af1[i1 + 2] = af[j1 + 2];
			}

		}

	}

	public Image process(Image image)
	{
		int i = image.getWidth();
		int j = image.getHeight();
		float af[] = ApplyBlur(ConvertImageWithPadding(image, i, j), i, j);
		int k = i + 2 * Padding;
		for (int l = 0; l < j; l++)
		{
			int i1 = 3 + k * (l + 3);
			for (int j1 = 0; j1 < i; j1++)
			{
				int k1 = 3 * (i1 + j1);
				image.setPixelColor(j1, l, (byte) (int) (255F * af[k1]),
						(byte) (int) (255F * af[k1 + 1]),
						(byte) (int) (255F * af[k1 + 2]));
			}

		}

		return image;
	}

	protected static int Padding = 3;
	public float Sigma;

}
//2131296256