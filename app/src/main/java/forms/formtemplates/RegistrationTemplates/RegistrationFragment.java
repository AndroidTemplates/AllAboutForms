package forms.formtemplates.RegistrationTemplates;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
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
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View registrationView = null;
    TextInputLayout name_input,email_input,password_input,retype_password_input;
    EditText name_et,email_et,password_et,retype_et;
    AppCompatButton register_btn;
    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
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
        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Dark);
        LayoutInflater localInflater = inflater.cloneInContext(contextThemeWrapper);
        getActivity().getWindow().setBackgroundDrawableResource(R.color.primary);
        //
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        registrationView =  localInflater.inflate(R.layout.fragment_registration, container, false);
        initViews(registrationView);
        registerListener();
        return  registrationView;
    }


    private void initViews(View view){
        name_input = (TextInputLayout) view.findViewById(R.id.name_text_input);
        email_input = (TextInputLayout) view.findViewById(R.id.email_text_input);
        password_input = (TextInputLayout) view.findViewById(R.id.password_text_input);
        retype_password_input =  (TextInputLayout) view.findViewById(R.id.retype_password_text_input);

        name_et = (EditText)view.findViewById(R.id.input_name);
        email_et = (EditText)view.findViewById(R.id.input_email);
        password_et = (EditText)view.findViewById(R.id.input_password);
        retype_et = (EditText)view.findViewById(R.id.input_retype_password);

        register_btn = (AppCompatButton)view.findViewById(R.id.btn_signup);
    }

    private  void registerListener(){
        register_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signup:
                register();
                break;
        }
    }

    private void register(){
      if(validateForm()){
          Toast.makeText(getActivity(),"Registered",Toast.LENGTH_SHORT).show();
      }
    }

    private boolean validateForm(){
        boolean isValidate = true;
        if(!TextUtils.isEmpty(name_et.getText().toString())){
            name_input.setError(null);
        }else{
            name_input.setError("Name entry required");
            isValidate = false;
            return  isValidate;
        }


        String emailAddr = email_et.getText().toString();
        String passWord = password_et.getText().toString();
        String reTypePassword = retype_et.getText().toString();

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

        if(FormUtils.validatePassWord(reTypePassword)){
            retype_password_input.setError(null);
        }else{
            retype_password_input.setError("Enter Re-Type Password between 6 and 10 alpha numeric characters ");
            retype_et.requestFocus();
            isValidate = false;
            return isValidate;
        }


        if(!TextUtils.isEmpty(passWord) && !TextUtils.isEmpty(reTypePassword)){
            if(passWord.equalsIgnoreCase(reTypePassword)){
                retype_password_input.setError(null);
            }else{
                retype_password_input.setError("Password and  Re-Type Password does not match");
                retype_et.requestFocus();
                isValidate = false;
                return isValidate;
            }
        }

        return  isValidate;
    }
}
