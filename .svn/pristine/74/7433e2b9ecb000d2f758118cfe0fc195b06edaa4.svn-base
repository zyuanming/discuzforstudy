package net.discuz.source.widget;

import net.discuz.R;
import net.discuz.source.DEBUG;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

/**
 * 加载显示辅助类
 * 
 * @author Ming
 * 
 */
public class Loading
{

	public Loading(Context context, ViewGroup viewgroup)
	{
		mContext = context;
		mParentView = viewgroup;
		mAlphaAnim = new AlphaAnimation(1.0F, 0.0F);
		mAlphaAnim.setDuration(300L);
		mHandler = new Handler(Looper.getMainLooper());
		init();
	}

	private void init()
	{
		if (mParentView != null)
		{
			mLoadingView = LayoutInflater.from(mContext).inflate(
					R.layout.loading, null);
			mLoadingText = (TextView) mLoadingView
					.findViewById(R.id.loading_text);
			mLoadingView.setVisibility(8);
			android.widget.RelativeLayout.LayoutParams layoutparams = new android.widget.RelativeLayout.LayoutParams(
					-2, -2);
			layoutparams.addRule(14);
			layoutparams.addRule(15);
			mLoadingView.setLayoutParams(layoutparams);
			mParentView.addView(mLoadingView);
		}
	}

	public void dismissLoading()
	{
		mHandler.post(new Runnable()
		{

			public void run()
			{
				if (mLoadingView != null && mLoadingView.isShown())
				{
					mLoadingView.startAnimation(mAlphaAnim);
					mLoadingView.setVisibility(8);
					return;
				} else
				{
					DEBUG.o("######dismissLoading\u7528\u6CD5\u4E0D\u5BF9######");
					return;
				}
			}
		});
	}

	public boolean isShown()
	{
		return mLoadingView != null && mLoadingView.isShown();
	}

	public void showLoading(final String loadingText)
	{
		mHandler.post(new Runnable()
		{

			public void run()
			{
				if (mLoadingView != null && loadingText != null)
				{
					mLoadingText.setText(loadingText);
					mLoadingView.setVisibility(0);
					return;
				} else
				{
					DEBUG.o("######showLoading\u7528\u6CD5\u4E0D\u5BF9######");
					return;
				}
			}
		});
	}

	private AlphaAnimation mAlphaAnim;
	private Context mContext;
	private Handler mHandler;
	private TextView mLoadingText;
	private View mLoadingView;
	private ViewGroup mParentView;

}
// 2131296256