package forms.formtemplates.Utils;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import forms.formtemplates.R;

public class CustomEditText extends EditText{

	private Drawable imgCloseButton = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_cancel_black_24dp, null);
			//getResources().getDrawable(R.drawable.ic_clear);
	
	public CustomEditText(Context context) {
		super(context);
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	void init(){
		imgCloseButton.setBounds(0, 0, imgCloseButton.getIntrinsicWidth(), imgCloseButton.getIntrinsicHeight());
		
		handleClearButton();
		
		this.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				CustomEditText et = CustomEditText.this;
				
				if (et.getCompoundDrawables()[2] == null)
				return false;
				
				if (event.getAction() != MotionEvent.ACTION_UP)
				return false;
				
				if (event.getX() > et.getWidth() - et.getPaddingRight() - imgCloseButton.getIntrinsicWidth()) {
				et.setText("");
				CustomEditText.this.handleClearButton();
				}
				return false;
			}
			
		});
		
		
		this.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				CustomEditText.this.handleClearButton();
				
			}
			
		});
	}

	protected void handleClearButton() {
		if(this.getText().toString().equals("")){
			this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], null, this.getCompoundDrawables()[3]);
		}
		else{
			this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], imgCloseButton, this.getCompoundDrawables()[3]);
		}
/*		if (this.getText().toString().equals("")){
			this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], null, this.getCompoundDrawables()[3]);
		}
		else{
//			this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], imgCloseButton, this.getCompoundDrawables()[3]);
		
		}
			����

*/		
	}
	
	 
	
}
