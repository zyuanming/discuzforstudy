package net.discuz.source.ImageFilter;

public class ReflectionFilter implements IImageFilter
{

	public ReflectionFilter(boolean flag)
	{
		IsHorizontal = true;
		Offset = 0.5F;
		IsHorizontal = flag;
	}

	public Image process(Image image)
	{
		return null;
	}

	public boolean IsHorizontal;
	public float Offset;
}
// 2131296256