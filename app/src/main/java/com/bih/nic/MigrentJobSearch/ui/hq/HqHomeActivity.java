package com.bih.nic.MigrentJobSearch.ui.hq;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.GlobalVariables;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.ui.MultiLoginActivity;
import com.bih.nic.MigrentJobSearch.ui.employer.EmployerMainHomeActivity;

public class HqHomeActivity extends Activity {

    DataBaseHelper dataBaseHelper;
    SQLiteDatabase db;
    TextView tv_email;
    String OrgId="",user_name="",lvlthree_id="", mobile="", address="", DistName="", ProfileImg="",CompanyName="", UserId,UserRole="",Block_Code="",lvlone_id="",lvltwo_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hq_home);


        getActionBar().hide();
        Utiilties.setStatusBarColor(this);
        dataBaseHelper=new DataBaseHelper(HqHomeActivity.this);
        tv_email=findViewById(R.id.tv_email);
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

    private void confirmLogout()
    {
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("isLogin",false).commit();
        SharedPreferences settings = this.getSharedPreferences("PreferencesName", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
        GlobalVariables.isLogin=false;
//        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("UserId","").commit();
//        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("UserRole","").commit();
        Intent intent = new Intent(this, MultiLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void onExit()
    {
        new AlertDialog.Builder(this)
                .setTitle("अलर्ट!!")
                .setMessage("क्या आप ऐप बन्द करना चाहते हैं??\n ")
                .setCancelable(false)
                .setPositiveButton("हाँ", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        finish();
                    }
                })
                .setNegativeButton("नहीं", null)
                .show();
    }


    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
        onExit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        OrgId=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("OrgId", "");
        UserId=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "");


        //DistName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("DistName", "");
        Block_Code=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("BlockCode", "");
        lvlone_id=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("LvlOne_Id", "");
        lvltwo_id=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Lvl_TwoId", "");
        lvlthree_id=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("LvlThree_Id", "");
        UserRole=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserRole", "");

        tv_email.setText(UserId);
    }

    public  void onViewConsolidatedReport(View view){

    }
    public  void onViewDeptJobVacency(View view){
        Intent intent = new Intent(this, DeptJobVacencyReportActivity.class);

        startActivity(intent);
    }
}
