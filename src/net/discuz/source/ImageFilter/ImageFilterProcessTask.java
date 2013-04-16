package net.discuz.source.ImageFilter;

import java.util.ArrayList;
import java.util.List;

import net.discuz.R;
import net.discuz.tools.Tools;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImageFilterProcessTask extends AsyncTask
{
	public static class ImageFilterAdapter extends BaseAdapter
	{

		public int getCount()
		{
			return filterArray.size();
		}

		public Object getItem(int i)
		{
			if (i < filterArray.size())
				return ((FilterInfo) filterArray.get(i)).filter;
			else
				return null;
		}

		public long getItemId(int i)
		{
			return (long) i;
		}

		public View getView(int i, View view, ViewGroup viewgroup)
		{
			RelativeLayout relativelayout = (RelativeLayout) LayoutInflater
					.from(mContext).inflate(R.layout.filtergalleryitem, null);
			TextView textview;
			if (clickGallery && selectItem == i)
				relativelayout
						.setBackgroundResource(R.drawable.filter_select_bg);
			else
				relativelayout
						.setBackgroundResource(R.drawable.filter_unselect_bg);
			((ImageView) relativelayout.findViewById(R.id.imagefilter))
					.setImageResource(((FilterInfo) filterArray.get(i)).filterID);
			textview = (TextView) relativelayout
					.findViewById(R.id.imagefiltertext);
			textview.setTypeface(Typeface.create("\u5B8B\u4F53", 1));
			textview.setText(((FilterInfo) filterArray.get(i)).filterName);
			return relativelayout;
		}

		public void setSelectItem(int i)
		{
			clickGallery = true;
			if (selectItem != i)
			{
				selectItem = i;
				notifyDataSetChanged();
			}
		}

		private boolean clickGallery;
		private List filterArray;
		private Context mContext;
		private int selectItem;

		public ImageFilterAdapter(Context context)
		{
			filterArray = new ArrayList();
			clickGallery = false;
			mContext = context;
			filterArray.add(new FilterInfo(R.drawable.brightcontrast_filter,
					new BrightContrastFilter(), "\u9AD8\u4EAE"));
			filterArray.add(new FilterInfo(R.drawable.oilpaint_filter,
					new OilPaintFilter(), "\u8721\u7B14"));
			filterArray.add(new FilterInfo(R.drawable.saturationmodify_filter,
					new SaturationModifyFilter(), "\u9648\u65E7"));
			filterArray.add(new FilterInfo(R.drawable.vintage_filter,
					new VintageFilter(), "\u7535\u5F71"));
			filterArray.add(new FilterInfo(R.drawable.oldphoto_filter,
					new OldPhotoFilter(), "\u8001\u7535\u5F71 "));
			filterArray.add(new FilterInfo(R.drawable.sepia_filter,
					new SepiaFilter(), "\u56DE\u5FC6"));
			filterArray.add(new FilterInfo(R.drawable.feather_filter,
					new FeatherFilter(), "\u5149\u6655"));
			filterArray.add(new FilterInfo(R.drawable.xradiation_filter,
					new XRadiationFilter(), "\u53CD\u5411"));
			filterArray.add(new FilterInfo(R.drawable.scene_filter_3,
					new SceneFilter(5F, Gradient.Scene2()), "\u84DD\u666F"));
			filterArray.add(new FilterInfo(
					R.drawable.paintborder_filter_yellow,
					new PaintBorderFilter(65535), "\u6D82\u9EC4"));
			filterArray.add(new FilterInfo(R.drawable.lomo_filter,
					new LomoFilter(), "Lomo"));
			filterArray.add(new FilterInfo(R.drawable.zoomblur_fitler,
					new ZoomBlurFilter(30), "\u901F\u5EA6"));
			filterArray.add(new FilterInfo(R.drawable.softglow_filter,
					new SoftGlowFilter(10, 0.1F, 0.1F), "\u4ED9\u5883"));
			filterArray.add(new FilterInfo(R.drawable.saturationmodity_filter,
					null, "\u539F\u56FE"));
		}

		private static class FilterInfo
		{

			public IImageFilter filter;
			public int filterID;
			public String filterName;

			public FilterInfo(int i, IImageFilter iimagefilter, String s)
			{
				filterID = i;
				filter = iimagefilter;
				filterName = s;
			}
		}
	}

	public static class PhotoFrameAdapter extends BaseAdapter
	{

		public int getCount()
		{
			return frameArray.length;
		}

		public Object getItem(int i)
		{
			switch (i)
			{
			default:
				return null;

			case 0: // '\0'
				return "/sdcard/photoframe1.jpg";

			case 1: // '\001'
				return "/sdcard/photoframe2.jpg";
			}
		}

		public long getItemId(int i)
		{
			return (long) i;
		}

		public View getView(int i, View view, ViewGroup viewgroup)
		{
			BitmapFactory.decodeResource(mContext.getResources(),
					frameArray[i].intValue()).recycle();
			ImageView imageview = new ImageView(mContext);
			imageview.setImageResource(frameArray[i].intValue());
			imageview.setLayoutParams(new android.widget.Gallery.LayoutParams(
					100, 100));
			imageview
					.setScaleType(android.widget.ImageView.ScaleType.FIT_CENTER);
			return imageview;
		}

		private Integer frameArray[];
		private Context mContext;

		public PhotoFrameAdapter(Context context)
		{
			Integer ainteger[] = new Integer[2];
			ainteger[0] = Integer.valueOf(R.drawable.photoframe1);
			ainteger[1] = Integer.valueOf(R.drawable.photoframe2);
			frameArray = ainteger;
			mContext = context;
		}
	}

	public ImageFilterProcessTask(
			net.discuz.source.ImageSelector.FilterProcessInfo filterprocessinfo)
	{
		fpi = null;
		frameFileName = null;
		fpi = filterprocessinfo;
	}

	public ImageFilterProcessTask(
			net.discuz.source.ImageSelector.FilterProcessInfo filterprocessinfo,
			String s)
	{
		this(filterprocessinfo);
		frameFileName = s;
	}

	private Image LoadPhotoFrame(int i, int j, String s)
	{
		android.graphics.BitmapFactory.Options options;
		int k;
		int l;
		options = new android.graphics.BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(s, options);
		k = options.outWidth;
		l = options.outHeight;
		options.inSampleSize = 1;
		if (k <= l)
		{
			if (l > 600)
				options.inSampleSize = 1 + l / 600;
		} else
		{
			if (k > 600)
				options.inSampleSize = 1 + k / 600;
		}
		options.inJustDecodeBounds = false;
		return new Image(Bitmap.createScaledBitmap(
				BitmapFactory.decodeFile(s, options), i, j, false));
	}

	private void RelaseBitmap()
	{
		if (fpi.bitmap.image != null && fpi.bitmap.image.isRecycled())
		{
			fpi.bitmap.image.recycle();
			fpi.bitmap.image = null;
			System.gc();
		}
		if (fpi.bitmap.destImage != null && fpi.bitmap.destImage.isRecycled())
		{
			fpi.bitmap.destImage.recycle();
			fpi.bitmap.destImage = null;
			System.gc();
		}
	}

	protected Bitmap doInBackground(Void avoid[])
	{
		try
		{
			Image image;
			if (fpi.filter != null)
				image = fpi.filter.process(fpi.bitmap);
			else
				image = fpi.bitmap;
			image.copyPixelsFromBuffer();
			if (!Tools.stringIsNullOrEmpty(frameFileName))
			{
				Image image1 = LoadPhotoFrame(image.getWidth(),
						image.getHeight(), frameFileName);
				ImageBlender imageblender = new ImageBlender();
				imageblender.Mode = ImageBlender.BlendMode.Frame;
				image = imageblender.Blend(image, image1);
				image.copyPixelsFromBuffer();
			}
			return image.getImage();
		} catch (Exception exception)
		{
			RelaseBitmap();
		}
		return null;
	}

	protected Object doInBackground(Object aobj[])
	{
		return doInBackground((Void[]) aobj);
	}

	protected void onPostExecute(Bitmap bitmap)
	{
		if (fpi.bitmap.image != null && fpi.bitmap.image.isRecycled())
		{
			fpi.bitmap.image.recycle();
			fpi.bitmap.image = null;
			System.gc();
		}
		net.discuz.source.ImageSelector.FilterProcessInfo filterprocessinfo = fpi;
		boolean flag;
		if (!Tools.stringIsNullOrEmpty(frameFileName))
			flag = true;
		else
			flag = false;
		filterprocessinfo.PostExcuteCallBack(bitmap, flag);
	}

	protected void onPostExecute(Object obj)
	{
		onPostExecute((Bitmap) obj);
	}

	protected void onPreExecute()
	{
		fpi.PreExecuteCallBack();
	}

	public net.discuz.source.ImageSelector.FilterProcessInfo fpi;
	public String frameFileName;
}
// 2131296256