package net.discuz.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import java.util.ArrayList;
import net.discuz.source.DEBUG;
import net.discuz.source.DFile;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.PostDraftDBHelper;

public class ReplyAttachListAdapter extends BaseAdapter
{

	public ReplyAttachListAdapter(DiscuzBaseActivity discuzbaseactivity)
	{
		attachList = null;
		idList = null;
		bitmapList = new SparseArray();
		context = discuzbaseactivity;
	}

	public int getCount()
	{
		if (attachList == null)
			return 0;
		else
			return attachList.size();
	}

	public Object getItem(int i)
	{
		return attachList.get(i);
	}

	public long getItemId(int i)
	{
		return (long) i;
	}

	public View getView(int i, View view, ViewGroup viewgroup)
	{
		ImageView imageview = new ImageView(context);
		imageview.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
		imageview.setLayoutParams(new android.widget.Gallery.LayoutParams(80,
				80));
		Bitmap bitmap = BitmapFactory.decodeFile((String) attachList.get(i));
		bitmapList.put(i, bitmap);
		imageview.setImageBitmap(bitmap);
		return imageview;
	}

	public void onDestroy()
	{
		if (attachList == null)
			return;
		for (int i = 0; i < attachList.size(); i++)
			removeListItem(i, true);

		PostDraftDBHelper.getInstance().deleteAllForReply();
		idList.clear();
		attachList.clear();
		bitmapList.clear();
	}

	public void removeListItem(int i, boolean flag)
	{
		DFile._deleterFileOrDir((String) attachList.get(i));
		if (!flag)
		{
			Long long1 = (Long) idList.get(i);
			PostDraftDBHelper.getInstance().deleteItem(long1);
			idList.remove(i);
			attachList.remove(i);
			notifyDataSetChanged();
		}
		if (bitmapList.get(i) != null)
		{
			((Bitmap) bitmapList.get(i)).recycle();
			bitmapList.remove(i);
		}
		DEBUG.o((new StringBuilder())
				.append("ReplyAttachListAdapter removeItemBy:").append(i)
				.toString());
	}

	public void setList(ArrayList arraylist, ArrayList arraylist1)
	{
		attachList = arraylist;
		idList = arraylist1;
		notifyDataSetChanged();
	}

	private ArrayList attachList;
	private SparseArray bitmapList;
	private DiscuzBaseActivity context;
	private ArrayList idList;
}
// 2131296256