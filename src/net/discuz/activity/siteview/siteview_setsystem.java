package net.discuz.activity.siteview;

import java.util.ArrayList;
import java.util.HashMap;

import net.discuz.R;
import net.discuz.adapter.SystemSetListAdapter;
import net.discuz.init.InitSetting;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class siteview_setsystem extends Activity
{

	public siteview_setsystem()
	{}

	private ArrayList getSetHistoryList()
	{
		ArrayList arraylist = new ArrayList();
		HashMap hashmap = new HashMap();
		hashmap.put("title", "\u7F13\u5B58\u8BBE\u7F6E");
		if (preferences.getInt("isHistory", 0) == 0)
		{
			hashmap.put("description", "\u5173\u95ED\u7F13\u5B58");
			hashmap.put("image", Integer.valueOf(R.drawable.btn_check_on));
		} else
		{
			hashmap.put("description", "\u542F\u7528\u7F13\u5B58");
			hashmap.put("image", Integer.valueOf(R.drawable.btn_check_off));
		}
		arraylist.add(hashmap);
		return arraylist;
	}

	private void initData()
	{
		setListView.setAdapter(setListAdapter);
	}

	private void initListener()
	{
		setListView
				.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
				{

					public void onItemClick(AdapterView adapterview, View view,
							int i, long l)
					{
						if (i == 0)
						{
							if (preferences.getInt("isHistory", 0) == 0)
							{
								InitSetting.isShowingHistory = false;
								setListAdapter.setHistoryList(0, "description",
										"\u542F\u7528\u7F13\u5B58");
								setListAdapter.setHistoryList(
										0,
										"image",
										Integer.valueOf(R.drawable.btn_check_off));
								preferences.edit().putInt("isHistory", 1)
										.commit();
							} else
							{
								InitSetting.isShowingHistory = true;
								setListAdapter.setHistoryList(0, "description",
										"\u5173\u95ED\u7F13\u5B58");
								setListAdapter.setHistoryList(
										0,
										"image",
										Integer.valueOf(R.drawable.btn_check_on));
								preferences.edit().putInt("isHistory", 0)
										.commit();
							}
						}
					}
				});
	}

	private void initWeight()
	{
		setListView = (ListView) findViewById(R.id.setList);
		setListAdapter = new SystemSetListAdapter(this, getSetHistoryList(),
				preferences);
	}

	public void finish()
	{
		preferences = null;
		setListAdapter = null;
		super.finish();
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.set_system);
		preferences = getSharedPreferences("application_tab", 0);
		initWeight();
		initData();
		initListener();
	}

	private SharedPreferences preferences;
	private SystemSetListAdapter setListAdapter;
	private ListView setListView;

}
// 2131296256