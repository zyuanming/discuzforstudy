package net.discuz.activity.siteview;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.discuz.R;
import net.discuz.adapter.DownLoadListAdapter;
import net.discuz.source.DFile;
import net.discuz.source.DialogPopup;
import net.discuz.source.ShowMessage;
import net.discuz.source.activity.DiscuzBaseActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class siteview_downloadmanager extends DiscuzBaseActivity
{

	public siteview_downloadmanager()
	{
		DownLoadList = null;
		sdList = null;
	}

	private String _getMIMEType(File file)
	{
		String s = file.getName();
		String s1 = s.substring(1 + s.lastIndexOf("."), s.length())
				.toLowerCase();
		if (s1.equals("m4a") || s1.equals("mp3") || s1.equals("mid")
				|| s1.equals("xmf") || s1.equals("ogg") || s1.equals("wav"))
			return "audio";
		if (s1.equals("3gp") || s1.equals("mp4"))
			return "video";
		if (s1.equals("jpg") || s1.equals("gif") || s1.equals("png")
				|| s1.equals("jpeg") || s1.equals("bmp"))
			return "image";
		if (s1.equals("apk"))
			return "application/vnd.android.package-archive";
		else
			return "*/*";
	}

	private List _getSDDownLoadFiles(String s)
	{
		(new DFile())._createSDCustomDir(s);
		File afile[] = (new File((new StringBuilder())
				.append("/sdcard/discuz/").append(s).toString())).listFiles();
		if (sdList == null)
		{
			sdList = new ArrayList();
		} else
		{
			int i = afile.length;
			int j = 0;
			while (j < i)
			{
				File file = afile[j];
				sdList.add(file.getName().toString());
				j++;
			}
		}
		return sdList;
	}

	private void initAnimation()
	{
		AnimationSet animationset = new AnimationSet(true);
		AlphaAnimation alphaanimation = new AlphaAnimation(0.0F, 1.0F);
		alphaanimation.setDuration(50L);
		animationset.addAnimation(alphaanimation);
		TranslateAnimation translateanimation = new TranslateAnimation(1, 0.0F,
				1, 0.0F, 1, -1F, 1, 0.0F);
		translateanimation.setDuration(100L);
		animationset.addAnimation(translateanimation);
		LayoutAnimationController layoutanimationcontroller = new LayoutAnimationController(
				animationset, 0.5F);
		DownLoadList.setLayoutAnimation(layoutanimationcontroller);
	}

	private void initData()
	{
		DownLoadList.setAdapter(downLoadListAdapter);
	}

	private void initListener()
	{
		DownLoadList
				.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener()
				{

					public boolean onItemLongClick(AdapterView adapterview,
							View view, final int i, long l)
					{
						fileName = downLoadListAdapter.getFileName(i);
						dialog._build(0, 0, 0, 0, 0);
						dialog._setMessage((new StringBuilder())
								.append("\u662F\u5426\u8981\u5220\u9664\uFF1A\n    ")
								.append(fileName).toString());
						dialog._setbtnClick(
								new android.view.View.OnClickListener()
								{

									public void onClick(View view)
									{
										downLoadListAdapter.deleteItem(i);
										dialog._dismiss();
										try
										{
											if ((new DFile())
													._deleteSDFile((new StringBuilder())
															.append("download/")
															.append(fileName)
															.toString()))
												ShowMessage
														.getInstance(
																siteview_downloadmanager.this)
														._showToast(
																(new StringBuilder())
																		.append("\u5DF2\u5220\u9664\u6587\u4EF6\uFF1A ")
																		.append(fileName)
																		.toString(),
																1);
											return;
										} catch (Exception exception)
										{
											exception.printStackTrace();
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
						return false;
					}
				});
		DownLoadList
				.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
				{

					public void onItemClick(AdapterView adapterview, View view,
							int i, long l)
					{
						fileName = downLoadListAdapter.getFileName(i);
						File file = new File((new StringBuilder())
								.append("/sdcard/discuz/download/")
								.append(fileName).toString());
						openFile(file);
					}
				});
		downLoad_back
				.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						finish();
					}
				});
	}

	private void initWidget()
	{
		sdList = new ArrayList();
		downLoad_back = (Button) findViewById(R.id.downLoad_btn_back);
		sdList = _getSDDownLoadFiles("download/");
		downLoadListAdapter = new DownLoadListAdapter(this, sdList);
		dialog = new DialogPopup(this);
	}

	private void openFile(File file)
	{
		Intent intent = new Intent();
		intent.addFlags(0x10000000);
		intent.setAction("android.intent.action.VIEW");
		String s = _getMIMEType(file);
		intent.setDataAndType(Uri.fromFile(file), s);
		startActivity(intent);
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.down_load_manager);
		initWidget();
		initData();
		initAnimation();
		initListener();
	}

	public boolean onKeyDown(int i, KeyEvent keyevent)
	{
		if (i == 4)
			finish();
		else if (i != 4)
			return super.onKeyDown(i, keyevent);
		return true;
	}

	private ListView DownLoadList;
	private DialogPopup dialog;
	private DownLoadListAdapter downLoadListAdapter;
	private Button downLoad_back;
	private String fileName;
	private List sdList;

	/*
	 * static String access$002(siteview_downloadmanager
	 * siteview_downloadmanager1, String s) { siteview_downloadmanager1.fileName
	 * = s; return s; }
	 */

}
// 2131296256