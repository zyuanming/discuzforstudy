package net.discuz.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.model.PmData;
import net.discuz.source.AsyncImageLoader;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.pulltorefresh_listview_prototype;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SMSPublicAdapter extends BaseAdapter
{
	class ViewHolder
	{

		ImageView image;
		TextView information;
		TextView name;
		TextView num;
		TextView time;

		ViewHolder()
		{
			super();
		}
	}

	public SMSPublicAdapter(DiscuzBaseActivity discuzbaseactivity, boolean flag)
	{
		activity = null;
		list = new ArrayList();
		icon = null;
		maxLines = 0;
		smsPull = null;
		dra = null;
		asyncImageLoader = null;
		activity = discuzbaseactivity;
		discuzApp = (DiscuzApp) activity.getApplicationContext();
		icon = activity.getResources().getDrawable(R.drawable.user_icon);
		asyncImageLoader = AsyncImageLoader.getAsyncImageLoader();
	}

	public void _clearList()
	{
		list.clear();
	}

	public void _setInformationMaxLines(int i)
	{
		maxLines = i;
	}

	public int getCount()
	{
		if (list == null)
			return 0;
		else
			return list.size();
	}

	public HashMap getItem(int i)
	{
		return (HashMap) list.get(i);
	}

	public long getItemId(int i)
	{
		return (long) i;
	}

	public ArrayList getList()
	{
		return list;
	}

	public View getView(int i, View view1, ViewGroup viewgroup)
	{
		ViewHolder viewholder;
		final RelativeLayout view;
		final ImageView avatar_box;
		String s;
		StringBuilder stringbuilder;
		if (view1 == null)
		{
			viewholder = new ViewHolder();
			RelativeLayout relativelayout = (RelativeLayout) LayoutInflater
					.from(activity)
					.inflate(R.layout.sms_public_list_item, null);
			viewholder.name = (TextView) relativelayout
					.findViewById(R.id.sms_username);
			viewholder.num = (TextView) relativelayout
					.findViewById(R.id.sms_num);
			viewholder.time = (TextView) relativelayout
					.findViewById(R.id.sms_smstime);
			viewholder.information = (TextView) relativelayout
					.findViewById(R.id.sms_information);
			if (maxLines != 0)
				viewholder.information.setMaxLines(maxLines);
			relativelayout.setTag(viewholder);
			view = relativelayout;
		} else
		{
			view = (RelativeLayout) view1;
			viewholder = (ViewHolder) view.getTag();
		}
		avatar_box = (ImageView) view.findViewById(R.id.sms_photo);
		s = discuzApp.getSiteInfo(activity.siteAppId).getUCenterUrl();
		stringbuilder = new StringBuilder();
		stringbuilder.append(s).append("/avatar.php?uid=")
				.append((String) ((HashMap) list.get(i)).get("touid"))
				.append("&size=middle");
		avatar_box.setTag(stringbuilder.toString());
		if (!"".equals(s))
		{
			String s1 = (String) ((HashMap) list.get(i)).get("touid");
			if (s1 == null || "".equals(s1))
				s1 = (String) ((HashMap) list.get(i)).get("authorid");
			StringBuilder stringbuilder1 = new StringBuilder();
			stringbuilder1.append(s).append("/avatar.php?uid=").append(s1)
					.append("&size=middle");
			asyncImageLoader.loadDrawable(activity.siteAppId,
					stringbuilder1.toString(),
					new net.discuz.source.AsyncImageLoader.ImageCallback()
					{

						public void imageCacheLoaded(Bitmap bitmap, String s2)
						{
							avatar_box.setImageBitmap(bitmap);
							avatar_box.postInvalidate();
						}

						public void imageError(String s2)
						{
							ImageView imageview = (ImageView) view
									.findViewWithTag(s2);
							if (imageview != null)
							{
								imageview.setImageDrawable(icon);
								imageview.postInvalidate();
							}
						}

						public void imageLoaded(Bitmap bitmap, String s2)
						{
							if (!smsPull.mpulltorefresh._getIsPullState()
									.booleanValue())
							{
								ImageView imageview = (ImageView) view
										.findViewWithTag(s2);
								if (imageview != null)
								{
									imageview.setImageBitmap(bitmap);
									imageview.postInvalidate();
								}
							}
						}
					});
		} else
		{
			avatar_box.setImageDrawable(icon);
		}
		viewholder.name.setText((CharSequence) ((HashMap) list.get(i))
				.get("tousername"));
		viewholder.time.setText((CharSequence) ((HashMap) list.get(i))
				.get("dateline"));
		viewholder.information.setTag((new StringBuilder()).append("message_")
				.append(i).toString());
		viewholder.information.setText(Html.fromHtml(
				(String) ((HashMap) list.get(i)).get("message"),
				new android.text.Html.ImageGetter()
				{

					public Drawable getDrawable(String s2)
					{
						AsyncImageLoader
								.getAsyncImageLoader()
								.loadDrawable(
										activity.siteAppId,
										s2,
										new net.discuz.source.AsyncImageLoader.ImageCallback()
										{

											public void imageCacheLoaded(
													Bitmap bitmap, String s)
											{
												dra = new BitmapDrawable(bitmap);
											}

											public void imageError(String s)
											{
												dra = activity
														.getResources()
														.getDrawable(
																R.drawable.alpha_bg);
											}

											public void imageLoaded(
													Bitmap bitmap, String s)
											{
												Object obj;
												int i;
												int j;
												if (bitmap == null)
												{
													obj = activity
															.getResources()
															.getDrawable(
																	R.drawable.alpha_bg);
												} else
												{
													obj = new BitmapDrawable(
															bitmap);
													((Drawable) (obj))
															.setBounds(
																	0,
																	0,
																	((Drawable) (obj))
																			.getIntrinsicWidth(),
																	((Drawable) (obj))
																			.getIntrinsicHeight());
												}
												if (Math.max(
														((Drawable) (obj))
																.getIntrinsicHeight(),
														((Drawable) (obj))
																.getIntrinsicWidth()) > 100)
												{
													int k = Math.max(
															((Drawable) (obj))
																	.getIntrinsicHeight(),
															((Drawable) (obj))
																	.getIntrinsicWidth()) / 100;
													i = ((Drawable) (obj))
															.getIntrinsicHeight()
															/ k;
													j = ((Drawable) (obj))
															.getIntrinsicWidth()
															/ k;
												} else
												{
													i = ((Drawable) (obj))
															.getIntrinsicHeight();
													j = ((Drawable) (obj))
															.getIntrinsicWidth();
												}
												((Drawable) (obj)).setBounds(0,
														0, j, i);
												dra = ((Drawable) (obj));
												if (!smsPull.mpulltorefresh
														._getIsPullState()
														.booleanValue())
													activity.runOnUiThread(new Runnable()
													{

														public void run()
														{
															notifyDataSetChanged();
														}
													});
											}
										});
						return dra;
					}
				}, null));
		return view;
	}

	public void setList(PmData pmdata)
	{
		if (pmdata != null)
			list = pmdata.getPmList();
	}

	public void setSmsPull(
			pulltorefresh_listview_prototype pulltorefresh_listview_prototype)
	{
		smsPull = pulltorefresh_listview_prototype;
	}

	private DiscuzBaseActivity activity;
	private AsyncImageLoader asyncImageLoader;
	private DiscuzApp discuzApp;
	private Drawable dra;
	private Drawable icon;
	private ArrayList list;
	private int maxLines;
	private pulltorefresh_listview_prototype smsPull;
}
// 2131296256