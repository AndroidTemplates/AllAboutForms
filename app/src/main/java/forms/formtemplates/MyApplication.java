package forms.formtemplates;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.client.Firebase;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by CHANDRASAIMOHAN on 7/21/2016.
 */
public class MyApplication extends Application {
    private static MyApplication appInstance;
  //  private static DBProducts mDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        if(!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        Firebase.setAndroidContext(this);
        appInstance = this;



    }

    public  static  MyApplication getAppInstance(){
        return  appInstance;
    }

    public static Context getMyAppContext(){
        return  appInstance.getApplicationContext();
    }

  /*  public synchronized static DBProducts getWritableDatabase() {
        if (mDatabase == null) {
            mDatabase = new DBProducts(getMyAppContext());
        }
        return mDatabase;
    }
*/
    AppCompatActivity mActivityContext;

    public Context getmActivityContext() {
        return mActivityContext;
    }

    public void setmActivityContext(AppCompatActivity mActivityContext) {
        this.mActivityContext = mActivityContext;
    }

    GoogleApiClient mGoogleApiClient=null;

    public GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }

    public void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {
        this.mGoogleApiClient = mGoogleApiClient;
    }
}
