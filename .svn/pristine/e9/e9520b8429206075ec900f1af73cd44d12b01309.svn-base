package net.discuz.source.ImageFilter;

public class BrickFilter implements IImageFilter
{

	public BrickFilter()
	{
		ThreshHold = 128;
	}

	public Image process(Image image)
	{
		int i = image.getWidth();
		int j = image.getHeight();
		try
		{
			Image image1 = (Image) image.clone();
			for (int k = 0; k < i; k++)
			{
				int l = 0;
				while (l < j)
				{
					int i1 = image1.getRComponent(k, l);
					int j1 = image1.getGComponent(k, l);
					char c;
					if ((image1.getBComponent(k, l) + (i1 + j1)) / 3 >= ThreshHold)
						c = '\377';
					else
						c = '\0';
					image.setPixelColor(k, l, c, c, c);
					l++;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return image;
	}

	public int ThreshHold;
}
//2131296256