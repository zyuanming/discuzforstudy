package net.discuz.source.ImageFilter;

import java.util.ArrayList;
import java.util.List;

public class XRadiationFilter implements IImageFilter
{

	public XRadiationFilter()
	{
		gradientMapFx = new GradientMapFilter();
		blender = new ImageBlender();
		ArrayList arraylist = new ArrayList();
		arraylist.add(Integer.valueOf(Gradient.TintColors.LightCyan()));
		arraylist.add(Integer.valueOf(0xff000000));
		gradientMapFx.Map = new Gradient(arraylist);
		blender.Mode = ImageBlender.BlendMode.ColorBurn;
		blender.Mixture = 0.8F;
	}

	public Image process(Image image)
	{
		Image image1 = gradientMapFx.process(image);
		return blender.Blend(image1, image1);
	}

	private ImageBlender blender;
	private GradientMapFilter gradientMapFx;
}
//2131296256