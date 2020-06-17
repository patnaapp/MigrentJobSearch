package com.bih.nic.MigrentJobSearch.ui.hq;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bih.nic.MigrentJobSearch.DataBaseHelper.DataBaseHelper;
import com.bih.nic.MigrentJobSearch.GlobalVariables;
import com.bih.nic.MigrentJobSearch.OfficeReport.DistricWise;
import com.bih.nic.MigrentJobSearch.R;
import com.bih.nic.MigrentJobSearch.Utiilties;
import com.bih.nic.MigrentJobSearch.ui.MultiLoginActivity;
import com.bih.nic.MigrentJobSearch.ui.dept.ApproveRejectWorkSite_activity;
import com.bih.nic.MigrentJobSearch.ui.dept.Job_Offer_activity_Dept;
import com.bih.nic.MigrentJobSearch.ui.dstadm.WorkReqApproval_Dst_Activity;
import com.bih.nic.MigrentJobSearch.ui.dstadm.WorkSiteApproval_dst_activity;

public class HqHomeActivity extends Activity {

    DataBaseHelper dataBaseHelper;
    SQLiteDatabase db;
    TextView tv_email,tv_dept_name,tv_version;
    LinearLayout ll_first,ll_username,aprove_rjct_worksite,ll_emp_reports;
    String OrgId="",user_name="",lvlthree_id="", mobile="", address="", DistName="", ProfileImg="",CompanyName="", UserId,UserRole="",Block_Code="",lvlone_id="",lvltwo_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hq_home);

        // getActionBar().hide();
        Utiilties.setStatusBarColor(this);
        dataBaseHelper=new DataBaseHelper(HqHomeActivity.this);
        tv_email=findViewById(R.id.tv_email);
        tv_dept_name=findViewById(R.id.tv_dept_name);
        ll_first=findViewById(R.id.ll_first);
        ll_emp_reports=findViewById(R.id.ll_emp_reports);
        aprove_rjct_worksite=findViewById(R.id.aprove_rjct_worksite);
        ll_username=findViewById(R.id.ll_username);
        tv_version=(TextView) findViewById(R.id.tv_version);

        String version = Utiilties.getAppVersion(this);
        if(version != null){
            tv_version.setText("ऐप वर्ज़न "+version);
        }else{
            tv_version.setText("");
        }

        OrgId=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("OrgId", "");
        UserId=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserId", "");


        //DistName=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("DistName", "");
        Block_Code=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("BlockCode", "");
        lvlone_id=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("LvlOne_Id", "");
        lvltwo_id=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Lvl_TwoId", "");
        lvlthree_id=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("LvlThree_Id", "");
        UserRole=PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserRole", "");
        String username = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("UserName", "");
        tv_email.setText(UserId);
        if(UserRole.equals("ORGADM"))
        {
            ll_first.setVisibility(View.GONE);
            ll_username.setVisibility(View.VISIBLE);
            aprove_rjct_worksite.setVisibility(View.VISIBLE);
            tv_dept_name.setText(username);
        }
        else if (UserRole.equals("DSTADM"))
        {
            ll_first.setVisibility(View.GONE);
            ll_emp_reports.setVisibility(View.GONE);
            ll_username.setVisibility(View.VISIBLE);
            aprove_rjct_worksite.setVisibility(View.VISIBLE);
            tv_dept_name.setText(username);
        }

        else
        {
            ll_username.setVisibility(View.GONE);
            aprove_rjct_worksite.setVisibility(View.GONE);
        }
    }

    public void OnClick_goToLoginScreen(View view)
    {
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
        //super.onBackPressed();
        onExit();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    public  void onViewConsolidatedReport(View view)
    {
        Intent intent = new Intent(this, DistricWise.class);
        startActivity(intent);
    }

    public  void onViewDeptJobVacency(View view){
        Intent intent = new Intent(this, DeptJobVacencyReportActivity.class);
        startActivity(intent);
    }

    public void  onJobOfferReport(View view){
        if(UserRole.equals("ORGADM"))
        {
            Intent intent = new Intent(this, Job_Offer_activity_Dept.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, Job_Offer_activity_HQ.class);
            startActivity(intent);
        }

    }

    public void onViewWorkOrgDetail(View view){
        Intent intent = new Intent(this, WorksiteEmplyeeReportActivity.class);
        startActivity(intent);
    }

    public void onApproveWorkSite(View view){

        if(UserRole.equals("ORGADM"))
        {
            Intent intent = new Intent(this, ApproveRejectWorkSite_activity.class);
            startActivity(intent);
        }
        else if (UserRole.equals("DSTADM"))
        {
            Intent intent = new Intent(this, WorkSiteApproval_dst_activity.class);
            startActivity(intent);
        }

    }
}
