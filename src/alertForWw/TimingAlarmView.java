package alertForWw;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alertforww.R;

public class TimingAlarmView extends LinearLayout{

	private TimingAlarm timingAlarm;
	private ImageView awakeButton;
	private LinearLayout infoLayout;
	private TextView timeTextView;
	private TextView repeatableTextView;
	private TextView remarkTextView;
	private TextView shakeTextView;
	private TextView descriptionTextView;
	private ImageView deleteButton;
	
	public TimingAlarmView(Context context){super(context);}
	public TimingAlarmView(Context context,TimingAlarm alarm) {
		super(context);
		setOrientation(HORIZONTAL);
		setPadding(20, 15, 20, 15);
		setFocusable(true);
		setFocusableInTouchMode(true);
		awakeButton = new ImageView(getContext());
		infoLayout = new LinearLayout(getContext());
		infoLayout.setOrientation(VERTICAL);
		infoLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));	
		timeTextView = new TextView(getContext());
		timeTextView.setTextColor(Color.rgb(0, 200, 255));
		timeTextView.setTextSize(24);
		repeatableTextView = new TextView(getContext());
		remarkTextView = new TextView(getContext());
		shakeTextView = new TextView(getContext());
		descriptionTextView = new TextView(getContext());
		deleteButton = new ImageView(getContext());
		deleteButton.setImageResource(R.drawable.delete);
		
		setAlarm(alarm);
	}
	public void setAlarm(TimingAlarm alarm){
		
		this.timingAlarm = alarm;
		
		if(timingAlarm.isAwake()) awakeButton.setImageResource(R.drawable.awake);
		else awakeButton.setImageResource(R.drawable.sleep);
		addView(awakeButton);
		
		timeTextView.setText(timingAlarm.getHour() + ":" + timingAlarm.getMinute());
		infoLayout.addView(timeTextView);

		repeatableTextView.setText(timingAlarm.getRepeatable().toString());
		infoLayout.addView(repeatableTextView);
		
		if(timingAlarm.getRemark() != null && !timingAlarm.getRemark().equals("")){
			remarkTextView.setText(timingAlarm.getRemark());
			infoLayout.addView(remarkTextView);
		}
		
		if(timingAlarm.isShake()){
			shakeTextView.setText("震动");
			infoLayout.addView(shakeTextView);
		}
		
		if(timingAlarm.isAwake())
			descriptionTextView.setText("开启状态");
		else
			descriptionTextView.setText("关闭状态");
		infoLayout.addView(descriptionTextView);

		addView(infoLayout);
		addView(deleteButton);
	}
	public void awake(){
		timingAlarm.setAwake(true);
		awakeButton.setImageResource(R.drawable.awake);
		descriptionTextView.setText("开启状态");
	}
	public void sleep(){
		timingAlarm.setAwake(false);
		awakeButton.setImageResource(R.drawable.sleep);
		descriptionTextView.setText("关闭状态");
	}
	public void setAwakeButtonListener(OnClickListener listener){
		awakeButton.setOnClickListener(listener);
	}
	public void setDeleteButtonListener(OnClickListener listener){
		deleteButton.setOnClickListener(listener);
	}
	public TimingAlarm getAlarm(){
		return this.timingAlarm;
	}
}
