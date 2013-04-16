package net.discuz.activity.sitelist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.discuz.R;
import net.discuz.activity.siteview.SiteViewActivity;
import net.discuz.adapter.SearchListAdapter;
import net.discuz.asynctask.SiteSearchTask;
import net.discuz.asynctask.StrongRecommend;
import net.discuz.model.SiteInfo;
import net.discuz.source.ClearCache;
import net.discuz.source.DEBUG;
import net.discuz.source.ShowMessage;
import net.discuz.source.SiteDetail;
import net.discuz.source.QRCodes.CaptureActivity;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.TopSiteDBHelper;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.Tools;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

/**
 * 站点搜索
 * 
 * @author Ming
 * 
 */
public class SiteSearchActivity extends DiscuzBaseActivity
{
	public class LoadTop1000SiteList extends AsyncTask
	{

		protected Object doInBackground(Object aobj[])
		{
			return doInBackground((Void[]) aobj);
		}

		protected List doInBackground(Void avoid[])
		{
			localTop1000List = TopSiteDBHelper.getInstance().getAll();
			return localTop1000List;
		}

		protected void onPostExecute(Object obj)
		{
			onPostExecute((List) obj);
		}

		protected void onPostExecute(List list)
		{
			SiteSearchActivity.top1000List = list;
			DEBUG.o("======================top1000_site\u6570\u636E\u5E93\u4E2D\u52A0\u8F7D\u6570\u636E\u5B8C\u6BD5=============================");
		}

		private List localTop1000List;

		public LoadTop1000SiteList()
		{
			super();
			localTop1000List = null;
		}
	}

	public SiteSearchActivity()
	{
		searchListView = null;
		urlEditor = null;
		pageID = 1;
		pageSize = 10;
		onClick = new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				DEBUG.o((new StringBuilder())
						.append("=========CLICK LISTENER===========")
						.append(view.getId()).toString());
				switch (view.getId())
				{
				default:
					return;

				case 2131230998:
					MobclickAgent.onEvent(SiteSearchActivity.this,
							"c_enter_app_qr");
					DiscuzStats.add(null, "c_enter_app_qr");
					intent = new Intent(SiteSearchActivity.this,
							CaptureActivity.class);
					startActivityForResult(intent, 1);
					return;

				case 2131230997:
					core._hideSoftInput(urlEditor);
					finish();
					return;

				case 2131230961:
					String s = urlEditor.getText().toString().trim();
					if (s != null && s.length() > 0)
					{
						(new SiteSearchTask(SiteSearchActivity.this, s,
								pageSize, pageID,
								net.discuz.init.InitSetting.FavSiteFrom.Input,
								Boolean.valueOf(false))).execute(new String[0]);
						return;
					} else
					{
						ShowMessage
								.getInstance(SiteSearchActivity.this)
								._showToast(
										R.string.message_input_siteurl_empty, 3);
						return;
					}
				case 2131231001:
					goToUrl();
					return;
				}
			}

			private Intent intent;
		};
		onEnterListener = new android.view.View.OnKeyListener()
		{

			public boolean onKey(View view, int i, KeyEvent keyevent)
			{
				if (i == 66)
					goToUrl();
				return false;
			}
		};
		urlWatcher = new TextWatcher()
		{

			public void afterTextChanged(Editable editable)
			{
			}

			public void beforeTextChanged(CharSequence charsequence, int i,
					int j, int k)
			{
			}

			public void onTextChanged(CharSequence charsequence, int i, int j,
					int k)
			{
				if (charsequence != null && charsequence.length() > 0)
				{
					recommendTags.setVisibility(8);
					searchListBox.setVisibility(0);
					goBtn.setEnabled(true);
					InitSearchSiteListAdapter(charsequence.toString().trim());
					return;
				} else
				{
					goBtn.setEnabled(false);
					recommendTags.setVisibility(0);
					searchListBox.setVisibility(4);
					core._showSoftInput(urlEditor);
					return;
				}
			}
		};
		onScroll = new android.widget.AbsListView.OnScrollListener()
		{

			public void onScroll(AbsListView abslistview, int i, int j, int k)
			{
			}

			public void onScrollStateChanged(AbsListView abslistview, int i)
			{
				core._hideSoftInput(urlEditor);
			}
		};
	}

	private boolean IsURL(String s)
	{
		return s.replace("http://", "")
				.replace("https://", "")
				.matches(
						"([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&%\\$\\-]+)*@)*((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|localhost|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{1,10}))(\\:[0-9]+)*(/($|[a-zA-Z0-9\\.\\,\\?\\'\\\\+&%\\$#\\=~_\\-]+))*$");
	}

	private void _initListener()
	{
		findViewById(R.id.qr_btn).setOnClickListener(onClick);
		findViewById(R.id.back_btn).setOnClickListener(onClick);
		goBtn.setOnClickListener(onClick);
		urlEditor.setOnKeyListener(onEnterListener);
		urlEditor.addTextChangedListener(urlWatcher);
		searchListView.setOnScrollListener(onScroll);
	}

	private void goToUrl()
	{
		String s;
		core._hideSoftInput(urlEditor);
		s = urlEditor.getText().toString().trim();
		if (!Tools.stringIsNullOrEmpty(s))
		{
			if (IsURL(s))
			{
				showLoading(getResources().getString(R.string.loading_site));
				MobclickAgent.onEvent(this, "c_search_url");
				DiscuzStats.add(null, "c_search_url");
				core._hideSoftInput(urlEditor);
				final String url = Tools.getHttpUrl(urlEditor.getText()
						.toString().toLowerCase().trim());
				new SiteDetail(
						this,
						url,
						null,
						new net.discuz.source.service.DownLoadService.DownLoadInterface()
						{

							public void downLoadError(Exception exception)
							{
								dismissLoading();
							}

							public void downLoadSuccess(String s1)
							{
								Intent intent = new Intent();
								intent.setClass(SiteSearchActivity.this,
										SiteViewActivity.class);
								intent.putExtra("siteurl", url);
								startActivity(intent);
								dismissLoading();
								finish();
							}
						});
			}
			(new SiteSearchTask(this, s, pageSize, pageID,
					net.discuz.init.InitSetting.FavSiteFrom.Input,
					Boolean.valueOf(false))).execute(new String[0]);
		}
	}

	private void setSiteTag(final SiteInfo si, TextView textview)
	{
		String s;
		if (si.getSiteName().length() > 6)
			s = si.getSiteName().substring(0, 6);
		else
			s = si.getSiteName();
		textview.setText(s);
		textview.setOnClickListener(new android.view.View.OnClickListener()
		{

			public void onClick(View view)
			{
				new SiteDetail(
						SiteSearchActivity.this,
						si.getSiteUrl(),
						si.getSiteName(),
						new net.discuz.source.service.DownLoadService.DownLoadInterface()
						{

							public void downLoadError(Exception exception)
							{
								dismissLoading();
							}

							public void downLoadSuccess(String s)
							{
								Intent intent = new Intent();
								intent.setClass(SiteSearchActivity.this,
										SiteViewActivity.class);
								intent.putExtra("siteurl", si.getSiteUrl());
								startActivity(intent);
								dismissLoading();
								finish();
							}
						});
				MobclickAgent.onEvent(SiteSearchActivity.this,
						"c_siteindex_hot");
				DiscuzStats.add(null, "c_siteindex_hot");
			}
		});
	}

	public void InitSearchSiteListAdapter(String s1)
	{
		final String s = s1.toLowerCase();
		LinkedList linkedlist = new LinkedList();
		final List _searchlist;
		if (s.length() <= 0)
		{
			_searchlist = TopSiteDBHelper.getInstance().getByNameUrl("");
		} else
		{
			if (top1000List != null)
			{
				DEBUG.o("======================top1000_site\u4ECE\u5185\u5B58\u4E2D\u52A0\u8F7D=============================");
				Iterator iterator = top1000List.iterator();
				do
				{
					SiteInfo siteinfo;
					if (iterator.hasNext())
					{
						siteinfo = (SiteInfo) iterator.next();
						if (linkedlist.size() > 100)
						{
							if (s.length() == 0 || s.length() > 0
									&& siteinfo.getSiteNameUrl().contains(s))
							{
								SiteInfo siteinfo1 = new SiteInfo();
								siteinfo1.setIcon(siteinfo.getIcon());
								siteinfo1.setSiteCharset(siteinfo
										.getSiteCharset());
								siteinfo1.setVersion(siteinfo.getVersion());
								siteinfo1.setSiteName(siteinfo.getSiteName()
										.toLowerCase());
								siteinfo1.setSiteUrl(siteinfo.getSiteUrl()
										.toLowerCase());
								if (s.length() > 0)
									siteinfo1
											.setSiteUrl(siteinfo
													.getSiteUrl()
													.replace(
															s,
															(new StringBuilder())
																	.append("<font color=red>")
																	.append(s)
																	.append("</font>")
																	.toString()));
								siteinfo1.setProductid(siteinfo.getProductid());
								siteinfo1.setCloudid(siteinfo.getCloudId());
								linkedlist.add(siteinfo1);
							}
						}
					} else
					{
						_searchlist = linkedlist;
						break;
					}
				} while (true);
			} else
			{
				DEBUG.o("======================top1000_site\u4ECE\u6570\u636E\u5E93\u4E2D\u52A0\u8F7D=============================");
				_searchlist = TopSiteDBHelper.getInstance().getByNameUrl(s);
			}
		}
		searchListAdapter = new SearchListAdapter(this, ((List) (_searchlist)),
				Boolean.valueOf(false));
		searchListView.setAdapter(searchListAdapter);
		searchListView.setVisibility(0);
		searchListView
				.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
				{

					public void onItemClick(AdapterView adapterview, View view,
							int i, long l)
					{
						MobclickAgent.onEvent(SiteSearchActivity.this,
								"c_enter_app_top");
						DiscuzStats.add(null, "c_enter_app_top");
						urlEditor.clearFocus();
						core._hideSoftInput(urlEditor);
						if (!Environment.getExternalStorageState().equals(
								"mounted"))
							ShowMessage.getInstance(SiteSearchActivity.this)
									._showToast(R.string.message_sdcard_remove,
											3);
						showLoading(getResources().getString(
								R.string.loading_site));
						final SiteInfo siteinfo2 = (SiteInfo) _searchlist
								.get(i);
						siteinfo2.setSiteUrl(siteinfo2
								.getSiteUrl()
								.toLowerCase()
								.replace(
										(new StringBuilder())
												.append("<font color=red>")
												.append(s).append("</font>")
												.toString(), s));
						new SiteDetail(
								SiteSearchActivity.this,
								siteinfo2.getSiteUrl(),
								siteinfo2.getSiteName(),
								new net.discuz.source.service.DownLoadService.DownLoadInterface()
								{

									public void downLoadError(
											Exception exception)
									{
										dismissLoading();
									}

									public void downLoadSuccess(String s)
									{
										Intent intent = new Intent();
										intent.setClass(
												SiteSearchActivity.this,
												SiteViewActivity.class);
										intent.putExtra("siteurl",
												siteinfo2.getSiteUrl());
										startActivity(intent);
										dismissLoading();
										finish();
									}
								});
					}
				});
	}

	protected void init()
	{
		super.init();
		urlEditor = (EditText) findViewById(R.id.url_editor);
		searchListView = (ListView) findViewById(R.id.mListView);
		recommendTags = findViewById(R.id.recommend_tags);
		searchListBox = findViewById(R.id.search_list);
		goBtn = findViewById(R.id.go_btn);
		core = Core.getInstance();
		core._hideSoftInput(urlEditor);
		new StrongRecommend(this);
	}

	protected void onActivityResult(int i, int j, Intent intent)
	{
		if (i > 0 && intent != null)
		{
			Bundle bundle = intent.getExtras();
			if (bundle != null)
			{
				showLoading(getResources().getString(R.string.loading_site));
				final String url = Tools.getHttpUrl(bundle.getString(
						"QRCodesResult").trim());
				DEBUG.o((new StringBuilder())
						.append("***\u626B\u63CF\u4E8C\u7EF4\u7801\u7ED3\u679C****")
						.append(url).toString());
				new SiteDetail(
						this,
						url,
						null,
						new net.discuz.source.service.DownLoadService.DownLoadInterface()
						{

							public void downLoadError(Exception exception)
							{
								dismissLoading();
							}

							public void downLoadSuccess(String s)
							{
								Intent intent1 = new Intent();
								intent1.setClass(SiteSearchActivity.this,
										SiteViewActivity.class);
								intent1.putExtra("siteurl", url);
								startActivity(intent1);
								dismissLoading();
								finish();
								dismissLoading();
							}
						});
			}
		}
	}

	protected void onBackKeyEvent()
	{
		core._hideSoftInput(urlEditor);
		super.onBackKeyEvent();
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.site_search_activity);
		init();
		_initListener();
		MobclickAgent.onEvent(this, "v_search");
		DiscuzStats.add(null, "v_search");
	}

	protected void onDestroy()
	{
		new ClearCache();
		ClearCache._clearViewThreadTemplateCacheData();
		urlEditor = null;
		urlEditor = null;
		SearchListAdapter.siteInfo_ArrayList = null;
		searchListAdapter = null;
		searchListView = null;
		top1000List = null;
		super.onDestroy();
	}

	public void updateTags(ArrayList arraylist)
	{
		if (arraylist.size() > 0)
		{
			AbsoluteLayout absolutelayout = (AbsoluteLayout) findViewById(R.id.strong_recommend_tags);
			if (arraylist.size() > 0)
				setSiteTag((SiteInfo) arraylist.get(0),
						(TextView) absolutelayout.findViewById(R.id.sitetag1));
			if (arraylist.size() > 1)
				setSiteTag((SiteInfo) arraylist.get(1),
						(TextView) absolutelayout.findViewById(R.id.sitetag2));
			if (arraylist.size() > 2)
				setSiteTag((SiteInfo) arraylist.get(2),
						(TextView) absolutelayout.findViewById(R.id.sitetag3));
			if (arraylist.size() > 3)
				setSiteTag((SiteInfo) arraylist.get(3),
						(TextView) absolutelayout.findViewById(R.id.sitetag4));
			if (arraylist.size() > 4)
				setSiteTag((SiteInfo) arraylist.get(4),
						(TextView) absolutelayout.findViewById(R.id.sitetag5));
			absolutelayout.setVisibility(0);
		}
	}

	private static List top1000List = null;
	private Core core;
	private View goBtn;
	private android.view.View.OnClickListener onClick;
	private android.view.View.OnKeyListener onEnterListener;
	private android.widget.AbsListView.OnScrollListener onScroll;
	private int pageID;
	private int pageSize;
	private View recommendTags;
	private SearchListAdapter searchListAdapter;
	private View searchListBox;
	private ListView searchListView;
	private EditText urlEditor;
	private TextWatcher urlWatcher;

}
// 2131296256