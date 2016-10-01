package forms.formtemplates.SocialLogin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import forms.formtemplates.MyApplication;
import forms.formtemplates.R;
import forms.formtemplates.Utils.SharedPreferencesUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FaceBookInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FaceBookInfoFragment extends Fragment implements  View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View faceBookInfoView = null;

    SocialDTO socialDTO;
    TextView name,email;
    ImageView profilePic;
    Button logout_fb;
    int mContainerID = -1;
    public FaceBookInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FaceBookInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FaceBookInfoFragment newInstance(String param1, String param2) {
        FaceBookInfoFragment fragment = new FaceBookInfoFragment();
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
        /*    mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
            socialDTO  =  (SocialDTO) getArguments().getParcelable("parcel_key");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        faceBookInfoView =  inflater.inflate(R.layout.fragment_face_book_info, container, false);
        mContainerID = container.getId();
        initView(faceBookInfoView);
        setListener();
        if(socialDTO!=null){
            populateInfo();
        }

        return  faceBookInfoView;
    }


    private  void initView(View view){
        name = (TextView) view.findViewById(R.id.profile_name);
        email = (TextView) view.findViewById(R.id.profile_email);
        profilePic = (ImageView) view.findViewById(R.id.profile_photo);
        logout_fb = (Button)view.findViewById(R.id.logout_fb);
    }

    private void setListener(){
        logout_fb.setOnClickListener(this);
    }

    private void populateInfo(){
        name.setText(socialDTO.getProfileName());
        email.setText(socialDTO.getProfileEmail());
        Picasso.with(getActivity())
                .load(socialDTO.getProfileImage())
                .into(profilePic);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout_fb:
                logout();
                break;
        }
    }


    private void logout() {
        if(SharedPreferencesUtils.getInstance().getStringPreferences(getActivity(),"loginType").equalsIgnoreCase("facebook")) {
            LoginManager.getInstance().logOut();
            Toast.makeText(getActivity(),"LogOut Successful",Toast.LENGTH_LONG).show();
        }else if(SharedPreferencesUtils.getInstance().getStringPreferences(getActivity(),"loginType").equalsIgnoreCase("google")) {
            AppCompatActivity ctx = (AppCompatActivity)getActivity();
           // GoogleSignOut.getInstance().init(ctx);
            signOut();
        }

    }


    public void signOut() {

        /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(MyApplication.getInstance().getActivity())
                .enableAutoManage(MyApplication.getInstance().getActivity() *//* FragmentActivity *//*, this *//* OnConnectionFailedListener *//*)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();*/
        GoogleApiClient mGoogleApiClient = MyApplication.getAppInstance().getmGoogleApiClient();
        if (mGoogleApiClient!=null && mGoogleApiClient.isConnected()){
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            Toast.makeText(getActivity(), "Signout:::", Toast.LENGTH_LONG).show();
                            //     status.getStatusMessage()
                            // [START_EXCLUDE]
                            //      updateUI(false);
                            // [END_EXCLUDE]
                        }
                    });
        }
    }
}
