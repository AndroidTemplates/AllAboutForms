package forms.formtemplates.Login;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import forms.formtemplates.R;
import forms.formtemplates.Utils.FormUtils;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginType3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginType3Fragment extends Fragment implements  View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View loginView = null;
    TextInputLayout name_input,email_input,password_input,retype_password_input;
    EditText name_et,email_et,password_et,retype_et;
    Button register_btn;

    public LoginType3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFormFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginType3Fragment newInstance(String param1, String param2) {
        LoginType3Fragment fragment = new LoginType3Fragment();
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
        loginView =  inflater.inflate(R.layout.fragment_login_type3_form, container, false);
        initViews(loginView);
        registerListener();
        return  loginView;
    }


    private void initViews(View view){
        email_input = (TextInputLayout) view.findViewById(R.id.email_text_input);
        password_input = (TextInputLayout) view.findViewById(R.id.password_text_input);

        email_et = (EditText)view.findViewById(R.id.input_email);
        password_et = (EditText)view.findViewById(R.id.input_password);

        register_btn = (Button)view.findViewById(R.id.sign_in);
    }

    private  void registerListener(){
        register_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in:
                login();
                break;
        }
    }

    private void login(){
        if(validateForm()){
            Toast.makeText(getActivity(),"Login Success",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateForm(){
        boolean isValidate = true;



        String emailAddr = email_et.getText().toString();
        String passWord = password_et.getText().toString();

        if(FormUtils.validateEmail(emailAddr)){
            email_input.setError(null);
        }else{
            email_input.setError("Enter Valid Email");
            email_et.requestFocus();
            isValidate = false;
            return isValidate;
        }

        if(FormUtils.validatePassWord(passWord)){
            password_input.setError(null);
        }else{
            password_input.setError("Enter  Password between 6 and 10 alpha numeric characters ");
            password_et.requestFocus();
            isValidate = false;
            return isValidate;
        }

        return  isValidate;
    }
}
