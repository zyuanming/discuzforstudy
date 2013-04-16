package net.discuz.source.ImageFilter;

public class MosaicFilter implements IImageFilter
{

	public MosaicFilter()
	{
		MosiacSize = 4;
	}

	public Image process(Image image)
	{
		int i = image.getWidth();
		int j = image.getHeight();
		int k = 0;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		for (; k < j; k++)
		{
			int k1 = 0;
			while (k1 < i)
			{
				if (k % MosiacSize == 0)
				{
					if (k1 % MosiacSize == 0)
					{
						j1 = image.getRComponent(k1, k);
						i1 = image.getGComponent(k1, k);
						l = image.getBComponent(k1, k);
					}
					image.setPixelColor(k1, k, j1, i1, l);
				} else
				{
					image.setPixelColor(k1, k, image.getPixelColor(k1, k - 1));
				}
				k1++;
			}
		}

		return image;
	}

	public int MosiacSize;
}
//2131296256