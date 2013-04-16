package net.discuz.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import net.discuz.model.MsgInfo;
import net.discuz.source.activity.DiscuzBaseActivity;

public class MsgCenterAdapter extends BaseExpandableListAdapter
{

	public MsgCenterAdapter(DiscuzBaseActivity discuzbaseactivity)
	{
		activity = null;
		subList = new HashMap();
		activity = discuzbaseactivity;
	}

	public void addMsgs(String s, ArrayList arraylist)
	{
		int i = arraylist.size();
		int k = 0;
		int l = 0;
		int k1;
		int l1;
		for (int j = 0; j < i; j++)
		{
			switch (((MsgInfo) arraylist.get(j)).getType())
			{
			default:
				l1 = k;
				k1 = l;
			case 1: // '\001'
				int i1 = l + 1;
				int j1 = k;
				k1 = i1;
				l1 = j1;
				break;
			case 2: // '\002'
				l1 = k + 1;
				k1 = l;
				break;
			}
			l = k1;
			k = l1;
			ArrayList arraylist1 = (ArrayList) subList.get(s);
			if (arraylist1 == null)
			{
				arraylist1 = new ArrayList();
				subList.put(s, arraylist1);
			}
			arraylist1.add("您有" + l + "条公共消息");
			arraylist1.add("您有" + k + "条私人消息");
		}
	}

	public String getChild(int i, int j)
	{
		if (siteArray != null)
		{
			String s = siteArray[i];
			return (String) ((ArrayList) subList.get(s)).get(j);
		} else
		{
			return null;
		}
	}

	public long getChildId(int i, int j)
	{
		return (long) j;
	}

	public View getChildView(int i, int j, boolean flag, View view,
			ViewGroup viewgroup)
	{
		TextView textview;
		if (view == null)
			textview = new TextView(activity);
		else
			textview = (TextView) view;
		textview.setText(getChild(i, j));
		return textview;
	}

	public int getChildrenCount(int i)
	{
		if (siteArray != null)
		{
			String s = siteArray[i];
			return ((ArrayList) subList.get(s)).size();
		} else
		{
			return 0;
		}
	}

	public String getGroup(int i)
	{
		if (siteArray != null)
			return siteArray[i];
		else
			return null;
	}

	public int getGroupCount()
	{
		if (siteArray != null)
			return siteArray.length;
		else
			return 0;
	}

	public long getGroupId(int i)
	{
		return (long) i;
	}

	public View getGroupView(int i, boolean flag, View view, ViewGroup viewgroup)
	{
		TextView textview;
		if (view == null)
			textview = new TextView(activity);
		else
			textview = (TextView) view;
		textview.setText(getGroup(i));
		return textview;
	}

	public boolean hasStableIds()
	{
		return true;
	}

	public boolean isChildSelectable(int i, int j)
	{
		return true;
	}

	public void setSiteArray(String as[])
	{
		siteArray = as;
	}

	private DiscuzBaseActivity activity;
	private String siteArray[];
	private HashMap subList;
}
// 2131296256