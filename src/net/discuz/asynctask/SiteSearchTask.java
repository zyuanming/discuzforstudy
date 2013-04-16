package net.discuz.asynctask;

import java.util.ArrayList;
import java.util.Iterator;

import net.discuz.R;
import net.discuz.activity.sitelist.SiteSearchActivity;
import net.discuz.activity.siteview.SiteViewActivity;
import net.discuz.adapter.SearchListAdapter;
import net.discuz.model.SiteInfo;
import net.discuz.source.DEBUG;
import net.discuz.source.HttpRequest;
import net.discuz.source.ShowMessage;
import net.discuz.source.SiteDetail;
import net.discuz.tools.Core;
import net.discuz.tools.Tools;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class SiteSearchTask extends AsyncTask
{
	public class JsonReaderSiteList
	{

		public String error;
		public ArrayList siteinfoList;
		public String sitename;

		public JsonReaderSiteList(String s)
		{
			super();
			sitename = null;
			siteinfoList = null;
			error = "";
			if (!s.contains("No match result."))
			{
				try
				{
					JSONObject jsonobject = (new JSONObject(s))
							.optJSONObject("res");
					siteinfoList = new ArrayList();
					if (jsonobject != null)
					{
						JSONArray jsonarray = jsonobject.optJSONArray("data");
						if (jsonarray == null)
						{
							try
							{
								totalNum = jsonobject.optInt("totalNum");
								return;
							} catch (Exception exception)
							{
								exception.printStackTrace();
								error = exception.getMessage();
								siteinfoList = null;
								totalNum = 0;
							}
						} else
						{
							for (int i = 0; i < jsonarray.length(); i++)
							{
								SiteInfo siteinfo = new SiteInfo();
								siteinfo._initSearchValue(jsonarray
										.getJSONObject(i));
								siteinfoList.add(siteinfo);
							}
						}
					}
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public SiteSearchTask(SiteSearchActivity sitesearchactivity, String s,
			int i, int j, net.discuz.init.InitSetting.FavSiteFrom favsitefrom,
			Boolean boolean1)
	{
		search_siteurl = null;
		pageSize = 10;
		pageId = 1;
		append = Boolean.valueOf(false);
		totalNum = 0;
		errorMessage = "";
		search_siteurl = s;
		activity = sitesearchactivity;
		from = favsitefrom;
		pageSize = i;
		pageId = j;
		append = boolean1;
	}

	protected Object doInBackground(Object aobj[])
	{
		return doInBackground((String[]) aobj);
	}

	protected ArrayList doInBackground(String as[])
	{
		String s1;
		ArrayList arraylist = null;
		int i = (-1 + pageId) * pageSize;
		try
		{
			HttpRequest httprequest = new HttpRequest(activity.siteAppId);
			StringBuilder stringbuilder = (new StringBuilder())
					.append("http://api.discuz.qq.com/mobile/site/search?");
			Core core = Core.getInstance();
			String as1[] = new String[4];
			as1[0] = (new StringBuilder()).append("from=")
					.append(from.getValue()).toString();
			as1[1] = (new StringBuilder()).append("num=").append(pageSize)
					.toString();
			as1[2] = (new StringBuilder()).append("q=").append(search_siteurl)
					.toString();
			as1[3] = (new StringBuilder()).append("start=").append(i)
					.toString();
			s1 = httprequest._get(stringbuilder.append(core._getParamsSig(as1))
					.toString(), null, "utf-8", null);
			String s = s1;
			if (!isCancelled())
			{
				try
				{
					DEBUG.o((new StringBuilder()).append("resultString:")
							.append(s).toString());
					if ("error".equals(s))
					{
						errorMessage = activity
								.getString(R.string.message_sitelist_search_error);
						return null;
					}
					arraylist = (new JsonReaderSiteList(s)).siteinfoList;
				} catch (Exception e)
				{
					e.printStackTrace();
					if (activity != null)
					{
						errorMessage = activity
								.getString(R.string.message_sitelist_search_error);
						return null;
					}
				}
				return arraylist;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			if (activity != null)
				errorMessage = activity
						.getString(R.string.message_cloud_error_warning);
		}
		return null;
	}

	protected void onCancelled()
	{
		super.onCancelled();
	}

	protected void onPostExecute(Object obj)
	{
		onPostExecute((ArrayList) obj);
	}

	protected void onPostExecute(ArrayList arraylist)
	{
		if (activity == null || isCancelled())
			return;
		activity.dismissLoading();
		if (arraylist == null || arraylist.size() == 0)
			if (Tools.stringIsNullOrEmpty(errorMessage))
			{
				ShowMessage.getInstance(activity)._showToast(
						(new StringBuilder())
								.append("\u672A\u641C\u7D22\u5230  ")
								.append(search_siteurl)
								.append(" \u7684\u4FE1\u606F!").toString(), 2,
						3);
				return;
			} else
			{
				ShowMessage.getInstance(activity)
						._showToast(errorMessage, 3, 3);
				return;
			}
		EditText _tmp = (EditText) activity
				.findViewById(R.id.sitelist_edit_url);
		SiteInfo siteinfo;
		for (Iterator iterator = arraylist.iterator(); iterator.hasNext(); siteinfo
				.setSiteUrl(siteinfo
						.getSiteUrl()
						.toLowerCase()
						.replace(
								search_siteurl,
								(new StringBuilder())
										.append("<font color=red>")
										.append(search_siteurl)
										.append("</font>").toString())))
		{
			siteinfo = (SiteInfo) iterator.next();
			siteinfo.setSiteName(siteinfo
					.getSiteName()
					.toLowerCase()
					.replace(
							search_siteurl,
							(new StringBuilder()).append("<font color=red>")
									.append(search_siteurl).append("</font>")
									.toString()));
		}

		SearchListAdapter searchlistadapter = new SearchListAdapter(activity,
				arraylist, append);
		ListView listview = (ListView) activity.findViewById(R.id.mListView);
		if (listview != null)
		{
			if (SearchListAdapter.siteInfo_ArrayList.size() < totalNum)
				pageId = 1 + pageId;
			listview.setAdapter(searchlistadapter);
			listview.setVisibility(0);
			listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
			{

				public void onItemClick(AdapterView adapterview, View view,
						int i, long l)
				{
					activity.showLoading(activity.getResources().getString(
							R.string.loading_site));
					if (!Environment.getExternalStorageState()
							.equals("mounted"))
						ShowMessage.getInstance(activity)._showToast(
								R.string.message_sdcard_remove, 3);
					final SiteInfo siteinfo1 = (SiteInfo) SearchListAdapter.siteInfo_ArrayList
							.get(i);
					if (isCancelled())
					{
						return;
					} else
					{
						new SiteDetail(
								activity,
								siteinfo1.getSiteUrl().replace(
										(new StringBuilder())
												.append("<font color=red>")
												.append(search_siteurl)
												.append("</font>").toString(),
										search_siteurl),
								siteinfo1.getSiteName().replace(
										(new StringBuilder())
												.append("<font color=red>")
												.append(search_siteurl)
												.append("</font>").toString(),
										search_siteurl),
								new net.discuz.source.service.DownLoadService.DownLoadInterface()
								{

									public void downLoadError(
											Exception exception)
									{
										activity.dismissLoading();
									}

									public void downLoadSuccess(String s)
									{
										Intent intent = new Intent();
										intent.setFlags(0x40000000);
										intent.putExtra(
												"siteurl",
												siteinfo1
														.getSiteUrl()
														.replace(
																"<font color=red>",
																"")
														.replace("</font>", ""));
										intent.setClass(activity,
												SiteViewActivity.class);
										activity.startActivity(intent);
										activity.dismissLoading();
										activity.finish();
									}
								});
						return;
					}
				}
			});
		}
		if (append.booleanValue())
			listview.setSelection(listview.getCount() - arraylist.size()
					- listview.getFooterViewsCount());
		super.onPostExecute(arraylist);
	}

	protected void onPreExecute()
	{
		super.onPreExecute();
		if (activity != null)
			activity.showLoading(activity.getResources().getString(
					R.string.Searching_site));
	}

	private SiteSearchActivity activity;
	private Boolean append;
	public String errorMessage;
	private net.discuz.init.InitSetting.FavSiteFrom from;
	private int pageId;
	private int pageSize;
	private String search_siteurl;
	public int totalNum;

}
// 2131296256