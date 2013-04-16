package net.discuz.adapter;

import java.util.HashMap;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.source.AsyncImageLoader;
import net.discuz.source.activity.DiscuzBaseActivity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SmsFriendsListAdapter extends BaseAdapter
{
	class ViewHolder
	{

		TextView name;
		ImageView photo;
		ImageView select;

		ViewHolder()
		{
			super();
		}
	}

	public SmsFriendsListAdapter(DiscuzBaseActivity discuzbaseactivity)
	{
		activity = null;
		friendsListName = null;
		friendsListID = null;
		holder = null;
		icon = null;
		selectMap = null;
		discuzApp = null;
		activity = discuzbaseactivity;
		discuzApp = (DiscuzApp) activity.getApplicationContext();
		icon = activity.getResources().getDrawable(R.drawable.user_icon);
		selectMap = new HashMap();
	}

	public int _addOrdeleteMap(String s)
	{
		if (selectMap.get(s) == null)
			selectMap.put(s, s);
		else
			selectMap.remove(s);
		notifyDataSetChanged();
		return selectMap.size();
	}

	public int getCount()
	{
		if (friendsListName.length == 0)
			return 1;
		else
			return friendsListName.length;
	}

	public String[] getFriendsListID()
	{
		return friendsListID;
	}

	public String[] getFriendsListName()
	{
		return friendsListName;
	}

	public Object getItem(int i)
	{
		return null;
	}

	public long getItemId(int i)
	{
		if (friendsListName.length == 0)
			return -1L;
		else
			return (long) i;
	}

	public HashMap getSelectMap()
	{
		return selectMap;
	}

	public View getView(final int position, View view, ViewGroup viewgroup)
	{
		final String uCenterUrl;
		if (view == null)
		{
			holder = new ViewHolder();
			view = LayoutInflater.from(activity).inflate(
					R.layout.sms_friends_seach_list_item, null);
			holder.photo = (ImageView) view
					.findViewById(R.id.sms_friends_list_item_photo);
			holder.name = (TextView) view
					.findViewById(R.id.sms_friends_list_item_name);
			holder.select = (ImageView) view
					.findViewById(R.id.sms_friends_list_item_select);
			view.setTag(holder);
		} else
		{
			holder = (ViewHolder) view.getTag();
		}

		final View paramview = view;
		uCenterUrl = discuzApp.getSiteInfo(activity.siteAppId).getUCenterUrl();
		holder.photo.setTag((new StringBuilder()).append(uCenterUrl)
				.append("/avatar.php?uid=").append(friendsListID[position])
				.append("&size=middle").toString());
		holder.photo.setImageDrawable(icon);
		AsyncImageLoader.getAsyncImageLoader().loadDrawable(
				activity.siteAppId,
				(new StringBuilder()).append(uCenterUrl)
						.append("/avatar.php?uid=")
						.append(friendsListID[position]).append("&size=middle")
						.toString(),
				new net.discuz.source.AsyncImageLoader.ImageCallback()
				{

					public void imageCacheLoaded(Bitmap bitmap, String s)
					{
						holder.photo.setImageBitmap(bitmap);
						holder.photo.postInvalidate();
					}

					public void imageError(String s)
					{
						final ImageView imageview = (ImageView) paramview
								.findViewWithTag((new StringBuilder())
										.append(uCenterUrl)
										.append("/avatar.php?uid=")
										.append(friendsListID[position])
										.append("&size=middle").toString());
						if (imageview != null)
							activity.runOnUiThread(new Runnable()
							{

								public void run()
								{
									imageview.setImageDrawable(icon);
									imageview.postInvalidate();
								}
							});
					}

					public void imageLoaded(final Bitmap bitmap, String s)
					{
						final ImageView photoview = (ImageView) paramview
								.findViewWithTag((new StringBuilder())
										.append(uCenterUrl)
										.append("/avatar.php?uid=")
										.append(friendsListID[position])
										.append("&size=middle").toString());
						if (photoview != null)
							activity.runOnUiThread(new Runnable()
							{

								public void run()
								{
									photoview.setImageBitmap(bitmap);
									photoview.postInvalidate();
								}
							});
					}
				});
		holder.name.setText(friendsListName[position]);
		if (selectMap.get(friendsListName[position]) == null)
		{
			holder.select.setImageResource(R.drawable.btn_check_off);
			return view;
		} else
		{
			holder.select.setImageResource(R.drawable.btn_check_on);
			return view;
		}
	}

	public void setFriendsListID(String as[])
	{
		friendsListID = new String[as.length];
		friendsListID = as;
	}

	public void setFriendsListName(String as[])
	{
		friendsListName = as;
	}

	public void setSelectMap(HashMap hashmap)
	{
		selectMap = hashmap;
	}

	private DiscuzBaseActivity activity;
	private DiscuzApp discuzApp;
	private String friendsListID[];
	private String friendsListName[];
	private ViewHolder holder;
	private Drawable icon;
	private HashMap selectMap;

}
// 2131296256