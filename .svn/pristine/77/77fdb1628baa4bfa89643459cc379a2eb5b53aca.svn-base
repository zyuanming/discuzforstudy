package net.discuz.source.popupwindow;

import java.util.ArrayList;
import java.util.List;

import net.discuz.R;
import net.discuz.adapter.DownLoadListAdapter;
import net.discuz.source.DEBUG;
import net.discuz.source.DFile;
import net.discuz.source.DialogPopup;
import net.discuz.source.ShowMessage;
import net.discuz.source.activity.DiscuzBaseActivity;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

public class DownLoadPopupWindow
{

	public DownLoadPopupWindow(Activity activity1)
	{
		activity = null;
		downLoadListView = null;
		popupWindow_download = null;
		downLoadView = null;
		downLoad_back = null;
		downLoadListAdapter = null;
		downLoadList = null;
		fileName = null;
		dialog = null;
		if (activity1 instanceof DiscuzBaseActivity)
		{
			activity = (DiscuzBaseActivity) activity1;
			_init();
			_initListener();
		}
	}

	private void _init()
	{
		downLoadView = activity.getLayoutInflater().inflate(
				R.layout.down_load_manager, null);
		popupWindow_download = new PopupWindow(downLoadView, -1, -1, true);
		popupWindow_download.setAnimationStyle(R.style.MenuAnimation);
		downLoad_back = (Button) downLoadView
				.findViewById(R.id.downLoad_btn_back);
		downLoadList = new ArrayList();
		downLoadList = (new DFile())._getSDDownLoadFiles("download/");
		downLoadListView = (ListView) downLoadView
				.findViewById(R.id.downLoadList);
		downLoadListAdapter = new DownLoadListAdapter(activity, downLoadList);
		downLoadListView.setAdapter(downLoadListAdapter);
	}

	private void _initListener()
	{
		downLoadListView
				.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener()
				{

					public boolean onItemLongClick(AdapterView adapterview,
							View view, final int i, long l)
					{
						fileName = downLoadListAdapter.getFileName(i);
						dialog = new DialogPopup(activity);
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
														.getInstance(activity)
														._showToast(
																(new StringBuilder())
																		.append("\u5DF2\u5220\u9664\u6587\u4EF6\uFF1A ")
																		.append(fileName)
																		.toString(),
																1);
											return;
										} catch (Exception exception)
										{
											DEBUG.e("###\u5728\u4E0B\u8F7D\u7BA1\u7406\u4E2D\u5220\u9664\u6587\u4EF6\u5F02\u5E38\uFF1A\u3000DiscuzMenuActivity\u3002_initListener()");
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
		downLoadListView
				.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
				{

					public void onItemClick(AdapterView adapterview, View view,
							int i, long l)
					{
						fileName = downLoadListAdapter.getFileName(i);
						(new DFile())._openFile(
								activity,
								(new StringBuilder())
										.append("/sdcard/discuz/download/")
										.append(fileName).toString());
					}
				});
		downLoadListView.setOnKeyListener(new android.view.View.OnKeyListener()
		{

			public boolean onKey(View view, int i, KeyEvent keyevent)
			{
				if (i == 4 && popupWindow_download != null
						&& popupWindow_download.isShowing())
					popupWindow_download.dismiss();
				return true;
			}
		});
		downLoad_back
				.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						popupWindow_download.dismiss();
					}
				});
	}

	public void _dismiss()
	{
		if (popupWindow_download != null && popupWindow_download.isShowing())
			popupWindow_download.dismiss();
	}

	public boolean _isShowing()
	{
		return popupWindow_download != null && popupWindow_download.isShowing();
	}

	public void _showPopupWindow()
	{
		if (popupWindow_download != null && !popupWindow_download.isShowing())
			popupWindow_download.showAtLocation(
					activity.findViewById(R.id.DiscuzActivityBox), 80, 0, 0);
	}

	private DiscuzBaseActivity activity;
	private DialogPopup dialog;
	private List downLoadList;
	private DownLoadListAdapter downLoadListAdapter;
	private ListView downLoadListView;
	private View downLoadView;
	private Button downLoad_back;
	private String fileName;
	private PopupWindow popupWindow_download;

}
// 2131296256