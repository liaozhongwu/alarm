package alertForWw.OptionView;

import com.example.alertforww.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

public class RemarkOptionView extends OptionView{
	private TextView remarkTextView;
	private EditText remarkEditText;
	private String remark;
	public RemarkOptionView(Context context) {
		super(context);
	}

	public RemarkOptionView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public RemarkOptionView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
	@Override
	public void action() {
		remarkEditText = new EditText(getContext());
		remarkEditText.setText(remark);
		new AlertDialog.Builder(getContext())
		.setTitle("请输入")
		.setIcon(android.R.drawable.ic_dialog_info)
		.setView(remarkEditText)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				String remark = remarkEditText.getText().toString();
				setRemark(remark);
			}
		})
		.setNegativeButton("取消", null)
		.show();
	}
	public void setRemark(String remark){
		this.remark = remark;
		if(remarkTextView == null)
			remarkTextView = (TextView) findViewById(R.id.remark);
		remarkTextView.setText(remark);
	}
	public String getRemark(){
		return this.remark;
	};
}
