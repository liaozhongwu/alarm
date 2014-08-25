package alertForWw;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alertforww.R;

public class SleepAlarmView extends LinearLayout{

	private SleepAlarm sleepAlarm;
	private ImageView awakeButton;
	private LinearLayout infoLayout;
	private TextView timeTextView;
	private TextView remarkTextView;
	private TextView shakeTextView;
	private TextView descriptionTextView;
	private ImageView deleteButton;
	public SleepAlarmView(Context context){super(context);}
	public SleepAlarmView(Context context, SleepAlarm alarm) {
		super(context);
		setOrientation(HORIZONTAL);
		setPadding(20, 15, 20, 15);
		awakeButton = new ImageView(getContext());
		infoLayout = new LinearLayout(getContext());
		infoLayout.setOrientation(VERTICAL);
		infoLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
		timeTextView = new TextView(getContext());
		timeTextView.setTextColor(Color.rgb(0, 200, 255));
		timeTextView.setTextSize(24);
		remarkTextView = new TextView(getContext());
		shakeTextView = new TextView(getContext());
		descriptionTextView = new TextView(getContext());
		deleteButton = new ImageView(getContext());
		deleteButton.setImageResource(R.drawable.delete);
		
		setAlarm(alarm);
	}
	public void setAlarm(SleepAlarm alarm){

		this.sleepAlarm = alarm;
		if(sleepAlarm.isAwake()) awakeButton.setImageResource(R.drawable.awake);
		else awakeButton.setImageResource(R.drawable.sleep);
		addView(awakeButton);
		
		StringBuilder time = new StringBuilder();
		if(sleepAlarm.getHour() > 0){
			time.append(sleepAlarm.getHour());
			time.append("小时");
		}
		if(sleepAlarm.getMinute() > 0){
			time.append(sleepAlarm.getMinute());
			time.append("分钟");
		}
		timeTextView.setText(time.toString());
		infoLayout.addView(timeTextView);
		
		if(sleepAlarm.getRemark() != null && !sleepAlarm.getRemark().equals("")){
			remarkTextView.setText(sleepAlarm.getRemark());
			infoLayout.addView(remarkTextView);
		}
		
		if(sleepAlarm.isShake()){
			shakeTextView.setText("震动");
			infoLayout.addView(shakeTextView);
		}
		
		if(sleepAlarm.isAwake())
			descriptionTextView.setText("开启状态");
		else
			descriptionTextView.setText("关闭状态");
		infoLayout.addView(descriptionTextView);

		addView(infoLayout);
		addView(deleteButton);
	}
	public void awake(){
		sleepAlarm.setAwake(true);
		awakeButton.setImageResource(R.drawable.awake);
		descriptionTextView.setText("开启状态");
	}
	public void sleep(){
		sleepAlarm.setAwake(false);
		awakeButton.setImageResource(R.drawable.sleep);
		descriptionTextView.setText("关闭状态");
	}
	public void setAwakeButtonListener(OnClickListener listener){
		awakeButton.setOnClickListener(listener);
	}
	public void setDeleteButtonListener(OnClickListener listener){
		deleteButton.setOnClickListener(listener);
	}
	public SleepAlarm getAlarm(){
		return this.sleepAlarm;
	}
}
