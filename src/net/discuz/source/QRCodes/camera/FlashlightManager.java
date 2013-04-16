package net.discuz.source.QRCodes.camera;

import android.os.IBinder;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class FlashlightManager
{

	private FlashlightManager()
	{}

	static void disableFlashlight()
	{
		setFlashlight(false);
	}

	static void enableFlashlight()
	{
		setFlashlight(true);
	}

	private static Object getHardwareService()
	{
		Class class1 = maybeForName("android.os.ServiceManager");
		Method method;
		Object obj;
		Class class2;
		Method method1;
		if (class1 != null)
			if ((method = maybeGetMethod(class1, "getService",
					new Class[] { String.class })) != null
					&& (obj = invoke(method, null, new Object[] { "hardware" })) != null
					&& (class2 = maybeForName("android.os.IHardwareService$Stub")) != null
					&& (method1 = maybeGetMethod(class2, "asInterface",
							new Class[] { IBinder.class })) != null)
				return invoke(method1, null, new Object[] { obj });
		return null;
	}

	private static Method getSetFlashEnabledMethod(Object obj)
	{
		if (obj == null)
		{
			return null;
		} else
		{
			Class class1 = obj.getClass();
			Class aclass[] = new Class[1];
			aclass[0] = Boolean.TYPE;
			return maybeGetMethod(class1, "setFlashlightEnabled", aclass);
		}
	}

	private static Object invoke(Method method, Object obj, Object aobj[])
	{
		Object obj1;
		try
		{
			obj1 = method.invoke(obj, aobj);
		} catch (IllegalAccessException illegalaccessexception)
		{
			Log.w(TAG,
					(new StringBuilder())
							.append("Unexpected error while invoking ")
							.append(method).toString(), illegalaccessexception);
			return null;
		} catch (InvocationTargetException invocationtargetexception)
		{
			Log.w(TAG,
					(new StringBuilder())
							.append("Unexpected error while invoking ")
							.append(method).toString(),
					invocationtargetexception.getCause());
			return null;
		} catch (RuntimeException runtimeexception)
		{
			Log.w(TAG,
					(new StringBuilder())
							.append("Unexpected error while invoking ")
							.append(method).toString(), runtimeexception);
			return null;
		}
		return obj1;
	}

	private static Class maybeForName(String s)
	{
		Class class1;
		try
		{
			class1 = Class.forName(s);
		} catch (ClassNotFoundException classnotfoundexception)
		{
			return null;
		} catch (RuntimeException runtimeexception)
		{
			Log.w(TAG,
					(new StringBuilder())
							.append("Unexpected error while finding class ")
							.append(s).toString(), runtimeexception);
			return null;
		}
		return class1;
	}

	private static Method maybeGetMethod(Class class1, String s, Class aclass[])
	{
		Method method;
		try
		{
			method = class1.getMethod(s, aclass);
		} catch (NoSuchMethodException nosuchmethodexception)
		{
			return null;
		} catch (RuntimeException runtimeexception)
		{
			Log.w(TAG,
					(new StringBuilder())
							.append("Unexpected error while finding method ")
							.append(s).toString(), runtimeexception);
			return null;
		}
		return method;
	}

	private static void setFlashlight(boolean flag)
	{
		if (iHardwareService != null)
		{
			Method method = setFlashEnabledMethod;
			Object obj = iHardwareService;
			Object aobj[] = new Object[1];
			aobj[0] = Boolean.valueOf(flag);
			invoke(method, obj, aobj);
		}
	}

	private static final String TAG = FlashlightManager.class.getSimpleName();
	private static final Object iHardwareService;
	private static final Method setFlashEnabledMethod;

	static
	{
		iHardwareService = getHardwareService();
		setFlashEnabledMethod = getSetFlashEnabledMethod(iHardwareService);
		if (iHardwareService == null)
			Log.v(TAG, "This device does supports control of a flashlight");
		else
			Log.v(TAG, "This device does not support control of a flashlight");
	}
}
// 2131296256