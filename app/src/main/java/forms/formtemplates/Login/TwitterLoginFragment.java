package forms.formtemplates.Login;


import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;

import forms.formtemplates.R;


public class TwitterLoginFragment extends Fragment {

	EditText password_edt,username_edt;
	Button singnIn_btn;
	CheckedTextView twitter_check_label;
	int mContainerId;
	View loginView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		mContainerId = container.getId();
		loginView = inflater.inflate(R.layout.twitter_login_layout, container, false);
       
		password_edt=(EditText)loginView.findViewById(R.id.twitter_pwd);
		username_edt=(EditText)loginView.findViewById(R.id.twitter_uname);
		singnIn_btn=(Button)loginView.findViewById(R.id.twitter_btn);
		twitter_check_label=(CheckedTextView)loginView.findViewById(R.id.twitter_checked_label);
		singnIn_btn.setBackgroundResource(android.R.drawable.btn_default);
		password_edt.addTextChangedListener(mTextEditorWatcher);
		username_edt.addTextChangedListener(mTextEditorWatcher);
		twitter_check_label.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(twitter_check_label.isChecked()){
					twitter_check_label.setChecked(false);
				}
				else{
					twitter_check_label.setChecked(true);
				}
				
			}
		});
		
		return loginView;
	}

	private final TextWatcher  mTextEditorWatcher = new TextWatcher() {
        
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {
                    // When No Password Entered
                 
        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
           if(username_edt.getText().length()>0 && !TextUtils.isEmpty(username_edt.getText()) && !TextUtils.isEmpty(password_edt.getText())){
        	   singnIn_btn.setBackgroundResource(R.drawable.twitter_buttonstyle);
        	   singnIn_btn.setClickable(true);
           }
        }

        public void afterTextChanged(Editable s)
        {
                   if(TextUtils.isEmpty(password_edt.getText())){
                	   singnIn_btn.setClickable(false);
                	   singnIn_btn.setBackgroundResource(android.R.drawable.btn_default);

                   }
        }
};



}
