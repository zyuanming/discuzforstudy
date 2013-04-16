package net.discuz.source.ImageFilter;

public class BigBrotherFilter implements IImageFilter
{

	public BigBrotherFilter()
	{}

	private void drawTone(int i, int j, Image image)
	{
		int k = 0;
		while (k < 100)
		{
			int l = k % 10;
			int i1 = k / 10;
			if (i + l < image.getWidth() && j + i1 < image.getHeight())
				if (255 - image.getRComponent(i + l, j + i1) > arrDither[k])
					image.setPixelColor(i + l, i1 + j, 0, 0, 0);
				else
					image.setPixelColor(i + l, j + i1, 255, 255, 255);
			k++;
		}
	}

	public Image process(Image image)
	{
		for (int i = 0; i < image.getWidth(); i += 10)
		{
			for (int j = 0; j < image.getHeight(); j += 10)
				drawTone(i, j, image);

		}

		return image;
	}

	private static final int DOT_AREA = 10;
	private static final int arrDither[] = { 167, 200, 230, 216, 181, 94, 72,
			193, 242, 232, 36, 52, 222, 167, 200, 181, 126, 210, 94, 72, 232,
			153, 111, 36, 52, 167, 200, 230, 216, 181, 94, 72, 193, 242, 232,
			36, 52, 222, 167, 200, 181, 126, 210, 94, 72, 232, 153, 111, 36,
			52, 167, 200, 230, 216, 181, 94, 72, 193, 242, 232, 36, 52, 222,
			167, 200, 181, 126, 210, 94, 72, 232, 153, 111, 36, 52, 167, 200,
			230, 216, 181, 94, 72, 193, 242, 232, 36, 52, 222, 167, 200, 181,
			126, 210, 94, 72, 232, 153, 111, 36, 52 };

}
//2131296256