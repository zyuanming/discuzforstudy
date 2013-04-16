package net.discuz.source.prototype;

import net.discuz.R;
import net.discuz.source.MessageHandler;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.widget.pulltorefresh;
import net.discuz.tools.DiscuzStats;
import android.os.Handler;
import android.widget.AbsListView;
import android.widget.ListView;

import com.umeng.analytics.MobclickAgent;

public abstract class pulltorefresh_listview_prototype
{
	public pulltorefresh_listview_prototype(
			DiscuzBaseActivity discuzbaseactivity)
	{
		handler = new Handler();
		messagehandler = null;
		initrunnable = null;
		url = null;
		context = discuzbaseactivity;
		messagehandler = new MessageHandler(context);
	}

	public pulltorefresh getPullToRefresh()
	{
		return mpulltorefresh;
	}

	public void init()
	{
		mpulltorefresh = (pulltorefresh) context.getLayoutInflater().inflate(
				R.layout.pulltorefresh, null);
		listview = (ListView) context.getLayoutInflater().inflate(
				R.layout.listview, null);
		listview.setOnScrollListener(new android.widget.AbsListView.OnScrollListener()
		{

			public void onScroll(AbsListView abslistview, int i, int j, int k)
			{
				mpulltorefresh.onFirstScrollItem(i);
			}

			public void onScrollStateChanged(AbsListView abslistview, int i)
			{}
		});
		mpulltorefresh
				.setOnLoading(new net.discuz.source.widget.pulltorefresh.OnLoading()
				{

					public void Loading()
					{
						MobclickAgent.onEvent(context, "c_ref");
						DiscuzStats.add(null, "c_ref");
						update();
					}
				});
		mpulltorefresh.addView(listview);
	}

	public abstract void newList();

	public void onDestroy()
	{
		context = null;
		mpulltorefresh = null;
		messagehandler = null;
		initrunnable = null;
		url = null;
	}

	public abstract void update();

	public DiscuzBaseActivity context;
	public Handler handler;
	public Runnable initrunnable;
	public ListView listview;
	public MessageHandler messagehandler;
	public pulltorefresh mpulltorefresh;
	public String url;
}
// 2131296256