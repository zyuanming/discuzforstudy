package net.discuz.source.prototype;

import net.discuz.R;
import net.discuz.source.activity.DiscuzBaseActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class sub_pulltorefresh_expandablelistview_prototype extends
		pulltorefresh_expandablelistview_prototype
{

	public sub_pulltorefresh_expandablelistview_prototype(
			DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		listFooter = null;
		errorBox = null;
		retryBoxBtn = null;
		errorText = null;
		errorMessage = null;
		isShowingLoding = false;
		errorIcon = null;
		init();
	}

	public void _setListFooter(final int resId)
	{
		handler.post(new Runnable()
		{

			public void run()
			{
				if (resId != 0)
					errorIcon.setImageResource(resId);
				errorText.setText(errorMessage);
				errorBox.setVisibility(0);
				retryBoxBtn.setVisibility(0);
			}
		});
	}

	public void init()
	{
		super.init();
		listFooter = LayoutInflater.from(context).inflate(R.layout.list_footer,
				null);
		errorBox = listFooter.findViewById(R.id.error_box);
		retryBoxBtn = (Button) listFooter.findViewById(R.id.retry);
		listview.addFooterView(listFooter);
		errorText = (TextView) listFooter.findViewById(R.id.error_text);
		errorIcon = (ImageView) listFooter.findViewById(R.id.error_icon);
		retryBoxBtn.setOnClickListener(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				errorBox.setVisibility(8);
				isShowingLoding = true;
				newList();
			}
		});
	}

	public void newList()
	{}

	public void update()
	{}

	private View errorBox;
	public ImageView errorIcon;
	public String errorMessage;
	private TextView errorText;
	public boolean isShowingLoding;
	private View listFooter;
	private Button retryBoxBtn;

}
// 2131296256