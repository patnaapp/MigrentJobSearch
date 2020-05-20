package com.bih.nic.aadhar1;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bih.nic.aadhar1.DataBaseHelper.DataBaseHelper;
import com.bih.nic.aadhar1.Model.UserDetails;
import com.bih.nic.aadhar1.Model.panchayat;

import java.util.ArrayList;

public class Login extends Activity {

    Button email_sign_in_button,email_sign_in_button2;
    EditText email, password;
    String str_email, str_pass;
    String[] param;
    TextView text_signup;
    TelephonyManager tm;
    private static String imei;
    TextView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getActionBar().hide();

//        Utiilties.setActionBarBackground(Login.this);
        Utiilties.setStatusBarColor(Login.this);
//        ActionBar actionBar = getActionBar();
//        actionBar.setTitle(" Log In ");
        Initialization();

        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(Utiilties.isOnline(getApplicationContext())) {
                   setvalue();
                   PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Link","1").commit();
                   param = new String[2];
                   param[0] = email.getText().toString();
                   param[1] = password.getText().toString();
                   new LoginTask().execute();
               }else {
                   CustomDialogClass cdd=new CustomDialogClass(Login.this,email.getText().toString(),password.getText().toString());
                   cdd.show();
               }

            }
        }); email_sign_in_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(Utiilties.isOnline(getApplicationContext())) {
                   setvalue();
                   PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Link","2").commit();
                   param = new String[2];
                   param[0] = email.getText().toString();
                   param[1] = password.getText().toString();
                   new LoginTask().execute();
               }else {
                   CustomDialogClass cdd=new CustomDialogClass(Login.this,email.getText().toString(),password.getText().toString());
                   cdd.show();
               }

            }
        });
        getIMEI();

    }

    private void setvalue() {
        str_email = email.getText().toString();
        str_pass = password.getText().toString();
    }

    private void Initialization() {
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        text_signup = (TextView) findViewById(R.id.text_signup);
        email_sign_in_button = (Button) findViewById(R.id.email_sign_in_button);
        email_sign_in_button2 = (Button) findViewById(R.id.email_sign_in_button2);
        info=(TextView)findViewById(R.id.info);
        BlinkTextView(info);
    }
    @Override
    protected void onResume() {
        super.onResume();
       // getIMEI();

    }
    private void getIMEI() {

        MarshmallowPermission permission = new MarshmallowPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permission.result == -1 || permission.result == 0) {
            try {
                tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                if (tm != null) imei = tm.getDeviceId();
            } catch (Exception e) {
            }
        } else if (permission.result == 1) {
            tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) imei = tm.getDeviceId();
                    /* Intent i=new Intent(this,LoginActivity.class);
                     startActivity(i);
	            	 finish();*/
        }
    }
    private class LoginTask extends AsyncTask<String, Void, UserDetails> {

        private final ProgressDialog dialog = new ProgressDialog(Login.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(
                Login.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage(getResources().getString(R.string.authenticating));
            this.dialog.show();
        }

        @Override
        protected UserDetails doInBackground(String... param) {

            if (!Utiilties.isOnline(Login.this)) {
                UserDetails userDetails = new UserDetails();
                userDetails.setAuthenticated(true);
                return userDetails;

            } else {
                return WebserviceHelper.getAdharUserDetail(str_email,str_pass);
            }

        }

        @Override
        protected void onPostExecute( UserDetails result) {

            if (this.dialog.isShowing()) this.dialog.dismiss();

            if(result!=null) {
                if(result.isAuthenticated()) {
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());

                    dataBaseHelper.insertUserDetails(result,str_email);
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("UserId",str_email.toLowerCase()).commit();
                   /* PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("password",result.get_UserId().toLowerCase()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Role",result.getRole().toLowerCase()).commit();
                    if(result.getRole().equals("BLKOPTMOB")){
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Block",result.getBlockCode()).commit();
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("BlockName",result.getBlockName()).commit();
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("DistrictName",result.getDistName()).commit();
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("District",result.getDistCode()).commit();
                    }else if(result.getRole().equalsIgnoreCase("ABU")){
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Block",result.getBlockCode()).commit();
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("BlockName",result.getBlockName()).commit();
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("DistrictName",result.getDistName()).commit();
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("District",result.getDistCode()).commit();
                    }else if(result.getRole().equals("DSWO")){
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("DistrictName",result.getDistName()).commit();
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("District",result.getDistCode()).commit();
                    }*/
                   // new loadPanchayatData(result.getBlockCode()).execute();
                   // String panchyatcode = "";
                  //  panchyatcode=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("PanchayatCode", "");
                  //  if(!panchyatcode.equals("")) {
                        Intent intent=new Intent(Login.this,MainHomeActivity.class);
                        startActivity(intent);
                        finish();

                    /*}else {
                        Intent intent=new Intent(Login.this,ChooseCenter.class);
                        startActivity(intent);
                        finish();
                    }*/

                }else {
                    Toast.makeText(getApplicationContext(),"Invalid User Id and  ",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    public  void BlinkTextView(TextView txt)
    {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        txt.startAnimation(anim);
    }
    private class loadPanchayatData extends AsyncTask<String, Void, ArrayList<panchayat>> {


        String blockcode = "";
        ArrayList<panchayat> blocklist = new ArrayList<>();
        private final ProgressDialog dialog = new ProgressDialog(
                Login.this);

        loadPanchayatData(String distCode) {
            this.blockcode = distCode;
        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<panchayat> doInBackground(String... param) {

            this.blocklist = WebserviceHelper.getPanchayatData(blockcode);

            return this.blocklist;
        }

        @Override
        protected void onPostExecute(ArrayList<panchayat> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();

            }

            if (result != null) {
                if (result.size() > 0) {

                    DataBaseHelper placeData = new DataBaseHelper(Login.this);
                    long i = placeData.setPanchayatLocal(result, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("District", ""),blockcode);
                  //  if (i > 0) setPanchayatList(blockcode);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.loading_fail),
                            Toast.LENGTH_LONG).show();

                }
            }

        }

    }
}
