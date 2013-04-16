package net.discuz.source.ImageFilter;

public class SepiaFilter implements IImageFilter
{

	public SepiaFilter()
	{
		gradientMapFx = new GradientMapFilter(Gradient.BlackSepia());
		gradientMapFx.ContrastFactor = 0.2F;
		gradientMapFx.BrightnessFactor = 0.1F;
		saturationFx = new SaturationModifyFilter();
		saturationFx.SaturationFactor = -0.6F;
	}

	public Image process(Image image)
	{
		Image image1 = gradientMapFx.process(image);
		return saturationFx.process(image1);
	}

	private GradientMapFilter gradientMapFx;
	private SaturationModifyFilter saturationFx;
}
//2131296256