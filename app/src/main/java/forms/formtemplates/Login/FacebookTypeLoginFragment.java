/*
 * Copyright (C) 2013 - Cognizant Technology Solutions. 
 * This file is a part of OneMobileStudio 
 * Licensed under the OneMobileStudio, Cognizant Technology Solutions, 
 * Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *      http://www.cognizant.com/
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package forms.formtemplates.Login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import forms.formtemplates.R;

/**
 * 
 * @author 280779
 * 
 *         This Fragment authenticates the User's login credential by invoking
 *         the Login Service and loads the next screen if successful.
 */
public class FacebookTypeLoginFragment extends Fragment implements
		OnClickListener {

	private String TAG = this.getClass().getSimpleName();
	private static FacebookTypeLoginFragment LoginScreenFragment = null;
	private int mContainerId = -1;
	private String uniqueId = null;
	private EditText label1val = null;
	private EditText label2val = null;
	private String loginUrl = null;

	private View loginView;
	// CatalogType
	private int configDBAppId;

	// Screen mode
	private boolean screenMode = true;
	private Button submit = null;

	@Override
	public void onResume() {
	//	label1val.setText("10028");
		//label2val.setText("10028");
	//	submit.performClick();
		super.onResume();
	}

	public static FacebookTypeLoginFragment newInstance(/*String usId*/) {
		LoginScreenFragment = new FacebookTypeLoginFragment();
/*		Bundle b = new Bundle();
		b.putString(OMSMessages.UNIQUE_ID.getValue(), usId);
		LoginScreenFragment.setArguments(b);
*/		return LoginScreenFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

			}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		mContainerId = container.getId();
		// Added Code CustomScreen
		

		loginView = inflater.inflate(R.layout.login_facebook_type_layout, container, false);
		/*setActionBarTitle(
				getResources().getString(R.string.login_title));*/
	    label1val = (EditText) loginView.findViewById(R.id.txtUserName);
		label2val = (EditText) loginView.findViewById(R.id.txtPassword);
		submit = (Button) loginView.findViewById(R.id.buttonSignIn);
		submit.setOnClickListener(FacebookTypeLoginFragment.this);

		
		return loginView;
	}

	@Override
	public void onClick(View v) {}

	private void resetView() {
		// TODO Auto-generated method stub

		if (label1val != null && (!label1val.getText().toString().isEmpty()))
			label1val.setText("");
		if (label2val != null && (!label2val.getText().toString().isEmpty()))
			label2val.setText("");
	}

	

}