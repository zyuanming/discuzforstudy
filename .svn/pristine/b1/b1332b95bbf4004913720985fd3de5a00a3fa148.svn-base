package net.discuz.activity.siteview;

import net.discuz.R;
import net.discuz.model.PostDraft;
import net.discuz.source.ShowMessage;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.PostDraftDBHelper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class siteview_sendpost_addtext extends DiscuzBaseActivity
{

	public siteview_sendpost_addtext()
	{
		text_id = null;
		EditModeMessage = null;
		clickListener = new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				switch (view.getId())
				{
				default:
					return;

				case 2131230780:
					String s = message.getText().toString().trim();
					if (s.length() > 3)
					{
						Intent intent;
						if (text_id == null)
							dbHelper.addItemText(s);
						else
							dbHelper.updateItem(text_id, s);
						intent = new Intent();
						setResult(-1, intent);
						finish();
						return;
					} else
					{
						ShowMessage
								.getInstance(siteview_sendpost_addtext.this)
								._showToast(
										"\u8BF7\u8F93\u5165\u591A\u4E00\u70B9\u5427",
										1);
						return;
					}

				case 2131230776:
					finish();
					return;
				}
			}
		};
	}

	private void _getBundleParams()
	{
		if (text_id != null)
		{
			PostDraft postdraft = _getTextDraft(text_id);
			if (postdraft != null)
				EditModeMessage = postdraft.getValue();
		}
	}

	private PostDraft _getTextDraft(String s)
	{
		if (dbHelper == null)
			return null;
		else
			return dbHelper.getItemById(s);
	}

	private void _initListener()
	{
		submit.setOnClickListener(clickListener);
		back.setOnClickListener(clickListener);
	}

	protected void init()
	{
		super.init();
		submit = (Button) findViewById(R.id.done);
		submit.setVisibility(0);
		if (text_id != null)
			submit.setText("\u7F16\u8F91");
		else
			submit.setText("\u6DFB\u52A0");
		back = (Button) findViewById(R.id.back);
		back.setVisibility(0);
		message = (EditText) findViewById(R.id.message);
		if (EditModeMessage != null)
			message.setText(EditModeMessage);
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.post_draft_addtext);
		siteAppId = getIntent().getStringExtra("siteappid");
		if (getIntent().getStringExtra("txtid") != null)
			text_id = getIntent().getStringExtra("txtid");
		dbHelper = PostDraftDBHelper.getInstance();
		_getBundleParams();
		init();
		_initListener();
	}

	private String EditModeMessage;
	private Button back;
	private android.view.View.OnClickListener clickListener;
	private PostDraftDBHelper dbHelper;
	private EditText message;
	private Button submit;
	private String text_id;

}
// 2131296256