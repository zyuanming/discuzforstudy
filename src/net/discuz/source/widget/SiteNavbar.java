package net.discuz.source.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import net.discuz.R;
import net.discuz.source.DAnimation;
import net.discuz.source.popupwindow.TopMenuPopupWindow;
import net.discuz.tools.DiscuzStats;
import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

public class SiteNavbar extends RelativeLayout
{

	public SiteNavbar(Context context)
	{
		super(context);
		currentSelected = 0;
		mContext = context;
	}

	public SiteNavbar(Context context, AttributeSet attributeset)
	{
		super(context, attributeset);
		currentSelected = 0;
		mContext = context;
	}

	public SiteNavbar(Context context, AttributeSet attributeset, int i)
	{
		super(context, attributeset, i);
		currentSelected = 0;
		mContext = context;
	}

	public boolean areMoreBtnsListShow()
	{
		return areButtonsShown;
	}

	public void dissmissButtonsList()
	{
		DAnimation.startAnimationsOut(buttonsList);
	}

	protected void onFinishInflate()
	{
		addView(LayoutInflater.from(mContext).inflate(R.layout.site_navbar , null));
		site_nav_box = findViewById(R.id.site_nav_box );
		header_title = (TextView) findViewById(R.id.header_title );
		header_click_area = findViewById(R.id.header_click_area );
		pull_down_arrow_image = (ImageView) findViewById(R.id.pull_down_arrow );
		pull_down_arrow_drawable = (TransitionDrawable) pull_down_arrow_image
				.getDrawable();
		pull_down_arrow_drawable.setCrossFadeEnabled(true);
		sendsms_btn = findViewById(R.id.sendsms_btn );
		left_btn = (ImageButton) findViewById(R.id.left_btn );
		custom_btn = (Button) findViewById(R.id.custom_btn );
		refresh_btn = findViewById(R.id.refresh_btn );
		more_btn = findViewById(R.id.more_btn );
		super.onFinishInflate();
	}

	public void setCustomBtnText(CharSequence charsequence)
	{
		custom_btn.setText(charsequence);
	}

	public void setCustomBtnVisibility(boolean flag)
	{
		if (flag)
		{
			custom_btn.setVisibility(0);
			return;
		} else
		{
			custom_btn.setVisibility(8);
			return;
		}
	}

	public void setLeftBtnType(int i)
	{
		if (i == 0)
		{
			left_btn.setImageResource(R.drawable.back_btn_selector );
			return;
		} else
		{
			left_btn.setImageResource(R.drawable.home_btn_selector );
			return;
		}
	}

	public void setMoreBtnVisibility(boolean flag, ViewGroup viewgroup,
			View view)
	{
		if (flag && viewgroup != null)
		{
			buttonsList = viewgroup;
			more_btn.setOnClickListener(moreBtnClickListener);
			more_btn.setVisibility(0);
			block_view = view;
			return;
		} else
		{
			more_btn.setOnClickListener(null);
			more_btn.setVisibility(8);
			return;
		}
	}

	public void setMoreBtnsListShow()
	{
		if (areButtonsShown)
		{
			DAnimation.startAnimationsOut(buttonsList);
			if (block_view != null)
				block_view.setVisibility(8);
		} else
		{
			MobclickAgent.onEvent(getContext(), "c_navright");
			DiscuzStats.add(null, "c_navright");
			DAnimation.startAnimationsIn(buttonsList);
			if (block_view != null)
				block_view.setVisibility(0);
		}
		boolean flag = areButtonsShown;
		boolean flag1 = false;
		if (!flag)
			flag1 = true;
		areButtonsShown = flag1;
		return;
	}

	public void setOnCustomBtnClicked(
			android.view.View.OnClickListener onclicklistener)
	{
		if (onclicklistener != null)
			custom_btn.setVisibility(0);
		else
			custom_btn.setVisibility(8);
		custom_btn.setOnClickListener(onclicklistener);
	}

	public void setOnLeftBtnClicked(
			android.view.View.OnClickListener onclicklistener)
	{
		if (onclicklistener != null)
			left_btn.setOnClickListener(onclicklistener);
	}

	public void setOnRefreshBtnClicked(
			android.view.View.OnClickListener onclicklistener)
	{
		if (onclicklistener != null)
			refresh_btn.setVisibility(0);
		else
			refresh_btn.setVisibility(8);
		refresh_btn.setOnClickListener(onclicklistener);
	}

	public void setOnSendSMSBtnClicked(
			android.view.View.OnClickListener onclicklistener)
	{
		if (onclicklistener != null)
			sendsms_btn.setVisibility(0);
		else
			sendsms_btn.setVisibility(8);
		sendsms_btn.setOnClickListener(onclicklistener);
	}

	public void setOnTitleMenuSelectAction(
			net.discuz.source.popupwindow.TopMenuPopupWindow.OnSelectAction onselectaction)
	{
		mOnTitleMenuSelectAction = onselectaction;
	}

	public void setParentView(View view)
	{
		mParentView = view;
	}

	public void setTitle(CharSequence charsequence)
	{
		header_title.setText(charsequence);
	}

	public void setTitleClickable(boolean flag)
	{
		if (flag)
		{
			header_click_area.setOnClickListener(titleClickListener);
			pull_down_arrow_image.setVisibility(0);
			return;
		} else
		{
			header_click_area.setOnClickListener(null);
			pull_down_arrow_image.setVisibility(8);
			return;
		}
	}

	public void setTitleMenuCheck(String s)
	{
		Set set = mTitleMenuMaps.entrySet();
		ArrayList arraylist = new ArrayList();
		for (Iterator iterator = set.iterator(); iterator.hasNext(); arraylist
				.add(((java.util.Map.Entry) iterator.next()).getKey()))
			;
		currentSelected = arraylist.indexOf(s);
		if (mTitleMenuMaps.containsKey(s))
		{
			mOnTitleMenuSelectAction.itemSelected(currentSelected, s);
			header_title.setText((CharSequence) mTitleMenuMaps.get(s));
		}
	}

	public void setTitleMenuMaps(HashMap hashmap)
	{
		mTitleMenuMaps = hashmap;
	}

	private boolean areButtonsShown;
	private View block_view;
	private ViewGroup buttonsList;
	private int currentSelected;
	private Button custom_btn;
	private View header_click_area;
	private TextView header_title;
	private ImageButton left_btn;
	private Context mContext;
	private net.discuz.source.popupwindow.TopMenuPopupWindow.OnSelectAction mOnTitleMenuSelectAction;
	private View mParentView;
	private HashMap mTitleMenuMaps;
	protected TopMenuPopupWindow mTitleMenuPopupWindow;
	private android.view.View.OnClickListener moreBtnClickListener = new android.view.View.OnClickListener()
	{

		public void onClick(View view)
		{
			setMoreBtnsListShow();
		}
	};
	private View more_btn;
	private net.discuz.source.popupwindow.TopMenuPopupWindow.OnSelectAction onTitleMenuSelected = new net.discuz.source.popupwindow.TopMenuPopupWindow.OnSelectAction()
	{

		public void itemSelected(int i, String s)
		{
			if (mOnTitleMenuSelectAction != null)
			{
				currentSelected = i;
				mOnTitleMenuSelectAction.itemSelected(i, s);
			}
		}
	};
	private TransitionDrawable pull_down_arrow_drawable;
	private ImageView pull_down_arrow_image;
	private View refresh_btn;
	private View sendsms_btn;
	private View site_nav_box;
	private android.view.View.OnClickListener titleClickListener = new android.view.View.OnClickListener()
	{

		public void onClick(View view)
		{
			pull_down_arrow_drawable.startTransition(100);
			mTitleMenuPopupWindow = new TopMenuPopupWindow(mContext,
					mParentView, site_nav_box, header_title);
			mTitleMenuPopupWindow.setMenus(mTitleMenuMaps);
			mTitleMenuPopupWindow
					.setOnDissmissListener(new android.widget.PopupWindow.OnDismissListener()
					{

						public void onDismiss()
						{
							pull_down_arrow_drawable.reverseTransition(100);
						}
					});
			mTitleMenuPopupWindow.setCurrentSelected(currentSelected);
			mTitleMenuPopupWindow.showPopupWindow();
			mTitleMenuPopupWindow.setOnSelectAction(onTitleMenuSelected);
		}
	};

}
//2131296256