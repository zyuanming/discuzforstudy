package net.discuz.source.popupwindow;

import net.discuz.R;
import net.discuz.source.AsyncImageLoader;
import net.discuz.source.DEBUG;
import net.discuz.source.ShowMessage;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.tools.Core;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class Vcode
{
	public static interface onVcode
	{

		public abstract void VcodeError();

		public abstract void VcodeSuceess(String s, String s1, String s2);
	}

	public Vcode(DiscuzBaseActivity discuzbaseactivity, String s, String s1)
	{
		context = null;
		vcode_popupwindow = null;
		vcode_image = null;
		question_tv = null;
		refreshvcode_tv = null;
		vcode_et = null;
		question_et = null;
		vcode_layout = null;
		question_layout = null;
		onvcode = null;
		submitvcode_btn = null;
		loadingText = null;
		seccode = "";
		secqaa = "";
		asyncImageLoader = new AsyncImageLoader(new Rect(0, 0, 100, 30));
		vcodeclick = new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				String s2 = vcode_et.getText().toString();
				String s3 = question_et.getText().toString();
				if (!seccode.equals("") && s2.equals(""))
				{
					ShowMessage.getInstance(context)._showToast(
							"\u9A8C\u8BC1\u7801\u4E0D\u80FD\u4E3A\u7A7A", 3);
				} else
				{
					if (!secqaa.equals("") && s3.equals(""))
					{
						ShowMessage.getInstance(context)._showToast(
								"\u7B54\u6848\u4E0D\u80FD\u4E3A\u7A7A", 3);
						return;
					}
					if (!Core.getInstance()._hasInternet())
					{
						ShowMessage
								.getInstance(context)
								._showToast(
										"\u8BF7\u60A8\u8FDE\u63A5\u7F51\u7EDC\u540E\u91CD\u8BD5",
										3);
						return;
					}
					onvcode.VcodeSuceess(s2, s3, asyncImageLoader.VcodeCookie);
					if (vcode_popupwindow.isShowing())
					{
						vcode_popupwindow.dismiss();
						return;
					}
				}
			}
		};
		context = discuzbaseactivity;
		vcodewindow = context.getLayoutInflater().inflate(R.layout.vcode, null);
		loadingText = (TextView) vcodewindow.findViewById(R.id.loading_text);
		vcode_image = (ImageView) vcodewindow.findViewById(R.id.vcode_image);
		question_tv = (TextView) vcodewindow.findViewById(R.id.question_tv);
		vcode_et = (EditText) vcodewindow.findViewById(R.id.vcode_et);
		question_et = (EditText) vcodewindow.findViewById(R.id.question_et);
		refreshvcode_tv = (TextView) vcodewindow
				.findViewById(R.id.refreshvcode_tv);
		submitvcode_btn = (TextView) vcodewindow
				.findViewById(R.id.submitvcode_btn);
		submitvcode_btn.setOnClickListener(vcodeclick);
		seccode = s;
		secqaa = s1;
	}

	private void loadVCodeImage(String s)
	{
		asyncImageLoader.loadDrawable(context.siteAppId, s,
				new net.discuz.source.AsyncImageLoader.ImageCallback()
				{

					public void imageCacheLoaded(final Bitmap bitmap, String s1)
					{
						context.runOnUiThread(new Runnable()
						{

							public void run()
							{
								vcode_image.setImageBitmap(bitmap);
								vcode_image.postInvalidate();
							}
						});
					}

					public void imageError(String s1)
					{}

					public void imageLoaded(final Bitmap bitmap, String s1)
					{
						context.runOnUiThread(new Runnable()
						{

							public void run()
							{
								vcode_image.setImageBitmap(bitmap);
								vcode_image.postInvalidate();
							}
						});
					}
				}, false);
	}

	public void VcodeError()
	{}

	public void _showVcode()
	{
		vcode_popupwindow = new PopupWindow(vcodewindow, -1, -1, true);
		vcode_popupwindow.setBackgroundDrawable(context.getResources()
				.getDrawable(R.drawable.alpha_bg));
		vcode_popupwindow.setOutsideTouchable(true);
		DEBUG.o((new StringBuilder())
				.append("===========VCODE ACTIVITY FINISH========")
				.append(context.isFinishing()).toString());
		if (context != null)
		{
			DEBUG.o((new StringBuilder())
					.append("===========VCODE ACTIVITY FINISH========")
					.append(context.isFinishing()).toString());
			vcode_popupwindow.showAtLocation(
					context.findViewById(R.id.DiscuzActivityBox), 17, 0, 0);
		}
		vcode_layout = (LinearLayout) vcodewindow
				.findViewById(R.id.vcode_layout);
		question_layout = (LinearLayout) vcodewindow
				.findViewById(R.id.question_layout);
		if (!seccode.equals(""))
		{
			vcode_layout.setVisibility(0);
			loadVCodeImage(seccode);
			vcode_image
					.setOnClickListener(new android.view.View.OnClickListener()
					{

						public void onClick(View view)
						{
							loadVCodeImage(seccode);
						}
					});
			refreshvcode_tv
					.setOnClickListener(new android.view.View.OnClickListener()
					{

						public void onClick(View view)
						{
							loadVCodeImage(seccode);
						}
					});
			DEBUG.o((new StringBuilder())
					.append("===================\u9A8C\u8BC1\u7801\u5730\u5740==================")
					.append(seccode).toString());
		}
		if (!secqaa.equals(""))
		{
			question_layout.setVisibility(0);
			question_tv.setText(Html.fromHtml(secqaa));
		}
	}

	public void setOnVcode(onVcode onvcode1)
	{
		onvcode = onvcode1;
	}

	AsyncImageLoader asyncImageLoader;
	public DiscuzBaseActivity context;
	private TextView loadingText;
	private onVcode onvcode;
	private EditText question_et;
	private LinearLayout question_layout;
	private TextView question_tv;
	private TextView refreshvcode_tv;
	private String seccode;
	private String secqaa;
	private TextView submitvcode_btn;
	private EditText vcode_et;
	private ImageView vcode_image;
	private LinearLayout vcode_layout;
	private PopupWindow vcode_popupwindow;
	private android.view.View.OnClickListener vcodeclick;
	private View vcodewindow;

}
// 2131296256