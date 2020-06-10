package com.bih.nic.MigrentJobSearch.ui.hq;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bih.nic.MigrentJobSearch.GlobalVariables;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.ui.MultiLoginActivity;
import com.bih.nic.MigrentJobSearch.ui.employer.EmployerMainHomeActivity;
import com.bih.nic.MigrentJobSearch.ui.employer.JobOfferPostedActivity;
import com.bih.nic.MigrentJobSearch.ui.employer.LabourSearchActivity;

public class HqHomeActivity extends Activity {

    String userId="";
    TextView tv_username,tv_version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hq_home);

        Utiilties.setStatusBarColor(this);

        initialise();
    }

    public void initialise(){
        tv_username = findViewById(R.id.tv_username);
        tv_version = findViewById(R.id.tv_version);

        userId= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "");
        tv_username.setText(userId);

        String version = Utiilties.getAppVersion(this);
        if(version != null){
            tv_version.setText("ऐप वर्ज़न "+version);
        }else{
            tv_version.setText("");
        }
    }

    public void onViewConsolidatedReport(View view){
//        Intent intent = new Intent(this, LabourSearchActivity.class);
//        startActivity(intent);
    }

    public void onViewDeptJobVacency(View view){
        Intent intent = new Intent(this, JobOfferPostedActivity.class);
        startActivity(intent);
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
        onExit();
    }
}
