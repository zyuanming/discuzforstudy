package net.discuz.adapter;

import java.util.List;

import net.discuz.R;
import net.discuz.activity.siteview.noticecenterlist_new;
import net.discuz.model.SiteInfo;
import net.discuz.source.DialogPopup;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.NoticeListDBHelper;
import net.discuz.source.storage.SiteInfoDBHelper;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NotifyListAdapter extends BaseExpandableListAdapter
{
	class ViewHolder
	{

		ImageView icon;
		TextView name;
		ImageView notice_clear;
		RelativeLayout noticelist_item_info;

		ViewHolder()
		{
			super();
		}
	}

	public NotifyListAdapter(DiscuzBaseActivity discuzbaseactivity, List list)
	{
		notifyList = null;
		factor = Integer.valueOf(3);
		context = discuzbaseactivity;
		notifyList = list;
		if (list.size() == 0)
			context.ShowMessageByHandler("\u6682\u65E0\u6570\u636E", 2);
	}

	public net.discuz.activity.siteview.noticecenterlist_new.NoticeInfo getChild(
			int i, int j)
	{
		return (net.discuz.activity.siteview.noticecenterlist_new.NoticeInfo) notifyList
				.get(1 + (j + i * factor.intValue()));
	}

	public long getChildId(int i, int j)
	{
		return (long) j;
	}

	public View getChildView(int i, int j, boolean flag, View view,
			ViewGroup viewgroup)
	{
		net.discuz.activity.siteview.noticecenterlist_new.NoticeInfo noticeinfo;
		if (view == null)
		{
			view = LayoutInflater.from(context).inflate(
					R.layout.noticelist_child, null);
			holder = new ViewHolder();
			holder.icon = (ImageView) view.findViewById(R.id.notice_item_icon);
			holder.name = (TextView) view.findViewById(R.id.notice_item_name);
			holder.notice_clear = (ImageView) view
					.findViewById(R.id.notice_right_arrow);
			holder.noticelist_item_info = (RelativeLayout) view
					.findViewById(R.id.noticelist_child_info);
			view.setTag(holder);
		} else
		{
			holder = (ViewHolder) view.getTag();
		}
		noticeinfo = getChild(i, j);

		switch (noticeinfo.type.intValue())
		{
		default:
			this.holder.icon.setImageResource(2130837708);
			this.holder.noticelist_item_info.setBackgroundResource(2130837720);
		case 1:
			holder.icon.setImageResource(R.drawable.noticepm_icon);
			break;
		case 2:
			holder.icon.setImageResource(R.drawable.noticecm_icon);
			break;
		case 3:
			holder.icon.setImageResource(R.drawable.noticetm_icon);
			break;
		case 4:
			holder.icon.setImageResource(R.drawable.noticestm_icon);
			break;
		}

		holder.name.setText(noticeinfo.msg);
		if (noticeinfo.isclick.intValue() == 1)
		{
			holder.name.setTextColor(0xffcccccc);
			return view;
		} else
		{
			holder.name.setTextColor(0xff000000);
			return view;
		}
	}

	public int getChildrenCount(int i)
	{
		return -1 + factor.intValue();
	}

	public net.discuz.activity.siteview.noticecenterlist_new.NoticeInfo getGroup(
			int i)
	{
		return (net.discuz.activity.siteview.noticecenterlist_new.NoticeInfo) notifyList
				.get(i * factor.intValue());
	}

	public int getGroupCount()
	{
		return notifyList.size() / factor.intValue();
	}

	public long getGroupId(int i)
	{
		return (long) i;
	}

	public View getGroupView(int i, boolean flag, View view, ViewGroup viewgroup)
	{
		final net.discuz.activity.siteview.noticecenterlist_new.NoticeInfo notice;
		SiteInfo siteinfo;
		if (view == null)
		{
			view = LayoutInflater.from(context).inflate(
					R.layout.noticelist_group, null);
			holder = new ViewHolder();
			holder.icon = (ImageView) view.findViewById(R.id.notice_item_icon);
			holder.name = (TextView) view.findViewById(R.id.notice_item_name);
			holder.notice_clear = (ImageView) view
					.findViewById(R.id.notice_clear);
			holder.noticelist_item_info = (RelativeLayout) view
					.findViewById(R.id.noticelist_group_info);
			view.setTag(holder);
		} else
		{
			holder = (ViewHolder) view.getTag();
		}
		notice = getGroup(i);
		siteinfo = SiteInfoDBHelper.getInstance().getByAppId(notice.siteappid);
		if (siteinfo != null && siteinfo.getIconFromSD().endsWith(".png"))
		{
			android.graphics.Bitmap bitmap = BitmapFactory.decodeFile(siteinfo
					.getIconFromSD());
			if (bitmap != null)
				holder.icon.setImageBitmap(bitmap);
		}
		if (notice.isclick.intValue() == 1)
		{
			holder.name.setText(notice.msg);
			return view;
		}
		if (notice.type.intValue() <= 0)
			holder.notice_clear
					.setOnClickListener(new android.view.View.OnClickListener()
					{

						public void onClick(View view1)
						{
							final DialogPopup dialogpopup = new DialogPopup(
									context);
							dialogpopup._build(0, 0, 0, 0, 0);
							dialogpopup._setMessage((new StringBuilder())
									.append("\u5220\u9664 '")
									.append(notice.msg)
									.append("' \u7684\u901A\u77E5").toString());
							dialogpopup._setbtnClick(
									new android.view.View.OnClickListener()
									{

										public void onClick(View view)
										{
											NoticeListDBHelper.getInstance()
													.deleteByCloudId(
															notice.cloudid);
											SiteInfo siteinfo = SiteInfoDBHelper
													.getInstance().getByAppId(
															notice.siteappid);
											if (siteinfo != null)
											{
												siteinfo.setIsNotify("0");
												SiteInfoDBHelper
														.getInstance()
														.updateSiteByAppId(
																siteinfo.getSiteAppid(),
																siteinfo);
											}
											noticecenterlist_new
													.loadNoticeList();
											setNoticeList(notifyList);
											dialogpopup._dismiss();
										}
									}, new android.view.View.OnClickListener()
									{

										public void onClick(View view)
										{
											dialogpopup._dismiss();
										}
									});
							dialogpopup._show();
						}
					});
		holder.name.setText(notice.msg);
		return view;
	}

	public boolean hasStableIds()
	{
		return false;
	}

	public boolean isChildSelectable(int i, int j)
	{
		return true;
	}

	public void setNoticeList(List list)
	{
		if (list.size() == 0)
			context.ShowMessageByHandler("\u6682\u65E0\u6570\u636E", 2);
		notifyList = list;
		notifyDataSetInvalidated();
	}

	private DiscuzBaseActivity context;
	public Integer factor;
	private ViewHolder holder;
	public List notifyList;

}
// 2131296256