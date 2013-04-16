package net.discuz.source.ImageFilter;

public class ComicFilter implements IImageFilter
{

	public ComicFilter()
	{
		saturationFx = new SaturationModifyFilter();
		blurFx = new GaussianBlurFilter();
		blender = new ImageBlender();
		edgeDetectionFx = new ParamEdgeDetectFilter();
		edgeBlender = new ImageBlender();
		saturationFx.SaturationFactor = 1.0F;
		blurFx.Sigma = 1.0F;
		blender.Mixture = 1.0F;
		blender.Mode = ImageBlender.BlendMode.Lighten;
		edgeDetectionFx.Threshold = 0.25F;
		edgeDetectionFx.DoGrayConversion = true;
		edgeBlender.Mixture = 0.8F;
		edgeBlender.Mode = ImageBlender.BlendMode.Lighten;
	}

	public Image process(Image image)
	{
		Image image3 = null;
		Image image4 = null;
		try
		{
			Image image1 = saturationFx.process((Image) image.clone());
			Image image2 = blurFx.process(image1);
			image3 = blender.Blend(image1, image2);
			image4 = edgeDetectionFx.process((Image) image3.clone());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return edgeBlender.Blend(image3, image4);
	}

	ImageBlender blender;
	GaussianBlurFilter blurFx;
	ImageBlender edgeBlender;
	ParamEdgeDetectFilter edgeDetectionFx;
	SaturationModifyFilter saturationFx;
}
// 2131296256