package forms.formtemplates.Utils;

import android.text.TextUtils;

/**
 * Created by CHANDRASAIMOHAN on 9/21/2016.
 */
public class FormUtils {

     public static boolean validateEmail(String email){
         boolean isValid = false;
         if(!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
             isValid = true;
         }

         return  isValid;
     }


    public  static  boolean validatePassWord(String password){
        boolean isValid = false;
       if(!TextUtils.isEmpty(password) && (password.length()>=6 && password.length()<=10))
       {
           isValid = true;
       }
        return  isValid;
    }
}
