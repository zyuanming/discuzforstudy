package net.discuz.source.ImageFilter;

public class TintFilter implements IImageFilter
{

	public TintFilter()
	{}

	public Image process(Image image)
	{
		for (int i = 0; i < image.getWidth(); i++)
		{
			for (int j = 0; j < image.getHeight(); j++)
			{
				int k = 255 - image.getRComponent(i, j);
				int l = 255 - image.getGComponent(i, j);
				int i1 = 255 - image.getBComponent(i, j);
				int j1 = k * 6966 + l * 23436 + i1 * 2366 >> 15;
				image.setPixelColor(i, j, (byte) (j1 * 0xffff0000 >> 8),
						(byte) (j1 * 0xff00ff00 >> 8),
						(byte) (j1 * 0xff0000ff >> 8));
			}

		}

		return image;
	}
}
//2131296256