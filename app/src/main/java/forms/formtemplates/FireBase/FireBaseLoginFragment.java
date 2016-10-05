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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import forms.formtemplates.FireBase.ProfileWithImageFragment;

import forms.formtemplates.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FireBaseLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FireBaseLoginFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View fireBaseRegistrationView = null;
    EditText email,password;
    Button register,logout;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    private int mContainerId = -1;
    public FireBaseLoginFragment() {
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
    public static FireBaseLoginFragment newInstance(String param1, String param2) {
        FireBaseLoginFragment fragment = new FireBaseLoginFragment();
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
        fireBaseRegistrationView =  inflater.inflate(R.layout.fragment_fire_base_login, container, false);
        mContainerId = container.getId();
        initView(fireBaseRegistrationView);
        registerListener();
     /*   if(checkForUserLogin()){
            logout.setVisibility(View.VISIBLE);
        }else{
            logout.setVisibility(View.INVISIBLE);
        }*/
        return  fireBaseRegistrationView;
    }


    private  void initFireBaseAuth(){
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true); //Stores data in offline
    }
    private  void initView(View v){
        progressDialog = new ProgressDialog(getActivity());
        email = (EditText) v.findViewById(R.id.email_ed);
     password = (EditText)   v.findViewById(R.id.password_ed);
        register = (Button) v.findViewById(R.id.login);
        logout = (Button)v.findViewById(R.id.logout);
    }

    private  void registerListener(){
        register.setOnClickListener(this);
        logout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                if(!checkForUserLogin()) {
                    signIn();
                }
                break;
            case R.id.logout:
                firebaseAuth.signOut();
                logout.setVisibility(View.INVISIBLE);
                break;
        }
    }

private boolean checkForUserLogin(){
    boolean isLogin = false;
    if(firebaseAuth.getCurrentUser()!=null){
        Toast.makeText(getActivity(),"Already Logged in",Toast.LENGTH_SHORT).show();
        isLogin = true;
    }
    return  isLogin;
}
    private  void signIn(){
        String emailId = email.getText().toString();
        String passwordForEmail  = password.getText().toString();

        if(!TextUtils.isEmpty(emailId) && !TextUtils.isEmpty(passwordForEmail)){
               progressDialog.setMessage("Logging  user....");
               progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(emailId,passwordForEmail).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                      //  checkUserExists();
                        Toast.makeText(getActivity(),"Login Success",Toast.LENGTH_LONG).show();
                        logout.setVisibility(View.GONE);
                        launchProfileSetUp();
                    }else{
                        logout.setVisibility(View.INVISIBLE);
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),"Failed to Login"+ task.getException().toString(),Toast.LENGTH_LONG).show();
                    }
                }

            });
        }else{
            Toast.makeText(getActivity(),"Please enter Email password to Register",Toast.LENGTH_LONG).show();
        }


    }

    private void launchProfileSetUp(){
        Fragment profileWithImageFragment = new ProfileWithImageFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(mContainerId, profileWithImageFragment)
                //   .addToBackStack(null)
                .commit();
    }
    private  void checkUserExists(){
        if(firebaseAuth.getCurrentUser()!=null) {
            final String user_id = firebaseAuth.getCurrentUser().getUid();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(user_id)) {
                       // launchFragmentInfo();
                        launchSetUpFragment();
                    } else {
                          Toast.makeText(getActivity(),"User does not exist...",Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }


    private void launchFragmentInfo(){
        Fragment fireBaseInfoFragment = new FireBaseInfoFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(mContainerId, fireBaseInfoFragment)
                .addToBackStack(null)
                .commit();
    }


    private void launchSetUpFragment(){
        Fragment profileWithImageFrag = new ProfileWithImageFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(mContainerId, profileWithImageFrag)
               // .addToBackStack(null)
                .commit();
    }
}
