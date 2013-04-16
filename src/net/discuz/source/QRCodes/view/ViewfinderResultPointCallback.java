package net.discuz.source.QRCodes.view;

import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

public final class ViewfinderResultPointCallback implements ResultPointCallback
{

	public ViewfinderResultPointCallback(ViewfinderView viewfinderview)
	{
		viewfinderView = viewfinderview;
	}

	public void foundPossibleResultPoint(ResultPoint resultpoint)
	{
		viewfinderView.addPossibleResultPoint(resultpoint);
	}

	private final ViewfinderView viewfinderView;
}
//2131296256