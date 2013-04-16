package net.discuz.adapter;

import java.io.File;
import java.util.ArrayList;

import net.discuz.R;
import net.discuz.model.PostDraft;
import net.discuz.source.DEBUG;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.tools.Tools;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PostDraftAdapter extends BaseAdapter
{

	public PostDraftAdapter(DiscuzBaseActivity discuzbaseactivity)
	{
		items = new ArrayList();
		context = discuzbaseactivity;
	}

	public int getCount()
	{
		return items.size();
	}

	public PostDraft getItem(int i)
	{
		return (PostDraft) items.get(i);
	}

	public long getItemId(int i)
	{
		return (long) i;
	}

	public View getView(int i, View view, ViewGroup viewgroup)
	{
		PostDraft postdraft;
		String s;
		postdraft = (PostDraft) items.get(i);
		s = postdraft.getValue();
		Object obj;
		switch (postdraft.getType())
		{
		case 0:
			LinearLayout linearlayout1 = (LinearLayout) LayoutInflater.from(
					context).inflate(R.layout.post_draft_listview_text, null);
			((TextView) linearlayout1.findViewById(R.id.message)).setText(s);
			return linearlayout1;
		case 1:
			File file;
			obj = (LinearLayout) LayoutInflater.from(context).inflate(
					R.layout.post_draft_listview_image, null);
			file = new File(s);
			if (file.exists())
			{
				DEBUG.o((new StringBuilder())
						.append("==========filename size=======>")
						.append(file.getName()).append(" ")
						.append(Tools.readableFileSize(file.length()))
						.toString());
				((ImageView) ((LinearLayout) (obj))
						.findViewById(R.id.imagefile))
						.setImageBitmap(BitmapFactory.decodeFile(s,
								Tools.getBitmapOptions(s, 60)));
				TextView textview = (TextView) ((LinearLayout) (obj))
						.findViewById(R.id.filename);
				StringBuilder stringbuilder = new StringBuilder();
				stringbuilder.append("<font color=gray>")
						.append(textview.getText()).append("</font>")
						.append("<font color=black>: ").append(file.getName())
						.append("</font>");
				textview.setText(Html.fromHtml(stringbuilder.toString()));
				TextView textview1 = (TextView) ((LinearLayout) (obj))
						.findViewById(R.id.filesize);
				StringBuilder stringbuilder1 = new StringBuilder();
				stringbuilder1.append("<font color=gray>")
						.append(textview1.getText()).append("</font>")
						.append("<font color=black>: ")
						.append(Tools.readableFileSize(file.length()))
						.append("</font>");
				textview1.setText(Html.fromHtml(stringbuilder1.toString()));
				return ((View) (obj));
			} else
			{
				return ((View) (obj));
			}
		case 2:
			LinearLayout linearlayout = (LinearLayout) LayoutInflater.from(
					context).inflate(R.layout.post_draft_listview_text, null);
			((TextView) linearlayout.findViewById(R.id.message)).setText(s);
			return linearlayout;
		default:
			obj = null;
		}
		return ((View) (obj));

	}

	public void setList(ArrayList arraylist)
	{
		if (arraylist != null)
		{
			items = arraylist;
			notifyDataSetChanged();
			return;
		} else
		{
			items = new ArrayList();
			return;
		}
	}

	public DiscuzBaseActivity context;
	public ArrayList items;
}
// 2131296256