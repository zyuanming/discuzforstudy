package a.a.a;

public class s
{

	public s()
	{
		c = "1 5 42 35 2 43 2 38 0 5 42 42 42 40 37 39 3 44 0 35 4 39 43 42 44 38 37 38 42 38 41 39";
	}

	public String as()
	{
		StringBuffer stringbuffer = new StringBuffer();
		String as1[] = c.split(" ");
		int i = 0;
		while (i < as1.length)
		{
			try
			{
				stringbuffer.append(z.b[Integer.valueOf(as1[i]).intValue()]);
			} catch (Exception exception)
			{
				stringbuffer.append(z.b[-10
						+ Integer.valueOf(as1[i]).intValue()]);
			}
			i++;
		}
		return stringbuffer.toString();
	}

	char b[] = { 'a', 'c', 'b', 'd', 'e', 'f', 'g', 'h', 'q', 'k', 'l', 'm',
			'n', 'o', 'p', 'j', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	String c;
}
//2131296256