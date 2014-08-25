package alertForWw;

import android.os.Handler;
import android.os.Message;

public class CurrTimerHandler extends Handler{
	
	AlarmActivity alarmActivity;
	
	public CurrTimerHandler(AlarmActivity alarmActivity){
		this.alarmActivity = alarmActivity;
	}
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		alarmActivity.refreshCurrTime();
	}
}
