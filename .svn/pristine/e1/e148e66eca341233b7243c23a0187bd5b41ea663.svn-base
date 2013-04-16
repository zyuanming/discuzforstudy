package net.discuz.activity.siteview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.discuz.R;
import net.discuz.adapter.SmsFriendsListAdapter;
import net.discuz.app.DiscuzApp;
import net.discuz.source.DJsonReader;
import net.discuz.source.ShowMessage;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.popupwindow.SelectList;
import net.discuz.source.widget.SiteNavbar;
import net.discuz.tools.Core;
import net.discuz.tools.DiscuzStats;
import net.discuz.tools.HttpConnThread;

import org.json.JSONException;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.umeng.analytics.MobclickAgent;

public class SmsNewMessage extends DiscuzBaseActivity
{
	class ClickListener implements android.view.View.OnClickListener
	{

		public void onClick(View view)
		{
			switch (view.getId())
			{
			case 2131231086:
			default:
				return;

			case 2131230991:
				setResult(0);
				finish();
				return;

			case 2131230996:
				message = sms_new_message_information.getText().toString();
				if (friendsString.toString().trim().length() < 2)
				{
					ShowMessage.getInstance(SmsNewMessage.this)._showToast(
							"\u6536\u4EF6\u4EBA\u4E0D\u80FD\u4E3A\u7A7A", 3);
					return;
				}
				if (message.trim().equals(""))
				{
					ShowMessage.getInstance(SmsNewMessage.this)._showToast(
							"\u4E0D\u80FD\u53D1\u9001\u7A7A\u6D88\u606F", 3);
					return;
				} else
				{
					sendNewMessage(message);
					return;
				}

			case 2131231084:
				if (smsFriendsListName == null
						|| smsFriendsListName.length == 0)
				{
					ShowMessageByHandler(
							R.string.message_sms_load_null_friends, 2);
					return;
				}
				View view1 = getLayoutInflater().inflate(
						R.layout.sms_friends_seach, null);
				final PopupWindow popupwindow = new PopupWindow(view1, -1, -1,
						true);
				popupwindow.setBackgroundDrawable(new BitmapDrawable());
				popupwindow.setOutsideTouchable(true);
				if (!popupwindow.isShowing())
					popupwindow.showAtLocation(
							findViewById(R.id.DiscuzActivityBox), 80, 0, 0);
				final Button button = (Button) view1
						.findViewById(R.id.sms_friends_sub);
				button.setOnClickListener(new android.view.View.OnClickListener()
				{

					public void onClick(View view)
					{
						popupwindow.dismiss();
						_selectedFriends();
					}
				});
				((Button) view1.findViewById(R.id.sms_friends_cancel))
						.setOnClickListener(new android.view.View.OnClickListener()
						{

							public void onClick(View view)
							{
								popupwindow.dismiss();
							}
						});
				smsFriendsAdapter = new SmsFriendsListAdapter(
						SmsNewMessage.this);
				smsFriendsAdapter.setFriendsListName(smsFriendsListName);
				smsFriendsAdapter.setFriendsListID(smsFriendsListID);
				ListView listview = (ListView) view1
						.findViewById(R.id.sms_friends_list);
				listview.setAdapter(smsFriendsAdapter);
				listview.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener()
				{

					public void onItemClick(AdapterView adapterview, View view,
							int i, long l)
					{
						button.setText((new StringBuilder())
								.append("\u5B8C\u6210(")
								.append(smsFriendsAdapter
										._addOrdeleteMap(smsFriendsListName[i]))
								.append(")").toString());
					}
				});
				return;

			case 2131231089:
				selectList = new SelectList(SmsNewMessage.this,
						findViewById(R.id.DiscuzActivityBox),
						sms_new_message_friends_show_friends,
						strTolist(sms_new_message_friends_show_friends
								.getText().toString()), null);
				selectList.setGetData(getData);
				selectList.setCanDelete(true);
				selectList.popupWindowShowing();
				return;
			}
		}

		ClickListener()
		{
			super();
		}
	}

	public SmsNewMessage()
	{
		sms_new_message_receiver = null;
		sms_new_message_information = null;
		sms_new_message_friends_show_friends = null;
		sms_new_message_show_friends_layout = null;
		smsFriendsAdapter = null;
		adapter = null;
		smsFriendsListName = null;
		smsFriendsListID = null;
		selectFriendsList = new ArrayList();
		selectList = null;
		friendsString = new StringBuffer();
		httpConnListener = new net.discuz.boardcast.HttpConnReceiver.HttpConnListener()
		{

			public void onFailed(Exception exception)
			{
				dismissLoading();
			}

			public void onSucceed(String s, String s1)
			{
				DJsonReader djsonreader = new DJsonReader(s);
				String s2;
				String as[];
				String s3;
				try
				{
					djsonreader._jsonParse();
				} catch (JSONException jsonexception)
				{
					jsonexception.printStackTrace();
				}
				djsonreader._debug();
				s2 = djsonreader.getJsonString();
				as = djsonreader._getMessage();
				s3 = Core.getInstance()._getMessageByName(as);
				if (s2.indexOf("success") > -1)
				{
					setResult(200);
					try
					{
						Thread.sleep(500L);
					} catch (InterruptedException interruptedexception)
					{
						interruptedexception.printStackTrace();
					}
					ShowMessageByHandler("\u53D1\u9001\u6210\u529F", 1);
					finish();
				} else
				{
					ShowMessageByHandler(s3, 3);
				}
				dismissLoading();
			}
		};
		getData = new net.discuz.source.popupwindow.SelectList.GetData()
		{

			public void getData(final ArrayList arraylist)
			{
				runOnUiThread(new Runnable()
				{

					public void run()
					{
						StringBuffer stringbuffer = new StringBuffer();
						String s;
						for (Iterator iterator = arraylist.iterator(); iterator
								.hasNext(); stringbuffer.append(s))
						{
							s = (String) iterator.next();
							if (stringbuffer.length() > 0)
								stringbuffer.append(" , ");
						}

						sms_new_message_friends_show_friends
								.setText(stringbuffer.toString());
						friendsString = stringbuffer;
					}
				});
			}
		};
	}

	private void _selectedFriends()
	{
		Iterator iterator = smsFriendsAdapter.getSelectMap().entrySet()
				.iterator();
		selectFriendsList.clear();
		friendsString = new StringBuffer();
		String s;
		for (; iterator.hasNext(); selectFriendsList.add(s))
		{
			s = (String) ((java.util.Map.Entry) iterator.next()).getKey();
			if (friendsString.length() > 0)
				friendsString.append(" , ");
			friendsString.append(s);
		}

		if (friendsString.toString().trim().length() > 1)
		{
			if (sms_new_message_receiver.isFocused())
			{
				sms_new_message_friends_show_friends.setVisibility(0);
				sms_new_message_friends_show_friends.setText(friendsString);
				return;
			} else
			{
				sms_new_message_receiver.setText(friendsString);
				sms_new_message_friends_show_friends.setVisibility(8);
				return;
			}
		} else
		{
			sms_new_message_receiver.setText("");
			sms_new_message_friends_show_friends.setText("");
			sms_new_message_friends_show_friends.setVisibility(8);
			return;
		}
	}

	private void sendNewMessage(String s)
	{
		showLoading("\u6B63\u5728\u53D1\u9001...");
		MobclickAgent.onEvent(this, "c_sendmsg");
		DiscuzStats.add(siteAppId, "c_sendmsg");
		HashMap hashmap = new HashMap();
		hashmap.put("formhash", DiscuzApp.getInstance().getSiteInfo(siteAppId)
				.getLoginUser().getFormHash());
		String as[] = getFriendsString().toString().split(",");
		if (as.length > 0)
		{
			for (int i = 0; i < as.length; i++)
				hashmap.put((new StringBuilder()).append("users[").append(i)
						.append("]").toString(), as[i].trim());

		} else
		{
			ShowMessageByHandler("\u6536\u4EF6\u4EBA\u4E0D\u80FD\u4E3A\u7A7A",
					3);
			return;
		}
		hashmap.put("message", getMessage());
		String as1[] = new String[5];
		as1[0] = siteAppId;
		as1[1] = "module=sendpm";
		as1[2] = "touid=0";
		as1[3] = "pmid=0";
		as1[4] = "pmsubmit=yes";
		String s1 = Core.getSiteUrl(as1);
		HttpConnThread httpconnthread = new HttpConnThread(siteAppId, 1);
		httpconnthread.setUrl(s1);
		httpconnthread.setHttpMethod(1);
		httpconnthread.setPostparams(hashmap);
		httpconnthread.setCachePath("_t/");
		String s2 = getClass().getSimpleName();
		httpconnthread.setFilter(s2);
		DiscuzApp.getInstance().setHttpConnListener(s2, httpConnListener);
		DiscuzApp.getInstance().sendHttpConnThread(httpconnthread);
	}

	public StringBuffer getFriendsString()
	{
		return friendsString;
	}

	public String getMessage()
	{
		return message;
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		siteAppId = getIntent().getStringExtra("siteappid");
		Bundle bundle1 = getIntent().getExtras();
		setContentView(R.layout.sms_new_message);
		MobclickAgent.onEvent(this, "v_newmsg");
		DiscuzStats.add(siteAppId, "v_newmsg");
		smsFriendsAdapter = new SmsFriendsListAdapter(this);
		site_navbar = (SiteNavbar) findViewById(R.id.site_navbar);
		site_navbar.setTitle("\u53D1\u6D88\u606F");
		site_navbar.setTitleClickable(false);
		site_navbar.setCustomBtnText("\u53D1\u9001");
		site_navbar.setOnCustomBtnClicked(new ClickListener());
		site_navbar.setOnLeftBtnClicked(new ClickListener());
		sms_new_message_receiver = (AutoCompleteTextView) findViewById(R.id.sms_new_message_receiver);
		sms_new_message_information = (EditText) findViewById(R.id.sms_new_message_information);
		sms_new_message_show_friends_layout = (LinearLayout) findViewById(R.id.show_friends);
		sms_new_message_friends_show_friends = (Button) findViewById(R.id.sms_new_message_friends_show_friends);
		sms_new_message_friends_show_friends
				.setOnClickListener(new ClickListener());
		sms_new_message_information.setOnClickListener(new ClickListener());
		sms_new_message_show_friends_layout
				.setOnClickListener(new ClickListener());
		sms_new_message_receiver.setOnClickListener(new ClickListener());
		sms_new_message_receiver
				.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener()
				{

					public void onFocusChange(View view, boolean flag)
					{
						if (flag)
						{
							sms_new_message_friends_show_friends
									.setText(friendsString);
							sms_new_message_receiver.setText("");
							if ("".equals(sms_new_message_friends_show_friends
									.getText().toString()))
							{
								sms_new_message_friends_show_friends
										.setVisibility(8);
								return;
							} else
							{
								sms_new_message_friends_show_friends
										.setVisibility(0);
								return;
							}
						}
						if (!"".equals(sms_new_message_receiver.getText()
								.toString())
								&& friendsString
										.indexOf(sms_new_message_receiver
												.getText().toString()) == -1)
							friendsString.insert(
									0,
									(new StringBuilder())
											.append(sms_new_message_receiver
													.getText().toString())
											.append(",").toString());
						sms_new_message_receiver.setText(friendsString);
						sms_new_message_friends_show_friends.setText("");
						sms_new_message_friends_show_friends.setVisibility(8);
					}
				});
		if (bundle1 != null)
		{
			smsFriendsListName = bundle1.getStringArray("friendsListName");
			smsFriendsListID = bundle1.getStringArray("friendsListID");
			if (smsFriendsListName != null)
			{
				adapter = new ArrayAdapter(this, 0x109000a, smsFriendsListName);
				sms_new_message_receiver.setAdapter(adapter);
			}
		}
		String s = getIntent().getStringExtra("username");
		if (s != null)
		{
			sms_new_message_receiver.setText(s);
			friendsString.insert(0, (new StringBuilder()).append(s).append(",")
					.toString());
			sms_new_message_information.requestFocus();
		}
	}

	protected void onPause()
	{
		super.onPause();
		MobclickAgent.onPause(this);
	}

	protected void onResume()
	{
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public ArrayList strTolist(String s)
	{
		selectFriendsList.clear();
		String as[] = s.split(",");
		for (int i = 0; i < as.length; i++)
			selectFriendsList.add(as[i]);

		return selectFriendsList;
	}

	private final int FINISH_CODE = 0;
	public final int SEND_SUCCESS_CODE = 200;
	private ArrayAdapter adapter;
	private StringBuffer friendsString;
	net.discuz.source.popupwindow.SelectList.GetData getData;
	private net.discuz.boardcast.HttpConnReceiver.HttpConnListener httpConnListener;
	private String message;
	private ArrayList selectFriendsList;
	private SelectList selectList;
	private SiteNavbar site_navbar;
	private SmsFriendsListAdapter smsFriendsAdapter;
	private String smsFriendsListID[];
	private String smsFriendsListName[];
	private Button sms_new_message_friends_show_friends;
	private EditText sms_new_message_information;
	private AutoCompleteTextView sms_new_message_receiver;
	private LinearLayout sms_new_message_show_friends_layout;

}
// 2131296256