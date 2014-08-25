package alertForWw;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.alertforww.R;

public class AlarmActivity extends Activity{
	
	protected Date currTime;
	protected SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
	protected TextView currTimeTextView;
	protected Timer currTimer;
	protected TimerTask currTimerTask;
	protected CurrTimerHandler currTimerHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		runCurrTime();
	}
	public void runCurrTime(){
		if(currTimer == null && currTimerTask == null){
			currTimeTextView = (TextView)findViewById(R.id.currTime);
			currTime = new Date(System.currentTimeMillis()); 
			currTimeTextView.setText(df.format(currTime));
			
			currTimerHandler = new CurrTimerHandler(this);
			currTimer = new Timer();
			currTimerTask = new TimerTask() {
				
				@Override
				public void run() {
					currTimerHandler.sendEmptyMessage(0);
				}
			};
			currTimer.schedule(currTimerTask, 0, 1000);
		}
	}
	public void refreshCurrTime(){
		currTime = new Date(System.currentTimeMillis()); 
		currTimeTextView.setText(df.format(currTime));
	}
}
