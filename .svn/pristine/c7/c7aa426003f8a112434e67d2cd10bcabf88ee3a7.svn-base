package net.discuz.source.ImageFilter;

public class BlockPrintFilter implements IImageFilter
{

	public BlockPrintFilter()
	{}

	public Image process(Image image)
	{
		ParamEdgeDetectFilter paramedgedetectfilter = new ParamEdgeDetectFilter();
		paramedgedetectfilter.K00 = 1.0F;
		paramedgedetectfilter.K01 = 2.0F;
		paramedgedetectfilter.K02 = 1.0F;
		paramedgedetectfilter.Threshold = 0.25F;
		paramedgedetectfilter.DoGrayConversion = false;
		ImageBlender imageblender = new ImageBlender();
		try
		{
			imageblender.Mode = ImageBlender.BlendMode.Multiply;
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