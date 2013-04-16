package net.discuz.source.ImageFilter;

import java.lang.reflect.Array;

public class AutoLevelFilter implements IImageFilter
{

	public AutoLevelFilter()
	{
		Intensity = 1.0F;
	}

	private static float[] ComputeGamma(int ai[], int ai1[], int ai2[])
	{
		float af[] = new float[3];
		int i = 0;
		do
		{
			while (i < 3)
			{
				if (ai[i] < ai1[i] && ai1[i] < ai2[i])
				{
					double d = Math.log1p((float) (ai1[i] - ai[i])
							/ (float) (ai2[i] - ai[i]));
					float f;
					if (d > 10D)
						f = 10F;
					else if (d < 0.10000000000000001D)
						f = 0.1F;
					else
						f = (float) d;
					af[i] = f;
				} else
				{
					af[i] = 1.0F;
				}
				i++;
			}
			return af;
		} while (true);
	}

	public int[] GetMeanColor(int ai[][])
	{
		float af[] = new float[3];
		int i = 0;
		while (i < 3)
		{
			long l = 0L;
			long l1 = 0L;
			for (int j = 0; j < 256; j++)
			{
				l += j * ai[i][j];
				l1 += ai[i][j];
			}

			float f;
			if (l1 == 0L)
				f = 0.0F;
			else
				f = (float) l / (float) l1;
			af[i] = f;
			i++;
		}
		int ai1[] = new int[3];
		ai1[0] = 0xff & (int) (0.5F + af[0]);
		ai1[1] = 0xff & (int) (0.5F + af[1]);
		ai1[2] = 0xff & (int) (0.5F + af[2]);
		return ai1;
	}

	public int[] GetPercentileColor(int ai[][], float f)
	{
		int ai1[] = new int[3];
		int i = 0;
		label0: do
		{
			if (i < 3)
			{
				long l = 0L;
				long l1 = 0L;
				for (int j = 0; j < 256; j++)
					l1 += ai[i][j];

				int k = 0;
				do
				{
					label1:
					{
						if (k < 256)
						{
							l += ai[i][k];
							if ((float) l <= f * (float) l1)
								break label1;
							ai1[i] = k;
						}
						i++;
						continue label0;
					}
					k++;
				} while (true);
			}
			return ai1;
		} while (true);
	}

	public Image process(Image image)
	{
		int ai[] = { 3, 256 };
		int ai1[][] = (int[][]) Array.newInstance(Integer.TYPE, ai);
		int ai2[] = new int[3];
		int ai3[] = { 255, 255, 255 };
		int ai4[] = new int[256];
		int ai5[] = new int[256];
		int ai6[] = new int[256];
		int i = (int) (255F * Intensity);
		int j = 255 - i;
		for (int k = 0; k < -1 + image.getWidth(); k++)
		{
			for (int i3 = 0; i3 < -1 + image.getHeight(); i3++)
			{
				int ai11[] = ai1[0];
				int j3 = image.getRComponent(k, i3);
				ai11[j3] = 1 + ai11[j3];
				int ai12[] = ai1[1];
				int k3 = image.getGComponent(k, i3);
				ai12[k3] = 1 + ai12[k3];
				int ai13[] = ai1[2];
				int l3 = image.getBComponent(k, i3);
				ai13[l3] = 1 + ai13[l3];
			}

		}

		int ai7[] = GetPercentileColor(ai1, 0.005F);
		int ai8[] = GetMeanColor(ai1);
		int ai9[] = GetPercentileColor(ai1, 0.995F);
		float af[] = ComputeGamma(ai7, ai8, ai9);
		for (int l = 0; l < 3; l++)
		{
			for (int j2 = 0; j2 < 256; j2++)
			{
				int ai10[] = new int[3];
				int k2 = 0;
				while (k2 < 3)
				{
					float f = j2 - ai7[k2];
					if (f < 0.0F)
						ai10[k2] = ai2[k2];
					else if (f + (float) ai7[k2] >= (float) ai9[k2])
					{
						ai10[k2] = ai3[k2];
					} else
					{
						double d = (double) ai2[k2]
								+ (double) (ai3[k2] - ai2[k2])
								* Math.pow(f / (float) (ai9[k2] - ai7[k2]),
										af[k2]);
						int l2;
						if (d > 255D)
							l2 = 255;
						else if (d < 0.0D)
							l2 = 0;
						else
							l2 = (int) d;
						ai10[k2] = l2;
					}
					k2++;
				}
				ai6[j2] = ai10[0];
				ai5[j2] = ai10[1];
				ai4[j2] = ai10[2];
			}

		}

		try
		{
			Image image1 = (Image) image.clone();
			for (int i1 = 0; i1 < -1 + image.getWidth(); i1++)
			{
				for (int j1 = 0; j1 < -1 + image.getHeight(); j1++)
				{
					int k1 = image1.getRComponent(i1, j1);
					int l1 = image1.getGComponent(i1, j1);
					int i2 = image1.getBComponent(i1, j1);
					image.setPixelColor(i1, j1, k1 * j + i * ai6[k1] >> 8, l1
							* j + i * ai5[l1] >> 8, i2 * j + i * ai4[i2] >> 8);
				}

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return image;
	}

	public float Intensity;
}
//2131296256