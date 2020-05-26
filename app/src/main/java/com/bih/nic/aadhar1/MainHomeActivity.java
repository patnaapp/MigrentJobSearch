package com.bih.nic.aadhar1;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.TestLooperManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bih.nic.aadhar1.Model.BenDetails;
import com.bih.nic.aadhar1.Model.DefaultResponse;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainHomeActivity extends Activity {
    LinearLayout ll_profile,ll_register_Grivance;
    String Reg_No="",user_name="", mobile="", address="";
    TextView tv_benname,urole,tv_mobile,tv_address;
    CircleImageView profile_image;

   BenDetails BenDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        getActionBar().hide();

        BenDetails=new BenDetails();
        Utiilties.setStatusBarColor(MainHomeActivity.this);
        Reg_No=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "");
        user_name=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserName", "");
        mobile=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Mobile", "");
        address=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Address", "");

        ll_profile=(LinearLayout)findViewById(R.id.ll_profile);
        ll_register_Grivance=(LinearLayout)findViewById(R.id.ll_register_Grivance);
        tv_benname=(TextView) findViewById(R.id.tv_benname);
        urole=(TextView) findViewById(R.id.urole);
        tv_mobile=(TextView) findViewById(R.id.tv_mobile);
        tv_address=(TextView) findViewById(R.id.tv_address);

        profile_image=(CircleImageView) findViewById(R.id.profile_image);

        tv_benname.setText(user_name);
        urole.setText("  पंजीकरण संख्या: "+Reg_No);
        tv_mobile.setText("  मोबाइल नंबर: "+mobile);
        tv_address.setText("  पता: "+address);


        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!GlobalVariables.isOffline && !Utiilties.isOnline(MainHomeActivity.this)) {

                    AlertDialog.Builder ab = new AlertDialog.Builder(MainHomeActivity.this);
                    ab.setMessage(Html.fromHtml(
                            "<font color=#000000>Internet Connection is not avaliable..Please Turn ON Network Connection </font>"));
                    ab.setPositiveButton("Turn On Network Connection", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(I);
                        }
                    });

                    ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
                    ab.show();

                }else{

                    new FetchBenData1().execute();
                    //new ValidateAdhhar(benfiList).execute();
                }

            }
        });
        ll_register_Grivance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!GlobalVariables.isOffline && !Utiilties.isOnline(MainHomeActivity.this)) {

                    AlertDialog.Builder ab = new AlertDialog.Builder(MainHomeActivity.this);
                    ab.setMessage(Html.fromHtml(
                            "<font color=#000000>Internet Connection is not avaliable..Please Turn ON Network Connection </font>"));
                    ab.setPositiveButton("Turn On Network Connection", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            new FetchBenData().execute();
                            Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(I);
                        }
                    });

                    ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
                    ab.show();

                }else{
                    new FetchBenData().execute();
                    //new ValidateAdhhar(benfiList).execute();
                }

            }
        });
       // ActionBar actionBar = getActionBar();
       // actionBar.setTitle("Physical Verification");
    }

    public void onSearchJob(View view){
        Intent i =new Intent(MainHomeActivity.this,JobSearchActivity.class);
        i.putExtra("data",BenDetails);
        startActivity(i);
    }

    public void onLogout(View view){
        new AlertDialog.Builder(this)
                .setTitle("लॉग आउट ?")
                .setMessage("क्या आप वाकई एप्लिकेशन से लॉगआउट करना चाहते हैं \n ")
                .setCancelable(false)
                .setPositiveButton("हाँ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        confirmLogout();
                    }
                })
                .setNegativeButton("नहीं", null)
                .show();
    }

    private void confirmLogout(){
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("UserId","").commit();

        Intent intent = new Intent(this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private class FetchBenData extends AsyncTask<String, Void, BenDetails> {
        BenDetails data;
        String _uid;
        private final ProgressDialog dialog = new ProgressDialog(MainHomeActivity.this);
        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(MainHomeActivity.this).create();

//
//        UPLOADDATA(BarcodeEntity data) {
//            this.data = data;
//            this._uid = data.getUniqueNo();
//
//        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("लोड हो रहा है...");
            if (!MainHomeActivity.this.isFinishing()) {
                this.dialog.show();
            }
        }

        @Override
        protected BenDetails doInBackground(String... param) {

//
//            String res = WebServiceHelper.UploadFinalData(data, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USERID", ""));
//            return res;

            return WebserviceHelper.getBen_Details(Reg_No);

        }

        @Override
        protected void onPostExecute(BenDetails result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            Log.d("Responsevalue", "" + result);
            if (result != null) {
                BenDetails=result;
                Intent i =new Intent(MainHomeActivity.this,ModifyDocumentActivity.class);
                i.putExtra("data",BenDetails);
                startActivity(i);


            } else {
                //chk_msg_OK_networkdata("Uploading failed.Please Try Again Later");
                Toast.makeText(getApplicationContext(), "Result Null..Please Try Later", Toast.LENGTH_SHORT).show();
            }

        }
    } private class FetchBenData1 extends AsyncTask<String, Void, BenDetails> {
        BenDetails data;
        String _uid;
        private final ProgressDialog dialog = new ProgressDialog(MainHomeActivity.this);
        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(MainHomeActivity.this).create();

//
//        UPLOADDATA(BarcodeEntity data) {
//            this.data = data;
//            this._uid = data.getUniqueNo();
//
//        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("लोड हो रहा है...");
            if (!MainHomeActivity.this.isFinishing()) {
                this.dialog.show();
            }
        }

        @Override
        protected BenDetails doInBackground(String... param) {

//
//            String res = WebServiceHelper.UploadFinalData(data, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("USERID", ""));
//            return res;

            return WebserviceHelper.getBen_Details(Reg_No);

        }

        @Override
        protected void onPostExecute(BenDetails result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            Log.d("Responsevalue", "" + result);
            if (result != null) {
                BenDetails=result;
                Intent i =new Intent(MainHomeActivity.this,ProfileActivity.class);
                i.putExtra("data",BenDetails);
                startActivity(i);


            } else {
                //chk_msg_OK_networkdata("Uploading failed.Please Try Again Later");
                Toast.makeText(getApplicationContext(), "Result Null..Please Try Later", Toast.LENGTH_SHORT).show();
            }

        }
    }


}
