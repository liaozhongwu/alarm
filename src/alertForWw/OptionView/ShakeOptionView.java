package alertForWw.OptionView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

import com.example.alertforww.R;

public class ShakeOptionView extends OptionView {
	private CheckBox optionCheckBox;
	public ShakeOptionView(Context context) {
		super(context);
	}

	public ShakeOptionView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public ShakeOptionView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	@Override
	public void action() {
		setShake(!isShake());
	}

	public boolean isShake() {
		if(optionCheckBox == null)
			return false;
		else return optionCheckBox.isChecked();
	}

	public void setShake(boolean shake) {
		if(optionCheckBox == null)
			optionCheckBox = (CheckBox) findViewById(R.id.vibrator);
		optionCheckBox.setChecked(shake);
	}
}
