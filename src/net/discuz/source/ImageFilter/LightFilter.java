package net.discuz.source.ImageFilter;

public class LightFilter extends RadialDistortionFilter
{

	public LightFilter()
	{
		Light = 150F;
	}

	public Image process(Image image)
	{
		int i = image.getWidth();
		int j = i / 2;
		int k = image.getHeight();
		int l = k / 2;
		int i1 = Math.min(j, l);
		RadialDistortionFilter.Point point = new RadialDistortionFilter.Point(
				j, l);
		for (int j1 = 0; j1 < i; j1++)
		{
			for (int k1 = 0; k1 < k; k1++)
			{
				float f = (float) Math.sqrt(Math.pow((float) j1 - point.X, 2D)
						+ Math.pow((float) k1 - point.Y, 2D));
				int l1 = image.getRComponent(j1, k1);
				int i2 = image.getGComponent(j1, k1);
				int j2 = image.getBComponent(j1, k1);
				if (f < (float) i1)
				{
					float f1 = Light * (1.0F - f / (float) i1);
					l1 = Math.max(0, Math.min(l1 + (int) f1, 255));
					i2 = Math.max(0, Math.min(i2 + (int) f1, 255));
					j2 = Math.max(0, Math.min(j2 + (int) f1, 255));
				}
				image.setPixelColor(j1, k1, l1, i2, j2);
			}

		}

		return image;
	}

	public float Light;
}
//2131296256