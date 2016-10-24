package forms.formtemplates.ResetPassword;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import forms.formtemplates.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResetPasswordSendEmailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResetPasswordSendEmailFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText email_input;
    Button send_email;

    View resetPasswordView = null;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public ResetPasswordSendEmailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResetPasswordSendEmailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResetPasswordSendEmailFragment newInstance(String param1, String param2) {
        ResetPasswordSendEmailFragment fragment = new ResetPasswordSendEmailFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        resetPasswordView =  inflater.inflate(R.layout.reset_password_layout, container, false);
        initViews(resetPasswordView);
        registerListener();
        return  resetPasswordView;
    }


    private void initViews(View view){
        email_input = (EditText)view.findViewById(R.id.email_input);
        send_email  = (Button)view.findViewById(R.id.send_email);
    }

    private void registerListener(){
        send_email.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_email:
                if(validateEmail()){
                    if(checkNetworkConnectivity()) {
                        sendEmail();
                    }else {
                        Toast.makeText(getActivity(), "Please check your internet Connectivity...", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    private  boolean validateEmail(){
        boolean isValid = false;
         if(!TextUtils.isEmpty(email_input.getText().toString())){
            String email = email_input.getText().toString();
             if (email.matches(emailPattern)){
                 isValid  = true;
                 return  isValid;
             }else{
                 Toast.makeText(getActivity(),"InValidEmai",Toast.LENGTH_LONG).show();
                 isValid = false;
                 return isValid;
             }
         }else{
             Toast.makeText(getActivity(),"Email Can't be Empty",Toast.LENGTH_LONG).show();
             return isValid;
         }
    }

    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {email_input.getText().toString()};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Reset Password");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

     public  boolean checkNetworkConnectivity() {
         ConnectivityManager  connectionManager = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectionManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

}
