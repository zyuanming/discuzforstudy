package net.discuz.json;

import net.discuz.app.DiscuzApp;
import net.discuz.json.helper.ForumDisplayParseHelper;
import net.discuz.json.helper.ForumIndexParseHelper;
import net.discuz.json.helper.ForumNavParseHelper;
import net.discuz.json.helper.FriendParseHelper;
import net.discuz.json.helper.HotForumParseHelper;
import net.discuz.json.helper.HotThreadParseHelper;
import net.discuz.json.helper.MyFavForumParseHelper;
import net.discuz.json.helper.MyFavThreadParseHelper;
import net.discuz.json.helper.MyPmParseHelper;
import net.discuz.json.helper.MyPmViewParseHelper;
import net.discuz.json.helper.MyThreadParseHelper;
import net.discuz.json.helper.ProfileParseHelper;
import net.discuz.json.helper.PublicPmParseHelper;
import net.discuz.json.helper.TopListParseHelper;
import net.discuz.json.helper.ViewThreadParseHelper;
import net.discuz.json.helper.x25.ForumDisplayParseHelperX25;
import net.discuz.json.helper.x25.ForumIndexParseHelperX25;
import net.discuz.json.helper.x25.ForumNavParseHelperX25;
import net.discuz.json.helper.x25.FriendParseHelperX25;
import net.discuz.json.helper.x25.HotForumParseHelperX25;
import net.discuz.json.helper.x25.HotThreadParseHelperX25;
import net.discuz.json.helper.x25.MyFavForumParseHelperX25;
import net.discuz.json.helper.x25.MyFavThreadParseHelperX25;
import net.discuz.json.helper.x25.MyPmParseHelperX25;
import net.discuz.json.helper.x25.MyPmViewParseHelperX25;
import net.discuz.json.helper.x25.MyThreadParseHelperX25;
import net.discuz.json.helper.x25.ProfileParseHelperX25;
import net.discuz.json.helper.x25.PublicPmParseHelperX25;
import net.discuz.json.helper.x25.TopListParseHelperX25;
import net.discuz.json.helper.x25.ViewThreadParseHelperX25;
import net.discuz.source.InterFace.JsonParseHelperCallBack;
import net.discuz.source.activity.DiscuzBaseActivity;

import org.json.JSONObject;

public class JsonParser
{

	public JsonParser(DiscuzBaseActivity discuzbaseactivity)
	{
		siteVersion = "";
		siteAppId = null;
		activity = discuzbaseactivity;
		siteAppId = discuzbaseactivity.siteAppId;
		String s;
		if (DiscuzApp.getInstance().getSiteInfo(siteAppId).getVersion() == null)
			s = "X25";
		else
			s = DiscuzApp.getInstance()
					.getSiteInfo(discuzbaseactivity.siteAppId).getVersion();
		siteVersion = s;
	}

	public ForumDisplayParseHelper forumDisplay(JSONObject jsonobject,
			JsonParseHelperCallBack jsonparsehelpercallback) throws Exception
	{
		if (siteVersion.equals("X25") || siteVersion.equals("X2"))
		{
			ForumDisplayParseHelperX25 forumdisplayparsehelperx25 = new ForumDisplayParseHelperX25(
					siteAppId);
			forumdisplayparsehelperx25
					.setParseHelperCallBack(jsonparsehelpercallback);
			forumdisplayparsehelperx25.parse(jsonobject);
			return forumdisplayparsehelperx25;
		} else
		{
			return null;
		}
	}

	public ForumNavParseHelper forumNav(JSONObject jsonobject,
			JsonParseHelperCallBack jsonparsehelpercallback) throws Exception
	{
		ForumNavParseHelperX25 forumnavparsehelperx25 = new ForumNavParseHelperX25(
				siteAppId);
		forumnavparsehelperx25.setParseHelperCallBack(jsonparsehelpercallback);
		forumnavparsehelperx25.parse(jsonobject);
		return forumnavparsehelperx25;
	}

	public ForumIndexParseHelper forumindex(JSONObject jsonobject,
			JsonParseHelperCallBack jsonparsehelpercallback) throws Exception
	{
		if (siteVersion.equals("X25") || siteVersion.equals("X2"))
		{
			ForumIndexParseHelperX25 forumindexparsehelperx25 = new ForumIndexParseHelperX25(
					siteAppId);
			forumindexparsehelperx25
					.setParseHelperCallBack(jsonparsehelpercallback);
			forumindexparsehelperx25.parse(jsonobject);
			return forumindexparsehelperx25;
		} else
		{
			return null;
		}
	}

	public FriendParseHelper friend(JSONObject jsonobject,
			JsonParseHelperCallBack jsonparsehelpercallback) throws Exception
	{
		FriendParseHelperX25 friendparsehelperx25 = new FriendParseHelperX25(
				siteAppId);
		friendparsehelperx25.setParseHelperCallBack(jsonparsehelpercallback);
		friendparsehelperx25.parse(jsonobject);
		return friendparsehelperx25;
	}

	public HotForumParseHelper hotForum(JSONObject jsonobject,
			JsonParseHelperCallBack jsonparsehelpercallback) throws Exception
	{
		if (siteVersion.equals("X25") || siteVersion.equals("X2"))
		{
			HotForumParseHelperX25 hotforumparsehelperx25 = new HotForumParseHelperX25(
					siteAppId);
			hotforumparsehelperx25
					.setParseHelperCallBack(jsonparsehelpercallback);
			hotforumparsehelperx25.parse(jsonobject);
			return hotforumparsehelperx25;
		} else
		{
			return null;
		}
	}

	public HotThreadParseHelper hotThread(JSONObject jsonobject,
			JsonParseHelperCallBack jsonparsehelpercallback) throws Exception
	{
		if (siteVersion.equals("X25") || siteVersion.equals("X2"))
		{
			HotThreadParseHelperX25 hotthreadparsehelperx25 = new HotThreadParseHelperX25(
					siteAppId);
			hotthreadparsehelperx25
					.setParseHelperCallBack(jsonparsehelpercallback);
			hotthreadparsehelperx25.parse(jsonobject);
			return hotthreadparsehelperx25;
		} else
		{
			return null;
		}
	}

	public MyFavForumParseHelper myFavForum(JSONObject jsonobject,
			JsonParseHelperCallBack jsonparsehelpercallback) throws Exception
	{
		if (siteVersion.equals("X25") || siteVersion.equals("X2"))
		{
			MyFavForumParseHelperX25 myfavforumparsehelperx25 = new MyFavForumParseHelperX25(
					siteAppId);
			myfavforumparsehelperx25
					.setParseHelperCallBack(jsonparsehelpercallback);
			myfavforumparsehelperx25.parse(jsonobject);
			return myfavforumparsehelperx25;
		} else
		{
			return null;
		}
	}

	public MyFavThreadParseHelper myFavThread(JSONObject jsonobject,
			JsonParseHelperCallBack jsonparsehelpercallback) throws Exception
	{
		if (siteVersion.equals("X25") || siteVersion.equals("X2"))
		{
			MyFavThreadParseHelperX25 myfavthreadparsehelperx25 = new MyFavThreadParseHelperX25(
					siteAppId);
			myfavthreadparsehelperx25
					.setParseHelperCallBack(jsonparsehelpercallback);
			myfavthreadparsehelperx25.parse(jsonobject);
			return myfavthreadparsehelperx25;
		} else
		{
			return null;
		}
	}

	public MyPmParseHelper myPm(JSONObject jsonobject,
			JsonParseHelperCallBack jsonparsehelpercallback) throws Exception
	{
		if (siteVersion.equals("X25") || siteVersion.equals("X2"))
		{
			MyPmParseHelperX25 mypmparsehelperx25 = new MyPmParseHelperX25(
					siteAppId);
			mypmparsehelperx25.setParseHelperCallBack(jsonparsehelpercallback);
			mypmparsehelperx25.parse(jsonobject);
			return mypmparsehelperx25;
		} else
		{
			return null;
		}
	}

	public MyPmViewParseHelper myPmView(JSONObject jsonobject,
			JsonParseHelperCallBack jsonparsehelpercallback) throws Exception
	{
		if (siteVersion.equals("X25") || siteVersion.equals("X2"))
		{
			MyPmViewParseHelperX25 mypmviewparsehelperx25 = new MyPmViewParseHelperX25(
					siteAppId);
			mypmviewparsehelperx25
					.setParseHelperCallBack(jsonparsehelpercallback);
			mypmviewparsehelperx25.parse(jsonobject);
			return mypmviewparsehelperx25;
		} else
		{
			return null;
		}
	}

	public MyThreadParseHelper myThread(JSONObject jsonobject,
			JsonParseHelperCallBack jsonparsehelpercallback) throws Exception
	{
		if (siteVersion.equals("X25") || siteVersion.equals("X2"))
		{
			MyThreadParseHelperX25 mythreadparsehelperx25 = new MyThreadParseHelperX25(
					siteAppId);
			mythreadparsehelperx25
					.setParseHelperCallBack(jsonparsehelpercallback);
			mythreadparsehelperx25.parse(jsonobject);
			return mythreadparsehelperx25;
		} else
		{
			return null;
		}
	}

	public ProfileParseHelper profile(JSONObject jsonobject,
			JsonParseHelperCallBack jsonparsehelpercallback) throws Exception
	{
		if (siteVersion.equals("X25") || siteVersion.equals("X2"))
		{
			ProfileParseHelperX25 profileparsehelperx25 = new ProfileParseHelperX25(
					siteAppId);
			profileparsehelperx25
					.setParseHelperCallBack(jsonparsehelpercallback);
			profileparsehelperx25.parse(jsonobject);
			return profileparsehelperx25;
		} else
		{
			return null;
		}
	}

	public PublicPmParseHelper publicPm(JSONObject jsonobject,
			JsonParseHelperCallBack jsonparsehelpercallback) throws Exception
	{
		if (siteVersion.equals("X25") || siteVersion.equals("X2"))
		{
			PublicPmParseHelperX25 publicpmparsehelperx25 = new PublicPmParseHelperX25(
					siteAppId);
			publicpmparsehelperx25
					.setParseHelperCallBack(jsonparsehelpercallback);
			publicpmparsehelperx25.parse(jsonobject);
			return publicpmparsehelperx25;
		} else
		{
			return null;
		}
	}

	public TopListParseHelper topList(JSONObject jsonobject,
			JsonParseHelperCallBack jsonparsehelpercallback) throws Exception
	{
		if (siteVersion.equals("X25") || siteVersion.equals("X2"))
		{
			TopListParseHelperX25 toplistparsehelperx25 = new TopListParseHelperX25(
					siteAppId);
			toplistparsehelperx25
					.setParseHelperCallBack(jsonparsehelpercallback);
			toplistparsehelperx25.parse(jsonobject);
			return toplistparsehelperx25;
		} else
		{
			return null;
		}
	}

	public ViewThreadParseHelper viewThread(JSONObject jsonobject,
			JsonParseHelperCallBack jsonparsehelpercallback) throws Exception
	{
		if (siteVersion.equals("X25") || siteVersion.equals("X2"))
		{
			ViewThreadParseHelperX25 viewthreadparsehelperx25 = new ViewThreadParseHelperX25(
					siteAppId);
			viewthreadparsehelperx25.createTemplate(activity);
			viewthreadparsehelperx25
					.setParseHelperCallBack(jsonparsehelpercallback);
			viewthreadparsehelperx25.parse(jsonobject);
			return viewthreadparsehelperx25;
		} else
		{
			return null;
		}
	}

	private DiscuzBaseActivity activity;
	private String siteAppId;
	private String siteVersion;
}
// 2131296256