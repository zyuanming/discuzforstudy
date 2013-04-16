package net.discuz.source.ImageFilter;

import java.util.ArrayList;
import java.util.List;

public class NightVisionFilter implements IImageFilter
{

	public NightVisionFilter()
	{
		noisefx = new NoiseFilter();
		blender = new ImageBlender();
		vignetteFx = new VignetteFilter();
		gradientFx = new GradientMapFilter();
		noisefx.Intensity = 0.15F;
		vignetteFx.Size = 1.0F;
		ArrayList arraylist = new ArrayList();
		arraylist.add(Integer.valueOf(0xff000000));
		arraylist.add(Integer.valueOf(0xff00ff00));
		gradientFx.Map = new Gradient(arraylist);
		gradientFx.BrightnessFactor = 0.2F;
	}

	public Image process(Image image)
	{
		Image image1 = noisefx.process(image);
		Image image2 = gradientFx.process(image1);
		return vignetteFx.process(image2);
	}

	private ImageBlender blender;
	private GradientMapFilter gradientFx;
	private NoiseFilter noisefx;
	private VignetteFilter vignetteFx;
}
//2131296256