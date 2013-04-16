package net.discuz.source.ImageFilter;

public class VintageFilter implements IImageFilter
{

	public VintageFilter()
	{
	}

	public Image process(Image image)
	{
		GradientMapFilter gradientmapfilter = new GradientMapFilter(
				Gradient.BlackSepia());
		gradientmapfilter.ContrastFactor = 0.15F;
		ImageBlender imageblender = new ImageBlender();
		imageblender.Mixture = 0.7F;
		try
		{
			imageblender.Mode = ImageBlender.BlendMode.Overlay;
			Image image1 = imageblender.Blend((Image) image.clone(),
					gradientmapfilter.process(image));
			VignetteFilter vignettefilter = new VignetteFilter();
			vignettefilter.Size = 0.7F;
			return vignettefilter.process(image1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
// 2131296256