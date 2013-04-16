package net.discuz.activity.siteview;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.discuz.R;
import net.discuz.app.DiscuzApp;
import net.discuz.model.SiteInfo;
import net.discuz.source.AsyncImageLoader;
import net.discuz.source.DEBUG;
import net.discuz.source.ShowMessage;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.tools.Core;
import net.discuz.tools.Tools;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 验证码界面
 * 
 * @author lkh
 * 
 */
public class Vscode extends DiscuzBaseActivity
{
	public static interface onVcode
	{

		public abstract void VcodeError();

		public abstract void VcodeSuceess(String s, String s1, String s2);
	}

	public Vscode()
	{
		vcode_image = null;
		question_tv = null;
		refreshvcode_tv = null;
		vcode_et = null;
		question_et = null;
		vcode_layout = null;
		question_layout = null;
		submitvcode_btn = null;
		seccodeurl = "";
		secqaa = "";
		progressbar_vcode = null;
		siteappid = "";
		asyncImageLoader = new AsyncImageLoader(new Rect(0, 0, 100, 30));
		vcodeclick = new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				SiteInfo siteinfo;
				String s;
				siteinfo = DiscuzApp.getInstance().getSiteInfo(siteAppId);
				if (Tools.stringIsNullOrEmpty(siteinfo.getSiteCharset()))
					s = "utf-8";
				else
					s = siteinfo.getSiteCharset();
				String s1;
				String s2;
				try
				{
					s1 = URLEncoder.encode(vcode_et.getText().toString(), s);
					s2 = URLEncoder.encode(question_et.getText().toString(), s);
					if (!seccodeurl.equals("") && s1.equals(""))
					{
						ShowMessage.getInstance(Vscode.this)._showToast(
								"验证码不能为空", 3);
						return;
					}
					if (!secqaa.equals("") && s2.equals(""))
					{
						ShowMessage.getInstance(Vscode.this)._showToast(
								"答案不能为空", 3);
						return;
					}
					if (!Core.getInstance()._hasInternet())
					{
						ShowMessage.getInstance(Vscode.this)._showToast(
								"请您连接网络后重试", 3);
						return;
					} else
					{
						dismissLoading();
						if (Vscode.onvcode != null)
							Vscode.onvcode.VcodeSuceess(s1, s2,
									asyncImageLoader.VcodeCookie);
						finish();
					}
				} catch (UnsupportedEncodingException unsupportedencodingexception)
				{
					unsupportedencodingexception.printStackTrace();
				}
			}
		};
	}

	private void _getBundleParams()
	{
		Log.d("Vscode", "_getBundleParams() -----> Enter");
		Bundle bundle = getIntent().getExtras();
		if (bundle == null)
		{
			finish();
			return;
		} else
		{
			seccodeurl = bundle.getString("seccodeurl");
			secqaa = bundle.getString("secqaa");
			siteappid = bundle.getString("siteappid");
			Log.d("Vscode", "_getBundleParams() -----> Exit");
			return;
		}
	}

	/**
	 * 加载验证码图片的地址
	 * 
	 * @param s
	 *            验证码的URL
	 */
	private void loadVCodeImage(String s)
	{
		asyncImageLoader.loadDrawable(siteAppId, s,
				new net.discuz.source.AsyncImageLoader.ImageCallback()
				{
					public void imageCacheLoaded(Bitmap bitmap, String s1)
					{
						if (progressbar_vcode != null)
							progressbar_vcode.setVisibility(8);
						vcode_image.setImageBitmap(bitmap);
						vcode_image.postInvalidate();
					}

					public void imageError(String s1)
					{
						if (progressbar_vcode != null)
							progressbar_vcode.setVisibility(8);
					}

					public void imageLoaded(Bitmap bitmap, String s1)
					{
						if (progressbar_vcode != null)
							progressbar_vcode.setVisibility(8);
						if (vcode_image != null && bitmap != null)
						{
							vcode_image.setImageBitmap(bitmap);
							vcode_image.postInvalidate();
						}
					}
				}, false);
	}

	public void _showVcode()
	{
		if (!seccodeurl.equals(""))
		{
			vcode_layout.setVisibility(0);
			loadVCodeImage(seccodeurl);
			vcode_image
					.setOnClickListener(new android.view.View.OnClickListener()
					{

						public void onClick(View view)
						{
							progressbar_vcode.setVisibility(0);
							vcode_image.setImageDrawable(null);
							vcode_image.postInvalidate();
							loadVCodeImage(seccodeurl);
						}
					});
			refreshvcode_tv
					.setOnClickListener(new android.view.View.OnClickListener()
					{

						public void onClick(View view)
						{
							progressbar_vcode.setVisibility(0);
							vcode_image.setImageDrawable(null);
							vcode_image.postInvalidate();
							loadVCodeImage(seccodeurl);
						}
					});
			DEBUG.o((new StringBuilder())
					.append("===================验证码地址==================")
					.append(seccodeurl).toString());
		}
		if (!secqaa.equals(""))
		{
			question_layout.setVisibility(0);
			question_tv.setText(Html.fromHtml(secqaa));
		}
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		_getBundleParams();
		siteAppId = getIntent().getStringExtra("siteappid");
		DEBUG.o((new StringBuilder()).append("===========InitSiteData========")
				.append(siteAppId).toString());
		setContentView(R.layout.vcode);
		vcode_image = (ImageView) findViewById(R.id.vcode_image); // 验证码图片
		question_tv = (TextView) findViewById(R.id.question_tv); // 安全问题
		vcode_et = (EditText) findViewById(R.id.vcode_et);
		question_et = (EditText) findViewById(R.id.question_et);
		refreshvcode_tv = (TextView) findViewById(R.id.refreshvcode_tv);
		submitvcode_btn = (TextView) findViewById(R.id.submitvcode_btn);
		submitvcode_btn.setOnClickListener(vcodeclick);
		vcode_layout = (LinearLayout) findViewById(R.id.vcode_layout);
		question_layout = (LinearLayout) findViewById(R.id.question_layout);
		progressbar_vcode = (ProgressBar) findViewById(R.id.progressbar_vcode);
		_showVcode();
	}

	protected void onDestroy()
	{
		vcode_image = null;
		question_tv = null;
		refreshvcode_tv = null;
		vcode_et = null;
		question_et = null;
		vcode_layout = null;
		question_layout = null;
		onvcode = null;
		submitvcode_btn = null;
		seccodeurl = null;
		secqaa = null;
		asyncImageLoader = null;
		progressbar_vcode = null;
		super.onDestroy();
	}

	public static onVcode onvcode = null;
	AsyncImageLoader asyncImageLoader;
	private ProgressBar progressbar_vcode;
	private EditText question_et;
	private LinearLayout question_layout;
	private TextView question_tv;
	private TextView refreshvcode_tv;
	private String seccodeurl;
	private String secqaa;
	private String siteappid;
	private TextView submitvcode_btn;
	private EditText vcode_et;
	private ImageView vcode_image;
	private LinearLayout vcode_layout;
	private android.view.View.OnClickListener vcodeclick;

}
// 2131296256