package net.discuz.source.ImageFilter;

public class OldPhotoFilter implements IImageFilter
{

	public OldPhotoFilter()
	{
		blurFx = new GaussianBlurFilter();
		blurFx.Sigma = 0.3F;
		noiseFx = new NoiseFilter();
		noiseFx.Intensity = 0.03F;
		vignetteFx = new VignetteFilter();
		vignetteFx.Size = 0.6F;
		gradientFx = new GradientMapFilter();
		gradientFx.ContrastFactor = 0.3F;
	}

	public Image process(Image image)
	{
		Image image1 = gradientFx.process(image);
		return vignetteFx.process(image1);
	}

	private GaussianBlurFilter blurFx;
	private GradientMapFilter gradientFx;
	private NoiseFilter noiseFx;
	private VignetteFilter vignetteFx;
}
//2131296256