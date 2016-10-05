package forms.formtemplates.FireBase;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import forms.formtemplates.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FireBaseInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FireBaseInfoFragment extends Fragment implements  View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   private  FirebaseAuth firebaseAuth;
   private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference userDataBaseReference;


   View infoFragmentView = null;
    int mContainerID = -1;
    Button logout_btn;

    public FireBaseInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FireBaseInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FireBaseInfoFragment newInstance(String param1, String param2) {
        FireBaseInfoFragment fragment = new FireBaseInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void initFireBaseAuth(){
        firebaseAuth  = FirebaseAuth.getInstance();
        userDataBaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        userDataBaseReference.keepSynced(true);
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
        infoFragmentView =  inflater.inflate(R.layout.fragment_fire_base_info, container, false);
        mContainerID = container.getId();
        initViews(infoFragmentView);
        registerListener();
        checkUserExists();
    //    checkForCurrentUserLogin();
        return  infoFragmentView;
    }

    private void checkForCurrentUserLogin() {
        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    launchLogin();
                }
            }
        });

    }


    private void launchLogin(){
        Fragment fireBaseLoginFragment = new FireBaseLoginFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(mContainerID, fireBaseLoginFragment)
                //.addToBackStack(null)
                .commit();
    }


    private void initViews(View view){
        logout_btn = (Button) view.findViewById(R.id.logout);
    }

    private  void registerListener(){
        logout_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout:
                firebaseAuth.signOut();
                break;
        }
    }


    private  void checkUserExists(){
        if(firebaseAuth.getCurrentUser()!=null) {
            final String user_id = firebaseAuth.getCurrentUser().getUid();
            userDataBaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.hasChild(user_id)) {
                        launchSetUpFragment();
                    } else {
                        checkForCurrentUserLogin();
                        //Toast.makeText(getActivity(),"User does not exist...",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }


    private void launchSetUpFragment(){
        Fragment profileWithImageFrag = new ProfileWithImageFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(mContainerID, profileWithImageFrag)
                .addToBackStack(null)
                .commit();
    }
}
