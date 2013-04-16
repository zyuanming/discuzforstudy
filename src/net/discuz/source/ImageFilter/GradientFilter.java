package net.discuz.source.ImageFilter;

import android.util.FloatMath;

public class GradientFilter implements IImageFilter
{

	public GradientFilter()
	{
		palette = null;
		OriginAngleDegree = 0.0F;
		Gradient = new Gradient();
	}

	public void ClearCache()
	{
		palette = null;
	}

	public Image process(Image image)
	{
		int i = image.getWidth();
		int j = image.getHeight();
		float f = 0.01745329F * OriginAngleDegree;
		float f1 = FloatMath.cos(f);
		float f2 = FloatMath.sin(f);
		float f3 = f1 * (float) i + f2 * (float) j;
		float f4 = f1 * f3;
		float f5 = f3 * f2;
		int k = Math.max(Math.max((int) FloatMath.sqrt(f4 * f4 + f5 * f5), i),
				j);
		if (palette == null || k != palette.Length)
			palette = Gradient.CreatePalette(k);
		int ai[] = palette.Red;
		int ai1[] = palette.Green;
		int ai2[] = palette.Blue;
		for (int l = 0; l < j; l++)
		{
			for (int i1 = 0; i1 < i; i1++)
			{
				float f6 = f1 * (float) i1 + f2 * (float) l;
				float f7 = f1 * f6;
				float f8 = f6 * f2;
				int j1 = (int) FloatMath.sqrt(f7 * f7 + f8 * f8);
				image.setPixelColor(i1, l, ai[j1], ai1[j1], ai2[j1]);
			}

		}

		return image;
	}

	public Gradient Gradient;
	public float OriginAngleDegree;
	private Palette palette;
}
//2131296256