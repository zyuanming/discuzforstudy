package net.discuz.dialog;

import net.discuz.R;
import net.discuz.source.widget.SiteNavbar;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class AboutDialog extends Dialog
{

	public AboutDialog(Context context)
	{
		super(context, R.style.ResizableDialogTheme );
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.about );
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar );
		site_navbar.setTitle("\u5173\u4E8E");
		site_navbar.setLeftBtnType(0);
		site_navbar.setOnLeftBtnClicked(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				dismiss();
			}
		});
	}

	private SiteNavbar site_navbar;
}
//2131296256