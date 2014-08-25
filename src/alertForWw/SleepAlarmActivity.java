package alertForWw;

import java.util.Calendar;

import alertForWw.OptionView.RemarkOptionView;
import alertForWw.OptionView.ShakeOptionView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alertforww.R;

public class SleepAlarmActivity extends AlarmActivity{
	private SleepAlarm sleepAlarm;
	private int MIN_SLEEP_HOUR = 0;
	private int MAX_SLEEP_HOUR = 12;
	private int NORMAL_SLEEP_HOUR = 8;
	private int MIN_SLEEP_MINUTE = 0;
	private int MAX_SLEEP_MINUTE = 59;
	private int NORMAL_SLEEP_MINUTE= 0;
	private NumberPicker hourNumberPicker;
	private NumberPicker minuteNumberPicker;
	private EditText hourEditText;
	private EditText minuteEditText;
	private TextView sleepTimeTextView;
	private TextView getupTimeTextView;
	private ShakeOptionView shakeOptionView;
	private RemarkOptionView remarkOptionView;
	private Button sleepButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.sleepalarm);
		super.onCreate(savedInstanceState);
		
		hourNumberPicker = (NumberPicker) findViewById(R.id.sleepalarm_hour_numberPicker);
		minuteNumberPicker = (NumberPicker) findViewById(R.id.sleepalarm_minute_numberPicker);
		hourEditText = (EditText) findViewById(R.id.sleepalarm_hour_editText);
		minuteEditText = (EditText) findViewById(R.id.sleepalarm_minute_editText);
		sleepTimeTextView = (TextView) findViewById(R.id.sleepalarm_sleepTime);
		getupTimeTextView = (TextView) findViewById(R.id.sleepalarm_getupTime);
		hourNumberPicker.setMaxValue(MAX_SLEEP_HOUR);
		hourNumberPicker.setValue(NORMAL_SLEEP_HOUR);
		minuteNumberPicker.setMaxValue(MAX_SLEEP_MINUTE);
		minuteNumberPicker.setValue(NORMAL_SLEEP_MINUTE);
		shakeOptionView = (ShakeOptionView) findViewById(R.id.shakeOptionView);
		remarkOptionView = (RemarkOptionView) findViewById(R.id.remarkOptionView);
		sleepButton = (Button)findViewById(R.id.sleepalarm_sleep_button);
		
		Intent intent = getIntent();
		if(intent.hasExtra("sleepAlarm")){
			sleepAlarm = (SleepAlarm)intent.getSerializableExtra("sleepAlarm");
			hourNumberPicker.setValue(sleepAlarm.getHour());
			minuteNumberPicker.setValue(sleepAlarm.getMinute());
			shakeOptionView.setShake(sleepAlarm.isShake());
			remarkOptionView.setRemark(sleepAlarm.getRemark());
		}
		hourNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
				changeValue();
			}
		});
		minuteNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
			
			@Override
			public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
				changeValue();
			}
		});
		setEditTextListener();
		
		sleepButton.setOnClickListener(new Button.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				int hour = hourNumberPicker.getValue();
				int minute = minuteNumberPicker.getValue();
				boolean shake = shakeOptionView.isShake();
				String remark = remarkOptionView.getRemark();
				Intent intent = new Intent();
				if(sleepAlarm == null){
					sleepAlarm = new SleepAlarm(hour, minute, shake, remark ,true);
					intent.putExtra("sleepAlarm", sleepAlarm);
				}else{
					sleepAlarm.setHour(hour);
					sleepAlarm.setMinute(minute);
					sleepAlarm.setShake(shake);
					sleepAlarm.setRemark(remark);
					intent.putExtra("sleepAlarm", sleepAlarm);
				}
				setResult(AlarmApplication.resultCode_success, intent);
				finish();
			}
		});
		changeValue();
	}
	public void setEditTextListener(){
		hourEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				if(arg0.length() <= 0){
					hourNumberPicker.setValue(NORMAL_SLEEP_HOUR);
				}
				else if(arg0.length() <= 2){
					try {
						int hour = Integer.parseInt(arg0.toString());
						if(hour < MIN_SLEEP_HOUR){
							hourEditText.setText(""+MIN_SLEEP_HOUR);
							hourNumberPicker.setValue(MIN_SLEEP_HOUR);
						}else if(hour > MAX_SLEEP_HOUR){
							hourEditText.setText(""+MAX_SLEEP_HOUR);
							hourNumberPicker.setValue(MAX_SLEEP_HOUR);
						}else{
							hourNumberPicker.setValue(hour);
						}
					} catch (NumberFormatException e) {
						Toast.makeText(SleepAlarmActivity.this, "only number permissed", Toast.LENGTH_SHORT).show();
					}
				}
				else{
					hourEditText.setText(""+MAX_SLEEP_HOUR);
					hourNumberPicker.setValue(MAX_SLEEP_HOUR);
				}
				changeValue();
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
		hourEditText.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				if(arg1 == KeyEvent.KEYCODE_ENTER){
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(hourEditText.getWindowToken(), 0);
					hourEditText.clearFocus();
				}
				return false;
			}
		});
		minuteEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				if(arg0.length() <= 0){
					minuteNumberPicker.setValue(NORMAL_SLEEP_MINUTE);
				}
				else if(arg0.length() <= 2){
					try {
						int minute = Integer.parseInt(arg0.toString());
						if(minute < MIN_SLEEP_MINUTE){
							minuteEditText.setText(""+MIN_SLEEP_MINUTE);
							minuteNumberPicker.setValue(MIN_SLEEP_MINUTE);
						}else if(minute > MAX_SLEEP_MINUTE){
							minuteEditText.setText(""+MAX_SLEEP_MINUTE);
							minuteNumberPicker.setValue(MAX_SLEEP_MINUTE);
						}else{
							minuteNumberPicker.setValue(minute);
						}
					} catch (NumberFormatException e) {
						Toast.makeText(SleepAlarmActivity.this, "only number permissed", Toast.LENGTH_SHORT).show();
					}
				}
				else{
					minuteEditText.setText(""+MAX_SLEEP_MINUTE);
					minuteNumberPicker.setValue(MAX_SLEEP_MINUTE);
				}
				changeValue();
				hourEditText.clearFocus();
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
		minuteEditText.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View view, int keycode, KeyEvent event) {
				if(keycode == KeyEvent.KEYCODE_ENTER){
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(minuteEditText.getWindowToken(), 0);
					minuteEditText.clearFocus();
				}
				return false;
			}
		});
	}
	public void changeValue(){
		int hour = hourNumberPicker.getValue();
		int minute = minuteNumberPicker.getValue();
		StringBuilder sleepTime = new StringBuilder();
		if(hour > 0){
			sleepTime.append(hour);
			sleepTime.append("小时");
		}
		if(minute > 0){
			sleepTime.append(minute);
			sleepTime.append("分钟");
		}
		sleepTimeTextView.setText(sleepTime.toString());
		StringBuilder getupMessage = new StringBuilder();
		long currTime = System.currentTimeMillis();
		long alarmTime = currTime + (hour * 60 + minute) * 60 * 1000;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(currTime);
		int currHour = calendar.get(Calendar.HOUR_OF_DAY);
		int currMinute = calendar.get(Calendar.MINUTE);
		if(currHour + hour + (currMinute + minute) / 60 < 24)
			getupMessage.append("今天");
		else
			getupMessage.append("明天");
		calendar.setTimeInMillis(alarmTime);
		int alarmHour = calendar.get(Calendar.HOUR_OF_DAY);
		int alarmMinute = calendar.get(Calendar.MINUTE);
		if(alarmHour < 5)
			getupMessage.append("凌晨");
		else if(alarmHour < 8)
			getupMessage.append("早上");
		else if(alarmHour < 11)
			getupMessage.append("上午");
		else if(alarmHour < 13)
			getupMessage.append("中午");
		else if(alarmHour < 18)
			getupMessage.append("下午");
		else if(alarmHour < 24)
			getupMessage.append("晚上");
		getupMessage.append(alarmHour % 12);
		getupMessage.append(":");
		getupMessage.append(alarmMinute);
		getupTimeTextView.setText(getupMessage.toString());
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK)
			setResult(AlarmApplication.resultCode_cancel);
		return super.onKeyDown(keyCode, event);
	}
}
