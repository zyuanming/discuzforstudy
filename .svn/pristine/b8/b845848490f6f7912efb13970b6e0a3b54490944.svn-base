package net.discuz.source.popupwindow;

import java.util.HashMap;

import net.discuz.R;
import net.discuz.source.DJsonReader;
import net.discuz.source.HttpRequest;
import net.discuz.source.InterFace.ThreadPay;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.AttachmentDBHelper;
import net.discuz.tools.Core;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PayMain
{
	private class PayTask extends AsyncTask
	{

		protected Object doInBackground(Object aobj[])
		{
			return doInBackground((String[]) aobj);
		}

		protected Void doInBackground(String as[])
		{
			try
			{
				String s = (new HttpRequest(context.siteAppId))._get(PayUrl,
						null, "utf-8");
				if (isCancelled())
					return null;
				if (s != null)
				{
					DJsonReader djsonreader = new DJsonReader(s);
					djsonreader._jsonParse();
					djsonreader._debug();
					if (djsonreader.isjson)
					{
						message = djsonreader._getMessage();
						if (message[0] == null)
						{
							jsonobj = djsonreader._getVariables();
						}
					}
				}
			} catch (Exception e)
			{
				jsonobj = null;
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Object obj)
		{
			onPostExecute((Void) obj);
		}

		protected void onPostExecute(Void void1)
		{
			super.onPostExecute(void1);
			_progress_dismiss();
			if (jsonobj != null)
			{
				_showPayMain(jsonobj);
				return;
			} else
			{
				context.ShowMessageByHandler(message, 3);
				return;
			}
		}

		protected void onPreExecute()
		{
			super.onPreExecute();
			if (!isCancelled())
			{
				progress = new ProgressDialog(context, 0);
				progress.setMessage(context.getString(R.string.willpaying));
				progress.show();
				if (inThreadPay.booleanValue())
				{
					PayMain paymain1 = PayMain.this;
					String as1[] = new String[3];
					as1[0] = context.siteAppId;
					as1[1] = "module=buythread";
					as1[2] = (new StringBuilder()).append("tid=").append(tid)
							.toString();
					paymain1.PayUrl = Core.getSiteUrl(as1);
					return;
				}
				if (inAttachPay.booleanValue())
				{
					PayMain paymain = PayMain.this;
					String as[] = new String[4];
					as[0] = context.siteAppId;
					as[1] = "module=buyattachment";
					as[2] = (new StringBuilder()).append("tid=").append(tid)
							.toString();
					as[3] = (new StringBuilder()).append("aid=").append(aid)
							.toString();
					paymain.PayUrl = Core.getSiteUrl(as);
					return;
				}
			}
		}

		private JSONObject jsonobj;
		private String message[];

		private PayTask()
		{
			super();
			jsonobj = null;
			message = null;
		}

	}

	private class paySubmitTask extends AsyncTask
	{

		protected Object doInBackground(Object aobj[])
		{
			return doInBackground((Void[]) aobj);
		}

		protected Void doInBackground(Void avoid[])
		{
			try
			{
				String s = (new HttpRequest(context.siteAppId))._post(PayUrl,
						null, postparams, "utf-8");
				if (isCancelled())
					return null;
				boolean flag;
				flag = "error".equals(s);
				if (!flag && s != null)
				{
					djson = new DJsonReader(s);
					djson._jsonParse();
					djson._debug();
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			_progress_dismiss();
			return null;
		}

		protected void onPostExecute(Object obj)
		{
			onPostExecute((Void) obj);
		}

		protected void onPostExecute(Void void1)
		{
			if (isCancelled())
				return;
			if (!djson.isjson)
			{
				context.ShowMessageByHandler(
						"\u8D2D\u4E70\u53D1\u751F\u9519\u8BEF\u6216\u7AD9\u70B9\u51FA\u73B0\u95EE\u9898",
						3);
			} else
			{
				String as[] = djson._getMessage();
				if (as[0] != null)
				{
					if (!inThreadPay.booleanValue())
					{
						if (inAttachPay.booleanValue())
							if (as[0].lastIndexOf("attachment_buy") > -1)
							{
								AttachmentDBHelper.getInstance()
										.updatePayedByAid(1, aid);
								if (onPay != null)
									onPay.onThreadPaySucceed(filename);
							} else
							{
								if (onPay != null)
									onPay.onThreadPayFail();
								context.ShowMessageByHandler(as, 3);
							}
					} else
					{
						if (as[0].lastIndexOf("_succeed") > -1)
						{
							if (onPay != null)
								onPay.onThreadPaySucceed(null);
							context.ShowMessageByHandler(
									"\u8D2D\u4E70\u6210\u529F", 1);
						} else
						{
							if (onPay != null)
								onPay.onThreadPayFail();
							context.ShowMessageByHandler(as, 3);
						}
					}
				}

			}
			if (pay_window.isShowing())
				pay_window.dismiss();
			super.onPostExecute(void1);
			return;
		}

		protected void onPreExecute()
		{
			super.onPreExecute();
		}

		private DJsonReader djson;

		private paySubmitTask()
		{
			super();
			djson = null;
		}

	}

	public PayMain(DiscuzBaseActivity discuzbaseactivity)
	{
		inThreadPay = Boolean.valueOf(false);
		inAttachPay = Boolean.valueOf(false);
		PayUrl = "";
		progress = null;
		tid = null;
		aid = null;
		getparams = null;
		postparams = null;
		onPay = null;
		pay_window = null;
		filename = null;
		formhash_tmp = null;
		context = discuzbaseactivity;
	}

	private void _progress_dismiss()
	{
		if (progress.isShowing())
			progress.dismiss();
	}

	public void _init(int i, String as[])
	{
		if (i < 1 || i > 2)
			i = 1;
		if (i != 1)
		{
			if (i == 2)
			{
				inAttachPay = Boolean.valueOf(true);
				tid = as[0];
				aid = as[1];
			}
		} else
		{
			inThreadPay = Boolean.valueOf(true);
			tid = as[0];
		}
		(new PayTask()).execute(new String[0]);
		return;
	}

	public void _showPayMain(JSONObject jsonobject)
	{
		TextView textview;
		TextView textview1;
		TextView textview2;
		TextView textview3;
		LinearLayout linearlayout;
		TextView textview4;
		TextView textview5;
		Button button;
		String s;
		int i = 0;
		formhash_tmp = jsonobject.optString("formhash");
		View view = context.getLayoutInflater().inflate(
				R.layout.siteview_show_attachpay, null);
		pay_window = new PopupWindow(view, -1, -2, true);
		pay_window.setBackgroundDrawable(context.getResources().getDrawable(
				R.drawable.alpha_bg));
		pay_window.setOutsideTouchable(true);
		pay_window.setAnimationStyle(R.style.MenuAnimation);
		pay_window.showAtLocation(context.findViewById(R.id.DiscuzActivityBox),
				80, 0, 0);
		((Button) view.findViewById(R.id.btn_close))
				.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View view1)
					{
						if (pay_window.isShowing())
							pay_window.dismiss();
					}
				});
		textview = (TextView) view.findViewById(R.id.pay_title);
		textview1 = (TextView) view.findViewById(R.id.pay_price_name);
		textview2 = (TextView) view.findViewById(R.id.pay_price);
		textview3 = (TextView) view.findViewById(R.id.pay_author);
		linearlayout = (LinearLayout) view.findViewById(R.id.pay_name_box);
		textview4 = (TextView) view.findViewById(R.id.pay_name);
		textview5 = (TextView) view.findViewById(R.id.pay_balance);
		button = (Button) view.findViewById(R.id.pay_submit);
		s = jsonobject.optString("balance");
		if (s != null)
		{
			int j = 0;
			try
			{
				j = Integer.parseInt(s);
			} catch (NumberFormatException numberformatexception)
			{
				i = 0;
			}
			i = j;
		}
		if (inThreadPay.booleanValue())
		{
			textview.setText("\u4E3B\u9898\u8D2D\u4E70");
			textview2.setText(jsonobject.optString("price"));
			textview3.setText(jsonobject.optString("author"));
			textview1.setText((new StringBuilder())
					.append("\u6D88\u8D39")
					.append(jsonobject.optJSONObject("credit").optString(
							"title")).toString());
			linearlayout.setVisibility(8);
			textview5.setText(jsonobject.optString("balance"));
			PayUrl = (new StringBuilder()).append(PayUrl)
					.append("&paysubmit=yes").toString();
		} else if (inAttachPay.booleanValue())
		{
			filename = jsonobject.optString("filename");
			textview3.setText(jsonobject.optString("author"));
			textview4.setText(jsonobject.optString("filename"));
			textview2.setText(jsonobject.optString("price"));
			textview1.setText((new StringBuilder())
					.append("\u6D88\u8D39")
					.append(jsonobject.optJSONObject("credit").optString(
							"title")).toString());
			textview5.setText(jsonobject.optString("balance"));
			PayUrl = (new StringBuilder()).append(PayUrl)
					.append("&paysubmit=yes").toString();
		}
		postparams = new HashMap();
		postparams.put("formhash", formhash_tmp);
		if (i >= 0)
		{
			button.setOnClickListener(new android.view.View.OnClickListener()
			{

				public void onClick(View view1)
				{
					progress.setMessage(context.getString(R.string.paying));
					progress.show();
					(new paySubmitTask()).execute(new Void[0]);
				}
			});
			return;
		} else
		{
			button.setEnabled(false);
			textview5.setText(context.getString(R.string.balance_insufficient));
			button.setText(R.string.balance_insufficient_btn);
			return;
		}
	}

	public void setOnPay(ThreadPay threadpay)
	{
		onPay = threadpay;
	}

	private String PayUrl;
	private String aid;
	private DiscuzBaseActivity context;
	private String filename;
	private String formhash_tmp;
	private HashMap getparams;
	private Boolean inAttachPay;
	private Boolean inThreadPay;
	private ThreadPay onPay;
	private PopupWindow pay_window;
	private HashMap postparams;
	private ProgressDialog progress;
	private String tid;

}
// 2131296256