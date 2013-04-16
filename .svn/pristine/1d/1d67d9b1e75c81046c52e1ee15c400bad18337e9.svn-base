package net.discuz.source;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.discuz.R;
import net.discuz.source.ImageFilter.IImageFilter;
import net.discuz.source.ImageFilter.Image;
import net.discuz.source.ImageFilter.ImageFilterProcessTask;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.tools.Tools;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class ImageSelector
{
	public class FilterProcessInfo
	{

		public void PostExcuteCallBack(Bitmap bitmap1, boolean flag)
		{
			finishedFilter = true;
			if (activity != null)
				activity.dismissLoading();
			if (bitmap1 != null)
			{
				if (flag)
				{
					frameImage = bitmap1;
				} else
				{
					uploadimage = bitmap1;
					frameImage = null;
				}
				if (threadPopupWindow.isShowing())
					threadPopupWindow.refreshImageViewShow(bitmap1);
			}
		}

		public void PreExecuteCallBack()
		{
			if (activity != null)
				activity.showLoading("\u56FE\u7247\u6E32\u67D3\u4E2D...");
		}

		public Image bitmap;
		public IImageFilter filter;

		public FilterProcessInfo()
		{
			super();
		}
	}

	private class ImageViewPopupwindow extends PopupWindow
	{

		public void clearImageViewShow()
		{
			imageViewShow.setImageBitmap(null);
			imageViewShow = null;
		}

		public void dismiss()
		{
			clearImageViewShow();
			super.dismiss();
		}

		public void refreshImageViewShow(Bitmap bitmap)
		{
			if (bitmap != null)
			{
				imageViewShow.setImageBitmap(bitmap);
				imageViewShow.postInvalidate();
			}
		}

		public void setImageView(ImageView imageview)
		{
			imageViewShow = imageview;
		}

		private ImageView imageViewShow;

		public ImageViewPopupwindow(View view, int i, int j, boolean flag)
		{
			super(view, i, j, flag);
			imageViewShow = null;
		}
	}

	public static interface OnSelectSucceed
	{

		public abstract void onSucceed(String s);
	}

	public ImageSelector(DiscuzBaseActivity discuzbaseactivity)
	{
		activity = null;
		showPicturePopupWindowView = null;
		threadPopupWindow = null;
		uploadimage = null;
		frameImage = null;
		path_temp = null;
		imageTmpName = null;
		onSelectSucceed = null;
		gallery = null;
		finishedFilter = true;
		activity = discuzbaseactivity;
		path_temp = (new StringBuilder()).append("/sdcard/DCIM/Camera/")
				.append(_getCameraTmpFileName()).toString();
	}

	private void LoadImageFilter(final String imagePath, final int scaleSize)
	{
		final net.discuz.source.ImageFilter.ImageFilterProcessTask.ImageFilterAdapter filterAdapter = new net.discuz.source.ImageFilter.ImageFilterProcessTask.ImageFilterAdapter(
				activity);
		gallery.setAdapter(filterAdapter);
		gallery.setSelection(2);
		gallery.setAnimationDuration(3000);
		gallery.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
		{

			public void onItemClick(AdapterView adapterview, View view, int i,
					long l)
			{
				FilterProcessInfo filterprocessinfo = new FilterProcessInfo();
				filterprocessinfo.filter = (IImageFilter) filterAdapter
						.getItem(i);
				if (filterprocessinfo.filter != null && finishedFilter)
				{
					filterAdapter.setSelectItem(i);
					finishedFilter = false;
					filterprocessinfo.bitmap = new Image(BitmapFactory
							.decodeFile(path_temp, Tools.getBitmapOptions(
									path_temp, scaleSize)));
					(new ImageFilterProcessTask(filterprocessinfo))
							.execute(new Void[0]);
				} else if (activity != null && filterprocessinfo.filter == null)
				{
					filterAdapter.setSelectItem(i);
					(new ShowMessage(activity))._showToast(
							"\u5DF2\u53D6\u6D88\u6E32\u67D3", 2, 3);
					uploadimage = BitmapFactory.decodeFile(imagePath,
							Tools.getBitmapOptions(imagePath, scaleSize));
					threadPopupWindow.refreshImageViewShow(uploadimage);
					return;
				}
			}
		});
	}

	private void LoadPhotoFrame(String s, int i)
	{
		Gallery gallery1 = (Gallery) showPicturePopupWindowView
				.findViewById(R.id.galleryPhotoFrame);
		final net.discuz.source.ImageFilter.ImageFilterProcessTask.PhotoFrameAdapter frameAdapter = new net.discuz.source.ImageFilter.ImageFilterProcessTask.PhotoFrameAdapter(
				activity);
		gallery1.setAdapter(new net.discuz.source.ImageFilter.ImageFilterProcessTask.PhotoFrameAdapter(
				activity));
		gallery1.setSelection(0);
		gallery1.setAnimationDuration(3000);
		gallery1.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
		{

			public void onItemClick(AdapterView adapterview, View view, int j,
					long l)
			{
				FilterProcessInfo filterprocessinfo = new FilterProcessInfo();
				String s1 = (String) frameAdapter.getItem(j);
				if (!Tools.stringIsNullOrEmpty(s1))
				{
					filterprocessinfo.bitmap = new Image(uploadimage);
					(new ImageFilterProcessTask(filterprocessinfo, s1))
							.execute(new Void[0]);
				}
			}
		});
	}

	private void ReleaseBitMap()
	{
		if (uploadimage != null && !uploadimage.isRecycled())
		{
			uploadimage.recycle();
			uploadimage = null;
			System.gc();
		}
		if (frameImage != null && !frameImage.isRecycled())
		{
			frameImage.recycle();
			frameImage = null;
			System.gc();
		}
	}

	public static Intent _getCameraIntent(File file)
	{
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE", null);
		intent.putExtra("output", Uri.fromFile(file));
		return intent;
	}

	public void _dismiss()
	{
		if (threadPopupWindow.isShowing())
			threadPopupWindow.dismiss();
		ReleaseBitMap();
	}

	public String _getCameraTmpFileName()
	{
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat simpledateformat = new SimpleDateFormat(
				"'IMG'yyyyMMddHHmmss");
		return (new StringBuilder()).append(simpledateformat.format(date))
				.append(".jpg").toString();
	}

	public String _getImagePath(Intent intent, Activity activity1)
	{
		String as[] = { "_data" };
		if (intent == null)
			return null;
		String s;
		if (!intent.getData().toString().contains("file:///"))
		{
			Cursor cursor = activity1.managedQuery(intent.getData(), as, null,
					null, null);
			int i = cursor.getColumnIndexOrThrow("_data");
			cursor.moveToFirst();
			s = cursor.getString(i);
		} else
		{
			s = intent.getData().toString().replace("file:///", "");
		}
		DEBUG.o((new StringBuilder()).append("selectPicture>>>").append(s)
				.toString());
		return s;
	}

	public void _useAlbum()
	{
		Intent intent = new Intent("android.intent.action.GET_CONTENT");
		intent.addCategory("android.intent.category.OPENABLE");
		intent.setType("image/*");
		activity.startActivityForResult(intent, 22);
	}

	public void _useCamera(String s)
	{
		try
		{
			File file = new File("/sdcard/DCIM/Camera/");
			DEBUG.o("*><>>>>>>/sdcard/DCIM/Camera/");
			if (file.isFile() && !file.exists())
				file.mkdirs();
			Intent intent = _getCameraIntent(new File((new StringBuilder())
					.append("/sdcard/DCIM/Camera/").append(s).toString()));
			activity.startActivityForResult(intent, 21);
			return;
		} catch (Exception exception)
		{
			DEBUG.o("#######\u8C03\u7528\u76F8\u673A\u5F02\u5E38########");
			exception.printStackTrace();
			return;
		}
	}

	public List getData()
	{
		ArrayList arraylist = new ArrayList();
		HashMap hashmap = new HashMap();
		hashmap.put("image", "camera");
		hashmap.put("text", "\u968F\u62CD");
		arraylist.add(hashmap);
		HashMap hashmap1 = new HashMap();
		hashmap1.put("image", "album");
		hashmap1.put("text", "\u76F8\u518C");
		arraylist.add(hashmap1);
		return arraylist;
	}

	public void setOnUploadSucceed(OnSelectSucceed onselectsucceed)
	{
		if (onselectsucceed != null)
			onSelectSucceed = onselectsucceed;
	}

	public void upLoadPopupWindow(View view, String s)
	{
		File file = new File("/sdcard/DCIM/Camera/");
		if (!file.exists())
			file.mkdirs();
		showPicturePopupWindowView = activity.getLayoutInflater().inflate(
				R.layout.upload_image_show, null);
		Display display = ((WindowManager) activity.getSystemService("window"))
				.getDefaultDisplay();
		int i = Math.max(display.getWidth(), display.getHeight());
		int j;
		if (i > 500)
			j = 500;
		else
			j = i;
		uploadimage = BitmapFactory.decodeFile(s, Tools.getBitmapOptions(s, j));
		uploadimage = imagemaker._thumb(uploadimage, j, path_temp);
		if (uploadimage == null)
			(new ShowMessage(activity))._showToast(R.string.message_zip_error,
					3);
		((LinearLayout) showPicturePopupWindowView
				.findViewById(R.id.upload_image_rotate_left))
				.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View view1)
					{
						ImageSelector imageselector = ImageSelector.this;
						Bitmap bitmap;
						if (frameImage != null)
							bitmap = frameImage;
						else
							bitmap = uploadimage;
						imageselector.uploadimage = bitmap;
						uploadimage = imagemaker._rotate(uploadimage, -90,
								path_temp);
						threadPopupWindow.refreshImageViewShow(uploadimage);
					}
				});
		((LinearLayout) showPicturePopupWindowView
				.findViewById(R.id.upload_image_rotate_right))
				.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View view1)
					{
						ImageSelector imageselector = ImageSelector.this;
						Bitmap bitmap;
						if (frameImage != null)
							bitmap = frameImage;
						else
							bitmap = uploadimage;
						imageselector.uploadimage = bitmap;
						uploadimage = imagemaker._rotate(uploadimage, 90,
								path_temp);
						threadPopupWindow.refreshImageViewShow(uploadimage);
					}
				});
		((Button) showPicturePopupWindowView.findViewById(R.id.sendButton))
				.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View view1)
					{
						ImageSelector imageselector = ImageSelector.this;
						Bitmap bitmap;
						if (frameImage != null)
							bitmap = frameImage;
						else
							bitmap = uploadimage;
						imageselector.uploadimage = bitmap;
						imagemaker._saveFile(uploadimage, path_temp);
						onSelectSucceed.onSucceed(path_temp);
						threadPopupWindow.dismiss();
						if (activity != null)
							activity.dismissLoading();
						ReleaseBitMap();
					}
				});
		((Button) showPicturePopupWindowView
				.findViewById(R.id.sendButton_cancel))
				.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View view1)
					{
						threadPopupWindow.dismiss();
						if (activity != null)
							activity.dismissLoading();
						File file1 = new File(path_temp);
						if (file1.exists() && file1.isFile())
							file1.delete();
						ReleaseBitMap();
					}
				});
		threadPopupWindow = new ImageViewPopupwindow(
				showPicturePopupWindowView, -1, -1, false);
		threadPopupWindow.setImageView((ImageView) showPicturePopupWindowView
				.findViewById(R.id.imageView));
		threadPopupWindow.showAtLocation(view, 80, 0, 0);
		threadPopupWindow.refreshImageViewShow(uploadimage);
		gallery = (Gallery) showPicturePopupWindowView
				.findViewById(R.id.galleryFilter);
		LoadImageFilter(s, j);
		LoadPhotoFrame(s, j);
	}

	public static final String MIME_TYPE_IMAGE_JPEG = "image/*";
	private DiscuzBaseActivity activity;
	private boolean finishedFilter;
	private Bitmap frameImage;
	private Gallery gallery;
	public String imageTmpName;
	final DImage imagemaker = new DImage();
	public OnSelectSucceed onSelectSucceed;
	private String path_temp;
	private View showPicturePopupWindowView;
	private ImageViewPopupwindow threadPopupWindow;
	private Bitmap uploadimage;
}
// 2131296256