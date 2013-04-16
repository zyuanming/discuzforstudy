package net.discuz.json.helper.x25;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.discuz.app.DiscuzApp;
import net.discuz.json.helper.ViewThreadParseHelper;
import net.discuz.model.SiteInfo;
import net.discuz.source.DEBUG;
import net.discuz.source.storage.AttachmentDBHelper;
import net.discuz.tools.Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

public class ViewThreadParseHelperX25 extends ViewThreadParseHelper
{
	private class setAttachmentListDBTask extends AsyncTask
	{

		protected Object doInBackground(Object aobj[])
		{
			return doInBackground((Void[]) aobj);
		}

		protected Void doInBackground(Void avoid[])
		{
			if (attachmentList == null)
			{
				return null;
			} else
			{
				AttachmentDBHelper.getInstance().replaceAttachment(
						attachmentList);
				attachmentList.clear();
				attachmentList = null;
				return null;
			}
		}

		private setAttachmentListDBTask()
		{
			super();
		}

	}

	public ViewThreadParseHelperX25(String s)
	{
		super(s);
		imageMaxWidth = 268;
		imageMaxHeight = 380;
		attachmentList = null;
		used_aids = new HashMap();
		rewritepreg = null;
		rewritetag = null;
		imageListHtmlResult = null;
		attachListHtmlResult = null;
		ViewThreadNodePostListHtmlResult = new StringBuilder();
		mypost = "\u6211\u7684\u56DE\u5E16";
		siteInfo = null;
		siteUrl = null;
		siteUcenterUrl = null;
		siteInfo = DiscuzApp.getInstance().getSiteInfo(s);
		siteUrl = DiscuzApp.getInstance().getSiteInfo(s).getSiteUrl();
		siteUcenterUrl = DiscuzApp.getInstance().getSiteInfo(s).getUCenterUrl();
	}

	private String _clearJavascript(String s)
	{
		String s1 = Pattern.compile("onclick=\"zoom(.*?)\" class=\"zoom\"", 32)
				.matcher(s).replaceAll("");
		return Pattern.compile("<script(.*?)>(.*?)<\\/script>", 32).matcher(s1)
				.replaceAll("");
	}

	private String _getURL(String s)
	{
		StringBuilder stringbuilder;
		String s1;
		StringBuilder stringbuilder1;
		Boolean boolean1;
		stringbuilder = new StringBuilder();
		s1 = s.replace("&amp;", "&");
		stringbuilder.append("javascript:showimage=true;DZ.gotoUrlWebView('")
				.append(s1).append("');");
		if (s1.contains("&") && s1.contains("=") || s1.contains(".php"))
			if (s1.contains("forum.php"))
			{
				String s2 = "";
				if (s1.contains("forumdisplay"))
					s2 = "forumdisplay";
				else if (s1.contains("viewthread"))
					s2 = "viewthread";
				if ("viewthread".equals(s2) || "forumdisplay".equals(s2))
				{
					String as2[] = s1.split("\\?")[1].split("&");
					StringBuilder stringbuilder4 = new StringBuilder();
					Boolean boolean4 = Boolean.valueOf(true);
					int j = 0;
					while (j < as2.length)
					{
						String as3[] = as2[j].split("=");
						if (as2[j].startsWith("mod="))
							as3[0] = "module";
						if (!boolean4.booleanValue())
							stringbuilder4.append(",");
						else
							boolean4 = Boolean.valueOf(false);
						stringbuilder4.append("'").append(as3[0]).append("=")
								.append(as3[1]).append("'");
						j++;
					}
					if ("viewthread".equals(s2))
						stringbuilder4
								.insert(0,
										"javascript:showimage=true;DZ.gotoUrlViewThread([");
					else
						stringbuilder4
								.insert(0,
										"javascript:showimage=true;DZ.gotoUrlForumDisplay([");
					stringbuilder4.append("]);");
					return stringbuilder4.toString();
				} else
				{
					return stringbuilder.toString();
				}
			} else
			{
				return stringbuilder.toString();
			}
		if (s1.contains("thread-"))
		{
			String as1[] = s1.split("-");
			StringBuilder stringbuilder3 = new StringBuilder();
			stringbuilder3
					.append("javascript:showimage=true;DZ.gotoUrlViewThread([");
			stringbuilder3.append("'module=viewthread','tid=").append(as1[1])
					.append("'");
			stringbuilder3.append("]);");
			return stringbuilder3.toString();
		}
		if (s1.contains("forum-"))
		{
			String as[] = s1.split("-");
			StringBuilder stringbuilder2 = new StringBuilder();
			stringbuilder2
					.append("javascript:showimage=true;DZ.gotoUrlForumDisplay([");
			stringbuilder2.append("'module=forumdisplay','fid=").append(as[1])
					.append("'");
			stringbuilder2.append("]);");
			return stringbuilder2.toString();
		}
		stringbuilder1 = new StringBuilder();
		boolean1 = Boolean.valueOf(false);
		if (rewritepreg == null)
		{
			return stringbuilder.toString();
		} else
		{
			Boolean boolean2;
			Iterator iterator;
			Matcher matcher;
			MatchResult matchresult;
			int i;
			if ("viewthread".equals(""))
				stringbuilder1
						.append("javascript:showimage=true;DZ.gotoUrlViewThread(['module=")
						.append("").append("',");
			else if ("forumdisplay".equals(""))
				stringbuilder1
						.append("javascript:showimage=true;DZ.gotoUrlForumDisplay(['module=")
						.append("").append("',");
			else
				return stringbuilder.toString();
			iterator = rewritepreg.entrySet().iterator();
			boolean2 = boolean1;
			while (iterator.hasNext())
			{
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator
						.next();
				matcher = Pattern.compile((String) entry.getValue())
						.matcher(s1);
				Boolean boolean3;
				if (matcher.find())
				{
					matchresult = matcher.toMatchResult();
					for (i = 1; i < 1 + matchresult.groupCount(); i++)
					{
						if (i != 1)
							stringbuilder1.append(",");
						stringbuilder1
								.append("'")
								.append((String) ((ArrayList) rewritetag
										.get(entry.getKey())).get(i - 1))
								.append("=").append(matchresult.group(i))
								.append("'");
					}

					boolean3 = Boolean.valueOf(true);
				} else
				{
					boolean3 = boolean2;
				}
				if (!boolean3.booleanValue())
					;
			}
		}
		stringbuilder1.append("]);");
		return stringbuilder1.toString();
	}

	private String _parseAllImageForOtherSite(String s)
	{
		Matcher matcher = Pattern.compile("(?is:<img\\s.*?src=\"(.*?)\".*?>)",
				32).matcher(s);
		StringBuffer stringbuffer = new StringBuffer();
		while (matcher.find())
		{
			StringBuilder stringbuilder = new StringBuilder();
			String s1 = matcher.group(1);
			if (DiscuzApp.isShowPicture)
			{
				if (!s1.contains("http://") && !s1.contains("https://"))
					stringbuilder.append("<img src=\"").append(siteUrl)
							.append("/").append(matcher.group(1))
							.append("\" border=0 />");
				else
					stringbuilder.append(matcher.group());
			} else
			{
				stringbuilder.append(_parse_no_image_for_othersite(matcher
						.group(1)));
			}
			matcher.appendReplacement(stringbuffer, stringbuilder.toString());
		}
		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

	private String _parse_Jammer(String s)
	{
		return Pattern.compile("\\<font class=\"jammer\"\\>.*?\\</font\\>", 32)
				.matcher(s).replaceAll("");
	}

	private String _parse_attach(String s)
	{
		if (attachmentList == null)
			return "";
		if (attachmentList.get((new StringBuilder()).append("attachment")
				.append(s).toString()) == null)
			return "";
		used_aids.put(s, Boolean.valueOf(true));
		HashMap hashmap = (HashMap) attachmentList.get((new StringBuilder())
				.append("attachment").append(s).toString());
		if ("1".equals(hashmap.get("isimage"))
				|| "1".equals(hashmap.get("attachimg")))
		{
			String s1 = _getImageThumb(s, String.valueOf(imageMaxWidth),
					String.valueOf(imageMaxHeight), false, "");
			String s2;
			StringBuilder stringbuilder;
			if (((String) hashmap.get("attachment")).contains("http:"))
				s2 = (String) hashmap.get("attachment");
			else
				s2 = (new StringBuilder()).append(siteUrl).append("/")
						.append((String) hashmap.get("attachment")).toString();
			stringbuilder = new StringBuilder();
			stringbuilder
					.append("<div id=\"aimg_")
					.append(s)
					.append("_box\"><p class=\"postnodeimagebox\"><img id=\"aimg_")
					.append(s)
					.append("\" src=\"")
					.append(s1)
					.append("\" onclick=\"javascript:showimage=true; DZ.ImageWindow('")
					.append(s2)
					.append("', '")
					.append((String) hashmap.get("filename"))
					.append("');\" class=\"postnodeimage\" onload=\"javascript:this.style.background='#FFF';\" onerror=\"javascript:this.style.background='#FFF';\"></p></div>");
			return stringbuilder.toString();
		} else
		{
			StringBuilder stringbuilder1 = new StringBuilder();
			stringbuilder1
					.append("<a href=\"javascript:showimage=true;DZ.DownLoadAttach('")
					.append(s).append("');\" ><p class=\"downloadfile\">")
					.append((String) hashmap.get("filename"))
					.append("<font class='attachdesc'>(")
					.append((String) hashmap.get("attachsize"))
					.append(")</font></p></a>");
			return stringbuilder1.toString();
		}
	}

	private String _parse_attachlist(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder
				.append("<a href=\"javascript:showimage=true;DZ.DownLoadAttach('")
				.append(s)
				.append("')\"><p class=\"downloadfile\">")
				.append((String) ((HashMap) attachmentList
						.get((new StringBuilder()).append("attachment")
								.append(s).toString())).get("filename"))
				.append("<span class=\"attachdesc\">(")
				.append((String) ((HashMap) attachmentList
						.get((new StringBuilder()).append("attachment")
								.append(s).toString())).get("attachsize"))
				.append(")</span></p></a>");
		return stringbuilder.toString();
	}

	private String _parse_ignore_js_op(String s)
	{
		if (!s.contains("ignore_js_op"))
			return s;
		Matcher matcher = Pattern.compile(
				"<ignore_js_op>.*?aimg_(\\d+).*?</ignore_js_op>", 32)
				.matcher(s);
		StringBuffer stringbuffer = new StringBuffer();
		for (; matcher.find(); matcher.appendReplacement(stringbuffer,
				_parse_attach(matcher.group(1))))
			;
		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

	private String _parse_imagelist(String s)
	{
		String s1 = _getImageThumb(s, String.valueOf(imageMaxWidth),
				String.valueOf(imageMaxHeight), false, "");
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder
				.append("<p class=\"postnodeimagebox\"><img src=\"")
				.append(s1)
				.append("\" onclick=\"javascript:showimage=true; DZ.ImageWindow('")
				.append((String) ((HashMap) attachmentList
						.get((new StringBuilder()).append("attachment")
								.append(s).toString())).get("attachment"))
				.append("', '")
				.append((String) ((HashMap) attachmentList
						.get((new StringBuilder()).append("attachment")
								.append(s).toString())).get("filename"))
				.append("');\"  class=\"postnodeimage\" onload=\"javascript:this.style.background='#FFF';\" onerror=\"javascript:this.style.background='#FFF';\"></p>");
		return stringbuilder.toString();
	}

	private String _parse_message(String s)
	{
		String s1 = _parseAllImageForOtherSite(_parse_ignore_js_op(_parse_tthread(s)));
		Matcher matcher = Pattern.compile(
				"\\[attach(.*)?\\](\\d+)\\[\\/attach(.*)?\\]").matcher(s1);
		String s2;
		String s3;
		String s4;
		if (DiscuzApp.isShowPicture)
		{
			StringBuffer stringbuffer = new StringBuffer();
			for (; matcher.find(); matcher.appendReplacement(stringbuffer,
					_parse_attach(matcher.group(2))))
				;
			matcher.appendTail(stringbuffer);
			s2 = stringbuffer.toString();
		} else
		{
			s2 = matcher.replaceAll("");
		}
		s3 = Pattern.compile("(<br( /)?>(\n\r|\r\n)?){3,}").matcher(s2)
				.replaceAll("<br /><br />");
		s4 = Pattern
				.compile("(<br( /)?>(\n\r|\r\n)?)(?=(</(strong|a|div|p)>))")
				.matcher(s3).replaceAll("");
		if (s4.lastIndexOf("plugin.php?") > -1)
			s4 = s4.replaceAll(
					"plugin.php\\?",
					(new StringBuilder()).append(siteUrl)
							.append("/plugin.php?mobile=no&").toString());
		return s4;
	}

	private String _parse_no_image_for_othersite(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder
				.append("<p class=\"downloadfile\" ")
				.append("onclick=\"javascript:showimage=true; DZ.ImageWindow('")
				.append(s).append("', '").append(s).append("');\" >").append(s)
				.append("</p>");
		return stringbuilder.toString();
	}

	private String _parse_no_imagelist(String s)
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder
				.append("<p class=\"downloadfile\" ")
				.append("onclick=\"javascript:showimage=true; DZ.ImageWindow('")
				.append((String) ((HashMap) attachmentList
						.get((new StringBuilder()).append("attachment")
								.append(s).toString())).get("attachment"))
				.append("', '")
				.append((String) ((HashMap) attachmentList
						.get((new StringBuilder()).append("attachment")
								.append(s).toString())).get("filename"))
				.append("');\" >")
				.append((String) ((HashMap) attachmentList
						.get((new StringBuilder()).append("attachment")
								.append(s).toString())).get("filename"))
				.append("</p>");
		return stringbuilder.toString();
	}

	private String _parse_quote(String s)
	{
		StringBuffer stringbuffer = new StringBuffer();
		Matcher matcher = Pattern
				.compile(
						"<div class=\"quote\"><blockquote>((\\s|\\S)*?)</blockquote></div>")
				.matcher(s);
		if (matcher.find())
		{
			String s1 = matcher.group();
			matcher.appendReplacement(stringbuffer,
					Pattern.compile("<a.+?>(.*?)</a>", 32).matcher(s1)
							.replaceAll(""));
		}
		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

	private String _parse_reply(String s)
	{
		Matcher matcher = Pattern
				.compile(
						"<a href=\"(.+(=reply)+)(.+) onclick=\"show(.+)\">.+<\\/a><\\/div>")
				.matcher(s);
		StringBuffer stringbuffer = new StringBuffer();
		for (; matcher.find(); matcher
				.appendReplacement(stringbuffer,
						"<a href=\"javascript:showimage=true;DZ.Reply();\">\u56DE\u590D</a></div>"))
			;
		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

	private void _parse_rewrite()
	{
		JSONObject jsonobject = json.optJSONObject("setting_rewriterule");
		HashMap hashmap = new HashMap();
		if (jsonobject != null)
		{
			for (int i = 0; i < jsonobject.names().length(); i++)
				hashmap.put(jsonobject.names().optString(i),
						jsonobject.optString(jsonobject.names().optString(i)));

			HashMap hashmap1 = new HashMap();
			JSONArray jsonarray = json.optJSONArray("setting_rewritestatus");
			int j = 0;
			if (jsonarray != null)
				for (; j < jsonarray.length(); j++)
					hashmap1.put(jsonarray.optString(j), Boolean.valueOf(true));

			rewritetag = new HashMap();
			rewritepreg = new HashMap();
			HashMap hashmap2 = new HashMap();
			Iterator iterator = hashmap1.entrySet().iterator();
			do
			{
				if (!iterator.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator
						.next();
				if (hashmap.get(entry.getKey()) != null)
				{
					Matcher matcher = Pattern.compile("\\{(.+?)\\}").matcher(
							(CharSequence) hashmap.get(entry.getKey()));
					StringBuffer stringbuffer = new StringBuffer();
					ArrayList arraylist = new ArrayList();
					for (; matcher.find(); matcher.appendReplacement(
							stringbuffer, "(.+?)"))
					{
						String s = matcher.group();
						arraylist.add(s.substring(1, -1 + s.length()));
					}

					matcher.appendTail(stringbuffer);
					rewritetag.put(entry.getKey(), arraylist);
					rewritepreg.put(entry.getKey(), stringbuffer.toString());
					hashmap2.put(entry.getKey(), hashmap.get(entry.getKey()));
				}
			} while (true);
		}
	}

	private String _parse_smily(String s)
	{
		StringBuffer stringbuffer = new StringBuffer();
		Matcher matcher = Pattern.compile(
				"<img(\\s)(.*?)(\\s)(smilieid=)(.*?)+ />").matcher(s);
		while (matcher.find())
		{
			String s1 = matcher.group();
			String s2 = "";
			if (s1.lastIndexOf("http://") == -1)
			{
				Matcher matcher1 = Pattern.compile("src=\"(.*?)\"").matcher(s1);
				if (matcher1.find())
					s2 = matcher1.group();
				String s3;
				if (siteUrl.lastIndexOf("http://") > -1)
					s3 = siteUrl;
				else
					s3 = (new StringBuilder()).append("http://")
							.append(siteUrl).toString();
				s2 = (new StringBuilder()).append("<img src=\"").append(s3)
						.append("/").append(s2.substring(5, -1 + s2.length()))
						.append("\" border=0 alt='' />").toString();
			}
			matcher.appendReplacement(stringbuffer, s2);
		}
		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

	private String _parse_tthread(String s)
	{
		if (!s.contains("[tthread="))
			return s;
		Matcher matcher = Pattern.compile("\\[tthread=(.+)].+\\[\\/tthread]",
				32).matcher(s);
		StringBuffer stringbuffer = new StringBuffer();
		StringBuilder stringbuilder;
		for (; matcher.find(); matcher.appendReplacement(stringbuffer,
				stringbuilder.toString()))
		{
			String as[] = matcher.group(1).split(",");
			stringbuilder = new StringBuilder();
			stringbuilder.append("<a href=\"http://t.qq.com/").append(as[0])
					.append("\" target=\"_blank\">").append(as[1])
					.append(" \u817E\u8BAF\u5FAE\u535A</a>");
		}

		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

	private String _parse_url(String s)
	{
		Matcher matcher = Pattern.compile(
				"<a href=\"(.+?)\".*?target=\"_blank\">").matcher(s);
		StringBuffer stringbuffer = new StringBuffer();
		while (matcher.find())
		{
			DEBUG.o((new StringBuilder()).append("matcher url:")
					.append(matcher.group(1)).toString());
			String s1 = matcher.group(1);
			if (!s1.contains("http:"))
				s1 = (new StringBuilder()).append(siteUrl).append(s1)
						.toString();
			StringBuilder stringbuilder = new StringBuilder();
			if (s1.contains(siteUrl))
				stringbuilder.append("<a href=\"").append(_getURL(s1))
						.append("\">");
			else
				stringbuilder
						.append("<a href=\"javascript:showimage=true;DZ.gotoUrlWebView('")
						.append(s1).append("');\">");
			matcher.appendReplacement(stringbuffer, stringbuilder.toString());
		}
		matcher.appendTail(stringbuffer);
		return stringbuffer.toString();
	}

	public String _getImageThumb(String s, String s1, String s2, boolean flag,
			String s3)
	{
		if (s3 == "")
			s3 = "fixnone";
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append(s).append("|").append(s1).append("|").append(s2);
		String s4 = URLEncoder.encode(Tools._md5(stringbuilder.toString()));
		String s5;
		StringBuilder stringbuilder1;
		if (!siteUrl.contains("http://"))
			s5 = (new StringBuilder()).append("http://").append(siteUrl)
					.toString();
		else
			s5 = siteUrl;
		stringbuilder1 = new StringBuilder(120);
		stringbuilder1.append(s5);
		if (Float.parseFloat(siteInfo.getPluginVersion()) >= 1.06F)
			stringbuilder1
					.append("/api/mobile/index.php?module=forumimage&version=2&aid=");
		else
			stringbuilder1.append("/forum.php?mod=image&aid=");
		stringbuilder1.append(s).append("&size=").append(s1).append("x")
				.append(s2).append("&key=").append(s4).append("&type=")
				.append(s3);
		if (flag)
			stringbuilder1.append("&nocache=yes");
		return stringbuilder1.toString();
	}

	protected void parseData()
	{
		_parse_rewrite();
		JSONArray jsonarray = json.optJSONArray("cache_custominfo_postno");
		HashMap hashmap = new HashMap();
		hashmap.put("ppp", json.optString("ppp"));
		JSONObject jsonobject = json.optJSONObject("thread");
		hashmap.put("tid", jsonobject.optString("tid"));
		hashmap.put("price", jsonobject.optString("price"));
		hashmap.put("author", jsonobject.optString("author"));
		hashmap.put("authorid", jsonobject.optString("authorid"));
		hashmap.put("subject", jsonobject.optString("subject"));
		hashmap.put("views", jsonobject.optString("views"));
		hashmap.put("replies", jsonobject.optString("replies"));
		hashmap.put("attachment", jsonobject.optString("attachment"));
		hashmap.put("freemessage", jsonobject.optString("freemessage"));
		JSONArray jsonarray1;
		if (json.optString("forum_threadpay") != null)
			if (!"false".equals(json.optString("forum_threadpay")))
				hashmap.put("threadpay", json.optString("forum_threadpay"));
			else
				hashmap.put("threadpay", "");
		hashmap.put("ucenterurl", siteUcenterUrl);
		jsonarray1 = json.optJSONArray("postlist");
		for (int i = 0; i < jsonarray1.length(); i++)
		{
			imageListHtmlResult = new StringBuilder();
			attachListHtmlResult = new StringBuilder();
			HashMap hashmap1 = new HashMap();
			JSONObject jsonobject1 = jsonarray1.optJSONObject(i);
			hashmap1.put("pid", jsonobject1.optString("pid"));
			hashmap1.put("tid", jsonobject1.optString("tid"));
			int j;
			String s;
			String s1;
			if (jsonobject1.optString("first").equals("1"))
				hashmap1.put(
						"subject",
						(new StringBuilder())
								.append("<div class=\"postsubject cl\">")
								.append(jsonobject1.optString("subject"))
								.append("</div>").toString());
			else
				hashmap1.put("subject", "");
			hashmap1.put("dateline", jsonobject1.optString("dateline"));
			if ("1".equals(jsonobject1.optString("anonymous")))
			{
				hashmap1.put("author",
						"<font color=\"#999999\">\u533F\u540D\u53D1\u8868</font>");
				hashmap1.put("authorid", "0");
			} else
			{
				hashmap1.put("author", jsonobject1.optString("author"));
				hashmap1.put("authorid", jsonobject1.optString("authorid"));
			}
			hashmap1.put("username", jsonobject1.optString("username"));
			hashmap1.put("groupid", jsonobject1.optString("groupid"));
			hashmap1.put("attachmentguest", "");
			hashmap1.put("threadpay", "");
			hashmap1.put("realnumber", jsonobject1.optString("number"));
			j = jsonobject1.optInt("status");
			s = "";
			if (Integer.parseInt(jsonobject1.optString("number")) < jsonarray
					.length())
			{
				String s3 = "";
				if (Integer.parseInt(jsonobject1.optString("number")) < 3)
					s3 = "<font style='color:#FF5500'>";
				if (jsonobject1.optInt("number") == 1
						&& jsonobject1.optInt("first") == 0)
				{
					StringBuilder stringbuilder4 = new StringBuilder();
					JSONObject jsonobject2;
					JSONArray jsonarray2;
					int k;
					JSONObject jsonobject3;
					Matcher matcher;
					String s6;
					StringBuilder stringbuilder5;
					String s7;
					if (s3 != "")
						s6 = s3;
					else
						s6 = "";
					stringbuilder5 = stringbuilder4.append(s6).append(mypost);
					if (s3 != "")
						s7 = "</font>";
					else
						s7 = "";
					s = stringbuilder5.append(s7).toString();
				} else
				{
					StringBuilder stringbuilder2 = new StringBuilder();
					String s4;
					StringBuilder stringbuilder3;
					String s5;
					if (s3 != "")
						s4 = s3;
					else
						s4 = "";
					stringbuilder3 = stringbuilder2.append(s4).append(
							jsonarray.optString(Integer.parseInt(jsonobject1
									.optString("number"))));
					if (s3 != "")
						s5 = "</font>";
					else
						s5 = "";
					s = stringbuilder3.append(s5).toString();
				}
			} else if (jsonarray.opt(0) != null)
				s = (new StringBuilder())
						.append(jsonobject1.optString("number"))
						.append(jsonarray.optString(0)).toString();
			hashmap1.put("number", s);
			if ((j & 0x10) != 16
					&& ("-1".equals(jsonobject1.optString("memberstatus"))
							|| !"".equals(hashmap1.get("authorid"))
							&& ((String) hashmap1.get("username")).equals("")
							|| ((String) hashmap1.get("groupid")).equals("4")
							|| ((String) hashmap1.get("groupid")).equals("5") || !"0"
								.equals(jsonobject1.optString("memberstatus"))))
				hashmap1.put(
						"message",
						"<div class=\"locked\">\u63D0\u793A:\u4F5C\u8005\u88AB\u7981\u6B62\u6216\u5220\u9664</div>");
			else if (jsonobject1.optString("first").equals("1")
					&& ((String) hashmap.get("threadpay")).equals("true"))
			{
				hashmap1.put("message", hashmap.get("freemessage"));
				hashmap1.put(
						"threadpay",
						(new StringBuilder())
								.append("<div class='locked' style='margin:10px;'><a onclick='javascript:showimage=true;DZ.ThreadPay(\"")
								.append(jsonobject1.optString("tid"))
								.append("\");'>\u672C\u4E3B\u9898\u4E3A\u4ED8\u8D39\u4E3B\u9898\u9700\u8981\u652F\u4ED8\u624D\u80FD\u6D4F\u89C8</a></div>")
								.toString());
			} else
			{
				if (jsonobject1.optJSONObject("attachments") != null)
				{
					if (attachmentList == null)
						attachmentList = new HashMap();
					JSONObject jsonobject4 = jsonobject1
							.optJSONObject("attachments");
					if (jsonobject4.names() != null)
					{
						int j1 = 0;
						while (j1 < jsonobject4.names().length())
						{
							HashMap hashmap2 = new HashMap();
							JSONObject jsonobject5 = jsonobject4
									.optJSONObject(jsonobject4.names()
											.optString(j1));
							hashmap2.put("aid", jsonobject5.optString("aid"));
							hashmap2.put("tid", jsonobject5.optString("tid"));
							hashmap2.put("uid", jsonobject5.optString("uid"));
							hashmap2.put("dateline",
									jsonobject5.optString("dateline"));
							hashmap2.put("filename",
									jsonobject5.optString("filename"));
							hashmap2.put("filesize",
									jsonobject5.optString("filesize"));
							hashmap2.put(
									"attachment",
									(new StringBuilder())
											.append(jsonobject5
													.optString("url"))
											.append(jsonobject5
													.optString("attachment"))
											.toString());
							hashmap2.put("remote",
									jsonobject5.optString("remote"));
							hashmap2.put("description",
									jsonobject5.optString("description"));
							hashmap2.put("readperm",
									jsonobject5.optString("readperm"));
							hashmap2.put("price",
									jsonobject5.optString("price"));
							hashmap2.put("width",
									jsonobject5.optString("width"));
							hashmap2.put("thumb",
									jsonobject5.optString("thumb"));
							hashmap2.put("picid",
									jsonobject5.optString("picid"));
							hashmap2.put("isimage",
									jsonobject5.optString("isimage"));
							hashmap2.put("ext", jsonobject5.optString("ext"));
							hashmap2.put("imgalt",
									jsonobject5.optString("imgalt"));
							hashmap2.put("attachsize",
									jsonobject5.optString("attachsize"));
							if (jsonobject5.optString("attachimg").equals("1"))
								hashmap2.put("attachimg", "1");
							else
								hashmap2.put("attachimg", "0");
							hashmap2.put("payed",
									jsonobject5.optString("payed"));
							hashmap2.put("url", jsonobject5.optString("url"));
							hashmap2.put("downloads",
									jsonobject5.optString("downloads"));
							attachmentList.put(
									(new StringBuilder())
											.append("attachment")
											.append(jsonobject5
													.optString("aid"))
											.toString(), hashmap2);
							j1++;
						}
					}
				}
				hashmap1.put(
						"message",
						_parse_Jammer(_parse_smily(_clearJavascript(_parse_url(_parse_reply(_parse_message(_parse_quote(jsonobject1
								.optString("message")))))))));
				if (jsonobject1.optJSONArray("imagelist") != null)
				{
					int i1 = 0;
					while (i1 < jsonobject1.optJSONArray("imagelist").length())
					{
						if (used_aids.get(jsonobject1.optJSONArray("imagelist")
								.optString(i1)) == null)
							if (DiscuzApp.isShowPicture)
								imageListHtmlResult
										.append(_parse_imagelist(jsonobject1
												.optJSONArray("imagelist")
												.optString(i1)));
							else
								imageListHtmlResult
										.append(_parse_no_imagelist(jsonobject1
												.optJSONArray("imagelist")
												.optString(i1)));
						i1++;
					}
					if (imageListHtmlResult.length() > 1)
						imageListHtmlResult
								.insert(0,
										"<div class=\"attachmentlist\"><div class=\"attachtitle\">\u56FE\u7247\u5217\u8868</div>")
								.append("</div>");
				}
				if (jsonobject1.optJSONArray("attachlist") != null)
				{
					int l = 0;
					while (l < jsonobject1.optJSONArray("attachlist").length())
					{
						if (used_aids.get(jsonobject1
								.optJSONArray("attachlist").optString(l)) == null)
							attachListHtmlResult
									.append(_parse_attachlist(jsonobject1
											.optJSONArray("attachlist")
											.optString(l)));
						l++;
					}
					if (attachListHtmlResult.length() > 1)
						attachListHtmlResult
								.insert(0,
										"<div class=\"attachmentlist\"><div class=\"attachtitle\">\u9644\u4EF6\u5217\u8868</div>")
								.append("</div>");
				} else if (jsonobject1.optString("attachment") != null
						&& Integer.valueOf(jsonobject1.optString("attachment"))
								.intValue() > 0)
					hashmap1.put(
							"attachmentguest",
							"<div class='locked' style='margin:10px;'><a onclick='javascript:showimage=true;DZ.toLogin()'>\u767B\u5F55\u65B9\u53EF\u67E5\u770B\u9644\u4EF6</a></div>");
			}
			if ((j & 0x10) != 16)
				s1 = (String) hashmap1.get("author");
			else
				s1 = "\u5FAE\u535A\u8BC4\u8BBA";
			JSONObject jsonobject2 = null;
			JSONObject jsonobject3 = null;
			JSONArray jsonarray2 = null;
			Matcher matcher;
			if (jsonobject1.optString("first").equals("1"))
			{
				jsonobject2 = json.optJSONObject("threadsortshow");
				if (jsonobject2 != null)
				{
					StringBuilder stringbuilder1 = new StringBuilder();
					stringbuilder1
							.append("<table class=\"threadsort\"><caption>")
							.append(jsonobject2.optString("threadsortname"))
							.append("</caption><tbody>");
					jsonarray2 = jsonobject2.optJSONArray("optionlist");
					if (jsonarray2 != null && jsonarray2.length() > 0)
					{
						int k = 0;
						while (k < jsonarray2.length())
						{
							jsonobject3 = jsonarray2.optJSONObject(k);
							stringbuilder1.append("<tr><th>")
									.append(jsonobject3.optString("title"))
									.append("</th><td>");
							String s2 = jsonobject3.optString("value");
							matcher = Pattern.compile("<image src=\"(.*?)\">")
									.matcher(s2);
							if (matcher.find())
								stringbuilder1.append("<image src=\"")
										.append(siteUrl).append("/")
										.append(matcher.group(1)).append("\">");
							else if (s2.toLowerCase().contains("onclick"))
								stringbuilder1
										.append("[\u624B\u673A\u5BA2\u6237\u7AEF\u4E0D\u652F\u6301\u663E\u793A]");
							else
								stringbuilder1.append(s2);
							stringbuilder1.append("</td></tr>");
							k++;
						}
					}
					stringbuilder1.append("</tbody></table>");
					hashmap1.put("sort", stringbuilder1.toString());
				}
			}
			StringBuilder stringbuilder = ViewThreadNodePostListHtmlResult;
			String as[] = new String[13];
			as[0] = "";
			as[1] = siteUcenterUrl;
			as[2] = (String) hashmap1.get("message");
			as[3] = (String) hashmap1.get("authorid");
			as[4] = s1;
			as[5] = (String) hashmap1.get("number");
			as[6] = (String) hashmap1.get("dateline");
			as[7] = (String) hashmap1.get("threadpay");
			as[8] = (String) hashmap1.get("attachmentguest");
			as[9] = imageListHtmlResult.toString();
			as[10] = attachListHtmlResult.toString();
			as[11] = (String) hashmap1.get("pid");
			as[12] = (String) hashmap1.get("sort");
			stringbuilder.append(parseViewThreadPostListResult(as));
			viewThreadData.setPostListValues(hashmap1);
		}

		viewThreadData
				.setViewThreadPostListHtml(ViewThreadNodePostListHtmlResult);
		viewThreadData.setThreadValueList(hashmap);
		(new setAttachmentListDBTask()).execute(new Void[0]);
		viewThreadData.setAttachmentList(attachmentList);
		if (json.optJSONObject("allowperm") != null)
			viewThreadData.setAllowPerm(json.optJSONObject("allowperm"));
	}

	public String parseViewThreadPostListResult(String as[])
	{
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append("<li class=\"postnode\"");
		if (!as[5].contains(mypost))
			stringbuilder
					.append(" onTouchStart=\"this.className='postnode postnodetouch';\" onTouchEnd=\"this.className='postnode';\"");
		stringbuilder.append(">");
		stringbuilder.append("<div class=\"author cl\"");
		stringbuilder.append(" onclick=\"viewuserinfo('").append(as[3])
				.append("','").append(as[4]).append("');\" ");
		stringbuilder.append(">").append("<div class=\"postno y xs9\">")
				.append(as[5]).append("</div>");
		if (DiscuzApp.isShowPicture)
			stringbuilder
					.append("<div class=\"avatar z\"><img src=\"")
					.append(as[1])
					.append("/avatar.php?uid=")
					.append(as[3])
					.append("&size=small\" width=\"32\" height=\"32\" onError=\"javascript:this.style.backgroundImage='url(file:///android_asset/images/sitestyle/avatar.png)';\"/></div>");
		stringbuilder.append("<div class=\"profile z\"><p class=\"xs8\">")
				.append(as[4]).append("</p><p class=\"dateline xs6\">")
				.append(as[6]).append("</p></div>").append("</div>")
				.append("<div class=\"message\"");
		stringbuilder.append(" onclick=\"replybypid('").append(as[11])
				.append("');\" ");
		if (!as[5].contains(mypost))
			stringbuilder
					.append(" onTouchStart=\"this.className='message messagetouch';\" onTouchEnd=\"this.className='message';\"");
		stringbuilder.append("><div class=\"triangle\"></div>").append(
				"<div class=\"inner_triangle\"></div>");
		if (as[12] != null)
			stringbuilder.append(as[12]);
		stringbuilder.append(as[2]).append(as[7]).append(as[8]).append(as[9])
				.append(as[10]).append("</div></li>");
		System.out.println(stringbuilder.toString());
		return stringbuilder.toString();
	}

	private StringBuilder ViewThreadNodePostListHtmlResult;
	private StringBuilder attachListHtmlResult;
	private HashMap attachmentList;
	private StringBuilder imageListHtmlResult;
	private int imageMaxHeight;
	private int imageMaxWidth;
	private String mypost;
	private HashMap rewritepreg;
	private HashMap rewritetag;
	private SiteInfo siteInfo;
	private String siteUcenterUrl;
	private String siteUrl;
	private HashMap used_aids;
}
// 2131296256