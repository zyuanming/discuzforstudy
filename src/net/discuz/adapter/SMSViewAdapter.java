package net.discuz.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.model.PmData;
import net.discuz.source.AsyncImageLoader;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.prototype.pulltorefresh_listview_prototype;
import net.discuz.tools.DiscuzStats;
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

import com.umeng.analytics.MobclickAgent;

public class SMSViewAdapter extends BaseAdapter
{
	class ViewHolder
	{

		ImageView image;
		TextView information;
		TextView name;
		TextView time;

		ViewHolder()
		{
			super();
		}
	}

	public SMSViewAdapter(DiscuzBaseActivity discuzbaseactivity, boolean flag)
	{
		activity = null;
		list = new ArrayList();
		icon = null;
		maxLines = 0;
		smsPull = null;
		dra = null;
		asyncImageLoader = null;
		name = "\u60A8";
		activity = discuzbaseactivity;
		discuzApp = (DiscuzApp) activity.getApplicationContext();
		icon = activity.getResources().getDrawable(R.drawable.user_icon);
		asyncImageLoader = AsyncImageLoader.getAsyncImageLoader();
		MobclickAgent.onEvent(activity, "v_msg");
		DiscuzStats.add(activity.siteAppId, "v_msg");
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
		ViewHolder viewholder = new ViewHolder();
		final RelativeLayout view;
		final ImageView avatar_box;
		String s;
		if (name.equals(((HashMap) list.get(i)).get("tousername")))
			view = (RelativeLayout) LayoutInflater.from(activity).inflate(
					R.layout.sms_list_right_item, null);
		else
			view = (RelativeLayout) LayoutInflater.from(activity).inflate(
					R.layout.sms_list_left_item, null);
		viewholder.time = (TextView) view.findViewById(R.id.sms_smstime);
		viewholder.information = (TextView) view
				.findViewById(R.id.sms_information);
		if (maxLines != 0)
			viewholder.information.setMaxLines(maxLines);
		avatar_box = (ImageView) view.findViewById(R.id.sms_photo);
		s = discuzApp.getSiteInfo(activity.siteAppId).getUCenterUrl();
		(new StringBuilder()).append(s).append("/avatar.php?uid=")
				.append((String) ((HashMap) list.get(i)).get("touid"))
				.append("&size=middle");
		if (!"".equals(s))
		{
			String s1 = (String) ((HashMap) list.get(i)).get("touid");
			if (s1 == null || "".equals(s1))
				s1 = (String) ((HashMap) list.get(i)).get("authorid");
			StringBuilder stringbuilder = new StringBuilder();
			stringbuilder.append(s).append("/avatar.php?uid=").append(s1)
					.append("&size=middle");
			asyncImageLoader.loadDrawable(activity.siteAppId,
					stringbuilder.toString(),
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
		{
			list = pmdata.getPmList();
			return;
		} else
		{
			list = null;
			return;
		}
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
	private String name;
	private pulltorefresh_listview_prototype smsPull;

	/*
	 * static Drawable access$302(SMSViewAdapter smsviewadapter, Drawable
	 * drawable) { smsviewadapter.dra = drawable; return drawable; }
	 */
}
// 2131296256