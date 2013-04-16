package net.discuz.activity.siteview;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.adapter.ReplyAttachListAdapter;
import net.discuz.app.DiscuzApp;
import net.discuz.json.JsonParser;
import net.discuz.model.AllowPerm;
import net.discuz.model.PostDraft;
import net.discuz.model.ViewThreadData;
import net.discuz.source.AllowPermManager;
import net.discuz.source.DEBUG;
import net.discuz.source.DJsonReader;
import net.discuz.source.DialogPopup;
import net.discuz.source.HttpRequest;
import net.discuz.source.ImageSelector;
import net.discuz.source.InitSiteData;
import net.discuz.source.PostSender;
import net.discuz.source.ShowMessage;
import net.discuz.source.InterFace.InterfaceErrorException;
import net.discuz.source.InterFace.JsonParseHelperCallBack;
import net.discuz.source.InterFace.OnLogin;
import net.discuz.source.InterFace.onPostSending;
import net.discuz.source.WebInterFace.ViewThreadJavaScriptInterFace;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.popupwindow.Postpic;
import net.discuz.source.popupwindow.ViewThreadBodyClick;
import net.discuz.source.storage.PostDraftDBHelper;
import net.discuz.source.widget.DWebView_TouchEvent;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.HttpConnThread;
import net.discuz.tools.Tools;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

/**
 * 贴子内容查看，重要
 * 
 * @author lkh
 * 
 */
public class siteview_forumviewthread extends DiscuzBaseActivity
{
	private class favoritePostTask extends AsyncTask
	{

		protected Object doInBackground(Object aobj[])
		{
			return doInBackground((String[]) aobj);
		}

		protected String doInBackground(String as[])
		{
			HttpRequest httprequest;
			String s;
			HashMap hashmap;
			httprequest = new HttpRequest(siteAppId);
			String as1[] = new String[4];
			as1[0] = siteAppId;
			as1[1] = "module=favthread";
			as1[2] = "favoritesubmit=yes";
			as1[3] = (new StringBuilder()).append("id=").append(tid).toString();
			s = Core.getSiteUrl(as1);
			hashmap = new HashMap();
			hashmap.put("formhash",
					DiscuzApp.getInstance().getLoginUser(siteAppId)
							.getFormHash());
			try
			{
				String s1 = httprequest._post(s, null, hashmap, "GBK");
				if (isCancelled())
					return null;
				String as2[];
				DJsonReader djsonreader = new DJsonReader(s1);
				djsonreader._jsonParse();
				djsonreader._debug();
				as2 = djsonreader._getMessage();
				if (as2 != null)
				{
					if (as2[1] != null)
					{
						if (as2[0].indexOf("success") == -1)
						{
							JSONException jsonexception;
							if (as2[0].indexOf("repeat") != -1)
							{
								ShowMessageByHandler(as2, 2);
							} else
							{
								ShowMessageByHandler(as2, 3);
							}
						} else
						{
							ShowMessageByHandler(as2, 1);
						}
					}
				}
			} catch (JSONException je)
			{
				je.printStackTrace();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			dismissLoading();
			return null;
		}

		protected void onPostExecute(Object obj)
		{
			onPostExecute((String) obj);
		}

		protected void onPostExecute(String s)
		{
			super.onPostExecute(s);
		}

		private favoritePostTask()
		{
			super();
		}

	}

	public siteview_forumviewthread()
	{
		reply_fast_box = null;
		reply_input = null;
		tid = null;
		forumname = null;
		discuzWebView = null;
		imageSelect = null;
		url = null;
		page = 1;
		isRefresh = false;
		isFavorite = false;
		isOnlyAuthor = false;
		viewThreadFilter = getClass().getName();
		nextPageFilter = (new StringBuilder()).append(viewThreadFilter)
				.append("nextpage").toString();
		viewPidFilter = (new StringBuilder()).append(viewThreadFilter)
				.append("viewpid").toString();
		extgetparams = null;
		ThreadValueList = new HashMap();
		postListValues = new HashMap();
		allowPerm = null;
		webViewIsError = false;
		viewThreadCachePath = "viewthread/";
		isReplying = false;
		imageSelector = null;
		replyAttachListAdapter = null;
		attachlist = new ArrayList();
		attachRealIdlist = null;
		attachlistPostDraftId = new ArrayList();
		uploadHash = "";

		// 下拉菜单（三个按钮：跳页、收藏、楼主）/ 下方的回帖
		clickListener = new android.view.View.OnClickListener()
		{
			public void onClick(View view)
			{
				switch (view.getId())
				{
				default:
					return;
				case R.id.jump_btn: // 跳页
					MobclickAgent.onEvent(siteview_forumviewthread.this,
							"c_jump");
					DiscuzStats.add(siteAppId, "c_jump");
					site_navbar.dissmissButtonsList();
					if (page_controller.isShown())
					{
						page_controller.setVisibility(8);
						reply_fast_box.setVisibility(0);
						return;
					} else
					{
						page_controller.setVisibility(0);
						reply_fast_box.setVisibility(8);
						changePageControlNum();
						gotopage_input.setFocusable(true);
						gotopage_input.requestFocus();
						core._showSoftInput(gotopage_input);
						return;
					}
				case R.id.fav_btn: // 收藏
					MobclickAgent.onEvent(siteview_forumviewthread.this,
							"c_addfavtrd");
					DiscuzStats.add(siteAppId, "c_addfavtrd");
					site_navbar.dissmissButtonsList();
					showLoading("正在收藏...");
					(new favoritePostTask()).execute(new String[0]);
					return;
				case R.id.lz_btn:// 楼主
					MobclickAgent.onEvent(siteview_forumviewthread.this,
							"c_boss");
					DiscuzStats.add(siteAppId, "c_boss");
					site_navbar.dissmissButtonsList();
					if (ThreadValueList == null)
					{
						ShowMessageByHandler("请等待帖子加载完毕", 2);
						return;
					}
					if (!isOnlyAuthor)
					{
						isOnlyAuthor = true;
						webViewIsError = true;
						extgetparams = (new StringBuilder())
								.append("authorid=")
								.append((String) ThreadValueList
										.get("authorid")).toString();
						refresh("只看楼主", 1);
					} else
					{
						isOnlyAuthor = false;
						webViewIsError = true;
						extgetparams = null;
						refresh("查看全部", 1);
					}
					page_controller.setVisibility(8);
					reply_fast_box.setVisibility(0);
					core._hideSoftInput(gotopage_input);
					return;

				case R.id.reply_fast_attach_box: // 添加照片后会有缩略图，这个是点击缩略图
					DEBUG.o("reply_fast_attach_box click!");
					if (!reply_fast_attach_controll_box.isShown())
					{
						reply_fast_attach_controll_box.setVisibility(0);
						core._hideSoftInput(reply_input);
					} else
					{
						reply_fast_attach_controll_box.setVisibility(8);
					}
					reply_fast_attach_gallery
							.setAdapter(replyAttachListAdapter);
					return;

				case R.id.reply_input: // 回帖输入框
					MobclickAgent.onEvent(siteview_forumviewthread.this,
							"c_replyboss");
					DiscuzStats.add(siteAppId, "c_replyboss");
					return;
				case R.id.firstpage_btn:// 跳页---->首页
					siteview_forumviewthread.this.refresh("正在打开第一页..", 1);
					return;
				case R.id.lastpage_btn:// 跳页---->尾页
					siteview_forumviewthread.this.refresh("正在打开尾页..",
							siteview_forumviewthread.this.getAllPageNum());
					return;
				case R.id.gotopage_btn: // 跳页---->指定页
					String s = gotopage_input.getText().toString().trim();
					if ("".equals(s))
					{
						ShowMessageByHandler("请输入有效页码", 2);
						return;
					}
					int i = Integer.valueOf(s).intValue();
					int j = getAllPageNum();
					Intent intent;
					if (i <= j)
						j = i;
					gotopage_input.setText("");
					core._hideSoftInput(gotopage_input);
					refresh((new StringBuilder()).append("正在打开第").append(j)
							.append("页..").toString(), j);
					return;

				case R.id.reply_submit: // 回帖确认
					MobclickAgent.onEvent(siteview_forumviewthread.this,
							"v_reply");
					DiscuzStats.add(siteAppId, "v_reply");
					if (Tools.isEmptyString(reply_input.getText().toString()))
					{
						ShowMessageByHandler("请填写回复内容", 2);
						return;
					}
					if (!core._hasInternet())
					{
						ShowMessageByHandler("抱歉，您现在处于无网状态", 2);
						dismissLoading();
						return;
					}
					if (DiscuzApp.getInstance().getLoginUser(siteAppId)
							.getUid() < 1)
					{
						ShowMessageByHandler("请登录后继续回帖", 2);
						core._hideSoftInput(reply_input);
						Core.showLogin(siteview_forumviewthread.this,
								new OnLogin()
								{

									public void loginError()
									{}

									public void loginSuceess(String s,
											JSONObject jsonobject)
									{
										siteAppId = s;
										if (jsonobject != null)
										{
											if (jsonobject
													.optJSONObject("allowperm") != null)
												uploadHash = jsonobject
														.optJSONObject(
																"allowperm")
														.optString("uploadhash");
											refresh();
										}
									}
								});
						return;
					}
					if (attachlist.size() > 0)
					{
						intent = new Intent();
						intent.putExtra("SEND_POST_TYPE", 2);
						intent.putExtra("UPLOAD_HASH", uploadHash);
						intent.putExtra("siteappid", siteAppId);
						intent.putExtra("tid", tid);
						intent.setClass(siteview_forumviewthread.this,
								uploadFileList.class);
						startActivityForResult(intent, 98);
						return;
					} else
					{
						doSubmit();
						return;
					}
				case R.id.reply_getphoto:// 添加照片
					MobclickAgent.onEvent(siteview_forumviewthread.this,
							"c_replyimage");
					DiscuzStats.add(siteAppId, "c_replyimage");
					DEBUG.o("reply_getphoto click!");
					reply_fast_attach_controll_box.setVisibility(8);
					postpic = new Postpic(siteview_forumviewthread.this);
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
					return;
				case R.id.favorite_box:
					if (!isFavorite)
					{
						isFavorite = true;
						if (DiscuzApp.getInstance().getLoginUser(siteAppId)
								.getUid() > 0)
						{
							(new favoritePostTask()).execute(new String[0]);
							if ("0".equals(ThreadValueList.get("favid")))
								return;
						} else
						{
							isFavorite = false;
							Core.showLogin(siteview_forumviewthread.this,
									new OnLogin()
									{

										public void loginError()
										{
											dismissLoading();
										}

										public void loginSuceess(String s,
												JSONObject jsonobject)
										{
											siteAppId = s;
											dismissLoading();
											(new favoritePostTask())
													.execute(new String[0]);
										}
									});
							return;
						}
					} else
					{
						ShowMessage.getInstance(siteview_forumviewthread.this)
								._showToast("请等候...", 2);
						return;
					}
					return;
				}
			}
		};

		onBlockViewClick = new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				site_navbar.setMoreBtnsListShow();
			}
		};
		onSelectSucceed = new net.discuz.source.ImageSelector.OnSelectSucceed()
		{

			public void onSucceed(String s)
			{
				attachlistPostDraftId.add(Long.valueOf(PostDraftDBHelper
						.getInstance().addItemImageForReply(s)));
				attachlist.add(s);
				if (replyAttachListAdapter == null)
					replyAttachListAdapter = new ReplyAttachListAdapter(
							siteview_forumviewthread.this);
				replyAttachListAdapter.setList(attachlist,
						attachlistPostDraftId);
				if (thumbImage != null)
				{
					reply_fast_attach_gallery
							.setAdapter(replyAttachListAdapter);
					runOnUiThread(new Runnable()
					{

						public void run()
						{
							reply_fast_attach_controll_box.setVisibility(0);
						}
					});
					return;
				} else
				{
					PostDraft postdraft = PostDraftDBHelper.getInstance()
							.getLastItemForReply();
					DEBUG.o((new StringBuilder()).append("get photo succeed:")
							.append(postdraft.getValue()).toString());
					String s1 = postdraft.getValue();
					thumbImage = BitmapFactory.decodeFile(s1);
					reply_thumb.setImageBitmap(thumbImage);
					AlphaAnimation alphaanimation = new AlphaAnimation(0.0F,
							1.0F);
					TranslateAnimation translateanimation = new TranslateAnimation(
							-50F, reply_fast_attach_box.getLeft(), 0.0F, 0.0F);
					AnimationSet animationset = new AnimationSet(true);
					alphaanimation.setDuration(500L);
					translateanimation.setDuration(500L);
					animationset.addAnimation(alphaanimation);
					animationset.addAnimation(translateanimation);
					animationset.setFillAfter(true);
					animationset.setInterpolator(new DecelerateInterpolator());
					animationset
							.setAnimationListener(new android.view.animation.Animation.AnimationListener()
							{

								public void onAnimationEnd(Animation animation)
								{
									runOnUiThread(new Runnable()
									{

										public void run()
										{
											reply_fast_attach_box
													.setVisibility(0);
										}
									});
								}

								public void onAnimationRepeat(
										Animation animation)
								{}

								public void onAnimationStart(Animation animation)
								{}
							});
					reply_fast_attach_box.startAnimation(animationset);
					return;
				}
			}
		};
		viewThreadhttpConnListener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
		{

			public void onFailed(Exception exception)
			{
				ShowMessageByHandler("\u5E16\u5B50\u52A0\u8F7D\u5931\u8D25", 3);
				onExceptionViewThread();
			}

			public void onSucceed(String s, String s1)
			{
				showViewThread(s);
			}
		};
		viewThreadByPidHttpConnListener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
		{

			public void onFailed(Exception exception)
			{}

			public void onSucceed(String s, String s1)
			{
				try
				{
					DJsonReader djsonreader = new DJsonReader(s);
					djsonreader._jsonParse();
					(new JsonParser(siteview_forumviewthread.this)).viewThread(
							djsonreader._getVariables(),
							new JsonParseHelperCallBack()
							{

								public void onParseBegin()
								{}

								public void onParseFinish(Object obj)
								{
									onParseFinish((ViewThreadData) obj);
								}

								public void onParseFinish(
										ViewThreadData viewthreaddata)
								{
									postListValues.putAll(viewthreaddata
											.getPostListValues());
									webviewInterFace
											.setHtmlResult(viewthreaddata
													.getViewThreadPostListHtml()
													.toString());
									discuzWebView
											.loadUrl("javascript:shownextpage();");
									discuzWebView.scrollTo(0,
											discuzWebView.getScrollY());
								}
							});
				} catch (Exception exception)
				{
					exception.printStackTrace();
				}
				dismissLoading();
			}
		};

		nextPageHttpConnThreadListener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
		{

			public void onFailed(Exception exception)
			{
				exception.printStackTrace();
				if (discuzWebView != null)
					discuzWebView
							.loadUrl("javascript:changenextpagetext('2');");
				dismissLoading();
			}

			public void onSucceed(String s, String s1)
			{
				try
				{
					DJsonReader djsonreader = new DJsonReader(s);
					djsonreader._jsonParse();
					(new JsonParser(siteview_forumviewthread.this)).viewThread(
							djsonreader._getVariables(),
							new JsonParseHelperCallBack()
							{

								public void onParseBegin()
								{}

								public void onParseFinish(Object obj)
								{
									onParseFinish((ViewThreadData) obj);
								}

								public void onParseFinish(
										ViewThreadData viewthreaddata)
								{
									ThreadValueList = viewthreaddata
											.getThreadValueList();
									postListValues.putAll(viewthreaddata
											.getPostListValues());
									webviewInterFace
											.setHtmlResult(viewthreaddata
													.getViewThreadPostListHtml()
													.insert(0,
															(new StringBuilder())
																	.append("<div class='pageline'>\u7B2C")
																	.append(page)
																	.append("\u9875</div>")
																	.toString())
													.toString());
									discuzWebView
											.loadUrl("javascript:shownextpage();");
									discuzWebView
											.loadUrl("javascript:changenextpagetext('1');");
									refreshNextPageBtn();
								}
							});
					dismissLoading();
				} catch (Exception e)
				{
					if (discuzWebView != null)
						discuzWebView
								.loadUrl("javascript:changenextpagetext('2');");
				}
			}
		};
		replyByPid = null;
		postvalue = null;
	}

	private void _addWebView()
	{
		discuzWebView = (DWebView_TouchEvent) findViewById(R.id.discuzWebView);
		webviewInterFace = new ViewThreadJavaScriptInterFace(this,
				discuzWebView);
		discuzWebView._init(this, core);
		discuzWebView._initWebViewClient();
		discuzWebView._initWebChromeClient();
		discuzWebView
				.setOnPageLoad(new net.discuz.source.widget.DWebView.onPageLoad()
				{

					public void pageError()
					{
						webViewIsError = true;
					}

					public void pageFinished(WebView webview, String s)
					{
						DEBUG.o("DWebView Page Load finish");
					}

					public void pageStart(WebView webview, String s)
					{}
				});
		discuzWebView._addJavascriptInterFace(webviewInterFace);
		webviewInterFace.loadViewBox("forum/viewthread_box");
	}

	private void _initListener()
	{
		block_view.setOnClickListener(onBlockViewClick);
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{
			public void onClick(View view)
			{
				dismissLoading();
				finish();
			}
		});
		findViewById(R.id.jump_btn).setOnClickListener(clickListener);
		findViewById(R.id.fav_btn).setOnClickListener(clickListener);
		findViewById(R.id.lz_btn).setOnClickListener(clickListener);
		reply_input.setOnClickListener(clickListener);
		reply_submit.setOnClickListener(clickListener);
		reply_getphoto.setOnClickListener(clickListener);
		reply_fast_attach_box.setOnClickListener(clickListener);
		firstpage_btn.setOnClickListener(clickListener);
		lastpage_btn.setOnClickListener(clickListener);
		gotopage_btn.setOnClickListener(clickListener);

		// 长按图片墙中的图片事件
		reply_fast_attach_gallery
				.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener()
				{

					public boolean onItemLongClick(AdapterView adapterview,
							View view, final int position, long l)
					{
						final DialogPopup dialogpopup = new DialogPopup(
								siteview_forumviewthread.this);
						dialogpopup._build(0, 0, 0, 0, 0);
						dialogpopup
								._setMessage(R.string.message_delete_picture); // 是否要删除图片附件
						dialogpopup._setbtnClick(
								new android.view.View.OnClickListener()
								{

									public void onClick(View view)
									{
										replyAttachListAdapter.removeListItem(
												position, false);
										dialogpopup._dismiss();
										if (attachlist.size() == 0)
										{
											reply_fast_attach_controll_box
													.setVisibility(8);
											removeReplyThumb(true);
										}
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

	private void _viewThreadByPidHttpConnThread(long l)
	{
		String as[] = new String[4];
		as[0] = siteAppId;
		as[1] = "module=viewthread";
		as[2] = (new StringBuilder()).append("tid=").append(tid).toString();
		as[3] = (new StringBuilder()).append("viewpid=").append(l).toString();
		String s = Core.getSiteUrl(as);
		HttpConnThread httpconnthread = new HttpConnThread(siteAppId, 0);
		httpconnthread.setUrl(s);
		httpconnthread.setCachePath(viewThreadCachePath);
		httpconnthread.setCacheType(2);
		httpconnthread.setFilter(viewPidFilter);
		DiscuzApp.getInstance().sendHttpConnThread(httpconnthread);
		DiscuzApp.getInstance().setHttpConnListener(viewPidFilter,
				viewThreadByPidHttpConnListener);
	}

	private void _viewThreadHttpConnThread()
	{
		String as[] = new String[7];
		as[0] = siteAppId;
		as[1] = "module=viewthread";
		as[2] = "submodule=checkpost";
		as[3] = (new StringBuilder()).append("tid=").append(tid).toString();
		as[4] = "ppp=10";
		as[5] = "page=1";
		as[6] = extgetparams;
		url = Core.getSiteUrl(as);
		HttpConnThread httpconnthread = new HttpConnThread(siteAppId, 0);
		httpconnthread.setUrl(url);
		httpconnthread.setCachePath(viewThreadCachePath);
		httpconnthread.setCacheType(2);
		httpconnthread.setFilter(viewThreadFilter);
		DiscuzApp.getInstance().setHttpConnListener(viewThreadFilter,
				viewThreadhttpConnListener);
		DiscuzApp.getInstance().sendHttpConnThread(httpconnthread);
	}

	private void changePageControlNum()
	{
		int i = getAllPageNum();
		nowpage.setText((new StringBuilder()).append(page).append("/")
				.append(i).toString());
	}

	private void clearReplyGallery(boolean flag)
	{
		if (replyAttachListAdapter != null)
			replyAttachListAdapter.onDestroy();
		if (flag)
			reply_fast_attach_controll_box.setVisibility(8);
	}

	private void doSubmit()
	{
		String s = reply_input.getText().toString().trim();
		if (isReplying)
		{
			ShowMessageByHandler("正在发送请稍候...", 2);
			return;
		}
		isRefresh = true;
		showLoading("正在为您回帖");
		PostSender postsender = new PostSender(this);
		postsender._setMessage(s);
		postsender._isSendReply();
		if (replyByPid != null && postvalue != null)
			postsender._setReplyByPid(tid, replyByPid, postvalue);
		if (attachRealIdlist != null)
			postsender._setAttach(attachRealIdlist);
		postsender._setOnSend(new onPostSending()
		{

			public void sendBefore()
			{
				DEBUG.o("sender before");
				core._hideSoftInput(reply_input);
			}

			public void sendFaild()
			{
				DEBUG.o("sender Faild");
			}

			public void sendFinish()
			{
				DEBUG.o("sender finish");
				dismissLoading();
				isReplying = false;
				if (reply_input != null && core != null)
					core._hideSoftInput(reply_input);
			}

			public void sendSuccess(DJsonReader djsonreader)
			{
				MobclickAgent.onEvent(siteview_forumviewthread.this,
						"reply_succeed");
				DiscuzStats.add(siteAppId, "reply_succeed");
				DEBUG.o("sender success");
				long l = djsonreader._getVariables().optLong("pid");
				if (l == 0L)
					refresh();
				else
					_viewThreadByPidHttpConnThread(l);
				reply_input.setText("");
				removeReplyThumb(true);
				clearReplyGallery(true);
			}
		});
		String as[] = new String[1];
		as[0] = tid;
		postsender._send(as);
	}

	private int getAllPageNum()
	{
		if (ThreadValueList == null)
			return 0;
		String s = (String) ThreadValueList.get("replies");
		if (s == null)
		{
			return 0;
		} else
		{
			int i = (int) Math.ceil(Float.valueOf(s).floatValue() / 10F);
			DEBUG.o((new StringBuilder()).append("allpage:").append(s)
					.append("|").append(i).toString());
			return i;
		}
	}

	private int getRealPage()
	{
		String s = (String) ThreadValueList.get("replies");
		double d;
		String s1;
		int i;
		if (s == null || "".equals(s))
			d = 0.0D;
		else
			d = Double.parseDouble(s);
		s1 = (String) ThreadValueList.get("ppp");
		if (s1 == null || "".equals(s1))
			i = 10;
		else
			i = Integer.parseInt(s1);
		return (int) Math.ceil(d / (double) i);
	}

	private void onExceptionViewThread()
	{
		site_navbar.setMoreBtnVisibility(false, null, null);
		dismissLoading();
		if (discuzWebView != null)
		{
			webViewIsError = true;
			ViewThreadJavaScriptInterFace viewthreadjavascriptinterface = webviewInterFace;
			String as[] = new String[2];
			as[0] = "message_viewthread_data_error";
			as[1] = getString(R.string.message_viewthread_data_error);
			viewthreadjavascriptinterface.showWebViewMessage(as);
		}
	}

	private void onSuccessViewThread()
	{
		changePageControlNum();
		site_navbar.setMoreBtnVisibility(true,
				(RelativeLayout) findViewById(R.id.buttons_list), block_view);
	}

	private void removeReplyThumb(boolean flag)
	{
		if (thumbImage != null)
		{
			reply_thumb.setImageBitmap(null);
			thumbImage.recycle();
		}
		if (flag)
			runOnUiThread(new Runnable()
			{

				public void run()
				{
					reply_fast_attach_box.clearAnimation();
					reply_fast_attach_box.setVisibility(4);
				}
			});
	}

	public void _showPostBtn()
	{
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadein);
		reply_fast_box.startAnimation(animation);
		reply_fast_box.setVisibility(0);
	}

	public void closePopup()
	{
		if (popup != null)
			popup.dismiss();
	}

	public ImageSelector getImageUpLoad()
	{
		return imageSelect;
	}

	public void gotoNextPage()
	{
		page = 1 + page;
		showNextPageText((new StringBuilder()).append(page).append("/")
				.append(getRealPage()).append("页").toString());
		String as[] = new String[5];
		as[0] = siteAppId;
		as[1] = "module=viewthread";
		as[2] = (new StringBuilder()).append("tid=").append(tid).toString();
		as[3] = "ppp=10";
		as[4] = (new StringBuilder()).append("page=").append(page).toString();
		nextPageHttpConnThread(as);
	}

	public void gotoReply()
	{
		runOnUiThread(new Runnable()
		{

			public void run()
			{
				reply_input.setFocusable(true);
				reply_input.requestFocus();
				core._showSoftInput(reply_input);
			}
		});
	}

	protected void init()
	{
		super.init();
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		block_view = findViewById(R.id.block_view);
		pagenum_box = (RelativeLayout) findViewById(R.id.pagenum_box);
		pagenum = (TextView) findViewById(R.id.pagenum);
		reply_fast_box = (LinearLayout) findViewById(R.id.reply_fast_box);
		reply_input = (EditText) findViewById(R.id.reply_input);
		reply_submit = (Button) findViewById(R.id.reply_submit);
		reply_getphoto = (ImageView) findViewById(R.id.reply_getphoto);
		reply_thumb = (ImageView) findViewById(R.id.reply_fast_thumb);
		reply_fast_attach_box = (RelativeLayout) findViewById(R.id.reply_fast_attach_box);
		reply_fast_attach_controll_box = (RelativeLayout) findViewById(R.id.reply_fast_attach_controll_box);
		reply_fast_attach_gallery = (Gallery) findViewById(R.id.reply_fast_attach_gallery);
		page_controller = (RelativeLayout) findViewById(R.id.page_controller);
		nowpage = (TextView) findViewById(R.id.nowpage);
		firstpage_btn = (Button) findViewById(R.id.firstpage_btn);
		lastpage_btn = (Button) findViewById(R.id.lastpage_btn);
		gotopage_btn = (Button) findViewById(R.id.gotopage_btn);
		gotopage_input = (EditText) findViewById(R.id.gotopage_input);
	}

	public boolean isPopupShown()
	{
		return popup != null && popup.isShown();
	}

	public void nextPageHttpConnThread(String as[])
	{
		MobclickAgent.onEvent(this, "c_trdnpg");
		DiscuzStats.add(siteAppId, "c_trdnpg");
		String s = Core.getSiteUrl(as);
		HttpConnThread httpconnthread = new HttpConnThread(as[0], 1);
		httpconnthread.setCacheType(-1);
		httpconnthread.setFilter(nextPageFilter);
		httpconnthread.setUrl(s);
		DiscuzApp.getInstance().setHttpConnListener(nextPageFilter,
				nextPageHttpConnThreadListener);
		DiscuzApp.getInstance().sendHttpConnThread(httpconnthread);
	}

	protected void onActivityResult(int i, int j, Intent intent)
	{
		super.onActivityResult(i, j, intent);
		if (j == -1)
		{
			String s;
			if (i == 98)
			{
				attachRealIdlist = intent
						.getStringArrayListExtra("attachidlist");
				doSubmit();
				return;
			}
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
					return;
				}
			}
		}
	}

	protected void onBackKeyEvent()
	{
		if (page_controller.isShown())
		{
			page_controller.setVisibility(8);
			reply_fast_box.setVisibility(0);
			return;
		}
		if (postpic != null && postpic.pic_window.isShowing())
		{
			postpic.pic_window.dismiss();
			return;
		} else
		{
			super.onBackKeyEvent();
			return;
		}
	}

	public void onConfigurationChanged(Configuration configuration)
	{
		super.onConfigurationChanged(configuration);
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		MobclickAgent.onEvent(this, "v_thread");
		DiscuzStats.add(siteAppId, "v_thread");
		setContentView(R.layout.viewthread);
		core = Core.getInstance();
		_addWebView();

		if (bundle != null)
		{
			new InitSiteData(siteAppId);
			tid = bundle.getString("tid");
			forumname = bundle.getString("forumname");
		} else
		{
			tid = getIntent().getStringExtra("tid");
			forumname = getIntent().getStringExtra("forumname");
			siteAppId = getIntent().getStringExtra("siteappid");
		}
		_viewThreadHttpConnThread();
		if (bundle != null)
			imageTmpName = bundle.getString("UserCaptureImagePath");
		imageSelector = new ImageSelector(this);
		init();
		showLoading(null);
		_initListener();

		setErrorException(new InterfaceErrorException()
		{
			public void errorException(int i)
			{
				if (i == 0)
				{
					ShowMessageByHandler(R.string.parse_json_error, 3); // 数据解析错误
					return;
				} else
				{
					isFavorite = false;
					ShowMessageByHandler(R.string.error_check_net, 3); // 网络异常,请检查网络
					return;
				}
			}
		});
	}

	protected void onDestroy()
	{
		ThreadValueList = null;
		imageSelect = null;
		discuzWebView = null;
		forumname = null;
		tid = null;
		url = null;
		removeReplyThumb(false); // 清除回复时添加的照片
		clearReplyGallery(false); // 清除回复时添加照片所初始化的图片墙
		super.onDestroy();
	}

	protected void onPause()
	{
		super.onPause();
		MobclickAgent.onPause(this);
	}

	protected void onResume()
	{
		super.onResume();
		MobclickAgent.onResume(this);
	}

	protected void onSaveInstanceState(Bundle bundle)
	{
		bundle.putString("tid", tid);
		bundle.putString("forumname", forumname);
		if (imageTmpName != null)
			bundle.putString("UserCaptureImagePath", imageTmpName);
		super.onSaveInstanceState(bundle);
	}

	public void refresh()
	{
		refresh(null, 1);
	}

	public void refresh(String s, int i)
	{
		super.refresh();
		page = 1;
		if (s == null)
			isRefresh = true;
		if (i > 1)
			page = i;
		changePageControlNum();
		String as[];
		String s1;
		HttpConnThread httpconnthread;
		if (s != null)
			showLoading(s);
		else
			showLoading("刷新帖子");
		if (webViewIsError)
		{
			webviewInterFace.loadViewBox("forum/viewthread_box");
			webViewIsError = false;
		}
		as = new String[7];
		as[0] = siteAppId;
		as[1] = "module=viewthread";
		as[2] = "submodule=checkpost";
		as[3] = (new StringBuilder()).append("tid=").append(tid).toString();
		as[4] = "ppp=10";
		as[5] = (new StringBuilder()).append("page=").append(page).toString();
		as[6] = extgetparams;
		s1 = Core.getSiteUrl(as);
		httpconnthread = new HttpConnThread(siteAppId, 1);
		httpconnthread.setCachePath(viewThreadCachePath);
		httpconnthread.setCacheType(1);
		httpconnthread.setFilter(viewThreadFilter);
		httpconnthread.setUrl(s1);
		DiscuzApp.getInstance().sendHttpConnThread(httpconnthread);
	}

	public void refreshNextPageBtn()
	{
		label0:
		{
			if (ThreadValueList != null
					&& ThreadValueList.get("replies") != null)
			{
				if (getRealPage() <= page)
					break label0;
				discuzWebView
						.loadUrl("javascript:document.getElementById(\"nextpagebtnbox\").style.display='block';");
			}
			return;
		}
		discuzWebView
				.loadUrl("javascript:document.getElementById(\"nextpagebtnbox\").style.display='none';");
	}

	public void reloadNextPage()
	{
		MobclickAgent.onEvent(this, "c_next");
		DiscuzStats.add(siteAppId, "c_next");
		showNextPageText((new StringBuilder()).append("重新加载:").append(page)
				.append("/").append(getRealPage()).append("\u9875").toString());
		String as[] = new String[5];
		as[0] = siteAppId;
		as[1] = "module=viewthread";
		as[2] = (new StringBuilder()).append("tid=").append(tid).toString();
		as[3] = "ppp=10";
		as[4] = (new StringBuilder()).append("page=").append(page).toString();
		nextPageHttpConnThread(as);
	}

	public void showInputReplyByPid(final String pid, int i, int j)
	{
		popup = new ViewThreadBodyClick(this);
		popup.addButton("回复", new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				MobclickAgent.onEvent(siteview_forumviewthread.this,
						"c_flr_reply");
				DiscuzStats.add(siteAppId, "c_flr_reply");
				if (postListValues.get(pid) != null)
				{
					replyByPid = pid;
					postvalue = (HashMap) postListValues.get(pid);
					reply_input.setText("");
					reply_input.setHint((new StringBuilder()).append("回复")
							.append((String) postvalue.get("realnumber"))
							.append("#").toString());
					reply_input.setFocusable(true);
					reply_input.requestFocus();
					core._showSoftInput(reply_input);
				}
				popup.dismiss();
			}
		});
		int k = j - 20;
		popup.showPopupWindow(i, k);
	}

	public void showNextPageText(final String pageText)
	{
		final Animation anim_fadeout = AnimationUtils.loadAnimation(this,
				R.anim.fadeout);
		anim_fadeout.setDuration(1000L);
		final Animation anim_fadein = AnimationUtils.loadAnimation(this,
				R.anim.fadein);
		anim_fadein.setDuration(1000L);
		anim_fadein
				.setAnimationListener(new android.view.animation.Animation.AnimationListener()
				{

					public void onAnimationEnd(Animation animation)
					{
						runOnUiThread(new Runnable()
						{

							public void run()
							{
								try
								{
									Thread.sleep(1000L);
								} catch (InterruptedException interruptedexception)
								{}
								pagenum_box.startAnimation(anim_fadeout);
								pagenum_box.setVisibility(8);
							}
						});
					}

					public void onAnimationRepeat(Animation animation)
					{}

					public void onAnimationStart(Animation animation)
					{}
				});
		runOnUiThread(new Runnable()
		{

			public void run()
			{
				pagenum.setText(pageText);
				pagenum_box.startAnimation(anim_fadein);
				pagenum_box.setVisibility(0);
			}
		});
	}

	public void showViewThread(String s)
	{
		if (isRefresh)
			ShowMessageByHandler("刷新成功", 1);
		DJsonReader djsonreader;
		djsonreader = new DJsonReader(s);
		djsonreader._debug();
		try
		{
			djsonreader._jsonParse();
			if (djsonreader._getMessage()[0] != null)
			{
				webViewIsError = true;
				webviewInterFace.showWebViewMessage(djsonreader._getMessage());
				return;
			}

			DEBUG.o((new StringBuilder())
					.append(Tools._getTimeStamp(Boolean.valueOf(true)))
					.append(":The Time For LoadThread AsyncTask JsonParser:")
					.toString());
			(new JsonParser(this)).viewThread(djsonreader._getVariables(),
					new JsonParseHelperCallBack()
					{

						public void onParseBegin()
						{}

						public void onParseFinish(Object obj)
						{
							onParseFinish((ViewThreadData) obj);
						}

						public void onParseFinish(ViewThreadData viewthreaddata)
						{
							ThreadValueList = viewthreaddata
									.getThreadValueList();
							postListValues.putAll(viewthreaddata
									.getPostListValues());
							if (page > 1)
								webviewInterFace
										.setHtmlResult(viewthreaddata
												.getViewThreadPostListHtml()
												.insert(0,
														(new StringBuilder())
																.append("<div class='pageline'>\u7B2C")
																.append(page)
																.append("\u9875</div>")
																.toString())
												.toString());
							else
								webviewInterFace
										.setHtmlResult(viewthreaddata
												.getViewThreadPostListHtml()
												.toString()); // 帖子内容的HTML格式，通过JavaScript调用来显示
							System.out.println(webviewInterFace.getHtmlResult());
							discuzWebView
									.loadUrl("javascript:show_viewthread_node();");
							webviewInterFace
									.addSubject((String) ThreadValueList
											.get("subject"));
							refreshNextPageBtn();
							allowPerm = viewthreaddata.getAllowPerm();
							uploadHash = allowPerm.getUploadHash();
						}
					});
			onSuccessViewThread();
			DEBUG.o((new StringBuilder())
					.append(Tools._getTimeStamp(Boolean.valueOf(false)))
					.append(":The Time For LoadThread AsyncTask loadTemplate Start")
					.toString());
		} catch (Exception exception)
		{
			onExceptionViewThread();
			exception.printStackTrace();
		}
		dismissLoading();
		(new Thread()
		{

			public void run()
			{
				super.run();
				AllowPermManager.setAllowPermSerialization(siteAppId, tid,
						allowPerm, false);
			}
		}).start();
		return;
	}

	public static final int ppp_default_params = 10;
	private HashMap ThreadValueList;
	private AllowPerm allowPerm;
	private ArrayList attachRealIdlist;
	private ArrayList attachlist;
	private ArrayList attachlistPostDraftId;
	private View block_view;
	private android.view.View.OnClickListener clickListener;
	private Core core;
	private DWebView_TouchEvent discuzWebView;
	private String extgetparams;
	private Button firstpage_btn;
	private String forumname;
	private Button gotopage_btn;
	private EditText gotopage_input;
	private ImageSelector imageSelect;
	private ImageSelector imageSelector;
	private String imageTmpName;
	private boolean isFavorite;
	private boolean isOnlyAuthor;
	private boolean isRefresh;
	private boolean isReplying;
	private Button lastpage_btn;
	private String nextPageFilter;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener nextPageHttpConnThreadListener;
	private TextView nowpage;
	private android.view.View.OnClickListener onBlockViewClick;
	private net.discuz.source.ImageSelector.OnSelectSucceed onSelectSucceed;
	private int page;
	private RelativeLayout page_controller;
	private TextView pagenum;
	private RelativeLayout pagenum_box;
	private ViewThreadBodyClick popup;
	private HashMap postListValues;
	private Postpic postpic;
	private HashMap postvalue;
	private ReplyAttachListAdapter replyAttachListAdapter;
	private String replyByPid;
	private RelativeLayout reply_fast_attach_box;
	private RelativeLayout reply_fast_attach_controll_box;
	private Gallery reply_fast_attach_gallery;
	private LinearLayout reply_fast_box;
	private ImageView reply_getphoto;
	private EditText reply_input;
	private Button reply_submit;
	private ImageView reply_thumb;// 添加照片后的缩略图
	private SiteNavbar site_navbar;
	private Bitmap thumbImage;
	private String tid;
	private String uploadHash;
	private String url;
	private String viewPidFilter;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener viewThreadByPidHttpConnListener;
	private String viewThreadCachePath;
	private String viewThreadFilter;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener viewThreadhttpConnListener;
	private boolean webViewIsError;
	private ViewThreadJavaScriptInterFace webviewInterFace;

}
// 2131296256