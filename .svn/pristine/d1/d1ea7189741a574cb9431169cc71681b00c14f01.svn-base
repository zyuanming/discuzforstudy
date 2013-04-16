package a.a.a;

public class z
{

	public z()
	{}

	public String byte2hex(byte abyte0[])
	{
		String s = "";
		int i = 0;
		while (i < abyte0.length)
		{
			String s1 = Integer.toHexString(0xff & abyte0[i]);
			if (s1.length() == 1)
				s = (new StringBuilder()).append(s).append("0").append(s1)
						.toString();
			else
				s = (new StringBuilder()).append(s).append(s1).toString();
			i++;
		}
		return s;
	}

	public byte[] hex2byte(String s)
	{
		if (s != null)
		{
			String s1 = s.trim();
			int i = s1.length();
			if (i != 0 && i % 2 != 1)
			{
				byte abyte0[] = new byte[i / 2];
				for (int j = 0; j < s1.length(); j += 2)
					abyte0[j / 2] = (byte) Integer.decode(
							(new StringBuilder()).append("0x")
									.append(s1.substring(j, j + 2)).toString())
							.intValue();

				return abyte0;
			}
		}
		return null;
	}

	public String setString(String s)
	{
		String s1 = s.substring(0, 8);
		String s2 = s.substring(24, s.length());
		String s3 = (new StringBuilder()).append(s2).append(s1)
				.append(s.replace(s1, "").replace(s2, "")).toString();
		StringBuffer stringbuffer = new StringBuffer();
		for (int i = s3.length(); i > 0; i--)
			stringbuffer.append(s3.substring(i - 1, i));

		return stringbuffer.toString();
	}

	public static char b[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	public static String z = " 012132 ";

}
//2131296256