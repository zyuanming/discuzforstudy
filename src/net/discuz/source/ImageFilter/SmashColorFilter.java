package net.discuz.source.ImageFilter;

public class SmashColorFilter implements IImageFilter
{

	public SmashColorFilter()
	{
	}

	public Image process(Image image)
	{
		ParamEdgeDetectFilter paramedgedetectfilter = new ParamEdgeDetectFilter();
		paramedgedetectfilter.K00 = 1.0F;
		paramedgedetectfilter.K01 = 2.0F;
		paramedgedetectfilter.K02 = 1.0F;
		paramedgedetectfilter.Threshold = 0.25F;
		paramedgedetectfilter.DoGrayConversion = false;
		paramedgedetectfilter.DoInversion = false;
		ImageBlender imageblender = new ImageBlender();
		imageblender.Mode = ImageBlender.BlendMode.LinearLight;
		imageblender.Mixture = 2.5F;
		try
		{
			return imageblender.Blend((Image) image.clone(),
					paramedgedetectfilter.process(image));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
// 2131296256