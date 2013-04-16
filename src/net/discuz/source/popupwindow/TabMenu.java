package net.discuz.source.popupwindow;

import net.discuz.R;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class TabMenu extends PopupWindow
{
	public static class MenuBodyAdapter extends BaseAdapter
	{

		private LinearLayout makeMenyBody(int i)
		{
			LinearLayout linearlayout = new LinearLayout(mContext);
			linearlayout.setOrientation(1);
			linearlayout.setGravity(17);
			linearlayout.setPadding(10, 10, 10, 10);
			TextView textview = new TextView(mContext);
			textview.setText(texts[i]);
			textview.setTextSize(fontSize);
			textview.setTextColor(fontColor);
			textview.setGravity(17);
			textview.setPadding(5, 5, 5, 5);
			ImageView imageview = new ImageView(mContext);
			imageview.setBackgroundResource(resID[i]);
			linearlayout.addView(imageview,
					new android.widget.LinearLayout.LayoutParams(
							new android.view.ViewGroup.LayoutParams(-2, -2)));
			linearlayout.addView(textview);
			return linearlayout;
		}

		public int getCount()
		{
			return texts.length;
		}

		public Object getItem(int i)
		{
			return makeMenyBody(i);
		}

		public long getItemId(int i)
		{
			return (long) i;
		}

		public View getView(int i, View view, ViewGroup viewgroup)
		{
			return makeMenyBody(i);
		}

		private int fontColor;
		private int fontSize;
		private Context mContext;
		private int resID[];
		private String texts[];

		public MenuBodyAdapter(Context context, String as[], int ai[], int i,
				int j)
		{
			mContext = context;
			fontColor = j;
			texts = as;
			fontSize = i;
			resID = ai;
		}
	}

	public static class MenuTitleAdapter extends BaseAdapter
	{

		private void SetFocus(int i)
		{
			for (int j = 0; j < title.length; j++)
				if (j != i)
				{
					title[j].setBackgroundResource(R.drawable.menu_top_unselect);
					title[j].setTextColor(fontColor);
				}

			title[i].setBackgroundResource(R.drawable.menu_top_selected);
			title[i].setTextColor(selcolor);
		}

		public int getCount()
		{
			return title.length;
		}

		public Object getItem(int i)
		{
			return title[i];
		}

		public long getItemId(int i)
		{
			return (long) title[i].getId();
		}

		public View getView(int i, View view, ViewGroup viewgroup)
		{
			if (view == null)
				view = title[i];
			return view;
		}

		private int fontColor;
		private Context mContext;
		private int selcolor;
		private TextView title[];
		private int unselcolor;

		public MenuTitleAdapter(Context context, String as[], int i, int j,
				int k, int l)
		{
			mContext = context;
			fontColor = j;
			unselcolor = k;
			selcolor = l;
			title = new TextView[as.length];
			for (int i1 = 0; i1 < as.length; i1++)
			{
				title[i1] = new TextView(mContext);
				title[i1].setText(as[i1]);
				title[i1].setTextSize(i);
				title[i1].setTextColor(fontColor);
				title[i1].setGravity(17);
				title[i1].setPadding(100, 20, 100, 20);
			}

		}
	}

	public TabMenu(
			Context context,
			android.widget.AdapterView.OnItemClickListener onitemclicklistener,
			android.widget.AdapterView.OnItemClickListener onitemclicklistener1,
			MenuTitleAdapter menutitleadapter, int i)
	{
		super(context);
		mLayout = new LinearLayout(context);
		mLayout.setOrientation(1);
		gvTitle = new GridView(context)
		{

			public boolean onKeyUp(int j, KeyEvent keyevent)
			{
				if (j == 82 && isShowing())
					dismiss();
				return super.onKeyUp(j, keyevent);
			}

			public boolean onTouchEvent(MotionEvent motionevent)
			{
				if (motionevent.getAction() == 2)
					return true;
				else
					return super.onTouchEvent(motionevent);
			}
		};
		gvTitle.setLayoutParams(new android.view.ViewGroup.LayoutParams(-1, -2));
		gvTitle.setNumColumns(menutitleadapter.getCount());
		gvTitle.setStretchMode(2);
		gvTitle.setVerticalSpacing(1);
		gvTitle.setHorizontalSpacing(1);
		gvTitle.setGravity(17);
		gvTitle.setOnItemClickListener(onitemclicklistener);
		gvTitle.setAdapter(menutitleadapter);
		gvTitle.setSelector(new ColorDrawable(0));
		titleAdapter = menutitleadapter;
		gvBody = new GridView(context)
		{

			public boolean onTouchEvent(MotionEvent motionevent)
			{
				if (motionevent.getAction() == 2)
					return true;
				else
					return super.onTouchEvent(motionevent);
			}
		};
		gvBody.setLayoutParams(new android.view.ViewGroup.LayoutParams(-1, -2));
		gvBody.setSelector(new ColorDrawable(0));
		gvBody.setNumColumns(4);
		gvBody.setStretchMode(2);
		gvBody.setVerticalSpacing(10);
		gvBody.setHorizontalSpacing(10);
		gvBody.setPadding(10, 10, 10, 10);
		gvBody.setGravity(17);
		gvBody.setOnItemClickListener(onitemclicklistener1);
		mLayout.addView(gvTitle);
		mLayout.addView(gvBody);
		setContentView(mLayout);
		setWidth(-1);
		setHeight(-2);
		setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.menu_bg));
		setAnimationStyle(i);
		setFocusable(true);
	}

	public void SetBodyAdapter(MenuBodyAdapter menubodyadapter)
	{
		gvBody.setAdapter(menubodyadapter);
	}

	public void SetBodySelect(int i, int j)
	{
		int k = gvBody.getChildCount();
		for (int l = 0; l < k; l++)
			if (l != i)
				((LinearLayout) gvBody.getChildAt(l)).setBackgroundColor(0);

		((LinearLayout) gvBody.getChildAt(i)).setBackgroundColor(j);
	}

	public void SetTitleSelect(int i)
	{
		gvTitle.setSelection(i);
		titleAdapter.SetFocus(i);
	}

	private GridView gvBody;
	private GridView gvTitle;
	private LinearLayout mLayout;
	private MenuTitleAdapter titleAdapter;
}
// 2131296256