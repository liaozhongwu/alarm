package alertForWw.OptionView;

import alertForWw.Repeatable;
import android.content.Context;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class RepeatableDialog extends LinearLayout{
	private Repeatable repeatable;
	private RadioGroup repeatableRadioGroup;
	private LinearLayout weekdayLayout;
	public RepeatableDialog(Context context){
		this(context , null);
	}
	public RepeatableDialog(Context context,Repeatable r) {
		super(context);
		this.repeatable = r.clone();
		
		setOrientation(VERTICAL);
		repeatableRadioGroup = new RadioGroup(context);
		repeatableRadioGroup.setOrientation(VERTICAL);
		repeatableRadioGroup.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		addView(repeatableRadioGroup);
		weekdayLayout = new LinearLayout(context);
		weekdayLayout.setOrientation(VERTICAL);
		for(int i = 0 ; i < Repeatable.WEEKDAYSTRS.length ; i++){
			final int temp = i;
			CheckBox checkbox = new CheckBox(context);
			checkbox.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			checkbox.setGravity(Gravity.CENTER_VERTICAL + Gravity.RIGHT);
			checkbox.setText(Repeatable.WEEKDAYSTRS[i]);
			checkbox.setChecked(repeatable.isRepeatable(temp));
			checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					repeatable.setRepeatable(temp, isChecked);
				}
			});
			weekdayLayout.addView(checkbox);
		}
		RadioButton button1 = new RadioButton(context);
		button1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		button1.setGravity(Gravity.CENTER_VERTICAL + Gravity.RIGHT);
		button1.setText(Repeatable.REPEATABLESTRS[0]);
		button1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					repeatable.setRepeatable(false);
					removeView(weekdayLayout);
				}
			}
		});
		RadioButton button2 = new RadioButton(context);
		button2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		button2.setGravity(Gravity.CENTER_VERTICAL + Gravity.RIGHT);
		button2.setText(Repeatable.REPEATABLESTRS[1]);
		button2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					repeatable.setRepeatable(true);
					addView(weekdayLayout);
				}
			}
		});
		repeatableRadioGroup.addView(button1);
		repeatableRadioGroup.addView(button2);
		button1.setChecked(!repeatable.isRepeatable());
		button2.setChecked(repeatable.isRepeatable());
	}
	public Repeatable getRepeatable(){
		return repeatable;
	}
}
