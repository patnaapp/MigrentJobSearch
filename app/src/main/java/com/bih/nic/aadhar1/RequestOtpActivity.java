package com.bih.nic.aadhar1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bih.nic.aadhar1.DataBaseHelper.DataBaseHelper;
import com.bih.nic.aadhar1.Model.UserDetails;

public class RequestOtpActivity extends Activity {

    EditText et_reg_no,et_mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_otp);

        getActionBar().hide();
        Utiilties.setStatusBarColor(this);

        initialization();
    }

    private void initialization() {
        et_reg_no = (EditText) findViewById(R.id.et_reg_no);
        et_mobile = (EditText) findViewById(R.id.et_otp);
    }

    public void onRequestOtp(View view){
        if(isValidInput()){
            if(Utiilties.isOnline(getApplicationContext())) {
                //new requestOtp(et_reg_no.getText().toString(),et_mobile.getText().toString()).execute();
            }else {
                Utiilties.internetNotAvailableDialog(RequestOtpActivity.this);
            }
        }
    }

    Boolean isValidInput(){
        View focusView = null;
        boolean validate = true;

        if (et_reg_no.getText().toString().equals("")) {
            et_reg_no.setError("कृप्या सही पंजीकरण संख्या डालें");
            focusView = et_reg_no;
            validate = false;
        }

        if (et_mobile.getText().toString().equals("")) {
            et_mobile.setError("कृप्या सही मोबाइल नम्बर डालें");
            focusView = et_mobile;
            validate = false;
        }else if (et_mobile.getText().toString().length() < 10) {
            et_mobile.setError("मोबाइल नम्बर 10 अंक से कम हैं");
            focusView = et_mobile;
            validate = false;
        }

        if (!validate) focusView.requestFocus();
        return validate;
    }

//    private class requestOtp extends AsyncTask<String, Void, UserDetails> {
//
//        private final ProgressDialog dialog = new ProgressDialog(Login.this);
//
//        private final AlertDialog alertDialog = new AlertDialog.Builder(
//                Login.this).create();
//
//        String regN0,otp;
//
//        public LoginTask(String regN0, String otp) {
//            this.regN0 = regN0;
//            this.otp = otp;
//        }
//
//        @Override
//        protected void onPreExecute() {
//
//            this.dialog.setCanceledOnTouchOutside(false);
//            this.dialog.setMessage(getResources().getString(R.string.authenticating));
//            this.dialog.show();
//        }
//
//        @Override
//        protected UserDetails doInBackground(String... param) {
//
////            if (!Utiilties.isOnline(Login.this)) {
////                UserDetails userDetails = new UserDetails();
////                userDetails.setAuthenticated(true);
////                return userDetails;
////
////            } else {
//            return WebserviceHelper.loginUser(regN0,otp);
//            //}
//
//        }
//
//        @Override
//        protected void onPostExecute( UserDetails result) {
//
//            if (this.dialog.isShowing()) this.dialog.dismiss();
//
//            if(result!=null) {
//                if(result.isAuthenticated()) {
//                    result.set_Passwoed(et_otp.getText().toString());
//                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
//                    dataBaseHelper.insertUserDetails(result,str_email);
//
//                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("UserId",et_reg_no.getText().toString()).commit();
//                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Password",et_otp.getText().toString()).commit();
//                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("UserName",result.getUserName()).commit();
//                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Mobile",result.getMobileNo()).commit();
//                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Age",result.getAge()).commit();
//
//                    Intent intent=new Intent(Login.this,MainHomeActivity.class);
//                    startActivity(intent);
//                    finish();
//
//                }else {
//                    Toast.makeText(getApplicationContext(),result.getMessage(),Toast.LENGTH_LONG).show();
//                }
//            }else{
//                Toast.makeText(getApplicationContext(),"Null Response: Check Network and try again!!",Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}
