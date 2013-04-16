package net.discuz.activity.siteview;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import net.discuz.R;
import net.discuz.activity.siteview.siteview_sendpost_audio.AudioImage;
import net.discuz.activity.siteview.siteview_sendpost_audio.PlayButton;
import net.discuz.activity.siteview.siteview_sendpost_audio.RecordButton;
import net.discuz.init.InitSetting;
import net.discuz.source.DEBUG;
import net.discuz.source.DFile;
import net.discuz.source.activity.DiscuzBaseActivity;
import net.discuz.source.storage.PostDraftDBHelper;
import net.discuz.tools.Tools;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class siteview_sendpost_audio extends DiscuzBaseActivity
{
	class AudioImage extends ImageView
	{

		android.view.View.OnClickListener clicker;
		private int mStartPlaying;

		public AudioImage(Context context)
		{
			super(context);
			mStartPlaying = 0;
			clicker = new View.OnClickListener()
			{

				public void onClick(View view)
				{
					new Handler().post(new Runnable()
					{
						public void run()
						{
							switch (mStartPlaying)
							{
							default:
								return;

							case 0: // '\0'
								startRecording();
								setImageResource(R.drawable.record_stop_img);
								mStartPlaying = 1;
								return;

							case 1: // '\001'
								stopRecording();
								setImageResource(R.drawable.playing_start_img);
								mStartPlaying = 2;
								return;

							case 2: // '\002'
								startPlaying();
								setImageResource(R.drawable.playing_stop_img);
								mStartPlaying = 3;
								return;

							case 3: // '\003'
								stopPlaying();
								setImageResource(R.drawable.playing_start_img);
								mStartPlaying = 2;
								return;
							}
						}
					});
				}
			};
			setOnClickListener(clicker);
		}
	}

	class PlayButton extends Button
	{

		android.view.View.OnClickListener clicker;
		private boolean mStartPlaying;

		public PlayButton(Context context)
		{
			super(context);
			mStartPlaying = true;
			clicker = new View.OnClickListener()
			{
				public void onClick(View view)
				{
					onPlay(mStartPlaying);
					PlayButton playbutton;
					boolean flag;
					if (mStartPlaying)
						setText(R.string.audio_record_stop_btn);
					else
						setText(R.string.audio_playing);
					playbutton = PlayButton.this;
					if (!mStartPlaying)
						flag = true;
					else
						flag = false;
					playbutton.mStartPlaying = flag;
				}
			};
			setText(R.string.audio_playing);
			setOnClickListener(clicker);
		}
	}

	class RecordButton extends Button
	{

		android.view.View.OnClickListener clicker;
		public boolean mStartRecording;

		public RecordButton(Context context)
		{
			super(context);
			mStartRecording = true;
			clicker = new View.OnClickListener()
			{
				public void onClick(View view)
				{
					onRecord(mStartRecording);
					RecordButton recordbutton;
					boolean flag;
					if (mStartRecording)
						setText(R.string.audio_record_stop_btn);
					else
						setText(R.string.audio_record_start_btn);
					recordbutton = RecordButton.this;
					if (!mStartRecording)
						flag = true;
					else
						flag = false;
					recordbutton.mStartRecording = flag;
				}
			};
			setText(R.string.audio_record_start_btn);
			setOnClickListener(clicker);
		}
	}

	class RecordTimerTask extends TimerTask
	{

		public void run()
		{
			handler.post(new Runnable()
			{

				public void run()
				{
					long l = scheduledExecutionTime() - time;
					if (l > 0x83d60L)
					{
						stopRecording();
						return;
					} else
					{
						recordShow.setText(Tools._getNumToDateTime(l, "mm:ss"));
						return;
					}
				}
			});
		}

		RecordTimerTask()
		{
			super();
		}
	}

	public siteview_sendpost_audio()
	{
		recordBtn = null;
		audioImage = null;
		audioBtnLayout = null;
		mRecorder = null;
		mPlayer = null;
		handler = new Handler();
		timer = null;
		recordShow = null;
		mFileName = null;
		time = 0L;
	}

	private void onPlay(boolean flag)
	{
		if (flag)
		{
			startPlaying();
			return;
		} else
		{
			stopPlaying();
			return;
		}
	}

	private void onRecord(boolean flag)
	{
		if (flag)
		{
			startRecording();
			return;
		} else
		{
			stopRecording();
			return;
		}
	}

	private void startPlaying()
	{
		mPlayer = new MediaPlayer();
		try
		{
			mPlayer.setDataSource(mFileName);
			mPlayer.setAudioStreamType(3);
			mPlayer.prepare();
			mPlayer.start();
			return;
		} catch (IOException ioexception)
		{
			ioexception.printStackTrace();
		}
	}

	private void startRecording()
	{
		recordShow.setText(R.string.audio_record_time);
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(1);
		mRecorder.setOutputFormat(1);
		mRecorder.setAudioEncoder(1);
		mRecorder.setOutputFile(mFileName);
		mRecorder.setMaxDuration(0x927c0);
		mRecorder
				.setOnInfoListener(new android.media.MediaRecorder.OnInfoListener()
				{

					public void onInfo(MediaRecorder mediarecorder, int i, int j)
					{
						timer.cancel();
						recordBtn.setText(R.string.audio_record_start_btn);
						recordBtn.mStartRecording = true;
					}
				});

		try
		{
			mRecorder.prepare();
		} catch (IOException ioexception)
		{
			ioexception.printStackTrace();
		}
		mRecorder.start();
		timerSet();
		setResult(-1);
	}

	private void stopPlaying()
	{
		mPlayer.release();
		mPlayer = null;
	}

	private void stopRecording()
	{
		mRecorder.stop();
		mRecorder.release();
		timer.cancel();
		mRecorder = null;
		mPlayer = new MediaPlayer();
		try
		{
			mPlayer.setDataSource(mFileName);
			mPlayer.setAudioStreamType(3);
			mPlayer.prepare();
		} catch (IllegalArgumentException illegalargumentexception)
		{
			illegalargumentexception.printStackTrace();
		} catch (IllegalStateException illegalstateexception)
		{
			illegalstateexception.printStackTrace();
		} catch (IOException ioexception)
		{
			ioexception.printStackTrace();
		}
		stopPlaying();
		DEBUG.o((new StringBuilder()).append(">>>>>")
				.append(Tools.readableFileSize((new File(mFileName)).length()))
				.toString());
		PostDraftDBHelper.getInstance().addItem(mFileName, 2);
	}

	private void timerSet()
	{
		timer = new Timer();
		RecordTimerTask recordtimertask = new RecordTimerTask();
		time = System.currentTimeMillis();
		timer.schedule(recordtimertask, 0L, 1000L);
	}

	protected void init()
	{
		super.init();
		audioBtnLayout = (LinearLayout) findViewById(R.id.audio_btn_layout);
		recordShow = (TextView) findViewById(R.id.record_show);
		audioImage = new AudioImage(this);
		audioImage.setImageResource(R.drawable.record_start_img);
		audioBtnLayout.addView(audioImage,
				new android.widget.LinearLayout.LayoutParams(-2, -2, 0.0F));
		long l = System.currentTimeMillis();
		if (!(new DFile())._isDirExist(InitSetting.AUDIO_PATH))
			(new File(InitSetting.AUDIO_PATH)).mkdirs();
		mFileName = (new StringBuilder()).append(InitSetting.AUDIO_PATH)
				.append(Tools._getNumToDateTime(l, null)).append("_")
				.append(String.valueOf(l).substring(4, 9)).toString();
	}

	protected void onCreate(Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.audio);
		init();
	}

	protected void onDestroy()
	{
		super.onDestroy();
		mFileName = null;
		mPlayer = null;
		mRecorder = null;
		timer = null;
	}

	private LinearLayout audioBtnLayout;
	private AudioImage audioImage;
	private Handler handler;
	private String mFileName;
	private MediaPlayer mPlayer;
	private MediaRecorder mRecorder;
	private RecordButton recordBtn;
	private TextView recordShow;
	private long time;
	private Timer timer;

}
// 2131296256