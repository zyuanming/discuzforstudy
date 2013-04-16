package net.discuz.source;

import net.discuz.init.InitSetting;
import net.discuz.model.AllowPerm;
import net.discuz.tools.Tools;

public class AllowPermManager
{

	public AllowPermManager()
	{}

	public static AllowPerm getAllowPermSerialization(String s, String s1,
			boolean flag)
	{
		String s2;
		if (flag)
			s2 = (new StringBuilder()).append(InitSetting.CACHE_PATH)
					.append("vperm/").append(s).append("_").append(s1)
					.append(".perm").toString();
		else
			s2 = (new StringBuilder()).append(InitSetting.CACHE_PATH)
					.append("fperm/").append(s).append("_").append(s1)
					.append(".perm").toString();
		return (AllowPerm) Tools.DeSerialize(s2);
	}

	public static void setAllowPermSerialization(String s, String s1,
			AllowPerm allowperm, boolean flag)
	{
		String s2;
		if (flag)
			s2 = (new StringBuilder()).append(InitSetting.CACHE_PATH)
					.append("vperm/").append(s).append("_").append(s1)
					.append(".perm").toString();
		else
			s2 = (new StringBuilder()).append(InitSetting.CACHE_PATH)
					.append("fperm/").append(s).append("_").append(s1)
					.append(".perm").toString();
		Tools.Serialize(allowperm, s2);
	}

	public static final String FORUM_PERM_PATH = "fperm/";
	public static final String VIEWTHREAD_PERM_PATH = "vperm/";
}
//2131296256