/**
 * 
 */
package forms.formtemplates.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author CHANDRASAIMOHAN
 *
 */
public class SharedPreferencesUtils {
	public static final String PREFS_NAME = "PatientPayPrefs";
	SharedPreferences sharedPreferences;
    private static SharedPreferencesUtils instance = null;
    private SharedPreferencesUtils(){

    }

    public static SharedPreferencesUtils getInstance() {
        if(instance == null) {
            instance = new SharedPreferencesUtils();
        }
        return instance;
    }
	
	public void saveIntegerPreferences(Context ctx,String key,int value){
		SharedPreferences patientPayPreferences;
	    Editor editor;
        patientPayPreferences = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
	    editor = patientPayPreferences.edit(); //2
	    editor.putInt(key, value); //3
	    editor.commit(); //4
	}
	
	public int getIntegerPreferences(Context ctx,String key){
		SharedPreferences patientPayPreferences;
	    int preferenceValue;
        patientPayPreferences = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
	    preferenceValue = patientPayPreferences.getInt(key, 1);
	    return preferenceValue;
	}
	
	
	public void saveStringPreferences(Context ctx,String key,String value){
		SharedPreferences patientPayPreferences;
	    Editor editor;
        patientPayPreferences = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
	    editor = patientPayPreferences.edit(); //2
	    editor.putString(key, value); //3
	    editor.commit(); //4
	}
	
	public String getStringPreferences(Context ctx,String key){
		SharedPreferences patientPayPreferences;
	    String preferenceValue;
        patientPayPreferences = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1
	    preferenceValue = patientPayPreferences.getString(key, "");
	    return preferenceValue;
	}
	
	public void clearSharedPreference(Context context) {
	    SharedPreferences settings;
	    Editor editor;
	 
	    settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
	    editor = settings.edit();
	 
	    editor.clear();
	    editor.commit();
	}
	
	public void removeValue(Context context,String key) {
	    SharedPreferences settings;
	    Editor editor;
	    settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
	    editor = settings.edit();
	 
	    editor.remove(key);
	    editor.commit();
	}
}
