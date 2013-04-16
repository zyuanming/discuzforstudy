package net.discuz.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.discuz.R;
import net.discuz.activity.siteview.Vscode;
import net.discuz.app.DiscuzApp;
import net.discuz.boardcast.HttpConnReceiver;
import net.discuz.model.SiteInfo;
import net.discuz.source.InterFace.onPostSending;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.tools.Core;
import net.discuz.tools.HttpConnThread;
import net.discuz.tools.Tools;
import net.discuz.tools.checkVcodeHttpConnTread;

import org.json.JSONObject;

import android.content.Intent;

public class PostSender
{
	public class ThreadTask
	{

		public void execute()
		{
			HttpConnReceiver.HttpConnListener httpconnlistener = new HttpConnReceiver.HttpConnListener()
			{

				public void onFailed(Exception exception)
				{
					exception.printStackTrace();
					context.dismissLoading();
					context.ShowMessageByHandler(
							R.string.message_internet_error, 3);
					postFailMessage(context
							.getString(R.string.message_response_error));
				}

				public void onSucceed(String s, String s1)
				{
					if (!"error".equals(s))
					{
						DJsonReader djsonreader;
						if (s != null)
						{
							djsonreader = new DJsonReader(s);
							String as[];
							try
							{
								djsonreader._jsonParse();
								djsonreader._debug();
								if (djsonreader.isjson)
								{
									as = djsonreader._getMessage();
									DEBUG.o((new StringBuilder())
											.append("resultmessage:")
											.append(as[1]).toString());
									if (as[0].lastIndexOf("_succeed") > -1)
									{
										context.ShowMessageByHandler(Core
												.getInstance()
												._getMessageByName(as), 1);
										Iterator iterator1 = onPostSending
												.iterator();
										while (iterator1.hasNext())
											((onPostSending) iterator1.next())
													.sendSuccess(djsonreader);
									}
									postFailMessage(Core.getInstance()
											._getMessageByName(as));
									if (as[0].equals("submit_secqaa_invalid")
											|| as[0].equals("submit_seccode_invalid"))
										(new checkVcode(Integer.valueOf(1),
												params)).execute();
								}
							} catch (Exception e)
							{
								e.printStackTrace();
								postFailMessage(context
										.getString(R.string.message_response_error));
							}
						}

					}
					for (Iterator iterator = onPostSending.iterator(); iterator
							.hasNext(); ((onPostSending) iterator.next())
							.sendFinish())
						;
				}
			};
			HttpConnThread httpconnthread = new HttpConnThread(
					context.siteAppId, 0);
			httpconnthread.setHttpMethod(1);
			httpconnthread.setPostparams(postParams);
			httpconnthread.setUrl(newThreadUrl);
			httpconnthread.setCacheType(-1);
			if (!vcodeCookie.equals(""))
				httpconnthread.setCookie(vcodeCookie);
			String s = getClass().getSimpleName();
			httpconnthread.setFilter(s);
			DiscuzApp.getInstance().setHttpConnListener(s, httpconnlistener);
			DiscuzApp.getInstance().sendHttpConnThread(httpconnthread);
		}

		private String newThreadUrl;
		private String params[];
		private HashMap postParams;

		public ThreadTask(List list, String as[])
		{
			super();
			params = as;
			if (list == null)
				list = new ArrayList();
			LBS_location.LocationData locationdata = LBS_location.getInstance(
					context).getLocation(2000L);
			for (Iterator iterator = PostSender.this.onPostSending.iterator(); iterator
					.hasNext(); ((onPostSending) iterator.next()).sendBefore())
				;
			list.add("module=newthread");
			list.add("topicsubmit=yes");
			list.add((new StringBuilder()).append("fid=").append(params[0])
					.toString());
			list.add(0, context.siteAppId);
			newThreadUrl = Core.getSiteUrl((String[]) list
					.toArray(new String[list.size()]));
			postParams = new HashMap();
			postParams.put("formhash", siteInfo.getLoginUser().getFormHash());
			postParams.put("subject", subject);
			postParams.put("mobiletype", "2");
			postParams.put("allownoticeauthor", "1");
			if (threadType != null)
				postParams.put("typeid", threadType);
			postParams.put("message", message);
			if (locationdata != null)
			{
				DEBUG.o((new StringBuilder()).append("--location--")
						.append(locationdata.address).toString());
				postParams.put(
						"location",
						(new StringBuilder()).append(locationdata.lon)
								.append("|").append(locationdata.lat)
								.append("|").append(locationdata.address)
								.toString());
			}
			if (aids != null)
			{
				boolean flag = aids.isEmpty();
				int i = 0;
				if (!flag)
				{
					for (; i < aids.size(); i++)
					{
						String s = (String) aids.get(i);
						postParams.put(
								(new StringBuilder()).append("attachnew[")
										.append(s).append("][description]")
										.toString(), "");
					}

					postParams.put("allowphoto", "1");
				}
			}
		}
	}

	public class checkVcode
	{

		private void onPostExecute(String s)
		{
			if ("vccode".equals(s))
			{
				Intent intent = new Intent();
				intent.setClass(context, Vscode.class);
				intent.putExtra("seccodeurl", seccodeurl);
				intent.putExtra("secqaa", secqaa);
				intent.putExtra("siteappid", context.siteAppId);
				context.startActivity(intent);
				Vscode.onvcode = new net.discuz.activity.siteview.Vscode.onVcode()
				{

					public void VcodeError()
					{}

					public void VcodeSuceess(String s, String s1, String s2)
					{
						resultParamList = new ArrayList();
						if (!sechash.equals(""))
							resultParamList.add((new StringBuilder())
									.append("sechash=").append(sechash)
									.toString());
						vcodeCookie = s2;
						if (!s.equals(""))
						{
							resultParamList.add((new StringBuilder())
									.append("seccodeverify=").append(s)
									.toString());
							DEBUG.o((new StringBuilder())
									.append("============== VCODE  ===========")
									.append(s).toString());
						}
						if (!s1.equals(""))
						{
							resultParamList
									.add((new StringBuilder())
											.append("secanswer=").append(s1)
											.toString());
							DEBUG.o((new StringBuilder())
									.append("============== ANSWER  ===========")
									.append(s1).toString());
						}
						if (threadType.intValue() == 1)
						{
							(new ThreadTask(resultParamList, params)).execute();
							return;
						} else
						{
							(new replyTask(resultParamList, params)).execute();
							return;
						}
					}
				};
			} else if (threadType.intValue() == 1)
				(new ThreadTask(null, params)).execute();
			else
				(new replyTask(null, params)).execute();
			resultParamList = null;
		}

		public void execute()
		{
			String as[] = new String[3];
			as[0] = context.siteAppId;
			as[1] = "module=secure";
			as[2] = "type=post";
			String s = Core.getSiteUrl(as);
			net.discuz.boardcast.HttpConnReceiver.HttpConnListener httpconnlistener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
			{

				public void onFailed(Exception exception)
				{
					exception.printStackTrace();
					context.ShowMessageByHandler(
							R.string.message_internet_error, 3);
				}

				public void onSucceed(String s, String s1)
				{
					boolean flag = "error".equals(s);
					if (!flag)
					{
						if (s != null)
						{
							try
							{
								JSONObject jsonobject = (new JSONObject(s))
										.optJSONObject("Variables");
								if (jsonobject != null)
								{
									seccodeurl = jsonobject
											.optString("seccode").trim();
									secqaa = jsonobject.optString("secqaa")
											.trim();
									siteInfo.getLoginUser().setFormHash(
											jsonobject.optString("formhash")
													.trim());
									siteInfo.getLoginUser().setCookiepre(
											jsonobject.optString("cookiepre")
													.trim());
									sechash = jsonobject.optString("sechash")
											.trim();
									if (!seccodeurl.equals("")
											|| !secqaa.equals(""))
										s = "vccode";
								}
							} catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					} else
					{
						s = null;
					}
					onPostExecute(s);
					return;
				}
			};
			DEBUG.o("=================checkVcodeHttpConnTread START============");
			checkVcodeHttpConnTread checkvcodehttpconntread = new checkVcodeHttpConnTread(
					context.siteAppId, 0);
			checkvcodehttpconntread.setUrl(s);
			checkvcodehttpconntread.setCacheType(-1);
			String s1 = getClass().getSimpleName();
			checkvcodehttpconntread.setFilter(s1);
			DiscuzApp.getInstance().setHttpConnListener(s1, httpconnlistener);
			DiscuzApp.getInstance().sendHttpConnThread(checkvcodehttpconntread);
		}

		private String params[];
		private List resultParamList;
		private String seccodeurl;
		private String sechash;
		private String secqaa;
		private Integer threadType;

		public checkVcode(Integer integer, String as[])
		{
			super();
			threadType = Integer.valueOf(0);
			params = null;
			resultParamList = null;
			threadType = integer;
			params = as;
		}
	}

	private class replyTask
	{

		public void execute()
		{
			net.discuz.boardcast.HttpConnReceiver.HttpConnListener httpconnlistener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
			{

				public void onFailed(Exception exception)
				{
					exception.printStackTrace();
					context.dismissLoading();
					context.ShowMessageByHandler(
							R.string.message_internet_error, 3);
					postFailMessage(context
							.getString(R.string.message_response_error));
				}

				public void onSucceed(String s, String s1)
				{
					if (s == null)
					{
						postFailMessage(context
								.getString(R.string.message_response_error));
					} else
					{
						DJsonReader djsonreader = new DJsonReader(s);
						if (djsonreader != null && djsonreader.isjson)
						{
							djsonreader._debug();
							String as[];
							try
							{
								djsonreader._jsonParse();
								as = djsonreader._getMessage();
								if (as[0].lastIndexOf("_succeed") <= -1)
								{
									try
									{
										postFailMessage(Core.getInstance()
												._getMessageByName(as));
										if (as[0]
												.equals("submit_secqaa_invalid")
												|| as[0].equals("submit_seccode_invalid"))
											(new checkVcode(Integer.valueOf(0),
													params)).execute();
									} catch (Exception exception)
									{
										postFailMessage(context
												.getString(R.string.message_response_error));
										exception.printStackTrace();
									}
								} else
								{
									Exception exception;
									context.ShowMessageByHandler(Core
											.getInstance()
											._getMessageByName(as), 1);
									for (Iterator iterator1 = onPostSending
											.iterator(); iterator1.hasNext(); ((onPostSending) iterator1
											.next()).sendSuccess(djsonreader))
										;
								}
							} catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					}

					for (Iterator iterator = onPostSending.iterator(); iterator
							.hasNext(); ((onPostSending) iterator.next())
							.sendFinish())
						;
				}
			};
			HttpConnThread httpconnthread = new HttpConnThread(
					context.siteAppId, 0);
			httpconnthread.setHttpMethod(1);
			httpconnthread.setPostparams(postParams);
			httpconnthread.setUrl(replyurl);
			httpconnthread.setCacheType(-1);
			if (!vcodeCookie.equals(""))
				httpconnthread.setCookie(vcodeCookie);
			String s = getClass().getSimpleName();
			httpconnthread.setFilter(s);
			DiscuzApp.getInstance().setHttpConnListener(s, httpconnlistener);
			DiscuzApp.getInstance().sendHttpConnThread(httpconnthread);
		}

		private String params[];
		HashMap postParams;
		private String replyurl;

		public replyTask(List list, String as[])
		{
			super();
			params = as;
			if (list == null)
				list = new ArrayList();
			LBS_location.LocationData locationdata = LBS_location.getInstance(
					context).getLocation(2000L);
			for (Iterator iterator = PostSender.this.onPostSending.iterator(); iterator
					.hasNext(); ((onPostSending) iterator.next()).sendBefore())
				;
			list.add("module=sendreply");
			list.add("replysubmit=yes");
			list.add((new StringBuilder()).append("tid=").append(params[0])
					.toString());
			list.add(0, context.siteAppId);
			replyurl = Core.getSiteUrl((String[]) list.toArray(new String[list
					.size()]));
			postParams = new HashMap();
			postParams.put("formhash", siteInfo.getLoginUser().getFormHash());
			postParams.put("message", message);
			postParams.put("mobiletype", "2");
			if (noticetrimstr != null)
				postParams.put("noticetrimstr", noticetrimstr);
			if (locationdata != null)
			{
				DEBUG.o((new StringBuilder()).append("--location--")
						.append(locationdata.address).toString());
				postParams.put(
						"location",
						(new StringBuilder()).append(locationdata.lon)
								.append("|").append(locationdata.lat)
								.append("|").append(locationdata.address)
								.toString());
			}
			if (aids != null)
			{
				boolean flag = aids.isEmpty();
				int i = 0;
				if (!flag)
				{
					for (; i < aids.size(); i++)
					{
						String s = (String) aids.get(i);
						postParams.put(
								(new StringBuilder()).append("attachnew[")
										.append(s).append("][description]")
										.toString(), "");
					}

					postParams.put("allowphoto", "1");
				}
			}
			DEBUG.o((new StringBuilder()).append("replyurl:").append(replyurl)
					.toString());
		}
	}

	public PostSender(DiscuzBaseActivity discuzbaseactivity)
	{
		sendMode = 0;
		subject = null;
		message = null;
		threadTypeRequired = false;
		threadType = null;
		aids = null;
		noticetrimstr = "";
		message_ext = "";
		reppid = null;
		siteInfo = null;
		onPostSending = new HashSet();
		vcodeCookie = "";
		context = discuzbaseactivity;
		siteInfo = DiscuzApp.getInstance().getSiteInfo(context.siteAppId);
	}

	private boolean _checkMessageLength()
	{
		return message.length() >= 3;
	}

	private boolean _checkSubjectLenght()
	{
		return subject.length() >= 3;
	}

	private void postFailMessage(String s)
	{
		if (context != null)
			context.ShowMessageByHandler(s, 3);
		for (Iterator iterator = onPostSending.iterator(); iterator.hasNext(); ((onPostSending) iterator
				.next()).sendFaild())
			;
	}

	public boolean _checkinternet()
	{
		return Core.getInstance()._hasInternet();
	}

	public void _getLBS()
	{}

	public void _isSendReply()
	{
		sendMode = 2;
	}

	public void _isSendThread()
	{
		sendMode = 1;
	}

	public void _send(String as[])
	{
		if (!_checkinternet())
		{
			ShowMessage.getInstance(context)._showToast("您处于离线状态", 3);
			return;
		}
		if (message.length() == 0)
		{
			ShowMessage.getInstance(context)._showToast("请输入内容", 3);
			return;
		}
		if (!_checkMessageLength())
		{
			ShowMessage.getInstance(context)._showToast("内容不能少于3个字", 2);
			return;
		}
		switch (sendMode)
		{
		default:
			return;

		case 1: // '\001'
			if (subject.length() == 0)
			{
				ShowMessage.getInstance(context)._showToast("请输入标题", 3);
				return;
			}
			if (!_checkSubjectLenght())
			{
				ShowMessage.getInstance(context)._showToast("标题文字不能少于3个字", 3);
				return;
			}
			if (threadType == null && threadTypeRequired)
			{
				ShowMessage.getInstance(context)._showToast("请选择分类", 3);
				return;
			} else
			{
				(new checkVcode(Integer.valueOf(1), as)).execute();
				return;
			}

		case 2: // '\002'
			(new checkVcode(Integer.valueOf(0), as)).execute();
			return;
		}
	}

	public void _setAttach(ArrayList arraylist)
	{
		aids = arraylist;
	}

	public void _setMessage(String s)
	{
		message = s.trim();
	}

	public void _setOnSend(onPostSending onpostsending)
	{
		onPostSending.add(onpostsending);
	}

	public void _setReplyByPid(String s, String s1, HashMap hashmap)
	{
		reppid = s1;
		String s2 = (String) hashmap.get("dateline");
		String s3;
		if (s2.contains("span"))
		{
			Matcher matcher = Pattern.compile("<span.+\"(.+)\".+span>", 32)
					.matcher(s2);
			matcher.find();
			s3 = matcher.group(1);
		} else
		{
			s3 = s2;
		}
		noticetrimstr = (String) hashmap.get("message");
		noticetrimstr = Pattern
				.compile("\\<span style=\"display:none\"\\>.*?\\</span\\>", 32)
				.matcher(noticetrimstr).replaceAll("");
		noticetrimstr = Tools.htmlSpecialCharDecode(noticetrimstr);
		noticetrimstr = Pattern.compile("\\<.+quote\\>.+div\\>", 32)
				.matcher(noticetrimstr).replaceAll("");
		noticetrimstr = Pattern.compile("<[^<>]*>", 32).matcher(noticetrimstr)
				.replaceAll("");
		if (noticetrimstr.length() > 100)
			noticetrimstr = noticetrimstr.substring(0, 100);
		noticetrimstr = (new StringBuilder())
				.append("[quote][size=2][color=#999999]")
				.append((String) hashmap.get("author"))
				.append(" \u53D1\u8868\u4E8E ")
				.append(s3)
				.append("[/color] [url=forum.php?mod=redirect&goto=findpost&pid=")
				.append(s1)
				.append("&ptid=")
				.append(s)
				.append("][img]static/image/common/back.gif[/img][/url][/size]\n")
				.append(noticetrimstr).append("[/quote]").toString();
	}

	public void _setSubject(String s)
	{
		subject = s.trim();
	}

	public void _setThreadType(String s)
	{
		threadType = s;
	}

	public void _setThreadTypeRequired(boolean flag)
	{
		threadTypeRequired = flag;
	}

	private ArrayList aids;
	private DiscuzBaseActivity context;
	private String message;
	private String message_ext;
	private String noticetrimstr;
	private HashSet onPostSending;
	private String reppid;
	private int sendMode;
	private SiteInfo siteInfo;
	private String subject;
	private String threadType;
	private boolean threadTypeRequired;
	private String vcodeCookie;

}
// 2131296256