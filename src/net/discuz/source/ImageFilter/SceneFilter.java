package net.discuz.source.ImageFilter;

public class SceneFilter implements IImageFilter
{

	public SceneFilter(float f, Gradient gradient)
	{
		gradientFx = new GradientFilter();
		gradientFx.Gradient = gradient;
		gradientFx.OriginAngleDegree = f;
		saturationFx = new SaturationModifyFilter();
		saturationFx.SaturationFactor = -0.6F;
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
		Image image2 = gradientFx.process(image);
		ImageBlender imageblender = new ImageBlender();
		imageblender.Mode = ImageBlender.BlendMode.Subractive;
		return saturationFx.process(imageblender.Blend(image1, image2));
	}

	private GradientFilter gradientFx;
	private SaturationModifyFilter saturationFx;
}
// 2131296256