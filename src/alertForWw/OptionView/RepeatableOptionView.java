package alertForWw.OptionView;

import alertForWw.Repeatable;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.alertforww.R;

public class RepeatableOptionView extends OptionView{
	
	private RepeatableDialog repeatableDialog;
	private TextView optionTextView;
	private Repeatable repeatable;
	public RepeatableOptionView(Context context) {
		super(context);
		init();
	}

	public RepeatableOptionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public RepeatableOptionView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	private void init(){
		repeatable = new Repeatable();
	}
	@Override
	public void action() {
		repeatableDialog = new RepeatableDialog(getContext(), repeatable);
		new AlertDialog.Builder(getContext())  
		.setTitle("请选择") 
		.setView(repeatableDialog)
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
			}
		})    
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				setRepeatable(repeatableDialog.getRepeatable());
			}
		})
		.show();
	}

	public Repeatable getRepeatable() {
		return repeatable;
	}

	public void setRepeatable(Repeatable repeatable) {
		this.repeatable = repeatable;
		if(optionTextView == null)
			optionTextView = (TextView) findViewById(R.id.repeatable);
		optionTextView.setText(repeatable.toString());
	}
}
