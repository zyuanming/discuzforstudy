package net.discuz.json.helper;

import org.json.JSONObject;

public abstract class FriendParseHelper extends JsonParseHelper
{
	public class FriendData
	{

		public String[] getUids()
		{
			return uids;
		}

		public String[] getUsernames()
		{
			return usernames;
		}

		public void setUids(String as[])
		{
			uids = as;
		}

		public void setUsernames(String as[])
		{
			usernames = as;
		}

		public String uids[];
		public String usernames[];

		public FriendData()
		{
			super();
		}
	}

	public FriendParseHelper(String s)
	{
		super(s);
		friendData = null;
		friendData = new FriendData();
	}

	public void parse(JSONObject jsonobject) throws Exception
	{
		onParseBegin();
		super.parse(jsonobject);
		onParseFinish(friendData);
	}

	public FriendData friendData;
}
// 2131296256