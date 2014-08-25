package alertForWw;

import java.util.Calendar;
import java.util.Locale;

import alertForWw.OptionView.RemarkOptionView;
import alertForWw.OptionView.RepeatableOptionView;
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
import android.widget.Toast;

import com.example.alertforww.R;

public class TimingAlarmActivity extends AlarmActivity{
	private TimingAlarm timingAlarm;
	private NumberPicker hourNumberPicker;
	private NumberPicker minuteNumberPicker;
	private EditText hourEditText;
	private EditText minuteEditText;
	private RepeatableOptionView repeatableOptionView;
	private ShakeOptionView shakeOptionView;
	private RemarkOptionView remarkOptionView;
	private Button confirmButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.timingalarm);
		super.onCreate(savedInstanceState);
		
		hourNumberPicker = (NumberPicker) findViewById(R.id.timingalarm_hour_numberPicker);
		minuteNumberPicker = (NumberPicker) findViewById(R.id.timingalarm_minute_numberPicker);
		hourNumberPicker.setMaxValue(23);
		minuteNumberPicker.setMaxValue(59);
		hourEditText = (EditText) findViewById(R.id.timingalarm_hour_editText);
		minuteEditText = (EditText) findViewById(R.id.timingalarm_minute_editText);
		repeatableOptionView = (RepeatableOptionView) findViewById(R.id.repeatableOptionView);
		shakeOptionView = (ShakeOptionView) findViewById(R.id.shakeOptionView);
		remarkOptionView = (RemarkOptionView) findViewById(R.id.remarkOptionView);
		
		Intent intent = getIntent();
		if(intent.hasExtra("timingAlarm")){
			timingAlarm = (TimingAlarm) intent.getSerializableExtra("timingAlarm");
			hourNumberPicker.setValue(timingAlarm.getHour());
			minuteNumberPicker.setValue(timingAlarm.getMinute());
			repeatableOptionView.setRepeatable(timingAlarm.getRepeatable());
			shakeOptionView.setShake(timingAlarm.isShake());
			remarkOptionView.setRemark(timingAlarm.getRemark());
		}
		else{
			Calendar calendar = Calendar.getInstance(Locale.CHINA);
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.add(Calendar.MINUTE, 1);
			hourNumberPicker.setValue(calendar.get(Calendar.HOUR_OF_DAY));
			minuteNumberPicker.setValue(calendar.get(Calendar.MINUTE));
		}
		hourEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				if(arg0.length() > 0 &&arg0.length() <= 2){
					try {
						int hour = Integer.parseInt(arg0.toString());
						if(hour < 0){
							hourEditText.setText("0");
							hourNumberPicker.setValue(0);
							clearFocus(hourEditText);
						}else if(hour > 23){
							hourEditText.setText("23");
							hourNumberPicker.setValue(23);
							clearFocus(hourEditText);
						}else{
							hourNumberPicker.setValue(hour);
						}
					} catch (NumberFormatException e) {
						Toast.makeText(TimingAlarmActivity.this, "only number permissed", Toast.LENGTH_SHORT).show();
					}
				}
				else{
					setCurrTime();
					clearFocus(hourEditText);
				}
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
					clearFocus(hourEditText);
				}
				return false;
			}
		});
		minuteEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				if(arg0.length() > 0 &&arg0.length() <= 2){
					try {
						int hour = Integer.parseInt(arg0.toString());
						if(hour < 0){
							minuteEditText.setText("0");
							minuteNumberPicker.setValue(0);
							clearFocus(minuteEditText);
						}else if(hour > 59){
							minuteEditText.setText("59");
							minuteNumberPicker.setValue(59);
							clearFocus(minuteEditText);
						}else{
							minuteNumberPicker.setValue(hour);
						}
					} catch (NumberFormatException e) {
						Toast.makeText(TimingAlarmActivity.this, "only number permissed", Toast.LENGTH_SHORT).show();
					}
				}
				else{
					setCurrTime();
					clearFocus(minuteEditText);
				}
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
					clearFocus(minuteEditText);
				}
				return false;
			}
		});
		confirmButton = (Button) findViewById(R.id.timingalarm_confirm_button);
		confirmButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				int hour = hourNumberPicker.getValue();
				int minute = minuteNumberPicker.getValue();
				Repeatable repeatable = repeatableOptionView.getRepeatable();
				boolean shake = shakeOptionView.isShake();
				String remark = remarkOptionView.getRemark();
				Intent intent = new Intent();
				if(timingAlarm == null){
					timingAlarm = new TimingAlarm(hour, minute, repeatable, shake, remark, true);
					intent.putExtra("timingAlarm", timingAlarm);
				}else{
					timingAlarm.setHour(hour);
					timingAlarm.setMinute(minute);
					timingAlarm.setRepeatable(repeatable);
					timingAlarm.setShake(shake);
					timingAlarm.setRemark(remark);
					intent.putExtra("timingAlarm", timingAlarm);
				}
				setResult(AlarmApplication.resultCode_success, intent);
				finish();
			}
		});
	}
	private void clearFocus(EditText editText){
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		editText.clearFocus();
	}
	private void setCurrTime(){
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.setTimeInMillis(System.currentTimeMillis());
		hourEditText.setText(""+calendar.get(Calendar.HOUR_OF_DAY));
		hourNumberPicker.setValue(calendar.get(Calendar.HOUR_OF_DAY));
		minuteEditText.setText(""+calendar.get(Calendar.MINUTE));
		minuteNumberPicker.setValue(calendar.get(Calendar.MINUTE));
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK)
			setResult(AlarmApplication.resultCode_cancel);
		return super.onKeyDown(keyCode, event);
	}
}
