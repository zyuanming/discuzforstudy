package net.discuz.source.ImageFilter;

public class LomoFilter implements IImageFilter
{

	public LomoFilter()
	{
		contrastFx = new BrightContrastFilter();
		gradientMapFx = new GradientMapFilter();
		blender = new ImageBlender();
		vignetteFx = new VignetteFilter();
		noiseFx = new NoiseFilter();
		contrastFx.BrightnessFactor = 0.05F;
		contrastFx.ContrastFactor = 0.5F;
		blender.Mixture = 0.5F;
		blender.Mode = ImageBlender.BlendMode.Multiply;
		vignetteFx.Size = 0.6F;
		noiseFx.Intensity = 0.02F;
	}

	public Image process(Image image)
	{
		Image image1 = contrastFx.process(image);
		Image image2 = noiseFx.process(image1);
		Image image3 = gradientMapFx.process(image2);
		Image image4 = blender.Blend(image3, image2);
		return vignetteFx.process(image4);
	}

	private ImageBlender blender;
	private BrightContrastFilter contrastFx;
	private GradientMapFilter gradientMapFx;
	private NoiseFilter noiseFx;
	private VignetteFilter vignetteFx;
}
//2131296256