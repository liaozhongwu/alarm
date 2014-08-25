package alertForWw;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.alertforww.R;

public class SleepPerformActivity extends AlarmActivity{
	private SleepAlarm alarm;
	Window window;
	Uri alert;
	MediaPlayer mMediaPlayer; 
	Vibrator vibrator;  
	TextView remarkTextView;
	Button closeAlarmButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		setContentView(R.layout.perform);
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		alarm = (SleepAlarm)intent.getSerializableExtra("sleepAlarm");
		alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM); 
		mMediaPlayer = new MediaPlayer();
		try {
			mMediaPlayer.setDataSource(this, alert);
			final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE); 
			if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) { 
				mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM); 
				mMediaPlayer.setLooping(true); 
				mMediaPlayer.prepare(); 
				mMediaPlayer.start(); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(alarm.isShake()){
			vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);  
			long[] pattern = {100,400};
			vibrator.vibrate(pattern, 1);
		}
		remarkTextView = (TextView) findViewById(R.id.perform_remark);
		remarkTextView.setText(alarm.getRemark());
		closeAlarmButton = (Button) findViewById(R.id.perform_closealarm);
		closeAlarmButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				alarm.setAwake(false);
				((AlarmApplication) getApplication()).over(alarm);
				SleepPerformActivity.this.finish();
			}
		});
	}
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
	@Override
	public void finish() {
		super.finish();
		if(mMediaPlayer != null)
			mMediaPlayer.stop();
		if(vibrator != null)
			vibrator.cancel();
	}
}
