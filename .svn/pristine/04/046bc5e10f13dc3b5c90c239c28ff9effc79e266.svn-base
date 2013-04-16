package net.discuz.source.prototype;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;

import net.discuz.R;
import net.discuz.source.LimitsException;
import net.discuz.source.InterFace.OnLogin;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.tools.Core;

import org.json.JSONObject;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class sub_pulltorefresh_listview_prototype extends
		pulltorefresh_listview_prototype
{
	class ClickListener implements android.view.View.OnClickListener
	{

		public void onClick(View view)
		{
			switch (view.getId())
			{
			default:
				return;

			case R.id.retry:
				if (error_code != -501 && error_code != -502)
				{
					context.runOnUiThread(new Runnable()
					{

						public void run()
						{
							errorBox.setVisibility(8);
							isPullDownrefresh = false;
							isShowingLoding = true;
							newList();
						}
					});
					return;
				} else
				{
					Core.showLogin(context, new OnLogin()
					{

						public void loginError()
						{
						}

						public void loginSuceess(String s, JSONObject jsonobject)
						{
							context.siteAppId = s;
							context.runOnUiThread(new Runnable()
							{

								public void run()
								{
									errorBox.setVisibility(8);
								}
							});
							uid = -1L;
							isBackLod = false;
							newList();
						}
					});
					return;
				}

			case R.id.load_more_item:
				loadMore();
				return;

			case R.id.error_retry_btn:
				if (isPullDownrefresh)
				{
					loadMore.setVisibility(8);
					load.setVisibility(0);
					noMoreItem.setVisibility(8);
					update();
					return;
				} else
				{
					sub_pulltorefresh_listview_prototype sub_pulltorefresh_listview_prototype1 = sub_pulltorefresh_listview_prototype.this;
					sub_pulltorefresh_listview_prototype1.page = -1
							+ sub_pulltorefresh_listview_prototype1.page;
					loadMore();
					return;
				}
			}

		}

		ClickListener()
		{
			super();
		}
	}

	public sub_pulltorefresh_listview_prototype(
			DiscuzBaseActivity discuzbaseactivity)
	{
		super(discuzbaseactivity);
		listFooter = null;
		loadMore = null;
		load = null;
		noMoreItem = null;
		errorBox = null;
		errorItem = null;
		errorIcon = null;
		retryBoxBtn = null;
		errorItemRetryBtn = null;
		errorText = null;
		errorMessage = null;
		error_code = 0;
		errorNet = false;
		size = 0;
		page = 1;
		uid = -1L;
		errorCode = 0;
		isPullDownrefresh = false;
		isLoadMore = false;
		isNextPage = false;
		isBackLod = false;
		isShowingLoding = false;
		error = null;
		init();
	}

	private void _initListener()
	{
		noMoreItem.setOnClickListener(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
			}
		});
		load.setOnClickListener(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
			}
		});
		errorBox.setOnClickListener(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
			}
		});
		loadMore.setOnClickListener(new ClickListener());
		errorItemRetryBtn.setOnClickListener(new ClickListener());
		retryBoxBtn.setOnClickListener(new ClickListener());
		DiscuzBaseActivity discuzbaseactivity = context;
		View aview[] = new View[1];
		aview[0] = listview;
		discuzbaseactivity.onTouchListener(aview);
	}

	public void init()
	{
		super.init();
		listFooter = LayoutInflater.from(context).inflate(R.layout.list_footer,
				null);
		loadMore = listFooter.findViewById(R.id.load_more_item);
		load = listFooter.findViewById(R.id.load_item);
		noMoreItem = listFooter.findViewById(R.id.no_more_item);
		errorBox = listFooter.findViewById(R.id.error_box);
		errorItem = listFooter.findViewById(R.id.error_retry);
		errorItemRetryBtn = (Button) listFooter
				.findViewById(R.id.error_retry_btn);
		errorIcon = (ImageView) listFooter.findViewById(R.id.error_icon);
		errorText = (TextView) listFooter.findViewById(R.id.error_text);
		retryBoxBtn = (Button) listFooter.findViewById(R.id.retry);
		listview.addFooterView(listFooter);
		_initListener();
	}

	public void loadMore()
	{
		loadMore.setVisibility(8);
		load.setVisibility(0);
		noMoreItem.setVisibility(8);
	}

	public void loadMoreMessage(boolean flag)
	{
		if (flag)
		{
			loadMore.setVisibility(0);
			mpulltorefresh.isLoading = false;
			return;
		} else
		{
			loadMore.setVisibility(8);
			mpulltorefresh.isLoading = false;
			return;
		}
	}

	public boolean manageException(Exception exception, BaseAdapter baseadapter)
	{
		if (baseadapter == null || context == null)
			return false;
		isPullDownrefresh = false;
		mpulltorefresh.loadedReturnOnAsyncTask();
		if (exception instanceof UnknownHostException)
		{
			if (baseadapter.getCount() > 0)
			{
				context.ShowMessageByHandler(R.string.message_internet_error, 3);
				mpulltorefresh.isLoading = false;
			} else
			{
				errorMessage = context.getResources().getString(
						R.string.error_read);
				setListFooter(-601);
			}
		} else if (exception instanceof ConnectException)
		{
			if (baseadapter.getCount() > 0)
			{
				mpulltorefresh.isLoading = false;
				context.ShowMessageByHandler(R.string.message_no_internet_1, 3);
			} else
			{
				errorMessage = context.getResources().getString(
						R.string.error_read);
				setListFooter(-601);
			}
		} else if (exception instanceof SocketException)
		{
			if (baseadapter.getCount() > 0)
			{
				mpulltorefresh.isLoading = false;
				context.ShowMessageByHandler(R.string.message_internet_out, 3);
			} else
			{
				errorMessage = context.getResources().getString(
						R.string.message_internet_out);
				setListFooter(-601);
			}
		} else if (exception instanceof LimitsException)
		{
			errorMessage = ((LimitsException) exception).getErrorMessage();
			setListFooter(-502);
		} else
		{
			errorMessage = context.getResources()
					.getString(R.string.error_read);
			setListFooter(-301);
		}
		context.dismissLoading();
		return true;
	}

	public void newList()
	{
	}

	public void onDestroy()
	{
		errorMessage = null;
		super.onDestroy();
	}

	public void setListFooter(int i)
	{
		setListFooter(i, 0, null);
	}

	public void setListFooter(final int _errorCode, int i,
			final BaseAdapter adapter)
	{
		error_code = _errorCode;
		retryBoxBtn.setVisibility(0);
		context.runOnUiThread(new Runnable()
		{

			public void run()
			{
				switch (_errorCode)
				{
				default:
					if (size == 0)
						size = _errorCode;
					else
						size = _errorCode - size;
					if (size < 18)
					{
						loadMore.setVisibility(8);
						if (size == 0 && _errorCode == 0)
							noMoreItem.setVisibility(0);
						else
							noMoreItem.setVisibility(8);
					} else
					{
						loadMore.setVisibility(0);
						noMoreItem.setVisibility(8);
					}
					load.setVisibility(8);
					errorItem.setVisibility(8);
					errorBox.setVisibility(8);
					return;

				case -601:
					loadMore.setVisibility(8);
					load.setVisibility(8);
					noMoreItem.setVisibility(8);
					errorItem.setVisibility(8);
					errorBox.setVisibility(0);
					errorIcon.setImageResource(R.drawable.error_wifi);
					errorText.setText(R.string.error_check_net);
					errorText.setVisibility(0);
					retryBoxBtn.setText(R.string.rely_loading);
					mpulltorefresh.isLoading = true;
					return;

				case -602:
					loadMore.setVisibility(8);
					load.setVisibility(8);
					noMoreItem.setVisibility(8);
					errorItem.setVisibility(0);
					errorBox.setVisibility(8);
					listview.setSelection(adapter.getCount());
					mpulltorefresh.isLoading = true;
					return;

				case -501:
					isBackLod = true;
					loadMore.setVisibility(8);
					load.setVisibility(8);
					noMoreItem.setVisibility(8);
					errorItem.setVisibility(8);
					errorBox.setVisibility(0);
					errorIcon.setVisibility(0);
					errorIcon.setImageResource(R.drawable.error_login);
					errorText.setText(R.string.error_no_login);
					errorText.setVisibility(0);
					retryBoxBtn.setText(R.string.to_login);
					mpulltorefresh.isLoading = true;
					return;

				case -401:
					loadMore.setVisibility(8);
					load.setVisibility(8);
					noMoreItem.setVisibility(8);
					errorItem.setVisibility(8);
					errorBox.setVisibility(0);
					retryBoxBtn.setText(R.string.rely_loading);
					errorIcon.setImageResource(R.drawable.error_value);
					errorText.setText(R.string.error_no_run);
					errorText.setVisibility(0);
					mpulltorefresh.isLoading = true;
					return;

				case -410:
					loadMore.setVisibility(8);
					load.setVisibility(8);
					noMoreItem.setVisibility(8);
					errorItem.setVisibility(8);
					errorBox.setVisibility(0);
					retryBoxBtn.setText(R.string.rely_loading);
					errorIcon.setImageResource(R.drawable.error_value);
					errorText.setText(R.string.parse_json_error);
					errorText.setVisibility(0);
					mpulltorefresh.isLoading = true;
					return;

				case -502:
					loadMore.setVisibility(8);
					load.setVisibility(8);
					noMoreItem.setVisibility(8);
					errorItem.setVisibility(8);
					errorBox.setVisibility(0);
					retryBoxBtn.setVisibility(8);
					errorIcon.setImageResource(R.drawable.error_value);
					errorText.setText(errorMessage);
					errorText.setVisibility(0);
					mpulltorefresh.isLoading = true;
					return;

				case -301:
					loadMore.setVisibility(8);
					load.setVisibility(8);
					noMoreItem.setVisibility(8);
					errorItem.setVisibility(8);
					errorBox.setVisibility(0);
					retryBoxBtn.setText(R.string.rely_loading);
					errorIcon.setImageResource(R.drawable.error_value);
					errorText.setText(errorMessage);
					errorText.setVisibility(0);
					mpulltorefresh.isLoading = true;
					return;

				case -101:
					errorBox.setVisibility(8);
					loadMore.setVisibility(8);
					load.setVisibility(8);
					noMoreItem.setVisibility(8);
					errorItem.setVisibility(8);
					errorBox.setVisibility(8);
					mpulltorefresh.isLoading = false;
					return;

				case -102:
					loadMore.setVisibility(0);
					load.setVisibility(8);
					noMoreItem.setVisibility(8);
					errorItem.setVisibility(8);
					errorBox.setVisibility(8);
					mpulltorefresh.isLoading = false;
					return;
				}
			}
		});
	}

	public void update()
	{
	}

	public final int clear_flag = -101;
	protected Exception error;
	public View errorBox;
	public int errorCode;
	private ImageView errorIcon;
	private View errorItem;
	private Button errorItemRetryBtn;
	public String errorMessage;
	public boolean errorNet;
	private TextView errorText;
	public final int error_check_net_flag = -601;
	private int error_code;
	public final int error_json_flag = -410;
	public final int error_net_flag = -602;
	public final int error_no_data_flag = -301;
	public final int error_no_login_flag = -501;
	public final int error_no_run_flag = -401;
	public final int error_not_limits = -502;
	public boolean isBackLod;
	protected boolean isLoadMore;
	protected boolean isNextPage;
	public boolean isPullDownrefresh;
	public boolean isShowingLoding;
	private View listFooter;
	private View load;
	private View loadMore;
	public final int loadMore_flag = -102;
	private View noMoreItem;
	protected int page;
	private Button retryBoxBtn;
	private int size;
	protected long uid;

}
// 2131296256