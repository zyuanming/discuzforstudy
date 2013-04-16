package net.discuz.source.ImageFilter;

public class FilmFilter implements IImageFilter
{

	public FilmFilter(float f)
	{
		gradient = new GradientFilter();
		gradient.Gradient = Gradient.Fade();
		gradient.OriginAngleDegree = f;
		saturationFx = new SaturationModifyFilter();
		saturationFx.SaturationFactor = -0.6F;
	}

	public Image process(Image image)
	{
		Image image1 = null;
		Image image2 = null;
		ImageBlender imageblender = null;
		try
		{
			image1 = (Image) image.clone();
			image2 = gradient.process(image);
			imageblender = new ImageBlender();
			imageblender.Mode = ImageBlender.BlendMode.Multiply;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return saturationFx.process(imageblender.Blend(image1, image2));
	}

	private GradientFilter gradient;
	private SaturationModifyFilter saturationFx;
}
// 2131296256