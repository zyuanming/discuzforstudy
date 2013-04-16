package net.discuz.source;

import android.view.View;
import net.discuz.tools.Tools;

public class BtnSelect
{

	public BtnSelect(View view, View view5, View view6, View view7)
	{
		allowChangeColor = Boolean.valueOf(false);
		view1 = view;
		view2 = view5;
		view3 = view6;
		if (view7 != null)
			view4 = view7;
	}

	public void _initChangeColor(Boolean boolean1)
	{
		allowChangeColor = boolean1;
	}

	public void _onDestroy()
	{}

	public void _setSelected(View view)
	{
		if (view.getId() == view1.getId())
		{
			if (view.getId() == view2.getId())
			{
				view1.setSelected(false);
				view2.setSelected(true);
				view3.setSelected(false);
				if (allowChangeColor.booleanValue())
				{
					view1.setBackgroundColor(0);
					view2.setBackgroundColor(Tools._getHeaderBgColor());
					view3.setBackgroundColor(0);
				}
				if (view4 != null)
				{
					view4.setSelected(false);
					if (allowChangeColor.booleanValue())
					{
						view4.setBackgroundColor(0);
						return;
					}
				}
			}
			if (view.getId() == view3.getId())
			{
				view1.setSelected(false);
				view2.setSelected(false);
				view3.setSelected(true);
				if (allowChangeColor.booleanValue())
				{
					view1.setBackgroundColor(0);
					view2.setBackgroundColor(0);
					view3.setBackgroundColor(Tools._getHeaderBgColor());
				}
				if (view4 != null)
				{
					view4.setSelected(false);
					if (allowChangeColor.booleanValue())
					{
						view4.setBackgroundColor(0);
						return;
					}
				}
			}
			if (view.getId() == view4.getId())
			{
				view1.setSelected(false);
				view2.setSelected(false);
				view3.setSelected(false);
				if (allowChangeColor.booleanValue())
				{
					view1.setBackgroundColor(0);
					view2.setBackgroundColor(0);
					view3.setBackgroundColor(0);
				}
				if (view4 != null)
				{
					view4.setSelected(true);
					if (allowChangeColor.booleanValue())
					{
						view4.setBackgroundColor(Tools._getHeaderBgColor());
						return;
					}
				}
			}
		} else
		{
			view1.setSelected(true);
			view2.setSelected(false);
			view3.setSelected(false);
			if (allowChangeColor.booleanValue())
			{
				view1.setBackgroundColor(Tools._getHeaderBgColor());
				view2.setBackgroundColor(0);
				view3.setBackgroundColor(0);
			}
			if (view4 != null)
			{
				view4.setSelected(false);
				if (allowChangeColor.booleanValue())
					view4.setBackgroundColor(0);
			}
		}
	}

	private Boolean allowChangeColor;
	private View view1;
	private View view2;
	private View view3;
	private View view4;
}
//2131296256