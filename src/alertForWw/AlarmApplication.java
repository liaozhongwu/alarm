package alertForWw;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmApplication extends Application{
	public static final int requestCode_add_timingAlarm = 0;
	public static final int requestCode_add_sleepAlarm = 1;
	public static final int requestCode_modify_timingAlarm = 2;
	public static final int requestCode_modify_sleepAlarm = 3;
	public static final int resultCode_success = 4;
	public static final int resultCode_cancel = 5;
	Database database;
	ArrayList<TimingAlarm> timingAlarms;
	ArrayList<SleepAlarm> sleepAlarms;
	AlarmManager alarmManager;
	
	@Override
	public void onCreate() {
		super.onCreate();
		database = Database.getInstance(this);
		timingAlarms = database.queryTimingAlarm();
		sleepAlarms = database.querySleepAlarm();
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
	}
	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	public void add(TimingAlarm alarm){
		database.insert(alarm);
		timingAlarms.add(alarm);
		awake(alarm);
	}
	public void add(SleepAlarm alarm){
		database.insert(alarm);
		sleepAlarms.add(alarm);
		awake(alarm);
	}
	public void modify(TimingAlarm alarm){
		database.update(alarm);
		timingAlarms.remove(alarm);
		timingAlarms.add(alarm);
		if(alarm.isAwake()){
			sleep(alarm);
			awake(alarm);
		}
	}
	public void modify(SleepAlarm alarm){
		database.update(alarm);
		sleepAlarms.remove(alarm);
		sleepAlarms.add(alarm);
		if(alarm.isAwake()){
			sleep(alarm);
			awake(alarm);
		}
	}
	public void delete(TimingAlarm alarm){
		if(alarm.isAwake()) sleep(alarm);
		database.delete(alarm);
		timingAlarms.remove(alarm);
	}
	public void delete(SleepAlarm alarm){
		if(alarm.isAwake()) sleep(alarm);
		database.delete(alarm);
		sleepAlarms.remove(alarm);
	}
	public ArrayList<TimingAlarm> getTimingAlarms(){
		Collections.sort(timingAlarms);
		return timingAlarms;
	}
	public ArrayList<SleepAlarm> getSleepAlarms(){
		Collections.sort(sleepAlarms);
		return sleepAlarms;
	}
	public void awake(TimingAlarm alarm){
		database.updateAwake(alarm);
		timingAlarms.remove(alarm);
		timingAlarms.add(alarm);
		int hour = alarm.getHour();
		int minute = alarm.getMinute();
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTimeInMillis(System.currentTimeMillis());
		int currHour = calendar.get(Calendar.HOUR_OF_DAY);
		int currMinute = calendar.get(Calendar.MINUTE);
		if(hour * 60 + minute < currHour * 60 + currMinute){
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		int currWeekDay = calendar.get(Calendar.DAY_OF_WEEK);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Intent intent = new Intent(this, TimingAlarmReceiver.class);                                
		intent.setAction("ALARM_ACTION");
		intent.putExtra("timingAlarm", alarm);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarm.getID(), intent, 0);
		if(!alarm.getRepeatable().isRepeatable())
			alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
		else
			for(int i = 0 ; i < Repeatable.WEEKDAYSTRS.length ; i++)
				if(alarm.getRepeatable().isRepeatable(i)){
					int temp = (i + 1 < currWeekDay) ? i + 1 - currWeekDay + 7 : i + 1 - currWeekDay;
					calendar.add(Calendar.DAY_OF_WEEK, temp);
					alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 7*24*60*60*1000,pendingIntent);
					calendar.set(Calendar.DAY_OF_WEEK, currWeekDay);
				}
	}
	public void awake(SleepAlarm alarm){
		database.updateAwake(alarm);
		sleepAlarms.remove(alarm);
		sleepAlarms.add(alarm);
		int hour = alarm.getHour();
		int minute = alarm.getMinute();
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.HOUR_OF_DAY, hour);
		calendar.add(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Intent intent = new Intent(this, SleepAlarmReceiver.class);                                
		intent.setAction("ALARM_ACTION");
		intent.putExtra("sleepAlarm", alarm);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarm.getID(), intent, 0);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
	}
	public void sleep(TimingAlarm alarm){
		database.updateAwake(alarm);
		timingAlarms.remove(alarm);
		timingAlarms.add(alarm);
		Intent intent = new Intent(this, TimingAlarmReceiver.class);                                
		intent.setAction("ALARM_ACTION");
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarm.getID(), intent, 0);
		alarmManager.cancel(pendingIntent);
	}
	public void sleep(SleepAlarm alarm){
		database.updateAwake(alarm);
		sleepAlarms.remove(alarm);
		sleepAlarms.add(alarm);
		Intent intent = new Intent(this, SleepAlarmReceiver.class);                                
		intent.setAction("ALARM_ACTION");
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarm.getID(), intent, 0);
		alarmManager.cancel(pendingIntent);
	}
	public void over(TimingAlarm alarm){
		if(!alarm.getRepeatable().isRepeatable()){
			database.updateAwake(alarm);
			timingAlarms.remove(alarm);
			timingAlarms.add(alarm);
			for(TimingAlarm temp : timingAlarms)
				Log.i("test", temp.getID()+":"+temp.isAwake());
		}
	}
	public void over(SleepAlarm alarm){
		database.updateAwake(alarm);
		sleepAlarms.remove(alarm);
		sleepAlarms.add(alarm);
	}
}
