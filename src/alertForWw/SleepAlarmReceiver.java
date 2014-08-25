package alertForWw;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class SleepAlarmReceiver extends BroadcastReceiver{
	PowerManager powerManager;
	WakeLock wakeLock;
	@Override
	public void onReceive(Context context, Intent intent) {
		powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);  
	    wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "perform");  
	    wakeLock.acquire(); 
	    intent.setClass(context, SleepPerformActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_USER_ACTION);
		context.startActivity(intent);
	}
	
}
