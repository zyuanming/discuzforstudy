package net.discuz.source.ImageFilter;

import android.graphics.Color;
import java.util.ArrayList;
import java.util.List;

public class Gradient
{
	public static class TintColors
	{

		public static int LightCyan()
		{
			return 0xffebf5e1;
		}

		public static int Sepia()
		{
			return 0xffb3b3e6;
		}

		public TintColors()
		{}
	}

	public Gradient()
	{
		ArrayList arraylist = new ArrayList();
		arraylist.add(Integer.valueOf(0xff000000));
		arraylist.add(Integer.valueOf(-1));
		MapColors = arraylist;
	}

	public Gradient(List list)
	{
		MapColors = list;
	}

	public static Gradient BlackSepia()
	{
		ArrayList arraylist = new ArrayList();
		arraylist.add(Integer.valueOf(0xff000000));
		arraylist.add(Integer.valueOf(TintColors.Sepia()));
		return new Gradient(arraylist);
	}

	private Palette CreateGradient(List list, int i)
	{
		if (list == null || list.size() < 2)
			return null;
		Palette palette = new Palette(i);
		int ai[] = palette.Red;
		int ai1[] = palette.Green;
		int ai2[] = palette.Blue;
		int j = i / (-1 + list.size());
		float f = 1.0F / (float) j;
		int k = ((Integer) list.get(0)).intValue();
		int l = (0xff0000 & k) >> 16;
		int i1 = (0xff00 & k) >> 8;
		int j1 = k & 0xff;
		int k1 = l;
		int l1 = 0;
		int i2 = i1;
		int j2 = j1;
		int k2 = 1;
		do
		{
			if (k2 >= list.size())
				break;
			int l2 = (0xff0000 & ((Integer) list.get(k2)).intValue()) >> 16;
			int i3 = (0xff00 & ((Integer) list.get(k2)).intValue()) >> 8;
			int j3 = 0xff & ((Integer) list.get(k2)).intValue();
			int k3 = 0;
			while (k3 < j)
			{
				float f1 = f * (float) k3;
				int l3 = k1 + (int) (f1 * (float) (l2 - k1));
				int i4 = i2 + (int) (f1 * (float) (i3 - i2));
				int j4 = j2 + (int) (f1 * (float) (j3 - j2));
				int k4;
				int l4;
				if (l3 > 255)
					l3 = 255;
				else if (l3 < 0)
					l3 = 0;
				ai[l1] = l3;
				if (i4 > 255)
					k4 = 255;
				else if (i4 < 0)
					k4 = 0;
				else
					k4 = i4;
				ai1[l1] = k4;
				if (j4 > 255)
					l4 = 255;
				else if (j4 < 0)
					l4 = 0;
				else
					l4 = j4;
				ai2[l1] = l4;
				l1++;
				k3++;
			}
			k2++;
			j2 = j3;
			i2 = i3;
			k1 = l2;
		} while (true);
		if (l1 < i)
		{
			ai[l1] = ai[l1 - 1];
			ai1[l1] = ai1[l1 - 1];
			ai2[l1] = ai2[l1 - 1];
		}
		return palette;
	}

	public static Gradient Fade()
	{
		ArrayList arraylist = new ArrayList();
		arraylist.add(Integer.valueOf(0xff000000));
		arraylist.add(Integer.valueOf(Color.rgb(205, 232, 238)));
		arraylist.add(Integer.valueOf(0xff000000));
		return new Gradient(arraylist);
	}

	public static Gradient Inverse()
	{
		ArrayList arraylist = new ArrayList();
		arraylist.add(Integer.valueOf(-1));
		arraylist.add(Integer.valueOf(0xff000000));
		return new Gradient(arraylist);
	}

	public static Gradient RainBow()
	{
		ArrayList arraylist = new ArrayList();
		arraylist.add(Integer.valueOf(0xffff0000));
		arraylist.add(Integer.valueOf(-65281));
		arraylist.add(Integer.valueOf(0xff0000ff));
		arraylist.add(Integer.valueOf(0xff00ffff));
		arraylist.add(Integer.valueOf(0xff00ff00));
		arraylist.add(Integer.valueOf(-256));
		arraylist.add(Integer.valueOf(0xffff0000));
		return new Gradient(arraylist);
	}

	public static Gradient Scene()
	{
		ArrayList arraylist = new ArrayList();
		arraylist.add(Integer.valueOf(Color.rgb(0, 215, 255)));
		arraylist.add(Integer.valueOf(-1));
		arraylist.add(Integer.valueOf(Color.rgb(0, 215, 255)));
		return new Gradient(arraylist);
	}

	public static Gradient Scene1()
	{
		ArrayList arraylist = new ArrayList();
		arraylist.add(Integer.valueOf(Color.rgb(237, 149, 100)));
		arraylist.add(Integer.valueOf(-1));
		arraylist.add(Integer.valueOf(Color.rgb(237, 149, 100)));
		return new Gradient(arraylist);
	}

	public static Gradient Scene2()
	{
		ArrayList arraylist = new ArrayList();
		arraylist.add(Integer.valueOf(Color.rgb(255, 191, 0)));
		arraylist.add(Integer.valueOf(-1));
		arraylist.add(Integer.valueOf(Color.rgb(255, 191, 0)));
		return new Gradient(arraylist);
	}

	public static Gradient Scene3()
	{
		ArrayList arraylist = new ArrayList();
		arraylist.add(Integer.valueOf(Color.rgb(0, 165, 255)));
		arraylist.add(Integer.valueOf(-1));
		arraylist.add(Integer.valueOf(Color.rgb(0, 165, 255)));
		return new Gradient(arraylist);
	}

	public static Gradient WhiteSepia()
	{
		ArrayList arraylist = new ArrayList();
		arraylist.add(Integer.valueOf(-1));
		arraylist.add(Integer.valueOf(TintColors.Sepia()));
		return new Gradient(arraylist);
	}

	public Palette CreatePalette(int i)
	{
		return CreateGradient(MapColors, i);
	}

	public List MapColors;
}
//2131296256