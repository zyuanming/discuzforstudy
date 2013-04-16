package net.discuz.source.ImageFilter;

public class AutoAdjustFilter implements IImageFilter
{

	public AutoAdjustFilter()
	{}

	public Image process(Image image)
	{
		HistogramEqualFilter histogramequalfilter = new HistogramEqualFilter();
		histogramequalfilter.ContrastIntensity = 0.5F;
		Image image1 = histogramequalfilter.process(image);
		AutoLevelFilter autolevelfilter = new AutoLevelFilter();
		autolevelfilter.Intensity = 0.5F;
		return autolevelfilter.process(image1);
	}
}
//2131296256