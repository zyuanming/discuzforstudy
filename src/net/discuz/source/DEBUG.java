package net.discuz.source;

import android.util.Log;
import java.io.PrintStream;

public class DEBUG
{

	public DEBUG()
	{}

	public static void d(Object obj)
	{
		if (!showDebug.booleanValue())
		{
			return;
		} else
		{
			Log.d("Discuz DEBUG(D)",
					(new StringBuilder()).append("").append(obj).toString());
			return;
		}
	}

	public static void e(Object obj)
	{
		if (!showDebug.booleanValue())
		{
			return;
		} else
		{
			Log.v("Discuz DEBUG(V)",
					(new StringBuilder()).append("").append(obj).toString());
			return;
		}
	}

	public static void i(Object obj)
	{
		if (!showDebug.booleanValue())
		{
			return;
		} else
		{
			Log.i("Discuz DEBUG(I):",
					(new StringBuilder()).append("").append(obj).toString());
			return;
		}
	}

	public static void o(Object obj)
	{
		if (!showDebug.booleanValue())
		{
			return;
		} else
		{
			System.out.println(obj);
			return;
		}
	}

	private static Boolean showDebug = Boolean.valueOf(true);

}
//2131296256