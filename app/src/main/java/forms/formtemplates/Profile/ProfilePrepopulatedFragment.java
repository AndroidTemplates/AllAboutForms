package forms.formtemplates.Profile;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import forms.formtemplates.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfilePrepopulatedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilePrepopulatedFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private  String fireBaseDBURL = "https://fir-authen-39277.firebaseio.com/profile";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Firebase mRef;
    View profileView = null;

    Button submit,dob;
    ProgressDialog progressDialog = null;
    EditText fname,mName,lname,age,addr1,addr2,mobile1,mobile2,email,zipcode;


    public ProfilePrepopulatedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilePrepopulatedFragment newInstance(String param1, String param2) {
        ProfilePrepopulatedFragment fragment = new ProfilePrepopulatedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private  void initFireBaseURL(){
        mRef = new Firebase(fireBaseDBURL);
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
        profileView =  inflater.inflate(R.layout.fragment_profile, container, false);
        initFireBaseURL();
        initViews(profileView);
        registerListeners();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Getting the data from snapshot
                Profile profile = dataSnapshot.getValue(Profile.class);
                prepopulateViews(profile);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return  profileView;
    }

    private void prepopulateViews(Profile profile){
        if(profile!=null){
            fname.setText(profile.getFname());
            mName.setText(profile.getMname());
            lname.setText(profile.getLname());

            age.setText(profile.getAge());
            dob.setText(profile.getDob());

            addr1.setText(profile.getAddr1());
            addr2.setText(profile.getAddr2());

            mobile1.setText(profile.getMobile());
            mobile2.setText(profile.getMobile2());

            zipcode.setText(profile.getZipcode());
            email.setText(profile.getEmail());
        }
    }

    private void initViews(View view){
       submit = (Button) view.findViewById(R.id.submit_form);
        progressDialog = new ProgressDialog(getActivity());
        fname = (EditText) view.findViewById(R.id.fname);
        mName = (EditText) view.findViewById(R.id.mname);
        lname = (EditText) view.findViewById(R.id.lname);
        age = (EditText) view.findViewById(R.id.age);
        addr1 = (EditText) view.findViewById(R.id.addr1);
        addr2 = (EditText) view.findViewById(R.id.addr2);
        zipcode = (EditText) view.findViewById(R.id.zipcode);
        email = (EditText) view.findViewById(R.id.email);
        mobile1 = (EditText) view.findViewById(R.id.mobile1);
        mobile2 = (EditText) view.findViewById(R.id.mobile2);

        dob = (Button) view.findViewById(R.id.dob);
    }

    private void registerListeners(){
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_form:
                Profile userProfile = prepareData();
                postToFireBase(userProfile);
                break;
        }
    }


    private void postToFireBase(Profile profile){
        progressDialog.setMessage("Posting...");
        progressDialog.show();
         if(profile!=null){
             mRef.setValue(profile);
             progressDialog.dismiss();
             Toast.makeText(getActivity(),"Posted Data::",Toast.LENGTH_LONG).show();
         }

    }

    private Profile prepareData(){
        Profile profile = new Profile();
        profile.setFname("Chandra");
        profile.setMname("Sai Mohan");
        profile.setLname("Bhupathi");
        profile.setAddr1("Hyd");
        profile.setAddr2("GNT");
        profile.setAge("32");
        profile.setCountry("IND");
        profile.setState("AP");
        profile.setDob("17021984");
        profile.setZipcode("522002");
        profile.setMobile("9887575666");
        profile.setMobile2("9887575669");
        profile.setEmail("a@b.com");
        return profile;

    }
}
