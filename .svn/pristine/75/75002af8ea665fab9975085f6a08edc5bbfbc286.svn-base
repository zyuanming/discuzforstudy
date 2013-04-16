package net.discuz.source.widget;

import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.source.storage.GlobalDBHelper;
import net.discuz.tools.Tools;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MovableView extends ViewGroup
{
	public class MainView
	{

		public int getOnTouchLimit()
		{
			return show_left_bn.getRight();
		}

		public View getView()
		{
			return mainView;
		}

		public void initView()
		{
			mainView = LayoutInflater.from(context)
					.inflate(mainViewResId, null);
			show_left_bn = mainView.findViewById(R.id.back_home);
			show_left_bn
					.setOnClickListener(new android.view.View.OnClickListener()
					{

						public void onClick(View view)
						{
							if (movableView.getNowState() == 0)
							{
								mSliderView.refresh();
								movableView.moveToRight();
								return;
							} else
							{
								movableView.moveToMain();
								return;
							}
						}
					});
			mainView.findViewById(R.id.block_view).setOnClickListener(
					new android.view.View.OnClickListener()
					{

						public void onClick(View view)
						{
							if (movableView.getNowState() == 0)
							{
								mSliderView.refresh();
								movableView.moveToRight();
								return;
							} else
							{
								movableView.moveToMain();
								return;
							}
						}
					});
		}

		private View mainView;
		private MovableView movableView;
		private View show_left_bn;

		public MainView(MovableView movableview1)
		{
			super();
			movableView = movableview1;
			initView();
		}
	}

	public static interface MoveListener
	{

		public abstract void onMovedToCenter();

		public abstract void onMovedToRight();
	}

	public static interface SliderMenuListener
	{

		public abstract void onMenuClick(String s);

		public abstract void onTitleClick();
	}

	public class SliderView
	{

		private void initView()
		{
			sliderView = LayoutInflater.from(context).inflate(
					R.layout.sliderview, null);
			((TextView) sliderView.findViewById(R.id.mSiteName))
					.setText(DiscuzApp.getInstance().getSiteInfo(mSiteAppId)
							.getSiteName());
			sliderView.findViewById(R.id.mSliderTitleBtn).setOnClickListener(
					new android.view.View.OnClickListener()
					{

						public void onClick(View view)
						{
							if (now_state == 1)
								mSliderMenuClickListener.onTitleClick();
						}
					});
			menuListView = (ListView) sliderView.findViewById(R.id.mMenuList);
			menuItemAdapter = new MenuItemAdapter();
			menuListView.setAdapter(menuItemAdapter);
			menuListView.setOnItemClickListener(mOnItemClickListener);
		}

		public View getView()
		{
			return sliderView;
		}

		public void refresh()
		{
			String s = GlobalDBHelper.getInstance().getValue("noticenumber");
			if (Tools.isEmptyString(s))
				s = "0";
			mNoticesCount = Integer.parseInt(s);
			menuItemAdapter.notifyDataSetChanged();
		}

		public void setWidth(int i)
		{
			android.view.ViewGroup.LayoutParams layoutparams = menuListView
					.getLayoutParams();
			layoutparams.width = i;
			menuListView.setLayoutParams(layoutparams);
		}

		private MenuItemAdapter menuItemAdapter;
		private ListView menuListView;
		private View sliderView;

		public SliderView()
		{
			super();
			initView();
		}

		public class MenuItemAdapter extends BaseAdapter
		{

			public int getCount()
			{
				if (mMenukeys == null)
					return 0;
				else
					return mMenukeys.size();
			}

			public String getItem(int i)
			{
				if (mMenukeys == null)
					return "";
				else
					return (String) mMenukeys.get(i);
			}

			public long getItemId(int i)
			{
				return (long) i;
			}

			public View getView(int i, View view, ViewGroup viewgroup)
			{
				SliderView.ViewHolder viewholder;
				String s;
				if (view == null)
				{
					view = LayoutInflater.from(context).inflate(
							R.layout.slider_menu_list_item, null);
					viewholder = new SliderView.ViewHolder();
					viewholder.item = (TextView) view.findViewById(R.id.item);
					viewholder.notices_count = (TextView) view
							.findViewById(R.id.notices_count);
					view.setTag(viewholder);
				} else
				{
					viewholder = (SliderView.ViewHolder) view.getTag();
				}
				s = (String) mMenukeys.get(i);
				if (s.equals("noticecenter") && mNoticesCount > 0)
				{
					viewholder.notices_count.setText((new StringBuilder())
							.append("").append(mNoticesCount).toString());
					viewholder.notices_count.setVisibility(0);
				} else
				{
					viewholder.notices_count.setVisibility(8);
				}
				viewholder.item.setText((CharSequence) menuMap.get(s));
				return view;
			}

			public MenuItemAdapter()
			{
				super();
			}
		}

		private class ViewHolder
		{

			TextView item;
			TextView notices_count;

			private ViewHolder()
			{
				super();
			}

		}
	}

	public MovableView(Context context1, int i)
	{
		super(context1);
		touch_state = 0;
		move_state = 0;
		now_state = 0;
		min_up_distance = 10;
		isMoving = false;
		mNoticesCount = 0;
		mOnItemClickListener = new android.widget.AdapterView.OnItemClickListener()
		{

			public void onItemClick(AdapterView adapterview, View view, int j,
					long l)
			{
				selected_key = (String) mMenukeys.get(j);
				mSliderMenuClickListener.onMenuClick(selected_key);
			}

		};
		context = context1;
		mainViewResId = i;
		menuMap = new HashMap();
		menuMap.put("index", "\u9996\u9875");
		menuMap.put("forumdisplay", "\u7248\u5757");
		menuMap.put("mymsg", "\u8BBA\u575B\u6D88\u606F");
		menuMap.put("myfav", "\u6211\u7684\u6536\u85CF");
		menuMap.put("mythread", "\u6211\u7684\u4E3B\u9898");
		menuMap.put("profile", "\u4E2A\u4EBA\u8D44\u6599");
		menuMap.put("login", "\u767B\u5F55");
		menuMap.put("register", "\u6CE8\u518C");
		menuMap.put("noticecenter", "\u901A\u77E5\u4E2D\u5FC3");
		menuMap.put("setting", "\u901A\u7528\u8BBE\u7F6E");
	}

	public boolean dispatchTouchEvent(MotionEvent motionevent)
	{
		int i;
		float f;
		float f1;
		i = motionevent.getAction();
		f = motionevent.getX();
		f1 = motionevent.getY();

		switch (i)
		{
		case 0:
			if (!isMoving)
			{
				start_x = (int) f;
				start_y = (int) f1;
				if (touch_state == 0)
				{
					touch_state = 1;
					isMoving = false;
					move_state = 0;
				}
				if (!isMoving)
				{
					super.dispatchTouchEvent(motionevent);
					super.onTouchEvent(motionevent);
					return true;
				}
			}
			break;
		case 1:
			if (touch_state == 1)
			{
				if (!isMoving)
				{
					super.dispatchTouchEvent(motionevent);
					touch_state = 0;
					move_state = 0;
					isMoving = false;
					return false;
				} else
				{
					if ((now_state != 0 || start_x >= mOnTouchArea)
							&& (now_state != 1 || start_x <= screen_w
									- mOnTouchArea))
					{
						isMoving = false;
						super.onTouchEvent(motionevent);
						touch_state = 0;
						return true;
					} else
					{
						if (Math.abs((int) f - start_x) <= min_up_distance)
						{
							if (now_state == 0)
								moveToMain();
							else if (now_state == 1)
								moveToRight();
						} else
						{
							if (now_state == 0)
							{
								if (move_state == 1)
									moveToRight();
							} else
							{
								moveToMain();
							}
						}
					}
				}
			}
			super.onTouchEvent(motionevent);
			touch_state = 0;
			return true;
		case 2:
			int j = (int) f1;
			int k = (int) f;
			if (!isMoving)
			{
				if (Math.abs(j - start_y) > Math.abs(k - start_x))
				{
					super.dispatchTouchEvent(motionevent);
					super.onTouchEvent(motionevent);
					isMoving = false;
					return true;
				}
				if (Math.abs(k - start_x) > 10)
					isMoving = true;
			}
			if (isMoving)
			{
				if (now_state == 0 && start_x < mOnTouchArea || now_state == 1
						&& start_x > screen_w - mOnTouchArea)
				{
					if (touch_state == 1)
					{
						if (Math.abs(k - start_x) > 10)
						{
							isMoving = true;
							int l = k - start_x;
							if (l > 0 && now_state == 1)
							{
								isMoving = false;
								return true;
							}
							move(l);
						}
						motionevent.setAction(3);
						super.dispatchTouchEvent(motionevent);
						super.onTouchEvent(motionevent);
					} else
					{
						isMoving = false;
					}
				} else
				{
					isMoving = false;
				}
				return false;
			}
			return true;
		default:
			return true;
		}
		return true;
	}

	public boolean getIsMoved()
	{
		return isMoving;
	}

	public int getNowState()
	{
		return now_state;
	}

	public void initScreenSize(int i, int j)
	{
		screen_w = i;
		screen_h = j;
		setKeepScreenOn(true);
		min_up_distance = (int) (0.25F * (float) screen_w);
		initView();
		moveToMain();
	}

	public void initView()
	{
		if (mainView == null)
		{
			mainView = new MainView(this);
			mSliderView = new SliderView();
		}
		addView(mSliderView.getView());
		addView(mainView.getView());
		moveToMain();
	}

	public void move(int i)
	{
		int j = mainView.getView().getLeft();
		if (now_state == 0)
		{
			if (j > 0)
			{
				if (move_state != 1)
					move_state = 1;
			} else
			{
				move_state = 0;
			}
			if (i < 0)
				i = 0;
			if (i > move_distance)
				i = move_distance;
			mainView.getView().layout(i, 0, i + screen_w, screen_h);
			if (i >= move_distance && mMoveListener != null)
				mMoveListener.onMovedToRight();
		} else
		{
			int k = i + move_distance;
			if (k > move_distance)
				k = move_distance;
			if (k < 0)
				k = 0;
			mainView.getView().layout(k, 0, k + screen_w, screen_h);
			if (k <= 0 && mMoveListener != null)
			{
				mMoveListener.onMovedToCenter();
				return;
			}
		}
	}

	public void moveToMain()
	{
		TranslateAnimation translateanimation = new TranslateAnimation(0.0F,
				0 - Math.abs(mainView.getView().getLeft()), 0.0F, 0.0F);
		translateanimation.setInterpolator(new AccelerateInterpolator());
		translateanimation.setFillEnabled(true);
		translateanimation.setDuration(200L);
		translateanimation
				.setAnimationListener(new android.view.animation.Animation.AnimationListener()
				{

					public void onAnimationEnd(Animation animation)
					{
						mainView.getView().layout(0, 0, screen_w, screen_h);
						mSliderView.getView().layout(0, 0, move_distance,
								screen_h);
						now_state = 0;
						isMoving = false;
						if (mMoveListener != null)
							mMoveListener.onMovedToCenter();
					}

					public void onAnimationRepeat(Animation animation)
					{
					}

					public void onAnimationStart(Animation animation)
					{
					}
				});
		mainView.getView().startAnimation(translateanimation);
	}

	public void moveToRight()
	{
		TranslateAnimation translateanimation = new TranslateAnimation(0.0F,
				move_distance - Math.abs(mainView.getView().getLeft()), 0.0F,
				0.0F);
		translateanimation.setInterpolator(new AccelerateInterpolator());
		translateanimation.setFillEnabled(true);
		translateanimation.setDuration(200L);
		translateanimation
				.setAnimationListener(new android.view.animation.Animation.AnimationListener()
				{

					public void onAnimationEnd(Animation animation)
					{
						mSliderView.getView().layout(0, 0, move_distance,
								screen_h);
						mainView.getView().layout(move_distance, 0,
								move_distance + screen_w, screen_h);
						now_state = 1;
						isMoving = false;
						if (mMoveListener != null)
							mMoveListener.onMovedToRight();
					}

					public void onAnimationRepeat(Animation animation)
					{
					}

					public void onAnimationStart(Animation animation)
					{
					}
				});
		mainView.getView().startAnimation(translateanimation);
	}

	protected void onLayout(boolean flag, int i, int j, int k, int l)
	{
		move_distance = screen_w - mOnTouchArea
				- (int) (0.5F + 4F * DiscuzApp.getInstance().density);
		if (move_state == 0)
		{
			if (now_state != 0)
			{
				if (now_state == 1)
					moveToRight();
			} else
			{
				mainView.getView().layout(0, 0, screen_w, screen_h);
				mSliderView.getView().layout(0, 0, move_distance, screen_h);
				isMoving = false;
			}
		}
		mOnTouchArea = mainView.getOnTouchLimit();
		return;
	}

	protected void onMeasure(int i, int j)
	{
		mainView.getView().measure(i, j);
		mSliderView.getView().measure(0, j);
		mSliderView.setWidth(screen_w);
		super.onMeasure(i, j);
	}

	public void setMoveListener(MoveListener movelistener)
	{
		mMoveListener = movelistener;
	}

	public void setNoticesCount(int i)
	{
		mNoticesCount = i;
		mSliderView.refresh();
	}

	public void setSiteAppId(String s)
	{
		mSiteAppId = s;
	}

	public void setSliderMenu(ArrayList arraylist)
	{
		mMenukeys = arraylist;
		mSliderView.refresh();
	}

	public void setSliderMenuClickListener(SliderMenuListener slidermenulistener)
	{
		mSliderMenuClickListener = slidermenulistener;
	}

	public static final int LEFT = 1;
	public static final int MAIN = 0;
	private static final int MOVE_TO_LEFT = 1;
	private static final int MOVE_TO_REST = 0;
	private static final int TOUCH_STATE_MOVING = 1;
	// private static final int TOUCH_STATE_REST;
	private Context context;
	private boolean isMoving;
	private ArrayList mMenukeys;
	private MoveListener mMoveListener;
	private int mNoticesCount;
	private android.widget.AdapterView.OnItemClickListener mOnItemClickListener;
	private int mOnTouchArea;
	private String mSiteAppId;
	private SliderMenuListener mSliderMenuClickListener;
	private SliderView mSliderView;
	private MainView mainView;
	private int mainViewResId;
	private HashMap menuMap;
	private int min_up_distance;
	private int move_distance;
	private int move_state;
	private int now_state;
	private int screen_h;
	private int screen_w;
	protected String selected_key;
	private int start_x;
	private int start_y;
	private int touch_state;
}
// 2131296256