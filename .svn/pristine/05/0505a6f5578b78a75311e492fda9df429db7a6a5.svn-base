package net.discuz.activity.siteview;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.adapter.ForumNavAdapter;
import net.discuz.adapter.ReplyAttachListAdapter;
import net.discuz.app.DiscuzApp;
import net.discuz.json.JsonParser;
import net.discuz.model.AllowPerm;
import net.discuz.model.PostDraft;
import net.discuz.source.DEBUG;
import net.discuz.source.DJsonReader;
import net.discuz.source.DialogPopup;
import net.discuz.source.HttpRequest;
import net.discuz.source.ImageSelector;
import net.discuz.source.PostSender;
import net.discuz.source.InterFace.JsonParseHelperCallBack;
import net.discuz.source.InterFace.onPostSending;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.popupwindow.Postpic;
import net.discuz.source.storage.PostDraftDBHelper;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.HttpConnThread;
import net.discuz.tools.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class PostThreadActivity extends DiscuzBaseActivity
{
	private class getForumCheckPost_AsyncTask extends AsyncTask
	{

		protected Object doInBackground(Object aobj[])
		{
			return doInBackground((String[]) aobj);
		}

		protected Void doInBackground(String as[])
		{
			HttpRequest httprequest;
			String s1;
			String s = as[0];
			httprequest = new HttpRequest(siteAppId);
			String as1[] = new String[4];
			as1[0] = siteAppId;
			as1[1] = "module=newthread";
			as1[2] = "submodule=checkpost";
			as1[3] = (new StringBuilder()).append("fid=").append(s).toString();
			s1 = Core.getSiteUrl(as1);
			String s2;
			try
			{
				String s4 = httprequest._get(s1);
				s2 = s4;
			} catch (Exception e)
			{
				e.printStackTrace();
			}

			if (isCancelled())
				return null;
			else
			{
				s2 = null;
				if (s2 != null)
				{
					DJsonReader djsonreader = new DJsonReader(s2);
					try
					{
						djsonreader._jsonParse();
					} catch (JSONException jsonexception)
					{
						jsonexception.printStackTrace();
					}
					if (djsonreader.isjson)
					{
						String as2[] = djsonreader._getMessage();
						if (as2[0] != null)
						{
							canSubmit(null);
							ShowMessageByHandler(as2, 3);
						} else
						{
							onCheckPostSending = true;
							JSONObject jsonobject = djsonreader._getVariables();
							String s3 = jsonobject.optString("formhash");
							DiscuzApp.getInstance().getSiteInfo(siteAppId)
									.getLoginUser().setFormHash(s3);
							JSONObject jsonobject1 = jsonobject
									.optJSONObject("allowperm");
							allowPerm = new AllowPerm(jsonobject1);
							uploadHash = allowPerm.getUploadHash();
							canSubmit(allowPerm);
						}
					} else
					{
						canSubmit(null);
					}
				} else
				{
					canSubmit(null);
				}
				onCheckPostSending = false;
			}
			return null;
		}

		protected void onPostExecute(Object obj)
		{
			onPostExecute((Void) obj);
		}

		protected void onPostExecute(Void void1)
		{
			dismissLoading();
			super.onPostExecute(void1);
		}

		protected void onPreExecute()
		{
			super.onPreExecute();
			showLoading("\u9A8C\u8BC1\u60A8\u7684\u53D1\u5E16\u6743\u9650");
			onCheckPostSending = true;
		}

		private getForumCheckPost_AsyncTask()
		{
			super();
		}
	}

	public PostThreadActivity()
	{
		postDraft = null;
		message = "";
		send_post_type = 1;
		fid = "";
		forumname = null;
		tid = "";
		uploadHash = "";
		canSubmit = false;
		forumNavData = null;
		selectFup = "";
		selectSub = "";
		selectFupForumName = "";
		selectSubForumName = "";
		selectThreadType = 0;
		selectThreadTypeValue = "";
		lastselectFupPos = -1;
		onCheckPostSending = false;
		uploadItems = null;
		pditems = null;
		threadTypes_now = null;
		_aidList = null;
		attachlist = new ArrayList();
		attachRealIdlist = null;
		attachlistPostDraftId = new ArrayList();
		replyAttachListAdapter = null;
		postpic = null;
		imageSelector = null;
		imageTmpName = null;
		post_content_input = null;
		post_subject_input = null;
		haveContent = false;
		haveSubject = false;
		contentWatcher = new TextWatcher()
		{

			public void afterTextChanged(Editable editable)
			{}

			public void beforeTextChanged(CharSequence charsequence, int i,
					int j, int k)
			{}

			public void onTextChanged(CharSequence charsequence, int i, int j,
					int k)
			{
				if (charsequence != null && charsequence.length() > 0)
					haveContent = true;
				else
					haveContent = false;
				if (haveSubject && haveContent)
				{
					site_navbar.setCustomBtnVisibility(true);
					return;
				} else
				{
					site_navbar.setCustomBtnVisibility(false);
					return;
				}
			}
		};
		subjectWatcher = new TextWatcher()
		{

			public void afterTextChanged(Editable editable)
			{}

			public void beforeTextChanged(CharSequence charsequence, int i,
					int j, int k)
			{}

			public void onTextChanged(CharSequence charsequence, int i, int j,
					int k)
			{
				if (charsequence != null && charsequence.length() > 0)
					haveSubject = true;
				else
					haveSubject = false;
				if (haveSubject && haveContent)
				{
					site_navbar.setCustomBtnVisibility(true);
					return;
				} else
				{
					site_navbar.setCustomBtnVisibility(false);
					return;
				}
			}
		};
		onClickListener = new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				switch (view.getId())
				{
				default:
					return;

				case 2131230910:
					initPopupWindow_ForumNav(
							threadTypes_now,
							null,
							new android.widget.AdapterView.OnItemClickListener()
							{

								public void onItemClick(
										AdapterView adapterview, View view,
										int i, long l)
								{
									final String s = threadTypes_now[i];
									selectThreadType(i, threadTypes_now);
									runOnUiThread(new Runnable()
									{

										public void run()
										{
											post_threadtype_text
													.setTextColor(0xff000000);
											post_threadtype_text.setText(s
													.split("\t")[1]);
										}
									});
									if (fupPopupWindow.isShowing())
										fupPopupWindow.dismiss();
								}
							});
					return;

				case 2131230915:
					MobclickAgent
							.onEvent(PostThreadActivity.this, "c_trdimage");
					break;
				}
				DiscuzStats.add(siteAppId, "c_trdimage");
				DEBUG.o("posttopic_getphoto click!");
				Core.getInstance()._hideSoftInput(PostThreadActivity.this);
				postpic = new Postpic(PostThreadActivity.this);
				postpic.setOnPostPic(new net.discuz.source.popupwindow.Postpic.onPostPic()
				{

					public void Error()
					{}

					public void Suceess(Integer integer)
					{
						if (integer.intValue() == 1)
						{
							imageTmpName = imageSelector
									._getCameraTmpFileName();
							imageSelector._useCamera(imageTmpName);
						} else if (integer.intValue() == 2)
						{
							imageSelector._useAlbum();
							return;
						}
					}
				});
				postpic._show();
			}
		};
		httpConnListener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
		{

			public void onFailed(Exception exception)
			{}

			public void onSucceed(String s, String s1)
			{
				dismissLoading();
				if (s != null)
				{
					try
					{
						DJsonReader djsonreader = new DJsonReader(s);
						djsonreader._jsonParse();
						if (djsonreader.isjson)
						{
							djsonreader._debug();
							(new JsonParser(PostThreadActivity.this)).forumNav(
									djsonreader._getVariables(),
									new JsonParseHelperCallBack()
									{

										public void onParseBegin()
										{}

										public void onParseFinish(Object obj)
										{
											onParseFinish((net.discuz.json.helper.ForumNavParseHelper.ForumNavData) obj);
										}

										public void onParseFinish(
												net.discuz.json.helper.ForumNavParseHelper.ForumNavData forumnavdata)
										{
											forumNavData = forumnavdata;
											getThreadType(fid);
											DEBUG.o((new StringBuilder())
													.append("forumNavData:")
													.append(forumNavData)
													.toString());
										}
									});
							return;
						}
					} catch (Exception exception)
					{
						exception.printStackTrace();
					}
				}
				forumNavData = null;
				return;
			}
		};
		onSelectSucceed = new net.discuz.source.ImageSelector.OnSelectSucceed()
		{

			public void onSucceed(String s)
			{
				if (!Tools.stringIsNullOrEmpty(s))
				{
					attachlistPostDraftId.add(Long.valueOf(PostDraftDBHelper
							.getInstance().addItemImageForReply(s)));
					attachlist.add(s);
					if (replyAttachListAdapter == null)
						replyAttachListAdapter = new ReplyAttachListAdapter(
								PostThreadActivity.this);
					replyAttachListAdapter.setList(attachlist,
							attachlistPostDraftId);
					posttopic_fast_attach_gallery
							.setAdapter(replyAttachListAdapter);
					runOnUiThread(new Runnable()
					{

						public void run()
						{
							posttopic_fast_attach_controll_box.setVisibility(0);
						}
					});
					if (replyAttachListAdapter.getCount() > 0)
						posttopic_fast_attach_gallery.setSelection(-1
								+ replyAttachListAdapter.getCount(), true);
				}
			}
		};
	}

	private void _getBundleParams()
	{
		Bundle bundle = getIntent().getExtras();
		if (bundle != null)
		{
			siteAppId = bundle.getString("siteappid");
			send_post_type = bundle.getInt("SEND_POST_TYPE");
			uploadHash = bundle.getString("UPLOAD_HASH");
			switch (send_post_type)
			{
			default:
			case 1:
				fid = bundle.getString("fid");
				forumname = bundle.getString("forumname");
				break;
			case 2:
				tid = bundle.getString("tid");
				break;
			}
			if (fid != null && send_post_type == 1)
				canSubmit = true;
			else if (tid != null && send_post_type == 2)
				canSubmit = true;
			if (fid != null)
			{
				getThreadType(fid);
				return;
			}
		} else
		{
			finish();
		}

	}

	private void _initListener()
	{
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				finish();
			}
		});
		site_navbar
				.setOnCustomBtnClicked(new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						if (attachlist.size() > 0)
						{
							Intent intent = new Intent();
							intent.putExtra("SEND_POST_TYPE", 2);
							intent.putExtra("UPLOAD_HASH", uploadHash);
							intent.putExtra("siteappid", siteAppId);
							intent.putExtra("tid", tid);
							intent.setClass(PostThreadActivity.this,
									uploadFileList.class);
							startActivityForResult(intent, 98);
							return;
						} else
						{
							doSubmit();
							return;
						}
					}
				});
		site_navbar.setCustomBtnVisibility(false);
		posttopic_getphoto.setOnClickListener(onClickListener);
		post_content_input.setOnClickListener(onClickListener);
		post_content_input.addTextChangedListener(contentWatcher);
		post_subject_input.addTextChangedListener(subjectWatcher);
		post_threadtype_box.setOnClickListener(onClickListener);
		posttopic_fast_attach_gallery
				.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener()
				{

					public boolean onItemLongClick(AdapterView adapterview,
							View view, final int position, long l)
					{
						final DialogPopup dialogpopup = new DialogPopup(
								PostThreadActivity.this);
						dialogpopup._build(0, 0, 0, 0, 0);
						dialogpopup
								._setMessage(R.string.message_delete_picture);
						dialogpopup._setbtnClick(
								new android.view.View.OnClickListener()
								{

									public void onClick(View view)
									{
										replyAttachListAdapter.removeListItem(
												position, false);
										dialogpopup._dismiss();
										if (attachlist.size() == 0)
											posttopic_fast_attach_controll_box
													.setVisibility(8);
									}
								}, new android.view.View.OnClickListener()
								{

									public void onClick(View view)
									{
										dialogpopup._dismiss();
									}
								});
						dialogpopup._show();
						return false;
					}
				});
	}

	private void canSubmit(AllowPerm allowperm)
	{
		if (allowperm == null)
		{
			canSubmit = false;
		} else
		{
			if (send_post_type == 1)
			{
				if (allowperm.getAllowPost())
					canSubmit = true;
				else
					canSubmit = false;
			} else if (send_post_type == 2)
				if (allowperm.getAllowReply())
					canSubmit = true;
				else
					canSubmit = false;
			if (canSubmit)
				runOnUiThread(new Runnable()
				{

					public void run()
					{
						forumnav_fup_name.setText(Html
								.fromHtml(selectFupForumName));
						if (!"".equals(selectSub))
							post_forumnav_sub_name.setText(selectSubForumName);
					}
				});
			else
				runOnUiThread(new Runnable()
				{

					public void run()
					{
						ShowMessageByHandler(R.string.message_site_error, 3);
						if ("".equals(selectSub))
							forumnav_fup_name.setText("");
					}
				});
			if (fupPopupWindow.isShowing())
			{
				runOnUiThread(new Runnable()
				{

					public void run()
					{
						fupPopupWindow.dismiss();
					}
				});
				return;
			}
		}
	}

	private void doSubmit()
	{
		if (send_post_type == 1)
		{
			if (!"".equals(selectSub))
				fid = selectSub;
			if ("".equals(selectSub) && !"".equals(selectFup))
				fid = selectFup;
			if ("".equals(fid) || fid == null)
			{
				ShowMessageByHandler("请指定版块", 3);
				return;
			}
			String s = subject_input.getText().toString().trim();
			if ("".equals(s))
			{
				ShowMessageByHandler("请输入标题", 2);
				return;
			}
			if (s.length() < 3)
			{
				ShowMessageByHandler("标题请输入3个字以上", 2);
				return;
			}
			postsender._setSubject(s);
		}
		if (!canSubmit)
			if (onCheckPostSending)
			{
				ShowMessageByHandler("正在验证发帖权限", 2);
				return;
			} else
			{
				ShowMessageByHandler("对不起您不能在本版发帖", 3);
				return;
			}
		if (!subject_input.getText().toString().trim().equals(""))
			postsender._setSubject(subject_input.getText().toString().trim());
		if (post_content_input.getText().toString().trim().equals("")
				&& attachlist.size() == 0)
		{
			ShowMessageByHandler("请输入内容或上传图片", 2);
			return;
		}
		postsender._setMessage(post_content_input.getText().toString().trim());
		if (attachRealIdlist != null)
			postsender._setAttach(attachRealIdlist);
		showLoading("正在发送,请稍候...");
		PostSender postsender1 = postsender;
		String as[] = new String[1];
		as[0] = fid;
		postsender1._send(as);
	}

	private void getThreadType(String s)
	{
		while (forumNavData == null || forumNavData.threadTypes == null)
			return;
		if (forumNavData.threadTypes.get(s) != null)
		{
			String as[] = (String[]) (String[]) forumNavData.threadTypes.get(s);
			if (forumNavData.requiredThreadTypes.get(s) != null)
			{
				threadTypes_now = new String[1 + as.length];
				threadTypes_now[0] = "0\t(不选择)";
				for (int i = 0; i < as.length; i++)
					threadTypes_now[i + 1] = Html.fromHtml(as[i]).toString();

			} else
			{
				threadTypes_now = as;
				post_threadtype_text.setTextColor(0xff000000);
				post_threadtype_text.setText(threadTypes_now[0]);
			}
			selectThreadType(0, threadTypes_now);
			visibleView(post_threadtype_box, true);
			post_threadtype_btn.setOnClickListener(onClickListener);
			return;
		} else
		{
			threadTypes_now = null;
			post_threadtype_btn.setOnClickListener(null);
			visibleView(post_threadtype_box, false);
			return;
		}
	}

	private void initPopupWindow_ForumNav(String as[], HashMap hashmap,
			android.widget.AdapterView.OnItemClickListener onitemclicklistener)
	{
		forumnavview = getLayoutInflater().inflate(
				R.layout.post_draft_submit_forumnav_popupwindow, null);
		fupPopupWindow = new PopupWindow(forumnavview, -1, -2, true);
		fupPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.alpha_bg));
		fupPopupWindow.setOutsideTouchable(true);
		fupPopupWindow.showAtLocation(findViewById(R.id.DiscuzActivityBox), 80,
				0, 0);
		ListView listview = (ListView) forumnavview
				.findViewById(R.id.mListView);
		listview.setDivider(getResources().getDrawable(R.drawable.list_line));
		ForumNavAdapter forumnavadapter = new ForumNavAdapter(this, as, hashmap);
		listview.setAdapter(forumnavadapter);
		if (lastselectFupPos > -1
				&& forumnavadapter.getCount() > lastselectFupPos)
		{
			listview.setSelection(lastselectFupPos);
			DEBUG.o((new StringBuilder())
					.append("=====lastselectFupPos======= ")
					.append(lastselectFupPos).toString());
		}
		listview.setOnItemClickListener(onitemclicklistener);
	}

	private void selectThreadType(int i, String as[])
	{
		selectThreadType = i;
		selectThreadTypeValue = as[i].split("\t")[0];
		postsender._setThreadType(selectThreadTypeValue);
	}

	private void visibleView(final View view, boolean flag)
	{
		final int visiblestate;
		if (flag)
			visiblestate = 0;
		else
			visiblestate = 8;
		runOnUiThread(new Runnable()
		{

			public void run()
			{
				view.setVisibility(visiblestate);
			}
		});
	}

	protected void init()
	{
		super.init();
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		site_navbar.setTitleClickable(false);
		site_navbar.setTitle("\u53D1\u5E16");
		site_navbar.setCustomBtnText("\u53D1\u8868");
		subject_input = (EditText) findViewById(R.id.post_subject_input);
		post_threadtype_box = (RelativeLayout) findViewById(R.id.post_threadtype_box);
		post_threadtype_text = (TextView) findViewById(R.id.post_threadtype_text);
		if (send_post_type == 2)
			post_forumnav_fup_box.setVisibility(8);
		posttopic_getphoto = findViewById(R.id.posttopic_getphoto);
		posttopic_fast_attach_controll_box = (RelativeLayout) findViewById(R.id.posttopic_fast_attach_controll_box);
		posttopic_fast_attach_gallery = (Gallery) findViewById(R.id.posttopic_fast_attach_gallery);
		post_content_input = (EditText) findViewById(R.id.post_content_input);
		post_subject_input = (EditText) findViewById(R.id.post_subject_input);
	}

	protected void onActivityResult(int i, int j, Intent intent)
	{
		if (i != 98)
		{
			String s;
			switch (i)
			{
			default:
				return;

			case 21: // '\025'
				String s1 = (new StringBuilder())
						.append("/sdcard/DCIM/Camera/").append(imageTmpName)
						.toString();
				File file = new File(s1);
				if (file.isFile() && file.exists())
				{
					imageSelector = new ImageSelector(this);
					imageSelector.setOnUploadSucceed(onSelectSucceed);
					imageSelector.upLoadPopupWindow(
							findViewById(R.id.DiscuzActivityBox), s1);
					return;
				}
				break;

			case 22: // '\026'
				s = imageSelector._getImagePath(intent, this);
				if (!Tools.stringIsNullOrEmpty(s))
				{
					imageSelector = new ImageSelector(this);
					imageSelector.setOnUploadSucceed(onSelectSucceed);
					imageSelector.upLoadPopupWindow(
							findViewById(R.id.DiscuzActivityBox), s);
				}
			}
		} else
		{
			if (j == -1)
			{
				attachRealIdlist = intent
						.getStringArrayListExtra("attachidlist");
				doSubmit();
			}
		}

	}

	protected void onBackKeyEvent()
	{
		if (postpic != null && postpic._isShowing())
		{
			postpic._dismiss();
			return;
		} else
		{
			super.onBackKeyEvent();
			return;
		}
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		MobclickAgent.onEvent(this, "v_post");
		DiscuzStats.add(siteAppId, "v_post");
		getWindow().setSoftInputMode(32);
		_getBundleParams();
		setContentView(R.layout.post_thread_activity);
		init();
		_initListener();
		postDraft = PostDraftDBHelper.getInstance();
		postDraft.deleteAll();
		postsender = new PostSender(this);
		switch (send_post_type)
		{
		case 1:
			postsender._isSendThread();
			break;
		case 2:
			postsender._isSendReply();
			break;
		default:
		}

		postsender._setOnSend(new onPostSending()
		{

			public void sendBefore()
			{}

			public void sendFaild()
			{}

			public void sendFinish()
			{
				dismissLoading();
			}

			public void sendSuccess(DJsonReader djsonreader)
			{
				MobclickAgent.onEvent(PostThreadActivity.this, "suc_sendtrd");
				DiscuzStats.add(siteAppId, "suc_sendtrd");
				postDraft.deleteAll();
				setResult(-1);
				if (send_post_type == 1 && djsonreader.isjson)
				{
					String s1 = djsonreader._getVariables().optString("tid");
					Intent intent = new Intent();
					intent.setClass(PostThreadActivity.this,
							siteview_forumviewthread.class);
					intent.putExtra("tid", s1);
					intent.putExtra("forumname", "\u67E5\u770B\u65B0\u5E16");
					intent.putExtra("siteappid", siteAppId);
					startActivity(intent);
				}
				finish();
			}
		});
		postsender._setMessage(message);
		PostDraft postdraft;
		if (send_post_type == 1)
		{
			HttpConnThread httpconnthread = new HttpConnThread(siteAppId, 1);
			String as[] = new String[2];
			as[0] = siteAppId;
			as[1] = "module=forumnav";
			httpconnthread.setUrl(Core.getSiteUrl(as));
			String s = getClass().getSimpleName();
			httpconnthread.setFilter(s);
			DiscuzApp.getInstance().setHttpConnListener(s, httpConnListener);
			DiscuzApp.getInstance().sendHttpConnThread(httpconnthread);
		} else
		{
			site_navbar.setCustomBtnVisibility(false);
			doSubmit();
		}
		postdraft = postDraft.getItemForText();
		if (postdraft != null)
			subject_input.setText(postdraft.getValue());
		if (fid != null)
			if (forumname == null)
				;
		imageSelector = new ImageSelector(this);
		getWindow().setSoftInputMode(18);
		return;
	}

	protected void onDestroy()
	{
		uploadItems = null;
		pditems = null;
		forumNavData = null;
		fupPopupWindow = null;
		forumnavview = null;
		forumnav_fup_name = null;
		forumnav_fup_btn = null;
		site_navbar = null;
		postsender = null;
		allowPerm = null;
		tid = null;
		post_threadtype_text = null;
		post_threadtype_box = null;
		super.onDestroy();
	}

	private ArrayList _aidList;
	private AllowPerm allowPerm;
	private ArrayList attachRealIdlist;
	private ArrayList attachlist;
	private ArrayList attachlistPostDraftId;
	private boolean canPost;
	private boolean canReply;
	private boolean canSubmit;
	private TextWatcher contentWatcher;
	private String fid;
	private net.discuz.json.helper.ForumNavParseHelper.ForumNavData forumNavData;
	private String forumname;
	private RelativeLayout forumnav_fup_btn;
	private TextView forumnav_fup_name;
	private View forumnavview;
	private PopupWindow fupPopupWindow;
	private boolean haveContent;
	private boolean haveSubject;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener httpConnListener;
	private ImageSelector imageSelector;
	public String imageTmpName;
	private int lastselectFupPos;
	private String message;
	private boolean onCheckPostSending;
	private android.view.View.OnClickListener onClickListener;
	private net.discuz.source.ImageSelector.OnSelectSucceed onSelectSucceed;
	private ArrayList pditems;
	private PostDraftDBHelper postDraft;
	private EditText post_content_input;
	private LinearLayout post_forumnav_fup_box;
	private LinearLayout post_forumnav_sub_box;
	private RelativeLayout post_forumnav_sub_btn;
	private TextView post_forumnav_sub_name;
	private LinearLayout post_subject_box;
	private EditText post_subject_input;
	private RelativeLayout post_threadtype_box;
	private RelativeLayout post_threadtype_btn;
	private TextView post_threadtype_text;
	private Postpic postpic;
	private PostSender postsender;
	private RelativeLayout posttopic_fast_attach_controll_box;
	private Gallery posttopic_fast_attach_gallery;
	private View posttopic_getphoto;
	private ReplyAttachListAdapter replyAttachListAdapter;
	private String selectFup;
	private String selectFupForumName;
	private String selectSub;
	private String selectSubForumName;
	private int selectThreadType;
	private String selectThreadTypeValue;
	private int send_post_type;
	private SiteNavbar site_navbar;
	private String subFids[];
	private TextWatcher subjectWatcher;
	private EditText subject_input;
	private String threadTypes_now[];
	private Bitmap thumbImage;
	private String tid;
	private String uploadHash;
	private ArrayList uploadItems;

}
// 2131296256