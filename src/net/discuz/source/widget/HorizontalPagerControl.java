package net.discuz.source.widget;

import net.discuz.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

public class HorizontalPagerControl extends View
{

	public HorizontalPagerControl(Context context, AttributeSet attributeset)
	{
		this(context, attributeset, 0);
	}

	public HorizontalPagerControl(Context context, AttributeSet attributeset,
			int i)
	{
		super(context, attributeset, i);
		bitmap0 = BitmapFactory.decodeResource(getResources(),
				R.drawable.pageslider0);
		bitmap1 = BitmapFactory.decodeResource(getResources(),
				R.drawable.pageslider1);
		TypedArray typedarray = context
				.obtainStyledAttributes(
						attributeset,
						net.discuz.R.styleable.net_discuz_source_widget_HorizontalPagerControl);
		int j = typedarray.getColor(0, 0xaa444444);
		int k = typedarray.getColor(1, 0xaa98b838);
		fadeDelay = typedarray.getInteger(2, 2000);
		fadeDuration = typedarray.getInteger(3, 500);
		ovalRadius = typedarray.getDimension(4, 0.0F);
		typedarray.recycle();
		barPaint = new Paint();
		barPaint.setColor(j);
		barPaint.setAntiAlias(true);
		highlightPaint = new Paint();
		highlightPaint.setColor(k);
		highlightPaint.setAntiAlias(true);
		fadeOutAnimation = new AlphaAnimation(1.0F, 0.0F);
		fadeOutAnimation.setDuration(fadeDuration);
		fadeOutAnimation.setRepeatCount(0);
		fadeOutAnimation.setInterpolator(new LinearInterpolator());
		fadeOutAnimation.setFillEnabled(true);
		fadeOutAnimation.setFillAfter(true);
	}

	private void fadeOut()
	{
		if (fadeDuration > 0)
		{
			clearAnimation();
			fadeOutAnimation.setStartTime(AnimationUtils
					.currentAnimationTimeMillis() + (long) fadeDelay);
			setAnimation(fadeOutAnimation);
		}
	}

	public int getCurrentPage()
	{
		return currentPage;
	}

	public int getNumPages()
	{
		return numPages;
	}

	public int getPageWidth()
	{
		return getWidth() / numPages;
	}

	protected void onDraw(Canvas canvas)
	{
		int i = bitmap1.getWidth();
		if (currentPage == 0)
		{
			canvas.drawBitmap(bitmap1, getWidth() / 2 - 6 - i, 0.0F, null);
			canvas.drawBitmap(bitmap0, 6 + getWidth() / 2, 0.0F, null);
		}
		if (currentPage == 1)
		{
			canvas.drawBitmap(bitmap0, getWidth() / 2 - 6 - i, 0.0F, null);
			canvas.drawBitmap(bitmap1, 6 + getWidth() / 2, 0.0F, null);
		}
	}

	public void setCurrentPage(int i)
	{
		if (i < 0 || i >= numPages)
			throw new IllegalArgumentException(
					"currentPage parameter out of bounds");
		if (currentPage != i)
		{
			currentPage = i;
			position = i * getPageWidth();
			invalidate();
		}
	}

	public void setNumPages(int i)
	{
		if (i <= 0)
		{
			throw new IllegalArgumentException("numPages must be positive");
		} else
		{
			numPages = i;
			invalidate();
			return;
		}
	}

	public void setPosition(int i)
	{
		if (position != i)
		{
			position = i;
			invalidate();
		}
	}

	private static final int DEFAULT_BAR_COLOR = 0xaa444444;
	private static final int DEFAULT_FADE_DELAY = 2000;
	private static final int DEFAULT_FADE_DURATION = 500;
	private static final int DEFAULT_HIGHLIGHT_COLOR = 0xaa98b838;
	private static final String TAG = "DeezApps.Widget.PagerControl";
	private Paint barPaint;
	Bitmap bitmap0;
	Bitmap bitmap1;
	private int currentPage;
	private int fadeDelay;
	private int fadeDuration;
	private Animation fadeOutAnimation;
	private Paint highlightPaint;
	private int numPages;
	private float ovalRadius;
	private int position;
}
// 2131296256