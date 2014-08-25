package alertForWw.OptionView;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public abstract class OptionView extends LinearLayout{
	protected OptionView(Context context) {
		super(context);
		init();
	}

	protected OptionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	protected OptionView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	private void init(){
		setGravity(Gravity.CENTER_VERTICAL);
		setOrientation(HORIZONTAL);
		setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_DOWN)
					setBackgroundColor(Color.BLUE);
				else if(event.getAction() == MotionEvent.ACTION_UP){
					setBackgroundColor(0);
					action();
				}
				return true;
			}
		});
	}
	protected abstract void action();
}
