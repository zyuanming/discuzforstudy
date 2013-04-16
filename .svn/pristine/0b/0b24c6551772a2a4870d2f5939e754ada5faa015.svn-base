package net.discuz.activity.siteview;

import java.io.File;
import java.util.ArrayList;

import net.discuz.R;
import net.discuz.adapter.PostDraftAdapter;
import net.discuz.app.DiscuzApp;
import net.discuz.model.PostDraft;
import net.discuz.source.DEBUG;
import net.discuz.source.DialogPopup;
import net.discuz.source.ImageSelector;
import net.discuz.source.ShowMessage;
import net.discuz.source.InterFace.OnLogin;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.popupwindow.Postpic;
import net.discuz.source.storage.PostDraftDBHelper;
import net.discuz.tools.Core;
import net.discuz.tools.Tools;

import org.json.JSONObject;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class siteview_sendpost extends DiscuzBaseActivity
{

	public siteview_sendpost()
	{
		fid = "";
		forumname = "";
		tid = "";
		uploadHash = "";
		send_post_type = 1;
		submit = null;
		back = null;
		draft_empty = null;
		draft_add_text = null;
		draft_add_image = null;
		draft_add_sound = null;
		draft_add_camera = null;
		imageTmpName = null;
		imageSelector = null;
		dialog = null;
		postpic = null;
		onSelectSucceed = new net.discuz.source.ImageSelector.OnSelectSucceed()
		{

			public void onSucceed(String s)
			{
				postDraft.addItemImageForNewThread(s);
				refreshPostDraft(postDraft.getAllItemForNewThread());
				adapter.setList(postDraft.getAllItemForNewThread());
			}
		};
		clickListener = new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				switch (view.getId())
				{
				default:
					return;

				case 2131230780:
					if (DiscuzApp.getInstance().getSiteInfo(siteAppId)
							.getLoginUser().getUid() < 1)
					{
						ShowMessageByHandler("\u8BF7\u5148\u767B\u5F55", 2);
						Core.showLogin(siteview_sendpost.this, new OnLogin()
						{

							public void loginError()
							{}

							public void loginSuceess(String s,
									JSONObject jsonobject)
							{
								siteAppId = s;
								gotoSubmit();
							}
						});
						return;
					} else
					{
						gotoSubmit();
						return;
					}

				case 2131230776:
					finish();
					return;

				case 2131230896:
					intent = new Intent();
					intent.putExtra("siteappid", siteAppId);
					intent.setClass(siteview_sendpost.this,
							siteview_sendpost_addtext.class);
					startActivityForResult(intent, 11);
					return;

				case 2131230897:
					postpic = new Postpic(siteview_sendpost.this);
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

				case 2131230898:
					ShowMessage.getInstance(siteview_sendpost.this)._showToast(
							R.string.message_error_this_no_use, 2);
					return;
				}
			}
		};
		itemlongClickListener = new android.widget.AdapterView.OnItemLongClickListener()
		{

			public boolean onItemLongClick(AdapterView adapterview, View view,
					int i, long l)
			{
				DEBUG.o((new StringBuilder())
						.append("\u9879\u76EEposition\uFF1A").append(i)
						.append("\u88AB\u957F\u6309").toString());
				DEBUG.o((new StringBuilder()).append("\u9879\u76EEid\uFF1A")
						.append(l).append("\u88AB\u957F\u6309").toString());
				_exit(i);
				return false;
			}
		};
	}

	private void _getBundleParams()
	{
		Bundle bundle = getIntent().getExtras();
		if (bundle == null)
		{
			finish();
			return;
		}
		siteAppId = bundle.getString("siteappid");
		send_post_type = bundle.getInt("SEND_POST_TYPE");
		uploadHash = bundle.getString("UPLOAD_HASH");
		switch (send_post_type)
		{
		default:
			return;

		case 1: // '\001'
			fid = bundle.getString("fid");
			forumname = bundle.getString("forumname");
			DEBUG.o((new StringBuilder()).append("fid:").append(fid).toString());
			return;

		case 2: // '\002'
			tid = bundle.getString("tid");
			break;
		}
	}

	private void _initListener()
	{
		submit.setOnClickListener(clickListener);
		back.setOnClickListener(clickListener);
		draft_add_text.setOnClickListener(clickListener);
		draft_add_image.setOnClickListener(clickListener);
		draft_add_sound.setOnClickListener(clickListener);
		listview.setOnItemLongClickListener(itemlongClickListener);
		listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
		{

			public void onItemClick(AdapterView adapterview, View view, int i,
					long l)
			{
				DEBUG.o((new StringBuilder()).append("position:").append(i)
						.toString());
				DEBUG.o((new StringBuilder()).append("id:").append(l)
						.toString());
				PostDraft postdraft = adapter.getItem(i);
				if (postdraft != null && postdraft.getType() == 0)
				{
					intent = new Intent();
					String s = String.valueOf(postdraft.getId());
					intent.putExtra("txtid", s);
					intent.putExtra("siteappid", siteAppId);
					intent.setClass(siteview_sendpost.this,
							siteview_sendpost_addtext.class);
					startActivityForResult(intent, 12);
				}
			}
		});
	}

	private void gotoSubmit()
	{
		if (postDraft.getItemNum("type<=2") < 1)
		{
			ShowMessageByHandler(
					"\u8BF7\u5199\u4E00\u4E9B\u6587\u5B57\u540E\u518D\u53D1\u5427",
					2);
			return;
		}
		intent = new Intent();
		intent.putExtra("SEND_POST_TYPE", send_post_type);
		intent.putExtra("UPLOAD_HASH", uploadHash);
		switch (send_post_type)
		{
		case 1:
			intent.putExtra("fid", fid);
			if (forumname != null)
				intent.putExtra("forumname", forumname);
			break;
		case 2:
			intent.putExtra("tid", tid);
		default:
		}
		intent.setClass(this, PostThreadActivity.class);
		intent.putExtra("siteappid", siteAppId);
		startActivityForResult(intent, 99);
		return;
	}

	private void refreshPostDraft(ArrayList arraylist)
	{
		if (arraylist != null && arraylist.size() > 0)
		{
			DEBUG.o((new StringBuilder()).append("refreshPostDraft items:")
					.append(arraylist.size()).toString());
			DEBUG.o("draft gone!");
			draft_empty.setVisibility(8);
			return;
		} else
		{
			DEBUG.o("draft visible!");
			draft_empty.setVisibility(0);
			return;
		}
	}

	public void _exit(final int position)
	{
		dialog = new DialogPopup(this);
		dialog._build(0, 0, 0, 0, 0);
		dialog._setMessage(R.string.message_delete_item);
		dialog._setbtnClick(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				dialog._dismiss();
				PostDraft postdraft = adapter.getItem(position);
				if (postdraft != null)
				{
					postDraft.deleteItem(postdraft.getId());
					ArrayList arraylist = postDraft.getAllItemForNewThread();
					refreshPostDraft(arraylist);
					adapter.setList(arraylist);
				}
			}
		}, new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				dialog._dismiss();
			}
		});
		dialog._show();
	}

	protected void init()
	{
		super.init();
		DEBUG.o((new StringBuilder()).append("send_post_type:")
				.append(send_post_type).toString());
		submit = (Button) findViewById(R.id.done);
		submit.setVisibility(0);
		switch (send_post_type)
		{
		case 1:
			submit.setText("\u4E0B\u4E00\u6B65");
			break;
		case 2:
			submit.setText("\u53D1\u9001");
			break;
		default:
		}
		back = (Button) findViewById(R.id.back);
		back.setVisibility(0);
		listview = (ListView) findViewById(R.id.mListView);
		draft_add_text = (LinearLayout) findViewById(R.id.draft_add_text);
		draft_add_image = (LinearLayout) findViewById(R.id.draft_add_image);
		draft_add_sound = (LinearLayout) findViewById(R.id.draft_add_sound);
		draft_empty = (LinearLayout) findViewById(R.id.draft_empty);
	}

	protected void onActivityResult(int i, int j, Intent intent1)
	{
		if (j != -1)
			return;
		switch (i)
		{
		case 21:
			String s1 = (new StringBuilder()).append("/sdcard/DCIM/Camera/")
					.append(imageTmpName).toString();
			File file = new File(s1);
			if (file.isFile() && file.exists())
			{
				imageSelector = new ImageSelector(this);
				imageSelector.setOnUploadSucceed(onSelectSucceed);
				imageSelector.upLoadPopupWindow(
						findViewById(R.id.DiscuzActivityBox), s1);
			}
			break;
		case 22:
			String s = imageSelector._getImagePath(intent1, this);
			if (!Tools.stringIsNullOrEmpty(s))
			{
				imageSelector = new ImageSelector(this);
				imageSelector.setOnUploadSucceed(onSelectSucceed);
				imageSelector.upLoadPopupWindow(
						findViewById(R.id.DiscuzActivityBox), s);
			}
			break;
		case 99:
			setResult(-1);
			finish();
			break;
		default:
		}
		ArrayList arraylist = postDraft.getAllItemForNewThread();
		refreshPostDraft(arraylist);
		adapter.setList(arraylist);
		return;
	}

	protected void onBackKeyEvent()
	{
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
		if (bundle != null)
			imageTmpName = bundle.getString("UserCaptureImagePath");
		setContentView(R.layout.post_draft);
		_getBundleParams();
		init();
		_initListener();
		postDraft = PostDraftDBHelper.getInstance();
		ArrayList arraylist = postDraft.getAllItemForNewThread();
		adapter = new PostDraftAdapter(this);
		refreshPostDraft(arraylist);
		adapter.setList(arraylist);
		listview.setAdapter(adapter);
		imageSelector = new ImageSelector(this);
	}

	protected void onDestroy()
	{
		postDraft = null;
		imageSelector = null;
		imageTmpName = null;
		intent = null;
		adapter = null;
		listview = null;
		draft_add_camera = null;
		draft_add_sound = null;
		draft_add_image = null;
		draft_add_text = null;
		back = null;
		submit = null;
		uploadHash = null;
		fid = null;
		super.onDestroy();
	}

	protected void onMenuKeyEvent()
	{}

	protected void onSaveInstanceState(Bundle bundle)
	{
		if (imageTmpName != null)
			bundle.putString("UserCaptureImagePath", imageTmpName);
		super.onSaveInstanceState(bundle);
	}

	private final int SEND_POST_TYPE_NEW_THREAD = 1;
	private final int SEND_POST_TYPE_REPLY = 2;
	private PostDraftAdapter adapter;
	private Button back;
	private android.view.View.OnClickListener clickListener;
	private DialogPopup dialog;
	private LinearLayout draft_add_camera;
	private LinearLayout draft_add_image;
	private LinearLayout draft_add_sound;
	private LinearLayout draft_add_text;
	private LinearLayout draft_empty;
	private String fid;
	private String forumname;
	private ImageSelector imageSelector;
	public String imageTmpName;
	private Intent intent;
	private android.widget.AdapterView.OnItemLongClickListener itemlongClickListener;
	private ListView listview;
	private net.discuz.source.ImageSelector.OnSelectSucceed onSelectSucceed;
	private PostDraftDBHelper postDraft;
	private Postpic postpic;
	private int send_post_type;
	private Button submit;
	private String tid;
	private String uploadHash;

}
// 2131296256