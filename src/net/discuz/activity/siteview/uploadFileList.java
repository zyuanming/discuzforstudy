package net.discuz.activity.siteview;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.model.PostDraft;
import net.discuz.source.DEBUG;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.PostDraftDBHelper;
import net.discuz.tools.Core;
import net.discuz.tools.Tools;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

public class uploadFileList extends DiscuzBaseActivity
{
	public class UpLoadFileTask extends AsyncTask
	{

		private void addFormField(DataOutputStream dataoutputstream)
		{
			StringBuilder stringbuilder;
			stringbuilder = new StringBuilder();
			try
			{
				stringbuilder.append((new StringBuilder())
						.append("--****************fD4fH3gL0hK7aI6")
						.append(lineEnd).toString());
				stringbuilder
						.append((new StringBuilder())
								.append("Content-Disposition: form-data; name=\"hash\"")
								.append(lineEnd).toString());
				stringbuilder.append(lineEnd);
				stringbuilder.append((new StringBuilder()).append(uploadHash)
						.append(lineEnd).toString());
				stringbuilder.append((new StringBuilder())
						.append("--****************fD4fH3gL0hK7aI6")
						.append(lineEnd).toString());
				stringbuilder.append((new StringBuilder())
						.append("Content-Disposition: form-data; name=\"uid\"")
						.append(lineEnd).toString());
				stringbuilder.append(lineEnd);
				stringbuilder.append((new StringBuilder()).append(uid)
						.append(lineEnd).toString());
				dataoutputstream.writeBytes(stringbuilder.toString());
			} catch (Exception e)
			{
				e.printStackTrace();
				if (activity != null)
				{
					activity.dismissLoading();
					return;
				}
			}
		}

		private void addImageContent(UploadFileInfo uploadfileinfo,
				DataOutputStream dataoutputstream)
		{
			byte abyte0[];
			File file = new File(uploadfileinfo.FullFileName);
			StringBuilder stringbuilder = new StringBuilder();
			stringbuilder.append((new StringBuilder())
					.append("--****************fD4fH3gL0hK7aI6")
					.append(lineEnd).toString());
			stringbuilder
					.append((new StringBuilder())
							.append("Content-Disposition: form-data; name=\"Filedata\"; filename=\"")
							.append(file.getName()).append("\"")
							.append(lineEnd).toString());
			FileInputStream fileinputstream;
			int j;
			Integer ainteger1[];
			if (uploadfileinfo.FileType == "\u56FE\u7247")
				stringbuilder.append((new StringBuilder())
						.append("Content-Type: image/png").append(lineEnd)
						.toString());
			else
				stringbuilder.append((new StringBuilder())
						.append("Content-Type: application/octet-stream")
						.append(lineEnd).toString());
			stringbuilder.append(lineEnd);

			try
			{
				fileinputstream = new FileInputStream(file);
				abyte0 = new byte[(int) file.length()];
				fileinputstream.read(abyte0);
				fileinputstream.close();
				dataoutputstream.writeBytes(stringbuilder.toString());
				for (int i = 0; i < abyte0.length; i += 1024)
				{
					j = (int) (100F * ((float) i / (float) abyte0.length));
					ainteger1 = new Integer[1];
					ainteger1[0] = Integer.valueOf(j);
					publishProgress(ainteger1);
					if (abyte0.length - i < 1024)
					{
						Integer ainteger[];
						dataoutputstream.write(abyte0, i, abyte0.length - i);
						ainteger = new Integer[1];
						ainteger[0] = Integer.valueOf(100);
						publishProgress(ainteger);
						dataoutputstream.write(abyte0, 0, abyte0.length);
						dataoutputstream.writeBytes(lineEnd);
					} else
					{
						dataoutputstream.write(abyte0, i, 1024);
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		private String getPostAid(HttpURLConnection httpurlconnection, int i)
				throws IOException
		{
			BufferedReader bufferedreader;
			StringBuilder stringbuilder;
			if (httpurlconnection.getResponseCode() == 200)
			{
				bufferedreader = new BufferedReader(new InputStreamReader(
						httpurlconnection.getInputStream()));
				stringbuilder = new StringBuilder();
				String s;
				while ((s = bufferedreader.readLine()) != null)
				{
					stringbuilder.append((new StringBuilder()).append(s)
							.append(lineEnd).toString());
				}
				String s1;
				s1 = stringbuilder.toString();
				DEBUG.o((new StringBuilder()).append("postfile result = ")
						.append(s1).toString());
				String as[];
				as = null;
				if (s1 != null)
					as = s1.split("\\|");
				if (bufferedreader != null)
					bufferedreader.close();
				if (as != null)
					if (as.length > 4)
						if (Integer.parseInt(as[2]) > 0)
							return as[2];
				if (Integer.parseInt(as[4]) > 0 && activity != null)
					activity.ShowMessageByHandler(
							(new StringBuilder())
									.append("\u4E0A\u4F20\u6587\u4EF6\u5927\u4E8E ")
									.append(Tools.readableFileSize(Integer
											.parseInt(as[4])))
									.append(" \u7684\u9650\u5236!").toString(),
							2);
				return "-1";
			}
			return "0";
		}

		protected Object doInBackground(Object aobj[])
		{
			return doInBackground((String[]) aobj);
		}

		protected String doInBackground(String as[])
		{
			String s;
			DataOutputStream dataoutputstream;
			HttpURLConnection httpurlconnection;
			s = "0";
			dataoutputstream = null;
			httpurlconnection = null;
			for (int i = 0; i < 3; i++)
			{
				try
				{
					DataOutputStream dataoutputstream3 = null;
					HttpURLConnection httpurlconnection2 = null;
					boolean flag;
					String s3;
					String s4;
					int j;
					httpurlconnection2 = (HttpURLConnection) (new URL(siteUrl))
							.openConnection();
					httpurlconnection2.setConnectTimeout(60000);
					httpurlconnection2.setDoInput(true);
					httpurlconnection2.setDoOutput(true);
					httpurlconnection2.setUseCaches(false);
					httpurlconnection2.setRequestMethod("POST");
					httpurlconnection2.setRequestProperty("Connection",
							"keep-alive");
					httpurlconnection2
							.setRequestProperty("Content-Type",
									"multipart/form-data; boundary=****************fD4fH3gL0hK7aI6");
					httpurlconnection2.connect();
					dataoutputstream3 = new DataOutputStream(
							httpurlconnection2.getOutputStream());
					addImageContent((UploadFileInfo) fileList.get(fileIndex),
							dataoutputstream3);
					addFormField(dataoutputstream3);
					dataoutputstream3.writeBytes((new StringBuilder())
							.append("--****************fD4fH3gL0hK7aI6--")
							.append(lineEnd).toString());
					dataoutputstream3.flush();
					flag = isCancelled();
					if (!flag)
					{
						s3 = getPostAid(httpurlconnection2, i);
						s4 = s3;
						j = Tools.strToInt(s4);
						if (j > 0)
						{
							if (dataoutputstream3 != null)
								try
								{
									dataoutputstream3.close();
								} catch (IOException ioexception7)
								{
									ioexception7.printStackTrace();
								}
							if (httpurlconnection2 != null)
							{
								httpurlconnection2.disconnect();
								return s4;
							}
						}
					} else
					{
					}

					if (activity != null)
					{
						Integer ainteger[] = new Integer[1];
						ainteger[0] = Integer.valueOf(0);
						publishProgress(ainteger);
					}
					DataOutputStream dataoutputstream1;
					String s2;
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			return s;
		}

		protected void onPostExecute(Object obj)
		{
			onPostExecute((String) obj);
		}

		protected void onPostExecute(String s)
		{
			Intent intent = null;
			ArrayList arraylist;
			try
			{
				if (activity == null)
					return;
			} catch (Exception exception)
			{
				return;
			}
			if (!isCancelled())
				((UploadFileInfo) fileList.get(fileIndex)).AttachID = Integer
						.valueOf(Tools.strToInt(s, 0));
			if (((UploadFileInfo) fileList.get(fileIndex)).AttachID.intValue() > 0)
				activity.UpdateProgress(Integer.valueOf(fileIndex),
						Integer.valueOf(100));
			if (1 + fileIndex > fileList.size())
				intent = new Intent();
			arraylist = new ArrayList();
			for (Iterator iterator = fileList.iterator(); iterator.hasNext(); arraylist
					.add(((UploadFileInfo) iterator.next()).AttachID.toString()))
				;
			if (fileList.size() > 1 + fileIndex)
			{
				(new UpLoadFileTask(activity, siteUrl, fileList, 1 + fileIndex,
						fid, uid, uploadHash, null)).execute(new String[0]);
				return;
			}
			if (((UploadFileInfo) fileList.get(fileIndex)).AttachID.intValue() == 0)
			{
				activity.UpdateProgress(Integer.valueOf(fileIndex),
						Integer.valueOf(-1));
				android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
						activity);
				builder.setTitle("\u7CFB\u7EDF\u63D0\u793A");
				builder.setMessage("\u4E0A\u4F20\u5931\u8D25\uFF0C\u518D\u6B21\u4E0A\u4F20\u8BE5\u6587\u4EF6?");
				builder.setPositiveButton("\u786E\u5B9A",
						new android.content.DialogInterface.OnClickListener()
						{

							public void onClick(
									DialogInterface dialoginterface, int i)
							{
								(new UpLoadFileTask(activity, siteUrl,
										fileList, fileIndex, fid, uid,
										uploadHash, null))
										.execute(new String[0]);
							}
						});
				builder.setNegativeButton("\u53D6\u6D88",
						new android.content.DialogInterface.OnClickListener()
						{

							public void onClick(
									DialogInterface dialoginterface, int i)
							{
								if (fileList.size() > 1 + fileIndex)
									(new UpLoadFileTask(activity, siteUrl,
											fileList, 1 + fileIndex, fid, uid,
											uploadHash, null))
											.execute(new String[0]);
							}
						});
				builder.create();
				builder.show();
			}
			intent.putStringArrayListExtra("attachidlist", arraylist);
			activity.setResult(-1, intent);
			activity.finish();
		}

		protected void onProgressUpdate(Integer ainteger[])
		{
			if (activity != null)
				activity.UpdateProgress(Integer.valueOf(fileIndex), ainteger[0]);
		}

		protected void onProgressUpdate(Object aobj[])
		{
			onProgressUpdate((Integer[]) aobj);
		}

		private uploadFileList activity;
		private final String boundary = "****************fD4fH3gL0hK7aI6";
		private String fid;
		private int fileIndex;
		private ArrayList fileList;
		private final String lineEnd = System.getProperty("line.separator");
		private final String multipart_form_data = "multipart/form-data";
		private PopupWindow popupWindow;
		private String siteUrl;
		private final String twoHyphens = "--";
		private int uid;
		private String uploadHash;

		public UpLoadFileTask(uploadFileList uploadfilelist1, String s,
				ArrayList arraylist, int i, String s1, int j, String s2,
				PopupWindow popupwindow)
		{
			super();
			activity = null;
			siteUrl = null;
			fileList = null;
			fid = null;
			uid = 0;
			uploadHash = null;
			popupWindow = null;
			activity = uploadfilelist1;
			siteUrl = s;
			fileList = arraylist;
			fileIndex = i;
			fid = s1;
			uid = j;
			uploadHash = s2;
			popupWindow = popupwindow;
		}
	}

	public class UploadFileInfo
	{

		public Integer AttachID;
		public String FileName;
		public String FileType;
		public String FullFileName;
		public Integer Progress;
		public String State;

		public UploadFileInfo(String s, String s1, Integer integer, String s2,
				String s3, Integer integer1)
		{
			super();
			FileName = s;
			FullFileName = s1;
			Progress = integer;
			State = s2;
			FileType = s3;
			AttachID = integer1;
		}
	}

	public class UploadFileListAdapter extends BaseAdapter
	{

		public void destroy()
		{
			uploadItems = null;
		}

		public int getCount()
		{
			if (uploadItems == null)
				return 0;
			else
				return uploadItems.size();
		}

		public Object getItem(int i)
		{
			return uploadItems.get(i);
		}

		public long getItemId(int i)
		{
			return (long) i;
		}

		public View getView(int i, View view, ViewGroup viewgroup)
		{
			if (view == null)
				view = LayoutInflater.from(activity).inflate(
						R.layout.uploadfile_listitem, null);
			TextView textview = (TextView) view
					.findViewById(R.id.uploadfilename);
			ProgressBar progressbar = (ProgressBar) view
					.findViewById(R.id.uploadprogress);
			TextView textview1 = (TextView) view.findViewById(R.id.uploadstate);
			TextView textview2 = (TextView) view
					.findViewById(R.id.uploadfiletype);
			UploadFileInfo uploadfileinfo = (UploadFileInfo) uploadItems.get(i);
			progressbar.setProgress(uploadfileinfo.Progress.intValue());
			textview.setText(uploadfileinfo.FileName);
			textview2.setText(uploadfileinfo.FileType);
			textview1.setText(uploadfileinfo.State);
			return view;
		}

		public void remove(int i)
		{
			uploadItems.remove(i);
			notifyDataSetChanged();
		}

		public void setuploadFile_ArrayList(ArrayList arraylist)
		{
			uploadItems = arraylist;
			notifyDataSetChanged();
		}

		private Activity activity;
		private ArrayList uploadItems;

		public UploadFileListAdapter(Activity activity1, ArrayList arraylist)
		{
			super();
			uploadItems = null;
			activity = null;
			activity = activity1;
			uploadItems = arraylist;
		}
	}

	public uploadFileList()
	{
		uploadFile_listview = null;
		uploadFileListAdapter = null;
		uploadItems = new ArrayList();
		uploadHash = null;
		send_post_type = null;
		fid = null;
		tid = null;
	}

	private void _getBundleParams()
	{
		Bundle bundle = getIntent().getExtras();
		if (bundle == null)
		{
			finish();
			return;
		}
		send_post_type = Integer.valueOf(bundle.getInt("SEND_POST_TYPE"));
		uploadHash = bundle.getString("UPLOAD_HASH");
		switch (send_post_type.intValue())
		{
		default:
			return;

		case 1: // '\001'
			fid = bundle.getString("fid");
			return;

		case 2: // '\002'
			tid = bundle.getString("tid");
			break;
		}
	}

	public void UpdateProgress(Integer integer, Integer integer1)
	{
		try
		{
			UploadFileInfo localUploadFileInfo = (UploadFileInfo) this.uploadItems
					.get(integer.intValue());
			if (localUploadFileInfo != null)
			{
				localUploadFileInfo.Progress = integer1;
				if ((localUploadFileInfo.Progress.intValue() >= 100)
						&& (localUploadFileInfo.AttachID.intValue() > 0))
					localUploadFileInfo.State = "上传完毕";
				else if (localUploadFileInfo.Progress.intValue() == -1)
				{
					localUploadFileInfo.State = "上传失败";
				} else
				{
					localUploadFileInfo.Progress = Integer
							.valueOf(10 + localUploadFileInfo.Progress
									.intValue());
					localUploadFileInfo.State = "上传中...";
				}
			}
			this.uploadFileListAdapter
					.setuploadFile_ArrayList(this.uploadItems);
			return;
		} catch (Exception localException)
		{
		}
	}

	protected void init()
	{
		super.init();
		uploadFile_listview = (ListView) findViewById(R.id.uploadfile_listview);
	}

	protected void onBackKeyEvent()
	{
		if (uploadHash != null)
		{
			setResult(0, null);
			finish();
			return;
		} else
		{
			super.onBackKeyEvent();
			return;
		}
	}

	public void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		siteAppId = getIntent().getStringExtra("siteappid");
		setContentView(R.layout.uploadfile_list);
		_getBundleParams();
		init();
		ArrayList arraylist;
		if (send_post_type.intValue() == 1)
			arraylist = PostDraftDBHelper.getInstance()
					.getAllItemForNewThread();
		else if (send_post_type.intValue() == 2)
			arraylist = PostDraftDBHelper.getInstance().getAllItemForReply();
		else
			arraylist = null;
		if (arraylist != null)
		{
			int i = arraylist.size();
			int j = 0;
			do
			{
				if (j >= i)
					break;
				PostDraft postdraft = (PostDraft) arraylist.get(j);
				String s = postdraft.getValue();
				String as1[] = s.split("\\/");
				String s1 = as1[-1 + as1.length];
				int k = postdraft.getType();
				if (k == 3)
					k = 1;
				if (k == 1)
					uploadItems.add(new UploadFileInfo(s1, s, Integer
							.valueOf(0), "\u7B49\u5F85\u4E0A\u4F20",
							"\u56FE\u7247", Integer.valueOf(0)));
				else if (k == 2)
					uploadItems.add(new UploadFileInfo(s1, s, Integer
							.valueOf(0), "\u7B49\u5F85\u4E0A\u4F20",
							"\u58F0\u97F3", Integer.valueOf(0)));
				j++;
			} while (true);
		}
		if (uploadItems.size() > 0)
		{
			uploadFileListAdapter = new UploadFileListAdapter(this, uploadItems);
			uploadFile_listview.setAdapter(uploadFileListAdapter);
			uploadFile_listview.setVisibility(0);
			String as[] = new String[7];
			as[0] = siteAppId;
			as[1] = "module=forumupload";
			as[2] = "simple=1";
			as[3] = "type=image";
			as[4] = (new StringBuilder()).append("fid=").append(fid).toString();
			as[5] = (new StringBuilder()).append("hash=").append(uploadHash)
					.toString();
			as[6] = (new StringBuilder())
					.append("uid=")
					.append(DiscuzApp.getInstance().getLoginUser(siteAppId)
							.getUid()).toString();
			(new UpLoadFileTask(this, Core.getSiteUrl(as), uploadItems, 0, fid,
					DiscuzApp.getInstance().getSiteInfo(siteAppId)
							.getLoginUser().getUid(), uploadHash, null))
					.execute(new String[0]);
		}
	}

	protected void onDestroy()
	{
		uploadFile_listview = null;
		uploadFileListAdapter = null;
		uploadItems = null;
		uploadHash = null;
		fid = null;
		tid = null;
		send_post_type = null;
		super.onDestroy();
	}

	private String fid;
	private Integer send_post_type;
	private String tid;
	UploadFileListAdapter uploadFileListAdapter;
	private ListView uploadFile_listview;
	private String uploadHash;
	ArrayList uploadItems;
}
// 2131296256