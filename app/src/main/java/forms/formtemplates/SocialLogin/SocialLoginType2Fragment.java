package forms.formtemplates.SocialLogin;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import forms.formtemplates.MyApplication;
import forms.formtemplates.R;
import forms.formtemplates.Utils.SharedPreferencesUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SocialLoginType2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SocialLoginType2Fragment extends Fragment implements  View.OnClickListener ,GoogleApiClient.OnConnectionFailedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View socialView = null;
   Button faceBookLogin,signInButton;
  //  private LoginButton faceBookLogin;
   // LoginManager fbLoginManager;
  //  CallbackManager callbackManager;
    private CallbackManager callbackManager = null;
    private AccessTokenTracker mtracker = null;
    private ProfileTracker mprofileTracker = null;

    public static final String PARCEL_KEY = "parcel_key";
    public  static String TAG = "SocialLoginFragment";
    int mContainerId = -1;
    SocialDTO socialDTO  = null;

    GoogleSignInOptions gso;


    //google api client
    private GoogleApiClient mGoogleApiClient = null;

    //Signin constant to check the activity result
    private int RC_SIGN_IN = 100;

    public SocialLoginType2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SocialLoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SocialLoginType2Fragment newInstance(String param1, String param2) {
        SocialLoginType2Fragment fragment = new SocialLoginType2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
           Log.d(TAG, "In onSuccess()...........");
            Profile profile = Profile.getCurrentProfile();
            Log.d(TAG,"Profile Name::::"+profile.getName());
            Log.d(TAG, "Profile Pic:::"+profile.getProfilePictureUri(400, 400).toString());
            RequestData();
           // homeFragment(profile);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        //callbackManager = CallbackManager.Factory.create();
        initFaceBookLoginCallBackManager();

        mtracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

                Log.v("AccessTokenTracker", "oldAccessToken=" + oldAccessToken + "||" + "CurrentAccessToken" + currentAccessToken);
            }
        };


        mprofileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                Log.v("Session Tracker", "oldProfile=" + oldProfile + "||" + "currentProfile" + currentProfile);
             //   homeFragment(currentProfile);

            }
        };

        mtracker.startTracking();
        mprofileTracker.startTracking();
      //  initFaceBookLoginCallBackManager();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        socialView =  inflater.inflate(R.layout.fragment_social_login_type2, container, false);
        mContainerId = container.getId();
        initViews(socialView);
        registerListener();
        return  socialView;
    }


    private void initFaceBookLoginCallBackManager(){
         callbackManager = CallbackManager.Factory.create();
    }

    private  void initViews(View view){
        faceBookLogin = (Button) view.findViewById(R.id.facebook_login);
        initGoogleSignInOptions();
        signInButton = (Button) view.findViewById(R.id.google_sign_in_button);
    }

    private void registerListener(){
        faceBookLogin.setOnClickListener(this);
        signInButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.facebook_login:
              //  facebookLogin();
                facebookLoginUsingFaceBookLoginButton();
                break;
            case R.id.google_sign_in_button:
                signIn();
                break;
        }
    }

    private void initGoogleSignInOptions(){
        //Signing Options
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        //Initializing google api client
        mGoogleApiClient = MyApplication.getAppInstance().getmGoogleApiClient();
        if(mGoogleApiClient==null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    //This function will option signing intent
    private void signIn() {
        //Creating an intent
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        //Starting intent for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
private void facebookLoginUsingFaceBookLoginButton(){
    //faceBookLogin.setReadPermissions("user_friends");
    LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email"));
    // If using in a fragment
   // faceBookLogin.setFragment(this);
    LoginManager.getInstance().registerCallback(callbackManager, callback);
    //faceBookLogin.registerCallback(callbackManager, callback);

}

    public void RequestData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object,GraphResponse response) {

                JSONObject json = response.getJSONObject();
                System.out.println("Json data :"+json);
                try {
                    if(json != null){
                        Log.d(TAG,"Name::::"+json.getString("name"));
                        Log.d(TAG,"email::::"+json.getString("email"));
                        Log.d(TAG,"link::::"+json.getString("link"));
                        String userId =  json.getString("id");
                        String imgUrl = "https://graph.facebook.com/" + userId + "/picture?type=large";
                        Log.d(TAG,"ImageURL::::"+imgUrl);
                        socialDTO = new SocialDTO();
                        socialDTO.setProfileName(json.getString("name"));
                        socialDTO.setProfileEmail(json.getString("email"));
                        socialDTO.setProfileImage(imgUrl);
                        SharedPreferencesUtils.getInstance().saveStringPreferences(getActivity(),"loginType","facebook");
                        InfoFragment(socialDTO);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,"RequestCode"+requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        }else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    //After the signing we are calling this function
    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();
            if(acct!=null) {
                SocialDTO socialDTO = new SocialDTO();
                socialDTO.setProfileName(acct.getDisplayName());
                socialDTO.setProfileImage(acct.getEmail());
                socialDTO.setProfileImage(acct.getPhotoUrl().toString());
                SharedPreferencesUtils.getInstance().saveStringPreferences(getActivity(),"loginType","google");
                //disConnectGoogleClient();
                MyApplication.getAppInstance().setmGoogleApiClient(mGoogleApiClient);
                InfoFragment(socialDTO);
            }else{
                Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show();
            }


        } else {
            //If login fails
            Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show();
        }
    }
    private void InfoFragment(SocialDTO profile) {

        if (profile != null) {
            Bundle mBundle = new Bundle();
            mBundle.putParcelable(PARCEL_KEY, profile);
            Fragment faceBookInfoFragment = new FaceBookInfoFragment();
            faceBookInfoFragment.setArguments(mBundle);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();
            fragmentTransaction.replace(mContainerId,faceBookInfoFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

    }


}
