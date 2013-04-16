package net.discuz.tools;

import android.os.AsyncTask;
import java.lang.reflect.Method;
import net.discuz.source.DEBUG;

public class AsyncTaskDelegateFactory
{
	public static class AsyncTaskProxy extends AsyncTask
	{

		protected Object doInBackground(Object aobj[])
		{
			return doInBackground((Void[]) aobj);
		}

		protected Object doInBackground(Void avoid[])
		{
			int i = 0;
			Method amethod[];
			int j;
			try
			{
				amethod = up.Instance.getClass().getMethods();
				j = amethod.length;

				for (int k = 0; k < j; k++)
				{
					Method method = amethod[k];
					if (method.getName().equals(up.FuncName))
					{
						Class aclass[] = method.getParameterTypes();
						if (aclass.length == up.Params.length)
						{
							Boolean boolean1 = Boolean.valueOf(true);
							for (; i < aclass.length; i++)
							{
								if (!aclass[i].getName().equals(
										up.Params[i].getClass().getName()))
								{
									boolean1 = Boolean.valueOf(false);
								}
							}
							if (boolean1.booleanValue())
								up.FuncResult = method.invoke(up.Instance,
										up.Params);
						}

					}
				}
			} catch (Exception e)
			{
				DEBUG.o((new StringBuilder())
						.append("=====asyncTaskDelegateError====")
						.append(e.getMessage()).toString());
			}

			return up.FuncResult;
		}

		protected void onPostExecute(Object obj)
		{
			try
			{
				if (!up.CallFuncName.equals(""))
				{
					up.CallFuncResult = DelegateFactory.CreateInstance().call(
							up.Instance, up.CallFuncName, new Object[] { obj });
				}
			} catch (Exception e)
			{
				DEBUG.o((new StringBuilder())
						.append("=====asyncTaskDelegateError====")
						.append(e.getMessage()).toString());
			}
		}

		private ProcessUnit up;

		public AsyncTaskProxy(ProcessUnit processunit)
		{
			up = null;
			up = processunit;
		}
	}

	public static class ProcessUnit
	{

		public void Execute()
		{
			(new AsyncTaskProxy(this)).execute(new Void[0]);
		}

		public String CallFuncName;
		public Object CallFuncResult;
		public String FuncName;
		public Object FuncResult;
		public Object Instance;
		public Object Params[];

		public ProcessUnit(Object obj, String s, Object aobj[], String s1)
		{
			Instance = null;
			FuncName = "";
			FuncResult = null;
			Params = null;
			CallFuncName = "";
			CallFuncResult = null;
			Instance = obj;
			FuncName = s;
			Params = aobj;
			CallFuncName = s1;
		}
	}

	public AsyncTaskDelegateFactory()
	{}

	public static ProcessUnit Create(Object obj, String s, Object aobj[],
			String s1)
	{
		return new ProcessUnit(obj, s, aobj, s1);
	}
}
//2131296256