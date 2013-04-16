package net.discuz.source;

import android.os.Handler;
import android.os.Message;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.tools.Core;

public class MessageHandler extends Handler
{

	public MessageHandler(DiscuzBaseActivity discuzbaseactivity)
	{
		activity = discuzbaseactivity;
	}

	public void handleMessage(Message message)
	{
		super.handleMessage(message);
		super.handleMessage(message);
		switch (message.what)
		{
		default:
			return;
		case 1:
			String str2 = "刷新成功";
			if (message.obj != null)
			{
				String[] arrayOfString2 = (String[]) message.obj;
				str2 = Core.getInstance()._getMessageByName(arrayOfString2);
			}
			ShowMessage.getInstance(this.activity)._showToast(str2, 1);
			return;
		case 2:
			String str1 = "刷新失败";
			if (message.obj != null)
			{
				String[] arrayOfString1 = (String[]) message.obj;
				str1 = Core.getInstance()._getMessageByName(arrayOfString1);
			}
			ShowMessage.getInstance(this.activity)._showToast(str1, 3);
			return;
		case 201:
			ShowMessage.getInstance(this.activity)._showToast("版块列表刷新成功!", 1);
			return;
		case 202:
			ShowMessage.getInstance(this.activity)._showToast("版块列表刷新失败", 3);
			return;
		case 101:
			ShowMessage.getInstance(this.activity)._showToast("我的帖子刷新成功", 1);
			return;
		case 102:
			ShowMessage.getInstance(this.activity)._showToast("帖子列表刷新失败", 3);
			return;
		case 111:
			ShowMessage.getInstance(this.activity)._showToast("我收藏的帖子刷新成功", 1);
			return;
		case 112:
			ShowMessage.getInstance(this.activity)._showToast("收藏帖子刷新失败", 3);
			return;
		case 211:
			ShowMessage.getInstance(this.activity)._showToast("我的收藏版块刷新成功", 1);
			return;
		case 212:
			ShowMessage.getInstance(this.activity)._showToast("我的收藏版块刷新失败", 3);
			break;
		}

	}

	public final int MESSAGE_DEFAULT_REFRESH_FAILD = 2;
	public final int MESSAGE_DEFAULT_REFRESH_SUCESS = 1;
	public final int MESSAGE_FORUMLIST_REFRESH_FAILD = 202;
	public final int MESSAGE_FORUMLIST_REFRESH_SUCESS = 201;
	public final int MESSAGE_MYFAVFORUM_REFRESH_FAILD = 212;
	public final int MESSAGE_MYFAVFORUM_REFRESH_SUCESS = 211;
	public final int MESSAGE_MYFAVTHREADLIST_REFRESH_FAILD = 112;
	public final int MESSAGE_MYFAVTHREADLIST_REFRESH_SUCESS = 111;
	public final int MESSAGE_MYTHREADLIST_REFRESH_FAILD = 102;
	public final int MESSAGE_MYTHREADLIST_REFRESH_SUCESS = 101;
	public DiscuzBaseActivity activity;
}
// 2131296256