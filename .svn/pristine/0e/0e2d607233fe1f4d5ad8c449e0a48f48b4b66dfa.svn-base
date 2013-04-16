package net.discuz.tools;

import android.os.Handler;
import java.lang.reflect.Method;

public class DelegateFactory
{
	public static class Delegate
	{

		public Object call(Object obj, String s, Object aobj[])
		{
			Object obj1 = "";
			Method amethod[];
			int i;
			amethod = obj.getClass().getMethods();
			i = amethod.length;

			for (int j = 0; j < i; j++)
			{
				Method method = amethod[j];
				if (method.getName().equals(s))
				{
					Class aclass[] = method.getParameterTypes();
					if (aclass.length == aobj.length)
					{
						Boolean boolean1 = Boolean.valueOf(true);
						for (int k = 0; k < aclass.length; k++)
						{
							if (!aclass[k].getName().equals(
									aobj[k].getClass().getName()))
							{
								boolean1 = Boolean.valueOf(false);
							}
						}
						if (boolean1.booleanValue())
						{
							try
							{
								Object obj3 = method.invoke(obj, aobj);
								obj1 = obj3;
							} catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					}
				}
			}
			return obj1;
		}

		public Object handlerPost(final Object instance, final String funcName,
				final Object aobj[])
		{
			if (handler != null)
			{
				handlerPostUIResult = null;
				handler.post(new Runnable()
				{
					public void run()
					{
						handlerPostUIResult = call(instance, funcName, aobj);
					}

				});
			}
			return handlerPostUIResult;
		}

		private Handler handler;
		private Object handlerPostUIResult;

		public Delegate()
		{
			handler = new Handler();
			handlerPostUIResult = null;
		}
	}

	public DelegateFactory()
	{}

	public static Delegate CreateInstance()
	{
		return new Delegate();
	}
}
//2131296256