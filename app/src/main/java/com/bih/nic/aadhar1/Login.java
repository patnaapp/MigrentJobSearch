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
import android.util.Log;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Login extends Activity {

    Button btn_login;
    EditText et_reg_no, et_otp;
    String str_email, str_pass;
    String[] param;
    TextView text_signup,tv_version;
    TelephonyManager tm;
    private static String imei;
    TextView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        getActionBar().hide();

        Utiilties.setStatusBarColor(Login.this);
        Initialization();

        String version = Utiilties.getAppVersion(this);
        if(version != null){
            tv_version.setText("ऐप वर्ज़न "+version);
        }else{
            tv_version.setText("");
        }


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isValidInput()){
                    if(Utiilties.isOnline(getApplicationContext())) {
                        new LoginTask(et_reg_no.getText().toString(),et_otp.getText().toString()).execute();
                    }else {
                        Utiilties.internetNotAvailableDialog(Login.this);
                    }
                }

            }
        });
        //getIMEI();

    }

    Boolean isValidInput(){
        View focusView = null;
        boolean validate = true;

        if (et_reg_no.getText().toString().equals("")) {
            et_reg_no.setError("कृप्या सही पंजीकरण संख्या डालें");
            focusView = et_reg_no;
            validate = false;
        }

        if (et_otp.getText().toString().equals("")) {
            et_otp.setError("कृप्या सही ओटीपी डालें");
            focusView = et_otp;
            validate = false;
        }

        if (!validate) focusView.requestFocus();
        return validate;
    }

    private void setvalue() {
        str_email = et_reg_no.getText().toString();
        str_pass = et_otp.getText().toString();
    }

    private void Initialization() {
        et_reg_no = (EditText) findViewById(R.id.et_reg_no);
        et_otp = (EditText) findViewById(R.id.et_otp);
        btn_login = (Button) findViewById(R.id.btn_login);

        tv_version = (TextView) findViewById(R.id.tv_version);
    }

    public void onRequestOtp(View view){
        Intent intent = new Intent(this, RequestOtpActivity.class);
        startActivity(intent);
    }

    public void onChangeMobileNo(View view){
        Intent intent = new Intent(this, ChangeMobileNumberActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
       // getIMEI();

    }
//    private void getIMEI() {
//
//        MarshmallowPermission permission = new MarshmallowPermission(this, Manifest.permission.READ_PHONE_STATE);
//        if (permission.result == -1 || permission.result == 0) {
//            try {
//                tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//
//                if (tm != null) imei = tm.getDeviceId();
//            } catch (Exception e) {
//            }
//        } else if (permission.result == 1) {
//            tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//            if (tm != null) imei = tm.getDeviceId();
//                    /* Intent i=new Intent(this,LoginActivity.class);
//                     startActivity(i);
//	            	 finish();*/
//        }
//        }
//    }

    private class LoginTask extends AsyncTask<String, Void, UserDetails> {

        private final ProgressDialog dialog = new ProgressDialog(Login.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(
                Login.this).create();

        String regN0,otp;

        public LoginTask(String regN0, String otp) {
            this.regN0 = regN0;
            this.otp = otp;
        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage(getResources().getString(R.string.authenticating));
            this.dialog.show();
        }

        @Override
        protected UserDetails doInBackground(String... param) {

//            if (!Utiilties.isOnline(Login.this)) {
//                UserDetails userDetails = new UserDetails();
//                userDetails.setAuthenticated(true);
//                return userDetails;
//
//            } else {
                return WebserviceHelper.loginUser(regN0,otp);
            //}

        }

        @Override
        protected void onPostExecute( UserDetails result) {

            if (this.dialog.isShowing()) this.dialog.dismiss();

            if(result!=null) {
                if(result.isAuthenticated()) {
                    result.set_Passwoed(et_otp.getText().toString());
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
                    dataBaseHelper.insertUserDetails(result,str_email);

                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("UserId",et_reg_no.getText().toString()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Password",et_otp.getText().toString()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("UserName",result.getUserName()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Mobile",result.getMobileNo()).commit();
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Age",result.getAge()).commit();


                    Intent intent=new Intent(Login.this,MainHomeActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(getApplicationContext(),result.getMessage(),Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Null Response: Check Network and try again!!",Toast.LENGTH_LONG).show();
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
