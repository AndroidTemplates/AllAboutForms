package forms.formtemplates.Login;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import forms.formtemplates.R;
import forms.formtemplates.Utils.FormUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginType2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginType2Fragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
     View loginView = null;

    EditText email,password;
    AppCompatButton login_btn;
    TextInputLayout emailTextInputLayout,passwordTextInputLayout;

    public LoginType2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginType2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginType2Fragment newInstance(String param1, String param2) {
        LoginType2Fragment fragment = new LoginType2Fragment();
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
        // create ContextThemeWrapper from the original Activity Context with the custom theme
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Dark);

// clone the inflater using the ContextThemeWrapper
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        getActivity().getWindow().setBackgroundDrawableResource(R.color.primary);
        //
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        loginView =  localInflater.inflate(R.layout.fragment_login_type2, container, false);
        initView(loginView);
        setListeners();
        return loginView;
    }

    private void initView(View v){
        email = (EditText) v.findViewById(R.id.input_email);
        password = (EditText) v.findViewById(R.id.input_password);
        login_btn = (AppCompatButton)v.findViewById(R.id.btn_login);
        emailTextInputLayout = (TextInputLayout)v.findViewById(R.id.email_input_layout);
        passwordTextInputLayout = (TextInputLayout)v.findViewById(R.id.pwd_input_layout);
    }

    private void setListeners(){
        login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;
        }
    }


    private void login(){
        if(!validate()){
            Toast.makeText(getActivity(),"Login Failed",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(),"Login Success",Toast.LENGTH_SHORT).show();
        }
    }


    private boolean validate(){
        boolean isValidate =  true;
        String emailAddr = email.getText().toString();
        String passWord = password.getText().toString();

        if(FormUtils.validateEmail(emailAddr)){
            emailTextInputLayout.setError(null);
        }else{
            emailTextInputLayout.setError("Enter Valid Email");
            emailTextInputLayout.requestFocus();
            isValidate = false;
            return isValidate;
        }

        if(FormUtils.validatePassWord(passWord)){
            passwordTextInputLayout.setError(null);
        }else{
            passwordTextInputLayout.setError("Enter  Password between 6 and 10 alpha numeric characters ");
            passwordTextInputLayout.requestFocus();
            isValidate = false;
            return isValidate;
        }

        return isValidate;


    }
}
