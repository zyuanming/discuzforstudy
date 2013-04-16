package net.discuz.source.prototype;

import net.discuz.R;
import net.discuz.source.MessageHandler;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.widget.pulltorefresh;
import android.os.Handler;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

public abstract class pulltorefresh_expandablelistview_prototype
{
	class ScrollListener implements AbsListView.OnScrollListener
	{

		/**
		 * 滑动停止时回调
		 */
		public void onScroll(AbsListView abslistview, int i, int j, int k)
		{
			mpulltorefresh.onFirstScrollItem(i);
		}

		public void onScrollStateChanged(AbsListView abslistview, int i)
		{}

		ScrollListener()
		{
			super();
		}
	}

	public pulltorefresh_expandablelistview_prototype(
			DiscuzBaseActivity discuzbaseactivity)
	{
		handler = new Handler();
		messagehandler = null;
		initrunnable = null;
		context = discuzbaseactivity;
		messagehandler = new MessageHandler(context);
	}

	public pulltorefresh getPullToRefresh()
	{
		return mpulltorefresh;
	}

	public void init()
	{
		// 下拉加载
		mpulltorefresh = (pulltorefresh) context.getLayoutInflater().inflate(
				R.layout.pulltorefresh, null);
		mpulltorefresh
				.setOnLoading(new net.discuz.source.widget.pulltorefresh.OnLoading()
				{

					public void Loading()
					{
						update();
					}
				});
		listview = (ExpandableListView) context.getLayoutInflater().inflate(
				R.layout.expandable_listview, null);
		listview.setOnScrollListener(new ScrollListener());
		mpulltorefresh.addView(listview);
	}

	public abstract void newList();

	public abstract void update();

	public static DiscuzBaseActivity context;
	public Handler handler;
	public Runnable initrunnable;
	public ExpandableListView listview;
	public MessageHandler messagehandler;
	public pulltorefresh mpulltorefresh;
	public String url;
}
// 2131296256