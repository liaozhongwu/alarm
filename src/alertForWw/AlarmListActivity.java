package alertForWw;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.example.alertforww.R;

public class AlarmListActivity extends Activity{
	private AlarmApplication application;
	private TabHost tabHost;
	private LinearLayout timingAlarmlist;
	private LinearLayout sleepAlarmlist;
	private Button addTimingAlarmButton;
	private Button addSleepAlarmButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarmlist);
		application = (AlarmApplication) getApplication();
		timingAlarmlist = (LinearLayout) findViewById(R.id.timingAlarm_list);
		sleepAlarmlist = (LinearLayout) findViewById(R.id.sleepAlarm_list);
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		tabHost.addTab(tabHost.newTabSpec("timingAlarmList").setIndicator("定时闹钟").setContent(R.id.tab1));
        tabHost.addTab(tabHost.newTabSpec("sleepAlarmList").setIndicator("睡眠闹钟").setContent(R.id.tab2));
		
		addTimingAlarmButton = (Button) findViewById(R.id.timingAlarm_add);
		addTimingAlarmButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(AlarmListActivity.this, TimingAlarmActivity.class);
				startActivityForResult(intent, AlarmApplication.requestCode_add_timingAlarm);
			}
		});
		addSleepAlarmButton = (Button) findViewById(R.id.sleepAlarm_add);
		addSleepAlarmButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(AlarmListActivity.this, SleepAlarmActivity.class);
				startActivityForResult(intent, AlarmApplication.requestCode_add_sleepAlarm);
			}
		});
		listTimingAlarms();
		listSleepAlarms();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == AlarmApplication.resultCode_success){
			if(requestCode == AlarmApplication.requestCode_add_timingAlarm){
				if(data.hasExtra("timingAlarm")){
					TimingAlarm alarm = (TimingAlarm) data.getSerializableExtra("timingAlarm");
					application.add(alarm);
					tabHost.setCurrentTabByTag("timingAlarmList");
					listTimingAlarms();
				}
			}
			else if(requestCode == AlarmApplication.requestCode_modify_timingAlarm){
				if(data.hasExtra("timingAlarm")){
					TimingAlarm alarm = (TimingAlarm) data.getSerializableExtra("timingAlarm");
					application.modify(alarm);
					tabHost.setCurrentTabByTag("timingAlarmList");
					listTimingAlarms();
				}
			}
			if(requestCode == AlarmApplication.requestCode_add_sleepAlarm){
				if(data.hasExtra("sleepAlarm")){
					SleepAlarm temp = (SleepAlarm) data.getSerializableExtra("sleepAlarm");
					application.add(temp);
					tabHost.setCurrentTabByTag("sleepAlarmList");
					listSleepAlarms();
				}
			}
			if(requestCode == AlarmApplication.requestCode_modify_sleepAlarm){
				if(data.hasExtra("sleepAlarm")){
					SleepAlarm temp = (SleepAlarm) data.getSerializableExtra("sleepAlarm");
					application.modify(temp);
					tabHost.setCurrentTabByTag("sleepAlarmList");
					listSleepAlarms();
				}
			}
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		listTimingAlarms();
		listSleepAlarms();
	}
	private void listTimingAlarms(){
		timingAlarmlist.removeAllViews();
		for(final TimingAlarm alarm : application.getTimingAlarms()){
			final TimingAlarmView alarmView = new TimingAlarmView(this, alarm);
			timingAlarmlist.addView(alarmView);
			alarmView.setAwakeButtonListener(new View.OnClickListener() {
			
				@Override
				public void onClick(View view) {
					if(alarm.isAwake()){
						alarmView.sleep();
						application.sleep(alarm);
					}else{
						alarmView.awake();
						application.awake(alarm);
					}
				}
			});
			alarmView.setDeleteButtonListener(new View.OnClickListener() {
			
				@Override
				public void onClick(View view) {
					timingAlarmlist.removeView(alarmView);
					application.delete(alarm);
				}
			});
			alarmView.setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View view, MotionEvent event) {
					if(event.getAction() == MotionEvent.ACTION_DOWN)
						alarmView.setBackgroundColor(Color.BLUE);
					else if(event.getAction() == MotionEvent.ACTION_MOVE)
						alarmView.setBackgroundColor(Color.BLUE);
					else if(event.getAction() == MotionEvent.ACTION_UP){
						alarmView.setBackgroundColor(0);
						Intent intent = new Intent();
						intent.putExtra("timingAlarm", alarm);
						intent.setClass(AlarmListActivity.this, TimingAlarmActivity.class);
						startActivityForResult(intent, AlarmApplication.requestCode_modify_timingAlarm);
					}
					else
						alarmView.setBackgroundColor(0);
					return true;
				}
			});
		}
	}
	private void listSleepAlarms(){
		sleepAlarmlist.removeAllViews();
		for(final SleepAlarm alarm : application.getSleepAlarms()){
			final SleepAlarmView alarmView = new SleepAlarmView(this, alarm);
			sleepAlarmlist.addView(alarmView);
			alarmView.setAwakeButtonListener(new View.OnClickListener() {
			 
				@Override
				public void onClick(View view) {
					if(alarm.isAwake()){
						alarmView.sleep();
						application.sleep(alarm);
					}else{
						alarmView.awake();
						application.awake(alarm);
					}
				}
			});
			alarmView.setDeleteButtonListener(new View.OnClickListener() {
			
				@Override
				public void onClick(View view) {
					sleepAlarmlist.removeView(alarmView);
					application.delete(alarm);
				}
			});
			alarmView.setOnTouchListener(new View.OnTouchListener() {
				
				@Override
				public boolean onTouch(View view, MotionEvent event) {
					if(event.getAction() == MotionEvent.ACTION_DOWN)
						alarmView.setBackgroundColor(Color.BLUE);
					else if(event.getAction() == MotionEvent.ACTION_MOVE)
						alarmView.setBackgroundColor(Color.BLUE);
					else if(event.getAction() == MotionEvent.ACTION_UP){
						alarmView.setBackgroundColor(0);
						Intent intent = new Intent();
						intent.putExtra("sleepAlarm", alarm);
						intent.setClass(AlarmListActivity.this, SleepAlarmActivity.class);
						startActivityForResult(intent, AlarmApplication.requestCode_modify_sleepAlarm);
					}
					else
						alarmView.setBackgroundColor(0);
					return true;
				}
			});
		}
	}
}
