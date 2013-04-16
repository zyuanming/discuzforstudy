package net.discuz.source.ImageFilter;

import java.util.List;

public class RainBowFilter implements IImageFilter
{

	public RainBowFilter()
	{
		blender = new ImageBlender();
		IsDoubleRainbow = false;
		gradAngleDegree = 40F;
		blender.Mixture = 0.25F;
		blender.Mode = ImageBlender.BlendMode.Additive;
		IsDoubleRainbow = true;
		List list = Gradient.RainBow().MapColors;
		if (IsDoubleRainbow)
		{
			list.remove(-1 + list.size());
			list.addAll(Gradient.RainBow().MapColors);
		}
		gradientFx = new GradientFilter();
		gradientFx.OriginAngleDegree = gradAngleDegree;
		gradientFx.Gradient = new Gradient(list);
	}

	public Image process(Image image)
	{
		Image image1 = null;
		try
		{
			image1 = gradientFx.process((Image) image.clone());
		} catch (CloneNotSupportedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blender.Blend(image, image1);
	}

	public boolean IsDoubleRainbow;
	public ImageBlender blender;
	public float gradAngleDegree;
	private GradientFilter gradientFx;
}
//2131296256