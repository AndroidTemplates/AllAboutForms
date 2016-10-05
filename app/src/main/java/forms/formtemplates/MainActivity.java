package forms.formtemplates;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import forms.formtemplates.FireBase.FireBaseInfoFragment;
import forms.formtemplates.FireBase.FireBaseLoginFragment;
import forms.formtemplates.FireBase.FireBaseRegistrationFragment;
import forms.formtemplates.Login.FacebookTypeLoginFragment;
import forms.formtemplates.Login.LoginType2Fragment;
import forms.formtemplates.Login.LoginType3Fragment;
import forms.formtemplates.Login.SimpleLoginFragment;
import forms.formtemplates.Login.YahooTypeLoginFragment;
import forms.formtemplates.Profile.ProfileFragment;
import forms.formtemplates.Profile.ProfilePrepopulatedFragment;
import forms.formtemplates.RegistrationTemplates.RegistrationFragment;
import forms.formtemplates.SocialLogin.SocialLoginFragment;
import forms.formtemplates.SocialLogin.SocialLoginType2Fragment;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    private static final String  SELECTED_ITEM_ID = "selected_item_id";
    private static final String FIRST_TIME = "first_time";
    private Toolbar mToolbar;
    private NavigationView navigationViewDrawer;
    private DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mActionBarDrawerToggle;
    private int mSelectedId;
    private boolean mUserSawDrawer = false;
    Menu popUpMenu;
    private  AppCompatActivity appCompatActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar)findViewById(R.id.app_toolbar);
        setSupportActionBar(mToolbar);
        LayoutInflater mInflater= LayoutInflater.from(getApplicationContext());
        View mCustomView = mInflater.inflate(R.layout.toolbar_custom_view, null);
        mToolbar.addView(mCustomView);
        saveActivityStateInGlobal();
        navigationViewDrawer = (NavigationView)findViewById(R.id.main_nav_view_drawer);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        navigationViewDrawer.setNavigationItemSelectedListener(this);
        if (!didUserSeeDrawer()) {
            showDrawer();
            markDrawerSeen();
        } else {
            hideDrawer();
        }
        mSelectedId = savedInstanceState==null ? R.id.navigation_item_2:savedInstanceState.getInt(SELECTED_ITEM_ID);
        navigate(mSelectedId);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
        saveActivityStateInGlobal();

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        mSelectedId = menuItem.getItemId();

        navigate(mSelectedId);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(SELECTED_ITEM_ID,mSelectedId);
    }

    @Override
    protected void onPause() {

        super.onPause();
    }



    @Override
    protected void onStop() {
        Log.d("Activity","OnStop()....");
        super.onStop();
    }

    private void navigate(int mSelectedId) {
        Intent intent = null;
        if(mSelectedId == R.id.navigation_item_2){
            mDrawerLayout.closeDrawer(GravityCompat.START);
            //  return  true;
            //SimpleListType1
            Fragment simpleLoginFrag = new SimpleLoginFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_parentLayout, simpleLoginFrag)
                    .commit();
        }else if(mSelectedId == R.id.navigation_item_3){
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Fragment loginType2Frag = new LoginType2Fragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_parentLayout, loginType2Frag)
                    .commit();

        }else if(mSelectedId == R.id.navigation_item_6){
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Fragment loginType3Fragment = new LoginType3Fragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_parentLayout, loginType3Fragment)
                    .commit();
        }else if(mSelectedId == R.id.navigation_item_5){
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Fragment registrationFragment = new RegistrationFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_parentLayout, registrationFragment)
                    .commit();
        }

        else if(mSelectedId == R.id.navigation_item_11){
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Fragment facebookFrag = new FacebookTypeLoginFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_parentLayout, facebookFrag)
                    .commit();
        }

       else if(mSelectedId == R.id.navigation_item_16){
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Fragment socialLoginFragment = new SocialLoginFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_parentLayout, socialLoginFragment)
                    .commit();
        }

        else if(mSelectedId == R.id.navigation_item_13){
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Fragment yahooTypeFrag = new YahooTypeLoginFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_parentLayout, yahooTypeFrag)
                    .commit();
        }else  if(mSelectedId == R.id.navigation_item_18){
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Fragment profileFragment = new ProfileFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_parentLayout, profileFragment)
                    .commit();
        }else  if(mSelectedId == R.id.navigation_item_19){
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Fragment socialLoginType2Fragment = new SocialLoginType2Fragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_parentLayout, socialLoginType2Fragment)
                    .commit();
        }else if(mSelectedId == R.id.navigation_item_21){
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Fragment fireBaseRegistrationFragment = new FireBaseRegistrationFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_parentLayout, fireBaseRegistrationFragment)
                    .commit();
        }else if(mSelectedId == R.id.navigation_item_fire_base_login){

            mDrawerLayout.closeDrawer(GravityCompat.START);
            Fragment fireBaseLoginFragment = new FireBaseLoginFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_parentLayout, fireBaseLoginFragment)
                    .commit();
        }else if(mSelectedId == R.id.prepopulate_firebase_form){
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Fragment profilePrepopulatedFragment = new ProfilePrepopulatedFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_parentLayout, profilePrepopulatedFragment)
                    .commit();
        }/*else if(mSelectedId == R.id.firebase_profile_image_form){
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Fragment profileWithImageFragment = new ProfileWithImageFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_parentLayout, profileWithImageFragment)
                    .commit();
        }*/else if(mSelectedId == R.id.firebase_info){
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Fragment fireBaseInfoFragment = new FireBaseInfoFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_parentLayout, fireBaseInfoFragment)
                    .commit();
        }
    }
    private boolean didUserSeeDrawer() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserSawDrawer = sharedPreferences.getBoolean(FIRST_TIME, false);
        return mUserSawDrawer;
    }

    private void markDrawerSeen() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserSawDrawer = true;
        sharedPreferences.edit().putBoolean(FIRST_TIME, mUserSawDrawer).apply();
    }

    private void showDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private void hideDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }
    private void saveActivityStateInGlobal(){
        appCompatActivity = MainActivity.this;
        MyApplication.getAppInstance().setmActivityContext(appCompatActivity);
    }
}
