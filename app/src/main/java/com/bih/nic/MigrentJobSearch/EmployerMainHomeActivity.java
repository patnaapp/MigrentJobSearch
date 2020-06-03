package com.bih.nic.MigrentJobSearch;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.Model.BenDetails;
import com.bih.nic.MigrentJobSearch.Model.PaymentStatusEntity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployerMainHomeActivity extends Activity {
    LinearLayout ll_profile,ll_new_labours;
    String Reg_No="",user_name="", mobile="", address="", DistName="", ProfileImg="",workplace="";
    TextView tv_benname,urole,tv_mobile,tv_address,tv_version;
    CircleImageView profile_image;
    TextView tv_workplace,tv_phone,tv_work_address;
    //ImageView profile_image;

    BenDetails BenDetails;
    DataBaseHelper dataBaseHelper;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_home_new);
        getActionBar().hide();

        BenDetails=new BenDetails();
        dataBaseHelper=new DataBaseHelper(EmployerMainHomeActivity.this);
        Utiilties.setStatusBarColor(EmployerMainHomeActivity.this);

        ll_profile=(LinearLayout)findViewById(R.id.ll_profile);
        ll_new_labours=(LinearLayout)findViewById(R.id.ll_new_labours);
        tv_benname=(TextView) findViewById(R.id.tv_benname);
        urole=(TextView) findViewById(R.id.urole);
        tv_mobile=(TextView) findViewById(R.id.tv_mobile);
        tv_address=(TextView) findViewById(R.id.tv_address);
        tv_version=(TextView) findViewById(R.id.tv_version);


        tv_workplace=(TextView) findViewById(R.id.tv_workplace);
        tv_phone=(TextView) findViewById(R.id.tv_phone);
        tv_work_address=(TextView) findViewById(R.id.tv_work_address);

        profile_image=(CircleImageView) findViewById(R.id.profile_image);

        String version = Utiilties.getAppVersion(this);
        if(version != null){
            tv_version.setText("ऐप वर्ज़न "+version);
        }else{
            tv_version.setText("");
        }

        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        ll_new_labours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Reg_No=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "");
        user_name=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserName", "");


        DistName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("DistName", "");
        workplace=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Photo", "");
        mobile=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Mobile", "");
        address=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Photo", "");


//        tv_benname.setText(user_name);
        tv_workplace.setText(workplace);
        tv_phone.setText(mobile);
        tv_work_address.setText(address);

    }

    public void OnClick_goToLoginScreen(View view){
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

    public void onExit(){
        new AlertDialog.Builder(this)
                .setTitle("अलर्ट!!")
                .setMessage("क्या आप ऐप बन्द करना चाहते हैं??\n ")
                .setCancelable(false)
                .setPositiveButton("हाँ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("नहीं", null)
                .show();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        onExit();
    }



}
