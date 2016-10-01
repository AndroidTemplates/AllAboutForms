package forms.formtemplates.FireBase;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import forms.formtemplates.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FireBaseRegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FireBaseRegistrationFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View fireBaseRegistrationView = null;
    EditText email,password;
    Button register;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    TextView login;
    int mContainerId = -1;
    public FireBaseRegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FireBaseRegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FireBaseRegistrationFragment newInstance(String param1, String param2) {
        FireBaseRegistrationFragment fragment = new FireBaseRegistrationFragment();
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
        initFireBaseAuth();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fireBaseRegistrationView =  inflater.inflate(R.layout.fragment_fire_base_registration, container, false);
        mContainerId = container.getId();
        initView(fireBaseRegistrationView);
        registerListener();
        return  fireBaseRegistrationView;
    }


    private  void initFireBaseAuth(){
        firebaseAuth = FirebaseAuth.getInstance();
    }
    private  void initView(View v){
        progressDialog = new ProgressDialog(getActivity());
        email = (EditText) v.findViewById(R.id.email_ed);
     password = (EditText)   v.findViewById(R.id.password_ed);
        register = (Button) v.findViewById(R.id.register);
        login = (TextView) v.findViewById(R.id.login_here);
    }

    private  void registerListener(){
        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                register();
                break;
            case R.id.login_here:
                launchLogin();
                break;
        }
    }

private void launchLogin(){
    Fragment fireBaseLoginFragment = new FireBaseLoginFragment();
    getActivity().getSupportFragmentManager().beginTransaction()
            .replace(mContainerId, fireBaseLoginFragment)
            .addToBackStack(null)
            .commit();
}
    private  void register(){
        String emailId = email.getText().toString();
        String passwordForEmail  = password.getText().toString();

        if(!TextUtils.isEmpty(emailId) && !TextUtils.isEmpty(passwordForEmail)){
               progressDialog.setMessage("Registering user....");
               progressDialog.show();
            firebaseAuth.createUserWithEmailAndPassword(emailId,passwordForEmail).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),"Registered Successfully",Toast.LENGTH_LONG).show();
                    }else{

                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),"Failed to Register"+ task.getException().toString(),Toast.LENGTH_LONG).show();
                    }
                }

            });
        }else{
            Toast.makeText(getActivity(),"Please enter Email password to Register",Toast.LENGTH_LONG).show();
        }


    }
}
