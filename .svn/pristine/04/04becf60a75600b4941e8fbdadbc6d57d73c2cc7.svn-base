package net.discuz.source;

import android.graphics.*;
import java.util.HashMap;
import net.discuz.app.DiscuzApp;
import net.discuz.model.LoginInfo;
import net.discuz.tools.ImageThread;

public class AsyncImageLoader
{
	public static interface ImageCallback
	{

		public abstract void imageCacheLoaded(Bitmap bitmap1, String s);

		public abstract void imageError(String s);

		public abstract void imageLoaded(Bitmap bitmap1, String s);
	}

	public AsyncImageLoader()
	{
		this(new Rect(0, 0, 60, 60));
	}

	public AsyncImageLoader(Rect rect1)
	{
		imageCache = new HashMap();
		VcodeCookie = "";
		SecqaaCookie = "";
		options = new android.graphics.BitmapFactory.Options();
		options.inTempStorage = new byte[0x200000];
		options.inSampleSize = 2;
		rect = rect1;
	}

	public static AsyncImageLoader getAsyncImageLoader()
	{
		return asyncImageLoader;
	}

	public void loadDrawable(String s, String s1, ImageCallback imagecallback)
	{
		loadDrawable(s, s1, imagecallback, true);
	}

	public void loadDrawable(final String siteAppId, final String imageUrl,
			final ImageCallback callback, boolean flag)
	{
		DEBUG.e((new StringBuilder()).append("asyimage:").append(imageUrl)
				.toString());
		if (flag && imageCache.containsKey(imageUrl))
		{
			Bitmap bitmap1 = (Bitmap) imageCache.get(imageUrl);
			if (bitmap1 != null)
				callback.imageCacheLoaded(bitmap1, imageUrl);
			return;
		} else
		{
			ImageThread imagethread = new ImageThread(siteAppId, 2);
			imagethread.setUrl(imageUrl);
			imagethread.setFilter(imageUrl);
			final DiscuzApp discuzApp = DiscuzApp.getInstance();
			discuzApp
					.setHttpConnListener(
							imageUrl,
							new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
							{
								public void onFailed(Exception exception)
								{
									callback.imageError(imageUrl);
									discuzApp.removeHttpConnListener(imageUrl);
								}

								public void onSucceed(String s, String s1)
								{
									if (s != null)
									{
										Bitmap bitmap2 = BitmapFactory
												.decodeFile(s);
										imageCache.put(imageUrl, bitmap2);
										callback.imageLoaded(bitmap2, imageUrl);
									} else
									{
										callback.imageError(imageUrl);
									}
									discuzApp.removeHttpConnListener(imageUrl);
									VcodeCookie = DiscuzApp.getInstance()
											.getLoginUser(siteAppId)
											.getLoginCookie("seccode");
								}
							});
			discuzApp.sendHttpConnThread(imagethread);
			return;
		}
	}

	private static AsyncImageLoader asyncImageLoader = new AsyncImageLoader();
	public String SecqaaCookie;
	public String VcodeCookie;
	private Bitmap bitmap;
	private HashMap imageCache;
	private android.graphics.BitmapFactory.Options options;
	private Rect rect;

}
// 2131296256