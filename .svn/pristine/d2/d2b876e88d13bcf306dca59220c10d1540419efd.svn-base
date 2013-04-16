package net.discuz.source.ImageFilter;

public class InvertFilter implements IImageFilter
{

	public InvertFilter()
	{}

	public Image process(Image image)
	{
		for (int i = 0; i < image.getWidth(); i++)
		{
			for (int j = 0; j < image.getHeight(); j++)
				image.setPixelColor(i, j, 255 - image.getRComponent(i, j),
						255 - image.getGComponent(i, j),
						255 - image.getBComponent(i, j));

		}

		return image;
	}
}
//2131296256