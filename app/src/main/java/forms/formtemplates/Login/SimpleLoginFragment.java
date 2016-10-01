package forms.formtemplates.Login;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import forms.formtemplates.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SimpleLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SimpleLoginFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View simpleLogin = null;

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    Button mEmailSignInButton;
    private View mProgressView;
    private View mLoginFormView;


    public SimpleLoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SimpleLoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SimpleLoginFragment newInstance(String param1, String param2) {
        SimpleLoginFragment fragment = new SimpleLoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        simpleLogin =  inflater.inflate(R.layout.fragment_simple_login, container, false);
        initViews(simpleLogin);
        registerListener();
        return  simpleLogin;
    }

    private void initViews(View view){
        mEmailView = (AutoCompleteTextView) view.findViewById(R.id.email);
        mPasswordView = (EditText) view.findViewById(R.id.password);
         mEmailSignInButton = (Button) view.findViewById(R.id.email_sign_in_button);
    }

    private void registerListener(){
        mEmailSignInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.email_sign_in_button){
            attemptLogin();
        }
    }


    private  void  attemptLogin(){
        resetErrors();
        View focusView = null;
        boolean cancel = false;
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        // Check for a valid password, if the user entered one.


        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }else  if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        if(cancel) {
            focusView.requestFocus();
        }else{
            Toast.makeText(getActivity(),"Successful Login",Toast.LENGTH_SHORT).show();
        }
    }

    private void resetErrors(){
        mEmailView.setError(null);
        mPasswordView.setError(null);
    }
}
