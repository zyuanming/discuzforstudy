package net.discuz.source.ImageFilter;

public class SoftGlowFilter implements IImageFilter
{

	public SoftGlowFilter(int i, float f, float f1)
	{
		contrastFx = new BrightContrastFilter();
		gaussianBlurFx = new GaussianBlurFilter();
		contrastFx.BrightnessFactor = f;
		contrastFx.ContrastFactor = f1;
		gaussianBlurFx.Sigma = i;
	}

	public Image process(Image image)
	{
		Image image1 = null;
		try
		{
			image1 = (Image) image.clone();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		Image image2 = gaussianBlurFx.process(image);
		Image image3 = contrastFx.process(image2);
		for (int i = 0; i < -1 + image3.getWidth(); i++)
		{
			for (int j = 0; j < -1 + image3.getHeight(); j++)
			{
				int k = image1.getRComponent(i, j);
				int l = image1.getGComponent(i, j);
				int i1 = image1.getBComponent(i, j);
				image3.setPixelColor(
						i,
						j,
						255 - ((255 - k) * (255 - image3.getRComponent(i, j))) / 255,
						255 - ((255 - l) * (255 - image3.getGComponent(i, j))) / 255,
						255 - ((255 - i1) * (255 - image3.getBComponent(i, j))) / 255);
			}

		}

		return image3;
	}

	BrightContrastFilter contrastFx;
	GaussianBlurFilter gaussianBlurFx;
}
//2131296256